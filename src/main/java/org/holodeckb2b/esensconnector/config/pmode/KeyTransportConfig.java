/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.pmode.security.IKeyTransport;
/*    */ import org.holodeckb2b.interfaces.pmode.security.X509ReferenceType;
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
/*    */ 
/*    */ public class KeyTransportConfig
/*    */   implements IKeyTransport
/*    */ {
/*    */   private X509ReferenceType keyRefMethod;
/*    */   private String encryptionAlgorithm;
/*    */   private String MGFAlgorithm;
/*    */   private String digestAlgorithm;
/*    */   
/*    */   public KeyTransportConfig(IKeyTransport keytransportCfg)
/*    */   {
/* 36 */     if (keytransportCfg != null) {
/* 37 */       this.keyRefMethod = keytransportCfg.getKeyReferenceMethod();
/* 38 */       this.encryptionAlgorithm = keytransportCfg.getAlgorithm();
/* 39 */       this.MGFAlgorithm = keytransportCfg.getMGFAlgorithm();
/* 40 */       this.digestAlgorithm = keytransportCfg.getDigestAlgorithm();
/*    */     }
/*    */   }
/*    */   
/*    */   public String getAlgorithm()
/*    */   {
/* 46 */     return this.encryptionAlgorithm;
/*    */   }
/*    */   
/*    */   public void setAlgorithm(String algorithm) {
/* 50 */     this.encryptionAlgorithm = algorithm;
/*    */   }
/*    */   
/*    */   public String getMGFAlgorithm()
/*    */   {
/* 55 */     return this.MGFAlgorithm;
/*    */   }
/*    */   
/*    */   public void setMGFAlgorithm(String algorithm) {
/* 59 */     this.MGFAlgorithm = algorithm;
/*    */   }
/*    */   
/*    */   public String getDigestAlgorithm()
/*    */   {
/* 64 */     return this.digestAlgorithm;
/*    */   }
/*    */   
/*    */   public void setDigestAlgorithm(String algorithm) {
/* 68 */     this.digestAlgorithm = algorithm;
/*    */   }
/*    */   
/*    */   public X509ReferenceType getKeyReferenceMethod()
/*    */   {
/* 73 */     return this.keyRefMethod;
/*    */   }
/*    */   
/*    */   public void setKeyReferenceMethod(X509ReferenceType refMethod) {
/* 77 */     this.keyRefMethod = refMethod;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\KeyTransportConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */