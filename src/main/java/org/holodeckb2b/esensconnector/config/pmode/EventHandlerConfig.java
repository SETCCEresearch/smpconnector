/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.holodeckb2b.common.util.Utils;
/*    */ import org.holodeckb2b.interfaces.events.IMessageProcessingEvent;
/*    */ import org.holodeckb2b.interfaces.events.IMessageProcessingEventConfiguration;
/*    */ import org.holodeckb2b.interfaces.messagemodel.IMessageUnit;
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
/*    */ public class EventHandlerConfig
/*    */   implements IMessageProcessingEventConfiguration
/*    */ {
/*    */   private String id;
/*    */   private List<Class<? extends IMessageProcessingEvent>> handledEvents;
/*    */   private List<Class<? extends IMessageUnit>> forMessageUnits;
/*    */   private String factoryClass;
/*    */   private Map<String, ?> settings;
/*    */   
/*    */   public EventHandlerConfig(IMessageProcessingEventConfiguration eventConfig)
/*    */   {
/* 34 */     this.id = eventConfig.getId();
/* 35 */     setHandledEvents(eventConfig.getHandledEvents());
/* 36 */     setAppliesTo(eventConfig.appliesTo());
/* 37 */     this.factoryClass = eventConfig.getFactoryClass();
/* 38 */     setHandlerSettings(eventConfig.getHandlerSettings());
/*    */   }
/*    */   
/*    */   public String getId()
/*    */   {
/* 43 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String newId) {
/* 47 */     this.id = newId;
/*    */   }
/*    */   
/*    */   public List<Class<? extends IMessageProcessingEvent>> getHandledEvents()
/*    */   {
/* 52 */     return this.handledEvents;
/*    */   }
/*    */   
/*    */   public void setHandledEvents(List<Class<? extends IMessageProcessingEvent>> newHandledEvents) {
/* 56 */     if (!Utils.isNullOrEmpty(this.handledEvents)) {
/* 57 */       this.handledEvents = new ArrayList(this.handledEvents);
/*    */     } else {
/* 59 */       this.handledEvents = null;
/*    */     }
/*    */   }
/*    */   
/*    */   public List<Class<? extends IMessageUnit>> appliesTo() {
/* 64 */     return this.forMessageUnits;
/*    */   }
/*    */   
/*    */   public void setAppliesTo(List<Class<? extends IMessageUnit>> newAppliesTo) {
/* 68 */     if (!Utils.isNullOrEmpty(newAppliesTo)) {
/* 69 */       this.forMessageUnits = new ArrayList(newAppliesTo);
/*    */     } else {
/* 71 */       this.forMessageUnits = null;
/*    */     }
/*    */   }
/*    */   
/*    */   public String getFactoryClass() {
/* 76 */     return this.factoryClass;
/*    */   }
/*    */   
/*    */   public void setFactoryClass(String newFactoryClass) {
/* 80 */     this.factoryClass = newFactoryClass;
/*    */   }
/*    */   
/*    */   public Map<String, ?> getHandlerSettings()
/*    */   {
/* 85 */     return this.settings;
/*    */   }
/*    */   
/*    */   public void setHandlerSettings(Map<String, ?> newSettings) {
/* 89 */     if (!Utils.isNullOrEmpty(newSettings)) {
/* 90 */       this.settings = new HashMap(newSettings);
/*    */     } else {
/* 92 */       this.settings = null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\EventHandlerConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */