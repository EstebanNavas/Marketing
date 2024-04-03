package com.marketing.Utilidades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblLocalesService;

@Component
public class ProcesoConsumoM3 {
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;

	@Autowired
	TblLocalesService tblLocalesService;

	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	
	 public void consumoQryM3(int xIdLocal) throws ClassNotFoundException {

		 	
		 tblDctosOrdenesDetalleRepo.ingresaSuntuario(xIdLocal);
		 System.out.println("QUERY 24");
		 tblDctosOrdenesDetalleRepo.actualizaSuntuario(xIdLocal);
		 System.out.println("QUERY 25");
		 tblDctosOrdenesDetalleRepo.ingresaComplementario(xIdLocal);
		 System.out.println("QUERY 26");
		 tblDctosOrdenesDetalleRepo.actualizaComplementario(xIdLocal);
		 System.out.println("QUERY 27");
		 

	    }
	 
	 
	 public void consumoQryM3Alcantarillado(int xIdLocal) throws ClassNotFoundException {

		 tblDctosOrdenesDetalleRepo.ingresaSuntuarioAlcantarillado(xIdLocal);
		 System.out.println("QUERY 36");
		 tblDctosOrdenesDetalleRepo.actualizaSuntuarioAlcantarillado(xIdLocal);
		 System.out.println("QUERY 37");
		 tblDctosOrdenesDetalleRepo.ingresaComplementarioAlcantarillado(xIdLocal);
		 System.out.println("QUERY 38");
		 tblDctosOrdenesDetalleRepo.actualizaComplementarioAlcantarillado(xIdLocal);
		 System.out.println("QUERY 39");

	    }

	 
}



























