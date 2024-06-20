package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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

import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblTerceroEstractoService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReporteEstadoProducto {

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
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblTerceroEstractoService  tblTerceroEstractoService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@GetMapping("/ReporteEstadoProducto")
	public String reporteDetalleVentas (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();

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
				

	
		
		
		return "Reporte/ReporteEstadoProducto";
	}
	
	
//	@PostMapping("/DescargarReporteEstadoProducto")
//	public ResponseEntity<Resource> DescargarReporteEstadoProducto(HttpServletRequest request,
//			@RequestParam String formato,
//			@RequestParam("PeriodoCobro") Integer idPeriodo, // Recibe como String
//			Model model) throws JRException, IOException, SQLException {
//	   
//	    // Validar si el local está logueado	
//		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
//		String sistema=(String) request.getSession().getAttribute("sistema");
//		
//		System.out.println("PeriodoCobro : " + idPeriodo);
//
//		
//		int idLocal = usuario.getIdLocal();
//		
//	    int xIdReporte = 1225;
//	    
//	    //Obtenemos el FileName del reporte y el titulo 
//	    List<TblLocalesReporte> reporte = tblLocalesReporteService.listaUnFCH(idLocal, xIdReporte);
//	    
//	    String xFileNameReporte = "";
//	    String xTituloReporte = "";
//	    
//	    for(TblLocalesReporte R : reporte) {
//	    	
//	    	xFileNameReporte = R.getFileName();
//	    	xTituloReporte = R.getReporteNombre();
//	    }
//		
//		//Obtenemos la información del local que usaremos para los PARAMS del encabezado
//	    List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
//		
//	    Map<String, Object> params = new HashMap<>();
//	    params.put("tipo", formato);
//	    params.put("idLocal", idLocal);
//
//	   Integer IdTipoOrdenINI = 9;
//	   Integer IdTipoOrdenFIN = 29;
//	   Integer IndicadorINICIAL = 1;
//	   Integer IndicadorFINNAL = 2;
//	   
//	   String xPathReport = "";
//	   
//	   
//	    for(TblLocales L : Local) {
//	    	
//		    // Parametros del encabezado 
//		    params.put("p_idPeriodo", idPeriodo);
//		    params.put("p_nombreLocal", L.getNombreLocal());
//		    params.put("p_nit", L.getNit());
//		    params.put("p_titulo", xTituloReporte);
//		    params.put("p_direccion", L.getDireccion());
//		    params.put("p_idLocal", idLocal);
//		    params.put("p_indicadorINI", IndicadorINICIAL);
//		    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
//		    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
//		    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
//		    xPathReport = L.getPathReport();
//	    	
//	    }
//	    
//	    Integer IdProducto = 200;
//	    
//	    List<Integer> IdProductoList = new ArrayList<>();
//	    
//	    IdProductoList.add(100); // Acueducto
//	    IdProductoList.add(200); // Alcantarillado
//	    IdProductoList.add(300); // Aseo
//	    
//	    List<TblDctosOrdenesDetalleDTO> lista = null;
//	    
//	    
//
//            // QUERY PARA ALIMENTAR EL DATASOURCE
//            lista = tblDctosOrdenesDetalleService.listaProductoPeriodo(IdProductoList, idLocal, idPeriodo);
//            
//            System.out.println("lista en estado producto es " + lista);
//
//	    
//    
//		    // Se crea una instancia de JRBeanCollectionDataSource con la lista 
//		    JRDataSource dataSource = new JRBeanCollectionDataSource(lista);
//		    
//		    ReportesDTO dto = reporteSmsServiceApi.Reportes(params, dataSource, formato, xFileNameReporte, xPathReport); // Incluir (params, dataSource, formato, xFileNameReporte)
//		    
//		    // Verifica si el stream tiene datos y, si no, realiza una lectura en un búfer
//		    InputStream inputStream = dto.getStream();
//		    if (inputStream == null) {
//		        // Realiza una lectura en un búfer alternativo si dto.getStream() es nulo
//		        byte[] emptyContent = new byte[0];
//		        inputStream = new ByteArrayInputStream(emptyContent);
//		    }
//		    
//		    
//		    // Envuelve el flujo en un InputStreamResource
//		    InputStreamResource streamResource = new InputStreamResource(inputStream);
//		    
//		    // Configura el tipo de contenido (media type)
//		    MediaType mediaType;
//		    if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
//		        mediaType = MediaType.APPLICATION_OCTET_STREAM;
//		    } else {
//		        mediaType = MediaType.APPLICATION_PDF;
//		    }
//		    
//		    // Configura la respuesta HTTP
//		    return ResponseEntity.ok()
//		            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
//		            .contentLength(dto.getLength())
//		            .contentType(mediaType)
//		            .body(streamResource);
//		}
	
	
	
	
	@PostMapping("/DescargarReporteEstadoProducto")
	public ResponseEntity<Resource> DescargarReporteEstadoProducto(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		

		
		 // Obtenemos los datos del JSON recibido
        String idPeriodo = (String) requestBody.get("idPeriodo");
        System.out.println("idPeriodo en DescargarReporteCortes es  : " + idPeriodo);
        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
        
        
        String formato = (String) requestBody.get("formato");

        
       int idLocal = usuario.getIdLocal();
		
	    int xIdReporte = 1225;
	    
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
	    
	    Integer IdProducto = 200;
	    
	    List<Integer> IdProductoList = new ArrayList<>();
	    
	    IdProductoList.add(100); // Acueducto
	    IdProductoList.add(200); // Alcantarillado
	    IdProductoList.add(300); // Aseo
	    
	    List<TblDctosOrdenesDetalleDTO> lista = null;
	    
	    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblDctosOrdenesDetalleService.listaProductoPeriodo(IdProductoList, idLocal, idPeriodoInt);
            
            System.out.println("lista en estado producto es " + lista);

	    
    
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/BuscarSuscriptorReporteEstadoProducto")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarSuscriptorReporteEstadoProducto(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Map<String, Object> response = new HashMap<>();
	    System.out.println("SI ENTRÓ A  /BuscarSuscriptorReporteEstadoProducto");

	        // Obtenemos los datos del JSON recibido
	        String idCliente = (String) requestBody.get("palabraClave");
	        System.out.println("palabraClave desde /BuscarSuscriptorReporteEstadoProducto " + idCliente);

	        List<TercerosDTO> tercero = tblTercerosService.ObtenerSuscriptor(usuario.getIdLocal(), idCliente);
	        System.out.println("tercero es  " + tercero );
	        
	        
	        if(tercero == null) {
	        	
	        	response.put("tercero", "NULL");
	        	return ResponseEntity.ok(response);
	        }
	        

		    
		    
	        response.put("tercero", "OK");
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/DescargarReporteEstadoProductoNUID")
	@ResponseBody
	public ResponseEntity<Resource>  DescargarReporteEstadoProductoNUID(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  DescargarReporteEstadoProductoNUID");

	        // Obtenemos los datos del JSON recibido
	        String idCliente = (String) requestBody.get("palabraClave");
	        System.out.println("palabraClave desde /BuscarSuscriptorReporteEstadoProducto " + idCliente);
	        
	        String PeriodoCobro = (String) requestBody.get("PeriodoCobro");
	        System.out.println("PeriodoCobro" + PeriodoCobro);
	        Integer idPeriodo = Integer.parseInt(PeriodoCobro);
	        String formato = (String) requestBody.get("formato");

	        int idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 1225;
		    
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
			    params.put("p_idPeriodo", idPeriodo);
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
		    
		    Integer IdProducto = 200;
		    
		    List<TblDctosOrdenesDetalleDTO> lista = null;
		    

	            // QUERY PARA ALIMENTAR EL DATASOURCE
	            lista = tblDctosOrdenesDetalleService.listaProductoPeriodoIdCliente(IdProducto, idLocal, idPeriodo, idCliente );
		    	
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
