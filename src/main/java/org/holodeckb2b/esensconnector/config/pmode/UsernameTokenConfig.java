/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.pmode.security.IUsernameTokenConfiguration;
/*    */ import org.holodeckb2b.interfaces.pmode.security.IUsernameTokenConfiguration.PasswordType;
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
/*    */ public class UsernameTokenConfig
/*    */   implements IUsernameTokenConfiguration
/*    */ {
/*    */   private String username;
/*    */   private String password;
/*    */   private IUsernameTokenConfiguration.PasswordType pwdType;
/*    */   private boolean includeNonce;
/*    */   private boolean includeCreated;
/*    */   
/*    */   public UsernameTokenConfig(IUsernameTokenConfiguration utConfig)
/*    */   {
/* 36 */     if (utConfig != null) {
/* 37 */       this.username = utConfig.getUsername();
/* 38 */       this.password = utConfig.getPassword();
/* 39 */       this.pwdType = utConfig.getPasswordType();
/* 40 */       this.includeNonce = utConfig.includeNonce();
/* 41 */       this.includeCreated = utConfig.includeCreated();
/*    */     }
/*    */   }
/*    */   
/*    */   public String getUsername()
/*    */   {
/* 47 */     return this.username;
/*    */   }
/*    */   
/*    */   public void setUsername(String username) {
/* 51 */     this.username = username;
/*    */   }
/*    */   
/*    */   public String getPassword()
/*    */   {
/* 56 */     return this.password;
/*    */   }
/*    */   
/*    */   public void setPassword(String password) {
/* 60 */     this.password = password;
/*    */   }
/*    */   
/*    */   public IUsernameTokenConfiguration.PasswordType getPasswordType()
/*    */   {
/* 65 */     return this.pwdType;
/*    */   }
/*    */   
/*    */   public void setPasswordType(IUsernameTokenConfiguration.PasswordType pwdType) {
/* 69 */     this.pwdType = pwdType;
/*    */   }
/*    */   
/*    */   public boolean includeNonce()
/*    */   {
/* 74 */     return this.includeNonce;
/*    */   }
/*    */   
/*    */   public void setIncludeNonce(boolean includeNonce) {
/* 78 */     this.includeNonce = includeNonce;
/*    */   }
/*    */   
/*    */   public boolean includeCreated()
/*    */   {
/* 83 */     return this.includeCreated;
/*    */   }
/*    */   
/*    */   public void setIncludeCreated(boolean includeCreated) {
/* 87 */     this.includeCreated = includeCreated;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\UsernameTokenConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */