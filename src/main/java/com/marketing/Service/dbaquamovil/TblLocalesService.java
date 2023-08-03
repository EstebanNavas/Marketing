package com.marketing.Service.dbaquamovil;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Repository.dbaquamovil.TblLocalesRepo;

@Service
public class TblLocalesService {
	
	@Autowired
	TblLocalesRepo tblLocalesRepo;
	
	// EXTRAER EL ID DEL LOCAL Y SU RAZÓN SOCIAL 
	  public TblLocales consultarLocal(Integer idLocal) {
		  
		   // Buscammos el Objeto TblLocales por su id
	        Optional<TblLocales> localOptional = tblLocalesRepo.findByIdLocal(idLocal);
	        
	        if (localOptional.isPresent()) {
	            TblLocales locales = localOptional.get();
	            return locales;
	        } else {
	            System.out.println("No se encontró ningún local con el idLocal: " + idLocal);
	            return null;
	        }

	  }
}
