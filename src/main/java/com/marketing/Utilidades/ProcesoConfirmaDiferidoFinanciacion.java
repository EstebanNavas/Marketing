package com.marketing.Utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblPlusDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosService;

@Component
public class ProcesoConfirmaDiferidoFinanciacion {

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
	TblDctosService tblDctosService;
	
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
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	
	  public int ingresa(int xIdLocal,
	            Integer xIdTipoOrdenNew,
	            int xIdLog,
	            int xIdTipoOrdenOld,
	            String[] ArrayVrCuota,
	            Integer xNumeroCuotas) {
		  
		  
		  
		  
		  
		  List<TblDctosOrdenesDTO>  listaDcto = tblDctosOrdenesService.listaDctoOrdenIdLogIdTipoOrden(xIdLocal, xIdTipoOrdenOld, xIdLog);
		  
		  
		  	// Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        String strFechaVisita = fechaActual.format(formatter);
		  
		  //
	        int xEstadoDcto = 1;
	        int xIOrigenBB = 1;
	        int xIdRazonVacia = 01;
	        int xIdEstadoTx = 2;  // autorizado
	        int xIdTipoTx = 1;
	        int xNumeroOrden = 0;
	        int xIdResponsable = 0;
	        Double xVrTotalDiferir = 0.0;
	        int xCuotaDiferir = 0;
	        Double xVrPorcentajeADiferir = 0.0;
	        Double xVrInteresADiferir = 0.0;
	        int cero = 0;
	        Double ceroDouble = 0.0;
	        String nada = "";
		  
		  
		  
	     // Encabezado Dctos
	        int xIdOrdenOld = 0;
	        String xIdCliente = "";
	        int xIdUsuario = 0;
	        String email = "";
	        String fax = "";
	        String contacto = "";
	        String observacion = "";
	        String direccionDespacho = "";
	        String ciudadDespacho = "";
	        String formaPago = "";

	        //------------------
	        String ordenCompra = "";
	        String descuentoComercial = "";
	        String impuestoVenta = "";
	        String idRazon = "";
	        int xIdPeriodo = 0;
	        

	        
	        
	        
	        for(TblDctosOrdenesDTO lista : listaDcto) {
	        	
	        	 xIdOrdenOld = lista.getIdOrden();
	        	 xIdCliente = lista.getIdCliente();
	             xIdUsuario = lista.getIDUSUARIO();
	             email = lista.getEmail() != null ? lista.getEmail() : "";
	             fax = lista.getFax() != null ?  lista.getFax() : "";
	             contacto = lista.getContacto() != null ? lista.getContacto() : "";
	             observacion = lista.getObservacion()  != null ? lista.getObservacion() : "";
	             direccionDespacho = lista.getDireccionDespacho()  != null ? lista.getDireccionDespacho() : "";
	             ciudadDespacho = lista.getCiudadDespacho() != null ? lista.getCiudadDespacho() : "";
	             formaPago = lista.getFormaPago();

	            //------------------
	             ordenCompra = lista.getOrdenCompra();
	             descuentoComercial = lista.getDescuentoComercial() != null && lista.getDescuentoComercial().toString().isEmpty() ? lista.getDescuentoComercial().toString() : "0";
	             impuestoVenta = lista.getImpuestoVenta() != null ? lista.getImpuestoVenta().toString() : "0";
	             idRazon = lista.getIdRazon() != null ? lista.getIdRazon().toString() : "0";
	             xIdPeriodo = lista.getIdPeriodo();
	             
	             xVrTotalDiferir = lista.getVrTotalDiferir();
	             xCuotaDiferir = lista.getCuotaDiferir();
	             xVrPorcentajeADiferir = lista.getPorcentajeInteresADiferir();
	             xVrInteresADiferir = lista.getVrInteresADiferir();
	        	
	        }
	        


		  
	        List<TblDctosOrdenesDTO>  logLiquida = tblDctosOrdenesService.liquidaLog(xIdLog, xIdLocal, xIdTipoOrdenOld);
		  
	        Double xVrVentaSinIva = 0.0;
	        
	        for(TblDctosOrdenesDTO liquida : logLiquida) {
	        	
	        	xVrVentaSinIva = liquida.getVrVentaSinIva();
	        }
		  
	        System.out.println("xVrVentaSinIva es : " + xVrVentaSinIva);


	        
	        //------------- ACTUALIZAMOS DETALLE
	        tblDctosOrdenesDetalleRepo.actualizaIdTipoOrden(xIdTipoOrdenNew, xIdLocal, xIdOrdenOld);
	        
	        //------------- ACTUALIZAMOS ORDEN
	        tblDctosOrdenesRepo.actualizaIdTipoOrden(xIdTipoOrdenNew, xIdLocal, xIdOrdenOld, xIdLog);
	        
	        
	        
	        List<TercerosDTO2> listaTercero =  tblTercerosService.listaUnTerceroFachada(xIdLocal, xIdCliente);
            

            int xIndicador = 0;
	        String xNombreTercero = "";
            
            for(TercerosDTO2 L : listaTercero) {

            	xIndicador = L.getIndicador();
            	xNombreTercero = L.getNombreTercero();
            }

	        

	        
	       // tblDctosOrdenesDetalleRepo.actualizaCuotaNumero(xIdLocal, xIdTipoOrdenNew, xIdOrdenOld);

	        double xVrCero = 0.0;
	        
	        
	        Integer xIdDctoMax = tblDctosService.maximoDctoLocalIndicador(xIdLocal, xIdTipoOrdenNew, xIndicador) + 1;
	        
	        
	        tblDctosRepo.ingresaDcto(xIdLocal, xIdTipoOrdenNew, xIdOrdenOld, xIdDctoMax, xIndicador, strFechaVisita, xVrVentaSinIva, cero, 1, xVrCero, 
	        		2, cero, xVrCero, cero, cero, xNombreTercero, xIdUsuario, xIdCliente, cero, cero,
	        		cero, xIdDctoMax.toString(), strFechaVisita, cero, cero, xVrCero, xIdLocal, cero, cero,
	        		xIdPeriodo, xIdUsuario, ceroDouble, xVrCero, cero, cero, cero);
	        
	        
	        
	        
	        // retira
	        tblDctosOrdenesRepo.retiraLog(xIdLocal, xIdTipoOrdenOld, xIdOrdenOld);
	        

		  return xIdOrdenOld;
		  
	  }
}
