package model;
// Generated 12-jun-2018 19:07:14 by Hibernate Tools 5.2.8.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UnidadMedida generated by hbm2java
 */
@Entity
@Table(name = "unidad_medida", schema = "sfe")
public class UnidadMedida implements java.io.Serializable {

	private int unmId;
	private String unmDescripcion;
	private String unmRelacion;
	private char unmEstado;
	private int uepId;
	private Date fechaCreacion;
	private Integer userModifica;
	private Date fechaModifica;
	private Set<ProductoValorMedida> productoValorMedidas = new HashSet<ProductoValorMedida>(0);

	public UnidadMedida() {
	}

	public UnidadMedida(int unmId, String unmDescripcion, char unmEstado, int uepId, Date fechaCreacion) {
		this.unmId = unmId;
		this.unmDescripcion = unmDescripcion;
		this.unmEstado = unmEstado;
		this.uepId = uepId;
		this.fechaCreacion = fechaCreacion;
	}

	public UnidadMedida(int unmId, String unmDescripcion, String unmRelacion, char unmEstado, int uepId,
			Date fechaCreacion, Integer userModifica, Date fechaModifica,
			Set<ProductoValorMedida> productoValorMedidas) {
		this.unmId = unmId;
		this.unmDescripcion = unmDescripcion;
		this.unmRelacion = unmRelacion;
		this.unmEstado = unmEstado;
		this.uepId = uepId;
		this.fechaCreacion = fechaCreacion;
		this.userModifica = userModifica;
		this.fechaModifica = fechaModifica;
		this.productoValorMedidas = productoValorMedidas;
	}

	@Id
	@SequenceGenerator(name="MEDIDA_GENERATOR", sequenceName="SEQ_UNIDAD_MEDIDA" , allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MEDIDA_GENERATOR" )
	@Column(name = "unm_id", unique = true, nullable = false)
	public int getUnmId() {
		return this.unmId;
	}

	public void setUnmId(int unmId) {
		this.unmId = unmId;
	}

	@Column(name = "unm_descripcion", nullable = false, length = 250)
	public String getUnmDescripcion() {
		return this.unmDescripcion;
	}

	public void setUnmDescripcion(String unmDescripcion) {
		this.unmDescripcion = unmDescripcion;
	}

	@Column(name = "unm_relacion", length = 10)
	public String getUnmRelacion() {
		return this.unmRelacion;
	}

	public void setUnmRelacion(String unmRelacion) {
		this.unmRelacion = unmRelacion;
	}

	@Column(name = "unm_estado", nullable = false, length = 1)
	public char getUnmEstado() {
		return this.unmEstado;
	}

	public void setUnmEstado(char unmEstado) {
		this.unmEstado = unmEstado;
	}

	@Column(name = "uep_id", nullable = false)
	public int getUepId() {
		return this.uepId;
	}

	public void setUepId(int uepId) {
		this.uepId = uepId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Column(name = "user_modifica")
	public Integer getUserModifica() {
		return this.userModifica;
	}

	public void setUserModifica(Integer userModifica) {
		this.userModifica = userModifica;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modifica", length = 29)
	public Date getFechaModifica() {
		return this.fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidadMedida")
	public Set<ProductoValorMedida> getProductoValorMedidas() {
		return this.productoValorMedidas;
	}

	public void setProductoValorMedidas(Set<ProductoValorMedida> productoValorMedidas) {
		this.productoValorMedidas = productoValorMedidas;
	}

}
