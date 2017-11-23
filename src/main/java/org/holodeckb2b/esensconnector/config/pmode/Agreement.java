/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.general.IAgreement;
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
/*    */ public class Agreement
/*    */   extends TypedName
/*    */   implements IAgreement
/*    */ {
/*    */   public Agreement(IAgreement agreement)
/*    */   {
/* 31 */     if (agreement != null) {
/* 32 */       setName(agreement.getName());
/* 33 */       setType(agreement.getType());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\Agreement.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */