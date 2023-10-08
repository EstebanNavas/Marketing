package com.marketing.Projection;

import java.sql.Timestamp;

public interface ReporteSuiDTO {

	//tblDctosOrdenes
	Integer getIDLOCAL();
	Integer getIDTIPOORDEN();
	Integer getIDUSUARIO();
	Integer getIDORDEN();
	Integer getIDLOG();
	String getIdCliente();
	String getFECHAENTREGA();
	Integer getNumeroOrden();
	String getOrdenCompra();
	Timestamp getFECHAORDEN();
	String getfechaRadicacion();
	
	//tblCiudades
	Integer getIdDpto();
	
	//tblDctos
	Integer getIdDcto();
	String getFechaDcto();
	String getFechaEjecucion();
	
	//tblLocales
	String getIdCiudad();
	
	//tblPlus
	Integer getTipoTramite();
	String getNombreCausa();
	Integer getTipoRespuesta();
	Integer getTipoNotificacion();
	
	
	Integer getTipoAcentamiento();
	String getCodigoCausaResolucion();
	
	
}
