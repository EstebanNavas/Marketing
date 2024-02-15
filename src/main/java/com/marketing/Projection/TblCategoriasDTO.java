package com.marketing.Projection;

public interface TblCategoriasDTO {

	Integer getIDLOCAL();
	Integer getIDPLU();
	String getNombreCategoria();
	String getNombrePlu();
	Integer getIdEstracto();
	Integer getIdTIPO();
	Double getVrGeneral();
	Double getPorcentajeIva();
	Integer getTopeMaximo();
	Integer getRangoMaximo();
	Double getPorcentajeSubCon();
	Integer getIdCategoria();
	Double getVrCostoIND();
	Integer getIdProducto();
	String getCuentaContableDebito();
	String getCuentaContableCredito();
	String getCuentaRecaudoDebito();
	String getCuentaRecaudoCredito();
	String getAviso();
	
	Integer getIdLinea();
	String getNombreLinea();
}
