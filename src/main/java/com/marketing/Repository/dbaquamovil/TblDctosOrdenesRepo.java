package com.marketing.Repository.dbaquamovil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblDctosOrdenes;
import com.marketing.Projection.ReporteSuiDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;

public interface TblDctosOrdenesRepo extends JpaRepository<TblDctosOrdenes, Integer> {

	 @Query("SELECT MAX(r.IDORDEN) FROM TblDctosOrdenes r "+
			 "WHERE r.IDLOCAL = ?1 " +
			 "AND r.IDTIPOORDEN  IN (17, 67) ")
	    Integer findMaxIDORDEN(int idLocal);
	 
	// Modificamos el IDTIPOORDEN de 67 a 17
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblDctosOrdenes SET IDTIPOORDEN = 17 " +
		                 "WHERE IDTIPOORDEN = 67 AND tblDctosOrdenes.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenes.IDUSUARIO = ?2 " +
		                 "AND tblDctosOrdenes.IDORDEN = ?3", nativeQuery = true)
		  public void actualizarIdTipoOrden(int idLocal, int IDUSUARIO, int IDORDEN);
		  
		  // Obtener el IDTIPOORDEN, el IDUSUARIO y el IDORDEN del IDORDEN Maximo
		  @Query( value = "SELECT * " +
				  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
				  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
				  		  "AND tblDctosOrdenes.IDUSUARIO = ?2 " +
				  		  "AND IDORDEN = (SELECT MAX(IDORDEN) FROM bdaquamovil.dbo.tblDctosOrdenes WHERE IDLOCAL = ?1 AND IDUSUARIO = ?2)", nativeQuery = true)
		  List<TblDctosOrdenesDTO> ObtenerIdTipoOrdenAndIdUsuarioAndIdOrden(int IDLOCAL, int IDUSUARIO);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.idCliente " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.IDUSUARIO = ?2 " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 67 "+
		  		  "AND IDORDEN = (SELECT MAX(IDORDEN) FROM bdaquamovil.dbo.tblDctosOrdenes WHERE IDLOCAL = ?1 AND IDUSUARIO = ?2)", nativeQuery = true)
		  Integer ObtenerIdCliente(int IDLOCAL, int IDUSUARIO);
		  
		  
		// Eliminamos los registros del IDORDEN de ese momento
		  @Modifying
		  @Transactional
		  @Query(value = "DELETE FROM bdaquamovil.dbo.tblDctosOrdenes " +
		                 "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenes.IDORDEN = ?2 " +
		                 "AND tblDctosOrdenes.IDTIPOORDEN = 67", nativeQuery = true)
		  public void eliminarRegistrosOrdenes(int idLocal, int IDORDEN);
		  
		  
		  @Query( value = "SELECT DISTINCT tblDctosOrdenes.idCliente " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 " +
		  		  "AND tblDctosOrdenes.ESTADO = 0 "
		  		  , nativeQuery = true)
		  List <String> ObtenerIdClienteIdTipoOrden17(int IDLOCAL);
		  
		  @Query( value = "SELECT DISTINCT tblDctosOrdenes.numeroOrden " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 " +
		  		  "AND tblDctosOrdenes.idCliente = ?2 " +
		  		  "AND tblDctosOrdenes.ESTADO = 0 "
		  		  , nativeQuery = true)
		  List <String> ObtenerListaIdOrden(int IDLOCAL, String idCliente);
		  
		  @Query( value = "SELECT tblDctosOrdenes.IDLOG " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " + 
		  		  "AND tblDctosOrdenes.IDUSUARIO = ?2 " +
		  		  "AND tblDctosOrdenes.numeroOrden = ?3 "+
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 67 "
		  		  , nativeQuery = true)
		  Integer ObtenerIdLog(int IDLOCAL, Integer IDUSUARIO, int numeroOrden);
		  
		  @Query( value = "SELECT tblDctosOrdenes.IDLOG " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " + 
		  		  "AND tblDctosOrdenes.idCliente = ?2 " +
		  		  "AND tblDctosOrdenes.IDORDEN = ?3 "+
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 "
		  		  , nativeQuery = true)
		  Integer ObtenerIdLog17(int IDLOCAL, String idCliente, int IDORDEN);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.idCliente " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 " +
		  		  "AND tblDctosOrdenes.IDORDEN = ?2"
		  		  , nativeQuery = true)
		  Integer ObtenerIdClientePorIdORden(int IDLOCAL, int IDORDEN);
		  
		// Modificamos el ESTADO a 1 Cuando se finaliza por completo la pqr 
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblDctosOrdenes SET ESTADO = 1 " +
		                 "WHERE IDTIPOORDEN = 17 AND tblDctosOrdenes.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenes.IDORDEN = ?2 ", nativeQuery = true)
		  public void actualizarEstadoA1(int idLocal, int IDORDEN);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.FECHAORDEN " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 " +
		  		  "AND tblDctosOrdenes.IDORDEN = ?2"
		  		  , nativeQuery = true)
		  String ObtenerFechaRadicacion(int IDLOCAL, int IDORDEN);
		  
		  
		  @Query("SELECT MAX(r.numeroOrden) FROM TblDctosOrdenes r "+
					 "WHERE r.IDLOCAL = ?1 " +
					 "AND r.IDTIPOORDEN = 17 ")
			    Integer findMaxNumeroOrden(int idLocal);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.IDORDEN " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " + 
		  		  "AND tblDctosOrdenes.numeroOrden = ?2 "+
		  		  "AND tblDctosOrdenes.idCliente = ?3 "+
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 "
		  		  , nativeQuery = true)
		  Integer ObtenerIdOrden(int IDLOCAL,  int numeroOrden, String idCliente);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.IDORDEN " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " + 
		  		  "AND tblDctosOrdenes.IDLOG = ?2 "+
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 67 "
		  		  , nativeQuery = true)
		  Integer ObtenerIdOrdenDelIdLog(int IDLOCAL,  int idLog);
		  
		  @Query( value = "SELECT tblDctosOrdenes.numeroOrden " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 "+
		  		  "AND tblDctosOrdenes.IDORDEN = ?2 ", nativeQuery = true)
		  Integer ObtenerNumeroOrden(int IDLOCAL, int IDORDEN);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.IDTIPOORDEN " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.IDORDEN = ?2 " + 
		  		  "AND tblDctosOrdenes.IDLOG = ?3 ", nativeQuery = true)
		  Integer ObtenerTipoOrden(int IDLOCAL, int IDORDEN, int IDLOG);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.IDTIPOORDEN " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.idCliente = ?2 " + 
		  		  "AND tblDctosOrdenes.IDUSUARIO = ?3 " +
		  		  "AND IDTIPOORDEN = 67 ", nativeQuery = true)
		  Integer ObtenerTipoOrdenCliente(int IDLOCAL, String idCliente, int IDUSUARIO);
		  
		  
			// Actualizamos la FECHAENTREGA del IDORDEN
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblDctosOrdenes SET FECHAENTREGA = ?3 " +
		                 "WHERE IDTIPOORDEN = 17 AND tblDctosOrdenes.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenes.IDORDEN = ?2 ", nativeQuery = true)
		  public void actualizarFECHAENTREGA(int idLocal, int IDORDEN, Timestamp FECHAENTREGA);
	
		  
		  @Query( value = "SELECT tblDctosOrdenes.ordenCompra " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 67 "+
		  		  "AND tblDctosOrdenes.IDORDEN = ?2 ", nativeQuery = true)
		  String ObtenerOrdenCompra(int IDLOCAL, int IDORDEN);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.numeroOrden " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " + 
		  		  "AND tblDctosOrdenes.IDORDEN IN ?2 "+
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 " +
		  		 "AND   CONVERT(VARCHAR(10), tblDctosOrdenes.FECHAORDEN, 23) " +
		  		  "BETWEEN ?3 AND  ?4 "
		  		  , nativeQuery = true)
		  List<Integer> ObtenerListaNumeroOrden(int IDLOCAL, List<Integer> IDORDEN, String fechaInicial, String fechaFinal );
		  
		  
		  @Query( value = "SELECT DISTINCT tblDctosOrdenes.idCliente " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
				  "JOIN bdaquamovil.dbo.tblDctos " +
		  		  "ON tblDctosOrdenes.IDLOCAL = tblDctos.IDLOCAL " + 
				  "AND tblDctosOrdenes.IDTIPOORDEN = tblDctos.IDTIPOORDEN " +
		  		  "AND tblDctosOrdenes.IDORDEN = tblDctos.IDORDEN " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " + 
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 " +
		  		  "AND   CONVERT(VARCHAR(10), tblDctosOrdenes.FECHAORDEN, 23) " +
		  		  "BETWEEN ?2 AND  ?3 "
		  		  , nativeQuery = true)
		  List<String> ObtenerListaClientesFecha(int IDLOCAL,  String fechaInicial, String fechaFinal );
		  
		  
		  
		  @Query( value = "SELECT tblCiudades.idDpto, " +
		  		  "CASE " +
		  		  "WHEN  tblLocales.idDptoCiudad<=9999 THEN SUBSTRING(LTRIM(RTRIM(STR(tblLocales.idDptoCiudad))), 2, 5) " +
		  		  "ELSE SUBSTRING(LTRIM(RTRIM(STR(tblLocales.idDptoCiudad))), 3, 6) " +
		  		  "END AS idCiudad, " +
		  		  "0 AS tipoAcentamiento, " +
		  		  "tblDctosOrdenes.numeroOrden, " +
		  		  "CONVERT(VARCHAR(12), tblDctosOrdenes.FECHAORDEN, 34) AS fechaRadicacion, " + 
		  		  "tmpTRAMITE.tipoTramite, " +
		  		  "tmpCAUSA.nombreCausa, " +
		  		  "'000' AS codigoCausaResolucion, " +
		  		  "tblDctosOrdenes.idCliente, " +
		  		  "tblDctosOrdenes.ordenCompra, " +
		  		  "tmpRESPUESTA.tipoRespuesta, " +
		  		  "CONVERT(VARCHAR(12), tblDctos.fechaDcto, 34) AS fechaDcto, " +
		  		  "tblDctos.idDcto, " +
		  		  "CONVERT(VARCHAR(12), tblDctos.fechaDcto, 34) AS fechaEjecucion, " +
		  		  "tmpNOTIFICACION.tipoNotificacion, " +
		  		  "CONVERT(VARCHAR(12), tblDctosOrdenes.FECHAENTREGA, 34) AS FECHAENTREGA " +
		  		  
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "JOIN bdaquamovil.dbo.tblLocales " +
		  		  "ON tblDctosOrdenes.IDLOCAL = tblLocales.idLocal " +
		  		  
		  		  "JOIN bdaquamovil.dbo.tblCiudades " +
		  		  "ON tblLocales.idDptoCiudad  = tblCiudades.IDCIUDAD " +
		  		  
		  		  "JOIN ( SELECT 	   tblDctosOrdenesDetalle.IDLOCAL, " +
		  		  "tblDctosOrdenesDetalle.IDTIPOORDEN, " +
		  		  "tblDctosOrdenesDetalle.IDORDEN, " +
		  		  "tblPlus.vrGeneral AS tipoTramite " +
		  		  "FROM  bdaquamovil.dbo.tblDctosOrdenesDetalle " +
		  		  "JOIN  bdaquamovil.dbo.tblPlus " +
		  		  "ON tblDctosOrdenesDetalle.IDLOCAL = tblPlus.idLocal " +
		  		  "AND tblDctosOrdenesDetalle.IDPLU = tblPlus.idPlu " +
		  		  "WHERE tblPlus.idLinea = 200 " +
		  		  "AND tblPlus.idCategoria = 12 ) AS tmpTRAMITE " +
		  		  "ON tmpTRAMITE.IDLOCAL = tblDctosOrdenes.IDLOCAL " +
		  		  "AND tmpTRAMITE.IDTIPOORDEN = tblDctosOrdenes.IDTIPOORDEN " +
		  		  "AND tmpTRAMITE.IDORDEN = tblDctosOrdenes.IDORDEN " +
		  		  
		  		  "JOIN (	  SELECT tblDctosOrdenesDetalle.IDLOCAL, " +
		  		  "tblDctosOrdenesDetalle.IDTIPOORDEN, " +
		  		  "tblDctosOrdenesDetalle.IDORDEN, " +
		  		  "SUBSTRING(tblPlus.nombrePlu,1,1) AS nombreCausa " +
		  		  "FROM  bdaquamovil.dbo.tblDctosOrdenesDetalle " +
		  		  "JOIN  bdaquamovil.dbo.tblPlus " +
		  		  "ON tblDctosOrdenesDetalle.IDLOCAL = tblPlus.idLocal " +
		  		  "AND tblDctosOrdenesDetalle.IDPLU = tblPlus.idPlu " +
		  		  "WHERE tblPlus.idLinea = 200 " +
		  		  "AND tblPlus.idCategoria = 11	) AS tmpCAUSA " +
		  		  "ON tmpCAUSA.IDLOCAL = tblDctosOrdenes.IDLOCAL " +
		  		  "AND tmpCAUSA.IDTIPOORDEN = tblDctosOrdenes.IDTIPOORDEN " +
		  		  "AND tmpCAUSA.IDORDEN = tblDctosOrdenes.IDORDEN " +
		  		  
		  		  "JOIN ( SELECT 	   tblDctosOrdenesDetalle.IDLOCAL, " +
		  		  "tblDctosOrdenesDetalle.IDTIPOORDEN, " +
		  		  "tblDctosOrdenesDetalle.IDORDEN, " +
		  		  "tblPlus.vrGeneral AS tipoRespuesta " +
		  		  "FROM  bdaquamovil.dbo.tblDctosOrdenesDetalle " +
		  		  "JOIN  bdaquamovil.dbo.tblPlus " +
		  		  "ON tblDctosOrdenesDetalle.IDLOCAL = tblPlus.idLocal " +
		  		  "AND tblDctosOrdenesDetalle.IDPLU = tblPlus.idPlu " +
		  		  "WHERE tblPlus.idLinea = 200 " +
		  		  "AND tblPlus.idCategoria = 15 ) AS tmpRESPUESTA " +
		  		  "ON tmpRESPUESTA.IDLOCAL = tblDctosOrdenes.IDLOCAL " +
		  		  "AND tmpRESPUESTA.IDTIPOORDEN = tblDctosOrdenes.IDTIPOORDEN " +
		  		  "AND tmpRESPUESTA.IDORDEN = tblDctosOrdenes.IDORDEN " +
		  		  
		  		  
		  		  "JOIN ( SELECT 	   tblDctosOrdenesDetalle.IDLOCAL, " +
		  		  "tblDctosOrdenesDetalle.IDTIPOORDEN, " +
		  		  "tblDctosOrdenesDetalle.IDORDEN, " +
		  		  "tblPlus.vrGeneral AS tipoNotificacion " +
		  		  "FROM  bdaquamovil.dbo.tblDctosOrdenesDetalle " +
		  		  "JOIN  bdaquamovil.dbo.tblPlus " +
		  		  "ON tblDctosOrdenesDetalle.IDLOCAL = tblPlus.idLocal " +
		  		  "AND tblDctosOrdenesDetalle.IDPLU = tblPlus.idPlu " +
		  		  "WHERE tblPlus.idLinea = 200 " +
		  		  "AND tblPlus.idCategoria = 16 ) AS tmpNOTIFICACION " +
		  		  "ON tmpNOTIFICACION.IDLOCAL = tblDctosOrdenes.IDLOCAL " +
		  		  "AND tmpNOTIFICACION.IDTIPOORDEN = tblDctosOrdenes.IDTIPOORDEN " +
		  		  "AND tmpNOTIFICACION.IDORDEN = tblDctosOrdenes.IDORDEN " +
		  		  
		  		  "JOIN bdaquamovil.dbo.tblDctos " +
		  		  "ON tblDctosOrdenes.IDLOCAL  = tblDctos.IDLOCAL " +
		  		  "AND tblDctosOrdenes.IDORDEN  = tblDctos.IDORDEN " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN  = tblDctos.IDTIPOORDEN " +
		  		  
		  		  "WHERE tblDctosOrdenes.idLocal= ?1 " +
		  		  "AND   tblDctosOrdenes.IDTIPOORDEN = 17 " +
		  		  "AND   CONVERT(VARCHAR(10), tblDctosOrdenes.FECHAORDEN, 23) " +
		  		  "BETWEEN ?2 AND  ?3 "
		  		  , nativeQuery = true)
		  List<ReporteSuiDTO> ObtenerReporteSUI(int idLocal, String fechaInicial, String fechaFinal);
		  
		  
		  
		  @Modifying
		  @Transactional
		  @Query(value = "DELETE FROM tbldctosordenes " +
		                 "WHERE tbldctosordenes.IDLOCAL =  ?1 " +
		                 "AND tbldctosordenes.IDTIPOORDEN IN (7,9,29, 59) " +
		                 "AND tbldctosordenes.idPeriodo =  ?2 ", nativeQuery = true)
		  public void retiraOrdenes(int idLocal, int idPeriodo);
		  
		  
		  @Modifying
		  @Transactional
		  @Query(value = "INSERT INTO tbldctosordenes " +
		                 "(IDLOCAL " +
		                 ",IDTIPOORDEN " +
		                 ",IDORDEN " +
		                 ",FECHAORDEN " +
		                 ",ESTADO " +
		                 ",idCliente " +
		                 ",IDUSUARIO " +
		                 ",IDORIGEN " +
		                 ",IDLOG " +
		                 ",FECHAENTREGA " +
		                 ",TIPODCTO " +
		                 ",DIRECCIONDESPACHO " +
		                 ",EMAIL " +
		                 ",FAX " +
		                 ",CONTACTO " +
		                 ",OBSERVACION " +
		                 ",CIUDADDESPACHO " +
		                 ",FORMAPAGO " +
		                 ",ordenCompra " +
		                 ",descuentoComercial " +
		                 ",impuestoVenta  " +
		                 ",idRazon " +
		                 ",idEstadoTx " +
		                 ",idTipoTx " +
		                 ",numeroOrden " +
		                 ",idResponsable " +
		                 ",diasHistoria " +
		                 ",diasInventario " +
		                 ",idBodegaOrigen " +
		                 ",idBodegaDestino " +
		                 ",idPeriodo " +
		                 ",vrTotalDiferir " +
		                 ",cuotaDiferir " +
		                 ",porcentajeInteresADiferir " +
		                 ",vrInteresADiferir " +
		                 ",comentario " +
		                 ",historiaConsumo " +
		                 ",promedio  " +
		                 ",cuotaVencida " +
		                 ",estadoCorte " +
		                 ",fechaPagoUltimo " +
		                 ",vrPagoUltimo " +
		                 ",promedioEstrato " +
		                 ",fechaInicioContrato " +
		                 ",vrSalarioBasico " +
		                 ",vrSubsidioTransporte " +
		                 ",fechaFinContrato " +
		                 ",idContrato " +
		                 ",idMedio " +
		                 ",entidadMedio " +
		                 ",cuentaMedio) " +
		                 "SELECT  bak_DctosOrdenes.* " +
		                 "FROM    bak_DctosOrdenes " +
		                 "WHERE EXISTS ( " +
		                 "SELECT * " +
		                 "FROM bak_DctosOrdenesDetalle " +
		                 "WHERE bak_DctosOrdenes.IDLOCAL   = bak_DctosOrdenesDetalle.IDLOCAL " +
		                 "AND bak_DctosOrdenes.IDTIPOORDEN = bak_DctosOrdenesDetalle.IDTIPOORDEN " +
		                 "AND bak_DctosOrdenes.IDORDEN  = bak_DctosOrdenesDetalle.IDORDEN ) " +
		                 "AND  bak_DctosOrdenes.IDLOCAL =  ?1 " +
		                 "AND  bak_DctosOrdenes.IDTIPOORDEN IN (7,9,29, 59) " +
		                 "AND  bak_DctosOrdenes.idPeriodo  = ?2 ", nativeQuery = true)
		  public void ingresaOrdenes(int idLocal, int idPeriodo);
		  
		  
		  
		  
		  
		  @Query( value = " SELECT tbldctosordenes.IDLOCAL           "
	                + "   ,tbldctosordenes.IDTIPOORDEN         "
	                + "   ,tbldctosordenes.IDORDEN             "
	                + "   ,MAX(tbldctosordenes.idCliente)      "
	                + "              AS idCliente              "
	                + "  ,MAX(tblterceros.nombreTercero)       "
	                + "                AS nombreTercero        "
	                + "   ,MAX(tbldctosordenes.FECHAORDEN)     "
	                + "              AS fechaOrden             "
	                + "  ,MAX(tbldctosordenes.observacion)     "
	                + "                 AS observacion         "
	                + "  ,MAX(tbldctosordenes.idPeriodo)       "
	                + "             AS idPeriodo               "
	                + "  ,MAX(tbldctosordenes.vrTotalDiferir)  "
	                + "  	                   AS vrCredito    "
	                + "  ,MAX(tbldctosordenes.cuotaDiferir)    "
	                + "                    AS cantidad         "
	                + "  ,SUM( tmpFIN.vrVentaUnitario *        "
	                + "    tmpFIN.cuotaFacturada )             "
	                + "                    AS vrFacturado      "
	                + "  ,SUM(tmpFIN.cuotaFacturada)           "
	                + "                 AS cuotaFacturada      "
	                + "  ,MAX(tbldctosordenes.vrTotalDiferir)- "
	                + "  	   SUM( tmpFIN.vrVentaUnitario *   "
	                + "  	   tmpFIN.cuotaFacturada ) AS 	   "
	                + "  		      vrCreditoPendiente   "
	                + " FROM tbldctosordenes                   "
	                + " INNER JOIN (                           "
	                + "  SELECT IDLOCAL                        "
	                + "      ,IDTIPOORDEN                      "
	                + "      ,IDORDEN                          "
	                + "      ,SUM(VRVENTAUNITARIO)             "
	                + "                AS vrVentaUnitario,     "
	                + "       CASE                             "
	                + "       WHEN itemPadre > 1 THEN 1        "
	                + "       ELSE  0                          "
	                + "       END AS cuotaFacturada            "
	                + "  FROM  tbldctosordenesdetalle          "
	                + "  WHERE IDLOCAL       =                 "
	                + "?1                          "
	                + "    AND   IDTIPOORDEN   =               "
	                + "?2                      "
	                + "   AND   IDTIPO   =  8                  "
	                + "   GROUP BY IDLOCAL                     "
	                + "      ,IDTIPOORDEN                      "
	                + "      ,IDORDEN                          "
	                + "    ,itemPadre ) AS tmpFIN              "
	                + "  ON tmpFIN.idLocal       =             "
	                + "              tbldctosordenes.idLocal   "
	                + "  AND tmpFIN.idTipoOrden  =             "
	                + "          tbldctosordenes.idTipoOrden   "
	                + "  AND tmpFIN.idOrden      =             "
	                + "              tbldctosordenes.idOrden   "
	                + "  INNER JOIN tblterceros                "
	                + "  ON tblterceros.idLocal  =             "
	                + "              tbldctosordenes.idLocal   "
	                + "  AND tblterceros.idCliente  =          "
	                + "           tbldctosordenes.idCliente    "
	                + "  WHERE tbldctosordenes.idLocal  =      "
	                + "?1                         "
	                + "    AND tbldctosordenes.idTipoOrden =   "
	                + "?2                      "
	                + "  AND tbldctosordenes.cuotaDiferir > 0  "
	                + "  AND tblterceros.idTipoTercero    = 1  "
	                + "  GROUP BY tbldctosordenes.IDLOCAL      "
	                + "      ,tbldctosordenes.IDTIPOORDEN      "
	                + "      ,tbldctosordenes.IDORDEN          "
	                + "  HAVING                                "
	                + "  (MAX(tbldctosordenes.vrTotalDiferir)- "
	                + "    SUM(tmpFIN.vrVentaUnitario *        "
	                + "        tmpFIN.cuotaFacturada)) > 0 ", nativeQuery = true)
		  List<TblDctosOrdenesDTO> listaDetalleFinanciacion(int idLocal, int xIdTipoOrden);
		  
		  
		  
		  
		  @Query( value = " SELECT tbldctosordenes.IDLOCAL           "
	                + "   ,tbldctosordenes.IDTIPOORDEN         "
	                + "   ,tbldctosordenes.IDORDEN             "
	                + "   ,MAX(tbldctosordenes.idCliente)      "
	                + "              AS idCliente              "
	                + "  ,MAX(tblterceros.nombreTercero)       "
	                + "                AS nombreTercero        "
	                + "   ,MAX(tbldctosordenes.FECHAORDEN)     "
	                + "              AS fechaOrden             "
	                + "  ,MAX(tbldctosordenes.observacion)     "
	                + "                 AS observacion         "
	                + "  ,MAX(tbldctosordenes.idPeriodo)       "
	                + "             AS idPeriodo               "
	                + "  ,MAX(tbldctosordenes.vrTotalDiferir)  "
	                + "  	                   AS vrCredito    "
	                + "  ,MAX(tbldctosordenes.cuotaDiferir)    "
	                + "                    AS cantidad         "
	                + "  ,SUM( tmpFIN.vrVentaUnitario *        "
	                + "    tmpFIN.cuotaFacturada )             "
	                + "                    AS vrFacturado      "
	                + "  ,SUM(tmpFIN.cuotaFacturada)           "
	                + "                 AS cuotaFacturada      "
	                + "  ,MAX(tbldctosordenes.vrTotalDiferir)- "
	                + "  	   SUM( tmpFIN.vrVentaUnitario *   "
	                + "  	   tmpFIN.cuotaFacturada ) AS 	   "
	                + "  		      vrCreditoPendiente   "
	                + " FROM tbldctosordenes                   "
	                + " INNER JOIN (                           "
	                + "  SELECT IDLOCAL                        "
	                + "      ,IDTIPOORDEN                      "
	                + "      ,IDORDEN                          "
	                + "      ,SUM(VRVENTAUNITARIO)             "
	                + "                AS vrVentaUnitario,     "
	                + "       CASE                             "
	                + "       WHEN itemPadre > 1 THEN 1        "
	                + "       ELSE  0                          "
	                + "       END AS cuotaFacturada            "
	                + "  FROM  tbldctosordenesdetalle          "
	                + "  WHERE IDLOCAL       =                 "
	                + "?1                          "
	                + "    AND   IDTIPOORDEN   =               "
	                + "?2                      "
	                + "   AND   IDTIPO   =  8                  "
	                + "   GROUP BY IDLOCAL                     "
	                + "      ,IDTIPOORDEN                      "
	                + "      ,IDORDEN                          "
	                + "    ,itemPadre ) AS tmpFIN              "
	                + "  ON tmpFIN.idLocal       =             "
	                + "              tbldctosordenes.idLocal   "
	                + "  AND tmpFIN.idTipoOrden  =             "
	                + "          tbldctosordenes.idTipoOrden   "
	                + "  AND tmpFIN.idOrden      =             "
	                + "              tbldctosordenes.idOrden   "
	                + "  INNER JOIN tblterceros                "
	                + "  ON tblterceros.idLocal  =             "
	                + "              tbldctosordenes.idLocal   "
	                + "  AND tblterceros.idCliente  =          "
	                + "           tbldctosordenes.idCliente    "
	                + "  WHERE tbldctosordenes.idLocal  =      "
	                + "?1                          "
	                + "    AND tbldctosordenes.idTipoOrden =   "
	                + "?2                      "
	                + "    AND tbldctosordenes.idPeriodo   =   "
	                + "?3                        "                
	                + "  AND tbldctosordenes.cuotaDiferir =    "
	                + "?4                         "
	                + "  AND tblterceros.idTipoTercero    = 1  "
	                + "  GROUP BY tbldctosordenes.IDLOCAL      "
	                + "      ,tbldctosordenes.IDTIPOORDEN      "
	                + "      ,tbldctosordenes.IDORDEN ", nativeQuery = true)
		  List<TblDctosOrdenesDTO> listaDetalleFinanciacionCuotaDiferir(int idLocal, int xIdTipoOrden, int idPeriodo, Double xCuotaDiferir);
		  
		  
		  
		  
		  @Query( value = " SELECT tbldctos.IDLOCAL                 "
	                + "       ,tbldctos.IDTIPOORDEN           "
	                + "       ,tbldctos.IDORDEN               "
	                + "       ,idDcto                         "
	                + "       ,MAX(tbldctos.idCliente)        "
	                + " 	              AS idCliente        "
	                + " 	  ,MAX(tblterceros.nombreTercero) "
	                + " 	                AS nombreTercero  "
	                + "       ,MAX(tbldctos.fechaDcto)        "
	                + " 	              AS fechaOrden       "
	                + "     ,MAX(tbldctosordenes.observacion) "
	                + " 	                  AS observacion  "
	                + "       ,MAX(tbldctos.idPeriodo)        "
	                + " 	                    AS idPeriodo  "
	                + "  ,MAX(tbldctosordenes.vrTotalDiferir) "
	                + "	                   AS vrCredito   "
	                + "  ,MAX(tbldctosordenes.cuotaDiferir)   "
	                + " 	                   AS cantidad    "
	                + "      ,SUM( tmpFIN.vrVentaUnitario *   "
	                + " 	   tmpFIN.cuotaFacturada )        "
	                + " 	                   AS vrFacturado "
	                + " 	  ,SUM(tmpFIN.cuotaFacturada)     "
	                + " 	              AS cuotaFacturada   "
	                + " ,MAX(tbldctosordenes.vrTotalDiferir)- "
	                + " 	   SUM( tmpFIN.vrVentaUnitario *  "
	                + " 	   tmpFIN.cuotaFacturada ) AS 	  "
	                + " 		      vrCreditoPendiente  "
	                + "       ,MAX(tmpFIN.idPlu) AS idPlu     "
	                + "       ,MAX(tmpFIN.NOMBREPLU)          "
	                + "                          AS nombrePlu "
	                + "   FROM tbldctos                       "
	                + "   INNER JOIN tbldctosordenes          "
	                + "   ON tbldctos.idLocal      =          "
	                + "              tbldctosordenes.idLocal  "
	                + "   AND tbldctos.idTipoOrden =          "
	                + "         tbldctosordenes.idTipoOrden   "
	                + "   AND tbldctos.idOrden     =          "
	                + "             tbldctosordenes.idOrden   "
	                + "   INNER JOIN (                        "
	                + "   SELECT IDLOCAL                      "
	                + "       ,IDTIPOORDEN                    "
	                + "       ,IDORDEN                        "
	                + "       ,SUM(VRVENTAUNITARIO)           "
	                + " 	              AS vrVentaUnitario  "
	                + "    ,MAX(tbldctosordenesdetalle.idPLU) "
	                + "                             AS idPLU  "
	                + " ,MAX(tbldctosordenesdetalle.NOMBREPLU)"
	                + "                         AS nombrePlu  "
	                + " ,CASE                                 "
	                + "     WHEN itemPadre > 1 THEN 1         "
	                + "     ELSE  0                           "
	                + " END AS cuotaFacturada                 "
	                + "   FROM  tbldctosordenesdetalle        "
	                + "   WHERE IDLOCAL       =               "
	                + "?1                         "
	                + "   AND   IDTIPOORDEN   =               "
	                + "?2                     "
	                + "    AND   IDTIPO   !=  8                "
	                + "   GROUP BY IDLOCAL                    "
	                + "       ,IDTIPOORDEN                    "
	                + "       ,IDORDEN                        "
	                + " 	  ,itemPadre ) AS tmpFIN          "
	                + "   ON tmpFIN.idLocal       =           "
	                + "       tbldctosordenes.idLocal         "
	                + "   AND tmpFIN.idTipoOrden  =           "
	                + "     tbldctosordenes.idTipoOrden       "
	                + "   AND tmpFIN.idOrden      =           "
	                + "        tbldctosordenes.idOrden        "
	                + "   INNER JOIN tblterceros              "
	                + "   ON tblterceros.idLocal  =           "
	                + "             tbldctos.idLocal          "
	                + "   AND tblterceros.idCliente  =        "
	                + "             tbldctos.idCliente        "
	                + "   WHERE tbldctos.idLocal  =           "
	                + "?1                        "
	                + "   AND tbldctos.idTipoORden=           "
	                + "?2                     "
	                + "   AND tbldctos.vrBase     <> 0         "
	                + "   AND                                 "
	                + " tblterceros.idTipoTercero = 1         "
	                + "   GROUP BY tbldctos.IDLOCAL           "
	                + "       ,tbldctos.IDTIPOORDEN           "
	                + "       ,tbldctos.IDORDEN               "
	                + "       ,idDcto ", nativeQuery = true)
		  List<TblDctosOrdenesDTO> listaDetalleCobroPermanente(int idLocal, int xIdTipoOrden);
		  
		  
		  
		  @Query( value = " SELECT tbldctos.idLocal,                                      "                                                            
				  + "   tbldctos.idOrden,                                         "           
				  + "   tbldctos.idDcto,                                          "           
				  + "   tbldctos.idPeriodo,                                       "           
				  + "   tbldctos.fechaDcto,                                       "           
				  + "   tbldctos.idCliente,                                       "           
				  + "   tbldctos.nombreTercero,                                   "           
				  + "   tbldctosordenes.promedio,                                 "           
				  + "   tbldctosordenes.promedioEstrato,                          "           
				  + "   tbldctosordenes.cuotaVencida,                             "           
				  + "   (CASE WHEN (tbldctosordenes.cuotaVencida = 0)             "           
				  + "    THEN 0 WHEN (tbldctosordenes.cuotaVencida > 0)           "           
				  + "    THEN (tbldctosordenes.cuotaVencida * 30)                 "           
				  + "                     END) AS diasMora,                       "           
				  + "   'FIN. '+ stuff(tmpDET.comentario, 1,                      "           
				  + "   charindex(' CTA', tmpDET.comentario), '')                 "           
				  + "  			      AS financiacion,                          "          
				  + "   (tmpDET.comentario) AS textoFinanciacion,                 "           
				  + "   tbldctosordenes.comentario,                               "           
				  + "   tbldctosordenes.email,                                    "           
				  + "   tbldctosordenes.DIRECCIONDESPACHO                         "           
				  + "                            AS direccionTercero,             "           
				  + "   tbldctosordenes.historiaConsumo,                          "           
				  + "   tblterceros.numeroMedidor,                                "           
				  + "   tblmedidores.marcaMedidor,                                "           
				  + "   tblterceros.fechaInstalacionMedidor,                      "           
				  + "   tblterceros.CC_Nit,                                       "           
				  + "   tblterceros.direccionCobro,                               "           
				  + "   tblterceros.codigoCatastral,                              "           
				  + "   tblterceros.ordenRuta,                                    "           
				  + "   tmpDET.idRuta,                                            "           
				  + "   tmpDET.nombreRuta,                                        "           
				  + "   tmpDET.idEstracto,                                        "           
				  + "   tmpDET.codigoClaseUso,                                    "           
				  + "   tmpDET.nombreEstracto,                                    "           
				  + "   tbldctosperiodo.fechaInicial,                             "           
				  + "   tbldctosperiodo.fechaFinal,                               "           
				  + "   tbldctosperiodo.fechaConRecargo,                          "           
				  + "   tbldctosperiodo.fechaSinRecargo,                          "           
				  + "   tbldctosperiodo.nombrePeriodo,                            "           
				  + "   (tmpLEC.lecturaMedidor) AS lecturaActual,                 "           
				  + "   tmpANT.lecturaAnterior,                                   "           
				  + "   tmpCON.consumo,                                           "           
				  + "   tmpLEC.nombreCausa,                                       "           
				  + "   (CASE WHEN (tbldctosordenes.cuotaVencida) >=              "           
				  + "             (tbllocales.cuotaSuspension)	                "              
				  + "        THEN (tbllocales.txtSuspension)                      "           
				  + "           		        END) AS txtSuspension,              "          
				  + "   tbldctosordenes.fechaPagoUltimo AS fechaPagoUltimo,       "           
				  + "   tbldctosordenes.vrPagoUltimo AS vrPagoUltimo,             "           
				  + "   0.0 AS vrBase,                                            "           
				  + "   0.0 AS vrIva,                                             "           
				  + "   0.0 AS vrFactura,                                         "           
				  + "   tmpDET.idTipo,                                            "           
				  + "   CASE WHEN tmpDET.VRVENTAUNITARIO<0                        "           
				  + "         THEN CONVERT(numeric(10,2),                         "           
				  + "        ABS(tmpDET.VRVENTAUNITARIO* tmpDET.CANTIDAD))        "           
				  + "           ELSE 0 END AS vrTipoNegativo,                     "           
				  + "   tmpDET.CANTIDAD,                                          "           
				  + "   tmpDET.VRVENTAUNITARIO,                                   "           
				  + "   tmpDET.PORCENTAJEIVA,                                     "           
				  + "   tmpDET.idPlu,                                             "           
				  + "   tmpCAT.NOMBREPLU,                                         "           
				  + "   CONVERT(numeric(10,2),ROUND((tmpDET.VRVENTAUNITARIO)/     "           
				  + "   ( 1+ tmpDET.PORCENTAJEIVA/100.0 ),2))                     "           
				  + "                             AS vrBaseUnitarioSinIva,        "           
				  + "   CONVERT(numeric(10,2),ROUND((tmpDET.VRVENTAUNITARIO)/     "           
				  + "   ( 1+ tmpDET.PORCENTAJEIVA/100 ),2) *                      "           
				  + "   tmpDET.CANTIDAD *                                         "           
				  + "   tmpDET.PORCENTAJEIVA/100.0 ) AS vrIvaUnitario,            "           
				  + "   CONVERT(numeric(10,2),ROUND((tmpDET.VRVENTAUNITARIO)/     "           
				  + "   ( 1+ tmpDET.PORCENTAJEIVA/100.0 ),2) *                    "           
				  + "   tmpDET.CANTIDAD) AS vrVentaUnitarioSinIva,                "           
				  + "   tbldctos.vrRteFuente,                                     "           
				  + "   tbldctos.vrRteIva,                                        "           
				  + "   1.0 AS porcentajeRteFuente,                               "           
				  + "   6 AS taxId_RteFuente,                                     "           
				  + "   1.0 AS porcentajeRteIva,                                  "           
				  + "   5 AS taxId_RteIva,                                        "           
				  + "   1 AS taxId_Iva,                                           "           
				  + "   CONVERT(numeric(10,2),ROUND((tmpDET.VRVENTAUNITARIO)/     "           
				  + "   ( 1+ tmpDET.PORCENTAJEIVA/100.0 ) *                       "           
				  + "   tmpDET.CANTIDAD *                                         "           
				  + "   1.0 / 100.00,2)) AS vrUnitarioRteFuente,                  "           
				  + "   CONVERT(numeric(10,2),ROUND((tmpDET.VRVENTAUNITARIO)/     "           
				  + "   ( 1+ tmpDET.PORCENTAJEIVA/100 ) *                         "           
				  + "   tmpDET.CANTIDAD *                                         "           
				  + "   tmpDET.PORCENTAJEIVA/100.0 *                              "           
				  + "   1.0 / 100.00,2)) AS vrUnitarioRteIva,                     "           
				  + "   tbldctos.cufe                                             "           
				  + "   ,tmpCAT.idProducto                                        "           
				  + "   FROM tbldctos                                             "           
				  + "   INNER JOIN                                                "           
				  + "   (SELECT tbldctosordenesdetalle.idLocal,                   "           
				  + "     CASE WHEN tbldctosordenesdetalle.IDTIPOORDEN = 29       "           
				  + "     THEN 9 ELSE tbldctosordenesdetalle.IDTIPOORDEN          "           
				  + "     END AS IDTIPOORDEN,                                     "           
				  + "     CASE WHEN tbldctosordenesdetalle.IDTIPOORDEN = 29       "           
				  + "     THEN idOrdenCruce                                       "           
				  + "     ELSE tbldctosordenesdetalle.idOrden                     "           
				  + "     END AS idOrden,                                         "           
				  + "     tbldctosordenesdetalle.idPlu,                           "           
				  + "     tbldctosordenesdetalle.idTipo,                          "           
				  + "     tbldctosordenesdetalle.CANTIDAD,                        "           
				  + "     tbldctosordenesdetalle.VRVENTAUNITARIO,                 "           
				  + "    (tbldctosordenesdetalle.PORCENTAJEIVA)                   "           
				  + "                                      AS PORCENTAJEIVA,      "           
				  + "     tbldctosordenesdetalle.nombrePlu,                       "           
				  + "     tbltercerosruta.idRuta,                                 "           
				  + "     tbltercerosruta.nombreCiclo + '-' +                     "           
				  + "             tbltercerosruta.nombreRuta AS nombreRuta,       "           
				  + "     tblterceroestracto.idEstracto,                          "           
				  + "     tblterceroestracto.codigoClaseUso,                      "           
				  + "     tblterceroestracto.nombreEstracto,                      "           
				  + "     tbldctosordenesdetalle.comentario,                      "
				  + " 	  tmpDCT.idPeriodo                                        "
				  + "    FROM tbldctos AS tmpDCT                                  "                    
				  + "    INNER JOIN tbldctosordenesdetalle                        "           
				  + "    ON tmpDCT.IDLOCAL = tbldctosordenesdetalle.IDLOCAL       "         
				  + "    AND tmpDCT.IDTIPOORDEN =                                 "         
				  + "                      tbldctosordenesdetalle.IDTIPOORDEN     "           
				  + "    AND tmpDCT.IDORDEN     =                                 "         
				  + "                          tbldctosordenesdetalle.IDORDEN     "           
				  + "    INNER JOIN tbltercerosruta                               "           
				  + "    ON tbldctosordenesdetalle.IDLOCAL =                      "           
				  + "                                 tbltercerosruta.idLocal     "           
				  + "    AND tbldctosordenesdetalle.idRuta =                      "           
				  + "                                  tbltercerosruta.idRuta     "           
				  + "    INNER JOIN tblterceroestracto                            "           
				  + "    ON tbldctosordenesdetalle.IDLOCAL =                      "           
				  + "                              tblterceroestracto.idLocal     "           
				  + "    AND tbldctosordenesdetalle.idEstracto =                  "           
				  + "                           tblterceroestracto.idEstracto     "           
				  + "    WHERE tmpDCT.idLocal =    ?1                            "                                                       
				  + "    AND tmpDCT.idTipoOrden IN (9,29)                         "         
				  + "    AND tbldctosordenesdetalle.VRVENTAUNITARIO!=0            "           
				  + "    AND tbldctosordenesdetalle.CANTIDAD != 0) AS tmpDET      "           
				  + "   ON tbldctos.IDLOCAL = tmpDET.IDLOCAL                      "           
				  + "   AND tbldctos.IDTIPOORDEN = tmpDET.IDTIPOORDEN             "           
				  + "   AND tbldctos.IDORDEN = tmpDET.IDORDEN                     "
				  + "   AND tbldctos.idPeriodo = tmpDET.idPeriodo   				"	  
				  + "   INNER JOIN                                                "           
				  + "   (SELECT tblplus.idLocal                                   "           
				  + "       ,tblplus.idPlu                                        "           
				  + "       ,RTRIM(LTRIM(                                         "           
				  + "       tblcategorias.nombreCategoria)) + ' ' +               "           
				  + "       tblplus.nombrePlu AS nombrePlu,                       "           
				  + " 	  tblcategorias.idProducto                              "              
				  + "    FROM tblcategorias                                       "           
				  + "    INNER JOIN tblplus                                       "           
				  + "    ON tblcategorias.idLocal = tblplus.idLocal               "           
				  + "    AND tblcategorias.idLinea = tblplus.idLinea              "           
				  + "    AND tblcategorias.idCategoria = tblplus.idCategoria      "           
				  + "    WHERE tblplus.idLocal = ?1 ) AS tmpCAT                  "            
				  + "   ON tbldctos.idLocal = tmpCAT.idLocal                      "           
				  + "   AND tmpDET.idPlu = tmpCAT.idPlu                           "           
				  + "   INNER JOIN  tbldctosordenes                               "           
				  + "  ON tbldctosordenes.IDLOCAL = tbldctos.IDLOCAL              "           
				  + "  AND tbldctosordenes.IDTIPOORDEN = tbldctos.IDTIPOORDEN     "           
				  + "  AND tbldctosordenes.IDORDEN = tbldctos.IDORDEN             "           
				  + "  INNER JOIN tbllocales                                      "           
				  + "  ON tbllocales.idLocal = tbldctosordenes.IDLOCAL            "           
				  + "  INNER JOIN tblterceros                                     "           
				  + "  ON tbldctos.IDLOCAL = tblterceros.idLocal                  "           
				  + "  AND tbldctos.idCliente = tblterceros.idCliente             "           
				  + "  INNER JOIN tbldctosperiodo                                 "           
				  + "  ON tbldctos.IDLOCAL = tbldctosperiodo.idLocal              "           
				  + "  AND tbldctos.idPeriodo = tbldctosperiodo.idPeriodo         "           
				  + "  INNER JOIN (                                               "           
				  + "    SELECT tbldctosordenesdetalle.IDLOCAL,                   "           
				  + "      tbldctosordenesdetalle.IDTIPOORDEN,                    "           
				  + "    	 tbldctosordenesdetalle.IDORDEN,                        "          
				  + "      tbldctos.idPeriodo,                                    "           
				  + "    	 MAX(tbltipocausanota.nombreCausa)                      "          
				  + "                                   AS nombreCausa,           "           
				  + "      MAX(CONVERT(int,tbldctosordenesdetalle.lecturaMedidor))"               
				  + "    	                                    AS lecturaMedidor   "          
				  + "    FROM tbldctosordenesdetalle                              "           
				  + "    INNER JOIN tbldctos                                      "           
				  + "    ON tbldctosordenesdetalle.IDLOCAL = tbldctos.IDLOCAL     "           
				  + "    AND tbldctosordenesdetalle.IDTIPOORDEN =                 "           
				  + "                                       tbldctos.IDTIPOORDEN  "           
				  + "    AND tbldctosordenesdetalle.IDORDEN = tbldctos.IDORDEN    "           
				  + "    INNER JOIN tbltipocausanota                              "           
				  + "    ON tbltipocausanota.idCausa =                            "           
				  + "                   tbldctosordenesdetalle.idNovedadLectura   "           
				  + "    WHERE tbltipocausanota.idTipoTabla    =  2               "           
				  + "    AND tbldctosordenesdetalle.idTipo     =  4               "           
				  + "    AND tbldctosordenesdetalle.STRIDLISTA =  '1'             "           
				  + "    AND tbldctosordenesdetalle.idLocal   =  ?1              "                               
				  + "    AND tbldctos.idPeriodo =  ?3                         "                  
				  + "    GROUP BY tbldctosordenesdetalle.IDLOCAL,                 "           
				  + "       tbldctosordenesdetalle.IDTIPOORDEN,                   "           
				  + "       tbldctosordenesdetalle.IDORDEN,                       "           
				  + "       tbldctos.idPeriodo) AS tmpLEC                         "           
				  + "    ON tmpLEC.IDLOCAL = tbldctos.IDLOCAL                     "           
				  + "    AND tmpLEC.IDTIPOORDEN = tbldctos.IDTIPOORDEN            "           
				  + "    AND tmpLEC.IDORDEN = tbldctos.IDORDEN                    "           
				  + "    AND tmpLEC.idPeriodo = tbldctos.idPeriodo                "           
				  + "  LEFT JOIN                                                  "           
				  + "  (SELECT tbldctosordenesdetalle.IDLOCAL,                    "           
				  + "         tbldctosordenesdetalle.IDTIPOORDEN,                 "           
				  + "  	  tbldctosordenesdetalle.idCliente,                     "          
				  + "         MAX(CONVERT(int,                                    "           
				  + "                   tbldctosordenesdetalle.lecturaAnterior))  "           
				  + "  	                                 AS lecturaAnterior     "          
				  + "  FROM tbldctosordenesdetalle                                "           
				  + "  INNER JOIN tbldctos                                        "           
				  + "  ON tbldctosordenesdetalle.IDLOCAL = tbldctos.IDLOCAL       "           
				  + "  AND tbldctosordenesdetalle.IDTIPOORDEN =                   "           
				  + "                                    tbldctos.IDTIPOORDEN     "           
				  + "  AND tbldctosordenesdetalle.IDORDEN = tbldctos.IDORDEN      "           
				  + "  AND tbldctosordenesdetalle.idCliente = tbldctos.idCliente  "           
				  + "  WHERE tbldctosordenesdetalle.idTipo     =  4               "           
				  + "  AND tbldctosordenesdetalle.STRIDLISTA   = '1'              "           
				  + "  AND tbldctosordenesdetalle.idLocal   =  ?1                "                             
				  + "  AND tbldctos.idPeriodo =  ?3                           "                
				  + "  GROUP BY tbldctosordenesdetalle.IDLOCAL,                   "           
				  + "           tbldctosordenesdetalle.IDTIPOORDEN,               "           
				  + "           tbldctosordenesdetalle.idCliente) AS tmpANT       "           
				  + "  ON tmpANT.IDLOCAL = tbldctos.IDLOCAL                       "           
				  + "  AND tmpANT.IDTIPOORDEN = tbldctos.IDTIPOORDEN              "           
				  + "  AND tmpANT.idCliente = tbldctos.idCliente                  "           
				  + "  INNER JOIN tblmedidores                                    "           
				  + "  ON tblmedidores.idLocal =tblterceros.IDLOCAL               "           
				  + "  AND tblmedidores.idMedidor = tblterceros.idMedidor         "           
				  + "  INNER JOIN                                                 "           
				  + "  ( SELECT tbldctos.IDLOCAL,                                 "           
				  + "           tbldctos.IDTIPOORDEN,                             "           
				  + "  	        tbldctos.IDORDEN,                                   "          
				  + "           tbldctos.idPeriodo,                               "           
				  + "           tbldctosordenesdetalle.idcliente,                 "           
				  + "  	    SUM(tbldctosordenesdetalle.CANTIDAD)                "          
				  + "  	                                     AS consumo         "          
				  + "  FROM tbldctos                                              "           
				  + "  INNER JOIN tbldctosordenesdetalle                          "           
				  + "  ON tbldctos.idLocal = tbldctosordenesdetalle.IDLOCAL       "           
				  + "  AND tbldctos.idTipoOrden =                                 "           
				  + "                     tbldctosordenesdetalle.idTipoOrden      "           
				  + "  AND tbldctos.idOrden = tbldctosordenesdetalle.idOrden      "           
				  + "  WHERE tbldctosordenesdetalle.IDTIPO = 4                    "           
				  + "  GROUP BY tbldctos.IDLOCAL,                                 "           
				  + "           tbldctos.IDTIPOORDEN,                             "           
				  + "  	        tbldctos.IDORDEN,                                 "          
				  + "           tbldctos.idPeriodo,                               "           
				  + "           tbldctosordenesdetalle.idcliente) AS tmpCON       "           
				  + "  ON tmpCON.IDLOCAL = tbldctos.IDLOCAL                       "           
				  + "  AND tmpCON.IDTIPOORDEN = tbldctos.IDTIPOORDEN              "           
				  + "  AND tmpCON.IDORDEN= tbldctos.IDORDEN                       "           
				  + "  AND tmpCON.idPeriodo= tbldctos.idPeriodo                   "           
				  + "                                                             "           
				  + "   WHERE tbldctos.idLocal = ?1                              "              
				  + "   AND tmpCON.idPeriodo = ?3                           "               
				  + "   AND tbldctos.idTipoOrden IN (9,29)                       	"	      
				  + "   AND tbldctos.idCliente  IN (?2)                       "           
				  + "                                                             "           
				  + "   AND tmpDET.VRVENTAUNITARIO != 0                           "           
				  + "   AND tmpDET.CANTIDAD != 0                                  "                                   
				  + "   ORDER BY tbldctos.idLocal,                                "           
				  + "            tbldctos.idOrden,                                "           
				  + " 	       tmpCAT.idProducto,                               "              
				  + "            tmpDET.IDTIPO                                    "           
				  , nativeQuery = true)
		  
 
		  
		  List<TblDctosOrdenesDTO> listaUnClienteProducto(int idLocal, List<String> idClientes, Double xIdPeriodo);
		  
	
	
		  
		  
		  

}






