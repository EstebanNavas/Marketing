package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTerceros;
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
	
	
	
	public boolean ingresarDctoPeriodo(int idLocal, int xIdPeriodo, String nombre, Timestamp fechaInicial, Timestamp fechaFinal, Timestamp fechaSinRecargo, Timestamp fechaConRecargo ) {
		

		Integer CeroInt = 0;
		Integer UnoInt = 1;
		Integer DosInt = 2;


		
		// Creamos una instancia de  TblAgendaLogVisitas
		TblDctosPeriodo orden = new TblDctosPeriodo();
		
    	orden.setIdLocal(idLocal);
    	orden.setIdPeriodo(xIdPeriodo);
    	orden.setNombrePeriodo(nombre);
    	orden.setFechaInicial(fechaInicial);
    	orden.setFechaFinal(fechaFinal);
    	orden.setFechaSinRecargo(fechaSinRecargo);
    	orden.setFechaConRecargo(fechaConRecargo);
    	orden.setEstadoPeriodo(DosInt);
    	orden.setEstado(DosInt);
    	orden.setEstadoEmail(DosInt);
    	orden.setEstadoLecturaApp(DosInt);
    	orden.setEstadoFacturado(UnoInt);
    	orden.setTextoPeriodo("");
    	orden.setEstadoSTR(DosInt);
    	orden.setEstadoFEDctos(CeroInt);
    	orden.setEstadoFENotas(CeroInt);
    	orden.setEstadoEnvioEmail(DosInt);
    	
		
		// Guardamos el objeto orden en la tabla TblTerceros
    	tblDctosPeriodoRepo.save(orden);
    	
    	System.out.println("IDPERIODO INGRESADO CORRECTAMENTE");
		
		return true;
	}
	
	
	
	public List <TblDctosPeriodo> ObtenerPeriodo(int idLocal, int idPeriodo){
		
		List <TblDctosPeriodo> Periodo = tblDctosPeriodoRepo.ObtenerPeriodo(idLocal, idPeriodo);
		
		return Periodo;
	}
	
	
	
	public List <TblDctosPeriodo> ObtenerPeriodoActivo(int idLocal){
		
		List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoRepo.ObtenerPeriodoActivo(idLocal);
		
		return PeriodoActivo;
	}
	
	
	public List <TblDctosPeriodo> listaUnFCH(int idPeriodo,  int idLocal){
		
		List <TblDctosPeriodo> periodo = tblDctosPeriodoRepo.listaUnFCH(idPeriodo, idLocal);
		
		return periodo;
	}
	
}
