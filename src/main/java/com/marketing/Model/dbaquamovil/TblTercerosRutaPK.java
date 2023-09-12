package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblTercerosRutaPK implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idRuta;

	
	public TblTercerosRutaPK() {
		super();
	}
	public TblTercerosRutaPK(Integer idlocal,  Integer idRuta) {
		super();
		this.idLocal = idlocal;
		this.idRuta = idRuta;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public Integer getIdRuta() {
		return idRuta;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}

}
