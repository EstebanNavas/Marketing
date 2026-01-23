package com.marketing.Projection;

public interface TblLocalesDTO {
	
	Integer getIdLocal();
	String getNombreLocal();
	String getDireccion();
	String getTelefono();
	String getEmail();
	String getToken();
	Integer getIdCaja();
	String getPrefijo();
	String getResolucion();
	String getPathReport();
	String getPathImagen();
	
	String getFechaResolucion();
	String getFechaResolucionLimite();
	String getRango();

}