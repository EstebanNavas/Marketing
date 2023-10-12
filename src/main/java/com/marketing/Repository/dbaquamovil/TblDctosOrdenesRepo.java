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
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 "
		  		  , nativeQuery = true)
		  List<Integer> ObtenerListaNumeroOrden(int IDLOCAL, List<Integer> IDORDEN);
		  
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
}






