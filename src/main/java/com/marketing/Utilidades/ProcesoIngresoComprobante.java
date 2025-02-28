package com.marketing.Utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Projection.TblDctosDTO3;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
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
public class ProcesoIngresoComprobante {
	
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
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	ProcesoInventario procesoInventario;
	

    public int ingresaCompra(int xIdLocal,
            int xIdTipoOrdenNew,
            int xIdLog,
            int xIdTipoOrdenOld,
            String xFechaCorte,
            String xDescripcion,
            String xIdDctoNitCC,
            int xIdAlcance,
            int xIndicador,
            int xIdTipoNegocio,
            String xVrIva,
            String xVrRteFuente,
            String xVrRteIva,
            String xVrRteIca) { 
    	
    	
    	
    	
    	List<TblDctosOrdenesDTO> ListaDctoOrden = tblDctosOrdenesService.listaDctoOrdenIdLogIdTipoOrden(xIdLocal, xIdTipoOrdenOld, xIdLog);
    	
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
    	
     // Encabezado Dctos
        int xIdOrdenOld = 0;
        String xIdCliente = "";
        int xIdUsuario = 0;
        String email = "";
        String fax = "";
        String contacto = "";
        String direccionDespacho = "";
        String ciudadDespacho = "";
        
        //------------------
        String ordenCompra = "";
        Double descuentoComercial = 0.0;
        Double impuestoVenta = 0.0;
        
        for(TblDctosOrdenesDTO dctOrden : ListaDctoOrden) {
        	
        	   xIdOrdenOld = dctOrden.getIdOrden();
        	    xIdCliente = dctOrden.getIdCliente() != null ? dctOrden.getIdCliente() : "";
        	    xIdUsuario = dctOrden.getIdUsuario() != null ? dctOrden.getIdUsuario() : 0; 
        	    email = dctOrden.getEmail() != null ? dctOrden.getEmail() : "";
        	    fax = dctOrden.getFax() != null ? dctOrden.getFax() : "";
        	    contacto = dctOrden.getContacto() != null ? dctOrden.getContacto() : "";
        	    direccionDespacho = dctOrden.getDireccionDespacho() != null ? dctOrden.getDireccionDespacho() : "";
        	    ciudadDespacho = dctOrden.getCiudadDespacho() != null ? dctOrden.getCiudadDespacho() : "";
        	    ordenCompra = dctOrden.getOrdenCompra() != null ? dctOrden.getOrdenCompra() : "";
        	    descuentoComercial = dctOrden.getDescuentoComercial() != null ? dctOrden.getDescuentoComercial() : 0.0;
        	    impuestoVenta = dctOrden.getImpuestoVenta() != null ? dctOrden.getImpuestoVenta() : 0.0;
        }
    	
        
        
        
    	
        List<TercerosDTO2> listaTerceroUnion = tblTercerosService.listaUnTerceroUnionFCH(xIdLocal, xIdCliente);
    	
        String xNombreTercero = "";
        Integer xIdFormaPago = 0;
    	
    	for(TercerosDTO2 tercero : listaTerceroUnion ) {
    		
    		xNombreTercero = tercero.getNombreTercero();
    		xIdFormaPago = tercero.getIdFormaPago() != null ? tercero.getIdFormaPago() : 0 ;
    	}

    	
    	  List<TblDctosOrdenesDetalleDTO> liquidaOrden = tblDctosOrdenesDetalleService.liquidaOrdenFCH(xIdLocal, xIdTipoOrdenOld, xIdLog);
          
    	  double xVrVentaSinIva = 0.0;
          Double xVrVentaSinDscto = 0.0;
          double xVrCosto = 0.0;
          
          
          for(TblDctosOrdenesDetalleDTO orden : liquidaOrden) {
          	
        	  xVrVentaSinIva = orden.getVrVentaSinIva();
        	  xVrVentaSinDscto = orden.getVrVentaSinDscto();
        	  xVrCosto = orden.getVrVentaConIva();
          }
          

          Double xVrIvaDou = Double.parseDouble(xVrIva);
          Double xVrRteFuenteDou = Double.parseDouble(xVrRteFuente);
          Double xVrRteIvaDou = Double.parseDouble(xVrRteIva);
          Double xVrRteIcaDou = Double.parseDouble(xVrRteIca);
          
          
          Double VrDescuento = xVrVentaSinIva - xVrVentaSinDscto;
          Double VrPagarDctoNitCC = xVrVentaSinDscto + xVrIvaDou - xVrRteFuenteDou-xVrRteIvaDou-xVrRteIcaDou;

          
          int  xIdOrdenMax  = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocal) + 1;
          
          System.out.println("xIdOrdenMax es " + xIdOrdenMax);
          
          double xCero = 0.0;
          int xUno = 1;
          int CeroInt = 0;
          String xNada = "";
          
          
          // Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(xIdLocal);
	        
	       Integer idPeriodo = 0;
			
