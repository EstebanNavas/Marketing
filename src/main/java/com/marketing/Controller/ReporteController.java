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
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Projection.TblTercerosProjectionDTO;

import net.sf.jasperreports.repo.InputStreamResource;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Controller
public class ReporteController {

	@GetMapping("/downloadReport")
    public ResponseEntity<ByteArrayResource> downloadReport(
            @RequestParam String format
    ) {
        // Cargar el archivo JasperReport desde el classpath
        InputStream jasperStream = (InputStream) getClass().getResourceAsStream("/reports/reporte_mensajes_sms.jasper");
		//InputStream jasperStream = getClass().getResourceAsStream("/reports/reporte_mensajes_sms.jasper");
		
       
        // Parámetros para el informe
        Map<String, Object> params = new HashMap<>();

        // Generar el informe según el formato especificado (PDF o Excel)
        byte[] reportBytes;
        String contentType;
        String fileName;

        if ("pdf".equalsIgnoreCase(format)) {
            reportBytes = JasperReportUtils.generatePdfReport(jasperStream, params);
            contentType = MediaType.APPLICATION_PDF_VALUE;
            fileName = "reporte_mensajes_sms.pdf";
        } else if ("excel".equalsIgnoreCase(format)) {
            reportBytes = JasperReportUtils.generateExcelReport(jasperStream, params);
            contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            fileName = "reporte_mensajes_sms.xlsx";
        } else {
            // Manejar otro formato o error aquí
            return ResponseEntity.badRequest().build();
        }

        // Crear una respuesta con el contenido del informe y encabezados adecuados
        ByteArrayResource resource = new ByteArrayResource(reportBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
