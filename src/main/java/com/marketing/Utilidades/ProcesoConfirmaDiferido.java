package com.marketing.Utilidades;

import java.sql.Timestamp;
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
public class ProcesoConfirmaDiferido {
	
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
	        
	        int xEstadoNoMarcado = 0;
	        String xIdLista = "1";
	        int xItemPadre = 1;
	        int xIdBodega = 1;
	        int xIdRuta = 0;
	        int xIdEstracto = 0;
	        
	        
	        
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
	        
	        Integer descuentoComercialInt = Integer.parseInt(descuentoComercial);
	        Integer impuestoVentaInt = Integer.parseInt(impuestoVenta);
	        
	        
	        Integer xIdPluint = tblDctosOrdenesDetalleService.ObtenerIdPlu(xIdLocal, xIdOrdenOld);
	        System.out.println("xIdPluint es : " + xIdPluint);
		  
		  
		  
	        List<TblDctosOrdenesDTO>  logLiquida = tblDctosOrdenesService.liquidaLog(xIdLog, xIdLocal, xIdTipoOrdenOld);
		  
	        Double xVrVentaSinIva = 0.0;
	        
	        for(TblDctosOrdenesDTO liquida : logLiquida) {
	        	
	        	xVrVentaSinIva = liquida.getVrVentaSinIva();
	        }
		  
	        
	        Integer xIdOrdenMax  = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocal) + 1;
		  
	        // ingresaPedido
	        tblDctosOrdenesRepo.ingresaPedido(xIdLocal, xIdTipoOrdenNew, xIdOrdenMax, strFechaVisita, xEstadoDcto, xIdCliente, xIdUsuario, xIOrigenBB, xIdLog, strFechaVisita,
	        		xIdTipoOrdenNew.toString(), email, fax, contacto, observacion, direccionDespacho, ciudadDespacho, formaPago, ordenCompra,
	        		descuentoComercialInt, impuestoVentaInt, xIdRazonVacia, xIdEstadoTx, xIdTipoTx, xNumeroOrden, xIdResponsable, cero, cero, xIdPeriodo,
	        		xVrTotalDiferir, xCuotaDiferir, xVrPorcentajeADiferir, xVrInteresADiferir, ceroDouble, nada, ceroDouble);
	        
	        Integer xIdClienteint = Integer.parseInt(xIdCliente);
	        
	        String xObservacion = tblDctosOrdenesDetalleService.ObtenerComentario(xIdLocal, xIdOrdenOld, xIdClienteint);
	        
	        
	        //------------- ELIMINAMOS LA ORDEN ACTUAL PARA INGRESAR LAS NUEVAS CUOTAS MODIFICADAS 
	        
	        tblDctosOrdenesDetalleRepo.eliminaridOrden(xIdLocal, xIdOrdenOld);
	        
	        
	        
	        
	        
	        List<TblDctosOrdenesDTO> listaDctos = tblDctosOrdenesService.listaDctoOrdenIdLog(xIdLocal,  xIdLog);
	        int idOrden = 0;
	        Double DescuentoComercial = 0.0;
	        
	        for(TblDctosOrdenesDTO lista : listaDctos) {
	        	
	        	idOrden = lista.getIdOrden();
	        	DescuentoComercial = lista.getDescuentoComercial();
	        }
	        
	        
	        List<TercerosDTO2> listaTercero =  tblTercerosService.listaUnTerceroFachada(xIdLocal, xIdCliente);
            
	        String xIdFormaPago = "";
            String xEmail = "";
            int xIndicador = 0;
	        String xNombreTercero = "";
            
            for(TercerosDTO2 L : listaTercero) {
            	
            	xIdFormaPago = L.getIdFormaPago().toString();
            	xEmail = L.getEmail();
            	xIdRuta = L.getIdRuta();
            	xIdEstracto = L.getIdEstracto();
            	xIndicador = L.getIndicador();
            	xNombreTercero = L.getNombreTercero();
            }
	        
	        
	        
	        
	        
	        double xCantidad = 1.0;
	        
	        List<TblPlusDTO> listaPlu =  tblPlusService.listaUnPluFCH(xIdPluint.toString(), xIdLocal);
	        
	        String NombreCategoria = "";
	        String NombrePlu = "";
	        int idTipo = 0;
	        Double PorcentajeIva = 0.0;
	        Double VrCosto = 0.0;
	        String IdUVentaStr = "";
	        
	        for(TblPlusDTO lista : listaPlu) {
	        	
	        	NombreCategoria = lista.getNombreCategoria();
	        	NombrePlu = lista.getNombrePlu();
	        	idTipo = lista.getIdTipo();
	        	PorcentajeIva = lista.getPorcentajeIva();
	        	VrCosto = lista.getVrCosto();
	        	
	            // Validar si IdUVenta es null 
	            if (lista.getIdUVenta() != null) {
	                IdUVentaStr = lista.getIdUVenta().toString();
	            } else {
	                IdUVentaStr = "0"; 
	            }
	        }
	        
	        String nombreCompleto = NombreCategoria + "" + NombrePlu;
	        
	        
	        
	        
	        System.out.println("xObservacion es : " + xObservacion );
	        


	     // Realizamos el ingreso de la cuotas 
	        
	     for (int indice = 0; indice < ArrayVrCuota.length; indice++) {
	         // Obtener el valor de la cuota en la posición actual del array
	         double xVrVentaUnitarioInt = Double.parseDouble(ArrayVrCuota[indice]);

	         // Resto del código...
	         int xItem = indice + 1;
	         int xCero = 0;
	         

	         
	         tblDctosOrdenesDetalleRepo.ingresaLecturaMedidor(xIdLocal, xIdTipoOrdenNew, xIdOrdenMax, xCantidad, nombreCompleto, xIdPluint, idTipo, xEstadoNoMarcado, PorcentajeIva,
	                 xVrVentaUnitarioInt, xVrVentaUnitarioInt, VrCosto, DescuentoComercial, ceroDouble, ceroDouble, xIdLista, IdUVentaStr, 
	                 xObservacion, xItem, xItemPadre, xCero, xCero, xIdBodega, xCero, ceroDouble, xIdCliente, xIdEstracto, xIdRuta, xCero);
	     }
	        
	        
	        
		   
	        
	        
	        
	        tblDctosOrdenesDetalleRepo.actualiza(xIdTipoOrdenNew, xIdOrdenMax, xIdLocal, xIdTipoOrdenOld, xIdOrdenOld);
	        
	        tblDctosOrdenesDetalleRepo.actualizaCuotaNumero(xIdLocal, xIdTipoOrdenNew, xIdOrdenMax);
	        

		  
	        
	        double xVrCero = 0.0;
	        
	        
	        Integer xIdDctoMax = tblDctosService.maximoDctoLocalIndicador(xIdLocal, xIdTipoOrdenNew, xIndicador) + 1;
	        
	        
	        tblDctosRepo.ingresaDcto(xIdLocal, xIdTipoOrdenNew, xIdOrdenMax, xIdDctoMax, xIndicador, strFechaVisita, xVrVentaSinIva, cero, 1, xVrCero, 
	        		2, cero, xVrCero, cero, cero, xNombreTercero, xIdUsuario, xIdCliente, cero, cero,
	        		cero, xIdDctoMax.toString(), strFechaVisita, cero, cero, xVrCero, xIdLocal, cero, cero,
	        		xIdPeriodo, xIdUsuario, ceroDouble, xVrCero, cero, cero, cero);
	        
	        
	        
	        
	        // retira
	        tblDctosOrdenesRepo.retiraLog(xIdLocal, xIdTipoOrdenOld, xIdOrdenOld);
	        

		  return xIdOrdenMax;
		  
	  }

}
