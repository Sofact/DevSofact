//************************************************************************************************************
// CARGA PRODUCTO 
// FILTROS: MARCA, TIPO_PRODUCTO
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

import model.Producto;

/**
 * Servlet implementation class ProductoServlet
 */
@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		GetData gd = new GetData();
		int tipo_prod = Integer.parseInt(request.getParameter("tipo_prod"));
		int marca = Integer.parseInt(request.getParameter("marca"));

		List<Producto> producto = gd.getProductoByMarcaTipo(tipo_prod, marca);
		StringBuilder sb = new StringBuilder("");
		
		for(Producto pro : producto) {
			sb.append(pro.getProId() + "-" + pro.getProDescripcion()+":");
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
