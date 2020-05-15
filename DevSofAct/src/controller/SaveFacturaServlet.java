//************************************************************************************************************
// GUARDAR FACTURAS
// 05/10/2018 BY JHUALC
//************************************************************************************************************

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
 * Servlet implementation class SaveFacturaServlet
 */
@WebServlet("/SaveFacturaServlet")
public class SaveFacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveFacturaServlet() {
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

			SaveData sd = new SaveData();
			id = sd.SaveData(fact);
						
			for (DetalleFactura deta : fact.getDetalleFactura()) {

				sd.SaveData(deta, id);
				Session sesion = HibernateUtil.getSessionFactory().openSession();
				sesion.beginTransaction();
				Transaction tx = sesion.getTransaction();
				ProductoValorMedida pv = sesion.find(ProductoValorMedida.class, Integer.parseInt(deta.getPvmid()));
				pv.setPvmCantidad(pv.getPvmCantidad() - Double.parseDouble(deta.getCantidad()));
				tx.commit();
				
			}
			
			ReporteFacturaServlet rfs = new ReporteFacturaServlet();
			rfs.processRequest(request, response, id);
						
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
