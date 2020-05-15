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
 * Servlet implementation class ReporteNotaServlet
 */
@WebServlet("/ReporteNotaServlet")
public class ReporteNotaServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReporteNotaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	 
	protected void processRequest(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {

		Generadores generadores = new Generadores();
		Cliente cliente = new Cliente();
		Factura factura = new Factura();
		GetData gd = new GetData();
		TestQRCode qr= new TestQRCode();
		Execute execute = new Execute();
		
		factura = gd.getLastFactura(id);
		cliente = gd.getCliente(factura.getCliId());
		
		try {
			Connection cn;
			Class.forName("org.postgresql.Driver");
			cn = (DriverManager.getConnection("jdbc:postgresql://localhost:5432/sofact", "postgres", "postgres"));
			
			Map<String,Object> parameters = new HashMap();

			NotaCreditoXMLGenerator gen= new NotaCreditoXMLGenerator();
			gen.generador(id);
			parameters.put("Factura_id", id);
			qr.generador(id, "credito");
			
			 execute.run(factura, id, "credito");
			 
			 ServletContext context = request.getServletContext();
			 String path = context.getRealPath("admin/NotaCredito.jrxml");
			
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
			String nombreArchivo = path1 + generadores.generadorNombreArchivo("credito", id) +".pdf"; 
			JasperExportManager.exportReportToPdfFile(print,  nombreArchivo );
			
			SendMail sm = new SendMail();
			
			sm.enviarConGMail(cliente.getCliEmail(), "Prueba Nota Credito electronica", "Primera prueba de envio de correo con Nota Credito electrónica", nombreArchivo);
			
		
			
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
		 
		protected void processRequestDebito(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {

			Generadores generadores = new Generadores();
			Cliente cliente = new Cliente();
			Factura factura = new Factura();
			GetData gd = new GetData();
			TestQRCode qr= new TestQRCode();
			Execute execute = new Execute();
			
			factura = gd.getLastFactura(id);
			cliente = gd.getCliente(factura.getCliId());
			
			try {
				Connection cn;
				Class.forName("org.postgresql.Driver");
				cn = (DriverManager.getConnection("jdbc:postgresql://localhost:5432/sofact", "postgres", "postgres"));
				
				Map<String,Object> parameters = new HashMap();

				NotaDebitoXMLGenerator gen= new NotaDebitoXMLGenerator();
				gen.generador(id);
				parameters.put("Factura_id", id);
				qr.generador(id, "debito");
				
				 execute.run(factura, id, "debito");
				 
				 ServletContext context = request.getServletContext();
				 String path1 = context.getRealPath("admin/NotaDebito.jrxml");
				String path = gd.getParametroDominio("PATH").getPadValor();
				
				
				String sourceFileName = path1;  //"C:\\\\Users\\\\jhual\\\\eclipse-workspace\\\\DevSofAct\\\\FacturaMediaCarta.jrxml";            
				File theFile = new File(sourceFileName);
				JasperDesign jasperDesign = JRXmlLoader.load(theFile);
				
				
				
				//Se compila el archivo a .jasper
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				
				//Aqui se llena el reporte (se ejecuta la consulta)
				JasperPrint print = new JasperPrint(); 
				print = JasperFillManager.fillReport(jasperReport, parameters, cn);
				
				
				String nombreArchivo = path + generadores.generadorNombreArchivo("debito", id) +".pdf"; 
				JasperExportManager.exportReportToPdfFile(print,  nombreArchivo );
				
				SendMail sm = new SendMail();
				
				sm.enviarConGMail(cliente.getCliEmail(), "Prueba de Nota Debito", "Primera prueba de envio de correo con Nota Debito electrónica", nombreArchivo);
				
			
				
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
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
