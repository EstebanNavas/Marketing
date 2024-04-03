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
	
	
}
