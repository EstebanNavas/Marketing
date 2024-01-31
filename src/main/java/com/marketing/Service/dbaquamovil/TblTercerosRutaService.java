package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Repository.dbaquamovil.TblTercerosRutaRepo;

@Service
public class TblTercerosRutaService {
	
	@Autowired
	TblTercerosRutaRepo tblTercerosRutaRepo;
	
	public List<TblTercerosRuta> ListaRutas(int idLocal){
		List<TblTercerosRuta> ListaRutas = tblTercerosRutaRepo.ListaRutas(idLocal);
		
		return ListaRutas;
		
	}

}
