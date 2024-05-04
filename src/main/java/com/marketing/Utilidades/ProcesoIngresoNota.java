package com.marketing.Utilidades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblContableRetencionService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Model.dbaquamovil.TblContableRetencion;
import com.marketing.Model.dbaquamovil.TblDctos;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TercerosDTO2;

@Component
public class ProcesoIngresoNota {
	
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
	TblContableRetencionService tblContableRetencionService;
	
	@Autowired
	TblDctosService tblDctosService;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	
	
	public Integer ingresa(int xIdLocal,
            Integer xIdTipoOrdenNew,
            int xIdOrden,
            int xIdLog,
            int xIdTipoOrdenOld,
            int xSignoOperacion,
            int xIdDcto,
            int xIndicador,
            String xObservacion) {
		
		
		int xIdTipoOrdenCruce = xIdTipoOrdenNew - 20;
		
		List<TblDctosOrdenesDTO> listaDcto = tblDctosOrdenesService.listaDctoOrdenIdLogIdTipoOrden(xIdLocal, xIdTipoOrdenOld, xIdLog);
		
		
		
		// Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
		
        
        int xEstadoDcto = 1;
        int xIOrigenBB = 1;
        int xIdRazonVacia = 01;
        int xIdEstadoTx = 2;  // autorizado
        int xIdTipoTx = 1;
        int xNumeroOrden = 0;
        int xIdResponsable = 0;
        int xIdConceptoRFCompra = 1;
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
        String observacion = xObservacion;
        String direccionDespacho = "";
        String ciudadDespacho = "";
        String formaPago = "";

        //------------------
        String ordenCompra = "";
        String descuentoComercial = "";
        String impuestoVenta = "";
        String idRazon = "";
        int idPeriodo = 0;
		
        
        for(TblDctosOrdenesDTO lista : listaDcto) {
        	
        	 xIdOrdenOld = lista.getIdOrden();
        	 xIdCliente = lista.getIdCliente();
             xIdUsuario = lista.getIDUSUARIO();
             email = lista.getEmail() != null ? lista.getEmail() : "";
             fax = lista.getFax() != null ?  lista.getFax() : "";
             contacto = lista.getContacto() != null ? lista.getContacto() : "";
             direccionDespacho = lista.getDireccionDespacho()  != null ? lista.getDireccionDespacho() : "";
             ciudadDespacho = lista.getCiudadDespacho() != null ? lista.getCiudadDespacho() : "";
             formaPago = lista.getFormaPago();

            //------------------
             ordenCompra = lista.getOrdenCompra();
             descuentoComercial = lista.getDescuentoComercial() != null ? lista.getDescuentoComercial().toString() : "0";
             impuestoVenta = lista.getImpuestoVenta() != null ? lista.getImpuestoVenta().toString() : "0";
             idRazon = lista.getIdRazon() != null ? lista.getIdRazon().toString() : "0";
             idPeriodo = lista.getIdPeriodo();
        	
        }
        
        
        Integer descuentoComercialInt = Integer.parseInt(descuentoComercial);
        Integer impuestoVentaInt = Integer.parseInt(impuestoVenta);
		
		
        List<TercerosDTO2> listaTercero =  tblTercerosService.listaUnTerceroFachada(xIdLocal, xIdCliente);
        
        int xIdFormaPago = 0;
        
        for(TercerosDTO2 L : listaTercero) {
        	
        	xIdFormaPago = L.getIdFormaPago();
        }
        
        
        
        List<TblDctosOrdenesDetalleDTO> liquidaOrden =  tblDctosOrdenesDetalleService.liquidaOrdenFCH(xIdLocal, xIdTipoOrdenOld, xIdLog);
        
        double xVrVentaSinIva = 0.0 ;
        double xVrVentaSinDscto =  0.0;
        double xVrIva =  0.0;
        double xVrCosto =  0.0;
        
        for(TblDctosOrdenesDetalleDTO L : liquidaOrden) {
        	
        	xVrVentaSinIva = L.getVrVentaSinIva() != null ? L.getVrVentaSinIva() : 0.0;
        	xVrVentaSinDscto = L.getVrVentaSinDscto() != null ? L.getVrVentaSinDscto() : 0.0;
        	xVrIva = L.getVrIvaVenta() != null ? L.getVrIvaVenta() : 0.0;
        	xVrCosto = L.getVrCostoSinIva() != null ? L.getVrCostoSinIva() : 0.0;
        }
        
        
        
        List<TblContableRetencion> listaRetencion = tblContableRetencionService.calculaRetencion(xIdConceptoRFCompra, xVrVentaSinDscto);
        
        Double xVrBase = 0.0;
        Double xPorcentajeRetencion = 0.0;
        
        
        for(TblContableRetencion L : listaRetencion  ) {
        	
        	xVrBase = L.getVrBaseRetencion() != null ? L.getVrBaseRetencion() : 0.0;
        	xPorcentajeRetencion = L.getPorcentajeRetencion() != null ? L.getPorcentajeRetencion() : 0.0;
        	
        }
        
        
        Double xVrRetencion = xVrBase * ( xPorcentajeRetencion / 100 );
        
        Integer xIdOrdenMax  = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocal) + 1;
        
        
        
