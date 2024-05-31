package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.marketing.Model.dbaquamovil.TblTerceros;
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
public class HistoricoFinanciacionController {

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
	
	
	@GetMapping("/HistoricoFinanciacion")
	public String historicoFinanciacion(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer IdUsuario = usuario.getIdUsuario();

				
				
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
				
				
			
				
				
				
		
		return "Cliente/HistoricoFinanciacion";
	}
	
	
	
	@PostMapping("/ConsultarHistorico-Post")
	public ResponseEntity<Map<String, String>> ConsultarHistoricoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ConfirmarFinanciacion-Post");
	    
	    // Limpiar las variables anteriores de la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("idCliente");
	    session.removeAttribute("FechaInicial");
	    session.removeAttribute("FechaFinal");

	    

	    // Obtenemos los datos del JSON recibido
	    String idCliente = (String) requestBody.get("idCliente");
	    String FechaInicial = (String) requestBody.get("FechaInicial");
	    String FechaFinal = (String) requestBody.get("FechaFinal");

	    System.out.println("idCliente es : " + idCliente);
	    System.out.println("FechaInicial es : " + FechaInicial);
	    System.out.println("FechaFinal es : " + FechaFinal);
	    
	    // Guardar las variable en la sesión
	    session.setAttribute("idCliente", idCliente);
	    session.setAttribute("FechaInicial", FechaInicial);
	    session.setAttribute("FechaFinal", FechaFinal);


	    


        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./DetalleHistorico");

        return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/DetalleHistorico")
	public String DetalleHistorico( HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");

			int idLocal = usuario.getIdLocal();
		
			int xIdTipoTerceroCliente = 1;

			System.out.println("Entró a /ConfirmarFinanciaciones");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    
		    // Obtener las variables de la sesión
		    String xIdCliente =  (String) session.getAttribute("idCliente");
		    String xFechaInicial =  (String) session.getAttribute("FechaInicial");
		    String xFechaFinal =  (String) session.getAttribute("FechaFinal");

		    
		    int IdTipoOrden = 7;
		    
		    
		    List<TblDctosOrdenesDTO> listaHistorico = tblDctosOrdenesService.listaHistoricoFinanciacionCliente(idLocal, IdTipoOrden, xIdCliente, xFechaInicial, xFechaFinal);
		    model.addAttribute("xListaHistorico", listaHistorico);
		    
		    
		    List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), xIdCliente, xIdTipoTerceroCliente);
			
			for(TblTerceros L : listaTercero) {
				
				model.addAttribute("xEstado", L.getEstado());
				model.addAttribute("xNuid", L.getIdCliente());
				model.addAttribute("xNombreTercero", L.getNombreTercero());
				model.addAttribute("xRuta", L.getIdRuta());
				
			}
		    
		    

			// Removemos de la session las variables
		    session.removeAttribute("idCliente");
		    session.removeAttribute("FechaInicial");
		    session.removeAttribute("FechaFinal");
		    
		    
		    
			
            
            return "Cliente/DetalleHistorico";
	

	}
	
	
	
	@PostMapping("/detallarCotizacionHistorico-Post")
	@ResponseBody
	public ResponseEntity<Resource>  detallarCotizacionHistoricoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  detallarCotizacionHistorico");

	        // Obtenemos los datos del JSON recibido
	        String xidOrden = (String) requestBody.get("idOrden");
	        Integer idOrden = Integer.parseInt(xidOrden);
	        
	        

	        
	        
	        String formato = "PDF";

	        int idLocal = usuario.getIdLocal();
	        
	        int idTipoOrden = 7;
 
            
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
			    xPathReport = L.getPathReport();
		    	
		    }
		    
		    List<TblDctosOrdenesDetalleDTO> listaOrden =  tblDctosOrdenesDetalleService.listaUnLocalOrden(idOrden, idTipoOrden, idLocal);
		    
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
		    
		    List<TercerosDTO2> tercero = tblTercerosService.listaUnTerceroOrden(idLocal, idTipoOrden, idOrden);
		    
		    
		    
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
		    List<TblDctosOrdenesDetalleDTO> liquidaCotizacion = tblDctosOrdenesDetalleService.liquidaUnCotizacion(idLocal, idTipoOrden, idOrden);
		    

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
	            lista = tblDctosOrdenesDetalleService.detallaFinanciacion(idLocal, idTipoOrden, idOrden);
		    	
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
	
	
}
