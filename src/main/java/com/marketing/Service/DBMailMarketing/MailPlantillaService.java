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
	

}
