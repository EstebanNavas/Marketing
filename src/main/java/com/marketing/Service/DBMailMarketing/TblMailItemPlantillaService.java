package com.marketing.Service.DBMailMarketing;

import java.text.ParseException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.DBMailMarketing.TblMailItemPlantillaRepo;
import com.marketing.Model.DBMailMarketing.TblMailItemPlantilla;

@Service
public class TblMailItemPlantillaService {
	
	@Autowired
    private TblMailItemPlantillaRepo mailItemPlantillaRepo;
	
	   public Integer obtenerMaximoIdRequerimiento() {
	        return mailItemPlantillaRepo.findMaxIdRequerimiento();
	    }
	   
	   // insertar plantilla
	   public void ingresarItemPlantilla(Integer xMaxIdItem, String xFormato, String xComentario) {
		   try {
		   //obtenemos el Max idRequerimiento
		   Integer maxIdRequerimiento = mailItemPlantillaRepo.findMaxIdRequerimiento();
		   
		// Sumar 1 al valor máximo para obtener el nuevo idRequerimiento
           Integer newIdRequerimiento = (maxIdRequerimiento != null) ? maxIdRequerimiento + 1 : 1;
           
           TblMailItemPlantilla itemPlantilla= new TblMailItemPlantilla();
           
           itemPlantilla.setIdRequerimiento(newIdRequerimiento);
           itemPlantilla.setFormato(xFormato);
           itemPlantilla.setComentario(xComentario);
           
           mailItemPlantillaRepo.save(itemPlantilla);
           
           System.out.println("itemPlantilla ingresada con éxito");
		   } catch (Exception e) {
		        e.printStackTrace();
		        
		    }
	   }
	   
	   // obtener todas las plantillas
	   public ArrayList <TblMailItemPlantilla> obtenerTodasLasPlantillas() {
	        return mailItemPlantillaRepo.findAll();
	    }
}
