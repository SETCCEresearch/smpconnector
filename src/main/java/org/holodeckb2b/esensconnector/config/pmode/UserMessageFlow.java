/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.pmode.IBusinessInfo;
/*    */ import org.holodeckb2b.interfaces.pmode.IErrorHandling;
/*    */ import org.holodeckb2b.interfaces.pmode.IPayloadProfile;
/*    */ import org.holodeckb2b.interfaces.pmode.IUserMessageFlow;
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
/*    */ public class UserMessageFlow
/*    */   implements IUserMessageFlow
/*    */ {
/*    */   private BusinessInfo businessInfo;
/*    */   private PayloadProfile payloadProfile;
/*    */   private ErrorHandlingConfig errorHandlingCfg;
/*    */   
/*    */   public UserMessageFlow(IUserMessageFlow usrMsgFlow)
/*    */   {
/* 37 */     if (usrMsgFlow != null)
/*    */     {
/* 39 */       this.businessInfo = (usrMsgFlow.getBusinessInfo() != null ? new BusinessInfo(usrMsgFlow.getBusinessInfo()) : null);
/* 40 */       this.payloadProfile = new PayloadProfile(usrMsgFlow.getPayloadProfile());
/* 41 */       this.errorHandlingCfg = new ErrorHandlingConfig(usrMsgFlow.getErrorHandlingConfiguration());
/*    */     }
/*    */   }
/*    */   
/*    */   public IBusinessInfo getBusinessInfo()
/*    */   {
/* 47 */     return this.businessInfo;
/*    */   }
/*    */   
/*    */   public void setBusinnessInfo(IBusinessInfo busInfo) {
/* 51 */     this.businessInfo = (busInfo != null ? new BusinessInfo(busInfo) : null);
/*    */   }
/*    */   
/*    */   public IPayloadProfile getPayloadProfile()
/*    */   {
/* 56 */     return this.payloadProfile;
/*    */   }
/*    */   
/*    */   public void setPayloadProfile(IPayloadProfile payloadProfile) {
/* 60 */     this.payloadProfile = (payloadProfile != null ? new PayloadProfile(payloadProfile) : null);
/*    */   }
/*    */   
/*    */   public IErrorHandling getErrorHandlingConfiguration()
/*    */   {
/* 65 */     return this.errorHandlingCfg;
/*    */   }
/*    */   
/*    */   public void setErrorHandlingConfiguration(IErrorHandling errorHandlingConfig) {
/* 69 */     this.errorHandlingCfg = new ErrorHandlingConfig(errorHandlingConfig);
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\UserMessageFlow.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */