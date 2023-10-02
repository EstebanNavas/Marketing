package com.marketing.Model.DBMailMarketing;

import java.io.Serializable;

public class MailCampaignPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private String sistema;
	private Integer idCampaign;
	
	public MailCampaignPK() {
		super();
	}
	public MailCampaignPK(Integer idLocal,  Integer idCampaign,String sistema) {
		super();
		this.idLocal = idLocal;
		this.sistema = sistema;
		this.idCampaign = idCampaign;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public String getsSistema() {
		return sistema;
	}
	public Integer getIdTipoTercero() {
		return idCampaign;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public void setIdTipoTercero(Integer idTipoTercero) {
		this.idCampaign = idTipoTercero;
	}
}
