package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
