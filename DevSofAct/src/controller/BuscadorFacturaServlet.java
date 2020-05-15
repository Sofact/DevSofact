package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.DetalleFacturaResumen;

/**
 * Servlet implementation class BuscadorFacturaServlet
 */
@WebServlet("/BuscadorFacturaServlet")
public class BuscadorFacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscadorFacturaServlet() {
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
		System.out.println("La fecha recibida en del buscador "+request.getParameter("fechaIni"));
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaIni= null;
		Date fechaFin= null;
		String fechaCadenaIni = request.getParameter("fechaIni");
		String fechaCadenaFin = request.getParameter("fechaFin");
		
		System.out.println("La fehca previo al cambio::::" + fechaCadenaIni);
		
		try {
			
			
			 fechaIni = dateFormat.parse(fechaCadenaIni);
			 fechaFin = dateFormat.parse(fechaCadenaFin);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("La fehca antes de enviarse::::::"  + fechaIni);
		
		GetData gd = new GetData();
		List<DetalleFacturaResumen> detalle = null;
		
		detalle = gd.getDetalleFacturaResumen("P", fechaCadenaIni, fechaCadenaFin);
		
		Gson gson = new Gson();
		
		String jsonDetalle = gson.toJson(detalle);
		
		System.out.println("El jsonDetalle:::::" + jsonDetalle);
		
		response.getWriter().append(jsonDetalle);
				
	}

}