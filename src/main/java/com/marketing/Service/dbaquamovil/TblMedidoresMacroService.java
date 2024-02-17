package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Repository.dbaquamovil.TblMedidoresMacroRepo;

@Service
public class TblMedidoresMacroService {
	
	@Autowired 
	TblMedidoresMacroRepo tblMedidoresMacroRepo;

	
	public List<TblMedidoresMacro> ListaMedidoresMacro(int idLocal){
		
		List<TblMedidoresMacro> Lista = tblMedidoresMacroRepo.ListaMedidoresMacro(idLocal);
		
		return Lista;
		
	}
	
	public Integer maximoIdMacroMedidor(int idLocal) {
		
		Integer idMacro = tblMedidoresMacroRepo.maximoIdMacroMedidor(idLocal);
		
		return idMacro;
	}
	
	
	 public boolean ingresarMacroMedidor(int idLocal, int MaximoIdMacroMedidor,  String descripcion, int diametro) {
			
			Integer ESTADO = 0;
			Integer CeroInt = 0;
			Integer UnoInt = 1;
			String punto = ".";
			float ceroFloat = 0;

			
			

			// Creamos una instancia de  TblAgendaLogVisitas
			TblMedidoresMacro orden = new TblMedidoresMacro();
			
	    	orden.setIdLocal(idLocal);
	    	orden.setIdMacro(MaximoIdMacroMedidor);
	    	orden.setNombreMacro(descripcion);
	    	orden.setDiametro(diametro);
	    	orden.setEstado(UnoInt);
	    		
	    	

			// Guardamos el objeto orden en la tabla 
	    	tblMedidoresMacroRepo.save(orden);
	    	
	    	System.out.println("MACRO MEDIDOR INGRESADO CORRECTAMENTE");
			
			return true;
		}
	 
	 
	 public List<TblMedidoresMacro> ObtenerMacroMedidor(int idLocal, int idMacro){
		 
		 List<TblMedidoresMacro> Macro = tblMedidoresMacroRepo.ObtenerMacroMedidor(idLocal, idMacro);
		 
		 return Macro;
	 }
	
}
