package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblPagosMediosPK  implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idTipoOrden;
	private Integer idRecibo;
	private Integer indicador;
	private Integer idMedio;
	private Integer idDctoMedio;
	private Integer IDPLU;
	

	
	public TblPagosMediosPK() {
		super();
	}
	public TblPagosMediosPK(Integer idlocal,  Integer idTipoOrden, Integer idRecibo, Integer indicador, Integer idMedio, Integer idDctoMedio, Integer IDPLU) {
		super();
		this.idLocal = idlocal;
		this.idTipoOrden = idTipoOrden;
		this.idRecibo = idRecibo;
		this.indicador = indicador;
		this.idMedio = idMedio;
		this.idDctoMedio = idDctoMedio;
		this.IDPLU = IDPLU;
		
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
	public Integer getIdMedio() {
		return idMedio;
	}
	public Integer getIdDctoMedio() {
		return idDctoMedio;
	}
	public Integer getIDPLU() {
		return IDPLU;
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
	public void setIdMedio(Integer idMedio) {
		this.idMedio = idMedio;
	}
	public void setIdDctoMedio(Integer idDctoMedio) {
		this.idDctoMedio = idDctoMedio;
	}
	public void setIDPLU(Integer IDPLU) {
		this.IDPLU = IDPLU;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
