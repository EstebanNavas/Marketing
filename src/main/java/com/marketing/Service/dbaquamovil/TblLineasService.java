package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblLineas;
import com.marketing.Repository.dbaquamovil.TblLineasRepo;

@Service
public class TblLineasService {
	
	@Autowired
	TblLineasRepo tblLineasRepo;
	
	public List<TblLineas> listaLineasLocal(int idLocal){
		
		List<TblLineas> lista = tblLineasRepo.listaLineasLocal(idLocal);
		
		return lista;
	}

}
