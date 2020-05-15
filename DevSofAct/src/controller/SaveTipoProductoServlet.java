package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class SaveTipoProductoServlet
 */
@WebServlet("/SaveTipoProductoServlet")
public class SaveTipoProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveTipoProductoServlet() {
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
       
        ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			
			ParametroPojo prod = mapper.readValue(json, ParametroPojo.class);
			
			for (Parametro tipo : prod.getParametro()) {
				
				
				String descripcion = tipo.getParamDescripcion(); 
				
				
				int idDetalle = gd.getTipoProductoDesc(descripcion);
				
				if(idDetalle < 0) {
					idDetalle = sd.SaveData(tipo);		
				}	
			}
	
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
