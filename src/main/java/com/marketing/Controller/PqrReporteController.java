package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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


import com.marketing.Model.Reportes.ReporteSmsDTO;
import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Controller
public class PqrReporteController {
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	TblDctosService tblDctosService;
	
	
	@GetMapping("/ReportePqr")
	public String reportePQR (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
		
		
		//Obtenemos la lista de los IdClientes con PQR ya finalizadas (Respondidas)
//		List<String> xClientesPQR =  tblDctosService.ObtenerClientesPQR(idLocal);
//		System.out.println("ClientesPQR en /ReportePqr es: " + xClientesPQR);
//		
//		List <TblTercerosProjectionDTO> datosListaTercerosClientes = tblTercerosService.obtenerDatosTercerosListaClientes(usuario.getIdLocal(), xClientesPQR);
//		System.out.println("datosListaTercerosClientes en /ReportePqr es: " + datosListaTercerosClientes);
//		
//		
//		
//		model.addAttribute("datosListaTercerosClientes", datosListaTercerosClientes);
		
		return "pqr/ReportesPqr";
	}
	
	
	@PostMapping("/MostarClientePQR")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> MostarClientePQR(@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		Integer IdUsuario = usuario.getIdUsuario();
		int idLocal = usuario.getIdLocal();
		System.out.println("Ingresó a /ReportePqr es: ");
		
		String xidCliente = (String) requestBody.get("selectCliente");
		System.out.println("xidCliente en /ReportePqr es: " + xidCliente);
		
		String xFechaInicial = (String) requestBody.get("FechaInicial");
		String xFechaFinal = (String) requestBody.get("FechaFinal");
		
		System.out.println("xFechaInicial en /ReportePqr es: " + xFechaInicial);
		System.out.println("xFechaFinal en /ReportePqr es: " + xFechaFinal);
		
		//Obtenemos la lista de los idlcientes por rango de fecha
		List<String> xListaCLientesFecha = tblDctosOrdenesService.ObtenerListaClientesFecha(idLocal, xFechaInicial,  xFechaFinal);
		
		List <TblTercerosProjectionDTO> datosListaTercerosClientes = tblTercerosService.obtenerDatosTercerosListaClientes(usuario.getIdLocal(), xListaCLientesFecha);
		System.out.println("datosListaTercerosClientes en /ReportePqr es: " + datosListaTercerosClientes);
		
		//Obtenemos la lista de los idDcto del IDTIPOORDEN = 17
//		List<Integer> xListaPQR = tblDctosService.ObtenerListaPQR(idLocal, xidCliente);
//		System.out.println("ListaPQR en /ReportePqr es: " + xListaPQR);
//		
//		//Obtenemos la lista de los IDORDEN correspondientes a la lista xListaPQR
//		List<Integer> ListaIDORDEN  = tblDctosService.ObtenerListaIDORDEN(usuario.getIdLocal(), xListaPQR);
//		System.out.println("ListaIDORDEN en /MostarListaPQR: " + ListaIDORDEN);
//		
//		List<Integer> ListaNumeroOrden = tblDctosOrdenesService.ObtenerListaNumeroOrden(usuario.getIdLocal(), ListaIDORDEN, xFechaInicial, xFechaFinal);
//		System.out.println("ListaNumeroOrden en /MostarListaPQR: " + ListaNumeroOrden);

		
		Map<String, Object> response = new HashMap<>();
		response.put("datosListaTercerosClientes", datosListaTercerosClientes);
		
	
            return ResponseEntity.ok(response);
		
    }
	
	
	@PostMapping("/MostarListaPQR")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> MostarListaPQR(@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		Integer IdUsuario = usuario.getIdUsuario();
		int idLocal = usuario.getIdLocal();
		System.out.println("Ingresó a /ReportePqr es: ");
		
		String xidCliente = (String) requestBody.get("selectCliente");
		System.out.println("xidCliente en /ReportePqr es: " + xidCliente);
		
		String xFechaInicial = (String) requestBody.get("FechaInicial");
		String xFechaFinal = (String) requestBody.get("FechaFinal");
		
		System.out.println("xFechaInicial en /ReportePqr es: " + xFechaInicial);
		System.out.println("xFechaFinal en /ReportePqr es: " + xFechaFinal);
		
		//Obtenemos la lista de los idDcto del IDTIPOORDEN = 17
		List<Integer> xListaPQR = tblDctosService.ObtenerListaPQR(idLocal, xidCliente);
		System.out.println("ListaPQR en /ReportePqr es: " + xListaPQR);
		
		//Obtenemos la lista de los IDORDEN correspondientes a la lista xListaPQR
		List<Integer> ListaIDORDEN  = tblDctosService.ObtenerListaIDORDEN(usuario.getIdLocal(), xListaPQR);
		System.out.println("ListaIDORDEN en /MostarListaPQR: " + ListaIDORDEN);
		
		List<Integer> ListaNumeroOrden = tblDctosOrdenesService.ObtenerListaNumeroOrden(usuario.getIdLocal(), ListaIDORDEN, xFechaInicial, xFechaFinal);
		System.out.println("ListaNumeroOrden en /MostarListaPQR: " + ListaNumeroOrden);

		
		Map<String, Object> response = new HashMap<>();
		response.put("xListaPQR", ListaNumeroOrden);
		
	
            return ResponseEntity.ok(response);
		
    }
	
	
//	@PostMapping("/DescargarReportePQR")
//	public ResponseEntity<Resource> DescargarReportePQR(HttpServletRequest request,
//			@RequestParam(value = "formato", required = false) String formato,
//			@RequestParam(value = "IdCliente", required = false) String IdCliente,
//			@RequestParam(value = "listaClientes", required = false) String xIdCLiente,
//			//@RequestParam(value = "numeroOrden", required = false) Integer numeroOrden,
//			@RequestParam(value = "listaPQR", required = false) Integer numeroOrden,
//			Model model) throws JRException, IOException, SQLException {
//	   
//	    // Validar si el local está logueado	
//		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
//		String sistema=(String) request.getSession().getAttribute("sistema");
//		
//		int idLocal = usuario.getIdLocal();
//		
//		
//		Integer xIdCLienteInt = Integer.parseInt(xIdCLiente);
//		System.out.println("ClienteSeleccionado en /DescargarReportePQR es: " + xIdCLiente);
//		
//		
//		//Obtenemos el IDORDEN
//		Integer IDORDEN =  tblDctosOrdenesService.ObtenerIdOrden(idLocal, numeroOrden, xIdCLiente);
//		System.out.println("IDORDEN en /DescargarReportePQR es: " + IDORDEN);
//		//Integer IDORDEN = tblDctosService.ObtenerIDORDEN(idLocal, xidDcto);
//	    
//		
//	    // Obtenemos los datos del Local
//	    String xrazonSocial = tblLocalesService.ObtenerRazonSocial(idLocal);
//	    String xnit = tblLocalesService.ObtenerNit(idLocal);
//	    String xdireccion = tblLocalesService.ObtenerDireccion(idLocal);
//	    String xciudad = tblLocalesService.ObtenerCiudad(idLocal);
//
//	    //Obtenemos los datos del Cliente
//	    String xIdCliente = tblTercerosService.ObtenerIdCliente(idLocal, xIdCLiente);
//	    String xNombreTercero = tblTercerosService.ObtenerNombreTercero(idLocal, xIdCLiente);
//	    String xTelefonoCelular = tblTercerosService.ObtenerTelefonoCelular(idLocal, xIdCLiente);
//	    String xDireccionTercero = tblTercerosService.ObtenerDireccionTercero(idLocal, xIdCLiente);
//	    
//	     //Obtenemos los datos de la PQR
//	    String xServicio = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 10);
//	    String xCausal = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 11);
//	    String xTipoTramite = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 12);
//	    String xDetalleCausal = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 13);
//	    String xMedioRecepcion = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 14);
//	    String xTipoRespuesta = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 15);
//	    String xTipoNotificación = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 16);
//	    
//	    String xFechaRadicacion = tblDctosOrdenesService.ObtenerFechaRadicacion(idLocal, IDORDEN);
//	    String xComentarioPQR = tblDctosOrdenesDetalleService.ObtenerComentarioPQR(idLocal, IDORDEN, xIdCLiente);
//	    String xComentarioRespuesta = tblDctosOrdenesDetalleService.ObtenerComentarioRespuesta(idLocal, IDORDEN, xIdCLiente);
//	    
//	    Integer xNumeroOrden = tblDctosOrdenesService.ObtenerNumeroOrden(idLocal, IDORDEN);
//	    Integer xIdDcto = tblDctosService.ObtenerIdDcto(idLocal, IDORDEN, xIdCLiente);
//	    String xFechaDcto = tblDctosService.ObtenerFechaDcto(idLocal, IDORDEN, xIdCLiente);
//	    
//	    	
//
//	    // Creamos un objeto Map llamado params y le seteamos los valores que usaremos para crear el reporte
//	    Map<String, Object> params = new HashMap<>();
//	    params.put("tipo", formato);
//	    params.put("idLocal", idLocal);
//	    params.put("p_razonSocial", xrazonSocial);
//	    params.put("p_nit", xnit);
//	    params.put("p_direccion", xdireccion);
//	    params.put("p_ciudad", xciudad);
//	    params.put("p_IdCliente", xIdCliente);
//	    params.put("p_NombreTercero", xNombreTercero);
//	    params.put("p_TelefonoCelular", xTelefonoCelular);
//	    params.put("p_DireccionTercero", xDireccionTercero);
//	    
//	    params.put("p_Servicio", xServicio);
//	    params.put("p_Causal", xCausal);
//	    params.put("p_TipoTramite", xTipoTramite);
//	    params.put("p_DetalleCausal", xDetalleCausal);
//	    params.put("p_MedioRecepcion", xMedioRecepcion);
//	    params.put("p_TipoRespuesta", xTipoRespuesta);
//	    params.put("p_TipoNotificacion", xTipoNotificación);
//	    
//	    params.put("p_FechaRadicacion", xFechaRadicacion);
//	    params.put("p_ComentarioPQR", xComentarioPQR);
//	    params.put("p_ComentarioRespuesta", xComentarioRespuesta);
//	    
//	    params.put("p_NumeroOrden", xNumeroOrden);
//	    params.put("p_IdDcto", xIdDcto);
//	    params.put("p_FechaDcto", xFechaDcto);
//	    
//	    
//	    JRDataSource dataSource = new JREmptyDataSource();
//	    
//	    ReporteSmsDTO dto = reporteSmsServiceApi.obtenerReportePQR(params, dataSource);
//	    
//	    // Verifica si el stream tiene datos y, si no, realiza una lectura en un búfer
//	    InputStream inputStream = dto.getStream();
//	    if (inputStream == null) {
//	        // Realiza una lectura en un búfer alternativo si dto.getStream() es nulo
//	        byte[] emptyContent = new byte[0];
//	        inputStream = new ByteArrayInputStream(emptyContent);
//	    }
//	    
//	    
//	    // Envuelve el flujo en un InputStreamResource
//	    InputStreamResource streamResource = new InputStreamResource(inputStream);
//	    
//	    // Configura el tipo de contenido (media type)
//	    MediaType mediaType;
//	    if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
//	        mediaType = MediaType.APPLICATION_OCTET_STREAM;
//	    } else {
//	        mediaType = MediaType.APPLICATION_PDF;
//	    }
//	    
//	    // Configura la respuesta HTTP
//	    return ResponseEntity.ok()
//	            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
//	            .contentLength(dto.getLength())
//	            .contentType(mediaType)
//	            .body(streamResource);
//	    	
//
//	    
//		}
	
	
	
	
	@PostMapping("/DescargarReportePQR")
	public ResponseEntity<Resource> DescargarReportePQR(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
	
		

		
		 // Obtenemos los datos del JSON recibido
        String listaPQR = (String) requestBody.get("listaPQR");
        Integer numeroOrden = Integer.parseInt(listaPQR);
        
        String formato = (String) requestBody.get("formato");
        
        String xIdCLiente = (String) requestBody.get("listaClientes");
        
        
        

        
        int idLocal = usuario.getIdLocal();
		
		
		Integer xIdCLienteInt = Integer.parseInt(xIdCLiente);
		System.out.println("ClienteSeleccionado en /DescargarReportePQR es: " + xIdCLiente);
		
		
		//Obtenemos el IDORDEN
		Integer IDORDEN =  tblDctosOrdenesService.ObtenerIdOrden(idLocal, numeroOrden, xIdCLiente);
		System.out.println("IDORDEN en /DescargarReportePQR es: " + IDORDEN);
		//Integer IDORDEN = tblDctosService.ObtenerIDORDEN(idLocal, xidDcto);
	    
		
	    // Obtenemos los datos del Local
	    String xrazonSocial = tblLocalesService.ObtenerRazonSocial(idLocal);
	    String xnit = tblLocalesService.ObtenerNit(idLocal);
	    String xdireccion = tblLocalesService.ObtenerDireccion(idLocal);
	    String xciudad = tblLocalesService.ObtenerCiudad(idLocal);

	    //Obtenemos los datos del Cliente
	    String xIdCliente = tblTercerosService.ObtenerIdCliente(idLocal, xIdCLiente);
	    String xNombreTercero = tblTercerosService.ObtenerNombreTercero(idLocal, xIdCLiente);
	    String xTelefonoCelular = tblTercerosService.ObtenerTelefonoCelular(idLocal, xIdCLiente);
	    String xDireccionTercero = tblTercerosService.ObtenerDireccionTercero(idLocal, xIdCLiente);
	    
	     //Obtenemos los datos de la PQR
	    String xServicio = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 10);
	    String xCausal = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 11);
	    String xTipoTramite = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 12);
	    String xDetalleCausal = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 13);
	    String xMedioRecepcion = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 14);
	    String xTipoRespuesta = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 15);
	    String xTipoNotificación = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, IDORDEN, xIdCLiente, 16);
	    
	    String xFechaRadicacion = tblDctosOrdenesService.ObtenerFechaRadicacion(idLocal, IDORDEN);
	    String xComentarioPQR = tblDctosOrdenesDetalleService.ObtenerComentarioPQR(idLocal, IDORDEN, xIdCLiente);
	    String xComentarioRespuesta = tblDctosOrdenesDetalleService.ObtenerComentarioRespuesta(idLocal, IDORDEN, xIdCLiente);
	    
	    Integer xNumeroOrden = tblDctosOrdenesService.ObtenerNumeroOrden(idLocal, IDORDEN);
	    Integer xIdDcto = tblDctosService.ObtenerIdDcto(idLocal, IDORDEN, xIdCLiente);
	    String xFechaDcto = tblDctosService.ObtenerFechaDcto(idLocal, IDORDEN, xIdCLiente);
	    
	    	

	    // Creamos un objeto Map llamado params y le seteamos los valores que usaremos para crear el reporte
	    Map<String, Object> params = new HashMap<>();
	    params.put("tipo", formato);
	    params.put("idLocal", idLocal);
	    params.put("p_razonSocial", xrazonSocial);
	    params.put("p_nit", xnit);
	    params.put("p_direccion", xdireccion);
	    params.put("p_ciudad", xciudad);
	    params.put("p_IdCliente", xIdCliente);
	    params.put("p_NombreTercero", xNombreTercero);
	    params.put("p_TelefonoCelular", xTelefonoCelular);
	    params.put("p_DireccionTercero", xDireccionTercero);
	    
	    params.put("p_Servicio", xServicio);
	    params.put("p_Causal", xCausal);
	    params.put("p_TipoTramite", xTipoTramite);
	    params.put("p_DetalleCausal", xDetalleCausal);
	    params.put("p_MedioRecepcion", xMedioRecepcion);
	    params.put("p_TipoRespuesta", xTipoRespuesta);
	    params.put("p_TipoNotificacion", xTipoNotificación);
	    
	    params.put("p_FechaRadicacion", xFechaRadicacion);
	    params.put("p_ComentarioPQR", xComentarioPQR);
	    params.put("p_ComentarioRespuesta", xComentarioRespuesta);
	    
	    params.put("p_NumeroOrden", xNumeroOrden);
	    params.put("p_IdDcto", xIdDcto);
	    params.put("p_FechaDcto", xFechaDcto);
	    
	    
	    JRDataSource dataSource = new JREmptyDataSource();
	    
	    ReporteSmsDTO dto = reporteSmsServiceApi.obtenerReportePQR(params, dataSource);
	    
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/DescargarReportePQR_Respuesta")
	public ResponseEntity<Resource> DescargarReportePQR_Respuesta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,
			@RequestParam(value = "formato", required = false) String formato,
			@RequestParam(value = "IdCliente", required = false) String IdCliente,
			@RequestParam(value = "numeroOrden", required = false) Integer numeroOrden,
			Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		// Obtenemos los datos del JSON recibido
        String xformato = (String) requestBody.get("formato");
        String idCliente = (String) requestBody.get("idCliente");
        Integer numeroRadicado = (Integer) requestBody.get("numeroRadicado");
        
        System.out.println("xformato en /DescargarReportePQR_Respuesta es : " + xformato);
        System.out.println("idCliente en /DescargarReportePQR_Respuesta es : " + idCliente);
        System.out.println("numeroRadicado en /DescargarReportePQR_Respuesta es : " + numeroRadicado);
		
		int idLocal = usuario.getIdLocal();
		Integer IdClienteInt = Integer.parseInt(IdCliente);
		
	    
	    
		
	    // Obtenemos los datos del Local
	    String xrazonSocial = tblLocalesService.ObtenerRazonSocial(idLocal);
	    String xnit = tblLocalesService.ObtenerNit(idLocal);
	    String xdireccion = tblLocalesService.ObtenerDireccion(idLocal);
	    String xciudad = tblLocalesService.ObtenerCiudad(idLocal);

	    //Obtenemos los datos del Cliente
	    String xIdCliente = tblTercerosService.ObtenerIdCliente(idLocal, IdCliente);
	    String xNombreTercero = tblTercerosService.ObtenerNombreTercero(idLocal, IdCliente);
	    String xTelefonoCelular = tblTercerosService.ObtenerTelefonoCelular(idLocal, IdCliente);
	    String xDireccionTercero = tblTercerosService.ObtenerDireccionTercero(idLocal, IdCliente);
	    
	     //Obtenemos los datos de la PQR
	    String xServicio = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, numeroOrden, IdCliente, 10);
	    String xCausal = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, numeroOrden, IdCliente, 11);
	    String xTipoTramite = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, numeroOrden, IdCliente, 12);
	    String xDetalleCausal = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, numeroOrden, IdCliente, 13);
	    String xMedioRecepcion = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, numeroOrden, IdCliente, 14);
	    String xTipoRespuesta = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, numeroOrden, IdCliente, 15);
	    String xTipoNotificación = tblDctosOrdenesDetalleService.ObtenerNombrePlu(idLocal, numeroOrden, IdCliente, 16);
	    
	    String xFechaRadicacion = tblDctosOrdenesService.ObtenerFechaRadicacion(idLocal, numeroOrden);
	    String xComentarioPQR = tblDctosOrdenesDetalleService.ObtenerComentarioPQR(idLocal, numeroOrden, IdCliente);
	    String xComentarioRespuesta = tblDctosOrdenesDetalleService.ObtenerComentarioRespuesta(idLocal, numeroOrden, IdCliente);
	    
	    Integer xNumeroOrden = tblDctosOrdenesService.ObtenerNumeroOrden(idLocal, numeroOrden);
	    Integer xIdDcto = tblDctosService.ObtenerIdDcto(idLocal, numeroOrden, IdCliente);
	    String xFechaDcto = tblDctosService.ObtenerFechaDcto(idLocal, numeroOrden, IdCliente);
	    
	    	

	    // Creamos un objeto Map llamado params y le seteamos los valores que usaremos para crear el reporte
	    Map<String, Object> params = new HashMap<>();
	    params.put("tipo", formato);
	    params.put("idLocal", idLocal);
	    params.put("p_razonSocial", xrazonSocial);
	    params.put("p_nit", xnit);
	    params.put("p_direccion", xdireccion);
	    params.put("p_ciudad", xciudad);
	    params.put("p_IdCliente", xIdCliente);
	    params.put("p_NombreTercero", xNombreTercero);
	    params.put("p_TelefonoCelular", xTelefonoCelular);
	    params.put("p_DireccionTercero", xDireccionTercero);
	    
	    params.put("p_Servicio", xServicio);
	    params.put("p_Causal", xCausal);
	    params.put("p_TipoTramite", xTipoTramite);
	    params.put("p_DetalleCausal", xDetalleCausal);
	    params.put("p_MedioRecepcion", xMedioRecepcion);
	    params.put("p_TipoRespuesta", xTipoRespuesta);
	    params.put("p_TipoNotificacion", xTipoNotificación);
	    
	    params.put("p_FechaRadicacion", xFechaRadicacion);
	    params.put("p_ComentarioPQR", xComentarioPQR);
	    params.put("p_ComentarioRespuesta", xComentarioRespuesta);
	    
	    params.put("p_NumeroOrden", xNumeroOrden);
	    params.put("p_IdDcto", xIdDcto);
	    params.put("p_FechaDcto", xFechaDcto);
	    
	    
	    JRDataSource dataSource = new JREmptyDataSource();
	    
	    ReporteSmsDTO dto = reporteSmsServiceApi.obtenerReportePQR(params, dataSource);
	    
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
