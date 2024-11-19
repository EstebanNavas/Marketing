package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosDTO4;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
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
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class HistoricoPedidoController {
	
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
	ControlDeInactividad controlDeInactividad;
	
	
	@GetMapping("/HistoricoPedido")
	public String historicoPedido (HttpServletRequest request,Model model) {
		
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

//				// Obtenemos la lista de periodos 
//				List <TblDctosPeriodo> Periodos = tblDctosPeriodoService.ListaTotalPeriodos(idLocal);
//				model.addAttribute("xPeriodos", Periodos);
//				
//				// ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
//				// Obtenemos el periodo activo
//				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
//				
//				Integer idPeriodo = 0;
//				Integer idTipoOrden = 9;
//				
//				for(TblDctosPeriodo P : PeriodoActivo) {
//					
//					idPeriodo = P.getIdPeriodo();
//					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
//					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
//				
//				}
//				
//				List<TblDctosOrdenesDTO> CuentaFacturado =  tblDctosOrdenesService.PeriodoFacturado(idLocal, idTipoOrden, idPeriodo);
//				
//				Integer Cuenta = 0;
//				
//				for(TblDctosOrdenesDTO C : CuentaFacturado) {
//					
//					Cuenta = C.getCuenta();
//				}
//				
//								
//				if(Cuenta == 0) {
//					
//					model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodo + " NO HA SIDO FACTURADO");
//	            	model.addAttribute("url", "./menuPrincipal");
//	        		return "defaultErrorSistema";
//				}
//				// -------------------------------------------------------------------------------------------------------------------------------------------------
				
				
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
		        
		        DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String FechActual = fechaActual.format(formatterAct);

		        model.addAttribute("xFechaActual", FechActual);
		        

				
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

				
		
		return "Cliente/HistoricoPedido";
	}
	
	
	
	@PostMapping("/DescargarReporteHistoricoPedido")
	public ResponseEntity<Resource> DescargarReporteDetalleVentas(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	   
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				
				// Obtenemos los datos del JSON recibido
		        String FechaInicial = (String) requestBody.get("FechaInicial");
		        System.out.println("FechaInicial en DescargarReporteHistoricoPedido es  : " + FechaInicial);
		        
		        String FechaFinal = (String) requestBody.get("FechaFinal");
		        System.out.println("FechaFinal en DescargarReporteHistoricoPedido es  : " + FechaFinal);
		        	        
		        String idCliente = (String) requestBody.get("nuid");		        
		        String formato = (String) requestBody.get("formato");
		        				
		        String IdTipoOrdenCadena =  "9,29";
		        
		        int indicadorInicial = 1;
		        int indicadorFinal = 2;
		        
				
				int idLocal = usuario.getIdLocal();
				
			    int xIdReporte = 4100;
			    
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
				    params.put("p_titulo", xTituloReporte + " DEL " + FechaInicial + " AL " + FechaFinal);
				    params.put("p_direccion", L.getDireccion());
				    params.put("p_idLocal", idLocal);
				    params.put("p_indicadorINI", IndicadorINICIAL);
				    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
				    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
				    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
				    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
			    	
			    }
			    
			    
			    List<TercerosDTO2> listaTercero = tblTercerosService.listaUnTerceroFachada(idLocal, idCliente);
			    
			    for(TercerosDTO2 tercero : listaTercero) {
			    	
			    	params.put("p_tercero", "CLIENTE " + tercero.getIdCliente().trim() + " - " + tercero.getNombreTercero() );
			    }
			    
			    
			    List<TblDctosDTO4>  lista = null;
			    

		        	
		        	lista = TblDctosService.listaHistoriaDetalle(idLocal, idCliente, FechaInicial, FechaFinal, indicadorInicial, indicadorFinal);
		       
			    
		        	System.out.println("formato es : " + formato);
		        	System.out.println("xFileNameReporte es : " + xFileNameReporte);
		        	System.out.println("xPathReport es : " + xPathReport);
		    
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
				    
				    // Configura la respuesta HTTP
				    return ResponseEntity.ok()
				            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
				            .contentLength(dto.getLength())
				            .contentType(mediaType)
				            .body(streamResource);
		}
	
	
	
	
	
	@PostMapping("/ConsultarHistoricoPedido-Post")
	public ModelAndView ConsultarHistoricoPedidoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerPeriodo-Post");

	    // Obtenemos los datos del JSON recibido
	    String FechaInicial = (String) requestBody.get("FechaInicial");
	    String FechaFinal = (String) requestBody.get("FechaFinal");
	    String idCliente = (String) requestBody.get("nuid");
	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/ConsultarHistoricoPedido?idCliente=" + idCliente + "&FechaInicial=" + FechaInicial + "&FechaFinal=" + FechaFinal);
	    return modelAndView;
	}
	
	
	
	
	@GetMapping("/ConsultarHistoricoPedido")
	public String ConsultarHistoricoPedido(@RequestParam(name = "idCliente", required = false) String idCliente, String FechaInicial, String FechaFinal,  HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /ConsultarHistoricoPedido con idCliente: " + idCliente);
		
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

	           Integer xIdTipoTerceroCliente = 1;
		   
	           List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), idCliente, xIdTipoTerceroCliente);
				
				for(TblTerceros L : listaTercero) {
					
					model.addAttribute("xEstado", L.getEstado());
					model.addAttribute("xNuid", L.getIdCliente());
					model.addAttribute("xNombreTercero", L.getNombreTercero());
					model.addAttribute("xRuta", L.getIdRuta());
					
				}
	        
				 int indicadorInicial = 1;
			     int indicadorFinal = 2;

				
				
				List<TblDctosDTO4> listaOrden = TblDctosService.listaOrdenPeriodo(usuario.getIdLocal(), idCliente, FechaInicial, FechaFinal, indicadorInicial, indicadorFinal);
				model.addAttribute("xlistaOrden", listaOrden);
				
			
			return "Cliente/DetalleHitoricoPedido";

	}
	
	
	
	@PostMapping("/MostrarDetallePedido")
	public ResponseEntity<Resource> MostrarDetallePedido(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	   
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				
				// Obtenemos los datos del JSON recibido
		        String idDcto = (String) requestBody.get("idDctoStr");
		        System.out.println("idDcto en MostrarDetallePedido es  : " + idDcto);
		        Integer xIdDcto = Integer.parseInt(idDcto);	
		        
		        String idCliente = (String) requestBody.get("nuid");
		        
		        String idTipoOrdenStr = (String) requestBody.get("idTipoOrdenStr");
		        Integer xIdTipoOrdenNota = Integer.parseInt(idTipoOrdenStr);	
		        
		      //  Integer xIdTipoOrdenNota = 29;
		        
		        Integer xidOrden = TblDctosService.ObtenerIdOrden(idLocal, xIdTipoOrdenNota, xIdDcto);
                System.out.println("xidOrden en MostrarDetallePedido es  : " + xidOrden);
                
                
		        int xIdReporte = 1401;
		        
		        String formato = "PDF";
			    
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
				    
				    // Configura la respuesta HTTP
				    return ResponseEntity.ok()
				            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
				            .contentLength(dto.getLength())
				            .contentType(mediaType)
				            .body(streamResource);
		}
	
	
	

}
