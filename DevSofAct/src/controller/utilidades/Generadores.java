//***************************************************************************************************
// CLASE CON GENERADORES
// GENERAR CUFE
// GENERAR NOMBRE DE ARCHIVO
// GENERAR INVOICE LINE DESCRIPTION
//***************************************************************************************************


package controller.utilidades;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import controller.GetData;
import model.Cliente;
import model.Empresa;
import model.Factura;
import model.Marca;
import model.Producto;
import model.ProductoValorMedida;
import model.TipoDocumento;
import model.TipoProducto;
import model.UnidadMedida;

public class Generadores {
	
	GetData gd = new GetData();
	Convertidores con = new Convertidores();
	Factura factura;
	Empresa empresa;
	Hash hash;
	TipoDocumento tipodoc;
	Cliente cliente;
	Producto producto;
	ProductoValorMedida productoValorMedida;
	UnidadMedida unidadMedida;
	Marca marca;
	TipoProducto tipoProducto;
	
	public String generadorConsecutivoPos(String tipo) {
		
		return String.valueOf((Integer.parseInt(gd.getLastFactura(tipo))+1));
	}
	
	public String generadorConsecutivo(String tipo) {
		
		return con.sumarHexa(gd.getLastFactura(tipo));
	}
	
	public String generadorCufe(int id) {
		
		System.out.println("Ingreso al generador de cuffeeeeee");
		String cufe=null;
		SimpleDateFormat parser = new SimpleDateFormat ("yyyyMMddHHmmss");
		DecimalFormat formateador = new DecimalFormat("0000.00");
		Date date= null;
		factura = gd.getLastFactura(id);
		empresa = gd.getEmpresa();
		cliente = gd.getCliente(factura.getCliId());
		tipodoc = gd.getTipoDoc(cliente.getCliTipoIdent());
		try {
			date = parser.parse(String.valueOf(factura.getFacFechaFactura()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		cufe = "PRUE"+Integer.parseInt(factura.getFacNumFactura().trim(), 16 ) + parser.format(factura.getFacFechaFactura()) + formateador.format(factura.getFacSubTotal()) + "01"+ formateador.format(factura.getFacIva()) + "020.00030.00" 
					 + formateador.format(factura.getFacTotal()) + empresa.getEmpNit() + tipodoc.getTidCodigo() + cliente.getCliNumIdent() + empresa.getEmpCitec()  ;
		
		cufe= cufe.replace(',', '.');
		
		String shaseado = hash.sha1(cufe);

		return shaseado;		
	}
	
	public String generadorConsecutivoPosPrefijo(String tipo) {
		
		String consecutivo = String.valueOf((Integer.parseInt(gd.getLastFactura(tipo))+1));
		
		while (consecutivo.length()<4) {
			consecutivo = "0"+consecutivo;
		}
		
		return gd.getResolucion("P").getRefPrefijo()+ consecutivo;
		
		
	}

	
	public String generadorInvoiceLineDescription (int pvm_id) {
		
		
		productoValorMedida= gd.getIProductoValorMedida(pvm_id);
		producto = productoValorMedida.getProducto();
		unidadMedida = productoValorMedida.getUnidadMedida();
		marca= producto.getMarca();
		tipoProducto = producto.getTipoProducto();
		
		
		
		return producto.getProReferencia() + " "+ tipoProducto.getTipDescripcion() + " " + producto.getProDescripcion() + " " + marca.getMarDescripcion() + " (" +unidadMedida.getUnmDescripcion() + ")";
	}
	
	public String generadorNombreArchivo(String tipo, int fac_id) {
		
		String nombre= null;
		GetData gd = new GetData();
		Factura factura = new Factura();
		Empresa empresa = new Empresa();
		factura = gd.getLastFactura(fac_id);
		empresa =gd.getEmpresa();
		String respuesta = "";
		String nit="";
		
		for(int i =0; i < 10 - empresa.getEmpNit().length(); i++) {
			respuesta = respuesta + "0";
		}
		nit = respuesta + empresa.getEmpNit();
		
		if (tipo.equals("factura")) {
			
			nombre = "face_f" + nit + factura.getFacNumFactura();
			
		}
		
		if (tipo.equals("credito")) {
			
			nombre = "face_cn" + nit + factura.getFacNumFactura();
			
		}
		
		if (tipo.equals("debito")) {
			
			nombre = "face_dn" + nit + factura.getFacNumFactura();
			
		}

		return nombre;
	}

}
