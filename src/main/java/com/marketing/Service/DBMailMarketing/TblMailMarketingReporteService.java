package com.marketing.Service.DBMailMarketing;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.DBMailMarketing.TblMailMarketingReporteRepo;
import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import com.marketing.Model.Reportes.ReporteDTO;
import com.marketing.Projection.PruebaDTO;
import com.marketing.Projection.TblMailMarketingReporteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@Service
public class TblMailMarketingReporteService {
	
	@Autowired
    private TblMailMarketingReporteRepo reporteRepo;

    // Método para obtener todos los registros de las columnas específicas para un idLocal
    public List<ReporteDTO> obtenerRegistrosPorIdLocal(int idLocal) {
    	
    	System.out.println("Entró al service obtenerRegistrosPorIdLocal ");
  
        List<TblMailMarketingReporteDTO> reporteSMS = reporteRepo.findByIdLocal(idLocal);
        
        
        List<ReporteDTO> reporteDTOs = new ArrayList<>();
        System.out.println("reporteSMS: " + reporteSMS.toString());
        for (TblMailMarketingReporteDTO reporte : reporteSMS) {
     
            ReporteDTO reporteDTO = new ReporteDTO();
            reporteDTO.setIdReporte(reporte.getIdReporte());
            reporteDTO.setIdCampaign(reporte.getIdCampaign());
            reporteDTO.setIdPlantilla(reporte.getIdPlantilla());
            reporteDTO.setDescripcion(reporte.getDescripcion());
            reporteDTO.setFechaHoraEvento(reporte.getFechaHoraEvento());
            reporteDTO.setCelular(reporte.getCelular());
           // System.out.println(reporte.getTblMailCampaignCliente().getIdCliente());
            
            reporteDTOs.add(reporteDTO);
        }
        return reporteDTOs;
    }
    
    
    public Page<TblMailMarketingReporte> obtenerRegistrosPorIdLocalPaginados(int idLocal, int pagina, int tamañoPagina) {
    	Pageable pageable = PageRequest.of(pagina - 1, tamañoPagina);
        return reporteRepo.findByidLocal(idLocal, pageable);
    }
	

}
