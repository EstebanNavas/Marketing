package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;

@Service
public class TblAgendaLogVisitasService {

	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	
	//OBTENEMOS EL IDLOG MÁXIMO Usuario
	public Integer ObtenerIdLogActivo(int IDUSUARIO) {
		
		Integer  idLog0 = 0;
		Integer maxIDLOG = 0;
		
		//Buscamos el idLog máximo 
         maxIDLOG = tblAgendaLogVisitasRepo.ObtenerIdLogActivo( IDUSUARIO);
        if (maxIDLOG == null) { // Validamos si maxIDLOG es null
        	
            System.out.println("El maxIDLOG en el service es : " + maxIDLOG);
            return idLog0;
        } else {
            return maxIDLOG;
        }
    }
		
		
		//OBTENEMOS EL IDLOG MÁXIMO 
		public Integer findMaxIDLOG() {
			
			Integer  idLog0 = 0;
			Integer maxIDLOG = 0;
			
			//Buscamos el idLog máximo 
	         maxIDLOG = tblAgendaLogVisitasRepo.findMaxIDLOG();
	        if (maxIDLOG == null) { // Validamos si maxIDLOG es null
	        	
	            System.out.println("El maxIDLOG en el service es : " + maxIDLOG);
	            return idLog0;
	        } else {
	            return maxIDLOG;
	        }
	    }
		
		
		
		public boolean ingresarLog(int idLocal, int IDLOG, String idCliente, int IDUSUARIO) {
			
			System.out.println("Ingresó a ingresarLog con el idLog (65): " + IDLOG + " usuario "+ IDUSUARIO);
			
			Integer ESTADO = 9;
			
			Timestamp fechaVista = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha y hora actuales
			
			TblAgendaLogVisitas log = new TblAgendaLogVisitas(); // Creamos una instancia de  TblAgendaLogVisitas
			
			log.setIdLocal(idLocal);
			log.setIDLOG(IDLOG);
			log.setIdCliente(idCliente);
			log.setIDUSUARIO(IDUSUARIO);
			log.setESTADO(ESTADO);
			log.setFECHAVISITA(fechaVista);
			
			
			  try {
			        // Guardamos el objeto reporte en la tabla TblAgendaLogVisitas
			        tblAgendaLogVisitasRepo.save(log);
			        System.out.println("Salio a ingresarLog con el idLog (84): " + IDLOG + " usuario "+ IDUSUARIO);
			        return true;
			    } catch (Exception e) {
			        e.printStackTrace();
			        
			        return false;
			    }
		}
		
		public boolean ingresarLogSessionID(int idLocal, int IDLOG, String idCliente, int IDUSUARIO, String ipTx, String sessionId) {
			
			System.out.println("Ingresó a ingresarLog con el idLog (65): " + IDLOG + " usuario "+ IDUSUARIO);
			
			Integer ESTADO = 1;
			Integer idEstadoTX = 9;
			
			Timestamp fechaVista = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha y hora actuales
			
			TblAgendaLogVisitas log = new TblAgendaLogVisitas(); // Creamos una instancia de  TblAgendaLogVisitas
			
			log.setIdLocal(idLocal);
			log.setIDLOG(IDLOG);
			log.setIdCliente(idCliente);
			log.setIDUSUARIO(IDUSUARIO);
			log.setESTADO(ESTADO);
			log.setFECHAVISITA(fechaVista);
			log.setIpTx(ipTx);
			log.setIdEstadoTx(idEstadoTX);
			log.setSessionId(sessionId);
			
			
			  try {
			        // Guardamos el objeto reporte en la tabla TblAgendaLogVisitas
			        tblAgendaLogVisitasRepo.save(log);
			        System.out.println("Salio a ingresarLog con el idLog (84): " + IDLOG + " usuario "+ IDUSUARIO);
			        return true;
			    } catch (Exception e) {
			        e.printStackTrace();
			        
			        return false;
			    }
		}
		
		
		public Integer ObtenerIdCliente(int idLocal, int IDUSUARIO) {
			
			Integer xIdCliente = tblAgendaLogVisitasRepo.ObtenerIdCliente(idLocal, IDUSUARIO);
			
			return xIdCliente;
		}
		
		
	public Integer ObtenerEstado(int idLocal, int IDUSUARIO, int IDLOG, int idCliente) {
			
			Integer xEstado = tblAgendaLogVisitasRepo.ObtenerEstado(idLocal, IDUSUARIO, IDLOG, idCliente);
			
			return xEstado;
		}
	
	
	public Integer ObtenerEstadoDeIdLog(int idLocal, int IDLOG, String idCliente) {
		
		Integer Estado = tblAgendaLogVisitasRepo.ObtenerEstadoDeIdLog(idLocal, IDLOG, idCliente);
		
		return Estado;
	}
		
	
	public Integer ObtenerEstadoLog(int idLocal, int IDUSUARIO, int IDLOG) {
		
		Integer EstadloLog1 = 1;
		Integer EstadoLog = 0;
		
		
		EstadoLog = tblAgendaLogVisitasRepo.ObtenerEstadoLog(idLocal, IDUSUARIO, IDLOG);
		
		if(EstadoLog == null) {
			
			return EstadloLog1;
		}else {
			
			return EstadoLog;
		}
		
		
	}
	
