package com.marketing.Model.DBMailMarketing;

import java.io.Serializable;

public class TblMailCampaignClientePK  implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private String idCliente;
	private Integer idCampaign;
	
	public TblMailCampaignClientePK() {
		super();
	}
	public TblMailCampaignClientePK(Integer idLocal,  Integer idCampaign,String idCliente) {
		super();
		this.idLocal = idLocal;
		this.idCliente = idCliente;
		this.idCampaign = idCampaign;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public Integer getIdTipoTercero() {
		return idCampaign;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public void setIdTipoTercero(Integer idTipoTercero) {
		this.idCampaign = idTipoTercero;
	}
}
