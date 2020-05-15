package controller;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Factura;
import model.TipoProducto;

/**
 * Servlet implementation class GetTipoProductoServlet
 */
@WebServlet("/GetTipoProductoServlet")
public class GetTipoProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTipoProductoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		GetData gd = new GetData();
		String TipDescripcion = request.getParameter("tipo");
			
		System.out.println("LA descripcion del tipo de producto::::" + TipDescripcion);
		
		List<TipoProducto>  tipoProducto = null;
		tipoProducto = gd.getTipoProductoAll(TipDescripcion);
		
		System.out.println("El tamaño del tipo de producto::::" + tipoProducto.get(0).getTipDescripcion());
		
	

		for (TipoProducto tipo : tipoProducto){
			
			response.getWriter().append(tipo.getTipId() + "|" + tipo.getTipDescripcion() + ":" );
			}
	}

}
