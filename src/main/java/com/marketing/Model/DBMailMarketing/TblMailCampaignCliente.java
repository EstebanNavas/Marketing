package com.marketing.Model.DBMailMarketing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.marketing.Model.DBMailMarketing.TblMailCampaignClientePK;

@Entity
@Table(name="tblMailCampaignCliente")
@IdClass(TblMailCampaignClientePK.class)
public class TblMailCampaignCliente {
	
	
	@Id
	@Column(name="idLocal")
	private Integer idLocal;

	@Id
	@Column(name="idCampaign")
	private Integer idCampaign;

	@Id
	@Column(name="idCliente")
	private String idCliente;
	

	public Integer getIdCampaign() {
		return idCampaign;
	}

	public void setIdCampaign(Integer idCampaign) {
		this.idCampaign = idCampaign;
	}

	public Integer getIdlocal() {
		return idLocal;
	}

	public void setIdlocal(Integer idlocal) {
		this.idLocal = idlocal;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	
	
}
