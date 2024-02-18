package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblMediosPagoPK implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idMedio;

	
	public TblMediosPagoPK() {
		super();
	}
	public TblMediosPagoPK(Integer idlocal,  Integer idMedio) {
		super();
		this.idLocal = idlocal;
		this.idMedio = idMedio;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public Integer geIdMedio() {
		return idMedio;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdMedio(Integer idMedio) {
		this.idMedio = idMedio;
	}
}
