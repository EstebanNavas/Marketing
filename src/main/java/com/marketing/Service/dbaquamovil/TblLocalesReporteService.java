package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Repository.dbaquamovil.TblLocalesReporteRepo;

@Service
public class TblLocalesReporteService {
	
	@Autowired
	TblLocalesReporteRepo tblLocalesReporteRepo;

	public String obtenerNombreReporte (int idLocal, int idReporte) {
		
		String nombreReporte = tblLocalesReporteRepo.nombreReporte(idLocal, idReporte);
        return nombreReporte;
	}
	
	
	public List<TblLocalesReporte> listaUnFCH(int idLocal, int idReporte){
		
		List<TblLocalesReporte> reporte = tblLocalesReporteRepo.listaUnFCH(idLocal, idReporte);
		
		return reporte;
		
	}
}
