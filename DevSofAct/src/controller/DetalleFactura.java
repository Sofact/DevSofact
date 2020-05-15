package controller;

public class DetalleFactura {
	private String total;

	private String cantidad;

	private String pvmid;
	
	private String descuento;
	
	private String subtotal;

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getDescuento() {
		return descuento;
	}

	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getPvmid() {
		return pvmid;
	}

	public void setPvmid(String pvmid) {
		this.pvmid = pvmid;
	}

	@Override
	public String toString() {
		return "ClassPojo [total = " + total + ", cantidad = " + cantidad + ", pvmid = " + pvmid + ", descuento = " + descuento + "]";
	}
}
