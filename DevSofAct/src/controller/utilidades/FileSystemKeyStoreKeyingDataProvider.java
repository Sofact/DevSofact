package controller.utilidades;


import java.io.File;
import java.security.KeyStore;
import java.security.KeyStore.Builder;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;

import controller.utilidades.KeyStoreKeyingDataProvider;

public class FileSystemKeyStoreKeyingDataProvider
  extends KeyStoreKeyingDataProvider
{
  public FileSystemKeyStoreKeyingDataProvider(String keyStoreType, final String keyStorePath, FirstCertificateSelector firstCertificateSelector, DirectPasswordProvider directPasswordProvider, DirectPasswordProvider directPasswordProvider2, boolean returnFullChain)
    throws KeyStoreException
  {
	 
	  
    super(new KeyStoreKeyingDataProvider.KeyStoreBuilderCreator()
    {
      public KeyStore.Builder getBuilder(KeyStore.ProtectionParameter loadProtection)
      {
    	
    	return KeyStore.Builder.newInstance("pkcs12", null, new File(keyStorePath), loadProtection);
        //return KeyStore.Builder.newInstance(null, loadProtection);
      }
    }, firstCertificateSelector, directPasswordProvider, directPasswordProvider2, returnFullChain);
  }
  
  protected KeyStore.ProtectionParameter getKeyProtection(String entryAlias, X509Certificate entryCert, KeyStoreKeyingDataProvider.KeyEntryPasswordProvider entryPasswordProvider)
  {
    return new KeyStore.PasswordProtection(entryPasswordProvider.getPassword(entryAlias, entryCert));
  }
}
