package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import model.Departamento;


/**
 * Servlet implementation class DepartamentoServlet
 */
@WebServlet("/DepartamentoServlet")
public class DepartamentoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartamentoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT d FROM Departamento d ");
		
		
		List<Departamento> departamento = query.getResultList();
		StringBuilder sb= new StringBuilder("");
		
		for(Departamento depto : departamento) {
			sb.append(depto.getDepId() + "-" + depto.getDepDescripcion()+":");
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
