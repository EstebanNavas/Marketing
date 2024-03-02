package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblDctos;
import com.marketing.Projection.ReporteFeDTO;
import com.marketing.Projection.TblDctosDTO;

@Repository
public interface TblDctosRepo extends JpaRepository<TblDctos, Integer> {

	@Query("SELECT MAX(r.idDcto) FROM TblDctos r " +
			  "WHERE r.IDLOCAL = ?1 " +
			  "AND r.IDTIPOORDEN = 17 ")
	    Integer findMaxIdDcto(int IDLOCAL);
	
	  @Query(value = "SELECT idDcto " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.IDORDEN = ?2 " +
              "AND tblDctos.idCliente = ?3 ",
              nativeQuery = true)
	  Integer ObtenerIdDcto(int idLocal, int IDORDEN, String idCliente);
	  
	  @Query(value = "SELECT fechaDcto " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.IDORDEN = ?2 " +
              "AND tblDctos.idCliente = ?3 ",
              nativeQuery = true)
	  String ObtenerFechaDcto(int idLocal, int IDORDEN, String idCliente);
	  
	  @Query(value = "SELECT DISTINCT idCliente " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.IDTIPOORDEN = 17 ",
              nativeQuery = true)
	  List<String> ObtenerClientesPQR(int idLocal);
	  
	  @Query(value = "SELECT  idDcto " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.idCliente = ?2 "+
              "AND tblDctos.IDTIPOORDEN = 17 ",
              nativeQuery = true)
	  List<Integer> ObtenerListaPQR(int idLocal, String idCliente);
	  
	  @Query(value = "SELECT  IDORDEN " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.idDcto = ?2 " +
              "AND tblDctos.IDTIPOORDEN = 17 ",
              nativeQuery = true)
	  Integer ObtenerIDORDEN(int idLocal, int idDcto);
	  
	  @Query(value = "SELECT  IDORDEN " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.idDcto IN ?2 "+
              "AND tblDctos.IDTIPOORDEN = 17 ",
              nativeQuery = true)
	  List<Integer> ObtenerListaIDORDEN(int idLocal, List<Integer> idDcto);
	  
	  @Query(value = "SELECT  IDORDEN " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.IDTIPOORDEN = ?2 " +
              "AND tblDctos.idPeriodo = ?3",
              nativeQuery = true)
	  List<Integer> ObtenerCantidadFacturas(int idLocal, int idTipoOrden, int idPeriodo );
	  
