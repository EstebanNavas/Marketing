package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblPagos;
import com.marketing.Projection.TblPagosDTO;

@Repository
public interface TblPagosRepo extends JpaRepository<TblPagos, Integer> {

	
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM tblpagos " +
	                 "WHERE tblpagos.idLocal  =  ?1 " +
	                 "AND  tblpagos.idTipoOrden IN (7,9,29, 59) " +
	                 "AND  tblpagos.idPeriodo  = ?2 ", nativeQuery = true)
	  public void retiraPago(int idLocal, int idPeriodo);
	  
	  
	  
	  @Query(value = " SELECT tblpagos.nitCC AS idCliente,       "
              + "        tblterceros.nombreTercero,         "
              + "        tblpagos.idLocal,                  "
              + "        tblpagos.idTipoOrden,              "
              + "        tblpagos.idRecibo,                 "
              + "        tblpagos.indicador,                "
              + "        tblpagos.fechaPago,                "
              + "        tmpMED.vrMedio AS vrPago,          "
              + "        tblpagos.vrRteFuente,              "
              + "        tblpagos.vrDescuento,              "
              + "        tblpagos.vrRteIva,                 "
              + "        tblpagos.idDcto,                   "
              + "        tblpagos.idDctoNitCC,              "
              + "        tblpagos.idPlanilla,               "
              + "        tblpagos.vrRteIca,                 "
              + "        tbltercerosruta.nombreCiclo        "
              + "                            + ' / ' +      "
              + "        tbltercerosruta.nombreRuta         "
              + "                           AS nombreRuta,  "
              + "        tblterceroestracto.nombreEstracto, "
              + "        tblpagos.idPeriodo,                "
              + "        ctrlusuarios.nombreUsuario         "
              + "                        AS aliasUsuario,   "
              + "        tmpMED.idMedio,                    "
              + "        tblmediospago.nombreMedio,         "
              + "        tblTerceros.CC_Nit                 "
              + " FROM tblpagos                             "
              + " INNER JOIN tblterceros                    "
              + " ON tblpagos.idLocal = tblterceros.idLocal "
              + " AND tblpagos.nitCC =                      "
              + "                     tblterceros.idCliente "
              + " INNER JOIN tblterceroestracto             "
              + " ON tblterceros.idLocal     =              "
              + "               tblterceroestracto.idLocal  "
              + " AND tblterceros.idEstracto =              "
              + "            tblterceroestracto.idEstracto  "
              + " INNER JOIN tbltercerosruta                "
              + " ON tblterceros.idLocal  =                 "
              + "                   tbltercerosruta.idLocal "
              + " AND tblterceros.idRuta     =              "
              + "                    tbltercerosruta.idRuta "
              + " INNER JOIN ctrlusuarios                   "
              + " ON tblpagos.idLocal =                     "
              + "                      ctrlusuarios.idLocal "
              + " AND tblpagos.idUsuario =                  "
              + "                  ctrlusuarios.idUsuario   "
              + " INNER JOIN			              "
              + " ( SELECT  idLocal                         "
              + "       ,idTipoOrden                        "
              + "       ,idRecibo                           "
              + "       ,indicador                          "
              + "       ,idMedio                            "
              + "       ,SUM(vrMedio) AS vrMedio            "
              + "   FROM tblpagosmedios                     "
              + "   WHERE idLocal =                         "
              + "?1                             "
              + "   AND idTipoOrden = 9                     "
              + "   GROUP BY  idLocal                       "
              + "       ,idTipoOrden                        "
              + "       ,idRecibo                           "
              + "       ,indicador                          "
              + " 	  ,idMedio ) AS tmpMED                "
              + " ON tmpMED.idLocal =  tblpagos.idLocal     "
              + " AND tmpMED.idTipoOrden =                  "
              + "                      tblpagos.idTipoOrden "
              + " AND tmpMED.idRecibo = tblpagos.idRecibo   "
              + " AND tmpMED.indicador = tblpagos.indicador "
              + " INNER JOIN tblmediospago                  "
              + " ON tblmediospago.idMedio = tmpMED.idMedio "
              + " AND	tblmediospago.idLocal = tmpMED.idLocal "
              + " WHERE  tblpagos.idLocal   =        "
              + "?1                      "
              + " AND  tblpagos.idTipoOrden = 9      "
              + " AND (tblpagos.indicador            "
              + "               BETWEEN 1 AND 2)     "
              + " AND  tblpagos.idPeriodo   =        "
              + "?2                    "
              + " ORDER BY tmpMED.idMedio,           "
              + "          tblpagos.fechaPago,       "
              + "          tblpagos.idRecibo ", nativeQuery = true)
	  List<TblPagosDTO> listaRepRecaudoPeriodo(int idLocal, int idPeriodo);
	  
	  
	  
	  
	  @Query(value = " SELECT tblpagos.nitCC              "
              + "              AS idCliente,         "
              + " tblterceros.nombreTercero,         "
              + " tblpagos.idLocal,                  "
              + " tblpagos.idTipoOrden,              "
              + " tblpagos.idRecibo,                 "
              + " tblpagos.indicador,                "
              + " tblpagos.fechaPago,                "
              + " tmpMED.vrMedio AS vrPago,          "
              + " tblpagos.vrRteFuente,              "
              + " tblpagos.vrDescuento,              "
              + " tblpagos.vrRteIva,                 "
              + " tblpagos.idDcto,                   "
              + " tblpagos.idDctoNitCC,              "
              + " tblpagos.idPlanilla,               "
              + " tblpagos.vrRteIca,                 "
              + " tbltercerosruta.nombreCiclo        "
              + "                     + ' / ' +      "
              + " tbltercerosruta.nombreRuta         "
              + "                 AS nombreRuta,     "
              + " tblterceroestracto.nombreEstracto, "
              + " tblpagos.idPeriodo,                "
              + " ctrlusuarios.nombreUsuario         "
              + "                 AS aliasUsuario,   "
              + " tmpMED.idMedio,                    "
              + " tblmediospago.nombreMedio,         "
              + "        tblTerceros.CC_Nit           "
              + " FROM tblpagos                      "
              + " INNER JOIN tblterceros             "
              + " ON tblpagos.idLocal   =            "
              + "     tblterceros.idLocal            "
              + " AND tblpagos.nitCC    =            "
              + "   tblterceros.idCliente            "
              + " INNER JOIN tblterceroestracto      "
              + " ON tblterceros.idLocal     =       "
              + "    tblterceroestracto.idLocal      "
              + " AND tblterceros.idEstracto =       "
              + "   tblterceroestracto.idEstracto    "
              + " INNER JOIN tbltercerosruta         "
              + " ON tblterceros.idLocal     =       "
              + "         tbltercerosruta.idLocal    "
              + " AND tblterceros.idRuta     =       "
              + "          tbltercerosruta.idRuta    "
              + " INNER JOIN ctrlusuarios            "
              + " ON tblpagos.idLocal        =       "
              + "             ctrlusuarios.idLocal   "
              + " AND tblpagos.idUsuario     =       "
              + "           ctrlusuarios.idUsuario   "
              + " INNER JOIN			       "
              + " ( SELECT  idLocal                  "
              + "       ,idTipoOrden                 "
              + "       ,idRecibo                    "
              + "       ,indicador                   "
              + "       ,idMedio                     "
              + "       ,SUM(vrMedio)                "
              + " 	        AS vrMedio             "
              + "   FROM tblpagosmedios              "
              + "   WHERE idLocal =                  "
              + "?1                      "
              + "   AND idTipoOrden = 9              "
              + "   GROUP BY  idLocal                "
              + "       ,idTipoOrden                 "
              + "       ,idRecibo                    "
              + "       ,indicador                   "
              + " 	  ,idMedio ) AS tmpMED         "
              + " ON 	 tmpMED.idLocal     =          "
              + "          tblpagos.idLocal          "
              + " AND  tmpMED.idTipoOrden =          "
              + "      tblpagos.idTipoOrden          "
              + " AND  tmpMED.idRecibo    =          "
              + "         tblpagos.idRecibo          "
              + " AND  tmpMED.indicador   =          "
              + "         tblpagos.indicador	       "
              + " INNER JOIN tblmediospago           "
              + " ON 	tblmediospago.idMedio =        "
              + "         	tmpMED.idMedio	       "
              + " AND  tblmediospago.idLocal =       "
              + "         	tmpMED.idLocal	       "
              + " WHERE  tblpagos.idLocal   =        "
              + "?1                      "
              + " AND  tblpagos.idTipoOrden = 9      "
              + " AND (tblpagos.indicador            "
              + "               BETWEEN 1 AND 2)     "
              + " AND  tblpagos.idPeriodo   =        "
              + "?2                   "
              + "AND tbltercerosruta.idRuta =        "
              + "?3                       "
              + " ORDER BY tmpMED.idMedio,           "
              + "          tblpagos.fechaPago,       "
              + "          tblpagos.idRecibo ", nativeQuery = true)
	  List<TblPagosDTO> listaRepRecaudoPeriodoRuta(int idLocal, int idPeriodo, int idRuta);
	  
	  
	  
	  @Query(value = " SELECT tblpagos.nitCC              "
              + "              AS idCliente,         "
              + " tblterceros.nombreTercero,         "
              + " tblpagos.idLocal,                  "
              + " tblpagos.idTipoOrden,              "
              + " tblpagos.idRecibo,                 "
              + " tblpagos.indicador,                "
              + " tblpagos.fechaPago,                "
              + " tmpMED.vrMedio AS vrPago,          "
              + " tblpagos.vrRteFuente,              "
              + " tblpagos.vrDescuento,              "
              + " tblpagos.vrRteIva,                 "
              + " tblpagos.idDcto,                   "
              + " tblpagos.idDctoNitCC,              "
              + " tblpagos.idPlanilla,               "
              + " tblpagos.vrRteIca,                 "
              + " tbltercerosruta.nombreCiclo        "
              + "                     + ' / ' +      "
              + " tbltercerosruta.nombreRuta         "
              + "                 AS nombreRuta,     "
              + " tblterceroestracto.nombreEstracto, "
              + " tblpagos.idPeriodo,                "
              + " ctrlusuarios.nombreUsuario         "
              + "                 AS aliasUsuario,   "
              + " tmpMED.idMedio,                    "
              + " tblmediospago.nombreMedio,         "
              + "    tblTerceros.CC_Nit              "
              + " FROM tblpagos                      "
              + " INNER JOIN tblterceros             "
              + " ON tblpagos.idLocal   =            "
              + "     tblterceros.idLocal            "
              + " AND tblpagos.nitCC    =            "
              + "   tblterceros.idCliente            "
              + " INNER JOIN tblterceroestracto      "
              + " ON tblterceros.idLocal     =       "
              + "    tblterceroestracto.idLocal      "
              + " AND tblterceros.idEstracto =       "
              + "   tblterceroestracto.idEstracto    "
              + " INNER JOIN tbltercerosruta         "
              + " ON tblterceros.idLocal     =       "
              + "         tbltercerosruta.idLocal    "
              + " AND tblterceros.idRuta     =       "
              + "          tbltercerosruta.idRuta    "
              + " INNER JOIN ctrlusuarios            "
              + " ON tblpagos.idLocal        =       "
              + "             ctrlusuarios.idLocal   "
              + " AND tblpagos.idUsuario     =       "
              + "           ctrlusuarios.idUsuario   "
              + " INNER JOIN			       "
              + " ( SELECT  idLocal                  "
              + "       ,idTipoOrden                 "
              + "       ,idRecibo                    "
              + "       ,indicador                   "
              + "       ,idMedio                     "
              + "       ,SUM(vrMedio)                "
              + " 	        AS vrMedio             "
              + "   FROM tblpagosmedios              "
              + "   WHERE idLocal =                  "
              + "?1                      "
              + "   AND idTipoOrden = 9              "
              + "   GROUP BY  idLocal                "
              + "       ,idTipoOrden                 "
              + "       ,idRecibo                    "
              + "       ,indicador                   "
              + " 	  ,idMedio ) AS tmpMED         "
              + " ON 	 tmpMED.idLocal     =          "
              + "          tblpagos.idLocal          "
              + " AND  tmpMED.idTipoOrden =          "
              + "      tblpagos.idTipoOrden          "
              + " AND  tmpMED.idRecibo    =          "
              + "         tblpagos.idRecibo          "
              + " AND  tmpMED.indicador   =          "
              + "         tblpagos.indicador	       "
              + " INNER JOIN tblmediospago           "
              + " ON 	tblmediospago.idMedio =        "
              + "         	tmpMED.idMedio	       "
              + " AND  tblmediospago.idLocal =       "
              + "         	tmpMED.idLocal	       "
              + " WHERE  tblpagos.idLocal   =        "
              + "?1                      "
              + " AND  tblpagos.idTipoOrden = 9      "
              + " AND (tblpagos.indicador            "
              + "               BETWEEN 1 AND 2)     "
              + " AND  tblpagos.idPeriodo   =        "
              + "?2                    "
              + "AND tblpagos.fechaPago     =       "
              + "?3             "
              + " ORDER BY tmpMED.idMedio,           "
              + "          tblpagos.fechaPago,       "
              + "          tblpagos.idRecibo ", nativeQuery = true)
	  List<TblPagosDTO> listaRepRecaudoPeriodoFecha(int idLocal, int idPeriodo, String fecha);
	  
	  
	  
	  @Query(value = "SELECT tblpagos.idLocal,                    "
              + " 	   MAX(tblpagos.idPeriodo) AS idPeriodo,"
              + " 	   MAX(tblplus.idTipo) AS idTipo,       "
              + " 	   tblpagosmedios.IDPLU,                "
              + " 	   (tblcategorias.nombreCategoria+' '+  "
              + " 	   tblplus.nombrePlu) AS nombrePlu,     "
              + " 	   SUM(tblpagosmedios.vrMedio)          "
              + " 	                             AS vrMedio "
              + " FROM     tblpagos                           "
              + " INNER JOIN tblpagosmedios                   "
              + " ON tblpagos.idLocal =                       "
              + "                      tblpagosmedios.idLocal "
              + " AND tblpagos.idTipoOrden =                  "
              + "                  tblpagosmedios.idTipoOrden "
              + " AND tblpagos.idRecibo =                     "
              + "                     tblpagosmedios.idRecibo "
              + " AND tblpagos.indicador =                    "
              + "                    tblpagosmedios.indicador "
              + " INNER JOIN tblplus                          "
              + " ON tblplus.idLocal = tblpagosmedios.idLocal "
              + " AND tblplus.idPlu = tblpagosmedios.idPlu    "
              + " INNER JOIN tblcategorias                    "
              + " ON tblplus.idLocal = tblcategorias.idLocal  "
              + " AND tblplus.idLinea = tblcategorias.idLinea "
              + " AND tblplus.idCategoria =                   "
              + "                  tblcategorias.idCategoria  "                
              + " where tblpagos.idlocal =                    "
              + "?1                               "
              + " AND tblpagos.idTipoOrden IN (9,29)          "
              + " AND tblpagosmedios.vrMedio != 0             "
              + " AND tblpagos.idPeriodo =                    "
              + "?2                             "
              + " GROUP BY  tblpagos.idLocal,                 "
              + "           tblpagosmedios.IDPLU,             "
              + " 	      tblcategorias.nombreCategoria,    "
              + " 	      tblplus.nombrePlu", nativeQuery = true)
	  List<TblPagosDTO> listaRecuadoRubro(int idLocal, int idPeriodo);
	  
	  
	  @Query(value = "SELECT tblpagos.idLocal,                    "
              + " 	   MAX(tblpagos.idPeriodo) AS idPeriodo,"
              + " 	   MAX(tblplus.idTipo) AS idTipo,       "
              + " 	   tblpagosmedios.IDPLU,                "
              + " 	   (tblcategorias.nombreCategoria+' '+  "
              + " 	   tblplus.nombrePlu) AS nombrePlu,     "
              + " 	   SUM(tblpagosmedios.vrMedio)          "
              + " 	                             AS vrMedio "
              + " FROM     tblpagos                           "
              + " INNER JOIN tblpagosmedios                   "
              + " ON tblpagos.idLocal =                       "
              + "                      tblpagosmedios.idLocal "
              + " AND tblpagos.idTipoOrden =                  "
              + "                  tblpagosmedios.idTipoOrden "
              + " AND tblpagos.idRecibo =                     "
              + "                     tblpagosmedios.idRecibo "
              + " AND tblpagos.indicador =                    "
              + "                    tblpagosmedios.indicador "
              + " INNER JOIN tblplus                          "
              + " ON tblplus.idLocal = tblpagosmedios.idLocal "
              + " AND tblplus.idPlu = tblpagosmedios.idPlu    "
              + " INNER JOIN tblcategorias                    "
              + " ON tblplus.idLocal = tblcategorias.idLocal  "
              + " AND tblplus.idLinea =                       "
              + "                       tblcategorias.idLinea "                
              + " AND tblplus.idCategoria =                   "
              + "                  tblcategorias.idCategoria  "
              + " where tblpagos.idlocal =                    "
              + "?1                              "
              + " AND tblpagos.idTipoOrden IN (9,29)          "
              + " AND tblpagosmedios.vrMedio <> 0             "
              + " AND tblpagos.idPeriodo =                    "
              + "?2                             "
              + " AND tblpagos.fechaPago     =   ?3           "
              + " GROUP BY  tblpagos.idLocal,                 "
              + "           tblpagosmedios.IDPLU,             "
              + " 	      tblcategorias.nombreCategoria,    "
              + " 	      tblplus.nombrePlu", nativeQuery = true)
	  List<TblPagosDTO> listaRecuadoRubroFecha(int idLocal, int idPeriodo, String fecha);
	  
	  
	  @Query(value = "SELECT tbldctos.idLocal,                        "
              + "       tbldctos.idTipoOrden,                    "
              + "       tbldctos.idDcto,                         "
              + "       MAX(tbldctos.idCliente) AS  idCliente,   "
              + "       MAX(tmpTER.CC_Nit) AS nitCC,            "
              + "       MAX(tbldctos.nombreTercero) AS           "
              + "                              nombreTercero,    "
              + "       MAX(tmpTER.nombreRuta) AS nombreRuta,    "
              + "       MAX(tmpTER.nombreEstracto) AS            "
              + "                              nombreEstracto,   "
              + "       tblpagosmedios.idPlu,                    "
              + "       MAX(tblcategorias.nombreCategoria+' '+   "
              + "           tblplus.nombrePlu) AS nombrePlu,     "
              + "       (tblpagosmedios.vrMedio)   AS vrMedio   "
              + "FROM tblpagos                                   "
              + "INNER JOIN tblpagosmedios                       "
              + "ON tblpagos.idLocal = tblpagosmedios.idLocal    "
              + "AND tblpagos.idTipoOrden =                      "
              + "                 tblpagosmedios.idTipoOrden     "
              + "AND tblpagos.idRecibo = tblpagosmedios.idRecibo "
              + "AND tblpagos.indicador =                        "
              + "                       tblpagosmedios.indicador "
              + "INNER JOIN tblplus                              "
              + "ON tblplus.idLocal = tblpagosmedios.idLocal     "
              + "AND tblplus.idPlu = tblpagosmedios.idPlu        "
              + "INNER JOIN tblcategorias                        "
              + "ON tblplus.idLocal = tblcategorias.idLocal      "
              + "AND tblplus.idCategoria =                       "
              + "                      tblcategorias.idCategoria "
              + "INNER JOIN tbldctos                             "
              + "ON tbldctos.idLocal = tblpagos.IDLOCAL          "
              + "AND tbldctos.idtipoorden = tblpagos.idtipoorden "
              + "AND tbldctos.idCliente  = tblpagos.nitCC        "
              + "AND tbldctos.idPeriodo  = tblpagos.idPeriodo    "
              + "INNER JOIN                                      "
              + "    ( SELECT tblterceros.idLocal,               "
              + "             tblterceros.idCliente,             "
              + "             tblterceros.CC_Nit,                "
              + "             tblterceros.nombreTercero,         "
              + "             (tbltercerosruta.nombreCiclo+' '+  "
              + "             tbltercerosruta.nombreRuta)        "
              + "                                AS nombreRuta,  "
              + "             tblterceroestracto.nombreEstracto  "
              + "     FROM tblterceros                           "
              + "     INNER JOIN tbltercerosruta                 "
              + "     ON tblterceros.idLocal                     "
              + "                      = tbltercerosruta.idLocal "
              + "     AND tblterceros.idRuta                     "
              + "                      = tbltercerosruta.idRuta  "
              + "     INNER JOIN  tblterceroestracto             "
              + "     ON tblterceros.idLocal =                   "
              + "                   tblterceroestracto.idLocal   "
              + "     AND tblterceros.idEstracto =               "
              + "              tblterceroestracto.idEstracto )   "
              + "                               AS tmpTER        "
              + "     ON tmpTER.idCliente  = tbldctos.idCliente  "
              + "     AND tmpTER.idLocal   = tbldctos.idLocal    "
              + "     where tblpagos.idlocal =                   "
              + "?1                                  "
              + "AND   tblpagos.idTipoOrden  IN (9,29)           "
              + "     AND tblpagosmedios.vrMedio <> 0            "
              + "     AND tblpagos.idPeriodo =                   "
              + "?2                                "
              + "GROUP BY tbldctos.IDdcto,                       "
              + "         tblpagosmedios.IDPLU,                  "
              + "         tbldctos.idLocal,                      "
              + "         tbldctos.idTipoOrden,                  "
              + "         tblpagosmedios.vrMedio                 "
              + "ORDER BY tbldctos.idLocal,                      "
              + "         tbldctos.idtipoOrden,                  "
              + "         tbldctos.idDcto ", nativeQuery = true)
	  List<TblPagosDTO> listaDetalleRecaudo(int idLocal, int idPeriodo);
	  
	  
	  
	  @Query(value = " SELECT tblpagos.idLocal               "
              + "        ,tblpagos.idTipoOrden        "
              + "        ,tblpagos.idRecibo           "
              + "        ,tblpagos.fechaPago          "
              + "        ,tblpagos.vrPago             "
              + "        ,tblpagos.nitCC              "
              + "        ,tblterceros.nombreTercero AS nombreCliente   "
              + "        ,tblpagos.idUsuario          "
              + "        ,tblpagos.idPeriodo          "
              + "        ,tblpagos.idDcto             "
              + "        ,tblpagos.idDctoNitCC        "
              + "        ,tblpagos.idPlanilla         "
              + "        ,tblpagos.vrSaldo            "
              + "  ,tblterceroestracto.nombreEstracto "
              + "  FROM tblpagos                      "
              + "  INNER JOIN tblterceros             "
              + "  ON tblterceros.idLocal  =          "
              + "                 tblpagos.idLocal    "
              + "  AND tblterceros.idCliente  =       "
              + "                 tblpagos.nitCC      "
              + "  INNER JOIN tblterceroestracto      "
              + "  ON tblterceros.idLocal  =          "
              + "      tblterceroestracto.idLocal     "
              + "  AND tblterceros.idEstracto  =      "
              + "    tblterceroestracto.idEstracto    "
              + "  WHERE tblpagos.idLocal      =      "
              + "?1                       "
              + "  AND tblpagos.idTipoOrden    =      "
              + "?2                   "
              + "  AND tblpagos.idPeriodo      =      "
              + "?3                     "
              + "  ORDER BY fechaPago ", nativeQuery = true)
	  List<TblPagosDTO> listaAllRecaudo(int idLocal, int xIdTipoOrden, int idPeriodo);
	  
	  
	  
}
