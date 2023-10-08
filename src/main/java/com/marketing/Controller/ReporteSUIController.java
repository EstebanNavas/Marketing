package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.marketing.Model.Reportes.ReporteDTO;
import com.marketing.Model.Reportes.ReporteSmsDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Projection.ReporteSuiDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReporteSUIController {
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	
	@GetMapping("/ReporteSUI")
	public String reportePQR (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();

		
	
//		model.addAttribute("datosListaTercerosClientes", datosListaTercerosClientes);
		
		return "pqr/ReporteSUI";
	}
	
	
	@PostMapping("/DescargarReporteSUI")
	public ResponseEntity<Resource> DescargarReporteSUI(HttpServletRequest request,
			@RequestParam String formato,
			@RequestParam("FechaInicial") String fechaInicialStr, // Recibe como String
			@RequestParam("FechaFinal") String fechaFinalStr, // Recibe como String
			Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		System.out.println("fechaInicialStr : " + fechaInicialStr);
		System.out.println("fechaFinalStr : " + fechaFinalStr);
		
		int idLocal = usuario.getIdLocal();
		
	    Map<String, Object> params = new HashMap<>();
	    params.put("tipo", formato);
	    params.put("idLocal", idLocal);
	    params.put("p_fechaInicial", fechaInicialStr);
	    params.put("p_fechaFinal", fechaFinalStr);

//	    String fechaInicialFormateada = "";
//        String fechaFinalFormateada = "";
//  
//
//        try {
//            // Convierte la cadena de fecha en formato "yyyy-MM-dd'T'HH:mm" a un objeto Date
//            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//            Date fechaInicial = inputDateFormat.parse(fechaInicialStr);
//            Date fechaFinal = inputDateFormat.parse(fechaFinalStr);
//
//
//            // Formatea las fechas de respuesta y notificación
//            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            fechaInicialFormateada = outputDateFormat.format(fechaInicial);
//            fechaFinalFormateada = outputDateFormat.format(fechaFinal);
//
//            System.out.println("fechaInicialFormateada en /DescargarReporteSUI: " + fechaInicialFormateada);
//            System.out.println("fechaFinalFormateada en /DescargarReporteSUI: " + fechaFinalFormateada);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
		
	    	
	    	
	    	List<ReporteSuiDTO>  ReporteSUI =  tblDctosOrdenesService.ObtenerReporteSUI(idLocal, fechaInicialStr, fechaFinalStr );
    
	    
		    
		    // Se crea una instancia de JRBeanCollectionDataSource con la lista de ReporteDTO
		    JRDataSource dataSource = new JRBeanCollectionDataSource(ReporteSUI);
		    
		    ReporteSmsDTO dto = reporteSmsServiceApi.obtenerReporteSUI(params, dataSource);
		    
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
