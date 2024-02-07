package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblCategoriasPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idLinea;
	private Integer IdCategoria;
	
	public TblCategoriasPK() {
		super();
	}
	public TblCategoriasPK(Integer idlocal,  Integer idLinea, Integer IdCategoria) {
		super();
		this.idLocal = idlocal;
		this.idLinea = idLinea;
		this.IdCategoria = IdCategoria;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public Integer getIdLinea() {
		return idLinea;
	}
	public Integer getIdCategoria() {
		return IdCategoria;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdLinea(Integer idLinea) {
		this.idLinea = idLinea;
	}
	public void setIdCategoria(Integer IdCategoria) {
		this.IdCategoria = IdCategoria;
	}
}
