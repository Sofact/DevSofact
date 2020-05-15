package controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Users;


/**
 * Servlet implementation class SaveUsuarioServlet
 */
@WebServlet("/SaveUsuarioServlet")
public class SaveUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveUsuarioServlet() {
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

		String nombre = request.getParameter("nombre");
		String password = request.getParameter("pass");
		Users user = new Users();
		String hash = null;
		SaveData sd= new SaveData();
		
		MessageDigest sha256;
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
			sha256.update(password.getBytes("UTF-8"));
			byte[] digest = sha256.digest();
			StringBuffer sb=new StringBuffer();
			for(int i=0;i < digest.length;i++){
			    sb.append(String.format("%02x", digest[i]));
			}
			hash =sb.toString();
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		user.setUsername(nombre);
		user.setUserpassword(hash);
		sd.SaveData(user);
		
		
	}

}
