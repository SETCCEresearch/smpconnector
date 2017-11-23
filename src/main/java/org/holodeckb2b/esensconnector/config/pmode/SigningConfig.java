/*     */ package org.holodeckb2b.esensconnector.config.pmode;
/*     */ 
/*     */ import org.holodeckb2b.interfaces.pmode.security.ISigningConfiguration;
/*     */ import org.holodeckb2b.interfaces.pmode.security.X509ReferenceType;
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
/*     */ public class SigningConfig
/*     */   implements ISigningConfiguration
/*     */ {
/*     */   private String keyAlias;
/*     */   private String keyPassword;
/*     */   private X509ReferenceType keyRefMethod;
/*     */   private Boolean includeCertPath;
/*     */   private Boolean enableRevocation;
/*     */   private String signAlgorithm;
/*     */   private String hashAlgorithm;
/*     */   
/*     */   public SigningConfig(ISigningConfiguration signingConfig)
/*     */   {
/*  38 */     if (signingConfig != null) {
/*  39 */       this.keyAlias = signingConfig.getKeystoreAlias();
/*  40 */       this.keyRefMethod = signingConfig.getKeyReferenceMethod();
/*  41 */       this.keyPassword = signingConfig.getCertificatePassword();
/*  42 */       this.includeCertPath = signingConfig.includeCertificatePath();
/*  43 */       this.enableRevocation = signingConfig.enableRevocationCheck();
/*  44 */       this.signAlgorithm = signingConfig.getSignatureAlgorithm();
/*  45 */       this.hashAlgorithm = signingConfig.getHashFunction();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getKeystoreAlias()
/*     */   {
/*  51 */     return this.keyAlias;
/*     */   }
/*     */   
/*     */   public void setKeystoreAlias(String alias) {
/*  55 */     this.keyAlias = alias;
/*     */   }
/*     */   
/*     */   public String getCertificatePassword()
/*     */   {
/*  60 */     return this.keyPassword;
/*     */   }
/*     */   
/*     */   public void setCertificatePassword(String password) {
/*  64 */     this.keyPassword = password;
/*     */   }
/*     */   
/*     */   public X509ReferenceType getKeyReferenceMethod()
/*     */   {
/*  69 */     return this.keyRefMethod;
/*     */   }
/*     */   
/*     */   public void setKeyReferenceMethod(X509ReferenceType refMethod) {
/*  73 */     this.keyRefMethod = refMethod;
/*     */   }
/*     */   
/*     */   public Boolean includeCertificatePath()
/*     */   {
/*  78 */     return this.includeCertPath;
/*     */   }
/*     */   
/*     */   public void setIncludeCertPath(Boolean includePath) {
/*  82 */     this.includeCertPath = includePath;
/*     */   }
/*     */   
/*     */   public Boolean enableRevocationCheck()
/*     */   {
/*  87 */     return this.enableRevocation;
/*     */   }
/*     */   
/*     */   public void setRevocationCheck(Boolean enableRevocation) {
/*  91 */     this.enableRevocation = enableRevocation;
/*     */   }
/*     */   
/*     */   public String getSignatureAlgorithm()
/*     */   {
/*  96 */     return this.signAlgorithm;
/*     */   }
/*     */   
/*     */   public void setSignatureAlgorithm(String algorithm) {
/* 100 */     this.signAlgorithm = algorithm;
/*     */   }
/*     */   
/*     */   public String getHashFunction()
/*     */   {
/* 105 */     return this.hashAlgorithm;
/*     */   }
/*     */   
/*     */   public void setHashFunction(String algorithm) {
/* 109 */     this.hashAlgorithm = algorithm;
/*     */   }
/*     */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\SigningConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */