package org.holodeckb2b.esensconnector.submit.workers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import no.difi.vefa.sbdh.SbdhParserFactory;
import no.difi.vefa.sbdh.api.SbdhParser;
import org.holodeckb2b.common.exceptions.DatabaseException;
import org.holodeckb2b.common.util.Utils;
import org.holodeckb2b.common.workers.DirWatcher;
import org.holodeckb2b.common.workers.PathWatcher;
import org.holodeckb2b.esensconnector.config.ConfigurationException;
import org.holodeckb2b.esensconnector.config.PModeConfigurator;
import org.holodeckb2b.esensconnector.etsi.rem.EvidenceHelper;
import org.holodeckb2b.esensconnector.persistency.SimpleSBDHData;
import org.holodeckb2b.esensconnector.persistency.SimpleSBDHDataDAO;
import org.holodeckb2b.esensconnector.submit.IPModeSMPInfo;
import org.holodeckb2b.esensconnector.submit.ISBDHHandler;
import org.holodeckb2b.esensconnector.submit.MMDHandler;
import org.holodeckb2b.esensconnector.submit.SBDHHandler;
import org.holodeckb2b.esensconnector.submit.SBDHHandlerException;
import org.holodeckb2b.interfaces.core.HolodeckB2BCoreInterface;
import org.holodeckb2b.interfaces.messagemodel.IUserMessage;
import org.holodeckb2b.interfaces.submit.IMessageSubmitter;
import org.holodeckb2b.interfaces.submit.MessageSubmitException;
import org.holodeckb2b.interfaces.workerpool.TaskConfigurationException;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.StandardBusinessDocumentHeader;

public class SBDHDirWatcher extends DirWatcher {
    private String output_dir = null;
    private String profilePath = null;
    private ISBDHHandler.SMLZone sml_zone = null;
    private PModeConfigurator pmodeConfigurator = null;

    protected void onChange(File f, PathWatcher.Event event){
        if (event != PathWatcher.Event.ADDED) {
            this.log.debug(event.toString().toLowerCase() + " " + f.getName() + " ignored");
            return;
        }

        String cFileName = f.getAbsolutePath();
        String tFileName = cFileName + ".processing";

        if (!f.renameTo(new File(tFileName))) {
            this.log.info(f.getName() + " is not processed because it could be renamed");
            return;
        }
        try {
            this.log.debug("Read SBDH meta data from " + f.getName());
            SbdhParser sp = SbdhParserFactory.sbdhParserAndExtractor();

            FileInputStream fi = new FileInputStream(tFileName);
            StandardBusinessDocumentHeader header = sp.parse(fi);
            ISBDHHandler sbdhHandler = new SBDHHandler(header);

            this.log.debug("Get SMP meta-data");
            IPModeSMPInfo smpInfo = sbdhHandler.getPModeSMPInfo(this.sml_zone);

            String pmodeId = this.pmodeConfigurator.createSendingPMode(smpInfo);

            if (Utils.isNullOrEmpty(pmodeId)) {
                throw new ConfigurationException("Could not setup P-Mode!");
            }
            this.log.debug("P-Mode configured for sending the SBDH");

            byte[] digest = EvidenceHelper.calculateASICDigest(tFileName);
            this.log.debug("Calculated digest of embedded ASiC");

            IUserMessage mmd = MMDHandler.createSubmitDocument(pmodeId, sbdhHandler.getFirstSenderIdValue(), sbdhHandler.getFirstReceiverIdValue(), tFileName);

            this.log.debug("Message ready for submission");
            IMessageSubmitter msgSubmitter = HolodeckB2BCoreInterface.getMessageSubmitter();
            String messageId = msgSubmitter.submitMessage(mmd, false);
            this.log.debug("Document successfully submitted to Holodeck B2B, assigned msgId=" + messageId);

            SimpleSBDHDataDAO.storeDocument(sbdhHandler, digest, messageId, SimpleSBDHData.Direction.OUT);
            this.log.debug("Saved meta-data to database");

            this.log.info("Succesfully submitted SBDH from " + f.getName() + " to Holodeck B2B Core (msgId=" + messageId + ")");

            new File(tFileName).renameTo(new File(cFileName + ".accepted"));
        } catch (FileNotFoundException ex) {
            this.log.error("Submitted file [" + cFileName + "] was removed before processing could start!");
            return;
        }
        catch (SBDHHandlerException ex) {
            this.log.error("Submitted file [" + cFileName + "] does not contain a valid SBDH!" + "\n\tError details: " + ex
                    .getMessage());
            writeErrorFile(cFileName, ex);
            return;
        }
        catch (Throwable t) {
            this.log.error("An error occured when reading message meta data from " + f.getName() + "\n\tDetails: " + t.getMessage());
            writeErrorFile(cFileName, t);
            new File(tFileName).renameTo(new File(cFileName + ".rejected"));
        }
    }

