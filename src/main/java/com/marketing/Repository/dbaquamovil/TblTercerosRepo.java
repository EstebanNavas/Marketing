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
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblMailMarketingReporteDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Projection.TercerosDTO2;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface TblTercerosRepo extends  JpaRepository<TblTerceros, Integer> {

		
		
	  @Query(value = "SELECT DISTINCT t.idLocal, t.idCliente, te.nombreEstracto, t.idEstracto, tr.nombreRuta, t.nombreTercero, t.direccionTercero, t.telefonoCelular  " +
	            "FROM bdaquamovil.dbo.TblTerceros t " +
	            "JOIN bdaquamovil.dbo.tblTerceroEstracto te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
	            "JOIN bdaquamovil.dbo.tblTercerosRuta tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
	            "WHERE t.idLocal = ?1 "+
	            "AND t.idTipoTercero = 1 " +
	            
	            "AND ISNUMERIC(t.telefonoCelular) = 1 "+
	            "AND LEN(t.telefonoCelular) = 10 " +
	            "ORDER BY t.nombreTercero ",
	            nativeQuery = true)
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
		
		@Query(value = "SELECT tblTerceros.email " + 
				"FROM bdaquamovil.dbo.tblTerceros " +
				"WHERE tblTerceros.idLocal = ?1 " +
				"AND tblTerceros.idCliente = ?2 " +
				"AND tblTerceros.idTipoTercero = 1",
				nativeQuery = true)
		String ObtenerEmailTercero(int idLocal, String idCliente);
		
		
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, te.idEstracto, t.direccionTercero, tr.nombreRuta, te.nombreEstracto, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTerceroEstracto] te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
				"JOIN [bdaquamovil].[dbo].[tblTercerosRuta] tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 1 " +
				"AND tcn.idTipoTabla = 3 " +
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> ListaTercerosSuscriptor(int idLocal);
		
		@Query(value = "SELECT DISTINCT t.idLocal, t.nombreTercero, t.idCliente, t.idTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular, t.telefonoFijo, t.CC_Nit " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 3 " +
				"AND tcn.idTipoTabla = 3 " +
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO2> ListaTercerosEmpleados(int idLocal);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idCliente ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular, t.CC_Nit " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 2 " +
				"AND tcn.idTipoTabla = 3 " +
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> ListaTercerosProveedor(int idLocal);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idCliente ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular, t.CC_Nit " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 4 " +
				"AND tcn.idTipoTabla = 3 " +
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> ListaTercerosClientes(int idLocal);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, te.idEstracto, t.direccionTercero, tr.nombreRuta, te.nombreEstracto, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTerceroEstracto] te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
				"JOIN [bdaquamovil].[dbo].[tblTercerosRuta] tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 1 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND (t.nombreTercero LIKE %?2%) " + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosSuscriptor(int idLocal, String palabraClave);
		
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, te.idEstracto, t.direccionTercero, tr.nombreRuta, te.nombreEstracto, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTerceroEstracto] te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
				"JOIN [bdaquamovil].[dbo].[tblTercerosRuta] tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 1 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND t.idCliente = ?2    " + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosSuscriptorNUID(int idLocal, String idCliente);
		
		
		
		
		
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 3 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND (t.nombreTercero LIKE %?2%) " + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosEmpleados(int idLocal, String palabraClave);
		
		
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero, t.idCliente ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular, t.CC_Nit " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 3 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND t.idCliente = ?2 " + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosEmpleadosNUID(int idLocal, String idCliente);
		
		
		
		@Query(value = "SELECT DISTINCT t.idLocal , t.idCliente, t.idTercero ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 2 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND (t.nombreTercero LIKE %?2%) " + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosProveedor(int idLocal, String palabraClave);
		
		@Query(value = "SELECT DISTINCT t.idLocal , t.idCliente, t.idTercero ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 4 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND (t.nombreTercero LIKE %?2%) " + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosCliente(int idLocal, String palabraClave);
		
		
		@Query(value = "SELECT DISTINCT t.idLocal , t.idCliente, t.idTercero ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 2 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND t.idCliente = ?2 " + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosProveedorNUID(int idLocal, String palabraClave);
		
		
		@Query(value = "SELECT DISTINCT t.idLocal , t.idCliente, t.idTercero ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 4 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND t.idCliente = ?2 " + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosClienteNUID(int idLocal, String palabraClave);
		
		
		@Query(value = "SELECT MAX(CAST(IdCliente AS decimal(20,0))) " + 
				"FROM bdaquamovil.dbo.tblTerceros " +
				"WHERE tblTerceros.idLocal = ?1 " +
				"AND idTipoTercero = ?2 ",
				nativeQuery = true)
		Long MaximoIdTercero(int idLocal, int idTipoTercero);
		
		
		
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
				  		"fechaInstalacionMedidor = ?16, codigoAlterno = ?17, tipoSuscriptor = ?18, matricula = ?19, estado = ?20, estadoCorte = ?21, promedio = ?22, estadoEmail = ?23, estadoWhatsApp = ?24 "+
		                 "WHERE tblTerceros.idLocal = ?25 " +
		                 "AND tblTerceros.idCliente = ?26 " +
		                 "AND tblTerceros.idTipoTercero = ?27 ", nativeQuery = true)
		  public void actualizarTercero(String nombreTercero,  String direccionTercero, String direccionCobro, int idDptoCiudad, String telefonoFijo,
					String telefonoCelular, String email, int idRuta, int idEstracto, String CC_Nit, String numeroMedidor, int idMedidor, int idMacro, 
					String codigoCatastral, java.sql.Timestamp fechaIngreso, java.sql.Timestamp fechaDeInstalacion, String codigoAlterno, int tipoSuscriptor, String matricula, int estadoSuscriptorInt, int estadoCorteInt, Double promedio, int estadoEmail, int estadoWhatsApp,  int idLocal, String idCliente, int idTipoTercero) ;
		
		
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
		  
		  
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblTerceros SET nombreTercero = ?1, direccionTercero = ?2, direccionCobro = ?3, idDptoCiudad = ?4, telefonoFijo = ?5, telefonoCelular = ?6, " +
				  		"email = ?7, idRuta = ?8, idEstracto = ?9, CC_Nit = ?10, numeroMedidor = ?11, idMedidor = ?12, idMacro = ?13, codigoCatastral = ?14, fechaIngreso = ?15, " +
				  		"fechaInstalacionMedidor = ?16, codigoAlterno = ?17, tipoSuscriptor = ?18, matricula = ?19, digitoVerificacion = ?20, idRegimen = ?21, telefonoFax =?22, contactoTercero = ?23 "+
		                 "WHERE tblTerceros.idLocal = ?24 " +
		                 "AND tblTerceros.idCliente = ?25 " +
		                 "AND tblTerceros.idTipoTercero = ?26 ", nativeQuery = true)
		  public void actualizarTerceroCliente(String nombreTercero,  String direccionTercero, String direccionCobro, int idDptoCiudad, String telefonoFijo,
					String telefonoCelular, String email, int idRuta, int idEstracto, String CC_Nit, String numeroMedidor, int idMedidor, int idMacro, 
					String codigoCatastral, java.sql.Timestamp fechaIngreso, java.sql.Timestamp fechaDeInstalacion, String codigoAlterno, int tipoSuscriptor, String matricula, int digitoVerificacion, String idRegimen, String telefonoFax, String contactoTercero,  int idLocal, String idCliente, int idTipoTercero) ;
		  
		  
		  
			@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, te.idEstracto, t.direccionTercero, tr.nombreRuta, te.nombreEstracto, tcn.nombreCausa, t.telefonoCelular, t.ordenRuta, t.CC_Nit, t.idCliente, t.idRuta " + 
					"FROM tblTerceros t " +
					"JOIN tblTerceroEstracto te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
					"JOIN tblTercerosRuta tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
					"JOIN tblTipoCausaNota tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
					"WHERE t.idLocal = ?1 " +
					"AND t.idTipoTercero = 1 " +
					"AND tr.idRuta = ?2 " + 
					"AND tcn.idTipoTabla = 3 " +
					"ORDER BY t.ordenRuta ",
					nativeQuery = true)
			List<TercerosDTO> ListaTercerosRutas(int idLocal, int idRuta);
			
			
			// Actualizamos Suscriptor
			  @Modifying
			  @Transactional
			  @Query(value = "UPDATE tblTerceros SET ordenRuta = ?1 " +
			                 "WHERE tblTerceros.idCliente = ?2 " +
			                 "AND tblTerceros.idLocal = ?3 " +
			                 "AND tblTerceros.idTipoTercero = 1 ", nativeQuery = true)
			  public void actualizarOrdenRuta(int ordenRuta,  int idCliente,  int idLocal);
			  
				// Actualizamos Suscriptor
			  @Modifying
			  @Transactional
			  @Query(value = "UPDATE tblTerceros SET ordenRuta = tmpORD.ordenRutaNuevo " +
					  		 "FROM bdaquamovil.dbo.tblTerceros " + 
					  		 "JOIN (   SELECT idLocal ,idCliente ,row_number()  OVER " + 
					  		 "(ORDER BY ordenRuta) * 10  AS ordenRutaNuevo " +
					  		 "FROM bdaquamovil.dbo.tblTerceros " +
			                 "WHERE tblTerceros.idLocal = ?1 " +
			                 "AND tblTerceros.idTipoTercero = 1 " +
			                 "AND tblTerceros.idRuta = ?2 ) AS tmpORD " +
			                 "ON tblterceros.idLocal  = tmpORD.idLocal " +
			                 "AND tblterceros.idCliente = tmpORD.idCliente " +
			                 "WHERE  tblterceros.idLocal = ?1 " +
			                 "AND tblterceros.idTipoTercero = 1 " +
			                 "AND  tblterceros.idRuta = ?2 " 
			                 , nativeQuery = true)
			  public void ReordenarRuta(int idLocal, int idRuta );
			  
			  
				// ActualizaPromedio
			  @Modifying
			  @Transactional
			  @Query(value = "UPDATE tblTerceros SET promedio = tmpPRO.promedio " +
					  		 "FROM bdaquamovil.dbo.tblTerceros " + 
					  		 "JOIN (   SELECT tbldctosordenes.IDLOCAL, tbldctosordenes.idCliente, " + 
					  		 "CEILING(SUM(tbldctosordenesdetalle.CANTIDAD)/ + ?3  )  AS promedio " +
					  		 "FROM bdaquamovil.dbo.tbldctosordenes " +
					  		 "JOIN bdaquamovil.dbo.tbldctosordenesdetalle " + 
			                 "ON tbldctosordenes.IDLOCAL =  tbldctosordenesdetalle.IDLOCAL " +
			                 "AND tbldctosordenes.IDTIPOORDEN =  tbldctosordenesdetalle.IDTIPOORDEN " +
			                 "AND tbldctosordenes.IDORDEN =  tbldctosordenesdetalle.IDORDEN " +
			                 "WHERE tbldctosordenes.IDLOCAL = ?1 " +
			                 "AND tbldctosordenes.IDTIPOORDEN = 9 " +
			                 "AND tbldctosordenesdetalle.IDTIPO = 4 " +
			                 "AND tbldctosordenes.idPeriodo IN (?2 ) " +
			                 "GROUP BY tbldctosordenes.IDLOCAL,  tbldctosordenes.idCliente) AS tmpPRO " +
			                 "ON tblterceros.idLocal = tmpPRO.IDLOCAL " +
			                 "AND tblterceros.idCliente = tmpPRO.idCliente " +
			                 "WHERE tblterceros.IDLOCAL = ?1 "
			                 , nativeQuery = true)
			  public void actualizaPromedio(int idLocal, List<String> xIdPeriodoLista,int xPeriodoFactura );
			  
				// ActualizaPromedio
			  @Modifying
			  @Transactional
			  @Query(value = "UPDATE tblTerceros SET tblTerceros.promedioEstrato = ROUND(tmpCAN.cantidad / (tmpTERC.numUsuario  * ?3 ),0) " +
					  		 "FROM bdaquamovil.dbo.tblTerceros " + 
					  		 "JOIN (   SELECT tbldctosordenes.IDLOCAL, tblDctosOrdenesDetalle.idEstracto, SUM(tblDctosOrdenesDetalle.CANTIDAD) AS cantidad " + 
					  		 "FROM bdaquamovil.dbo.tblDctosOrdenes " +
					  		 "JOIN  bdaquamovil.dbo.tblDctosOrdenesDetalle " +
					  		 "ON tblDctosOrdenes.IDLOCAL =   tblDctosOrdenesDetalle.IDLOCAL " +
					  		 "AND tblDctosOrdenes.IDTIPOORDEN =   tblDctosOrdenesDetalle.IDTIPOORDEN " +
					  		 "AND tblDctosOrdenes.IDORDEN =  tblDctosOrdenesDetalle.IDORDEN " +
					  		 " WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
					  		 "AND tblDctosOrdenes.IDTIPOORDEN = 9 " +
					  		 "AND tblDctosOrdenesDetalle.IDTIPO = 4 " +
					  		 "AND tblDctosOrdenes.idPeriodo IN (?2) " +
					  		 "GROUP BY tblDctosOrdenes.IDLOCAL, tblDctosOrdenesDetalle.idEstracto) AS tmpCAN  " +
					  		 "ON tblTerceros.idLocal = tmpCAN.IDLOCAL " +
					  		 "AND tblTerceros.idEstracto = tmpCAN.idEstracto " +
					  		 "JOIN (SELECT idLocal,  idEstracto, COUNT(idCliente) AS numUsuario " +
					  		 "FROM  tblTerceros AS tblTerceros_1 " +
					  		 "WHERE idLocal = ?1 " +
					  		 "AND estado IN (1) " +
					  		 "GROUP BY idLocal,  idEstracto) AS tmpTERC " +
					  		 "ON tblTerceros.idLocal = tmpTERC.idLocal " +
					  		 "AND tblTerceros.idEstracto = tmpTERC.idEstracto " +
					  		 "WHERE tblTerceros.idLocal = ?1 "
			                 , nativeQuery = true)
			  public void actualizaPromedioEstrato(int idLocal, List<String> xIdPeriodoLista,int xPeriodoFactura );
			  
			  
			  
			  @Modifying
			  @Transactional
			  @Query(value = "UPDATE tblterceros " +
			                 "SET  historiaConsumo =  tbldctosordenesdetalle_2.historicoConsumo " +
			                 "FROM    tblterceros " +
			                 "JOIN " +
			                 "(SELECT tbldctosordenesdetalle.IDLOCAL, " +
			                 "tbldctosordenesdetalle.idCliente, " +
			                 "tbldctosordenesdetalle_1.historicoConsumo, " +
			                 "tbldctosordenesdetalle_1.idPeriodo " +
			                 "FROM  tbldctosordenesdetalle " +
			                 "JOIN ( " +
			                 "SELECT ?1   AS idLocal, " +
			                 "?2   AS idPeriodo, " +
			                 "[idCliente], " +
			                 "STUFF((SELECT '  '  historicoConsumo " +
			                 "FROM tmp_historicoConsumo " +
			                 "WHERE idCliente =  Results.idCliente " +
			                 "FOR XML PATH(''),TYPE).value('(./text())[1]','VARCHAR(MAX)') " +
			                 ",1,2,'') AS historicoConsumo " +
			                 "FROM tmp_historicoConsumo Results " +
			                 "GROUP BY Results.idCliente)  AS tbldctosordenesdetalle_1 " +
			                 "ON tbldctosordenesdetalle.IDLOCAL =tbldctosordenesdetalle_1.IDLOCAL " +
			                 "AND tbldctosordenesdetalle.idCliente = tbldctosordenesdetalle_1.idCliente " +
			                 "WHERE tbldctosordenesdetalle.idLocal = ?1 ) AS tbldctosordenesdetalle_2 " +
			                 "ON tblterceros.IDLOCAL = tbldctosordenesdetalle_2.IDLOCAL " +
			                 "AND tblterceros.idCliente =  tbldctosordenesdetalle_2.idCliente ", nativeQuery = true)
			  public void actualizaHistoricoConsumo(int idLocal, int xIdPeriodo);
			  
			  
			  @Modifying
			  @Transactional
			  @Query(value = "UPDATE tblterceros " +
			                 "SET  estadoCorte =  tbldctosordenes.estadoCorte " +
			                 "FROM tbldctosordenes " +
			                 "JOIN tblterceros " +
			                 "ON tbldctosordenes.IDLOCAL    =  tblterceros.idLocal " +
			                 "AND tbldctosordenes.idCliente = tblterceros.idCliente " +
			                 "WHERE tbldctosordenes.IDLOCAL = ?1 " +
			                 "AND tbldctosordenes.idPeriodo = ?2 ", nativeQuery = true)
			  public void actualizaRecuperaEstadoCorte(int idLocal, int xIdPeriodo);
			  
			  
			  
				@Query(value = "SELECT * " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idTipoTercero = 1 " +
						"AND tblTerceros.idCliente = ?2 ",
						nativeQuery = true)
				List<TercerosDTO> ObtenerSuscriptor(int idLocal, String idCliente);
			  
			  
			  
			  
			  
				@Query(value = "SELECT tblTerceros.idCliente " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idRuta = ?2 " +
						"AND tblTerceros.idTipoTercero = 1",
						nativeQuery = true)
				List<String> ObtenerListaTercerosPorRuta(int idLocal, int idRuta);
				
				
				@Query(value = "SELECT tblTerceros.idCliente " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idTipoTercero = 1",
						nativeQuery = true)
				List<String> ObtenerListaTerceros(int idLocal);
			  
			  
			  
			  
			  
			  
				@Query(value = " SELECT tblterceros.idLocal        "
		                + "       ,tblterceros.idCliente      "
		                + "       ,tblterceros.nombreTercero  "
		                + "       ,tblterceros.telefonoCelular "
		                + "       ,tblterceros.email          "
		                + "       ,tbldctos.idDcto            "
		                + " 	  ,tblterceros.idRuta         "
		                + "	  ,tbltercerosRuta.nombreRuta "
		                + " FROM tblterceros                  "
		                + " INNER JOIN tbldctos               "
		                + " ON tblterceros.idLocal    =       "
		                + "                  tbldctos.idLocal "
		                + " AND tblterceros.idCliente =       "
		                + "                tbldctos.idCliente "
		                + " INNER JOIN tbltercerosRuta        "
		                + " ON tbltercerosRuta.idLocal =      "
		                + "	          tblterceros.idLocal "
		                + " AND tbltercerosRuta.idRuta =      "
		                + "		   tblterceros.idRuta "
		                + " WHERE tblterceros.idLocal   =     "
		                + "?1                     "
		                + " AND tblterceros.estadoEmail = 1   "
		                + " AND tbldctos.IDTIPOORDEN    = 9   "
		                + " AND tbldctos.idPeriodo      =     "
		                + "?2                   "
		                + " AND tbldctos.idCliente  IN (?3)   "
		                + " AND NOT EXISTS (                 "
		                + " SELECT tblagendaeventolog.idLocal "
		                + "     ,tblagendaeventolog.idCliente "
		                + " FROM tblagendaeventolog           "
		                + " WHERE tblterceros.idLocal   =     "
		                + "        tblagendaeventolog.idLocal "
		                + " AND tblagendaeventolog.idPeriodo = "
		                + "                 tbldctos.idPeriodo "
		                + " AND tblagendaeventolog.idCliente = "
		                + "            tblterceros.idCliente  "
		                + " AND tblagendaeventolog.idEvento = ?4 ) "
		                + " ORDER BY tblterceros.idRuta ,     "
		                + "          tblterceros.ordenRuta ",
						nativeQuery = true)
				List<TercerosDTO> listaUnCliente(int idLocal, int idPeriodo, List<String> idCliente, int idEvento);
				
				
				@Query(value = " SELECT tblterceros.idLocal        "
		                + "       ,tblterceros.idCliente      "
		                + "       ,tblterceros.nombreTercero  "
		                + "       ,tblterceros.telefonoCelular "
		                + "       ,tblterceros.email          "
		                + "       ,tbldctos.idDcto            "
		                + " 	  ,tblterceros.idRuta         "
		                + "	  ,tbltercerosRuta.nombreRuta "
		                + " FROM tblterceros                  "
		                + " INNER JOIN tbldctos               "
		                + " ON tblterceros.idLocal    =       "
		                + "                  tbldctos.idLocal "
		                + " AND tblterceros.idCliente =       "
		                + "                tbldctos.idCliente "
		                + " INNER JOIN tbltercerosRuta        "
		                + " ON tbltercerosRuta.idLocal =      "
		                + "	          tblterceros.idLocal "
		                + " AND tbltercerosRuta.idRuta =      "
		                + "		   tblterceros.idRuta "
		                + " WHERE tblterceros.idLocal   =     "
		                + "?1                     "
		                + " AND tblterceros.estadoWhatsApp = 1   "
		                + " AND tbldctos.IDTIPOORDEN    = 9   "
		                + " AND tbldctos.idPeriodo      =     "
		                + "?2                   "
		                + " AND tbldctos.idCliente  IN (?3)   "
		                + " AND NOT EXISTS (                 "
		                + " SELECT tblagendaeventolog.idLocal "
		                + "     ,tblagendaeventolog.idCliente "
		                + " FROM tblagendaeventolog           "
		                + " WHERE tblterceros.idLocal   =     "
		                + "        tblagendaeventolog.idLocal "
		                + " AND tblagendaeventolog.idPeriodo = "
		                + "                 tbldctos.idPeriodo "
		                + " AND tblagendaeventolog.idCliente = "
		                + "            tblterceros.idCliente  "
		                + " AND tblagendaeventolog.idEvento = ?4 ) "
		                + " ORDER BY tblterceros.idRuta ,     "
		                + "          tblterceros.ordenRuta ",
						nativeQuery = true)
				List<TercerosDTO> listaUnClienteWhatsApp(int idLocal, int idPeriodo, List<String> idCliente, int idEvento);
				
				
				
				@Query(value = " SELECT tblterceros.idLocal        "
		                + "       ,tblterceros.idCliente      "
		                + "       ,tblterceros.nombreTercero  "
		                + "       ,tblterceros.email          "
		                + "       ,tbldctos.idDcto            "
		                + " 	  ,tblterceros.idRuta         "
		                + "	  ,tbltercerosRuta.nombreRuta "
		                + " FROM tblterceros                  "
		                + " INNER JOIN tbldctos               "
		                + " ON tblterceros.idLocal    =       "
		                + "                  tbldctos.idLocal "
		                + " AND tblterceros.idCliente =       "
		                + "                tbldctos.idCliente "
		                + " INNER JOIN tbltercerosRuta        "
		                + " ON tbltercerosRuta.idLocal =      "
		                + "	          tblterceros.idLocal "
		                + " AND tbltercerosRuta.idRuta =      "
		                + "		   tblterceros.idRuta "
		                + " WHERE tblterceros.idLocal   =     "
		                + "?1                     "
		                + " AND tblterceros.estadoEmail = 1   "
		                + " AND tbldctos.IDTIPOORDEN    = 9   "
		                + " AND tbldctos.idPeriodo      =     "
		                + "?2                   "
		                + " AND NOT EXISTS (                 "
		                + " SELECT tblagendaeventolog.idLocal "
		                + "     ,tblagendaeventolog.idCliente "
		                + " FROM tblagendaeventolog           "
		                + " WHERE tblterceros.idLocal   =     "
		                + "        tblagendaeventolog.idLocal "
		                + " AND tblagendaeventolog.idPeriodo = "
		                + "                 tbldctos.idPeriodo "
		                + " AND tblagendaeventolog.idCliente = "
		                + "            tblterceros.idCliente  "
		                + " AND tblagendaeventolog.idEvento = 100) "
		                + " ORDER BY tblterceros.idRuta ,     "
		                + "          tblterceros.ordenRuta ",
						nativeQuery = true)
				List<TercerosDTO> listaTodosLosClientesEstadoFacturaAct(int idLocal, int idPeriodo);
				
				
				@Query(value = " SELECT tblterceros.idLocal        "
		                + "       ,tblterceros.idCliente      "
		                + "       ,tblterceros.nombreTercero  "
		                + "       ,tblterceros.telefonoCelular"
		                + "       ,tblterceros.email          "
		                + "       ,tbldctos.idDcto            "
		                + " 	  ,tblterceros.idRuta         "
		                + "	  ,tbltercerosRuta.nombreRuta "
		                + " FROM tblterceros                  "
		                + " INNER JOIN tbldctos               "
		                + " ON tblterceros.idLocal    =       "
		                + "                  tbldctos.idLocal "
		                + " AND tblterceros.idCliente =       "
		                + "                tbldctos.idCliente "
		                + " INNER JOIN tbltercerosRuta        "
		                + " ON tbltercerosRuta.idLocal =      "
		                + "	          tblterceros.idLocal "
		                + " AND tbltercerosRuta.idRuta =      "
		                + "		   tblterceros.idRuta "
		                + " WHERE tblterceros.idLocal   =     "
		                + "?1                     "
		                + " AND tblterceros.estadoWhatsApp = 1   "
		                + " AND tbldctos.IDTIPOORDEN    = 9   "
		                + " AND tbldctos.idPeriodo      =     "
		                + "?2                   "
		                + " AND NOT EXISTS (                 "
		                + " SELECT tblagendaeventolog.idLocal "
		                + "     ,tblagendaeventolog.idCliente "
		                + " FROM tblagendaeventolog           "
		                + " WHERE tblterceros.idLocal   =     "
		                + "        tblagendaeventolog.idLocal "
		                + " AND tblagendaeventolog.idPeriodo = "
		                + "                 tbldctos.idPeriodo "
		                + " AND tblagendaeventolog.idCliente = "
		                + "            tblterceros.idCliente  "
		                + " AND tblagendaeventolog.idEvento = 200) "
		                + " ORDER BY tblterceros.idRuta ,     "
		                + "          tblterceros.ordenRuta ",
						nativeQuery = true)
				List<TercerosDTO> listaTodosLosClientesEstadoFacturaActWhasApp(int idLocal, int idPeriodo);
			  
			  
			  
				@Query(value = "SELECT tblTerceros.idCliente " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND IDTIPOTERCERO=1              " + 
						"AND tblTerceros.idRuta = ?2 " +
						"AND tblTerceros.estadoEmail != 1 ",
						nativeQuery = true)
				List<String> ObtenerListaTercerosEstadoEmail(int idLocal, int idRuta);
				
				@Query(value = "SELECT tblTerceros.idCliente " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND IDTIPOTERCERO=1              " +
						"AND tblTerceros.idRuta = ?2 " +
						"AND tblTerceros.estadoWhatsApp != 1 ",
						nativeQuery = true)
				List<String> ObtenerListaTercerosEstadoWhatsApp(int idLocal, int idRuta);
				
				
				
				@Query(value = "SELECT tblTerceros.idCliente " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND IDTIPOTERCERO=1              " +
						"AND tblTerceros.estadoEmail != 1 ",
						nativeQuery = true)
				List<String> ObtenerListaTercerosEstadoEmailSinRuta(int idLocal);
				
				
				@Query(value = "SELECT tblTerceros.idCliente " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND IDTIPOTERCERO=1              " +
						"AND tblTerceros.estadoWhatsApp != 1 ",
						nativeQuery = true)
				List<String> ObtenerListaTercerosEstadoWhatsAppSinRuta(int idLocal);
				
				
				@Query(value = "SELECT tblTerceros.idCliente " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND IDTIPOTERCERO=1            " +
						"AND (estadoWhatsApp != 1 OR estadoWhatsApp IS NULL ) " +
						"AND (estadoEMAIL != 1   OR estadoEMAIL IS NULL ) ",
						nativeQuery = true)
				List<String> ObtenerListaTercerosEstadoWhatsAppEstadoEmailSinRuta(int idLocal);
				
				
				@Query(value = "SELECT tblTerceros.idCliente " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idRuta = ?2 " +
						"AND IDTIPOTERCERO=1            " +
						"AND (estadoWhatsApp != 1 OR estadoWhatsApp IS NULL ) " +
						"AND (estadoEMAIL != 1   OR estadoEMAIL IS NULL ) ",
						nativeQuery = true)
				List<String> ObtenerListaTercerosEstadoWhatsAppEstadoEmail(int idLocal, int idRuta);
				
				
				
				@Query(value = "SELECT tblterceros.idLocal                "
		                + "       ,tblterceros.idCliente             "
		                + "       ,tblterceros.idTercero             "
		                + "       ,tblterceros.nombreTercero         "
		                + "       ,tblterceros.direccionTercero      "
		                + "       ,tblciudades.nombreCiudad          "
		                + "       ,tblterceros.telefonoFijo          "
		                + "       ,tblterceros.telefonoCelular       "
		                + "       ,tblterceros.telefonoFax           "
		                + "       ,tblterceros.email                 "
		                + "       ,tblterceros.direccionCobro        "
		                + "       ,tblterceros.CC_Nit                "
		                + " 	  ,tblterceros.estado                "
		                + " 	  ,(tmpEstado.nombreCausa)           "
		                + "                          AS nombreEstado "
		                + "       ,tblterceros.ordenRuta             "
		                + "     ,(tbltercerosruta.nombreCiclo + '-'+ "
		                + "            tbltercerosruta.nombreRuta)   "
		                + "                          AS   nombreRuta "
		                + "       ,tblterceroestracto.nombreEstracto "
		                + "       ,tblterceros.codigoAlterno         "
		                + "       ,tblterceros.numeroMedidor         "
		                + "       ,tblterceros.idMedidor             "
		                + " 	  ,(tmpMedidor.marcaMedidor)         "
		                + " 	                     AS marcaMedidor "
		                + " 	  ,(tmpMedidor.diametro)             "
		                + " 	                  AS diametroMedidor "
		                + "       ,tblterceros.idMacro               "
		                + " 	  ,(tmpMacro.nombreMacro)            "
		                + " 	                     AS nombreMacro  "
		                + "       ,(tmpMacro.diametro)               "
		                + " 	                   AS diametroMacro  "
		                + " 	  ,tblterceros.estadoMedidor         "
		                + "       ,(tmpEstMedidor.nombreCausa)       "
		                + " 	             AS nombreEstadoMedidor  "
		                + "       ,tblterceros.estadoCorte           "
		                + " 	  ,(tmpEstCorte.nombreCausa)         "
		                + " 	               AS nombreEstadoCorte  "
		                + "       ,tblterceros.estadoEmail           "
		                + "       ,tblterceros.codigoCatastral       "
		                + "       ,tblterceros.matricula             "
		                + " FROM       tblterceros                   "
		                + " INNER JOIN tblterceroestracto            "
		                + " ON tblterceroestracto.idEstracto =       "
		                + "                  tblterceros.idEstracto  "
		                + " AND tblterceroestracto.idLocal =         "
		                + "                     tblterceros.idLocal  "
		                + " INNER JOIN tbltercerosruta               "
		                + " ON tblterceros.idRuta            =       "
		                + "                  tbltercerosruta.idRuta  "
		                + " AND tblterceros.idLocal            =     "
		                + "                 tbltercerosruta.idLocal  "
		                + " INNER JOIN tblciudades                   "
		                + " ON tblciudades.idCiudad =                "
		                + "                tblterceros.idDptoCiudad  "
		                + " INNER JOIN (                             "
		                + "       SELECT idTipoTabla,                "
		                + "              idCausa,                    "
		                + "              nombreCausa                 "
		                + "       FROM     tbltipocausanota          "
		                + "       WHERE idtipoTabla = 3              "
		                + " 	                     ) AS tmpEstado  "
		                + " ON tmpEstado.idCausa =                   "
		                + "                      tblterceros.estado  "
		                + " INNER JOIN (                             "
		                + "       SELECT idTipoTabla,                "
		                + "              idCausa,                    "
		                + "              nombreCausa                 "
		                + "       FROM tbltipocausanota              "
		                + "       WHERE idtipoTabla = 4              "
		                + " 	                 ) AS tmpEstMedidor  "
		                + " ON tmpEstMedidor.idCausa =               "
		                + "               tblterceros.estadoMedidor  "
		                + " INNER JOIN (                             "
		                + "       SELECT idTipoTabla,                "
		                + "              idCausa,                    "
		                + "              nombreCausa                 "
		                + "       FROM  tbltipocausanota             "
		                + "       WHERE idtipoTabla = 5              "
		                + " 	                  ) AS tmpEstCorte   "
		                + " ON tmpEstCorte.idCausa =                 "
		                + "                tblterceros.estadoCorte   "
		                + " INNER JOIN (                             "
		                + "        SELECT idLocal,                   "
		                + " 		  idMacro,                   "
		                + " 	          nombreMacro,               "
		                + " 	          diametro                   "
		                + "        FROM  tblmedidoresMacro           "
		                + "        WHERE idLocal =                   "
		                + "?1  ) AS tmpMacro             "
		                + " ON tmpMacro.idLocal =                    "
		                + "                   tblterceros.idLocal    "
		                + " AND tmpMacro.idMacro =                   "
		                + "                   tblterceros.idMacro    "
		                + " INNER JOIN (                             "
		                + "         SELECT idLocal,                  "
		                + "                idMedidor,                "
		                + "                marcaMedidor,             "
		                + "                diametro                  "
		                + "         FROM tblmedidores                "
		                + "         WHERE idLocal =                  "
		                + "?1  ) AS tmpMedidor           "
		                + " ON tmpMedidor.idLocal =                  "
		                + "                    tblterceros.idLocal   "
		                + " AND tmpMedidor.idMedidor =               "
		                + "                  tblterceros.idMedidor   "
		                + " WHERE tblterceros.idLocal        =       "
		                + "?1                            "
		                + " AND tblterceros.idTipoTercero    = 1     "
		                + " ORDER BY tblterceros.idRuta,             "
		                + "          tblterceros.ordenRuta",
						nativeQuery = true)
				List<TercerosDTO2> listaAllTercero(int idLocal);
				
				
				
				@Query(value = "SELECT tmpPRC.idLocal,                           "
		                + "        tmpPRC.idPeriodo,                        "
		                + "        tmpPRC.idRuta,                           "
		                + "        tmpPRC.nombreRuta,                       "
		                + "        tmpPRC.idCliente,                        "
		                + "        MAX(tmpPRC.nombreTercero)                "
		                + " 		                AS nombreTercero,   "
		                + " 	    MAX(tmpPRC.direccionTercero)            "
		                + " 		               AS direccionTercero, "
		                + "        MAX(tmpPRC.lecturaMedidor)               "
		                + "                               AS lecturaMedidor, "
		                + "        MAX(tmpPRC.lecturaMedidor) -             "
		                + "        SUM(tmpPRC.cantidad) AS lecturaMedidorAnterior, "
		                + " 		SUM(tmpPRC.cantidad) AS consumo,   "
		                + "        tmpPRC.nombrePeriodo,                    "
		                + " 		tmpPRC.idMacro,                     "
		                + " 		tmpPRC.nombreMacro                  "
		                + " FROM (                                          "
		                + " SELECT tbldctos.idLocal,                        "
		                + "        tbldctos.IDTIPOORDEN,                    "
		                + "        tbldctos.idDcto,                         "
		                + "        tbldctos.idPeriodo,                      "
		                + "        tbldctos.idCliente,                      "
		                + " 	    tmpTER.nombreTercero,                   "
		                + " 	    tmpTER.direccionTercero,  		    "
		                + "        tmpTER.idRuta,                           "
		                + "        tmpTER.nombreRuta,                       "
		                + "        tmpTER.nombreEstracto,                   "
		                + " 		tmpTER.idMacro,                     "
		                + " 		tmpTER.nombreMacro,                 "
		                + "       (CASE WHEN tblplus.idTipo = 4             "
		                + "        THEN                                     "
		                + "         tbldctosordenesdetalle.lecturaMedidor   "
		                + "        ELSE 0 END) AS lecturaMedidor,           "
		                + "       (CASE WHEN tblplus.idTipo = 4             "
		                + "        THEN tbldctosordenesdetalle.cantidad     "
		                + "        ELSE 0 END) AS cantidad,                 "
		                + "        tbldctosPeriodo.nombrePeriodo            "
		                + " FROM         tbldctos                           "
		                + " INNER JOIN tbldctosordenesdetalle               "
		                + " ON   tbldctos.IDLOCAL           =               "
		                + "             tbldctosordenesdetalle.IDLOCAL      "
		                + " AND  tbldctos.IDTIPOORDEN       =               "
		                + "         tbldctosordenesdetalle.IDTIPOORDEN      "
		                + " AND tbldctos.IDORDEN            =               "
		                + "             tbldctosordenesdetalle.IDORDEN      "
		                + " INNER JOIN tblplus                              "
		                + " ON tbldctosordenesdetalle.IDPLU =               "
		                + "                              tblplus.idPlu      "
		                + " AND tbldctosordenesdetalle.idLocal =            "
		                + "                         tblplus.idLocal         "
		                + " INNER JOIN tbldctosPeriodo                      "
		                + " ON tbldctosPeriodo.idPeriodo =                  "
		                + "                         tbldctos.idPeriodo      "
		                + " AND tbldctosPeriodo.idLocal =                   "
		                + "                           tbldctos.idLocal      "
		                + " INNER JOIN                                      "
		                + "    (SELECT tblterceros.idCliente,               "
		                + " 	       tblterceros.nombreTercero,           "
		                + " 	       tblterceros.direccionTercero,        "
		                + "            tblterceros.idLocal,                 "
		                + "            tbltercerosruta.idRuta,              "
		                + "            tbltercerosruta.nombreRuta,          "
		                + "          ( CONVERT(VARCHAR(2),                  "
		                + "            tblterceroestracto.idEstracto) +     "
		                + "                                       '-' +     "
		                + "            tblterceroestracto.nombreEstracto)   "
		                + "                              AS nombreEstracto, "
		                + " 	       tblmedidoresmacro.idMacro,           "
		                + " 	     (tblmedidoresmacro.nombreMacro)        "
		                + " 		                     AS nombreMacro "
		                + "     FROM tblterceroestracto                     "
		                + "     INNER JOIN tblterceros                      "
		                + "     ON tblterceroestracto.idEstracto =          "
		                + "                          tblterceros.idEstracto "
		                + " 	 AND tblterceroestracto.idLocal =           "
		                + "                             tblterceros.idLocal "
		                + "     INNER JOIN tbltercerosruta                  "
		                + "     ON tblterceros.idRuta =                     "
		                + " 	                     tbltercerosruta.idRuta "
		                + "     AND tblterceros.idLocal =                   "
		                + "    			    tbltercerosruta.idLocal "
		                + " 	 INNER JOIN tblmedidoresmacro               "
		                + " 	 ON tblmedidoresmacro.idMacro=              "
		                + "                             tblterceros.idMacro "
		                + " 	  AND tblmedidoresmacro.idLocal =           "
		                + "                       tblterceros.idLocal       "
		                + "                                    ) AS tmpTER  "
		                + " ON tmpTER.idCliente =  tbldctos.idCliente       "
		                + " AND tmpTER.idLocal =  tbldctos.idLocal          "
		                + " WHERE  tbldctos.idPeriodo   =                   "
		                + "?1                                 "
		                + " AND  tbldctos.idLocal  =                        "
		                + "?2 )     AS tmpPRC                  "
		                + " WHERE  tmpPRC.idMacro                     =     "
		                + "?3                                  "
		                + " AND     tmpPRC.idLocal                    =     "
		                + "?2                                  "
		                + " AND  tmpPRC.IDTIPOORDEN  = 9                    "
		                + " GROUP BY tmpPRC.idLocal,                        "
		                + "        tmpPRC.IDTIPOORDEN,                       "
		                + "        tmpPRC.idDcto,                           "
		                + "        tmpPRC.idPeriodo,                        "
		                + "        tmpPRC.idCliente,                        "
		                + "        tmpPRC.idRuta,                           "
		                + "        tmpPRC.nombrePeriodo,                    "
		                + "        tmpPRC.nombreRuta,                       "
		                + " 	   tmpPRC.idMacro,                          "
		                + " 	   tmpPRC.nombreMacro",
						nativeQuery = true)
				List<TercerosDTO2> listaConsumoMacro( int idPeriodo, int idLocal, int idMacro);
		
				
				@Query(value = " SELECT tblterceros.idCliente,                    "
		                + "  tblterceros.idTercero,                        "
		                + "  tblterceros.tipoIdTercero,	                   "
		                + "  tblterceros.digitoVerificacion,               "
		                + "  tblterceros.idTipoTercero, 	           "
		                + "  tblterceros.idPersona,                        "
		                + "  tblterceros.idAutoRetenedor,                  "
		                + "  tblterceros.idRegimen, 	                   "
		                + "  tblterceros.nombreTercero, 	           "
		                + "  tblterceros.direccionTercero,                 "
		                + "  tblterceros.idDptoCiudad, 	                   "
		                + "  tblterceros.telefonoFijo, 	                   "
		                + "  tblterceros.telefonoCelular,                  "
		                + "  tblterceros.telefonoFax, 	                   "
		                + "  tblterceros.email, 		           "
		                + "  tblterceros.idFormaPago, 	                   "
		                + "  tblterceros.estado,                           "
		                + "  tblregimen.nombreRegimen, 	                   "
		                + "  tbltipotercero.nombreTipoTercero,             "
		                + "  tblciudades.nombreCiudad, 	                   "
		                + "  tblciudades.nombreDpto,    	           "
		                + "  tblterceros.idVendedor,     	           "
		                + "  tblterceros.idRuta,                           "
		                + "  tbltercerosruta.nombreRuta,                   "
		                + "  tblterceroestracto.nombreEstracto,            "
		                + "  tblterceros.idEstracto,                       "
		                + "  tmpACT.lecturaActual,                         "
		                + "  tmpACT.cantidadPedida,                        "
		+ "  ( SELECT  TOP 1                               "
		+ "          tmpDET.lecturaMedidor                 "
		+ "             as lecturaAnterior                 "
		+ " FROM   tblDctos tmpDCT                         "
		+ " INNER JOIN tblDctosOrdenesDetalle              "
		+ "                            tmpDET              "
		+ " ON tmpDCT.IDLOCAL        =                     "
		+ "       tmpDET.IDLOCAL                           "
		+ " AND tmpDCT.IDTIPOORDEN   =                     "
		+ "   tmpDET.IDTIPOORDEN                           "
		+ " AND tmpDCT.IDORDEN       =                     "
		+ "       tmpDET.IDORDEN                           "
		+ " AND   tmpDCT.idLocal     =                     "
		+ "?1                                     "
		+ " AND   tmpDCT.idTipoOrden IN (9,29)             "
		+ " AND   tmpDCT.idPeriodo   =                     "
		+ "?2                           "
		+ " AND   tmpDET.IDTIPO      =                     "
		 +" ?3                                     "
		+ " AND   tmpDET.IDCLIENTE   =                     "
		+ "         tblterceros.idCliente                  "
		+ " ORDER BY tmpDCT.idCliente ,                    "
		+ "           tmpDCT.idOrden DESC )                "
		+ " 		    AS lecturaAnterior,            "

		                + " tblterceros.promedio,                         "
		                + "  tblterceros.ordenRuta,                        "
		                + "  tblterceros.CC_Nit,                           "
		                + "  tmpCAU.idCausal AS idCausa,                   "
		                + "  tmpEST.nombreEstado,                          "
		                + "  tmpCAU.nombreCausa,                           "
		                + "  tmpACT.fechaRegistroTx 			   "
		                + "  FROM tblterceros                              "
		                + "  INNER JOIN tblregimen                         "
		                + "  ON tblregimen.idRegimen         =             "
		                + "              tblterceros.idRegimen             "
		                + "  INNER JOIN tbltipotercero                     "
		                + "  ON tbltipotercero.idTipoTercero =             "
		                + "           tblterceros.idTipoTercero            "
		                + "  INNER JOIN tblciudades                        "
		                + "  ON tblciudades.idCiudad         =             "
		                + "           tblterceros.idDptoCiudad             "
		                + "  INNER JOIN tbltercerosruta                    "
		                + "  ON tbltercerosruta.idRuta       =             "
		                + "                 tblterceros.idRuta             "
		                + "  AND tbltercerosruta.idLocal     =             "
		                + "                tblterceros.idLocal             "
		                + "  INNER JOIN tblterceroestracto                 "
		                + "  ON tblterceroestracto.idEstracto =            "
		                + "              tblterceros.idEstracto            "
		                + "  AND tblterceroestracto.idLocal=               "
		                + "                 tblterceros.idLocal            "
		                + "  INNER JOIN (                                  "
		                + "  SELECT tbltipocausanota.idCausa,              "
		                + "  	 tbltipocausanota.nombreCausa              "
		                + "  		      AS nombreEstado              "
		                + "  FROM tbltipocausanota                         "
		                + "  WHERE tbltipocausanota.idTipoTabla = 3 )      "
		                + "  			  AS tmpEST                "
		                + "  ON   tmpEST.idCausa    =                      "
		                + "  	              tblterceros.estado           "
		                + "  LEFT OUTER JOIN (                             "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,     "
		                + "      tbldctosordenesdetalle.fechaRegistroTx,   "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor)   "
		                + "                             AS lecturaActual,  "
		                + "   MAX(tbldctosordenesdetalle.cantidadPedida)   "
		                + "                             AS cantidadPedida  "
		                + "   FROM   tbldctosordenes                       "
		                + "   INNER JOIN tbldctosordenesdetalle            "
		                + "   ON tbldctosordenes.IDLOCAL      =            "
		                + "         tbldctosordenesdetalle.IDLOCAL         "
		                + "   AND tbldctosordenes.IDTIPOORDEN =            "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN        "
		                + "   AND tbldctosordenes.IDORDEN       =          "
		                + "          tbldctosordenesdetalle.IDORDEN        "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =         "
		                + "        tbldctosordenesdetalle.idLocal          "
		                + "   AND  tbldctosordenes.IDLOCAL     =           "
		                + "?1                                     "
		                + "   AND tbldctosordenes.idPeriodo    =           "
		                + "?4                             "
		                + "   AND tbldctosordenesdetalle.IDTIPO =          "
		                + "?3                                      "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente,   "
		                + "       tbldctosordenesdetalle.fechaRegistroTx)  "
		                + "                                    AS tmpACT   "
		                + "  ON tmpACT.idCliente = tblterceros.idCliente   "
		                + "  LEFT OUTER JOIN (                             "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,     "
		                + "  MAX(tbldctosordenesdetalle.idNovedadLectura)  "
		                + "                               AS idCausal,     "
		                + "   MAX(tbltipocausanota.nombreCausa)            "
		                + "                            AS nombreCausa      "
		                + "   FROM   tbldctosordenes                       "
		                + "   INNER JOIN tbldctosordenesdetalle            "
		                + "   ON tbldctosordenes.IDLOCAL      =            "
		                + "         tbldctosordenesdetalle.IDLOCAL         "
		                + "   AND tbldctosordenes.IDTIPOORDEN =            "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN        "
		                + "   AND tbldctosordenes.IDORDEN       =          "
		                + "          tbldctosordenesdetalle.IDORDEN        "
		                + "   INNER JOIN tbltipocausanota                  "
		                + "   ON tbltipocausanota.idCausa =                "
		                + "      tbldctosordenesdetalle.idNovedadLectura   "
		                + "   WHERE  tbldctosordenes.IDLOCAL   =           "
		                + "        tbldctosordenesdetalle.idLocal          "
		                + "   AND  tbldctosordenes.IDLOCAL     =           "
		                + "?1                                     "
		                + "   AND tbldctosordenes.idPeriodo    =           "
		                + "?4                            "
		                + "   AND tbldctosordenesdetalle.IDTIPO =          "
		                + "?3                                      "
		                + "   AND tbldctosordenesdetalle.IDORDEN =   ?8    "
		                + "   AND tbltipocausanota.idTipoTabla  = 2        "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente)   "
		                + "                                     AS tmpCAU  "
		                + "  ON tmpCAU.idCliente = tblterceros.idCliente   "
		                + "  WHERE tblterceros.idLocal         =           "
		                + "?1                                     "
		                + "  AND tbltercerosruta.idRuta        =           "
		                + "?5                                      "
		                + "  AND tblterceros.estado NOT IN (2)             "
		                + " ORDER BY tblTerceros.ordenRuta              "
		                + " OFFSET ?6 ROWS          "
		                + " FETCH NEXT ?7 ROWS ONLY ",
						nativeQuery = true)
				List<TercerosDTO2> listaLecturaRutaTx(int idLocal, int xIdPeriodoAnterior, int xIdTipo, int idPeriodo, int xIdRuta, int xInicioRegistroTx, int xCuentaRegistroTx, int idOrden );
				
				
				
				@Query(value = " SELECT tblterceros.idCliente,                    "
		                + "  tblterceros.idTercero,                        "
		                + "  tblterceros.tipoIdTercero,	                   "
		                + "  tblterceros.digitoVerificacion,               "
		                + "  tblterceros.idTipoTercero, 	           "
		                + "  tblterceros.idPersona,                        "
		                + "  tblterceros.idAutoRetenedor,                  "
		                + "  tblterceros.idRegimen, 	                   "
		                + "  tblterceros.nombreTercero, 	           "
		                + "  tblterceros.direccionTercero,                 "
		                + "  tblterceros.idDptoCiudad, 	                   "
		                + "  tblterceros.telefonoFijo, 	                   "
		                + "  tblterceros.telefonoCelular,                  "
		                + "  tblterceros.telefonoFax, 	                   "
		                + "  tblterceros.email, 		           "
		                + "  tblterceros.idFormaPago, 	                   "
		                + "  tblterceros.estado,                           "
		                + "  tblregimen.nombreRegimen, 	                   "
		                + "  tbltipotercero.nombreTipoTercero,             "
		                + "  tblciudades.nombreCiudad, 	                   "
		                + "  tblciudades.nombreDpto,    	           "
		                + "  tblterceros.idVendedor,     	           "
		                + "  tblterceros.idRuta,                           "
		                + "  tbltercerosruta.nombreRuta,                   "
		                + "  tblterceroestracto.nombreEstracto,            "
		                + "  tblterceros.idEstracto,                       "
		                + "  tmpACT.lecturaActual,                         "
		                + "  tmpACT.cantidadPedida,                        "
		+ "  ( SELECT  TOP 1                               "
		+ "          tmpDET.lecturaMedidor                 "
		+ "             as lecturaAnterior                 "
		+ " FROM   tblDctos tmpDCT                         "
		+ " INNER JOIN tblDctosOrdenesDetalle              "
		+ "                            tmpDET              "
		+ " ON tmpDCT.IDLOCAL        =                     "
		+ "       tmpDET.IDLOCAL                           "
		+ " AND tmpDCT.IDTIPOORDEN   =                     "
		+ "   tmpDET.IDTIPOORDEN                           "
		+ " AND tmpDCT.IDORDEN       =                     "
		+ "       tmpDET.IDORDEN                           "
		+ " AND   tmpDCT.idLocal     =                     "
		+ "?1                                     "
		+ " AND   tmpDCT.idTipoOrden IN (9,29)             "
		+ " AND   tmpDCT.idPeriodo   =                     "
		+ "?2                           "
		+ " AND   tmpDET.IDTIPO      =                     "
		 +" ?3                                     "
		+ " AND   tmpDET.IDCLIENTE   =                     "
		+ "         tblterceros.idCliente                  "
		+ " ORDER BY tmpDCT.idCliente ,                    "
		+ "           tmpDCT.idOrden DESC )                "
		+ " 		    AS lecturaAnterior,            "

		                + " tblterceros.promedio,                         "
		                + "  tblterceros.ordenRuta,                        "
		                + "  tblterceros.CC_Nit,                           "
		                + "  tmpCAU.idCausal AS idCausa,                   "
		                + "  tmpEST.nombreEstado,                          "
		                + "  tmpCAU.nombreCausa,                           "
		                + "  tmpACT.fechaRegistroTx 			   "
		                + "  FROM tblterceros                              "
		                + "  INNER JOIN tblregimen                         "
		                + "  ON tblregimen.idRegimen         =             "
		                + "              tblterceros.idRegimen             "
		                + "  INNER JOIN tbltipotercero                     "
		                + "  ON tbltipotercero.idTipoTercero =             "
		                + "           tblterceros.idTipoTercero            "
		                + "  INNER JOIN tblciudades                        "
		                + "  ON tblciudades.idCiudad         =             "
		                + "           tblterceros.idDptoCiudad             "
		                + "  INNER JOIN tbltercerosruta                    "
		                + "  ON tbltercerosruta.idRuta       =             "
		                + "                 tblterceros.idRuta             "
		                + "  AND tbltercerosruta.idLocal     =             "
		                + "                tblterceros.idLocal             "
		                + "  INNER JOIN tblterceroestracto                 "
		                + "  ON tblterceroestracto.idEstracto =            "
		                + "              tblterceros.idEstracto            "
		                + "  AND tblterceroestracto.idLocal=               "
		                + "                 tblterceros.idLocal            "
		                + "  INNER JOIN (                                  "
		                + "  SELECT tbltipocausanota.idCausa,              "
		                + "  	 tbltipocausanota.nombreCausa              "
		                + "  		      AS nombreEstado              "
		                + "  FROM tbltipocausanota                         "
		                + "  WHERE tbltipocausanota.idTipoTabla = 3 )      "
		                + "  			  AS tmpEST                "
		                + "  ON   tmpEST.idCausa    =                      "
		                + "  	              tblterceros.estado           "
		                + "  LEFT OUTER JOIN (                             "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,     "
		                + "      tbldctosordenesdetalle.fechaRegistroTx,   "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor)   "
		                + "                             AS lecturaActual,  "
		                + "   MAX(tbldctosordenesdetalle.cantidadPedida)   "
		                + "                             AS cantidadPedida  "
		                + "   FROM   tbldctosordenes                       "
		                + "   INNER JOIN tbldctosordenesdetalle            "
		                + "   ON tbldctosordenes.IDLOCAL      =            "
		                + "         tbldctosordenesdetalle.IDLOCAL         "
		                + "   AND tbldctosordenes.IDTIPOORDEN =            "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN        "
		                + "   AND tbldctosordenes.IDORDEN       =          "
		                + "          tbldctosordenesdetalle.IDORDEN        "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =         "
		                + "        tbldctosordenesdetalle.idLocal          "
		                + "   AND  tbldctosordenes.IDLOCAL     =           "
		                + "?1                                     "
		                + "   AND tbldctosordenes.idPeriodo    =           "
		                + "?4                             "
		                + "   AND tbldctosordenesdetalle.IDTIPO =          "
		                + "?3                                      "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente,   "
		                + "       tbldctosordenesdetalle.fechaRegistroTx)  "
		                + "                                    AS tmpACT   "
		                + "  ON tmpACT.idCliente = tblterceros.idCliente   "
		                + "  LEFT OUTER JOIN (                             "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,     "
		                + "  MAX(tbldctosordenesdetalle.idNovedadLectura)  "
		                + "                               AS idCausal,     "
		                + "   MAX(tbltipocausanota.nombreCausa)            "
		                + "                            AS nombreCausa      "
		                + "   FROM   tbldctosordenes                       "
		                + "   INNER JOIN tbldctosordenesdetalle            "
		                + "   ON tbldctosordenes.IDLOCAL      =            "
		                + "         tbldctosordenesdetalle.IDLOCAL         "
		                + "   AND tbldctosordenes.IDTIPOORDEN =            "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN        "
		                + "   AND tbldctosordenes.IDORDEN       =          "
		                + "          tbldctosordenesdetalle.IDORDEN        "
		                + "   INNER JOIN tbltipocausanota                  "
		                + "   ON tbltipocausanota.idCausa =                "
		                + "      tbldctosordenesdetalle.idNovedadLectura   "
		                + "   WHERE  tbldctosordenes.IDLOCAL   =           "
		                + "        tbldctosordenesdetalle.idLocal          "
		                + "   AND  tbldctosordenes.IDLOCAL     =           "
		                + "?1                                     "
		                + "   AND tbldctosordenes.idPeriodo    =           "
		                + "?4                            "
		                + "   AND tbldctosordenesdetalle.IDTIPO =          "
		                + "?3                                      "
		                + "   AND tbldctosordenesdetalle.IDORDEN =   ?8    "
		                + "   AND tbltipocausanota.idTipoTabla  = 2        "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente)   "
		                + "                                     AS tmpCAU  "
		                + "  ON tmpCAU.idCliente = tblterceros.idCliente   "
		                + "  WHERE tblterceros.idLocal         =    ?1       "
		                + " AND tblterceros.ordenRuta >=  ?9              "
		                + " AND tblterceros.idRuta = ?5                     "
		                + " AND tblterceros.estado NOT IN (2)              "
		                + " ORDER BY tblTerceros.ordenRuta                 "
		                + " OFFSET ?6 ROWS          "
		                + " FETCH NEXT ?7 ROWS ONLY ",
						nativeQuery = true)
				List<TercerosDTO2> listaLecturaRutaTxPorCliente(int idLocal, int xIdPeriodoAnterior, int xIdTipo, int idPeriodo, int idRuta, int xInicioRegistroTx, int xCuentaRegistroTx, int idOrden, int ordenRuta );
		
				
				
				@Query(value = "SELECT tblterceros.idCliente,           "
		                + "       tblterceros.idTercero,           "
		                + "       tblterceros.tipoIdTercero,       "
		                + "       tblterceros.digitoVerificacion,  "
		                + "       tblterceros.idTipoTercero,       "
		                + "       tblterceros.idPersona,           "
		                + "       tblterceros.idAutoRetenedor,     "
		                + "       tblterceros.idRegimen,           "
		                + "       tblterceros.nombreTercero,       "
		                + "       tblterceros.direccionTercero,    "
		                + "       tblterceros.idDptoCiudad,        "
		                + "       tblterceros.telefonoFijo,        "
		                + "       tblterceros.telefonoCelular,     "
		                + "       tblterceros.telefonoFax,         "
		                + "       tblterceros.email,               "
		                + "       tblterceros.idFormaPago,         "
		                + "       tblterceros.estado,              "
		                + "       tblterceros.idRuta,              "
		                + "       tblterceros.nombreEmpresa,       "
		                + "       tblterceros.cupoCredito,         "
		                + "       tblterceros.indicador,           "
		                + "       tblterceros.ciudadTercero,       "
		                + "       tblterceros.contactoTercero,     "
		                + "       tblterceros.idListaPrecio,       "
		                + "       tblterceros.idVendedor,          "
		                + "       tblterceros.idEstracto           "
						     + "FROM tblterceros                        "
			                 + "WHERE tblterceros.idLocal        =      "
			                 + "?1                            "
			                 + "AND   tblterceros.idCliente     =       "
			                 + "?2                      ",
						nativeQuery = true)
				List<TercerosDTO2> listaUnTerceroFachada(int idLocal, String idCliente);
				
				
				
				@Query(value = " SELECT  tblterceros.idCliente,               "
		                + "   tblterceros.idTercero,                     "
		                + "    tblterceros.tipoIdTercero,	         "
		                + "    tblterceros.digitoVerificacion,           "
		                + "    tblterceros.idTipoTercero, 	         "
		                + "    tblterceros.idPersona,                    "
		                + "    tblterceros.idAutoRetenedor,              "
		                + "    tblterceros.idRegimen, 	                 "
		                + "    tblterceros.nombreTercero, 	         "
		                + "    tblterceros.direccionTercero,             "
		                + "    tblterceros.idDptoCiudad, 	         "
		                + "    tblterceros.telefonoFijo, 	         "
		                + "    tblterceros.telefonoCelular,              "
		                + "    tblterceros.telefonoFax, 	         "
		                + "    tblterceros.email, 		         "
		                + "    tblterceros.idFormaPago, 	         "
		                + "    tblterceros.estado,                       "
		                + "    tblregimen.nombreRegimen, 	         "
		                + "    tbltipotercero.nombreTipoTercero,         "
		                + "    tblciudades.nombreDpto,    	         "
		                + "    tblterceros.idVendedor,     	         "
		                + "    tblciudades.nombreCiudad,                 "
		                + "  tbltercerosruta.nombreCiclo + '-' +         "
		                + "  tbltercerosruta.nombreRuta AS nombreRuta,   "
		                + "    tblterceroestracto.nombreEstracto,        "
		                + "    tblterceros.idEstracto,                   "
		                + "    tmpACT.lecturaActual AS lecturaMedidor,                     "
		                + "    tmpANT.lecturaAnterior AS lecturaMedidorAnterior,                   "
		                + "    tbltercerosruta.idRuta                    "
		                + "    ,tblterceros.promedio,                    "
		                + "    tblterceros.ordenRuta,                    "
		                + "    tblterceros.CC_Nit,                       "
		                + "    tmpCAU.idCausal AS idCausa,               "
		                + "    tmpEST.nombreEstado, 		  	 "
		                + "    tblterceros.numeroMedidor,                "
		                + "    tmpCAU.nombreCausa                        "
		                + "  FROM tblterceros                            "
		                + "  INNER JOIN tblregimen                       "
		                + "  ON tblregimen.idRegimen         =           "
		                + "             tblterceros.idRegimen            "
		                + " INNER JOIN tbltipotercero                    "
		                + " ON tbltipotercero.idTipoTercero =            "
		                + "          tblterceros.idTipoTercero           "
		                + " INNER JOIN tblciudades                       "
		                + " ON tblciudades.idCiudad         =            "
		                + "          tblterceros.idDptoCiudad            "
		                + " INNER JOIN tbltercerosruta                   "
		                + " ON tbltercerosruta.idRuta       =            "
		                + "                tblterceros.idRuta            "
		                + " AND tbltercerosruta.idLocal     =            "
		                + "               tblterceros.idLocal            "
		                + " INNER JOIN tblterceroestracto                "
		                + " ON tblterceroestracto.idEstracto =           "
		                + "             tblterceros.idEstracto           "
		                + " AND tblterceroestracto.idLocal=              "
		                + "                 tblterceros.idLocal          "
		                + " INNER JOIN (                                 "
		                + " SELECT tbltipocausanota.idCausa,             "
		                + " 	 tbltipocausanota.nombreCausa            "
		                + " 		      AS nombreEstado            "
		                + " FROM tbltipocausanota                        "
		                + " WHERE tbltipocausanota.idTipoTabla = 3 )     "
		                + " 			      AS tmpEST          "
		                + " ON   tmpEST.idCausa    =                     "
		                + " 	               tblterceros.estado        "
		                + " LEFT OUTER JOIN (                            "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,   "
		                + "  MAX(tbldctosordenesdetalle.lecturaMedidor)  "
		                + "            AS lecturaAnterior                "
		                + "  FROM   tbldctosordenes                      "
		                + "  INNER JOIN tbldctosordenesdetalle           "
		                + "  ON tbldctosordenes.IDLOCAL      =           "
		                + "        tbldctosordenesdetalle.IDLOCAL        "
		                + "  AND tbldctosordenes.IDTIPOORDEN =           "
		                + "     tbldctosordenesdetalle.IDTIPOORDEN       "
		                + "  AND tbldctosordenes.IDORDEN       =         "
		                + "         tbldctosordenesdetalle.IDORDEN       "
		                + "  WHERE  tbldctosordenes.IDLOCAL     =        "
		                + "       tbldctosordenesdetalle.idLocal         "
		                + "  AND  tbldctosordenes.IDLOCAL =              "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?2                         "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "  GROUP BY tbldctosordenesdetalle.idCliente)  "
		                + "                                   AS tmpANT  "
		                + " ON tmpANT.idCliente = tblterceros.idCliente  "
		                + " LEFT OUTER JOIN (                            "
		                + " SELECT  tbldctosordenesdetalle.idCliente,    "
		                + "  MAX(tbldctosordenesdetalle.lecturaMedidor)  "
		                + "                            AS lecturaActual  "
		                + "  FROM   tbldctosordenes                      "
		                + "  INNER JOIN tbldctosordenesdetalle           "
		                + "  ON tbldctosordenes.IDLOCAL      =           "
		                + "        tbldctosordenesdetalle.IDLOCAL        "
		                + "  AND tbldctosordenes.IDTIPOORDEN =           "
		                + "     tbldctosordenesdetalle.IDTIPOORDEN       "
		                + "  AND tbldctosordenes.IDORDEN       =         "
		                + "         tbldctosordenesdetalle.IDORDEN       "
		                + "  WHERE  tbldctosordenes.IDLOCAL     =        "
		                + "       tbldctosordenesdetalle.idLocal         "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "  GROUP BY tbldctosordenesdetalle.idCliente)  "
		                + "                                   AS tmpACT  "
		                + " ON tmpACT.idCliente = tblterceros.idCliente  "
		                + " LEFT OUTER JOIN (                            "
		                + " SELECT  tbldctosordenesdetalle.idCliente,    "
		                + " MAX(tbldctosordenesdetalle.idNovedadLectura) "
		                + "                              AS idCausal,     "
		                + "  MAX(tbltipocausanota.nombreCausa)           "
		                + "                           AS nombreCausa     "
		                + "  FROM   tbldctosordenes                      "
		                + "  INNER JOIN tbldctosordenesdetalle           "
		                + "  ON tbldctosordenes.IDLOCAL      =           "
		                + "        tbldctosordenesdetalle.IDLOCAL        "
		                + "  AND tbldctosordenes.IDTIPOORDEN =           "
		                + "     tbldctosordenesdetalle.IDTIPOORDEN       "
		                + "  AND tbldctosordenes.IDORDEN       =         "
		                + "         tbldctosordenesdetalle.IDORDEN       "
		                + "  INNER JOIN tbltipocausanota                 "
		                + "  ON tbltipocausanota.idCausa =               "
		                + "	 tbldctosordenesdetalle.idNovedadLectura "
		                + "  WHERE  tbldctosordenes.IDLOCAL   =          "
		                + "       tbldctosordenesdetalle.idLocal         "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "  AND tbltipocausanota.idTipoTabla = 2        "
		                + "  GROUP BY tbldctosordenesdetalle.idCliente)  "
		                + "                                    AS tmpCAU "
		                + " ON tmpCAU.idCliente = tblterceros.idCliente  "
		                + " WHERE tblterceros.idLocal        =           "
		                + "?1                                   "
		                + " AND tblterceros.estado NOT IN (2)            "
		                + " ORDER BY tbltercerosruta.idRuta,             "
		                + "          tblterceros.ordenRuta,              "
		                + "          tblterceros.idCliente ",
						nativeQuery = true)
				List<TercerosDTO2> listaLecturaRutaAll(int idLocal, int idPeriodoAnterior, int idPeriodoactual, int idTipo);
				
				
				
				@Query(value = " SELECT tblterceros.idCliente,                    "
		                + "  tblterceros.idTercero,                        "
		                + "  tblterceros.tipoIdTercero,	                   "
		                + "  tblterceros.digitoVerificacion,               "
		                + "  tblterceros.idTipoTercero, 	           "
		                + "  tblterceros.idPersona,                        "
		                + "  tblterceros.idAutoRetenedor,                  "
		                + "  tblterceros.idRegimen, 	                   "
		                + "  tblterceros.nombreTercero, 	           "
		                + "  tblterceros.direccionTercero,                 "
		                + "  tblterceros.idDptoCiudad, 	                   "
		                + "  tblterceros.telefonoFijo, 	                   "
		                + "  tblterceros.telefonoCelular,                  "
		                + "  tblterceros.telefonoFax, 	                   "
		                + "  tblterceros.email, 		           "
		                + "  tblterceros.idFormaPago, 	                   "
		                + "  tblterceros.estado,                           "
		                + "  tblregimen.nombreRegimen, 	                   "
		                + "  tbltipotercero.nombreTipoTercero,             "
		                + "  tblciudades.nombreCiudad, 	                   "
		                + "  tblciudades.nombreDpto,    	           "
		                + "  tblterceros.idVendedor,     	           "
		                + "  tblterceros.idRuta,                           "
		                + "  tbltercerosruta.nombreRuta,                   "
		                + "  tblterceroestracto.nombreEstracto,            "
		                + "  tblterceros.idEstracto,                       "
		                + "  tmpACT.lecturaActual,                         "
		                + "  tmpACT.cantidadPedida,                        "
		+ "  ( SELECT  TOP 1                               "
		+ "          tmpDET.lecturaMedidor                 "
		+ "             as lecturaAnterior                 "
		+ " FROM   tblDctos tmpDCT                         "
		+ " INNER JOIN tblDctosOrdenesDetalle              "
		+ "                            tmpDET              "
		+ " ON tmpDCT.IDLOCAL        =                     "
		+ "       tmpDET.IDLOCAL                           "
		+ " AND tmpDCT.IDTIPOORDEN   =                     "
		+ "   tmpDET.IDTIPOORDEN                           "
		+ " AND tmpDCT.IDORDEN       =                     "
		+ "       tmpDET.IDORDEN                           "
		+ " AND   tmpDCT.idLocal     =                     "
		+ "?1                                     "
		+ " AND   tmpDCT.idTipoOrden IN (9,29)             "
		+ " AND   tmpDCT.idPeriodo   =                     "
		+ "?2                           "
		+ " AND   tmpDET.IDTIPO      =                     "
		 +" ?3                                     "
		+ " AND   tmpDET.IDCLIENTE   =                     "
		+ "         tblterceros.idCliente                  "
		+ " ORDER BY tmpDCT.idCliente ,                    "
		+ "           tmpDCT.idOrden DESC )                "
		+ " 		    AS lecturaAnterior,            "

		                + " tblterceros.promedio,                         "
		                + "  tblterceros.ordenRuta,                        "
		                + "  tblterceros.CC_Nit,                           "
		                + "  tmpCAU.idCausal AS idCausa,                   "
		                + "  tmpEST.nombreEstado,                          "
		                + "  tmpCAU.nombreCausa,                           "
		                + "  tmpACT.fechaRegistroTx 			   "
		                + "  FROM tblterceros                              "
		                + "  INNER JOIN tblregimen                         "
		                + "  ON tblregimen.idRegimen         =             "
		                + "              tblterceros.idRegimen             "
		                + "  INNER JOIN tbltipotercero                     "
		                + "  ON tbltipotercero.idTipoTercero =             "
		                + "           tblterceros.idTipoTercero            "
		                + "  INNER JOIN tblciudades                        "
		                + "  ON tblciudades.idCiudad         =             "
		                + "           tblterceros.idDptoCiudad             "
		                + "  INNER JOIN tbltercerosruta                    "
		                + "  ON tbltercerosruta.idRuta       =             "
		                + "                 tblterceros.idRuta             "
		                + "  AND tbltercerosruta.idLocal     =             "
		                + "                tblterceros.idLocal             "
		                + "  INNER JOIN tblterceroestracto                 "
		                + "  ON tblterceroestracto.idEstracto =            "
		                + "              tblterceros.idEstracto            "
		                + "  AND tblterceroestracto.idLocal=               "
		                + "                 tblterceros.idLocal            "
		                + "  INNER JOIN (                                  "
		                + "  SELECT tbltipocausanota.idCausa,              "
		                + "  	 tbltipocausanota.nombreCausa              "
		                + "  		      AS nombreEstado              "
		                + "  FROM tbltipocausanota                         "
		                + "  WHERE tbltipocausanota.idTipoTabla = 3 )      "
		                + "  			  AS tmpEST                "
		                + "  ON   tmpEST.idCausa    =                      "
		                + "  	              tblterceros.estado           "
		                + "  LEFT OUTER JOIN (                             "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,     "
		                + "      tbldctosordenesdetalle.fechaRegistroTx,   "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor)   "
		                + "                             AS lecturaActual,  "
		                + "   MAX(tbldctosordenesdetalle.cantidadPedida)   "
		                + "                             AS cantidadPedida  "
		                + "   FROM   tbldctosordenes                       "
		                + "   INNER JOIN tbldctosordenesdetalle            "
		                + "   ON tbldctosordenes.IDLOCAL      =            "
		                + "         tbldctosordenesdetalle.IDLOCAL         "
		                + "   AND tbldctosordenes.IDTIPOORDEN =            "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN        "
		                + "   AND tbldctosordenes.IDORDEN       =          "
		                + "          tbldctosordenesdetalle.IDORDEN        "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =         "
		                + "        tbldctosordenesdetalle.idLocal          "
		                + "   AND  tbldctosordenes.IDLOCAL     =           "
		                + "?1                                     "
		                + "   AND tbldctosordenes.idPeriodo    =           "
		                + "?4                             "
		                + "   AND tbldctosordenesdetalle.IDTIPO =          "
		                + "?3                                      "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente,   "
		                + "       tbldctosordenesdetalle.fechaRegistroTx)  "
		                + "                                    AS tmpACT   "
		                + "  ON tmpACT.idCliente = tblterceros.idCliente   "
		                + "  LEFT OUTER JOIN (                             "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,     "
		                + "  MAX(tbldctosordenesdetalle.idNovedadLectura)  "
		                + "                               AS idCausal,     "
		                + "   MAX(tbltipocausanota.nombreCausa)            "
		                + "                            AS nombreCausa      "
		                + "   FROM   tbldctosordenes                       "
		                + "   INNER JOIN tbldctosordenesdetalle            "
		                + "   ON tbldctosordenes.IDLOCAL      =            "
		                + "         tbldctosordenesdetalle.IDLOCAL         "
		                + "   AND tbldctosordenes.IDTIPOORDEN =            "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN        "
		                + "   AND tbldctosordenes.IDORDEN       =          "
		                + "          tbldctosordenesdetalle.IDORDEN        "
		                + "   INNER JOIN tbltipocausanota                  "
		                + "   ON tbltipocausanota.idCausa =                "
		                + "      tbldctosordenesdetalle.idNovedadLectura   "
		                + "   WHERE  tbldctosordenes.IDLOCAL   =           "
		                + "        tbldctosordenesdetalle.idLocal          "
		                + "   AND  tbldctosordenes.IDLOCAL     =           "
		                + "?1                                     "
		                + "   AND tbldctosordenes.idPeriodo    =           "
		                + "?4                            "
		                + "   AND tbldctosordenesdetalle.IDTIPO =          "
		                + "?3                                      "
		                + "   AND tbltipocausanota.idTipoTabla  = 2        "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente)   "
		                + "                                     AS tmpCAU  "
		                + "  ON tmpCAU.idCliente = tblterceros.idCliente   "
		                + "  WHERE tblterceros.idLocal         =           "
		                + "?1                                     "
		                + " AND tblterceros.idCliente IN (?5) "
		                + "  AND tblterceros.estado NOT IN (2)             "
		                + " ORDER BY tblTerceros.ordenRuta              ",
						nativeQuery = true)
				List<TercerosDTO2> listaLecturaRutaTxPorCliente(int idLocal, int xIdPeriodoAnterior, int xIdTipo, int idPeriodo, List<String> idCliente);
				
				
				
				
				@Query(value = "SELECT  tblterceros.idCliente,                "
		                + "   tblterceros.nombreTercero, 	         "
		                + "   tblterceros.estado,                        "
		                + "   tblterceros.idRuta,                        "
		                + "   tblciudades.nombreCiudad,                  "
		                + "   tbltercerosruta.nombreCiclo + '/' +        "
		                + "            tbltercerosruta.nombreRuta        "
		                + " 		                  AS nombreRuta, "
		                + "   tblterceros.idEstracto,                    "
		                + "   tblterceroestracto.nombreEstracto,         "
		                + "   ISNULL(tmpACT.lecturaActual,0)             "
		                + "                            AS lecturaActual, "
		                + "   ISNULL(tmpANT.lecturaAnterior,0)           "
		                + "                          AS lecturaAnterior, "
		                + "   (ISNULL(tmpACT.lecturaActual,0) -          "
		                + "          ISNULL(tmpANT.lecturaAnterior,0))   "
		                + "                                 AS consumo,  "
		                + "   tblterceros.promedio,                      "
		                + "   (tblterceros.promedio *(1+("
		                + "?1/100))) AS valorExceso,   "
		                + "     tblterceros.ordenRuta,                   "
		                + "   tmpCAU.idCausal AS idCausa,                "
		                + "   tmpEST.nombreEstado, 		  	 "
		                + " tbltipocausanota.nombreCausa                  "
		                + "   FROM tblterceros                           "
		                + "   INNER JOIN tblregimen                      "
		                + "   ON tblregimen.idRegimen         =          "
		                + "              tblterceros.idRegimen           "
		                + "  INNER JOIN tbltipotercero                   "
		                + "  ON tbltipotercero.idTipoTercero =           "
		                + "           tblterceros.idTipoTercero          "
		                + "  INNER JOIN tblciudades                      "
		                + "  ON tblciudades.idCiudad         =           "
		                + "           tblterceros.idDptoCiudad           "
		                + "  INNER JOIN tbltercerosruta                  "
		                + "  ON tbltercerosruta.idRuta       =           "
		                + "                 tblterceros.idRuta           "
		                + "  AND tbltercerosruta.idLocal     =           "
		                + "                tblterceros.idLocal           "
		                + "  INNER JOIN tblterceroestracto               "
		                + "  ON tblterceroestracto.idEstracto =          "
		                + "              tblterceros.idEstracto          "
		                + "  AND tblterceroestracto.idLocal=             "
		                + "                  tblterceros.idLocal         "
		                + "  INNER JOIN (                                "
		                + "  SELECT tbltipocausanota.idCausa,            "
		                + "  	 tbltipocausanota.nombreCausa            "
		                + "                           AS nombreEstado    "
		                + "  FROM tbltipocausanota                       "
		                + "  WHERE tbltipocausanota.idTipoTabla = 3 )    "
		                + "  			      AS tmpEST          "
		                + "  ON tmpEST.idCausa = tblterceros.estado      "
		                + "  LEFT OUTER JOIN (                           "
		                + "   SELECT tbldctosordenesdetalle.IDLOCAL,     "
		                + "          tbldctosordenesdetalle.idCliente,   "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "             AS lecturaAnterior,              "
		                + "   MAX(tbldctosordenes.promedio) AS promedio  "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "               tbldctosordenesdetalle.IDLOCAL "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "          tbldctosordenesdetalle.IDTIPOORDEN  "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "              tbldctosordenesdetalle.IDORDEN  "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL =              "
		                + "?2                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                         "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?5                                    "
		                + "   GROUP BY tbldctosordenesdetalle.IDLOCAL,   "
		                + "            tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpANT "
		                + "  ON tmpANT.idCliente = tblterceros.idCliente "
		                + "  AND tmpANT.idLocal = tblterceros.idLocal    "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT tbldctosordenesdetalle.IDLOCAL,      "
		                + "         tbldctosordenesdetalle.idCliente,    "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "                             AS lecturaActual "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?2                                    "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?4                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?5                                    "
		                + "   GROUP BY tbldctosordenesdetalle.IDLOCAL,   "
		                + "            tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpACT "
		                + "  ON tmpACT.idCliente = tblterceros.idCliente "
		                + "  AND tmpACT.idLocal = tblterceros.idLocal    "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT tbldctosordenesdetalle.IDLOCAL,      "
		                + "         tbldctosordenesdetalle.idCliente,    "
		                + "  MAX(tbldctosordenesdetalle.idNovedadLectura)"
		                + "                               AS idCausal    "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL   =         "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?2                                    "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?4                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?5                                    "
		                + "   GROUP BY tbldctosordenesdetalle.IDLOCAL,   "
		                + "            tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpCAU "
		                + "  ON tmpCAU.idCliente = tblterceros.idCliente "
		                + "  AND tmpCAU.idLocal = tblterceros.idLocal    "
		                + "INNER JOIN tbltipocausanota                   "
		                + "ON tmpCAU.idCausal = tbltipocausanota.idCausa "
		                + " WHERE tblterceros.idLocal        =           "
		                + "?2                                    "
		                + " AND tbltercerosruta.idRuta       =           "
		                + "?6                                    "
		                + "  AND tblterceros.estado NOT IN (2)	         "
		                + "  AND (ISNULL(tmpACT.lecturaActual,0) -       "
		                + "       ISNULL(tmpANT.lecturaAnterior,0))>=    "
		                + "   (tblterceros.promedio *(1+(                "
		                + " ?1/100)))                   "
		                + "  AND    tblterceros.promedio  !=0            "
		                + "  AND (ISNULL(tmpACT.lecturaActual,0) -       "
		                + "       ISNULL(tmpANT.lecturaAnterior,0)) >    "
		                + "?7                               "
		                + "AND  tbltipocausanota.idTipoTabla = 2          "
		                + "  ORDER BY tbltercerosruta.idRuta,            "
		                + "           tblterceros.ordenRuta,             "
		                + "           tblterceros.idCliente",
						nativeQuery = true)
				List<TercerosDTO2> listaCriticaPorcentajeExceso(Double xPorcentajeExceso, int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta, Double xConsumoBase);
				
				
				
				@Query(value = "SELECT  tblterceros.idCliente,                "
		                + "   tblterceros.nombreTercero, 	         "
		                + "   tblterceros.estado,                        "
		                + "   tblterceros.idRuta,                        "
		                + "   tblciudades.nombreCiudad,                  "
		                + "   tbltercerosruta.nombreCiclo + '/' +        "
		                + "            tbltercerosruta.nombreRuta        "
		                + " 		                  AS nombreRuta, "
		                + "   tblterceros.idEstracto,                    "
		                + "   tblterceroestracto.nombreEstracto,         "
		                + "   ISNULL(tmpACT.lecturaActual,0)             "
		                + "                            AS lecturaActual, "
		                + "   ISNULL(tmpANT.lecturaAnterior,0)           "
		                + "                          AS lecturaAnterior, "
		                + "   (ISNULL(tmpACT.lecturaActual,0) -          "
		                + "          ISNULL(tmpANT.lecturaAnterior,0))   "
		                + "                                 AS consumo,  "
		                + "   tblterceros.promedio,                      "
		                + "   (tblterceros.promedio *(1+("
		                + "?1/100))) AS valorExceso,   "
		                + "     tblterceros.ordenRuta,                   "
		                + "   tmpCAU.idCausal AS idCausa,                "
		                + "   tmpEST.nombreEstado, 		  	 "
		                + " tbltipocausanota.nombreCausa                  "
		                + "   FROM tblterceros                           "
		                + "   INNER JOIN tblregimen                      "
		                + "   ON tblregimen.idRegimen         =          "
		                + "              tblterceros.idRegimen           "
		                + "  INNER JOIN tbltipotercero                   "
		                + "  ON tbltipotercero.idTipoTercero =           "
		                + "           tblterceros.idTipoTercero          "
		                + "  INNER JOIN tblciudades                      "
		                + "  ON tblciudades.idCiudad         =           "
		                + "           tblterceros.idDptoCiudad           "
		                + "  INNER JOIN tbltercerosruta                  "
		                + "  ON tbltercerosruta.idRuta       =           "
		                + "                 tblterceros.idRuta           "
		                + "  AND tbltercerosruta.idLocal     =           "
		                + "                tblterceros.idLocal           "
		                + "  INNER JOIN tblterceroestracto               "
		                + "  ON tblterceroestracto.idEstracto =          "
		                + "              tblterceros.idEstracto          "
		                + "  AND tblterceroestracto.idLocal=             "
		                + "                  tblterceros.idLocal         "
		                + "  INNER JOIN (                                "
		                + "  SELECT tbltipocausanota.idCausa,            "
		                + "  	 tbltipocausanota.nombreCausa            "
		                + "                           AS nombreEstado    "
		                + "  FROM tbltipocausanota                       "
		                + "  WHERE tbltipocausanota.idTipoTabla = 3 )    "
		                + "  			      AS tmpEST          "
		                + "  ON tmpEST.idCausa = tblterceros.estado      "
		                + "  LEFT OUTER JOIN (                           "
		                + "   SELECT tbldctosordenesdetalle.IDLOCAL,     "
		                + "          tbldctosordenesdetalle.idCliente,   "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "             AS lecturaAnterior,              "
		                + "   MAX(tbldctosordenes.promedio) AS promedio  "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "               tbldctosordenesdetalle.IDLOCAL "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "          tbldctosordenesdetalle.IDTIPOORDEN  "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "              tbldctosordenesdetalle.IDORDEN  "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL =              "
		                + "?2                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                         "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?5                                    "
		                + "   GROUP BY tbldctosordenesdetalle.IDLOCAL,   "
		                + "            tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpANT "
		                + "  ON tmpANT.idCliente = tblterceros.idCliente "
		                + "  AND tmpANT.idLocal = tblterceros.idLocal    "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT tbldctosordenesdetalle.IDLOCAL,      "
		                + "         tbldctosordenesdetalle.idCliente,    "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "                             AS lecturaActual "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?2                                    "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?4                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?5                                    "
		                + "   GROUP BY tbldctosordenesdetalle.IDLOCAL,   "
		                + "            tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpACT "
		                + "  ON tmpACT.idCliente = tblterceros.idCliente "
		                + "  AND tmpACT.idLocal = tblterceros.idLocal    "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT tbldctosordenesdetalle.IDLOCAL,      "
		                + "         tbldctosordenesdetalle.idCliente,    "
		                + "  MAX(tbldctosordenesdetalle.idNovedadLectura)"
		                + "                               AS idCausal    "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL   =         "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?2                                    "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?4                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?5                                    "
		                + "   GROUP BY tbldctosordenesdetalle.IDLOCAL,   "
		                + "            tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpCAU "
		                + "  ON tmpCAU.idCliente = tblterceros.idCliente "
		                + "  AND tmpCAU.idLocal = tblterceros.idLocal    "
		                + "INNER JOIN tbltipocausanota                   "
		                + "ON tmpCAU.idCausal = tbltipocausanota.idCausa "
		                
						+ " WHERE tblterceros.idLocal        =           "
						+ "?2                                   "
						+ " AND tbltercerosruta.idRuta       =           "
						+ "?6                                    "
						+ "  AND tblterceros.estado NOT IN (2)	         "
						+ "  AND (tmpACT.lecturaActual -                 "
						+ "   tmpANT.lecturaAnterior)<                   "
						+ "   (tblterceros.promedio *(                   "
						+ "?1/100))                    "
						+ "  AND    tblterceros.promedio  !=0            "
						+ "  AND    (tmpACT.lecturaActual -              "
						+ "                 tmpANT.lecturaAnterior) <   "
						+ "?7                               "
						+ "AND  tbltipocausanota.idTipoTabla = 2          "
						+ "  ORDER BY tbltercerosruta.idRuta,            "
						+ "           tblterceros.ordenRuta,             "
						+ "           tblterceros.idCliente"
		               ,
						nativeQuery = true)
				List<TercerosDTO2> listaCriticaPorcExcesoDefecto(Double xPorcentajeExceso, int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta, Double xConsumoBase);
				
				
				
				@Query(value = "SELECT  tblterceros.idCliente,                "
		                + "   tblterceros.nombreTercero, 	         "
		                + "   tblterceros.estado,                        "
		                + "   tblterceros.idRuta,                        "
		                + "   tblciudades.nombreCiudad,                  "
		                + "   tbltercerosruta.nombreCiclo + '/' +        "
		                + "            tbltercerosruta.nombreRuta        "
		                + " 		                  AS nombreRuta, "
		                + "    tblterceros.idEstracto,                   "
		                + "   tblterceroestracto.nombreEstracto,         "
		                + "   tmpACT.lecturaActual,                      "
		                + "   tmpANT.lecturaAnterior,                    "
		                + "   (tmpACT.lecturaActual -                    "
		                + "   tmpANT.lecturaAnterior) AS consumo,        "
		                + "   tblterceros.promedio,                      "
		                + "     tblterceros.ordenRuta,                   "
		                + "   tmpCAU.idCausal AS idCausa,                "
		                + "   tmpEST.nombreEstado, 		  	 "
		                + " tbltipocausanota.nombreCausa                  "
		                + "   FROM tblterceros                           "
		                + "   INNER JOIN tblregimen                      "
		                + "   ON tblregimen.idRegimen         =          "
		                + "              tblterceros.idRegimen           "
		                + "  INNER JOIN tbltipotercero                   "
		                + "  ON tbltipotercero.idTipoTercero =           "
		                + "           tblterceros.idTipoTercero          "
		                + "  INNER JOIN tblciudades                      "
		                + "  ON tblciudades.idCiudad         =           "
		                + "           tblterceros.idDptoCiudad           "
		                + "  INNER JOIN tbltercerosruta                  "
		                + "  ON tbltercerosruta.idRuta       =           "
		                + "                 tblterceros.idRuta           "
		                + "  AND tbltercerosruta.idLocal     =           "
		                + "                tblterceros.idLocal           "
		                + "  INNER JOIN tblterceroestracto               "
		                + "  ON tblterceroestracto.idEstracto =          "
		                + "              tblterceros.idEstracto          "
		                + "  AND tblterceroestracto.idLocal=             "
		                + "                  tblterceros.idLocal         "
		                + "  INNER JOIN (                                "
		                + "  SELECT tbltipocausanota.idCausa,            "
		                + "  	 tbltipocausanota.nombreCausa            "
		                + "                           AS nombreEstado    "
		                + "  FROM tbltipocausanota                       "
		                + "  WHERE tbltipocausanota.idTipoTabla = 3 )    "
		                + "  			      AS tmpEST          "
		                + "  ON tmpEST.idCausa = tblterceros.estado      "
		                + "  LEFT OUTER JOIN (                           "
		                + "   SELECT  tbldctosordenesdetalle.idCliente,  "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "             AS lecturaAnterior,              "
		                + "   MAX(tbldctosordenes.promedio) AS promedio  "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "               tbldctosordenesdetalle.IDLOCAL "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "          tbldctosordenesdetalle.IDTIPOORDEN  "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "              tbldctosordenesdetalle.IDORDEN  "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL =              "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?2                        "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpANT "
		                + "  ON tmpANT.idCliente = tblterceros.idCliente "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,   "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "                             AS lecturaActual "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpACT "
		                + "  ON tmpACT.idCliente = tblterceros.idCliente "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,   "
		                + "  MAX(tbldctosordenesdetalle.idNovedadLectura)"
		                + "                               AS idCausal    "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL   =         "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpCAU "
		                + "  ON tmpCAU.idCliente = tblterceros.idCliente "
		                
		                + "INNER JOIN tbltipocausanota                   "
		                + "ON tmpCAU.idCausal = tbltipocausanota.idCausa "
		                + " WHERE tblterceros.idLocal        =           "
		                + "?1                                   "
		                + " AND tbltercerosruta.idRuta       =           "
		                + "?5                                    "
		                + "  AND tblterceros.estado NOT IN (2)	         "
		                + "  AND    (tmpACT.lecturaActual -              "
		                + "                 tmpANT.lecturaAnterior) = 0  "
		                + "AND  tbltipocausanota.idTipoTabla = 2          "
		                + "  ORDER BY tbltercerosruta.idRuta,            "
		                + "           tblterceros.ordenRuta,             "
		                + "           tblterceros.idCliente"
		               ,
						nativeQuery = true)
				List<TercerosDTO2> listaCriticaConsumoCero(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta);
				
				
				
				
				@Query(value = "SELECT  tblterceros.idCliente,                "
		                + "   tblterceros.nombreTercero, 	         "
		                + "   tblterceros.estado,                        "
		                + "   tblterceros.idRuta,                        "
		                + "   tblciudades.nombreCiudad,                  "
		                + "   tbltercerosruta.nombreCiclo + '/' +        "
		                + "            tbltercerosruta.nombreRuta        "
		                + " 		                  AS nombreRuta, "
		                + "    tblterceros.idEstracto,                   "
		                + "   tblterceroestracto.nombreEstracto,         "
		                + "   tmpACT.lecturaActual,                      "
		                + "   tmpANT.lecturaAnterior,                    "
		                + "   (tmpACT.lecturaActual -                    "
		                + "   tmpANT.lecturaAnterior) AS consumo,        "
		                + "   tblterceros.promedio,                      "
		                + "     tblterceros.ordenRuta,                   "
		                + "   tmpCAU.idCausal AS idCausa,                "
		                + "   tmpEST.nombreEstado, 		  	 "
		                + " tbltipocausanota.nombreCausa                  "
		                + "   FROM tblterceros                           "
		                + "   INNER JOIN tblregimen                      "
		                + "   ON tblregimen.idRegimen         =          "
		                + "              tblterceros.idRegimen           "
		                + "  INNER JOIN tbltipotercero                   "
		                + "  ON tbltipotercero.idTipoTercero =           "
		                + "           tblterceros.idTipoTercero          "
		                + "  INNER JOIN tblciudades                      "
		                + "  ON tblciudades.idCiudad         =           "
		                + "           tblterceros.idDptoCiudad           "
		                + "  INNER JOIN tbltercerosruta                  "
		                + "  ON tbltercerosruta.idRuta       =           "
		                + "                 tblterceros.idRuta           "
		                + "  AND tbltercerosruta.idLocal     =           "
		                + "                tblterceros.idLocal           "
		                + "  INNER JOIN tblterceroestracto               "
		                + "  ON tblterceroestracto.idEstracto =          "
		                + "              tblterceros.idEstracto          "
		                + "  AND tblterceroestracto.idLocal=             "
		                + "                  tblterceros.idLocal         "
		                + "  INNER JOIN (                                "
		                + "  SELECT tbltipocausanota.idCausa,            "
		                + "  	 tbltipocausanota.nombreCausa            "
		                + "                           AS nombreEstado    "
		                + "  FROM tbltipocausanota                       "
		                + "  WHERE tbltipocausanota.idTipoTabla = 3 )    "
		                + "  			      AS tmpEST          "
		                + "  ON tmpEST.idCausa = tblterceros.estado      "
		                + "  LEFT OUTER JOIN (                           "
		                + "   SELECT  tbldctosordenesdetalle.idCliente,  "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "             AS lecturaAnterior,              "
		                + "   MAX(tbldctosordenes.promedio) AS promedio  "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "               tbldctosordenesdetalle.IDLOCAL "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "          tbldctosordenesdetalle.IDTIPOORDEN  "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "              tbldctosordenesdetalle.IDORDEN  "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL =              "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?2                        "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpANT "
		                + "  ON tmpANT.idCliente = tblterceros.idCliente "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,   "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "                             AS lecturaActual "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpACT "
		                + "  ON tmpACT.idCliente = tblterceros.idCliente "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,   "
		                + "  MAX(tbldctosordenesdetalle.idNovedadLectura)"
		                + "                               AS idCausal    "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL   =         "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpCAU "
		                + "  ON tmpCAU.idCliente = tblterceros.idCliente "
		                
		                + "INNER JOIN tbltipocausanota                   "
		                + "ON tmpCAU.idCausal = tbltipocausanota.idCausa "
		                + " WHERE tblterceros.idLocal        =           "
		                + "?1                                   "
		                + " AND tbltercerosruta.idRuta       =           "
		                + "?5                                    "
		                + "  AND tblterceros.estado NOT IN (2)	         "
		                + "  AND (ISNULL(tmpACT.lecturaActual,0) -       "
		                + "       ISNULL(tmpANT.lecturaAnterior,0)) < 0  "
		                + "  ORDER BY tbltercerosruta.idRuta,            "
		                + "           tblterceros.ordenRuta,             "
		                + "           tblterceros.idCliente"
		               ,
						nativeQuery = true)
				List<TercerosDTO2> listaCriticaConsumoNegativo(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta);
				
				
				
				@Query(value = "SELECT  tblterceros.idCliente,                "
		                + "   tblterceros.nombreTercero, 	         "
		                + "   tblterceros.estado,                        "
		                + "   tblterceros.idRuta,                        "
		                + "   tblciudades.nombreCiudad,                  "
		                + "   tbltercerosruta.nombreCiclo + '/' +        "
		                + "            tbltercerosruta.nombreRuta        "
		                + " 		                  AS nombreRuta, "
		                + "    tblterceros.idEstracto,                   "
		                + "   tblterceroestracto.nombreEstracto,         "
		                + "   tmpACT.lecturaActual,                      "
		                + "   tmpANT.lecturaAnterior,                    "
		                + "   (tmpACT.lecturaActual -                    "
		                + "   tmpANT.lecturaAnterior) AS consumo,        "
		                + "   tblterceros.promedio,                      "
		                + "     tblterceros.ordenRuta,                   "
		                + "   tmpCAU.idCausal AS idCausa,                "
		                + "   tmpEST.nombreEstado, 		  	 "
		                + " tbltipocausanota.nombreCausa                  "
		                + "   FROM tblterceros                           "
		                + "   INNER JOIN tblregimen                      "
		                + "   ON tblregimen.idRegimen         =          "
		                + "              tblterceros.idRegimen           "
		                + "  INNER JOIN tbltipotercero                   "
		                + "  ON tbltipotercero.idTipoTercero =           "
		                + "           tblterceros.idTipoTercero          "
		                + "  INNER JOIN tblciudades                      "
		                + "  ON tblciudades.idCiudad         =           "
		                + "           tblterceros.idDptoCiudad           "
		                + "  INNER JOIN tbltercerosruta                  "
		                + "  ON tbltercerosruta.idRuta       =           "
		                + "                 tblterceros.idRuta           "
		                + "  AND tbltercerosruta.idLocal     =           "
		                + "                tblterceros.idLocal           "
		                + "  INNER JOIN tblterceroestracto               "
		                + "  ON tblterceroestracto.idEstracto =          "
		                + "              tblterceros.idEstracto          "
		                + "  AND tblterceroestracto.idLocal=             "
		                + "                  tblterceros.idLocal         "
		                + "  INNER JOIN (                                "
		                + "  SELECT tbltipocausanota.idCausa,            "
		                + "  	 tbltipocausanota.nombreCausa            "
		                + "                           AS nombreEstado    "
		                + "  FROM tbltipocausanota                       "
		                + "  WHERE tbltipocausanota.idTipoTabla = 3 )    "
		                + "  			      AS tmpEST          "
		                + "  ON tmpEST.idCausa = tblterceros.estado      "
		                + "  LEFT OUTER JOIN (                           "
		                + "   SELECT  tbldctosordenesdetalle.idCliente,  "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "             AS lecturaAnterior,              "
		                + "   MAX(tbldctosordenes.promedio) AS promedio  "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "               tbldctosordenesdetalle.IDLOCAL "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "          tbldctosordenesdetalle.IDTIPOORDEN  "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "              tbldctosordenesdetalle.IDORDEN  "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL =              "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?2                        "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpANT "
		                + "  ON tmpANT.idCliente = tblterceros.idCliente "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,   "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "                             AS lecturaActual "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpACT "
		                + "  ON tmpACT.idCliente = tblterceros.idCliente "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,   "
		                + "  MAX(tbldctosordenesdetalle.idNovedadLectura)"
		                + "                               AS idCausal    "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL   =         "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpCAU "
		                + "  ON tmpCAU.idCliente = tblterceros.idCliente "
		                
		                + "INNER JOIN tbltipocausanota                   "
		                + "ON tmpCAU.idCausal = tbltipocausanota.idCausa "
		                + " WHERE tblterceros.idLocal        =           "
		                + "?1                                   "
		                + " AND tbltercerosruta.idRuta       =           "
		                + "?5                                    "
		                + "  AND tblterceros.estado NOT IN (2)	         "
		                + "  AND tblterceros.promedio      = 0           "
		                + "  ORDER BY tbltercerosruta.idRuta,            "
		                + "           tblterceros.ordenRuta,             "
		                + "           tblterceros.idCliente"
		               ,
						nativeQuery = true)
				List<TercerosDTO2> listaCriticaPromedioCero(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta);
				
				
				
				@Query(value = "SELECT  tblterceros.idCliente,                "
		                + "   tblterceros.nombreTercero, 	         "
		                + "   tblterceros.estado,                        "
		                + "   tblterceros.idRuta,                        "
		                + "   tblciudades.nombreCiudad,                  "
		                + "   tbltercerosruta.nombreCiclo + '/' +        "
		                + "            tbltercerosruta.nombreRuta        "
		                + " 		                  AS nombreRuta, "
		                + "    tblterceros.idEstracto,                   "
		                + "   tblterceroestracto.nombreEstracto,         "
		                + "   tmpACT.lecturaActual,                      "
		                + "   tmpANT.lecturaAnterior,                    "
		                + "   (tmpACT.lecturaActual -                    "
		                + "   tmpANT.lecturaAnterior) AS consumo,        "
		                + "   tblterceros.promedio,                      "
		                + "     tblterceros.ordenRuta,                   "
		                + "   tmpCAU.idCausal AS idCausa,                "
		                + "   tmpEST.nombreEstado, 		  	 "
		                + " tbltipocausanota.nombreCausa                  "
		                + "   FROM tblterceros                           "
		                + "   INNER JOIN tblregimen                      "
		                + "   ON tblregimen.idRegimen         =          "
		                + "              tblterceros.idRegimen           "
		                + "  INNER JOIN tbltipotercero                   "
		                + "  ON tbltipotercero.idTipoTercero =           "
		                + "           tblterceros.idTipoTercero          "
		                + "  INNER JOIN tblciudades                      "
		                + "  ON tblciudades.idCiudad         =           "
		                + "           tblterceros.idDptoCiudad           "
		                + "  INNER JOIN tbltercerosruta                  "
		                + "  ON tbltercerosruta.idRuta       =           "
		                + "                 tblterceros.idRuta           "
		                + "  AND tbltercerosruta.idLocal     =           "
		                + "                tblterceros.idLocal           "
		                + "  INNER JOIN tblterceroestracto               "
		                + "  ON tblterceroestracto.idEstracto =          "
		                + "              tblterceros.idEstracto          "
		                + "  AND tblterceroestracto.idLocal=             "
		                + "                  tblterceros.idLocal         "
		                + "  INNER JOIN (                                "
		                + "  SELECT tbltipocausanota.idCausa,            "
		                + "  	 tbltipocausanota.nombreCausa            "
		                + "                           AS nombreEstado    "
		                + "  FROM tbltipocausanota                       "
		                + "  WHERE tbltipocausanota.idTipoTabla = 3 )    "
		                + "  			      AS tmpEST          "
		                + "  ON tmpEST.idCausa = tblterceros.estado      "
		                + "  LEFT OUTER JOIN (                           "
		                + "   SELECT  tbldctosordenesdetalle.idCliente,  "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "             AS lecturaAnterior,              "
		                + "   MAX(tbldctosordenes.promedio) AS promedio  "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "               tbldctosordenesdetalle.IDLOCAL "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "          tbldctosordenesdetalle.IDTIPOORDEN  "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "              tbldctosordenesdetalle.IDORDEN  "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL =              "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?2                        "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpANT "
		                + "  ON tmpANT.idCliente = tblterceros.idCliente "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,   "
		                + "   MAX(tbldctosordenesdetalle.lecturaMedidor) "
		                + "                             AS lecturaActual "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL     =       "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpACT "
		                + "  ON tmpACT.idCliente = tblterceros.idCliente "
		                + "  LEFT OUTER JOIN (                           "
		                + "  SELECT  tbldctosordenesdetalle.idCliente,   "
		                + "  MAX(tbldctosordenesdetalle.idNovedadLectura)"
		                + "                               AS idCausal    "
		                + "   FROM   tbldctosordenes                     "
		                + "   INNER JOIN tbldctosordenesdetalle          "
		                + "   ON tbldctosordenes.IDLOCAL      =          "
		                + "         tbldctosordenesdetalle.IDLOCAL       "
		                + "   AND tbldctosordenes.IDTIPOORDEN =          "
		                + "      tbldctosordenesdetalle.IDTIPOORDEN      "
		                + "   AND tbldctosordenes.IDORDEN       =        "
		                + "          tbldctosordenesdetalle.IDORDEN      "
		                + "   WHERE  tbldctosordenes.IDLOCAL   =         "
		                + "        tbldctosordenesdetalle.idLocal        "
		                + "  AND  tbldctosordenes.IDLOCAL     =          "
		                + "?1                                   "
		                + "  AND tbldctosordenes.idPeriodo    =          "
		                + "?3                           "
		                + "  AND tbldctosordenesdetalle.IDTIPO =         "
		                + "?4                                    "
		                + "   GROUP BY tbldctosordenesdetalle.idCliente) "
		                + "                                    AS tmpCAU "
		                + "  ON tmpCAU.idCliente = tblterceros.idCliente "
		                
		                + "INNER JOIN tbltipocausanota                   "
		                + "ON tmpCAU.idCausal = tbltipocausanota.idCausa "
		                + " WHERE tblterceros.idLocal        =           "
		                + "?1                                   "
		                + " AND tbltercerosruta.idRuta       =           "
		                + "?5                                    "
		                + " AND tblterceros.estado NOT IN (2)	         "
		                + " AND tmpCAU.idCausal IN (99)                  "
		                + " ORDER BY tbltercerosruta.idRuta,             "
		                + "           tblterceros.ordenRuta,             "
		                + "           tblterceros.idCliente"
		               ,
						nativeQuery = true)
				List<TercerosDTO2> listaCriticaInconsistencia(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta);
				
				
				
				@Query(value = "		  SELECT  tblterceros.idCliente,                                                   "
				        + "           tblterceros.nombreTercero, 	                                               "
				        + "           tblterceros.estado,                                                          "
				        + "           tblterceros.idRuta,                                                          "
						+ "		   tblterceros.idLocal,                                                            "
				        + "           tblciudades.nombreCiudad,                                                    "
				        + "           tbltercerosruta.nombreCiclo + '/' +                                          "
				        + "           tbltercerosruta.nombreRuta  AS nombreRuta,                                   "
				        + "           tblterceros.idEstracto,                                                      "
				        + "           tblterceroestracto.nombreEstracto,                                           "
				        + "           tmpACT.lecturaActual,                                                        "
				        + "           tmpANT.lecturaAnterior,                                                      "
				        + "           (tmpACT.lecturaActual -                                                      "
				        + "           tmpANT.lecturaAnterior) AS consumo,                                          "
				        + "           tblterceros.promedio,                                                        "
				        + "           tblterceros.ordenRuta,                                                       "
				        + "           tmpCAU.idCausal AS idCausa,                                                  "
				        + "           tmpEST.nombreEstado, 		  	                                               "
				        + "           tbltipocausanota.nombreCausa                                                 "
				        + "           FROM tblterceros   						                                   "
				        + "           INNER JOIN tblregimen                                                        "
				        + "           ON tblregimen.idRegimen  =  tblterceros.idRegimen 						   "
				        + "          INNER JOIN tbltipotercero                                                     "
				        + "          ON tbltipotercero.idTipoTercero =  tblterceros.idTipoTercero   			   "			  
				        + "          INNER JOIN tblciudades                                                        "
				        + "          ON tblciudades.idCiudad         =   tblterceros.idDptoCiudad 				   "		  
				        + "          INNER JOIN tbltercerosruta                                                    "
				        + "          ON tbltercerosruta.idRuta       = tblterceros.idRuta                          "
				        + "          AND tbltercerosruta.idLocal     =  tblterceros.idLocal  					   "	  
				        + "          INNER JOIN tblterceroestracto                                                 "
				        + "          ON tblterceroestracto.idEstracto =  tblterceros.idEstracto                    "
				        + "          AND tblterceroestracto.idLocal   =  tblterceros.idLocal      				   "		  
				        + "          INNER JOIN (                                                                  "
				        + "          SELECT tbltipocausanota.idCausa,                                              "
				        + "          	     tbltipocausanota.nombreCausa  AS nombreEstado                         "
				        + "          FROM tbltipocausanota                                                         "
				        + "          WHERE tbltipocausanota.idTipoTabla = 3 )                                      "
				        + "          			                     AS tmpEST                                     "
				        + "          ON tmpEST.idCausa = tblterceros.estado                                        "
				        + "          LEFT JOIN (                                                                   "
				        + "           SELECT  tbldctosordenesdetalle.idCliente,                                    "
				        + "           MAX(tbldctosordenesdetalle.lecturaMedidor) AS lecturaAnterior,               "
				        + "           MAX(tbldctosordenes.promedio) AS promedio                                    "
				        + "           FROM   tbldctosordenes                                                       "
				        + "           INNER JOIN tbldctosordenesdetalle                                            "
				        + "           ON tbldctosordenes.IDLOCAL      =   tbldctosordenesdetalle.IDLOCAL           "
				        + "           AND tbldctosordenes.IDTIPOORDEN =   tbldctosordenesdetalle.IDTIPOORDEN       "
				        + "           AND tbldctosordenes.IDORDEN       = tbldctosordenesdetalle.IDORDEN           "
				        + "           WHERE  tbldctosordenes.IDLOCAL     =   tbldctosordenesdetalle.idLocal        "
				        + "           AND  tbldctosordenes.IDLOCAL =                                               "
				        + "          ?1                                                                           "
				        + "          AND tbldctosordenes.idPeriodo    =                                            "
				        + "          ?2                                                                        "
				        + "          AND tbldctosordenesdetalle.IDTIPO =                                           "
				        + "          ?4                                                                             "
				        + "           GROUP BY tbldctosordenesdetalle.idCliente)                                   "
				        + "                                            AS tmpANT                                   "
				        + "          ON tmpANT.idCliente = tblterceros.idCliente                                   "
				        + "          LEFT  JOIN (                                                                  "
				        + "          SELECT  tbldctosordenesdetalle.idCliente,                                     "
				        + "           MAX(tbldctosordenesdetalle.lecturaMedidor)                                   "
				        + "                                     AS lecturaActual                                   "
				        + "           FROM   tbldctosordenes                                                       "
				        + "           INNER JOIN tbldctosordenesdetalle                                            "
				        + "           ON tbldctosordenes.IDLOCAL      =                                            "
				        + "                 tbldctosordenesdetalle.IDLOCAL                                         "
				        + "           AND tbldctosordenes.IDTIPOORDEN =                                            "
				        + "              tbldctosordenesdetalle.IDTIPOORDEN                                        "
				        + "           AND tbldctosordenes.IDORDEN       =                                          "
				        + "                  tbldctosordenesdetalle.IDORDEN                                        "
				        + "           WHERE  tbldctosordenes.IDLOCAL     =                                         "
				        + "                tbldctosordenesdetalle.idLocal                                          "
				        + "          AND  tbldctosordenes.IDLOCAL     =                                            "
				        + "        ?1                                                                             "
				        + "          AND tbldctosordenes.idPeriodo    =                                            "
				        + "        ?3                                                                          "
				        + "          AND tbldctosordenesdetalle.IDTIPO =                                           "
				        + "        ?4                                                                               "
				        + "           GROUP BY tbldctosordenesdetalle.idCliente                                    "
						+ "		                                    )  AS tmpACT                                   "
				        + "          ON tmpACT.idCliente = tblterceros.idCliente                                   "
				        + "          LEFT  JOIN (                                                                  "
				        + "          SELECT  tbldctosordenesdetalle.idCliente,                                     "
				        + "          MAX(tbldctosordenesdetalle.idNovedadLectura)                                  "
				        + "                                       AS idCausal                                      "
				        + "           FROM   tbldctosordenes                                                       "
				        + "           INNER JOIN tbldctosordenesdetalle                                            "
				        + "           ON tbldctosordenes.IDLOCAL      =                                            "
				        + "                 tbldctosordenesdetalle.IDLOCAL                                         "
				        + "           AND tbldctosordenes.IDTIPOORDEN =                                            "
				        + "              tbldctosordenesdetalle.IDTIPOORDEN                                        "
				        + "           AND tbldctosordenes.IDORDEN       =                                          "
				        + "                  tbldctosordenesdetalle.IDORDEN                                        "
				        + "           WHERE  tbldctosordenes.IDLOCAL   =                                           "
				        + "                tbldctosordenesdetalle.idLocal                                          "
				        + "          AND  tbldctosordenes.IDLOCAL     =                                            "
				        + "        ?1                                                                             "
				        + "          AND tbldctosordenes.idPeriodo    =                                            "
				        + "        ?3                                                                          "
				        + "          AND tbldctosordenesdetalle.IDTIPO =                                           "
				        + "        ?4                                                                               "
				        + "           GROUP BY tbldctosordenesdetalle.idCliente)                                   "
				        + "                                            AS tmpCAU                                   "
				        + "          ON tmpCAU.idCliente = tblterceros.idCliente                                   "
				        + "        INNER JOIN tbltipocausanota                                                     "
				        + "        ON tmpCAU.idCausal = tbltipocausanota.idCausa                                   "
				        + "         WHERE tblterceros.idLocal        =                                             "
				        + "        ?1                                                                             "
				        + "         AND tbltercerosruta.idRuta       =  ?5                                          "
				        + "         AND tblterceros.estado NOT IN (2)                                              "
		                + "      AND NOT EXISTS (                                                                  "
		                + "      SELECT tblDctos.IDLOCAL,                                                          "
		                + "             tblDctos.IDTIPOORDEN,                                                      "
			            + "             tblDctos.idCliente                                                         "
		                + "      FROM    tblDctos                                                                  "
		                + "      INNER JOIN tblDctosOrdenesDetalle                                                 "
		                + "      ON tblDctos.IDLOCAL  = tblDctosOrdenesDetalle.idLocal                             "
		                + "      AND tblDctos.IDTIPOORDEN = tblDctosOrdenesDetalle.IDTIPOORDEN                     "
		                + "      AND tblDctos.IDORDEN = tblDctosOrdenesDetalle.IdORDEN                             "
		                + "      WHERE tblDctos.IDLOCAL=?1                                                        "
		                + "      AND   tblDctos.IDTIPOORDEN=9                                                      "
		                + "      AND   tblDctos.idPeriodo=?2                                                   "
		                + "      AND   tblDctosOrdenesDetalle.IDTIPO= ?4                                             "
		                + "      AND tblDctos.IDLOCAL      = tblterceros.IDLOCAL                                   "
		                + "      AND tblDctos.idCliente   = tblterceros.IDCLIENTE)					               "
				        + "      ORDER BY tbltercerosruta.idRuta,                                                  "
				        + "                   tblterceros.ordenRuta,                                               "
				        + "                   tblterceros.idCliente                                                "
						+ "				                                                                           "
		               ,
						nativeQuery = true)
				List<TercerosDTO2> listaCriticaInconsistenciaSinFacturaAnterior(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta);
				
				
				
				
				@Query(value = "SELECT * " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idCliente = ?2 " +
						"AND idTipoTercero = ?3 ",
						nativeQuery = true)
				List<TblTerceros> listaUnTerceroFCH(int idLocal, String idCliente, int idTipoTercero);
				
				
				 @Modifying
				  @Transactional
				  @Query(value = " UPDATE       tblterceros        "
			                + " SET          estadoCorte = 1    "
			                + " FROM  tbldctosordenes           "
			                + " INNER JOIN  tblterceros         "
			                + " ON tbldctosordenes.IDLOCAL    = "
			                + "             tblterceros.idLocal "
			                + " AND tbldctosordenes.idCliente = "
			                + "           tblterceros.idCliente "
			                + " WHERE tbldctosordenes.IDLOCAL = "
			                + "?1                  "
			                + " AND tbldctosordenes.idPeriodo = "
			                + "?2                    "
			                + " AND tblterceros.estadoCorte =   "
			                + "?3        "
			                + " AND tblterceros.idTipoTercero=1 ", nativeQuery = true)
				  public void actualizaEstadoCorte(int idLocal, int xIdPeriodo, int xEstadoCorteReconexion);
				 
				 
				 
				 @Query(value = "SELECT idEstracto " + 
							"FROM bdaquamovil.dbo.tblTerceros " +
							"WHERE tblTerceros.idLocal = ?1 " +
							"AND idCliente = ?2 ",
							nativeQuery = true)
					Integer ObteneridEstrato(int idLocal, String idCliente);
				 
				 
				 @Query(value = "SELECT tblterceros.idLocal,                "
			                + "       tblterceros.idCliente,            "
			                + "       tblterceros.idTercero,            "
			                + "       tblterceros.tipoIdTercero,        "
			                + "       tblterceros.digitoVerificacion,   "
			                + "       tblterceros.idTipoTercero,        "
			                + "       tblterceros.idPersona,            "
			                + "       tblterceros.idAutoRetenedor,      "
			                + "       tblterceros.idRegimen,            "
			                + "       tblterceros.nombreTercero,        "
			                + "       tblterceros.direccionTercero,     "
			                + "       tblterceros.idDptoCiudad,         "
			                + "       tblterceros.telefonoFijo,         "
			                + "       tblterceros.telefonoCelular,      "
			                + "       tblterceros.telefonoFax,          "
			                + "       tblterceros.email,                "
			                + "       tblterceros.idFormaPago,          "
			                + "       tblterceros.estado,               "
			                + "       tblterceros.nombreEmpresa,        "
			                + "       tblterceros.cupoCredito,          "
			                + "       tblterceros.indicador,            "
			                + " (SELECT tblciudades.nombreCiudad + '/' +"
			                + "         tblciudades.nombreDpto          "
			                + "  FROM tblciudades                       "
			                + "  WHERE tblciudades.idCiudad      =      "
			                + "            tblterceros.idDptoCiudad)    "
			                + "                      AS ciudadTercero,  "
			                + "       tblterceros.contactoTercero,      "
			                + "       tblterceros.idListaPrecio,        "
			                + "       tblterceros.idVendedor,           "
			                + "       00 AS idLocalTercero,             "
			                + "       tblterceros.idEstracto,           "
			                + "       tblterceros.idRuta                "
			                + "FROM   tblterceros                       "
			                + "WHERE  tblterceros.idLocal     =         "
			                + "?1                    "
			                + "AND tblterceros.idTipoTercero  =         "
			                +"?2                    "
			                + "AND    tblterceros.idCliente   =        "
			                + "?3                       "
			               ,
							nativeQuery = true)
					List<TercerosDTO2> listaTerceroFCH(int idLocal, int IdTipoTercero, String IdCliente);
				
				 
				 
				 @Query(value = "SELECT MAX(tblterceros.idTercero)                     "
			                + "                           AS idTercero,              "
			                + "       MAX(tblterceros.nombreTercero)                 "
			                + "                      AS nombreTercero ,              "
			                + "       MAX(RTRIM(LTRIM(tblterceros.direccionTercero)))"
			                + "                    AS direccionTercero,              "
			                + "      (SELECT tblciudades.nombreCiudad + ' / ' +      "
			                + "                 tblciudades.nombreDpto               "
			                + "       FROM tblciudades                               "
			                + "       WHERE tblciudades.idCiudad =                   "
			                + "                tblterceros.idDptoCiudad) AS ciudad,  "
			                + "       MAX(tblterceros.telefonoFijo)  AS telefonoFijo,"
			                + "       MAX(tblterceros.idFormaPago)   AS formaPago,   "
			                + "       tblterceros.idCliente,                         "
			                + "       MAX(tblterceros.idRuta)        AS idRuta,      "
			                + "       MAX(tblterceros.email)         AS email,       "
			                + "       MAX(tblterceros.digitoVerificacion)            "
			                + "                                AS digitoVerificacion "
			                + "FROM tblterceros,                                     "
			                + "     tbldctosordenes ,                                "
			                + "     tbldctosordenesdetalle                           "
			                + "WHERE tbldctosordenes.idOrden         =               "
			                + "            tbldctosordenesdetalle.idOrden            "
			                + "AND   tbldctosordenes.idTipoOrden     =               "
			                + "      tbldctosordenesdetalle.idTipoOrden              "
			                + "AND   tbldctosordenes.idLocal         =               "
			                + "            tbldctosordenesdetalle.idLocal            "
			                + "AND   tbldctosordenes.idLocal         =               "
			                + "?1                                       "
			                + "AND   tbldctosordenes.idTipoOrden     =               "
			                + "?2                                   "
			                + "AND   tbldctosordenes.idOrden         =               "
			                + "?3                                       "
			                + "AND   tblterceros.idCliente           =               "
			                + "                   tbldctosordenes.idCliente          "
			                + "AND   tblterceros.idLocal             =               "
			                + "                   tbldctosordenes.idLocal            "
			                + "GROUP BY tblterceros.idCliente,                       "
			                + "         tblterceros.idDptoCiudad                     "
			               ,
							nativeQuery = true)
					List<TercerosDTO2> listaUnTerceroOrden(int idLocal, int IdTipoOrden, int IdOrden);
				 
				 
				 
				 
				 @Query(value = " SELECT  tblterceros.idCliente,                 "
			                + "  tblterceros.idTercero,                      "
			                + "  tblterceros.tipoIdTercero,	                 "
			                + "  tblterceros.digitoVerificacion,             "
			                + "  tblterceros.idTipoTercero, 	         "
			                + "  tblterceros.idPersona,                      "
			                + "  tblterceros.idAutoRetenedor,                "
			                + "  tblterceros.idRegimen, 	                 "
			                + "  tblterceros.nombreTercero, 	         "
			                + "  tblterceros.direccionTercero,               "
			                + "  tblterceros.idDptoCiudad, 	                 "
			                + "  tblterceros.telefonoFijo, 	                 "
			                + "  tblterceros.telefonoCelular,                "
			                + "  tblterceros.telefonoFax, 	                 "
			                + "  tblterceros.email, 		         "
			                + "  tblterceros.idFormaPago, 	                 "
			                + "  tblterceros.estado,                         "
			                + "  tblregimen.nombreRegimen, 	                 "
			                + "  tbltipotercero.nombreTipoTercero,           "
			                + "  tblciudades.nombreCiudad, 	                 "
			                + "  tblciudades.nombreDpto,    	         "
			                + "  tblterceros.idVendedor,     	         "
			  
			                + "  tbltercerosruta.nombreRuta,                 "
			                + "  tblterceroestracto.nombreEstracto,          "
			                + "  tblterceros.idEstracto,                     "
			                

			                + "  ( SELECT  TOP 1                       "
			                + "          tmpDET.lecturaMedidor         "
			                + "             as lecturaAnterior         "
			                + " FROM   tblDctos tmpDCT                 "
			                + " INNER JOIN tblDctosOrdenesDetalle      "
			                + "                            tmpDET      "
			                + " ON tmpDCT.IDLOCAL        =             "
			                + "       tmpDET.IDLOCAL                   "
			                + " AND tmpDCT.IDTIPOORDEN   =             "
			                + "   tmpDET.IDTIPOORDEN                   "
			                + " AND tmpDCT.IDORDEN       =             "
			                + "       tmpDET.IDORDEN                   "
			                + " AND   tmpDCT.idLocal     =             "
			                + "?1                            "
			                + " AND   tmpDCT.idTipoOrden IN (9,29)     "
			                + " AND   tmpDCT.idPeriodo   =             "
			                + "?2                     "
			                + " AND   tmpDET.IDTIPO      =             "
			                + "?3                              "                
			                + " AND   tmpDET.IDCLIENTE   =             "
			                + "         tblterceros.idCliente          "
			                + " ORDER BY tmpDCT.idCliente ,            "
			                + "          tmpDCT.idOrden DESC )         "
			                + " 		    AS lecturaActual,      "                
			                
			                
			                + "  ( SELECT  TOP 1                       "
			                + "          tmpDET.lecturaMedidor         "
			                + "             as lecturaAnterior         "
			                + " FROM   tblDctos tmpDCT                 "
			                + " INNER JOIN tblDctosOrdenesDetalle      "
			                + "                            tmpDET      "
			                + " ON tmpDCT.IDLOCAL        =             "
			                + "       tmpDET.IDLOCAL                   "
			                + " AND tmpDCT.IDTIPOORDEN   =             "
			                + "   tmpDET.IDTIPOORDEN                   "
			                + " AND tmpDCT.IDORDEN       =             "
			                + "       tmpDET.IDORDEN                   "
			                + " AND   tmpDCT.idLocal     =             "
			                + "?1                             "
			                + " AND   tmpDCT.idTipoOrden IN (9,29)     "
			                + " AND   tmpDCT.idPeriodo   =             "
			                + "?4                   "
			                + " AND   tmpDET.IDTIPO      =             "
			                + "?3                              "                
			                + " AND   tmpDET.IDCLIENTE   =             "
			                + "         tblterceros.idCliente          "
			                + " ORDER BY tmpDCT.idCliente ,            "
			                + "          tmpDCT.idOrden DESC )         "
			                + " 		    AS lecturaAnterior,    "                   
			                
			                
			       //         + "  tmpACT.lecturaActual,                       "
			       //         + "  tmpANT.lecturaAnterior,                     "
			                + "  tbltercerosruta.idRuta                      "
			                + "  ,tblterceros.promedio,                      "
			                + "  tblterceros.ordenRuta,                      "
			                + "  tblterceros.CC_Nit,                         "
			                + "  tmpCAU.idCausal AS idCausa,                 "
			                + "  tmpEST.nombreEstado 		  	 "
			                + " FROM tblterceros                            "
			                + " INNER JOIN tblregimen                       "
			                + " ON tblregimen.idRegimen         =           "
			                + "             tblterceros.idRegimen            "
			                + " INNER JOIN tbltipotercero                    "
			                + " ON tbltipotercero.idTipoTercero =            "
			                + "          tblterceros.idTipoTercero           "
			                + " INNER JOIN tblciudades                       "
			                + " ON tblciudades.idCiudad         =            "
			                + "          tblterceros.idDptoCiudad            "
			                + " INNER JOIN tbltercerosruta                   "
			                + " ON tbltercerosruta.idRuta       =            "
			                + "                tblterceros.idRuta            "
			                + " AND tbltercerosruta.idLocal     =            "
			                + "               tblterceros.idLocal            "
			                + " INNER JOIN tblterceroestracto                "
			                + " ON tblterceroestracto.idEstracto =           "
			                + "             tblterceros.idEstracto           "
			                + " AND tblterceroestracto.idLocal=              "
			                + "                 tblterceros.idLocal          "
			                + " INNER JOIN (                                 "
			                + " SELECT tbltipocausanota.idCausa,             "
			                + " 	 tbltipocausanota.nombreCausa            "
			                + " 		      AS nombreEstado            "
			                + " FROM tbltipocausanota                        "
			                + " WHERE tbltipocausanota.idTipoTabla = 3 )     "
			                + " 			      AS tmpEST          "
			                + " ON   tmpEST.idCausa    =                     "
			                + " 	               tblterceros.estado        "
			                + " LEFT OUTER JOIN (                            "
			                + " SELECT  tbldctosordenesdetalle.idCliente,    "
			                + " MAX(tbldctosordenesdetalle.idNovedadLectura) "
			                + "                              AS idCausal     "
			                + "  FROM   tbldctosordenes                      "
			                + "  INNER JOIN tbldctosordenesdetalle           "
			                + "  ON tbldctosordenes.IDLOCAL      =           "
			                + "        tbldctosordenesdetalle.IDLOCAL        "
			                + "  AND tbldctosordenes.IDTIPOORDEN =           "
			                + "     tbldctosordenesdetalle.IDTIPOORDEN       "
			                + "  AND tbldctosordenes.IDORDEN       =         "
			                + "         tbldctosordenesdetalle.IDORDEN       "
			                + "  WHERE  tbldctosordenes.IDLOCAL   =          "
			                + "       tbldctosordenesdetalle.idLocal         "
			                + "  AND  tbldctosordenes.IDLOCAL     =          "
			                + "?1                                   "
			                + "  AND tbldctosordenes.idPeriodo    =          "
			                + "?2                           "
			                + "  AND tbldctosordenesdetalle.IDTIPO =         "
			                + "?3                                    "
			                + "  GROUP BY tbldctosordenesdetalle.idCliente)  "
			                + "                                    AS tmpCAU "
			                + " ON tmpCAU.idCliente = tblterceros.idCliente  "
			                + " WHERE tblterceros.idLocal        =           "
			                + "?1                                   "
			                + " AND tblterceros.idCliente       =            "
			                + "?5                            "
			                + " AND tblterceros.estado NOT IN (2)            "
			                + " ORDER BY tbltercerosruta.idRuta,             "
			                + "          tblterceros.ordenRuta,              "
			                + "          tblterceros.idCliente "
			               ,
							nativeQuery = true)
					List<TercerosDTO2> listaLecturaClienteFCH(int idLocal, int IdPeriodoActual, int IdTipo, int IdPeriodoAnterior, String IdCliente);
				 
				 
				 @Query(value = " SELECT tmpSaldo.idLocal,                     "
			              + "      tmpSaldo.idTipoOrden,                   "
			              + "      tmpSaldo.idOrden,                       "
			              + "      tmpSaldo.idDcto,                        "
			              + "      tmpSaldo.fechaDcto,                     "
			              + "      tmpSaldo.fechaVcto,                     "
			              + "      tmpSaldo.idCliente,                     "
			              + "      MAX(tmpSaldo.nombreTercero)             "
			              + "                           AS nombreTercero,  "
			              + "      MAX(tblterceros.telefonoCelular)+ '/' + "
			              + "       MAX(tblterceros.telefonoFijo )         "
			              + "                                 AS telefono, "
			              + "      MAX(tblterceros.direccionTercero)       "
			              + "                         AS direccion, "
			              + "      MAX(tmpSaldo.cuotaVencida)              "
			              + "                 	        AS cuotaVencida, "
			              + "      MAX(tbltercerosruta.nombreCiclo +'-'+   "
			              + "	             tbltercerosruta.nombreRuta) "
			              + "                               AS nombreRuta, "
			              + "      MAX(tblterceros.idRuta) AS idRuta,      "
			              + "      tmpSaldo.vrSaldo,                       "
			              + "      MAX(tmpSaldo.vrPago) AS vrPago,         "
			              + "    MAX(tmpSaldo.idDctoNitCC) AS idDctoNitCC, "
			              + "      tmpSaldo.nombreEstrato,                 "
			              + "      MAX(tblterceros.ordenRuta) AS ordenRuta,"
			              + "      MAX(tmpSaldo.fechaPago) AS fechaPago,   "
			              + "	 MAX(tmpSaldo.vrUltimoPago)              "
			              + "                              AS vrUltimoPago "
			              + " FROM                                         "
			              + "  (SELECT tmpCXC.IDLOCAL,                     "
			              + "         tmpCXC.IDTIPOORDEN,                  "
			              + "         tmpCXC.idDcto,                       "
			              + "         tmpCXC.idOrden,                      "
			              + "         tmpCXC.indicador,                    "
			              + "         tmpCXC.idCliente,                    "
			              + "         MAX(tmpCXC.idDctoNitCC)              "
			              + "                              AS idDctoNitCC, "
			              + "         MAX(tmpCXC.diasPlazo) AS diasPlazo,  "
			              + "         MIN(tmpCXC.fechaDcto) AS fechaDcto,  "
			              + "         MIN(tmpCXC.fechaDcto) +              "
			              + "         MAX(tmpCXC.diasPlazo) AS fechaVcto,  "
			              + "         SUM(tmpCXC.vrBase) AS vrBase,        "
			              + "         SUM(tmpCXC.vrPago) AS vrPago,        "
			              + "         SUM(tmpCXC.vrIva) AS vrIva,          "
			              + "         SUM(tmpCXC.vrImpoconsumo)            "
			              + "                            AS vrImpoconsumo, "
			              + "      SUM(tmpCXC.vrRteFuente) AS vrRteFuente, "
			              + "      SUM(tmpCXC.vrDsctoFcro) AS vrDsctoFcro, "
			              + "         SUM(tmpCXC.vrRteIva) AS vrRteIva,    "
			              + "         SUM(tmpCXC.vrRteIca) AS vrRteIca,    "
			              + "     ( SUM(tmpCXC.vrBase)               +     "
			              + "       SUM(tmpCXC.vrIva)         +            "
			              + "       SUM(tmpCXC.vrImpoconsumo) -            "
			              + "       SUM(tmpCXC.vrPago)        -            "
			              + "       SUM(tmpCXC.vrRteFuente)   -            "
			              + "       SUM(tmpCXC.vrDsctoFcro)   -            "
			              + "       SUM(tmpCXC.vrRteIva)      -            "
			              + "       SUM(tmpCXC.vrRteIca) ) AS vrSaldo,     "
			              + "         MAX(tmpCXC.nombreTercero)            "
			              + "                            AS nombreTercero, "
			              + "        MAX(tmpCXC.idVendedor) AS idVendedor, "
			              + "         MAX(tmpCXC.nombreVendedor)           "
			              + "                           AS nombreVendedor, "
			              + "         tmpCXC.nombreRuta,	                 "
			              + "         tmpCXC.nombreEstrato,          	 "
			              + "       ISNULL(tbldctosordenes.cuotaVencida,0) "
			              + "                             AS cuotaVencida, "
			              + "         MAX(tbldctosordenes.idPeriodo)       "
			              + "                                AS idPeriodo, "
			              + "      (SELECT TOP 1 tblpagos.fechaPago        "
			              + "                                 AS fechaPago "
			              + "       FROM tblpagos                          "
			              + "       WHERE tblpagos.IDLOCAL = tmpCXC.idLocal "
			              + "       AND tblpagos.IDTIPOORDEN =             "
			              + "                           tmpCXC.idTipoOrden "
			              + "       AND tblpagos.nitCC = tmpCXC.idCliente  "
			              + "           ORDER BY tblpagos.fechaPago DESC ) "
			              + "                               AS fechaPago,  "
			              + "      (SELECT TOP 1 tblpagos.vrPago AS vrPago "
			              + "       FROM tblpagos                          "
			              + "       WHERE tblpagos.IDLOCAL = tmpCXC.idLocal "
			              + "       AND tblpagos.IDTIPOORDEN =             "
			              + "                           tmpCXC.idTipoOrden "
			              + "       AND tblpagos.nitCC = tmpCXC.idCliente  "
			              + "       ORDER BY fechaPago DESC) AS vrUltimoPago"
			              + "  FROM                                        "
			              + "  (SELECT tbldctos.idLocalCruce               "
			              + "                 AS idLocal,                  "
			              + "  tbldctos.idTipoOrdenCruce                   "
			              + "             AS idTipoOrden,                  "
			              + "  tbldctos.idDctoCruce                        "
			              + "                 AS idDcto,                   "
			              + "  tbldctos.idOrdenCruce AS idOrden,           "
			              + "  tbldctos.indicador,                         "
			              + "  tbldctos.idCliente,                         "
			              + "  tbldctos.vrBase,                            "
			              + "  tbldctos.vrPago,                            "
			              + "  tbldctos.vrIva,                             "
			              + "  tbldctos.vrImpoconsumo,                     "
			              + "  tbldctos.vrRteFuente,                       "
			              + "  tbldctos.vrDsctoFcro,                       "
			              + "  tbldctos.vrRteIva,                          "
			              + "  tbldctos.vrRteIca,                          "
			              + "  tbldctos.fechaDcto,                         "
			              + "  tbldctos.diasPlazo,                         "
			              + "  tbldctos.idDctoNitCC,                       "
			              + "  tbldctos.nombreTercero,                     "
			              + "  tbldctos.idVendedor,                        "
			              + "  (SELECT ctrlusuarios.aliasUsuario           "
			              + "  FROM ctrlusuarios                           "
			              + "  WHERE ctrlusuarios.idUsuario  =             "
			              + "                  tbldctos.idVendedor         "
			              + "  AND ctrlusuarios.idLocal      =             "
			              + "                     tbldctos.idLocal)        "
			              + "                   AS nombreVendedor,         "
			              + " (SELECT tbltercerosruta.nombreRuta           "
			              + "    FROM tbltercerosruta                      "
			              + "    INNER JOIN tblterceros                    "
			              + "  ON tbltercerosruta.idLocal                  "
			              + " 		 = tblterceros.idLocal           "
			              + "  AND tbltercerosruta.idRuta                  "
			              + "            =tblterceros.idRuta               "
			              + "     WHERE tblterceros.idLocal                "
			              + "  		  = tbldctos.IDLOCAL             "
			              + "   AND tblterceros.idCliente                  "
			              + "  	= tbldctos.idCliente)                    "
			              + "  		AS nombreRuta,                   "
			              + "   (SELECT                                    "
			              + "   tblterceroestracto.nombreEstracto          "
			              + "    FROM tblterceroestracto                   "
			              + "    INNER JOIN tblterceros                    "
			              + "     ON tblterceroestracto.idLocal            "
			              + "  	          =tblterceros.idLocal           "
			              + "     AND tblterceroestracto.idEstracto        "
			              + " 	=tblterceros.idEstracto                  "
			              + "    WHERE tblterceros.idLocal                 "
			              + " 	         = tbldctos.IDLOCAL              "
			              + "  AND tblterceros.idCliente                   "
			              + " 		= tbldctos.idCliente)            "
			              + " 		       AS nombreEstrato          "
			              + " FROM   tbldctos                                 "
			              + " WHERE  tbldctos.idLocal           =  	    "
			              + "?1                                  "
			              + " AND    tbldctos.idTipoOrdenCruce  =             "
			              + "?2                              "
			              + " AND    tbldctos.idDctoCruce      != 0           "
			              + " AND    tbldctos.idPeriodo         =             "
			              + "?3                                "
			              + "  AND    EXISTS (                             "
			              + "  SELECT *                                    "
			              + "  FROM tbldctosordenesdetalle                 "
			              + " WHERE tbldctosordenesdetalle.idLocal =       "
			              + "                       tbldctos.idLocal       "
			              + " AND tbldctosordenesdetalle.idTipoOrden       "
			              + "                                  =           "
			              + "             tbldctos.idTipoOrden             "
			              + " AND tbldctosordenesdetalle.idOrden   =       "
			              + "                  tbldctos.idOrden )          "
			              + "  UNION                                       "
			              + "  SELECT tbldctos.idLocal,                    "
			              + " tbldctos.idTipoOrden,                        "
			              + " tbldctos.idDcto,                             "
			              + " tbldctos.idOrden,                            "
			              + " tbldctos.indicador,                          "
			              + " tbldctos.idCliente,                          "
			              + " tbldctos.vrBase,                             "
			              + " tbldctos.vrPago,                             "
			              + " tbldctos.vrIva,                              "
			              + " tbldctos.vrImpoconsumo,                      "
			              + " tbldctos.vrRteFuente,                        "
			              + " tbldctos.vrDsctoFcro,                        "
			              + " tbldctos.vrRteIva,                           "
			              + " tbldctos.vrRteIca,                           "
			              + " tbldctos.fechaDcto,                          "
			              + " tbldctos.diasPlazo,                          "
			              + " tbldctos.idDctoNitCC,                        "
			              + " tbldctos.nombreTercero,                      "
			              + " tbldctos.idVendedor,                         "
			              + " (SELECT ctrlusuarios.aliasUsuario            "
			              + "  FROM ctrlusuarios                           "
			              + "  WHERE ctrlusuarios.idUsuario  =             "
			              + "                  tbldctos.idVendedor         "
			              + "  AND ctrlusuarios.idLocal      =             "
			              + "                     tbldctos.idLocal)        "
			              + "                      AS nombreVendedor,      "
			              + " (SELECT tbltercerosruta.nombreRuta           "
			              + "    FROM tbltercerosruta                      "
			              + "    INNER JOIN tblterceros                    "
			              + "  ON tbltercerosruta.idLocal                  "
			              + " 		 = tblterceros.idLocal           "
			              + "    AND tbltercerosruta.idRuta                "
			              + "               =tblterceros.idRuta            "
			              + "    WHERE tblterceros.idLocal                 "
			              + " 		  = tbldctos.IDLOCAL             "
			              + "  AND tblterceros.idCliente                   "
			              + "    = tbldctos.idCliente)                     "
			              + " 	AS nombreRuta,                           "
			              + "    (SELECT                                   "
			              + "     tblterceroestracto.nombreEstracto        "
			              + "     FROM tblterceroestracto                  "
			              + "     INNER JOIN tblterceros                   "
			              + "     ON tblterceroestracto.idLocal            "
			              + " 	          =tblterceros.idLocal           "
			              + "     AND tblterceroestracto.idEstracto        "
			              + " 		=tblterceros.idEstracto          "
			              + "     WHERE tblterceros.idLocal                "
			              + "           = tbldctos.IDLOCAL                 "
			              + "     AND tblterceros.idCliente                "
			              + " 		= tbldctos.idCliente)            "
			              + "    	       AS nombreEstrato                  "
			              + " FROM   tbldctos                                 "
			              + " WHERE  tbldctos.idLocal           =             "
			              + "?1                                  "
			              + " AND    tbldctos.idTipoOrden       =             "
			              + "?2                              "
			              + " AND    tbldctos.idDctoCruce       = 0           "
			              + " AND    tbldctos.idPeriodo         =             "
			              + "?3                               "
			              + "  AND    EXISTS (                             "
			              + "  SELECT *                                    "
			              + "    FROM tbldctosordenesdetalle               "
			              + "    WHERE tbldctosordenesdetalle.idLocal =    "
			              + "                         tbldctos.idLocal     "
			              + "    AND tbldctosordenesdetalle.idTipoOrden    "
			              + "                                         =    "
			              + "                    tbldctos.idTipoOrden      "
			              + "   AND  tbldctosordenesdetalle.idOrden   =    "
			              + "                        tbldctos.idOrden )    "
			              + "  UNION                                       "
			              + "  SELECT tbldctos.idLocal,                    "
			              + " tbldctos.idTipoOrden,                        "
			              + " tbldctos.idDctoCruce                         "
			              + "                AS idDcto,                    "
			              + " tbldctos.idOrdenCruce AS idOrden,            "
			              + " tbldctos.indicador,                          "
			              + " tbldctos.idCliente,                          "
			              + " tbldctos.vrBase,                             "
			              + " tbldctos.vrPago,                             "
			              + " tbldctos.vrIva,                              "
			              + " tbldctos.vrImpoconsumo,                      "
			              + " tbldctos.vrRteFuente,                        "
			              + " tbldctos.vrDsctoFcro,                        "
			              + " tbldctos.vrRteIva,                           "
			              + " tbldctos.vrRteIca,                           "
			              + " tbldctos.fechaDcto,                          "
			              + " tbldctos.diasPlazo,                          "
			              + " tbldctos.idDctoNitCC,                        "
			              + " tbldctos.nombreTercero,                      "
			              + " tbldctos.idVendedor,                         "
			              + " (SELECT ctrlusuarios.aliasUsuario            "
			              + "  FROM ctrlusuarios                           "
			              + "  WHERE ctrlusuarios.idUsuario  =             "
			              + "                  tbldctos.idVendedor         "
			              + "  AND ctrlusuarios.idLocal      =             "
			              + "                     tbldctos.idLocal)        "
			              + "                      AS nombreVendedor,      "
			              + " (SELECT tbltercerosruta.nombreRuta           "
			              + "    FROM tbltercerosruta                      "
			              + "    INNER JOIN tblterceros                    "
			              + "  ON tbltercerosruta.idLocal                  "
			              + " 		 = tblterceros.idLocal           "
			              + "    AND tbltercerosruta.idRuta                "
			              + "                   =tblterceros.idRuta        "
			              + "    WHERE tblterceros.idLocal                 "
			              + " 		  = tbldctos.IDLOCAL             "
			              + "  AND tblterceros.idCliente                   "
			              + " 	= tbldctos.idCliente)                    "
			              + " 		AS nombreRuta,                   "
			              + "      (SELECT                                 "
			              + "        tblterceroestracto.nombreEstracto     "
			              + "    FROM tblterceroestracto                   "
			              + "  INNER JOIN tblterceros                      "
			              + "    ON tblterceroestracto.idLocal             "
			              + " 	          =tblterceros.idLocal           "
			              + "    AND tblterceroestracto.idEstracto         "
			              + " 		=tblterceros.idEstracto          "
			              + "    WHERE tblterceros.idLocal                 "
			              + " 	         = tbldctos.IDLOCAL              "
			              + "  AND tblterceros.idCliente                   "
			              + " 		= tbldctos.idCliente)            "
			              + " 		       AS nombreEstrato          "
			              + " FROM   tbldctos                                 "
			              + " WHERE  tbldctos.idLocal           =             "
			              + "?1                                  "
			              + " AND    tbldctos.idTipoOrden       =             "
			              + "?2                              "
			              + " AND    tbldctos.idTipoOrdenCruce  = 0           "
			              + " AND    tbldctos.idDctoCruce      != 0           "
			              + " AND    tbldctos.idPeriodo         =             "
			              + "?3                                "
			              + "  AND    EXISTS (                             "
			              + "    SELECT *                                  "
			              + "     FROM tbldctosordenesdetalle              "
			              + "     WHERE tbldctosordenesdetalle.idLocal =   "
			              + "                          tbldctos.idLocal    "
			              + "     AND tbldctosordenesdetalle.idTipoOrden   "
			              + "                                         =    "
			              + "                      tbldctos.idTipoOrden    "
			              + "     AND  tbldctosordenesdetalle.idOrden   =  "
			              + "                          tbldctos.idOrden )  "
			              + "                         )  AS tmpCXC         "
			              + " INNER JOIN  tbldctosordenes                  "
			              + " ON tbldctosordenes.IDLOCAL = tmpCXC.idLocal  "
			              + " AND tbldctosordenes.IDTIPOORDEN =            "
			              + "                           tmpCXC.IDTIPOORDEN "
			              + " AND tbldctosordenes.IDORDEN = tmpCXC.IDORDEN "
			              + "  GROUP BY tmpCXC.IDLOCAL,                    "
			              + "           tmpCXC.IDTIPOORDEN,                "
			              + "           tmpCXC.idDcto,                     "
			              + "           tmpCXC.idOrden,                    "
			              + "           tmpCXC.indicador,                  "
			              + "           tmpCXC.idCliente,                  "
			              + "  	      tmpCXC.nombreRuta,                 "
			              + "           tmpCXC.nombreEstrato,              "
			              + "           tbldctosordenes.cuotaVencida       "
			              + "  HAVING ( SUM(tmpCXC.vrBase)        +        "
			              + "           SUM(tmpCXC.vrIva)         +        "
			              + "           SUM(tmpCXC.vrImpoconsumo) -        "
			              + "           SUM(tmpCXC.vrPago)        -        "
			              + "           SUM(tmpCXC.vrRteFuente)   -        "
			              + "           SUM(tmpCXC.vrDsctoFcro)   -        "
			              + "           SUM(tmpCXC.vrRteIva)      -        "
			              + "           SUM(tmpCXC.vrRteIca) ) > 1 )       "
			              + "                            AS tmpSaldo       "
			              + "   INNER JOIN tblterceros                     "
			              + "   ON tblterceros.idLocal =                   "
			              + "                        tmpSaldo.idLocal      "
			              + "   AND tblterceros.idCliente =                "
			              + "                     tmpSaldo.idCliente       "
			              + "   INNER JOIN tbltercerosruta                 "
			              + "   ON tblterceros.idLocal                     "
			              + "                = tbltercerosruta.idLocal     "
			              + "   AND tblterceros.idRuta =                   "
			              + "                   tbltercerosruta.idRuta     "
			              + " WHERE tmpSaldo.indicador BETWEEN 1  AND 2    "
			              + " AND   tmpSaldo.cuotaVencida > =              "
			              + "?4                         "
			              + " AND    tmpSaldo.idLocal =                    "
			              + "?1                               "
			              + " AND tmpSaldo.idTipoOrden =                   "
			              + "?2                           "
			              + " AND   tmpSaldo.idPeriodo  =                  "
			              + "?3                             "
			              + " GROUP BY tmpSaldo.idLocal,                   "
			              + "           tmpSaldo.IDTIPOORDEN,              "
			              + "           tmpSaldo.idOrden,                  "
			              + "           tmpSaldo.idDcto,                   "
			              + "           tmpSaldo.fechaDcto,                "
			              + "           tmpSaldo.fechaVcto,                "
			              + "           tmpSaldo.idCliente,                "
			              + "           tmpSaldo.vrSaldo,                  "
			              + "  	      tmpSaldo.nombreRuta,               "
			              + "           tmpSaldo.nombreEstrato             "
			              + "  ORDER BY 12,10,16 ",
			              nativeQuery = true)
				  List<TercerosDTO2> listaDetalleCortes(int idLocal, int IdTipoOrden, int idPeriodo, int CuotaVencida);
				 
				 
				 
				 @Modifying
				  @Transactional
				  @Query(value = "UPDATE tblterceros                  "
			                + " SET   tblterceros.estadoMedidor =  "
			                + "?1 ,             "
			                + "       tblterceros.estadoCorte   =  "
			                + "?2                 "
			                + " WHERE tblterceros.idCliente     =  "
			                + "?3                  "
			                + " AND tblterceros.idLocal         =  "
			                + "?4                    "
			                + "AND tblterceros.idTipoTercero    =  ?5 ",
			                nativeQuery = true)
				  public void actualizaEstadoMedidorCorte(int EstadoMedidor, int EstadoCorte, String IdCliente, int idLocal, int IdTipoTercero);
				 
				 
				 
				 @Query(value = " SELECT   tblterceros.idLocal,   "
			                + " tblterceros.idCliente,          "
			                + " tblterceros.ordenRuta,          "
			                + " tblterceros.nombreTercero,      "
			                + " tblterceros.direccionTercero,   "
			                + " tbltercerosruta.nombreCiclo,    "
			                + " tbltercerosruta.nombreCiclo     "
			                + "                        + '-' +  "
			                + "    tbltercerosruta.nombreRuta   "
			                + "                  AS nombreRuta, "
			                + " tblterceros.idRuta              "
			                + " FROM tblterceros                "
			                + " INNER JOIN tbltercerosruta      "
			                + " ON tblterceros.idLocal =        "
			                + "    tbltercerosruta.idLocal      "
			                + " AND tblterceros.idRuta =        "
			                + "      tbltercerosruta.idRuta     "
			                + " INNER JOIN (                    "
			                + " SELECT tblpagos.idLocal,        "
			                + "        tblpagos.nitCC,          "
			                + " 	   SUM(tblpagos.vrPago)     "
			                + " 	              AS vrPago     "
			                + " FROM   tblpagos                 "
			                + " INNER JOIN  tblpagosMedios      "
			                + " ON tblpagos.idLocal       =     "
			                + "     tblpagosMedios.idLocal      "
			                + " AND tblpagos.idTipoOrden  =     "
			                + "   tblpagosMedios.idTipoOrden    "
			                + " AND tblpagos.idRecibo     =     "
			                + "      tblpagosMedios.idRecibo    "
			                + " AND tblpagos.indicador    =     "
			                + "     tblpagosMedios.indicador    "
			                + " WHERE tblpagos.idLocal    =     "
			                + "?1                  "
			                + " AND   tblpagos.idPeriodo  =     "
			                + "?2                "
			                + " GROUP BY tblpagos.idLocal,      "
			                + "        tblpagos.nitCC           "
			                + " HAVING SUM(tblpagos.vrPago) > 0 "
			                + "                   ) AS tmpREC   "
			                + " ON tmpREC.idLocal         =     "
			                + "         tblterceros.idLocal     "
			                + " AND tmpREC.nitCC          =     "
			                + "       tblterceros.idCliente     "
			                + " WHERE tblterceros.idLocal =     "
			                + "?1                  "
			                + " AND tblterceros.estadoCorte = 2 "
			                + " ORDER BY tblterceros.idRuta ",
			              nativeQuery = true)
				  List<TercerosDTO2> listaReconexion(int idLocal, int idPeriodo);
				 
				 
				 @Query(value = " SELECT tblDctosOrdenes.IDLOCAL            "
			                + "   ,tblDctosOrdenes.idCliente            "
			                + "   ,tblDctosOrdenes.fechaInicioContrato  "
			                + "   ,tblDctosOrdenes.vrSalarioBasico      "
			                + "   ,tblDctosOrdenes.vrSubsidioTransporte "
			                + "   ,tblDctosOrdenes.fechaFinContrato     "
			                + "   ,tblDctosOrdenes.idContrato           "
			                + "   ,tblDctosOrdenes.idMedio              "
			                + "   ,tblDctosOrdenes.entidadMedio         "
			                + "   ,tblDctosOrdenes.cuentaMedio          "
			                + "   ,tblTerceros.nombreTercero            "
			                + "   ,tblTerceros.idTercero                "
			                + "   ,tblTerceros.direccionTercero         "
			                + "   ,tblTerceros.telefonoCelular          "
			                + " FROM tblDctosOrdenes                    "
			                + " INNER JOIN tblTerceros                  "
			                + " ON tblTerceros.idLocal     =            "
			                + "               tblDctosOrdenes.idLocal   "
			                + " AND tblTerceros.idCliente      =        "
			                + "            tblDctosOrdenes.idCliente    "
			                + "  INNER JOIN tblAgendaLogVisitas         "
			                + " ON tblAgendaLogVisitas.idLocal =        "
			                + "                 tblDctosOrdenes.IDLOCAL "
			                + "AND tblAgendaLogVisitas.idLog   =        "
			                + "                   tblDctosOrdenes.idLog "
			                + " WHERE EXISTS ( SELECT *                 "
			                + " FROM tblDctosOrdenesDetalle             "
			                + " WHERE tblDctosOrdenes.idLocal     =     "
			                + "      tblDctosOrdenesDetalle.idLocal     "
			                + " AND tblDctosOrdenes.idTipoOrden=        "
			                + "   tblDctosOrdenesDetalle.idTipoOrden    "
			                + " AND tblDctosOrdenes.idOrden    =        "
			                + "     tblDctosOrdenesDetalle.idOrden )    "
			                + " AND tblDctosOrdenes.idlocal    =        "
			                + "?1                          "
			                + " AND tblDctosOrdenes.idTipoOrden=        "
			                + "?2                      "
			                + " AND tblTerceros.idTipoTercero  =   3    "
			                + " AND tblAgendaLogVisitas.estado =   8    "
			                + " ORDER BY tblTerceros.nombreTercero ",
			              nativeQuery = true)
				  List<TercerosDTO2> listaContratoNEAll(int idLocal, int IdTipoOrden);
				 
				 
				 @Query(value = " SELECT tblDctosOrdenes.IDLOCAL            "
			                + "   ,tblDctosOrdenes.idCliente            "
			                + "   ,tblDctosOrdenes.fechaInicioContrato  "
			                + "   ,tblDctosOrdenes.vrSalarioBasico      "
			                + "   ,tblDctosOrdenes.vrSubsidioTransporte "
			                + "   ,tblDctosOrdenes.fechaFinContrato     "
			                + "   ,tblDctosOrdenes.idContrato           "
			                + "   ,tblDctosOrdenes.idMedio              "
			                + "   ,tblDctosOrdenes.entidadMedio         "
			                + "   ,tblDctosOrdenes.cuentaMedio          "
			                + "   ,tblTerceros.nombreTercero            "
			                + "   ,tblTerceros.idTercero                "
			                + "   ,tblTerceros.direccionTercero         "
			                + "   ,tblTerceros.telefonoCelular          "
			                + " FROM tblDctosOrdenes                    "
			                + " INNER JOIN tblTerceros                  "
			                + " ON tblTerceros.idLocal     =            "
			                + "               tblDctosOrdenes.idLocal   "
			                + " AND tblTerceros.idCliente      =        "
			                + "            tblDctosOrdenes.idCliente    "
			                + "  INNER JOIN tblAgendaLogVisitas         "
			                + " ON tblAgendaLogVisitas.idLocal =        "
			                + "                 tblDctosOrdenes.IDLOCAL "
			                + "AND tblAgendaLogVisitas.idLog   =        "
			                + "                   tblDctosOrdenes.idLog "
			                + " WHERE EXISTS ( SELECT *                 "
			                + " FROM tblDctosOrdenesDetalle             "
			                + " WHERE tblDctosOrdenes.idLocal     =     "
			                + "      tblDctosOrdenesDetalle.idLocal     "
			                + " AND tblDctosOrdenes.idTipoOrden=        "
			                + "   tblDctosOrdenesDetalle.idTipoOrden    "
			                + " AND tblDctosOrdenes.idOrden    =        "
			                + "     tblDctosOrdenesDetalle.idOrden )    "
			                + " AND tblDctosOrdenes.idlocal    =        "
			                + "?1                          "
			                + " AND tblDctosOrdenes.idTipoOrden=        "
			                + "?2                      "
			                + " AND tblTerceros.idTipoTercero  =   3    "
			                + " AND tblAgendaLogVisitas.estado =   8    "
			                +"AND (tblTerceros.nombreTercero LIKE %?3% OR CAST(tblTerceros.CC_Nit AS VARCHAR(20)) LIKE %?3%)" 
			                + " ORDER BY tblTerceros.nombreTercero ",
			              nativeQuery = true)
				  List<TercerosDTO2> listaContratoNEAllBusqueda(int idLocal, int IdTipoOrden, String palabraClave);
				 
				 
				 
				 
				 @Query(value = " SELECT tblDctosOrdenes.idLocal,                 "
			                + "    tblDctosOrdenes.idTipoOrden,                 "
			                + "    tblDctosOrdenes.idOrden,                     "
			                + "    tblDctosOrdenes.idLog,                       "
			                + "    tblDctosOrdenes.idCliente,                   "
			                + "    tblTerceros.nombreTercero,                   "
			                + "    tblDctosOrdenes.fechaOrden,                  "
			                + "    tblDctosOrdenes.idContrato,                  "
			                + "    tblDctosOrdenes.fechaInicioContrato,         "
			                + "    tblDctosOrdenes.fechaFinContrato,            "
			                + "    tblDctosOrdenes.vrSalarioBasico,             "
			                + "    tblDctosOrdenes.vrSubsidioTransporte,	    "
			                + "    tblDctosOrdenes.idMedio,	                    "
			                + "    tblDctosOrdenes.entidadMedio,	            "
			                + "    tblDctosOrdenes.cuentaMedio,	                "
			                + "    tblTerceros.idTercero,                       "
			                + "    tblTerceros.direccionTercero,                "
			                + "    tblTerceros.CC_Nit,                          "
			                + "    tblTerceros.telefonoCelular,                 "
			                + "     tmpTOT.IDPLU,                                "
			                + "     tmpTOT.NOMBREPLU ,                          "
			                + "	tmpTOT.idCategoria                            "
			                + " FROM tblDctosOrdenes                            "
			                + " INNER JOIN tblAgendaLogVisitas                  "
			                + " ON tblDctosOrdenes.idLocal =                    "
			                + "                    tblAgendaLogVisitas.idLocal  "
			                + " AND tblDctosOrdenes.idLog =                     "
			                + "                     tblAgendaLogVisitas.idLog "
			                + " LEFT JOIN (                                     "
			                + "     SELECT tblDctosOrdenesDetalle.idLocal,      "
			                + "         tblDctosOrdenesDetalle.idTipoOrden,     "
			                + "         tblDctosOrdenesDetalle.idOrden,         "
			                + "         tblDctosOrdenesDetalle.IDPLU,           "
			                + "	    tblDctosOrdenesDetalle.NOMBREPLU,       "
			                + "         tblPlus.idCategoria                     "
			                + "     FROM tblDctosOrdenesDetalle                 "
			                + "     INNER JOIN tblDctosOrdenes                  "
			                + "     ON tblDctosOrdenes.idLocal        =         "
			                + "                  tblDctosOrdenesDetalle.idLocal "
			                + "     AND tblDctosOrdenes.idTipoOrden     =       "
			                + "              tblDctosOrdenesDetalle.idTipoOrden "
			                + "     AND tblDctosOrdenes.idOrden     =           "
			                + "                  tblDctosOrdenesDetalle.idOrden "
			                + "     INNER JOIN tblPlus                          "
			                + "	ON tblPlus.idLocal =                        "
			                + "                  tblDctosOrdenesDetalle.idLocal "
			                + "	AND tblPlus.idPlu =                         "
			                + "         tblDctosOrdenesDetalle.idPlu) AS tmpTOT "
			                + "   ON tblDctosOrdenes.idLocal        =           "
			                + "                tmpTOT.idLocal                   "
			                + "   AND tblDctosOrdenes.idTipoOrden   =           "
			                + "            tmpTOT.idTipoOrden                   "
			                + "   AND tblDctosOrdenes.idOrden       =           "
			                + "                tmpTOT.idOrden                   "
			                + "   INNER JOIN tblTerceros                        "
			                + "   ON tblTerceros.idLocal            =           "
			                + "                     tblDctosOrdenes.idLocal     "
			                + "   AND tblTerceros.idCliente         =           "
			                + "                   tblDctosOrdenes.idCliente     "
			                + "  WHERE tblDctosOrdenes.idLocal   =              "
			                + "?1                                  "
			                + "  AND tblDctosOrdenes.idTipoOrden =              "
			                + "?2                              "
			                + "  AND tblDctosOrdenes.idCliente   =              "
			                + "?3                               "
			                + "  AND tblAgendaLogVisitas.estado  = 8            "
			                + "  AND tblTerceros.idTipoTercero   = 3  ",
			              nativeQuery = true)
				  List<TercerosDTO2> listaDetalleContratoFCH(int idLocal, int IdTipoOrden, String idCliente);
				 
				 
				 @Query(value = " SELECT tblDctosOrdenes.idLocal,                 "
			                + "    tblDctosOrdenes.idTipoOrden,                 "
			                + "    tblDctosOrdenes.idOrden,                     "
			                + "    tblDctosOrdenes.idLog,                       "
			                + "    tblDctosOrdenes.idCliente,                   "
			                + "    tblTerceros.nombreTercero,                   "
			                + "    tblDctosOrdenes.fechaOrden,                  "
			                + "    tblDctosOrdenes.idContrato,                  "
			                + "    tblDctosOrdenes.fechaInicioContrato,         "
			                + "    tblDctosOrdenes.fechaFinContrato,            "
			                + "    tblDctosOrdenes.vrSalarioBasico,             "
			                + "    tblDctosOrdenes.vrSubsidioTransporte,	    "
			                + "    tblDctosOrdenes.idMedio,	                    "
			                + "    tblDctosOrdenes.entidadMedio,	            "
			                + "    tblDctosOrdenes.cuentaMedio,	                "
			                + "    tblTerceros.idTercero,                       "
			                + "    tblTerceros.direccionTercero,                "
			                + "    tblTerceros.telefonoCelular,                 "
			                + "     tmpTOT.IDPLU,                                "
			                + "     tmpTOT.NOMBREPLU ,                          "
			                + "	tmpTOT.idCategoria                            "
			                + " FROM tblDctosOrdenes                            "
			                + " INNER JOIN tblAgendaLogVisitas                  "
			                + " ON tblDctosOrdenes.idLocal =                    "
			                + "                    tblAgendaLogVisitas.idLocal  "
			                + " AND tblDctosOrdenes.idLog =                     "
			                + "                     tblAgendaLogVisitas.idLog "
			                + " LEFT JOIN (                                     "
			                + "     SELECT tblDctosOrdenesDetalle.idLocal,      "
			                + "         tblDctosOrdenesDetalle.idTipoOrden,     "
			                + "         tblDctosOrdenesDetalle.idOrden,         "
			                + "         tblDctosOrdenesDetalle.IDPLU,           "
			                + "	    tblDctosOrdenesDetalle.NOMBREPLU,       "
			                + "         tblPlus.idCategoria                     "
			                + "     FROM tblDctosOrdenesDetalle                 "
			                + "     INNER JOIN tblDctosOrdenes                  "
			                + "     ON tblDctosOrdenes.idLocal        =         "
			                + "                  tblDctosOrdenesDetalle.idLocal "
			                + "     AND tblDctosOrdenes.idTipoOrden     =       "
			                + "              tblDctosOrdenesDetalle.idTipoOrden "
			                + "     AND tblDctosOrdenes.idOrden     =           "
			                + "                  tblDctosOrdenesDetalle.idOrden "
			                + "     INNER JOIN tblPlus                          "
			                + "	ON tblPlus.idLocal =                        "
			                + "                  tblDctosOrdenesDetalle.idLocal "
			                + "	AND tblPlus.idPlu =                         "
			                + "         tblDctosOrdenesDetalle.idPlu) AS tmpTOT "
			                + "   ON tblDctosOrdenes.idLocal        =           "
			                + "                tmpTOT.idLocal                   "
			                + "   AND tblDctosOrdenes.idTipoOrden   =           "
			                + "            tmpTOT.idTipoOrden                   "
			                + "   AND tblDctosOrdenes.idOrden       =           "
			                + "                tmpTOT.idOrden                   "
			                + "   INNER JOIN tblTerceros                        "
			                + "   ON tblTerceros.idLocal            =           "
			                + "                     tblDctosOrdenes.idLocal     "
			                + "   AND tblTerceros.idCliente         =           "
			                + "                   tblDctosOrdenes.idCliente     "
			                + "  WHERE tblDctosOrdenes.idLocal   =              "
			                + "?1                                  "
			                + "  AND tblDctosOrdenes.idTipoOrden =              "
			                + "?2                              "
			                + "  AND tblDctosOrdenes.idCliente   =              "
			                + "?3                               "
			                + "  AND tblAgendaLogVisitas.estado  = 8            "
			                + "  AND tblTerceros.idTipoTercero   = 3  "
			                + " AND tmpTOT.idCategoria = ?4                     ",
			              nativeQuery = true)
				  List<TercerosDTO2> listaDetalleContratoFCHPorCategoria(int idLocal, int IdTipoOrden, String idCliente, int idCategoria);
				 
				 
				 
				 
				 @Query(value = " SELECT tblterceros.idCliente,	        "
			                + "        tblterceros.idTercero,	        "
			                + "        tblterceros.tipoIdTercero,	   	"
			                + "        tblterceros.digitoVerificacion,   	"
			                + "        tblterceros.idTipoTercero, 	   	"
			                + "        tblterceros.idPersona, 	        "
			                + "        tblterceros.idAutoRetenedor, 	"
			                + "        tblterceros.idRegimen, 		"
			                + "        tblterceros.cupoCredito, 	        "
			                + "        tblterceros.nombreTercero, 		"
			                + "        tblterceros.direccionTercero, 	"
			                + "        tblterceros.idDptoCiudad, 		"
			                + "        tblterceros.telefonoFijo, 		"
			                + "        tblterceros.telefonoCelular, 	"
			                + "        tblterceros.telefonoFax, 		"
			                + "        tblterceros.email, 			"
			                + "        tblterceros.idFormaPago, 		"
			                + "        tblterceros.estado, 			"
			                + "        tblregimen.nombreRegimen, 		"
			                + "        tbltipotercero.nombreTipoTercero, 	"
			                + "        tblciudades.nombreCiudad, 		"
			                + "        tblciudades.nombreDpto,    	        "
			                + "        tblterceros.idVendedor,              "
			                + "        tblterceros.CC_Nit,                  "
			                + "       (CASE                                 "
			                + "        WHEN ctrlusuarios.nombreUsuario      "
			                + "                                  IS NULL    "
			                + "        THEN 'NO ASIGNADO'                   "
			                + "        ELSE ctrlusuarios.nombreUsuario      "
			                + "        END)AS nombreVendedor                "
			                + " FROM tblregimen, 				"
			                + "      tbltipotercero, 			"
			                + "      tblciudades,				"
			                + "      tblterceros                            "
			                + " LEFT JOIN ctrlusuarios                      "
			                + " ON ctrlusuarios.idUsuario =                 "
			                + "                      tblterceros.idVendedor "
			                + " WHERE tblterceros.nombreTercero   LIKE     ("
			                + "?1 )                       "
			                + " AND   tblterceros.idTipoTercero =           "
			                + "               tbltipotercero.idTipoTercero  "
			                + " AND   tblterceros.idRegimen     =           "
			                + "                      tblregimen.idRegimen   "
			                + " AND   tblterceros.idDptoCiudad              "
			                + "                     = tblciudades.idCiudad	"
			                + " AND   tblterceros.idTipoTercero =   ?2        "
			                + " AND   tblterceros.idLocal =           ?3      "
			                + " ORDER BY  tblterceros.nombreTercero",
			              nativeQuery = true)
				  List<TercerosDTO2> seleccionaTerceroProveedorxNombreIdTipoTercero(String NombreTercero, int idTipoTercero,  int idLocal);
				 
				 
				 
				 @Query(value = " SELECT MAX(tblterceros.CC_Nit)          "
						 + "                      AS CC_Nit         "
						 + "   ,MAX(tblterceros.tipoIdTercero)      "
						 + "                  AS tipoIdTercero      "
						 + "   ,MAX(tblterceros.nombreTercero)      "
						 + "                  AS nombreTercero      "
						 + "   ,MAX([direccionTercero])             "
						 + "               AS direccionTercero      "
						 + "   ,MAX(tblCiudades.nombreCiudad)       "
						 + "                  AS nombreCiudad       "
						 + "   ,MAX(tblterceros.telefonoFijo)       "
						 + "                  AS telefonoFijo       "
						 + "   ,MAX(tblterceros.idDptoCiudad)       "
						 + "                  AS idDptoCiudad       "
						 + "   ,'S'  AS estadoNuid                  "
						 + "   ,'N'  AS tieneRut                    "
						 + "   ,169  AS paisTercero                 "
						 + "   ,'' AS primerNombre                  "
						 + "   ,'' AS segundoNombre                 "
						 + "   ,'' AS primerApellido                "
						 + "   ,'' AS segundoApellido               "
						 + "   ,MAX(tblterceros.email) AS email     "
						 + "   ,MAX(tblterceros.telefonoCelular)    "
						 + "                  AS telefonoCelular    "
						 + "   ,0 AS idFormaPago                    "
						 + "   ,'' AS actividadEconomica            "
						 + "   ,'' AS indicativo                    "
						 + "   ,'N' AS naturaleza                   "
						 + "  FROM tbldctos                         "                           
						 + "  INNER JOIN tbldctosordenes            "                           
						 + "   ON tbldctosordenes.idLocal         = " 
						 + "                       tbldctos.idLocal "              
						 + "   AND tbldctosordenes.idTipoOrden    = "
						 + "                   tbldctos.idTipoOrden "    
						 + "   AND tbldctosordenes.idOrden        = "
						 + "                       tbldctos.idOrden "           
						 + "   AND tbldctosordenes.idCliente      = "
						 + "                     tbldctos.idCliente "     
						 + "   INNER JOIN  tbldctosordenesdetalle   "                   
						 + "   ON tbldctosordenesdetalle.idLocal  = " 
						 + "                       tbldctos.idLocal "  
						 + " AND tbldctosordenesdetalle.idTipoOrden "
						 + "                                      = "
						 + " 	              tbldctos.idTipoOrden "   
						 + "   AND tbldctosordenesdetalle.idOrden = "
						 + "                       tbldctos.idOrden "         
						 + "   INNER JOIN tblterceros               "                     
						 + "   ON tblterceros.IDLOCAL             = "
						 + "         tbldctosordenesdetalle.IDLOCAL "  
						 + "   AND tblterceros.idCliente          = " 
						 + "       tbldctosordenesdetalle.idCliente " 
						 + "   INNER JOIN tblCiudades               "                   
						 + "   ON tblterceros.idDptoCiudad        = "
						 + "                   tblCiudades.idCiudad "
						 + "   WHERE tblterceros.idlocal          = "   
						 + "?1                         "        
						 + "   AND tblterceros.idTipoTercero      = " 
						 + "?2                   "                   
						 + "  AND tbldctos.idPeriodo   = ?3         "
						 + "  GROUP BY tblterceros.idCliente ",
			              nativeQuery = true)
				  List<TercerosDTO2> listaTercerosILimitada(int idLocal, int idtipoTercero, int idPeriodo);
				 
				 
				 @Query(value = "SELECT 'SI' AS resPositiva                                        "
			                + "	 ,'NO' AS resNegativa                                        "
			                + "	,SUBSTRING(MAX(tblterceros.nombreTercero), 1,                "
			                + "         CASE WHEN                                                "
			                + "         CHARINDEX(' ',MAX(tblterceros.nombreTercero)) - 1 < 0    "
			                + "         THEN LEN(MAX(tblterceros.nombreTercero))                 "
			                + "      ELSE CHARINDEX(' ',MAX(tblterceros.nombreTercero)) - 1 END) "
			                + "                			                  AS nombres "
			                + "     ,LTRIM(RIGHT(MAX(tblterceros.nombreTercero),                 "
			                + "            LEN(MAX(tblterceros.nombreTercero)) -                 "
			                + "            CHARINDEX(' ',MAX(tblterceros.nombreTercero)) ))      "
			                + "                				        AS apellidos "
			                + "        ,13 AS codTipoId                                          "
			                + "       ,tblterceros.idCliente AS identificacion                   "
			                + "        ,MAX([digitoVerificacion]) AS   [digitoVerificacion]      "
			                + "        ,MAX([direccionTercero]) AS direccion                     "
			                + "        ,'CO' AS codPais                                          "
			                + "         ,MAX([idDptoCiudad]) AS codCiudad                        "
			                + "         ,999 AS indiceTelExt                                     "
			                + "        ,MAX([telefonoFax]) AS telFax                             "
			                + "       ,MAX(tblterceros.[email]) AS correoElectronico             "
			                + "      ,''  AS observacion                                         "
			                + "       ,'' AS textoVacio                                          "
			                + "	  ,0 AS tipoRegIva                                           "
			                + " FROM tbldctos                                                    "
			                + " INNER JOIN tbldctosordenes                                       "
			                + "  ON tbldctosordenes.idLocal =  tbldctos.idLocal               "
			                + "  AND tbldctosordenes.idTipoOrden = tbldctos.idTipoOrden     "
			                + "  AND tbldctosordenes.idOrden = tbldctos.idOrden            "
			                + "  AND tbldctosordenes.idCliente = tbldctos.idCliente      "
			                + "  INNER JOIN  tbldctosordenesdetalle                      "
			                + "  ON tbldctosordenesdetalle.idLocal =  tbldctos.idLocal   "
			                + "  AND tbldctosordenesdetalle.idTipoOrden = tbldctos.idTipoOrden    "
			                + "  AND tbldctosordenesdetalle.idOrden = tbldctos.idOrden          "
			                + "  INNER JOIN tblterceros                                    "
			                + "  ON tblterceros.IDLOCAL = tbldctosordenesdetalle.IDLOCAL   "
			                + "  AND tblterceros.idCliente =  tbldctosordenesdetalle.idCliente  "
			                + "  WHERE tblterceros.idlocal =                               "
			                + "?1                                             "
			                + "  AND tblterceros.idTipoTercero =                           "
			                + "?2                                      "
			                + "  AND tbldctos.idPeriodo   = ?3         "
			                + "  AND LEN(tblterceros.nombreTercero) -                      "
			                + "	          CHARINDEX(' ',tblterceros.nombreTercero)>=0  "
			                + " GROUP BY tblterceros.idCliente",
			              nativeQuery = true)
				  List<TercerosDTO2> listaTercerosSiigo(int idLocal, int idtipoTercero, int idPeriodo);
				 
				 
				 
				 
				 @Modifying
				  @Transactional
				  @Query(value = "UPDATE tblterceros " +
				                 "SET  estadoEmail =2 " +
				                 "FROM tblterceros " +
				                 "WHERE tblterceros.idLocal = ?1 ", nativeQuery = true)
				  public void actualizaEstadoEmailInactivo(int idLocal);
				 
				 
				 
				 @Modifying
				  @Transactional
				  @Query(value = "   UPDATE tblTerceros                                             " 
						  + "  set estadoEmail =1                                              " 
						  + "  WHERE idLocal= ?1                                               " 
						  + "  AND  idTipoTErcero=1                                            " 
						  + "  AND NOT (( PATINDEX ('%[&'',\":;!+=\\/()<>]*%', email) > 0        "
						  + "  OR PATINDEX ('[@.-_]%', email) > 0                              " 
						  + "  OR PATINDEX ('%[@.-_]', email) > 0                              " 
						  + "  OR EMAIL NOT LIKE '%@%.%'                                       " 
						  + "  OR EMAIL LIKE '%..%'                                            " 
						  + "  OR EMAIL LIKE '%@%@%'                                           " 
						  + "  OR EMAIL LIKE '%.@%'                                            " 
						  + "  OR EMAIL LIKE '%.cm'                                            " 
						  + "  OR PATINDEX('%á%',email)>0                                      " 
						  + "  OR PATINDEX('%é%',email)>0                                      " 
						  + "  OR PATINDEX('%í%',email)>0                                      " 
						  + "  OR PATINDEX('%ó%',email)>0                                      " 
						  + "  OR PATINDEX('%ú%',email)>0                                      " 
						  + "  OR PATINDEX('%ñ%',email)>0                                      " 
						  + "  OR EMAIL LIKE '%nn@gmail.com%'                                  " 
						  + "  OR EMAIL LIKE '%.or'))                                          ",
						  nativeQuery = true)
				  public void actualizaEstadoEmailOK(int idLocal);
				 
				 
				 
				 
				  @Modifying
				  @Transactional
				  @Query(value = "UPDATE tblterceros " +
				                 "SET  estadoWhatsApp =2 " +
				                 "FROM tblterceros " +
				                 "WHERE tblterceros.idLocal = ?1 ", nativeQuery = true)
				  public void actualizaEstadoWhatsAppInactivo(int idLocal);
				  
				  
				  
				  @Modifying
				  @Transactional
				  @Query(value = "   UPDATE tblTerceros                                " 
						  + "  set estadoWhatsApp =1                                   " 
						  + "  WHERE idLocal= ?1                                       " 
						  + "  AND  idTipoTErcero=1                                    " 
						  + "  AND telefonoCelular IS NOT NULL                         "
						  + "  AND LEN(telefonoCelular) = 10                           " 
						  + "  AND telefonoCelular NOT LIKE '%[^0-9]%'                 " 
						  + "  AND PATINDEX('%[^0-9]%', telefonoCelular) = 0           ",
						  nativeQuery = true)
				  public void actualizaEstadoWhatsAppOK(int idLocal);
				  
				  
				  
				  @Query(value = "SELECT tblTerceros.estadoWhatsApp " +
							"FROM bdaquamovil.dbo.tblTerceros " +
							"WHERE tblTerceros.idLocal = ?1 " +
							"AND tblTerceros.idtipotercero = 1 " +
							"AND tblTerceros.idCliente = ?2 ",
			              nativeQuery = true)
				  Integer obtenerEstadoWppSuscriptor(int idLocal,  String idCliente);
				  
				  
				  @Query(value = "SELECT tblTerceros.* " +
							"FROM bdaquamovil.dbo.tblTerceros " +
							"WHERE tblTerceros.idLocal = ?1 " +
							"AND tblTerceros.idtipotercero = 1 " +
							"AND tblTerceros.idCliente = ?2 " +
							"AND tblTerceros.CC_Nit = ?3 " +
							"AND tblTerceros.telefonoCelular = ?4 ",
			              nativeQuery = true)
				  List<TercerosDTO2> obtenerSusciptor(int idLocal,  String idCliente, String CC_Nit, String telefonoCelular);
				 
				  
				  
				  @Query(value = " SELECT tmpTER.idCliente                  "
			                + "   ,MAX(tmpTER.nombreTercero)             "
			                + "                   AS nombreTercero       "
			                + "   ,MAX(tmpTER.direccionTercero)          "
			                + "                AS direccionTercero       "
			                + "   ,MAX(tmpTER.telefonoFijo)              "
			                + "                    AS telefonoFijo       "
			                + "  ,MAX(tmpTER.ordenSalida)                "
			                + "             AS ordenSalida               "
			                + "   FROM                                   "
			                + "   (SELECT [idLocal] AS idLocal,          "
			                + " 	  [idCliente] AS idCliente           "
			                + "  ,[nombreTercero] AS nombreTercero       "
			                + "  ,[direccionTercero] AS direccionTercero "
			                + "  ,[telefonoFijo] AS telefonoFijo         "
			                + "         ,02   AS ordenSalida             "
			                + "     FROM tblterceros                     "
			                + "     WHERE tblterceros.idLocal =          "
			                + "?1                          "
			                + "   UNION                                  "
			                + "   SELECT [idLocal] AS idLocal            "
			                + "         ,[idUsuario]  AS idCliente       "
			                + "   ,[nombreUsuario]  AS nombreTercero     "
			                + "   ,[direccion] AS direccionTercero       "
			                + "         ,[telefono] AS telefonoFijo      "
			                + "         ,03   AS ordenSalida             "
			                + "   FROM ctrlusuarios                      "
			                + "   WHERE ctrlusuarios.idLocal  =          "
			                + "?1  )          AS tmpTER     "
			                + "   WHERE tmpTER.idCliente      =          "
			                + "?2                        "
			                + "   AND    tmpTER.idLocal =                "
			                + "?1                                        "
			                + "   GROUP BY tmpTER.idCliente              "
			                + "   ORDER BY 5, 2   ",
			              nativeQuery = true)
				  List<TercerosDTO2> listaUnTerceroUnionFCH(int idLocal,  String idCliente);
				  
				  
				  @Query(value = " SELECT ordenRuta                 "
						         +" FROM bdaquamovil.dbo.tblTerceros "
						         +" where idLocal= ?1  "
						         +" and idCliente = ?2 "
						         +" and idTipoTercero=1",
			              nativeQuery = true)
				  Integer ObtenerOrdenRutaPorCliente(int idLocal,  String idCliente);
				 
}
