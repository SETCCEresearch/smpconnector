package org.holodeckb2b.esensconnector.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.ImmutableFieldKeySorter;
import com.thoughtworks.xstream.converters.reflection.Sun14ReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.holodeckb2b.esensconnector.config.pmode.BusinessInfo;
import org.holodeckb2b.esensconnector.config.pmode.EncryptionConfig;
import org.holodeckb2b.esensconnector.config.pmode.Leg;
import org.holodeckb2b.esensconnector.config.pmode.PartnerConfig;
import org.holodeckb2b.esensconnector.config.pmode.PartyId;
import org.holodeckb2b.esensconnector.config.pmode.ProfileBasedPMode;
import org.holodeckb2b.esensconnector.config.pmode.Protocol;
import org.holodeckb2b.esensconnector.config.pmode.ReceiptConfiguration;
import org.holodeckb2b.esensconnector.config.pmode.ReceptionAwarenessConfig;
import org.holodeckb2b.esensconnector.config.pmode.SecurityConfig;
import org.holodeckb2b.esensconnector.config.pmode.UserMessageFlow;
import org.holodeckb2b.esensconnector.submit.IPModeSMPInfo;
import org.holodeckb2b.interfaces.config.IConfiguration;
import org.holodeckb2b.interfaces.core.HolodeckB2BCoreInterface;
import org.holodeckb2b.interfaces.general.IPartyId;
import org.holodeckb2b.interfaces.general.IService;
import org.holodeckb2b.interfaces.general.ReplyPattern;
import org.holodeckb2b.interfaces.pmode.*;
import org.holodeckb2b.interfaces.pmode.ILeg.Label;
import org.holodeckb2b.interfaces.pmode.security.ISecurityConfiguration;
import org.holodeckb2b.interfaces.pmode.security.ISigningConfiguration;
import org.holodeckb2b.pmode.xml.PMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.x509.X500Name;

public class PModeConfigurator
{
    private static Logger log = LoggerFactory.getLogger(PModeConfigurator.class);
    public static final String RECEIVING_PMODE_ID = "hb2b-esens-receiving";
    private static final String SENDING_PMODE_ID_PREFIX = "hb2b-esens-";
    private IPMode pmodeProfile = null;

    public PModeConfigurator(String profilePath) throws ConfigurationException
    {
        try
        {
            File f = new File(profilePath);
            this.pmodeProfile = PMode.createFromFile(f);
        } catch (Exception ex) {
            log.error("Could not load the P-Mode profile from {}!", profilePath);
            throw new ConfigurationException("Could not load the P-Mode profile from " + profilePath + "!", ex);
        }
    }

    public String createReceivingPMode() throws ConfigurationException
    {
        ProfileBasedPMode receivingPMode = null;
        try
        {
            log.debug("Create new P-Mode for receiving based on profile");
            receivingPMode = new ProfileBasedPMode(this.pmodeProfile);
            receivingPMode.setId("hb2b-esens-receiving");
            receivingPMode.setInitiator(null);
            receivingPMode.setResponder(this.pmodeProfile.getInitiator());
            receivingPMode.getResponder().setRole(this.pmodeProfile.getResponder().getRole());
            ISigningConfiguration sigConfig = this.pmodeProfile.getInitiator().getSecurityConfiguration().getSignatureConfiguration();
            EncryptionConfig decrConfig = new EncryptionConfig(null);
            decrConfig.setKeystoreAlias(sigConfig.getKeystoreAlias());
            decrConfig.setCertificatePassword(sigConfig.getCertificatePassword());
            receivingPMode.getResponder().getSecurityConfiguration().setEncryptionConfiguration(decrConfig);
            receivingPMode.getLeg(ILeg.Label.REQUEST).getReceiptConfiguration().setPattern(ReplyPattern.RESPONSE);
            receivingPMode.getLeg(ILeg.Label.REQUEST).getReceptionAwareness().setDuplicateDetection(true);
        }
        catch (Exception ex)
        {
            log.error("Could not create a P-Mode for receiving messages based on P-Mode profile.");
            throw new ConfigurationException("Could not create a P-Mode for receiving messages based on P-Mode profile", ex);
        }


        log.debug("P-Mode for receiving message created, installing in Holodeck B2B Core");
        try {
            IPModeSet hb2bPModeSet = HolodeckB2BCoreInterface.getPModeSet();
            if (hb2bPModeSet.containsId(receivingPMode.getId())) {
                log.info("Existing version of connector P-Mode exists and will be replaced");
                hb2bPModeSet.remove(receivingPMode.getId());
            }
            log.debug("Loading P-Mode for received messages into the Holodeck B2B Core");
            hb2bPModeSet.add(receivingPMode);
            log.info("Loaded P-Mode for received messages into the Holodeck B2B Core. PMode.Id={}", receivingPMode
                    .getId());

            return receivingPMode.getId();
        }
        catch (PModeSetException pmsException) {
            log.error("Could not install P-Mode for receiving message in Holodeck B2B Core! Details: {}", pmsException
                    .getMessage());
            throw new ConfigurationException("Could not install P-Mode for receiving message in Holodeck B2B Core!", pmsException);
        }
    }

