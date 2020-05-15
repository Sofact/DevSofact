//***************************************************************************************************
// Clase con operaciones de transformación
// sumar siguiente hexadecimal
// Calcular diferencia en fechas de facturación que determina dias de credito
//***************************************************************************************************

package controller.utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Factura;

public class Convertidores {
	
	public String sumarHexa (String hexa) {
		
		System.out.println("La ultima factura::::: " + hexa);
		String respuesta="";
		int dec = Integer.parseInt(hexa, 16);
		dec++;
		String hex = Integer.toHexString(dec).toUpperCase();
		
		
		for(int i =0; i < 10 - hex.length(); i++) {
			respuesta = respuesta + "0";
		}
		hex = respuesta + hex;
		
		return hex;
	}
	
	public int calcularDiferenciaFecha (Factura factura) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 
		Date fechaInicial = null;
		Date fechaFinal = null;
		try {
			fechaInicial = dateFormat.parse(String.valueOf(factura.getFacFechaFactura()));
			fechaFinal=dateFormat.parse(String.valueOf(factura.getFacFecaPago()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int resultado = (int)(fechaFinal.getTime() - fechaInicial.getTime()) / 86400000 ;
		
		return resultado;
	}

}
