package controller;


import org.hibernate.Session;
import model.ProductoValorMedida;
import model.TipoProducto;
import model.Marca;
import model.Producto;

public class DeleteData {

	
	Session sesion = HibernateUtil.getSessionFactory().openSession();
	
	public void deleteProducto(int proId) {
		 
		 Producto pro = sesion.find(Producto.class, proId);

		 sesion.getTransaction().begin();
		 sesion.remove(pro);
		 sesion.getTransaction().commit();
		 
	}
	 
	public void deleteProductoValorMedida(int pvm_id) {
		 
		 ProductoValorMedida pvm = sesion.find(ProductoValorMedida.class, pvm_id);

		 sesion.getTransaction().begin();
		 sesion.remove(pvm);
		 sesion.getTransaction().commit();
		 
	}

	public void deleteTipoProducto(int tip_id) {
		
		TipoProducto tp = sesion.find(TipoProducto.class, tip_id);
		
		sesion.getTransaction().begin();
		sesion.remove(tp);
		sesion.getTransaction().commit();
		
	}
	
	public void deleteMarca(int mar_id) {
		
		Marca mar = sesion.find(Marca.class, mar_id);
		
		sesion.getTransaction().begin();
		sesion.remove(mar);
		sesion.getTransaction().commit();
	}
}
