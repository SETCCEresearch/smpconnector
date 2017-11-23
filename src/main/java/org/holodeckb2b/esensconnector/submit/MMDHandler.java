/*    */ package org.holodeckb2b.esensconnector.submit;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import org.holodeckb2b.common.util.MessageIdGenerator;
/*    */ import org.holodeckb2b.ebms3.mmd.xml.AgreementReference;
/*    */ import org.holodeckb2b.ebms3.mmd.xml.CollaborationInfo;
/*    */ import org.holodeckb2b.ebms3.mmd.xml.MessageMetaData;
/*    */ import org.holodeckb2b.ebms3.mmd.xml.PartInfo;
/*    */ import org.holodeckb2b.ebms3.mmd.xml.Property;
/*    */ import org.holodeckb2b.interfaces.general.IProperty;
/*    */ import org.holodeckb2b.interfaces.messagemodel.IPayload;
/*    */ import org.holodeckb2b.interfaces.messagemodel.IPayload.Containment;
/*    */ import org.holodeckb2b.interfaces.messagemodel.IUserMessage;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MMDHandler
/*    */ {
/*    */   public static IUserMessage createSubmitDocument(String pmodeId, String senderParticipantId, String receiverParticipantId, String sbdhPath)
/*    */     throws IOException
/*    */   {
/* 52 */     MessageMetaData mmd = new MessageMetaData();
/*    */     
/*    */ 
/* 55 */     CollaborationInfo ciInfo = new CollaborationInfo();
/* 56 */     AgreementReference agreementRef = new AgreementReference();
/* 57 */     agreementRef.setPModeId(pmodeId);
/* 58 */     ciInfo.setAgreement(agreementRef);
/*    */     
/*    */ 
/* 61 */     ciInfo.setConversationId(MessageIdGenerator.createMessageId());
/* 62 */     mmd.setCollaborationInfo(ciInfo);
/*    */     
/*    */ 
/* 65 */     Collection<IProperty> msgProps = new ArrayList(2);
/* 66 */     Property orgSender = new Property();
/* 67 */     orgSender.setName("originalSender");
/* 68 */     orgSender.setValue(senderParticipantId);
/* 69 */     msgProps.add(orgSender);
/* 70 */     Property finalReceiver = new Property();
/* 71 */     finalReceiver.setName("finalRecipient");
/* 72 */     finalReceiver.setValue(receiverParticipantId);
/* 73 */     msgProps.add(finalReceiver);
/* 74 */     mmd.setMessageProperties(msgProps);
/*    */     
/*    */ 
/* 77 */     PartInfo sbdhPart = new PartInfo();
/* 78 */     sbdhPart.setContainment(IPayload.Containment.ATTACHMENT);
/* 79 */     sbdhPart.setMimeType("application/xml");
/* 80 */     sbdhPart.setContentLocation(sbdhPath);
/*    */     
/* 82 */     Collection<IPayload> payloads = new ArrayList();
/* 83 */     payloads.add(sbdhPart);
/* 84 */     mmd.setPayloads(payloads);
/*    */     
/* 86 */     return mmd;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\submit\MMDHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */