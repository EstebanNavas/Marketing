package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.dbaquamovil.TblDctosPeriodoRepo;

@Service
public class TblDctosPeriodoService {

	@Autowired
	TblDctosPeriodoRepo tblDctosPeriodoRepo;
	
	public Integer ObtenerIdPeriodo(int idLocal) {
		
		Integer idPeriodo = tblDctosPeriodoRepo.ObtenerIdPeriodo(idLocal);
		
		return idPeriodo;
	}
	
	public Integer ObtenerIdPeriodoNotas(int idLocal) {
		
		Integer idPeriodoNota = tblDctosPeriodoRepo.ObtenerIdPeriodoNotas(idLocal);
		
		return idPeriodoNota;
	}
	
	public List <Integer> ListaIdPeriodos(int idLocal){
		
		List <Integer> ListaIdperiodo = tblDctosPeriodoRepo.ListaIdPeriodos(idLocal);
		
		return ListaIdperiodo;
	}
}
