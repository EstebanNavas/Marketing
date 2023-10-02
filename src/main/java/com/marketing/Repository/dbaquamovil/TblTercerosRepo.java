package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblMailMarketingReporteDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TblTercerosRepo extends  JpaRepository<TblTerceros, Integer> {

		
		
	  @Query("SELECT DISTINCT t FROM TblTerceros t " +
	            "JOIN FETCH t.terceroEstracto te " +
	            "JOIN FETCH t.terceroRuta tr " +
	            "WHERE t.idLocal = ?1 " +
	            "AND t.idLocal = te.idLocal "+
	            "AND  t.terceroEstracto.idEstracto = te.idEstracto " +
	            
	            "AND t.idLocal = tr.idLocal "+
	            "AND  t.terceroRuta.idRuta = tr.idRuta " +
	            
	            "AND t.idTipoTercero = 1 " +
	            "AND ISNUMERIC(t.telefonoCelular) = 1 " +
	            "AND LEN(t.telefonoCelular) = 10 " +
	            "ORDER BY t.nombreTercero")
	     List<TblTercerosProjectionDTO> findByIdLocal(int idLocal);
    
    
	  @Query("SELECT t.telefonoCelular FROM TblTerceros t WHERE t.idTercero IN :ids AND t.idLocal = :idLocal")
	  List<String> findTelefonoCelularByIdsAndIdLocal(@Param("ids") List<String> ids, @Param("idLocal") int idLocal);
	  
	  
	  @Query(
				value = "SELECT tblMailCampaignCliente.idCliente, tblTerceros.nombreTercero " +
						"FROM bdaquamovil.dbo.tblTerceros " +
						"JOIN BDMailMarketing.dbo.tblMailCampaignCliente " +
						"ON tblMailCampaignCliente.idLocal =  tblTerceros.idLocal  " +
						"AND tblMailCampaignCliente.idCliente =  tblTerceros.idCliente COLLATE Modern_Spanish_CI_AS " +
						"WHERE tblMailCampaignCliente.IDLOCAL = ?1 ",
				nativeQuery = true
				)

		List <TblTercerosProjectionDTO> obtenerNombreTerceros(int idLocal);
	  
	  @Query(
				value = "SELECT * " +
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idtipotercero = 3 ",
				nativeQuery = true
				)

		List <TblTercerosProjectionDTO> obtenerNombreTercerosEmpleados(int idLocal);
	
	  
	  @Query(
				value = "SELECT * " +
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idtipotercero = 1 ",
				nativeQuery = true
				)

		List <TblTercerosProjectionDTO> obtenerNombreTercerosClientes(int idLocal);
	  
	  @Query(
				value = "SELECT * " +
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idtipotercero = 1 " +
						"AND idCliente = ?2",
				nativeQuery = true
				)

		List <TblTercerosProjectionDTO> obtenerDatosTercerosClientes(int idLocal, int idCliente);
	  
	  
	  @Query(
				value = "SELECT * " +
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idtipotercero = 1 " +
						"AND idCliente IN ?2",
				nativeQuery = true
				)

		List <TblTercerosProjectionDTO> obtenerDatosTercerosListaClientes(int idLocal, List<String> idClientes);
	  
		@Query(value = "SELECT tblTerceros.idCliente " + 
				"FROM bdaquamovil.dbo.tblTerceros " +
				"WHERE tblTerceros.idLocal = ?1 " +
				"AND tblTerceros.idCliente = ?2 " +
				"AND tblTerceros.idTipoTercero = 1",
				nativeQuery = true)
		String ObtenerIdCliente(int idLocal, String idCliente);
		
		@Query(value = "SELECT tblTerceros.nombreTercero " + 
				"FROM bdaquamovil.dbo.tblTerceros " +
				"WHERE tblTerceros.idLocal = ?1 " +
				"AND tblTerceros.idCliente = ?2 " +
				"AND tblTerceros.idTipoTercero = 1",
				nativeQuery = true)
		String ObtenerNombreTercero(int idLocal, String idCliente);
		
		@Query(value = "SELECT tblTerceros.telefonoCelular " + 
				"FROM bdaquamovil.dbo.tblTerceros " +
				"WHERE tblTerceros.idLocal = ?1 " +
				"AND tblTerceros.idCliente = ?2 " +
				"AND tblTerceros.idTipoTercero = 1",
				nativeQuery = true)
		String ObtenerTelefonoCelular(int idLocal, String idCliente);
		
		@Query(value = "SELECT tblTerceros.direccionTercero " + 
				"FROM bdaquamovil.dbo.tblTerceros " +
				"WHERE tblTerceros.idLocal = ?1 " +
				"AND tblTerceros.idCliente = ?2 " +
				"AND tblTerceros.idTipoTercero = 1",
				nativeQuery = true)
		String ObtenerDireccionTercero(int idLocal, String idCliente);
		
		
		
		
		
		
		
}
