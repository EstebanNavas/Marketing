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
	  
	  
	  @Query(value = "SELECT  tblAgendaLogVisitas.IDUSUARIO " +
			  "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
              "WHERE tblAgendaLogVisitas.sessionId = ?1 "
              , nativeQuery = true)
	  List<Integer>  ObtenerIdUsuariosPorIdSession(String sessionId);
	  
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
	  
	  
	  @Query(value = "SELECT tblAgendaLogVisitas.idLocal " +
			  "FROM bdaquamovil.dbo.tblAgendaLogVisitas " +
              "WHERE tblAgendaLogVisitas.sessionId = ?1 " +
			  "AND tblAgendaLogVisitas.idEstadoTx = 9 "
              , nativeQuery = true)
	  List<Integer> ObtenerListaIdLocalPorSession(String sessionId);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblAgendaLogVisitas SET ESTADO = ?1 " +
	                 "WHERE tblagendalogvisitas.idUsuario  = ?2 " +
	                 "AND tblagendalogvisitas.estado = ?3 ", nativeQuery = true)
	  public void actualizaLogVisitaUsuario(int idEstado, int idUsuario, int xEstadoAnterior);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblagendalogvisitas                      "
              + "SET tblagendalogvisitas.estado         =        "
              + "?1,                                 "
              + "    tblagendalogvisitas.idCliente      =       "
              + "?2,                             "
              + "    tblagendalogvisitas.idEstadoVisita =        "
              + "?3,                         "
              + "    tblagendalogvisitas.idTipoOrden    =        "
              + "?4,                            "
              + "    tblagendalogvisitas.idEstadoTx     =        "
              + "?5,                             "
              + "    tblagendalogvisitas.idLocal        =        "
              + "?6,                                "
              + "    tblagendalogvisitas.ipTx           =       "
              + "?7,                                  "
              + "    tblagendalogvisitas.fechaTx        =       "
              + "?8                                "
              + "WHERE tblagendalogvisitas.idLocal      =        "
              + "?6                                 "
              + "AND tblagendalogvisitas.idLog          =   ?9     ", nativeQuery = true)
	  public void finaliza(int Estado, String IdCliente, int IdEstadoVisita, int IdTipoOrden, int IdEstadoTx, int idLocal, String IpTx, String FechaTx, int IdLog);
	  
	  
	  
	  @Query(value = "SELECT tblagendalogvisitas.idCliente    "
              + "FROM tblagendalogvisitas                      "
              + "WHERE tblagendalogvisitas.estado      =       "
              + "?1                                "
              + "AND   CONVERT(CHAR(10),                       "
              + "  tblagendalogvisitas.fechaVisita,111)     = "
              + "?2                       "
              + "AND   tblagendalogvisitas.idUsuario   =   ?3    "
              , nativeQuery = true)
	  String seleccionaVisitaEstadoFecha(int estado, String fecha, int idusuario);
	  
	  
	  @Query(value = "SELECT tblagendalogvisitas.idLog    "
              + "FROM tblagendalogvisitas                      "
              + "WHERE tblagendalogvisitas.estado      =       "
              + "?1                                "
              + "AND   CONVERT(CHAR(10),                       "
              + "  tblagendalogvisitas.fechaVisita,111)     = "
              + "?2                       "
              + "AND   tblagendalogvisitas.idUsuario   =   ?3    "
              , nativeQuery = true)
	  Integer seleccionaVisitaEstadoFechaIDLOG(int estado, String fecha, int idusuario);
	  
	  
	  @Query(value = "SELECT  * "
              + "FROM tblagendalogvisitas                      "
              + "WHERE tblagendalogvisitas.estado      =       "
              + "?1                                "
              + "AND   CONVERT(CHAR(10),                       "
              + "  tblagendalogvisitas.fechaVisita,111)     = "
              + "?2                       "
              + "AND   tblagendalogvisitas.idUsuario   =   ?3    "
              + "AND   tblagendalogvisitas.idLocal   =    ?4    "
              , nativeQuery = true)
	  List<TblAgendaLogVisitas> seleccionaVisitaEstadoxFecha(int estado, String fecha, int idusuario, int idLocal);
	  
	  

	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblagendalogvisitas             "
              + "SET tblagendalogvisitas.estado       = "
              + "?1,                        "
              + "tblagendalogvisitas.fechaVisita      = "
              + "?2          "
              + "WHERE tblagendalogvisitas.idUsuario  = "
              + "?3                      "
              + "AND tblagendalogvisitas.estado      =  ?4 ",
              nativeQuery = true)
	  public void actualizaVisita(int estado, String FechaVisitaSqlServer, int idUsuario, int xEstadoAnterior);
	  
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblagendalogvisitas                      "
              + "SET tblagendalogvisitas.estado         =        "
              + "?1,                                 "
              + "    tblagendalogvisitas.idEstadoVisita =        "
              + "?2,                         "
              + "    tblagendalogvisitas.idTipoOrden    =        "
              + "?3,                            "
              + "    tblagendalogvisitas.idEstadoTx     =        "
              + "?4,                             "
              + "    tblagendalogvisitas.ipTx           =       "
              + "?5,                                  "
              + "    tblagendalogvisitas.fechaTx        =       "
              + "?6                                "
              + "WHERE tblagendalogvisitas.idLocal      =        "
              + "?7                                 "
              + "AND tblagendalogvisitas.idLog          =  ?8      ",
              nativeQuery = true)
	  public void finalizaVisita(int estado, int idEstadoVisita, int IdTipoOrden, int IdEstadoTx, String IpTx, String FechaTx, int IdLocal, int idLog);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "INSERT INTO tblagendalogvisitas (idLog,           "
              + "                                 idCliente,       "
              + "                                 idUsuario,       "
              + "                                 idLocalTercero,  "
              + "                                 idLocal,         "
              + "                                 idPeriodo,       "
              + "                                 fechaVisita,     "
              + "                                 idEstadoVisita,  "
              + "                                 estado,          "
              + "                                 idTipoOrden,     "                
              + "                                 fechaTxInicio)   "
              + "VALUES (?1,"
              + "?2,"
              + "?3,"
              + "?4,"
              + "?5,"
              + "?6,"
              + "?7,"
              + "?8,"
              + "?9,"
              + "?10,"                
              + "?11) ",
              nativeQuery = true)
	  public void ingresaLogVisita(int idLog, String idCliente, int idUsuario, int idLocalTercero, int idLocal, int idPeriodo, String fechaVisita,
			  int idEstadoVisita, int estado, int idTipoOrden, String fechaTxInicio);
	  
	  
	  
	  @Query(value = "SELECT tblagendalogvisitas.IDLOG                  "
              + "FROM   tblagendalogvisitas                        "
              + "WHERE  tblagendalogvisitas.idusuario        =     "
              + "?1                                "
              + "AND    tblagendalogvisitas.FECHAVISITA      =    "
              + "?2                     "
              + "AND    tblagendalogvisitas.estado           =     "
              + "?3                                    "
              + "AND    tblagendalogvisitas.idLocal          =     "
              + "?4                                    "
              + "AND ( EXISTS (SELECT tbldctosordenes.*            "
              + "            FROM   tbldctosordenes                "
              + "            INNER JOIN tbldctosordenesdetalle     "
              + "            ON tbldctosordenes.IDLOCAL      =     "
              + "               tbldctosordenesdetalle.IDLOCAL     "
              + "            AND tbldctosordenes.IDTIPOORDEN =     "
              + "               tbldctosordenesdetalle.IDTIPOORDEN "
              + "            AND tbldctosordenes.IDORDEN     =     "
              + "                   tbldctosordenesdetalle.IDORDEN "
              + "          WHERE tblagendalogvisitas.IDLOG   =     "
              + "                           tbldctosordenes.idLog  "
              + "          AND tblagendalogvisitas.IDLOCAL   =     " 
              + "                         tbldctosordenes.IDLOCAL) "
              + "OR  EXISTS ( SELECT tblpagos.*                    "
              + "             FROM   tblpagos                      "
              + "             INNER JOIN tblpagosmedios            "
              + "                    ON tblpagos.idLocal      =    "
              + "                          tblpagosmedios.idLocal  "
              + "                    AND tblpagos.idTipoOrden =    "
              + "                       tblpagosmedios.idTipoOrden "
              + "                    AND tblpagos.idRecibo    =    "
              + "                          tblpagosmedios.idRecibo "
              + "                    AND tblpagos.indicador   =    "
              + "                         tblpagosmedios.indicador "
              + "               AND tblagendalogvisitas.idLog =    "
              + "                                 tblpagos.idLog   "
              + "             AND tblagendalogvisitas.idLocal =    " 
              + "                               tblpagos.idLocal)) "
              , nativeQuery = true)
	  Integer validaLogOcupado(int IdUsuario, String FechaVisita, int Estado, int IdLocal);
	  
	  
	  
	  
	  @Query(value = "SELECT  * "
              + "FROM tblagendalogvisitas                      "
              + "WHERE tblagendalogvisitas.idLocal      =   ?1    "
              + "AND   tblagendalogvisitas.IDLOG   =   ?2    ",
              nativeQuery = true)
	  List<TblAgendaLogVisitas> ObtenerRegistroDelLogin(int idLocal, int idLog);
	  
	  
	  
	  @Query(value = "SELECT  tblagendalogvisitas.FECHAVISITA "
              + "FROM tblagendalogvisitas                      "
              + "WHERE tblagendalogvisitas.idLocal      =   ?1    "
              + "AND   tblagendalogvisitas.IDLOG   =   ?2    "
              + "AND   tblagendalogvisitas.sessionId   =   ?3    "
              + "AND   tblagendalogvisitas.idEstadoTx   =   9    ",
              nativeQuery = true)
	  String ObtenerFechaYHoraSessionId(int idLocal, int idLog, String sessionId);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblagendalogvisitas                  "
              + "SET tblagendalogvisitas.FECHAVISITA    =  ?1     "
              + "WHERE tblagendalogvisitas.idLocal      =  ?2     "           
              + "AND tblagendalogvisitas.idLog          =  ?3     "
              + "AND tblagendalogvisitas.sessionId      =  ?4     ",
              nativeQuery = true)
	  public void actualizaActividad(String FECHAVISITA, int IdLocal, int idLog, String sessionId);
	  
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblagendalogvisitas             "
              + "SET tblagendalogvisitas.estado       = "
              + "?1                         "
              + "WHERE tblagendalogvisitas.idCliente  = "
              + "?2                    "
              + "AND tblagendalogvisitas.estado      = ?3 ",
              nativeQuery = true)
	  public void actualizaLogNE(int estado, String idCliente, int xEstadoAnterior);
	  

}
