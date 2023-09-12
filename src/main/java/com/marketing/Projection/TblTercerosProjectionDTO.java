package com.marketing.Projection;

import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;

public interface TblTercerosProjectionDTO {

	Integer getIdLocal();

	String getIdCliente();

	Integer getIdTipoTercero();

	Integer getIdTercero();

	String getNombreTercero();

	String getDireccionTercero();

	Integer getIdDptoCiudad();

	Integer getIdEstracto();

	Integer getIdRuta();

	String getTelefonoCelular();
	
	String getEmail();
	
	
   //tblTercerosEstracto
	TblTerceroEstracto getTerceroEstracto();
	
	// TblTercerosRuta
	TblTercerosRuta getTerceroRuta();

}
