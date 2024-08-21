package com.marketing.Utilidades;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosService;

@Component
public class ProcesoAjusteConsumoCliente {
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;

	@Autowired
	TblLocalesService tblLocalesService;

	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblPlusService tblPlusService;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	public int obtieneIdOrden(int xIdLocalUsuario,
            Integer xIdTipoOrden,
            int xIdLog,
            int xIdPeriodo,
            String xIdCliente,
            double xLecturaMedidor,
            int xIdUsuario,
            double xCantidad,
            int xIdCausa) {
		
		int xIdOrdenMax = 0;
        int xIdOrigenWeb = 4;
        int xEstadoDctoOrden = 1;
        int xIdRazonConsumo = 4;
        int xIdTipoConsumo = 4;
        

        
        
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
        
        List<TercerosDTO2> listaTercero =  tblTercerosService.listaUnTerceroFachada(xIdLocalUsuario, xIdCliente);
        
        String xIdFormaPago = "";
        String xEmail = "";
        int xIdRuta = 0;
        int xIdEstracto = 0;
        
        for(TercerosDTO2 L : listaTercero) {
        	
        	xIdFormaPago = L.getIdFormaPago().toString();
        	xEmail = L.getEmail();
        	xIdRuta = L.getIdRuta();
        	xIdEstracto = L.getIdEstracto();
        }
        
        int xIdLogMax = xIdLog;
        
        List<TblDctosOrdenesDTO> listaDcto = tblDctosOrdenesService.listaDctoOrdenIdLog(xIdLocalUsuario,  xIdLog);
        int idOrden = 0;
        Double DescuentoComercial = 0.0;
        
        for(TblDctosOrdenesDTO lista : listaDcto) {
        	
        	idOrden = lista.getIdOrden();
        	DescuentoComercial = lista.getDescuentoComercial();
        }
        
        
        if(idOrden > 0) {
        	
        	// SI existeOrden
            xIdOrdenMax = idOrden;
        }else { 
        	
        	//------------------------------------------------------------------
            int estadoAtendido = 1; // visitaActiva
            int estadoProgramada = 9; // visitaProgramada
            int idEstadoVisita = 1; // Programada
        	
        	
            //Actualiza visita
            tblAgendaLogVisitasRepo.actualizaLogVisitaUsuario(estadoAtendido, xIdUsuario, estadoProgramada);
            
            
            xIdOrdenMax  = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocalUsuario) + 1;
            
            int xCero = 0;
 	        int xUno = 1;
 	        String xNada = "";
 	        Double cero = 0.0;
 	        
 	        
            
            tblDctosOrdenesRepo.ingresaDctosOrden(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, strFechaVisita, xEstadoDctoOrden, xIdCliente, xIdUsuario, xIdOrigenWeb, xIdLog, strFechaVisita,
 	        		xIdTipoOrden.toString() , xEmail, xIdFormaPago, xCero, xCero, xNada, xIdRazonConsumo, xIdPeriodo, cero, cero, cero, cero, xCero);
        	
        	
        	
        }
        
        
		
		
		return xIdOrdenMax;
	}

}
