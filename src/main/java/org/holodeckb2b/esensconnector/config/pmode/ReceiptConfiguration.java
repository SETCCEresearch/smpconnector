/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.delivery.IDeliverySpecification;
/*    */ import org.holodeckb2b.interfaces.general.ReplyPattern;
/*    */ import org.holodeckb2b.interfaces.pmode.IReceiptConfiguration;
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
/*    */ public class ReceiptConfiguration
/*    */   implements IReceiptConfiguration
/*    */ {
/*    */   private ReplyPattern pattern;
/*    */   private String replyTo;
/*    */   private boolean notify;
/*    */   private DeliverySpecification rcptDeliverySpec;
/*    */   
/*    */   public ReceiptConfiguration(IReceiptConfiguration rcptConfig)
/*    */   {
/* 36 */     if (rcptConfig != null) {
/* 37 */       this.pattern = rcptConfig.getPattern();
/* 38 */       this.replyTo = rcptConfig.getTo();
/* 39 */       this.notify = rcptConfig.shouldNotifyReceiptToBusinessApplication();
/*    */       
/* 41 */       this.rcptDeliverySpec = (rcptConfig.getReceiptDelivery() != null ? new DeliverySpecification(rcptConfig.getReceiptDelivery()) : null);
/*    */     }
/*    */   }
/*    */   
/*    */   public ReplyPattern getPattern()
/*    */   {
/* 47 */     return this.pattern;
/*    */   }
/*    */   
/*    */   public void setPattern(ReplyPattern pattern) {
/* 51 */     this.pattern = pattern;
/*    */   }
/*    */   
/*    */   public String getTo()
/*    */   {
/* 56 */     return this.replyTo;
/*    */   }
/*    */   
/*    */   public void setTo(String replyTo) {
/* 60 */     this.replyTo = replyTo;
/*    */   }
/*    */   
/*    */   public boolean shouldNotifyReceiptToBusinessApplication()
/*    */   {
/* 65 */     return this.notify;
/*    */   }
/*    */   
/*    */   public void setNotifyReceiptToBusinessApplication(boolean notify) {
/* 69 */     this.notify = notify;
/*    */   }
/*    */   
/*    */   public IDeliverySpecification getReceiptDelivery()
/*    */   {
/* 74 */     return this.rcptDeliverySpec;
/*    */   }
/*    */   
/*    */   public void setReceiptDelivery(IDeliverySpecification deliverySpec) {
/* 78 */     this.rcptDeliverySpec = (deliverySpec != null ? new DeliverySpecification(deliverySpec) : null);
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\ReceiptConfiguration.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */