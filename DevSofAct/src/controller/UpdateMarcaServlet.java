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

import model.Marca;
import model.TipoProducto;

/**
 * Servlet implementation class UpdateMarcaServlet
 */
@WebServlet("/UpdateMarcaServlet")
public class UpdateMarcaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMarcaServlet() {
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
        
        System.out.println("EL JSONEl2 update:::" + json);
       
        ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			
			UpdateMarca upm = mapper.readValue(json, UpdateMarca.class);
			
			for (DetalleMarca deta : upm.getDetalleMarca())  {
				
				System.out.println("El tipID:::::::::" + deta.getMarId());
				
				int marId = Integer.parseInt(deta.getMarId().replace(" ",""));
				String valor = deta.getMarDesc();
								
					Session sesion = HibernateUtil.getSessionFactory().openSession();
					sesion.beginTransaction();
					Transaction tx = sesion.getTransaction();
					Marca pv = sesion.find(Marca.class, marId);
					pv.setMarDescripcion(valor); 
					
					tx.commit();
					
					
					
			}
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
