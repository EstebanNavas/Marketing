package com.marketing.Service.DBMailMarketing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.DBMailMarketing.MailCampaignRepo;
import com.marketing.CampaignTask;
import com.marketing.Model.DBMailMarketing.MailCampaign;

	@Service
	public class MailCampaignService {
		
		@Autowired
		MailCampaignRepo mailCampaignRepo;
		
		
		
		@Autowired
		CampaignTask campaignTask;
		
		private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		
		   public void iniciarEjecucionProgramada(Integer idLocal, String sistema, Integer idCampaign, Date fechaEjecucion) {
			   //se calcula el retraso inicial en milisegundos 
			   
			   // Se obtiene el idCampaign Maximo despues de ingresar una nueva campaña 
			   Integer xIdCampaign = maximaCampaign(idLocal, sistema);
		        long initialDelay = fechaEjecucion.getTime() - System.currentTimeMillis();
		        
		        //Se verifica si el retraso inicial es positivo
		        if (initialDelay > 0) {
		        	
		        	//Se utiliza el método schedule para programar la tarea de ejecutar la campaña BATCH en el futuro
		            executorService.schedule(() -> ejecutarCampaignBatch(idLocal, sistema, xIdCampaign), initialDelay, TimeUnit.MILLISECONDS);
		        }
		    }
		
		   public void ejecutarCampaignBatch(Integer idLocal, String sistema, Integer idCampaign) {
		        // Se llama a campaignTask para ejecutar el .jar
			   campaignTask.ejecutarJar(idLocal, idCampaign);
		    }
		
	
		public Integer maximaCampaign( Integer idLocal, String sistema) {
			
			return mailCampaignRepo.maximaCampaign(idLocal, sistema);
		}
		
		public void ingresarCampaignBatch(Integer idLocal, String sistema, Integer xIdCampaignMAX, String nombreCampaign,
	            String periodicidad, Integer idPlantilla, String fecha, String textoMensaje, String textoSMS,
	            String subject) {
	
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	
	        try {
	        	Date fechaDate = format.parse(fecha);
	            java.sql.Timestamp timestamp = new java.sql.Timestamp(fechaDate.getTime());
	            
	         // Obtener el valor máximo actual del idCampaign
	            Integer maxIdCampaign = mailCampaignRepo.maximaCampaign(idLocal, sistema);
//	            
//	            System.out.println("maxIdCampaign  es : " + maxIdCampaign);
//	            
//	         // Sumar 1 al valor máximo para obtener el nuevo idCampaign
//	            Integer newIdCampaign = (maxIdCampaign != null) ? maxIdCampaign + 1 : 1;
	
	            
	            MailCampaign mailCampaign = new MailCampaign();
	            mailCampaign.setIdLocal(idLocal);
	            mailCampaign.setSistema(sistema);
	            mailCampaign.setIdCampaign(xIdCampaignMAX); // Este debería ir aquí si se está pasando como parámetro
	            mailCampaign.setNombreCampaign(nombreCampaign);
	            mailCampaign.setPeriodicidad(periodicidad);
	            mailCampaign.setIdPlantilla(idPlantilla);
	            mailCampaign.setFechaYhora(timestamp);
	            mailCampaign.setTextoMensaje(textoMensaje);
	            mailCampaign.setTextoSMS(textoSMS);
	            mailCampaign.setSubject(subject);
	            
	            mailCampaignRepo.save(mailCampaign); // Guarda la nueva campaña en la base de datos
	            
	            System.out.println("Campaña ingresada con éxito");
	            
	        } catch (ParseException e) {
	            e.printStackTrace();
	          
	        }
	    }
		
		public boolean ingresarCampaignOnline(Integer idLocal, String sistema, Integer xIdCampaignMAX, String nombreCampaign,
	            String periodicidad, Integer idPlantilla, String textoMensaje, String textoSMS,
	            String subject) {
	

			
			// Obtener el valor máximo actual del idCampaign
	        Integer maxIdCampaign = mailCampaignRepo.maximaCampaign(idLocal, sistema);
	        
	        System.out.println("maxIdCampaign  es : " + maxIdCampaign);
	        
	        // Sumar 1 al valor máximo para obtener el nuevo idCampaign
	      // Integer newIdCampaign = (maxIdCampaign != null) ? maxIdCampaign + 1 : 1;
	        
	
	        
	            MailCampaign mailCampaign = new MailCampaign();
	
	            mailCampaign.setIdLocal(idLocal);
	            mailCampaign.setSistema(sistema);
	            mailCampaign.setIdCampaign(xIdCampaignMAX); // Este debería ir aquí si se está pasando como parámetro 231
	            mailCampaign.setNombreCampaign(nombreCampaign);
	            mailCampaign.setPeriodicidad(periodicidad);
	            mailCampaign.setIdPlantilla(idPlantilla);
	            mailCampaign.setTextoMensaje(textoMensaje);
	            mailCampaign.setTextoSMS(textoSMS);
	            mailCampaign.setSubject(subject);
	            
	            mailCampaignRepo.save(mailCampaign); // Guarda la nueva campaña en la base de datos
	            
	            System.out.println("Campaña ingresada con éxito");
	            
	            return true;
	            
	    }
		
	
	public ArrayList<MailCampaign> datosCampaignByIdLocalAndSistemaAndPeriodicidad(Integer idLocal,
			String sistema, String periodicidad){
		
		return mailCampaignRepo.findByidLocalAndSistemaAndPeriodicidad(idLocal, sistema, periodicidad);
	}

	
	public Integer datosCampaignByIdLocalAndSistemaAndIdCampaign(Integer idLocal, String sistema, Integer idCampaign) {
		
		MailCampaign campaign = mailCampaignRepo.findByIdLocalAndSistemaAndIdCampaign(idLocal, sistema, idCampaign);
		if (campaign != null) {
	        return campaign.getIdCampaign();
	    } else {
	        return null; 
	    }
	}
}
