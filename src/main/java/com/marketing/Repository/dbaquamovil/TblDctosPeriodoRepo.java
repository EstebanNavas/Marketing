package com.marketing.Repository.dbaquamovil;

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
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
