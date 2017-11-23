/*     */ package org.holodeckb2b.esensconnector.config.pmode;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.holodeckb2b.common.util.Utils;
/*     */ import org.holodeckb2b.interfaces.as4.pmode.IAS4Leg;
/*     */ import org.holodeckb2b.interfaces.as4.pmode.IReceptionAwareness;
/*     */ import org.holodeckb2b.interfaces.delivery.IDeliverySpecification;
/*     */ import org.holodeckb2b.interfaces.events.IMessageProcessingEventConfiguration;
/*     */ import org.holodeckb2b.interfaces.pmode.ILeg;
/*     */ import org.holodeckb2b.interfaces.pmode.ILeg.Label;
/*     */ import org.holodeckb2b.interfaces.pmode.IProtocol;
/*     */ import org.holodeckb2b.interfaces.pmode.IPullRequestFlow;
/*     */ import org.holodeckb2b.interfaces.pmode.IReceiptConfiguration;
/*     */ import org.holodeckb2b.interfaces.pmode.IUserMessageFlow;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Leg
/*     */   implements IAS4Leg
/*     */ {
/*     */   private ILeg.Label label;
/*     */   private Protocol protocolConfig;
/*     */   private ReceiptConfiguration receiptConfig;
/*     */   private ReceptionAwarenessConfig raConfig;
/*     */   private DeliverySpecification defDeliverySpec;
/*     */   private List<IMessageProcessingEventConfiguration> eventConfigurations;
/*     */   private UserMessageFlow userMessageConfig;
/*     */   
/*     */   public Leg(ILeg leg)
/*     */   {
/*  54 */     if (leg != null) {
/*  55 */       this.label = leg.getLabel();
/*  56 */       this.protocolConfig = new Protocol(leg.getProtocol());
/*     */       
/*     */ 
/*  59 */       this.receiptConfig = (leg.getReceiptConfiguration() != null ? new ReceiptConfiguration(leg.getReceiptConfiguration()) : null);
/*  60 */       if (((leg instanceof IAS4Leg)) && (((IAS4Leg)leg).getReceptionAwareness() != null)) {
/*  61 */         this.raConfig = new ReceptionAwarenessConfig(((IAS4Leg)leg).getReceptionAwareness());
/*     */       }
/*  63 */       this.defDeliverySpec = (leg.getDefaultDelivery() != null ? new DeliverySpecification(leg.getDefaultDelivery()) : null);
/*     */       
/*  65 */       this.userMessageConfig = (leg.getUserMessageFlow() != null ? new UserMessageFlow(leg.getUserMessageFlow()) : null);
/*  66 */       setMessageProcessingEventConfiguration(leg.getMessageProcessingEventConfiguration());
/*     */     }
/*     */   }
/*     */   
/*     */   public ILeg.Label getLabel()
/*     */   {
/*  72 */     return this.label;
/*     */   }
/*     */   
/*     */   public void setLabel(ILeg.Label label) {
/*  76 */     this.label = label;
/*     */   }
/*     */   
/*     */   public Protocol getProtocol()
/*     */   {
/*  81 */     return this.protocolConfig;
/*     */   }
/*     */   
/*     */   public void setProtocol(IProtocol protocolConfig) {
/*  85 */     this.protocolConfig = new Protocol(protocolConfig);
/*     */   }
/*     */   
/*     */   public ReceiptConfiguration getReceiptConfiguration()
/*     */   {
/*  90 */     return this.receiptConfig;
/*     */   }
/*     */   
/*     */   public void setReceiptConfiguration(IReceiptConfiguration rcptConfig) {
/*  94 */     this.receiptConfig = (rcptConfig != null ? new ReceiptConfiguration(rcptConfig) : null);
/*     */   }
/*     */   
/*     */   public ReceptionAwarenessConfig getReceptionAwareness()
/*     */   {
/*  99 */     return this.raConfig;
/*     */   }
/*     */   
/*     */   public void setReceptionAwareness(IReceptionAwareness raConfig) {
/* 103 */     this.raConfig = (raConfig != null ? new ReceptionAwarenessConfig(raConfig) : null);
/*     */   }
/*     */   
/*     */   public DeliverySpecification getDefaultDelivery()
/*     */   {
/* 108 */     return this.defDeliverySpec;
/*     */   }
/*     */   
/*     */   public void setDefaultDelivery(IDeliverySpecification deliverySpec) {
/* 112 */     this.defDeliverySpec = (deliverySpec != null ? new DeliverySpecification(deliverySpec) : null);
/*     */   }
/*     */   
/*     */   public Collection<IPullRequestFlow> getPullRequestFlows()
/*     */   {
/* 117 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   
/*     */   public UserMessageFlow getUserMessageFlow()
/*     */   {
/* 122 */     return this.userMessageConfig;
/*     */   }
/*     */   
/*     */   public void setUserMessageFlow(IUserMessageFlow usrMsgFlow) {
/* 126 */     this.userMessageConfig = (usrMsgFlow != null ? new UserMessageFlow(usrMsgFlow) : null);
/*     */   }
/*     */   
/*     */   public List<IMessageProcessingEventConfiguration> getMessageProcessingEventConfiguration()
/*     */   {
/* 131 */     return this.eventConfigurations;
/*     */   }
/*     */   
/*     */   public void setMessageProcessingEventConfiguration(List<IMessageProcessingEventConfiguration> eventConfig) {
/* 135 */     if (!Utils.isNullOrEmpty(eventConfig)) {
/* 136 */       this.eventConfigurations = new ArrayList(eventConfig);
/*     */     } else {
/* 138 */       this.eventConfigurations = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\Leg.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */