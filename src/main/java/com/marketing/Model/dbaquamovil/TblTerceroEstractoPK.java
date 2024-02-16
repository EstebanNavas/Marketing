package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblTerceroEstractoPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idEstracto;

	
	public TblTerceroEstractoPK() {
		super();
	}
	public TblTerceroEstractoPK(Integer idlocal,  Integer idEstracto) {
		super();
		this.idLocal = idlocal;
		this.idEstracto = idEstracto;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public Integer getIdEstracto() {
		return idEstracto;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdEstracto(Integer idEstracto) {
		this.idEstracto = idEstracto;
	}

}
