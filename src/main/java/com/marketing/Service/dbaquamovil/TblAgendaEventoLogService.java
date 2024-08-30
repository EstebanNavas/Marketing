package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblAgendaEventoLog;
import com.marketing.Projection.TblAgendaEventoLogDTO;
import com.marketing.Repository.dbaquamovil.TblAgendaEventoLogRepo;

@Service
public class TblAgendaEventoLogService {
	
	@Autowired
	TblAgendaEventoLogRepo tblAgendaEventoLogRepo;

	
	public List<TblAgendaEventoLogDTO>  listaReporteEmail(int idLocal, int idPeriodo){
		
		List<TblAgendaEventoLogDTO> lista = tblAgendaEventoLogRepo.listaReporteEmail(idLocal, idPeriodo);
		
		return lista;
	}
	
	
	public List<TblAgendaEventoLogDTO>  listaReporteWhatsApp(int idLocal, int idPeriodo){
		
		List<TblAgendaEventoLogDTO> listaWhatsApp = tblAgendaEventoLogRepo.listaReporteWhatsApp(idLocal, idPeriodo);
		
		return listaWhatsApp;
		
	}
}
