package com.marketing.Repository.dbaquamovil;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Projection.TblDctosPeriodoDTO;

@Repository
public interface TblDctosPeriodoRepo extends JpaRepository<TblDctosPeriodo, Integer>{

	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblDctosPeriodo " +
			"WHERE tblDctosPeriodo.idLocal = ?1 " +
			"AND tblDctosPeriodo.estadoFEDctos = 0 " +
			"ORDER BY idPeriodo DESC ",
			nativeQuery = true)
	List<TblDctosPeriodo> ObtenerIdPeriodo(int idLocal);
	
	// Modificamos el IDTIPOORDEN de 67 a 17
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblDctosPeriodo SET estadoFEDctos = 2 " +
	                 "WHERE tblDctosPeriodo.idLocal = ?1 " +
	                 "AND tblDctosPeriodo.idPeriodo = ?2 " +
	                 "AND tblDctosPeriodo.estadoFEDctos = 0", nativeQuery = true)
	  public void actualizarIdPeriodo(int idLocal, int idPeriodo);
	  
	  
		@Query(value = "SELECT * " + 
				"FROM bdaquamovil.dbo.tblDctosPeriodo " +
				"WHERE tblDctosPeriodo.idLocal = ?1 " +
				"AND tblDctosPeriodo.estadoFENotas = 0 " +
				"ORDER BY idPeriodo DESC ",
				nativeQuery = true)
		List<TblDctosPeriodo> ObtenerIdPeriodoNotas(int idLocal);
		
		@Query(value = "SELECT TOP (12) tblDctosPeriodo.idPeriodo " + 
				"FROM bdaquamovil.dbo.tblDctosPeriodo " +
				"WHERE tblDctosPeriodo.idLocal = ?1 " +
				"ORDER BY idPeriodo DESC ",
				nativeQuery = true)
		List <Integer> ListaIdPeriodos(int idLocal);
		
		@Query(value = "SELECT * " + 
				"FROM bdaquamovil.dbo.tblDctosPeriodo " +
				"WHERE tblDctosPeriodo.idLocal = ?1 " +
				"ORDER BY idPeriodo DESC ",
				nativeQuery = true)
		List <TblDctosPeriodo> ListaTotalPeriodos(int idLocal);
		
		@Query(value = "SELECT  TOP (?2) tblDctosPeriodo.idPeriodo " + 
				"FROM bdaquamovil.dbo.tblDctosPeriodo " +
				"WHERE tblDctosPeriodo.idLocal = ?1 " +
				"AND tblDctosPeriodo.idPeriodo < ?3 " +
				"ORDER BY tblDctosPeriodo.idPeriodo DESC ",
				nativeQuery = true)
		List <String> listaPeriodo(int idLocal, int PeriodoFactura , int idPeriodo);
		
		
		@Modifying
		@Transactional
		@Query(value = "DROP TABLE [BDAquamovil].[dbo].[tmp_historicoConsumo]",
				nativeQuery = true)
		public void eliminaTablaHistoricoConsumo();
		
		
		
		@Query(value = "SELECT * " + 
				"FROM bdaquamovil.dbo.tblDctosPeriodo " +
				"WHERE tblDctosPeriodo.idLocal = ?1 " +
				"AND tblDctosPeriodo.idPeriodo = ?2 ",
				nativeQuery = true)
		List <TblDctosPeriodo> ObtenerPeriodo(int idLocal, int idPeriodo);
		
		
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tbldctosperiodo " +
		                 "SET estadoPeriodo  = 2, " +
		                 "estadoEmail      = 2, " +
		                 "estadoLecturaApp = 2, " +
		                 "estado           = 2 " +
		                 "WHERE  tbldctosperiodo.idLocal = ?1 ", nativeQuery = true)
		  public void desactivaAll(int idLocal);
		
		
		
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tbldctosperiodo " +
		                 "SET tbldctosperiodo.estadoPeriodo = 1 " +
		                 "WHERE tbldctosperiodo.idLocal = ?1 " +
		                 "AND tbldctosperiodo.idPeriodo = ?2 ", nativeQuery = true)
		  public void activaUn(int idLocal, int idPeriodo);
		
		  
		  
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tbldctosperiodo SET nombrePeriodo = ?1, fechaInicial = ?2, fechaFinal = ?3, fechaSinRecargo = ?4, fechaConRecargo = ?5,  " +
		                 "estadoEmail = ?6, estadoLecturaApp = ?7, textoPeriodo = ?8 " +
		                 "WHERE tbldctosperiodo.idLocal = ?9 " +
		                 "AND tbldctosperiodo.idPeriodo = ?10 ", nativeQuery = true)
		  public void actualizarPeriodo(String nombrePeriodo, Timestamp fechaInicial, Timestamp fechaFinal, Timestamp fechaSinRecargo, Timestamp fechaConRecargo,
				  						int estadoEmail, int estadoLecturaApp, String TextoPeriodo, int idLocal, int idPeriodo);
		
		
		
		
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tbldctosperiodo " +
		                 "SET tbldctosperiodo.estadoFacturado = ?3 " +
		                 "WHERE tbldctosperiodo.idLocal    =  ?1 " +
		                 "AND   tbldctosperiodo.idPeriodo  = ?2 ", nativeQuery = true)
		  public void modificaEstadoFacturado(int idLocal, int idPeriodo, int xEstadoFacturado);
		
		  
			@Query(value = "SELECT * " + 
					"FROM bdaquamovil.dbo.tblDctosPeriodo " +
					"WHERE tblDctosPeriodo.idLocal = ?1 " +
					"AND tblDctosPeriodo.estadoPeriodo = 1 ",
					nativeQuery = true)
			List <TblDctosPeriodo> ObtenerPeriodoActivo(int idLocal);
			
			
			@Query(value = "SELECT *                          "
	                + "FROM bdaquamovil.dbo.tblDctosPeriodo                   "
	                + "WHERE tbldctosperiodo.idPeriodo =      "
	                + "?1                       "
	                + "AND tbldctosperiodo.idLocal         =  "
	                + "?2 ",
					nativeQuery = true)
			List <TblDctosPeriodo> listaUnFCH(int idPeriodo,  int idLocal);
			
			
			
			@Query(value = " SELECT tmpPER.idLocal,                     "
	                + "        tmpPER.idPeriodo,                 "
	                + "        tmpPER.nombrePeriodo,             "
	                + "        tmpPER.fechaInicial,              "
	                + "        tmpPER.fechaFinal,                "
	                + "        tmpPER.fechaSinRecargo,           "
	                + "        tmpPER.fechaConRecargo,           "
	                + "        tmpPER.estadoPeriodo,             "
	                + "        tmpPER.estado,                    "
	                + "        tmpPER.cuentaLectura,             "
	                + "        tmpPER.cuentaFactura,             "
	                + "      tmpPER.estadoLecturaApp             "
	                + " FROM (                                   "
	                + " SELECT tbldctosperiodo.idLocal           "
	                + "  ,tbldctosperiodo.idPeriodo              "
	                + "  ,MAX(tbldctosperiodo.nombrePeriodo)     "
	                + "                     AS nombrePeriodo     "
	                + "  ,MAX(tbldctosperiodo.fechaInicial)      "
	                + "                      AS fechaInicial     "
	                + "  ,MAX(tbldctosperiodo.fechaFinal)        "
	                + "                        AS fechaFinal     "
	                + "  ,MAX(tbldctosperiodo.fechaSinRecargo)   "
	                + "                     AS fechaSinRecargo   "
	                + "  ,MAX(tbldctosperiodo.fechaConRecargo)   "
	                + "                     AS fechaConRecargo   "
	                + "  ,MAX(tbldctosperiodo.estadoPeriodo)     "
	                + "                       AS estadoPeriodo   "
	                + "  ,MAX(tbldctosperiodo.estado)            "
	                + "                             AS estado,   "
	                + "  MAX(tbldctosperiodo.estadoLecturaApp)  "
	                + "                   AS estadoLecturaApp,   "
	                + " ISNULL(( SELECT  COUNT(*) AS cuenta      "
	                + " FROM    tbldctos                         "
	                + " INNER JOIN tbldctosOrdenes               "
	                + " ON tbldctos.IDLOCAL      =               "
	                + "       tbldctosOrdenes.IDLOCAL            "
	                + " AND tbldctos.IDTIPOORDEN =               "
	                + "   tbldctosOrdenes.IDTIPOORDEN            "
	                + " AND tbldctos.IDORDEN       =             "
	                + "       tbldctosOrdenes.IDORDEN            "
	                + " WHERE tbldctos.IDLOCAL     =             "
	                + "       tbldctosperiodo.IDLOCAL            "
	                + " AND   tbldctos.IDTIPOORDEN = 9           "
	                + " AND   tbldctos.idPeriodo   =             "
	                + "      tbldctosperiodo.idPeriodo           "
	                + " GROUP BY tbldctos.IDLOCAL,               "
	                + "          tbldctos.IDTIPOORDEN),0)        "
	                + "               AS cuentaFactura,          "
	                + " ISNULL(( SELECT  COUNT(*)                "
	                + "                      AS cuenta           "
	                + " FROM    tbldctosOrdenesDetalle           "
	                + " INNER JOIN tbldctosOrdenes               "
	                + " ON tbldctosOrdenesDetalle.IDLOCAL      = "
	                + "       tbldctosOrdenes.IDLOCAL            "
	                + " AND tbldctosOrdenesDetalle.IDTIPOORDEN = "
	                + "   tbldctosOrdenes.IDTIPOORDEN            "
	                + " AND tbldctosOrdenesDetalle.IDORDEN     = "
	                + "       tbldctosOrdenes.IDORDEN            "
	                + " WHERE tbldctosOrdenes.IDLOCAL          = "
	                + "                  tbldctosperiodo.IDLOCAL "
	                + " AND   tbldctosOrdenes.IDTIPOORDEN   = 59 "
	                + " AND   tbldctosOrdenes.idPeriodo        = "
	                + "                tbldctosperiodo.idPeriodo "
	                + " AND   tbldctosOrdenesDetalle.idTipo = 4  "
	                + " GROUP BY tbldctosOrdenes.IDLOCAL,        "
	                + "          tbldctosOrdenes.IDTIPOORDEN),0) "
	                + "                         AS cuentaLectura "
	                + " FROM tbldctosperiodo                     "
	                + " WHERE tbldctosperiodo.IDLOCAL          = "
	                + "?1                            "
	                + " AND   tbldctosperiodo.estadoPeriodo    = "
	                + "?2                      "
	                + " GROUP BY tbldctosperiodo.idLocal,        "
	                + "          tbldctosperiodo.idPeriodo)      "
	                + "                              AS tmpPER ",
					nativeQuery = true)
			List <TblDctosPeriodoDTO> listaEstadoFCH(int idLocal, int EstadoPeriodo);
			
			
			
			@Query(value = "SELECT TOP 1 tbldctosperiodo.idPeriodo      "
	                + "FROM tbldctosperiodo                "
	                + "WHERE tbldctosperiodo.idPeriodo <   "
	                + "( SELECT tbldctosperiodo.idPeriodo  "
	                + "  FROM tbldctosperiodo              "
	                + "  WHERE tbldctosperiodo.idPeriodo = "
	                + "?1                   "
	                + "  AND tbldctosperiodo.idLocal     = "
	                + "?2 )                    "
	                + "  AND tbldctosperiodo.idLocal     = "
	                + "?2                     "
	                + "ORDER BY tbldctosperiodo.idLocal,   "
	                + " tbldctosperiodo.idPeriodo DESC ",
					nativeQuery = true)
			Integer listaAnteriorFCH(int idPeriodo,  int idLocal);
			
			
			@Query(value = "SELECT TOP 1 tbldctosperiodo.idPeriodo      "
	                + "FROM tbldctosperiodo                "
	                + "INNER JOIN tblDctosOrdenes "
	                + "ON tbldctosperiodo.idLocal =   tblDctosOrdenes.idLocal "
	                + "AND tbldctosperiodo.idPeriodo =   tblDctosOrdenes.idPeriodo "
	                + "WHERE tbldctosperiodo.idPeriodo <   "
	                + "( SELECT tbldctosperiodo.idPeriodo  "
	                + "  FROM tbldctosperiodo              "
	                + "  WHERE tbldctosperiodo.idPeriodo = "
	                + "?1                   "
	                + "  AND tbldctosperiodo.idLocal     = "
	                + "?2 )                    "
	                + "  AND tbldctosperiodo.idLocal     = "
	                + "?2                     "
	                + " AND tblDctosOrdenes.IDTIPOORDEN = 9 "
	                + "ORDER BY tbldctosperiodo.idLocal,   "
	                + " tbldctosperiodo.idPeriodo DESC ",
					nativeQuery = true)
			Integer ObtenerPeriodoAnteriorFacturado(int idPeriodo,  int idLocal);
			

		
}