			for(TblDctosPeriodo P : PeriodoActivo) {					
				idPeriodo = P.getIdPeriodo();				
			}
          
          
//          (int idLocal, int IdTipoOrden, int IdOrden, String strFechaVisita, int Estado, String IdCliente, int IdUsuario, int IdOrigen, int IdLog, String FechaEntregaSqlServer, 
//				  String TipoDcto, String Email, String fax, String Contacto, String Observacion, String DireccionDespacho, String CiudadDespacho, String FormaPago, String OrdenCompra,
//				  int DescuentoComercial, int ImpuestoVenta, int IdRazon, int IdEstadoTx, int IdTipoTx, int NumeroOrden, int IdResponsable, int DiasHistoria, int DiasInventario, int IdPeriodo, 
//				  Double VrTotalDiferir, int CuotaDiferir, Double PorcentajeInteresADiferir, Double VrInteresADiferir, Double Promedio, String HistoriaConsumo, Double PromedioEstrato);
        
        tblDctosOrdenesRepo.ingresaPedido(xIdLocal, xIdTipoOrdenNew, xIdOrdenMax, strFechaVisita, xEstadoDcto, xIdCliente, xIdUsuario, xIOrigenBB, xIdLog, strFechaVisita, 
        								"0", email, fax, contacto, xDescripcion, direccionDespacho, ciudadDespacho, xIdFormaPago.toString(), ordenCompra,
        								descuentoComercial.intValue(), impuestoVenta.intValue(), xIdRazonVacia, xIdEstadoTx, xIdTipoTx, xNumeroOrden, xIdResponsable, CeroInt, CeroInt, idPeriodo, 
       		                             xCero, CeroInt, xCero, xCero, xCero, xNada, xCero);
    	
        
        int xIdTipoNegocioContado = 1;
        Double xVrPago = 0.0;
        
        
        //xIdTipoNegocio
        if (xIdTipoNegocio == xIdTipoNegocioContado) {
            //
            xVrPago = xVrVentaSinDscto + xVrIvaDou - xVrRteFuenteDou - 
                                                    xVrRteIvaDou - xVrRteIcaDou;

        } else {

            //
            xVrPago = 0.0;
        }
        
        
        
        int xIdDctoMax = tblDctosService.maximoDctoLocalIndicador(xIdLocal, 601, 1) + 1;
       
        // Documento Soporte
        int xIdRemisionMax = 0;
        
//      (int idLocal, int IdTipoOrden, int IdOrden, int IdDcto, int Indicador, String FechaDctoSqlServer, Double VrBaseSinRedondeo, int VrPago, int Estado, Double VrIva, 
// 			 int IdTipoNegocio, int VrRteFuente, Double VrDescuento, int VrRteIva, int VrRteIca, String NombreTercero, int IdUsuario, String IdCliente, int DiasPlazo, int PorcentajeDscto, 
// 			 int IdCausa, String IdDctoNitCC, String FechaDctoNitCCSqlServer, int VrPagarDctoNitCC, int VrDsctoFcro, Double VrCostoMV, int IdLocalCruce, int IdTipoOrdenCruce, int IdDctoCruce, 
// 			 int IdPeriodo, int IdVendedor, Double VrImpoconsumo, Double VrCostoIND, int IdOrdenCruce, int EtapaSTR, int EnvioFE)
      
      tblDctosRepo.ingresaDcto(xIdLocal, xIdTipoOrdenNew, xIdOrdenMax, xIdDctoMax, xIndicador, xFechaCorte, xVrVentaSinDscto, xVrPago.intValue(), 1, xVrIvaDou, 
    		  xIdTipoNegocio, xVrRteFuenteDou.intValue(), VrDescuento, xVrRteIvaDou.intValue(), xVrRteIcaDou.intValue(), xNombreTercero, xIdUsuario, xIdCliente, xIdFormaPago, CeroInt, 
     		 CeroInt, xIdDctoNitCC, xFechaCorte, VrPagarDctoNitCC.intValue(), CeroInt, xVrVentaSinDscto, xIdLocal, CeroInt, CeroInt,
     		idPeriodo, xIdUsuario, xCero, xVrVentaSinDscto, CeroInt, CeroInt, CeroInt);
        
      
      
      
      tblDctosOrdenesDetalleRepo.actualiza(xIdTipoOrdenNew, xIdOrdenMax, xIdLocal, xIdTipoOrdenOld, xIdOrdenOld);
        
      
      procesoInventario.actualizaInventarioEstado(xIdLocal, xIdTipoOrdenNew, xIdOrdenMax);
        
     // actualizaEstadoInventario
      int xEstadoInventario = 4;
      int estado = 0;  
        
      tblDctosOrdenesDetalleRepo.actualizaEstadoInventario(xEstadoInventario, xIdLocal, xIdTipoOrdenNew, xIdOrdenMax, estado); 
        
        
     // retira
      tblDctosOrdenesRepo.retiraLog(xIdLocal, xIdTipoOrdenOld, xIdOrdenOld);  
    	
    	
    	return xIdOrdenMax;
    }

}
