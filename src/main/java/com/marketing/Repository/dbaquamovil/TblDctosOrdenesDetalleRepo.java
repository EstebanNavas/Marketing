package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblDctosOrdenesDetalle;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;

@Repository
public interface TblDctosOrdenesDetalleRepo extends JpaRepository<TblDctosOrdenesDetalle, Integer> {

	// Modificamos el IDTIPOORDEN de 67 a 17
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblDctosOrdenesDetalle SET IDTIPOORDEN = 17, item = 1 " +
	                 "WHERE IDTIPOORDEN = 67 AND tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2", nativeQuery = true)
	  public void actualizarIdTipoOrdenDetalle(int idLocal, int IDORDEN);
	  
	  
	// Eliminamos los registros del IDORDEN de ese momento
		  @Modifying
		  @Transactional
		  @Query(value = "DELETE FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
		                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
		                 "AND tblDctosOrdenesDetalle.IDTIPOORDEN = 67", nativeQuery = true)
		  public void eliminarRegistros(int idLocal, int IDORDEN);
	  
		  
		  @Query(value = "SELECT NOMBREPLU " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.idCliente = ?3", nativeQuery = true)
		  List<String> ObtenerNombresPlus(int idLocal, int IDORDEN, int idCliente);
		  
		  @Query(value = "SELECT DISTINCT comentario " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.idCliente = ?3", nativeQuery = true)
		  String ObtenerComentario(int idLocal, int IDORDEN, int idCliente);
		  
		  @Query(value = "SELECT * " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.IDTIPOORDEN = 17 "
	                 , nativeQuery = true)
		  List<TblDctosOrdenesDetalleDTO> ObtenerInfoPQR(int idLocal, int IDORDEN);
		  
		  
		  @Query(value = "SELECT tblDctosOrdenesDetalle.item " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.IDTIPOORDEN = 17 "
	                 , nativeQuery = true)
		  List<String> ObtenerItems(int idLocal, int IDORDEN);
		  
		  
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblDctosOrdenesDetalle SET ESTADO = 9 " +
		                 "WHERE IDTIPOORDEN = 17 AND tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2", nativeQuery = true)
		  public void actualizarEstadoDetalle(int idLocal, int IDORDEN);
		  
		  
		  @Query(value = "SELECT DISTINCT IDORDEN " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.ESTADO = 9 " 
	                 , nativeQuery = true)
		  Integer ObtenerIdOrdenEstado9(int idLocal);
		  
		  @Query(value = "SELECT ESTADO " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.item = 2 " 
	                 , nativeQuery = true)
		  List<String> ObtenerEstado9(int idLocal);
		  
		  @Query(value = "SELECT DISTINCT comentario " +
	                 "FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
	                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
	                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
	                 "AND tblDctosOrdenesDetalle.idCliente = ?3 " +
	                 "AND tblDctosOrdenesDetalle.item = 2 ", nativeQuery = true)
		  String ObtenerComentarioRespuesta(int idLocal, int IDORDEN, int idCliente);
		  
			// Eliminamos los registros del IDORDEN de la respuesta de ese momento
		  @Modifying
		  @Transactional
		  @Query(value = "DELETE FROM bdaquamovil.dbo.tblDctosOrdenesDetalle " +
		                 "WHERE tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
		                 "AND tblDctosOrdenesDetalle.IDTIPOORDEN = 17 " +
		                 "AND tblDctosOrdenesDetalle.item = 2 ", nativeQuery = true)
		  public void eliminarRegistrosRespuesta(int idLocal, int IDORDEN);
		  
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblDctosOrdenesDetalle SET ESTADO = 1 " +
		                 "WHERE IDTIPOORDEN = 17 AND tblDctosOrdenesDetalle.IDLOCAL = ?1 " +
		                 "AND tblDctosOrdenesDetalle.IDORDEN = ?2 " +
		                 "AND tblDctosOrdenesDetalle.idCliente = ?3", nativeQuery = true)
		  public void actualizarEstadoDetalleFinal(int idLocal, int IDORDEN, String idCliente);
	  
}
