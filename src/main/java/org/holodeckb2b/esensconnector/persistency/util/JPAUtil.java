/*    */ package org.holodeckb2b.esensconnector.persistency.util;
/*    */ 
/*    */ import javax.persistence.EntityManager;
/*    */ import javax.persistence.EntityManagerFactory;
/*    */ import javax.persistence.Persistence;
/*    */ import org.holodeckb2b.common.exceptions.DatabaseException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JPAUtil
/*    */ {
/*    */   private static EntityManagerFactory emf;
/*    */   
/*    */   public static EntityManager getEntityManager()
/*    */     throws DatabaseException
/*    */   {
/* 48 */     if (emf == null) {
/* 49 */       emf = getEntityManagerFactory();
/*    */     }
/*    */     
/* 52 */     EntityManager em = null;
/*    */     try
/*    */     {
/* 55 */       em = emf.createEntityManager();
/*    */     }
/*    */     catch (Exception e) {
/* 58 */       throw new DatabaseException("Error while creating the EntityManager", e);
/*    */     }
/*    */     
/* 61 */     return em;
/*    */   }
/*    */   
/*    */   private static synchronized EntityManagerFactory getEntityManagerFactory() throws DatabaseException {
/* 65 */     if (emf != null) {
/* 66 */       return emf;
/*    */     }
/* 68 */     return Persistence.createEntityManagerFactory("esensconnect-db");
/*    */   }
/*    */ }
