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
import model.TipoProducto;

/**
 * Servlet implementation class UpdateTipoProductoServlet
 */
@WebServlet("/UpdateTipoProductoServlet")
public class UpdateTipoProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTipoProductoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
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
        
        System.out.println("EL JSONEl update:::" + json);
       
        ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			
			UpdateTipoProducto pvm = mapper.readValue(json, UpdateTipoProducto.class);
			
			for (DetalleTP deta : pvm.getDetalleTP())  {
				
				System.out.println("El tipID:::::::::" + deta.getTipId());
				
				int pvmId = Integer.parseInt(deta.getTipId().replace(" ",""));
				String valor = deta.getTipDesc();
								
					Session sesion = HibernateUtil.getSessionFactory().openSession();
					sesion.beginTransaction();
					Transaction tx = sesion.getTransaction();
					TipoProducto pv = sesion.find(TipoProducto.class, pvmId);
					pv.setTipDescripcion(valor); 
					
					tx.commit();
					
					
					
			}
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