	  @Query(value = "SELECT  envioFE, COUNT(*) AS cuenta " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.IDTIPOORDEN = ?2 " +
              "AND tblDctos.idPeriodo = ?3 " +
              "group by envioFE ",
              nativeQuery = true)
	  List<ReporteFeDTO> ObtenerReporteFE(int idLocal, int idTipoOrden, int idPeriodo );
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM tbldctos " +
	                 "WHERE tbldctos.IDLOCAL = ?1 " +
	                 "AND tbldctos.IDTIPOORDEN   IN (7,9,29, 59) " +
	                 "AND tbldctos.idPeriodo =  ?2 ", nativeQuery = true)
	  public void retiraDctos(int idLocal, int idPeriodo);
	  
	  
	  
	  
	  @Query(value = " SELECT tblDctos.idLocal,             "                 
			  + "   tblDctos.idTipoOrden,                   "
			  + "   tblDctos.idDcto,                        "
			  + "   tblDctos.idOrden,                       "
			  + "   tblDctos.fechaDcto,                     "
			  + "   tblDctos.indicador,                     "
			  + "   tblDctos.idCliente,                     "
			  + "   tblDctos.vrBase,                        "
			  + "   tblDctos.vrIva,                         "
			  + "   tblDctos.vrCostoMV,                     "
			  + "   tblDctos.nombreTercero,                 "
			  + "   tblDctos.idDctoNitCC,                   "
			  + "   tblDctos.fechaDctoNitCC,                "
			  + "   tblDctos.idLocalCruce,                  "
			  + "   tblDctos.idTipoOrdenCruce,              "
			  + "   tblDctos.idDctoCruce,                   "
			  + "   (tblDctos.vrBase      +                 "
			  + "   tblDctos.vrIva       -                  "
			  + "   tblDctos.vrRteFuente -                  "
			  + "   tblDctos.vrRteIva) AS vrFactura,        "
			  + "   tblDctos.vrDescuento,                   "
			  + "   tblDctos.vrRteFuente,                   "
			  + "   (CASE WHEN                              "
			  + "         tblDctos.idTipoOrden = 1          "
			  + "   THEN    tblDctos.idDctoNitCC            "
			  + "   ELSE    STR(tblDctos.idDcto)            "
			  + "   END) AS idDctoStr,                      "
			  + "   (CASE WHEN                              "
			  + "         tblDctos.idTipoNegocio = 1        "
			  + "   THEN      'CONTADO'                     "
			  + "   ELSE      'CREDITO'                     "
			  + "   END) AS nombreTipoNegocio,              "
			  + "   tblDctos.idTipoNegocio,                 "
			  + "   ctrlUsuarios.aliasUsuario,              "
			  + "   tblTercerosRuta.nombreRuta,             "
			  + "   tblTerceroEstracto.nombreEstracto       "
			  + "                    AS nombreEstrato,      "
			  + "   tblDctosOrdenes.OBSERVACION,            "   
			  + "   tblTerceros.CC_Nit,                     "
			  + "   tblDctosPeriodo.fechaConRecargo         "
			  + "                            AS fechaVcto   "
			  + "   FROM   tblDctos                         "     
			  + "   INNER JOIN tblDctosOrdenes              "    
			  + "   ON tblDctosOrdenes.IDLOCAL              "
			  + "                    =tblDctos.IDLOCAL      "
			  + "   AND tblDctosOrdenes.IDTIPOORDEN =       "    
			  + "                tblDctos.IDTIPOORDEN       "
			  + "   AND tblDctosOrdenes.IDORDEN   =         "      
			  + "                  tblDctos.IDORDEN         "
			  + "   INNER JOIN tblTerceros                  "    
			  + "   ON tblTerceros.idLocal        =         "
			  + "                  tblDctos.IDLOCAL         "
			  + "   AND tblTerceros.idCliente     =         "        
			  + "                tblDctos.idCliente         "
			  + "   INNER JOIN tblDctosPeriodo              "
			  + "   ON tblDctosPeriodo.idLocal    =         "
			  + "                  tblDctos.idLocal         "
			  + "   AND tblDctosPeriodo.idPeriodo =         "
			  + "                tblDctos.idPeriodo         "
			  + "   INNER JOIN ctrlUsuarios                 "
			  + "   ON ctrlUsuarios.idLocal       =         "
			  + "                  tblDctos.IDLOCAL         "
			  + "   AND ctrlUsuarios.idUsuario    =         "
			  + "               tblDctos.idVendedor         "
			  + "   INNER JOIN tblTercerosRuta              "
			  + "   ON tblTercerosRuta.idLocal    =         "
			  + "               tblTerceros.IDLOCAL         "
			  + "   AND tblTercerosRuta.idRuta    =         "
			  + "                tblTerceros.idRuta         "
			  + "   INNER JOIN tblTerceroEstracto           "
			  + "   ON tblTerceroEstracto.idLocal =         "
			  + "               tblTerceros.IDLOCAL         "
			  + "   AND tblTerceroEstracto.idEstracto =     "
			  + "               tblTerceros.idEstracto      "
			  + "                  WHERE EXISTS             "                    
			  + "  ( SELECT *                               " 
			  + "    FROM   tblDctosOrdenes                 " 
			  + "    INNER JOIN tblDctosOrdenesDetalle      " 
			  + "    ON tblDctosOrdenes.IDLOCAL      =      " 
			  + "       tblDctosOrdenesDetalle.IDLOCAL      " 
			  + "    AND tblDctosOrdenes.IDTIPOORDEN =      " 
			  + "   tblDctosOrdenesDetalle.IDTIPOORDEN      "
			  + "    AND tblDctosOrdenes.IDORDEN     =      " 
			  + "       tblDctosOrdenesDetalle.IDORDEN      " 
			  + "    WHERE tblDctosOrdenes.IDLOCAL   =      " 
			  + "                     tblDctos.IDLOCAL      "
			  + "    AND tblDctosOrdenes.IDTIPOORDEN =      " 
			  + "                 tblDctos.IDTIPOORDEN      "
			  + "    AND tblDctosOrdenes.IDORDEN     =      " 
			  + "          tblDctos.IDORDEN)                "
			  + "    AND tblDctos.idLocal   =               " 
			  + "?1                              "                
			  + "    AND tblDctos.idPeriodo =               "  
			  + "?2                           "                       
			  + " AND tblDctos.idTipoOrden  IN             ("
			  + "?3 ,                    "
			  + "?4 )                    "  
			  + " AND tblDctos.indicador BETWEEN            "
			  + "?5      AND          "
			  + "?6                      " 
			  + " AND    tblTercerosRuta.idRuta =              "
			  + "?7                                 "                
			  + "    AND tblTerceros.IDTIPOTERCERO =1       "			 
			  + "    ORDER BY tblDctos.fechaDcto,           "      
			  + "             tblDctos.idTipoOrden,         "      
			  + "             tblDctos.idDcto",
              nativeQuery = true)
	  List<TblDctosDTO> listaRepNotaRuta(int idLocal, int idPeriodo, int IdTipoOrdenINI, int IdTipoOrdenFIN, int IndicadorINICIAL, int IndicadorFINNAL, int idRuta );
	  
	  
	  

	  @Query(value = " SELECT tblDctos.idLocal,                    "             
			  + "   tblDctos.idTipoOrden,                   "
			  + "   tblDctos.idDcto,                        "
			  + "   tblDctos.idOrden,                       "
			  + "   tblDctos.fechaDcto,                     "
			  + "   tblDctos.indicador,                     "
			  + "   tblDctos.idCliente,                     "
			  + "   tblDctos.vrBase,                        "
			  + "   tblDctos.vrIva,                         "
			  + "   tblDctos.vrCostoMV,                     "
			  + "   tblDctos.nombreTercero,                 "
			  + "   tblDctos.idDctoNitCC,                   "
			  + "   tblDctos.fechaDctoNitCC,                "
			  + "   tblDctos.idLocalCruce,                  "
			  + "   tblDctos.idTipoOrdenCruce,              "
			  + "   tblDctos.idDctoCruce,                   "
			  + "   (tblDctos.vrBase      +                 "
			  + "   tblDctos.vrIva       -                  "
			  + "   tblDctos.vrRteFuente -                  "
			  + "   tblDctos.vrRteIva) AS vrFactura,        "
			  + "   tblDctos.vrDescuento,                   "
			  + "   tblDctos.vrRteFuente,                   "
			  + "   (CASE WHEN                              "
			  + "         tblDctos.idTipoOrden = 1          "
			  + "   THEN    tblDctos.idDctoNitCC            "
			  + "   ELSE    STR(tblDctos.idDcto)            "
			  + "   END) AS idDctoStr,                      "
			  + "   (CASE WHEN                              "
			  + "         tblDctos.idTipoNegocio = 1        "
			  + "   THEN      'CONTADO'                     "
			  + "   ELSE      'CREDITO'                     "
			  + "   END) AS nombreTipoNegocio,              "
			  + "   tblDctos.idTipoNegocio,                 "
			  + "   ctrlUsuarios.aliasUsuario,              "
			  + "   tblTercerosRuta.nombreRuta,             "
			  + "   tblTerceroEstracto.nombreEstracto       "
			  + "                    AS nombreEstrato,      "
			  + "   tblDctosOrdenes.OBSERVACION,            "   
			  + "   tblTerceros.CC_Nit,                     "
			  + "   tblDctosPeriodo.fechaConRecargo         "
			  + "                            AS fechaVcto   "
			  + "   FROM   tblDctos                         "     
			  + "   INNER JOIN tblDctosOrdenes              "    
			  + "   ON tblDctosOrdenes.IDLOCAL              "
			  + "                    =tblDctos.IDLOCAL      "
			  + "   AND tblDctosOrdenes.IDTIPOORDEN =       "    
			  + "                tblDctos.IDTIPOORDEN       "
			  + "   AND tblDctosOrdenes.IDORDEN   =         "      
			  + "                  tblDctos.IDORDEN         "
			  + "   INNER JOIN tblTerceros                  "    
			  + "   ON tblTerceros.idLocal        =         "
			  + "                  tblDctos.IDLOCAL         "
			  + "   AND tblTerceros.idCliente     =         "        
			  + "                tblDctos.idCliente         "
			  + "   INNER JOIN tblDctosPeriodo              "
			  + "   ON tblDctosPeriodo.idLocal    =         "
			  + "                  tblDctos.idLocal         "
			  + "   AND tblDctosPeriodo.idPeriodo =         "
			  + "                tblDctos.idPeriodo         "
			  + "   INNER JOIN ctrlUsuarios                 "
			  + "   ON ctrlUsuarios.idLocal       =         "
			  + "                  tblDctos.IDLOCAL         "
			  + "   AND ctrlUsuarios.idUsuario    =         "
			  + "               tblDctos.idVendedor         "
			  + "   INNER JOIN tblTercerosRuta              "
			  + "   ON tblTercerosRuta.idLocal    =         "
			  + "               tblTerceros.IDLOCAL         "
			  + "   AND tblTercerosRuta.idRuta    =         "
			  + "                tblTerceros.idRuta         "
			  + "   INNER JOIN tblTerceroEstracto           "
			  + "   ON tblTerceroEstracto.idLocal =         "
			  + "               tblTerceros.IDLOCAL         "
			  + "   AND tblTerceroEstracto.idEstracto =     "
			  + "               tblTerceros.idEstracto      "
			  + "                  WHERE EXISTS             "                    
			  + "  ( SELECT *                               " 
			  + "    FROM   tblDctosOrdenes                 " 
			  + "    INNER JOIN tblDctosOrdenesDetalle      " 
			  + "    ON tblDctosOrdenes.IDLOCAL      =      " 
			  + "       tblDctosOrdenesDetalle.IDLOCAL      " 
			  + "    AND tblDctosOrdenes.IDTIPOORDEN =      " 
			  + "   tblDctosOrdenesDetalle.IDTIPOORDEN      "
			  + "    AND tblDctosOrdenes.IDORDEN     =      " 
			  + "       tblDctosOrdenesDetalle.IDORDEN      " 
			  + "    WHERE tblDctosOrdenes.IDLOCAL   =      " 
			  + "                     tblDctos.IDLOCAL      "
			  + "    AND tblDctosOrdenes.IDTIPOORDEN =      " 
			  + "                 tblDctos.IDTIPOORDEN      "
			  + "    AND tblDctosOrdenes.IDORDEN     =      " 
			  + "          tblDctos.IDORDEN)                "
			  + "    AND tblDctos.idLocal   =               " 
			  +" ?1                             "                
			  + "    AND tblDctos.idPeriodo =               "  
			  + "?2                           "                       
			  + " AND tblDctos.idTipoOrden  IN             ("
			  + "?3 ,                    "
			  + "?4 )                    "  
			  + " AND tblDctos.indicador BETWEEN            "
			  + "?5       AND          "
			  + "?6                     " 
			  + "    AND tblTerceros.IDTIPOTERCERO =1       "			 
			  + "    ORDER BY tblDctos.fechaDcto,           "      
			  + "             tblDctos.idTipoOrden,         "      
			  + "             tblDctos.idDcto",
              nativeQuery = true)
	  List<TblDctosDTO> listaRepNota(int idLocal, int idPeriodo, int IdTipoOrdenINI, int IdTipoOrdenFIN, int IndicadorINICIAL, int IndicadorFINNAL);
	  
	  
	  
	  
	  
	  @Query(value = "SELECT tblDctos.IDLOCAL,            "
              + "        IDTIPOORDEN,               "
              + " 	   IDORDEN,                   "
              + " 	   idDcto,                    "
              + " 	   tblDctos.idCliente,        "
              + " 	   tblTerceros.numeroMedidor, "
              + " 	   fechaDcto,                 "
              + " 	   GETDATE() AS fechaAbono,   "
              + " 	   vrBase,                    "
              + " 	   vrPago,                    "
              + "?1  AS vrAbono,         "
              + " 	    (vrBase - "
              + "?1 ) AS vrSaldo,         "
              + " 	   tblTerceros.nombreTercero, "
              + " 	   idPeriodo                  "
              + " FROM     tblDctos                 "
              + " INNER JOIN tblTerceros            "
              + " ON tblTerceros.idLocal            "
              + "                = tblDctos.IDLOCAL "
              + " AND tblTerceros.idCliente         "
              + "              = tblDctos.idCliente "
              + " WHERE tblDctos.IDLOCAL   =        "
              + "?2                    "
              + " AND IDTIPOORDEN          =        "
              + "?3                 "
              + " AND tblDctos.idCliente   =        "
              + "?4                  "
              + " AND tblDctos.idPeriodo   =        "
              + "?5 ",
              nativeQuery = true)
	  List<TblDctosDTO> listaDctoRepAbono(int ValorAbono, int idLocal, int xIdTipoOrden, String idCliente, int idPeriodo);
	  
	  
	  
	  @Query(value = "SELECT tblDctosOrdenesDetalle.IDPLU,             "
              + "       tblDctosOrdenesDetalle.NOMBREPLU,         "
              + "       tblDctosOrdenesDetalle.VRVENTAUNITARIO,   "
              + " 	  tblDctosOrdenesDetalle.idCliente,         "
              + " 	  tblTerceros.nombreTercero,                "
              + " 	  tblDctosOrdenes.idperiodo                 "
              + " FROM     tblDctosOrdenes                        "
              + " INNER JOIN tblDctosOrdenesDetalle               "
              + " ON tblDctosOrdenes.IDLOCAL      =               "
              + "                 tblDctosOrdenesDetalle.IDLOCAL  "
              + " AND tblDctosOrdenes.IDTIPOORDEN =               "
              + "             tblDctosOrdenesDetalle.IDTIPOORDEN  "
              + " AND tblDctosOrdenes.IDORDEN    =                "
              + "                 tblDctosOrdenesDetalle.IDORDEN  "
              + " INNER JOIN tblTerceros                          "
              + " ON tblTerceros.idLocal =                        "
              + "                 tblDctosOrdenesDetalle.IDLOCAL  "
              + " AND tblTerceros.idCliente =                     "
              + "               tblDctosOrdenesDetalle.idCliente  "
              + " WHERE tblDctosOrdenes.idLocal                =  "
              + "?1                                   "
              + " AND   tblDctosOrdenes.IDTIPOORDEN            =  "
              + "?2                              "
              + " AND tblDctosOrdenes.idPeriodo                =  "
              + "?3                                 "
              + " AND tblDctosOrdenesDetalle.IDTIPO NOT IN (4,6)  "
              + " AND tblDctosOrdenesDetalle.VRVENTAUNITARIO!=0   "
              + " ORDER BY tblDctosOrdenesDetalle.IDPLU,          "
              + "      tblTerceros.nombreTercero",
              nativeQuery = true)
	  List<TblDctosDTO> listaNovedad(int idLocal, int xIdTipoOrden, int idPeriodo);
	  
	  
	  
	  
}
