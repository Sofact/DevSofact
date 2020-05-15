package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CLIENTE_CLIID_GENERATOR", sequenceName="SEQ_CLIENTE" , allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CLIENTE_CLIID_GENERATOR")
	@Column(name="cli_id")
	private Integer cliId;

	@Column(name="cli_apellidos")
	private String cliApellidos;

	@Column(name="cli_celular")
	private String cliCelular;

	@Column(name="cli_direccion")
	private String cliDireccion;

	@Column(name="cli_email")
	private String cliEmail;

	@Column(name="cli_nombre")
	private String cliNombre;

	@Column(name="cli_num_ident")
	private String cliNumIdent;

	@Column(name="cli_segundo_nombre")
	private String cliSegundoNombre;

	@Column(name="cli_telefono")
	private String cliTelefono;

	@Column(name="cli_tipo_ident")
	private Integer cliTipoIdent;

	@Column(name="fecha_creacion")
	private Date fechaCreacion;

	@Column(name="fecha_modificacion")
	private Date fechaModifica;

	@Column(name="muni_id")
	private Integer muniId;

	@Column(name="tpc_id")
	private Integer tpcId;

	@Column(name="uep_id")
	private Integer uepId;

	@Column(name="user_modifica")
	private Integer userModifica;

	public Cliente() {
	}

	public Integer getCliId() {
		return this.cliId;
	}

	public void setCliId(Integer cliId) {
		this.cliId = cliId;
	}

	public String getCliApellidos() {
		return this.cliApellidos;
	}

	public void setCliApellidos(String cliApellidos) {
		this.cliApellidos = cliApellidos;
	}

	public String getCliCelular() {
		return this.cliCelular;
	}

	public void setCliCelular(String cliCelular) {
		this.cliCelular = cliCelular;
	}

	public String getCliDireccion() {
		return this.cliDireccion;
	}

	public void setCliDireccion(String cliDireccion) {
		this.cliDireccion = cliDireccion;
	}

	public String getCliEmail() {
		return this.cliEmail;
	}

	public void setCliEmail(String cliEmail) {
		this.cliEmail = cliEmail;
	}

	public String getCliNombre() {
		return this.cliNombre;
	}

	public void setCliNombre(String cliNombre) {
		this.cliNombre = cliNombre;
	}

	public String getCliNumIdent() {
		return this.cliNumIdent;
	}

	public void setCliNumIdent(String cliNumIdent) {
		this.cliNumIdent = cliNumIdent;
	}

	public String getCliSegundoNombre() {
		return this.cliSegundoNombre;
	}

	public void setCliSegundoNombre(String cliSegundoNombre) {
		this.cliSegundoNombre = cliSegundoNombre;
	}

	public String getCliTelefono() {
		return this.cliTelefono;
	}

	public void setCliTelefono(String cliTelefono) {
		this.cliTelefono = cliTelefono;
	}

	public Integer getCliTipoIdent() {
		return this.cliTipoIdent;
	}

	public void setCliTipoIdent(Integer cliTipoIdent) {
		this.cliTipoIdent = cliTipoIdent;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date date) {
		this.fechaCreacion = date;
	}

	public Date getFechaModifica() {
		return this.fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	public Integer getMuniId() {
		return this.muniId;
	}

	public void setMuniId(Integer muniId) {
		this.muniId = muniId;
	}

	public Integer getTpcId() {
		return this.tpcId;
	}

	public void setTpcId(Integer tpcId) {
		this.tpcId = tpcId;
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

}