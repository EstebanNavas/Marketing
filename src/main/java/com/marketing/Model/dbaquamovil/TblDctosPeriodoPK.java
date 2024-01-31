package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblDctosPeriodoPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idPeriodo;

	
	public TblDctosPeriodoPK() {
		super();
	}
	public TblDctosPeriodoPK(Integer idlocal,  Integer idPeriodo) {
		super();
		this.idLocal = idlocal;
		this.idPeriodo = idPeriodo;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public Integer getIdRuta() {
		return idPeriodo;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdRuta(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
}
