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
			
			
	  
}