        tblDctosOrdenesRepo.ingresaPedido(xIdLocal, xIdTipoOrdenNew, xIdOrdenMax, strFechaVisita, xEstadoDcto, xIdCliente, xIdUsuario, xIOrigenBB, xIdLog, strFechaVisita,
        		xIdTipoOrdenNew.toString(), email, fax, contacto, observacion, direccionDespacho, ciudadDespacho, formaPago, ordenCompra,
        		descuentoComercialInt, impuestoVentaInt, xIdRazonVacia, xIdEstadoTx, xIdTipoTx, xNumeroOrden, xIdResponsable, cero, cero, idPeriodo,
        		ceroDouble, cero, ceroDouble, ceroDouble, ceroDouble, nada, ceroDouble);
        
        
        List<TercerosDTO2> listaCliente =  tblTercerosService.listaUnTerceroFachada(xIdLocal, xIdCliente);
        
        
        List<TblDctosDTO> listaUnDcto =  tblDctosService.listaUnDctoOrden(xIdLocal, xIdTipoOrdenCruce, xIdOrden, xIndicador);
        
        int xIdLocalCruce = 0;
        Double xIdDctoCruce = 0.0;
        Double xIdVendedor = 0.0;
        Double xIdTipoNegocio = 0.0;
        String xNombreTercero = "";
        
        for(TblDctosDTO L : listaUnDcto) {
        	
        	xIdLocalCruce = L.getIdLocalCruce();
        	xIdDctoCruce = L.getIdDcto();
        	xIdVendedor = L.getIdVendedor();
        	xIdTipoNegocio = L.getIdTipoNegocio();
        	xNombreTercero = L.getNombreTercero();
        	
        }
        
        Integer xIdDctoMax = tblDctosService.maximoDctoLocalIndicador(xIdLocal, xIdTipoOrdenNew, xIndicador) + 1;
        
        Double ValorIva = xVrIva * xSignoOperacion;
        Double VrRteFuente = xVrRetencion * (-1);
        int VrRteFuenteInt = VrRteFuente.intValue();
        
        Double VrCostoMV = xVrCosto * xSignoOperacion;
        
        int IdDctoCruce = xIdDctoCruce.intValue();
        int IdVendedor = xIdVendedor.intValue();
        
        System.out.println("IdDctoCruce essssss: " + IdDctoCruce);
        
        Double VrCostoIND = xVrCosto * xSignoOperacion;
        
//        (int idLocal, int IdTipoOrden, int IdOrden, int IdDcto, int Indicador, String FechaDctoSqlServer, Double VrBaseSinRedondeo, int VrPago, int Estado, Double VrIva, 
//   			 int IdTipoNegocio, int VrRteFuente, Double VrDescuento, int VrRteIva, int VrRteIca, String NombreTercero, int IdUsuario, String IdCliente, int DiasPlazo, int PorcentajeDscto, 
//   			 int IdCausa, String IdDctoNitCC, String FechaDctoNitCCSqlServer, int VrPagarDctoNitCC, int VrDsctoFcro, Double VrCostoMV, int IdLocalCruce, int IdTipoOrdenCruce, int IdDctoCruce, 
//   			 int IdPeriodo, int IdVendedor, Double VrImpoconsumo, Double VrCostoIND, int IdOrdenCruce, int EtapaSTR, int EnvioFE)
        
        tblDctosRepo.ingresaDcto(xIdLocal, xIdTipoOrdenNew, xIdOrdenMax, xIdDctoMax, xIndicador, strFechaVisita, ceroDouble, cero, 1, ValorIva, 
        		cero, VrRteFuenteInt, ceroDouble, cero, cero, xNombreTercero, xIdUsuario, xIdCliente, cero, cero,
        		cero, xIdDctoMax.toString(), strFechaVisita, cero, cero, VrCostoMV, xIdLocal, xIdTipoOrdenCruce, IdDctoCruce,
        		idPeriodo, IdVendedor, ceroDouble, VrCostoIND, xIdOrden, cero, cero);
        
        
        
        tblDctosOrdenesDetalleRepo.actualizaNota(xIdTipoOrdenNew, xIdOrdenMax, xSignoOperacion, xIdLocal, xIdTipoOrdenOld, xIdOrdenOld);

        tblDctosOrdenesRepo.retira(xIdLocal, xIdTipoOrdenOld, xIdOrdenOld);
        
        
        // actualizaEstadoInventario
        int xEstadoInventario = 4;
        int estado = 0;
        
        tblDctosOrdenesDetalleRepo.actualizaEstadoInventario(xEstadoInventario, xIdLocal, xIdTipoOrdenNew, xIdOrdenMax, estado);
		
		
		return xIdOrdenMax;
	}
	
	
	
	
	
	
	
	

}
