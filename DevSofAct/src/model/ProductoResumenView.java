package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the producto_resumen_view database table.
 * 
 */
@Entity
@Table(name="producto_resumen_view")
@NamedQuery(name="ProductoResumenView.findAll", query="SELECT p FROM ProductoResumenView p")
public class ProductoResumenView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="mar_descripcion")
	private String marDescripcion;

	@Column(name="mar_id")
	private Integer marId;

	@Column(name="pro_descripcion")
	private String proDescripcion;

	@Column(name="pro_id")
	private Integer proId;

	@Column(name="pro_referencia")
	private String proReferencia;

	@Id
	@Column(name="pvm_id")
	private Integer pvmId;

	@Column(name="pvm_valor")
	private double pvmValor;

	@Column(name="tip_descripcion")
	private String tipDescripcion;

	@Column(name="tip_id")
	private Integer tipId;

	@Column(name="unm_descripcion")
	private String unmDescripcion;

	@Column(name="unm_id")
	private Integer unmId;
	
	@Column(name="pvm_cantidad")
	private Integer pvmCantidad;

	public Integer getPvmCantidad() {
		return pvmCantidad;
	}

	public void setPvmCantidad(Integer pvmCantidad) {
		this.pvmCantidad = pvmCantidad;
	}

	public ProductoResumenView() {
	}

	public String getMarDescripcion() {
		return this.marDescripcion;
	}

	public void setMarDescripcion(String marDescripcion) {
		this.marDescripcion = marDescripcion;
	}

	public Integer getMarId() {
		return this.marId;
	}

	public void setMarId(Integer marId) {
		this.marId = marId;
	}

	public String getProDescripcion() {
		return this.proDescripcion;
	}

	public void setProDescripcion(String proDescripcion) {
		this.proDescripcion = proDescripcion;
	}

	public Integer getProId() {
		return this.proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public String getProReferencia() {
		return this.proReferencia;
	}

	public void setProReferencia(String proReferencia) {
		this.proReferencia = proReferencia;
	}

	public Integer getPvmId() {
		return this.pvmId;
	}

	public void setPvmId(Integer pvmId) {
		this.pvmId = pvmId;
	}

	public double getPvmValor() {
		return this.pvmValor;
	}

	public void setPvmValor(double pvmValor) {
		this.pvmValor = pvmValor;
	}

	public String getTipDescripcion() {
		return this.tipDescripcion;
	}

	public void setTipDescripcion(String tipDescripcion) {
		this.tipDescripcion = tipDescripcion;
	}

	public Integer getTipId() {
		return this.tipId;
	}

	public void setTipId(Integer tipId) {
		this.tipId = tipId;
	}

	public String getUnmDescripcion() {
		return this.unmDescripcion;
	}

	public void setUnmDescripcion(String unmDescripcion) {
		this.unmDescripcion = unmDescripcion;
	}

	public Integer getUnmId() {
		return this.unmId;
	}

	public void setUnmId(Integer unmId) {
		this.unmId = unmId;
	}

}