	public Integer ObtenerEstadoLogIdEstadoTx(int idLocal, int IDUSUARIO) {
		
		Integer idEstadoTX = tblAgendaLogVisitasRepo.ObtenerEstadoLogIdEstadoTx(idLocal, IDUSUARIO);
		
		return idEstadoTX;
	}
	
	public List<String> ObtenerListaSessionId(String fechaVisita){
		
		List <String> ListaSessionId = tblAgendaLogVisitasRepo.ObtenerListaSessionId(fechaVisita);
		
		System.out.println("La lista de las SessionId es: " + ListaSessionId);
		
		return ListaSessionId;
	}
	
	public Integer ObtenerIdLocalPorSession(String sessionId) {
		
		Integer IdLocal = tblAgendaLogVisitasRepo.ObtenerIdLocalPorSession(sessionId);
		
		return IdLocal;
	}
	
	
	public boolean ingresaLogVisita(int idLocal, int IDLOG, String idCliente, int IDUSUARIO, int idPeriodo, int idEstadoVisita, int  estadoAtendido, int idTipoOrden) {
		
		System.out.println("Ingresó a ingresarLog con el idLog (65): " + IDLOG + " usuario "+ IDUSUARIO);
		
		Integer ESTADO = 1;
		Integer idEstadoTX = 9;
		
		Timestamp fechaVista = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha y hora actuales
		
		TblAgendaLogVisitas log = new TblAgendaLogVisitas(); // Creamos una instancia de  TblAgendaLogVisitas
		
		log.setIdLocal(idLocal);
		log.setIDLOG(IDLOG);
		log.setIdCliente(idCliente);
		log.setIDUSUARIO(IDUSUARIO);
		log.setIDPERIODO(idPeriodo);
		log.setFECHAVISITA(fechaVista);		
		log.setIDESTADOVISITA(idEstadoVisita);
		log.setESTADO(estadoAtendido);
		log.setIdTipoOrden(idTipoOrden);
		
		
		  try {
		        // Guardamos el objeto reporte en la tabla TblAgendaLogVisitas
		        tblAgendaLogVisitasRepo.save(log);
		        System.out.println("Salio a ingresarLog con el idLog (84): " + IDLOG + " usuario "+ IDUSUARIO);
		        return true;
		    } catch (Exception e) {
		        e.printStackTrace();
		        
		        return false;
		    }
	}
	
	
	public boolean ingresaLogVisita(int IDLOG, String idCliente, int IDUSUARIO, int idLocalTercero, int idLocal, int idPeriodo, int idEstadoVisita, int estado, int idTipoOrden) {
		
		System.out.println("Ingresó a ingresaLogVisita con el idLog (65): " + IDLOG + " usuario "+ IDUSUARIO);
		
		Integer ESTADO = 9;
		
		Timestamp fechaVista = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha y hora actuales
		
		TblAgendaLogVisitas log = new TblAgendaLogVisitas(); // Creamos una instancia de  TblAgendaLogVisitas
		
		log.setIdLocal(idLocal);
		log.setIDLOG(IDLOG);
		log.setIdCliente(idCliente);
		log.setIDUSUARIO(IDUSUARIO);
		log.setIdLocalTercero(idLocalTercero);
		log.setIDPERIODO(idPeriodo);
		log.setIDESTADOVISITA(idEstadoVisita);
		log.setESTADO(estado);
		log.setFECHAVISITA(fechaVista);
		log.setFechaTxInicio(fechaVista);
	
		
		
		  try {
		        // Guardamos el objeto reporte en la tabla TblAgendaLogVisitas
		        tblAgendaLogVisitasRepo.save(log);
		        return true;
		    } catch (Exception e) {
		        e.printStackTrace();
		        
		        return false;
		    }
	}
	
	
	public String seleccionaVisitaEstadoFecha(int estado, String fecha, int idusuario) {
		
		String idCliente = tblAgendaLogVisitasRepo.seleccionaVisitaEstadoFecha(estado, fecha, idusuario);
		
		return idCliente;
		
	}
	

}
















