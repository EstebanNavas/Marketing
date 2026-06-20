package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblDctosPeriodoPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idPeriodo;
	private Integer idCiclo;

	
	public TblDctosPeriodoPK() {
		super();
	}
	public TblDctosPeriodoPK(Integer idlocal,  Integer idPeriodo, Integer idCiclo) {
		super();
		this.idLocal = idlocal;
		this.idPeriodo = idPeriodo;
	    this.idCiclo = idCiclo;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public Integer getIdPeriodo() {
		return idPeriodo;
	}
	public Integer getIdCiclo() {
		return idCiclo;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public void setIdCiclo(Integer idCiclo) {
		this.idCiclo = idCiclo;
	}
}
