package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblDctosOrdenesDetallePK  implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer IDLOCAL;
	
	
	private Integer IDTIPOORDEN;
	private Integer IDORDEN;
	private Integer IDPLU;
	
	
	public TblDctosOrdenesDetallePK() {
		super();
	}
	public TblDctosOrdenesDetallePK(Integer IDLOCAL,  Integer IDTIPOORDEN, Integer IDORDEN,  Integer IDPLU) {
		super();
		this.IDLOCAL = IDLOCAL;
		this.IDTIPOORDEN = IDTIPOORDEN;
		this.IDORDEN = IDORDEN;
		this.IDPLU = IDPLU;

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
	
	public Integer getIDPLU() {
		return IDPLU;
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
	
	public void setIDPLU(Integer IDPLU) {
		this.IDPLU = IDPLU;
	}
}
