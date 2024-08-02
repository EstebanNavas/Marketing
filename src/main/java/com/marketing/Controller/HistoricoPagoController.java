package com.marketing.Controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
import com.marketing.Projection.TblDctosPeriodoDTO;
import com.marketing.Projection.TblPagosDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosMediosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosRepo;
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
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.ProcesoIngresoNota;
import com.marketing.Utilidades.UtilidadesIP;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Controller
public class HistoricoPagoController {
	
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
	TblPagosRepo tblPagosRepo;
	
	@Autowired
	TblPagosMediosRepo tblPagosMediosRepo;
	
	@Autowired
	ProcesoIngresoNota procesoIngresoNota;
	
	
	@GetMapping("/HistoricoPago")
	public String historicoPago (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer IdUsuario = usuario.getIdUsuario();

				// Obtenemos la lista de periodos 
				List <TblDctosPeriodo> Periodos = tblDctosPeriodoService.ListaTotalPeriodos(idLocal);
				model.addAttribute("xPeriodos", Periodos);
				
				// ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				Integer idPeriodo = 0;
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					idPeriodo = P.getIdPeriodo();
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
				
				}

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

				
		
		return "Cliente/HistoricoPago";
	}
	

	
	
	@PostMapping("/TraerPagos-Post")
	public ModelAndView TraerDctoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerPeriodo-Post");

	    // Obtenemos los datos del JSON recibido
	    String idCliente = (String) requestBody.get("idCliente");
        String fechaInicial = (String) requestBody.get("FechaInicial");
	    String fechaFinal = (String) requestBody.get("FechaFinal");
	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerPagos?idCliente=" + idCliente + "&fechaInicial=" + fechaInicial + "&fechaFinal=" + fechaFinal);
	    return modelAndView;
	}
	
	
	
	
	@GetMapping("/TraerPagos")
	public String TraerDcto(@RequestParam(name = "idCliente", required = false) String idCliente, 
			@RequestParam(name = "fechaInicial", required = false) String fechaInicial, 
			@RequestParam(name = "fechaFinal", required = false) String fechaFinal, 
			HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /RetirarPago con idCliente: " + idCliente);
		
		int idLocal = usuario.getIdLocal();
		
		Integer idTipoTercero = 1;

		int xIdTipoTerceroCliente = 1;    
        int idTipoOrden = 9;
	    
	    System.out.println("FechaInicial es " + fechaInicial);
	    System.out.println("FechaFinal es " + fechaFinal);
	    
	    
	    List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(idLocal, idCliente, xIdTipoTerceroCliente);
		
		for(TblTerceros L : listaTercero) {
			
			model.addAttribute("xEstado", L.getEstado());
			model.addAttribute("xNuid", L.getIdCliente());
			model.addAttribute("xNombreTercero", L.getNombreTercero());
			model.addAttribute("xRuta", L.getIdRuta());
			
		}

	    
	    List<TblPagosDTO> listaPagos = tblPagosService.listaPagoTercero(idLocal, idTipoOrden, idCliente, fechaInicial, fechaFinal);
	    System.out.println("listaPagos es " + listaPagos);
           
	    
	    model.addAttribute("xlistaPagos", listaPagos);

			
	        return "Cliente/RetiraPagos";
			
		}


	
	
	
	@PostMapping("/RetirarRecibo")
	@ResponseBody
	public ResponseEntity<Resource>  retirarRecibo(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  RetirarRecibo");

	        // Obtenemos los datos del JSON recibido
	        String idRecibo = (String) requestBody.get("idRecibo");
	        System.out.println("idRecibo desde /RetirarRecibo " + idRecibo);
	        Integer xIdRecibo = Integer.parseInt(idRecibo);
	       

	        
	        String formato = "PDF";

	        int idLocal = usuario.getIdLocal();
	        int idTipoOrden = 9;
	        int indicador = 1;
	        
	        
	        
	        Integer xReciboRetirado = tblPagosService.validaReciboRetirado(idLocal, idTipoOrden, xIdRecibo, indicador);
	        System.out.println("xReciboRetirado es " + xReciboRetirado);
	        
	        
	        
	        if (xReciboRetirado > 0) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.add("xReciboRetirado", "true");
	            headers.add("Content-Type", "application/json");
	            
	            System.out.println("xReciboRetirado mayor a 0");

	            Map<String, String> response = new HashMap<>();
	            response.put("message", "ERROR, ESTE RECIBO FUE RETIRADO EN OTRO PROCESO");

	            return ResponseEntity.status(HttpStatus.OK)
	                    .headers(headers)
	                    .body(new ByteArrayResource(new ObjectMapper().writeValueAsBytes(response)));
	        }
	        
	        
	        
	        
	        List<TblPagosDTO> listaPago = tblPagosService.listaUnFCH(idLocal, idTipoOrden, xIdRecibo, indicador);
	        
	        Double vrPago = 0.0;
	        Double idDcto = 0.0;
	        Double VrRteFuente = 0.0;
	        Double VrDescuento = 0.0;
	        Double VrRteIva = 0.0;
	        Double VrRteIca = 0.0;
	        String fechapago = "";
	        String idCliente = "";
	        int idPeriodo = 0;
	        String IdDctoNitCC = "";
	        int idVendedor = 0;
	        
	        for(TblPagosDTO pago : listaPago) {
	        	
	        	vrPago = pago.getvrPago();
	        	idDcto = pago.getIdDcto();
	        	VrRteFuente = pago.getVrRteFuente();
	        	VrDescuento = pago.getVrDescuento();
	        	VrRteIva = pago.getVrRteIva();
	        	VrRteIca = pago.getVrRteIca();
	        	fechapago = pago.getFechaPago();
	        	idCliente = pago.getNitCC();
	        	idPeriodo =  pago.getIdPeriodo();
	        	IdDctoNitCC = pago.getIdDctoNitCC();
	        	idVendedor = pago.getIdVendedor();
	        }
	        
	        
	        if(vrPago.equals(0.0)) {
	        	
	        	System.out.println("vrPago igual a 0");
	        	
	        	 HttpHeaders headers = new HttpHeaders();
		            headers.add("xReciboRetirado", "true");
		            headers.add("Content-Type", "application/json");

		            Map<String, String> response = new HashMap<>();
		            response.put("message", "ERROR, INTENTA RETIRAR RECIBO RETIRADO");

		            return ResponseEntity.status(HttpStatus.OK)
		                    .headers(headers)
		                    .body(new ByteArrayResource(new ObjectMapper().writeValueAsBytes(response)));
	        	
	        }
	        
	        
	        
	        int idMaximaPlanilla = tblPagosService.maximaPlanilla(idLocal, idTipoOrden) + 1;
	        
	       // (Double  VrPago ,Double VrRteFuente, Double VrDsctoFcro, Double VrRteIva, Double VrRteIca, int idLocal, int IdTipoOrden, int IdDcto, int Indicador )
	        tblDctosRepo.actualizaPago(vrPago * (-1), VrRteFuente * (-1), VrDescuento * (-1), VrRteIva * (-1), VrRteIca * (-1), idLocal, idTipoOrden, idDcto.intValue(), indicador);
	        
	        
	        
	        int xIdReciboMAX = tblPagosService.maximoReciboIdLocalxIndicador(idLocal, idTipoOrden, indicador) + 1;
	        
