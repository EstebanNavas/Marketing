package com.marketing.Projection;

public interface TblAgendaEventoLogDTO {

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
	
	
	Integer getIdDcto();
	Integer getIdLocal();
	Integer getIdPeriodo();
	Integer getIdOrden();
	String getFechaDcto();
	Double getPromedio();
	String getTextoFinanciacion();
	Integer getCuotaVencida();
	String getEmailSuscriptor();
	String getDireccionTercero();
	String getHistoriaConsumo();
	String getNumeroMedidor();
	String getMarcaMedidor();
	String getCC_Nit();
	String getDireccionCobro();
	String getCodigoCatastral();
	Integer getOrdenRuta();
	Integer getIdRuta();
	String getNombreRuta();
	Integer getIdEstracto();
	String getNombreEstracto();
	String getFechaInicial();
	String getFechaConRecargo();
	String getFechaFinal();
	String getFechaSinRecargo();
	String getNombrePeriodo();
	Double getLecturaActual();
	Double getLecturaAnterior();
	String getNombreCausa();
	Double getVrBase();
	Double getVrTotalPagar();
	Integer getIdPlu();
	String getNombrePlu();
	String getClaseUso();
	Double getVrVentaUnitario();
	Double getPorcentajeIva();
	Double getVrVentaUnitarioSinIva();
	String getTxtSuspension();
	String getCufe();
	Double getVrIvaVenta();
	String getVrPagado();
	String getFechaPagado();
	String getComentario();
	Double getVrIvaUnitario();
	Integer getConsumo();
	Double getVrDeudaAnterior();
	Double getVrIva();
	String getFechaPagoUltimo();
	Double getVrPagoUltimo();
	Double getPromedioEstrato();
	String getFechaInstalacionMedidor();
	Integer getIdProducto();
	Integer getIdTipo();
	
	
	String getFechaEvento();
	String getDataEnvio();
	String getDataRecibo();
}
