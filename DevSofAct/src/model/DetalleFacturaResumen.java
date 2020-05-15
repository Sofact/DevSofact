package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the detalle_factura_resumen database table.
 * 
 */
@Entity
@Table(name="detalle_factura_resumen")
@NamedQuery(name="DetalleFacturaResumen.findAll", query="SELECT p FROM DetalleFacturaResumen p")
public class DetalleFacturaResumen implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Column(name="fac_num_original")
	private String facNumOriginal;
	
	@Column(name="fac_fecha_factura")
	private String facFechaFactura;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="cli_num_ident")
	private String cliNumIdent;
	
	@Column(name="cli_direccion")
	private String cliDireccion;
	
	@Column(name="cli_telefono")
	private String cliTelefono;
	
	@Column(name="fac_sub_total")
	private String facSubTotal;
	
	@Column(name="fac_total")
	private String facTotal;
	
	@Column(name="fac_iva")
	private String facIva;
	
	@Column(name="fac_tipo")
	private String facTipo;
	
	@Column(name="fac_feca_pago")
	private String facFecaPago;
	
	public String getFacObservacion() {
		return facObservacion;
	}

	public void setFacObservacion(String facObservacion) {
		this.facObservacion = facObservacion;
	}

	@Id
	@Column(name="fac_id")
	private String fac_id;
	
	@Column(name="fac_observacion")
	private String facObservacion;
	
	
	public String getFacNumOriginal() {
		return facNumOriginal;
	}

	public void setFacNumOriginal(String facNumOriginal) {
		this.facNumOriginal = facNumOriginal;
	}

	public String getFacFechaFactura() {
		return facFechaFactura;
	}

	public void setFacFechaFactura(String facFechaFactura) {
		this.facFechaFactura = facFechaFactura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCliNumIdent() {
		return cliNumIdent;
	}

	public void setCliNumIdent(String cliNumIdent) {
		this.cliNumIdent = cliNumIdent;
	}

	public String getCliDireccion() {
		return cliDireccion;
	}

	public void setCliDireccion(String cliDireccion) {
		this.cliDireccion = cliDireccion;
	}

	public String getCliTelefono() {
		return cliTelefono;
	}

	public void setCliTelefono(String cliTelefono) {
		this.cliTelefono = cliTelefono;
	}

	public String getFacSubTotal() {
		return facSubTotal;
	}

	public void setFacSubTotal(String facSubTotal) {
		this.facSubTotal = facSubTotal;
	}

	public String getFacTotal() {
		return facTotal;
	}

	public void setFacTotal(String facTotal) {
		this.facTotal = facTotal;
	}

	public String getFacIva() {
		return facIva;
	}

	public void setFacIva(String facIva) {
		this.facIva = facIva;
	}

	public String getFacTipo() {
		return facTipo;
	}

	public void setFacTipo(String facTipo) {
		this.facTipo = facTipo;
	}

	public String getFacFecaPago() {
		return facFecaPago;
	}

	public void setFacFecaPago(String facFecaPago) {
		this.facFecaPago = facFecaPago;
	}

	public String getFac_id() {
		return fac_id;
	}

	public void setFac_id(String fac_id) {
		this.fac_id = fac_id;
	}


}