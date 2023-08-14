package com.marketing.Service.DBMailMarketing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.DBMailMarketing.TblMailMarketingReporteRepo;
import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import org.springframework.data.domain.Sort;

@Service
public class TblMailMarketingReporteService {
	
	@Autowired
    private TblMailMarketingReporteRepo reporteRepo;

    // Método para obtener todos los registros de las columnas específicas para un idLocal
    public List<TblMailMarketingReporte> obtenerRegistrosPorIdLocal(int idLocal) {
    	
        return reporteRepo.findByIdLocal(idLocal);
    }
    

}
