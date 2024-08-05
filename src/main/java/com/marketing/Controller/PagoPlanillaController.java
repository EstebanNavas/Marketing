package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblMediosPago;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
import com.marketing.Projection.TblPagosDTO;
import com.marketing.Projection.TblPlusDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosMediosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosRepo;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblCategoriasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblMediosPagoService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ProcesoAjusteConsumoCliente;
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
public class PagoPlanillaController {
	
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
	TblMediosPagoService tblMediosPagoService;
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	 TblCategoriasService tblCategoriasService;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	TblPagosMediosRepo tblPagosMediosRepo;
	
	@Autowired
	TblPagosRepo tblPagosRepo;
	

	
	
	
	
	@GetMapping("/PagoPlanilla")
	public String pagoPlanilla(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer IdUsuario = usuario.getIdUsuario();

				
				HttpSession session = request.getSession();

				
				 // Obtener la fecha actual
		        LocalDate fechaActual = LocalDate.now();

		        // Formatear la fecha como un String
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		        String strFechaVisita = fechaActual.format(formatter);
		        
		        System.out.println("strFechaVisita  es" + strFechaVisita);
		        

		        DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String FechActual = fechaActual.format(formatterAct);

		        model.addAttribute("xFechaActual", FechActual);
				

				
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
				
				
				//---------------------------------------------------- Validacion pagos temporales por idLog ---------------------------------------------------
				Integer idLog = tblAgendaLogVisitasService.ObtenerIdLogActivo(IdUsuario);
				
				int xIdTipoOrdenPagoProceso = 59;
				int xIndicador = 1;	
				
				List<TblDctosDTO> cuentaplanilla = TblDctosService.listaCuentaPlanilla(idLocal, idTipoOrden, idLog);
				
				List<TblPagosDTO> pagoTemporalLista = tblPagosService.listaPagoTemporalFCH(idLocal, xIdTipoOrdenPagoProceso, xIndicador, idLog);

				List<TblPagosDTO> PagoTemporalTotal = tblPagosService.listaPagoTemporalTotal(idLocal, xIdTipoOrdenPagoProceso, idLog);
				
				Double vrPagoTotal = 0.0;
				
	            for(TblPagosDTO temporal : PagoTemporalTotal) {
	            	
	            	vrPagoTotal = temporal.getVrPago();
	            	
	            }
				
				 
				
				for(TblDctosDTO planilla : cuentaplanilla) {
					
					Integer xIdOrden = planilla.getIdOrden();
					
					if(xIdOrden != null) {
						
						//Obtenemos las variables de la session
						String FechaPago = (String) session.getAttribute("FechaPago");
						
						String xIdVendedor = (String) session.getAttribute("idVendedor");
					 	Integer IdVendedor = Integer.parseInt(xIdVendedor);
					 	
					 	String xNombreMedio = (String) session.getAttribute("xNombreMedio");
					 	
					 	
					 	String nombreUsuario = ctrlusuariosService.obtenerNombreUsuario(idLocal, IdVendedor);
						
						model.addAttribute("xCuentaplanilla", cuentaplanilla);
						model.addAttribute("xPagoTemporalLista", pagoTemporalLista);
						model.addAttribute("xVrPagoTotal", vrPagoTotal);
						
						model.addAttribute("xFechaPago", FechaPago);
					 	model.addAttribute("xIdVendedor", nombreUsuario);
					 	model.addAttribute("xNombreMedio", xNombreMedio);
					 	model.addAttribute("xIdPeriodo", idPeriodo);
					 	
					 	model.addAttribute("xIdLog", idLog);
					 	
						return "Cliente/RegistroPagosPlanilla";
					}
				}
				
				
				
				
				
				
				
				//Medios de pago
				List<TblMediosPago> MediosDePago  = tblMediosPagoService.ListaMediosDePago(idLocal);
				model.addAttribute("xMediosDePago", MediosDePago);
				
				List <CtrlusuariosDTO> usuarios = ctrlusuariosService.obtenerUsuariosActivosNivel5(idLocal);
				model.addAttribute("xUsuarios", usuarios);
				
				
		
		return "Cliente/PagoPlanilla";
	}
	
	
	@PostMapping("/RegistroPagosPlanilla-Post")
	public ResponseEntity<Map<String, String>> RegistroPagosPlanilla(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /Confirmar-Post");
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("FechaPago");
	    session.removeAttribute("idVendedor");
	    session.removeAttribute("xIdPeriodo");
	    session.removeAttribute("xIdMedio");

	    // Obtenemos los datos del JSON recibido
	    String FechaPago = (String) requestBody.get("FechaPago");
	    String idVendedor = (String) requestBody.get("idVendedor");
	    String xIdPeriodo = (String) requestBody.get("xIdPeriodo");
	    String xIdMedio = (String) requestBody.get("xIdMedio");

	    
	    session.setAttribute("FechaPago", FechaPago);
	    session.setAttribute("idVendedor", idVendedor);
	    session.setAttribute("xIdPeriodo", xIdPeriodo);
	    session.setAttribute("xIdMedio", xIdMedio);


    
	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./RegistroPagosPlanilla");

        return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/RegistroPagosPlanilla")
	public String ConfirmarNota( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		 HttpSession session = request.getSession();
		 Integer xIdUsuario = (Integer) session.getAttribute("xidUsuario");
		 
		 int idLocal = usuario.getIdLocal();
		    
		 
		 	// Obtener las variables de la sesión
		 	String FechaPago = (String) session.getAttribute("FechaPago");
		 	String xIdVendedor = (String) session.getAttribute("idVendedor");
		 	Integer IdVendedor = Integer.parseInt(xIdVendedor);
		 	
		 	
		 	String xIdPeriodo = (String) session.getAttribute("xIdPeriodo");
		 	Integer idPeriodo = Integer.parseInt(xIdPeriodo);
		 	
		 	String IdMedio = (String) session.getAttribute("xIdMedio");
		 	Integer xIdMedio = Integer.parseInt(IdMedio);
		 	
		 	
		 	
		 	
		 	 // Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        String strFechaVisita = fechaActual.format(formatter);
		 	
		 	
		 	
		 	
		 	int estadoAtendido = 1; // visitaActiva
            int estadoProgramada = 9; // visitaProgramada
            int idEstadoVisita = 1; // Programada
            
            int idTipoOrden = 9;
            
            
            Integer okLogOcupado = tblAgendaLogVisitasService.validaLogOcupado(xIdUsuario, strFechaVisita, estadoProgramada, idLocal);
            
            
            
            if (okLogOcupado != 0) {


                model.addAttribute("error", "EXISTE UN PEDIDO O PAGO EN PROCESO. DEBE TERMINAR O CANCELAR LO ACTIVO");
            	model.addAttribute("url", "./menuPrincipal");
        		return "defaultErrorSistema";

            }
            
            
            int idLog = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
            
            
            tblAgendaLogVisitasRepo.actualizaLogVisitaUsuario(estadoAtendido, xIdUsuario, estadoProgramada);
            
//            (int idLog, String idCliente, int idUsuario, int idLocalTercero, int idLocal, int idPeriodo, String fechaVisita,
//      			  int idEstadoVisita, int estado, int idTipoOrden, String fechaTxInicio)
            
            tblAgendaLogVisitasRepo.ingresaLogVisita(idLog, xIdVendedor, xIdUsuario, idLocal, idLocal, idPeriodo, strFechaVisita, idEstadoVisita, estadoProgramada, idTipoOrden, strFechaVisita);
            
            
            List<TblMediosPago> MedioDePago = tblMediosPagoService.ObtenerMedioDePago(idLocal, xIdMedio);
            
            String xNombreMedio = "";
            for(TblMediosPago medio : MedioDePago) {
            	
            	xNombreMedio = medio.getNombreMedio();
            }
            
            
            String nombreUsuario = ctrlusuariosService.obtenerNombreUsuario(idLocal, IdVendedor);
            
		    
		 	model.addAttribute("xFechaPago", FechaPago);
		 	model.addAttribute("xIdVendedor", nombreUsuario);
		 	model.addAttribute("xNombreMedio", xNombreMedio);
		 	model.addAttribute("xIdPeriodo", idPeriodo);
		 	
		 	model.addAttribute("xIdLog", idLog);
			
			return "Cliente/RegistroPagosPlanilla";
			
		

	}
	
	
	
	@PostMapping("/MostrarDetallePago-Post")
	public ResponseEntity<Map<String, String>> MostrarDetallePago(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /Confirmar-Post");
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdPeriodo");
	    session.removeAttribute("idCliente");
	    session.removeAttribute("xNombreMedio");
	    session.removeAttribute("xFechaPago");
	    session.removeAttribute("xIdVendedor");

	    // Obtenemos los datos del JSON recibido
	    String idCliente = (String) requestBody.get("idTercero");
	    String xIdPeriodo = (String) requestBody.get("xIdPeriodo");
	    String xIdVendedor = (String) requestBody.get("xIdVendedor");
	    String xNombreMedio = (String) requestBody.get("xNombreMedio");
	    String xFechaPago = (String) requestBody.get("xFechaPago");
	    



	    session.setAttribute("xIdPeriodo", xIdPeriodo);
	    session.setAttribute("idCliente", idCliente);
	    session.setAttribute("xIdVendedor", xIdVendedor);
	    session.setAttribute("xNombreMedio", xNombreMedio);
	    session.setAttribute("xFechaPago", xFechaPago);


    
	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./MostrarDetallePago");

        return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/MostrarDetallePago")
	public String MostrarDetallePago( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		 HttpSession session = request.getSession();
		 Integer xIdUsuario = (Integer) session.getAttribute("xidUsuario");
		 
		 int idLocal = usuario.getIdLocal();
		    
		 
		 // Obtener las variables de la sesión
		 	String xIdPeriodo = (String) session.getAttribute("xIdPeriodo");
		 	Integer idPeriodo = Integer.parseInt(xIdPeriodo);
		 	
		 	String idCliente = (String) session.getAttribute("idCliente");
		 	String xIdVendedor = (String) session.getAttribute("xIdVendedor");
		 	String xNombreMedio = (String) session.getAttribute("xNombreMedio");
		 	String xFechaPago = (String) session.getAttribute("xFechaPago");
		 	
		 	
		 	int xIdTipoTerceroCliente = 1;
		 	int idTipoOrden =9;
	        
		 	
	        
		 	
		 	List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), idCliente, xIdTipoTerceroCliente);
			
			for(TblTerceros L : listaTercero) {
				
				model.addAttribute("xEstado", L.getEstado());
				model.addAttribute("xNuid", L.getIdCliente());
				model.addAttribute("xNombreTercero", L.getNombreTercero());
				model.addAttribute("xRuta", L.getIdRuta());
				
			}
		 	
			System.out.println("idLocal es " + idLocal);
			System.out.println("idCliente es " + idCliente);
			System.out.println("idTipoOrden es " + idTipoOrden);
			System.out.println("idPeriodo es " + idPeriodo);
			
			List<TblDctosDTO> CuentaPeriodoCLiente = TblDctosService.listaCuentaPeriodoCliente(idLocal, idCliente, idTipoOrden, idPeriodo);
			
			
			for(TblDctosDTO cuenta : CuentaPeriodoCLiente) {
				
				//Double xDiferencia = cuenta.getVrSaldo() - (cuenta.getVrPago() + cuenta.getVrDsctoFcro() + cuenta.getVrRteFuente() + cuenta.getVrRteIva() + cuenta.getVrRteIca());
				
				Double getVrSaldo = cuenta.getVrSaldo();
				Double getVrPago = cuenta.getVrPago();
				Double getVrDsctoFcro = cuenta.getVrDsctoFcro();
				Double getVrRteFuente = cuenta.getVrRteFuente();
				Double getVrRteIva = cuenta.getVrRteIva();
				Double getVrRteIca = cuenta.getVrRteIca();
				
				
				System.out.println("getVrSaldo es " + getVrSaldo);
				System.out.println("getVrPago es " + getVrPago);
				System.out.println("getVrDsctoFcro es " + getVrDsctoFcro);
				System.out.println("getVrRteFuente es " + getVrRteFuente);
				System.out.println("getVrRteIva es " + getVrRteIva);
				System.out.println("getVrRteIca es " + getVrRteIca);
				
			}
			
		 	

		 	model.addAttribute("xIdPeriodo", idPeriodo);
		 	model.addAttribute("xCuentaPeriodoCLiente", CuentaPeriodoCLiente);
		 	model.addAttribute("xIdVendedor", xIdVendedor);
		 	model.addAttribute("xNombreMedio", xNombreMedio);
		 	model.addAttribute("xFechaPago", xFechaPago);
		 	
		 	

			
			return "Cliente/PagoCxC";
			
		

	}
	
	
	@PostMapping("/PagoTotal-Post")
	public ResponseEntity<Map<String, String>> PagoTotal(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /Confirmar-Post");
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdPeriodo");
	    session.removeAttribute("idCliente");

	    // Obtenemos los datos del JSON recibido
	    String idOrden = (String) requestBody.get("idOrden");
	    String FechaPago = (String) requestBody.get("xFechaPago");

	    session.setAttribute("idOrden", idOrden);
	    session.setAttribute("FechaPago", FechaPago);
	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./PagoTotal");

        return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/PagoTotal")
	public String PagoTotal( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		 HttpSession session = request.getSession();
		 Integer xIdUsuario = (Integer) session.getAttribute("xidUsuario");
		 
		 int idLocal = usuario.getIdLocal();
		    
		 
		 // Obtener las variables de la sesión
		 	String xIdVendedor = (String) session.getAttribute("idVendedor");
		 	Integer IdVendedor = Integer.parseInt(xIdVendedor);
		 	
		 	String idOrdenStr = (String) session.getAttribute("idOrden");
		 	Integer xIdOrden = Integer.parseInt(idOrdenStr);
		 	
		 	String FechaPago = (String) session.getAttribute("FechaPago");
		 	
		 	String IdMedio = (String) session.getAttribute("xIdMedio");
		 	Integer xIdMedio = Integer.parseInt(IdMedio);
		 	
		 	String xNombreMedio = (String) session.getAttribute("xNombreMedio");
		 	
		 	int xIdTipoTerceroCliente = 1;
		 	int idTipoOrden = 9;
		 	int xIdTipoOrdenPagoProceso = 59;
		 	int estadoActivo = 9;
		 	
		 	int xIndicador = 1;
		 	
		 	
		 	
		 // Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
			
			Integer idPeriodo = 0;
			
			for(TblDctosPeriodo P : PeriodoActivo) {
				
				idPeriodo = P.getIdPeriodo();
			
			}
		 	
		 	
		 	
		 	int idMaximaPlanilla = tblPagosService.maximaPlanilla(idLocal, xIdTipoOrdenPagoProceso) + 1;
		 	
		 	
		 	List<TblDctosDTO> listaUnDcto = TblDctosService.listaUnDctoOrden(idLocal, idTipoOrden, xIdOrden, xIndicador);
		 	
		 	
		 	String idCiente = "";
		 	Double idDcto = 0.0;
		 	Integer xIdTipoOrden = 0;
		 	for(TblDctosDTO listaDcto : listaUnDcto ) {
		 		
		 		idCiente = listaDcto.getIdCliente();
		 		idDcto = listaDcto.getIdDcto();
		 		xIdTipoOrden = listaDcto.getIdTipoOrden();
		 	}

		 	
		 	
		 	
		 	
		 	List<TblDctosDTO> CuentaDetalle = TblDctosService.listaCuentaDetalladoOrdenFCH(idLocal, idCiente, idTipoOrden, idDcto.intValue());
		 	
		 	Double VrSaldo = 0.0;
		 	for(TblDctosDTO cuenta : CuentaDetalle) {
		 		
		 		VrSaldo = cuenta.getVrSaldo();
		 	}
		 	
		 	double xVSaldoDctoActualizado = VrSaldo;
		 	
		 	
		 	tblPagosMediosRepo.retiraPagoDctoTemporal(idLocal, xIdTipoOrdenPagoProceso, xIndicador, idDcto.intValue());
		 	
		 	tblPagosRepo.retiraPagoDctoTemporal(idLocal, xIdTipoOrdenPagoProceso, xIndicador, idDcto.intValue());
		 	
		 	
		 	int xIdReciboMAX = tblPagosService.maximoReciboIdLocalxIndicador(idLocal, xIdTipoOrdenPagoProceso, xIndicador) + 1;
		 	
		 	
		 	Integer idCienteInt = Integer.parseInt(idCiente);
		 	Double xCero = 0.0;
		 	int cero = 0;
		 	String nada = "";
		 	
		 	Integer idLog = tblAgendaLogVisitasService.ObtenerIdLogActivo(xIdUsuario);
		 	
//		 	(int idLocal, int idTipoOrden, int IdRecibo, int Indicador, String FechaPagoSqlServer, Double VrPago, int NitCC, int Estado, int IdUsuario,
//	  				   Double VrRteFuente, Double VrDescuento, Double VrRteIva,  Double vrRteIca, int IdPeriodo, int IdDcto, int IdDctoNitCC, int IdPlanilla, Double VrSaldo,
//	  				   int IdLog, int IdVendedor, int IdReciboCruce, Double VrPagoCambio, String Comentario)
		 	
		 	tblPagosRepo.ingresaPago(idLocal, xIdTipoOrdenPagoProceso, xIdReciboMAX, xIndicador, FechaPago, xVSaldoDctoActualizado, idCienteInt, estadoActivo, xIdUsuario, 
		 			xCero, xCero, xCero, xCero, idPeriodo, idDcto.intValue(), idCienteInt, idMaximaPlanilla, xVSaldoDctoActualizado, 
		 			idLog, IdVendedor, cero, xCero, nada);
		 	
		 	
		 	System.out.println("INGRESA PAGO OK");
		 	
		 	
		 	//
            int xEstadoOk = 1;
            int xIdBancoOk = 0;
            int xIdDctoMedioOk = 1;
            int xIdMedioEfectivo = 1;
            
            
//            (int IdTipoOrden, int IdRecibo, int Indicador, int IdMedio, String FechaCobro, int IdBanco, int IdDctoMedio, int Estado, int IdLog, 
//                    int IdLocal, int xIdDcto, int xIdPeriodo)
           
            tblPagosMediosRepo.ingresaTotalPlanilla(xIdTipoOrdenPagoProceso, xIdReciboMAX, xIndicador, xIdMedio, FechaPago, xIdBancoOk, xIdDctoMedioOk, xEstadoOk, idLog, 
            									  idLocal, idDcto.intValue(), idPeriodo);
            
            System.out.println("INGRESA PAGOPLANILLA OK");
            System.out.println("idLog es " + idLog);
            
            // Revisar la query para incluir correctamente el vrPago 
            List<TblDctosDTO> cuentaplanilla = TblDctosService.listaCuentaPlanilla(idLocal, idTipoOrden, idLog);
            
            List<TblPagosDTO> pagoTemporalLista = tblPagosService.listaPagoTemporalFCH(idLocal, xIdTipoOrdenPagoProceso, xIndicador, idLog);

            List<TblPagosDTO> PagoTemporalTotal = tblPagosService.listaPagoTemporalTotal(idLocal, xIdTipoOrdenPagoProceso, idLog);
            
            for(TblPagosDTO temporal : PagoTemporalTotal) {
            	
            	Double vrPagoTotal = temporal.getVrPago();
            	model.addAttribute("xVrPagoTotal", vrPagoTotal);
            }
            
            
            System.out.println("cuentaplanilla es " + cuentaplanilla);
            
            String nombreUsuario = ctrlusuariosService.obtenerNombreUsuario(idLocal, IdVendedor);
		 	

            model.addAttribute("xIdVendedor", nombreUsuario);
            model.addAttribute("xNombreMedio", xNombreMedio);
            model.addAttribute("xFechaPago", FechaPago);
            model.addAttribute("xIdPeriodo", idPeriodo);
            
		 	model.addAttribute("xCuentaplanilla", cuentaplanilla);
		 	model.addAttribute("xPagoTemporalLista", pagoTemporalLista);
		 	
		 	model.addAttribute("xIdLog", idLog);
		 	
		 	

			
			return "Cliente/RegistroPagosPlanilla";
			
		

	}
	

	
	
	
	@PostMapping("/PagarPlanilla")
	@ResponseBody
	public ResponseEntity<Resource>  ConfirmarPago(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException 
	{
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdLogPago");
	    
	    
	    // Obtenemos los datos del JSON recibido
	    String IdLog = (String) requestBody.get("xIdLog");
	    Integer xIdLog = Integer.parseInt(IdLog);
	    
	    Integer xIdUsuario = usuario.getIdUsuario();
	    Integer xIdLocalUsuario = usuario.getIdLocal();
	    
	    Map<String, Object> response = new HashMap<>();
	    

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
        
        
     // ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
		// Obtenemos el periodo activo
		List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(xIdLocalUsuario);
		
		Integer xPeriodo = 0;
		Integer idTipoOrden = 9;
		
		for(TblDctosPeriodo P : PeriodoActivo) {
			
			xPeriodo = P.getIdPeriodo();
			model.addAttribute("xIdPeriodo", P.getIdPeriodo());
			model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
		
		}

		int xIdTipoOrdenVenta = 9;
		int xIdTipoOrdenPagoProceso = 59;
		
		int xIdMaximaPlanilla = tblPagosService.maximaPlanilla(xIdLocalUsuario, xIdTipoOrdenVenta) + 1;
		
		System.out.println("xIdLog es : " + xIdLog);
		
		List<TblPagosDTO> PagoProceso = tblPagosService.listaPagoProceso(xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIdLog);
		
		
		System.out.println("PagoProceso es : " + PagoProceso);
		
		for(TblPagosDTO pago : PagoProceso) {
			
			
			int xIdReciboOld = pago.getIdRecibo();
            int xIndicador = pago.getIndicador();
            Double xIdDcto = pago.getIdDcto();
            
            Integer xIdDctoInt = xIdDcto.intValue();
            
            System.out.println("xIdDctoInt es " + xIdDctoInt);
            System.out.println("xIndicador es " + xIndicador);
            
            Double VrPago = pago.getVrPago();
			Double VrRteFuente = pago.getVrRteFuente();
			Double VrDsctoFcro = pago.getVrDescuento();
			Double VrRteIva = pago.getVrRteIva();
			Double VrRteIca = pago.getVrRteIca();
			
			String idCliente = pago.getNitCC();
			
			System.out.println("VrPago es " + VrPago);
			System.out.println("idCliente es " + idCliente);
   
            	
            	
//            	(Double  VrPago ,Double VrRteFuente, Double VrDsctoFcro, Double VrRteIva, Double VrRteIca, int idLocal, int IdTipoOrden, int IdDcto, int Indicador )
            	tblDctosRepo.actualizaPago(VrPago, VrRteFuente, VrDsctoFcro, VrRteIva, VrRteIca, xIdLocalUsuario, xIdTipoOrdenVenta, xIdDctoInt, xIndicador);
            	
            	int xIdReciboMAX = tblPagosService.maximoReciboIdLocalxIndicador(xIdLocalUsuario, xIdTipoOrdenVenta, xIndicador) + 1;
            	
            	
            	List<TblDctosDTO> CuentaDetallado = TblDctosService.listaCuentaDetalladoClienteFCH(xIdLocalUsuario, idCliente, xIdTipoOrdenVenta, xIdDctoInt);
            	
            	Double VrSaldo = 0.0;
            	
            	for(TblDctosDTO cuenta : CuentaDetallado ) {
            		
            		VrSaldo = cuenta.getVrSaldo();
            	}
            	
            	Double xVSaldoDctoActualizado = VrSaldo;

            	System.out.println("xVSaldoDctoActualizado es " + xVSaldoDctoActualizado);
            	
            	
            	tblPagosRepo.ingresaPagos(xIdTipoOrdenVenta, xIdReciboMAX, xIndicador, VrPago, xPeriodo, xIdDctoInt, xIdDctoInt.toString(), xIdMaximaPlanilla, 
            			xVSaldoDctoActualizado, xIdUsuario, xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIdReciboOld, xIdLog);
            	
            	System.out.println("ingresaPagos");
            	
            	tblPagosMediosRepo.ingresaPago(xIdTipoOrdenVenta, xIdReciboMAX, xIndicador, xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIdReciboOld, xIdDctoInt, xIdLog);
            	System.out.println("ingresaPago");
            	
            	tblPagosMediosRepo.retiraReciboTemporal(xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIndicador, xIdReciboOld);
            	System.out.println("retiraReciboTemporal");
            	
            	
            	tblPagosRepo.retiraReciboTemporal(xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIndicador, xIdReciboOld);
            	System.out.println("retiraReciboTemporal");
            	
           
           
			
		}
		
		
		// Obtenemos la IP del servidor
        UtilidadesIP utilidadesIP = new UtilidadesIP();
        String xIpTx = utilidadesIP.getIpServidor();
        
        int estadoProgramado = 1;
        int tareaVisita = 6;   // Cotizacion
        int xIdEstadoTxSinTx = 1;

      
        tblAgendaLogVisitasRepo.finalizaVisita(estadoProgramado, tareaVisita, xIdTipoOrdenVenta, xIdEstadoTxSinTx, xIpTx, strFechaVisita, xIdLocalUsuario, xIdLog);
        
        
        
        // -------------------------------------------------------------------------------------- Reporte    -----------------------------------------------------
		
        String formato = "PDF";
		
        int xIdReporte = 1800;
	    
	    //Obtenemos el FileName del reporte y el titulo 
	    List<TblLocalesReporte> reporte = tblLocalesReporteService.listaUnFCH(xIdLocalUsuario, xIdReporte);
	    
	    String xFileNameReporte = "";
	    String xTituloReporte = "";
	    
	    for(TblLocalesReporte R : reporte) {
	    	
	    	xFileNameReporte = R.getFileName();
	    	xTituloReporte = R.getReporteNombre();
	    }
		
		//Obtenemos la información del local que usaremos para los PARAMS del encabezado
	    List<TblLocales> Local = tblLocalesService.ObtenerLocal(xIdLocalUsuario);
		
	    Map<String, Object> params = new HashMap<>();
	    params.put("tipo", formato);
	    params.put("idLocal", xIdLocalUsuario);

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
		    params.put("p_idLocal", xIdLocalUsuario);
		    params.put("p_idPlanilla", xIdMaximaPlanilla);
		    params.put("p_idTipoOrden", xIdTipoOrdenVenta);
		    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
	    	
	    }
	    
	   	    
	    
	    
	    
	    List<TblPagosDTO> lista = null;
	    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblPagosService.listaPlanilla(xIdLocalUsuario, xIdTipoOrdenVenta, xIdMaximaPlanilla);
	    	
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
        
		    
		    //Removemos las variables de la session
		    session.removeAttribute("idOrden");
		    session.removeAttribute("FechaPago");
		    session.removeAttribute("xIdPeriodo");
		    session.removeAttribute("idCliente");
		    session.removeAttribute("xIdVendedor");
		    session.removeAttribute("xNombreMedio");
		    session.removeAttribute("xFechaPago");
		    session.removeAttribute("xIdMedio");
		    session.removeAttribute("idVendedor");
		    


	    
	    
     // Configura la respuesta HTTP
	    return ResponseEntity.ok()
	            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
	            .contentLength(dto.getLength())
	            .contentType(mediaType)
	            .body(streamResource);
	   
	    
	}
	
	
	
	
	
	@PostMapping("/RetiraPagoPlanilla")
	public ResponseEntity<Map<String, String>> RetirarCobroPermanente(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /RetiraPagoPlanilla");
	    
	    Integer IdUsuario = usuario.getIdUsuario();
	    Integer idLocal = usuario.getIdLocal();

	    // Obtenemos los datos del JSON recibido
	    Integer xIdDcto = (Integer) requestBody.get("idDcto");
	    //Integer xIdDcto = Integer.parseInt(idDcto);
	    
	    String xFechaPago = (String) requestBody.get("xFechaPago");
	    
	    int xIdTipoOrdenPagoProceso = 59;
	    int xIndicador = 1;

        
	    tblPagosMediosRepo.retiraPagoDctoTemporal(idLocal, xIdTipoOrdenPagoProceso, xIndicador, xIdDcto);
        
	    tblPagosRepo.retiraPagoDctoTemporal(idLocal, xIdTipoOrdenPagoProceso, xIndicador, xIdDcto);


        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./PagoPlanilla");

        return ResponseEntity.ok(response);
	}
	
	
	
	@PostMapping("/PagoParcial-Post")
	public ResponseEntity<Map<String, String>> PagoParcial(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /Confirmar-Post");
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("idOrden");
	    session.removeAttribute("FechaPago");
	    session.removeAttribute("idCliente");

	    // Obtenemos los datos del JSON recibido
	    String idOrden = (String) requestBody.get("idOrden");
	    String FechaPago = (String) requestBody.get("xFechaPago");
	    String idCliente = (String) requestBody.get("idCliente");

	    session.setAttribute("idOrden", idOrden);
	    session.setAttribute("FechaPago", FechaPago);
	    session.setAttribute("idCliente", idCliente);
	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./PagoParcial");

        return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/PagoParcial")
	public String PagoParcial( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		 HttpSession session = request.getSession();
		 Integer xIdUsuario = (Integer) session.getAttribute("xidUsuario");
		 
		 int idLocal = usuario.getIdLocal();
		    
		 
		 // Obtener las variables de la sesión
		 	String xIdVendedor = (String) session.getAttribute("idVendedor");
		 	Integer IdVendedor = Integer.parseInt(xIdVendedor);
		 	
		 	String idOrdenStr = (String) session.getAttribute("idOrden");
		 	Integer xIdOrden = Integer.parseInt(idOrdenStr);
		 	
		 	String IdMedio = (String) session.getAttribute("xIdMedio");
		 	Integer xIdMedio = Integer.parseInt(IdMedio);
		 	
		 	String FechaPago = (String) session.getAttribute("FechaPago");
		 	String xNombreMedio = (String) session.getAttribute("xNombreMedio");	
		 	String idCliente = (String) session.getAttribute("idCliente");
		 	
		 	int xIdTipoTerceroCliente = 1;
		 	int idTipoOrden = 9;
		 	
		 	
		 	System.out.println("idCliente es " + idCliente);
		 	System.out.println("idLocal es " + idLocal);
		 	
		 	
		 	//Obtenemos el idDcto 
		 	Integer idDcto = TblDctosService.ObtenerIdDcto(idLocal, xIdOrden, idCliente);
		 	
		 	System.out.println("idDcto es " + idDcto);
		 			 	
		 	List<TblDctosOrdenesDetalleDTO2> pagoParcialLista = tblDctosOrdenesDetalleService.listaPagoParcial(idLocal, idTipoOrden, idDcto);
		 	
		 	//Obtenemos el valor total
		 	double totalVrMedio = pagoParcialLista.stream()
				    .mapToDouble(TblDctosOrdenesDetalleDTO2::getVrMedio)
				    .sum();
		 	
		 	
		 	model.addAttribute("xTotalVrMedio", totalVrMedio);
		 	
		 	
		 	// Información Cliente
		 	List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(idLocal, idCliente, xIdTipoTerceroCliente);
			
		 	System.out.println("listaTercero es " + listaTercero);
		 	
			for(TblTerceros L : listaTercero) {
				
				model.addAttribute("xEstado", L.getEstado());
				model.addAttribute("xNuid", L.getIdCliente());
				model.addAttribute("xNombreTercero", L.getNombreTercero());
				model.addAttribute("xRuta", L.getIdRuta());
				
			}
		 	

		 	
		 	String nombreUsuario = ctrlusuariosService.obtenerNombreUsuario(idLocal, IdVendedor);
		 	

            model.addAttribute("xIdVendedor", nombreUsuario);
            model.addAttribute("xNombreMedio", xNombreMedio);
            model.addAttribute("xFechaPago", FechaPago);
            model.addAttribute("xIdOrden", xIdOrden);
            

            model.addAttribute("xPagoParcialLista", pagoParcialLista);
		 	

			
			return "Cliente/PagoParcialPlanilla";
			
		

	}
	
	
	
	@PostMapping("/GuardarPagoParcial-Post")
	public ResponseEntity<Map<String, String>> GuardarPagoParcial(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /Confirmar-Post");
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("arrVrMedioArr");
	    session.removeAttribute("arrIdReferencia");

	    // Obtenemos los datos del JSON recibido
	    String xIdOrden = (String) requestBody.get("xIdOrden");
	

    	// Obtener los valores de los arrays
	    @SuppressWarnings("unchecked")
	    List<String> xVrMedioArr = (List<String>) requestBody.get("xVrMedioArr");
	    System.out.println("xVrMedioArr es  " + xVrMedioArr);
	    
	    @SuppressWarnings("unchecked")
	    List<String> xidPluArr = (List<String>) requestBody.get("xidPluArr");
	    
	    
	    
	    String[] arrVrMedioArr = xVrMedioArr.toArray(new String[0]);
	    String[] arrIdReferencia = xidPluArr.toArray(new String[0]);
	    
	    System.out.println("arrVrMedioArr en /Confirmar-Post " + arrVrMedioArr);
	    System.out.println("arrIdReferencia en /Confirmar-Post " + arrIdReferencia);
	    
	    // Guardar las listas en la sesión
	    session.setAttribute("xIdOrden", xIdOrden);
	    session.setAttribute("arrVrMedioArr", arrVrMedioArr);
	    session.setAttribute("arrIdReferencia", arrIdReferencia);


	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./GuardarPagoParcial");

        return ResponseEntity.ok(response);
	}
	
	
	
	
	@GetMapping("/GuardarPagoParcial")
	public String GuardarPagoParcial( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		 HttpSession session = request.getSession();
		 Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		 Integer idLocal = usuario.getIdLocal();
		 
		 // Obtener las listas de la sesión
		 	String idOrden = (String) session.getAttribute("xIdOrden");
		 	Integer xIdOrden = Integer.parseInt(idOrden);
		 	
		 	
		 	String[] xIdPluArr = (String[]) session.getAttribute("arrIdReferencia");
		 	String[] xVrMedioArr =  (String[]) session.getAttribute("arrVrMedioArr");
		 	
		 	
		 	String xIdVendedor = (String) session.getAttribute("idVendedor");
		 	Integer IdVendedor = Integer.parseInt(xIdVendedor);
		 	
		 	String IdMedio = (String) session.getAttribute("xIdMedio");
		 	Integer xIdMedio = Integer.parseInt(IdMedio);

		 	String xNombreMedio = (String) session.getAttribute("xNombreMedio");

		 	String FechaPago = (String) session.getAttribute("FechaPago");
		    
		System.out.println("Entró a /Confirmar con xIdOrden: " + xIdOrden);
		System.out.println("Entró a /Confirmar con xIdPluArr: " + xIdPluArr);
		System.out.println("Entró a /Confirmar con xVrMedioArr: " + xVrMedioArr);
		
		

	    
	    // Imprimir los arreglos
	    System.out.println("Dividido xIdPluArr:");
	    for (String elemento : xIdPluArr) {
	        System.out.println(elemento);
	    }

	    System.out.println("Dividido xVrMedioArr:");
	    for (String elemento : xVrMedioArr) {
	        System.out.println(elemento);
	    }
		
		   
			// Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(usuario.getIdLocal());
			
			Integer idPeriodo = 0;
			Integer idTipoOrden = 9;
			
			
			int xIdTipoOrdenPagoProceso = 59;
			int xIndicador = 1;		
			int xIdTipoTerceroCliente = 1;		
			int estadoActivo = 9;
			
			for(TblDctosPeriodo P : PeriodoActivo) {
				
				idPeriodo = P.getIdPeriodo();
				model.addAttribute("xIdPeriodo", P.getIdPeriodo());
				model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
			
			}
			
			
			
			
			double xVrPagoTotal = 0.0;
			
			
			for (int index = 0; index < xIdPluArr.length; index++) {

                //Sumamos los valores del Array y lo parseamos a double
				xVrPagoTotal += Double.parseDouble(xVrMedioArr[index]);

            }
			
			
			System.out.println("xVrPagoTotal es : " + xVrPagoTotal);
			
			
			//--- listaUnDcto
			
			List<TblDctosDTO> listaUnDcto = TblDctosService.listaUnDctoOrden(idLocal, idTipoOrden, xIdOrden, xIndicador);
			
			
			String idCiente = "";
		 	Double idDcto = 0.0;
		 	Integer xIdTipoOrden = 0;
		 	for(TblDctosDTO listaDcto : listaUnDcto ) {
		 		
		 		idCiente = listaDcto.getIdCliente();
		 		idDcto = listaDcto.getIdDcto();
		 		xIdTipoOrden = listaDcto.getIdTipoOrden();
		 	}

		 	
		 	
		 	List<TblDctosDTO> CuentaDetalle = TblDctosService.listaCuentaDetalladoOrdenFCH(idLocal, idCiente, idTipoOrden, idDcto.intValue());
			
			
		 	Double VrSaldo = 0.0;
		 	for(TblDctosDTO cuenta : CuentaDetalle) {
		 		
		 		VrSaldo = cuenta.getVrSaldo();
		 	}
		 	
		 	double xVSaldoDctoActualizado = VrSaldo;
			
			
		 	int idMaximaPlanilla = tblPagosService.maximaPlanilla(idLocal, xIdTipoOrdenPagoProceso) + 1;
			
			
		 	tblPagosMediosRepo.retiraPagoDctoTemporal(idLocal, xIdTipoOrdenPagoProceso, xIndicador, idDcto.intValue());
			
		 	tblPagosRepo.retiraPagoDctoTemporal(idLocal, xIdTipoOrdenPagoProceso, xIndicador, idDcto.intValue());
			
			
			
		 	int xIdReciboMAX = tblPagosService.maximoReciboIdLocalxIndicador(idLocal, xIdTipoOrdenPagoProceso, xIndicador) + 1;
		 	
		 	
		 	Integer idCienteInt = Integer.parseInt(idCiente);
		 	Double xCero = 0.0;
		 	int cero = 0;
		 	String nada = "";
		 	
		 	Integer idLog = tblAgendaLogVisitasService.ObtenerIdLogActivo(idUsuario);
		 	
//		 	(int idLocal, int idTipoOrden, int IdRecibo, int Indicador, String FechaPagoSqlServer, Double VrPago, int NitCC, int Estado, int IdUsuario,
//	  				   Double VrRteFuente, Double VrDescuento, Double VrRteIva,  Double vrRteIca, int IdPeriodo, int IdDcto, int IdDctoNitCC, int IdPlanilla, Double VrSaldo,
//	  				   int IdLog, int IdVendedor, int IdReciboCruce, Double VrPagoCambio, String Comentario)
		 	
		 	tblPagosRepo.ingresaPago(idLocal, xIdTipoOrdenPagoProceso, xIdReciboMAX, xIndicador, FechaPago, xVrPagoTotal, idCienteInt, estadoActivo, idUsuario, 
		 			xCero, xCero, xCero, xCero, idPeriodo, idDcto.intValue(), idCienteInt, idMaximaPlanilla, xVSaldoDctoActualizado, 
		 			idLog, IdVendedor, cero, xCero, nada);
		 	
		 	
		 	int xEstadoOk = 1;
            int xIdBancoOk = 0;
            int xIdDctoMedioOk = 0;
            int xIdMedioEfectivo = 1;
            
            
            
          //--
            for (int index = 0; index < xVrMedioArr.length; index++) {

//            	(int IdLocal, int IdTipoOrden, int IdRecibo, int Indicador, int IdMedio, Double VrMedio, String FechaCobro, int IdBanco, int IdDctoMedio, 
//                        int Estado, int IdLog, int IdPlu)
            	
            	Double VrMedio = Double.parseDouble(xVrMedioArr[index]);
            	Integer idPlu = Integer.parseInt(xIdPluArr[index]);
            	
            	tblPagosMediosRepo.ingresar(idLocal, xIdTipoOrdenPagoProceso, xIdReciboMAX, xIndicador, xIdMedio, VrMedio, FechaPago, xIdBancoOk, xIdDctoMedioOk,
            			xEstadoOk, idLog, idPlu);

            }
		 	
            System.out.println("idLog es " + idLog);
		 	
         // Revisar la query para incluir correctamente el vrPago 
            List<TblDctosDTO> cuentaplanilla = TblDctosService.listaCuentaPlanilla(idLocal, idTipoOrden, idLog);
            
            System.out.println("cuentaplanilla es " + cuentaplanilla);
            
            String nombreUsuario = ctrlusuariosService.obtenerNombreUsuario(idLocal, IdVendedor);
            
            
            List<TblPagosDTO> pagoTemporalLista = tblPagosService.listaPagoTemporalFCH(idLocal, xIdTipoOrdenPagoProceso, xIndicador, idLog);
            
            List<TblPagosDTO> PagoTemporalTotal = tblPagosService.listaPagoTemporalTotal(idLocal, xIdTipoOrdenPagoProceso, idLog);
            
            for(TblPagosDTO temporal : PagoTemporalTotal) {
            	
            	Double vrPagoTotal = temporal.getVrPago();
            	model.addAttribute("xVrPagoTotal", vrPagoTotal);
            }
		 	
            System.out.println("pagoTemporalLista es " + pagoTemporalLista);

            model.addAttribute("xIdVendedor", nombreUsuario);
            model.addAttribute("xNombreMedio", xNombreMedio);
            model.addAttribute("xFechaPago", FechaPago);
            model.addAttribute("xIdPeriodo", idPeriodo);
            
		 	model.addAttribute("xCuentaplanilla", cuentaplanilla);
		 	model.addAttribute("xPagoTemporalLista", pagoTemporalLista);
		 	
		 	model.addAttribute("xIdLog", idLog);
		 	
		 	

			
			return "Cliente/RegistroPagosPlanilla";
			
		

	}
	
	
	
	
	
	@PostMapping("/AnticipoNuid-Post")
	public ResponseEntity<Map<String, String>> AnticipoNuid(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdPeriodo");
	    session.removeAttribute("idCliente");
	    session.removeAttribute("xNombreMedio");
	    session.removeAttribute("xFechaPago");

	    // Obtenemos los datos del JSON recibido
        String idClienteAnticipo = (String) requestBody.get("palabraClave");
        String xNombreMedio = (String) requestBody.get("xNombreMedio");
        String xFechaPago = (String) requestBody.get("xFechaPago");
        //Integer idClienteAnticipo = Integer.parseInt(palabraClave);
        System.out.println("palabraClave desde /AnticipoNuid " + idClienteAnticipo);
	    



	    session.setAttribute("idClienteAnticipo", idClienteAnticipo);
	    session.setAttribute("xNombreMedio", xNombreMedio);
	    session.setAttribute("xFechaPago", xFechaPago);
	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./AnticipoNuid");

        return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/AnticipoNuid")
	public String AnticipoNuid( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		 HttpSession session = request.getSession();
		 Integer xIdUsuario = (Integer) session.getAttribute("xidUsuario");
		 
		 int idLocal = usuario.getIdLocal();
		    
		 
		 // Obtener las variables de la sesión
		 	String idCliente = (String) session.getAttribute("idClienteAnticipo");
		 	
		 	String xIdVendedor = (String) session.getAttribute("idVendedor");
		 	Integer IdVendedor = Integer.parseInt(xIdVendedor);
		 	
//		 	String idOrdenStr = (String) session.getAttribute("idOrden");
//		 	Integer xIdOrden = Integer.parseInt(idOrdenStr);
		 	
		 	String IdMedio = (String) session.getAttribute("xIdMedio");
		 	Integer xIdMedio = Integer.parseInt(IdMedio);
		 	
		 	String FechaPago = (String) session.getAttribute("xFechaPago");
		 	String xNombreMedio = (String) session.getAttribute("xNombreMedio");	
		 	
		 	
		 	System.out.println("FechaPago desde /AnticipoNuid " + FechaPago);
		 	System.out.println("xNombreMedio desde /AnticipoNuid " + xNombreMedio);
		 	
		 	
		 	
		 	

		 	 List<TercerosDTO> ListaBusqueda = tblTercerosService.BuscarTercerosSuscriptorNUID(usuario.getIdLocal(), idCliente);
		        System.out.println("La ListaBusqueda generada es:  " + ListaBusqueda );
		        
		        
		        Integer idTercero = 0;
		        
			    for(TercerosDTO busqueda : ListaBusqueda) {
			    	
			    	idTercero = busqueda.getIdTercero();
			    	
			    	System.out.println("busqueda " + busqueda.getIdTercero());
			    	System.out.println("busqueda nombre  " + busqueda.getNombreTercero());
			    	System.out.println("busqueda " + busqueda.getDireccionTercero());
			    	System.out.println("busqueda " + busqueda.getNombreCausa());
			    	
			    }
			    
			    
			    
			    if(idTercero == 0) {
			    	
			    	model.addAttribute("error", "El cliente seleccionado no existe ");
	            	model.addAttribute("url", "./PagoPlanilla");
	        		return "defaultErrorSistema";
			    }
			    
			    
			    int xIdTipoTerceroCliente = 1;
			 	int idTipoOrden = 9;
			    
			    
			 // Información Cliente
			 	List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(idLocal, idCliente, xIdTipoTerceroCliente);
				
			 	System.out.println("listaTercero es " + listaTercero);
			 	
				for(TblTerceros L : listaTercero) {
					
					model.addAttribute("xEstado", L.getEstado());
					model.addAttribute("xNuid", L.getIdCliente());
					model.addAttribute("xNombreTercero", L.getNombreTercero());
					model.addAttribute("xRuta", L.getIdRuta());
					
				}
		 	


			    
			    
			    List<TblPlusDTO> listaSeleccionaPlu = tblPlusService.seleccionaPlu(idLocal, idCliente);
			    
			    model.addAttribute("xListaSeleccionaPlu", listaSeleccionaPlu);
			    
			    
			    String nombreUsuario = ctrlusuariosService.obtenerNombreUsuario(idLocal, IdVendedor);
			    
			    model.addAttribute("xIdVendedor", nombreUsuario);
	            model.addAttribute("xNombreMedio", xNombreMedio);
	            model.addAttribute("xFechaPago", FechaPago);
	            //model.addAttribute("xIdOrden", xIdOrden);
			     	
			    

			return "Cliente/PagoAnticipo";
			
		

	}
	
	
	
	@PostMapping("/PagarAnticipoPlanilla")
	@ResponseBody
	public ResponseEntity<Resource>  PagarAnticipoPlanilla(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException 
	{
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer xIdUsuario = usuario.getIdUsuario();
	    Integer xIdLocalUsuario = usuario.getIdLocal();
	    
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdLogPago");
	    
	    
	    // Obtenemos los datos del JSON recibido
	    String idpluStr = (String) requestBody.get("idplu");
	    Integer idPlu = Integer.parseInt(idpluStr);
	    
	    
	    String valorPagoStr = (String) requestBody.get("valorPagoStr");
	    Double valorPago = Double.parseDouble(valorPagoStr);
	    
	    
	    String idCliente = (String) requestBody.get("idCliente");
	    
	    
	    
	    
	    
	    //Obtenemos las variables de la session
	    String xIdVendedor = (String) session.getAttribute("idVendedor");
	 	Integer IdVendedor = Integer.parseInt(xIdVendedor);
	 	
	 	String IdMedio = (String) session.getAttribute("xIdMedio");
	 	Integer xIdMedio = Integer.parseInt(IdMedio);

	 	String xNombreMedio = (String) session.getAttribute("xNombreMedio");

	 	String FechaPago = (String) session.getAttribute("xFechaPago");
	 	
	 	
	 	 System.out.println("xIdVendedor " + xIdVendedor);
	 	 System.out.println("IdMedio " + IdMedio);
	 	 System.out.println("xNombreMedio " + xNombreMedio);
	 	 System.out.println("FechaPago " + FechaPago);
	    
	    
	    Map<String, Object> response = new HashMap<>();
	    

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
        
        
     // ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
		// Obtenemos el periodo activo
		List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(xIdLocalUsuario);
		
		Integer xIdPeriodo = 0;
		Integer idTipoOrden = 9;
		
		for(TblDctosPeriodo P : PeriodoActivo) {
			
			xIdPeriodo = P.getIdPeriodo();
			model.addAttribute("xIdPeriodo", P.getIdPeriodo());
			model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
		
		}
		
		int xIdTipoOrdenVenta = 9;
		int xIdTipoOrdenPagoProceso = 59;
		int xIndicador = 1;
		int estadoActivo = 9;
		
		
		List<TblDctosDTO>  UnDctoPeriodo = TblDctosService.listaUnDctoPeriodoCliente(xIdLocalUsuario, xIdTipoOrdenVenta, idCliente, xIdPeriodo);
		
		Double xIdDcto = 0.0;
		int xIdOrden = 0;
		
		for(TblDctosDTO dcto : UnDctoPeriodo) {
			
			xIdDcto = dcto.getIdDcto();
			xIdOrden = dcto.getIdOrden();
		}
		
		
		
		double xVrPagoTotal = valorPago;
		
		
		int xIdMaximaPlanilla = tblPagosService.maximaPlanilla(xIdLocalUsuario, xIdTipoOrdenVenta) + 1;
		
		
		int xIdReciboMAX = tblPagosService.maximoReciboIdLocalxIndicador(xIdLocalUsuario, xIdTipoOrdenVenta, xIndicador) + 1;
		
		
		Integer idCienteInt = Integer.parseInt(idCliente);
	 	Double xCero = 0.0;
	 	int cero = 0;
	 	String nada = "";
	 	Double xVrSaldo = 0.0;
	 	
	 	//Obtenemos el idLog actual
	 	Integer idLog = tblAgendaLogVisitasService.ObtenerIdLogActivo(xIdUsuario);
		
		
//	 	(int idLocal, int idTipoOrden, int IdRecibo, int Indicador, String FechaPagoSqlServer, Double VrPago, int NitCC, int Estado, int IdUsuario,
//		   Double VrRteFuente, Double VrDescuento, Double VrRteIva,  Double vrRteIca, int IdPeriodo, int IdDcto, int IdDctoNitCC, int IdPlanilla, Double VrSaldo,
//		   int IdLog, int IdVendedor, int IdReciboCruce, Double VrPagoCambio, String Comentario)

		tblPagosRepo.ingresaPago(xIdLocalUsuario, xIdTipoOrdenVenta, xIdReciboMAX, xIndicador, FechaPago, xVrPagoTotal, idCienteInt, estadoActivo, xIdUsuario, 
		xCero, xCero, xCero, xCero, xIdPeriodo, 0, idCienteInt, xIdMaximaPlanilla, xVrSaldo, 
		idLog, IdVendedor, cero, xCero, nada);
		
		
		 int xEstadoOk = 1;
         int xIdBancoOk = 0;
         int xIdDctoMedioOk = 0;
		
		
//     	(int IdLocal, int IdTipoOrden, int IdRecibo, int Indicador, int IdMedio, Double VrMedio, String FechaCobro, int IdBanco, int IdDctoMedio, 
//       int Estado, int IdLog, int IdPlu)
		
         tblPagosMediosRepo.ingresar(xIdLocalUsuario, xIdTipoOrdenVenta, xIdReciboMAX, xIndicador, xIdMedio, xVrPagoTotal, FechaPago, xIdBancoOk, xIdDctoMedioOk,
     			xEstadoOk, idLog, idPlu);

         
		// Obtenemos la IP del servidor
        UtilidadesIP utilidadesIP = new UtilidadesIP();
        String xIpTx = utilidadesIP.getIpServidor();
        
        int estadoProgramado = 1;
        int tareaVisita = 6;   // Cotizacion
        int xIdEstadoTxSinTx = 1;

      
        tblAgendaLogVisitasRepo.finalizaVisita(estadoProgramado, tareaVisita, xIdTipoOrdenVenta, xIdEstadoTxSinTx, xIpTx, strFechaVisita, xIdLocalUsuario, idLog);
        
        
        
        // -------------------------------------------------------------------------------------- Reporte    -----------------------------------------------------
		
        String formato = "PDF";
		
        int xIdReporte = 1800;
	    
	    //Obtenemos el FileName del reporte y el titulo 
	    List<TblLocalesReporte> reporte = tblLocalesReporteService.listaUnFCH(xIdLocalUsuario, xIdReporte);
	    
	    String xFileNameReporte = "";
	    String xTituloReporte = "";
	    
	    for(TblLocalesReporte R : reporte) {
	    	
	    	xFileNameReporte = R.getFileName();
	    	xTituloReporte = R.getReporteNombre();
	    }
		
		//Obtenemos la información del local que usaremos para los PARAMS del encabezado
	    List<TblLocales> Local = tblLocalesService.ObtenerLocal(xIdLocalUsuario);
		
	    Map<String, Object> params = new HashMap<>();
	    params.put("tipo", formato);
	    params.put("idLocal", xIdLocalUsuario);

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
		    params.put("p_idLocal", xIdLocalUsuario);
		    params.put("p_idPlanilla", xIdMaximaPlanilla);
		    params.put("p_idTipoOrden", xIdTipoOrdenVenta);
		    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
	    	
	    }
	    
	   	    
	    
	    
	    
	    List<TblPagosDTO> lista = null;
	    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblPagosService.listaPlanilla(xIdLocalUsuario, xIdTipoOrdenVenta, xIdMaximaPlanilla);
	    	
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
        
		    
		    //Removemos las variables de la session
		    session.removeAttribute("idOrden");
		    session.removeAttribute("FechaPago");
		    session.removeAttribute("xIdPeriodo");
		    session.removeAttribute("idCliente");
		    session.removeAttribute("xIdVendedor");
		    session.removeAttribute("xNombreMedio");
		    session.removeAttribute("xFechaPago");
		    session.removeAttribute("xIdMedio");
		    session.removeAttribute("idVendedor");
		    


	    
	    
     // Configura la respuesta HTTP
	    return ResponseEntity.ok()
	            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
	            .contentLength(dto.getLength())
	            .contentType(mediaType)
	            .body(streamResource);
	   
	    
	}
	
	
	
	
	
	@PostMapping("/CambiarCliente-Post")
	public ResponseEntity<Map<String, String>> CambiarCliente(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /Confirmar-Post");
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdPeriodo");
	    session.removeAttribute("idCliente");

	    // Obtenemos los datos del JSON recibido
	    String FechaPago = (String) requestBody.get("xFechaPago");

	    session.setAttribute("FechaPago", FechaPago);
	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./CambiarCliente");

        return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/CambiarCliente")
	public String CambiarCliente( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		 HttpSession session = request.getSession();
		 Integer xIdUsuario = (Integer) session.getAttribute("xidUsuario");
		 
		 int idLocal = usuario.getIdLocal();
		    
		 
		 // Obtener las variables de la sesión
		 	String xIdVendedor = (String) session.getAttribute("idVendedor");
		 	Integer IdVendedor = Integer.parseInt(xIdVendedor);
		 	
		 	
		 	String FechaPago = (String) session.getAttribute("FechaPago");
		 	
		 	String IdMedio = (String) session.getAttribute("xIdMedio");
		 	Integer xIdMedio = Integer.parseInt(IdMedio);
		 	
		 	String xNombreMedio = (String) session.getAttribute("xNombreMedio");
		 	

		 	int idTipoOrden = 9;
		 	int xIdTipoOrdenPagoProceso = 59;

		 	int xIndicador = 1;
		 	
		 	
		 	
		 // Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
			
			Integer idPeriodo = 0;
			
			for(TblDctosPeriodo P : PeriodoActivo) {
				
				idPeriodo = P.getIdPeriodo();
			
			}
		 	
		 	Integer idLog = tblAgendaLogVisitasService.ObtenerIdLogActivo(xIdUsuario);
		 	

            
            // Revisar la query para incluir correctamente el vrPago 
            List<TblDctosDTO> cuentaplanilla = TblDctosService.listaCuentaPlanilla(idLocal, idTipoOrden, idLog);
            
            List<TblPagosDTO> pagoTemporalLista = tblPagosService.listaPagoTemporalFCH(idLocal, xIdTipoOrdenPagoProceso, xIndicador, idLog);

            List<TblPagosDTO> PagoTemporalTotal = tblPagosService.listaPagoTemporalTotal(idLocal, xIdTipoOrdenPagoProceso, idLog);
            
            for(TblPagosDTO temporal : PagoTemporalTotal) {
            	
            	Double vrPagoTotal = temporal.getVrPago();
            	model.addAttribute("xVrPagoTotal", vrPagoTotal);
            }
            
            
            System.out.println("cuentaplanilla es " + cuentaplanilla);
            
            String nombreUsuario = ctrlusuariosService.obtenerNombreUsuario(idLocal, IdVendedor);
		 	

            model.addAttribute("xIdVendedor", nombreUsuario);
            model.addAttribute("xNombreMedio", xNombreMedio);
            model.addAttribute("xFechaPago", FechaPago);
            model.addAttribute("xIdPeriodo", idPeriodo);
            
		 	model.addAttribute("xCuentaplanilla", cuentaplanilla);
		 	model.addAttribute("xPagoTemporalLista", pagoTemporalLista);
		 	
		 	model.addAttribute("xIdLog", idLog);
		 	
		 	

			
			return "Cliente/RegistroPagosPlanilla";
			
		

	}
	
	

}
