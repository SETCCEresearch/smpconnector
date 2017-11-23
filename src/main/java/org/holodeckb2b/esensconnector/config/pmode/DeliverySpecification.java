/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.holodeckb2b.interfaces.delivery.IDeliverySpecification;
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
/*    */ public class DeliverySpecification
/*    */   implements IDeliverySpecification
/*    */ {
/*    */   private String id;
/*    */   private String factory;
/*    */   private Map<String, Object> parameters;
/*    */   
/*    */   public DeliverySpecification(IDeliverySpecification deliverySpec)
/*    */   {
/* 36 */     if (deliverySpec != null) {
/* 37 */       this.id = deliverySpec.getId();
/* 38 */       this.factory = deliverySpec.getFactory();
/* 39 */       setSettings(deliverySpec.getSettings());
/*    */     }
/*    */   }
/*    */   
/*    */   public String getId()
/*    */   {
/* 45 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 49 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getFactory()
/*    */   {
/* 54 */     return this.factory;
/*    */   }
/*    */   
/*    */   public void setFactory(String factory) {
/* 58 */     this.factory = factory;
/*    */   }
/*    */   
/*    */   public Map<String, ?> getSettings()
/*    */   {
/* 63 */     return this.parameters;
/*    */   }
/*    */   
/*    */   public void setSettings(Map<String, ?> parameters) {
/* 67 */     if (parameters != null) {
/* 68 */       this.parameters = new HashMap(parameters);
/*    */     } else {
/* 70 */       this.parameters = null;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\DeliverySpecification.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */