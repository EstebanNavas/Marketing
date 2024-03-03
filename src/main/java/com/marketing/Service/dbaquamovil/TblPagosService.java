package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Projection.TblPagosDTO;
import com.marketing.Repository.dbaquamovil.TblPagosRepo;

@Service
public class TblPagosService {

	@Autowired
	TblPagosRepo tblPagosRepo;
	
	public List<TblPagosDTO> listaRepRecaudoPeriodo(int idLocal, int idPeriodo){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaRepRecaudoPeriodo(idLocal, idPeriodo);
		
		return lista;
	}
	
	
	public  List<TblPagosDTO> listaRepRecaudoPeriodoRuta(int idLocal, int idPeriodo, int idRuta){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaRepRecaudoPeriodoRuta(idLocal, idPeriodo, idRuta);
		
		return lista;
	}
	
	
	public List<TblPagosDTO> listaRepRecaudoPeriodoFecha(int idLocal, int idPeriodo, String fecha){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaRepRecaudoPeriodoFecha(idLocal, idPeriodo, fecha);
		
		return lista;
	}
}
