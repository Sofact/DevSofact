package controller;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class BuscarNota
 */
@WebServlet("/BuscarNota")
public class BuscarNota extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuscarNota() {
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
		Factura factura = new Factura();
		List<FacturaDetalle> facturaDetalle = null;
		Cliente cliente = new Cliente();
		List<String> resumen =  new ArrayList<String>();
		ResumenDetalles res = new ResumenDetalles();
		
		String idFactura = request.getParameter("idFactura");
		String cadena = null;
	
		factura = gd.getLastFactura(Integer.parseInt(idFactura));
		cliente = gd.getCliente(factura.getCliId());
		facturaDetalle = gd.getFacturaDetalle(factura.getFacId());
		
		Gson gson = new Gson();
		
		for (int i =0; i< facturaDetalle.size(); i++){
			resumen.add(gd.getPVM(facturaDetalle.get(i).getPvmId()));
			
		}
		
		
		
		String jsonFactura = gson.toJson(factura);
		String jsonCliente = gson.toJson(cliente);
		String jsonDetalle = gson.toJson(facturaDetalle);
		//String jsonProducto = gson.toJson(resumen);
		
		
		
		String resultado = jsonFactura +"|"+ jsonCliente +"|"+jsonDetalle + "|" + resumen;
		
		response.getWriter().append(resultado);
	}

}
