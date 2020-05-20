//************************************************************************************************************
// CARGA MEDIDA 
// FILTROS: PRODUCTO
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
import model.UnidadMedida;
/**
 * Servlet implementation class MedidaServlet
 */
@WebServlet("/MedidaServlet")
public class MedidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MedidaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("1.0-------");
		GetData gd = new GetData();
		int val = Integer.parseInt(request.getParameter("medida"));
		
		System.out.println("2.0-------");
		String tipo = request.getParameter("tipo");
		List<UnidadMedida> medida = null;
		
		System.out.println("3.0-------");
		
		if(tipo.equals("M")) {
			 medida = gd.getUnidadMedidaByProductoTipo(val);
		}
		
		if(tipo.equals("A")) {
			 medida = gd.getUnidadMedidaByProducto(val);
		}
		
		if(tipo.equals("T")) {
			System.out.println("4.0-------");
			int val2 = Integer.parseInt(request.getParameter("marca"));
			System.out.println("5.0-------");
			 medida = gd.getUnidadMedidaByTipoProducto(val, val2);
			 System.out.println("6.0-------");
		}
		
		//List<UnidadMedida> medida = gd.getUnidadMedidaByProducto(val);
		StringBuilder sb= new StringBuilder("");
		
		for(UnidadMedida unidad : medida) {
			sb.append(unidad.getUnmId() + "-" + unidad.getUnmDescripcion()+":");
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