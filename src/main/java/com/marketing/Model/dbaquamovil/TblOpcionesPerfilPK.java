package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblOpcionesPerfilPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idOpcion;
	
	
	private Integer idPerfil;
	private Integer idLocal;
	
	public TblOpcionesPerfilPK() {
		super();
	}
	public TblOpcionesPerfilPK(Integer idOpcion,  Integer idPerfil, Integer idLocal) {
		super();
		this.idOpcion = idOpcion;
		this.idPerfil = idPerfil;
		this.idLocal = idLocal;
	}
	public Integer getIdOpcion() {
		return idOpcion;
	}
	public Integer getIdPerfil() {
		return idPerfil;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public void setIdOpcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}
	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
}
