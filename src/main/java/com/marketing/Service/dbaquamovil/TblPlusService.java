package com.marketing.Service.dbaquamovil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Repository.dbaquamovil.TblPlusRepo;

@Service
public class TblPlusService {

	@Autowired
	TblPlusRepo tblPlusRepo;
	
	public ArrayList<TblPlus> ObtenerCategorias(int idLocal, int idCategoria){
		
		 ArrayList<TblPlus> categorias = tblPlusRepo.ObtenerCategorias(idLocal, idCategoria);
		 
		 
		 return categorias;
	}
	
	
    public Map<String, String> ObtenerNombrePluAndIdPlu(int idLocal){
		
			// Obtenemos la lista de los nombres de los idPlu
			List <TblPlus> nombresPlu = tblPlusRepo.ObtenerNombrePluAndIdPlu(idLocal);
			
			Map<String, String> nombrePluIdPluMap = new HashMap<>();
			
			for(TblPlus registro : nombresPlu) {// Recorremos la lista
				String idPlu = registro.getIdPlu().toString();
		        String nombrePlu = registro.getNombrePlu();
		        
		     // Llenamos el map con los valores de idPlu y nombrePlu
		        nombrePluIdPluMap.put(idPlu, nombrePlu);
	
		     
				
			}
			
			return nombrePluIdPluMap;
	}
}
