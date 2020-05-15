//************************************************************************************************************
// BUSCADOR FACTURA
//  IVA  SUBTOTAL Y TOTAL
// 05/10/2018 BY JHUALC
//************************************************************************************************************


package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import model.Cliente;
import model.Factura;
import model.FacturaDetalle;
import model.ResumenDetalles;

/**
 * Servlet implementation class BuscarFacturaServlet
 */
@WebServlet("/BuscarFacturaServlet")
public class BuscarFacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarFacturaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String numeroFactura= request.getParameter("factura");
				
		GetData gd = new GetData();
	//	List<Factura> fact = null;
		Cliente cliente = new Cliente();
	//	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Factura factura = gd.getFacturaNumero(numeroFactura);
	//	Date fechaIni= null;
	//	Date fechaFin= null;
		
		
		
		List<FacturaDetalle> facturaDetalle = null;
		
		List<String> resumen =  new ArrayList<String>();
	//	ResumenDetalles res = new ResumenDetalles();
		
	//	String idFactura = request.getParameter("idFactura");
	//	String cadena = null;
	
	
		cliente = gd.getCliente(factura.getCliId());
		facturaDetalle = gd.getFacturaDetalle(factura.getFacId());
		
		Gson gson = new Gson();
		
		for (int i =0; i< facturaDetalle.size(); i++){
			resumen.add(gd.getPVM(facturaDetalle.get(i).getPvmId()));
			//cadena =  gson.toJson(res);
			
		}
		
		
		
		String jsonFactura = gson.toJson(factura);
		String jsonCliente = gson.toJson(cliente);
		String jsonDetalle = gson.toJson(facturaDetalle);
		//String jsonProducto = gson.toJson(resumen);
		
		
		
		String resultado = jsonFactura +"|"+ jsonCliente +"|"+jsonDetalle + "|" + resumen;
		
		response.getWriter().append(resultado);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		GetData gd = new GetData();
		List<Factura> fact = null;
		Cliente client = new Cliente();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		String nombre = request.getParameter("nombre");
		String identificacion = request.getParameter("identificacion");
		String factura = request.getParameter("factura");
		String fechaCadenaIni = request.getParameter("fechaIni");
		String fechaCadenaFin = request.getParameter("fechaFin");
		Date fechaIni= null;
		Date fechaFin= null;
		
		
		try {
			 fechaIni = dateFormat.parse(fechaCadenaIni);
			 fechaFin = dateFormat.parse(fechaCadenaFin);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fechaFin.setHours(23);
		fechaFin.setMinutes(59);
		fechaFin.setSeconds(59);
		
		fact= gd.getFacturaNotas(nombre, identificacion, factura, fechaIni, fechaFin);
		
		
		
		for (Factura fac : fact){
			
			client= gd.getCliente(fac.getCliId());
			String nombreCompleto =  client.getCliNombre() + " " + client.getCliSegundoNombre() + " " + client.getCliApellidos();
			
			
		response.getWriter().append(fac.getFacNumFactura() + "|" + dateFormat2.format(fac.getFacFechaFactura()) + "|" + nombreCompleto + "|" +fac.getFacTotal() +"|"+fac.getFacId()+":" );
		}
		//doGet(request, response);
	}
	
	
}
