package controller.utilidades;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStore.Builder;
import java.security.KeyStore.CallbackHandlerProtection;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SigningCertChainException;
import xades4j.providers.SigningKeyException;
import xades4j.verification.UnexpectedJCAException;

public abstract class KeyStoreKeyingDataProvider
  implements KeyingDataProvider
{
  private final KeyStoreBuilderCreator builderCreator;
  private final SigningCertSelector certificateSelector;
  private final KeyStorePasswordProvider storePasswordProvider;
  private final KeyEntryPasswordProvider entryPasswordProvider;
  private final boolean returnFullChain;
  private KeyStore keyStore;
  private final Object lockObj;
  private boolean initialized;
  
  protected KeyStoreKeyingDataProvider(KeyStoreBuilderCreator builderCreator, SigningCertSelector certificateSelector, KeyStorePasswordProvider storePasswordProvider, KeyEntryPasswordProvider entryPasswordProvider, boolean returnFullChain)
  {
    this.builderCreator = builderCreator;
    this.certificateSelector = certificateSelector;
    this.storePasswordProvider = storePasswordProvider;
    this.entryPasswordProvider = entryPasswordProvider;
    this.returnFullChain = false;
    
    this.lockObj = new Object();
    this.initialized = false;
  }
  
  private void ensureInitialized()
    throws UnexpectedJCAException
  {
    synchronized (this.lockObj)
    {
      if (!this.initialized)
      {
        try
        {
          KeyStore.CallbackHandlerProtection storeLoadProtec = null;
          if (this.storePasswordProvider != null) {
            storeLoadProtec = new KeyStore.CallbackHandlerProtection(new CallbackHandler()
            {
              public void handle(Callback[] callbacks)
                throws IOException, UnsupportedCallbackException
              {
                PasswordCallback c = (PasswordCallback)callbacks[0];
                c.setPassword(KeyStoreKeyingDataProvider.this.storePasswordProvider.getPassword());
              }
            });
          } else {
            storeLoadProtec = new KeyStore.CallbackHandlerProtection(new CallbackHandler()
            {
              public void handle(Callback[] callbacks)
                throws IOException, UnsupportedCallbackException
              {
                throw new UnsupportedOperationException("No KeyStorePasswordProvider");
              }
            });
          }
          this.keyStore = this.builderCreator.getBuilder(storeLoadProtec).getKeyStore();
        }
        catch (KeyStoreException ex)
        {
          throw new UnexpectedJCAException("The keystore couldn't be initialized", ex);
        }
        this.initialized = true;
      }
    }
  }
  
  public List<X509Certificate> getSigningCertificateChain()
    throws SigningCertChainException, UnexpectedJCAException
  {
    ensureInitialized();
    try
    {
    	System.out.println("el tamano del kesytores::" +this.keyStore.size());
      List<X509Certificate> availableSignCerts = new ArrayList(this.keyStore.size());
      for (Enumeration<String> aliases = this.keyStore.aliases(); aliases.hasMoreElements();)
      {
    	  
        String alias = (String)aliases.nextElement();
        int contador = 0;
        System.out.println("Los aliases::: " + this.keyStore.getCertificate(alias).getPublicKey());
        if (this.keyStore.entryInstanceOf(alias, KeyStore.PrivateKeyEntry.class))
        {
          Certificate cer = this.keyStore.getCertificate(alias);
          if ((cer instanceof X509Certificate ) ) {
        	  if (contador == 0) {
            availableSignCerts.add((X509Certificate)cer);
           contador++;
        	  }
          }
        }
      }
      if (availableSignCerts.isEmpty()) {
        throw new SigningCertChainException("No certificates available in the key store");
      }
      X509Certificate signingCert = this.certificateSelector.selectCertificate(availableSignCerts);
      
      String signingCertAlias = this.keyStore.getCertificateAlias(signingCert);
      
      System.out.println("El singing certificate alias:::"+ signingCertAlias);
      if (null == signingCertAlias) {
        throw new SigningCertChainException("Selected certificate not present in the key store");
      }
      Certificate[] signingCertChain = this.keyStore.getCertificateChain(signingCertAlias);
      if (null == signingCertChain) {
        throw new SigningCertChainException("Selected certificate doesn't match a key and corresponding certificate chain");
      }
      if (this.returnFullChain)
      {
        List lChain = Arrays.asList(signingCertChain);
        
       
       // System.out.println("La cantidad del lchain::"+ lChain.get(0) );
        return Collections.checkedList(lChain, X509Certificate.class);
      }
      return Collections.singletonList((X509Certificate)signingCertChain[0]);
    }
    catch (KeyStoreException ex)
    {
      throw new UnexpectedJCAException(ex.getMessage(), ex);
    }
  }
  
  public PrivateKey getSigningKey(X509Certificate signingCert)
    throws SigningKeyException, UnexpectedJCAException
  {
    ensureInitialized();
    try
    {
      String entryAlias = this.keyStore.getCertificateAlias(signingCert);
      KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry)this.keyStore.getEntry(entryAlias, getKeyProtection(entryAlias, signingCert, this.entryPasswordProvider));
      
      return entry.getPrivateKey();
    }
    catch (UnrecoverableKeyException ex)
    {
      throw new SigningKeyException("Invalid key entry password", ex);
    }
    catch (GeneralSecurityException ex)
    {
      throw new UnexpectedJCAException(ex.getMessage(), ex);
    }
  }
  
  protected abstract KeyStore.ProtectionParameter getKeyProtection(String paramString, X509Certificate paramX509Certificate, KeyEntryPasswordProvider paramKeyEntryPasswordProvider);
  
  protected static abstract interface KeyStoreBuilderCreator
  {
    public abstract KeyStore.Builder getBuilder(KeyStore.ProtectionParameter paramProtectionParameter);
  }
  
  public static abstract interface SigningCertSelector
  {
    public abstract X509Certificate selectCertificate(List<X509Certificate> paramList);
  }
  
  public static abstract interface KeyEntryPasswordProvider
  {
    public abstract char[] getPassword(String paramString, X509Certificate paramX509Certificate);
  }
  
  public static abstract interface KeyStorePasswordProvider
  {
    public abstract char[] getPassword();
  }
}
