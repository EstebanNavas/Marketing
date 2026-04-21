package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblAgendaLogSoportes;
import com.marketing.Repository.dbaquamovil.TblAgendaLogSoportesRepo;

@Service
public class TblAgendaLogSoportesService {
	
	@Autowired
	TblAgendaLogSoportesRepo tblAgendaLogSoportesRepo;
	
	
	public List<TblAgendaLogSoportes> ObtenerReportesXUsuario(int idLocal, Double idUsuario){
		
		List<TblAgendaLogSoportes> lista = tblAgendaLogSoportesRepo.ObtenerReportesXUsuario(idLocal, idUsuario);
		
		return lista;
	}

}
