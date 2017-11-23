/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.holodeckb2b.common.util.Utils;
/*    */ import org.holodeckb2b.interfaces.general.IProperty;
/*    */ import org.holodeckb2b.interfaces.general.IService;
/*    */ import org.holodeckb2b.interfaces.pmode.IBusinessInfo;
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
/*    */ public class BusinessInfo
/*    */   implements IBusinessInfo
/*    */ {
/*    */   private String action;
/*    */   private String mpc;
/*    */   private Service service;
/*    */   private Collection<IProperty> properties;
/*    */   
/*    */   public BusinessInfo(IBusinessInfo busInfo)
/*    */   {
/* 39 */     if (busInfo != null) {
/* 40 */       this.action = busInfo.getAction();
/* 41 */       this.mpc = busInfo.getMpc();
/* 42 */       this.service = new Service(busInfo.getService());
/* 43 */       setProperties(busInfo.getProperties());
/*    */     }
/*    */   }
/*    */   
/*    */   public String getAction() {
/* 48 */     return this.action;
/*    */   }
/*    */   
/*    */   public void setAction(String action) {
/* 52 */     this.action = action;
/*    */   }
/*    */   
/*    */   public String getMpc()
/*    */   {
/* 57 */     return this.mpc;
/*    */   }
/*    */   
/*    */   public void setMpc(String mpc) {
/* 61 */     this.mpc = mpc;
/*    */   }
/*    */   
/*    */   public IService getService()
/*    */   {
/* 66 */     return this.service;
/*    */   }
/*    */   
/*    */   public void setService(IService service) {
/* 70 */     this.service = new Service(service);
/*    */   }
/*    */   
/*    */   public Collection<IProperty> getProperties()
/*    */   {
/* 75 */     return this.properties;
/*    */   }
/*    */   
/*    */   public void setProperties(Collection<IProperty> props) {
/* 79 */     if ((this.properties == null) || (props == null)) {
/* 80 */       this.properties = new ArrayList();
/*    */     }
/* 82 */     if (!Utils.isNullOrEmpty(props)) {
/* 83 */       for (IProperty p : props)
/* 84 */         this.properties.add(new Property(p));
/*    */     }
/*    */   }
/*    */   
/*    */   public void addProperty(Property prop) {
/* 89 */     if (this.properties == null) {
/* 90 */       this.properties = new ArrayList();
/*    */     }
/* 92 */     this.properties.add(prop);
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\BusinessInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */