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
 * Servlet implementation class ExistenciasServlet
 */
@WebServlet("/ExistenciasServlet")
public class ExistenciasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExistenciasServlet() {
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
		// TODO Auto-generated method stub
		GetData gd = new GetData();
		int unmId = Integer.parseInt(request.getParameter("medida"));
		int proId = Integer.parseInt(request.getParameter("producto"));
		
		double resultado  = gd.getExistencias(proId, unmId);
		StringBuilder sb= new StringBuilder("");
		
		System.out.println("ekl resultado::::"+ resultado);
		//for(UnidadMedida unidad : medida) {
			sb.append(resultado);
		//}
		
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
	}

}
