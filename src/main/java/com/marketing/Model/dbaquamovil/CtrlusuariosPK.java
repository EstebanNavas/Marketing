package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class CtrlusuariosPK implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idUsuario;
	
	public CtrlusuariosPK() {
		super();
	}
	public CtrlusuariosPK(Integer idlocal,  Integer idUsuario) {
		super();
		this.idLocal = idlocal;
		this.idUsuario = idUsuario;

	}
	public Integer getIdLocal() {
		return idLocal;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
}
