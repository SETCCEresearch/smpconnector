/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.general.IPartyId;
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
/*    */ public class PartyId
/*    */   implements IPartyId
/*    */ {
/*    */   private String id;
/*    */   private String type;
/*    */   
/*    */   public PartyId(IPartyId partyId)
/*    */   {
/* 33 */     if (partyId != null) {
/* 34 */       this.id = partyId.getId();
/* 35 */       this.type = partyId.getType();
/*    */     }
/*    */   }
/*    */   
/*    */   public String getId()
/*    */   {
/* 41 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 45 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getType()
/*    */   {
/* 50 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(String type) {
/* 54 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\PartyId.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */