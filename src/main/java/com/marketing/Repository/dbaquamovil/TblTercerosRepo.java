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
	            "WHERE t.idLocal = 142 "+
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
		
		@Query(value = "SELECT DISTINCT t.idLocal, t.nombreTercero, t.idCliente, t.idTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 3 " +
				"AND tcn.idTipoTabla = 3 " +
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO2> ListaTercerosEmpleados(int idLocal);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idCliente ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
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
				"AND (t.nombreTercero LIKE %?2% OR CAST(t.CC_Nit AS VARCHAR(20)) LIKE %?2%)" + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosEmpleados(int idLocal, String palabraClave);
		
		@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, t.direccionTercero, tcn.nombreCausa, t.telefonoCelular " + 
				"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
				"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
				"WHERE t.idLocal = ?1 " +
				"AND t.idTipoTercero = 2 " +
				"AND tcn.idTipoTabla = 3 " +
				"AND (t.nombreTercero LIKE %?2% OR CAST(t.CC_Nit AS VARCHAR(20)) LIKE %?2%)" + 
				"ORDER BY t.nombreTercero ",
				nativeQuery = true)
		List<TercerosDTO> BuscarTercerosProveedor(int idLocal, String palabraClave);
		
		
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
				  		"fechaInstalacionMedidor = ?16, codigoAlterno = ?17, tipoSuscriptor = ?18, matricula = ?19, estado = ?20, estadoCorte = ?21, promedio = ?22, estadoEmail = ?23 "+
		                 "WHERE tblTerceros.idLocal = ?24 " +
		                 "AND tblTerceros.idCliente = ?25 " +
		                 "AND tblTerceros.idTipoTercero = ?26 ", nativeQuery = true)
		  public void actualizarTercero(String nombreTercero,  String direccionTercero, String direccionCobro, int idDptoCiudad, String telefonoFijo,
					String telefonoCelular, String email, int idRuta, int idEstracto, String CC_Nit, String numeroMedidor, int idMedidor, int idMacro, 
					String codigoCatastral, java.sql.Timestamp fechaIngreso, java.sql.Timestamp fechaDeInstalacion, String codigoAlterno, int tipoSuscriptor, String matricula, int estadoSuscriptorInt, int estadoCorteInt, Double promedio, int estadoEmail,  int idLocal, String idCliente, int idTipoTercero) ;
		
		
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
		  
		  
		  
		  
			@Query(value = "SELECT DISTINCT t.idLocal ,t.idTercero ,t.nombreTercero, te.idEstracto, t.direccionTercero, tr.nombreRuta, te.nombreEstracto, tcn.nombreCausa, t.telefonoCelular, t.ordenRuta, t.CC_Nit, t.idCliente, t.idRuta " + 
					"FROM [bdaquamovil].[dbo].[tblTerceros] t " +
					"JOIN [bdaquamovil].[dbo].[tblTerceroEstracto] te ON t.idLocal = te.idLocal AND t.idEstracto = te.idEstracto " +
					"JOIN [bdaquamovil].[dbo].[tblTercerosRuta] tr ON t.idLocal = tr.idLocal AND t.idRuta = tr.idRuta " +
					"JOIN [bdaquamovil].[dbo].[tblTipoCausaNota] tcn ON t.estado = tcn.estado AND t.estado = tcn.idCausa " +
					"WHERE t.idLocal = ?1 " +
					"AND t.idTipoTercero = 1 " +
					"AND tr.idRuta = ?2 " + 
					"AND ISNUMERIC(t.telefonoCelular) = 1 " +
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
		                + "            tblterceros.idCliente)  "
		                + " ORDER BY tblterceros.idRuta ,     "
		                + "          tblterceros.ordenRuta ",
						nativeQuery = true)
				List<TercerosDTO> listaUnCliente(int idLocal, int idPeriodo, List<String> idCliente);
			  
			  
			  
				@Query(value = "SELECT tblTerceros.idCliente " + 
						"FROM bdaquamovil.dbo.tblTerceros " +
						"WHERE tblTerceros.idLocal = ?1 " +
						"AND tblTerceros.idRuta = ?2 " +
						"AND tblTerceros.estadoEmail = ?3",
						nativeQuery = true)
				List<String> ObtenerListaTercerosEstadoEmail(int idLocal, int idRuta, int estadoEmail);
				
				
				
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
		                + " AND tmpACT.lecturaActual =  0                "
		                + " AND tmpANT.lecturaAnterior > 0               "
		                + " AND tmpCAU.idCausal IN (0,99)                "
		                + " ORDER BY tbltercerosruta.idRuta,             "
		                + "           tblterceros.ordenRuta,             "
		                + "           tblterceros.idCliente"
		               ,
						nativeQuery = true)
				List<TercerosDTO2> listaCriticaInconsistencia(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta);
				
				
				
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
				
}
