//************************************************************************************************************
// VALOR PRODUCTO SERVLET
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
import model.ProductoValorMedida;


/**
 * Servlet implementation class ValorProductoServlet
 */
@WebServlet("/ValorProductoServlet")
public class ValorProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ValorProductoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		GetData gd = new GetData();
		int pro = Integer.parseInt(request.getParameter("produ"));
		int med = Integer.parseInt(request.getParameter("medi"));
		
		List<ProductoValorMedida> valores = gd.getProductoValMedidaByProductoMedida(pro, med);
		List<Producto> productos = gd.getProductoByIdMedida(pro, med);

		StringBuilder sb = new StringBuilder("");
			
		for(ProductoValorMedida val : valores) {
			for(Producto prod : productos) {
			sb.append(val.getPvmValor() +"-" + prod.getProDescripcion() + "-"+ prod.getProReferencia()+"-"+val.getPvmId()+":");
			}
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
