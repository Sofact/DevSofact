package controller;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;
import model.Cliente;
import model.Departamento;
import model.DetalleFacturaResumen;
import model.Empresa;
import model.Factura;
import model.FacturaDetalle;
import model.Marca;
import model.Municipio;
import model.ParametroDominio;
import model.Producto;
import model.ProductoResumenView;
import model.ProductoValorMedida;
import model.ResolucionFacturacion;
import model.ResumenDetalles;
import model.TipoDocumento;
import model.TipoProducto;
import model.UnidadMedida;

public class GetData {
	
	Session sesion = HibernateUtil.getSessionFactory().openSession();
	
	public int getResolucionFactura(String tipo) {
		
		Query query = sesion.createQuery("SELECT c.refId from ResolucionFacturacion c where c.refTipo =:arg  and refEstado =:arg2");
		query.setParameter("arg", tipo);
		query.setParameter("arg2", "A");
		
		Object test =  query.getSingleResult();
		String cant = String.valueOf(test);
		int resID= Integer.parseInt(cant);
		//System.out.println("Test:::"+ cantidad);
		
		return  resID;
		
	}
	
	public String getResolucionFacturaNumero(String tipo) {
		
		Query query = sesion.createQuery("SELECT c.refDescripcion from ResolucionFacturacion c where c.refTipo =:arg  and refEstado =:arg2");
		query.setParameter("arg", tipo);
		query.setParameter("arg2", "A");
		
		Object test =  query.getSingleResult();
		String resolucion = String.valueOf(test);
		
		//System.out.println("Test:::"+ cantidad);
		
		return  resolucion;
		
	}
	
	public int getCantProductos(int idProducto) {
		
	
		Query query = sesion.createQuery("SELECT count(pvm.pvmId) FROM ProductoValorMedida pvm INNER JOIN pvm.producto p INNER JOIN pvm.unidadMedida um where p.proId =:arg  ");
		query.setParameter("arg", idProducto);
		
		Object test =  query.getSingleResult();
		String cant = String.valueOf(test);
		int cantidad= Integer.parseInt(cant);
		System.out.println("Test:::"+ cantidad);
		
		return  cantidad;
	}

	public List<Cliente> getCliente() {
		
		//Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM Cliente c ");
					
		List<Cliente> clientes = query.getResultList();
		return clientes;
	}
	
	public List<Marca> getMarca() {
		
	//	Session sesion = HibernateUtil.getSessionFactory().getCurrentSession();
		Query query = sesion.createQuery("SELECT c FROM Marca c order by marDescripcion");
		
		List<Marca> marca = query.getResultList();
		return marca;
	}
	
	public List<Municipio> getMunicipio() {
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM Municipio c ");
					
		List<Municipio> municipio = query.getResultList();
		
		return municipio;
	}
	
	public List<Departamento> getDepartamento() {
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM Departamento c ");
			
		List<Departamento> departamento = query.getResultList();
		
		return departamento;
	}
	
	public int getIdProducto(String referencia){
		
		int id = -1;
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT p FROM Producto p where TRIM( p.proReferencia) =:arg1 order by 1 desc ");
		query.setParameter("arg1", referencia);
		
		List<Producto> productos = query.getResultList();
				
		for(Producto prod : productos) {
			id=prod.getProId();
			}
		
		
		
		return id;
	}
	
