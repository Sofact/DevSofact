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

import model.Producto;
import model.ProductoValorMedida;

/**
 * Servlet implementation class UpdateProductoValorServlet
 */
@WebServlet("/UpdateProductoValorServlet")
public class UpdateProductoValorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateProductoValorServlet() {
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
		BufferedReader br = new BufferedReader (new InputStreamReader(request.getInputStream()));
		
		SaveData sd = new SaveData();
		GetData gd = new GetData();
		
		
		String json = "";
        if(br != null){
            json = br.readLine();
        }
        
        System.out.println("EL JSON:::" + json);
       
        ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			
			UpdateValor pvm = mapper.readValue(json, UpdateValor.class);
			
			for (DetallePVMU deta : pvm.getDetallePVMU()) {
				 
				int pvmId = Integer.parseInt(deta.getPvmId());
				String valor = deta.getValor();
				String referencia = deta.getReferencia();
				String producto = deta.getProducto();
				String cantidad = deta.getCantidad();
				int proId = Integer.parseInt(deta.getProId());
								
					Session sesion = HibernateUtil.getSessionFactory().openSession();
					sesion.beginTransaction();
					Transaction tx = sesion.getTransaction();
					ProductoValorMedida pv = sesion.find(ProductoValorMedida.class, pvmId);
					Producto pro = sesion.find(Producto.class,  proId);
					pv.setPvmValor(Double.parseDouble(valor));
					pv.setPvmCantidad(Double.parseDouble(cantidad));
					pro.setProReferencia(referencia);
					pro.setProDescripcion(producto);
					tx.commit();
					
					
					
			}
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
