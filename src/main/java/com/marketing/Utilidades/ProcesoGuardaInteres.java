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
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosService;

@Component
public class ProcesoGuardaInteres {
	
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
	
	 public int guarda(int xIdLog,
	            String xIdPlu,
	            double xCantidad,
	            double xVrVentaUnitario,
	            int xItem,
	            Integer xIdTipoOrden,
	            int xIdUsuario,
	            int xIdLocalUsuario,
	            String xIdTercero,
	            Integer xIdOrdenMax,
	            String xObservacion,
	            int xIdPeriodo) {
		 
		 //

	        int xEstadoNoMarcado = 0;
	        String xIdLista = "1";
	        int xIdBodega = 1;
	        int xIdRuta = 0;
	        int xIdEstracto = 0;
	        int xItemPadre = 1;
	        int xVrVentaUnitarioInt = (int)(xVrVentaUnitario);
	        
	        Integer xIdPluint = Integer.parseInt(xIdPlu);
	        
	        
	        // Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        String strFechaVisita = fechaActual.format(formatter);
	        
	        List<TblDctosOrdenesDTO> listaDcto = tblDctosOrdenesService.listaDctoOrdenIdLog(xIdLocalUsuario,  xIdLog);

	        Double DescuentoComercial = 0.0;
	        
	        for(TblDctosOrdenesDTO lista : listaDcto) {
	        	
	        	DescuentoComercial = lista.getDescuentoComercial();
	        }
	        
	        
	        
	        
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
	        
	        String nombreCompleto = NombreCategoria + "" + NombrePlu;
	        
	        
	        // maximoItem
	        Integer maximoItem =  tblDctosOrdenesDetalleService.maximoItem(xIdTipoOrden, xIdLocalUsuario, xIdLog) + 1;
	        
	        
	        Double cero = 0.0;
	        int xCero = 0;
	        
	        
	        

	        
	        // Ingresa Historia
	        tblDctosOrdenesDetalleRepo.ingresaLecturaMedidor(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xCantidad, nombreCompleto, xIdPluint, idTipo, xEstadoNoMarcado, PorcentajeIva,
	        		(double)(xVrVentaUnitarioInt), (double)(xVrVentaUnitarioInt), VrCosto, DescuentoComercial, cero, cero, xIdLista, IdUVentaStr, 
	        		xObservacion, xItem, xItemPadre, xCero, xCero, xIdBodega, xCero, cero, xIdTercero, xIdEstracto, xIdRuta, xCero);
	        
	        System.out.println("ingresaLecturaMedidor OK" );
	        
	        tblDctosOrdenesDetalleRepo.actualizaDescuentoPie(DescuentoComercial, xIdLocalUsuario, xIdTipoOrden, xIdLog);
	        System.out.println("actualizaDescuentoPie OK" );
	        
		 
		 return xIdOrdenMax;
	 }

}
