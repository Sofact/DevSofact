package controller.utilidades;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStoreException;


import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;



import xades4j.UnsupportedAlgorithmException;
import xades4j.algorithms.Algorithm;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.algorithms.GenericAlgorithm;
import xades4j.production.DataObjectReference;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesEpesSigningProfile;
import xades4j.production.XadesSignatureResult;
import xades4j.production.XadesSigner;
import xades4j.properties.DataObjectDesc;
import xades4j.properties.ObjectIdentifier;
import xades4j.properties.SignaturePolicyBase;
import xades4j.properties.SignaturePolicyIdentifierProperty;
import xades4j.properties.SignaturePolicyImpliedProperty;
import xades4j.properties.SignerRoleProperty;
import xades4j.properties.SigningTimeProperty;
import xades4j.providers.AlgorithmsProviderEx;
import xades4j.providers.BasicSignatureOptionsProvider;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SignaturePolicyInfoProvider;
import xades4j.providers.SignaturePropertiesCollector;
import xades4j.providers.SignaturePropertiesProvider;
import xades4j.providers.SigningCertChainException;
import xades4j.verification.UnexpectedJCAException;
import controller.GetData;
import controller.utilidades.FileSystemKeyStoreKeyingDataProvider;

public class Signer
{
  public  void firmador(String origenPath)
  {
    Signer signer = new Signer();
    
    GetData gd = new GetData();
    //String pathZip = gd.getParametroDominio("PATH_CERTIFICADO").getPadValor();
    String certp12 = gd.getParametroDominio("PATH_CERTIFICADO").getPadValor();
    String pin = "MoreMoney010";
    String factura_xml_sin_firma = origenPath+"1.xml";  //= "E:\\EMPRESA\\FIRMADIGITAL\\face_f0053117601003B0234FA.xml";
    String factura_xml_firmada =  origenPath+".xml"; //"E:\\EMPRESA\\FIRMADIGITAL\\face_f0053117601003B0234FAf.xml";
    try
    {
    //  certp12 = args[0];
    //  pin = args[1];
    //  factura_xml_sin_firma = args[2];
    //  factura_xml_firmada = args[3];
    }
    catch (Exception e)
    {
      System.out.println("Error : " + e.getMessage());
    }
    signer.sign(certp12, pin, factura_xml_sin_firma, factura_xml_firmada);
    
  }
  
  
  
  
  public void sign(String keyPath, String password, String xmlInPath, String xmlOutPath)
  {
    try
    {
      File keyFile = new File(keyPath);
      if (!keyFile.exists()) {
        System.err.println("Archivo clave no encuentrado en la ruta: " + keyPath);
      }
      XadesSigner xsigner = getSigner(password, keyPath);
      
      
      
      signWithoutIDEnveloped(xmlInPath, xmlOutPath, xsigner);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private XadesSigner getSigner(String password, String pfxPath)
    throws Exception
  {
    KeyingDataProvider keyingProvider = getKeyingDataProvider(pfxPath, password);
     
    SignaturePolicyInfoProvider policyInfoProvider = new SignaturePolicyInfoProvider()  {
    	
    	 public SignaturePolicyBase getSignaturePolicy() {
             try {
                 return new SignaturePolicyIdentifierProperty(new ObjectIdentifier("https://facturaelectronica.dian.gov.co/politicadefirma/v2/politicadefirmav2.pdf"),
                         new URL("https://facturaelectronica.dian.gov.co/politicadefirma/v2/politicadefirmav2.pdf").openStream());
             } catch (MalformedURLException ex) {
                 //Logger.getLogger(StelaSign.class.getName()).log(Level.SEVERE, null, ex);
                 return new SignaturePolicyImpliedProperty();
             } catch (IOException ex) {
                 //Logger.getLogger(StelaSign.class.getName()).log(Level.SEVERE, null, ex);
                 return new SignaturePolicyImpliedProperty();
             }
        }
     }; 
						
     SignaturePolicyIdentifierProperty testpp = (SignaturePolicyIdentifierProperty) policyInfoProvider.getSignaturePolicy();
    
     String result = getStringFromInputStream(testpp.getPolicyDocumentStream());

     
     System.out.println("La policita:::::" +  result.substring(1, 10000));											    							    
	SignaturePropertiesProvider signaturePropsProv = new SignaturePropertiesProvider()	{
        @Override
        public void provideProperties(SignaturePropertiesCollector arg0) {
        	SigningTimeProperty sigTime = new SigningTimeProperty();
            arg0.setSignerRole(new SignerRoleProperty().withClaimedRole("supplier"));
            arg0.setSigningTime(sigTime );
        }

		};											    
    BasicSignatureOptionsProvider basicSignatureOptionsProvider = new BasicSignatureOptionsProvider()  {
																	    	public boolean includePublicKey()
																	      {
																	        return false;
																	      }
																	      
																	      public boolean includeSigningCertificate()
																	      {
																	        return true;
																	      }
																	      
																	      public boolean signSigningCertificate()
																	      {
																	        return true;
																	      }
																	      
																	     
																	    };
	
	AlgorithmsProviderEx algorithmPriverEx = new AlgorithmsProviderEx() {
        @Override
        public Algorithm getSignatureAlgorithm(String string) throws UnsupportedAlgorithmException {
           //El algoritmo de firma debe ser RSAwithSHA1 como lo pide la DIAN
           //return new GenericAlgorithm(SignatureMethod.RSA_SHA1);
        	return new GenericAlgorithm("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256");
        }
		@Override
		public Algorithm getCanonicalizationAlgorithmForSignature() {
			//return new GenericAlgorithm(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
			return new GenericAlgorithm(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
		}
		@Override
		public Algorithm getCanonicalizationAlgorithmForTimeStampProperties() {
			//return new GenericAlgorithm(Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
			return new GenericAlgorithm(Canonicalizer.ALGO_ID_C14N_OMIT_COMMENTS);
		}
		@Override
		public String getDigestAlgorithmForDataObjsReferences() {
			// TODO Auto-generated method stub
			return  Constants.ALGO_ID_DIGEST_SHA1;
		}
		@Override
		public String getDigestAlgorithmForReferenceProperties() {
			// TODO Auto-generated method stub
			return  Constants.ALGO_ID_DIGEST_SHA1;
		}
		@Override
		public String getDigestAlgorithmForTimeStampProperties() {
			// TODO Auto-generated method stub
			return  Constants.ALGO_ID_DIGEST_SHA1;
		}
		
	
};
	
    XadesEpesSigningProfile p = new XadesEpesSigningProfile(keyingProvider, policyInfoProvider);
    p.withSignaturePropertiesProvider(signaturePropsProv);
    p.withBasicSignatureOptionsProvider(basicSignatureOptionsProvider);
   
  // p.withAlgorithmsProviderEx(algorithmPriverEx);
    
    
    return p.newSigner();
  }
  
  private KeyingDataProvider getKeyingDataProvider(String pfxPath, String password)
    throws KeyStoreException, SigningCertChainException, UnexpectedJCAException
  {
    KeyingDataProvider keyingProvider = new FileSystemKeyStoreKeyingDataProvider("pkcs12", pfxPath, new FirstCertificateSelector(), new DirectPasswordProvider(password), new DirectPasswordProvider(password), true);
    if (keyingProvider.getSigningCertificateChain().isEmpty()) {
      throw new IllegalArgumentException("No se ha podido inicializar el keystore con la ruta: " + pfxPath);
    }
    
    System.out.println("Eñl singer al otro lado:::::" + keyingProvider.getSigningCertificateChain().get(0).getSerialNumber());
    return keyingProvider;
  }
  
  private void signWithoutIDEnveloped(String inputPath, String outputPath, XadesSigner signer)
    throws Exception
  {
    Document sourceDoc = XmlHelper.getDocument(inputPath);
    //sourceDoc.setDocumentURI(null);
    
    //writeXMLToFile(sourceDoc, outputPath);
    
   // sourceDoc = XmlHelper.getDocument(outputPath);
    
    
  
    Element signatureParent = (Element) sourceDoc.getElementsByTagName("ext:ExtensionContent").item(1);
     
    DataObjectDesc dataObjRef = new DataObjectReference("").withTransform(new EnvelopedSignatureTransform());//crea un dataobject del xml para firmar
    
    
   
    XadesSignatureResult resultados = signer.sign(new SignedDataObjects(new DataObjectDesc[] { dataObjRef }), signatureParent );
    
    System.out.println("Los resultadosXML::::"+resultados.getSignature());
     
   // System.out.println("El resultado de la firma::" + result);
    
    writeXMLToFile(sourceDoc, outputPath);
  }
  
  private void writeXMLToFile(Document doc, String outputPath)
    throws IOException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException, FileNotFoundException
  {
    Source source = new DOMSource(doc);
    
    File outFile = new File(outputPath);
    outFile.getParentFile().mkdirs();
    outFile.createNewFile();
    FileOutputStream fos = new FileOutputStream(outFile);
    
    StreamResult result = new StreamResult(fos);
    
 
	TransformerFactory tf = TransformerFactory.newInstance();
	Transformer transformer = tf.newTransformer();
	transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	//transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
	transformer.transform(source, result);
    
    
    fos.close();
  }
  
  private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
  
  
  
}
