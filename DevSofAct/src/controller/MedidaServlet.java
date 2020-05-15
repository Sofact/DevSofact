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

		GetData gd = new GetData();
		int val = Integer.parseInt(request.getParameter("medida"));
		
		List<UnidadMedida> medida = gd.getUnidadMedidaByProducto(val);
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
