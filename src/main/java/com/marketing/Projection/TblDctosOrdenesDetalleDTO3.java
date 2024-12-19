package com.marketing.Projection;

import java.sql.Timestamp;

public interface TblDctosOrdenesDetalleDTO3 {
	
	Integer getIDLOCAL();
	Integer getIDTIPOORDEN();
	Integer getIDORDEN();
	Integer getIDPLU();
	String getNOMBREPLU();
	String getComentario();
	
	
	Integer getIdLocal();
	Integer getIdDcto();
	String getIdCliente();
	String getNombreTercero();
	String getNombreRuta();
	String getNombreEstracto();
	Integer getIdTipo();
	Integer getIdPlu();
	Double getVrBase();
	String getNombrePlu();
	String getNombreDcto();
	String getCC_Nit();
	Double getVrVentaConIva();
	
	Integer getMaxItem();
	
	Double getPromedio();
	String getHistoriaConsumo();
	Double getPromedioEstrato();
	
	Double getVrVentaSinIva();
	Double getVrVentaSinDscto();
	Double getVrIvaVenta();
	Double getVrCostoSinIva();
	
	Double getTotalFactura();
	
	Double getCantidad();
	Double getVrVentaUnitario();
	
	String getNombreMarca();
	Double getPorcentajeDscto();
	Double getPorcentajeIva();
	String getNombreCategoria();
	
	String getResolucion();
	String getRango();
	String getRegimen();
	String getDireccion();
	String getCiudad();
	String getTelefono();
	
	
	Integer getItem();
	String getFechaOrden();
	Double getVrIva();
	Double getVrInteresADiferir();
	Double getPorcentajeInteresADiferir();
	Integer getItemPadre();
	
	Double getVrCredito();
	
	Integer getIdTipoOrden();
	Integer getIdOrden();
	
	Double getVrSaldo();
	
	Double getVrMedio();
	
	Integer getIdPeriodo();
	Timestamp getFechaDcto();
	Integer getCuotaVencida();
	Integer getDiasMora();
	String getFinanciacion();
	String getTextoFinanciacion();
	String getEmail();
	String getDireccionTercero();
	String getNumeroMedidor();
	String getMarcaMedidor();
	Timestamp getFechaInstalacionMedidor();
	String getDireccionCobro();
	String getCodigoCatastral();
	Integer getOrdenRuta();
	Integer getIdRuta();
	Integer getIdEstracto();
	Integer getCodigoClaseUso();
	Timestamp getFechaInicial();
	Timestamp getFechaFinal();
	Timestamp getFechaConRecargo();
	Timestamp getFechaSinRecargo();
	String getNombrePeriodo();
	Integer getLecturaActual();
	Integer getLecturaAnterior();
	Double getConsumo();
	String getNombreCausa();
	String getTxtSuspension();
	Timestamp getFechaPagoUltimo();
	Double getVrPagoUltimo();
	Double getVrFactura();
	Double getVrTipoNegativo();
	Double getCANTIDAD();
	Double getVRVENTAUNITARIO();
	Double getPORCENTAJEIVA();
	Double getVrBaseUnitarioSinIva();
	Double getVrIvaUnitario();
	Double getVrVentaUnitarioSinIva();
	Double getVrRteFuente();
	Double getVrRteIva();
	Double getPorcentajeRteFuente();
	Integer getTaxId_RteFuente();
	Double getPorcentajeRteIva();
	Integer getTaxId_RteIva();
	Integer getTaxId_Iva();
	Double getVrUnitarioRteFuente();
	Double getVrUnitarioRteIva();
	String getCufe();
	Integer getIdProducto();
	String getstrIdLista();
	Integer getIdDpto();
	Integer getIdCiudad();
	Integer getIdIgacSector();
	Integer getCorregimiento();
	Integer getBarrio();
	Integer getIdIgacVereda();
	Integer getIdIgacPredio();
	Integer getEdificio();
	Integer getIdUMultiusuario();
	Integer getIdUNoMultiusuario();
	Integer getIdHogarComunitario();
	Integer getEstadoMedidor();
	Integer getDeterminacionConsumo();
	Integer getCMT();
	Integer getIdCausaRefactura();
	Integer getDctoRefactura();
	Double getVrPagoAnticipado();
	Integer getDiasFactura();
	Double getFactorCargoFijoSf3();
	Double getFactorCargoConsumoSf3();
	Double getVrPago();
	
	Integer getUnidadPredial();

}
