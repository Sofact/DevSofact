package co.gov.dian.servicios.facturaelectronica.reportarfactura;


import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class MacAddressInjectHandler implements SOAPHandler<SOAPMessageContext>{

   @Override
   public boolean handleMessage(SOAPMessageContext context) {

	System.out.println("Client : handleMessage()......");
		
	Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

	//if this is a request, true for outbound messages, false for inbound
	if(isRequest){
			
	try{
		
			    
	    SOAPMessage soapMsg = context.getMessage();

	   
	    SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
	    SOAPHeader soapHeader = null ;// envelope.getHeader();
	    
	  //  System.out.println("Header: " + soapHeader);
        SOAPFactory factory = SOAPFactory.newInstance();
        String prefix = "wsse";
        String wsu = "wsu";
        String uri = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
        String usuuri = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
        SOAPElement securityElem = factory.createElement("Security", prefix, uri);
       // securityElem.addAttribute(QName.valueOf("S:mustUnderstand"), "0");
        
        SOAPElement tokenElem =
                factory.createElement("UsernameToken", prefix, uri);
        tokenElem.addAttribute(QName.valueOf("wsu:Id"), "UsernameToken-2");
        tokenElem.addAttribute(QName.valueOf("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
        SOAPElement userElem =
                factory.createElement("Username", prefix, uri);
        userElem.addTextNode("8216fa1f-7894-4fb8-b415-e51b38647af7");
        SOAPElement pwdElem =
                factory.createElement("Password", prefix, uri);
        pwdElem.addTextNode("bf64ffaca6f57b8a487dbdae90e6b5ba832d15af8c9d00bd3f031c93da5fc1a6");
        pwdElem.addAttribute(QName.valueOf("Type"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
       
        SOAPElement nonceElem =
                factory.createElement("Nonce", wsu, usuuri);
        nonceElem.addTextNode("UGludHVyYXMgUmF2ZWxvIEFsYXJjb24=");
        nonceElem.addAttribute( QName.valueOf("Type"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
        
        
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis()));
        
        SOAPElement createElem = factory.createElement("Created", wsu, usuuri);
        createElem.addTextNode(timeStamp);
        
        tokenElem.addChildElement(userElem);
        tokenElem.addChildElement(pwdElem);
        tokenElem.addChildElement(nonceElem);
        tokenElem.addChildElement(createElem);
        securityElem.addChildElement(tokenElem);
       
        
      //  envelope.getHeader().detachNode();
    
        if (envelope.getHeader() != null) {
            envelope.getHeader().detachNode();
         }
        SOAPHeader header = envelope.addHeader();
        header.addChildElement(securityElem);
            //if no header, add one
            if (soapHeader == null){
            	
            //	soapHeader = header;
            }
           // System.out.println("SoapHeader:: " + soapHeader.getNamespaceURI());
            //get mac address
            String mac = getMACAddress();
	            
            //add a soap header, name as "mac address"
      //      QName qname = new QName("http://ws.mkyong.com/", "macAddress");
      //      SOAPHeaderElement soapHeaderElement = soapHeader.addHeaderElement(qname);

      //      soapHeaderElement.setActor(SOAPConstants.URI_SOAP_ACTOR_NEXT);
      //      soapHeaderElement.addTextNode(mac);
            soapMsg.saveChanges();

            //tracking
           
            soapMsg.writeTo( System.out);
          
	             
	   }catch(SOAPException e){
		System.err.println(e);
	   }catch(IOException e){
		System.err.println(e);
	   }
            
         }

	   //continue other handler chain
	   return true;
   }

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("Client : handleFault()......");
		return true;
	}

	@Override
	public void close(MessageContext context) {
		System.out.println();
		System.out.println("Client : close()......");
	}

	@Override
	public Set<QName> getHeaders() {
		final QName securityHeader = new QName(
	            "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
	            "Security",
	            "wsse");
		final HashSet headers = new HashSet();
        headers.add(securityHeader);
        
		return headers;
	}
	
   //return current client mac address
   private String getMACAddress(){
		
	InetAddress ip;
	StringBuilder sb = new StringBuilder();
		
	try {
			
		ip = InetAddress.getLocalHost();
		System.out.println("Current IP address : " + ip.getHostAddress());
		
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);

		byte[] mac = network.getHardwareAddress();
			
		System.out.print("Current MAC address : ");
			
		for (int i = 0; i < mac.length; i++) {
				
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				
		}
		System.out.println("ES esto:::" +sb.toString());
			
	} catch (UnknownHostException e) {
		
		e.printStackTrace();
		
	} catch (SocketException e){
			
		e.printStackTrace();
			
	}
		
	return sb.toString();
   }
	
}