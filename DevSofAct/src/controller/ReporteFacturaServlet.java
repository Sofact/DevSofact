package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.gov.dian.servicios.facturaelectronica.reportarfactura.Execute;
//import co.gov.dian.www.servicios.facturaelectronica.ReportarFacturaq.EnvioFacturaElectronica;
//import co.gov.dian.www.servicios.facturaelectronica.ReportarFacturaq.FacturaElectronicaPortNameServiceLocator;
import controller.utilidades.Generadores;
import controller.utilidades.TestQRCode;
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

/**
 * Servlet implementation class ReporteFacturaServlet
 */
@WebServlet("/ReporteFacturaServlet")
public class ReporteFacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
   
	 
	protected void processRequest(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {

		Generadores generadores = new Generadores();
		Cliente cliente = new Cliente();
		Factura factura = new Factura();
		GetData gd = new GetData();
		TestQRCode qr= new TestQRCode();
		Execute execute = new Execute();
	//	FacturaElectronicaPortNameServiceLocator locator = new FacturaElectronicaPortNameServiceLocator();
	//	locator.getfacturaElectronicaPortNameSoap11WSDDServiceName();
		
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
			
			
			 execute.run(factura, id, "factura");
			
			 ServletContext context = request.getServletContext();
			 String path = context.getRealPath("admin/FacturaMediaCarta.jrxml");
			
			System.out.println("El path del contexto:::" +path);
			String sourceFileName = path;  //"C:\\\\Users\\\\jhual\\\\eclipse-workspace\\\\DevSofAct\\\\FacturaMediaCarta.jrxml";            
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
			String path1 = gd.getParametroDominio("PATH").getPadValor();
			String nombreArchivo = path1+ generadores.generadorNombreArchivo("factura", id) +".pdf"; 
			JasperExportManager.exportReportToPdfFile(print,  nombreArchivo );
			
			SendMail sm = new SendMail();
			
			sm.enviarConGMail(cliente.getCliEmail(), "Envío de facturación electronica", "A través de este correo estamos enviando su factura electrónica", nombreArchivo);
			
			
			
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
}

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//this.processRequest(request, response);
	}
	
}
