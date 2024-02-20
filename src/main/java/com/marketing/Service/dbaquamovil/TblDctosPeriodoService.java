package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Repository.dbaquamovil.TblDctosPeriodoRepo;

@Service
public class TblDctosPeriodoService {

	@Autowired
	TblDctosPeriodoRepo tblDctosPeriodoRepo;
	
	public List<TblDctosPeriodo> ObtenerIdPeriodo(int idLocal) {
		
		List<TblDctosPeriodo> ListaPeriodos = tblDctosPeriodoRepo.ObtenerIdPeriodo(idLocal);
		
		return ListaPeriodos;
	}
	
	public List<TblDctosPeriodo> ObtenerIdPeriodoNotas(int idLocal) {
		
		List<TblDctosPeriodo> idPeriodoNota = tblDctosPeriodoRepo.ObtenerIdPeriodoNotas(idLocal);
		
		return idPeriodoNota;
	}
	
	public List <Integer> ListaIdPeriodos(int idLocal){
		
		List <Integer> ListaIdperiodo = tblDctosPeriodoRepo.ListaIdPeriodos(idLocal);
		
		return ListaIdperiodo;
	}
	
	public List <TblDctosPeriodo> ListaTotalPeriodos(int idLocal){
		
		List <TblDctosPeriodo> listaPeriodos = tblDctosPeriodoRepo.ListaTotalPeriodos(idLocal);
		
		return listaPeriodos;
	}
	
	
	public List <String> listaPeriodo(int idLocal, int PeriodoFactura , int idPeriodo){
		
		List <String> listaPeriodo = tblDctosPeriodoRepo.listaPeriodo(idLocal, PeriodoFactura, idPeriodo);
		
		return listaPeriodo;
	}
	
	
	
	
	
	
	
}
