//************************************************************************************************************
// DATOS  CLIENTE SERVLET
// CARGA DE TODOS LOS ATRIBUTOS DEL CLIENTE
// 05/10/2018 BY JHUALC
//************************************************************************************************************


package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import model.Departamento;
import model.Municipio;

/**
 * Servlet implementation class DatosClienteServlet
 */
@WebServlet("/DatosClienteServlet")
public class DatosClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatosClienteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType(CONTENT_TYPE);
		GetData gd = new GetData();
		String param = request.getParameter("userdata");
				
			if(param != null) {
				
				Cliente clt = gd.getCliente(Integer.parseInt(param));
				Municipio muni =  gd.getMunicipio(clt.getMuniId());
				Departamento depa = gd.getDepartamento(muni.getDepId());
				
				PrintWriter  out = response.getWriter();
				out.println("<?xml version=\"1.0\"?>");
				out.print("<clientes>");
				out.println("<cliente_nombre id=\""+clt.getCliNombre() + "\"  >"+"</cliente_nombre>");
				out.println("<cliente_identifica id=\""+clt.getCliNumIdent() + "\"  >"+"</cliente_identifica>");
				out.println("<cliente_direccion id=\""+clt.getCliDireccion() + "\"  >"+"</cliente_direccion>");
				out.println("<cliente_telefono id=\""+clt.getCliTelefono() + "\"  >"+"</cliente_telefono>");
				out.println("<cliente_email id=\""+clt.getCliEmail() + "\"  >"+"</cliente_email>");
				out.println("<tipo_ident id=\""+clt.getCliTipoIdent() + "\"  >"+"</tipo_ident>");
				out.println("<cli_id id=\""+clt.getCliId() + "\"  >"+"</cli_id>");
				out.println("<segundo_nombre id=\""+clt.getCliSegundoNombre() + "\"  >"+"</segundo_nombre>");
				out.println("<apellidos id=\""+clt.getCliApellidos() + "\"  >"+"</apellidos>");
				out.println("<municipio id=\""+muni.getMuniDescripcion() + "\"  >"+"</municipio>");
				out.println("<departamento id=\""+depa.getDepDescripcion() + "\"  >"+"</departamento>");
				out.println("</clientes>");
				
				return;
			}
			
			response.setStatus(response.SC_NO_CONTENT);
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		doGet(request, response);
	}

}
