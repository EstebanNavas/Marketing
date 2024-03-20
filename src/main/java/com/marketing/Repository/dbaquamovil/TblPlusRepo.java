package com.marketing.Repository.dbaquamovil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblPlusDTO;

@Repository
public interface TblPlusRepo extends JpaRepository<TblPlus, Integer>{

	
	@Query(value = "SELECT tblPlus.* " + 
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblPlus " +
			"ON tblCategorias.idLocal = tblPlus.idLocal " +
			"AND tblCategorias.idCategoria = tblPlus.idCategoria " +
			"AND tblCategorias.idLinea = tblPlus.idLinea " +
			"WHERE tblCategorias.idLocal = ?1 " +
			"AND   tblCategorias.idLinea = 200 " +
			"AND   tblCategorias.idCategoria = ?2", 
			nativeQuery = true)
	ArrayList<TblPlus> ObtenerCategorias(int idLocal, int idCategoria);
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblPlus " +
			"WHERE tblPlus.idLocal = ?1 " +
			"AND   tblPlus.idLinea = 200 ",
			nativeQuery = true)
	List<TblPlus> ObtenerNombrePluAndIdPlu(int idLocal);
	
	@Query(value = "SELECT MAX(p.idPlu) FROM tblPlus p " + 
			"WHERE p.idLocal = ?1 ",
			nativeQuery = true)
	Integer maximoIdPlu(int idLocal);
	
	// Actualizamos La referencia
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblPlus SET nombrePlu = ?1, vrGeneral = ?2, porcentajeIva = ?3, idTipo = ?4, idCategoria = ?5, idEstracto = ?6, topeMaximo = ?7, vrCostoIND = ?8,  " +
			  		 "rangoMaximo = ?9, aviso = ?10, cuentaContableDebito = ?11, cuentaContableCredito = ?12, cuentaRecaudoDebito = ?13, cuentaRecaudoCredito = ?14 " +

	                 "WHERE tblPlus.idLocal = ?15 " +
	                 "AND tblPlus.idPlu = ?16 " , nativeQuery = true)
	  public void actualizarReferencia(String nombrePlu,  Double vrGeneral, Double porcentajeIva, int idTipo, int idCategoria, int idEstracto, int topeMaximo, Double subsidioContribucionInt, 
			  							int rangoMaximo, String aviso, String cuentaContableDebito, String cuentaContableCredito, String cuentaRecaudoDebito, String cuentaRecaudoCredito,  int idLocal, int idPlu ) ;
	
	  
	  
	  
		@Query(value = "SELECT  TOP 1 tblplus.idPlu,            "
                + "        tblplus.nombrePlu,            "
                + "        tblplus.vrGeneral,            "
                + "        tblplus.vrMayorista,          "
                + "        tblplus.porcentajeIva,        "
                + "        tblplus.idTipo,               "
                + "        tblplus.idLinea,              "
                + "        tblplus.idUCompra,            "
                + "        tblplus.idUVenta,             "
                + "        tblplus.vrCosto,              "
                + "        tblplus.idCategoria,          "
                + "        tblplus.idMarca,              "
                + "        tblplus.vrSucursal,           "
                + "        tblplus.factorVenta,          "
                + "        tblplus.factorDespacho,       "
                + "        tblplus.estado,               "
                + "        tblplus.idSeq,                "
                + "        tblplus.referencia,           "
                + "        tblcategorias.nombreCategoria,"
                + "        tblmarcas.nombreMarca,        "
                + "        tblplus.vrImpoconsumo,        "
                + "        tblplus.vrCostoIND,           "
                + "        tblplus.topeMaximo,           "
                + "        tblplus.idEstracto            "
                + "FROM tblplus                          "
                + "INNER JOIN tblmarcas                  "
                + "ON tblplus.idMarca      =             "
                + "                    tblmarcas.idMarca "
                + "INNER JOIN tblcategorias              "
                + "ON tblplus.idLinea      =             "
                + "                tblcategorias.idLinea "
                + "AND tblplus.idLocal      =            "
                + "                tblcategorias.idLocal "
                + "AND tblplus.idCategoria =             "
                + "            tblcategorias.IdCategoria "
                + "WHERE tblplus.idTipo    =             "
                + "?2                        "
                + "AND tblplus.idEstracto  =             "
                + "?3                    "
                + "AND tblplus.idCategoria = 1           "
                + "AND tblplus.idLocal     =             "
                + "?1 ",
				nativeQuery = true)
		List<TblPlusDTO> listaEstractoTipoFCH(int idLocal, int idTipo, int idEstracto );
	  
	
	
}
