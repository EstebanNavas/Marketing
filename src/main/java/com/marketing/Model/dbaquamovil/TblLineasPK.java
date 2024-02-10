package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblLineasPK  implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idLinea;


	
	
	public TblLineasPK() {
		super();
	}
	public TblLineasPK(Integer idLocal,  Integer idLinea) {
		super();
		this.idLocal = idLocal;
		this.idLinea = idLinea;


	}
	public Integer getIdLocal() {
		return idLocal;
	}

	public Integer getIdLinea() {
		return idLinea;
	}

	
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public void setIdLinea(Integer idLinea) {
		this.idLinea = idLinea;
	}
	

}
