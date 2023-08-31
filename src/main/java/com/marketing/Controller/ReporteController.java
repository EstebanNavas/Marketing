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
import org.springframework.web.bind.annotation.RequestParam;

//import com.lowagie.text.pdf.codec.Base64.InputStream;
import java.io.InputStream;
import java.io.File;

import com.marketing.JasperReportUtils.JasperReportUtils;
import com.marketing.Model.DBMailMarketing.MailPlantilla;
import com.marketing.Model.Reportes.ReporteSmsDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Projection.TblTercerosProjectionDTO;

import org.springframework.core.io.InputStreamResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import net.sf.jasperreports.engine.JRException;
import java.io.IOException;
import java.sql.SQLException;


import org.springframework.core.io.Resource;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.enums.TipoReporteEnum;

@Controller
public class ReporteController {
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;

//	@GetMapping("/downloadReport")
//    public ResponseEntity<ByteArrayResource> downloadReport(
//            @RequestParam String format
//    ) {
//        // Cargar el archivo JasperReport desde el classpath
//        InputStream jasperStream = (InputStream) getClass().getResourceAsStream("/reports/reporte_mensajes_sms.jasper");
//		//InputStream jasperStream = getClass().getResourceAsStream("/reports/reporte_mensajes_sms.jasper");
//		
//       
//        // Parámetros para el informe
//        Map<String, Object> params = new HashMap<>();
//
//        // Generar el informe según el formato especificado (PDF o Excel)
//        byte[] reportBytes;
//        String contentType;
//        String fileName;
//
//        if ("pdf".equalsIgnoreCase(format)) {
//            reportBytes = JasperReportUtils.generatePdfReport(jasperStream, params);
//            contentType = MediaType.APPLICATION_PDF_VALUE;
//            fileName = "reporte_mensajes_sms.pdf";
//        } else if ("excel".equalsIgnoreCase(format)) {
//            reportBytes = JasperReportUtils.generateExcelReport(jasperStream, params);
//            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//            fileName = "reporte_mensajes_sms.xlsx";
//        } else {
//            // Manejar otro formato o error aquí
//            return ResponseEntity.badRequest().build();
//        }
//
//        // Crear una respuesta con el contenido del informe y encabezados adecuados
//        ByteArrayResource resource = new ByteArrayResource(reportBytes);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
//                .contentType(MediaType.parseMediaType(contentType))
//                .body(resource);
//    }
//	
	
	@GetMapping("/downloadReport")
	public ResponseEntity<Resource> download(@RequestParam String format) throws JRException, IOException, SQLException{
		
		Map<String, Object> params = new HashMap<>(); //Se almacenan los parametros en la variable params
	    params.put("tipo", format); // Agregamos el parámetro "tipo" al params con el valor del format recibido
		
	    //Obtenemos el objeto ReporteSmsDTO
		ReporteSmsDTO dto = reporteSmsServiceApi.obtenerReporteSms(params);
		
		//Se toma el flujo de bytes que representa el contenido del informe y lo envuelve en un objeto InputStreamResource llamado streamResource
		InputStreamResource streamResource = new InputStreamResource(dto.getStream());
		
		// Usamos la clase MediaType de Spring framework para identificar el tipo de formato que se usará 
		MediaType mediaType = null;
		
		// Validamos de el tipo de formato es Excel
		if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
			mediaType = MediaType.APPLICATION_OCTET_STREAM;
		} else {
			mediaType = MediaType.APPLICATION_PDF;
		} 
		
		// Se crea una respuesta HTTP código  estado 200
		// Se especifica el nombre de archivo que se mostrará en el navegador dto.getFileName()
		// Se establece la longitud del contenido del archivo en la respuesta
		// Se establece el tipo de formato que será enviado en la respuesta PDF o EXCEL  contentType(mediaType)
		// Se establece el cuerpo de la respuesta HTTP utilizando streamResource, que es un InputStreamResource que contiene los bytes del archivo generado 
		
		return ResponseEntity.ok().header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
				.contentLength(dto.getLength()).contentType(mediaType).body(streamResource);
	}
	
	
	
	
}
