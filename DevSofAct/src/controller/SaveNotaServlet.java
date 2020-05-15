package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SaveNotaServlet
 */
@WebServlet("/SaveNotaServlet")
public class SaveNotaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveNotaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int id;
		
		BufferedReader br = new BufferedReader (new InputStreamReader(request.getInputStream()));
		
		String json = "";
        if(br != null){
            json = br.readLine();
        }

        
        
        ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			
			FacturaJson fact = mapper.readValue(json, FacturaJson.class);
			
			String cliente= fact.getCliente();
			String subtotal = fact.getSubtotal();
			String total = fact.getTotal();
			Date fecha = fact.getFecha();
			
			
			
			SaveData sd = new SaveData();
			id = sd.SaveNota(fact);
			
			
			String iva = fact.getIva();
			 
			for (DetalleFactura deta : fact.getDetalleFactura()) {
				
				String cantidad = deta.getCantidad();
				String pvmId = deta.getPvmid();
				String tot = deta.getTotal();
				String descuento = deta.getDescuento();
				String subt = deta.getSubtotal();
				
				int idDetalle = sd.SaveNota(deta, id);
				
				
				
			}
			
			ReporteNotaServlet rns = new ReporteNotaServlet();
			rns.processRequest(request, response, id);
			
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	}
}
