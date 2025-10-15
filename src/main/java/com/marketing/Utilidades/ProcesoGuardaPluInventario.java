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
public class ProcesoGuardaPluInventario {
	
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
	
	@Autowired
	TblDctosService tblDctosService;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	
	
	public int guarda(int xIdLog,
            String xIdPlu,
            double xCantidad,
            double xVrVentaUnitario,
            int xItemPadre,
            Integer xIdTipoOrden,
            int xIdUsuario,
            int xIdLocalUsuario,
            String xIdTercero,
            String xComentario,
            String xIdResponsable,
            int xIdClasificacion,
            int xIdPeriodo,
            Double xVrVenta) {
		
		
		//
        int xIdOrdenMax = 0;
        int xIdOrigenWeb = 4;
        int xEstadoDctoOrden = 1;
        int xEstado = 4;
        String xIdLista = "1";
        int xIdBodega = 1;
		
        Integer xIdPluint = Integer.parseInt(xIdPlu);
		
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
		
		
        List<TercerosDTO2> tercero =   tblTercerosService.listaUnTerceroFachada(xIdLocalUsuario, xIdTercero);
		
        String xEmail = "";
        String xIdFormaPago = "";
        int xIdRuta = 0;
        int xIdEstracto = 0;
        String xNombreTercero = "";
        
        for(TercerosDTO2  T : tercero ) {
        	
        	xEmail = T.getEmail();
        	xIdFormaPago = T.getIdFormaPago().toString();
        	xIdRuta = T.getIdRuta();
        	xIdEstracto = T.getIdEstracto();
        	xNombreTercero = T.getNombreTercero();
        }
        
        
        
        List<TblDctosOrdenesDTO> OrdenIdLog  = tblDctosOrdenesService.listaDctoOrdenIdLog(xIdLocalUsuario, xIdLog);
        
        Integer idOrden = 0;
        Double DescuentoComercial = 0.0;
        
        for(TblDctosOrdenesDTO orden : OrdenIdLog) {
        	
        	idOrden = orden.getIdOrden();
        	DescuentoComercial = orden.getDescuentoComercial();
        }
        
        
        
        if (idOrden > 0) {

            // SI existeOrden
            xIdOrdenMax = idOrden;

        } else {
        
        	// NO existeOrden y se igual idLocal = idLocalInicial
        	xIdOrdenMax = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocalUsuario) + 1;
        	
        	int xCero = 0;
	        int xUno = 1;
	        String xNada = "";
	        Double cero = 0.0;
	        
	        tblDctosOrdenesRepo.ingresaDctosOrden(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, strFechaVisita, xEstadoDctoOrden, xIdTercero, xIdUsuario, xIdOrigenWeb, xIdLog, strFechaVisita,
	        		xIdTipoOrden.toString() , xEmail, xIdFormaPago, xCero, xCero, xNada, xCero, xIdPeriodo, cero, cero, cero, cero, xCero);
	        
        
        }
        
        System.out.println("xIdPlu es : " + xIdPlu);
        List<TblPlusDTO> listaPlu =  tblPlusService.listaUnPluFCH(xIdPlu, xIdLocalUsuario);
        
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
        
        System.out.println("IdUVentaStr es : " + IdUVentaStr);
        
        String nombreCompleto = NombreCategoria + " " + NombrePlu;
        
        // Igual Encabezado = Detalle en IdLocal
        
        System.out.println("xIdTipoOrden es : " + xIdTipoOrden);
        System.out.println("xIdLog es : " + xIdLog);
        
        // maximoItem
        Integer maximoItem =  tblDctosOrdenesDetalleService.maximoItem(xIdTipoOrden, xIdLocalUsuario, xIdLog) + 1;
        
        // Valida  padre item = itemPadre o Complemento # diferentes
        if (xItemPadre == 0) {

            xItemPadre = maximoItem;

        }
        
        Double cero = 0.0;
        int xCero = 0;
        
        System.out.println("Ante sde ingresar lectura");
        
        // Ingresa Historia
        tblDctosOrdenesDetalleRepo.ingresaLecturaMedidor(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xCantidad, nombreCompleto, xIdPluint, idTipo, xEstado, PorcentajeIva,
        		xVrVentaUnitario, xVrVentaUnitario, VrCosto, DescuentoComercial, cero, cero, xIdLista, IdUVentaStr, 
        		xComentario, maximoItem, xItemPadre, xCero, xCero, xIdBodega, xCero, cero, xIdTercero, xIdEstracto, xIdRuta, xCero);
        System.out.println("ingresaPluDetalle");
        
        Integer xIndicador = 1;
        Double xVrCero = 0.0;
        int ceroInt = 0;
        
        
        Integer xIdDctoMax = 0;
        
        Integer idDctoExist = tblDctosService.ObtenerIdDcto(xIdLocalUsuario, xIdOrdenMax, xIdTercero);
        
        
        if (idDctoExist != null) {

            // SI existeOrden
        	xIdDctoMax = idDctoExist;

        } else {
        
        	// NO existeIdDcto -> se ingresa
        	xIdDctoMax = tblDctosService.maximoDctoLocalIndicador(xIdLocalUsuario, xIdTipoOrden, xIndicador) + 1;
        	
        	tblDctosRepo.ingresaDcto(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xIdDctoMax, xIndicador, strFechaVisita, xVrVenta, ceroInt, 1, xVrCero, 
            		2, ceroInt, xVrCero, ceroInt, ceroInt, xNombreTercero, xIdUsuario, xIdTercero, ceroInt, ceroInt,
            		ceroInt, xIdDctoMax.toString(), strFechaVisita, ceroInt, ceroInt, xVrCero, xIdLocalUsuario, ceroInt, ceroInt,
            		xIdPeriodo, xIdUsuario, xVrCero, xVrCero, ceroInt, ceroInt, ceroInt);
	        
        
        }
        

		
		return xIdOrdenMax;
	}

}
