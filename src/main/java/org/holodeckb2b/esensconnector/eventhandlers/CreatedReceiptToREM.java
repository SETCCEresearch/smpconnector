/*    */ package org.holodeckb2b.esensconnector.eventhandlers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import no.difi.vefa.peppol.evidence.rem.SignedRemEvidence;
/*    */ import org.holodeckb2b.common.exceptions.DatabaseException;
/*    */ import org.holodeckb2b.esensconnector.etsi.rem.EvidenceCreationFailed;
/*    */ import org.holodeckb2b.esensconnector.etsi.rem.EvidenceHelper;
/*    */ import org.holodeckb2b.esensconnector.etsi.rem.EvidenceHelper.EvidenceType;
/*    */ import org.holodeckb2b.esensconnector.persistency.SimpleSBDHData;
/*    */ import org.holodeckb2b.esensconnector.persistency.SimpleSBDHData.Direction;
/*    */ import org.holodeckb2b.esensconnector.persistency.SimpleSBDHDataDAO;
/*    */ import org.holodeckb2b.interfaces.events.IMessageProcessingEvent;
/*    */ import org.holodeckb2b.interfaces.events.IMessageProcessingEventHandler;
/*    */ import org.holodeckb2b.interfaces.events.types.IReceiptCreatedEvent;
/*    */ import org.holodeckb2b.interfaces.messagemodel.IMessageUnit;
/*    */ import org.holodeckb2b.interfaces.messagemodel.IReceipt;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreatedReceiptToREM
/*    */   implements IMessageProcessingEventHandler
/*    */ {
/* 27 */   private static Logger log = LoggerFactory.getLogger(CreatedReceiptToREM.class);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 32 */   private String remOutDirectory = null;
/*    */   
/*    */   CreatedReceiptToREM(String outDirectory) {
/* 35 */     this.remOutDirectory = outDirectory;
/*    */   }
/*    */   
/*    */
public void handleEvent(IMessageProcessingEvent event)
        throws IllegalArgumentException
{
    log.debug("Check that event is of correct type");
    IReceiptCreatedEvent rcptEvent;
    if(event instanceof IReceiptCreatedEvent)
    {
        rcptEvent = (IReceiptCreatedEvent)event;
    } else
    {
        log.error("{} event type can not be handled by this handler", event.getClass().getSimpleName());
        throw new IllegalArgumentException("Only IReceiptCreatedEvent can be handled");
    }
    String ebMSMessageId = rcptEvent.getSubject().getMessageId();
    log.debug("Get SBDH meta-data for messageId ({}) referenced by Receipt", ebMSMessageId);
    SimpleSBDHData metadata;
    try
    {
        metadata = SimpleSBDHDataDAO.getDocumentInfoForMessageId(ebMSMessageId, org.holodeckb2b.esensconnector.persistency.SimpleSBDHData.Direction.IN);
        log.debug("Retrieved meta-data from the connector database");
    }
    catch(DatabaseException ex)
    {
        log.error("Could not retrieve the SBDH meta-data from the connector database! Error details: {}", ex.getMessage());
        return;
    }
    if(metadata.isREMEvidenceRequired())
    {
        log.debug("REM evidence must be created for the {} business document [{}] in acknowledged message [{}]", new Object[] {
                metadata.getDocumentName(), metadata.getInstanceId(), ebMSMessageId
        });
        try
        {
            no.difi.vefa.peppol.evidence.rem.SignedRemEvidence signedEvidence = EvidenceHelper.createEvidence(org.holodeckb2b.esensconnector.etsi.rem.EvidenceHelper.EvidenceType.DeliveryNonDelivery, true, rcptEvent.getReceipt().getTimestamp(), metadata);
            EvidenceHelper.saveEvidenceToFile(signedEvidence, remOutDirectory, null);
        }
        catch(EvidenceCreationFailed ecf)
        {
            log.error("An error occurred during creation of evidence for Receipt {}.\n\tError details: {}", ebMSMessageId, ecf.getMessage());
        }
        catch(IOException iox)
        {
            log.error("Could not write evidence for Signal {} to output directory {}!\n\tError details: {}", new Object[] {
                    ebMSMessageId, remOutDirectory, iox.getMessage()
            });
        }
    } else
    {
        log.info("The {} business document [{}] in acknowledged message [{}] does not require a REM evidence.", new Object[] {
                metadata.getDocumentName(), metadata.getInstanceId(), ebMSMessageId
        });
    }
}
/*    */ }


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\eventhandlers\CreatedReceiptToREM.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */