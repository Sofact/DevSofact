//************************************************************************************************************
// CARGA MUNICIPIO 
// FILTROS: DEPARTAMENTO
// 05/10/2018 BY JHUALC
//************************************************************************************************************


package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Municipio;

/**
 * Servlet implementation class MunicipioServlet
 */
@WebServlet("/MunicipioServlet")
public class MunicipioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MunicipioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		GetData gd = new GetData();
		int val = Integer.parseInt(request.getParameter("depto"));
				
		Municipio municipios = gd.getMunicipio(val);
		StringBuilder sb= new StringBuilder("");
			sb.append(municipios.getMuniId() + "-" + municipios.getMuniDescripcion()+":");
		
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
