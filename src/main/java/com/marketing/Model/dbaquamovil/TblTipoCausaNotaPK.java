package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblTipoCausaNotaPK implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idTipoTabla;
	
	
	private Integer idCausa;

	
	public TblTipoCausaNotaPK() {
		super();
	}
	public TblTipoCausaNotaPK(Integer idTipoTabla,  Integer idCausa) {
		super();
		this.idTipoTabla = idTipoTabla;
		this.idCausa = idCausa;

	}
	public Integer getIdTipoTabla() {
		return idTipoTabla;
	}
	public Integer getIdCausa() {
		return idCausa;
	}

	public void setIdTipoTabla(Integer idTipoTabla) {
		this.idTipoTabla = idTipoTabla;
	}
	public void setIdCausa(Integer idCausa) {
		this.idCausa = idCausa;
	}

}
