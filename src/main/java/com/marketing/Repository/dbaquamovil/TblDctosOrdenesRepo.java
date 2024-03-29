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
		  
	
	
		  
		  @Query( value = " SELECT tmpPRC.idLocal,                             "
	                + "        tmpPRC.idDcto,                              "
	                + "        tmpPRC.idPeriodo,                           "
	                + " 	   tmpPRC.nombrePeriodo,                       "
	                + "        tmpPRC.idCliente,                           "
	                + "        MAX(tmpPRC.nombreTercero) AS nombreTercero, "
	                + "        MAX(tbldctosordenes.historiaConsumo)        "
	                + "                                AS historiaConsumo, "
	                + "        MAX(tbldctosordenes.promedio) AS promedio,  "
	                + "        ISNULL(MAX(tblterceros.ordenRuta),0)        "
	                + " 	                                 AS ordenRuta, "
	                + " 	   MAX(tmpPRC.idRuta)  AS idRuta,              "
	                + "        MAX(tmpPRC.nombreCiclo) + '-' +             "
	                + "  MAX(tmpPRC.nombreRuta) AS nombreRuta, "
	                + "        MAX(tmpPRC.idEstracto) AS idEstracto,       "
	                + "      MAX(tmpPRC.nombreEstracto) AS nombreEstracto, "
	                + "       MAX(tmpPRC.lecturaMedidor) AS lecturaActual, "
	                + "        ISNULL((SELECT TOP 1                        "
	                + " tbldctosordenesdetalle.lecturaMedidor  "
	                + "                                AS lecturaAnterior  "
	                + "         FROM tbldctosordenes                       "
	                + "         INNER JOIN tbldctosordenesdetalle          "
	                + "         ON tbldctosordenes.IDLOCAL                 "
	                + "                   = tbldctosordenesdetalle.IDLOCAL "
	                + "         AND tbldctosordenes.IDTIPOORDEN            "
	                + "               = tbldctosordenesdetalle.IDTIPOORDEN "
	                + "         AND tbldctosordenes.IDORDEN                "
	                + "                   = tbldctosordenesdetalle.IDORDEN "
	                + "         WHERE tbldctosordenes.idLocal =            "
	                + "                         tmpPRC.IDLOCAL "
	                + "         AND tbldctosordenes.idTipoOrden =          "
	                + "                     tmpPRC.IDTIPOORDEN "
	                + "         AND tbldctosordenes.idPeriodo =            "
	                + "?1                                "
	                + "         AND tbldctosordenesdetalle.idTipo = 4      "
	                + "         AND tbldctosordenesdetalle.idCliente =     "
	                + "           tmpPRC.idCliente),0)         "
	                + " 				   AS lecturaAnterior, "
	                + "         SUM(tmpPRC.cantidad) AS cantidad,          "
	                + "         MAX(tmpFRA.nombreCausa) AS nombreCausa     "
	                + " FROM (                                             "
	                + " SELECT tbldctos.idLocal,                           "
	                + "        tbldctos.idTipoOrden,                       "
	                + "        tbldctos.idOrden,                           "
	                + "        tbldctos.idDcto,                            "
	                + "        tbldctos.idPeriodo,                         "
	                + "        tbldctos.idCliente,                         "
	                + "        tbldctos.nombreTercero,                     "
	                + "        MAX(tbldctos.fechaDcto) AS fechaDcto,       "
	                + "        tblplus.idTipo,                             "
	                + "        MAX(CASE WHEN tblplus.idTipo = 4            "
	                + "        THEN tbldctosordenesdetalle.lecturaMedidor  "
	                + "                     ELSE 0 END) AS lecturaMedidor, "
	                + "        SUM(CASE WHEN tblplus.idTipo = 4            "
	                + "            THEN tbldctosordenesdetalle.cantidad    "
	                + "                           ELSE 0 END) AS cantidad, "
	                + "        MAX(tbldctosperiodo.nombrePeriodo)          "
	                + "                                  AS nombrePeriodo, "
	                + "        MAX(tbltercerosruta.nombreCiclo)            "
	                + "                                    AS nombreCiclo, "
	                + "        MAX(tbltercerosruta.nombreRuta)             "
	                + "                                     AS nombreRuta, "
	                + "        MAX(tbltercerosruta.idRuta) AS idRuta,      "
	                + "        MAX(tblterceroestracto.idEstracto)          "
	                + "                                     AS idEstracto, "
	                + "        MAX(tblterceroestracto.nombreEstracto)      "
	                + "            		             AS nombreEstracto "
	                + " FROM tbldctos                                      "
	                + " INNER JOIN tbldctosordenesdetalle                  "
	                + " ON tbldctos.IDLOCAL =                              "
	                + "         tbldctosordenesdetalle.IDLOCAL "
	                + " AND tbldctos.IDTIPOORDEN =                         "
	                + "     tbldctosordenesdetalle.IDTIPOORDEN "
	                + " AND tbldctos.IDORDEN =                             "
	                + "         tbldctosordenesdetalle.IDORDEN "
	                + " INNER JOIN tbldctosordenes                         "
	                + " ON tbldctos.IDLOCAL  =  tbldctosordenes.IDLOCAL    "
	                + " AND tbldctos.IDTIPOORDEN =                         "
	                + "            tbldctosordenes.IDTIPOORDEN "
	                + " AND tbldctos.IDORDEN = tbldctosordenes.IDORDEN     "
	                + " INNER JOIN tblterceroestracto                      "
	                + " ON tblterceroestracto.idEstracto =                 "
	                + "      tbldctosordenesdetalle.idEstracto "
	                + " AND tblterceroestracto.idLocal =                   "
	                + "         tbldctosordenesdetalle.idLocal "
	                + " INNER JOIN tbltercerosruta                         "
	                + " ON tbldctosordenesdetalle.idRuta                   "
	                + "                           = tbltercerosruta.idRuta "
	                + " AND tbldctosordenesdetalle.idLocal                 "
	                + "                          = tbltercerosruta.idLocal "
	                + " INNER JOIN tblplus                                 "
	                + " ON tbldctosordenesdetalle.IDPLU = tblplus.idPlu    "
	                + " AND tbldctosordenesdetalle.idLocal =               "
	                + "                        tblplus.idLocal "
	                + " INNER JOIN tbldctosperiodo                         "
	                + " ON tbldctosperiodo.idPeriodo = tbldctos.idPeriodo  "
	                + " AND tbldctosperiodo.idLocal = tbldctos.idLocal     "
	                + " WHERE     tbldctos.idLocal       =                 "
	                + "?2                                      "
	                + " AND       tbldctos.idTipoOrden   =                 "
	                + "?3                                  "
	                + " AND       tbldctos.idPeriodo     =                 "
	                + "?4                                    "
	                + " GROUP BY tbldctos.idLocal,                         "
	                + "         tbldctos.idTipoOrden,                      "
	                + "         tbldctos.idOrden,                          "
	                + "         tbldctos.idDcto,                           "
	                + "         tbldctos.idPeriodo,                        "
	                + "         tbldctos.idCliente,                        "
	                + "         tblplus.idTipo,                            "
	                + "         tbldctos.nombreTercero) AS tmpPRC          "
	                + " INNER JOIN tbllocales                              "
	                + " ON tbllocales.idLocal = tmpPRC.IDLOCAL             "
	                + " INNER JOIN tblterceros                             "
	                + " ON tblterceros.idLocal = tmpPRC.IDLOCAL            "
	                + " AND tblterceros.idCliente = tmpPRC.idCliente       "
	                + " INNER JOIN tbldctosordenes                         "
	                + " ON tbldctosordenes.idLocal = tmpPRC.IDLOCAL        "
	                + " AND tbldctosordenes.idTipoOrden =                  "
	                + "                     tmpPRC.idTipoOrden "
	                + " AND tbldctosordenes.idOrden = tmpPRC.idOrden       "
	                + " INNER JOIN tblterceroestracto                      "
	                + " ON tblterceroestracto.idEstracto =                 "
	                + "                tblterceros.idEstracto  "
	                + " AND tblterceroestracto.idLocal =                   "
	                + "                   tblterceros.idLocal  "
	                + " INNER JOIN tbltercerosruta                         "
	                + " ON tblterceros.idRuta = tbltercerosruta.idRuta     "
	                + " AND tblterceros.idLocal = tbltercerosruta.idLocal  "
	                + " INNER JOIN                                         "
	                + " (SELECT tbldctosordenesdetalle.IDLOCAL,            "
	                + "         tbldctosordenesdetalle.IDTIPOORDEN,        "
	                + "         tbldctosordenesdetalle.IDORDEN,            "
	                + "         tbltipocausanota.nombreCausa               "
	                + " FROM tbldctosordenes                               "
	                + " INNER JOIN tbldctosordenesdetalle                  "
	                + " ON tbldctosordenes.IDLOCAL =                       "
	                + "         tbldctosordenesdetalle.IDLOCAL "
	                + " AND tbldctosordenes.IDTIPOORDEN =                  "
	                + "     tbldctosordenesdetalle.IDTIPOORDEN "
	                + " AND tbldctosordenes.IDORDEN =                      "
	                + "         tbldctosordenesdetalle.IDORDEN "
	                + " INNER JOIN tbltipocausanota                        "
	                + " ON tbltipocausanota.idCausa =                      "
	                + "tbldctosordenesdetalle.idNovedadLectura "
	                + " WHERE tbldctosordenesdetalle.IDLOCAL  =            "
	                + "?2                                      "
	                + " AND tbldctosordenesdetalle.IDTIPOORDEN =           "
	                + "?3                                  "
	                + " AND tbldctosordenes.IDPERIODO         =            "
	                + "?4                                    "
	                + " AND tbldctosordenesdetalle.IDTIPO     = 4          "
	                + " AND tbldctosordenesdetalle.STRIDLISTA = 1          "
	                + " AND tbltipocausanota.idTipoTabla = 2) AS tmpFRA    "
	                + " ON tmpFRA.IDLOCAL=tmpPRC.IDLOCAL                   "
	                + " AND tmpFRA.IDTIPOORDEN = tmpPRC.idTipoOrden        "
	                + " AND tmpFRA.IDORDEN = tmpPRC.idOrden                "
	                + " INNER JOIN tblmedidores                            "
	                + " ON tblmedidores.idLocal = tblterceros.IDLOCAL      "
	                + " AND tblmedidores.idMedidor = tblterceros.idMedidor "
	                + " WHERE     tmpPRC.idLocal         =                 "
	                + "?2                                      "
	                + " AND       tmpPRC.idTipoOrden     =                 "
	                + "?3                                  "
	                + " AND       tmpPRC.idPeriodo       =                 "
	                + "?4                                    "
	                + " GROUP BY tmpPRC.idLocal,                           "
	                + "        tmpPRC.idTipoOrden,                         "
	                + "        tmpPRC.idOrden,                             "
	                + "        tmpPRC.idDcto,                              "
	                + "        tmpPRC.idPeriodo,                           "
	                + "        tmpPRC.idCliente,                           "
	                + "       tmpPRC.nombrePeriodo                         "
	                + " ORDER BY idruta,                                   "
	                + "          ordenRuta", nativeQuery = true)
		  List<TblDctosOrdenesDTO> listaLecturaAllSuscriptor(int xidPeriodoAnterior, int idLocal, int idTipoOrden, int idPeriodo );
		  

		  
		  @Query( value = " SELECT MAX(tblterceros.nombreTercero) AS nombreTercero, "
	                + "        MAX(tbltipocausanota.nombreCausa)                "
	                + " 	                            AS nombreEstadoCliente, "
	                + "        MAX(tblterceros.direccionTercero)                "
	                + "                                    AS direccionTercero, "
	                + "        tmpPRC.idDcto,                                   "
	                + "        tmpPRC.idPeriodo,                                "
	                + "        tmpPRC.idCliente,                                "
	                + "        SUM(tmpPRC.vrConsumo)  AS vrConsumo,     "
	                + "        MAX(tmpPRC.lecturaMedidor)               "
	                + "                               AS lecturaActual, "
	                + "       CASE WHEN MAX(tmpPRC.lecturaMedidor) -    "
	                + "SUM(tmpPRC.cantidad)<0 THEN 0        "
	                + "        ELSE MAX(tmpPRC.lecturaMedidor) -        "
	                + "SUM(tmpPRC.cantidad)                 "
	                + "                         END AS lecturaAnterior, "
	                + "        MAX(ISNULL(tbldctosordenes.promedio,0))  "
	                + "                                    AS promedio, "
	                + "        SUM(tmpPRC.vrCargoFijo) AS vrCargoFijo,  "
	                + "        MAX(tmpPRC.vrVentaUnitario)              "
	                + "                             AS vrVentaUnitario, "
	                + "        SUM(tmpPRC.vrDeudaCargoFijo)             "
	                + "                            AS vrDeudaCargoFijo, "
	                + "        SUM(tmpPRC.vrDeudaConsumo)               "
	                + "                              AS vrDeudaConsumo, "
	                + "        SUM(tmpPRC.vrDeudaContribucion)          "
	                + "                         AS vrDeudaContribucion, "
	                + "	   SUM(tmpPRC.vrDeudaSubsidio)              "
	                + "                            AS vrDeudaSubsidio,  "
	                + "        SUM(tmpPRC.vrDeudaReconexion)            "
	                + "                           AS vrDeudaReconexion, "
	                + "        SUM(tmpPRC.vrDeudaConexion)              "
	                + "                             AS vrDeudaConexion, "
	                + "        SUM(tmpPRC.vrDeudaSuspencion)            "
	                + "                           AS vrDeudaSuspencion, "
	                + "        SUM(tmpPRC.vrDeudaReinstalacion)         "
	                + "                        AS vrDeudaReinstalacion, "
	                + "       SUM(tmpPRC.vrDeudaOtros) AS vrDeudaOtros, "
	                + "        SUM(tmpPRC.vrDeudaCargoCorte)            "
	                + "                           AS vrDeudaCargoCorte, "
	                + "        SUM(tmpPRC.vrDeudaMedidor)               "
	                + "                              AS vrDeudaMedidor, "
	                + "        SUM(tmpPRC.vrFinanciacion)               "
	                + "                              AS vrFinanciacion, "
	                + "        SUM(tmpPRC.vrDeudaFinanciacion)          "
	                + "                         AS vrDeudaFinanciacion, "
	                + "        SUM(tmpPRC.vrMedidor) AS vrMedidor,      "
	                + "        MAX(ISNULL(                              "
	                + "           tbldctosordenes.cuotaVencida,0))      "
	                + "                                AS cuotaVencida, "
	                + "        SUM(tmpPRC.cantidad) AS cantidad,  	    "
	                + "        MAX(tmpFRA.nombreCausa) AS nombreCausa,  "
	                + "        SUM(tmpPRC.vrConexion) AS vrConexion,    "
	                + "       SUM(tmpPRC.vrReconexion) AS vrReconexion, "
	                + "       SUM(tmpPRC.vrReinstalacion)              "
	                + "                             AS vrReinstalacion, "
	                + "       SUM(tmpPRC.vrSuspencion) AS vrSuspencion, "
	                + "       SUM(tmpPRC.vrCargoCorte) AS vrCargoCorte, "
	                + "       SUM(tmpPRC.vrOtros) AS vrOtros,           "
	                + "       SUM(tmpPRC.vrConsumo  +                   "
	                + "           tmpPRC.vrCargoFijo    +               "
	                + "           tmpPRC.vrMedidor      +               "
	                + "           tmpPRC.vrFinanciacion +               "
	                //+ "           tmpPRC.vrDeudaFinanciacion +          "
	                + "           tmpPRC.vrInteres      +               "
	                //  + "           tmpPRC.vrInteresAcum  +               "
	                //  + "           tmpPRC.vrDeudaCargoFijo +             "
	                //  + "           tmpPRC.vrDeudaConsumo +               "
	                + "           tmpPRC.VrReconexion   +               "
	                // + "           tmpPRC.VrDeudaReconexion +            "
	                + "           tmpPRC.vrConexion     +               "
	                + "           tmpPRC.vrReinstalacion +              "
	                + "           tmpPRC.vrCargoCorte   +               "
	                + "           tmpPRC.vrOtros        +               "
	                // + "           tmpPRC.vrDeudaOtros   +               "
	                + "           tmpPRC.vrSuspencion  +                "
	                + "           tmpPRC.vrSubsidio  +                  "
	                + "           tmpPRC.vrContribucion +               "
	                + "           tmpPRC.vrAlcantarillaCargoFijo +	    "
	                + "           tmpPRC.vrAlcantarillaConsumo +        "
	                + "     tmpPRC.vrSubsidioCargoFijoAlcantarillado +  "
	                + "       tmpPRC.vrSubsidioConsumoAlcantarillado +  "
	                + "  tmpPRC.vrContribucionCargoFijoAlcantarillado + "
	                + "   tmpPRC.vrContribucionConsumoAlcantarillado +  "
	                + "           tmpPRC.vrAseoCargoFijo +              "
	                + "           tmpPRC.vrContribucionCargoFijoAseo +  "
	                + "           tmpPRC.vrSubsidioCargoFijoAseo +      "
	                + "           tmpPRC.vrAjusteDecena +               "
	                // + "           tmpPRC.vrDeudaAjusteDecena +          "
	                // + "           tmpPRC.VrDeudaSuspencion    +         "
	                //+ "           tmpPRC.vrDeudaCargoCorte    +         "
	                // + "           tmpPRC.VrDeudaReinstalacion  +        "
	                //+ "           tmpPRC.vrDeudaMedidor       +         "
	                // + "           tmpPRC.VrDeudaConexion  +             "
	                + "          tmpPRC.deudaAnterior) AS vrTotalPagar, "
	                + "       (SELECT ISNULL(SUM(tblpagos.vrPago),0)    "
	                + "                                       AS vrPago "
	                + "        FROM tblpagos                            "
	                + "        WHERE tblpagos.IDLOCAL = tmpPRC.idLocal  "
	                + "        AND tblpagos.IDTIPOORDEN =               "
	                + "                  tmpPRC.idTipoOrden "
	                + "        AND tblpagos.nitCC = tmpPRC.idCliente    "
	                + "        AND tblpagos.idPeriodo =                 "
	                + "?1  ) AS vrPago, 		    "
	                + "       (SELECT tbldctosordenesdetalle.CANTIDAD   "
	                + "                                AS cantidadRango "
	                + "        FROM tbldctosordenesdetalle              "
	                + "        WHERE tbldctosordenesdetalle.IDLOCAL =   "
	                + "                      tmpPRC.idLocal "
	                + "	   AND tbldctosordenesdetalle.IDTIPOORDEN = "
	                + "                 tmpPRC.idTipoOrden  "
	                + "	   AND tbldctosordenesdetalle.IDORDEN =     "
	                + "                      tmpPRC.idOrden "
	                + "	  AND tbldctosordenesdetalle.STRIDLISTA = 1 "
	                + "	   AND tbldctosordenesdetalle.IDTIPO   = 4) "
	                + "                              AS cantidadRango1, "
	                + "       ROUND(SUM(tmpPRC.vrSubsidioCargoFijo +    "
	                + " 	  tmpPRC.vrSubsidioConsumo),0)              "
	                + "                                  AS vrSubsidio, "
	                + "       ROUND(SUM(tmpPRC.vrSubsidioCargoFijo),0)  "
	                + "                         AS vrSubsidioCargoFijo, "
	                + "       ROUND(SUM(tmpPRC.vrSubsidioConsumo),0)    "
	                + "                           AS vrSubsidioConsumo, "
	                + "       SUM(tmpPRC.vrContribucion )               "
	                + " 		                 AS vrContribucion, "
	                + "       SUM(tmpPRC.vrContribucionCargoFijo )      "
	                + " 		        AS vrContribucionCargoFijo, "
	                + "       SUM(tmpPRC.vrContribucionConsumo )        "
	                + " 		          AS vrContribucionConsumo, "
	                + "       SUM(tmpPRC.vrAjusteDecena)                "
	                + "  	                         AS vrAjusteDecena, "
	                + "       SUM(tmpPRC.vrDeudaAjusteDecena)           "
	                + "	                    AS vrDeudaAjusteDecena, "
	                + " 	  SUM(tmpPRC.deudaAnterior)                 "
	                + "                             AS vrDeudaAnterior, "
	                + "       SUM(tmpPRC.vrInteres +                    "
	                + " tmpPRC.vrInteresAcum) AS vrInteres, "
	                + "      SUM(tmpPRC.vrInteres) AS vrInteresPeriodo, "
	                + "     SUM(tmpPRC.vrInteresAcum) AS vrInteresAcum, "
	                + "       SUM(tmpPRC.vrAlcantarillaConsumo)         "
	                + " 	                  AS vrAlcantarillaConsumo, "
	                + "       SUM(tmpPRC.vrAlcantarillaCargoFijo)       "
	                + " 		        AS vrAlcantarillaCargoFijo, "
	                + "   SUM(tmpPRC.vrSubsidioCargoFijoAlcantarillado) "
	                + "           AS vrSubsidioCargoFijoAlcantarillado, "
	                + "       SUM(tmpPRC.vrSubsidioConsumoAlcantarillado) "
	                + "                AS vrSubsidioConsumoAlcantarillado, "
	                + "  SUM(tmpPRC.vrContribucionCargoFijoAlcantarillado) "
	                + "          AS vrContribucionCargoFijoAlcantarillado, "
	                + "    SUM(tmpPRC.vrContribucionConsumoAlcantarillado) "
	                + "            AS vrContribucionConsumoAlcantarillado, "
	                + "       SUM(tmpPRC.vrAseoCargoFijo)                "
	                + "                             AS vrAseoCargoFijo, "
	                + "       SUM(tmpPRC.vrContribucionCargoFijoAseo)   "
	                + "                 AS vrContribucionCargoFijoAseo, "
	                + "       SUM(tmpPRC.vrSubsidioCargoFijoAseo)       "
	                + "                     AS vrSubsidioCargoFijoAseo, "
	                + "      ISNULL(                                    "
	                + "       MAX(tbldctosordenes.comentario),'')       "
	                + "                           AS textoFinanciacion, "
	                + "      MAX(tbltercerosruta.nombreCiclo) + '-' +   "
	                + "      MAX(tbltercerosruta.nombreRuta)            "
	                + "                                  AS nombreRuta, "
	                + "      MAX(ISNULL(tblterceros.ordenRuta,0))       "
	                + "                                   AS ordenRuta, "
	                + "      MAX(tblterceros.idRuta) AS idRuta,         "
	                + "      MAX(tblterceroestracto.nombreEstracto)     "
	                + "                              AS nombreEstracto, "
	                + "      MAX(tblterceros.email) AS emailSuscriptor, "
	                + "  	 MAX(tblterceros.direccionCobro)            "
	                + "  		                 AS direccionCobro, "
	                + "      tmpPRC.fechaInicial AS fechaDesde,         "
	                + "      tmpPRC.fechaFinal AS fechaHasta,           "
	                + "      tmpPRC.fechaSinRecargo,                    "
	                + "      tmpPRC.fechaConRecargo,                    "
	                + "      tmpPRC.nombrePeriodo                       "
	                + "  FROM (                                         "
	                + "  SELECT tbldctos.idLocal,                       "
	                + "         tbldctos.idTipoOrden,                   "
	                + "         tbldctos.idOrden,                       "
	                + "         tbldctos.idDcto,                        "
	                + "         tbldctos.idPeriodo,                     "
	                + "         tbldctos.idCliente,                     "
	                + "         MAX(tbldctos.fechaDcto) AS fechaDcto,   "
	                + "         tblplus.idTipo,                         "
	                + "         SUM (CASE WHEN tblplus.idTipo = 4       "
	                + "       THEN (tbldctosordenesdetalle.cantidad  *  "
	                + "       tbldctosordenesdetalle.vrVentaUnitario)   "
	                + "          ELSE 0 END) AS vrConsumo,              "
	                + "         SUM(CASE WHEN tblplus.idTipo = 104      "
	                + "         THEN (tbldctosordenesdetalle.cantidad * "
	                + "         tbldctosordenesdetalle.vrVentaUnitario) "
	                + "           ELSE 0 END) AS vrDeudaConsumo,        "
	                + "         MAX(CASE WHEN tblplus.idTipo = 4        "
	                + "           THEN                                  "
	                + "         tbldctosordenesdetalle.vrVentaUnitario  "
	                + "          ELSE 0 END) AS vrVentaUnitario,  	    "
	                + "         MAX (CASE WHEN tblplus.idTipo = 4       "
	                + "             THEN                                "
	                + "         tbldctosordenesdetalle.lecturaMedidor   "
	                + "             ELSE 0                             "
	                + "           END) AS lecturaMedidor,              "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo = 4           "
	                + "      THEN tbldctosordenesdetalle.cantidad      "
	                + "             ELSE 0                             "
	                + "           END) AS cantidad,                    "
	                + "         SUM (CASE WHEN tblplus.idTipo = 6      "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "       tbldctosordenesdetalle.vrVentaUnitario)  "
	                + "             ELSE 0 END) AS vrCargoFijo,        "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo = 106         "
	                + "    THEN  (tbldctosordenesdetalle.cantidad *    "
	                + "    tbldctosordenesdetalle.vrVentaUnitario )    "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaCargoFijo,            "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (9)        "
	                + "    THEN  (tbldctosordenesdetalle.cantidad *    "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "           ELSE 0                               "
	                + "           END) AS vrMedidor,                   "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (109)      "
	                + "    THEN  (tbldctosordenesdetalle.cantidad *    "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "           ELSE 0                               "
	                + "           END) AS vrDeudaMedidor, 		   "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (8)        "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrFinanciacion,              "
	                + "          SUM(CASE WHEN tblplus.idTipo IN (108) "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaFinanciacion,         "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (12)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrInteres,                   "
	                + "           SUM (CASE WHEN                       "
	                + "       tblplus.idTipo IN (112)      "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "    tbldctosordenesdetalle.vrVentaUnitario )    "
	                + "             ELSE 0                             "
	                + "           END) AS vrInteresAcum,               "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (13)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrReconexion,    		   "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (113)      "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaReconexion,	   "
	                + "           SUM (CASE WHEN                       "
	                + "     tblplus.idTipo IN (14)         "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrConexion,                  "
	                + "           SUM (CASE WHEN                       "
	                + "     tblplus.idTipo IN (114)        "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaConexion,   	   "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (15)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "   tbldctosordenesdetalle.vrVentaUnitario )     "
	                + "             ELSE 0                             "
	                + "           END) AS vrReinstalacion,             "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (115)      "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "   tbldctosordenesdetalle.vrVentaUnitario )     "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaReinstalacion, 	   "
	                + " SUM (CASE WHEN                     "
	                + "       tblplus.idTipo IN (16)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrSuspencion,                "
	                + " SUM (CASE WHEN                     "
	                + "       tblplus.idTipo IN (116)      "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaSuspencion, 	   "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (17)       "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "    tbldctosordenesdetalle.vrVentaUnitario  )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrCargoCorte,                "
	                + "SUM (CASE WHEN                      "
	                + "       tblplus.idTipo IN (117)      "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "    tbldctosordenesdetalle.vrVentaUnitario  )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaCargoCorte,     	   "
	                + " SUM (CASE WHEN                     "
	                + "       tblplus.idTipo IN (18)       "
	                + "    THEN  (tbldctosordenesdetalle.cantidad  *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrOtros,                     "
	                + " SUM (CASE WHEN                     "
	                + "       tblplus.idTipo IN (118)      "
	                + "    THEN  (tbldctosordenesdetalle.cantidad  *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaOtros,		   "
	                + "        SUM(CASE WHEN tblplus.idTipo IN (19,21) "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "       tbldctosordenesdetalle.vrVentaUnitario)  "
	                + "             ELSE 0 END) AS vrSubsidio,         "
	                + "          SUM(CASE WHEN tblplus.idTipo IN (19)  "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "       tbldctosordenesdetalle.vrVentaUnitario)  "
	                + "            ELSE 0 END) AS vrSubsidioCargoFijo, "
	                + "         SUM(CASE WHEN tblplus.idTipo IN (21)   "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "      tbldctosordenesdetalle.vrVentaUnitario)   "
	                + "             ELSE 0 END) AS vrSubsidioConsumo,  "
	                + " 	 SUM(CASE WHEN tblplus.idTipo IN (119,121) "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "       tbldctosordenesdetalle.vrVentaUnitario ) "
	                + "                ELSE 0 END) AS vrDeudaSubsidio, "
	                + "        SUM(CASE WHEN tblplus.idTipo IN (20,22) "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "       tbldctosordenesdetalle.vrVentaUnitario ) "
	                + "             ELSE 0 END) AS vrContribucion,     "
	                + "       SUM(CASE WHEN tblplus.idTipo IN (20)     "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "        ELSE 0 END) AS vrContribucionCargoFijo, "
	                + "      SUM(CASE WHEN tblplus.idTipo IN (22)      "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "        ELSE 0 END) AS vrContribucionConsumo,   "
	                + "      SUM(CASE WHEN tblplus.idTipo IN (120,122) "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "            ELSE 0 END) AS vrDeudaContribucion, "
	                + " SUM (CASE WHEN                     "
	                + "       tblplus.idTipo IN (50)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrAlcantarillaCargoFijo, 	   "
	                + " SUM (CASE WHEN                     "
	                + "       tblplus.idTipo IN (51)       "
	                + "    THEN  (tbldctosordenesdetalle.cantidad *    "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "          END) AS vrAlcantarillaConsumo, 	 "
	                + " SUM (CASE WHEN                     "
	                + "       tblplus.idTipo IN (23)       "
	                + "    THEN  (tbldctosordenesdetalle.cantidad *    "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "          END) AS vrAjusteDecena,        	   "
	                + " 	 SUM(CASE WHEN tblplus.idTipo IN (123)     "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "            ELSE 0 END) AS vrDeudaAjusteDecena, "
	                + "         SUM (CASE WHEN tblplus.idTipo IN       "
	                + "              (106,104,105,108,109,110,111,112  "
	                + "              ,113,119,114,115,116,117,118,121  "
	                + "              ,120,122,123,129,125,10,11,151    "
	                + "              ,150,160,127,128)                 "
	                + "      THEN (tbldctosordenesdetalle.cantidad *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "           END) AS deudaAnterior                "
	                + "           ,MAX(tbldctosperiodo.fechaInicial)   "
	                + "                             AS  fechaInicial   "
	                + "      ,MAX (tbldctosperiodo.fechaFinal )        "
	                + "                      AS  fechaFinal            "
	                + "      ,MAX(tbldctosperiodo.fechaSinRecargo)     "
	                + "                       AS fechaSinRecargo       "
	                + "       ,MAX(tbldctosperiodo.fechaConRecargo)      "
	                + "                        AS fechaConRecargo      "
	                + "       ,MAX(tbldctosperiodo.nombrePeriodo)      "
	                + "                       AS  nombrePeriodo        "
	                + " 	  ,tbldctosordenesdetalle.idEstracto       "
	                + " 	  ,tbldctosordenesdetalle.idRuta           "
	                + "	  ,SUM (CASE WHEN tblplus.idTipo IN (29)   "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "        ELSE 0                                  "
	                + "     END) AS vrSubsidioCargoFijoAlcantarillado, "
	                + "        SUM (CASE WHEN tblplus.idTipo IN (25)   "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "        ELSE 0                                  "
	                + "       END) AS vrSubsidioConsumoAlcantarillado, "
	                + "        SUM (CASE WHEN tblplus.idTipo IN (24)   "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "        ELSE 0                                  "
	                + "        END) AS vrContribucionCargoFijoAlcantarillado, "
	                + "        SUM (CASE WHEN tblplus.idTipo IN (26)   "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "        ELSE 0                                  "
	                + "        END) AS vrContribucionConsumoAlcantarillado, "
	                + "       SUM(CASE WHEN tblplus.idTipo IN (60)     "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "                ELSE 0 END) AS vrAseoCargoFijo, "
	                + "        SUM (CASE WHEN tblplus.idTipo IN (28)   "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "        ELSE 0                                  "
	                + "        END) AS vrContribucionCargoFijoAseo,	   "
	                + "        SUM (CASE WHEN tblplus.idTipo IN (27)   "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "        ELSE 0                                  "
	                + "        END) AS vrSubsidioCargoFijoAseo         "
	                + "  FROM tbldctos                                "
	                + "  INNER JOIN tbldctosordenesdetalle             "
	                + "  ON tbldctos.IDLOCAL           =               "
	                + "     tbldctosordenesdetalle.IDLOCAL "
	                + "  AND tbldctos.IDTIPOORDEN       =              "
	                + "tbldctosordenesdetalle.IDTIPOORDEN  "
	                + "  AND tbldctos.IDORDEN            =             "
	                + "    tbldctosordenesdetalle.IDORDEN  "
	                + "  INNER JOIN tblplus                            "
	                + "  ON tbldctosordenesdetalle.IDPLU =             "
	                + "                      tblplus.idPlu "
	                + "  AND tbldctosordenesdetalle.idLocal =          "
	                + "                    tblplus.idLocal "
	                + "  INNER JOIN tbldctosperiodo                    "
	                + "  ON tbldctosperiodo.idPeriodo =                "
	                + "                 tbldctos.idPeriodo "
	                + "  AND tbldctosperiodo.idLocal =                 "
	                + "                   tbldctos.idLocal "
	                + "  WHERE tbldctos.idLocal       =                "
	                + "?2                                 "
	                + "  AND       tbldctos.idTipoOrden   =            "
	                + "?3                             "
	                + "  AND       tbldctos.idPeriodo     =            "
	                + "?1                               "
	                + "  GROUP BY tbldctos.idLocal,                    "
	                + "          tbldctos.idTipoOrden,                 "
	                + "          tbldctos.idOrden,                     "
	                + "          tbldctos.idDcto,                      "
	                + "          tbldctos.idPeriodo,                   "
	                + "          tbldctos.idCliente,                   "
	                + "         tblplus.idTipo,                        "
	                + " 	   tbldctosordenesdetalle.idEstracto,      "
	                + " 	   tbldctosordenesdetalle.idRuta           "
	                + "  UNION ALL                                     "
	                + "  SELECT tbldctos.idLocalCruce AS idLocal,      "
	                + "         tbldctos.idTipoOrdenCruce              "
	                + "                                AS idTipoOrden, "
	                + "         tbldctos.idOrdenCruce AS idOrden,      "
	                + "         tbldctos.idDctoCruce AS idDcto,        "
	                + "         tbldctos.idPeriodo,                    "
	                + "         tbldctos.idCliente,                    "
	                + "         tbldctos.fechaDcto,                    "
	                + "         tblplus.idTipo,                        "
	                + "        (CASE WHEN tblplus.idTipo = 4           "
	                + "      THEN (tbldctosordenesdetalle.cantidad  *  "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "         ELSE 0 END) AS vrConsumo,              "
	                + "        (CASE WHEN tblplus.idTipo = 104         "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "       tbldctosordenesdetalle.vrVentaUnitario)  "
	                + "        ELSE 0 END) AS vrDeudaConsumo, 	   "
	                + "        (CASE WHEN tblplus.idTipo = 4           "
	                + "    THEN tbldctosordenesdetalle.vrVentaUnitario "
	                + "               ELSE 0 END) AS vrVentaUnitario,  "
	                + "          (CASE WHEN tblplus.idTipo = 4          "
	                + "     THEN tbldctosordenesdetalle.lecturaMedidor  "
	                + "             ELSE 0 END) AS lecturaMedidor,     "
	                + "          (CASE WHEN tblplus.idTipo = 4          "
	                + "          THEN tbldctosordenesdetalle.cantidad  "
	                + "             ELSE 0 END) AS cantidad,           "
	                + "           (CASE WHEN tblplus.idTipo = 6        "
	                + "      THEN (tbldctosordenesdetalle.cantidad *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0 END) AS vrCargoFijo,        "
	                + "           (CASE WHEN  tblplus.idTipo = 106     "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0 END) AS vrDeudaCargoFijo,   "
	                + "           (CASE WHEN tblplus.idTipo IN (9)     "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "           ELSE 0 END) AS vrMedidor,            "
	                + "          (CASE WHEN tblplus.idTipo IN (109)    "
	                + "    THEN  (tbldctosordenesdetalle.cantidad *    "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "           ELSE 0 END) AS vrDeudaMedidor, 	   "
	                + "           (CASE WHEN tblplus.idTipo IN (8)     "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0  END) AS vrFinanciacion,     "
	                + "           (CASE WHEN tblplus.idTipo IN (108)   "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaFinanciacion,         "
	                + "           (CASE WHEN                           "
	                + "       tblplus.idTipo IN (12)       "
	                + "    THEN  (tbldctosordenesdetalle.cantidad *    "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrInteres,                   "
	                + "           (CASE WHEN                           "
	                + "       tblplus.idTipo IN (112)      "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrInteresAcum,               "
	                + "           (CASE WHEN                           "
	                + "       tblplus.idTipo IN (13)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrReconexion,                "
	                + "            (CASE WHEN                          "
	                + "       tblplus.idTipo IN (113)      "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaReconexion,           "
	                + " 			                           "
	                + "            (CASE WHEN                          "
	                + "       tblplus.idTipo IN (14)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrConexion,                  "
	                + "            (CASE WHEN                          "
	                + "     tblplus.idTipo IN (114)        "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaConexion, 		   "
	                + "           (CASE WHEN                           "
	                + "       tblplus.idTipo IN (15)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrReinstalacion,             "
	                + "         (CASE WHEN                             "
	                + "       tblplus.idTipo IN (115)      "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "   tbldctosordenesdetalle.vrVentaUnitario )     "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaReinstalacion, 	   "
	                + "            (CASE WHEN                          "
	                + "       tblplus.idTipo IN (16)       "
	                + "    THEN  (tbldctosordenesdetalle.cantidad *    "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrSuspencion,                "
	                + "           (CASE WHEN                           "
	                + "       tblplus.idTipo IN (116)      "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaSuspencion, 	   "
	                + "           (CASE WHEN                           "
	                + "       tblplus.idTipo IN (17)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrCargoCorte,                "
	                + "          (CASE WHEN                            "
	                + "       tblplus.idTipo IN (117)      "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "    tbldctosordenesdetalle.vrVentaUnitario  )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaCargoCorte,  	   "
	                + "            (CASE WHEN                          "
	                + "       tblplus.idTipo IN (18)       "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "    tbldctosordenesdetalle.vrVentaUnitario )    "
	                + "             ELSE 0                             "
	                + "           END) AS vrOtros,                     "
	                + "         (CASE WHEN                             "
	                + "       tblplus.idTipo IN (118)      "
	                + "    THEN  (tbldctosordenesdetalle.cantidad  *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrDeudaOtros,		   "
	                + "            (CASE WHEN                          "
	                + "       tblplus.idTipo IN (19,21)    "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "       tbldctosordenesdetalle.vrVentaUnitario)  "
	                + "             ELSE 0                             "
	                + "           END) AS vrSubsidio,                  "
	                + "            (CASE WHEN                          "
	                + "       tblplus.idTipo IN (19)       "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario)   "
	                + "             ELSE 0                             "
	                + "           END) AS vrSubsidioCargoFijo,	   "
	                + "            (CASE WHEN                          "
	                + "      tblplus.idTipo IN (21)        "
	                + "      THEN  (tbldctosordenesdetalle.cantidad *  "
	                + "      tbldctosordenesdetalle.vrVentaUnitario)   "
	                + "              ELSE 0                            "
	                + "                   END) AS vrSubsidioConsumo,   "
	                + "         (CASE WHEN tblplus.idTipo IN (119,121) "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "                ELSE 0 END) AS vrDeudaSubsidio, "
	                + "        (CASE WHEN tblplus.idTipo IN (20,22)    "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "             ELSE 0 END) AS vrContribucion,     "
	                + "       (CASE WHEN tblplus.idTipo IN (20)        "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "        ELSE 0 END) AS vrContribucionCargoFijo, "
	                + "       (CASE WHEN tblplus.idTipo IN (22)        "
	                + "       THEN (tbldctosordenesdetalle.cantidad *  "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "        ELSE 0 END) AS vrContribucionConsumo,   "
	                + " 	  (CASE WHEN tblplus.idTipo IN (120,122)   "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "           ELSE 0 END) AS vrDeudaContribucion,   "
	                + "            (CASE WHEN                          "
	                + "       tblplus.idTipo IN (50)       "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "      tbldctosordenesdetalle.vrVentaUnitario )  "
	                + "             ELSE 0                             "
	                + "           END) AS vrAlcantarillaCargoFijo, 	   "
	                + "            (CASE WHEN                          "
	                + "       tblplus.idTipo IN (51)       "
	                + "     THEN  (tbldctosordenesdetalle.cantidad *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "           END) AS vrAlcantarillaConsumo, 	   "
	                + "       (CASE WHEN tblplus.idTipo IN (23)        "
	                + "      THEN (tbldctosordenesdetalle.cantidad *   "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "               ELSE 0 END) AS vrAjusteDecena,   "
	                + " 	    (CASE WHEN tblplus.idTipo IN (123)     "
	                + "        THEN (tbldctosordenesdetalle.cantidad * "
	                + "        tbldctosordenesdetalle.vrVentaUnitario) "
	                + "            ELSE 0 END) AS vrDeudaAjusteDecena, "
	                + "          (CASE WHEN tblplus.idTipo IN          "
	                + "              (106,104,105,108,109,110,111,112  "
	                + "              ,113,119,114,115,116,117,118,121  "
	                + "              ,120,122,123,129,125,10,11,151    "
	                + "              ,150,160,127,128)                 "
	                + "     THEN (tbldctosordenesdetalle.cantidad *    "
	                + "     tbldctosordenesdetalle.vrVentaUnitario )   "
	                + "             ELSE 0                             "
	                + "           END) AS deudaAnterior                "
	                + ",tbldctosperiodo.fechaInicial        "
	                + ",tbldctosperiodo.fechaFinal          "
	                + ",tbldctosperiodo.fechaSinRecargo     "
	                + ",tbldctosperiodo.fechaConRecargo     "
	                + ",tbldctosperiodo.nombrePeriodo       "
	                + " 	    ,tbldctosordenesdetalle.idEstracto     "
	                + " 	    ,tbldctosordenesdetalle.idRuta         "
	                + "         ,(CASE WHEN tblplus.idTipo IN (29)     "
	                + "         THEN (tbldctosordenesdetalle.cantidad * "
	                + "         tbldctosordenesdetalle.vrVentaUnitario) "
	                + "         ELSE 0 END)                             "
	                + "           AS vrSubsidioCargoFijoAlcantarillado, "
	                + "          (CASE WHEN tblplus.idTipo IN (25)      "
	                + "         THEN (tbldctosordenesdetalle.cantidad * "
	                + "         tbldctosordenesdetalle.vrVentaUnitario) "
	                + "          ELSE 0  END)                           "
	                + "             AS vrSubsidioConsumoAlcantarillado, "
	                + "          (CASE WHEN tblplus.idTipo IN (24)      "
	                + "         THEN (tbldctosordenesdetalle.cantidad * "
	                + "         tbldctosordenesdetalle.vrVentaUnitario) "
	                + "           ELSE 0 END)                           "
	                + "       AS vrContribucionCargoFijoAlcantarillado, "
	                + "         (CASE WHEN tblplus.idTipo IN (26)       "
	                + "         THEN (tbldctosordenesdetalle.cantidad * "
	                + "         tbldctosordenesdetalle.vrVentaUnitario) "
	                + "         ELSE 0 END)                             "
	                + "         AS vrContribucionConsumoAlcantarillado, "
	                + "          (CASE WHEN tblplus.idTipo IN (60)       "
	                + "         THEN (tbldctosordenesdetalle.cantidad * "
	                + "         tbldctosordenesdetalle.vrVentaUnitario) "
	                + "                 ELSE 0 END) AS vrAseoCargoFijo, "
	                + "         (CASE WHEN tblplus.idTipo IN (28)       "
	                + "         THEN (tbldctosordenesdetalle.cantidad * "
	                + "         tbldctosordenesdetalle.vrVentaUnitario) "
	                + "          ELSE 0 END)                            "
	                + "                 AS vrContribucionCargoFijoAseo, "
	                + "         (CASE WHEN tblplus.idTipo IN (27)       "
	                + "         THEN (tbldctosordenesdetalle.cantidad * "
	                + "         tbldctosordenesdetalle.vrVentaUnitario) "
	                + "         ELSE 0 END) AS vrSubsidioCargoFijoAseo  "
	                + "  FROM         tbldctos                          "
	                + "  INNER JOIN tbldctosordenesdetalle              "
	                + "  ON tbldctos.IDLOCAL           =                "
	                + "     tbldctosordenesdetalle.IDLOCAL  "
	                + "  AND tbldctos.IDTIPOORDEN       =               "
	                + " tbldctosordenesdetalle.IDTIPOORDEN  "
	                + "  AND tbldctos.IDORDEN            =              "
	                + "     tbldctosordenesdetalle.IDORDEN  "
	                + "  INNER JOIN tblplus                             "
	                + "  ON tbldctosordenesdetalle.IDPLU =              "
	                + "                       tblplus.idPlu "
	                + "  AND tbldctosordenesdetalle.idLocal =           "
	                + "                     tblplus.idLocal "
	                + "  INNER JOIN tbldctosperiodo                     "
	                + "  ON tbldctosperiodo.idPeriodo =                 "
	                + "                 tbldctos.idPeriodo  "
	                + "  AND tbldctosperiodo.idLocal = tbldctos.idLocal "
	                + "  WHERE     tbldctos.idLocal             =      "
	                + "?2                                  "
	                + "  AND       tbldctos.idTipoOrden         = 29   "
	                + "  AND       tbldctos.idPeriodo           =      "
	                + "?1) AS tmpPRC                    "
	                + "  INNER JOIN tbllocales                         "
	                + "  ON tbllocales.idLocal  =  tmpPRC.IDLOCAL      "
	                + "  INNER JOIN tblterceros                        "
	                + "  ON tblterceros.idLocal  = tmpPRC.IDLOCAL      "
	                + "  AND tblterceros.idCliente  = tmpPRC.idCliente "
	                + "  INNER JOIN tbldctosordenes                    "
	                + "  ON tbldctosordenes.idLocal = tmpPRC.IDLOCAL   "
	                + "  AND tbldctosordenes.idTipoOrden   =           "
	                + "                 tmpPRC.idTipoOrden "
	                + "  AND tbldctosordenes.idOrden =  tmpPRC.idOrden "
	                + "  INNER JOIN tblterceroestracto                 "
	                + "  ON tblterceroestracto.idEstracto =            "
	                + "                  tmpPRC.idEstracto "
	                + "  AND tblterceroestracto.idLocal =              "
	                + "                     tmpPRC.idLocal "
	                + "  INNER JOIN tbltercerosruta                    "
	                + "  ON tmpPRC.idRuta = tbltercerosruta.idRuta     "
	                + "  AND tmpPRC.idLocal =  tbltercerosruta.idLocal "
	                + "  INNER JOIN                                    "
	                + "  (SELECT tbldctosordenesdetalle.IDLOCAL,       "
	                + "          tbldctosordenesdetalle.IDTIPOORDEN,   "
	                + " 	     tbldctosordenesdetalle.IDORDEN,       "
	                + " 	     tbltipocausanota.nombreCausa          "
	                + "  FROM     tbldctosordenes                      "
	                + "  INNER JOIN tbldctosordenesdetalle             "
	                + "  ON tbldctosordenes.IDLOCAL =                  "
	                + "   tbldctosordenesdetalle.IDLOCAL   "
	                + "  AND tbldctosordenes.IDTIPOORDEN =             "
	                + "          tbldctosordenesdetalle.IDTIPOORDEN    "
	                + "  AND  tbldctosordenes.IDORDEN =                "
	                + "  tbldctosordenesdetalle.IDORDEN    "
	                + "  INNER JOIN tbltipocausanota                   "
	                + "  ON tbltipocausanota.idCausa =                 "
	                + "       tbldctosordenesdetalle.idNovedadLectura  "
	                + "  WHERE tbldctosordenesdetalle.IDLOCAL   =      "
	                + "?2                                  "
	                + "  AND tbldctosordenesdetalle.IDTIPOORDEN =      "
	                + "?3                             "
	                + "  AND tbldctosordenes.IDPERIODO          =      "
	                + "?1                                "
	                + "  AND tbldctosordenesdetalle.IDTIPO      = 4    "
	                + "  AND tbldctosordenesdetalle.STRIDLISTA  = 1    "
	                + "  AND tbltipocausanota.idTipoTabla       = 2)   "
	                + "                               AS tmpFRA        "
	                + "  ON tmpFRA.IDLOCAL=tmpPRC.IDLOCAL              "
	                + "  AND tmpFRA.IDTIPOORDEN = tmpPRC.idTipoOrden   "
	                + "  AND tmpFRA.IDORDEN=tmpPRC.idOrden             "
	                + "  INNER JOIN tblmedidores                       "
	                + "  ON tblmedidores.idLocal = tblterceros.IDLOCAL "
	                + "  AND tblmedidores.idMedidor =                  "
	                + "              tblterceros.idMedidor "
	                + "  INNER JOIN tbltipocausanota                   "
	                + "  ON tbltipocausanota.idCausa                   "
	                + "                       = tbldctosordenes.estado "
	                + "  WHERE     tmpPRC.idLocal         =            "
	                + "?2                                  "
	                + "  AND       tmpPRC.idTipoOrden     =            "
	                + "?3                             "
	                //   + "  AND       tbltercerosruta.idRuta =            "
	                //   + getIdRuta() + "                                  "
	                + "  AND       tmpPRC.idPeriodo       =            "
	                + "?1                               "
	                + "  AND tbltipocausanota.idTipoTabla  = 3         "
	                + "  GROUP BY tmpPRC.idLocal,                      "
	                + "         tmpPRC.idTipoOrden,                    "
	                + "         tmpPRC.idOrden,                        "
	                + "         tmpPRC.idDcto,                         "
	                + "         tmpPRC.idPeriodo,                      "
	                + "         tmpPRC.idCliente                       "
	                + "        ,tmpPRC.fechaInicial                    "
	                + "        ,tmpPRC.fechaFinal                      "
	                + "        ,tmpPRC.fechaSinRecargo                 "
	                + "        ,tmpPRC.fechaConRecargo                 "
	                + "        ,tmpPRC.nombrePeriodo                   "
	                + "  ORDER BY idRuta,ordenRuta   ", nativeQuery = true)
		  List<TblDctosOrdenesDTO> listaDetalleDeudaRuta(int idPeriodo, int idLocal, int idTipoOrden );
		  
		  
		  
		  
		  @Query( value = "SELECT  COUNT(*) AS cuenta " +
		  		  "FROM    tbldctos " +
				  "JOIN bdaquamovil.dbo.tbldctosOrdenes " +
		  		  "ON tbldctos.IDLOCAL      =   tbldctosOrdenes.IDLOCAL  " + 
				  "AND tbldctos.IDTIPOORDEN =   tbldctosOrdenes.IDTIPOORDEN " +
		  		  "AND tbldctos.IDORDEN       =      tbldctosOrdenes.IDORDEN  " +
		  		  "WHERE tbldctos.IDLOCAL     = ?1  " + 
		  		  "AND   tbldctos.IDTIPOORDEN = ?2 " +
		  		  "AND   tbldctos.idPeriodo   = ?3 " +
		  		  "GROUP BY tbldctos.IDLOCAL, " +
		  		  "tbldctos.IDTIPOORDEN "
		  		  , nativeQuery = true)
		  List<TblDctosOrdenesDTO> PeriodoFacturado(int idLocal,  int idTipoOrden, int idPeriodo );
		  
		  
		  
		  @Query( value = "SELECT TOP 1 tbldctosordenes.idOrden     "

	                + "FROM tbldctosordenes                      "
	                + "WHERE tbldctosordenes.idLocal   =         "
	                + "?1                            "
	                + "AND tbldctosordenes.idPeriodo   =         "
	                + "?2                          "
	                + "AND  tbldctosordenes.IDTIPOORDEN =        "
	                + "?3                        "
	                + "AND  tbldctosordenes.idRazon     = ?4      "

		  		  , nativeQuery = true)
		  Integer listaOrdenIdPeriodo(int idLocal,  int idPeriodo, int IdTipoOrden, int idRazon);
		  
		  
		  
		  
		  @Query( value = "SELECT MAX(tbldctosordenes.idOrden) "
	                + "FROM tbldctosordenes              "
	                + "WHERE tbldctosordenes.idLocal   = ?1 "
		  		  , nativeQuery = true)
		  Integer maximaIdOrdenIdLocal(int idLocal);
		  
		  
		  
		  @Query( value = "SELECT TOP 1 tbldctosordenes.idLog     "

	                + "FROM tbldctosordenes                      "
	                + "WHERE tbldctosordenes.idLocal   =         "
	                + "?1                            "
	                + "AND tbldctosordenes.idPeriodo   =         "
	                + "?2                          "
	                + "AND  tbldctosordenes.IDTIPOORDEN =        "
	                + "?3                        "
	                + "AND  tbldctosordenes.idRazon     = ?4      "

		  		  , nativeQuery = true)
		  Integer listaOrdenIdPeriodoIDLOG(int idLocal,  int idPeriodo, int IdTipoOrden, int idRazon);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.IDORDEN " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.idPeriodo = ?2 " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 9 "+
		  		  "AND tblDctosOrdenes.idCliente = ?3 ", nativeQuery = true)
		  Integer ObtenerIdOrdenConFactura(int IDLOCAL, int idPeriodoAnterior, String idCliente);
		  
		  

}






