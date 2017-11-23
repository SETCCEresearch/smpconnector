/*     */ package org.holodeckb2b.esensconnector.etsi.rem;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.security.DigestOutputStream;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStore.PasswordProtection;
/*     */ import java.security.KeyStore.PrivateKeyEntry;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.UnrecoverableEntryException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.Date;
/*     */ import no.difi.vefa.peppol.common.model.DocumentTypeIdentifier;
/*     */ import no.difi.vefa.peppol.common.model.InstanceIdentifier;
/*     */ import no.difi.vefa.peppol.common.model.ParticipantIdentifier;
/*     */ import no.difi.vefa.peppol.common.model.Scheme;
/*     */ import no.difi.vefa.peppol.evidence.rem.EventCode;
/*     */ import no.difi.vefa.peppol.evidence.rem.RemEvidenceBuilder;
/*     */ import no.difi.vefa.peppol.evidence.rem.RemEvidenceService;
/*     */ import no.difi.vefa.peppol.evidence.rem.RemEvidenceTransformer;
/*     */ import no.difi.vefa.peppol.evidence.rem.SignedRemEvidence;
/*     */ import no.difi.vefa.sbdh.AsicExtractorFactory;
/*     */ import no.difi.vefa.sbdh.api.AsicExtractor;
/*     */ import org.holodeckb2b.common.util.Utils;
/*     */ import org.holodeckb2b.esensconnector.persistency.SimpleSBDHData;
/*     */ import org.holodeckb2b.interfaces.config.IConfiguration;
/*     */ import org.holodeckb2b.interfaces.core.HolodeckB2BCoreInterface;
/*     */ import org.holodeckb2b.interfaces.pmode.IPMode;
/*     */ import org.holodeckb2b.interfaces.pmode.IPModeSet;
/*     */ import org.holodeckb2b.interfaces.pmode.ITradingPartnerConfiguration;
/*     */ import org.holodeckb2b.interfaces.pmode.security.ISecurityConfiguration;
/*     */ import org.holodeckb2b.interfaces.pmode.security.ISigningConfiguration;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EvidenceHelper
/*     */ {
/*  51 */   private static Logger log = LoggerFactory.getLogger(EvidenceHelper.class);
/*     */   public static final String ACCEPT_EV_POLICY_ID = "http://esens.holodeck-b2b.com/signals";
/*     */   public static final String DELIVERY_EV_POLICY_ID = "http://nothing.here.right.now";
/*     */   private static final String AP_ISSUER_DETAILS = "TenderNed-pilot-AP";
/*     */   
/*  56 */   public static enum EvidenceType { DeliveryNonDelivery,  AcceptanceNonAcceptance,  SubmissionAcceptanceRejection;
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
/*     */     private EvidenceType() {}
/*     */   }
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
/*     */   public static SignedRemEvidence createEvidence(EvidenceType type, boolean success, Date eventTime, SimpleSBDHData sbdMetadata)
/*     */     throws EvidenceCreationFailed
/*     */   {
/*  97 */     if (type == null)
/*  98 */       throw new EvidenceCreationFailed("Evidence type must be specified!");
/*  99 */     if (sbdMetadata == null) {
/* 100 */       throw new EvidenceCreationFailed("The SBDH meta-data must be specified!");
/*     */     }
/* 102 */     log.debug("Create REM evidence builder");
/* 103 */     RemEvidenceService remEvidenceService = new RemEvidenceService();
/*     */     
/*     */ 
/* 106 */     RemEvidenceBuilder builder = type == EvidenceType.DeliveryNonDelivery ? remEvidenceService.createDeliveryNonDeliveryToRecipientBuilder() : remEvidenceService.createRelayRemMdAcceptanceRejectionBuilder();
/*     */     try
/*     */     {
/* 109 */       log.debug("Set fields of the evindence");
/* 110 */       builder.eventCode(!success ? EventCode.REJECTION : type == EvidenceType.DeliveryNonDelivery ? EventCode.DELIVERY : !success ? EventCode.DELIVERY_EXPIRATION : EventCode.ACCEPTANCE)
/*     */       
/*     */ 
/* 113 */         .eventTime(eventTime != null ? eventTime : new Date())
/* 114 */         .evidenceIssuerDetails("TenderNed-pilot-AP")
/* 115 */         .evidenceIssuerPolicyID(type == EvidenceType.DeliveryNonDelivery ? "http://nothing.here.right.now" : "http://esens.holodeck-b2b.com/signals")
/*     */         
/* 117 */         .senderIdentifier(new ParticipantIdentifier(sbdMetadata.getSenderId(), new Scheme(sbdMetadata
/* 118 */         .getSenderIdScheme())))
/* 119 */         .recipientIdentifer(new ParticipantIdentifier(sbdMetadata.getReceiverId(), new Scheme(sbdMetadata
/* 120 */         .getReceiverIdScheme())))
/* 121 */         .documentTypeId(new DocumentTypeIdentifier(sbdMetadata.getDocumentId()))
/* 122 */         .documentTypeInstanceIdentifier(sbdMetadata.getDocumentInstanceIdentifier())
/* 123 */         .instanceIdentifier(new InstanceIdentifier(sbdMetadata.getInstanceId()))
/* 124 */         .payloadDigest(sbdMetadata.getDigest());
/*     */     } catch (Exception e) {
/* 126 */       log.error("Could not build to REM evidence! Error details: {}", e.getMessage());
/* 127 */       throw new EvidenceCreationFailed("Could not build REM evidence!", e);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 132 */     log.debug("Getting private key to sign evidence");
/* 133 */     KeyStore.PrivateKeyEntry privKey = getAccessPointPrivateKey();
/* 134 */     if (privKey == null)
/*     */     {
/* 136 */       log.error("Can not create signed REM evidence because private key is missing!");
/* 137 */       throw new EvidenceCreationFailed("No private key available for signing the REM evidence!");
/*     */     }
/*     */     try
/*     */     {
/* 141 */       log.debug("Sign the created REM evidence for this signal");
/* 142 */       return builder.buildRemEvidenceInstance(privKey);
/*     */     }
/*     */     catch (Exception e) {
/* 145 */       log.error("Signing the REM evidence failed! Error details: {}", e.getMessage());
/* 146 */       throw new EvidenceCreationFailed("Signing the REM evidence failed!", e);
/*     */     }
/*     */   }
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
/*     */   public static String saveEvidenceToFile(SignedRemEvidence evidence, String directory, String prefix)
/*     */     throws IOException
/*     */   {
/* 164 */     String evidenceId = evidence.getEvidenceIdentifier();
/* 165 */     log.debug("Saving {} REM evidence [id={}] to {}", new Object[] { evidence.getEvidenceType(), evidenceId, directory });
/*     */     
/*     */ 
/* 168 */     String evidenceFilePath = null;
/*     */     try {
/* 170 */       RemEvidenceTransformer remTransformer = new RemEvidenceTransformer();
/*     */       
/*     */ 
/*     */ 
/* 174 */       evidenceFilePath = Utils.createFileWithUniqueName(directory + (prefix != null ? prefix : "") + evidenceId.replaceAll("[^a-zA-Z0-9.-]", "_") + ".xml").toString();
/* 175 */       remTransformer.toFormattedXml(evidence, new FileOutputStream(evidenceFilePath));
/* 176 */       log.debug("Saved {} REM evidence [id={}] to {}", new Object[] { evidence.getEvidenceType(), evidenceId, evidenceFilePath });
/*     */       
/* 178 */       return evidenceFilePath;
/*     */     } catch (Exception ex) {
/* 180 */       log.error("Could not save {} REM evidence [id={}] to {}.\ntError details: {}", new Object[] { evidence.getEvidenceType(), evidenceId, directory, ex
/* 181 */         .getMessage() });
/*     */       try {
/* 183 */         if (evidenceFilePath != null)
/* 184 */           Files.deleteIfExists(Paths.get(evidenceFilePath, new String[0]));
/*     */       } catch (IOException io) {
/* 186 */         log.error("Could not delete temporarily file {}. Please remove manually", evidenceFilePath);
/*     */       }
/* 188 */       throw new IOException("Could not write evidence to directory", ex);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] calculateASICDigest(String sdbLocation)
/*     */     throws NoSuchAlgorithmException, IOException
/*     */   {
/* 201 */     AsicExtractor asicReader = AsicExtractorFactory.defaultAsicExtractor();
/* 202 */     FileInputStream sdbInputStream = new FileInputStream(sdbLocation);
/*     */     
/* 204 */     log.debug("Get SHA-256 MessageDigest object");
/* 205 */     MessageDigest digest = MessageDigest.getInstance("SHA-256");
/* 206 */     log.debug("Create digester outputstream with SHA-256 digest");
/* 207 */     DigestOutputStream digestStream = new DigestOutputStream(new OutputStream() { public void write(int b) throws IOException
/* 207 */       {} }, digest);
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 212 */       log.debug("Read ASiC and calculate the digest");
/* 213 */       asicReader.extractAsic(sdbInputStream, digestStream);
/* 214 */       log.debug("Return Base64 encode the digest");
/* 215 */       return digest.digest();
/*     */     } finally {
/* 217 */       sdbInputStream.close();
/* 218 */       digestStream.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static KeyStore.PrivateKeyEntry getAccessPointPrivateKey()
/*     */   {
/*     */     try
/*     */     {
/* 232 */       log.debug("Getting the private key to sign the REM evidence");
/* 233 */       IPMode pmode = HolodeckB2BCoreInterface.getPModeSet().get("hb2b-esens-receiving");
/*     */       
/* 235 */       ISigningConfiguration encCfg = pmode.getResponder().getSecurityConfiguration().getSignatureConfiguration();
/* 236 */       String keyAlias = encCfg.getKeystoreAlias();
/* 237 */       String keyPwd = encCfg.getCertificatePassword();
/*     */       
/* 239 */       KeyStore keyStore = KeyStore.getInstance("JKS");
/* 240 */       File ksFile = new File(HolodeckB2BCoreInterface.getConfiguration().getPrivateKeyStorePath());
/*     */       
/* 242 */       log.debug("Loading the keystore with the private keys in {}", ksFile.getAbsolutePath());
/* 243 */       InputStream in = new FileInputStream(ksFile);
/* 244 */       keyStore.load(in, HolodeckB2BCoreInterface.getConfiguration().getPrivateKeyStorePassword().toCharArray());
/*     */       
/* 246 */       log.debug("Getting private for alias {} from keystore", keyAlias);
/* 247 */       return (KeyStore.PrivateKeyEntry)keyStore.getEntry(keyAlias, new KeyStore.PasswordProtection(keyPwd
/* 248 */         .toCharArray()));
/*     */     }
/*     */     catch (NoSuchAlgorithmException|UnrecoverableEntryException|KeyStoreException|IOException|CertificateException|NullPointerException ex)
/*     */     {
/* 252 */       log.error("Could not get the private key to sign evidence from P-Mode [{}]!", "hb2b-esens-receiving");
/*     */     }
/* 254 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\etsi\rem\EvidenceHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */