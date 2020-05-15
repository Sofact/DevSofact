package controller;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import controller.utilidades.Generadores;
import model.Cliente;
import model.Factura;
import model.FacturaDetalle;
import model.Marca;
import model.Producto;
import model.ProductoValorMedida;
import model.ResolucionFacturacion;
import model.TipoProducto;
import model.UnidadMedida;
import model.Users;


public class SaveData {

	
	public int SaveData(FacturaJson fact) {

		Factura factura = new Factura();
		GetData gd = new GetData();
		Date date = new Date();
		ResolucionFacturacion resol = gd.getResolucion("FE");
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		Generadores gen = new Generadores();

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
		
		    tx.commit();
		}
		
		factura.setCliId(Integer.parseInt(fact.getCliente()));
		factura.setFacFecaPago(fact.getFecha());
		factura.setFacFechaFactura(timeStamp);
		factura.setFacIva(Double.parseDouble(fact.getIva()));
		factura.setFacNumFactura(gen.generadorConsecutivo("F"));
		factura.setFacNumOriginal(resol.getRefPrefijo()+String.valueOf(Integer.parseInt(factura.getFacNumFactura().trim(), 16 )));
		factura.setFacSubTotal(Double.parseDouble(fact.getSubtotal()));
		factura.setFacTotal(Double.parseDouble(fact.getTotal()));
		factura.setFechaCreacion(date);
		factura.setUepId(1);
		factura.setRefId(1);
		factura.setFacTipo("F");
		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(factura);
		tx.commit();
			
		String path = gd.getParametroDominio("PATH").getPadValor();
		factura.setFacPath(path + gen.generadorNombreArchivo("factura", factura.getFacId()));
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(factura);
		tx.commit();
		
		
		return factura.getFacId();
	}
	

	public int SaveData(FacturaJson fact, String tipo) {

		Factura factura = new Factura();
		GetData gd = new GetData();
		Date date = new Date();
		ResolucionFacturacion resol = gd.getResolucion(tipo); 
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		Generadores gen = new Generadores();

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
		
		    tx.commit();
		}
		
		factura.setCliId(Integer.parseInt(fact.getCliente()));
		factura.setFacFecaPago(fact.getFecha());
		factura.setFacFechaFactura(timeStamp);
		factura.setFacIva(Double.parseDouble(fact.getIva()));
		factura.setFacNumFactura(gen.generadorConsecutivoPos(tipo));
		factura.setFacNumOriginal(resol.getRefPrefijo()+String.valueOf(Integer.parseInt(factura.getFacNumFactura().trim(), 16 )));
		factura.setFacSubTotal(Double.parseDouble(fact.getSubtotal()));
		factura.setFacTotal(Double.parseDouble(fact.getTotal()));
		factura.setFechaCreacion(date);
		factura.setUepId(1);
		factura.setRefId(gd.getResolucionFactura(tipo));
		factura.setFacTipo(tipo);
		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(factura);
		tx.commit();
			
		String path = gd.getParametroDominio("PATH").getPadValor();
		factura.setFacPath(path + gen.generadorNombreArchivo("factura", factura.getFacId()));
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(factura);
		tx.commit();
		
		
		return factura.getFacId();
	}
	
	public int SaveNota(FacturaJson fact) {

		Factura factura = new Factura();
		Date date = new Date();
		GetData gd = new GetData();
		ResolucionFacturacion resol = gd.getResolucion("FE");
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		Generadores gen = new Generadores();

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
		
		    tx.commit();
		}
		
		factura.setCliId(Integer.parseInt(fact.getCliente()));
		factura.setFacFecaPago(fact.getFecha());
		factura.setFacFechaFactura(timeStamp);
		factura.setFacIva(Double.parseDouble(fact.getIva()));
		factura.setFacNumFactura(gen.generadorConsecutivo("F"));
		factura.setFacNumOriginal(resol.getRefPrefijo()+String.valueOf(Integer.parseInt(factura.getFacNumFactura().trim(), 16 )));
		factura.setFacSubTotal(Double.parseDouble(fact.getSubtotal()));
		factura.setFacTotal(Double.parseDouble(fact.getTotal()));
		factura.setFechaCreacion(date);
		factura.setUepId(1);
		factura.setRefId(1);
		factura.setFacTipo("F");
		factura.setFacNotaNumero(fact.getfacturaNum());
		factura.setFacObservacion(fact.getObservacion());
		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(factura);
		tx.commit();
			
		
		String path = gd.getParametroDominio("PATH").getPadValor();
		factura.setFacPath(path + gen.generadorNombreArchivo("credito", factura.getFacId()));
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(factura);
		tx.commit();
		
		return factura.getFacId();
	}
	
	public int SaveNotaDebito(FacturaJson fact) {

		Factura factura = new Factura();
		Date date = new Date();
		GetData gd = new GetData();
		ResolucionFacturacion resol = gd.getResolucion("FE");
		Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
		Generadores gen = new Generadores();

		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
		
		    tx.commit();
		}
		
		factura.setCliId(Integer.parseInt(fact.getCliente()));
		factura.setFacFecaPago(fact.getFecha());
		factura.setFacFechaFactura(timeStamp);
		factura.setFacIva(Double.parseDouble(fact.getIva()));
		factura.setFacNumFactura(gen.generadorConsecutivo("F"));
		factura.setFacNumOriginal(resol.getRefPrefijo()+String.valueOf(Integer.parseInt(factura.getFacNumFactura().trim(), 16 )));
		factura.setFacSubTotal(Double.parseDouble(fact.getSubtotal()));
		factura.setFacTotal(Double.parseDouble(fact.getTotal()));
		factura.setFechaCreacion(date);
		factura.setUepId(1);
		factura.setRefId(1);
		factura.setFacTipo("F");
		factura.setFacNotaNumero(fact.getfacturaNum());
		factura.setFacObservacion(fact.getObservacion());
		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(factura);
		tx.commit();
			

		String path = gd.getParametroDominio("PATH").getPadValor();
		factura.setFacPath(path + gen.generadorNombreArchivo("debito", factura.getFacId()));
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(factura);
		tx.commit();
		
		return factura.getFacId();
	}

	public int SaveData(DetalleFactura deta, int id) {
				
		FacturaDetalle fd = new FacturaDetalle();
		
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			
		    tx.commit();
		    
		}
		
		fd.setFadCantidad(Double.parseDouble(deta.getCantidad()));
		fd.setFacId(id);
		fd.setPvmId(Integer.parseInt(deta.getPvmid()));
		fd.setFadDescuento(Double.parseDouble(deta.getDescuento()));
		fd.setFadSubtotal(Double.parseDouble(deta.getTotal()));
		fd.setFadValorUnitario(Double.parseDouble(deta.getSubtotal()));
		
		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(fd);
		tx.commit();
		
		return fd.getFadId();
	}
	
	public int SaveNota(DetalleFactura deta, int id) {
		
		FacturaDetalle fd = new FacturaDetalle();
		
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			
		    tx.commit();
		   
		}
		
		fd.setFadCantidad(Double.parseDouble(deta.getCantidad()));
		fd.setFacId(id);
		fd.setPvmId(Integer.parseInt(deta.getPvmid()));
		//fd.setFadDescuento(Double.parseDouble(deta.getDescuento()));
		fd.setFadSubtotal(Double.parseDouble(deta.getTotal()));
		fd.setFadValorUnitario(Double.parseDouble(deta.getSubtotal()));
		
		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(fd);
		tx.commit();
		
		return fd.getFadId();
	}


	public int SaveData(Cliente cliente) throws  ConstraintViolationException {

		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			
		    tx.commit();
		   
		}
		
		
		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(cliente);
		tx.commit();
	//	throw new ConstraintViolationException("El nit ya se enceuntra registrado", null, null);
		
		return cliente.getCliId();		
	}

	public int SaveData(DetallePVM deta) {
		
		Producto p = new Producto();
		GetData gd = new GetData();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		System.out.println("El ide de la marca:::"+ deta.getIdMarca());
		
		p.setMarca(gd.getMarca(deta.getIdMarca()));
		p.setTipoProducto(gd.getTipoProducto(deta.getIdTipo()));
		p.setFechaCreacion(date);
		p.setProDescripcion(deta.getDescripcion());
		p.setProEstado('A');
		p.setUepId(1);
		p.setProReferencia(deta.getReferencia());
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			
		    tx.commit();
		   
		}

		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(p);
		tx.commit();
		
		return p.getProId();
		
	}

	public void SaveData(DetallePVM deta, int idDetalle) {
		
		ProductoValorMedida pvm = new ProductoValorMedida();
		GetData gd = new GetData();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		pvm.setProducto(gd.getProducto(idDetalle));
		pvm.setFechaCreacion(date);
		pvm.setPvmEstado('A');
		pvm.setPvmValor(Double.parseDouble(deta.getValor()));
		pvm.setTpcId(1);
		pvm.setUepId(1);
		pvm.setUnidadMedida(gd.getUnidadMedida(Integer.parseInt(deta.getIdMedida())));
		pvm.setPvmCantidad(Double.parseDouble(deta.getCantidad()));
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		   }

		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(pvm);
		tx.commit();
		
		
	}

	public int SaveData (Parametro deta) {

		TipoProducto tp = new TipoProducto();
		Date date = new Date();
		
		tp.setTipDescripcion(deta.getParamDescripcion());
		tp.setFechaCreacion(date);
		tp.setTipEstado('A');
		tp.setUepId(1);
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		   }

		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(tp);
		tx.commit();
		return 0;
	}

	public int SaveData(Parametro tipo, String string) {
		Marca m = new Marca();
		Date date = new Date();
		
		m.setMarDescripcion(tipo.getParamDescripcion());
		m.setFechaCreacion(date);
		m.setMarEstado('A');
		m.setUepId(1);
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		   }

		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(m);
		tx.commit();
		return 0;
	}

	public int SaveData(Parametro tipo, int id) {
		
		UnidadMedida m = new UnidadMedida();
		Date date = new Date();
		
		m.setUnmDescripcion(tipo.getParamDescripcion());
		m.setFechaCreacion(date);
		m.setUnmEstado('A');
		m.setUepId(1);
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		   }

		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(m);
		tx.commit();
		return 0;
	}
	
		public int SaveData(Users users) {
		
		Users user  = new Users();
		Date date = new Date();
		
		user.setUsername(users.getUsername());
		user.setUserpassword(users.getUserpassword());
		
		
		Session session = HibernateUtil.getSession();
		Transaction tx = session.getTransaction();
		if (tx.getStatus().equals(TransactionStatus.ACTIVE)) { 
			tx.commit();
		   }

		
		session.beginTransaction();
		tx = session.getTransaction();
		session.save(user);
		tx.commit();
		return 0;
	}


}
