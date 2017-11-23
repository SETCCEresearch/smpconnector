/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.pmode.security.IEncryptionConfiguration;
/*    */ import org.holodeckb2b.interfaces.pmode.security.IKeyTransport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EncryptionConfig
/*    */   implements IEncryptionConfiguration
/*    */ {
/*    */   private String keyAlias;
/*    */   private String keyPassword;
/*    */   private String encryptionAlgorithm;
/*    */   private KeyTransportConfig keytransportCfg;
/*    */   
/*    */   public EncryptionConfig(IEncryptionConfiguration encryptionCfg)
/*    */   {
/* 35 */     if (encryptionCfg != null) {
/* 36 */       this.keyAlias = encryptionCfg.getKeystoreAlias();
/* 37 */       this.keyPassword = encryptionCfg.getCertificatePassword();
/* 38 */       this.encryptionAlgorithm = encryptionCfg.getAlgorithm();
/* 39 */       this.keytransportCfg = new KeyTransportConfig(encryptionCfg.getKeyTransport());
/*    */     }
/*    */   }
/*    */   
/*    */   public String getKeystoreAlias()
/*    */   {
/* 45 */     return this.keyAlias;
/*    */   }
/*    */   
/*    */   public void setKeystoreAlias(String alias) {
/* 49 */     this.keyAlias = alias;
/*    */   }
/*    */   
/*    */   public String getCertificatePassword()
/*    */   {
/* 54 */     return this.keyPassword;
/*    */   }
/*    */   
/*    */   public void setCertificatePassword(String password) {
/* 58 */     this.keyPassword = password;
/*    */   }
/*    */   
/*    */   public String getAlgorithm()
/*    */   {
/* 63 */     return this.encryptionAlgorithm;
/*    */   }
/*    */   
/*    */   public void setAlgorithm(String algorithm) {
/* 67 */     this.encryptionAlgorithm = algorithm;
/*    */   }
/*    */   
/*    */   public KeyTransportConfig getKeyTransport()
/*    */   {
/* 72 */     return this.keytransportCfg;
/*    */   }
/*    */   
/*    */   public void setKeyTransport(IKeyTransport keytransport) {
/* 76 */     if (keytransport != null) {
/* 77 */       this.keytransportCfg = new KeyTransportConfig(keytransport);
/*    */     } else {
/* 79 */       this.keytransportCfg = null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\EncryptionConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */