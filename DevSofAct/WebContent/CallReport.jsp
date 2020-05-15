<%@page import="net.sf.jasperreports.engine.design.JRDesignQuery"%>
<%@page import="net.sf.jasperreports.engine.xml.JRXmlLoader"%>
<%@page import="net.sf.jasperreports.engine.design.JasperDesign"%>
<%@page import="net.sf.jasperreports.view.JasperViewer"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"%> 
<%@page pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
Connection conn=null;
try {
//Connecting to the MySQL database

Class.forName("org.postgresql.Driver");
conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sofact", "postgres" ,"postgres");

String report="C://Users//jhual//eclipse-workspace//DevSofAct//FacturaMediaCarta.jrxml";

JasperReport jasperreport=JasperCompileManager.compileReport(report);
JasperPrint jp=JasperFillManager.fillReport(jasperreport, null,conn);
JasperViewer.viewReport(jp);

}
catch(Exception e)
{
out.println(e +"Esto es lo caque slkdflas");

e.printStackTrace();

}

%>
</body>
</html>