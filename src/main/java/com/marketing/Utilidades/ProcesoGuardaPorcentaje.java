package com.marketing.Utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketing.Projection.TblDctosOrdenesDTO;
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
public class ProcesoGuardaPorcentaje {
	
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
	
	
	public int guarda(Integer xIdLog,
            Double xVrUnitario,
            int    xIdTipoOrden,
            int xIdUsuario,
            Integer xIdLocalUsuario,
            String xIdTercero,
            String xFechaCorte,
            Double xPorcentajeRteFuente) { 
		
		
		 int xIdPluSubcuenta        = 1;
         int   xCantidadPedida         = 1;
         int   xIdOrdenMax             = 0;
         int   xIdOrigenWeb            = 4;
         int   xEstadoDctoOrden        = 1;
         int   xEstadoNoMarcado        = 0;
         String xIdLista               = "1";
         String xNombrePluSubcuenta    = "SUBCUENTA CONTABLE";
         int   xIdBodega               = 1;
         int   xDiasHistoria           = 0;
         int   xDiasInventario         = 0;
         double xVrCostoNegociado      = 0.0;

         //
         String xComentario            = "";
         String xIdResponsable         = "0";
         String xIdClasificacion       = "0";
         
         
        // Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        String strFechaVisita = fechaActual.format(formatter);
		
		
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
	        	
	        	//
	             String xEmail              = "";
	             String xIdFormaPago        = "0";
	        	
	          // NO existeOrden y se igual idLocal = idLocalInicial
	        	
	            xIdOrdenMax  = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocalUsuario) + 1;
	            
	            int xCero = 0;
	 	        int xUno = 1;
	 	        String xNada = "";
	 	        Double cero = 0.0;
	 	        
//	 	       (int idLocal, int IdTipoOrden, int IdOrden, String strFechaVisita, int Estado, String IdCliente, int IdUsuario, int IdOrigen, int IdLog, String FechaEntregaSqlServer, 
//	 				  String TipoDcto, String Email, String FormaPago, int DiasHistoria, int DiasInventario, String Observacion, int IdRazon, int IdPeriodo, Double VrTotalDiferir, Double CuotaDiferir, 
//	 				  Double PorcentajeInteresADiferir, Double VrInteresADiferir, int OrdenCompra)
	 	        
	 	        tblDctosOrdenesRepo.ingresaDctosOrden(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, strFechaVisita, xEstadoDctoOrden, xIdTercero, xIdUsuario, xIdOrigenWeb, xIdLog, xFechaCorte,
	 	        		"0" , xEmail, xIdFormaPago, xCero, xCero, xNada, xCero, xCero, cero, cero, cero, cero, xCero);
	        	      
	        	
	        }
	        
	        
	        
	       // maximoItem
	        Integer maximoItem =  tblDctosOrdenesDetalleService.maximoItem(xIdTipoOrden, xIdLocalUsuario, xIdLog) + 1;
	        
	        Double cero = 0.0;
	        int xCero = 0;
	        
//	        (int idLocal, int IdTipoOrden, int IdOrden, int cantidad, String nombrePlu, int idPlu, int idTipo, int estado, int porcentajeIva, Double vrVentaUnitario, 
//					  Double vrVentaOriginal, Double vrCosto, Double vrDsctoPie, int porcentajeDscto, int cantidadPedida, String strIdLista, String nombreUnidadMedida, String comentario, int item, int itemPadre, 
//					  int idEstadoTx, int idTipoTx, int idBodega, int idSubcuenta, String idCliente, int idRuta, int idEstracto)
	        
	        tblDctosOrdenesDetalleRepo.ingresaDetalle(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xCantidadPedida, xNombrePluSubcuenta, xIdPluSubcuenta, 5, xEstadoNoMarcado, 0, xVrUnitario,
	        										cero, cero, DescuentoComercial, xCero, xCantidadPedida, xIdLista, "0", xComentario, maximoItem, maximoItem,
	        										xCero, xCero, xIdBodega, xCero, xIdTercero, xCero, xCero);
		
		
		    return maximoItem;
	}

}
