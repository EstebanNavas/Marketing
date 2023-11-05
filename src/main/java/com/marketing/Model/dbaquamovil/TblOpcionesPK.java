package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblOpcionesPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idOpcion;
	
	private Integer idLocal;
	
	public TblOpcionesPK() {
		super();
	}
	public TblOpcionesPK(Integer idOpcion,  Integer idLocal) {
		super();
		this.idOpcion = idOpcion;
		this.idLocal = idLocal;
	}
	public Integer getIdOpcion() {
		return idOpcion;
	}

	public Integer getIdLocal() {
		return idLocal;
	}
	public void setIdOpcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
}
