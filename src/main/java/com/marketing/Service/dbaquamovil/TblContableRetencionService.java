package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblContableRetencion;
import com.marketing.Repository.dbaquamovil.TblContableRetencionRepo;

@Service
public class TblContableRetencionService {

	
	@Autowired
	TblContableRetencionRepo tblContableRetencionRepo;
	
	
	public List<TblContableRetencion> calculaRetencion(int IdConcepto, Double VrBase){
		
		List<TblContableRetencion> lista = tblContableRetencionRepo.calculaRetencion(IdConcepto, VrBase);
		
		return lista;
	}
}
