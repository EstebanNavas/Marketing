package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Repository.dbaquamovil.TblTerceroEstractoRepo;

@Service
public class TblTerceroEstractoService {

	@Autowired
	TblTerceroEstractoRepo tblTerceroEstractoRepo;
	
	public List <TblTerceroEstracto> obtenerEstracto (int idLocal){
		
		List <TblTerceroEstracto> estracto = tblTerceroEstractoRepo.findByIdLocal(idLocal);
		
		return estracto;
	}
	
	
	public List<TblTerceroEstracto> ListaEstratos(int idLocal){
		
		List<TblTerceroEstracto> ListaEstratos = tblTerceroEstractoRepo.ListaEstratos(idLocal);
		
		return ListaEstratos;
	}
}
