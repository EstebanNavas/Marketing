package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblPlusPK  implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idPlu;
	
	public TblPlusPK() {
		super();
	}
	public TblPlusPK(Integer idlocal,  Integer idPlu) {
		super();
		this.idLocal = idlocal;
		this.idPlu = idPlu;

	}
	public Integer getIdLocal() {
		return idLocal;
	}

	public Integer getIdPlu() {
		return idPlu;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public void setIdPlu(Integer idPlu) {
		this.idPlu = idPlu;
	}
}
