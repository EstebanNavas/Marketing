package com.marketing.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblDctosOrdenesDTO;
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
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblMedidoresService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoAjusteConsumoCliente;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaCredito;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaNovedad;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;

@Controller
public class CortesServiciosController {
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblDctosService TblDctosService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	TblPagosService tblPagosService;
	
	@Autowired
	TblMedidoresMacroService tblMedidoresMacroService;
	
	@Autowired
	TblTipoCausaNotaService tblTipoCausaNotaService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblPlusService tblPlusService;
	
	@Autowired
	TblMedidoresService tblMedidoresService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	ProcesoGuardaNovedad procesoGuardaNovedad;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	

	
	@GetMapping("/CortesServicios")
	public String cortesServicios(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer IdUsuario = usuario.getIdUsuario();
				
				// ----------------------------------------------------------- VALIDA INACTIVIDAD ------------------------------------------------------------
			    HttpSession session = request.getSession();
			    //Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
			    
			    @SuppressWarnings("unchecked")
				List<TblAgendaLogVisitas> UsuarioLogueado = (List<TblAgendaLogVisitas>) session.getAttribute("UsuarioLogueado");
			    
			    Integer estadoUsuario = 0;
			    

			        for (TblAgendaLogVisitas usuarioLog : UsuarioLogueado) {
			            Integer idLocalUsuario = usuarioLog.getIdLocal();
			            Integer idLogUsuario = usuarioLog.getIDLOG();
			            String sessionIdUsuario = usuarioLog.getSessionId();
			            
			            
			           estadoUsuario = controlDeInactividad.ingresa(idLocalUsuario, idLogUsuario, sessionIdUsuario);          
			        }
		    
			           if(estadoUsuario.equals(2)) {
			        	   System.out.println("USUARIO INACTIVO");
			        	   return "redirect:/";
			           }
				
				//------------------------------------------------------------------------------------------------------------------------------------------

				
				
				// ---------------------------------------------------------------- VALIDACION SUSCIPTOR SELECCIONADO --------------------------------------------------------
				
				int xIdTipoTerceroCliente = 1;
		        int xIdTipoOrden = 7;
		        int xIdTipoOrdenProceso = xIdTipoOrden + 50 ;

		        //
		        int estadoActivo = 9;
				
				 // Obtener la fecha actual
		        LocalDate fechaActual = LocalDate.now();

		        // Formatear la fecha como un String
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		        String strFechaVisita = fechaActual.format(formatter);
		        
		        System.out.println("strFechaVisita  es" + strFechaVisita);
		        

				
				String idCliente = tblAgendaLogVisitasService.seleccionaVisitaEstadoFecha(estadoActivo, strFechaVisita, IdUsuario);
				System.out.println("idCliente desde /Factura " + idCliente);
				
				if(idCliente == null) {
					
					return "Cliente/Selecciona";
					
				}else {
					
					System.out.println("idCliente en el if es" + idCliente);
					
					List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), idCliente, xIdTipoTerceroCliente);
					
					for(TblTerceros L : listaTercero) {
						
						model.addAttribute("xEstado", L.getEstado());
						model.addAttribute("xNuid", L.getIdCliente());
						model.addAttribute("xNombreTercero", L.getNombreTercero());
						model.addAttribute("xDireccion", L.getDireccionTercero() );
						
					}
					
					
					int idTipoTercero = 1;
					
					
					List<TblTerceros> InformacionTercero =  tblTercerosService.ObtenerInformacionTercero(usuario.getIdLocal(), idCliente, idTipoTercero);
					
					
					for(TblTerceros tercero : InformacionTercero) {
				    	
				    	model.addAttribute("xEstadoCorte", tercero.getEstadoCorte());
				    	model.addAttribute("xEstadoMedidor", tercero.getEstadoMedidor());
				    	
				    	System.out.println("xEstadoMedidor" + tercero.getEstadoMedidor());
				    }
					
					
					ArrayList<TblTipoCausaNota> EstadoMedidor = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(4);
					model.addAttribute("xEstadoMedidor", EstadoMedidor );
					
					
					ArrayList<TblTipoCausaNota> EstadoCorte = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(5);
					model.addAttribute("EstadoCorte", EstadoCorte);
					
				}
				
				// ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				Integer idPeriodo = 0;
				Integer idTipoOrden = 9;
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					idPeriodo = P.getIdPeriodo();
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
				
				}
				
		
		return "Administrador/CorteServicio";
	}
	
	
	
	
	
	@PostMapping("/ModificarCorteservicio")
	public ResponseEntity<Map<String, String>> ModificarCorteservicio(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ModificarCorteservicio");
	    
	    
	    Integer IdUsuario = usuario.getIdUsuario();
	    Integer idLocal = usuario.getIdLocal();

	    // Obtenemos los datos del JSON recibido
	    String  xIdCliente =  (String) requestBody.get("idCliente");
	    
	    String  estadoMedidor =  (String) requestBody.get("estadoMedidor");
	    Integer xEstadoMedidor = Integer.parseInt(estadoMedidor);
	    
	    
	    String  estadoCorte =  (String) requestBody.get("estadoCorte");
	    Integer xEstadoCorte = Integer.parseInt(estadoCorte);
	    
	    
	    int idTipoTerceroCliente = 1;
	    int xEstadoActivo = 9;
        int xEstadoAtendido = 1;
        int xIdTipoOrdenPedido = 9;
	  
        
	    tblTercerosRepo.actualizaEstadoMedidorCorte(xEstadoMedidor , xEstadoCorte, xIdCliente, idLocal, idTipoTerceroCliente);
	    
	    tblAgendaLogVisitasRepo.actualizaLogVisitaUsuario(xEstadoAtendido, IdUsuario, xEstadoActivo);
	    
	    
	    
	 // Obtenemos el periodo activo
		List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
		
		Integer idPeriodo = 0;
		Integer idTipoOrden = 9;
		
		for(TblDctosPeriodo P : PeriodoActivo) {
			
			idPeriodo = P.getIdPeriodo();
			model.addAttribute("xIdPeriodo", P.getIdPeriodo());
			model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
		
		}
		
		 // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
        System.out.println("strFechaVisita  es" + strFechaVisita);
	    
	    
	    
	    
	    int xIdLogMAX = tblAgendaLogVisitasService.findMaxIDLOG() + 1;

	    
	    tblAgendaLogVisitasRepo.ingresaLogVisita(xIdLogMAX, xIdCliente, IdUsuario, idLocal, idLocal, idPeriodo, strFechaVisita,
				xEstadoAtendido, xEstadoActivo, xIdTipoOrdenPedido, strFechaVisita);
	    
	    
	    
	     //--
        double xCantidad = 1.0;
        Double xNumeroCuotas = 1.0;
        double xVrVentaUnitario = 1.0;
        Double xVrCredito = 1.0;
        int xItem = 1;
        String strIdReferencia = "59";
        int xIdTipoOrdenNovedad = 17;
        String xObservacion = "";
	    
	    procesoGuardaNovedad.guarda(xIdLogMAX, strIdReferencia, xCantidad, xVrVentaUnitario, xItem, xIdTipoOrdenNovedad, IdUsuario, 
	    		                    idLocal, xIdCliente, xVrCredito, xNumeroCuotas, xObservacion);
	    
	    
	    
	    tblAgendaLogVisitasRepo.actualizaLogVisitaUsuario(xEstadoAtendido, IdUsuario, xEstadoActivo);
	    
	    

        Map<String, String> response = new HashMap<>();
        
        response.put("message", "OK");

        return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
