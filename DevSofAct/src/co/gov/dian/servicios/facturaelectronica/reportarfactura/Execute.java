package co.gov.dian.servicios.facturaelectronica.reportarfactura;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import controller.GetData;
import controller.utilidades.Generadores;
import model.Empresa;
import model.Factura;
import model.ResolucionFacturacion;

public class Execute {

	public void run(Factura fact, int id, String tipo) {

		
		FacturaElectronicaPortNameService service = new FacturaElectronicaPortNameService();
		FacturaElectronicaPortName port = service.getFacturaElectronicaPortNameSoap11();
		EnvioFacturaElectronica factura = new EnvioFacturaElectronica();
		AcuseRecibo acuse = new AcuseRecibo();
		ReceivedInvoice recibida = new ReceivedInvoice();
		
		GetData gd = new GetData();
		Empresa empresa = new Empresa();
		Generadores gen = new Generadores();
		ResolucionFacturacion resolucion = gd.getResolucion("FE");

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String FORMATER = "yyyy-MM-dd'T'HH:mm:ss";
        
		DateFormat format = new SimpleDateFormat(FORMATER);
		        
		Date date = fact.getFacFechaFactura();
		XMLGregorianCalendar gDateFormatted1 = null;
		try {
			gDateFormatted1 = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(date));
		} catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String path = gd.getParametroDominio("PATH").getPadValor();
		
		//String directorioZip = "F:\\DATA\\face_f0053117601003B0234FA\\face_f0053117601003B0234FA.zip";
		//String directorioZip = "F:\\DATA\\face_f0053117601003B02342F\\face_f0053117601003B02342F.zip";
		
		String directorioZip = path + gen.generadorNombreArchivo(tipo, id)+"\\\\"+ gen.generadorNombreArchivo(tipo, id)+".zip" ;
  //"E:\\EMPRESA\\DIAN\\Facturas firmadas (1)\\Facturas firmadas\\FacturaFirmada.zip"; // path + gen.generadorNombreArchivo(tipo, id)+"\\"+ gen.generadorNombreArchivo(tipo, id)+".zip" ;
			
		System.out.println("El directorio del zip para el byte:: "+ directorioZip);
		
		File fich = new java.io.File(directorioZip); 
		
		while (!fich.exists()){
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("cuantas veces");
		}
		
		FileInputStream ficheroStream;
		try {
			ficheroStream = new FileInputStream(fich);
			byte document[] = new byte[(int)fich.length()]; 
			ficheroStream.read(document);
			
			while (document.length == 0)
			{
			System.out.println("El tamño del document despues de crearlo:::" + document.length);
			}
			
			
			factura.setDocument(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		empresa = gd.getEmpresa();
		
		factura.setNIT(empresa.getEmpNit());
		factura.setInvoiceNumber(resolucion.getRefPrefijo()+String.valueOf(Integer.parseInt(fact.getFacNumFactura().trim(), 16 )));
		
		System.out.println("la fecha de la factura: " + fact.getFacFechaFactura());
		factura.setIssueDate(gDateFormatted1);
		
		
		 final BindingProvider getProductsBP = (BindingProvider) port;
		    getProductsBP.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
		            "https://facturaelectronica.dian.gov.co/habilitacion/B2BIntegrationEngine/FacturaElectronica");
		
		System.out.println("WSLD loca:: " + service.getWSDLDocumentLocation());
		
		acuse = port.envioFacturaElectronica(factura);
		recibida = acuse.receivedInvoice;
		System.out.println("Response " + acuse.getResponse());
		System.out.println("Response " + acuse.getComments());
		System.out.println("Response " + acuse.getReceivedInvoice());
		System.out.println("Response " + acuse.getVersion());
		
		/*
		System.out.println("Response recibede " + recibida.getResponse());
		System.out.println("Response " + recibida.getComments());
		System.out.println("Response " + recibida.getNumeroFactura());
		System.out.println("Response " + recibida.getUUID());

		*/
		
	}
	
	

}
