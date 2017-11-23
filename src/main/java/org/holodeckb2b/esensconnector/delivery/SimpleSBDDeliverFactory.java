/*     */ package org.holodeckb2b.esensconnector.delivery;
/*     */ 
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Map;
/*     */ import org.holodeckb2b.interfaces.delivery.IMessageDeliverer;
/*     */ import org.holodeckb2b.interfaces.delivery.IMessageDelivererFactory;
/*     */ import org.holodeckb2b.interfaces.delivery.MessageDeliveryException;
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
/*     */ public class SimpleSBDDeliverFactory
/*     */   implements IMessageDelivererFactory
/*     */ {
/*     */   private static final String P_SBD_OUT_DIRECTORY = "sbd_out";
/*     */   private static final String P_SIG_OUT_DIRECTORY = "signals_out";
/*  60 */   private String sbdOutDirectory = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  65 */   private String sigOutDirectory = null;
/*     */   
/*     */   public void init(Map<String, ?> settings)
/*     */     throws MessageDeliveryException
/*     */   {
/*  70 */     if (settings != null) {
/*     */       try {
/*  72 */         this.sbdOutDirectory = ((String)settings.get("sbd_out"));
/*     */       } catch (ClassCastException ex) {
/*  74 */         throw new MessageDeliveryException("Configuration error! No directory for SBD specified!");
/*     */       }
/*     */     }
/*     */     
/*  78 */     if (!checkDirectory(this.sbdOutDirectory)) {
/*  79 */       throw new MessageDeliveryException("Configuration error! Specified directory for SBD documents [" + this.sbdOutDirectory + " does not exits or is not writable!");
/*     */     }
/*     */     
/*     */ 
/*  83 */     this.sbdOutDirectory = (this.sbdOutDirectory + FileSystems.getDefault().getSeparator());
/*     */     try
/*     */     {
/*  86 */       this.sigOutDirectory = ((String)settings.get("signals_out"));
/*     */     } catch (ClassCastException ex) {
/*  88 */       throw new MessageDeliveryException("Configuration error! No directory for Signal meta-data specified!");
/*     */     }
/*  90 */     if (!checkDirectory(this.sigOutDirectory)) {
/*  91 */       throw new MessageDeliveryException("Configuration error! Specified directory for SSignals [" + this.sigOutDirectory + " does not exits or is not writable!");
/*     */     }
/*     */     
/*     */ 
/*  95 */     this.sigOutDirectory = (this.sigOutDirectory + FileSystems.getDefault().getSeparator());
/*     */   }
/*     */   
/*     */   public IMessageDeliverer createMessageDeliverer()
/*     */     throws MessageDeliveryException
/*     */   {
/* 101 */     return new SimpleSBDDeliverer(this.sbdOutDirectory, this.sigOutDirectory);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean checkDirectory(String dir)
/*     */   {
/*     */     try
/*     */     {
/* 112 */       Path path = Paths.get(dir, new String[0]);
/*     */       
/*     */ 
/* 115 */       if ((path == null) || (!Files.isDirectory(path, new LinkOption[0])) || (!Files.isWritable(path)))
/*     */       {
/* 117 */         return false; }
/*     */     } catch (Exception ex) {
/* 119 */       return false;
/*     */     }
/*     */     
/* 122 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\delivery\SimpleSBDDeliverFactory.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */