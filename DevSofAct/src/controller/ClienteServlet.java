package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClienteServlet extends HttpServlet{
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		String parametro = request.getParameter("parametro");
		
		if(parametro != null) {
			if(parametro.equals("sesion")) {
				
				getServletContext().getRequestDispatcher("/WebContent/home.jsp").forward(request, response);
			}else {
				
				getServletContext().getRequestDispatcher("/WebContent/home.jsp").forward(request, response);
			}
		}
	}

}
