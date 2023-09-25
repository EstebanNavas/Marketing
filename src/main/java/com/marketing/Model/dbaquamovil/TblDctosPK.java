package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblDctosPK implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer IDLOCAL;
	
	
	private Integer IDTIPOORDEN;
	private Integer IDORDEN;
	private Integer idDcto;
	private Integer indicador;
	
	
	public TblDctosPK() {
		super();
	}
	public TblDctosPK(Integer IDLOCAL,  Integer IDTIPOORDEN, Integer IDORDEN,  Integer idDcto, Integer indicador) {
		super();
		this.IDLOCAL = IDLOCAL;
		this.IDTIPOORDEN = IDTIPOORDEN;
		this.IDORDEN = IDORDEN;
		this.idDcto = idDcto;
		this.indicador = indicador;

	}
	public Integer getIDLOCAL() {
		return IDLOCAL;
	}

	public Integer getIDTIPOORDEN() {
		return IDTIPOORDEN;
	}
	
	public Integer getIDORDEN() {
		return IDORDEN;
	}
	
	public Integer getIdDcto() {
		return idDcto;
	}
	
	public Integer getIndicador() {
		return indicador;
	}
	
	public void setIDLOCAL(Integer IDLOCAL) {
		this.IDLOCAL = IDLOCAL;
	}

	public void setIDTIPOORDEN(Integer IDTIPOORDEN) {
		this.IDTIPOORDEN = IDTIPOORDEN;
	}
	
	public void setIDORDEN(Integer IDORDEN) {
		this.IDORDEN = IDORDEN;
	}
	
	public void setIdDcto(Integer idDcto) {
		this.idDcto = idDcto;
	}
	
	public void setIndicador(Integer indicador) {
		this.indicador = indicador;
	}
}
