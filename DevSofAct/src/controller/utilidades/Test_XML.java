package controller.utilidades;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import co.gov.dian.servicios.facturaelectronica.reportarfactura.Execute;
import controller.GetData;
import controller.NotaCreditoXMLGenerator;
import controller.XMLGenerator;
import model.Cliente;
import model.Factura;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class Test_XML {
	
	public static void main (String args[]) {
		
		Test_XML test = new Test_XML();
		
		test.ejecutar();
		
	}
	
	public void ejecutar() {
		
		Generadores generadores = new Generadores();
		Cliente cliente = new Cliente();
		Factura factura = new Factura();
		GetData gd = new GetData();
		TestQRCode qr= new TestQRCode();
		Execute execute = new Execute();
	//	FacturaElectronicaPortNameServiceLocator locator = new FacturaElectronicaPortNameServiceLocator();
	//	locator.getfacturaElectronicaPortNameSoap11WSDDServiceName();
		
		int id = 1005;
		
		factura = gd.getLastFactura(id);
		cliente = gd.getCliente(factura.getCliId());
		//qr.generador(id);
		
		try {
			Connection cn;
			Class.forName("org.postgresql.Driver");
			cn = (DriverManager.getConnection("jdbc:postgresql://localhost:5432/sofact", "postgres", "postgres"));
			
			Map<String,Object> parameters = new HashMap();
			XMLGenerator gen= new XMLGenerator();
			gen.generador(id);
			parameters.put("Factura_id", id);
			qr.generador(id, "factura");
			 byte[] document = null;
			 java.util.Calendar issueDate = null;
			
			
			// execute.run(factura, id, "factura");
			
			 
						
			String sourceFileName = "WebContent/WEB-INF/jasper/FacturaMediaCarta.jrxml";  //"C:\\\\Users\\\\jhual\\\\eclipse-workspace\\\\DevSofAct\\\\FacturaMediaCarta.jrxml";            
			File theFile = new File(sourceFileName);
			JasperDesign jasperDesign = JRXmlLoader.load(theFile);
			

			
			
			
			//Se compila el archivo a .jasper
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			//Aqui se llena el reporte (se ejecuta la consulta)
			JasperPrint print = new JasperPrint(); 
			print = JasperFillManager.fillReport(jasperReport, parameters, cn);
			/*byte[] pdfBytes = JasperExportManager.exportReportToPdf(print);
			
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "inline;filename=" + "test_factura" + ".pdf");
			response.getOutputStream().write(pdfBytes);
			*/
			
			File pdf = File.createTempFile("outputrasta", ".pdf");
			String nombreArchivo = "F:\\DATA\\"+ generadores.generadorNombreArchivo("factura", id) +".pdf"; 
			JasperExportManager.exportReportToPdfFile(print,  nombreArchivo );
			
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Se carga el archivo
 catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

}
