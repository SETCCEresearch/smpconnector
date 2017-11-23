/*     */ package org.holodeckb2b.esensconnector.persistency;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.persistence.Column;
/*     */ import javax.persistence.Entity;
/*     */ import javax.persistence.GeneratedValue;
/*     */ import javax.persistence.Id;
/*     */ import javax.persistence.NamedQueries;
/*     */ import javax.persistence.Table;
/*     */ import org.holodeckb2b.common.util.Utils;
/*     */ import org.holodeckb2b.esensconnector.submit.ISBDHHandler;
/*     */ 
/*     */ 
/*     */ @Entity
/*     */ @Table(name="SBDH_INFO")
/*     */ @NamedQueries({@javax.persistence.NamedQuery(name="GetSBDHInfoForMsgId", query="SELECT si FROM  SimpleSBDHData si WHERE si.ebmsMessageId = :ebMSMsgId AND si.direction = :direction ")})
/*     */ public class SimpleSBDHData
/*     */   implements Serializable
/*     */ {
/*     */   @Id
/*     */   @GeneratedValue
/*     */   long OID;
/*     */   String ebmsMessageId;
/*     */   Direction direction;
/*     */   String receiverId;
/*     */   String receiverIdScheme;
/*     */   String senderId;
/*     */   String senderIdScheme;
/*     */   String instanceId;
/*     */   @Column(length=1024)
/*     */   String documentId;
/*     */   @Column(length=1024)
/*     */   String documentName;
/*     */   String documentInstanceIdentifier;
/*     */   @Column(length=1024)
/*     */   String processId;
/*     */   byte[] digest;
/*     */   public SimpleSBDHData() {}
/*     */   
/*     */   public static enum Direction
/*     */   {
/*  42 */     IN,  OUT;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Direction() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */   boolean remEvidenceRequired = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleSBDHData(ISBDHHandler docData, byte[] digest, String ebMSMessageId, Direction direction)
/*     */   {
/* 118 */     if (Utils.isNullOrEmpty(ebMSMessageId)) {
/* 119 */       throw new IllegalArgumentException("ebMS messageId MUST NOT be null or empty");
/*     */     }
/* 121 */     this.direction = direction;
/* 122 */     this.ebmsMessageId = ebMSMessageId;
/* 123 */     this.digest = digest;
/* 124 */     this.documentId = docData.getDocumentId();
/* 125 */     this.documentName = docData.getDocumentName();
/* 126 */     this.documentInstanceIdentifier = docData.getDocumentInstanceIdentifier();
/* 127 */     this.instanceId = docData.getInstanceId();
/* 128 */     this.processId = docData.getProcessId();
/* 129 */     this.remEvidenceRequired = docData.isNonRepudiationRequired();
/* 130 */     this.receiverId = docData.getFirstReceiverIdValue();
/* 131 */     this.receiverIdScheme = docData.getFirstReceiverIdScheme();
/* 132 */     this.senderId = docData.getFirstSenderIdValue();
/* 133 */     this.senderIdScheme = docData.getFirstSenderIdScheme();
/*     */   }
/*     */   
/*     */   public String getEbmsMessageId() {
/* 137 */     return this.ebmsMessageId;
/*     */   }
/*     */   
/*     */   public byte[] getDigest() {
/* 141 */     return this.digest;
/*     */   }
/*     */   
/*     */   public String getReceiverId() {
/* 145 */     return this.receiverId;
/*     */   }
/*     */   
/*     */   public String getReceiverIdScheme() {
/* 149 */     return this.receiverIdScheme;
/*     */   }
/*     */   
/*     */   public String getSenderId() {
/* 153 */     return this.senderId;
/*     */   }
/*     */   
/*     */   public String getSenderIdScheme() {
/* 157 */     return this.senderIdScheme;
/*     */   }
/*     */   
/*     */   public String getInstanceId() {
/* 161 */     return this.instanceId;
/*     */   }
/*     */   
/*     */   public String getDocumentId() {
/* 165 */     return this.documentId;
/*     */   }
/*     */   
/*     */   public String getDocumentName() {
/* 169 */     return this.documentName;
/*     */   }
/*     */   
/*     */   public String getDocumentInstanceIdentifier() {
/* 173 */     return this.documentInstanceIdentifier;
/*     */   }
/*     */   
/*     */   public String getProcessId() {
/* 177 */     return this.processId;
/*     */   }
/*     */   
/*     */   public boolean isREMEvidenceRequired() {
/* 181 */     return this.remEvidenceRequired;
/*     */   }
/*     */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\persistency\SimpleSBDHData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */