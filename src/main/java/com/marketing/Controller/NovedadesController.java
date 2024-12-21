package com.marketing.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblPlusDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
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
import com.marketing.Utilidades.ProcesoGuardaPluOrden;

@Controller
public class NovedadesController {
	
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
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	ProcesoCreaLecturaMovil procesoCreaLecturaMovil;
	
	@Autowired
	ProcesoGuardaLecturaMovil procesoGuardaLecturaMovil;
	
	@Autowired
	ProcesoGuardaPluOrden procesoGuardaPluOrden;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	ProcesoGuardaCredito procesoGuardaCredito;
	
	@Autowired
	ProcesoAjusteConsumoCliente procesoAjusteConsumoCliente;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	
	@GetMapping("/Novedades")
	public String novedades(HttpServletRequest request,Model model) {
		
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
						
						List<TblDctosOrdenesDTO> CuentaFacturado =  tblDctosOrdenesService.PeriodoFacturado(idLocal, idTipoOrden, idPeriodo);
						
						Integer Cuenta = 0;
						
						for(TblDctosOrdenesDTO C : CuentaFacturado) {
							
							Cuenta = C.getCuenta();
						}
						
										
						if(Cuenta != 0) {
							
							model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodo + " YA FACTURADO NO PERMITE INGRESO DE NOVEDADES");
			            	model.addAttribute("url", "./menuPrincipal");
			        		return "defaultErrorSistema";
						}      

				
				
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
					
					session.removeAttribute("pantalla"); //Se remueve de la session el valor de pantalla					
					String pantalla = "Novedades";
					
					session.setAttribute("pantalla", pantalla); //Se le asigna a la session el valor de pantalla 
					
					return "Cliente/Selecciona";
					
				}else {
					
					System.out.println("idCliente en el if es" + idCliente);
					
					List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), idCliente, xIdTipoTerceroCliente);
					
					for(TblTerceros L : listaTercero) {
						
						model.addAttribute("xEstado", L.getEstado());
						model.addAttribute("xNuid", L.getIdCliente());
						model.addAttribute("xNombreTercero", L.getNombreTercero());
						model.addAttribute("xRuta", L.getIdRuta());
						
					}
					
				}
				
				
				
				//Obtenemos la lista de plus
				List<TblPlusDTO> plusNovedad = tblPlusService.listaPluNovedad(idLocal);
				model.addAttribute("xPlusNovedad", plusNovedad);
				
				
		
		return "Cliente/Novedades";
	}
	
	
	
	@PostMapping("/GuardarNovedad-Post")
	public ResponseEntity<Map<String, String>> GuardarNovedad(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /GuardarNovedad-Post");
	    
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("arrVrVentaUnitario");
	    session.removeAttribute("arrIdReferencia");

	    // Obtenemos los datos del JSON recibido
    	// Obtener los valores de los arrays
	    @SuppressWarnings("unchecked")
	    List<String> xchkVrVentaUnitarioArr = (List<String>) requestBody.get("xchkVrVentaUnitarioArr");
	    System.out.println("xchkVrVentaUnitarioArr es  " + xchkVrVentaUnitarioArr);
	    
	    @SuppressWarnings("unchecked")
	    List<String> xidPluArr = (List<String>) requestBody.get("xidPluArr");
	    
	    
	    
	    String[] arrVrVentaUnitario = xchkVrVentaUnitarioArr.toArray(new String[0]);
	    String[] arrIdReferencia = xidPluArr.toArray(new String[0]);
	    String xCantidadStr = "1";
	    
	    System.out.println("arrVrVentaUnitario en /Confirmar-Post " + arrVrVentaUnitario);
	    System.out.println("arrIdReferencia en /Confirmar-Post " + arrIdReferencia);
	    
	    //
        int estadoActivo = 9;
		
		 // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
    	// Obtenemos el periodo activo
		List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(usuario.getIdLocal());
		
		Integer idPeriodo = 0;

		
		for(TblDctosPeriodo P : PeriodoActivo) {			
			idPeriodo = P.getIdPeriodo();
		
		}
	    
	    
	    
	    String idCliente = tblAgendaLogVisitasService.seleccionaVisitaEstadoFecha(estadoActivo, strFechaVisita, IdUsuario);
	    
	    Integer idLog = tblAgendaLogVisitasService.seleccionaVisitaEstadoFechaIDLOG(estadoActivo, strFechaVisita, IdUsuario);
	    
	    
	    int xIdTipoOrdenPedido = 9;
        int xIdTipoOrdenCotizacion = xIdTipoOrdenPedido + 50;
	    
	    
	    for (int indice = 0; indice < arrVrVentaUnitario.length; indice++) {

            //
            if (arrVrVentaUnitario[indice].length() == 0) {
                continue;
            }

            
            double xCantidad = Double.parseDouble(xCantidadStr); 
            double xVrVentaUnitario = Double.parseDouble(arrVrVentaUnitario[indice]);
                    

            //valida el idTercero sea el mismo para todos
            String strIdReferencia = arrIdReferencia[indice];
            int xItemPadre = 0;
            String xComentario = "ninguno";
            String xIdResponsable = "0";
            int xIdClasificacion = 0;

            //
            int maximoItem = procesoGuardaPluOrden.guarda(idLog,
                    strIdReferencia,
                    xCantidad,
                    xVrVentaUnitario,
                    xItemPadre,
                    xIdTipoOrdenCotizacion,
                    IdUsuario,
                    usuario.getIdLocal(),
                    idCliente,
                    xComentario,
                    xIdResponsable,
                    xIdClasificacion,
                    idPeriodo);

        }
	    
	    
	    Integer IdTipoTercero = 1;
	    
	    
	    List<TercerosDTO2>  listaTercero =  tblTercerosService.listaTerceroFCH(usuario.getIdLocal(), IdTipoTercero, idCliente);
	    
	    tblDctosOrdenesRepo.actualizaPeriodo(idPeriodo, usuario.getIdLocal(), xIdTipoOrdenCotizacion, idLog);
	    
	    List<TblDctosOrdenesDTO> ListaDctoOrden = tblDctosOrdenesService.listaDctoOrdenIdLogIdTipoOrden(usuario.getIdLocal(), xIdTipoOrdenCotizacion, idLog);
	    
	    
	    int estadoVisita = 9;
        int estadoSuspendido = 8; 
        
        tblAgendaLogVisitasRepo.actualizaVisita(estadoSuspendido, strFechaVisita, IdUsuario, estadoVisita);
	    

	    
	    Map<String, String> response = new HashMap<>();
        response.put("message", "OK");

        return ResponseEntity.ok(response);
	}

}
