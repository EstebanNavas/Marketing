package com.marketing.Utilidades;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblPlusDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosService;

@Component
public class ProcesoGuardaLecturaMovil {

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
	
	
	
	 public int guardaTx(int xIdLocalUsuario,
	            Integer xIdTipoOrden,
	            int xIdPeriodo,
	            String xIdCliente,
	            double xLecturaMedidor,
	            Integer xIdUsuario,
	            double xCantidad,
	            int xIdCausa,
	            double xCantidadPedida) {
		 
		 
		 System.out.println("INGRESÓ A guardaTx");
		 //
	        int xIdOrdenMax = 0;
	        int xIdOrigenWeb = 4;
	        int xEstadoDctoOrden = 1;
	        int xEstadoNoMarcado = 0;
	        String xIdLista = "1";
	        int xIdBodega = 1;
	        int xIdTipoCargoFijo = 4;
	        String xComentario = "";
	        int xIdRazonConsumo = 4;
		 
	        double xCero = 0.0;
		 
	        Timestamp fechaHoy = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha y hora actuales
	        
	        System.out.println("xIdCliente es : " + xIdCliente);
	        System.out.println("xIdLocalUsuario es : " + xIdLocalUsuario);
	        System.out.println("xIdCausa es : " + xIdCausa);
	        System.out.println("xLecturaMedidor es : " + xLecturaMedidor);
	        
	        List<TercerosDTO2> Tercero = tblTercerosService.listaUnTerceroFachada(xIdLocalUsuario, xIdCliente);
	        
	        String xEmail = "";
	        String xIdFormaPago = "";
	        
	        int xIdEstracto = 0;
	        int xIdRuta = 0;
	        
	        

	        
	        for(TercerosDTO2 T : Tercero) {
	        	
	        	xEmail = T.getEmail();
	        	xIdFormaPago = T.getIdFormaPago().toString();
	        	xIdEstracto = T.getIdEstracto();
	        	xIdRuta = T.getIdRuta();
	        }
	        
	        
	        System.out.println("xIdEstracto es : " + xIdEstracto);
	        
	        
	        Integer idOrden = tblDctosOrdenesService.listaOrdenIdPeriodo(xIdLocalUsuario, xIdPeriodo, xIdTipoOrden,
					xIdRazonConsumo);

	        Integer xIdLogMax = tblDctosOrdenesService.listaOrdenIdPeriodoIDLOG(xIdLocalUsuario, xIdPeriodo, xIdTipoOrden,
					xIdRazonConsumo);

	        
	        
	        
			// Validamos el periodo ya tiene orden o no
			if (idOrden > 0) {

				xIdOrdenMax = idOrden;
				
			} else {

				System.out.println("idOrden esssssssssss: " + idOrden);

				List<TblLocales> Local = tblLocalesService.ObtenerLocal(xIdLocalUsuario);
				int xPeriodoFactura = 0;

				for (TblLocales L : Local) {

					xPeriodoFactura = L.getPeriodoFactura();

				}
				
				
				List <String> xIdPeriodoLista = tblDctosPeriodoService.listaPeriodo(xIdLocalUsuario, xPeriodoFactura, xIdPeriodo);
				
				//Actualizamos promedio
				tblTercerosRepo.actualizaPromedio(xIdLocalUsuario, xIdPeriodoLista, xPeriodoFactura);
				
				
				int estadoAtendido = 1; // visitaActiva
	            int estadoProgramada = 9; // visitaProgramada
	            int idEstadoVisita = 1; // Programada
	            
	            // Obtenemos el idLog Maximo
	             xIdLogMax = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
	            
	            //Actualiza visita
	            tblAgendaLogVisitasRepo.actualizaLogVisitaUsuario(estadoAtendido, xIdUsuario, estadoProgramada);
	            
	            Integer xidTipoOrden = 9;
	            
	            //Ingresa visita
	            boolean okIngreso = tblAgendaLogVisitasService.ingresaLogVisita(xIdLocalUsuario, xIdLogMax, xIdUsuario.toString(), xIdUsuario, xIdPeriodo, idEstadoVisita, estadoAtendido, xidTipoOrden);
	            
	            //Obtenemos el idOrden maximo
	            xIdOrdenMax = tblDctosOrdenesService.maximaIdOrdenIdLocal(xIdLocalUsuario) + 1;
	            
	            okIngreso = tblDctosOrdenesService.ingresaDctosOrden(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, fechaHoy, xEstadoDctoOrden, xIdUsuario.toString(), xIdUsuario, xIdOrigenWeb,
	            		xIdLogMax, xIdTipoOrden.toString(), xEmail, xIdFormaPago, xIdRazonConsumo, xIdPeriodo );
	            
			}
		 
		 // Acá lo que sigue
		 
			List<TblPlusDTO> listaEstracto = tblPlusService.listaEstractoTipoFCH(xIdLocalUsuario, xIdTipoCargoFijo, xIdEstracto);
			
			Integer idPlu = 0;
			String nombrePlu = "";
			Integer idTipo = 0;
			Double porcentajeIva = 0.0;
			Double VrGeneral = 0.0;
			String IdUVentaStr = "";
			
			System.out.println("idPlu l " + idPlu);
			System.out.println("nombrePlu l " + nombrePlu);
			System.out.println("idTipo l " + idTipo);
			System.out.println("porcentajeIva l " + porcentajeIva);
			System.out.println("VrGeneral l " + VrGeneral);
			System.out.println("IdUVentaStr 1 " + IdUVentaStr);
			
			
			
			for(TblPlusDTO L : listaEstracto) {
				
				// Acá obtener los valores que voy a usar luego
				idPlu = L.getIdPlu();
				nombrePlu = L.getNombreCategoria();
				idTipo = L.getIdTipo();
				porcentajeIva = L.getPorcentajeIva();
				VrGeneral = L.getVrGeneral();
				IdUVentaStr = L.getIdUVenta() != null ? L.getIdUVenta().toString() : "";
				
			}
			
			
			System.out.println("IdUVentaStr 2 " + IdUVentaStr);

			
			
			System.out.println("IdUVentaStr 3 " + IdUVentaStr);
		 
			Integer idEstadoTx = 0;
			Integer IdTipoTx = 0;
			Integer IdSubcuenta = 0;
			
			Integer maximoItem = tblDctosOrdenesDetalleService.maximoItem(xIdTipoOrden, xIdLocalUsuario, xIdLogMax) + 1;
		 
			tblDctosOrdenesDetalleRepo.retiraLectura(idPlu, xIdCliente, xIdLocalUsuario, xIdLogMax);
			
			System.out.println("INGRESÓ retiraLectura OK");
			System.out.println("xIdCausa antes del insert es : " + xIdCausa);
			System.out.println("xIdOrdenMax antes del insert es : " + xIdOrdenMax);
			
			tblDctosOrdenesDetalleRepo.ingresaLecturaMedidor(xIdLocalUsuario, xIdTipoOrden, xIdOrdenMax, xCantidad, nombrePlu, idPlu, idTipo, xEstadoNoMarcado, porcentajeIva,
					VrGeneral, VrGeneral, xCero, xCero, xCero, xCantidadPedida, xIdLista, IdUVentaStr, xComentario, maximoItem, maximoItem, idEstadoTx, IdTipoTx, xIdBodega, IdSubcuenta,
					xLecturaMedidor, xIdCliente, xIdEstracto, xIdRuta, xIdCausa);
		 
			System.out.println("INGRESÓ ingresaLecturaMedidor OK");
		
		return maximoItem; 
		 
	 }
}
