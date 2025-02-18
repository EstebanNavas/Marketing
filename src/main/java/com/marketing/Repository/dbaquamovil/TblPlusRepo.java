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
import com.marketing.Projection.TblPlusDTO2;

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
			  		 "rangoMaximo = ?9, aviso = ?10, cuentaContableDebito = ?11, cuentaContableCredito = ?12, cuentaRecaudoDebito = ?13, cuentaRecaudoCredito = ?14, idLinea = ?15 " +

	                 "WHERE tblPlus.idLocal = ?16 " +
	                 "AND tblPlus.idPlu = ?17 " , nativeQuery = true)
	  public void actualizarReferencia(String nombrePlu,  Double vrGeneral, Double porcentajeIva, int idTipo, int idCategoria, int idEstracto, int topeMaximo, Double subsidioContribucionInt, 
			  							int rangoMaximo, String aviso, String cuentaContableDebito, String cuentaContableCredito, String cuentaRecaudoDebito, String cuentaRecaudoCredito, int idLinea,   int idLocal, int idPlu) ;
	
	  
	  
	  
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
		
		
		@Query(value = "SELECT tblplus.idLocal,        "
                + "        tblplus.idPlu,         "
                + " tblcategorias.nombreCategoria AS nombreCategoria,"
                + "        tblplus.nombrePlu,     "
                + "        tblplus.vrGeneral,     "
                + "        tblplus.vrMayorista,   "
                + "        tblplus.idTipo,        "
                + "        tblplus.idLinea,       "
                + "        tblplus.porcentajeIva, "
                + "        tblplus.idUCompra,     "
                + "        tblplus.idUVenta,      "
                + "        tblplus.vrCosto,       "
                + "        tblplus.idCategoria,   "
                + "        tblplus.idMarca,       "
                + "        tblplus.vrSucursal,    "
                + "        tblplus.factorVenta,   "
                + "        tblplus.factorDespacho,"
                + "        tblplus.estado,        "
                + "        tblplus.rangoMaximo,   "
                + "        tblplus.aviso,         "
                + "        tblplus.idEstracto,    "
                + "        tblplus.topeMaximo,    "
                + "        tblplus.vrCostoIND,    "
                + "        tblplus.referencia,    "
                + "        tblplus.idSeq,         "
                + "        tblplus.vrImpoconsumo, "
                + "        tblmarcas.nombreMarca  "
                + " FROM tblplus                  "
                + " INNER JOIN tblcategorias      "
                + " ON tblplus.idLinea      =     "
                + "        tblcategorias.idLinea  "
                + " AND tblplus.idCategoria =     "
                + "    tblcategorias.IdCategoria  "
                + " AND tblplus.idLocal     =     "
                + "        tblcategorias.idLocal  "
                + " INNER JOIN tblmarcas          "
                + " ON tblplus.idMarca            "
                + "         = tblmarcas.idMarca   "
                + " WHERE tblplus.idLocal       = "
                + "?1                "
                + " AND tblplus.idLinea         = "
                + "?2                "                
                + " AND tblplus.idTipo NOT IN     "
                + "           (105,4,21,19,20,21) "
                + " AND tblplus.idEstracto IN (0, "
                + "?3)",
				nativeQuery = true)
		List<TblPlusDTO> listaPluNota(int idLocal, int IdLinea, int idEstracto );
		
		
		
		@Query(value = "SELECT  tblplus.idPlu,                "
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
                + "        tblplus.idEstracto,           "
                + "        tblplus.idLocal,              "
                + "        tblplus.aviso,                "
                + "        tblplus.rangoMaximo,          "
                + "        tblplus.codigoContable,       "
                + "        tblplus.nombreContable,       "
                + "        tblplus.cuentaContableDebito, "
                + "        tblplus.cuentaContableCredito,"
                + "        tblplus.cuentaRecaudoDebito,  "
                + "        tblplus.cuentaRecaudoCredito  "
                + "FROM tblplus                          "
                + "INNER JOIN tblmarcas                  "
                + "ON tblplus.idMarca      =             "
                + "                    tblmarcas.idMarca "
                + "INNER JOIN tblcategorias              "
                + "ON tblplus.idLinea      =             "
                + "                tblcategorias.idLinea "
                + "AND tblplus.idCategoria =             "
                + "            tblcategorias.IdCategoria "
                + "AND tblplus.idLocal =                 "
                + "            tblcategorias.IdLocal     "
                + "WHERE tblplus.idPlu =                 "
                + "?1                         "
                + "AND tblplus.idLocal   =   ?2            ",
				nativeQuery = true)
		List<TblPlusDTO> listaUnPluFCH(String idplu, int idLocal );
		
		
		
		@Query(value = "SELECT *                         " 
					+ "FROM tblplus                      "
					+ "WHERE tblplus.idLocal = ?1       "
					+ "AND tblplus.idTipo = ?2        ",
				nativeQuery = true)
		List<TblPlus> ObtenerFinanciacion(int idLocal, int idTipo );
		
		
		@Query(value = "SELECT *                         " 
				+ "FROM tblplus                      "
				+ "WHERE tblplus.idLocal = ?1       "
				+ "AND tblplus.idLinea = ?2        "
				+ "AND tblplus.idTipo < 100       ",
			nativeQuery = true)
		List<TblPlus> ObtenerPlusPorIdLinea(int idLocal, int idLinea );
		
		
		@Query(value = "SELECT tblplus.idPlu,                "
                + "        tblcategorias.nombreCategoria "
                + " 	     +' '+tblplus.nombrePlu      "
                + " 		           AS nombrePlu, "
                + " 		 1 AS orden              "
                + " FROM     tblplus                     "
                + " INNER JOIN tblcategorias             "
                + " ON tblplus.idLocal =                 "
                + "                tblcategorias.idLocal "
                + " AND tblplus.idLinea =                "
                + "                tblcategorias.idLinea "
                + " AND tblplus.idCategoria =            "
                + "            tblcategorias.IdCategoria "
                + " WHERE tblplus.idLocal =              "
                + "?1                       "
                + " AND tblplus.idTipo                   "
                + "        NOT IN (4,6,19,20,21,22,50,51) "
                + " UNION                                "
                + " SELECT tblplus.idPlu,                "
                + "       tblcategorias.nombreCategoria  "
                + "              +' '+tblplus.nombrePlu  "
                + "                 	   AS nombrePlu, "
                + "       1 AS orden                     "
                + " FROM tblplus                         "
                + " INNER JOIN tblcategorias             "
                + " ON tblplus.idLocal =                 "
                + "                tblcategorias.idLocal "
                + " AND tblplus.idLinea =                "
                + "                tblcategorias.idLinea "
                + " AND tblplus.idCategoria =            "
                + "            tblcategorias.IdCategoria "
                + " INNER JOIN  tblterceros              "
                + " ON tblterceros.idLocal =             "
                + "                     tblplus.idlocal  "
                + " AND tblterceros.idestracto =         "
                + "                   tblplus.idEstracto "
                + " WHERE tblplus.idLocal =              "
                + "?1                      "
                + " AND idCliente  =                     "
                + "?2                     "
                + " AND tblplus.idTipo                   "
                + "           NOT IN (19,20,21,22,50,51) "
                + " AND tblterceros.idTipoTercero = 1    "
                + " ORDER BY 3, 2",
				nativeQuery = true)
		List<TblPlusDTO> seleccionaPlu(int idLocal, String idCliente);
		
		
		@Query(value = "SELECT TOP 1 tblPlus.idPlu,                  "
                + "       tblPlus.nombrePlu,                    "
                + "	  tblcategorias.nombreCategoria,        "
                + "       tblmarcas.nombreMarca,                "
                + "	  1 AS orden                            "
                + " FROM     tblDctosOrdenes                    "
                + " INNER JOIN tblDctosOrdenesDetalle           "
                + " ON tblDctosOrdenes.IDLOCAL =                "
                + "              tblDctosOrdenesDetalle.IDLOCAL "
                + " AND tblDctosOrdenes.IDTIPOORDEN =           "
                + "          tblDctosOrdenesDetalle.IDTIPOORDEN "
                + " AND  tblDctosOrdenes.IDORDEN =              "
                + "              tblDctosOrdenesDetalle.IDORDEN "
                + " INNER JOIN   tblPlus                        "
                + " ON tblDctosOrdenesDetalle.IDLOCAL =         "
                + "                             tblPlus.idLocal "
                + " AND tblDctosOrdenesDetalle.IDPLU =          "
                + "                               tblPlus.idPlu "
                + " INNER JOIN tblmarcas                        "
                + " ON tblplus.idMarca =   tblmarcas.idMarca    "
                + " INNER JOIN tblcategorias                    "
                + " ON tblplus.idLinea = tblcategorias.idLinea  "
                + " AND tblplus.idCategoria =                   "
                + "                   tblcategorias.IdCategoria "
                + " AND tblplus.idLocal = tblcategorias.IdLocal "
                + " WHERE tblplus.idLinea     =                 "
                + "?1                              "
                + " AND   tblplus.idCategoria =                 "
                + "?2                          "
                + " AND   tblplus.idLocal =                     "
                + "?3                              "
                + " AND tblDctosOrdenesDetalle.idPlu IN        ("
                + "?4)                               "
                + " UNION                                       "
                + " SELECT tblplus.idPlu,                       "
                + "        tblplus.nombrePlu,                   "
                + "        tblcategorias.nombreCategoria,       "
                + "        tblmarcas.nombreMarca,               "
                + "         2 AS orden                          "
                + "FROM tblplus                                 "
                + "INNER JOIN tblmarcas                         "
                + "ON tblplus.idMarca =  tblmarcas.idMarca      "
                + "INNER JOIN tblcategorias                     "
                + "ON tblplus.idLinea = tblcategorias.idLinea   "
                + "AND tblplus.idCategoria =                    "
                + "                   tblcategorias.IdCategoria "
                + "AND tblplus.idLocal = tblcategorias.IdLocal  "
                + "WHERE tblplus.idLinea     =             "
                + "?1                         "
                + "AND   tblplus.idCategoria =             "
                + "?2                     "
                + " AND   tblplus.idLocal =                "
                + "?3                         "
                + " ORDER BY 5  ",
				nativeQuery = true)
		List<TblPlusDTO> listaPluCategoriaTipoNE(int idLinea, int idCategoria, int idLocal, int idPlu);
		
		
		
		@Query(value = " SELECT  tblplus.idPlu,                     "
                + "   tblplus.nombrePlu,                     "
                + "   tblDctosOrdenesDetalle.VRVENTAUNITARIO "
                + "                    AS vrGeneral,         "
                + "   tblplus.vrMayorista,                   "
                + "   tblplus.porcentajeIva,                 "
                + "   tblplus.idTipo,                        "
                + "   tblplus.idLinea,                       "
                + "   tblplus.idUCompra,                     "
                + "   tblplus.idUVenta,                      "
                + "   tblplus.vrCosto,                       "
                + "   tblplus.idCategoria,                   "
                + "   tblplus.idMarca,                       "
                + "   tblplus.vrSucursal,                    "
                + "   tblplus.factorVenta,                   "
                + "   tblplus.factorDespacho,                "
                + "   tblplus.estado,                        "
                + "   tblplus.idSeq,                         "
                + "   tblplus.referencia,                    "
                + "   tblcategorias.nombreCategoria,         "
                + "   tblmarcas.nombreMarca,                 "
                + "   tblDctosOrdenesDetalle.cantidad        "
                + "                   AS existencia,         "
                + "   tblplus.topeMaximo,                    "
                + "   tblplus.idEstracto,                    "
                + "   tblplus.rangoMaximo                    "
                + " FROM tblplus                             "
                + " INNER JOIN tblmarcas                     "
                + " ON tblplus.idMarca      =                "
                + "                      tblmarcas.idMarca   "
                + " INNER JOIN tblcategorias                 "
                + " ON tblplus.idLinea      =                "
                + "                  tblcategorias.idLinea   "
                + " AND tblplus.idCategoria =                "
                + "              tblcategorias.IdCategoria   "
                + " AND tblplus.idLocal =                    "
                + "              tblcategorias.IdLocal       "
                + " INNER JOIN	tblDctosOrdenesDetalle       "
                + " ON 	tblplus.idLocal =                    "
                + "           tblDctosOrdenesDetalle.IdLocal "
                + " AND tblplus.idPlu   =                    "
                + "           tblDctosOrdenesDetalle.idPlu   "
                + " WHERE tblplus.idLocal             =      "
                + "?1                           "
                + " AND tblplus.idLinea               =      "
                + "?2                           "                
                + " AND tblplus.idCategoria IN              ("
                + "?3 )                       "
                + " AND tblDctosOrdenesDetalle.idOrden=      "
                + "?4                               "  
                + " ORDER BY tblplus.idCategoria,            "
                + "          tblplus.idPlu      ",
				nativeQuery = true)
		List<TblPlusDTO2> listaPluCategoriaNE( int idLocal, int idLinea, String xIdCategoriaStr, int xIdOrden );
		
		
		
		@Query(value = "  SELECT  tblCategorias.idLocal                          "
				+ "      ,tblCategorias.idLinea                             "
				+ "	  ,tblPlus.idPlu                                        "
				+ "	  ,tblPlus.nombrePlu                                    "
				+ "  FROM [bdaquamovil].[dbo].[tblCategorias]               "
				+ "  INNER JOIN tblPlus                                     "
				+ "  ON tblPlus.idLocal = tblCategorias.idLocal             "
				+ "  AND tblPlus.idCategoria = tblCategorias.IdCategoria    "
				+ "  Where tblCategorias.idLocal = ?1                       "
				+ "  AND tblCategorias.IdCategoria = ?2                     ",
				nativeQuery = true)
		List<TblPlusDTO> ObtenerPlusxCategoria( int idLocal, int idCategoria);
		
		
		@Query(value = "SELECT tblplus.idLocal,        "
                + "        tblplus.idPlu,         "
                + " tblcategorias.nombreCategoria,"
                + "        tblplus.nombrePlu,     "
                + "        tblplus.vrGeneral,     "
                + "        tblplus.vrMayorista,   "
                + "        tblplus.idTipo,        "
                + "        tblplus.idLinea,       "
                + "        tblplus.porcentajeIva, "
                + "        tblplus.idUCompra,     "
                + "        tblplus.idUVenta,      "
                + "        tblplus.vrCosto,       "
                + "        tblplus.idCategoria,   "
                + "        tblplus.idMarca,       "
                + "        tblplus.vrSucursal,    "
                + "        tblplus.factorVenta,   "
                + "        tblplus.factorDespacho,"
                + "        tblplus.estado,        "
                + "        tblplus.rangoMaximo,   "
                + "        tblplus.aviso,         "
                + "        tblplus.idEstracto,    "
                + "        tblplus.topeMaximo,    "
                + "        tblplus.vrCostoIND,    "
                + "        tblplus.referencia,    "
                + "        tblplus.idSeq,         "
                + "        tblplus.vrImpoconsumo, "
                + "        tblmarcas.nombreMarca  "
                + " FROM tblplus                  "
                + " INNER JOIN tblcategorias      "
                + " ON tblplus.idLinea      =     "
                + "        tblcategorias.idLinea  "
                + " AND tblplus.idCategoria =     "
                + "    tblcategorias.IdCategoria  "
                + " AND tblplus.idLocal     =     "
                + "        tblcategorias.idLocal  "
                + " INNER JOIN tblmarcas          "
                + " ON tblplus.idMarca            "
                + "         = tblmarcas.idMarca   "
                + " WHERE tblplus.idLocal       = "
                + "?1              "
                + " AND tblplus.idTipo            "
                + "         NOT IN (4,6,21,22,105)",
				nativeQuery = true)
		List<TblPlusDTO> listaPluNovedad( int idLocal);
		
		
		
		
		@Query(value = "SELECT tblplus.idLocal,        "
                + "        tblplus.idPlu,         "              
                + "        tblplus.nombrePlu,     "          
                + "        tblplus.idLinea,       "          
                + "        tblplus.idCategoria    "             
                + " FROM tblplus                  "
                + " WHERE tblplus.idLocal       = "
                + "?1              "
                + " AND tblplus.idLinea = ?2      ",
				nativeQuery = true)
		List<TblPlusDTO> listaPluXLinea( int idLocal, int idLinea);
		
		
		
		@Query(value = "SELECT tblplus.nombrePlu        "         
                + " FROM tblplus                  "
                + " WHERE tblplus.idLocal       = "
                + "?1              "
                + " AND tblplus.idPlu = ?2      ",
				nativeQuery = true)
		String obtenerNombrePlu( int idLocal, int idPlu);
		
		
	  
	
	
}
