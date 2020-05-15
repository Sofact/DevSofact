package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.ProductoResumenView;

/**
 * Servlet implementation class GetProductoValorServlet
 */
@WebServlet("/GetProductoValorServlet")
public class GetProductoValorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProductoValorServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("El parametro recibido es::"+request.getParameter("idTipo")+ "::");
		
		String idMarca = request.getParameter("idMarca");
		String idTipo = request.getParameter("idTipo");
		String referencia = request.getParameter("referencia");
		
		if (idMarca.equals("Seleccione...")) {	
			idMarca = "0";
		}
		
		if (idTipo.equals("Seleccione...")) {
			idTipo = "0";
		}
		
		
		
		GetData gd = new GetData();
		List<ProductoResumenView>  producto = null;
		producto = gd.getResumenView(Integer.parseInt(idMarca), Integer.parseInt(idTipo), referencia);
		
		Gson gson = new Gson();
		
		String jsonProducto = gson.toJson(producto);
		
		System.out.println("El json:::::" + jsonProducto);
		
		response.getWriter().append(jsonProducto);
		
	}

}
