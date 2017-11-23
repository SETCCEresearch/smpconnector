/*     */ package org.holodeckb2b.esensconnector.config.pmode;
/*     */ 
/*     */ import org.holodeckb2b.interfaces.delivery.IDeliverySpecification;
/*     */ import org.holodeckb2b.interfaces.general.ReplyPattern;
/*     */ import org.holodeckb2b.interfaces.pmode.IErrorHandling;
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
/*     */ public class ErrorHandlingConfig
/*     */   implements IErrorHandling
/*     */ {
/*     */   private ReplyPattern pattern;
/*     */   private String errorsTo;
/*     */   private Boolean addSOAPFault;
/*     */   private Boolean reportOnError;
/*     */   private Boolean reportOnReceipt;
/*     */   private boolean notify;
/*     */   private DeliverySpecification errorDelivery;
/*     */   
/*     */   public ErrorHandlingConfig(IErrorHandling errorConfig)
/*     */   {
/*  39 */     if (errorConfig != null) {
/*  40 */       this.pattern = errorConfig.getPattern();
/*  41 */       this.errorsTo = errorConfig.getReceiverErrorsTo();
/*  42 */       this.addSOAPFault = errorConfig.shouldAddSOAPFault();
/*  43 */       this.reportOnError = errorConfig.shouldReportErrorOnError();
/*  44 */       this.reportOnReceipt = errorConfig.shouldReportErrorOnReceipt();
/*  45 */       this.notify = errorConfig.shouldNotifyErrorToBusinessApplication();
/*     */       
/*  47 */       this.errorDelivery = (errorConfig.getErrorDelivery() != null ? new DeliverySpecification(errorConfig.getErrorDelivery()) : null);
/*     */     }
/*     */   }
/*     */   
/*     */   public ReplyPattern getPattern()
/*     */   {
/*  53 */     return this.pattern;
/*     */   }
/*     */   
/*     */   public void setPattern(ReplyPattern pattern) {
/*  57 */     this.pattern = pattern;
/*     */   }
/*     */   
/*     */   public String getReceiverErrorsTo()
/*     */   {
/*  62 */     return this.errorsTo;
/*     */   }
/*     */   
/*     */   public void setReceiverErrorsTo(String to) {
/*  66 */     this.errorsTo = to;
/*     */   }
/*     */   
/*     */   public Boolean shouldAddSOAPFault()
/*     */   {
/*  71 */     return this.addSOAPFault;
/*     */   }
/*     */   
/*     */   public void setAddSOAPFault(Boolean shouldAddSOAPFault) {
/*  75 */     this.addSOAPFault = shouldAddSOAPFault;
/*     */   }
/*     */   
/*     */   public Boolean shouldReportErrorOnError()
/*     */   {
/*  80 */     return this.reportOnError;
/*     */   }
/*     */   
/*     */   public void setReportErrorOnError(Boolean reportOnError) {
/*  84 */     this.reportOnError = reportOnError;
/*     */   }
/*     */   
/*     */   public Boolean shouldReportErrorOnReceipt()
/*     */   {
/*  89 */     return this.reportOnReceipt;
/*     */   }
/*     */   
/*     */   public void setReportErrorOnReceipt(Boolean reportOnReceipt) {
/*  93 */     this.reportOnReceipt = reportOnReceipt;
/*     */   }
/*     */   
/*     */   public boolean shouldNotifyErrorToBusinessApplication()
/*     */   {
/*  98 */     return this.notify;
/*     */   }
/*     */   
/*     */   public void setNotifyErrorToBusinessApplication(boolean notify) {
/* 102 */     this.notify = notify;
/*     */   }
/*     */   
/*     */   public IDeliverySpecification getErrorDelivery()
/*     */   {
/* 107 */     return this.errorDelivery;
/*     */   }
/*     */   
/*     */   public void setErrorDelivery(IDeliverySpecification deliverySpec) {
/* 111 */     this.errorDelivery = (deliverySpec != null ? new DeliverySpecification(deliverySpec) : null);
/*     */   }
/*     */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\ErrorHandlingConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */