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
import model.TipoProducto;


/**
 * Servlet implementation class TipoProductoParametroServlet
 */
@WebServlet("/TipoProductoParametroServlet")
public class TipoProductoParametroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TipoProductoParametroServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT tp FROM TipoProducto tp");
		
		
		List<TipoProducto> tipoProductos = query.getResultList();
		StringBuilder sb= new StringBuilder("");
		
		tipoProductos.sort(Comparator.comparing(TipoProducto::getTipDescripcion));
		
		for(TipoProducto tipoProducto : tipoProductos) {
			sb.append(tipoProducto.getTipId() + "-" + tipoProducto.getTipDescripcion() +":");
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
