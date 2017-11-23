/*    */ package org.holodeckb2b.esensconnector.config.pmode;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.HashSet;
/*    */ import org.holodeckb2b.common.util.Utils;
/*    */ import org.holodeckb2b.interfaces.general.IPartyId;
/*    */ import org.holodeckb2b.interfaces.pmode.ITradingPartnerConfiguration;
/*    */ import org.holodeckb2b.interfaces.pmode.security.ISecurityConfiguration;
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
/*    */ public class PartnerConfig
/*    */   implements ITradingPartnerConfiguration
/*    */ {
/*    */   private String role;
/*    */   private Collection<IPartyId> partyIds;
/*    */   private SecurityConfig secConfig;
/*    */   
/*    */   PartnerConfig(ITradingPartnerConfiguration partnerCfg)
/*    */   {
/* 39 */     if (partnerCfg != null) {
/* 40 */       this.role = partnerCfg.getRole();
/* 41 */       this.secConfig = new SecurityConfig(partnerCfg.getSecurityConfiguration());
/*    */       
/* 43 */       if (!Utils.isNullOrEmpty(partnerCfg.getPartyIds())) {
/* 44 */         for (IPartyId pid : partnerCfg.getPartyIds()) {
/* 45 */           addPartyId(pid);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public SecurityConfig getSecurityConfiguration() {
/* 52 */     return this.secConfig;
/*    */   }
/*    */   
/*    */   public void setSecurityConfiguration(ISecurityConfiguration secConfig) {
/* 56 */     this.secConfig = new SecurityConfig(secConfig);
/*    */   }
/*    */   
/*    */   public Collection<IPartyId> getPartyIds()
/*    */   {
/* 61 */     return this.partyIds;
/*    */   }
/*    */   
/*    */   public void addPartyId(IPartyId partyId) {
/* 65 */     if (partyId == null) {
/* 66 */       return;
/*    */     }
/* 68 */     if (this.partyIds == null) {
/* 69 */       this.partyIds = new HashSet();
/*    */     }
/* 71 */     this.partyIds.add(new PartyId(partyId));
/*    */   }
/*    */   
/*    */   public void setPartyIds(Collection<IPartyId> partyIds) {
/* 75 */     this.partyIds = new HashSet();
/*    */     
/* 77 */     if (Utils.isNullOrEmpty(partyIds)) {
/* 78 */       return;
/*    */     }
/* 80 */     for (IPartyId pid : partyIds) {
/* 81 */       addPartyId(pid);
/*    */     }
/*    */   }
/*    */   
/*    */   public String getRole() {
/* 86 */     return this.role;
/*    */   }
/*    */   
/*    */   public void setRole(String role) {
/* 90 */     this.role = role;
/*    */   }
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\PartnerConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */