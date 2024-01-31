package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Repository.dbaquamovil.TblMedidoresRepo;

@Service
public class TblMedidoresService {
	
	@Autowired
	TblMedidoresRepo tblMedidoresRepo;
	
	public List<TblMedidores> ListaMedidores(int idLocal){
		
		List<TblMedidores> lista = tblMedidoresRepo.ListaMedidores(idLocal);
		
		return lista;
	}

}
