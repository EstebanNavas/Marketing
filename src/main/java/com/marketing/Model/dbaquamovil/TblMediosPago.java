package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tblMediosPago")
@IdClass(TblMediosPagoPK.class)
public class TblMediosPago {

	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name = "idMedio")
	private Integer idMedio;
	
	@Column(name = "nombreMedio")
	private String nombreMedio;
	
	@Column(name = "estado")
	private Integer estado;
	
	@Column(name = "idSeq")
	private Integer idSeq;
	
	@Column(name = "textoMedio")
	private String textoMedio;
	
	@Column(name = "cuentaContable")
	private String cuentaContable;
	
	@Column(name = "cuentaCxC")
	private String cuentaCxC;
	
	@Column(name = "idConvenio")
	private String idConvenio;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdMedio() {
		return idMedio;
	}

	public void setIdMedio(Integer idMedio) {
		this.idMedio = idMedio;
	}

	public String getNombreMedio() {
		return nombreMedio;
	}

	public void setNombreMedio(String nombreMedio) {
		this.nombreMedio = nombreMedio;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getIdSeq() {
		return idSeq;
	}

	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}

	public String getTextoMedio() {
		return textoMedio;
	}

	public void setTextoMedio(String textoMedio) {
		this.textoMedio = textoMedio;
	}

	public String getCuentaContable() {
		return cuentaContable;
	}

	public void setCuentaContable(String cuentaContable) {
		this.cuentaContable = cuentaContable;
	}

	public String getCuentaCxC() {
		return cuentaCxC;
	}

	public void setCuentaCxC(String cuentaCxC) {
		this.cuentaCxC = cuentaCxC;
	}

	public String getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(String idConvenio) {
		this.idConvenio = idConvenio;
	}
	
	
}
