/*    */ package org.holodeckb2b.esensconnector.eventhandlers;
/*    */ 
/*    */ import java.nio.file.FileSystem;
/*    */ import java.nio.file.FileSystems;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.LinkOption;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.util.Map;
/*    */ import org.holodeckb2b.interfaces.events.IMessageProcessingEventHandlerFactory;
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
/*    */ public class CreatedReceiptToREMFactory
/*    */   implements IMessageProcessingEventHandlerFactory<CreatedReceiptToREM>
/*    */ {
/*    */   private static final String P_REM_OUT_DIRECTORY = "rem_out";
/* 33 */   private String remOutDirectory = null;
/*    */   
/*    */   public void init(Map<String, ?> settings)
/*    */   {
/* 37 */     if (settings != null) {
/*    */       try {
/* 39 */         this.remOutDirectory = ((String)settings.get("rem_out"));
/*    */       } catch (ClassCastException ex) {
/* 41 */         this.remOutDirectory = null;
/* 42 */         return;
/*    */       }
/*    */       
/* 45 */       if (checkDirectory(this.remOutDirectory))
/*    */       {
/*    */ 
/* 48 */         this.remOutDirectory = (this.remOutDirectory + FileSystems.getDefault().getSeparator());
/*    */       } else {
/* 50 */         this.remOutDirectory = null;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public CreatedReceiptToREM createHandler() {
/* 56 */     if (this.remOutDirectory != null) {
/* 57 */       return new CreatedReceiptToREM(this.remOutDirectory);
/*    */     }
/* 59 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private boolean checkDirectory(String dir)
/*    */   {
/*    */     try
/*    */     {
/* 70 */       Path path = Paths.get(dir, new String[0]);
/*    */       
/*    */ 
/* 73 */       if ((path == null) || (!Files.isDirectory(path, new LinkOption[0])) || (!Files.isWritable(path)))
/*    */       {
/* 75 */         return false; }
/*    */     } catch (Exception ex) {
/* 77 */       return false;
/*    */     }
/*    */     
/* 80 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\eventhandlers\CreatedReceiptToREMFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */