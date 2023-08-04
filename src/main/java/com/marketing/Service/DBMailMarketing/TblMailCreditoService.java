package com.marketing.Service.DBMailMarketing;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.DBMailMarketing.TblMailCredito;
import com.marketing.Repository.DBMailMarketing.TblMailCreditoRepo;

@Service
public class TblMailCreditoService {

	@Autowired
	TblMailCreditoRepo tblMailCreditoRepo;
	
	//CONSULTA CREDITO LOCAL
	public Integer consultaCreditoLocal(int idLocal) {
		
		//Buscamos de TblMailCredito el local
		Optional <TblMailCredito> localOptional = tblMailCreditoRepo.findByIdLocal(idLocal);
		if(localOptional.isPresent()) {//  Si se encontró un registro de TblMailCredito con el ID y idCampaign proporcionados
			
			TblMailCredito local = localOptional.get();// Obtenekos el objeto TblMailCredito
			Integer credito = local.getCredito(); // Obtenemos el credito del local
			return credito;
		} else {
            System.out.println("No se encontró ningún local con el idLocal: " + idLocal );
            return 0 ;
        }
	}
	
	//CONSULTA CREDITO LOCAL
public Integer consultaDebitoLocal(int idLocal) {
		
		//Buscamos de TblMailCredito el local
		Optional <TblMailCredito> localOptional = tblMailCreditoRepo.findByIdLocal(idLocal);
		if(localOptional.isPresent()) {//  Si se encontró un registro de TblMailCredito con el ID y idCampaign proporcionados
			
			TblMailCredito local = localOptional.get();// Obtenekos el objeto TblMailCredito
			Integer debito = local.getDebito(); // Obtenemos el credito del local
			
			return debito;
		} else {
            System.out.println("No se encontró ningún local con el idLocal: " + idLocal );
            return 0 ;
        }
	}
}
