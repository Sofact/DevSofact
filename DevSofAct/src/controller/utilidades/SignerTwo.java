//***************************************************************************************************
// CLASE QUE REALIZA EL FIRMADO DEL XML CON LA FIRMA DIGITAL
//***************************************************************************************************


package controller.utilidades;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import controller.GetData;
import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.production.DataObjectReference;
import xades4j.production.Enveloped;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesEpesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.properties.DataObjectDesc;
import xades4j.properties.IdentifierType;
import xades4j.properties.ObjectIdentifier;
import xades4j.properties.SignaturePolicyBase;
import xades4j.properties.SignaturePolicyIdentifierProperty;
import xades4j.properties.SignerRoleProperty;
import xades4j.properties.SigningTimeProperty;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.SignaturePolicyInfoProvider;
import xades4j.providers.SignaturePropertiesCollector;
import xades4j.providers.SignaturePropertiesProvider;
import xades4j.utils.DOMHelper;



public class SignerTwo {
	
	public void signEpes(String origenPath ) throws Exception {
	    Document doc = null;
	          
	    GetData gd = new GetData();
	    String certp12 = gd.getParametroDominio("PATH_CERTIFICADO").getPadValor();
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    doc = db.parse(new File( origenPath+"1.xml"));
	              
	    Element elem = doc.getDocumentElement();
	    DOMHelper.useIdAsXmlId(elem);

	    KeyingDataProvider kdp = new FileSystemKeyStoreKeyingDataProvider( "pkcs12", certp12,
	                        new FirstCertificateSelector(),
	                        new DirectPasswordProvider("celeste215922"),
	                        new DirectPasswordProvider("celeste215922"),
	                        true);

	    // politica
	    SignaturePolicyInfoProvider policyInfoProvider = new SignaturePolicyInfoProvider() {
	        @Override
	        public SignaturePolicyBase getSignaturePolicy() {
	            try {
	            	 return new SignaturePolicyIdentifierProperty(new ObjectIdentifier("https://facturaelectronica.dian.gov.co/politicadefirma/v2/politicadefirmav2.pdf"),
	                         new URL("https://facturaelectronica.dian.gov.co/politicadefirma/v2/politicadefirmav2.pdf").openStream());
	            } catch (IOException ex) {
	            	
	            	System.out.println("Error ex:::" + ex.getMessage());
	                //Logger.getLogger(FirmaFec.class.getName()).log(Level.SEVERE, null, ex);
	            }
	            return null;
	        }
	    };

	    // Role
	    
	   	    
	    XadesSigner signer =  new XadesEpesSigningProfile(kdp, policyInfoProvider)
	               .withSignaturePropertiesProvider(new SignaturePropertiesProvider() {
	                   @Override
	                   public void provideProperties(SignaturePropertiesCollector arg0) {
	                       SigningTimeProperty sigTime = new SigningTimeProperty();
	                       arg0.setSignerRole(new SignerRoleProperty().withClaimedRole("supplier"));
	                       arg0.setSigningTime(sigTime );
	                   }

					
	               })
	          .newSigner();
	    
	    Element signatureParent = (Element) doc.getElementsByTagName("ext:ExtensionContent").item(1);
	    
	    DataObjectDesc dataObjRef = new DataObjectReference("").withTransform(new EnvelopedSignatureTransform());
        signer.sign(new SignedDataObjects(dataObjRef), signatureParent);
        
        
	   TransformerFactory tFactory = TransformerFactory.newInstance();
	   Transformer transformer = tFactory.newTransformer();
	   DOMSource source = new DOMSource(doc);
	   StreamResult result = new StreamResult(new File(origenPath+".xml"));

	   transformer.transform (source, result);
	}

}
