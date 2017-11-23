/*    */ package org.holodeckb2b.esensconnector.submit;
/*    */ 
/*    */ import java.security.cert.X509Certificate;
/*    */ import no.difi.vefa.peppol.common.model.DocumentTypeIdentifier;
/*    */ import no.difi.vefa.peppol.common.model.Endpoint;
/*    */ import no.difi.vefa.peppol.common.model.ParticipantIdentifier;
/*    */ import no.difi.vefa.peppol.common.model.ProcessIdentifier;
/*    */ import no.difi.vefa.peppol.common.model.Scheme;
/*    */ import org.holodeckb2b.esensconnector.config.pmode.Service;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PModeSMPInfo
/*    */   implements IPModeSMPInfo
/*    */ {
/*    */   private final Endpoint endpoint;
/*    */   private final String documentId;
/*    */   private final String processId;
/*    */   private final String processIdScheme;
/*    */   private final ParticipantIdentifier receiverId;
/*    */   
/*    */   PModeSMPInfo(ParticipantIdentifier receiverId, DocumentTypeIdentifier documentId, ProcessIdentifier processId, Endpoint endpoint)
/*    */   {
/* 48 */     this.receiverId = receiverId;
/* 49 */     this.documentId = documentId.getIdentifier();
/* 50 */     this.processId = processId.getIdentifier();
/* 51 */     this.processIdScheme = processId.getScheme().getValue();
/* 52 */     this.endpoint = endpoint;
/*    */   }
/*    */   
/*    */ 
/*    */   public String getURL()
/*    */   {
/* 58 */     return this.endpoint.getAddress();
/*    */   }
/*    */   
/*    */   public X509Certificate getCertificate()
/*    */   {
/* 63 */     return this.endpoint.getCertificate();
/*    */   }
/*    */   
/*    */   public String getAction()
/*    */   {
/* 68 */     return this.documentId;
/*    */   }
/*    */   
/*    */   public IService getService()
/*    */   {
/* 73 */     Service svc = new Service(null);
/*    */     
/* 75 */     svc.setName(this.processId);
/* 76 */     svc.setType(this.processIdScheme);
/*    */     
/* 78 */     return svc;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\submit\PModeSMPInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */