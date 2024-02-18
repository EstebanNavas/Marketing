package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblMediosPago;
import com.marketing.Repository.dbaquamovil.TblMediosPagoRepo;

@Service
public class TblMediosPagoService {
	
	@Autowired
	TblMediosPagoRepo tblMediosPagoRepo;
	
	public List<TblMediosPago> ListaMediosDePago(int idLocal){
		
		List<TblMediosPago> MediosDePago = tblMediosPagoRepo.ListaMediosDePago(idLocal);
		
		return MediosDePago;
	}

}
