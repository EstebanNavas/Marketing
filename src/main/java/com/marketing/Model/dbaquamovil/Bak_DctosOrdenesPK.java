package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class Bak_DctosOrdenesPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer IDLOCAL;
	
	
	private Integer IDTIPOORDEN;
	private Integer IDORDEN;

	

	
	public Bak_DctosOrdenesPK() {
		super();
	}
	public Bak_DctosOrdenesPK(Integer IDLOCAL,  Integer IDTIPOORDEN, Integer IDORDEN) {
		super();
		this.IDLOCAL = IDLOCAL;
		this.IDTIPOORDEN = IDTIPOORDEN;
		this.IDORDEN = IDORDEN;

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

	
	

	public void setIDLOCAL(Integer IDLOCAL) {
		this.IDLOCAL = IDLOCAL;
	}
	public void setIDTIPOORDEN(Integer IDTIPOORDEN) {
		this.IDTIPOORDEN = IDTIPOORDEN;
	}
	public void setIDORDEN(Integer IDORDEN) {
		this.IDORDEN = IDORDEN;
	}

}
