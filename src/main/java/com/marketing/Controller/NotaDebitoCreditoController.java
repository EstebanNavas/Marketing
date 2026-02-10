package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.marketing.MailjetTask;
import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
import com.marketing.Projection.TblDctosPeriodoDTO;
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
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.ProcesoIngresoNota;
import com.marketing.Utilidades.UtilidadesIP;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class NotaDebitoCreditoController {
	
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
	ProcesoIngresoNota procesoIngresoNota;
	
	@Autowired
	MailjetTask mailjetTask;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	
	@GetMapping("/NotaDebitoCredito")
	public String notaDebitoCredito (HttpServletRequest request,Model model) {
		
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

				// Obtenemos la lista de periodos 
				List <TblDctosPeriodo> Periodos = tblDctosPeriodoService.ListaTotalPeriodos(idLocal);
				model.addAttribute("xPeriodos", Periodos);
				
				// ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				//Integer idPeriodo = 0;
				Integer idPeriodoActual = 0;
				Integer idTipoOrden = 9;
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					idPeriodoActual = P.getIdPeriodo();
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
				
				}
				
				//List<TblDctosOrdenesDTO> CuentaFacturado =  tblDctosOrdenesService.PeriodoFacturado(idLocal, idTipoOrden, idPeriodo);
				
				// Obtenemos el periodo POSTERIOR
				//Integer idPeriodoPosterior = tblDctosPeriodoService.listaPosteriorFCH(idPeriodoActual, idLocal);
				//System.out.println("idPeriodoPosterior es " + idPeriodoPosterior);
				
				// Integer Cuenta = 0;
				
//				for(TblDctosOrdenesDTO C : CuentaFacturado) {
//					
//					Cuenta = C.getCuenta();
//				}
				
				
				// idPeriodoActual
				List<TblDctosOrdenesDTO> CuentaFacturadoActual =  tblDctosOrdenesService.PeriodoFacturado(idLocal, idTipoOrden, idPeriodoActual);
				
				Integer CuentaPeriodoActual = 0;
				
				for(TblDctosOrdenesDTO C : CuentaFacturadoActual) {
					CuentaPeriodoActual = C.getCuenta();
				}   
				System.out.println("CuentaPeriodoActual es " + CuentaPeriodoActual); 
				
				
				
//				// idPeriodoPosterior
//				List<TblDctosOrdenesDTO> CuentaFacturadoPosterior =  tblDctosOrdenesService.PeriodoFacturado(idLocal, idTipoOrden, idPeriodoPosterior);
//				
//				Integer CuentaPeriodoPosterior = 0;
//				
//				for(TblDctosOrdenesDTO C : CuentaFacturadoPosterior) {
//					CuentaPeriodoPosterior = C.getCuenta();
//				} 
//				System.out.println("CuentaPeriodoPosterior es " + CuentaPeriodoPosterior); 
//				
				
				
				//Valida si el periodo actual ya fue facturado
                if(CuentaPeriodoActual == 0) {
	
	                model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodoActual + " NO HA SIDO FACTURADO");
	                model.addAttribute("url", "./menuPrincipal");
	                return "defaultErrorSistema";
                }
				
                Integer idPeriodoPosterior = tblDctosPeriodoService.listaPosteriorFCH(idPeriodoActual, idLocal);
				System.out.println("idPeriodoPosterior es " + idPeriodoPosterior); 
                
					if(idPeriodoPosterior != null) {
					
					// idPeriodoPosterior
					List<TblDctosOrdenesDTO> CuentaFacturadoPosterior =  tblDctosOrdenesService.PeriodoFacturado(idLocal, idTipoOrden, idPeriodoPosterior);
					
					System.out.println("CuentaFacturadoPosterior es " + CuentaFacturadoPosterior); 
					
					Integer CuentaPeriodoPosterior = 0;
					
					for(TblDctosOrdenesDTO C : CuentaFacturadoPosterior) {
						
						CuentaPeriodoPosterior = C.getCuenta();
					} 
					
					
					// Validamos Si el periodo posterior ya esta facturado
					if(CuentaPeriodoPosterior != 0 ) {
						
						model.addAttribute("error", "NO SE PERMITEN NOTAS EN EL PERIODO " + idPeriodoActual + " DEBIDO A QUE EL PERIODO " + idPeriodoPosterior + " YA ESTA FACTURADO" );
		            	model.addAttribute("url", "./menuPrincipal");
		        		return "defaultErrorSistema";
					}
					}
