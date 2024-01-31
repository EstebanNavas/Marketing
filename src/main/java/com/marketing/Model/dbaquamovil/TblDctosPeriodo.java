package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tblDctosPeriodo")
@IdClass(TblDctosPeriodoPK.class)
public class TblDctosPeriodo {

	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name = "idPeriodo")
	private Integer idPeriodo;
	
	@Column(name = "nombrePeriodo")
	private String nombrePeriodo;
	
	@Column(name = "estadoFEDctos")
	private Integer estadoFEDctos;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getNombrePeriodo() {
		return nombrePeriodo;
	}

	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}

	public Integer getEstadoFEDctos() {
		return estadoFEDctos;
	}

	public void setEstadoFEDctos(Integer estadoFEDctos) {
		this.estadoFEDctos = estadoFEDctos;
	}
	
	
}
