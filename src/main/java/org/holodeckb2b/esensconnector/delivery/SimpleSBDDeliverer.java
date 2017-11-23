/*     */ package org.holodeckb2b.esensconnector.delivery;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import no.difi.vefa.peppol.evidence.rem.SignedRemEvidence;
/*     */ import no.difi.vefa.sbdh.SbdhParserFactory;
/*     */ import no.difi.vefa.sbdh.api.SbdhParser;
/*     */ import org.holodeckb2b.common.exceptions.DatabaseException;
/*     */ import org.holodeckb2b.common.util.Utils;
/*     */ import org.holodeckb2b.esensconnector.etsi.rem.EvidenceCreationFailed;
/*     */ import org.holodeckb2b.esensconnector.etsi.rem.EvidenceHelper;
/*     */ import org.holodeckb2b.esensconnector.etsi.rem.EvidenceHelper.EvidenceType;
/*     */ import org.holodeckb2b.esensconnector.persistency.SimpleSBDHData;
/*     */ import org.holodeckb2b.esensconnector.persistency.SimpleSBDHData.Direction;
/*     */ import org.holodeckb2b.esensconnector.persistency.SimpleSBDHDataDAO;
/*     */ import org.holodeckb2b.esensconnector.submit.SBDHHandler;
/*     */ import org.holodeckb2b.esensconnector.submit.SBDHHandlerException;
/*     */ import org.holodeckb2b.interfaces.delivery.IMessageDeliverer;
/*     */ import org.holodeckb2b.interfaces.delivery.MessageDeliveryException;
/*     */ import org.holodeckb2b.interfaces.messagemodel.IMessageUnit;
/*     */ import org.holodeckb2b.interfaces.messagemodel.IPayload;
/*     */ import org.holodeckb2b.interfaces.messagemodel.IReceipt;
/*     */ import org.holodeckb2b.interfaces.messagemodel.ISignalMessage;
/*     */ import org.holodeckb2b.interfaces.messagemodel.IUserMessage;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleSBDDeliverer
/*     */   implements IMessageDeliverer
/*     */ {
/*  58 */   private static Logger log = LoggerFactory.getLogger(SimpleSBDDeliverer.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  63 */   private String sbdOutDirectory = null;
/*     */   
/*     */ 
/*     */ 
/*  67 */   private String sigOutDirectory = null;
/*     */   
/*     */   public SimpleSBDDeliverer(String sbdDirectory, String signalsDirectory) {
/*  70 */     this.sbdOutDirectory = sbdDirectory;
/*  71 */     this.sigOutDirectory = signalsDirectory;
/*     */   }
/*     */   
/*     */   public void deliver(IMessageUnit rcvdMsgUnit) throws MessageDeliveryException
/*     */   {
/*  76 */     if ((rcvdMsgUnit instanceof IUserMessage)) {
/*  77 */       deliverUserMessage((IUserMessage)rcvdMsgUnit);
/*     */     } else {
/*  79 */       deliverSignalMessage((ISignalMessage)rcvdMsgUnit);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */
protected void deliverUserMessage(IUserMessage userMessage)
        throws MessageDeliveryException
{
    String payloadLocation;
    if(Utils.isNullOrEmpty(userMessage.getPayloads()) || userMessage.getPayloads().size() > 1)
    {
        log.error("UserMessage [msgId={}] contains no or more than one payload!", userMessage.getMessageId());
        throw new MessageDeliveryException((new StringBuilder()).append(getClass().getSimpleName()).append(" can only deliver UserMessage message units with 1 payload!").toString());
    }
    payloadLocation = ((IPayload)userMessage.getPayloads().iterator().next()).getContentLocation();
    SimpleSBDHData sbdInfo;
    try
    {
        log.debug("Check that payload is a SBD");
        SbdhParser sp = SbdhParserFactory.sbdhParserAndExtractor();
        FileInputStream fi = new FileInputStream(payloadLocation);
        SBDHHandler sbdh = new SBDHHandler(sp.parse(fi));
        fi.close();
        log.debug("Calculate digest of contained ASiC for inclusion in REM evidence");
        byte asicDigest[] = EvidenceHelper.calculateASICDigest(payloadLocation);
        log.debug("Store meta-data of SBD contained in the payload in the database");
        sbdInfo = SimpleSBDHDataDAO.storeDocument(sbdh, asicDigest, userMessage.getMessageId(), org.holodeckb2b.esensconnector.persistency.SimpleSBDHData.Direction.IN);
    }
    catch(Exception e)
    {
        log.error("Could not process the payload contained in the message as a SBD!\n\tError: {} - {}", e.getClass().getSimpleName(), e.getMessage());
        throw new MessageDeliveryException((new StringBuilder()).append("Payload is not an SBD and can therefor not be delivered using ").append(getClass().getSimpleName()).toString());
    }
    Path destFilename = null;
    StringBuilder bDestFilename = new StringBuilder("sbd-");
    bDestFilename.append(sbdInfo.getSenderId()).append('-').append(sbdInfo.getDocumentName()).append('-').append(sbdInfo.getInstanceId()).append(".xml");
    try
    {
        log.debug("Saving the business document to output folder: {}", sbdOutDirectory);
        destFilename = Utils.createFileWithUniqueName((new StringBuilder()).append(sbdOutDirectory).append("/").append(bDestFilename.toString().replaceAll("[^a-zA-Z0-9.-]", "_")).toString());
        Files.move(Paths.get(payloadLocation, new String[0]), destFilename, new CopyOption[] {
                StandardCopyOption.REPLACE_EXISTING
        });
        log.info("Delivered SBD from message [{}] to {}", userMessage.getMessageId(), destFilename);
    }
    catch(IOException e)
    {
        log.error("An error occurred when saving the SBD to the output directory ({})", sbdOutDirectory);
        try
        {
            if(destFilename != null)
                Files.deleteIfExists(destFilename);
        }
        catch(IOException ex)
        {
            log.error("Could not delete temporarily file {}. Please remove manually", destFilename.toString());
        }
        throw new MessageDeliveryException((new StringBuilder()).append("Could not move the SBD to specified out directory [").append(sbdOutDirectory).append("]").toString());
    }
    return;
}

private void deliverSignalMessage(ISignalMessage signalMessage)
        throws MessageDeliveryException
{
    String refToMessageId = signalMessage.getRefToMessageId();
    if(Utils.isNullOrEmpty(refToMessageId))
    {
        log.error((new StringBuilder()).append(signalMessage.getClass().getSimpleName()).append(" [msgId={}] does not reference a sent UserMessage! Unable to notify business application!").toString());
        throw new MessageDeliveryException("Signal does not contain refToMessageId!");
    }
    SimpleSBDHData sbdMetaData;
    try
    {
        log.debug("Get meta-data for document in message with id: {}", refToMessageId);
        sbdMetaData = SimpleSBDHDataDAO.getDocumentInfoForMessageId(refToMessageId, org.holodeckb2b.esensconnector.persistency.SimpleSBDHData.Direction.OUT);
    }
    catch(DatabaseException ex)
    {
        log.error("Could not retrieve information from the connector database! Error: {}", ex.getMessage());
        sbdMetaData = null;
    }
    if(sbdMetaData == null)
    {
        log.warn("Received {} signal [msgId={}] can not be related to sent business document!", signalMessage.getClass().getSimpleName(), signalMessage.getMessageId());
        throw new MessageDeliveryException("Signal is not related to a sent business document!");
    }
    log.debug("Create the signed REM evidence for this signal");
    SignedRemEvidence signedEvidence;
    try
    {
        signedEvidence = EvidenceHelper.createEvidence(org.holodeckb2b.esensconnector.etsi.rem.EvidenceHelper.EvidenceType.AcceptanceNonAcceptance, signalMessage instanceof IReceipt, null, sbdMetaData);
    }
    catch(EvidenceCreationFailed ecf)
    {
        log.error("An error occurred during creation of evidence for Signal {}.\n\tError details: {}", signalMessage.getMessageId(), ecf.getMessage());
        throw new MessageDeliveryException("Could not create evidence for Signal", ecf);
    }
    try
    {
        EvidenceHelper.saveEvidenceToFile(signedEvidence, sigOutDirectory, "sig-");
    }
    catch(Exception ex)
    {
        log.error("Could not write evidence for Signal {} to output directory {}!\n\tError details: {}", new Object[] {
                signalMessage.getMessageId(), sigOutDirectory, ex.getMessage()
        });
        throw new MessageDeliveryException("Could not write evidence for Signal to output directory", ex);
    }
}
}