    public void setParameters(Map<String, ?> parameters) throws TaskConfigurationException {
        super.setParameters(parameters);
        setExtension("xml");

        this.output_dir = ((String)parameters.get("payload_dir"));
        if (Utils.isNullOrEmpty(this.output_dir)) {
            throw new TaskConfigurationException("You must provide the \"payload_dir\" parameter!");
        }

        this.profilePath = ((String)parameters.get("pmode_profile"));
        if (Utils.isNullOrEmpty(this.profilePath)) {
            throw new TaskConfigurationException("You must provide the \"pmode_profile\" parameter!");
        }

        if ("TEST".equalsIgnoreCase((String)parameters.get("sml_zone"))) {
            this.sml_zone = ISBDHHandler.SMLZone.TEST;
        } else {
            this.sml_zone = ISBDHHandler.SMLZone.PRODUCTION;
        }
        try {
            this.pmodeConfigurator = new PModeConfigurator(this.profilePath);
            this.pmodeConfigurator.createReceivingPMode();
        } catch (ConfigurationException ex) {
            this.log.fatal("Failed to initialize e-SENS connector! Could not create P-Mode for receiving messages.");
            throw new TaskConfigurationException("Could not create P-Mode for receiving messages.");
        }

        this.log.info("Initialized e-SENS Connector using parameters:\n\tWatched directory : " + this.watchPath + "\n\tPMode profile     : " + this.profilePath + "\n\tPayloads directory: " + this.output_dir + "\n\tSML zone          : " + this.sml_zone.toString());
    }

    protected void writeErrorFile(String fileName, Throwable fault) {
        String eFileName = fileName + ".error";

        this.log.debug("Writing submission error to error file: " + eFileName);
        try { PrintWriter errorFile = new PrintWriter(new File(eFileName));Throwable localThrowable3 = null;
            try { errorFile.write("The submission could not be completed due to an error:\n\n");

                String errorType = null;
                String errorMsg = fault.getMessage();

                if ((fault instanceof SBDHHandlerException)) {
                    errorType = "SMP Lookup";
                } else if ((fault instanceof ConfigurationException)) {
                    errorType = "P-Mode configuration";
                    errorMsg = "The P-Mode required for sending could not be created";
                } else if ((fault instanceof NoSuchAlgorithmException)) {
                    errorType = "Hashing of the ASiC";
                    errorMsg = "No SHA-256 implementation available";
                } else if ((fault instanceof IOException)) {
                    errorType = "Hashing of the ASiC";
                    errorMsg = "ASiC could not be extracted from the submittted document";
                } else if ((fault instanceof MessageSubmitException)) {
                    errorType = "Submission of document to Holodeck B2B";
                } else if ((fault instanceof DatabaseException)) {
                    errorType = "Storing the meta-data";
                    errorMsg = "Due to problem saving the document meta-data further reporting is limited";
                } else {
                    errorType = "Other";
                }

                errorFile.write("Error category   : " + errorType + "\n");
                errorFile.write("Error description: " + errorMsg + "\n");

                List<Throwable> errorStack = Utils.getCauses(fault);

                StringBuilder expTrace = new StringBuilder("\n\nException trace  : ").append(((Throwable)errorStack.get(0)).getClass().getSimpleName());
                for (int i = 1; i < errorStack.size(); i++) {
                    expTrace.append("\n\tCaused by: ").append(((Throwable)errorStack.get(i)).getClass().getSimpleName());
                }
                expTrace.append(" {").append(((Throwable)errorStack.get(errorStack.size() - 1)).getMessage()).append('}');
                errorFile.write(expTrace.toString());

                this.log.debug("Error information written to file");
            }
            catch (Throwable localThrowable1)
            {
                localThrowable3 = localThrowable1;throw localThrowable1;
            }
            finally
            {
                if (errorFile != null) if (localThrowable3 != null) try { errorFile.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else errorFile.close();
            }
        } catch (FileNotFoundException ioe) {
            this.log.error("Could not write error information to error file!");
        }
    }
}