package com.marketing.Repository.dbaquamovil;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblDctosPeriodo;

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
		
}
