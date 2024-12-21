package com.marketing.Controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
import com.marketing.Projection.TblDctosPeriodoDTO;
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
import com.marketing.Utilidades.ProcesoConfirmaDiferido;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaCredito;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.UtilidadesIP;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class AjusteConsumoController {
	
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
	
	
	@GetMapping("/AjusteConsumo")
	public String ajusteConsumo(HttpServletRequest request,Model model) {
		
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
					
					session.removeAttribute("pantalla"); //Se remueve de la session el valor de pantalla					
					String pantalla = "AjusteConsumo";
					
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
				
								
				if(Cuenta == 0) {
					
					model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodo + " NO HA SIDO FACTURADO");
	            	model.addAttribute("url", "./menuPrincipal");
	        		return "defaultErrorSistema";
				}
				
				
			
				
				
				
		
		return "Cliente/AjusteConsumo";
	}
	
	
	
	
	
	@PostMapping("/TraerDctoAjuste-Post")
	public ModelAndView TraerDctoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerPeriodo-Post");

	    // Obtenemos los datos del JSON recibido
	    String idCliente = (String) requestBody.get("idCliente");
	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerDctoAjuste?idCliente=" + idCliente);
	    return modelAndView;
	}
	
	
	
	
	@GetMapping("/TraerDctoAjuste")
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
		
		Integer idTipoTercero = 1;

		    
		   
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
			
			
			int xIdTipoConsumo = 4;
			
			int xIdPeriodoAnterior  = tblDctosPeriodoService.listaAnteriorFCH(idPeriodo, usuario.getIdLocal());
			System.out.println(" xIdPeriodoAnterior es: " + xIdPeriodoAnterior);
			
			
			
			List<TercerosDTO2> LecturaListaCliente = tblTercerosService.listaLecturaClienteFCH(usuario.getIdLocal(), idPeriodo, xIdTipoConsumo, xIdPeriodoAnterior, idCliente);
			System.out.println(" LecturaListaCliente es: " + LecturaListaCliente);
			model.addAttribute("xLecturaListaCliente", LecturaListaCliente);
			
			ArrayList<TblTipoCausaNota> EstadoLectura = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(2);
			model.addAttribute("xEstadoLectura", EstadoLectura);
			
			model.addAttribute("xIdPeriodo", idPeriodo);
			
			
	        


			
			return "Cliente/DocumentoAjusteConsumo";


	}
	
	
	
	@PostMapping("/FinalizarAjuste-Post")
	@ResponseBody
	public ResponseEntity<Resource>  FinalizarFinanciacionPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    Integer idLocal = usuario.getIdLocal();
	    
	    Integer idTipoOrden = 9;
	   
	    System.out.println("SI ENTRÓ A  FinalizarAjuste");

	        // Obtenemos los datos del JSON recibido
	        String IdDcto = (String) requestBody.get("xIdDcto");
	        Integer xIdDcto = Integer.parseInt(IdDcto);
	        
	        
	        
	        Integer xIdOrden = TblDctosService.ObtenerIdOrden(idLocal, idTipoOrden, xIdDcto);
	        
	        String xIdCliente = (String) requestBody.get("xIdCliente");
	        
	        String IdPeriodo = (String) requestBody.get("xIdPeriodo");
	        Integer xIdPeriodo = Integer.parseInt(IdPeriodo);
	        
	        String LecturaMedidor = (String) requestBody.get("xLecturaMedidor");
	        Double xLecturaMedidor = Double.parseDouble(LecturaMedidor);
	        
	        String LecturaMedidorAnterior = (String) requestBody.get("xLecturaMedidorAnterior");
	        Double xLecturaMedidorAnterior = Double.parseDouble(LecturaMedidorAnterior);
	        
	        String IdCausa = (String) requestBody.get("xIdCausa");
	        Integer xIdCausa = Integer.parseInt(IdCausa);
	        
	        
	        System.out.println("xLecturaMedidor es " + xLecturaMedidor);
	        System.out.println("xLecturaMedidorAnterior es " + xLecturaMedidorAnterior);

	        
	        //
	        int estadoActivo = 9;
			
			 // Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        String strFechaVisita = fechaActual.format(formatter);
			
			
			List<TblAgendaLogVisitas> visita = tblAgendaLogVisitasService.seleccionaVisitaEstadoxFecha(estadoActivo, strFechaVisita, IdUsuario, usuario.getIdLocal());
			
			String xCantidadStr = "1";
			
			int xIdLog = 0;
			int xIdUsuario = 0;
			String xIdTercero = "";

			
			for(TblAgendaLogVisitas V : visita) {
				
				xIdLog = V.getIDLOG();
				xIdUsuario = V.getIDUSUARIO();
				xIdTercero = V.getIdCliente();
				
			}
	        
	        
			double xCantidad = xLecturaMedidor  - xLecturaMedidorAnterior;
			
			Integer xIdTipoOrden = 9;
	        int xIdTipoOrdenNotaTemporal = xIdTipoOrden + 50;
	        
	        
			int xIdOrdenMax = procesoAjusteConsumoCliente.obtieneIdOrden(idLocal, xIdTipoOrdenNotaTemporal, xIdLog, xIdPeriodo, xIdCliente, xLecturaMedidor, xIdUsuario, xCantidad, xIdCausa);
			
			 List<TercerosDTO2> listaTercero =  tblTercerosService.listaUnTerceroFachada(idLocal, xIdCliente);
             
        	 String xIdFormaPago = "";
             String xEmail = "";
             String xFax = "";
             String xNombreTercero = "";
             
             for(TercerosDTO2 L : listaTercero) {
             	
             	xIdFormaPago = L.getIdFormaPago().toString();
             	xEmail = L.getEmail();
             	xFax = L.getTelefonoCelular();
             	xNombreTercero = L.getNombreTercero();
             }
	        
	        
             int xIdTipoConsumo = 4;
             
             int IdNovedadLectura = 1;
             

	        
             tblDctosOrdenesDetalleRepo.ingresaCreaLecturaConsumoUnCliente(xIdTipoOrdenNotaTemporal, xIdOrdenMax, xCantidad, xLecturaMedidor, IdNovedadLectura, xIdTipoConsumo, idLocal, xIdCliente);
             
             
             tblDctosOrdenesDetalleRepo.ingresaSuntuarioCliente(idLocal, xIdCliente, xIdPeriodo);
             
             tblDctosOrdenesDetalleRepo.actualizaSuntuarioCliente(idLocal, xIdCliente);
             
             tblDctosOrdenesDetalleRepo.ingresaComplementarioCliente(idLocal, xIdCliente, xIdPeriodo);
             
             tblDctosOrdenesDetalleRepo.actualizaComplementarioCliente(idLocal, xIdCliente);
             
             
             //-- Ingresa Subsidio ( original )
             tblDctosOrdenesDetalleRepo.ingresaClienteSuntuarioAlcantarillado(idLocal, xIdCliente);
             
             tblDctosOrdenesDetalleRepo.actualizaClienteSuntuarioAlcantarillado(idLocal, xIdCliente);
             
             tblDctosOrdenesDetalleRepo.ingresaClienteComplementarioAlcantarillado(idLocal, xIdCliente);
             
             tblDctosOrdenesDetalleRepo.actualizaClienteComplementarioAlcantarillado(idLocal, xIdCliente);
             
             
             //--------- Inicia Liquidacion Subsidio-----------------------*/
             int xIdSignoSubsidio = -1;
             int xIdTipoCategoriaSubsidio = 11;
             int xIdTipo_ConsumoSub = 21;
             
            // ( int IdLocal, int IdTipoOrden, int idOrden, int xIdSigno, int IdTipo, String idCliente, int idPeriodo)
             tblDctosOrdenesDetalleRepo.ingresaCategoriaConsumoCliente(idLocal, xIdTipoCategoriaSubsidio, xIdOrdenMax, xIdSignoSubsidio, xIdTipo_ConsumoSub, xIdCliente, xIdPeriodo);
             
             
           //--------- Inicia Liquidacion Contribucion-------------------*/
             int xIdSignoContribucion = 1;
             int xIdTipoCategoriaContribucion = 12;
             int xIdTipo_ConsumoContribucion = 22;
             
             
             tblDctosOrdenesDetalleRepo.ingresaCategoriaConsumoCliente(idLocal, xIdTipoCategoriaContribucion, xIdOrdenMax, xIdSignoContribucion, xIdTipo_ConsumoContribucion, xIdCliente, xIdPeriodo);
             
             
             tblDctosOrdenesDetalleRepo.ingresaAnulaConsumoUnCliente(xIdTipoOrdenNotaTemporal, xIdOrdenMax, idLocal, xIdTipoOrden, xIdPeriodo, xIdCliente);
             
             
             //---
             int xIndicador = 1;
             double xCero = 0.0;
             int xUno = 1;
             int CeroInt = 0;

             //--
             String xNada = "";
             
             int xIdTipoOrdenNota = 29;
             
