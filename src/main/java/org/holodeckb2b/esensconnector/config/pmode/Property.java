/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.general.IProperty;
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
/*    */ public class Property
/*    */   extends TypedName
/*    */   implements IProperty
/*    */ {
/*    */   private String value;
/*    */   
/*    */   public Property(IProperty prop)
/*    */   {
/* 33 */     if (prop != null) {
/* 34 */       setName(prop.getName());
/* 35 */       setType(prop.getType());
/* 36 */       setValue(prop.getValue());
/*    */     }
/*    */   }
/*    */   
/*    */   public String getValue()
/*    */   {
/* 42 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(String value) {
/* 46 */     this.value = value;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\Property.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */