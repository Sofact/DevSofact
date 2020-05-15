package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ClientServlet
 */
@WebServlet("/ClientServlet")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String parametro = request.getParameter("nombre_cliente");
		/*
		String tipoDoc = request.getParameter("tipo_doc");
		String numDoc = request.getParameter("identificacion");
		String direccion = request.getParameter("direccion");
		String telefono = request.getParameter("telefono");
		String email = request.getParameter("email");
		String tipoCliente = request.getParameter("tipo_cliente");
		*/
		
		if(parametro != null) {
			
				
				getServletContext().getRequestDispatcher("/Facturacion.jsp").forward(request, response);
		
		}
	}

}
