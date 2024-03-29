package model;
// Generated 12-jun-2018 19:07:14 by Hibernate Tools 5.2.8.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Departamento generated by hbm2java
 */
@Entity
@Table(name = "departamento", schema = "sfe")
public class Departamento implements java.io.Serializable {

	private int depId;
	private String depDescripcion;
	private char depEstado;
	private int uepId;
	private Date fechaCreacion;
	private Integer userModifica;
	private Date fechaModifica;

	public Departamento() {
	}

	public Departamento(int depId, String depDescripcion, char depEstado, int uepId, Date fechaCreacion) {
		this.depId = depId;
		this.depDescripcion = depDescripcion;
		this.depEstado = depEstado;
		this.uepId = uepId;
		this.fechaCreacion = fechaCreacion;
	}

	public Departamento(int depId, String depDescripcion, char depEstado, int uepId, Date fechaCreacion,
			Integer userModifica, Date fechaModifica) {
		this.depId = depId;
		this.depDescripcion = depDescripcion;
		this.depEstado = depEstado;
		this.uepId = uepId;
		this.fechaCreacion = fechaCreacion;
		this.userModifica = userModifica;
		this.fechaModifica = fechaModifica;
	}

	@Id

	@Column(name = "dep_id", unique = true, nullable = false)
	public int getDepId() {
		return this.depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	@Column(name = "dep_descripcion", nullable = false, length = 250)
	public String getDepDescripcion() {
		return this.depDescripcion;
	}

	public void setDepDescripcion(String depDescripcion) {
		this.depDescripcion = depDescripcion;
	}

	@Column(name = "dep_estado", nullable = false, length = 1)
	public char getDepEstado() {
		return this.depEstado;
	}

	public void setDepEstado(char depEstado) {
		this.depEstado = depEstado;
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

}
