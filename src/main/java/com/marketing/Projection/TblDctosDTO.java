package com.marketing.Projection;

import java.sql.Date;
import java.sql.Timestamp;

public interface TblDctosDTO {
	

	Integer getIdLocal();
	Integer getIdTipoOrden();
	Double getIdDcto();
	Integer getIdOrden();
	String getFechaDcto();
	Double getIndicador();
	String getIdCliente();
	Double getVrBase();
	Double getVrIva();
	Double getVrCostoMV();
	String getNombreTercero();
	String getIdDctoNitCC();
	Timestamp getFechaDctoNitCC();
	Integer getIdLocalCruce();
	Integer getIdTipoOrdenCruce();
	Double getIdDctoCruce();
	Double getVrImpoconsumo();
	Double getVrFactura();
	String getFechaVcto();
	
	Double getVrDescuento();
	Double getVrRteFuente();
	Integer getIdDctoStr();
	String getNombreTipoNegocio();
	Double getIdTipoNegocio();
	String getAliasUsuario();
	String getNombreRuta();
	String getNombreEstrato();
	String getOBSERVACION();
	String getCC_Nit();
	
	Double getVrSaldo();
	Double getVrAbono();
	String getFechaAbono();
	String getNumeroMedidor();
	
	Double getVrVentaUnitario();
	
	String getObservacion();
	
	String getDireccionTercero();
	Integer getOrdenRuta();
	Double getVrPago();
	Integer getIdRuta();
	
	
}
