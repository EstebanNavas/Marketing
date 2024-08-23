package com.marketing.Utilidades;

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
public class ProcesoGuardaNE {
	
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
	
	
	public int guardaContrato(int xIdLog,
			List<Integer> xIdPluInNE,          
            Integer xIdTipoOrden,
            Integer xIdUsuario,
            int xIdLocalUsuario,
            String xIdCliente,
            String xFechaInicioContrato,
            String xFechaFinContrato,                        
            double xVrSalarioBasico,
            double xVrSubsidioTransporte,
            String xObservacion,
            int xIdMedio,
            String xEntidadMedio,
            String xCuentadMedio) {
		
		
		//
        int xIdOrdenMax = 0;
        int xIdOrigenWeb = 4;
        int xEstadoDctoOrden = 1;
        int xNumeroCuotas = 1;
        String xVrCredito = "0";
        
        
       // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
        
        List<TblDctosOrdenesDTO> listaDcto = tblDctosOrdenesService.listaDctoOrdenIdLog(xIdLocalUsuario,  xIdLog);
        
        int idOrden = 0;
        
        for(TblDctosOrdenesDTO lista : listaDcto) {
        	
        	idOrden = lista.getIdOrden();

        }
        
        
        
        if(idOrden > 0) {
        	
        	// SI existeOrden
            xIdOrdenMax = idOrden;
        }else { 
        	
        	List<TercerosDTO2> listaTercero =  tblTercerosService.listaUnTerceroFachada(xIdLocalUsuario, xIdCliente);
            
        	String xIdFormaPago = "";
            String xEmail = "";
            
            for(TercerosDTO2 L : listaTercero) {
            	
            	xIdFormaPago = L.getIdFormaPago().toString();
            	xEmail = L.getEmail();

            }
        	
        	
            xIdOrdenMax  = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocalUsuario) + 1;
            
            System.out.println("xIdOrdenMax en guarda " + xIdOrdenMax);
        	
            int xIdContratoMax =  tblDctosOrdenesService.maximaIdContratoNE(xIdLocalUsuario, xIdTipoOrden);
        	
            
//            (int idLocal, int IdTipoOrden, int IdOrden, String strFechaVisita, int Estado, String IdCliente, int IdUsuario, int IdOrigen, int IdLog, 
//  				  String TipoDcto, String Email, String FormaPago, int DiasHistoria, int DiasInventario, String Observacion, int IdRazon, int IdPeriodo, int idContrato, String fechaInicioContrato, 
//  				  String fechaFinContrato, Double vrSalarioBasico, Double vrSubsidioTransporte, int idMedio, String entidadMedio, int cuentaMedio)
        	
            tblDctosOrdenesRepo.ingresaDctosOrdenNE(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, strFechaVisita, xEstadoDctoOrden, xIdCliente, xIdUsuario, xIdOrigenWeb, xIdLog,
            		xIdTipoOrden.toString(), xEmail, xIdFormaPago, 0, 0, xObservacion, 0, 0, xIdContratoMax, xFechaInicioContrato, 
            		xFechaFinContrato, xVrSalarioBasico, xVrSubsidioTransporte, xIdMedio, xEntidadMedio, xCuentadMedio);
        	
        	
            tblDctosOrdenesDetalleRepo.ingresaNE(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax);
            
            
            tblDctosOrdenesDetalleRepo.retiraPluNE(xIdLocalUsuario, xIdTipoOrden, xIdCliente, xIdPluInNE);
            
            int xIdPluSalarioBasico = 1400;
            
            
            tblDctosOrdenesDetalleRepo.actualizaPluBasicoNE(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xIdPluSalarioBasico);
            
            
            int xIdPluSubsidioTransporte = 1411;
            
            
            tblDctosOrdenesDetalleRepo.actualizaPluTransporteNE(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xIdPluSubsidioTransporte);
            
        	
        }

		
		return 0;
	}
	
	
	
	
	
	
	
	
	
	public int modificaContrato(int xIdLog,
			List<Integer> xIdPluInNE,          
            int xIdTipoOrden,
            Integer xIdUsuario,
            int xIdLocalUsuario,
            String xIdCliente,
            String xFechaInicioContrato,
            String xFechaFinContrato,                        
            double xVrSalarioBasico,
            double xVrSubsidioTransporte,
            String xObservacion,
            int xIdMedio,
            String xEntidadMedio,
            String xCuentaMedio) {
		
		
		int xIdOrdenMax = 0;
        int xIdContrato = 0;
		
		
        
        
     List<TblDctosOrdenesDTO> listaDcto = tblDctosOrdenesService.listaDctoOrdenIdLog(xIdLocalUsuario,  xIdLog);
        
        int idOrden = 0;
        
        for(TblDctosOrdenesDTO lista : listaDcto) {
        	
        	idOrden = lista.getIdOrden();

        }
        
        
       if(idOrden > 0) {
        	
        	// SI existeOrden
            xIdOrdenMax = idOrden;
            
            
            tblDctosOrdenesRepo.modificaOrdenNE(xFechaInicioContrato, xFechaFinContrato, xVrSalarioBasico, xVrSubsidioTransporte, xIdMedio, xEntidadMedio, xCuentaMedio, xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax);
                 
            tblDctosOrdenesDetalleRepo.retiraPluContratoNE(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax);

            tblDctosOrdenesDetalleRepo.ingresaNE(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax);
           
            
            tblDctosOrdenesDetalleRepo.retiraPluNE(xIdLocalUsuario, xIdTipoOrden, xIdCliente, xIdPluInNE);
            
            
            
            int xIdPluSalarioBasico = 1400;
            
            
            tblDctosOrdenesDetalleRepo.actualizaPluBasicoNE(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xIdPluSalarioBasico);
            
            
            int xIdPluSubsidioTransporte = 1411;
            
            
            tblDctosOrdenesDetalleRepo.actualizaPluTransporteNE(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xIdPluSubsidioTransporte);
            
            
        }
		
		
		return 0;
	}
	
	
	
	
	public int guardaPago(int xIdLog,
            int xIdOrdenNE,           
            int xIdTipoOrden,
            int xIdUsuario,
            int xIdLocalUsuario,
            String xIdTercero,
            String xFechaInicioContrato,
            String xObservacion,
            int xIdContrato) {
		
		
		System.out.println("INGRESA A guardaPago con xIdLog " + xIdLog);

		
		//
        int xIdOrdenMax = 0;
        int xIdTipoOrdenTmp = xIdTipoOrden + 50;
		
		
        
        
       List<TblDctosOrdenesDTO> listaDcto = tblDctosOrdenesService.listaDctoOrdenIdLog(xIdLocalUsuario,  xIdLog);
        
        int idOrden = 0;
        
        for(TblDctosOrdenesDTO lista : listaDcto) {
        	
        	idOrden = lista.getIdOrden();

        }
        
        
        System.out.println("idOrden de listaDcto es " + idOrden);
        
        
        if(idOrden > 0) {
        	
        	// SI existeOrden
            xIdOrdenMax = idOrden;
        }else { 
        	
        	xIdOrdenMax  = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocalUsuario) + 1;
        	
        	//(int xIdTipoOrdenTmp, int xIdOrdenTmp, int xIdLogTmp, int xIdContrato, int idLocal, int IdTipoOrden, int idOrden)
        	
        	tblDctosOrdenesRepo.ingresaDctosPagoNE(xIdTipoOrdenTmp, xIdOrdenMax, xIdLog, xIdContrato, xIdLocalUsuario, xIdTipoOrden, xIdOrdenNE);
        	
        	
        	//ingresaDctosOrdenPagoNE
        	tblDctosOrdenesDetalleRepo.ingresaPagoNE(xIdTipoOrdenTmp, xIdOrdenMax, xIdLocalUsuario, xIdTipoOrden, xIdOrdenNE);
        	
        	System.out.println("ingresaPagoNE OK");
        	
        }
        

	
	return xIdOrdenMax;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
