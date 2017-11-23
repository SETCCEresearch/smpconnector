/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.pmode.IProtocol;
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
/*    */ public class Protocol
/*    */   implements IProtocol
/*    */ {
/*    */   private String address;
/*    */   private String soapVersion;
/*    */   private boolean useChunking;
/*    */   private boolean useHTTPCompression;
/*    */   private boolean addActorAttribute;
/*    */   
/*    */   public Protocol(IProtocol procotolConfig)
/*    */   {
/* 35 */     if (procotolConfig != null) {
/* 36 */       this.address = procotolConfig.getAddress();
/* 37 */       this.soapVersion = procotolConfig.getSOAPVersion();
/* 38 */       this.useChunking = procotolConfig.useChunking();
/* 39 */       this.useHTTPCompression = procotolConfig.useHTTPCompression();
/* 40 */       this.addActorAttribute = procotolConfig.shouldAddActorOrRoleAttribute();
/*    */     }
/*    */   }
/*    */   
/*    */   public String getAddress()
/*    */   {
/* 46 */     return this.address;
/*    */   }
/*    */   
/*    */   public void setAddress(String address) {
/* 50 */     this.address = address;
/*    */   }
/*    */   
/*    */   public String getSOAPVersion()
/*    */   {
/* 55 */     return this.soapVersion;
/*    */   }
/*    */   
/*    */   public void setSOAPVersion(String soapVersion) {
/* 59 */     this.soapVersion = soapVersion;
/*    */   }
/*    */   
/*    */   public boolean useChunking()
/*    */   {
/* 64 */     return this.useChunking;
/*    */   }
/*    */   
/*    */   public void setChunking(boolean useChunking) {
/* 68 */     this.useChunking = useChunking;
/*    */   }
/*    */   
/*    */   public boolean useHTTPCompression()
/*    */   {
/* 73 */     return this.useHTTPCompression;
/*    */   }
/*    */   
/*    */   public void setHTTPCompression(boolean useHTTPCompression) {
/* 77 */     this.useHTTPCompression = useHTTPCompression;
/*    */   }
/*    */   
/*    */   public boolean shouldAddActorOrRoleAttribute()
/*    */   {
/* 82 */     return this.addActorAttribute;
/*    */   }
/*    */   
/*    */   public void setAddActorOrRoleAttribute(boolean shouldAddActorAttribute) {
/* 86 */     this.addActorAttribute = shouldAddActorAttribute;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\Protocol.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */