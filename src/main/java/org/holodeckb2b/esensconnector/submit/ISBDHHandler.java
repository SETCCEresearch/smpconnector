/*    */ package org.holodeckb2b.esensconnector.submit;
/*    */ 
/*    */ 
/*    */ public abstract interface ISBDHHandler
/*    */ {
/*    */   public static final String DOCUMENT_ID = "DOCUMENTID";
/*    */   
/*    */   public static final String PROCESS_ID = "PROCESSID";
/*    */   
/*    */ 
/*    */   public abstract String getFirstReceiverIdValue();
/*    */   
/*    */ 
/*    */   public abstract String getFirstReceiverIdScheme();
/*    */   
/*    */ 
/*    */   public abstract String getFirstSenderIdValue();
/*    */   
/*    */   public abstract String getFirstSenderIdScheme();
/*    */   
/*    */   public abstract String getDocumentType();
/*    */   
/*    */   public abstract String getInstanceId();
/*    */   
/*    */   public static enum SMLZone
/*    */   {
/* 27 */     PRODUCTION,  TEST;
/*    */     
/*    */     private SMLZone() {}
/*    */   }
/*    */   
/*    */   public abstract String getDocumentId();
/*    */   
/*    */   public abstract String getDocumentName();
/*    */   
/*    */   public abstract String getDocumentInstanceIdentifier();
/*    */   
/*    */   public abstract String getProcessId();
/*    */   
/*    */   public abstract boolean isNonRepudiationRequired();
/*    */   
/*    */   public abstract IPModeSMPInfo getPModeSMPInfo(SMLZone paramSMLZone)
/*    */     throws SBDHHandlerException;
/*    */   
/*    */   public abstract IPModeSMPInfo getPModeSMPInfo()
/*    */     throws SBDHHandlerException;
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\submit\ISBDHHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */