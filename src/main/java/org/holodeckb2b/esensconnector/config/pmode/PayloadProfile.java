/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.as4.pmode.IAS4PayloadProfile;
/*    */ import org.holodeckb2b.interfaces.pmode.IPayloadProfile;
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
/*    */ public class PayloadProfile
/*    */   implements IAS4PayloadProfile
/*    */ {
/*    */   private String compressionType;
/*    */   
/*    */   public PayloadProfile(IPayloadProfile payloadProfile)
/*    */   {
/* 33 */     if ((payloadProfile != null) && ((payloadProfile instanceof IAS4PayloadProfile))) {
/* 34 */       this.compressionType = ((IAS4PayloadProfile)payloadProfile).getCompressionType();
/*    */     }
/*    */   }
/*    */   
/*    */   public String getCompressionType()
/*    */   {
/* 40 */     return this.compressionType;
/*    */   }
/*    */   
/*    */   public void setCompressionType(String compressionType) {
/* 44 */     this.compressionType = compressionType;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\PayloadProfile.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */