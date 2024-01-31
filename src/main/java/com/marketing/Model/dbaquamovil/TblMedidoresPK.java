package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblMedidoresPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idMedidor;

	
	public TblMedidoresPK() {
		super();
	}
	public TblMedidoresPK(Integer idLocal,  Integer idMedidor) {
		super();
		this.idLocal = idLocal;
		this.idMedidor = idMedidor;

	}
	public Integer getIDLOCAL() {
		return idLocal;
	}

	public Integer getIDTIPOORDEN() {
		return idMedidor;
	}
	

	
	public void setIDLOCAL(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public void setIDTIPOORDEN(Integer idMedidor) {
		this.idMedidor = idMedidor;
	}

}
