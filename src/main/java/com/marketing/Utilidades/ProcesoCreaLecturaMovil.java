package com.marketing.Utilidades;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblLocalesService;

@Component
public class ProcesoCreaLecturaMovil {

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

	public boolean crea(int idLocalUsuario, Integer idTipoOrden, int idPeriodo, Integer idUsuario) {


		System.out.println("idLocalUsuario: " + idLocalUsuario);
		System.out.println("idTipoOrden: " + idTipoOrden);
		System.out.println("idPeriodo: " + idPeriodo);
		System.out.println("idUsuario: " + idUsuario);



		int xIdOrdenMax = 0;
		int xIdOrigenWeb = 4;
		int xEstadoDctoOrden = 1;
		int xIdRazonConsumo = 4;
		String xEmail = "nn@nn.com";
		String xIdFormaPago = "0";
		int xIdTipoConsumo = 4;

		Timestamp fechaHoy = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha y hora actuales

		Integer idOrden = tblDctosOrdenesService.listaOrdenIdPeriodo(idLocalUsuario, idPeriodo, idTipoOrden,
				xIdRazonConsumo);

		// Validamos el periodo ya tiene orden o no
		if (idOrden > 0) {

			xIdOrdenMax = idOrden;
		} else {

			System.out.println("idOrden esssssssssss: " + idOrden);

			List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocalUsuario);
			int xPeriodoFactura = 0;

			for (TblLocales L : Local) {

				xPeriodoFactura = L.getPeriodoFactura();

			}
			
			
			List <String> xIdPeriodoLista = tblDctosPeriodoService.listaPeriodo(idLocalUsuario, xPeriodoFactura, idPeriodo);
			
			//Actualizamos promedio
			tblTercerosRepo.actualizaPromedio(idLocalUsuario, xIdPeriodoLista, xPeriodoFactura);
			
			
			int estadoAtendido = 1; // visitaActiva
            int estadoProgramada = 9; // visitaProgramada
            int idEstadoVisita = 1; // Programada
            
            // Obtenemos el idLog Maximo
            Integer xIdLogMax = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
            
            //Actualiza visita
            tblAgendaLogVisitasRepo.actualizaLogVisitaUsuario(estadoAtendido, idUsuario, estadoProgramada);
            
            Integer xidTipoOrden = 9;
            
            //Ingresa visita
            boolean okIngreso = tblAgendaLogVisitasService.ingresaLogVisita(idLocalUsuario, xIdLogMax, idUsuario.toString(), idUsuario, idPeriodo, idEstadoVisita, estadoAtendido, xidTipoOrden);
            
            //Obtenemos el idOrden maximo
            xIdOrdenMax = tblDctosOrdenesService.maximaIdOrdenIdLocal(idLocalUsuario) + 1;
            
            okIngreso = tblDctosOrdenesService.ingresaDctosOrden(idLocalUsuario, idTipoOrden, xIdOrdenMax, fechaHoy, xEstadoDctoOrden, idUsuario.toString(), idUsuario, xIdOrigenWeb,
            		xIdLogMax, idTipoOrden.toString(), xEmail, xIdFormaPago, xIdRazonConsumo, idPeriodo );
            
		}
		
		
		tblDctosOrdenesDetalleRepo.ingresaCreaLectura(idTipoOrden, xIdOrdenMax, xIdTipoConsumo, idLocalUsuario);
		
		boolean exitoso = true; 

		return exitoso;
	}
}
