//************************************************************************************************************
// CARGA LOS TIPOS DE PRODUCTO
//  FILTROS: MARCA_ID
// 05/10/2018 BY JHUALC
//************************************************************************************************************

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TipoProducto;

/**
 * Servlet implementation class TipoProductoServlet
 */
@WebServlet("/TipoProductoServlet")
public class TipoProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TipoProductoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		GetData gd = new GetData();
		int marca = Integer.parseInt(request.getParameter("marca"));
					
		List<TipoProducto> tipoProducto = gd.getTipoProductoByMarca(marca);
		StringBuilder sb = new StringBuilder("");
		
		for (TipoProducto tipo : tipoProducto) {
			sb.append(tipo.getTipId() + "-" + tipo.getTipDescripcion() + ":");
		}
			
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
