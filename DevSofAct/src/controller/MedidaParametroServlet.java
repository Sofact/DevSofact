package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import model.UnidadMedida;

/**
 * Servlet implementation class MedidaParametroServlet
 */
@WebServlet("/MedidaParametroServlet")
public class MedidaParametroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MedidaParametroServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT um FROM UnidadMedida um order by 2 ");
		
		
		List<UnidadMedida> medidas = query.getResultList();
		StringBuilder sb= new StringBuilder("");
		
		medidas.sort(Comparator.comparing(UnidadMedida::getUnmDescripcion));
		
		for(UnidadMedida medida : medidas) {
			sb.append(medida.getUnmId()+ "-" + medida.getUnmDescripcion()+":");
		}
		
		PrintWriter out = response.getWriter();
		out.write(sb.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
