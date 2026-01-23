package com.marketing.Projection;

import java.sql.Timestamp;

public interface TblDctosDTO5 {
	
	Integer getIdLocal();
	Integer getIdTipoOrden();
	Integer getIdDcto();
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
	
	Double getIdVendedor();
	
	String getCufe();
	String getQr();
	
	Double getVrRteIca();
	Double getVrRteIva();
	
	Double getVrDsctoFcro();
	
	Integer getDiasMora();
	Integer getIdPlu();
	String getNombrePlu();
	
	String getNombreVendedor();
	String getFechaTx();
	
	Integer getIDUSUARIO();
	String getNombreUsuario();
	Integer getIdPeriodo();
	
	Double getLatitud();
	Double getLongitud();
	
	String getFechaRegistroTx();
	
	Integer getItem();
	String getNOMBREPLU();
	Double getVRVENTAUNITARIO();
	Double getCANTIDAD();

}