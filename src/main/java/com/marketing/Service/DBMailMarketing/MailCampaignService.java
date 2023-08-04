package com.marketing.Service.DBMailMarketing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.DBMailMarketing.MailCampaignRepo;
import com.marketing.Model.DBMailMarketing.MailCampaign;

@Service
public class MailCampaignService {
	
	@Autowired
	MailCampaignRepo mailCampaignRepo;

	public Integer maximaCampaign( Integer idLocal, String sistema) {
		
		return mailCampaignRepo.maximaCampaign(idLocal, sistema);
	}
	
	public void ingresarCampaign(Integer idLocal, String sistema, Integer idCampaign, String nombreCampaign,
            String periodicidad, Integer idPlantilla, String fecha, String textoMensaje, String textoSMS,
            String subject) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
        	Date fechaDate = format.parse(fecha);
            java.sql.Timestamp timestamp = new java.sql.Timestamp(fechaDate.getTime());

            
            MailCampaign mailCampaign = new MailCampaign();
//            mailCampaign.setIdCampaign(idCampaign);
            mailCampaign.setIdLocal(idLocal);
            mailCampaign.setSistema(sistema);
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
