package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tblLineas")
@IdClass(TblLineasPK.class)
public class TblLineas {

	@Id
	@Column(name= "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name= "idLinea")
	private Integer idLinea;
	
	@Column(name= "nombreLinea")
	private String nombreLinea;
	
	@Column(name= "estado")
	private Integer estado;
	
	@Column(name= "idSeq")
	private Integer idSeq;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Integer idLinea) {
		this.idLinea = idLinea;
	}

	public String getNombreLinea() {
		return nombreLinea;
	}

	public void setNombreLinea(String nombreLinea) {
		this.nombreLinea = nombreLinea;
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
	
	
	
}
