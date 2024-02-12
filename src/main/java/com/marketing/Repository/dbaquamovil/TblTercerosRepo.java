package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblMailMarketingReporteDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Projection.TercerosDTO;

import java.security.Timestamp;
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
		
		
		
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, te.idEstracto, t.direccionTercero, tr.nombreRuta, te.nombreEstracto, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTerceroEstracto] te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
				"JOIN [bdaquamovil].[dbo].[tblTercerosRuta] tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 1 " +
				"AND ISNUMERIC(t.telefonoCelular) = 1 " +
				"AND tcn.idTipoTabla = 3 " +
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> ListaTercerosSuscriptor(int idLocal);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 3 " +
				"AND tcn.idTipoTabla = 3 " +
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> ListaTercerosEmpleados(int idLocal);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 2 " +
				"AND tcn.idTipoTabla = 3 " +
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> ListaTercerosProveedor(int idLocal);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, te.idEstracto, t.direccionTercero, tr.nombreRuta, te.nombreEstracto, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTerceroEstracto] te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
				"JOIN [bdaquamovil].[dbo].[tblTercerosRuta] tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 1 " +
				"AND ISNUMERIC(t.telefonoCelular) = 1 " +
				"AND LEN(t.telefonoCelular) = 10 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND (t.nombreTercero LIKE %?2% OR CAST(t.idTercero AS VARCHAR(20)) LIKE %?2%)" + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosSuscriptor(int idLocal, String palabraClave);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 3 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND (t.nombreTercero LIKE %?2% OR CAST(t.idTercero AS VARCHAR(20)) LIKE %?2%)" + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosEmpleados(int idLocal, String palabraClave);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, te.idEstracto, t.direccionTercero, tr.nombreRuta, te.nombreEstracto, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTerceroEstracto] te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
				"JOIN [bdaquamovil].[dbo].[tblTercerosRuta] tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 2 " +
				"AND ISNUMERIC(t.telefonoCelular) = 1 " +
				"AND LEN(t.telefonoCelular) = 10 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND (t.nombreTercero LIKE %?2% OR CAST(t.idTercero AS VARCHAR(20)) LIKE %?2%)" + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosProveedor(int idLocal, String palabraClave);
		
		
		@Query(value = "SELECT MAX(CAST(IdCliente AS decimal(20,0))) " + 
				"FROM bdaquamovil.dbo.tblTerceros " +
				"WHERE tblTerceros.idLocal = ?1 " +
				"AND idTipoTercero = ?2 ",
				nativeQuery = true)
		Integer MaximoIdTercero(int idLocal, int idTipoTercero);
		
		
		
		@Query(value = "SELECT * " + 
				"FROM bdaquamovil.dbo.tblTerceros " +
				"WHERE tblTerceros.idLocal = ?1 " +
				"AND tblTerceros.idCliente = ?2 " +
				"AND idTipoTercero = ?3 ",
				nativeQuery = true)
		List<TblTerceros> ObtenerInformacionTercero(int idLocal, String idCliente, int idTipoTercero);
		
		
		// Actualizamos Suscriptor
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblTerceros SET nombreTercero = ?1, direccionTercero = ?2, direccionCobro = ?3, idDptoCiudad = ?4, telefonoFijo = ?5, telefonoCelular = ?6, " +
				  		"email = ?7, idRuta = ?8, idEstracto = ?9, CC_Nit = ?10, numeroMedidor = ?11, idMedidor = ?12, idMacro = ?13, codigoCatastral = ?14, fechaIngreso = ?15, " +
				  		"fechaInstalacionMedidor = ?16, codigoAlterno = ?17, tipoSuscriptor = ?18, matricula = ?19, estado = ?20, estadoCorte = ?21 "+
		                 "WHERE tblTerceros.idLocal = ?22 " +
		                 "AND tblTerceros.idCliente = ?23 " +
		                 "AND tblTerceros.idTipoTercero = ?24 ", nativeQuery = true)
		  public void actualizarTercero(String nombreTercero,  String direccionTercero, String direccionCobro, int idDptoCiudad, String telefonoFijo,
					String telefonoCelular, String email, int idRuta, int idEstracto, String CC_Nit, String numeroMedidor, int idMedidor, int idMacro, 
					String codigoCatastral, java.sql.Timestamp fechaIngreso, java.sql.Timestamp fechaDeInstalacion, String codigoAlterno, int tipoSuscriptor, String matricula, int estadoSuscriptorInt, int estadoCorteInt,  int idLocal, String idCliente, int idTipoTercero) ;
		
		
		// Actualizamos Suscriptor
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblTerceros SET nombreTercero = ?1, direccionTercero = ?2, direccionCobro = ?3, idDptoCiudad = ?4, telefonoFijo = ?5, telefonoCelular = ?6, " +
				  		"email = ?7, idRuta = ?8, idEstracto = ?9, CC_Nit = ?10, numeroMedidor = ?11, idMedidor = ?12, idMacro = ?13, codigoCatastral = ?14, fechaIngreso = ?15, " +
				  		"fechaInstalacionMedidor = ?16, codigoAlterno = ?17, tipoSuscriptor = ?18, matricula = ?19, digitoVerificacion = ?20, idRegimen = ?21, telefonoFax =?22, contactoTercero = ?23 "+
		                 "WHERE tblTerceros.idLocal = ?24 " +
		                 "AND tblTerceros.idCliente = ?25 " +
		                 "AND tblTerceros.idTipoTercero = ?26 ", nativeQuery = true)
		  public void actualizarTerceroProveedor(String nombreTercero,  String direccionTercero, String direccionCobro, int idDptoCiudad, String telefonoFijo,
					String telefonoCelular, String email, int idRuta, int idEstracto, String CC_Nit, String numeroMedidor, int idMedidor, int idMacro, 
					String codigoCatastral, java.sql.Timestamp fechaIngreso, java.sql.Timestamp fechaDeInstalacion, String codigoAlterno, int tipoSuscriptor, String matricula, int digitoVerificacion, String idRegimen, String telefonoFax, String contactoTercero,  int idLocal, String idCliente, int idTipoTercero) ;
		  
		  
		  
		  
			@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, te.idEstracto, t.direccionTercero, tr.nombreRuta, te.nombreEstracto, tcn.nombreCausa, t.telefonoCelular, t.ordenRuta, t.CC_Nit " + 
					"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
					"JOIN [bdaquamovil].[dbo].[tblTerceroEstracto] te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
					"JOIN [bdaquamovil].[dbo].[tblTercerosRuta] tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
					"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
					"WHERE t.idLocal = ?1 " +
					"AND t.idTipoTercero = 1 " +
					"AND tr.idRuta = ?2 " + 
					"AND ISNUMERIC(t.telefonoCelular) = 1 " +
					"AND tcn.idTipoTabla = 3 " +
					"ORDER BY t.nombreTercero ",
					nativeQuery = true)
			List<TercerosDTO> ListaTercerosRutas(int idLocal, int idRuta);
		
		
}
