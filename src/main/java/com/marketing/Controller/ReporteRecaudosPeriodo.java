package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblPagosDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReporteRecaudosPeriodo {
	
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
	TblPagosService tblPagosService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@GetMapping("/ReporteRecaudosPeriodo")
	public String reporteDetalleVentas (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				
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
				
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
				
				}
				
				
				List<TblTercerosRuta> Rutas = tblTercerosRutaService.ListaRutas(idLocal);
				model.addAttribute("xRutas", Rutas);
				

	
		
		
		return "Reporte/ReporteRecaudosPeriodo";
	}
	
	

	
	
	
	@PostMapping("/DescargarReporteRecaudosPeriodo")
	public ResponseEntity<Resource> DescargarReporteRecaudosPeriodo(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		

		
		 // Obtenemos los datos del JSON recibido
        String idPeriodo = (String) requestBody.get("idPeriodo");
        System.out.println("idPeriodo en DescargarReporteCortes es  : " + idPeriodo);
        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
        
        String formato = (String) requestBody.get("formato");

        
        int idLocal = usuario.getIdLocal();
		
	    int xIdReporte = 1600;
	    
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
		    params.put("p_idPeriodo", idPeriodoInt);
		    params.put("p_nombreLocal", L.getNombreLocal());
		    params.put("p_nit", L.getNit());
		    params.put("p_titulo", xTituloReporte);
		    params.put("p_direccion", L.getDireccion());
		    params.put("p_idLocal", idLocal);
		    params.put("p_indicadorINI", IndicadorINICIAL);
		    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
		    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
		    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
		    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
	    	
	    }
	    
	    
	    List<TblPagosDTO> lista = null;
	    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblPagosService.listaRepRecaudoPeriodo(idLocal, idPeriodoInt);

    
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/DescargarReporteRecaudosPeriodoRUTA")
	@ResponseBody
	public ResponseEntity<Resource>  DescargarReporteRecaudosPeriodoRUTA(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  DescargarReporteEstadoProductoNUID");

	        // Obtenemos los datos del JSON recibido
	        String idPeriodo = (String) requestBody.get("idPeriodo");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);

	        
	        String idRuta = (String) requestBody.get("idRuta");

	        Integer idRutaInt = Integer.parseInt(idRuta);
	        String formato = (String) requestBody.get("formato");

	        int idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 1600;
		    Integer xIdTipoOrden = 7;
		    
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
			    params.put("p_idPeriodo", idPeriodoInt);
			    params.put("p_nombreLocal", L.getNombreLocal());
			    params.put("p_nit", L.getNit());
			    params.put("p_titulo", xTituloReporte);
			    params.put("p_direccion", L.getDireccion());
			    params.put("p_idLocal", idLocal);
			    params.put("p_indicadorINI", IndicadorINICIAL);
			    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
			    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
			    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
			    
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
		    	
		    }
		    
		   
		    
		    List<TblPagosDTO> lista = null;
		    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblPagosService.listaRepRecaudoPeriodoRuta(idLocal, idPeriodoInt, idRutaInt);
		    	
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
	
	
	
	@PostMapping("/DescargarReporteRecaudosPeriodoFECHA")
	@ResponseBody
	public ResponseEntity<Resource>  DescargarReporteRecaudosPeriodoFECHA(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  DescargarReporteEstadoProductoNUID");

	        // Obtenemos los datos del JSON recibido
	        String idPeriodo = (String) requestBody.get("idPeriodo");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);

	        
	        String fecha = (String) requestBody.get("fecha");
	        String formato = (String) requestBody.get("formato");
	        
	        
	        String fechaFormateada = "";
	        try {
	            // Convierte la cadena de fecha en formato "yyyy-MM-dd'T'HH:mm" a un objeto Date
	            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date fechaRadicacionDate = inputDateFormat.parse(fecha);

	            // Formatea la fecha
	            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            fechaFormateada = outputDateFormat.format(fechaRadicacionDate);

	            System.out.println("fechaFormateada " + fechaFormateada);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        


	        int idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 1600;
		    Integer xIdTipoOrden = 7;
		    
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
			    params.put("p_idPeriodo", idPeriodoInt);
			    params.put("p_nombreLocal", L.getNombreLocal());
			    params.put("p_nit", L.getNit());
			    params.put("p_titulo", xTituloReporte);
			    params.put("p_direccion", L.getDireccion());
			    params.put("p_idLocal", idLocal);
			    params.put("p_indicadorINI", IndicadorINICIAL);
			    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
			    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
			    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
			    
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
		    	
		    }
		    
		   
		    
		    List<TblPagosDTO> lista = null;
		    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblPagosService.listaRepRecaudoPeriodoFecha(idLocal, idPeriodoInt, fechaFormateada);
		    	
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