	public String getPVM (int pvmId) {
		
		ResumenDetalles resumen = new ResumenDetalles();
		
		
		resumen.setPvmId(pvmId);
		resumen.setProductoValorMedida(this.getIProductoValorMedida(pvmId));
		resumen.setProducto(resumen.getProductoValorMedida().getProducto());
		resumen.setUnidadMedida(resumen.getProductoValorMedida().getUnidadMedida());
		resumen.setTipoProducto(resumen.getProducto().getTipoProducto());
		resumen.setMarca(resumen.getProducto().getMarca());
		
		
		String jsonString = null;
		try {
			jsonString = String.valueOf(new JSONObject()
			        .put("pvmId", String.valueOf(pvmId))
			        .put("producto", String.valueOf(resumen.getProducto().getProDescripcion()))
			        .put("referencia", resumen.getProducto().getProReferencia())
			        .put("tipoProducto", resumen.getTipoProducto().getTipDescripcion())
			        .put("marca", resumen.getMarca().getMarDescripcion())
			        .put("medida", resumen.getUnidadMedida().getUnmDescripcion()));
			
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
		
		
		return jsonString;
	}
	
	public List<UnidadMedida> getUnidadMedidaByProducto(int val){
		
		Query query = sesion.createQuery("SELECT um FROM ProductoValorMedida pvm INNER JOIN pvm.producto p INNER JOIN pvm.unidadMedida um where p.proId =:arg and pvm.pvmEstado = 'A'");
		query.setParameter("arg", val);
		
		List<UnidadMedida> medida = query.getResultList();
		return medida;
	}

	public List<UnidadMedida> getUnidadMedidaByProductoTipo(int val){
		
		Query query = sesion.createQuery("SELECT um FROM ProductoValorMedida pvm INNER JOIN pvm.producto p INNER JOIN pvm.unidadMedida um where p.proId =:arg");
		query.setParameter("arg", val);
		
		List<UnidadMedida> medida = query.getResultList();
		return medida;
	}
	
	public double getExistencias(int proId, int unmId) {
		
		Query query = sesion.createQuery("SELECT pvm.pvmCantidad FROM ProductoValorMedida pvm INNER JOIN pvm.producto p INNER JOIN pvm.unidadMedida um where p.proId =:proId and um.unmId =:unmId ");
		query.setParameter("proId", proId);
		query.setParameter("unmId", unmId);
		
		double resultado = (double) query.getSingleResult();
		
		return resultado;
	}
	
	
	public List<ProductoValorMedida> getProductoValMedidaByProductoMedida (int producto, int medida){
		
		Query query = sesion.createQuery("SELECT pvm FROM ProductoValorMedida pvm INNER JOIN pvm.producto p INNER JOIN pvm.unidadMedida um where p.proId=:arg1 and um.unmId=:arg2");
		query.setParameter("arg1", producto);
		query.setParameter("arg2", medida);
		
		List<ProductoValorMedida> valores = query.getResultList();
		
		return valores;
	}
	
	public double getValorProductoByProductoMedida (int producto, int medida){
		
		System.out.println("El producto::"+ producto + "la medida::" + medida);
		
		Query query = sesion.createQuery("SELECT pvm.pvmValor FROM ProductoValorMedida pvm INNER JOIN pvm.producto p INNER JOIN pvm.unidadMedida um where p.proId=:arg1 and um.unmId=:arg2");
		query.setParameter("arg1", producto);
		query.setParameter("arg2", medida);
		
		
		
		double valores = (double) query.getSingleResult();
		
		System.out.println("Valor de la conuslta$$$$:::" + valores);
		
		return valores;
	}
	
	public List<Producto> getProductoByIdMedida(int producto, int medida){
		
		Query query = sesion.createQuery("SELECT p FROM ProductoValorMedida pvm INNER JOIN pvm.producto p INNER JOIN pvm.unidadMedida um where p.proId=:arg1 and um.unmId=:arg2");
		query.setParameter("arg1", producto);
		query.setParameter("arg2", medida);
		
		List<Producto> productos = query.getResultList();
		
		return productos;
		
	}
	
	public int getIProductoValorMedida (int idDetalle, String medida) {
		
		
		int id = -1;
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT pv FROM ProductoValorMedida pv inner join Producto p on pv.producto = p.proId inner join UnidadMedida um on pv.unidadMedida = um.unmId  where p.proId =:arg1 and um.unmId =:arg2 order by 1 desc ");
		query.setParameter("arg1", idDetalle);
		query.setParameter("arg2", Integer.parseInt(medida));
		
		List<ProductoValorMedida> productos = query.getResultList();
		

		for(ProductoValorMedida prod : productos) {
			id=prod.getPvmId();
			}
		
		
		return id;
		
	}
	
public ProductoValorMedida getIProductoValorMedida (int pvm_id) {
		
		
		
		Query query = sesion.createQuery("SELECT pv FROM ProductoValorMedida pv where pvm_id = :arg1 ");
		query.setParameter("arg1", pvm_id);
		
		ProductoValorMedida productoValorMedida = (ProductoValorMedida) query.getSingleResult();

		return productoValorMedida;
		
	}
	
	public Marca getMarca(String idMarca) {
		
		Marca mar = new Marca();
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT m FROM Marca m where m.marId =:arg1 order by 1 desc ");
		query.setParameter("arg1", Integer.parseInt(idMarca));
		
		List<Marca> marcas = query.getResultList();			
		
		for(Marca marca : marcas) {
			mar.setMarId(marca.getMarId());
			mar.setMarDescripcion(marca.getMarDescripcion());
			mar.setFechaCreacion(marca.getFechaCreacion());
			mar.setMarEstado(marca.getMarEstado());
			
			}
		//sesion.close();
		return mar;
	}
	
	public TipoProducto getTipoProducto(String idTipoProducto) {
		
		TipoProducto tp = new TipoProducto();
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT tp FROM TipoProducto tp where tp.tipId =:arg1 order by 1 desc ");
		query.setParameter("arg1", Integer.parseInt(idTipoProducto));
		
		List<TipoProducto> tipos = query.getResultList();			
		
		for(TipoProducto tipo : tipos) {

			tp.setTipId(tipo.getTipId());
			tp.setTipDescripcion(tipo.getTipDescripcion());
			tp.setTipEstado(tipo.getTipEstado());
			tp.setFechaCreacion(tipo.getFechaCreacion());
			
			}
		
		return tp;
	}
	
	public List<TipoProducto> getTipoProductoAll(String tipo) {
	
		
		Query query = sesion.createQuery("SELECT tp FROM TipoProducto tp where lower (tp.tipDescripcion) LIKE lower(:referencia) order by tipDescripcion ");
		query.setParameter("referencia", "%"+tipo+"%");
		List<TipoProducto> tipos = query.getResultList();			
				
		return tipos;
	}
	
	public Producto getProducto (int idProducto) {
		
		Producto prod = new Producto();
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT p FROM Producto p where p.proId =:arg1 order by 1 desc ");
		query.setParameter("arg1", idProducto);
		
		List<Producto> productos = query.getResultList();			
		
		for(Producto producto : productos) {

			prod.setProId(producto.getProId());
			prod.setFechaCreacion(producto.getFechaCreacion());
			prod.setMarca(producto.getMarca());
			prod.setProDescripcion(producto.getProDescripcion());
			prod.setProEstado(producto.getProEstado());
			prod.setProReferencia(producto.getProReferencia());
			prod.setTipoProducto(producto.getTipoProducto());
			prod.setUepId(producto.getUepId());
			
			}
		
		return prod;
	}
	
	public UnidadMedida getUnidadMedida (int id) {
		
		UnidadMedida unidad = new UnidadMedida();
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT um FROM UnidadMedida um where um.unmId =:arg1 order by 1 desc ");
		query.setParameter("arg1", id);
		
		List<UnidadMedida> unidades = query.getResultList();			
		
		for(UnidadMedida uni : unidades) {

		unidad.setFechaCreacion(uni.getFechaCreacion());
		unidad.setUepId(uni.getUepId());
		unidad.setUnmDescripcion(uni.getUnmDescripcion());
		unidad.setUnmEstado(uni.getUnmEstado());
		unidad.setUnmId(uni.getUnmId());
		unidad.setUnmRelacion(uni.getUnmRelacion());
			
			}
		
		return unidad;
	}

	public int getTipoProductoDesc(String descripcion) {
		
		int tipId= -1;
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT tp FROM TipoProducto tp where tp.tipDescripcion =:arg1 order by 1 desc ");
		query.setParameter("arg1", descripcion);
		
		List<TipoProducto> tipos = query.getResultList();			
		
		for(TipoProducto tip : tipos) {

			tipId = tip.getTipId();

			}
		return tipId;
	}

	public List<TipoProducto> getTipoProductoByMarca (int marId ) {
		
		Query query= sesion.createQuery("SELECT DISTINCT (c) FROM Producto p INNER JOIN  p.tipoProducto c INNER JOIN p.marca m where m.marId =:arg order by c.tipDescripcion");
		query.setParameter("arg", marId);
		
		List<TipoProducto> tipoProducto = query.getResultList();
		return tipoProducto;	
	}
	
	public List<Producto> getProductoByMarcaTipo(int tipo_prod, int marca){
		
		Query query= sesion.createQuery("SELECT DISTINCT (p) FROM Producto p INNER JOIN p.tipoProducto tp INNER JOIN p.marca m where tp.tipId =:arg1 and m.marId =:mar_id order by p.proDescripcion");
		query.setParameter("arg1", tipo_prod);
		query.setParameter("mar_id", marca);
		
		List<Producto> producto = query.getResultList();
		
		return producto;
	}
	
	
	public List<Marca> getMarcaAll(String descripcion) {
		
		Query query = sesion.createQuery("SELECT m FROM Marca m where lower(m.marDescripcion) LIKE lower(:arg1) order by marDescripcion ");
		query.setParameter("arg1", "%"+descripcion+"%");
		
		List<Marca> tipos = query.getResultList();			
		
		return tipos;
	}
	
	public int getMarcaDesc(String descripcion) {
		int marId= -1;
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT m FROM Marca m where m.marDescripcion =:arg1 order by 1 desc ");
		query.setParameter("arg1", descripcion);
		
		List<Marca> tipos = query.getResultList();			
		
		for(Marca tip : tipos) {

			marId = tip.getMarId();
		
			
			}
		return marId;
	}

	public int getUnidadDesc(String descripcion) {
		int unmId= -1;
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT m FROM UnidadMedida m where m.unmDescripcion =:arg1 order by 1 desc ");
		query.setParameter("arg1", descripcion);
		
		List<UnidadMedida> tipos = query.getResultList();			
		
		for(UnidadMedida tip : tipos) {

			unmId = tip.getUnmId();
		
			
			}
		return unmId;
	}
	
	public Cliente getCliente(int cliId) {
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM Cliente c where cliId = :arg");
		query.setParameter("arg", cliId);
			
		Cliente clientes =  (Cliente) query.getSingleResult();
		
		if (clientes.getCliApellidos()== null) {
			clientes.setCliApellidos("");
		}
		return clientes;
	}
	
	public Cliente getCliente(String identificacion) {
		
		//	Session sesion = HibernateUtil.getSessionFactory().openSession();
			Query query = sesion.createQuery("SELECT c FROM Cliente c where cliNumIdent = :arg");
			query.setParameter("arg", identificacion);
				
			Cliente clientes =  (Cliente) query.getSingleResult();
			return clientes;
		}
	
		@SuppressWarnings("finally")
		public int getIdCliente(String identificacion) {
		
		//	Session sesion = HibernateUtil.getSessionFactory().openSession();
			Query query = sesion.createQuery("SELECT c FROM Cliente c where cliNumIdent = :arg");
			query.setParameter("arg", identificacion);
				int idcliente = 0;
			try {
			Cliente clientes =  (Cliente) query.getSingleResult();
			idcliente = clientes.getCliId();
			}catch(NoResultException e) {
				idcliente = 0;
			}finally {
			return idcliente;
			
			}
		}
	
	
	public Municipio getMunicipio(int i) {
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM Municipio c where muni_id = :arg");
		query.setParameter("arg", (i));
			
		List<Municipio> municipio = query.getResultList();
		Municipio mun = municipio.get(0);
		return mun;
	}
	
	public Departamento getDepartamento(int i) {
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM Departamento c where dep_id = :arg");
		query.setParameter("arg", (i));
			
		List<Departamento> departamento = query.getResultList();
		Departamento dep = departamento.get(0);
		return dep;
	}
	
	
	public String getLastFactura() {
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c.facNumFactura FROM Factura c order by facId desc");
		query.setMaxResults(1);
					
		List <String> ultimaFactura = query.getResultList();
		String ultima = ultimaFactura.get(0);
		
		
		return ultima;
	}
	
	public String getLastFactura(String tipo) {
		
		//	Session sesion = HibernateUtil.getSessionFactory().openSession();
			Query query = sesion.createQuery("SELECT c.facNumFactura FROM Factura c where facTipo =:arg order by facId desc");
			query.setParameter("arg", tipo);
			query.setMaxResults(1);
						
			List <String> ultimaFactura = query.getResultList();
			String ultima = ultimaFactura.get(0);
			
			
			return ultima;
		}
	
	public Factura getLastFactura(int id) {
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM Factura c where facId = :arg1 ");
		query.setParameter("arg1", id);
		
					
		Factura ultimaFactura = (Factura) query.getSingleResult();
		
		return ultimaFactura;
	}
	
	public Empresa getEmpresa() {
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM Empresa c ");
		
		
					
		Empresa empresa = (Empresa) query.getSingleResult();
		
		return empresa;
	}
	
	
public TipoDocumento getTipoDoc(int id) {
		
	//	Session sesion =  HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM TipoDocumento c where tidId = :arg1 ");
		query.setParameter("arg1", id);
		
		TipoDocumento tipodoc = (TipoDocumento) query.getSingleResult();
		
		return tipodoc;
	}
	

	public ParametroDominio getParametroDominio (String parametro) {
		
	//	Session sesion =  HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM ParametroDominio c where padDescripcion = :arg1 ");
		query.setParameter("arg1", parametro);
		
		ParametroDominio parametroDominio = (ParametroDominio) query.getSingleResult();
		return parametroDominio;
	}

	public List<FacturaDetalle> getFacturaDetalle (int id){
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		Query query = sesion.createQuery("SELECT c FROM FacturaDetalle c  where facId = :arg1 order by c.pvmId");
		query.setParameter("arg1", id);
		
		List<FacturaDetalle> facturaDetalle = query.getResultList();
		
		
		return facturaDetalle;
	}
	
	public Factura getFacturaNumero(String numeroFactura) {
		
		Factura factura = new Factura();
		
		Query fquery = sesion.createQuery("Select c from Factura c where  facNumFactura =:numFactura and facTipo = 'F'");
		fquery.setParameter("numFactura", numeroFactura);
		
		factura = (Factura) fquery.getSingleResult();
		
		return factura;
	}
	
	public Factura getFacturaNumeroByFNum(String numeroFactura) {
		
		Factura factura = new Factura();
		
		
		System.out.println("El numero de la factura:::" + numeroFactura + "::termina" );
		Query fquery = sesion.createQuery("Select c from Factura c where  facNumFactura =:numFactura ");
		fquery.setParameter("numFactura", numeroFactura);
		
		factura = (Factura) fquery.getSingleResult();
		
		return factura;
	}
	
	public List<Factura> getFacturaNotas(String nombre, String identificacion, String numeroFactura, Date fechaIni, Date fechaFin){
		
	//	Session sesion = HibernateUtil.getSessionFactory().openSession();
		
		List<Factura> factura = null;
				
		Query fquery = sesion.createQuery("Select c from Cliente c where  cliNumIdent LIKE :identificacion or lower (cliNombre) LIKE lower (TRIM(:nombre)) ");
		fquery.setParameter("nombre", nombre);
		fquery.setParameter("identificacion", identificacion);
		
		
		
		
			List<Cliente>  cliente =   fquery.getResultList();
		
		
		
		if(cliente.isEmpty())
		{
			
			Query query = sesion.createQuery("SELECT c FROM Factura c  where facNumFactura = :numFactura  or c.facFechaFactura BETWEEN :fechaIni AND :fechaFin AND facTipo = 'F'");
			query.setParameter("numFactura", numeroFactura);
			query.setParameter("fechaIni", fechaIni);
			query.setParameter("fechaFin", fechaFin);
			
			factura = query.getResultList();
		}
		else{
		int contador =0;
		
		for(Cliente cli: cliente) {
			
			Query query = sesion.createQuery("SELECT c FROM Factura c  where facNumFactura = :numFactura or cliId =:cliId  and c.facFechaFactura BETWEEN :fechaIni AND :fechaFin AND facTipo = 'F'");
			query.setParameter("numFactura", numeroFactura);
			query.setParameter("fechaIni", fechaIni);
			query.setParameter("fechaFin", fechaFin);
			query.setParameter("cliId", cli.getCliId());
			
			if(contador == 0) {
			factura = query.getResultList();
			contador ++;
			}else {
			 factura.addAll(query.getResultList());
			}
		}
		
		}
		
		
		return factura;
	}
	
		public ResolucionFacturacion getResolucion(String tipo) {
		
		ResolucionFacturacion  resolucion  = new ResolucionFacturacion();
		
		Query fquery = sesion.createQuery("Select c from ResolucionFacturacion c where refEstado = 'A' and c.refTipo =:arg order by refId");
		fquery.setParameter("arg", tipo);
		fquery.setMaxResults(1);
		
		resolucion = (ResolucionFacturacion) fquery.getSingleResult();
		
		return resolucion;
	}
		
		public ProductoResumenView getResumenById (int pvmId) {
			
			ProductoResumenView resumen;
			Query query =null;
			
			 query = sesion.createQuery("Select c from ProductoResumenView c where c.pvmId=:pvmId order by proReferencia");
				query.setParameter("pvmId", pvmId);
				
				resumen = (ProductoResumenView) query.getSingleResult();
			
			return resumen;
		}
		
		public List<ProductoResumenView> getResumenView(int marca, int tipo, String referencia) {
			
			ProductoResumenView resumen = new ProductoResumenView();
			Query query =null;
			
			if (!(referencia.equals(""))){
				System.out.println("Ingreso por la referencia::" + referencia);
				 query = sesion.createQuery("Select c from ProductoResumenView c where c.proReferencia LIKE:referencia order by proReferencia");
				query.setParameter("referencia", "%"+referencia);
			}
			else if(tipo != 0 && marca != 0) {
				System.out.println("Ingreso por el tipo de producto");
				 query = sesion.createQuery("Select c from ProductoResumenView c where c.marId=:mar_id and c.tipId=:tip_id order by proDescripcion");
				 query.setParameter("mar_id", marca);
				 query.setParameter("tip_id", tipo);
			}else {
			 query = sesion.createQuery("Select c from ProductoResumenView c where c.marId=:mar_id order by tip_descripcion");
			query.setParameter("mar_id", marca);
			}
			List<ProductoResumenView>  productos =   query.getResultList();
			
			for(ProductoResumenView prod : productos) {
				
				System.out.println(prod.getProDescripcion());
			}
			
			return productos;
			
		}
		
		
		public List<ProductoResumenView> getResumenViewMedida(int marca, int tipo, String referencia, int medida) {
			
			ProductoResumenView resumen = new ProductoResumenView();
			Query query =null;
			
			if (!(referencia.equals(""))){
				System.out.println("Ingreso por la referencia::" + referencia);
				 query = sesion.createQuery("Select c from ProductoResumenView c where c.proReferencia LIKE:referencia order by proReferencia");
				query.setParameter("referencia", "%"+referencia);
			}
			else if(tipo != 0 && marca != 0 && medida != 0) {
				System.out.println("Ingreso por el tipo de producto");
				 query = sesion.createQuery("Select distinct c from ProductoResumenView c where c.marId=:mar_id and c.tipId=:tip_id and c.unmId =:unm_id  order by proDescripcion");
				 query.setParameter("mar_id", marca);
				 query.setParameter("tip_id", tipo);
				 query.setParameter("unm_id", medida);
				 
			}else {
			 query = sesion.createQuery("Select c from ProductoResumenView c where c.marId=:mar_id order by tip_descripcion");
			query.setParameter("mar_id", marca);
			}
			List<ProductoResumenView>  productos =   query.getResultList();
			
			for(ProductoResumenView prod : productos) {
				
				System.out.println(prod.getProDescripcion());
			}
			
			return productos;
			
		}
		
		
		public TipoDocumento getTipoDocumento(int tip_id) {
			
			TipoDocumento  tipoDocumento  = new TipoDocumento();
			
			Query fquery = sesion.createQuery("Select c from TipoDocumento c where tidId =:arg ");
			fquery.setParameter("arg", tip_id);
			
			tipoDocumento = (TipoDocumento) fquery.getSingleResult();
			
			return tipoDocumento;
		}
		
		
		public List<DetalleFacturaResumen> getDetalleFacturaResumen(String tipo, String fechaIni, String fechaFin){
			
			
			System.out.println("Fecha de inicio::::::"+ fechaIni);
			DetalleFacturaResumen dfr = new DetalleFacturaResumen();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			Date fechaIniF = null;
			Date fechaFinF = null;
			try {
				
				
				fechaIniF = dateFormat.parse(fechaIni);
				fechaFinF = dateFormat.parse(fechaFin);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Query query =null;
			
			if ((tipo.equals("P"))){
				System.out.println("Ingreso por la referencia::" + fechaIni);
				System.out.println("Ingreso por la referenciarrrrrr::" + fechaFin);
				 query = sesion.createQuery("Select c from DetalleFacturaResumen c where c.facTipo =:tipo and c.facFechaFactura between :fechaIni AND :fechaFin ");
				 query.setParameter("tipo",  tipo);
				 query.setParameter("fechaIni",  fechaIniF , TemporalType.DATE );
				 query.setParameter("fechaFin",  fechaFinF , TemporalType.DATE );
				 
				 System.out.println("La consulta::::"+ query.getParameter("fechaIni")+ " ::::::::"+ query.getParameter("fechaFin"));
			}
			
			List<DetalleFacturaResumen>  facturas =   query.getResultList();
			
			return facturas;
			
		}
		
		public Factura getFacturaByNumOriginal (String facNumero) {
			
			Factura factura;
			
			Query fquery = sesion.createQuery("Select f from Factura f where f.facNumOriginal =:arg ");
			fquery.setParameter("arg", facNumero);
			
			factura = (Factura) fquery.getSingleResult();
			
			
			return factura;
		}

		public List<UnidadMedida> getUnidadMedidaByTipoProducto(int val, int val2) {

			Query query = sesion.createQuery("SELECT distinct um FROM ProductoValorMedida pvm INNER JOIN pvm.producto p INNER JOIN pvm.unidadMedida um INNER JOIN p.marca ma INNER JOIN  p.tipoProducto tp where ma.marId =:arg2 and tp.tipId =:arg");
			query.setParameter("arg", val);
			query.setParameter("arg2", val2);
			
			List<UnidadMedida> medida = query.getResultList();
			return medida;
		}
	

}