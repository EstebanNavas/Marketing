package com.marketing.Projection;

public interface TblDctosOrdenesDetalleDTO {

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
	Double getIdTipo();
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
	
	Integer getCantidad();
	Integer getVrVentaUnitario();
	
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
	String getEmail();
	
	Integer getItem();
	String getFechaOrden();
	Double getVrIva();
	Double getVrInteresADiferir();
	Double getPorcentajeInteresADiferir();
	Integer getItemPadre();
	
	Integer getIdOrden();
	
	String getNombreServicio();
	
	Integer getIdTipoOrden();
	String getFechaDcto();
	String getNombreTipoOrden();
	Integer getSigno();
	
	
	
}
