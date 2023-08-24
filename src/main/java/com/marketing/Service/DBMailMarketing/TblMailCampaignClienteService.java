package com.marketing.Service.DBMailMarketing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.DBMailMarketing.MailCampaignRepo;
import com.marketing.Repository.DBMailMarketing.TblMailCampaignClienteRepo;
import com.marketing.Model.DBMailMarketing.MailCampaign;
import com.marketing.Model.DBMailMarketing.TblMailCampaignCliente;

@Service
public class TblMailCampaignClienteService {

	@Autowired
	TblMailCampaignClienteRepo tblMailCampaignClienteRepo;
	
	@Autowired
	MailCampaignRepo mailCampaignRepo;
	
	
	public void ingresarCampaignCliente (Integer xIdCampaignMAX, Integer idLocal, String idCliente, String sistema) {
		
		// Obtener el valor máximo actual del idCampaign
        Integer maxIdCampaign = mailCampaignRepo.maximaCampaign(idLocal, sistema);
        
   
        System.out.println("maxIdCampaign  es : " + maxIdCampaign);
        

        
        TblMailCampaignCliente mailCampaignCliente = new TblMailCampaignCliente();
        
        mailCampaignCliente.setIdCampaign(xIdCampaignMAX);
        mailCampaignCliente.setIdlocal(idLocal);
        mailCampaignCliente.setIdCliente(idCliente);
        
        tblMailCampaignClienteRepo.save(mailCampaignCliente);
	}
}
