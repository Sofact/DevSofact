package controller.utilidades;

import java.security.cert.X509Certificate;
import java.util.List;

import xades4j.providers.impl.KeyStoreKeyingDataProvider.SigningCertSelector;

public class FirstCertificateSelector
  implements KeyStoreKeyingDataProvider.SigningCertSelector
{
  public X509Certificate selectCertificate(List<X509Certificate> availableCertificates)
  {
	  
	  System.out.println("El serial del certificado::::" + availableCertificates.size());
	  
    return (X509Certificate)availableCertificates.get(0);
  }
}
