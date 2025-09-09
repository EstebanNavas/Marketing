package com.marketing.Projection;

import java.sql.Timestamp;

public interface TblDctosDTO3 {

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
	String getIdDctoStr();
	String getNombreTipoNegocio();
	Integer getIdTipoNegocio();
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
	
	String getFechaDctoSiigo();
	String getSiglaMoneda();
	String getTextVacio();
	String getIdSubcuenta();
	Double getVrDebito();
	Double getVrCredito();
	String getComentario();
	Integer getIdRuta();
	
	String getCodCuentaContable();
	Integer getIdPlu();
}
