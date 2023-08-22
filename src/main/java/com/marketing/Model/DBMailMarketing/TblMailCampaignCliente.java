package com.marketing.Model.DBMailMarketing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblMailCampaignCliente")
public class TblMailCampaignCliente {

	@Id
	@Column(name="idCampaign")
	private Integer idCampaign;
	
	@Column(name="idlocal")
	private Integer idlocal;
	
	@Column(name="idCliente")
	private Integer idCliente;
	
	@Column(name="telefonoCelular")
	private String telefonoCelular;
}
