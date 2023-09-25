package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblAgendaLogVisitasPK  implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer IDLOG;
	
	public TblAgendaLogVisitasPK() {
		super();
	}
	public TblAgendaLogVisitasPK(Integer idlocal,  Integer IDLOG) {
		super();
		this.idLocal = idlocal;
		this.IDLOG = IDLOG;

	}
	public Integer getIdLocal() {
		return idLocal;
	}

	public Integer getIDLOG() {
		return IDLOG;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public void setIDLOG(Integer IDLOG) {
		this.IDLOG = IDLOG;
	}
}
