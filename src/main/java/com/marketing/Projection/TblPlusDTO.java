package com.marketing.Projection;

public interface TblPlusDTO {

		Integer getIdPlu();
		String getNombrePlu();
	   Double getVrGeneral();
	    Double getVrMayorista();
	    Double getPorcentajeIva();
	    Integer getIdTipo();
	    Integer getIdLinea();
	    Integer getIdUCompra();
	    Integer getIdUVenta();
	    Double getVrCosto();
	    Integer getIdCategoria();
	    Integer getIdMarca();
	    Double getVrSucursal();
	    Double getFactorVenta();
	    Double getFactorDespacho();
	    Integer getEstado();
	    Integer getIdSeq();
	    String getReferencia();
	    String getNombreCategoria();
	    String getNombreMarca();
	    Double getVrImpoconsumo();
	    Double getVrCostoIND();
	    Double getTopeMaximo();
	    Integer getIdEstracto();
		void setIdPlu(int i);
		void setIdUVenta(int i);
	
}