//             (int idLocal, int IdTipoOrden, int IdOrden, String strFechaVisita, int Estado, String IdCliente, int IdUsuario, int IdOrigen, int IdLog, String FechaEntregaSqlServer, 
//   				  String TipoDcto, String Email, String fax, String Contacto, String Observacion, String DireccionDespacho, String CiudadDespacho, String FormaPago, String OrdenCompra,
//   				  int DescuentoComercial, int ImpuestoVenta, int IdRazon, int IdEstadoTx, int IdTipoTx, int NumeroOrden, int IdResponsable, int DiasHistoria, int DiasInventario, int IdPeriodo, 
//   				  Double VrTotalDiferir, int CuotaDiferir, Double PorcentajeInteresADiferir, Double VrInteresADiferir, Double Promedio, String HistoriaConsumo, Double PromedioEstrato)
             
             tblDctosOrdenesRepo.ingresaPedido(idLocal, xIdTipoOrdenNota, xIdOrdenMax, strFechaVisita, xUno, xIdCliente, xIdUsuario, xUno, xIdLog, strFechaVisita, 
            		 xIdTipoOrden.toString(), xEmail, xFax, xNada, xNada, xNada, xNada, xNada, xNada,
            		 CeroInt, CeroInt, CeroInt, xUno, xUno, CeroInt, CeroInt, CeroInt, CeroInt, xIdPeriodo, 
            		 xCero, CeroInt, xCero, xCero, xCero, xNada, xCero);
             
             
             
             
             Integer xIdDctoMax = TblDctosService.maximoDctoLocalIndicador(idLocal, xIdTipoOrdenNota, xIndicador) + 1;
             
             
             List<TblDctosOrdenesDetalleDTO> liquidaOrdenDetalle = tblDctosOrdenesDetalleService.liquidaOrdenDetalleFCH(idLocal, xIdTipoOrdenNotaTemporal, xIdLog);
             
             Double xVrVentaSinIva = 0.0;
             Double xVrIva = 0.0;
             
             for(TblDctosOrdenesDetalleDTO orden : liquidaOrdenDetalle) {
            	 
            	 xVrVentaSinIva = orden.getVrVentaSinIva();
            	 xVrIva = orden.getVrIvaVenta();
            	 
             }
             
             
