package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblTercerosSuiPK implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private String idCliente;
	
	public TblTercerosSuiPK() {
		super();
	}
	public TblTercerosSuiPK(Integer idlocal, String idcliente) {
		super();
		this.idLocal = idlocal;
		this.idCliente = idcliente;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public String getIdCliente() {
		return idCliente;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

}
