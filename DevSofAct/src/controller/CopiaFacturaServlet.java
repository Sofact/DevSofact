package controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.utilidades.ImprimirTicket;
import model.Cliente;
import model.Empresa;
import model.Factura;
import model.FacturaDetalle;
import model.ProductoResumenView;
import model.ResolucionFacturacion;
import model.UnidadMedida;

/**
 * Servlet implementation class CopiaFacturaServlet
 */
@WebServlet("/CopiaFacturaServlet")
public class CopiaFacturaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CopiaFacturaServlet() {
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

		String facturaNum = request.getParameter("facNumero");
		java.util.Date fecha = new Date();
		String fechaActual = convertirFechaString(fecha);
		DecimalFormat formateador = new DecimalFormat("###,###.##");
		GetData gd = new GetData();
		ResolucionFacturacion ref = gd.getResolucion("P");
		
		Empresa em =  gd.getEmpresa();
		Factura factura;
		Cliente clienteFac;
		
		factura = gd.getFacturaByNumOriginal(facturaNum);
		clienteFac= gd.getCliente(factura.getCliId());
		String cliente;
		String apellidos="";
		
		if(factura.getCliId()== 1) {
			cliente = "";
		}else {
			
			if(clienteFac.getCliApellidos()== null) {
				System.out.println("esta vacio" + clienteFac.getCliApellidos());
				
			}
			else {
				apellidos = clienteFac.getCliApellidos();
			}
			cliente="NOMBRE:         "+ clienteFac.getCliNombre()+" "+clienteFac.getCliSegundoNombre() + " "+apellidos +"\n"+
					"IDENTIFICACION: "+ clienteFac.getCliNumIdent()+"\n"+
					"DIRECCION:      "+ clienteFac.getCliDireccion()+"\n"+
					"TELEFONO:       "+ clienteFac.getCliTelefono();
		}
		
		String descripcion="";
		String items = "Cant        Descripcion       V.Unit    valor\n"+
				   "------ --------------------- --------  --------\n";
		

		for (FacturaDetalle deta : gd.getFacturaDetalle(factura.getFacId())) {

			ProductoResumenView detalle = gd.getResumenById(deta.getPvmId());
			UnidadMedida unidadMedida = gd.getUnidadMedida(detalle.getUnmId());
			System.out.println("El id de la MArca creada es::::" + detalle.getProDescripcion());
			descripcion = detalle.getTipDescripcion()+" "+detalle.getProDescripcion();
			double detalleValor = (deta.getFadSubtotal());
			double valorUnitaro = (deta.getFadValorUnitario());
			String subValor = formateaValores(formateador.format(detalleValor));
			String valorUnit = formateaValores(formateador.format(valorUnitaro));
			String cantidad = String.valueOf(deta.getFadCantidad());
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
		
		ImprimirTicket it = new ImprimirTicket(em.getEmpDescripcion(), em.getEmpNit(), ref.getRefDescripcion(), fechaInicio, fechaFin, ref.getRefInicioConsecutivo(), ref.getRefFinConsecutivo(), factura.getFacNumOriginal(), items, "ticket", "caissier", fechaActual, "items", formateaValores(formateador.format((factura.getFacSubTotal()))), formateaValores(formateador.format((factura.getFacIva()))), formateaValores(formateador.format((factura.getFacTotal()))), cliente, "change");
		it.print();
		
		
	}
	
	public String convertirFechaString(Date date){	
		   return new SimpleDateFormat("yyyy-MM-dd").format(date);
			
		}
	
	public String formateaValores(String valor) {
		
		while (valor.length()<10) {
			valor = " "+valor;
		}
		return valor;
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

}