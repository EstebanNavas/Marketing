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
				  		"fechaInstalacionMedidor = ?16, codigoAlterno = ?17, tipoSuscriptor = ?18, matricula = ?19, estado = ?20, estadoCorte = ?21, promedio = ?22 "+
		                 "WHERE tblTerceros.idLocal = ?23 " +
		                 "AND tblTerceros.idCliente = ?24 " +
		                 "AND tblTerceros.idTipoTercero = ?25 ", nativeQuery = true)
		  public void actualizarTercero(String nombreTercero,  String direccionTercero, String direccionCobro, int idDptoCiudad, String telefonoFijo,
					String telefonoCelular, String email, int idRuta, int idEstracto, String CC_Nit, String numeroMedidor, int idMedidor, int idMacro, 
					String codigoCatastral, java.sql.Timestamp fechaIngreso, java.sql.Timestamp fechaDeInstalacion, String codigoAlterno, int tipoSuscriptor, String matricula, int estadoSuscriptorInt, int estadoCorteInt, Double promedio,  int idLocal, String idCliente, int idTipoTercero) ;
		
		
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
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
			  
		
		
}
