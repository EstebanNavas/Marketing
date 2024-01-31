package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblMedidoresMacroPK  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Integer idMacro;

	
	public TblMedidoresMacroPK() {
		super();
	}
	public TblMedidoresMacroPK(Integer idLocal,  Integer idMacro) {
		super();
		this.idLocal = idLocal;
		this.idMacro = idMacro;

	}
	public Integer getIDLOCAL() {
		return idLocal;
	}

	public Integer getIDTIPOORDEN() {
		return idMacro;
	}
	

	
	public void setIDLOCAL(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public void setIDTIPOORDEN(Integer idMacro) {
		this.idMacro = idMacro;
	}
	


}
