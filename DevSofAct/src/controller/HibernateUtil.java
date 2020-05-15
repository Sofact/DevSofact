package controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
	private static SessionFactory factory; 
	private static Session session;
	
	 static  {
		try {
			
			 factory = new Configuration().configure("/model/hibernate.cfg.xml").buildSessionFactory();
			 session = factory.openSession();
			 session.getTransaction().begin();
			
		}catch(Throwable e) {
			
			
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static SessionFactory getSessionFactory() {
		
		return factory;
	}
	
	public static Session getSession() {
		
		return session;
	}

}
