package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblDctosOrdenesDetalle;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblMailMarketingReporteDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;

@Repository
public interface TblDctosOrdenesDetalleRepo extends JpaRepository<TblDctosOrdenesDetalle, Integer> {

	// Modificamos el IDTIPOORDEN de 67 a 17
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblDctosOrdenesDetalle SET IDTIPOORDEN = 17, item = 1 " +
	                 "WHERE IDTIPOORDEN = 67 AND tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2", nativeQuery = true)
	  public void actualizarIdTipoOrdenDetalle(int idLocal, int IDORDEN);
	  
	  
	// Eliminamos los registros del IDORDEN de ese momento
		  @Modifying
		  @Transactional
		  @Query(value = "DELETE FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
		                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
		                 "AND tblDctosOrdenesDetalle.IDTIPOORDEN = 67", nativeQuery = true)
		  public void eliminarRegistros(int idLocal, int IDORDEN);
	  
		  
		  @Query(value = "SELECT NOMBREPLU " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.idCliente = ?3", nativeQuery = true)
		  List<String> ObtenerNombresPlus(int idLocal, int IDORDEN, int idCliente);
		  
		  @Query(value = "SELECT DISTINCT comentario " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.idCliente = ?3", nativeQuery = true)
		  String ObtenerComentario(int idLocal, int IDORDEN, int idCliente);
		  
		  @Query(value = "SELECT * " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.IDTIPOORDEN = 17 "
	                 , nativeQuery = true)
		  List<TblDctosOrdenesDetalleDTO> ObtenerInfoPQR(int idLocal, int IDORDEN);
		  
		  
		  @Query(value = "SELECT tblDctosOrdenesDetalle.item " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.IDTIPOORDEN = 17 "
	                 , nativeQuery = true)
		  List<String> ObtenerItems(int idLocal, int IDORDEN);
		  
		  
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblDctosOrdenesDetalle SET ESTADO = 9 " +
		                 "WHERE IDTIPOORDEN = 17 AND tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2", nativeQuery = true)
		  public void actualizarEstadoDetalle(int idLocal, int IDORDEN);
		  
		  
		  @Query(value = "SELECT DISTINCT IDORDEN " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.ESTADO = 9 " 
	                 , nativeQuery = true)
		  Integer ObtenerIdOrdenEstado9(int idLocal);
		  
		  @Query(value = "SELECT ESTADO " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.item = 2 " 
	                 , nativeQuery = true)
		  List<String> ObtenerEstado9(int idLocal);
		  
		  @Query(value = "SELECT DISTINCT comentario " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.idCliente = ?3 " +
	                 "AND tblDctosOrdenesDetalle.item = 2 ", nativeQuery = true)
		  String ObtenerComentarioRespuesta(int idLocal, int IDORDEN, int idCliente);
		  
		  @Query(value = "SELECT DISTINCT comentario " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.idCliente = ?3 " +
	                 "AND tblDctosOrdenesDetalle.item = 1 ", nativeQuery = true)
		  String ObtenerComentarioPQR(int idLocal, int IDORDEN, int idCliente);
		  
			// Eliminamos los registros del IDORDEN de la respuesta de ese momento
		  @Modifying
		  @Transactional
		  @Query(value = "DELETE FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
		                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
		                 "AND tblDctosOrdenesDetalle.IDTIPOORDEN = 17 " +
		                 "AND tblDctosOrdenesDetalle.item = 2 ", nativeQuery = true)
		  public void eliminarRegistrosRespuesta(int idLocal, int IDORDEN);
		  
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblDctosOrdenesDetalle SET ESTADO = 1 " +
		                 "WHERE IDTIPOORDEN = 17 AND tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
		                 "AND tblDctosOrdenesDetalle.idCliente = ?3", nativeQuery = true)
		  public void actualizarEstadoDetalleFinal(int idLocal, int IDORDEN, String idCliente);
		  
		  
			@Query(
					value = "SELECT tblDctosOrdenesDetalle.NOMBREPLU " +
							"FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
							"JOIN bdaquamovil.dbo.tblPlus "+
							"ON tblPlus.idLocal  = tblDctosOrdenesDetalle.IDLOCAL " +
							"AND tblPlus.idPlu  = tblDctosOrdenesDetalle.idPlu " +
							"WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
							"AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
							"AND tblDctosOrdenesDetalle.idCliente = ?3 " +
							"AND tblPlus.idCategoria = ?4 " +
							"AND tblPlus.idLinea = 200 " + 
							"AND tblDctosOrdenesDetalle.idTipoOrden = 17 ",
					nativeQuery = true
					)

			String ObtenerNombrePlu(int idLocal, int IDORDEN, String idCliente, int idCategoria);
			
			
			
			  @Modifying
			  @Transactional
			  @Query(value = "SELECT MAX(t1.IDLOCAL) AS idLocal,  " +
			                 "?2   AS idPeriodo, " +
			                 "t1.idCliente, " +
			                 "('c'+ " +
			                 "CAST(tbldctosordenes.idPeriodo AS VARCHAR(100)) " +
			                 "+'g'+ " +
			                 "CAST(SUM(t1.CANTIDAD) AS VARCHAR(100))) " +
			                 "AS historicoConsumo " +
			                 "INTO tmp_historicoConsumo " +
			                 "FROM tbldctosordenesdetalle t1 " +
			                 "INNER JOIN  tblterceros " +
			                 "ON t1.IDLOCAL = tblterceros.idLocal " +
			                 "AND t1.idCliente =  tblterceros.idCliente " +
			                 "INNER JOIN tbldctosordenes " +
			                 "ON t1.IDLOCAL = tbldctosordenes.IDLOCAL " +
			                 "AND t1.IDTIPOORDEN =  tbldctosordenes.IDTIPOORDEN " +
			                 "AND t1.IDORDEN =  tbldctosordenes.IDORDEN " +
			                 "WHERE t1.IDLOCAL = ?1 " +
			                 "AND t1.IDTIPOORDEN =  9 " +
			                 "AND tbldctosordenes.idPeriodo IN (?3) " +
			                 "AND t1.IDTIPO = 4 " +
			                 "GROUP BY t1.idCliente, tbldctosordenes.idPeriodo " +
			                 "ORDER BY t1.idCliente "
			                 , nativeQuery = true)
			  public void creaTablaHistoricoConsumo(int idLocal, int xIdPeriodo, List<String> xIdPeriodoLista);
			
			
			
			  @Modifying
			  @Transactional
			  @Query(value = "DELETE FROM tbldctosordenesdetalle " +
			                 "FROM  tbldctosordenes " +
			                 "JOIN tbldctosordenesdetalle " +
			                 "ON tbldctosordenes.IDLOCAL  =  tbldctosordenesdetalle.IDLOCAL " +
			                 "AND tbldctosordenes.IDTIPOORDEN = tbldctosordenesdetalle.IDTIPOORDEN  " +
			                 "AND tbldctosordenes.IDORDEN  = tbldctosordenesdetalle.IDORDEN " +
			                 "WHERE  tbldctosordenes.IDLOCAL  = ?1 " +
			                 "AND tbldctosordenes.IDTIPOORDEN  IN (7,9,29, 59) " +			                 
			                 "AND tbldctosordenes.idPeriodo  = ?2 ", nativeQuery = true)
			  public void retiraOrdenesDetalle(int idLocal, int idPeriodo);
			
			
			  
			  @Modifying
			  @Transactional
			  @Query(value = "UPDATE tbldctosordenesdetalle " +
			                 "SET  itemPadre = 1 " +
			                 ",tbldctosordenesdetalle.estado = 0 " +
			                 "FROM tbldctosordenes " +
			                 "JOIN tbldctosordenesdetalle " +
			                 "ON tbldctosordenes.IDLOCAL  =  tbldctosordenesdetalle.IDLOCAL " +
			                 "AND tbldctosordenes.IDTIPOORDEN =  tbldctosordenesdetalle.IDTIPOORDEN " +
			                 "AND tbldctosordenes.IDORDEN   =   tbldctosordenesdetalle.IDORDEN " +
			                 "WHERE tbldctosordenes.IDLOCAL   =   ?1 " +
			                 "AND  tbldctosordenes.IDTIPOORDEN = 7 " +
			                 "AND  tbldctosordenesdetalle.itempadre =  ?2 " +
			                 "AND tbldctosordenesdetalle.itemPadre !=1 ", nativeQuery = true)
			  public void recuperaOrdenesFinaciacion(int idLocal, int idPeriodo);
			
			
			
			
			  @Query(value = "SELECT tbldctosordenesdetalle.idLocal,        "
		                + "        tbldctosordenesdetalle.idTipo,        "
		                + "        tbldctosordenesdetalle.idPlu,         "
		                + "        SUM(tbldctosordenesdetalle.cantidad * "
		                + "                  vrVentaUnitario) AS vrBase, "
		                + "        MAX(tmpCAT.NOMBREPLU) AS nombrePlu    "
		                + " FROM       tbldctosordenesdetalle            "
		                + " INNER JOIN tbldctos                          "
		                + " ON tbldctos.idLocal     =                    "
		                + "   tbldctosordenesdetalle.IDLOCAL "
		                + " AND tbldctos.idtipoorden=                    "
		                + "           tbldctosordenesdetalle.idtipoorden "
		                + " AND tbldctos.idorden    =                    "
		                + "   tbldctosordenesdetalle.idorden "
		                + " INNER JOIN                                   "
		                + " (SELECT tblplus.idLocal,                     "
		                + "         tblplus.idPlu,                       "
		                + "       (tblcategorias.nombreCategoria + ' ' + "
		                + "         tblplus.nombrePlu) AS nombrePlu      "
		                + " FROM  tblcategorias                          "
		                + " INNER JOIN tblplus                           "
		                + " ON tblcategorias.idLocal = tblplus.idLocal   "
		                + " AND tblcategorias.idLinea = tblplus.idLinea  "
		                + " AND tblcategorias.IdCategoria =              "
		                + "             tblplus.idCategoria) AS tmpCAT   "
		                + " ON tmpCAT.idLocal =                          "
		                + "   tbldctosordenesdetalle.idLocal "
		                + " AND tmpCAT.idPlu  =                          "
		                + "     tbldctosordenesdetalle.idPlu "
		                + " INNER JOIN tblterceros                       "
		                + " ON tblterceros.idCliente =                   "
		                + "              tbldctos.idCliente  "
		                + " AND tbldctos.idLocal = tblterceros.idLocal   "
		                + " AND   tblterceros.idTipoTercero = 1          "
		                + " WHERE EXISTS                                 "
		                + "    ( SELECT *                                "
		                + "      FROM   tbldctosordenes                  "
		                + "      INNER JOIN tbldctosordenesdetalle       "
		                + "      ON tbldctosordenes.IDLOCAL      =       "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "      AND tbldctosordenes.IDTIPOORDEN =       "
		                + "         tbldctosordenesdetalle.IDTIPOORDEN   "
		                + "      AND tbldctosordenes.IDORDEN     =       "
		                + "         tbldctosordenesdetalle.IDORDEN       "
		                + "      WHERE tbldctosordenes.IDLOCAL   =       "
		                + "               tbldctos.IDLOCAL   "
		                + "      AND tbldctosordenes.IDTIPOORDEN =       "
		                + "           tbldctos.IDTIPOORDEN   "
		                + "      AND tbldctosordenes.IDORDEN     =       "
		                + "              tbldctos.IDORDEN)   "
		                + " AND tbldctos.idLocal   =                     "
		                + " ?1                                 "
		                + " AND tbldctos.idTipoOrden IN                 ("
		                + "?2 ,                       "
		                + "?3 )                       "
		                + " AND tbldctos.idPeriodo   =                   "
		                + "?4                             "
		                + " AND (tbldctosordenesdetalle.cantidad *       "
		                + "                       vrVentaUnitario) != 0  "
		                + " GROUP BY tbldctosordenesdetalle.idLocal,     "
		                + "          tbldctosordenesdetalle.idTipo,      "
		                + " 		 tbldctosordenesdetalle.idPlu    "
		                + " HAVING                                       "
		                + "      SUM(tbldctosordenesdetalle.cantidad *   "
		                + "                  vrVentaUnitario) != 0       "
		                + " ORDER BY tbldctosordenesdetalle.idLocal,     "
		                + "          idTipo,                             "
		                + " 	     idPlu", nativeQuery = true)
			  List<TblDctosOrdenesDetalleDTO> listaRubroAgrupado(int idLocal, int IdTipoOrdenINI, int IdTipoOrdenFIN, int idPeriodo);
			  
			  
			  
			  @Query(value = "SELECT tbldctos.idLocal,                    "
		                + "       tbldctos.idTipoOrden,                "
		                + "       tbldctos.idDcto,                     "
		                + "       MAX(tbldctos.idCliente) AS           "
		                + "		                   idCliente,  "
		                + "	  MAX(tmpTER.nombreTercero) AS         "
		                + "		                nombreTercero, "
		                + "       MAX(tmpTER.nombreRuta) AS            "
		                + "		                  nombreRuta,  "
		                + "	  MAX(tmpTER.nombreEstracto) AS        "
		                + "		               nombreEstracto, "
		                + "       tbldctosordenesdetalle.idPlu,        "
		                + "      SUM(tbldctosordenesdetalle.cantidad * "
		                + "                vrVentaUnitario) AS vrBase, "
		                + "       MAX(NOMBREPLU) AS nombrePlu          "
		                + "FROM tbldctosordenesdetalle                 "
		                + "INNER JOIN tbldctos                         "
		                + "ON [tbldctos].idLocal     =                 "
		                + " tbldctosordenesdetalle.IDLOCAL "
		                + "AND [tbldctos].idtipoorden=                 "
		                + "         tbldctosordenesdetalle.idtipoorden "
		                + "AND [tbldctos].idorden    =                 "
		                + "tbldctosordenesdetalle.idorden  "
		                + "INNER JOIN                                  "
		                + "( SELECT tblterceros.idLocal,               "
		                + "       tblterceros.idCliente,               "
		                + "	   tblterceros.nombreTercero,          "
		                + "	   (tbltercerosruta.nombreCiclo+' '+   "
		                + "	   tbltercerosruta.nombreRuta)         "
		                + "	                      AS nombreRuta,   "
		                + "	   tblterceroestracto.nombreEstracto   "
		                + "FROM     tblterceros                        "
		                + "INNER JOIN tbltercerosruta                  "
		                + "ON tblterceros.idLocal                      "
		                + "                = tbltercerosruta.idLocal   "
		                + "AND tblterceros.idRuta                      "
		                + "                 = tbltercerosruta.idRuta   "
		                + "INNER JOIN  tblterceroestracto              "
		                + "ON tblterceros.idLocal =                    "
		                + "  tblterceroestracto.idLocal    "
		                + "AND tblterceros.idEstracto =                "
		                + "           tblterceroestracto.idEstracto )  "
		                + "		                     AS tmpTER "
		                + "ON tmpTER.idCliente  = tbldctos.idCliente   "
		                + "AND tmpTER.idLocal   = tbldctos.idLocal     "
		                + "WHERE tbldctos.idLocal            =         "
		                +" ?1                              "
		                + " AND tbldctos.idTipoOrden IN               ("
		                + "?2 ,                     "
		                + "?3 )                     "
		                + "AND tbldctos.idPeriodo =                    "
		                + "?4                            "
		                + "AND tbldctosordenesdetalle.idEstracto  =    "
		                + "?5                           "
		                + "AND (tbldctosordenesdetalle.cantidad *      "
		                + "           vrVentaUnitario) != 0            "
		                + "GROUP BY tbldctos.IDdcto,                   "
		                + "         IDPLU,                             "
		                + "         tbldctos.idLocal,                  "
		                + "       tbldctos.idTipoOrden                 "
		                + "HAVING SUM(tbldctosordenesdetalle.cantidad * "
		                + "                 vrVentaUnitario)!=0        "
		                + "ORDER BY tbldctos.idLocal,                  "
		                + "         tbldctos.idtipoOrden,              "
		                + "         tbldctos.idDcto", nativeQuery = true)
			  List<TblDctosOrdenesDetalleDTO> listaDetalleRubroEstrato(int idLocal, int IdTipoOrdenINI, int IdTipoOrdenFIN, int idPeriodo, int idEstrato);
			  
			  
			  
			  @Query(value = " SELECT tbldctos.idLocal,                       "     
					  + "    tbldctos.idTipoOrden,                     "
					  + "    tbldctos.idDcto,                          "
					  + "    MAX(tbldctos.idCliente) AS                "
					  + "                     idCliente,               "
					  + "    MAX(tmpTER.nombreTercero) AS              "
					  + "                  nombreTercero,              "
					  + "    MAX(tmpTER.nombreRuta) AS                 "
					  + "                    nombreRuta,               "
					  + "    MAX(tmpTER.nombreEstracto) AS             "
					  + "                 nombreEstracto,              "
					  + "    tbldctosordenesdetalle.idPlu,             "
					  + "   CONVERT(DECIMAL(12, 2),                    "              
					  + "   SUM(tbldctosordenesdetalle.cantidad *      "
					  + "      vrVentaUnitario)) AS vrBase,            "              
					  + "    LTRIM(STR(tbldctosordenesdetalle.idPlu))  "
					  + "   +'-'+ MAX(tmpCAT.nombrePlu) AS nombrePlu,  "  
					  + "   MAX(tmpTER.CC_Nit) AS CC_Nit,              "
					  + "   tmpCAT.idProducto                          "
					  + "  FROM tbldctosordenesdetalle                 "
					  + "  INNER JOIN tbldctos                         "
					  + "  ON [tbldctos].idLocal     =                 "
					  + "   tbldctosordenesdetalle.IDLOCAL             "
					  + "  AND [tbldctos].idtipoorden=                 "
					  + "       tbldctosordenesdetalle.idtipoorden     "
					  + "  AND [tbldctos].idorden    =                 "
					  + "  tbldctosordenesdetalle.idorden              "
					  + "  INNER JOIN                                  "
					  + "  ( SELECT tblterceros.idLocal,               "
					  + "    tblterceros.idCliente,                    "
					  + "    tblterceros.nombreTercero,                "
					  + "    (tbltercerosruta.nombreCiclo+' '+         "
					  + "    tbltercerosruta.nombreRuta)               "
					  + "                       AS nombreRuta,         "
					  + "    tblterceroestracto.nombreEstracto,        "
					  + "     tblterceros.CC_Nit                       "
					  + "  FROM     tblterceros                        "
					  + "  INNER JOIN tbltercerosruta                  "
					  + "  ON tblterceros.idLocal                      "
					  + "                  = tbltercerosruta.idLocal   "
					  + "  AND tblterceros.idRuta                      "
					  + "                   = tbltercerosruta.idRuta   "
					  + "  INNER JOIN  tblterceroestracto              "
					  + "  ON tblterceros.idLocal =                    "
					  + "    tblterceroestracto.idLocal                "
					  + "  AND tblterceros.idEstracto =                "
					  + "             tblterceroestracto.idEstracto )  "
					  + "  		                     AS tmpTER   "
					  + "  ON tmpTER.idCliente  = tbldctos.idCliente   "
					  + "  AND tmpTER.idLocal   = tbldctos.idLocal     "
					  + " INNER JOIN				         "
					  + "  ( SELECT tblCategorias.idLocal,             "
					  + "     tblPlus.idPlu,                           "
					  + "     tblCategorias.idProducto,                "
					  + "     tblCategorias.nombreCategoria + ' ' +    "
					  + " 	   tblPlus.nombrePlu AS nombrePlu        "
					  + " FROM        tblCategorias                    "
					  + " INNER JOIN  tblPlus                          "
					  + " ON tblCategorias.idLocal      =              "
					  + "                   tblPlus.idLocal            "
					  + " AND tblCategorias.idLinea     =              "
					  + "                   tblPlus.idLinea            "
					  + " AND tblCategorias.IdCategoria =              "
					  + "  tblPlus.idCategoria ) AS tmpCAT             "
					  + "  ON tmpCAT.idLocal =                         "
					  + "       tbldctosordenesdetalle.idLocal         "
					  + "  AND tmpCAT.idPlu  =                         "
					  + "            tbldctosordenesdetalle.idPlu      "
					  + "  WHERE tbldctos.idLocal            =         " 
					  + "?1                                "                
					  + "AND tbldctos.idTipoOrden  BETWEEN             "
					  + "?2  AND                    "
					  + "?3                         "
					  + "  AND tbldctos.idPeriodo =                    "   
					  + "?4                              "                
					  + "  AND (tbldctosordenesdetalle.cantidad *      "
					  + "             vrVentaUnitario) != 0            "
					  + "  GROUP BY tbldctos.IDdcto,                   "
					  + "           tbldctosordenesdetalle.IDPLU,      "                       
					  + "           tbldctos.idLocal,                  " 
					  + "           tbldctos.idTipoOrden,              "
					  + "          tmpCAT.idProducto                   "
					  + " HAVING SUM(tbldctosordenesdetalle.cantidad * " 
					  + "                   vrVentaUnitario)!=0        "
					  + "  ORDER BY tbldctos.idLocal,                  "
					  + "           tbldctos.idtipoOrden,              "
					  + "           tbldctos.idDcto,                   "
					  + "  	 tmpCAT.idProducto  ", nativeQuery = true)
			  List<TblDctosOrdenesDetalleDTO> listaDetalleRubro(int idLocal, int IdTipoOrdenINI, int IdTipoOrdenFIN, int idPeriodo);
			  
			  
			  
			  @Query(value = " SELECT tmpDET.idLocal,                           "
					  + " 	   tblTerceroEstracto.nombreEstracto,      "
					  + " 	   tmpDET.idCliente,                       "
					  + " 	   tblTerceros.nombreTercero,              "
					  + " 	   tmpDET.grupo AS nombreDcto,             "
					  //+ " 	   tmpDET.idDcto,                          "
					  + " 	   tmpDET.idPlu,                           "
					  + " 	   tmpDET.nombrePlu,                       "
					  + " 	   tmpDET.vrVenta AS vrVentaConIva         "
					  + " FROM  tblTerceros                              "
					  + " INNER JOIN                                     "
					  + " (SELECT  tblDctosOrdenesDetalle.idLocal,       "
					  //+ "  tblDctos.idDcto,                              "
					  + "  tblDctosOrdenesDetalle.idPlu,                 "
					  + "  MAX(tblDctos.idCliente) AS idCliente,         "
					  + "  MAX(tmpPRO.nombrePlu) AS nombrePlu ,          "
					  + "  SUM( tblDctosOrdenesDetalle.VRVENTAUNITARIO * "
					  + "  tblDctosOrdenesDetalle.cantidad ) AS vrVenta, "
					  + " 	 'Factura' AS grupo,                       "
					  + " 	 tblDctos.idPeriodo                        "
					  + " FROM            tblDctos                       "
					  + " INNER JOIN tblDctosOrdenesDetalle              "
					  + " ON tblDctos.IDLOCAL      =                     "
					  + "      tblDctosOrdenesDetalle.IDLOCAL            "
					  + " AND tblDctos.IDTIPOORDEN =                     "
					  + "    tblDctosOrdenesDetalle.IDTIPOORDEN          "
					  + " AND tblDctos.IDORDEN     =                     "
					  + "      tblDctosOrdenesDetalle.IDORDEN            "
					  + " INNER JOIN (                                   "
					  + " SELECT tblPlus.idLocal,                        "
					  + "   tblPlus.idPlu,                               "
					  + "   tblCategorias.nombreCategoria + ' ' +        "
					  + "   tblPlus.nombrePlu AS nombrePlu,              "
					  + "   tblCategorias.idProducto                     "
					  + " FROM            tblCategorias                  "
					  + " INNER JOIN tblPlus                             "
					  + " ON tblCategorias.idLocal      =                "
					  + "                 tblPlus.idLocal                "
					  + " AND tblCategorias.idLinea     =                "
					  + "                 tblPlus.idLinea                "
					  + " AND tblCategorias.IdCategoria =                "
					  + "          tblPlus.idCategoria ) AS tmpPRO	   "
					  + " ON tmpPRO.idLocal         =                    "
					  + "        tblDctosOrdenesDetalle.idLocal          "
					  + " AND tmpPRO.idPlu          =                    "
					  + "         tblDctosOrdenesDetalle.idPlu           "
					  + " WHERE tblDctos.IDTIPOORDEN =  9                "
					  + " AND   tmpPRO.idProducto    =                   "
					  + " ?1                            "                
					  + " GROUP BY tblDctosOrdenesDetalle.idLocal,       "
					  + "         tblDctos.idCliente,                    "
					  + "         tblDctos.idPeriodo,                    "
					  + "         tblDctosOrdenesDetalle.idPlu           "
					  + " UNION                                          "
					  + " SELECT  tblDctosOrdenesDetalle.idLocal,        "
					  //+ "    tblDctos.idDcto,                            "
					  + "    tblDctosOrdenesDetalle.idPlu,               "
					  + "    MAX(tblDctos.idCliente) AS idCliente,       "
					  + "  MAX(tmpPRO.nombrePlu) AS nombrePlu ,          "
					  + "  SUM( tblDctosOrdenesDetalle.VRVENTAUNITARIO * "
					  + "  tblDctosOrdenesDetalle.cantidad ) AS vrVenta, "
					  + "    'Nota' AS grupo,                            "
					  + "    tblDctos.idPeriodo                          "
					  + " FROM            tblDctos                       "
					  + " INNER JOIN tblDctosOrdenesDetalle              "
					  + " ON tblDctos.IDLOCAL      =                     "
					  + "      tblDctosOrdenesDetalle.IDLOCAL            "
					  + " AND tblDctos.IDTIPOORDEN =                     "
					  + "    tblDctosOrdenesDetalle.IDTIPOORDEN          "
					  + " AND tblDctos.IDORDEN     =                     "
					  + "      tblDctosOrdenesDetalle.IDORDEN            "
					  + " INNER JOIN (                                   "
					  + " SELECT tblPlus.idLocal,                        "
					  + "   tblPlus.idPlu,                               "
					  + "   tblCategorias.nombreCategoria + ' ' +        "
					  + "   tblPlus.nombrePlu AS nombrePlu,              "
					  + "   tblCategorias.idProducto                     "
					  + " FROM            tblCategorias                  "
					  + " INNER JOIN tblPlus                             "
					  + " ON tblCategorias.idLocal      =                "
					  + "                 tblPlus.idLocal                "
					  + " AND tblCategorias.idLinea     =                "
					  + "                 tblPlus.idLinea                "
					  + " AND tblCategorias.IdCategoria =                "
					  + "             tblPlus.idCategoria ) AS tmpPRO	   "
					  + " ON tmpPRO.idLocal         =                    "
					  + "        tblDctosOrdenesDetalle.idLocal          "
					  + " AND tmpPRO.idPlu          =                    "
					  + "         tblDctosOrdenesDetalle.idPlu           "
					  + " WHERE tblDctos.IDTIPOORDEN =  29               "
					  + " AND   tmpPRO.idProducto    =                   "
					  + " ?1                              "                     
					  + " GROUP BY tblDctosOrdenesDetalle.idLocal,       "
					  + "         tblDctos.idCliente,                    "
					  + "         tblDctos.idPeriodo,                    "
					  + "         tblDctosOrdenesDetalle.idPlu           "
					  + " UNION                                          "
					  + " SELECT  tblPagosMedios.idLocal,                "
					  //+ "   tblPagos.idDcto,                             "
					  + "   tblPagosMedios.idPlu,                        "
					  + "   MAX(tblPagos.nitCC) AS idCliente,            "
					  + "   MAX(tmpPRO.nombrePlu) AS nombrePlu ,         "
					  + "   SUM( tblPagosMedios.vrMedio*(-1))            "
					  + "                           AS vrVenta,          "
					  + "  'Pago' AS grupo,                              "
					  + "  tblPagos.idPeriodo                            "
					  + " FROM    tblPagos                               "
					  + " INNER JOIN tblPagosMedios                      "
					  + " ON tblPagos.idLocal      =                     "
					  + "         tblPagosMedios.idLocal                 "
					  + " AND tblPagos.idTipoOrden =                     "
					  + "     tblPagosMedios.idTipoOrden                 "
					  + " AND tblPagos.idRecibo    =                     "
					  + "        tblPagosMedios.idRecibo                 "
					  + " AND tblPagos.indicador   =                     "
					  + "       tblPagosMedios.indicador                 "
					  + " INNER JOIN (                                   "
					  + " SELECT tblPlus.idLocal,                        "
					  + "   tblPlus.idPlu,                               "
					  + "   tblCategorias.nombreCategoria + ' ' +        "
					  + "   tblPlus.nombrePlu AS nombrePlu,              "
					  + "   tblCategorias.idProducto                     "
					  + " FROM            tblCategorias                  "
					  + " INNER JOIN tblPlus                             "
					  + " ON tblCategorias.idLocal      =                "
					  + "                 tblPlus.idLocal                "
					  + " AND tblCategorias.idLinea     =                "
					  + "                 tblPlus.idLinea                "
					  + " AND tblCategorias.IdCategoria =                "
					  + "          tblPlus.idCategoria ) AS tmpPRO	   "
					  + " ON tmpPRO.idLocal         =                    "
					  + "        tblPagosMedios.idLocal                  "
					  + " AND tmpPRO.idPlu          =                    "
					  + "         tblPagosMedios.idPlu                   "
					  + " WHERE tblPagosMedios.idTipoOrden IN (9,29)     "
					  + " AND   tmpPRO.idProducto    =                   " 
					  + " ?1                              "                     
					  + " GROUP BY tblPagosMedios.idLocal,               "
					  + "       tblPagos.nitCC,                          "
					  + "       tblPagos.idPeriodo,                      "
					  + "       tblPagosMedios.idPlu) AS tmpDET          "
					  + " ON  tblTerceros.idLocal   = tmpDET.idLocal     "
					  + " AND tblTerceros.idCliente = tmpDET.idCliente   "
					  + " INNER JOIN tblTerceroEstracto                  "
					  + " ON  tblTerceros.idLocal    =                   "
					  + "                 tblTerceroEstracto.idLocal     "
					  + " AND tblTerceros.idEstracto =                   "
					  + "              tblTerceroEstracto.idEstracto     "
					  + " WHERE tblTerceros.idLocal       =              "
					  + " ?2                                 "                     
					  + " AND   tmpDET.idPeriodo          =              "
					  + " ?3                               "                
					  + " AND   tblTerceros.idTipoTercero = 1            "
					  + " AND   tmpDET.vrVenta           != 0            "
					  + "  AND   tblTerceros.idCliente     = ?4      "          
					  + " ORDER BY tmpDET.idLocal,                       "
					  + " 	   tmpDET.idCliente,                       "
					  + " 	   tblTerceros.nombreTercero,              "
					  + " 	   tmpDET.grupo,                           "
					  //+ " 	   tmpDET.idDcto,                          "
					  + " 	   tmpDET.IDPLU,                           "
					  + " 	   tmpDET.nombrePlu,                       "
					  + " 	   tmpDET.vrVenta  ", nativeQuery = true)
			  List<TblDctosOrdenesDetalleDTO> listaProductoPeriodoIdCliente(int idProducto, int idLocal, int idPeriodo, String idCliente);
			  
			  
			  
			  @Query(value = " SELECT tmpDET.idLocal,                           "
					  + " 	   tblTerceroEstracto.nombreEstracto,      "
					  + " 	   tmpDET.idCliente,                       "
					  + " 	   tblTerceros.nombreTercero,              "
					  + " 	   tmpDET.grupo AS nombreDcto,             "
					  //+ " 	   tmpDET.idDcto,                          "
					  + " 	   tmpDET.idPlu,                           "
					  + " 	   tmpDET.nombrePlu,                       "
					  + " 	   tmpDET.vrVenta AS vrVentaConIva         "
					  + " FROM  tblTerceros                              "
					  + " INNER JOIN                                     "
					  + " (SELECT  tblDctosOrdenesDetalle.idLocal,       "
					  //+ "  tblDctos.idDcto,                              "
					  + "  tblDctosOrdenesDetalle.idPlu,                 "
					  + "  MAX(tblDctos.idCliente) AS idCliente,         "
					  + "  MAX(tmpPRO.nombrePlu) AS nombrePlu ,          "
					  + "  SUM( tblDctosOrdenesDetalle.VRVENTAUNITARIO * "
					  + "  tblDctosOrdenesDetalle.cantidad ) AS vrVenta, "
					  + " 	 'Factura' AS grupo,                       "
					  + " 	 tblDctos.idPeriodo                        "
					  + " FROM            tblDctos                       "
					  + " INNER JOIN tblDctosOrdenesDetalle              "
					  + " ON tblDctos.IDLOCAL      =                     "
					  + "      tblDctosOrdenesDetalle.IDLOCAL            "
					  + " AND tblDctos.IDTIPOORDEN =                     "
					  + "    tblDctosOrdenesDetalle.IDTIPOORDEN          "
					  + " AND tblDctos.IDORDEN     =                     "
					  + "      tblDctosOrdenesDetalle.IDORDEN            "
					  + " INNER JOIN (                                   "
					  + " SELECT tblPlus.idLocal,                        "
					  + "   tblPlus.idPlu,                               "
					  + "   tblCategorias.nombreCategoria + ' ' +        "
					  + "   tblPlus.nombrePlu AS nombrePlu,              "
					  + "   tblCategorias.idProducto                     "
					  + " FROM            tblCategorias                  "
					  + " INNER JOIN tblPlus                             "
					  + " ON tblCategorias.idLocal      =                "
					  + "                 tblPlus.idLocal                "
					  + " AND tblCategorias.idLinea     =                "
					  + "                 tblPlus.idLinea                "
					  + " AND tblCategorias.IdCategoria =                "
					  + "          tblPlus.idCategoria ) AS tmpPRO	   "
					  + " ON tmpPRO.idLocal         =                    "
					  + "        tblDctosOrdenesDetalle.idLocal          "
					  + " AND tmpPRO.idPlu          =                    "
					  + "         tblDctosOrdenesDetalle.idPlu           "
					  + " WHERE tblDctos.IDTIPOORDEN =  9                "
					  + " AND   tmpPRO.idProducto    =                   "
					  + "?1                              "                
					  + " GROUP BY tblDctosOrdenesDetalle.idLocal,       "
					  + "         tblDctos.idCliente,                    "
					  + "         tblDctos.idPeriodo,                    "
					  + "         tblDctosOrdenesDetalle.idPlu           "
					  + " UNION                                          "
					  + " SELECT  tblDctosOrdenesDetalle.idLocal,        "
					  //+ "    tblDctos.idDcto,                            "
					  + "    tblDctosOrdenesDetalle.idPlu,               "
					  + "    MAX(tblDctos.idCliente) AS idCliente,       "
					  + "  MAX(tmpPRO.nombrePlu) AS nombrePlu ,          "
					  + "  SUM( tblDctosOrdenesDetalle.VRVENTAUNITARIO * "
					  + "  tblDctosOrdenesDetalle.cantidad ) AS vrVenta, "
					  + "    'Nota' AS grupo,                            "
					  + "    tblDctos.idPeriodo                          "
					  + " FROM            tblDctos                       "
					  + " INNER JOIN tblDctosOrdenesDetalle              "
					  + " ON tblDctos.IDLOCAL      =                     "
					  + "      tblDctosOrdenesDetalle.IDLOCAL            "
					  + " AND tblDctos.IDTIPOORDEN =                     "
					  + "    tblDctosOrdenesDetalle.IDTIPOORDEN          "
					  + " AND tblDctos.IDORDEN     =                     "
					  + "      tblDctosOrdenesDetalle.IDORDEN            "
					  + " INNER JOIN (                                   "
					  + " SELECT tblPlus.idLocal,                        "
					  + "   tblPlus.idPlu,                               "
					  + "   tblCategorias.nombreCategoria + ' ' +        "
					  + "   tblPlus.nombrePlu AS nombrePlu,              "
					  + "   tblCategorias.idProducto                     "
					  + " FROM            tblCategorias                  "
					  + " INNER JOIN tblPlus                             "
					  + " ON tblCategorias.idLocal      =                "
					  + "                 tblPlus.idLocal                "
					  + " AND tblCategorias.idLinea     =                "
					  + "                 tblPlus.idLinea                "
					  + " AND tblCategorias.IdCategoria =                "
					  + "             tblPlus.idCategoria ) AS tmpPRO	   "
					  + " ON tmpPRO.idLocal         =                    "
					  + "        tblDctosOrdenesDetalle.idLocal          "
					  + " AND tmpPRO.idPlu          =                    "
					  + "         tblDctosOrdenesDetalle.idPlu           "
					  + " WHERE tblDctos.IDTIPOORDEN =  29               "
					  + " AND   tmpPRO.idProducto    =                   "
					  + " ?1                               "                     
					  + " GROUP BY tblDctosOrdenesDetalle.idLocal,       "
					  + "         tblDctos.idCliente,                    "
					  + "         tblDctos.idPeriodo,                    "
					  + "         tblDctosOrdenesDetalle.idPlu           "
					  + " UNION                                          "
					  + " SELECT  tblPagosMedios.idLocal,                "
					  //+ "   tblPagos.idDcto,                             "
					  + "   tblPagosMedios.idPlu,                        "
					  + "   MAX(tblPagos.nitCC) AS idCliente,            "
					  + "   MAX(tmpPRO.nombrePlu) AS nombrePlu ,         "
					  + "   SUM( tblPagosMedios.vrMedio*(-1))            "
					  + "                           AS vrVenta,          "
					  + "  'Pago' AS grupo,                              "
					  + "  tblPagos.idPeriodo                            "
					  + " FROM    tblPagos                               "
					  + " INNER JOIN tblPagosMedios                      "
					  + " ON tblPagos.idLocal      =                     "
					  + "         tblPagosMedios.idLocal                 "
					  + " AND tblPagos.idTipoOrden =                     "
					  + "     tblPagosMedios.idTipoOrden                 "
					  + " AND tblPagos.idRecibo    =                     "
					  + "        tblPagosMedios.idRecibo                 "
					  + " AND tblPagos.indicador   =                     "
					  + "       tblPagosMedios.indicador                 "
					  + " INNER JOIN (                                   "
					  + " SELECT tblPlus.idLocal,                        "
					  + "   tblPlus.idPlu,                               "
					  + "   tblCategorias.nombreCategoria + ' ' +        "
					  + "   tblPlus.nombrePlu AS nombrePlu,              "
					  + "   tblCategorias.idProducto                     "
					  + " FROM            tblCategorias                  "
					  + " INNER JOIN tblPlus                             "
					  + " ON tblCategorias.idLocal      =                "
					  + "                 tblPlus.idLocal                "
					  + " AND tblCategorias.idLinea     =                "
					  + "                 tblPlus.idLinea                "
					  + " AND tblCategorias.IdCategoria =                "
					  + "          tblPlus.idCategoria ) AS tmpPRO	   "
					  + " ON tmpPRO.idLocal         =                    "
					  + "        tblPagosMedios.idLocal                  "
					  + " AND tmpPRO.idPlu          =                    "
					  + "         tblPagosMedios.idPlu                   "
					  + " WHERE tblPagosMedios.idTipoOrden IN (9,29)     "
					  + " AND   tmpPRO.idProducto    =                   " 
					  +"?1                              "                     
					  + " GROUP BY tblPagosMedios.idLocal,               "
					  + "       tblPagos.nitCC,                          "
					  + "       tblPagos.idPeriodo,                      "
					  + "       tblPagosMedios.idPlu) AS tmpDET          "
					  + " ON  tblTerceros.idLocal   = tmpDET.idLocal     "
					  + " AND tblTerceros.idCliente = tmpDET.idCliente   "
					  + " INNER JOIN tblTerceroEstracto                  "
					  + " ON  tblTerceros.idLocal    =                   "
					  + "                 tblTerceroEstracto.idLocal     "
					  + " AND tblTerceros.idEstracto =                   "
					  + "              tblTerceroEstracto.idEstracto     "
					  + " WHERE tblTerceros.idLocal       =              "
					  + "?2                                 "                     
					  + " AND   tmpDET.idPeriodo          =              "
					  + "?3                               "                
					  + " AND   tblTerceros.idTipoTercero = 1            "
					  + " AND   tmpDET.vrVenta           != 0            "
					  + " ORDER BY tmpDET.idLocal,                       "
					  + " 	   tmpDET.idCliente,                       "
					  + " 	   tblTerceros.nombreTercero,              "
					  + " 	   tmpDET.grupo,                           "
					  //+ " 	   tmpDET.idDcto,                          "
					  + " 	   tmpDET.IDPLU,                           "
					  + " 	   tmpDET.nombrePlu,                       "
					  + " 	   tmpDET.vrVenta  ", nativeQuery = true)
			  List<TblDctosOrdenesDetalleDTO> listaProductoPeriodo(int idProducto, int idLocal, int idPeriodo);
			
			
	  
}
