package org.holodeckb2b.esensconnector.submit;

import java.security.cert.X509Certificate;
import org.holodeckb2b.interfaces.general.IService;

public abstract interface IPModeSMPInfo
{
  public static final String AS4_ENDPOINT = "busdox-transport-ebms3-as4";
  
  public abstract String getURL();
  
  public abstract X509Certificate getCertificate();
  
  public abstract String getAction();
  
  public abstract IService getService();
}


/* Location:              C:\Users\jure\Desktop\esensconnector-0.7-SNAPSHOT.jar!\org\holodeckb2b\esensconnector\submit\IPModeSMPInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */