/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import org.holodeckb2b.interfaces.as4.pmode.IReceptionAwareness;
/*    */ import org.holodeckb2b.interfaces.general.Interval;
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
/*    */ public class ReceptionAwarenessConfig
/*    */   implements IReceptionAwareness
/*    */ {
/*    */   private int maxRetries;
/*    */   private Interval interval;
/*    */   private boolean dupDetection;
/*    */   
/*    */   public ReceptionAwarenessConfig(IReceptionAwareness raConfig)
/*    */   {
/* 35 */     if (raConfig != null) {
/* 36 */       this.maxRetries = raConfig.getMaxRetries();
/* 37 */       this.interval = raConfig.getRetryInterval();
/* 38 */       this.dupDetection = raConfig.useDuplicateDetection();
/*    */     }
/*    */   }
/*    */   
/*    */   public int getMaxRetries()
/*    */   {
/* 44 */     return this.maxRetries;
/*    */   }
/*    */   
/*    */   public void setMaxRetries(int maxRetries) {
/* 48 */     this.maxRetries = maxRetries;
/*    */   }
/*    */   
/*    */   public Interval getRetryInterval()
/*    */   {
/* 53 */     return this.interval;
/*    */   }
/*    */   
/*    */   public void setRetryInterval(Interval interval) {
/* 57 */     this.interval = interval;
/*    */   }
/*    */   
/*    */   public boolean useDuplicateDetection()
/*    */   {
/* 62 */     return this.dupDetection;
/*    */   }
/*    */   
/*    */   public void setDuplicateDetection(boolean useDupDetection) {
/* 66 */     this.dupDetection = useDupDetection;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\ReceptionAwarenessConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */