package org.holodeckb2b.esensconnector.submit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBElement;
import no.difi.vefa.peppol.common.api.Icd;
import no.difi.vefa.peppol.common.lang.EndpointNotFoundException;
import no.difi.vefa.peppol.common.model.DocumentTypeIdentifier;
import no.difi.vefa.peppol.common.model.Endpoint;
import no.difi.vefa.peppol.common.model.ParticipantIdentifier;
import no.difi.vefa.peppol.common.model.ProcessIdentifier;
import no.difi.vefa.peppol.common.model.Scheme;
import no.difi.vefa.peppol.common.model.ServiceMetadata;
import no.difi.vefa.peppol.common.model.TransportProfile;
import no.difi.vefa.peppol.lookup.LookupClient;
import no.difi.vefa.peppol.lookup.LookupClientBuilder;
import no.difi.vefa.peppol.lookup.api.LookupException;
import no.difi.vefa.peppol.lookup.locator.BusdoxLocator;
import no.difi.vefa.peppol.lookup.locator.DynamicLocator;
import org.apache.commons.logging.LogFactory;
import org.holodeckb2b.common.util.Utils;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.BusinessScope;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.BusinessService;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.DocumentIdentification;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.Partner;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.PartnerIdentification;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.Scope;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.ServiceTransaction;
import org.unece.cefact.namespaces.standardbusinessdocumentheader.StandardBusinessDocumentHeader;

public class SBDHHandler implements ISBDHHandler
{
    protected final ParticipantIdentifier receiverId;
    protected final ParticipantIdentifier senderId;
    protected final DocumentTypeIdentifier documentId;
    protected final String documentType;
    protected final String documentInstanceId;
    protected  ProcessIdentifier processId;
    protected final String instanceId;
    protected boolean isNonRepudiationRequired;

    protected org.apache.commons.logging.Log log= LogFactory.getLog(this.getClass().getName());

    public SBDHHandler(StandardBusinessDocumentHeader header)
            throws SBDHHandlerException
    {
        if (header.getReceiver().isEmpty()) {
            throw new SBDHHandlerException("Receiver not present in SBDH");
        }

        this.receiverId = new ParticipantIdentifier(((Partner)header.getReceiver().get(0)).getIdentifier().getValue(), new Scheme(((Partner)header.getReceiver().get(0)).getIdentifier().getAuthority()));
        this.senderId = new ParticipantIdentifier(((Partner)header.getSender().get(0)).getIdentifier().getValue(), new Scheme(((Partner)header.getSender().get(0)).getIdentifier().getAuthority()));

        if ((header.getBusinessScope() == null) || (header.getBusinessScope().getScope() == null)) {
            throw new SBDHHandlerException("Business Scope not present in SBDH");
        }

        List<Scope> scopeList = header.getBusinessScope().getScope();
        String sbdhDocumentId = null;
        String sbdhProcessId = null;
        String sbdhDocInstanceId = null;
        for (Scope scope : scopeList) {
            if ("DOCUMENTID".equals(scope.getType())) {
                sbdhDocumentId = scope.getInstanceIdentifier();
                sbdhDocInstanceId = scope.getIdentifier();
                for (JAXBElement e : scope.getScopeInformation()) {
                    if (BusinessService.class.equals(e.getDeclaredType())) {
                        BusinessService businessSvc = (BusinessService)e.getValue();

                        this.isNonRepudiationRequired = Boolean.parseBoolean(businessSvc.getServiceTransaction().getIsNonRepudiationOfReceiptRequired());
                    }
                }
            }


            if ("PROCESSID".equals(scope.getType())) {
                sbdhProcessId = scope.getInstanceIdentifier();
            }
        }
        if (sbdhDocumentId == null) {
            throw new SBDHHandlerException("DOCUMENTID not present in SBDH Business Scope");
        }

        this.documentId = new DocumentTypeIdentifier(sbdhDocumentId,null);

        this.documentInstanceId = sbdhDocInstanceId;

        this.processId = (sbdhProcessId != null ? new ProcessIdentifier(sbdhProcessId) : null);

        this.instanceId = header.getDocumentIdentification().getInstanceIdentifier();
        if (Utils.isNullOrEmpty(this.instanceId)) {
            throw new SBDHHandlerException("No instance identifier in the SBDH");
        }
        this.documentType = header.getDocumentIdentification().getType();
        if (Utils.isNullOrEmpty(this.documentType)) {
            throw new SBDHHandlerException("No document type in the SBDH");
        }
    }

