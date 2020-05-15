package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the parametro_dominio database table.
 * 
 */
@Entity
@Table(name="parametro_dominio", schema="sfe")
@NamedQuery(name="ParametroDominio.findAll", query="SELECT p FROM ParametroDominio p")
public class ParametroDominio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="pad_descripcion")
	private String padDescripcion;

	@Id
	@Column(name="pad_id")
	private Integer padId;

	@Column(name="pad_valor")
	private String padValor;

	public ParametroDominio() {
	}

	public String getPadDescripcion() {
		return this.padDescripcion;
	}

	public void setPadDescripcion(String padDescripcion) {
		this.padDescripcion = padDescripcion;
	}

	public Integer getPadId() {
		return this.padId;
	}

	public void setPadId(Integer padId) {
		this.padId = padId;
	}

	public String getPadValor() {
		return this.padValor;
	}

	public void setPadValor(String padValor) {
		this.padValor = padValor;
	}

}