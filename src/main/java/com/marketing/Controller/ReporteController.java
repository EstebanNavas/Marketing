package com.marketing.Controller;


import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.lowagie.text.pdf.codec.Base64.InputStream;
import java.io.InputStream;


import com.marketing.Model.Reportes.ReporteDTO;
import com.marketing.Model.Reportes.ReporteSmsDTO;
import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Service.DBMailMarketing.MailCampaignService;
import com.marketing.Service.DBMailMarketing.TblMailMarketingReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblTercerosService;

import org.springframework.core.io.InputStreamResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.sql.SQLException;
import com.marketing.JasperReportUtils.GeneraReporte;
import net.sf.jasperreports.engine.JRDataSource;

import org.springframework.core.io.Resource;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.enums.TipoReporteEnum;

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
	    
	    
	    int xIdReporte = 3600;
	    
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
		

	    String xCharSeparator = File.separator;
        
        String xPathFileGralDB = ""; 
        String xPathReport = "";
        
       
	    for(TblLocales L : Local) {
	    	   
		    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
		    xPathFileGralDB = L.getPathFileGral(); //--------------------------------------------------------------------------------
	    }

		
	    	
	    	
	    	List<ReporteDTO> reporteSMS = mailCampaignService.reporteSmsAgrupadoIdCampaign(idLocal);
    
	    	
			
		    // Obtenemos la lista de objetos ReporteDTO
		   	//List<ReporteDTO> reporteDTOs = tblMailMarketingReporteService.obtenerRegistrosPorIdLocal(idLocal);
	    	//List<TblMailMarketingReporte> reporteSMS = tblMailMarketingReporteService.obtenerRegistrosPorIdLocal(idLocal);
		    
		    // Se crea una instancia de JRBeanCollectionDataSource con la lista de ReporteDTO
		    JRDataSource dataSource = new JRBeanCollectionDataSource(reporteSMS);
		    
		    ReporteSmsDTO dto = reporteSmsServiceApi.obtenerReporteSms(params, dataSource, formato, xFileNameReporte, xPathReport);
		    
		    //ReportesDTO dto = reporteSmsServiceApi.Reportes(params, dataSource, formato, xFileNameReporte, xPathReport);
		    
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
