package com.marketing.Repository.dbaquamovil;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;

@Repository
public interface TblAgendaLogVisitasRepo extends JpaRepository<TblAgendaLogVisitas, Integer> {

	@Query(value = "SELECT tblAgendaLogVisitas.IDLOG " +
	  		 "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
			  "WHERE tblAgendaLogVisitas.IDUSUARIO = ?1 " +
			  "AND tblAgendaLogVisitas.estado  = 9 ", nativeQuery = true)
	    Integer ObtenerIdLogActivo(int IDUSUARIO); 
	  
	  
	  @Query("SELECT MAX(r.IDLOG) FROM TblAgendaLogVisitas r " )
	    Integer findMaxIDLOG();
	  
	  
	  // Modificamos el ESTADO de 9 a 1
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblAgendaLogVisitas SET ESTADO = 1 " +
	                 "WHERE ESTADO = 9 AND tblAgendaLogVisitas.idLocal = ?1 " +
	                 "AND tblAgendaLogVisitas.IDUSUARIO = ?2", nativeQuery = true)
	  public void actualizarEstadoA1(int idLocal, int IDUSUARIO);
	  
	  // Modificamos el ESTADO de 9 a 1
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblAgendaLogVisitas SET idEstadoTx = 1 " +
	                 "WHERE idEstadoTx = 9 AND tblAgendaLogVisitas.idLocal = ?1 " +
	                 "AND tblAgendaLogVisitas.sessionId = ?2", nativeQuery = true)
	  public void actualizarIdEstadoTxA1(int idLocal, String sessionId);
	  
	  
	  
	  @Query(value = "SELECT tblAgendaLogVisitas.idCliente " +
			  "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
              "WHERE tblAgendaLogVisitas.idLocal = ?1 " +
              "AND tblAgendaLogVisitas.IDUSUARIO = ?2 " +
              "AND IDLOG = (SELECT MAX(IDLOG) FROM bdaquamovil.dbo.tblAgendaLogVisitas WHERE idLocal = ?1 AND IDUSUARIO = ?2) ", nativeQuery = true)
	  Integer ObtenerIdCliente(int idLocal, int IDUSUARIO);
	  
	  
	  @Query(value = "SELECT tblAgendaLogVisitas.ESTADO " +
			  "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
              "WHERE tblAgendaLogVisitas.idLocal = ?1 " +
              "AND tblAgendaLogVisitas.IDUSUARIO = ?2 " +
              "AND tblAgendaLogVisitas.IDLOG = ?3 " +
              "AND tblAgendaLogVisitas.idCliente = ?4", nativeQuery = true)
	  Integer ObtenerEstado(int idLocal, int IDUSUARIO, int IDLOG, int idCliente);
	  
	  // Modificamos el ESTADO de 9 a 1
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblAgendaLogVisitas SET ESTADO = 1 " +
	                 "WHERE ESTADO = 9 AND tblAgendaLogVisitas.idLocal = ?1 " +
	                 "AND tblAgendaLogVisitas.IDUSUARIO = ?2 " +
	                 "AND tblAgendaLogVisitas.IDLOG = ?3", nativeQuery = true)
	  public void actualizarEstadoAlGuardarPQR(int idLocal, int IDUSUARIO, int IDLOG);
	  
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblAgendaLogVisitas SET ESTADO = 9, IDUSUARIO = ?4 " +
	                 "WHERE ESTADO = 1 AND tblAgendaLogVisitas.idLocal = ?1 " +
	                 "AND tblAgendaLogVisitas.idCliente = ?2 " +
	                 "AND tblAgendaLogVisitas.IDLOG = ?3 ", nativeQuery = true)
	  public void actualizarEstadoAlGuardarRespuestaPQR(int idLocal, String idCliente, int IDLOG, int IDUSUARIO);
	  
	  
	  @Query(value = "SELECT tblAgendaLogVisitas.ESTADO " +
			  "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
              "WHERE tblAgendaLogVisitas.idLocal = ?1 " +
              "AND tblAgendaLogVisitas.IDLOG = ?2 " +
              "AND tblAgendaLogVisitas.idCliente = ?3 ", nativeQuery = true)
	  Integer ObtenerEstadoDeIdLog(int idLocal, int IDLOG, String idCliente);
	  
	  // Modificamos el ESTADO Fefinitivo de 9 a 1
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblAgendaLogVisitas SET ESTADO = 1 " +
	                 "WHERE ESTADO = 9 AND tblAgendaLogVisitas.idLocal = ?1 " +
	                 "AND tblAgendaLogVisitas.idCliente = ?2 " +
	                 "AND tblAgendaLogVisitas.IDLOG = ?3", nativeQuery = true)
	  public void actualizarEstadoAlFinalizarRespuesta(int idLocal, String idCliente, int IDLOG);

	  
	  @Query(value = "SELECT tblAgendaLogVisitas.ESTADO " +
			  "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
              "WHERE tblAgendaLogVisitas.idLocal = ?1 " +
              "AND tblAgendaLogVisitas.IDUSUARIO = ?2 " +
              "AND tblAgendaLogVisitas.IDLOG = ?3 "
              , nativeQuery = true)
	  Integer ObtenerEstadoLog(int idLocal, int IDUSUARIO, int IDLOG);
	  
	  @Query(value = "SELECT TOP (1) tblAgendaLogVisitas.idEstadoTx " +
			  "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
              "WHERE tblAgendaLogVisitas.idLocal = ?1 " +
              "AND tblAgendaLogVisitas.IDUSUARIO = ?2 " +
              "ORDER BY IDLOG DESC "
              , nativeQuery = true)
	  Integer ObtenerEstadoLogIdEstadoTx(int idLocal, int IDUSUARIO);
	  
	  @Query(value = "SELECT TOP (100) tblAgendaLogVisitas.sessionId " +
			  "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
			  "WHERE   CONVERT(VARCHAR(10), tblAgendaLogVisitas.FECHAVISITA, 23) " +
	  		  "BETWEEN ?1 AND  ?1 " +
			  "AND tblAgendaLogVisitas.idEstadoTx = 9 "
              , nativeQuery = true)
	  List<String> ObtenerListaSessionId(String fechaVisita);
	  
	  @Query(value = "SELECT tblAgendaLogVisitas.idLocal " +
			  "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
              "WHERE tblAgendaLogVisitas.sessionId = ?1 " +
			  "AND tblAgendaLogVisitas.idEstadoTx = 9 "
              , nativeQuery = true)
	  Integer ObtenerIdLocalPorSession(String sessionId);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblAgendaLogVisitas SET ESTADO = ?1 " +
	                 "WHERE tblagendalogvisitas.idUsuario  = ?2 " +
	                 "AND tblagendalogvisitas.estado = ?3 ", nativeQuery = true)
	  public void actualizaLogVisitaUsuario(int idEstado, int idUsuario, int xEstadoAnterior);

}
