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
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosService;

@Component
public class ProcesoGuardaNovedad {
	
	
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
            double xVrCredito,
            double xNumeroCuotas,
            String xObservacion) {
		
		
		//
        int xIdOrdenMax = 0;
        int xIdOrigenWeb = 4;
        int xEstadoDctoOrden = 1;
        int xEstadoNoMarcado = 0;
        String xIdLista = "1";
        int xIdBodega = 1;
        int xIdRuta = 0;
        int xIdEstracto = 0;
        int xItemPadre = 1;
        String xComentario = "NN";
        String xIdResponsable = "0";
        String xIdClasificacion = "0";
        
        
        
        Integer xIdPluint = Integer.parseInt(xIdPlu);
        
        
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
		
        
        List<TblDctosOrdenesDTO> listaDctos = tblDctosOrdenesService.listaDctoOrdenIdLog(xIdLocalUsuario,  xIdLog);
		
		int IdOrden = 0;
		Double DescuentoComercial = 0.0;
		
		for(TblDctosOrdenesDTO Dcto : listaDctos) {
			
			IdOrden = Dcto.getIdOrden();
			DescuentoComercial = Dcto.getDescuentoComercial();
		}
		
		
		
		if(IdOrden > 0) {
			
			xIdOrdenMax = IdOrden;
			
		}else {
			
			
			List<TercerosDTO2> listaTercero =  tblTercerosService.listaUnTerceroFachada(xIdLocalUsuario, xIdTercero);
            
       	    String xIdFormaPago = "";
            String xEmail = "";
            
            
            for(TercerosDTO2 L : listaTercero) {
            	
            	xIdFormaPago = L.getIdFormaPago().toString();
            	xEmail = L.getEmail();
            	xIdRuta = L.getIdRuta();
            	xIdEstracto = L.getIdEstracto();
            }
            
			
            xIdOrdenMax  = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocalUsuario) + 1;
			
			
            int xCero = 0;
 	        int xUno = 1;
 	        String xNada = "";
 	        Double cero = 0.0;
 	        

// 	       (int idLocal, int IdTipoOrden, int IdOrden, String strFechaVisita, int Estado, String IdCliente, int IdUsuario, int IdOrigen, int IdLog, String FechaEntregaSqlServer, 
// 				  String TipoDcto, String Email, String FormaPago, int DiasHistoria, int DiasInventario, String Observacion, int IdRazon, int IdPeriodo, Double VrTotalDiferir, Double CuotaDiferir, 
// 				  Double PorcentajeInteresADiferir, Double VrInteresADiferir, int OrdenCompra)
 	        
 	        tblDctosOrdenesRepo.ingresaDctosOrden(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, strFechaVisita, xEstadoDctoOrden, xIdTercero, xIdUsuario, xIdOrigenWeb, xIdLog, strFechaVisita,
 	        		xIdTipoOrden.toString() , xEmail, xIdFormaPago, xCero, xCero, xObservacion, xCero, xCero, xVrCredito, xNumeroCuotas, cero, cero, xCero);
	
			
		}
		
		
		
		List<TblPlusDTO> listaPlu =  tblPlusService.listaUnPluFCH(xIdPlu, xIdLocalUsuario);
        
        String NombreCategoria = "";
        String NombrePlu = "";
        int idTipo = 0;
        Double PorcentajeIva = 0.0;
        Double VrCosto = 0.0;
        String IdUVentaStr = "";
        Double VrGeneral = 0.0;
        
        for(TblPlusDTO lista : listaPlu) {
        	
        	NombreCategoria = lista.getNombreCategoria();
        	NombrePlu = lista.getNombrePlu();
        	idTipo = lista.getIdTipo();
        	PorcentajeIva = lista.getPorcentajeIva();
        	VrCosto = lista.getVrCosto();
        	VrGeneral = lista.getVrGeneral();
        	
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
		
        
      // Ingresa Historia
		
        Double cero = 0.0;
        int xCero = 0;
        
        
//        (int idLocal, int IdTipoOrden, int idOrden, Double Cantidad, String NombrePlu, int IdPlu, int IdTipo, int Estado, Double PorcentajeIva,
//				  Double VrVentaUnitario, Double VrVentaOriginal, Double VrCosto, Double VrDsctoPie, Double PorcentajeDscto, Double CantidadPedida, String StrIdLista, String NombreUnidadMedida,
//				  String Comentario, int Item, int ItemPadre, int IdEstadoTx, int IdTipoTx, int IdBodega, int IdSubcuenta, Double LecturaMedidor, String IdCliente, int IdEstracto,
//				  int IdRuta, int IdNovedadLectura)

        
        // Ingresa Historia
        tblDctosOrdenesDetalleRepo.ingresaLecturaMedidor(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xCantidad, nombreCompleto, xIdPluint, idTipo, xEstadoNoMarcado, PorcentajeIva,
        		VrGeneral, VrGeneral, VrCosto, DescuentoComercial, cero, cero, xIdLista, IdUVentaStr, 
        		xComentario, xItem, xItemPadre, xCero, xCero, xIdBodega, xCero, cero, xIdTercero, xIdEstracto, xIdRuta, xCero);
		
		
		
        tblDctosOrdenesDetalleRepo.actualizaDescuentoPie(DescuentoComercial, xIdLocalUsuario, xIdTipoOrden, xIdLog);
		
		
		return maximoItem;
		
	}
	
	
	
	
	

}
