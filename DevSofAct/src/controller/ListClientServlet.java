package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import model.Cliente;

/**
 * Servlet implementation class ListClientServlet
 */
@WebServlet("/ListClientServlet")
public class ListClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);		
		
		List<Cliente> cliente = null;		
			

				cliente = ObtenerCliente();
								
				PrintWriter  out = response.getWriter();
				out.println("<?xml version=\"1.0\"?>");
				out.print("<clientes>");
				for (Cliente clt : cliente) {
				out.println("<cliente_identifica id=\""+clt.getCliNumIdent() + "\"  >"+"</cliente_identifica>");
				out.println("<cliente_nombre id=\""+clt.getCliNombre() + "\"  >"+"</cliente_nombre>");
				}
				out.println("</clientes>");
				
				
				return;
		
	}
	
private List<Cliente> ObtenerCliente() {
		
		
		Session session =  HibernateUtil.getSession();
		CriteriaBuilder buidler = session.getCriteriaBuilder();
		CriteriaQuery <Cliente> criteria = buidler.createQuery(Cliente.class);
		Root<Cliente> cli = criteria.from(Cliente.class);
	    
		criteria = criteria.select(cli);
		
		
		List <Cliente> listCliente = session.createQuery(criteria).getResultList();
		
				
		return listCliente;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
