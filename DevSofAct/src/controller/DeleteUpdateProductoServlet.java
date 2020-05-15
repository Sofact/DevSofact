package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteUpdateProductoServlet
 */
@WebServlet("/DeleteUpdateProductoServlet")
public class DeleteUpdateProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUpdateProductoServlet() {
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

		int idPVM = Integer.parseInt(request.getParameter("pvmId"));
		int idProducto = Integer.parseInt(request.getParameter("proId"));
		
		System.out.println("Los aids::::" + idPVM+"::EL otro::"+ idProducto);
		
		DeleteData dd = new DeleteData();
		GetData gd = new GetData();		
			
		dd.deleteProductoValorMedida(idPVM);
		int cantidad = gd.getCantProductos(idProducto);
		
		if (cantidad == 0) {
			dd.deleteProducto(idProducto);
		}
		else {
			
			System.out.println("Queda otro registro");
			
		}
		
		
		
	}

}
