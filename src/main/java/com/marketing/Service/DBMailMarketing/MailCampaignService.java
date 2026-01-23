package com.marketing.Service.DBMailMarketing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.HashMap;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marketing.Repository.DBMailMarketing.MailCampaignRepo;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.CampaignTask;
import com.marketing.CampaignWpTask;
import com.marketing.Model.DBMailMarketing.MailCampaign;
import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import com.marketing.Model.Reportes.ReporteDTO;
import com.marketing.Projection.TblMailMarketingReporteDTO;

	@Service
	public class MailCampaignService {
		
		@Autowired
		MailCampaignRepo mailCampaignRepo;
		
		@Autowired
		TblTercerosService tblTercerosService;
		
		@Autowired
		CampaignWpTask campaignWpTask;
		
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
		
		public boolean ingresarCampaignBatch(Integer idLocal, String sistema, Integer xIdCampaignMAX, String nombreCampaign,
	            String periodicidad, Integer idPlantilla, String fecha, String textoMensaje, String textoSMS,
	            String subject) {
	
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	
	        try {
	        	Date fechaDate = format.parse(fecha);
	            java.sql.Timestamp timestamp = new java.sql.Timestamp(fechaDate.getTime());
	
	            
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
	            return true; 
	            
	        } catch (ParseException e) {
	            e.printStackTrace();
	            return false; 
	        }
	    }
		
		public boolean ingresarCampaignOnline(Integer idLocal, String sistema, Integer xIdCampaignMAX,
			    String nombreCampaign, String periodicidad, Integer idPlantilla, String textoMensaje, String textoSMS,
			    String subject) {
			    try {
			        System.out.println("maxIdCampaign es: " + xIdCampaignMAX);
			        System.out.println("idLocal es: " + idLocal);

			        MailCampaign mailCampaign = new MailCampaign();
			        mailCampaign.setIdLocal(idLocal);
			        mailCampaign.setSistema(sistema);
			        mailCampaign.setIdCampaign(xIdCampaignMAX); // Este debería ir aquí si se está pasando como parámetro
			        mailCampaign.setNombreCampaign(nombreCampaign);
			        mailCampaign.setPeriodicidad(periodicidad);
			        mailCampaign.setIdPlantilla(idPlantilla);
			        mailCampaign.setTextoMensaje(textoMensaje);
			        mailCampaign.setTextoSMS(textoSMS);
			        mailCampaign.setSubject(subject);

			        mailCampaignRepo.save(mailCampaign); // Guarda la nueva campaña en la base de datos

			        System.out.println("Campaña ingresada con éxito");
			        return true;
			    } catch (HibernateException e) {
			        // Captura y maneja la excepción de Hibernate aquí
			        System.err.println("Error de Hibernate al ingresar la campaña: " + e.getMessage());
			        // Puedes agregar un registro de auditoría o realizar otras acciones específicas aquí
			        return false;
			    } catch (Exception e) {
			        // Manejo de otras excepciones generales
			        System.err.println("Error al ingresar la campaña: " + e.getMessage());
			        return false;
			    }
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
	
	public List<ReporteDTO> reporteSmsAgrupadoIdCampaign(int idLocal) {

	    List<TblMailMarketingReporteDTO> reporteSms = mailCampaignRepo.obtenerReporteSmsAgrupadoIdCampaign(idLocal);

	    // Obtenemos los nombres de los terceros
	    List<ReporteDTO> nombreTerceros = tblTercerosService.obtenerNombreTerceros(idLocal);

	    // Creamos una nueva lista de ReporteDTO  
	    List<ReporteDTO> reporteDTOs = new ArrayList<>();

	    // Crear un mapa para asociar idCliente con nombre de tercero
	    Map<String, String> idClienteToNombreTerceroMap = new HashMap<>();

	    // Recorremos la lista nombreTerceros y crea un mapeo entre idCliente y nombreTercero.
	    for (ReporteDTO nombreTercero : nombreTerceros) {
	        idClienteToNombreTerceroMap.put(nombreTercero.getIdCliente(), nombreTercero.getNombreTercero());
	        System.out.println("nombreTercero.getIdCliente() EN EL FOR: " + nombreTercero.getIdCliente());
	    }

	 // Iteramos a través de la lista  reporteSms.
	    for (TblMailMarketingReporteDTO reporte : reporteSms) {
	        ReporteDTO reporteDTO = new ReporteDTO();

	        reporteDTO.setIdCliente(reporte.getIdCliente());
	        reporteDTO.setIdCampaign(reporte.getIdCampaign());
	        reporteDTO.setDescripcion(reporte.getDescripcion());
	        reporteDTO.setFechaHoraEvento(reporte.getFechaHoraEvento());
	        reporteDTO.setTelefonoCelular(reporte.getCelular());
	        

	        // Seteamos el NombreTercero a reporteDTO usando el Map para buscar el que coincida con el idCliente
	        String nombreTercero = idClienteToNombreTerceroMap.get(reporte.getIdCliente());
	        reporteDTO.setNombreTercero(nombreTercero);

	        reporteDTOs.add(reporteDTO);

	    }

	    System.out.println("reporteSmsAgrupadoIdCampaign reporteSms : " + reporteSms);
	    return reporteDTOs;
	}
	
	
	public String obtenerMensajePorCampana(Integer idLocal, Integer idCampaign) {
        return mailCampaignRepo.findMensajeByIdLocalAndIdCampaign(idLocal, idCampaign);
    }
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
