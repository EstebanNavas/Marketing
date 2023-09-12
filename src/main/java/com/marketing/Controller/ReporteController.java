package com.marketing.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.lowagie.text.pdf.codec.Base64.InputStream;
import java.io.InputStream;
import java.io.File;

import com.marketing.JasperReportUtils.JasperReportUtils;
import com.marketing.Model.DBMailMarketing.MailPlantilla;
import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import com.marketing.Model.Reportes.ReporteDTO;
import com.marketing.Model.Reportes.ReporteSmsDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Service.DBMailMarketing.MailCampaignService;
import com.marketing.Service.DBMailMarketing.TblMailMarketingReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblTercerosService;

import org.springframework.core.io.InputStreamResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.io.ByteArrayOutputStream;
import com.marketing.JasperReportUtils.GeneraReporte;
import net.sf.jasperreports.engine.JRDataSource;

import org.springframework.core.io.Resource;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.enums.TipoReporteEnum;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ReporteController {
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblMailMarketingReporteService tblMailMarketingReporteService;
	
	@Autowired
	MailCampaignService mailCampaignService;
	
	@Autowired
	TblTercerosService tblTercerosService;

	
	@PostMapping("/downloadReport")
	public ResponseEntity<Resource> download(HttpServletRequest request, @RequestParam String formato, Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		int idLocal = usuario.getIdLocal();
		
	    Map<String, Object> params = new HashMap<>();
	    params.put("tipo", formato);
	    params.put("idLocal", idLocal);
		
	    	
	    	
	    	List<ReporteDTO> reporteSMS = mailCampaignService.reporteSmsAgrupadoIdCampaign(idLocal);
	    	
	    	
			
		    // Obtenemos la lista de objetos ReporteDTO
		   	//List<ReporteDTO> reporteDTOs = tblMailMarketingReporteService.obtenerRegistrosPorIdLocal(idLocal);
	    	//List<TblMailMarketingReporte> reporteSMS = tblMailMarketingReporteService.obtenerRegistrosPorIdLocal(idLocal);
		    
		    // Se crea una instancia de JRBeanCollectionDataSource con la lista de ReporteDTO
		    JRDataSource dataSource = new JRBeanCollectionDataSource(reporteSMS);
		    
		    ReporteSmsDTO dto = reporteSmsServiceApi.obtenerReporteSms(params, dataSource);
		    
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
		
	    
	
	
	
	
	@PostMapping("/generarReporte")
	 public String generarReporte(HttpServletRequest request,
			 @RequestParam String formato,
			 Model model) {
		
		        // Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idReporteConsumoSMS = 2800;
				String xIdDestinacion = formato;
				
				System.out.println("xIdDestinacion es: " + xIdDestinacion);
				
				if(usuario == null) {
					model.addAttribute("usuario", new Ctrlusuarios());
					return "redirect:/";
				}else {
					
						// Se obtiene el nombre del reporte de tblLocalesReporte
						 String xFlileName = tblLocalesReporteService.obtenerNombreReporte(usuario.getIdLocal(), idReporteConsumoSMS);
						 System.out.println("el xFlileName es: " + xFlileName);
						 
						// SE CREA UNA INSTANCIA DE LA  CLASE GeneraReporte
						 
						 GeneraReporte reporte = new GeneraReporte(); 
						 
						 reporte.setIdLocal(usuario.getIdLocal());
						 reporte.setNombreReporte(xFlileName);
						 reporte.setFormatoReporte(xIdDestinacion);
					
					
				}
		
		return "defaultSuccess";
	}
	
	
}
