package com.marketing.Model.DBMailMarketing;

import java.io.Serializable;

public class TblPucAuxPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idCuentaAux;
	
	public TblPucAuxPK() {
		super();
	}
	public TblPucAuxPK(Integer idLocal,  Integer idCuentaAux) {
		super();
		this.idLocal = idLocal;
		this.idCuentaAux = idCuentaAux;

	}
	public Integer getIdLocal() {
		return idLocal;
	}

	public Integer getIdCuentaAux() {
		return idCuentaAux;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public void setIdCuentaAux(Integer idCuentaAux) {
		this.idCuentaAux = idCuentaAux;
	}

}