//				if(Cuenta == 0) {
//					
//					model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodo + " NO HA SIDO FACTURADO");
//	            	model.addAttribute("url", "./menuPrincipal");
//	        		return "defaultErrorSistema";
//				}
				// -------------------------------------------------------------------------------------------------------------------------------------------------
				
				
				// ---------------------------------------------------------------- VALIDACION SUSCIPTOR SELECCIONADO --------------------------------------------------------
				
				int xIdTipoTerceroCliente = 1;
		        int xIdTipoOrden = 9;

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
					String pantalla = "NotaDebitoCredito";
					
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
					
					
					//--------- Logica de /TraerDcto
					
					List<Integer> idtipoorden = new ArrayList<>();

			        // Agregar los valores 9 y 29 a la lista
					idtipoorden.add(9);
					//idtipoorden.add(29);
				    
					//Obtenemos el idDcto del periodo activo y el idCliente
					List<TblDctosDTO> Documento = TblDctosService.ObtenerIdDctoxPeriodo(usuario.getIdLocal(), idtipoorden, idCliente, idPeriodoActual);
					System.out.println("El Documento en TraerDcto es: " + Documento);
					
					Integer idOrden = 0;
					Integer idDcto = 0;
					
					for(TblDctosDTO Dcto : Documento) {
						
						model.addAttribute("xIdDcto", Dcto.getIdDcto().intValue());
						model.addAttribute("xFechaFactura", Dcto.getFechaDcto().substring(0, 10));
					
						idOrden = Dcto.getIdOrden();
						idDcto = Dcto.getIdDcto().intValue();
					}
					

					List<TblDctosOrdenesDetalleDTO> totalFatura = tblDctosOrdenesDetalleService.ObtenerValorFactura(usuario.getIdLocal(), idtipoorden, idOrden);
					
					for(TblDctosOrdenesDetalleDTO T : totalFatura) {
						
						model.addAttribute("xValorFactura", T.getTotalFactura().intValue());
						
					}
					
					
					Integer CantArticulos = tblDctosOrdenesDetalleService.ObtenerCantArticulos(usuario.getIdLocal(), idtipoorden, idOrden);
					model.addAttribute("xCantArticulos", CantArticulos);
					
					
					//--------- Logica de /TraerCotizacion
					
					Integer xIndicador = 1;
					
					Integer idlinea = 1;
					
					//Obtenemos el idOrden
					Integer xIdOrden = TblDctosService.ObtenerIdOrden(usuario.getIdLocal(), xIdTipoOrden, idDcto);
					
					
					//Obtenemos los item de la factura
					List<TblDctosOrdenesDetalleDTO>  listaOrden = tblDctosOrdenesDetalleService.listaDevolucionOrden(usuario.getIdLocal(), xIdTipoOrden, xIdOrden, xIndicador);
					model.addAttribute("xListaOrden", listaOrden);
					
					//Obtenemos el idEstracto
					Integer idEstrato = tblTercerosService.ObteneridEstrato(usuario.getIdLocal(), idCliente);
					
					//Obtenemos la lista de PLU
					List<TblPlusDTO> listaPlu = tblPlusService.listaPluNota(usuario.getIdLocal(), idlinea, idEstrato);
					model.addAttribute("xlListaPlu", listaPlu);
					
					model.addAttribute("xIdDcto", idDcto);
					
	
					
					
					
					
				}

				
		
		return "Cliente/DetalleCotizacion";
	}
	
	
	
	@PostMapping("/TraerDcto-Post")
	public ModelAndView TraerDctoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerPeriodo-Post");

	    // Obtenemos los datos del JSON recibido
	    String idCliente = (String) requestBody.get("idCliente");
	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerDcto?idCliente=" + idCliente);
	    return modelAndView;
	}
	
	
	
	
	@GetMapping("/TraerDcto")
	public String TraerDcto(@RequestParam(name = "idCliente", required = false) String idCliente, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerDcto con idCliente: " + idCliente);
		
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

		   
			// Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(usuario.getIdLocal());
			
			Integer idPeriodo = 0;
			Integer idTipoOrden = 9;
			
			int xIdTipoTerceroCliente = 1;
			
			for(TblDctosPeriodo P : PeriodoActivo) {
				
				idPeriodo = P.getIdPeriodo();
				model.addAttribute("xIdPeriodo", P.getIdPeriodo());
				model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
			
			}
			

			
			if(idCliente != null) {
				
				System.out.println("idCliente en el if es" + idCliente);
				
				List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), idCliente, xIdTipoTerceroCliente);
				
				for(TblTerceros L : listaTercero) {
					
					model.addAttribute("xEstado", L.getEstado());
					model.addAttribute("xNuid", L.getIdCliente());
					model.addAttribute("xNombreTercero", L.getNombreTercero());
					model.addAttribute("xRuta", L.getIdRuta());
					
				}
				
			}
			
			
			
			
			List<Integer> idtipoorden = new ArrayList<>();

	        // Agregar los valores 9 y 29 a la lista
			idtipoorden.add(9);
			//idtipoorden.add(29);
		    
			//Obtenemos el idDcto del periodo activo y el idCliente
			List<TblDctosDTO> Documento = TblDctosService.ObtenerIdDctoxPeriodo(usuario.getIdLocal(), idtipoorden, idCliente, idPeriodo);
			System.out.println("El Documento en TraerDcto es: " + Documento);
			
			Integer idOrden = 0;
			
			for(TblDctosDTO Dcto : Documento) {
				
				model.addAttribute("xIdDcto", Dcto.getIdDcto().intValue());
				model.addAttribute("xFechaFactura", Dcto.getFechaDcto().substring(0, 10));
			
				idOrden = Dcto.getIdOrden();
			}
			

			List<TblDctosOrdenesDetalleDTO> totalFatura = tblDctosOrdenesDetalleService.ObtenerValorFactura(usuario.getIdLocal(), idtipoorden, idOrden);
			
			for(TblDctosOrdenesDetalleDTO T : totalFatura) {
				
				model.addAttribute("xValorFactura", T.getTotalFactura().intValue());
				
			}
			
			
			Integer CantArticulos = tblDctosOrdenesDetalleService.ObtenerCantArticulos(usuario.getIdLocal(), idtipoorden, idOrden);
			model.addAttribute("xCantArticulos", CantArticulos);
	        


			
			return "Cliente/DocumentoNotaDebitoCredito";

	}
	
	
	
	
	
	
	
	@PostMapping("/TraerCotizacion-Post")
	public ModelAndView TraerCotizacionPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerPeriodo-Post");

	    // Obtenemos los datos del JSON recibido
	    String idDcto = (String) requestBody.get("idDcto");
	    String idCliente = (String) requestBody.get("idCliente");
	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerCotizacion?idDcto=" + idDcto + "&idCliente=" + idCliente);
	    return modelAndView;
	}
	
	
	
	
	@GetMapping("/TraerCotizacion")
	public String TraerCotizacion(@RequestParam(name = "idDcto", required = false) String idDcto,
				@RequestParam(name = "idCliente", required = false) String idCliente,
			HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerCotizacion con idDcto: " + idDcto);
		System.out.println("Entró a /TraerCotizacion con idCliente: " + idCliente);
		
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

		    Integer idDctoInt = Integer.parseInt(idDcto);
		   
			// Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(usuario.getIdLocal());
			
			Integer idPeriodo = 0;
			Integer idTipoOrden = 9;
			
			int xIdTipoTerceroCliente = 1;
			
			for(TblDctosPeriodo P : PeriodoActivo) {
				
				idPeriodo = P.getIdPeriodo();
				model.addAttribute("xIdPeriodo", P.getIdPeriodo());
				model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
			
			}
			
			
			Integer idtipoorden = 9 ;
			Integer xIndicador = 1;
			
			Integer idlinea = 1;
			
			//Obtenemos el idOrden
			Integer idOrden = TblDctosService.ObtenerIdOrden(usuario.getIdLocal(), idtipoorden, idDctoInt);
			
			
			//Obtenemos los item de la factura
			List<TblDctosOrdenesDetalleDTO>  listaOrden = tblDctosOrdenesDetalleService.listaDevolucionOrden(usuario.getIdLocal(), idtipoorden, idOrden, xIndicador);
			model.addAttribute("xListaOrden", listaOrden);
			
			//Obtenemos el idEstracto
			Integer idEstrato = tblTercerosService.ObteneridEstrato(usuario.getIdLocal(), idCliente);
			
			//Obtenemos la lista de PLU
			List<TblPlusDTO> listaPlu = tblPlusService.listaPluNota(usuario.getIdLocal(), idlinea, idEstrato);
			model.addAttribute("xlListaPlu", listaPlu);
			
			model.addAttribute("xIdDcto", idDctoInt);

			
			return "Cliente/DetalleCotizacion";

	}
	
	
	
	
	@PostMapping("/ConfirmarNota-Post")
	public ResponseEntity<Map<String, String>> ConfirmarPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /Confirmar-Post");
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("arrVrVentaUnitario");
	    session.removeAttribute("arrIdReferencia");

	    // Obtenemos los datos del JSON recibido
	    String idDcto = (String) requestBody.get("idDcto");

    	// Obtener los valores de los arrays
	    @SuppressWarnings("unchecked")
	    List<String> xchkVrVentaUnitarioArr = (List<String>) requestBody.get("xchkVrVentaUnitarioArr");
	    System.out.println("xchkVrVentaUnitarioArr es  " + xchkVrVentaUnitarioArr);
	    
	    @SuppressWarnings("unchecked")
	    List<String> xidPluArr = (List<String>) requestBody.get("xidPluArr");
	    
	    
	    
	    String[] arrVrVentaUnitario = xchkVrVentaUnitarioArr.toArray(new String[0]);
	    String[] arrIdReferencia = xidPluArr.toArray(new String[0]);
	    
	    System.out.println("arrVrVentaUnitario en /Confirmar-Post " + arrVrVentaUnitario);
	    System.out.println("arrIdReferencia en /Confirmar-Post " + arrIdReferencia);
	    
	    // Guardar las listas en la sesión
	    session.setAttribute("idDcto", idDcto);
	    session.setAttribute("arrVrVentaUnitario", arrVrVentaUnitario);
	    session.setAttribute("arrIdReferencia", arrIdReferencia);


	    // Redirige a la vista y le pasamos el parametro de idDcto
	    //ModelAndView modelAndView = new ModelAndView("redirect:/Confirmar?idDcto=" + idDcto );
	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./ConfirmarNota");

        return ResponseEntity.ok(response);
	}
	
	
	
	
	@GetMapping("/ConfirmarNota")
	public String ConfirmarNota( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");

		
		 
		 
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
		           
		           
		Integer idUsuario = (Integer) session.getAttribute("xidUsuario");        
		    
		 
		 // Obtener las listas de la sesión
		 	String idDcto = (String) session.getAttribute("idDcto");
		 	String[] arrIdReferencia = (String[]) session.getAttribute("arrIdReferencia");
		 	String[] arrVrVentaUnitario =  (String[]) session.getAttribute("arrVrVentaUnitario");
		    
		System.out.println("Entró a /Confirmar con idDcto: " + idDcto);
		System.out.println("Entró a /Confirmar con arrIdReferencia: " + arrIdReferencia);
		System.out.println("Entró a /Confirmar con arrVrVentaUnitario: " + arrVrVentaUnitario);
		
		

	    
	    // Imprimir los arreglos
	    System.out.println("Dividido arrIdReferencia:");
	    for (String elemento : arrIdReferencia) {
	        System.out.println(elemento);
	    }

	    System.out.println("Dividido arrVrVentaUnitario:");
	    for (String elemento : arrVrVentaUnitario) {
	        System.out.println(elemento);
	    }
		

		    
		   
		    
		    Integer idDctoInt = Integer.parseInt(idDcto);
		   
			// Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(usuario.getIdLocal());
			
			Integer idPeriodo = 0;
			Integer idTipoOrden = 9;
			
			int xIdTipoTerceroCliente = 1;
			
			for(TblDctosPeriodo P : PeriodoActivo) {
				
				idPeriodo = P.getIdPeriodo();
				model.addAttribute("xIdPeriodo", P.getIdPeriodo());
				model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
			
			}
			

			//
	        int estadoActivo = 9;
			
			 // Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        String strFechaVisita = fechaActual.format(formatter);
			
			
			List<TblAgendaLogVisitas> visita = tblAgendaLogVisitasService.seleccionaVisitaEstadoxFecha(estadoActivo, strFechaVisita, idUsuario, usuario.getIdLocal());
			
			String xCantidadStr = "1";
			
			int idLog = 0;
			int xIdUsuario = 0;
			String xIdTercero = "";

			
			for(TblAgendaLogVisitas V : visita) {
				
				idLog = V.getIDLOG();
				xIdUsuario = V.getIDUSUARIO();
				xIdTercero = V.getIdCliente();
				
			}
			
			
			
			Integer idtipoorden = 9 ;
			int xIdTipoOrdenNota = 29;
			int xIdTipoOrdenNotaTemporal = xIdTipoOrdenNota + 50;
			//Obtenemos el idOrden
			Integer idOrden = TblDctosService.ObtenerIdOrden(usuario.getIdLocal(), idtipoorden, idDctoInt);
			
			
			int xIdTipoOrdenCadena = 9; // venta + nota venta
			
			// Consulta si existeOrden
			List<TblDctosOrdenesDTO> listaOrden = tblDctosOrdenesService.listaDctoOrden(usuario.getIdLocal(), xIdTipoOrdenCadena, idOrden);
			
			int xIdPeriodo = 0;
			
			for(TblDctosOrdenesDTO lista : listaOrden) {
				
				xIdPeriodo = lista.getIdPeriodo();
			}
			
			// retiraDevolucion
			tblDctosOrdenesDetalleRepo.retiraDevolucion(usuario.getIdLocal(), idLog);
			
			
			// Retorna a seleccionar cliente   
            if(xIdTercero == null) {
				
				return "Cliente/Selecciona";
				
			}
			
			
			
            
            
            
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
                        xIdTipoOrdenNotaTemporal,
                        xIdUsuario,
                        usuario.getIdLocal(),
                        xIdTercero,
                        xComentario,
                        xIdResponsable,
                        xIdClasificacion,
                        xIdPeriodo);

            }
            
            
            
            List<TercerosDTO2>  listaTercero =  tblTercerosService.listaTerceroFCH(usuario.getIdLocal(), xIdTipoTerceroCliente, xIdTercero);
            
            
        	List<TblTerceros> listaCliente = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), xIdTercero, xIdTipoTerceroCliente);
			
			for(TblTerceros L : listaCliente) {
				
				model.addAttribute("xEstado", L.getEstado());
				model.addAttribute("xNuid", L.getIdCliente());
				model.addAttribute("xNombreTercero", L.getNombreTercero());
				model.addAttribute("xRuta", L.getIdRuta());
				
			}
			

			
			
			List<TblDctosOrdenesDTO> liquidaOrden = tblDctosOrdenesService.liquidaOrdenLocal(usuario.getIdLocal(), idLog, xIdTipoOrdenNotaTemporal);			
			model.addAttribute("xLiquidaOrden", liquidaOrden);
			
			
			int xIdBodega = 1;
			
			System.out.println("idLog esss " + idLog);

			
			List<TblDctosOrdenesDetalleDTO> listadetalle = tblDctosOrdenesDetalleService.listaDetalle(usuario.getIdLocal(), xIdBodega, idLog, xIdTipoOrdenNotaTemporal);
			model.addAttribute("xListadetalle", listadetalle);
			
			
			model.addAttribute("xIdLog", idLog);
			model.addAttribute("xIdOrden", idOrden);
			model.addAttribute("xIdDcto", idDctoInt);
			model.addAttribute("xidCliente", xIdTercero);
			
			
            // Removemos de la session las variables
			session.removeAttribute("arrVrVentaUnitario");
		    session.removeAttribute("arrIdReferencia");

			
			return "Cliente/FinalizaCotizacion";
			
		

	}
	
	
	
	@PostMapping("/Finalizar-Post")
	@ResponseBody
	public ResponseEntity<Resource>  FinalizarPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  Finalizar");

	        // Obtenemos los datos del JSON recibido
	        String idLog = (String) requestBody.get("idLog");
	        System.out.println("idLog desde /BuscarSuscriptorReporteEstadoProducto " + idLog);
	        Integer xIdLogActual = Integer.parseInt(idLog);
	        
	        String idOrden = (String) requestBody.get("idOrden");
	        System.out.println("idOrden" + idOrden);     
	        Integer xIdOrden = Integer.parseInt(idOrden);
	        
	        String idDcto = (String) requestBody.get("idDcto");
	        System.out.println("idDcto" + idDcto);        
	        Integer xIdDcto = Integer.parseInt(idDcto);
	        
	        String idCliente = (String) requestBody.get("idCliente");
	        System.out.println("idCliente" + idCliente);
	        
	        String xObservacion = (String) requestBody.get("observacion");
	        System.out.println("xObservacion" + xObservacion);
	        
	       
	        
	        String formato = "PDF";

	        int idLocal = usuario.getIdLocal();
	        
	        int xIdTipoOrdenNota = 29;
	        int xIdTipoOrdenNotaTemporal = xIdTipoOrdenNota + 50;
	        String xIdTipoOrdenCadena = "9"; // venta + nota venta
	        int xSignoOperacionNota = 1;
	        int xIndicador = 1;
	       
	        
	        
	        // existePedido
           // Integer idPeriodo = tblDctosOrdenesRepo.existePedido(xIdLogActual, xIdTipoOrdenNota, idLocal);
            
            
//            if(idPeriodo != null) {
//            	
//            	System.out.println("idPeriodo es null");
//            	return ResponseEntity.badRequest().body(new ByteArrayResource("Error: idPeriodo es null".getBytes()));
//            }
            
            
            
	        
	        
            List<TblDctosDTO> listaSaldo =  TblDctosService.listaSaldoDctoFCH(idLocal, idCliente, xIdTipoOrdenCadena, xIdDcto);
	        
            Double xVrSaldoDctoOrigen = 0.0;
            
            for(TblDctosDTO lista : listaSaldo) {
            	
            	
            	xVrSaldoDctoOrigen = lista.getVrSaldo();
            }
	        
            
	        
	        
            Integer xidOrden =  procesoIngresoNota.ingresa(idLocal, xIdTipoOrdenNota, xIdOrden, xIdLogActual, xIdTipoOrdenNotaTemporal, xSignoOperacionNota, xIdDcto, xIndicador, xObservacion);
	        
            System.out.println("xidOrden es " + xidOrden);
            
//            Integer xidOrden =   TblDctosService.ObtenerIdOrdenPorCruce(idLocal, xIdOrden, idCliente);
//            System.out.println("xidOrden en ObtenerIdOrdenPorCruce " + xidOrden);
            
            
            // -------------------------------------------------------------------------------------- Reporte    
	        
	        
	        
	        
	        
			
		    int xIdReporte = 1401;
		    
		    //Obtenemos el FileName del reporte y el titulo 
		    List<TblLocalesReporte> reporte = tblLocalesReporteService.listaUnFCH(idLocal, xIdReporte);
		    
		    String xFileNameReporte = "";
		    String xTituloReporte = "";
		    
		    for(TblLocalesReporte R : reporte) {
		    	
		    	xFileNameReporte = R.getFileName();
		    	xTituloReporte = R.getReporteNombre();
		    }
		    
		    
		    System.out.println("xFileNameReporte es " + xFileNameReporte);
		    System.out.println("xTituloReporte es " + xTituloReporte);
			
			//Obtenemos la información del local que usaremos para los PARAMS del encabezado
		    List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
			
		    Map<String, Object> params = new HashMap<>();
		    params.put("tipo", formato);
		    params.put("idLocal", idLocal);

		   Integer IdTipoOrdenINI = 9;
		   Integer IdTipoOrdenFIN = 29;
		   Integer IndicadorINICIAL = 1;
		   Integer IndicadorFINNAL = 2;
		   
		   String xPathReport = "";
		   String xPathFileGralDB =  "";
		   
		   String xCharSeparator = File.separator;
		   
		   String NitNE = "";
		   String nombreLocal = "";
		   
		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 

			    params.put("p_nombreLocal", L.getNombreLocal());
			    params.put("p_nit", L.getNit());
			    params.put("p_titulo", xTituloReporte);
			    params.put("p_idLocal", idLocal);
			    params.put("p_indicadorINI", IndicadorINICIAL);
			    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
			    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
			    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
			    xPathFileGralDB = L.getPathFileGral();
			    NitNE = L.getNitNE();
			    nombreLocal =  L.getNombreLocal();
		    	
		    }
		    
		    List<TblDctosOrdenesDetalleDTO> listaOrden =  tblDctosOrdenesDetalleService.listaUnLocalOrden(xidOrden, xIdTipoOrdenNota, idLocal);
		    
		    String xTextoFactura = "NOTA CREDITO/DEBITO";
	        String xResolucion = "";
	        String xRango = "";
	        String xRegimen = "";
	        
	        String strDireccionCiudad = "";
	        String ciudadTercero = "";
	        String email = "";
	  
	        
		    
		    for(TblDctosOrdenesDetalleDTO lista : listaOrden) {
		    	
		    	xResolucion = lista.getResolucion().trim();
		    	xRango = lista.getRango().trim();
		    	xRegimen = lista.getRegimen().trim();
		    	ciudadTercero = lista.getCiudad();
		    	strDireccionCiudad = lista.getDireccion().trim() + " " + lista.getCiudad().trim() + " " + lista.getTelefono().trim();
		    	email = lista.getEmail();
		    	

		    }
		    
		    params.put("p_regimen", xRegimen);
		    params.put("p_resolucion", xResolucion + " " + xRango);
		    params.put("p_direccion", strDireccionCiudad);
		    params.put("p_ciudadTercero", ciudadTercero);
		    params.put("p_email", email);
		    
		    
		    // Tercero
		    int idTipoTercero = 1;
		    
		    List<TblTerceros> tercero = tblTercerosService.listaUnTerceroFCH(idLocal, idCliente, idTipoTercero);
		    
		    for(TblTerceros T : tercero) {
		    	
		    	params.put("p_idTercero", T.getIdCliente().trim() + " " + T.getNombreTercero().trim());
		    	params.put("p_idCliente", T.getIdCliente());
		    	params.put("p_nombreTercero", T.getNombreTercero());
			    params.put("p_direccionTercero", T.getDireccionTercero());
			    params.put("p_formaPago", "0");
			    params.put("p_telefonoFijo", "TEL: " + T.getTelefonoFijo());
			    System.out.println("TELEFONO FIJO ES: " + T.getTelefonoFijo());
			    params.put("p_ciudadTercero", T.getCiudadTercero());
		    	
		    }
		    
		    // LiquidaOrden
		    List<TblDctosDTO> listaUnDcto = TblDctosService.listaUnDctoFCH(idLocal, xIdTipoOrdenNota, xidOrden);
		    
		    String idDctoCruce = "";
		    String FechaDctoCorta = "";
		    
		    for(TblDctosDTO lista : listaUnDcto ) {
		    	
		    	params.put("p_observacion", lista.getObservacion());
		    	params.put("p_vrVentaSinIva", lista.getVrBase());
		    	params.put("p_vrDescuento", lista.getVrDescuento());
		    	params.put("p_vrIva", lista.getVrIva());
		    	params.put("p_vrVentaConIva", lista.getVrFactura());
		    	params.put("p_cufe", lista.getCufe());
		    	params.put("p_qr", lista.getQr());
		    	idDctoCruce = lista.getIdDctoCruce().toString();
		    	FechaDctoCorta = lista.getFechaDcto();
		    }
		    
		    
		    xTextoFactura = xTextoFactura + " " + xIdDcto;
		    String xTextoDctoCruce = "FRA.ORIGEN " + idDctoCruce;
		    
		    
		    params.put("p_textoFactura", xTextoFactura);
		    params.put("p_idDctoCruce", xTextoDctoCruce);
		    params.put("p_fechaOrden", "FECHA NOTA "   + FechaDctoCorta);
		    
		    
		    
		    
		    
		    
		    Integer IdProducto = 200;
		    
		    List<TblDctosOrdenesDetalleDTO2> lista = null;
		    

	            // QUERY PARA ALIMENTAR EL DATASOURCE
	            lista = tblDctosOrdenesDetalleService.detallaUnPedidoOrden(idLocal, xIdTipoOrdenNota, xidOrden);
		    	
	            System.out.println("lista " + lista);
		    
	    
			    // Se crea una instancia de JRBeanCollectionDataSource con la lista 
			    JRDataSource dataSource = new JRBeanCollectionDataSource(lista);
			    
			    JRDataSource dataSourceEnCarpeta = new JRBeanCollectionDataSource(lista);
			    
			    ReportesDTO dto = reporteSmsServiceApi.Reportes(params, dataSource, formato, xFileNameReporte, xPathReport); // Incluir (params, dataSource, formato, xFileNameReporte)
			    
			    // Verifica si el stream tiene datos y, si no, realiza una lectura en un búfer
			    InputStream inputStream = dto.getStream();
			    if (inputStream == null) {
			        // Realiza una lectura en un búfer alternativo si dto.getStream() es nulo
			        byte[] emptyContent = new byte[0];
			        inputStream = new ByteArrayInputStream(emptyContent);
			    }
			    
			    
			    // Envuelve el flujo en un InputStreamResource
			    InputStreamResource streamResource = new InputStreamResource(inputStream);
			    
			    // Configura el tipo de contenido (media type)
			    MediaType mediaType;
			    if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
			        mediaType = MediaType.APPLICATION_OCTET_STREAM;
			    } else {
			        mediaType = MediaType.APPLICATION_PDF;
			    }
	        
			    
			    
			   
			    // Obtenemos la IP del servidor
	        	 UtilidadesIP utilidadesIP = new UtilidadesIP();
	        	 String ipTx = utilidadesIP.getIpServidor();
	        	 
	        	 int xEstadoTerminado = 1;
	        	 
	        	// Obtener la fecha actual
	             LocalDate fechaActual = LocalDate.now();

	             // Formatear la fecha como un String
	             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	             String strFechaVisita = fechaActual.format(formatter);
	             
	             int xEstadoProgramado = 9;
			    
	        	 tblAgendaLogVisitasRepo.actualizaVisita(xEstadoTerminado, strFechaVisita, IdUsuario, xEstadoProgramado);
	        	 
	        	 
	        	 	//
	                boolean xOkFacturado = true;

	                //Codigo de BARRAS----------------------------------------
	                int xEstadoPeriodoActivo = 1;

			    
	                List <TblDctosPeriodoDTO> Periodo = tblDctosPeriodoService.listaEstadoFCH(idLocal, xEstadoPeriodoActivo);
	                
	                int xIdPeriodo = 0;
	                String xFechaConRecargo = "";
	                int xIdTipoOrdenFactura = 9;
	                
	                for(TblDctosPeriodoDTO P : Periodo) {
	                	
	                	xIdPeriodo = P.getIdPeriodo();
	                	xFechaConRecargo = P.getFechaConRecargo();
	                }
	                
	                
	              //--okActualizaCuotaVencidaxCliente----------------------------
	                List<TblDctosDTO> listaDctoCliente = TblDctosService.listaUnDctoClienteFCH(idLocal, xIdPeriodo, idCliente);
	                
	                Double VrBase = 0.0;
	                
	                for(TblDctosDTO L : listaDctoCliente) {
	                	
	                	VrBase = L.getVrBase();
	                }
	                

	                
	                if (VrBase <= 0) {

	                    tblDctosRepo.actualizaUnaCuotaVencidaxAnticipo(idLocal, xIdPeriodo, xIdTipoOrdenFactura, idCliente);
	                }
	                
	                
	                
	                List<TblLocales> LocalObtenido = tblLocalesService.ObtenerLocal(idLocal);
	                
	                int xEstadoAjusteCentena = 0;
	                int xEstadoAjusteDecena_SI = 1;
	                int xEstadoAjusteCentena_SI = 3;
	                
	                String xCodigoIAC = "";
	                int xEstadoGeneraIAC = 0;
	                
	                // Factura AQUASITIO
	                int xEstadoSTR_SI = 1;
	    		    Integer xEstadoSTR = null;
	                
	                for(TblLocales L : LocalObtenido) {
	                	xEstadoAjusteCentena = L.getEstadoAjusteCentena();
	                	xCodigoIAC = L.getCodigoIAC();
	                	xEstadoGeneraIAC = L.getEstadoGeneraIAC();
	                	 xEstadoSTR = L.getEstadoSTR();
	                }

	              //--- Ajuste Decena ( posterior a facturado )
	                if ((xOkFacturado) && (xEstadoAjusteDecena_SI == xEstadoAjusteCentena)) {

	                    //
	                    int xIdTipoAjusteDecena = 23;

	                    tblDctosOrdenesDetalleRepo.retiraItemAjuste(idLocal, xIdTipoOrdenFactura, xIdPeriodo, idCliente, xIdTipoAjusteDecena);

	                    // Liquida ingresa ajuste
	                    tblDctosOrdenesDetalleRepo.ingresaTipoxCliente(idLocal, xIdTipoAjusteDecena, xIdTipoOrdenFactura, xIdPeriodo, idCliente);

	                }
	                
	                
	                
	              //--- Ajuste Centena ( posterior a facturado )
	                if ((xOkFacturado) && (xEstadoAjusteCentena_SI == xEstadoAjusteCentena)) {

	                    //
	                    int xIdTipoAjusteDecena = 23;

	                    tblDctosOrdenesDetalleRepo.retiraItemAjuste(idLocal, xIdTipoOrdenFactura, xIdPeriodo, idCliente, xIdTipoAjusteDecena);
	                    
	                    // Liquida ingresa ajuste
	                    tblDctosOrdenesDetalleRepo.ingresaTipoCentenaxCliente(idLocal, xIdTipoAjusteDecena, xIdTipoOrdenFactura, xIdPeriodo, idCliente);

	                }
	                
	                
	                

	                //
	                int xEstadoGeneraIAC_SI = 1;
	                
	                
	             // Genera imagen IAC CODE128 ( posterior a facturado )
	                if ((xOkFacturado) && (xEstadoGeneraIAC_SI == xEstadoGeneraIAC)) {

	                    // TODO code application logic here
	                    //String xCharSeparator = File.separator;
	                    String xRuta = "";

	                    // Linux 
	                    if (xCharSeparator.compareTo("/") == 0) {

	                        // Linux               
	                        xRuta = "" + xCharSeparator + "home" + xCharSeparator + "sw" + xCharSeparator + "jar" + xCharSeparator + "CodigoGS1" + xCharSeparator + "dist" + xCharSeparator + "CodigoGS1.jar ";

	                    } else {

	                        // Windows          
	                        xRuta = "C:" + xCharSeparator + "proyectoWeb" + xCharSeparator + "CodigoGS1" + xCharSeparator + "dist" + xCharSeparator + "CodigoGS1.jar ";

	                    }

	                    //
	                    final int xIdLocalUsuarioFinal = idLocal;                    
	                    final int xIdPeriodoFinal = xIdPeriodo;                        
	                    final String xRutaDisco = xRuta;
	                    final String xIdClienteFinal = idCliente; //  0 ( son todos)                 

	                    //                       //
	                    Thread t = new Thread(new Runnable() {

	                        @Override
	                        @SuppressWarnings("empty-statement")
	                        public void run() {
	                            try {

	                                //
	                                Runtime rt = Runtime.getRuntime();

	                                Process proc = rt.exec("java -jar " + xRutaDisco
	                                        + xIdLocalUsuarioFinal + " "
	                                        + xIdPeriodoFinal + " " 
	                                        + xIdClienteFinal);

	                                //
	                                BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

	                                BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

	                                // read the output from the command
	                                String s = null;
	                                while ((s = stdInput.readLine()) != null) {
	                                    System.out.println(s);
	                                }

	                                // read any errors from the attempted command
	                                while ((s = stdError.readLine()) != null) {
	                                    System.out.println(s);
	                                }
	                                proc.waitFor();
	                                System.out.println("success");
	                            } catch (Exception ex) {
	                                ex.printStackTrace();
	                            }
	                        }
	                    });
	                    t.start();                    

	                }
	                
	                
	                System.out.println("Despues del GS1");
	                
	                if (xEstadoSTR_SI == xEstadoSTR) {
	                	
	                	System.out.println("---- ENTRO A ACTUALIZAR ETAPASTR");
	                	// Actualiza Dctos       
	                    tblDctosRepo.actualizaEtapaSTRxDcto(idLocal, xIdPeriodo, xIdDcto, idCliente);
	                    
	                }
	                	                	                
	                //GENERACION DE REPORTE EN CARPETA Y ENVIO POR CORREO
	                	                
	                //Query del reporte para obtener el valor 	                
	                List<TblDctosOrdenesDetalleDTO2> reporteNota = tblDctosOrdenesDetalleService.detallaUnPedidoOrden(idLocal, xIdTipoOrdenNota, xidOrden);
	                
	                Double vrVentaUnitario = 0.0;	                
	                for(TblDctosOrdenesDetalleDTO2 rep : reporteNota) {	                	
	                	vrVentaUnitario = rep.getVrVentaUnitario();
	                }
	                	               
	                String prefijo = "";
	                if(vrVentaUnitario < 0) {
	                	
	                	prefijo = "NC";
	                }else {
	                	prefijo = "ND";
	                }
	                
	                String emailTercero = tblTercerosService.ObtenerEmailTercero(idLocal, idCliente);
	                
	                final String finalIdDcto = prefijo +  xIdDcto;
	                final String finalXToAddress = emailTercero;
	                final String finalXAsunto = NitNE + ";" + nombreLocal + ";" + prefijo + xIdDcto + ";" + nombreLocal;
	                final String finalXTextPart = "";
	               
	                
	                String xPathPDF = xPathFileGralDB + "aquamovil" + xCharSeparator + "BDMailFactura" + xCharSeparator + idLocal + xCharSeparator;
	                final String finalFileName = finalIdDcto + ".pdf";
	                final String xFileNameReporteFinal = xFileNameReporte;
	                final String xPathReportFinal = xPathReport;
	                final String finalPathFile = xPathPDF + finalIdDcto + ".pdf";
	                final String finalXMensaje = "NOTA " + prefijo + "\n" + xIdDcto + "\n";
	                        

	                
	                //Tarea Asincronica que genera el PDF en un hilo separado
	                CompletableFuture<Void> reporteTask = CompletableFuture.runAsync(() -> {
	                    try {
	                        System.out.println("Creando reporte para idDcto " + finalIdDcto);
	                        ReportesDTO dtoReport = reporteSmsServiceApi.ReporteEnCarpetaNotas(params, dataSourceEnCarpeta, formato, xFileNameReporteFinal, xPathReportFinal, xPathPDF,  finalIdDcto);
	                        System.out.println("Reporte creado para idDcto " + finalIdDcto);
	                    } catch (SQLException e) {
	                        e.printStackTrace(); 
	                    } catch (JRException e) {
							
							e.printStackTrace();
						} catch (IOException e) {
							
							e.printStackTrace();
						}
	                });
	                
	                String vacio =  "";
	                
	                       
	                // Envio por correo
	                mailjetTask.ejecutarJar(idLocal, finalXAsunto, finalXTextPart, finalPathFile, xIdDcto, finalFileName, finalXToAddress, finalXMensaje, vacio);
	                
	                
	                
	                
	                
		    
		    
	     // Configura la respuesta HTTP
		    return ResponseEntity.ok()
		            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
		            .contentLength(dto.getLength())
		            .contentType(mediaType)
		            .body(streamResource);
	   
	    
	}
	
	
	
	
	
	
	
	
	
	

}
