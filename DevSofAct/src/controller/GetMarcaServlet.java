package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Marca;


/**
 * Servlet implementation class GetMarcaServlet
 */
@WebServlet("/GetMarcaServlet")
public class GetMarcaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMarcaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		GetData gd = new GetData();
		String marca = request.getParameter("marca");
			
		System.out.println("LA descripcion del tipo de producto::::" + marca);
		
		List<Marca>  marcas = null;
		marcas = gd.getMarcaAll(marca);
		
		System.out.println("El tamaño del tipo de producto::::" + marcas.get(0).getMarDescripcion());
			

		for (Marca tipo : marcas){
			
			response.getWriter().append(tipo.getMarId() + "|" + tipo.getMarDescripcion() + ":" );
			}
		
	}

}
