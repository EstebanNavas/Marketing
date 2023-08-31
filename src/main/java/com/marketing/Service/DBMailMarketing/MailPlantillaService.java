package com.marketing.Service.DBMailMarketing;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.DBMailMarketing.MailPlantilla;
import com.marketing.Repository.DBMailMarketing.MailPlantillaRepo;

@Service
public class MailPlantillaService {
	
	@Autowired
	MailPlantillaRepo mailPlantillaRepo;

	public ArrayList<MailPlantilla> consultarTodasLasPlantillas() {
	    return (ArrayList<MailPlantilla>) mailPlantillaRepo.findAll();
	}
	
	public Integer maximaPlantilla() {
		return mailPlantillaRepo.findMaxIdPlantilla();
	}
	
	public String plantillaEnvioSMS() {
		
		String plantilla = mailPlantillaRepo.findPlantillaEnvioSMS();
		System.out.println("La plantilla 11 es: " + plantilla);
		return plantilla;
	}
	
	public void ingresarPlantilla(Integer xIdRequerimiento, Integer xIdPlantilla, String xNombrePlantilla) {
		
		try {
			//Obtenemos el id maximo de plantilla
			Integer maxIdPlantilla = mailPlantillaRepo.findMaxIdPlantilla();
			
			// Sumar 1 al valor máximo para obtener el nuevo idplantilla
	           Integer newIdPlantilla = (maxIdPlantilla != null) ? maxIdPlantilla + 1 : 1;
	           
	           MailPlantilla plantilla = new MailPlantilla();
	           
	          plantilla.setIdRequerimiento(xIdRequerimiento);
	          plantilla.setIdPlantilla(newIdPlantilla);
	          plantilla.setNombrePlantilla(xNombrePlantilla);
	          
	          mailPlantillaRepo.save(plantilla);
	          System.out.println("Plantilla ingresada con éxito");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
