package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalculaDescuento
 */
@WebServlet("/CalculaDescuento")
public class CalculaDescuento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalculaDescuento() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idProducto = Integer.parseInt(request.getParameter("produ"));
		int idMedida = Integer.parseInt(request.getParameter("medi"));
		double valor = 0.0;
		
		GetData gd = new GetData();
		
		
		valor= gd.getValorProductoByProductoMedida(idProducto, idMedida);
		
		System.out.println("El valor del query:::"+ valor);
		response.getWriter().print(valor);  
		
	}

}
