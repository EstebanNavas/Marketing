package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblPagosPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idTipoOrden;
	private Integer idRecibo;
	private Integer indicador;

	

	
	public TblPagosPK() {
		super();
	}
	public TblPagosPK(Integer idlocal,  Integer idTipoOrden, Integer idRecibo, Integer indicador) {
		super();
		this.idLocal = idlocal;
		this.idTipoOrden = idTipoOrden;
		this.idRecibo = idRecibo;
		this.indicador = indicador;

		
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public Integer getIdTipoOrden() {
		return idTipoOrden;
	}
	public Integer getIdRecibo() {
		return idRecibo;
	}
	public Integer getIndicador() {
		return indicador;
	}

	
	

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdTipoOrden(Integer idTipoOrden) {
		this.idTipoOrden = idTipoOrden;
	}
	public void setIdRecibo(Integer idRecibo) {
		this.idRecibo = idRecibo;
	}
	public void setIndicador(Integer indicador) {
		this.indicador = indicador;
	}

	
}
