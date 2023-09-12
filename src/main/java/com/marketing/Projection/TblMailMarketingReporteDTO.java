package com.marketing.Projection;

import java.sql.Timestamp;
import java.util.List;

import com.marketing.Model.DBMailMarketing.TblMailCampaignCliente;
import com.marketing.Model.dbaquamovil.TblTerceros;
public interface TblMailMarketingReporteDTO {

	Integer getIdReporte();
	Integer getIdLocal();
	String getSistema();
	Integer getIdCampaign();
	Integer getIdPlantilla();
	Integer getIdDcto();
	Integer getIdRequerimiento();
	String getDocumentoTercero();
	Integer getEstado();
	String getDescripcion();
	Timestamp getFechaHoraEvento();
	String getException();
	String getEmail();
	String getCelular();
	String getIdCliente();
	
	
	//TblTerceros getTercero();
	
	
	//TblMailCampaignCliente
	
	//TblMailCampaignCliente  getTblMailCampaignCliente();
	
	
	
	
}
