package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblDctos;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;

@Service
public class TblDctosService {

	@Autowired
	TblDctosRepo tblDctosRepo;
	
	public Integer findMaxIdDcto(int IDLOCAL) {
		
		Integer maxIdDto = tblDctosRepo.findMaxIdDcto(IDLOCAL);
		System.out.println("El maxIdDto del " + IDLOCAL +  " es " + maxIdDto);
		
		return maxIdDto;
	}
	
	
	public boolean ingresarDto(int IDLOCAL, int IDORDEN, int idDcto, String idCliente, int IDUSUARIO ) {
		
		Integer IDTIPOORDEN = 17;
		Integer indicador = 1;
		
		Timestamp fechaDcto = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha y hora actuales
		
		TblDctos Dcto = new TblDctos(); // Creamos una instancia de  TblAgendaLogVisitas
		
		Dcto.setIDLOCAL(IDLOCAL);
		Dcto.setIDTIPOORDEN(IDTIPOORDEN);
		Dcto.setIDORDEN(IDORDEN);
		Dcto.setIdDcto(idDcto);
		Dcto.setIndicador(indicador);
		Dcto.setIdCliente(idCliente);
		Dcto.setFechaDcto(fechaDcto);
		Dcto.setIDUSUARIO(IDUSUARIO);
		
		
		// Guardamos el objeto reporte en la tabla TblAgendaLogVisitas
		tblDctosRepo.save(Dcto);
		
		return true;
	}
}
