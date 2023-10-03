package com.marketing.Repository.dbaquamovil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblDctosOrdenes;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;

public interface TblDctosOrdenesRepo extends JpaRepository<TblDctosOrdenes, Integer> {

	 @Query("SELECT MAX(r.IDORDEN) FROM TblDctosOrdenes r "+
			 "WHERE r.IDLOCAL = ?1 " +
			  "AND r.IDUSUARIO = ?2")
	    Integer findMaxIDORDEN(int idLocal, int IDUSUARIO);
	 
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
					  "AND r.idCliente = ?2")
			    Integer findMaxNumeroOrden(int idLocal, String idCliente);
		  
		  
		  @Query( value = "SELECT tblDctosOrdenes.IDORDEN " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " + 
		  		  "AND tblDctosOrdenes.numeroOrden = ?2 "+
		  		  "AND tblDctosOrdenes.idCliente = ?3 "+
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 "
		  		  , nativeQuery = true)
		  Integer ObtenerIdOrden(int IDLOCAL,  int numeroOrden, String idCliente);
		  
		  @Query( value = "SELECT tblDctosOrdenes.numeroOrden " +
		  		  "FROM bdaquamovil.dbo.tblDctosOrdenes " +
		  		  "WHERE tblDctosOrdenes.IDLOCAL = ?1 " +
		  		  "AND tblDctosOrdenes.IDTIPOORDEN = 17 "+
		  		  "AND tblDctosOrdenes.IDORDEN = ?2 ", nativeQuery = true)
		  Integer ObtenerNumeroOrden(int IDLOCAL, int IDORDEN);
		  
		  
	
}






