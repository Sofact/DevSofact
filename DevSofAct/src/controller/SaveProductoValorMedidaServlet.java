package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ProductoValorMedida;

/**
 * Servlet implementation class SaveProductoValorMedidaServlet
 */
@WebServlet("/SaveProductoValorMedidaServlet")
public class SaveProductoValorMedidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveProductoValorMedidaServlet() {
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
	BufferedReader br = new BufferedReader (new InputStreamReader(request.getInputStream()));
	
		SaveData sd = new SaveData();
		GetData gd = new GetData();
		
		
		String json = "";
        if(br != null){
            json = br.readLine();
        }
       
        ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			
			PVMPojo pvm = mapper.readValue(json, PVMPojo.class);
			
			for (DetallePVM deta : pvm.getDetallePVM()) {
				
				String referencia = deta.getReferencia();
				/*
				String descripcion = deta.getDescripcion();
				String marca = deta.getIdMarca();
				String tipo = deta.getIdTipo();*/
				String medida = deta.getIdMedida();
				String valor = deta.getValor();
				String cantidad = deta.getCantidad();
				
				System.out.println("lA CANTIDAD ES:::::" +  cantidad);
				
				
				int idDetalle = gd.getIdProducto(referencia);
				
				if(idDetalle < 0) {
					
					idDetalle = sd.SaveData(deta);
					
				}
				
				int idPVM = gd.getIProductoValorMedida(idDetalle, medida);
				
				if(idPVM >= 0) {
					
					
					Session sesion = HibernateUtil.getSessionFactory().openSession();
					sesion.beginTransaction();
					Transaction tx = sesion.getTransaction();
					ProductoValorMedida pv = sesion.find(ProductoValorMedida.class, idPVM);
					pv.setPvmValor(Double.parseDouble(valor));
					
					tx.commit();
				}
				
				if (idPVM < 0) {
				
					sd.SaveData(deta, idDetalle);
				}
				
			}
		
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