    public String getFirstReceiverIdValue() {
        return this.receiverId.getIcd().getCode() + ":" + this.receiverId.getIdentifier();
    }

    public String getFirstReceiverIdScheme()
    {
        return this.receiverId.getScheme().getValue();
    }

    public String getFirstSenderIdValue()
    {
        return this.senderId.getIcd().getCode() + ":" + this.senderId.getIdentifier();
    }

    public String getFirstSenderIdScheme()
    {
        return this.senderId.getScheme().getValue();
    }

    public String getDocumentId()
    {
        return this.documentId.getIdentifier();
    }

    public String getDocumentName()
    {
        return this.documentId.getXmlRootElement();
    }

    public String getDocumentInstanceIdentifier()
    {
        return this.documentInstanceId;
    }

    public String getProcessId()
    {
        return this.processId.getIdentifier();
    }

    public String getDocumentType()
    {
        return this.documentType;
    }

    public String getInstanceId()
    {
        return this.instanceId;
    }

    public boolean isNonRepudiationRequired()
    {
        return this.isNonRepudiationRequired;
    }

    public IPModeSMPInfo getPModeSMPInfo() throws SBDHHandlerException
    {
        return getPModeSMPInfo(ISBDHHandler.SMLZone.PRODUCTION);
    }

    public IPModeSMPInfo getPModeSMPInfo(ISBDHHandler.SMLZone zone) throws SBDHHandlerException  {

        String smlInfo;
        switch (zone) {
            case TEST:
                smlInfo = DynamicLocator.OPENPEPPOL_TEST;
                break;
            default:
                smlInfo = DynamicLocator.OPENPEPPOL_PRODUCTION;
        }

        try {
            log.debug("1");
            LookupClient smpClient = LookupClientBuilder.newInstance().locator(new BusdoxLocator(smlInfo)).build();
            log.debug("2 "+this.receiverId+ " "+ this.documentId);
            log.debug("2.1"+this.documentId.getScheme()+ " "+ this.documentId.getIdentifier());

            ServiceMetadata sm = smpClient.getServiceMetadata(this.receiverId, this.documentId);
            log.debug("3");

            //Endpoint endpoint = sm.getEndpoint(this.processId, new TransportProfile[] { new TransportProfile("bdxr-transport-ebms3-as4-v1p0"), TransportProfile.AS4 });
            Endpoint endpoint =sm.getEndpoints().get(0);
            log.debug("4 "+endpoint.getAddress()+" "+endpoint.getProcessIdentifier()+" "+endpoint.getTransportProfile());

            if (endpoint == null) {
                throw new SBDHHandlerException("Receiver does not support the AS4 Protocol");
            }

            this.processId= new ProcessIdentifier("urn:www.cenbii.eu:profile:bii04:ver2.0");
            log.debug("5 "+this.receiverId+" "+this.documentId+" "+this.processId);

            return new PModeSMPInfo(this.receiverId, this.documentId, this.processId, endpoint);

//        } catch (EndpointNotFoundException e) {
//            throw new SBDHHandlerException("Receiver does not support the AS4 Protocol"+ getStack(e));
        } catch (LookupException e) {
            throw new SBDHHandlerException("Endpoint not found"+getStack(e));
        }  catch (Exception e) {
            throw new SBDHHandlerException("Other Exception"+getStack(e));
        }
    }

    private String getStack(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}

