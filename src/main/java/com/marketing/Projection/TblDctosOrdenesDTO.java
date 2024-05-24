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
	
	String getNombreEstadoCliente();

	
	Double getVrConsumo();
	Double getVrCargoFijo();
	Double getVrDeudaCargoFijo();
	Double getVrDeudaConsumo();
	Double getVrDeudaReconexion();
	Double getVrDeudaConexion();
	Double getVrDeudaSuspencion();
	Double getVrDeudaReinstalacion();
	Double getVrDeudaOtros();
	Double getVrDeudaCargoCorte();
	Double getVrDeudaMedidor();
	Double getVrFinanciacion();
	Double getVrDeudaFinanciacion();
	Double getVrMedidor();
	Double getVrConexion();
	Double getVrReconexion();
	Double getVrReinstalacion();
	Double getVrSuspencion();
	Double getVrCargoCorte();
	Double getVrOtros();
	Double getVrPago();
	Double getVrSubsidio();
	Double getVrSubsidioCargoFijo();
	Double getVrSubsidioConsumo();
	Double getVrContribucion();
	Double getVrContribucionConsumo();
	Double getVrContribucionCargoFijo();
	Double getVrInteres();
	Double getVrAlcantarillaConsumo();
	Double getVrAlcantarillaCargoFijo();
	Double getVrAjusteDecena();
	Double getCantidadRango1();
	Double getVrDeudaAjusteDecena();
	Double getVrSubsidioCargoFijoAlcantarillado();
	Double getVrSubsidioConsumoAlcantarillado();
	Double getVrContribucionCargoFijoAlcantarillado();
	Double getVrContribucionConsumoAlcantarillado();
	Double getVrDeudaContribucion();
	Double getVrDeudaSubsidio();
	Double getVrInteresPeriodo();
	Double getVrInteresAcum();
	
	
	Integer getCuenta();
	
	Double getDescuentoComercial();
	
	Integer getCantidadArticulos();
	Double getVrVentaSinIva();
	Double getVrVentaConIva();
	
	
	String getEmail();
	String getFax();
	String getContacto();
	String getDireccionDespacho();
	String getCiudadDespacho();
	String getFormaPago();
	String getOrdenCompra();
	Double getImpuestoVenta();
	Integer getIdRazon();
	
	Double getVrInteresADiferir();
	Double getPorcentajeInteresADiferir();
	Double getVrTotalSumatoria();
	Double getVrTotalDiferir();
	
	Integer getCuotaDiferir();
	
	Double getVrFacturado();
	
	
}