    public String createSendingPMode(IPModeSMPInfo smpInfo)
    {
        ProfileBasedPMode pmode = new ProfileBasedPMode(this.pmodeProfile);
        IPartyId partyId = getPartyIdFromCertificate(smpInfo.getCertificate());
        PartnerConfig responderCfg = pmode.getResponder();
        responderCfg.setPartyIds(null);
        responderCfg.addPartyId(partyId);
        StringBuilder pmodeId = new StringBuilder("pm-esens-");
        pmodeId.append(partyId.getId()).append('-');
        pmodeId.append(smpInfo.getService().getName()).append('-');
        pmodeId.append(smpInfo.getAction());
        pmode.setId(pmodeId.toString());

        Protocol protocolInfo = pmode.getLeg(ILeg.Label.REQUEST).getProtocol();
        if (protocolInfo == null)
            protocolInfo = new Protocol(null);
        protocolInfo.setAddress(smpInfo.getURL());
        pmode.getLeg(ILeg.Label.REQUEST).setProtocol(protocolInfo);

        BusinessInfo busInfo = (BusinessInfo)pmode.getLeg(ILeg.Label.REQUEST).getUserMessageFlow().getBusinessInfo();
        if (busInfo == null)
            busInfo = new BusinessInfo(null);
        busInfo.setAction(smpInfo.getAction());
        busInfo.setService(smpInfo.getService());
        pmode.getLeg(ILeg.Label.REQUEST).getUserMessageFlow().setBusinnessInfo(busInfo);
        try
        {
            addCertToKeystore(partyId.getId(), smpInfo.getCertificate());
        } catch (Exception e) {
            log.error("Could not load certificate [SerialNo=" + smpInfo.getCertificate().getSerialNumber().toString() + "] in the keystore! Error:" + e
                    .getCause().getClass().getSimpleName() + " - " + e
                    .getCause().getMessage());
            return null;
        }

        EncryptionConfig encConfig = pmode.getResponder().getSecurityConfiguration().getEncryptionConfiguration();
        if (encConfig == null) {
            encConfig = new EncryptionConfig(null);
        }
        encConfig.setKeystoreAlias(partyId.getId());

        try
        {
            IPModeSet pmodeSet = HolodeckB2BCoreInterface.getPModeSet();
            if (pmodeSet.containsId(pmodeId.toString())) {
                pmodeSet.replace(pmode);
            } else {
                pmodeSet.add(pmode);
            }
            log.info("Updated P-Mode set with new configuration for P-Mode.id= " + pmode.getId());
        } catch (PModeSetException pmse) {
            log.error("Could not update the P-Mode set with new configuration for P-Mode.id= " + pmode.getId() + "! Error: " + pmse
                    .getMessage());
        }

        return pmode.getId();
    }

    private static IPartyId getPartyIdFromCertificate(X509Certificate certificate)
    {
        PartyId pid = new PartyId(null);
        try
        {
            pid.setId(X500Name.asX500Name(certificate.getSubjectX500Principal()).getCommonName());
        } catch (IOException ex) {
            log.error("Could not extract the CN from the certificate [SerialNo=" + certificate
                    .getSerialNumber().toString() + "]!" + " Error details: " + ex
                    .getMessage());
            pid = null;
        }

        return pid;
    }

    private synchronized void addCertToKeystore(String alias, X509Certificate certificate)
            throws ConfigurationException
    {
        try
        {
            File ksFile = new File(HolodeckB2BCoreInterface.getConfiguration().getPublicKeyStorePath());
            InputStream in = new FileInputStream(ksFile);
            KeyStore ks = KeyStore.getInstance("jks");
            ks.load(in, HolodeckB2BCoreInterface.getConfiguration().getPublicKeyStorePassword().toCharArray());
            in.close();

            ks.setCertificateEntry(alias, certificate);

            OutputStream out = new FileOutputStream(ksFile);
            ks.store(out, HolodeckB2BCoreInterface.getConfiguration().getPublicKeyStorePassword().toCharArray());
            out.close();
        } catch (KeyStoreException|IOException|NoSuchAlgorithmException|CertificateException ex) {
            throw new ConfigurationException("Could not load certificate in keystore", ex);
        }
    }
}




