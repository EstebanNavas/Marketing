package com.marketing.Projection;

public interface TblDctosOrdenesDTO {

	Integer getIDLOCAL();
	Integer getIDTIPOORDEN();
	Integer getIDUSUARIO();
	Integer getIDORDEN();
	Integer getIDLOG();
	String getIdCliente();
	
	Double getVrCredito();
	Double getCantidad();
	String getFechaOrden();
	Double getVrCreditoPagado();
	String getNombreTercero();
	String getObservacion();
	Integer getItemPadre();
	Double getVrSaldo();
}
