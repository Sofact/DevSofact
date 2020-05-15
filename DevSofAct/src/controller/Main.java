package controller;

import java.util.Date;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import model.Perfil;

public class Main {
   private static SessionFactory factory; 
   public static void main(String[] args) {
      
      try {
         factory = new Configuration().configure("/model/hibernate.cfg.xml").buildSessionFactory();
      } catch (Throwable ex) { 
         System.err.println("Failed to create sessionFactory object." + ex);
         throw new ExceptionInInitializerError(ex); 
      }
      
      Main ME = new Main();
      Date date= new Date();

      /* Add few employee records in database */
      Integer empID1 = ME.addPerfil(3, "perfil", 'A', 1, date);
     /* Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
      Integer empID3 = ME.addEmployee("John", "Paul", 10000);
*/
      /* List down all the employees */
  //    ME.listEmployees();

      /* Update employee's records */
 //     ME.updateEmployee(empID1, 5000);

      /* Delete an employee from the database */
 //     ME.deleteEmployee(empID2);

      /* List down new list of the employees */
 //     ME.listEmployees();
   }
   
   /* Method to CREATE an employee in the database */
   public Integer addPerfil(int id, String fname, char estado, int usuario, Date fechaCreacion){
      Session session =  HibernateUtil.getSession();
      Transaction tx = null;
      Integer perfID = null;
      
      try {
         tx = session.beginTransaction();
         Perfil perfil = new Perfil( id,  fname,  estado,  usuario,  fechaCreacion);
         perfID = (Integer) session.save(perfil); 
         tx.commit();
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }
      return perfID;
   }
   
   /* Method to  READ all the employees */
   /*
   public void listEmployees( ){
      Session session = factory.openSession();
      Transaction tx = null;
      
      try {
         tx = session.beginTransaction();
         List employees = session.createQuery("FROM Employee").list(); 
         for (Iterator iterator = employees.iterator(); iterator.hasNext();){
            Employee employee = (Employee) iterator.next(); 
            System.out.print("First Name: " + employee.getFirstName()); 
            System.out.print("  Last Name: " + employee.getLastName()); 
            System.out.println("  Salary: " + employee.getSalary()); 
         }
         tx.commit();
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }
   }
   
   /* Method to UPDATE salary for an employee */
   /*
   public void updateEmployee(Integer EmployeeID, int salary ){
      Session session = factory.openSession();
      Transaction tx = null;
      
      try {
         tx = session.beginTransaction();
         Employee employee = (Employee)session.get(Employee.class, EmployeeID); 
         employee.setSalary( salary );
		 session.update(employee); 
         tx.commit();
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }
   }
   
   /* Method to DELETE an employee from the records 
   public void deleteEmployee(Integer EmployeeID){
      Session session = factory.openSession();
      Transaction tx = null;
      
      try {
         tx = session.beginTransaction();
         Employee employee = (Employee)session.get(Employee.class, EmployeeID); 
         session.delete(employee); 
         tx.commit();
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace(); 
      } finally {
         session.close(); 
      }
   }*/
}