package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Repository.dbaquamovil.TblMedidoresRepo;

@Service
public class TblMedidoresService {
	
	@Autowired
	TblMedidoresRepo tblMedidoresRepo;
	
	public List<TblMedidores> ListaMedidores(int idLocal){
		
		List<TblMedidores> lista = tblMedidoresRepo.ListaMedidores(idLocal);
		
		return lista;
	}
	
	public Integer maximoIdMedidor(int idLocal) {
		
		Integer idMedidor = tblMedidoresRepo.maximoIdMedidor(idLocal);
		
		return idMedidor;
	}
	
	
	 public boolean ingresarMedidor(int idLocal, int MaximoIdMedidor,  String descripcion, int diametro) {
			
			Integer ESTADO = 0;
			Integer CeroInt = 0;
			Integer UnoInt = 1;
			String punto = ".";
			float ceroFloat = 0;

			
			

			// Creamos una instancia de  TblAgendaLogVisitas
			TblMedidores orden = new TblMedidores();
			
	    	orden.setIdLocal(idLocal);
	    	orden.setIdMedidor(MaximoIdMedidor);
	    	orden.setMarcaMedidor(descripcion);
	    	orden.setDiametro(diametro);
	    	orden.setEstado(UnoInt);
	    		
	    	

			// Guardamos el objeto orden en la tabla 
	    	tblMedidoresRepo.save(orden);
	    	
	    	System.out.println("MEDIDOR INGRESADA CORRECTAMENTE");
			
			return true;
		}
	 
	 
	 public List<TblMedidores> ObtenerMedidor(int idLocal, int idMedidor){
		 
		 List<TblMedidores> Medidor = tblMedidoresRepo.ObtenerMedidor(idLocal, idMedidor);
		 
		 return Medidor;
	 }
	 
	 
	 
	 public List<TblMedidores> listaAll(int idLocal){
		 
		 List<TblMedidores> lista = tblMedidoresRepo.listaAll(idLocal);
		 
		 return lista;
		 
	 }

}
