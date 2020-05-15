package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the "RESOLUCION_FACTURACION" database table.
 * 
 */
@Entity
@Table(name="\"RESOLUCION_FACTURACION\"")
@NamedQuery(name="ResolucionFacturacion.findAll", query="SELECT r FROM ResolucionFacturacion r")
public class ResolucionFacturacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ref_id")
	private Integer refId;

	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Column(name="fecha_modifica")
	private Timestamp fechaModifica;

	@Column(name="ref_descripcion")
	private String refDescripcion;

	@Column(name="ref_estado")
	private String refEstado;

	@Temporal(TemporalType.DATE)
	@Column(name="ref_fecha_inicio")
	private Date refFechaInicio;

	@Column(name="ref_fin_consecutivo")
	private String refFinConsecutivo;

	@Column(name="ref_inicio_consecutivo")
	private String refInicioConsecutivo;

	@Column(name="ref_prefijo")
	private String refPrefijo;

	@Column(name="uep_id")
	private Integer uepId;

	@Column(name="user_modifica")
	private Integer userModifica;
	
	@Column(name="ref_software_id")
	private String refSoftwareId;
	
	@Column(name="ref_security_code")
	private String refSecurityCode;
	
	@Column(name="ref_tipo")
	private String refTipo;

	public ResolucionFacturacion() {
	}
	
	public String getRefTipo() {
		return this.refTipo;
	}
	
	public void setRefTipo(String refTipo) {
		this.refTipo = refTipo;
	}

	public Integer getRefId() {
		return this.refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Timestamp getFechaModifica() {
		return this.fechaModifica;
	}

	public void setFechaModifica(Timestamp fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	public String getRefDescripcion() {
		return this.refDescripcion;
	}

	public void setRefDescripcion(String refDescripcion) {
		this.refDescripcion = refDescripcion;
	}

	public String getRefEstado() {
		return this.refEstado;
	}

	public void setRefEstado(String refEstado) {
		this.refEstado = refEstado;
	}

	public Date getRefFechaInicio() {
		return this.refFechaInicio;
	}

	public void setRefFechaInicio(Date refFechaInicio) {
		this.refFechaInicio = refFechaInicio;
	}

	public String getRefFinConsecutivo() {
		return this.refFinConsecutivo;
	}

	public void setRefFinConsecutivo(String refFinConsecutivo) {
		this.refFinConsecutivo = refFinConsecutivo;
	}

	public String getRefInicioConsecutivo() {
		return this.refInicioConsecutivo;
	}

	public void setRefInicioConsecutivo(String refInicioConsecutivo) {
		this.refInicioConsecutivo = refInicioConsecutivo;
	}

	public String getRefPrefijo() {
		return this.refPrefijo;
	}

	public void setRefPrefijo(String refPrefijo) {
		this.refPrefijo = refPrefijo;
	}

	public Integer getUepId() {
		return this.uepId;
	}

	public void setUepId(Integer uepId) {
		this.uepId = uepId;
	}

	public Integer getUserModifica() {
		return this.userModifica;
	}

	public void setUserModifica(Integer userModifica) {
		this.userModifica = userModifica;
	}

	public String getRefSoftwareId() {
		return refSoftwareId;
	}

	public void setRefSoftwareId(String refSoftwareId) {
		this.refSoftwareId = refSoftwareId;
	}

	public String getRefSecurityCode() {
		return refSecurityCode;
	}

	public void setRefSecurityCode(String refSecurityCode) {
		this.refSecurityCode = refSecurityCode;
	}

}