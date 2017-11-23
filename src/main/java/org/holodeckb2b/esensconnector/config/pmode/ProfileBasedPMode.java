/*     */ package org.holodeckb2b.esensconnector.config.pmode;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.holodeckb2b.interfaces.general.IAgreement;
/*     */ import org.holodeckb2b.interfaces.pmode.ILeg;
/*     */ import org.holodeckb2b.interfaces.pmode.ILeg.Label;
/*     */ import org.holodeckb2b.interfaces.pmode.IPMode;
/*     */ import org.holodeckb2b.interfaces.pmode.ITradingPartnerConfiguration;
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
/*     */ public class ProfileBasedPMode
/*     */   implements IPMode
/*     */ {
/*     */   private String id;
/*     */   private Boolean include;
/*     */   private String mep;
/*     */   private String mepBinding;
/*     */   private Agreement agreement;
/*     */   private PartnerConfig initiator;
/*     */   private PartnerConfig responder;
/*     */   private ArrayList<Leg> legs;
/*     */   
/*     */   public ProfileBasedPMode(IPMode profile)
/*     */   {
/*  53 */     if (profile != null) {
/*  54 */       this.include = profile.includeId();
/*  55 */       this.mep = profile.getMep();
/*  56 */       this.mepBinding = profile.getMepBinding();
/*  57 */       this.agreement = new Agreement(profile.getAgreement());
/*  58 */       this.initiator = new PartnerConfig(profile.getInitiator());
/*  59 */       this.responder = new PartnerConfig(profile.getResponder());
/*  60 */       this.legs = new ArrayList(1);
/*  61 */       if (profile.getLegs() != null) {
/*  62 */         for (ILeg l : profile.getLegs()) {
/*  63 */           addLeg(l);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String getId() {
/*  70 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  74 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Boolean includeId()
/*     */   {
/*  79 */     return this.include;
/*     */   }
/*     */   
/*     */   public void setIncludeId(Boolean includeId) {
/*  83 */     this.include = includeId;
/*     */   }
/*     */   
/*     */   public IAgreement getAgreement()
/*     */   {
/*  88 */     return this.agreement;
/*     */   }
/*     */   
/*     */   public void setAgreement(IAgreement agreement) {
/*  92 */     this.agreement = new Agreement(agreement);
/*     */   }
/*     */   
/*     */   public String getMep()
/*     */   {
/*  97 */     return this.mep;
/*     */   }
/*     */   
/*     */   public void setMep(String mep) {
/* 101 */     this.mep = mep;
/*     */   }
/*     */   
/*     */   public String getMepBinding()
/*     */   {
/* 106 */     return this.mepBinding;
/*     */   }
/*     */   
/*     */   public void setMepBinding(String mepBinding) {
/* 110 */     this.mepBinding = mepBinding;
/*     */   }
/*     */   
/*     */   public PartnerConfig getInitiator()
/*     */   {
/* 115 */     return this.initiator;
/*     */   }
/*     */   
/*     */   public void setInitiator(ITradingPartnerConfiguration initiator) {
/* 119 */     this.initiator = (initiator != null ? new PartnerConfig(initiator) : null);
/*     */   }
/*     */   
/*     */   public PartnerConfig getResponder()
/*     */   {
/* 124 */     return this.responder;
/*     */   }
/*     */   
/*     */   public void setResponder(ITradingPartnerConfiguration responder) {
/* 128 */     this.responder = (responder != null ? new PartnerConfig(responder) : null);
/*     */   }
/*     */   
/*     */   public List<? extends ILeg> getLegs()
/*     */   {
/* 133 */     return this.legs;
/*     */   }
/*     */   
/*     */   public Leg getLeg(ILeg.Label label)
/*     */   {
/* 138 */     for (Leg l : this.legs) {
/* 139 */       if (l.getLabel() == label) {
/* 140 */         return l;
/*     */       }
/*     */     }
/* 143 */     switch (label) {
/*     */     case REQUEST: 
/* 145 */       return (Leg)this.legs.get(0);
/*     */     case REPLY: 
/* 147 */       return (Leg)this.legs.get(1);
/*     */     }
/*     */     
/* 150 */     return null;
/*     */   }
/*     */   
/*     */   public void addLeg(ILeg leg) {
/* 154 */     if (this.legs == null) {
/* 155 */       this.legs = new ArrayList();
/*     */     }
/* 157 */     if (leg != null) {
/* 158 */       this.legs.add(new Leg(leg));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\config\pmode\ProfileBasedPMode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */