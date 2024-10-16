package com.marketing.Controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Model.dbaquamovil.TblTerceros;
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
import com.marketing.Utilidades.ProcesoConfirmaDiferido;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaCredito;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.ProcesoIngresoNota;
import com.marketing.Utilidades.UtilidadesIP;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class FinanciacionController {
	
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
	ProcesoConfirmaDiferido procesoConfirmaDiferido;
	
	 @Autowired
	 ControlDeInactividad controlDeInactividad;
	
	
	@GetMapping("/Financiacion")
	public String financiacion(HttpServletRequest request,Model model) {
		
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

			           
			    // ---------------------------------------------------------------- VALIDACION PERIODOS FACTURADOS --------------------------------------------------------      
			           
				        // Obtenemos el periodo activo
							List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
							Integer idTipoOrden = 9;
							
							Integer idPeriodoActual = 0;		
							for(TblDctosPeriodo P : PeriodoActivo) {
								
								idPeriodoActual = P.getIdPeriodo();
							
							}      
				           
					   // Obtenemos el periodo anterior
						Integer idPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(idPeriodoActual, idLocal);
						System.out.println("idPeriodoAnterior es " + idPeriodoAnterior);  
				           
				       
						// idPeriodoActual
						List<TblDctosOrdenesDTO> CuentaFacturadoActual =  tblDctosOrdenesService.PeriodoFacturado(idLocal, idTipoOrden, idPeriodoActual);
						
						Integer CuentaPeriodoActual = 0;
						
						for(TblDctosOrdenesDTO C : CuentaFacturadoActual) {
							
							CuentaPeriodoActual = C.getCuenta();
						}   
						System.out.println("CuentaPeriodoActual es " + CuentaPeriodoActual); 
						
						
						// idPeriodoAnterior
						List<TblDctosOrdenesDTO> CuentaFacturadoAnterior =  tblDctosOrdenesService.PeriodoFacturado(idLocal, idTipoOrden, idPeriodoAnterior);
						
						Integer CuentaPeriodoAnterior = 0;
						
						for(TblDctosOrdenesDTO C : CuentaFacturadoAnterior) {
							
							CuentaPeriodoAnterior = C.getCuenta();
						} 
						System.out.println("CuentaPeriodoAnterior es " + CuentaPeriodoAnterior); 
						
						
						
						// Validamos los estados de los periodos 
						
						// SI el periodo actual NO está facturado y el periodo anterior SI está facturado
						if(CuentaPeriodoActual == 0 && CuentaPeriodoAnterior != 0 ) {
							
							model.addAttribute("error", "Por favor ingresar financiación en el periodo anterior facturado " + idPeriodoAnterior + ".");
			            	model.addAttribute("url", "./menuPrincipal");
			        		return "defaultErrorSistema";
						}
						
						
						// SI el periodo actual NO está facturado y el periodo anterior NO está facturado
	                    if(CuentaPeriodoActual == 0 && CuentaPeriodoAnterior == 0 ) {
							
							model.addAttribute("error", "Por favor facturar el periodo anterior " + idPeriodoAnterior + ".");
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
				
				//Validamos si el idCliente tiene financiaciones activas idTipoOrden = 7
				List<TblDctosOrdenesDetalleDTO2> financiacionLista  = tblDctosOrdenesDetalleService.listaFinanciacion(idLocal, xIdTipoOrden, idCliente);
				
				int idOrden = 0;
				
				for(TblDctosOrdenesDetalleDTO2 lista : financiacionLista) {
					
					idOrden = lista.getIDORDEN();
					
				}
				
				//Validamos si existe idOrden
				if(idOrden != 0) {
					
					model.addAttribute("xFinanciacionLista", financiacionLista);
					
				}
				
				
				// ------------------------------------------------- VALIDAR SI EXISTE ORDEN TEMPORAL = 57 -------------------------------------------------------
				
				List<TblDctosOrdenesDTO> ordenTemporal = tblDctosOrdenesService.obtenerOrdenTemporal(idLocal, xIdTipoOrdenProceso, idCliente);
				
				int xIDORDEN = 0;
				//int xIdLog = 0;
				
				// EXISTE ORDEN TEMPORAL 
				if (!ordenTemporal.isEmpty()) {
				    System.out.println("ordenTemporal NO está vacía");
				    
				    for(TblDctosOrdenesDTO orden : ordenTemporal ) {

				    	xIDORDEN = orden.getIDORDEN();
				    	//xIdLog = orden.getIDLOG();
				    }
				    
				    //Obtenemos el idLog Actual 
				    Integer idLog = tblAgendaLogVisitasService.ObtenerIdLogActivo(IdUsuario);
				    System.out.println("idLog en funanciacion es " + idLog);
				    
				    
				    //Validamos si el log actual tiene una financiacion en proceso = 57
				    List<TblDctosOrdenesDTO> liquidaReferidoLista = tblDctosOrdenesService.listaLiquidaDiferido(usuario.getIdLocal(), xIdTipoOrdenProceso, idLog);
		            model.addAttribute("xLiquidaReferidoLista", liquidaReferidoLista);
		            
		            
		            System.out.println("liquidaReferidoLista en financiacion es " +liquidaReferidoLista);
		            
		            Double vrTotalLista = 0.0;
		            
		            for(TblDctosOrdenesDTO liq : liquidaReferidoLista) {
		            	
		            	vrTotalLista = liq.getVrTotalDiferir();
		            }
		            
		            
		            
		            // Validamos si de liquidaReferidoLista los valores no son null
		            if (vrTotalLista != null) {
		            	
		            	System.out.println("vrTotalLista NO es null");
		            
		            for(TblDctosOrdenesDTO lista : liquidaReferidoLista ) {
		            	
		            	Double vrTotalDiferir = lista.getVrTotalDiferir();
		            	Double vrTotalSumatoria = lista.getVrTotalSumatoria();
		            	
		            	Double vrTotalDiferencia = vrTotalDiferir - vrTotalSumatoria;
		            	
		            	
		            	 // Convertir el número a BigDecimal
		                BigDecimal bd = BigDecimal.valueOf(vrTotalDiferencia);
		                
		                // Redondear el número a dos decimales
		                bd = bd.setScale(2, RoundingMode.HALF_UP);
		                
		                // Obtener el valor redondeado como double
		                Double TotalDiferenciaRedondeado = bd.doubleValue();

		            	
		            	model.addAttribute("xVrTotalDiferencia", TotalDiferenciaRedondeado.intValue());
		            	model.addAttribute("xVrTotalSumatoria", vrTotalSumatoria.intValue());
		            	model.addAttribute("xVrTotalDiferir", vrTotalDiferir.intValue());
		               }
		            
		            
		            List<TblDctosOrdenesDetalleDTO> ordenLista = tblDctosOrdenesDetalleService.listaOrden(usuario.getIdLocal(), 1, xIdTipoOrdenProceso, idLog);
		            model.addAttribute("xOrdenLista", ordenLista);
		            
		            
		            model.addAttribute("xIdLog", idLog);
		            
		            }else {
		            	
		            	System.out.println("liquidaReferidoLista SI es null");
		            	
		            }
		            
		           
				    
				    
				} 
				
				
				
		
		return "Financiacion/Financiacion";
	}
	
	
	
	@PostMapping("/Iniciar-Post")
	public ModelAndView IniciarPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerPeriodo-Post");

	    // Obtenemos los datos del JSON recibido
	    String idCliente = (String) requestBody.get("idCliente");
	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:./Iniciar?idCliente=" + idCliente);
	    return modelAndView;
	}
	
	
	
	
	@GetMapping("/Iniciar")
	public String Iniciar(@RequestParam(name = "idCliente", required = false) String idCliente, HttpServletRequest request, Model model) {
		
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

		    
		    int idTipo = 8;
		    
		    List<TblPlus>  listafinanciacion = tblPlusService.ObtenerFinanciacion(usuario.getIdLocal(), idTipo);
		    model.addAttribute("xListafinanciacion", listafinanciacion);

			
			return "Financiacion/AdicionarFinanciacion";
			
	

	}
	
	
	
	@PostMapping("/ConfirmarFinanciacion-Post")
	public ResponseEntity<Map<String, String>> ConfirmarFinanciacionPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ConfirmarFinanciacion-Post");
	    
	    // Limpiar las variables anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdPlu");
	    session.removeAttribute("xVrCredito");
	    session.removeAttribute("xPorcentajeInteres");
	    session.removeAttribute("xNumeroCuotas");
	    session.removeAttribute("xObservacion");
	    

	    // Obtenemos los datos del JSON recibido
	    String xIdPlu = (String) requestBody.get("xIdPlu");
	    String xVrCredito = (String) requestBody.get("xVrCredito");
	    String xPorcentajeInteres = (String) requestBody.get("xPorcentajeInteres");
	    String xNumeroCuotas = (String) requestBody.get("xNumeroCuotas");
	    String xObservacion = (String) requestBody.get("xObservacion");
	    
	    // Guardar las variable en la sesión
	    session.setAttribute("xIdPlu", xIdPlu);
	    session.setAttribute("xVrCredito", xVrCredito);
	    session.setAttribute("xPorcentajeInteres", xPorcentajeInteres);
	    session.setAttribute("xNumeroCuotas", xNumeroCuotas);
	    session.setAttribute("xObservacion", xObservacion);
	    


        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./ConfirmarFinanciaciones");

        return ResponseEntity.ok(response);
	}
	
	
	
	
	@GetMapping("/ConfirmarFinanciaciones")
	public String ConfirmarFinanciacion( HttpServletRequest request, Model model) {
		
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

	           
	           
	       	// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(usuario.getIdLocal());
				
				Integer idPeriodo = 0;
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					idPeriodo = P.getIdPeriodo();
				
				}
		
		
			int xIdTipoTerceroCliente = 1;

			System.out.println("Entró a /ConfirmarFinanciaciones");

		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    
		    // Obtener las variables de la sesión
		    String xIdPlu =  (String) session.getAttribute("xIdPlu");
		    String xVrCredito =  (String) session.getAttribute("xVrCredito");
		    String xPorcentajeInteres =  (String) session.getAttribute("xPorcentajeInteres");
		    String xNumeroCuotas =  (String) session.getAttribute("xNumeroCuotas");
		    String xObservacion =  (String) session.getAttribute("xObservacion");
		    
		    if(xPorcentajeInteres == "") {
		    	
		    	System.out.println("xPorcentajeInteres está vacio ");
		    	
		    	xPorcentajeInteres = "0";
		    }
		    
		    Integer xNumeroCuotasInt = Integer.parseInt(xNumeroCuotas);
		    
		    int xIdTipoOrdenFinanciacion = 7;
	        int xIdTipoOrdenProceso = xIdTipoOrdenFinanciacion + 50;
		 	
		    
		    System.out.println("xIdPlu es: " + xIdPlu);
		    System.out.println("xVrCredito es: " + xVrCredito);
		    System.out.println("xPorcentajeInteres es: " + xPorcentajeInteres);
		    System.out.println("xNumeroCuotas es: " + xNumeroCuotas);
		    System.out.println("xObservacion es: " + xObservacion);
		    
		    
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
			
			System.out.println("idLog esss: " + idLog);
			
			
			//--------------------------------------------------------------
            Double xFinanciacion = Double.parseDouble(xVrCredito);
            Double xInteres = Double.parseDouble(xPorcentajeInteres);
            Double xNumeroCuotasDou = Double.parseDouble(xNumeroCuotas);

            Double xValorInteres = 0.0;
            Double xTotalInteres = 0.0;
            Double xTotalFinanciacionMasInteres = 0.0;
            Double xVrCuota = (xFinanciacion / xNumeroCuotasDou);
			
            
            for (int i = 1; i <= xNumeroCuotasDou; i++) {

                //--
                xValorInteres =  (xFinanciacion - ((i-1) * xVrCuota)) * (xInteres / 100.0);

                xTotalInteres += xValorInteres;

            }
		    
            xTotalFinanciacionMasInteres = xFinanciacion + xTotalInteres;
            
            
            
            
            for (int indice = 1; indice <= xNumeroCuotasInt; indice++) {

                //
                double xCantidad = 1.0;

                //
                double xVrVentaUnitario = xTotalFinanciacionMasInteres / xNumeroCuotasInt;

                //valida el idTercero sea el mismo para todos
                // String strIdReferencia = xIdPlu;
                int xItem = indice;
                
                System.out.println("EN EL FOR INGRESÓ  " + xItem);

                //
                int maximoItem = procesoGuardaCredito.guarda(idLog,
                        xIdPlu,
                        xCantidad,
                        xVrVentaUnitario,
                        xItem,
                        xIdTipoOrdenProceso,
                        xIdUsuario,
                        usuario.getIdLocal(),
                        xIdTercero,
                        String.valueOf(xTotalFinanciacionMasInteres),
                        xNumeroCuotasDou,
                        xPorcentajeInteres,
                        String.valueOf(xTotalInteres),
                        xObservacion,
                        idPeriodo);
            }
            
            
			
			
			
			Integer registros = 10;
			
			for(int i = 1; i <= registros; i++) {
	
				System.out.println("EN EL FOR INGRESÓ CON i " + i);
				
			}
			
            
            System.out.println("SALIO DEL FOR" );

           // List<TercerosDTO2>  listaTercero =  tblTercerosService.listaTerceroFCH(usuario.getIdLocal(), xIdTipoTerceroCliente, xIdTercero);
            
            
            List<TblDctosOrdenesDTO> liquidaReferidoLista = tblDctosOrdenesService.listaLiquidaDiferido(usuario.getIdLocal(), xIdTipoOrdenProceso, idLog);
            model.addAttribute("xLiquidaReferidoLista", liquidaReferidoLista);
            
            for(TblDctosOrdenesDTO lista : liquidaReferidoLista ) {
            	
            	Double vrTotalDiferir = lista.getVrTotalDiferir();
            	Double vrTotalSumatoria = lista.getVrTotalSumatoria();
            	
            	Double vrTotalDiferencia = vrTotalDiferir - vrTotalSumatoria;
            	
            	
            	 // Convertir el número a BigDecimal
                BigDecimal bd = BigDecimal.valueOf(vrTotalDiferencia);
                
                // Redondear el número a dos decimales
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                
                // Obtener el valor redondeado como double
                Double TotalDiferenciaRedondeado = bd.doubleValue();

            	
            	model.addAttribute("xVrTotalDiferencia", TotalDiferenciaRedondeado.intValue());
            	model.addAttribute("xVrTotalSumatoria", vrTotalSumatoria.intValue());
            	model.addAttribute("xVrTotalDiferir", vrTotalDiferir.intValue());
            }
            
            
            List<TblDctosOrdenesDetalleDTO> ordenLista = tblDctosOrdenesDetalleService.listaOrden(usuario.getIdLocal(), 1, xIdTipoOrdenProceso, idLog);
            model.addAttribute("xOrdenLista", ordenLista);
            System.out.println("ordenLista es: " + ordenLista );
            
        	List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), xIdTercero, xIdTipoTerceroCliente);
			
			for(TblTerceros L : listaTercero) {
				
				model.addAttribute("xEstado", L.getEstado());
				model.addAttribute("xNuid", L.getIdCliente());
				model.addAttribute("xNombreTercero", L.getNombreTercero());
				model.addAttribute("xRuta", L.getIdRuta());
				
			}
			
			
			model.addAttribute("xIdLog", idLog);
			
			// Removemos de la session las variables
		    session.removeAttribute("xIdPlu");
		    session.removeAttribute("xVrCredito");
		    session.removeAttribute("xPorcentajeInteres");
		    session.removeAttribute("xNumeroCuotas");
		    session.removeAttribute("xObservacion");
            
            return "Financiacion/Financiacion";
	

	}
	
	
	
	@PostMapping("/FinalizarFinanciacion-Post")
	@ResponseBody
	public ResponseEntity<Resource>  FinalizarFinanciacionPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  Finalizar");

	        // Obtenemos los datos del JSON recibido
	        String idLog = (String) requestBody.get("idLog");
	        System.out.println("idLog desde /BuscarSuscriptorReporteEstadoProducto " + idLog);
	        Integer xIdLog = Integer.parseInt(idLog);
	        
	        
	        @SuppressWarnings("unchecked")
		    List<String> xVrCuotaArr = (List<String>) requestBody.get("xVrCuotaArr");
	        System.out.println("xVrCuotaArr es : " + xVrCuotaArr);
	        
	        String[] ArryVrCuota = xVrCuotaArr.toArray(new String[0]);
	        
	        
	        String xNumeroCuotas =  (String) requestBody.get("idLog");
	        Integer xNumeroCuotasInt = Integer.parseInt(xNumeroCuotas);

	        
	        
	        String formato = "PDF";

	        int idLocal = usuario.getIdLocal();
	        
	        int xIdTipoOrdenFinanciacion = 7;
	        int xIdTipoOrdenProceso = xIdTipoOrdenFinanciacion + 50;
	        String xIdTipoOrdenCadena = "9"; // venta + nota venta
	        int xSignoOperacionNota = 1;
	        int xIndicador = 1;
	       
	        
	        
            
	        List<TblDctosOrdenesDTO> listaLiquitaReferido = tblDctosOrdenesService.listaLiquidaDiferidoFCH(idLocal, xIdTipoOrdenProceso, xIdLog);
	        
	        
	        
	        //----------- proceso de financiacion idTipoOrden=7-------------
     
	        Integer xidOrden = procesoConfirmaDiferido.ingresa(idLocal, xIdTipoOrdenFinanciacion, xIdLog, xIdTipoOrdenProceso, ArryVrCuota, xNumeroCuotasInt);
	        System.out.println("xidOrden essss: " + xidOrden);
            
            
            // -------------------------------------------------------------------------------------- Reporte    -----------------------------------------------------
	        

		    int xIdReporte = 1900;
		    
		    //Obtenemos el FileName del reporte y el titulo 
		    List<TblLocalesReporte> reporte = tblLocalesReporteService.listaUnFCH(idLocal, xIdReporte);
		    
		    String xFileNameReporte = "";
		    String xTituloReporte = "";
		    
		    for(TblLocalesReporte R : reporte) {
		    	
		    	xFileNameReporte = R.getFileName();
		    	xTituloReporte = R.getReporteNombre();
		    }
			
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
		   
		   String xCharSeparator = File.separator;
		   
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
		    	
		    }
		    
		    List<TblDctosOrdenesDetalleDTO> listaOrden =  tblDctosOrdenesDetalleService.listaUnLocalOrden(xidOrden, xIdTipoOrdenFinanciacion, idLocal);
		    
		    String xTextoFactura = "FINANCIACION";
	        String xResolucion = "";
	        String xRango = "";
	        String xRegimen = "";
	        
	        String strDireccionCiudad = "";
	        String ciudadTercero = "";
	        String email = "";
	  
	        int xIdDcto = 0;
		    
		    for(TblDctosOrdenesDetalleDTO lista : listaOrden) {
		    	
		    	xResolucion = lista.getResolucion().trim();
		    	xRango = lista.getRango().trim();
		    	xRegimen = lista.getRegimen().trim();
		    	ciudadTercero = lista.getCiudad();
		    	strDireccionCiudad = lista.getDireccion().trim() + " " + lista.getCiudad().trim() + " " + lista.getTelefono().trim();
		    	email = lista.getEmail();
		    	xIdDcto = lista.getIdDcto();
		    	

		    }
		    
		    params.put("p_regimen", xRegimen);
		    params.put("p_resolucion", xResolucion + " " + xRango);
		    params.put("p_direccion", strDireccionCiudad);
		    params.put("p_ciudadTercero", ciudadTercero);
		    params.put("p_email", email);
		    
		    
		    // Tercero
		    int idTipoTercero = 1;
		    
		    List<TercerosDTO2> tercero = tblTercerosService.listaUnTerceroOrden(idLocal, xIdTipoOrdenFinanciacion, xidOrden);
		    
		    
		    
		    for(TercerosDTO2 T : tercero) {
		    	
		    	params.put("p_idTercero", T.getIdCliente().trim() + " " + T.getNombreTercero().trim());
		    	params.put("p_idCliente", T.getIdCliente());
		    	params.put("p_nombreTercero", T.getNombreTercero());
			    params.put("p_direccionTercero", T.getDireccionTercero());
			    params.put("p_formaPago", "0");
			    params.put("p_telefonoFijo", "TEL: " + T.getTelefonoFijo());
			    System.out.println("TELEFONO FIJO ES: " + T.getTelefonoFijo());
			    params.put("p_ciudadTercero", T.getCiudad());
		    	
		    }
		    
		    // LiquidaOrden
		    List<TblDctosOrdenesDetalleDTO> liquidaCotizacion = tblDctosOrdenesDetalleService.liquidaUnCotizacion(idLocal, xIdTipoOrdenFinanciacion, xidOrden);
		    

		    String FechaDctoCorta = "";
		    
		    for(TblDctosOrdenesDetalleDTO lista : liquidaCotizacion ) {
		    	
		    	params.put("p_vrVentaSinIva", lista.getVrVentaSinIva());
		    	params.put("p_vrDescuento", 0.0);
		    	params.put("p_vrIva", lista.getVrIva());
		    	params.put("p_vrVentaConIva", lista.getVrVentaConIva());
		    	params.put("p_vrFinanciacion", lista.getVrVentaConIva() - lista.getVrInteresADiferir());
		    	params.put("p_porcentajeInteresADiferir", lista.getPorcentajeInteresADiferir());
		    	params.put("p_vrInteresADiferir", lista.getVrInteresADiferir());

		    	FechaDctoCorta = lista.getFechaOrden();
		    }
		    
		    
		    xTextoFactura = xTextoFactura + " " + xIdDcto;

		    params.put("p_textoFactura", xTextoFactura);
		    params.put("p_fechaOrden", "FECHA "   + FechaDctoCorta);
		    
		    
		    
		    
		    
		    
		    Integer IdProducto = 200;
		    
		    List<TblDctosOrdenesDetalleDTO2> lista = null;
		    

	            // QUERY PARA ALIMENTAR EL DATASOURCE
	            lista = tblDctosOrdenesDetalleService.detallaFinanciacion(idLocal, xIdTipoOrdenFinanciacion, xidOrden);
		    	
	            System.out.println("lista " + lista);
		    
	    
			    // Se crea una instancia de JRBeanCollectionDataSource con la lista 
			    JRDataSource dataSource = new JRBeanCollectionDataSource(lista);
			    
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
	             
	             int estadoVisita = 9;
			    
	        	 tblAgendaLogVisitasRepo.actualizaVisita(xEstadoTerminado, strFechaVisita, IdUsuario, estadoVisita);

		    
		    
	     // Configura la respuesta HTTP
		    return ResponseEntity.ok()
		            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
		            .contentLength(dto.getLength())
		            .contentType(mediaType)
		            .body(streamResource);
	   
	    
	}
	
	
	
	@PostMapping("/RetirarFinanciacion")
	public ResponseEntity<Map<String, String>> RetirarFinanciacion(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ConfirmarFinanciacion-Post");
	    
	    Integer IdUsuario = usuario.getIdUsuario();

	    // Obtenemos los datos del JSON recibido
	    String idOrden = (String) requestBody.get("idOrden");
	    Integer xIdOrden = Integer.parseInt(idOrden);
	    
	    int xIdTipoOrdenFinanciacion = 7;
	    
	    
	    List<TblDctosOrdenesDTO> listaDctoOrden = tblDctosOrdenesService.listaDctoOrden(usuario.getIdLocal(), xIdTipoOrdenFinanciacion, xIdOrden);

	    //--
        String xObservacionFin = "**Fin**";
        
        
        tblDctosOrdenesRepo.actualizaFinObservacion(xObservacionFin, usuario.getIdLocal(), xIdTipoOrdenFinanciacion, xIdOrden);
        
        
        int xItemPadre = 1;
        
        tblDctosOrdenesDetalleRepo.retiraItemNoFacturado(usuario.getIdLocal(), xIdTipoOrdenFinanciacion, xItemPadre, xIdOrden);
        
        
        //---
        int estadoVisita = 9;
        int xIdEstadoVisitaConcluido = 6;
        int xEstadoTerminado = 1;
        int xIdEstadoTxSinTX = 1;
        
        
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
        System.out.println("strFechaVisita  es" + strFechaVisita);
        

		
		String idCliente = tblAgendaLogVisitasService.seleccionaVisitaEstadoFecha(estadoVisita, strFechaVisita, IdUsuario);
        
		int xIdLogActual = tblAgendaLogVisitasService.seleccionaVisitaEstadoFechaIDLOG(estadoVisita, strFechaVisita, IdUsuario);
		
		// Obtenemos la IP del servidor
		UtilidadesIP utilidadesIP = new UtilidadesIP();
		String ipTx = utilidadesIP.getIpServidor();
        
        
		//tblAgendaLogVisitasRepo.finalizaVisita(xEstadoTerminado, xIdEstadoVisitaConcluido, xIdTipoOrdenFinanciacion, xIdEstadoTxSinTX, ipTx, strFechaVisita, usuario.getIdLocal(), xIdLogActual);
        
        


        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./Financiacion");

        return ResponseEntity.ok(response);
	}
	
	
	
	@PostMapping("/RetirarItem")
	public ResponseEntity<Map<String, String>> RetirarItem(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /RetirarItem");
	    
	    Integer IdUsuario = usuario.getIdUsuario();

	    // Obtenemos los datos del JSON recibido
	    String item = (String) requestBody.get("item");
	    Integer xItem = Integer.parseInt(item);
	    
	    String idLog = (String) requestBody.get("idLog");
	    Integer xIdLog = Integer.parseInt(idLog);
	    

	    
	    List<TblDctosOrdenesDTO> listaDctos = tblDctosOrdenesService.listaDctoOrdenIdLog(usuario.getIdLocal(),  xIdLog);
	    
	    
	    
	    tblDctosOrdenesDetalleRepo.retiraItem(xItem, usuario.getIdLocal(), xIdLog);
	    


        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./Financiacion");

        return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	
	

}
