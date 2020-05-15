package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;

import model.Cliente;

/**
 * Servlet implementation class SaveClientServlet
 */
@WebServlet("/SaveClientServlet")
public class SaveClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,  ConstraintViolationException {
		
		
		System.out.println("Entro al save");
		GetData gd = new GetData();
		Cliente cl = new Cliente();
		//cl = gd.getIdCliente(request.getParameter("identificacion"));
		
		//System.out.println("El id del cliente a ver que tiente" + cl.getCliId());
		System.out.println("La identificacion que llega" + request.getParameter("primerNombre"));
		
		if (gd.getIdCliente(request.getParameter("identificacion")) == 0) {
		
		System.out.println("Ingreso al servlet cliente");
		Cliente cliente = new Cliente();
		Date date = new Date();
		String tipoID = request.getParameter("tipoIdent");
		int id_tipo= 0;
		
		switch (tipoID) {
		
			case "NIT":
				id_tipo = 6;
				break;
				
			case "CC":
				id_tipo = 3;
				break;
				
			case "CE":
				id_tipo = 5;
				break;
				
			case "TI":
				id_tipo = 2;
				break;
			
		}
		

		System.out.println("Ingreso al servlet cliente");
		
		cliente.setCliNombre( request.getParameter("primerNombre"));
		cliente.setCliSegundoNombre(request.getParameter("segundoNombre"));
		cliente.setCliApellidos(request.getParameter("apellido"));
		cliente.setCliDireccion(request.getParameter("direccion"));
		cliente.setCliEmail(request.getParameter("email"));
		cliente.setCliNumIdent(request.getParameter("identificacion"));
		cliente.setMuniId(Integer.parseInt(request.getParameter("municipio")));
		cliente.setCliTipoIdent(id_tipo);//request.getParameter("tipoIdent"));
		cliente.setCliTelefono(request.getParameter("telefono"));
		cliente.setFechaCreacion(date);
		cliente.setUepId(1);
		cliente.setTpcId(1);
		cliente.setUserModifica(0);
		String dis = request.getParameter("dispatcher");
		String dispatcher = "1"; //request.getParameter("dispatcher");
		
		SaveData sc = new SaveData();
		sc.SaveData(cliente);
		
		if (!dispatcher.equals("0") && !dis.equals("0")){
			getServletContext().getRequestDispatcher("/NuevoCliente.jsp").forward(request, response);
		}
		if (dis.equals("0")) {
			
			//GetData gd = new GetData();
			System.out.println("El id del cliente"+cliente.getCliId());
			PrintWriter out = response.getWriter();
			out.print(cliente.getCliId());
			return;
		}
		
	}else  {
		
		System.out.println("Entro por el else");
	//	getServletContext().getRequestDispatcher("/NuevoClienteExiste.jsp").forward(request, response);
		
	}
	}
	
}
