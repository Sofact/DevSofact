package controller.utilidades;


import java.security.cert.X509Certificate;
import xades4j.providers.impl.KeyStoreKeyingDataProvider.KeyEntryPasswordProvider;
import xades4j.providers.impl.KeyStoreKeyingDataProvider.KeyStorePasswordProvider;

public class DirectPasswordProvider
  implements KeyStoreKeyingDataProvider.KeyStorePasswordProvider, KeyStoreKeyingDataProvider.KeyEntryPasswordProvider
{
  private String password;
  
  DirectPasswordProvider(String password)
  {
    this.password = password;
  }
  
  public char[] getPassword()
  {
    return this.password.toCharArray();
  }
  
  public char[] getPassword(String entryAlias, X509Certificate entryCert)
  {
    return this.password.toCharArray();
  }
}