//             (int idLocal, int IdTipoOrden, int IdOrden, int IdDcto, int Indicador, String FechaDctoSqlServer, Double VrBaseSinRedondeo, int VrPago, int Estado, Double VrIva, 
//        			 int IdTipoNegocio, int VrRteFuente, Double VrDescuento, int VrRteIva, int VrRteIca, String NombreTercero, int IdUsuario, String IdCliente, int DiasPlazo, int PorcentajeDscto, 
//        			 int IdCausa, String IdDctoNitCC, String FechaDctoNitCCSqlServer, int VrPagarDctoNitCC, int VrDsctoFcro, Double VrCostoMV, int IdLocalCruce, int IdTipoOrdenCruce, int IdDctoCruce, 
//        			 int IdPeriodo, int IdVendedor, Double VrImpoconsumo, Double VrCostoIND, int IdOrdenCruce, int EtapaSTR, int EnvioFE)
             
             tblDctosRepo.ingresaDcto(idLocal, xIdTipoOrdenNota, xIdOrdenMax, xIdDctoMax, xIndicador, strFechaVisita, xVrVentaSinIva, CeroInt, xUno, xVrIva, 
            		 xUno, CeroInt, xCero, CeroInt, CeroInt, xNombreTercero, xIdUsuario, xIdCliente, CeroInt, CeroInt, 
            		 CeroInt, xIdDctoMax.toString(), strFechaVisita, CeroInt, CeroInt, xCero, idLocal, xIdTipoOrden, xIdDcto,
            		 xIdPeriodo, xIdUsuario, xCero, xCero, xIdOrden, CeroInt, CeroInt);
             
             
             
             tblDctosOrdenesDetalleRepo.actualiza(xIdTipoOrdenNota, xIdOrdenMax, idLocal, xIdTipoOrdenNotaTemporal, xIdOrdenMax);
             
             
             List<TblLocales> listaUnLocal = tblLocalesService.ObtenerLocal(idLocal);
                       
             //
             int xEstadoAjuste = 0;
             int xEstadoAjusteDecena_SI = 1;
             int xEstadoAjusteCentena_SI = 3;
             
             for(TblLocales local : listaUnLocal) {
            	 
            	 xEstadoAjuste = local.getEstadoAjusteCentena();
             }
             
             
           //--- Ajuste Decena ( posterior a facturado )
             if ((xEstadoAjusteDecena_SI == xEstadoAjuste)) {

                 //
                 int xIdTipoAjusteDecena = 23;

                 tblDctosOrdenesDetalleRepo.ingresaTipoxCliente(idLocal, xIdTipoAjusteDecena, xIdTipoOrdenNota, xIdPeriodo, xIdCliente);
                

                 // Actualiza Dctos       
                 tblDctosRepo.actualizaDctoxCliente(idLocal, xIdPeriodo, xIdTipoOrdenNota, xIdCliente);

             }
             
             
           //--- Ajuste Centena ( posterior a facturado )
             if ((xEstadoAjusteCentena_SI == xEstadoAjuste)) {

                 //
                 int xIdTipoAjusteDecena = 23;

                 tblDctosOrdenesDetalleRepo.ingresaTipoCentenaxCliente(idLocal, xIdTipoAjusteDecena, xIdTipoOrdenNota, xIdPeriodo, xIdCliente);

                 // Actualiza Dctos       
                 tblDctosRepo.actualizaDctoxCliente(idLocal, xIdPeriodo, xIdTipoOrdenNota, xIdCliente);

             }
             
             
	        
	        String formato = "PDF";

	        
            
            
            // -------------------------------------------------------------------------------------- Reporte    -----------------------------------------------------
	        

	        int xIdReporte = 1401;
		    
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
		    
		    List<TblDctosOrdenesDetalleDTO> listaOrden =  tblDctosOrdenesDetalleService.listaUnLocalOrden(xIdOrdenMax, xIdTipoOrdenNota, idLocal);
		    
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
		    
		    List<TblTerceros> tercero = tblTercerosService.listaUnTerceroFCH(idLocal, xIdCliente, idTipoTercero);
		    
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
		    List<TblDctosDTO> listaUnDcto = TblDctosService.listaUnDctoFCH(idLocal, xIdTipoOrdenNota, xIdOrdenMax);
		    
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
            lista = tblDctosOrdenesDetalleService.detallaUnPedidoOrden(idLocal, xIdTipoOrdenNota, xIdOrdenMax);
		    	
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
	        
			    
			    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
			    
			    int xEstadoTerminado = 1;
			    int xEstadoProgramado = 9;
			    
	        	 tblAgendaLogVisitasRepo.actualizaVisita(xEstadoTerminado, strFechaVisita, IdUsuario, xEstadoProgramado);
			    
			    
			    //
                boolean xOkFacturado = true;
			    
			    
			    List<TblLocales> LocalObtenido = tblLocalesService.ObtenerLocal(idLocal);
                
                int xEstadoAjusteCentena = 0;
                
                String xCodigoIAC = "";
                int xEstadoGeneraIAC = 0;
                
                for(TblLocales L : LocalObtenido) {
                	xEstadoAjusteCentena = L.getEstadoAjusteCentena();
                	xCodigoIAC = L.getCodigoIAC();
                	xEstadoGeneraIAC = L.getEstadoGeneraIAC();
                }
			    
			    
			    
			    
			
			    //
                int xEstadoGeneraIAC_SI = 1;
                
                
             // Genera imagen IAC CODE128 ( posterior a facturado )
                if ((xOkFacturado) && (xEstadoGeneraIAC_SI == xEstadoGeneraIAC)) {

                    // TODO code application logic here
                    
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
                    final String xIdClienteFinal = xIdCliente; //  0 ( son todos)                 

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
			    
			    
			    
			    
			    
			    

		    
		    
	     // Configura la respuesta HTTP
		    return ResponseEntity.ok()
		            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
		            .contentLength(dto.getLength())
		            .contentType(mediaType)
		            .body(streamResource);
	   
	    
	}
	
	
	
	@PostMapping("/RenumerarAjuste-Post")
	public ModelAndView RenumerarAjustePost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ActualizarSuscriptor");

	    // Obtenemos los datos del JSON recibido
	    String idTercero = (String) requestBody.get("xIdCliente");
	    //Integer idTercero = Integer.parseInt(idTerceroString);
	    
	    String idPeriodo = (String) requestBody.get("xIdPeriodo");

	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/RenumerarAjuste?idTercero=" + idTercero + "&idPeriodo=" + idPeriodo);
	    return modelAndView;
	}
	
	
	@GetMapping("/RenumerarAjuste")
	public String RenumerarAjuste(@RequestParam(name = "idTercero", required = false) String idTercero,
            				@RequestParam(name = "idPeriodo", required = false) String idPeriodo,
            				HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /Renumerar");
		
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
		
		
		List<String> listaIdClientes = new ArrayList<>(); // Crear una lista de String
		
		listaIdClientes.add(idTercero); // Agregar el valor de idCliente a la lista


		    Integer idPeriodoInt = Integer.parseInt(idPeriodo);
		    
		    int xIdTipo = 4;
		    
		    Integer xIdPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(idPeriodoInt, usuario.getIdLocal());
		    
		    List<TercerosDTO2> lista = tblTercerosService.listaLecturaRutaTxPorCliente(usuario.getIdLocal(), xIdPeriodoAnterior, xIdTipo, idPeriodoInt, listaIdClientes);
		    
		    
		    
		    for(TercerosDTO2 L : lista) {
		    	
		    	model.addAttribute("xnombreTercero", L.getNombreTercero());
		    	model.addAttribute("xnuid", L.getIdCliente());
		    	
		    	model.addAttribute("xLecturaAnterior", L.getLecturaAnterior());
		    	
		    	model.addAttribute("xRuta", L.getNombreRuta());
		    	model.addAttribute("xEstracto", L.getNombreEstracto());
		    }
		    
		    
		    model.addAttribute("xIdPeriodo", idPeriodoInt);

			
			return "Cliente/RenumerarAjuste";


	}
	
	
	
	@PostMapping("/ActualizarLecturaAjuste-Post")
	@ResponseBody
	public ModelAndView ActualizarLectura(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    Integer idLocal = usuario.getIdLocal();
	    
	    

	    System.out.println("SI ENTRÓ A  /ActualizarSuscriptor-Post");

	        // Obtenemos los datos del JSON recibido
	        String idPeriodo = (String) requestBody.get("idPeriodo");
	        String LecturaCorregida = (String) requestBody.get("LecturaCorregida");  
	        Double xLecturaMedidorAnteriorNueva = Double.parseDouble(LecturaCorregida);
	        
	        String xIdCliente = (String) requestBody.get("nuid"); 
	        
	        Integer xIdPeriodo = Integer.parseInt(idPeriodo);
	        
	        int xIdTipoConsumo = 4;
	        
	        //Obtenemos el Periodo anterior al actual 
	        Integer xIdPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(xIdPeriodo, idLocal);
	        System.out.println("xIdPeriodoAnterior es  ActualizarLecturaAjuste-Post es: " + xIdPeriodoAnterior);
	        
	        
	        List<TblDctosOrdenesDetalleDTO2> lastOrdenLista = tblDctosOrdenesDetalleService.listaLastOrdenFCH(idLocal, xIdCliente, xIdPeriodoAnterior);
	        
	        int xIdTipoOrdenLast = 0;
            int xIdOrdenLast = 0;
            
            for(TblDctosOrdenesDetalleDTO2 lista : lastOrdenLista) {
            	
            	xIdTipoOrdenLast = lista.getIdTipoOrden();
            	xIdOrdenLast = lista.getIdOrden();
            }
	        
	        
            tblDctosOrdenesDetalleRepo.actualizaLecturaLast(xLecturaMedidorAnteriorNueva, idLocal, xIdTipoOrdenLast, xIdOrdenLast, xIdPeriodoAnterior);
            
            List <TblDctosPeriodo> listaUnFCH = tblDctosPeriodoService.listaUnFCH(xIdPeriodo, idLocal);
	        
	        
            Integer idTipoTercero = 1;
            
            
            List<TercerosDTO2> listaLectura = tblTercerosService.listaLecturaClienteFCH(idLocal, xIdPeriodo, xIdTipoConsumo, xIdPeriodoAnterior, xIdCliente);
            
            Double xConsumoM3 = 0.0;
            

	        
            List<TercerosDTO2> LecturaListaCliente = tblTercerosService.listaLecturaClienteFCH(usuario.getIdLocal(), xIdPeriodo, xIdTipoConsumo, xIdPeriodoAnterior, xIdCliente);
			System.out.println(" LecturaListaCliente es: " + LecturaListaCliente);
			model.addAttribute("xLecturaListaCliente", LecturaListaCliente);
	        
		    
	        System.out.println("LECTURA ACTUALIZADA CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    
		    
		    
		    // Redirige a la vista y le pasamos el parametro de idTercero
		    ModelAndView modelAndView = new ModelAndView("redirect:/TraerDctoAjuste?idCliente=" + xIdCliente);
		    return modelAndView;
	   
	    
	}

}
