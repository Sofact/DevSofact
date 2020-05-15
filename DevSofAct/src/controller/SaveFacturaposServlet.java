package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import controller.utilidades.ImprimirTicket;
import model.Cliente;
import model.Empresa;
import model.Factura;
import model.FacturaDetalle;
import model.ProductoResumenView;
import model.ProductoValorMedida;
import model.ResolucionFacturacion;
import model.ResumenDetalles;
import model.UnidadMedida;

/**
 * Servlet implementation class SaveFacturaposServlet
 */
@WebServlet("/SaveFacturaposServlet")
public class SaveFacturaposServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveFacturaposServlet() {
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
	
	
	
	
	public String convertirFechaString(Date date){	
		   return new SimpleDateFormat("yyyy-MM-dd").format(date);
			
		}
	
	public String formateaTamano(String entrada) {
		
		while(entrada.length()<15) {
			entrada = entrada+" ";
		}
		
		return entrada;
	}
	
	public String formatString(String entrada) {
		
		String resultado="";
		String inicio="";
		String fin= "";
		String entrada2= "";
		String inicio2="";
		String fin2 ="";
		

		if(entrada.length()<15) {
			resultado= formateaTamano(entrada);
		}else {
		
		while(entrada.length()>= 15) {
			
			 inicio = entrada.substring(0, entrada.lastIndexOf(" "));
			 fin = entrada.substring(entrada.lastIndexOf(" ")+1, entrada.length())+" "+fin;
			 entrada = inicio;
			 
			
			
			System.out.println(":::"+inicio+"\n"+fin+"::::");
		}
		
		 if (fin.length() > 15) {
			 
			fin =  formatString(fin);
		 }
		fin = "         "+ formateaTamano(fin);
		resultado = inicio+"\n"+fin;
		}
		
		return resultado;
	}
	
	public String formateaValores(String valor) {
		
		while (valor.length()<10) {
			valor = " "+valor;
		}
		return valor;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int id;
		String json = "";
		java.util.Date fecha = new Date();
		String fechaActual = convertirFechaString(fecha);
		
		BufferedReader br = new BufferedReader (new InputStreamReader(request.getInputStream()));
		ObjectMapper mapper = new ObjectMapper();
		
        if(br != null){
            json = br.readLine();
        }
       
        
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			
			FacturaJson fact = mapper.readValue(json, FacturaJson.class);
			DecimalFormat formateador = new DecimalFormat("###,###.##");

			SaveData sd = new SaveData();
			GetData gd = new GetData();
			Cliente cl = gd.getCliente(Integer.parseInt(fact.getCliente()));
			Empresa em =  gd.getEmpresa();
			ResolucionFacturacion ref = gd.getResolucion("P");
			
			
			id = sd.SaveData(fact, "P");
						
			for (DetalleFactura deta : fact.getDetalleFactura()) {

				sd.SaveData(deta, id);
				Session sesion = HibernateUtil.getSessionFactory().openSession();
				sesion.beginTransaction();
				Transaction tx = sesion.getTransaction();
				ProductoValorMedida pv = sesion.find(ProductoValorMedida.class, Integer.parseInt(deta.getPvmid()));
				pv.setPvmCantidad(pv.getPvmCantidad() - Double.parseDouble(deta.getCantidad()));
				tx.commit();

			}
			
			Factura factura = gd.getLastFactura(id);
			String cliente;
			String apellidos="";
			
			if(fact.getCliente().equals("1")) {
				cliente = "";
			}else {
				
				if(cl.getCliApellidos()== null) {
					System.out.println("esta vacio" + cl.getCliApellidos());
					
				}
				else {
					apellidos = cl.getCliApellidos();
				}
				cliente="NOMBRE:         "+ cl.getCliNombre() + " "+apellidos +"\n"+
						"IDENTIFICACION: "+ cl.getCliNumIdent()+"\n"+
						"DIRECCION:      "+ cl.getCliDireccion()+"\n"+
						"TELEFONO:       "+ cl.getCliTelefono();
			}
			
			String descripcion="";
			//List<FacturaDetalle> facturaDetalle = (List<FacturaDetalle>) gd.getFacturaDetalle(factura.getFacId());
			String items = "Cant        Descripcion       V.Unit    valor\n"+
						   "------ --------------------- --------  --------\n";
			
			for (DetalleFactura deta : fact.getDetalleFactura()) {

				ProductoResumenView detalle = gd.getResumenById(Integer.parseInt(deta.getPvmid()));
				UnidadMedida unidadMedida = gd.getUnidadMedida(detalle.getUnmId());
				System.out.println("El id de la MArca creada es::::" + detalle.getProDescripcion());
				descripcion = detalle.getTipDescripcion()+" "+detalle.getProDescripcion();
				double detalleValor = Double.parseDouble(deta.getTotal());
				double valorUnitaro = Double.parseDouble(deta.getSubtotal());
				String subValor = formateaValores(formateador.format(detalleValor));
				String valorUnit = formateaValores(formateador.format(valorUnitaro));
				String cantidad = deta.getCantidad();
				String unidadRelacion = unidadMedida.getUnmRelacion();
				System.out.println("EL valor en euq formato esta:::::" + subValor);
				
				while(cantidad.length()<3)
				{
					cantidad = " "+cantidad;
				}
				
				while (unidadRelacion.length()<4) {
					unidadRelacion = unidadRelacion+" ";
				}
				
				items = items + cantidad+ unidadRelacion +" "+ formatString(descripcion) +" "+valorUnit+ " "+subValor+ "\n";

			}
			
			String fechaInicio = convertirFechaString(ref.getRefFechaInicio());
			String fechaFin = convertirFechaString(ref.getFechaCreacion());
			
			
			ImprimirTicket it = new ImprimirTicket(em.getEmpDescripcion(), em.getEmpNit(), ref.getRefDescripcion(), fechaInicio, fechaFin, ref.getRefInicioConsecutivo(), ref.getRefFinConsecutivo(), factura.getFacNumFactura(), items, "ticket", "caissier", fechaActual, "items", formateaValores(formateador.format((factura.getFacSubTotal()))), formateaValores(formateador.format((factura.getFacIva()))), formateaValores(formateador.format((factura.getFacTotal()))), cliente, "change");
			it.print();
			//request.getRequestDispatcher("./admin/FacturaPos.jsp").forward(request, response);
			
		//	ReporteFacturaServlet rfs = new ReporteFacturaServlet();
		//	rfs.processRequest(request, response, id);
						
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
