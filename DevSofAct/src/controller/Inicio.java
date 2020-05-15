package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class Inicio {
	
	public static void main(String[] args) {
		
	//	XMLGenerator gen = new XMLGenerator();
	//	gen.generador(103);
		
	//	Convertidores con = new Convertidores();
	//	con.sumarHexa("fff");
		
	//	SendMail sm = new SendMail();
		
	//	sm.enviarConGMail("ravelo.adriana@gmail.com", "Prueba facturación electronica", "Primera prueba de envio de correo con factura electrónica");
		
		Connection cn;
		try {
			Class.forName("org.postgresql.Driver");
		
		cn = (DriverManager.getConnection("jdbc:postgresql://localhost:5432/sofact", "postgres", "postgres"));
		
	
		String sourceFileName =  "/src/controller/FacturaMediaCarta.jrxml";  //"C:\\\\Users\\\\jhual\\\\eclipse-workspace\\\\DevSofAct\\\\FacturaMediaCarta.jrxml";            
		File theFile = new File(sourceFileName);
		JasperDesign jasperDesign = JRXmlLoader.load(theFile);
		Map<String,Object> parameters = new HashMap();
		GetData gd = new GetData();

	
		
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
		String path = gd.getParametroDominio("PATH").getPadValor();
		
		JasperExportManager.exportReportToPdfFile(print, path + "\\salida.pdf");
		
		//response.flushBuffer();
	
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	

}
