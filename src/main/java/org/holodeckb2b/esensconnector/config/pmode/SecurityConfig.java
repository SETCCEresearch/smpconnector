/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.pmode.security.IEncryptionConfiguration;
/*    */ import org.holodeckb2b.interfaces.pmode.security.ISecurityConfiguration;
/*    */ import org.holodeckb2b.interfaces.pmode.security.ISecurityConfiguration.WSSHeaderTarget;
/*    */ import org.holodeckb2b.interfaces.pmode.security.ISigningConfiguration;
/*    */ import org.holodeckb2b.interfaces.pmode.security.IUsernameTokenConfiguration;
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
/*    */ public class SecurityConfig
/*    */   implements ISecurityConfiguration
/*    */ {
/*    */   private UsernameTokenConfig[] usernameTokens;
/*    */   private SigningConfig signatureConfig;
/*    */   private EncryptionConfig encryptionConfig;
/*    */   
/*    */   public SecurityConfig(ISecurityConfiguration secConfig)
/*    */   {
/* 37 */     this.usernameTokens = new UsernameTokenConfig[2];
/*    */     
/* 39 */     if (secConfig != null)
/*    */     {
/* 41 */       this.signatureConfig = (secConfig.getSignatureConfiguration() != null ? new SigningConfig(secConfig.getSignatureConfiguration()) : null);
/*    */       
/* 43 */       this.encryptionConfig = (secConfig.getEncryptionConfiguration() != null ? new EncryptionConfig(secConfig.getEncryptionConfiguration()) : null);
/* 44 */       this.usernameTokens[0] = (secConfig.getUsernameTokenConfiguration(ISecurityConfiguration.WSSHeaderTarget.DEFAULT) != null ? new UsernameTokenConfig(secConfig
/* 45 */         .getUsernameTokenConfiguration(ISecurityConfiguration.WSSHeaderTarget.DEFAULT)) : null);
/* 46 */       this.usernameTokens[1] = (secConfig.getUsernameTokenConfiguration(ISecurityConfiguration.WSSHeaderTarget.EBMS) != null ? new UsernameTokenConfig(secConfig
/* 47 */         .getUsernameTokenConfiguration(ISecurityConfiguration.WSSHeaderTarget.EBMS)) : null);
/*    */     }
/*    */   }
/*    */   
/*    */   public UsernameTokenConfig getUsernameTokenConfiguration(ISecurityConfiguration.WSSHeaderTarget target)
/*    */   {
/* 53 */     if (target == ISecurityConfiguration.WSSHeaderTarget.EBMS) {
/* 54 */       return this.usernameTokens[1];
/*    */     }
/* 56 */     return this.usernameTokens[0];
/*    */   }
/*    */   
/*    */   public void setUsernameTokenConfiguration(ISecurityConfiguration.WSSHeaderTarget target, IUsernameTokenConfiguration utConfig) {
/* 60 */     if (target == ISecurityConfiguration.WSSHeaderTarget.EBMS) {
/* 61 */       this.usernameTokens[1] = new UsernameTokenConfig(utConfig);
/*    */     } else {
/* 63 */       this.usernameTokens[0] = new UsernameTokenConfig(utConfig);
/*    */     }
/*    */   }
/*    */   
/*    */   public SigningConfig getSignatureConfiguration() {
/* 68 */     return this.signatureConfig;
/*    */   }
/*    */   
/*    */   public void setSignatureConfiguration(ISigningConfiguration signingConfig) {
/* 72 */     if (signingConfig != null) {
/* 73 */       this.signatureConfig = new SigningConfig(signingConfig);
/*    */     } else {
/* 75 */       this.signatureConfig = null;
/*    */     }
/*    */   }
/*    */   
/*    */   public EncryptionConfig getEncryptionConfiguration() {
/* 80 */     return this.encryptionConfig;
/*    */   }
/*    */   
/*    */   public void setEncryptionConfiguration(IEncryptionConfiguration encryptionConfig) {
/* 84 */     if (encryptionConfig != null) {
/* 85 */       this.encryptionConfig = new EncryptionConfig(encryptionConfig);
/*    */     } else {
/* 87 */       this.encryptionConfig = null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\SecurityConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */