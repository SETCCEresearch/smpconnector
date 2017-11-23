/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.general.IService;
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
/*    */ public class Service
/*    */   extends TypedName
/*    */   implements IService
/*    */ {
/*    */   public Service(IService service)
/*    */   {
/* 31 */     if (service != null) {
/* 32 */       setName(service.getName());
/* 33 */       setType(service.getType());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\Service.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */