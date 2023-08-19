package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblTercerosPK implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private String idCliente;
	private Integer idTipoTercero;
	
	public TblTercerosPK() {
		super();
	}
	public TblTercerosPK(Integer idlocal,  Integer idtipotercero,String idcliente) {
		super();
		this.idLocal = idlocal;
		this.idCliente = idcliente;
		this.idTipoTercero = idtipotercero;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public Integer getIdTipoTercero() {
		return idTipoTercero;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public void setIdTipoTercero(Integer idTipoTercero) {
		this.idTipoTercero = idTipoTercero;
	}
}
