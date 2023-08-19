package com.marketing.Service.dbaquamovil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;

@Service
public class TblTercerosService {

	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	// Obtenemos todos los registros de la tabla tblTerceros
	public List<TblTerceros> obtenerTercerosCelular(int idLocal){
		List <TblTerceros> regTercero = tblTercerosRepo.findByIdLocal(idLocal);
        return regTercero;
		
	}
	
}