//	        (int idLocal, int idTipoOrden, int IdRecibo, int Indicador, String FechaPagoSqlServer, Double VrPago, int NitCC, int Estado, int IdUsuario,
//	  				   Double VrRteFuente, Double VrDescuento, Double VrRteIva,  Double vrRteIca, int IdPeriodo, int IdDcto, int IdDctoNitCC, int IdPlanilla, Double VrSaldo,
//	  				   int IdLog, int IdVendedor, int IdReciboCruce, Double VrPagoCambio, String Comentario)
	        
	        Integer idClienteInt = Integer.parseInt(idCliente);
	        // Convertir IdDctoNitCC a Double
	        Double IdDctoNitCCDouble = Double.parseDouble(IdDctoNitCC);

	        // Convertir el Double a Integer
	        Integer IdDctoNitCCInt = IdDctoNitCCDouble.intValue();
	        
	        int xEstadoActivo = 9;
	        int cero = 0;
	        Double ceroDouble = 0.0;
	        String vacio = "";
	        
	        Integer idLog = tblAgendaLogVisitasService.ObtenerIdLogActivo(IdUsuario);
	        
	        
	        tblPagosRepo.ingresaPago(idLocal, idTipoOrden, xIdReciboMAX, indicador, fechapago, vrPago * (-1), idClienteInt, xEstadoActivo, IdUsuario,
	        		               VrRteFuente * (-1), VrDescuento * (-1), VrRteIva * (-1), VrRteIca * (-1), idPeriodo, idDcto.intValue(), IdDctoNitCCInt, idMaximaPlanilla, ceroDouble,
	        		               idLog, idVendedor, xIdRecibo, ceroDouble, vacio);
	        
	        
	        
	        
	        tblPagosMediosRepo.ingresaRetiro(xIdReciboMAX, idLog, idLocal, idTipoOrden, xIdRecibo, indicador);
	        
	        
	     // ---
            int xIdTipoOrdenVenta = 9;
            int tareaVisita = 6;   // Cotizacion
            int estadoProgramado = 1;
            int xIdEstadoTxSinTx = 1;
            
         // Obtenemos la IP del servidor
            UtilidadesIP utilidadesIP = new UtilidadesIP();
            String xIpTx = utilidadesIP.getIpServidor();
            
            
            // Obtenemos la fecha y hora actual
		    Date fechaRadicacion = new Date(); 

		    // Formatea la fecha en el formato deseado
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    String fechaFormateada = dateFormat.format(fechaRadicacion);
	        
	       // (int estado, int idEstadoVisita, int IdTipoOrden, int IdEstadoTx, String IpTx, String FechaTx, int IdLocal, int idLog)
	        tblAgendaLogVisitasRepo.finalizaVisita(estadoProgramado, tareaVisita, idTipoOrden, xIdEstadoTxSinTx, xIpTx, fechaFormateada, idLocal, idLog);

	        
            // -------------------------------------------------------------------------------------- Reporte    

			
		    int xIdReporte = 4000;
		    
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

		    
		    List<TblPagosDTO> totalRecibo = tblPagosService.totalReciboFCH(idLocal, idTipoOrden, xIdReciboMAX, indicador);
		    System.out.println("totalRecibo es " + totalRecibo);
		    
		    
		    for(TblPagosDTO recibo : totalRecibo) {

		    	System.out.println("p_numeroDctos es " + recibo.getNumeroDcto());
		    	params.put("p_numeroDctos", "NUMERO FACTURAS "
		                + recibo.getNumeroDcto());

//		    	params.put("p_vrSaldo", "TOTAL PAGADO  $  "
//		                + recibo.getVrPago());
		    	
		    }
		    
		    
		    
		    
		    List<TercerosDTO2> listaTercero = tblTercerosService.listaUnTerceroFachada(idLocal, idCliente);
		    
		    for(TercerosDTO2 tercero : listaTercero) {
		    	
		    	params.put("p_tercero", tercero.getIdCliente().trim() + " - " + tercero.getNombreTercero());
		    	params.put("p_direccion", "DIRECCION  " + tercero.getDireccionTercero());
		    	
		    }
		    
		  
		    
		    List<TblPagosDTO>  lista = null;
		    

	            // QUERY PARA ALIMENTAR EL DATASOURCE
	            lista = tblPagosService.listaReciboMedidor(idLocal, idTipoOrden, xIdReciboMAX, indicador);
		    	
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
	
	
	
	
	
	
	@PostMapping("/ConsultarHistoricoPago-Post")
	public ModelAndView ConsultarHistoricoPago(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ConsultarHistoricoPago-Post");

	    // Obtenemos los datos del JSON recibido
	    String idCliente = (String) requestBody.get("idCliente");
        String fechaInicial = (String) requestBody.get("FechaInicial");
	    String fechaFinal = (String) requestBody.get("FechaFinal");
	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/ConsultarHistoricoPago?idCliente=" + idCliente + "&fechaInicial=" + fechaInicial + "&fechaFinal=" + fechaFinal);
	    return modelAndView;
	}
	
	
	
	
	@GetMapping("/ConsultarHistoricoPago")
	public String ConsultarHistoricoPago(@RequestParam(name = "idCliente", required = false) String idCliente, 
			@RequestParam(name = "fechaInicial", required = false) String fechaInicial, 
			@RequestParam(name = "fechaFinal", required = false) String fechaFinal, 
			HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /ConsultarHistoricoPago con idCliente: " + idCliente);
		
		int idLocal = usuario.getIdLocal();
		
		Integer idTipoTercero = 1;

		int xIdTipoTerceroCliente = 1;    
        int idTipoOrden = 9;
	    
	    System.out.println("FechaInicial es " + fechaInicial);
	    System.out.println("FechaFinal es " + fechaFinal);
	    
	    
	    List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(idLocal, idCliente, xIdTipoTerceroCliente);
		
		for(TblTerceros L : listaTercero) {
			
			model.addAttribute("xEstado", L.getEstado());
			model.addAttribute("xNuid", L.getIdCliente());
			model.addAttribute("xNombreTercero", L.getNombreTercero());
			model.addAttribute("xRuta", L.getIdRuta());
			
		}

	    
	    List<TblPagosDTO> listaPagos = tblPagosService.listaPagoTercero(idLocal, idTipoOrden, idCliente, fechaInicial, fechaFinal);
	    System.out.println("listaPagos es " + listaPagos);
           
	    
	    model.addAttribute("xlistaPagos", listaPagos);

			
	        return "Cliente/HistoricoPagosLista";
			
		}
	
	
	
	
	@PostMapping("/listaRecibo")
	@ResponseBody
	public ResponseEntity<Resource>  listaRecibo(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  listaRecibo");

	        // Obtenemos los datos del JSON recibido
	        String idRecibo = (String) requestBody.get("idRecibo");
	        System.out.println("idRecibo desde /RetirarRecibo " + idRecibo);
	        Integer xIdRecibo = Integer.parseInt(idRecibo);
	       

	        
	        String formato = "PDF";

	        int idLocal = usuario.getIdLocal();
	        int idTipoOrden = 9;
	        int indicador = 1;
	        
	        
	        
	        

	        
            // -------------------------------------------------------------------------------------- Reporte    

			
		    int xIdReporte = 1810;
		    
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

		    
		    List<TblPagosDTO> totalRecibo = tblPagosService.totalReciboFCH(idLocal, idTipoOrden, xIdRecibo, indicador);
		    System.out.println("totalRecibo es " + totalRecibo);
		    
		    String idCliente = "";
		    
		    for(TblPagosDTO recibo : totalRecibo) {

		    	System.out.println("p_numeroDctos es " + recibo.getNumeroDcto());
		    	params.put("p_numeroDctos", "NUMERO FACTURAS "
		                + recibo.getNumeroDcto());
		    		    	
		    	idCliente = recibo.getNitCC();
		    }
		    
		    
		    System.out.println("idCliente es " + idCliente);
		    
		    List<TercerosDTO2> listaTercero = tblTercerosService.listaUnTerceroFachada(idLocal, idCliente);
		    
		    for(TercerosDTO2 tercero : listaTercero) {
		    	
		    	params.put("p_tercero", tercero.getIdCliente().trim() + " - " + tercero.getNombreTercero());
		    	params.put("p_direccion", "DIRECCION  " + tercero.getDireccionTercero());
		    	
		    }
		    
		  
		    
		    List<TblPagosDTO>  lista = null;
		    

	            // QUERY PARA ALIMENTAR EL DATASOURCE
	            lista = tblPagosService.listaReciboMedidor(idLocal, idTipoOrden, xIdRecibo, indicador);
		    	
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
	
	
	
	
	
	@PostMapping("/listaPlanila")
	@ResponseBody
	public ResponseEntity<Resource>  listaPlanila(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  listaPlanila");

	        // Obtenemos los datos del JSON recibido
	        String idPlanilla = (String) requestBody.get("idPlanilla");
	        
	        Double idPlanillaDouble = Double.parseDouble(idPlanilla);
	        
	        Integer xIdPlanilla = idPlanillaDouble.intValue();
	       

	        
	        String formato = "PDF";

	        int idLocal = usuario.getIdLocal();
	        int idTipoOrden = 9;
	        int indicador = 1;
	        
	        
	        
	        

	        
            // -------------------------------------------------------------------------------------- Reporte    

			
		    int xIdReporte = 1800;
		    
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
			    
			    params.put("p_idLocal", idLocal);
			    params.put("p_indicadorINI", IndicadorINICIAL);
			    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
			    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
			    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
		    	
		    }
		    
		    
		    List<TblPagosDTO> totalPlanilla = tblPagosService.totalPlanillaFCH(idLocal, idTipoOrden, xIdPlanilla);
		    System.out.println("totalPlanilla es " + totalPlanilla);
		    
		    String idCliente = "";
		    
		    for(TblPagosDTO planilla : totalPlanilla) {
	    	
		    	params.put("p_titulo", xTituloReporte + xIdPlanilla + " FECHA PAGO " +  planilla.getFechaPago() + " " + planilla.getIdUsuario() );
		    		    	
		    	idCliente = planilla.getNitCC();
		    }
		    

		    List<TblPagosDTO>  lista = null;
		    

	            // QUERY PARA ALIMENTAR EL DATASOURCE
	            lista = tblPagosService.listaPlanilla(idLocal, idTipoOrden, xIdPlanilla);
		    	
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
