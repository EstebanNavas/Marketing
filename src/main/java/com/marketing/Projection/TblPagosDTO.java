package com.marketing.Projection;

public interface TblPagosDTO {
	
	String getIdCliente();
	String getNombreTercero();
	Integer getIdLocal();
	Integer getIdTipoOrden();
	Integer getIdRecibo();
	Integer getIndicador();
	String getFechaPago();
//	Double getvrPago();
	Double getVrRteFuente();
	Double getVrDescuento();
	Double getVrRteIva();
	Double getIdDcto();
	String getIdDctoNitCC();
	Double getIdPlanilla();
	Double getVrRteIca();
	String getAliasUsuario();
	Integer getIdPeriodo();
	Integer getIdMedio();
	String getNombreMedio();
	String getNombreRuta();
	String getNombreEstracto();
	String getNitCC();
	
	String getNombrePlu();
	Double getVrMedio();
	Integer getIdPlu(); 

	
	String getNombreCliente();
	Double getEstado();
	Double getIdUsuario();
	Double getVrSaldo();
	
	String getNombreServicio();
	Double getValorPago();
	Integer getIdVendedor();
	
	Integer getNumeroDcto();
	Double getVrPago();
	
}
