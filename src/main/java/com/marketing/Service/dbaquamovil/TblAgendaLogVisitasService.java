package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;

@Service
public class TblAgendaLogVisitasService {

	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	
	//OBTENEMOS EL IDLOG MÁXIMO 
		public Integer obtenerMaximoIDLOG(int idLocal, int IDUSUARIO) {
			
			Integer  idLog0 = 0;
			Integer maxIDLOG = 0;
			
			//Buscamos el idLog máximo 
	         maxIDLOG = tblAgendaLogVisitasRepo.findMaxIDLOG(idLocal, IDUSUARIO);
	        if (maxIDLOG == null) { // Validamos si maxIDLOG es null
	        	
	            System.out.println("El maxIDLOG en el service es : " + maxIDLOG);
	            return idLog0;
	        } else {
	            return maxIDLOG;
	        }
	    }
		
		
		
		public boolean ingresarLog(int idLocal, int IDLOG, String idCliente, int IDUSUARIO) {
			
			Integer ESTADO = 9;
			
			Timestamp fechaVista = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha y hora actuales
			
			TblAgendaLogVisitas log = new TblAgendaLogVisitas(); // Creamos una instancia de  TblAgendaLogVisitas
			
			log.setIdLocal(idLocal);
			log.setIDLOG(IDLOG);
			log.setIdCliente(idCliente);
			log.setIDUSUARIO(IDUSUARIO);
			log.setESTADO(ESTADO);
			log.setFECHAVISITA(fechaVista);
			
			
			// Guardamos el objeto reporte en la tabla TblAgendaLogVisitas
			tblAgendaLogVisitasRepo.save(log);
			
			return true;
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
		
	
}
















