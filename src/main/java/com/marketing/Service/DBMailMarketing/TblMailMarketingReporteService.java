package com.marketing.Service.DBMailMarketing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.DBMailMarketing.TblMailMarketingReporteRepo;
import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

@Service
public class TblMailMarketingReporteService {
	
	@Autowired
    private TblMailMarketingReporteRepo reporteRepo;

    // Método para obtener todos los registros de las columnas específicas para un idLocal
    public List<TblMailMarketingReporte> obtenerRegistrosPorIdLocal(int idLocal) {
    	
        return reporteRepo.findByIdLocal(idLocal);
    }
    
    
    public Page<TblMailMarketingReporte> obtenerRegistrosPorIdLocalPaginados(int idLocal, int pagina, int tamañoPagina) {
    	Pageable pageable = PageRequest.of(pagina - 1, tamañoPagina);
        return reporteRepo.findByidLocal(idLocal, pageable);
    }
	
//    public Page<TblMailMarketingReporte> obtenerRegistrosPorIdLocalPaginados(int idLocal, int pagina, int tamañoPagina) {
//        Pageable pageable = PageRequest.of(pagina - 1, tamañoPagina);
//        return reporteRepo.findByIdLocalOrderByidReporteDesc(idLocal, pageable);
//    }
}
