package controller;

import java.util.Date;

public class FacturaJson
{
	private String iva;
	
	private String total;
	
	private String cliente;
	
	private Date fecha;
	
	private DetalleFactura[] detalleFactura;
	
	private String subtotal;
	
	private String observacion;
	
	private String facturaNum;
	
	public String getfacturaNum() {
		return facturaNum;
	}

	public void setfacturaNum(String facturaNum) {
		this.facturaNum = facturaNum;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getIva ()
	{
	return iva;
	}
	
	public void setIva (String iva)
	{
	this.iva = iva;
	}
	
	public String getTotal ()
	{
	return total;
	}
	
	public void setTotal (String total)
	{
	this.total = total;
	}
	
	public String getCliente ()
	{
	return cliente;
	}
	
	public void setCliente (String cliente)
	{
	this.cliente = cliente;
	}
	
	public DetalleFactura[] getDetalleFactura ()
	{
	return detalleFactura;
	}
	
	public void setDetalleFactura (DetalleFactura[] detalleFactura)
	{
	this.detalleFactura = detalleFactura;
	}
	
	public String getSubtotal ()
	{
	return subtotal;
	}
	
	public void setSubtotal (String subtotal)
	{
	this.subtotal = subtotal;
	}
	
	@Override
	public String toString()
	{
	return "ClassPojo [iva = "+iva+", total = "+total+", cliente = "+cliente+", detalleFactura = "+detalleFactura+", subtotal = "+subtotal+"]";
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
