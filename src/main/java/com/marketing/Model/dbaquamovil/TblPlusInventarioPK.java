package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblPlusInventarioPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idPlu;
	
	private Integer idBodega;

	
	public TblPlusInventarioPK() {
		super();
	}
	public TblPlusInventarioPK(Integer idlocal,  Integer idPlu, Integer idBodega) {
		super();
		this.idLocal = idlocal;
		this.idPlu = idPlu;
		this.idBodega = idBodega;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public Integer geIdPlu() {
		return idPlu;
	}
	
	public Integer geIdBodega() {
		return idBodega;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdPlu(Integer idPlu) {
		this.idPlu = idPlu;
	}
	
	public void setIdBodega(Integer idBodega) {
		this.idBodega = idBodega;
	}

}
