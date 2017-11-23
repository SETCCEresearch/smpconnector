/*    */ package org.holodeckb2b.esensconnector.persistency;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.persistence.EntityManager;
/*    */ import javax.persistence.EntityTransaction;
/*    */ import javax.persistence.Query;
/*    */ import org.holodeckb2b.common.exceptions.DatabaseException;
/*    */ import org.holodeckb2b.common.util.Utils;
/*    */ import org.holodeckb2b.esensconnector.persistency.util.JPAUtil;
/*    */ import org.holodeckb2b.esensconnector.submit.ISBDHHandler;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleSBDHDataDAO
/*    */ {
/* 23 */   private static Logger log = LoggerFactory.getLogger(SimpleSBDHDataDAO.class);
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
/*    */   public static SimpleSBDHData storeDocument(ISBDHHandler sbdhInfo, byte[] digest, String ebMSMessageId, SimpleSBDHData.Direction direction)
/*    */     throws DatabaseException
/*    */   {
/* 39 */     EntityManager em = null;
/*    */     try
/*    */     {
/* 42 */       em = JPAUtil.getEntityManager();
/*    */       
/* 44 */       SimpleSBDHData entity = new SimpleSBDHData(sbdhInfo, digest, ebMSMessageId, direction);
/* 45 */       em.getTransaction().begin();
/* 46 */       em.persist(entity);
/* 47 */       em.getTransaction().commit();
/* 48 */       log.debug("Saved document meta-data to database");
/* 49 */       return entity;
/*    */     } catch (Exception e) {
/* 51 */       log.error("Can not save document data due to error : " + e.getMessage());
/* 52 */       throw new DatabaseException("Can not save document data due to error!", e);
/*    */     } finally {
/* 54 */       if (em != null) { em.close();
/*    */       }
/*    */     }
/*    */   }
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
/*    */   public static SimpleSBDHData getDocumentInfoForMessageId(String messageId, SimpleSBDHData.Direction direction)
/*    */     throws DatabaseException
/*    */   {
/* 73 */     EntityManager em = null;
/*    */     try
/*    */     {
/* 76 */       em = JPAUtil.getEntityManager();
/*    */       
/* 78 */       log.debug("Query connector database for ebMS messageId: {}", messageId);
/*    */       
/*    */ 
/*    */ 
/* 82 */       List<SimpleSBDHData> docs = em.createNamedQuery("GetSBDHInfoForMsgId").setParameter("ebMSMsgId", messageId).setParameter("direction", direction).getResultList();
/*    */       
/*    */       SimpleSBDHData localSimpleSBDHData;
/* 85 */       if (!Utils.isNullOrEmpty(docs)) {
/* 86 */         log.debug("Found related SBDH [instanceId={}] for ebMS messageId={}", 
/* 87 */           ((SimpleSBDHData)docs.get(0)).instanceId, messageId);
/* 88 */         return (SimpleSBDHData)docs.get(0);
/*    */       }
/* 90 */       log.warn("Found no related SDBH for ebMS messageId={}", messageId);
/* 91 */       return null;
/*    */     }
/*    */     catch (Exception e) {
/* 94 */       log.error("An error occurred while retrieving meta-data of related document: " + e.getMessage());
/* 95 */       throw new DatabaseException("An error occurred while retrieving meta-data of related document!", e);
/*    */     } finally {
/* 97 */       if (em != null) em.close();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\persistency\SimpleSBDHDataDAO.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */