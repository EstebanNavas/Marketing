package com.marketing.commons;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;

@Component
public class JasperReportManager {

	//Ruta en donde está la carpeta reports
	private static final String REPORT_FOLDER = "reports";

	// Se define la extensión de archivo .jasper
	private static final String JASPER = ".jasper";
	
	
	private static final String JRXML = ".jrxml";

	public ByteArrayOutputStream export(String xPathReport,  String fileName, String tipoReporte, Map<String, Object> params, JRDataSource dataSource) throws JRException, IOException {
	    
		// Acá se almacenará en memoria el archivo exportado
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		

		JasperReport jasperReport;
		
		//Para archivos .jasper (sin compilación)
		jasperReport = (JasperReport) JRLoader.loadObjectFromFile(xPathReport + File.separator + fileName + JASPER);
		
		// Se  llena el informe utilizando JasperFillManager, se le pasan parametros (flujo de entrada, los parametros y los datos)
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		
		
		
		// Se verifica si el tipoReporte especificado es EXCEL
		if (tipoReporte.equalsIgnoreCase(TipoReporteEnum.EXCEL.toString())) {
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			
		} else { // Si es PDF
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		}

		// Devolvemos el flujo de Bytes que contiene el informe exportado
		return stream;
	}
	
	
public ByteArrayOutputStream exportPQR(String fileName, String tipoReporte, Map<String, Object> params, JRDataSource dataSource) throws JRException, IOException {
	    
		// Acá se almacenará en memoria el archivo exportado
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		// Se construye la ruta completa del archivo (Caperta reports + nombre del reporte + extensión .jasper)
		ClassPathResource resource = new ClassPathResource(REPORT_FOLDER + File.separator + fileName + JASPER);
		
		// Se obtiene el flujo de entrada desde el recurso ya construido resource, este flujo se usa para llenar el informe jasper
		InputStream inputStream = resource.getInputStream();
		
		// Se  llena el informe utilizando JasperFillManager, se le pasan parametros (flujo de entrada, los parametros y los datos)
		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
		
		// Se verifica si el tipoReporte especificado es EXCEL
		if (tipoReporte.equalsIgnoreCase(TipoReporteEnum.EXCEL.toString())) {
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			
		} else { // Si es PDF
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		}

		// Devolvemos el flujo de Bytes que contiene el informe exportado
		return stream;
	}





public ByteArrayOutputStream exportReport(String xPathReport,  String fileName, String tipoReporte, Map<String, Object> params, JRDataSource dataSource) throws JRException, IOException {
    
	// Acá se almacenará en memoria el archivo exportado
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	

	JasperReport jasperReport;
		
	//jasperReport = JasperCompileManager.compileReport(REPORT_FOLDER + File.separator + fileName + JRXML);
	
	jasperReport = JasperCompileManager.compileReport(xPathReport + File.separator + fileName + JRXML);
	
	// Se  llena el informe utilizando JasperFillManager, se le pasan parametros (flujo de entrada, los parametros y los datos)
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
	
	
	
	// Se verifica si el tipoReporte especificado es EXCEL
	if (tipoReporte.equalsIgnoreCase(TipoReporteEnum.EXCEL.toString())) {
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
		SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		configuration.setDetectCellType(true);
		configuration.setCollapseRowSpan(true);
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		
	} else { // Si es PDF
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
	}

	// Devolvemos el flujo de Bytes que contiene el informe exportado
	return stream;
}


public ByteArrayOutputStream exportReportCarpeta(String xPathReport,  String fileName, String tipoReporte, Map<String, Object> params, JRDataSource dataSource, String xPathPDF, String xPathXML,  int idDcto) throws JRException, IOException {
    
	// Acá se almacenará en memoria el archivo exportado
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	
	
	JasperReport jasperReport;
		
	//jasperReport = JasperCompileManager.compileReport(REPORT_FOLDER + File.separator + fileName + JRXML);
	
	//2-Compilamos el archivo XML y lo cargamos en memoria
	jasperReport = JasperCompileManager.compileReport(xPathReport + File.separator + fileName + JRXML);
	
	// Se  llena el informe utilizando JasperFillManager, se le pasan parametros (flujo de entrada, los parametros y los datos)
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
	
	
	
	// Se verifica si el tipoReporte especificado es EXCEL
	if (tipoReporte.equalsIgnoreCase(TipoReporteEnum.EXCEL.toString())) {
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
		SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		configuration.setDetectCellType(true);
		configuration.setCollapseRowSpan(true);
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		
	} else { // Si es PDF
		JasperExportManager.exportReportToPdfFile(jasperPrint, xPathPDF + idDcto  + ".pdf" );
		//JasperExportManager.exportReportToPdfFile(jasperPrint, xPathXML + idDcto  + ".xml" );
	}

	// Devolvemos el flujo de Bytes que contiene el informe exportado
	return stream;
}


public ByteArrayOutputStream exportReportCarpetaNotas(String xPathReport,  String fileName, String tipoReporte, Map<String, Object> params, JRDataSource dataSource, String xPathPDF,  String idDcto) throws JRException, IOException {
    
	// Acá se almacenará en memoria el archivo exportado
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	
	
	JasperReport jasperReport;
		
	//jasperReport = JasperCompileManager.compileReport(REPORT_FOLDER + File.separator + fileName + JRXML);
	
	//2-Compilamos el archivo XML y lo cargamos en memoria
	jasperReport = JasperCompileManager.compileReport(xPathReport + File.separator + fileName + JRXML);
	
	// Se  llena el informe utilizando JasperFillManager, se le pasan parametros (flujo de entrada, los parametros y los datos)
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
	
	
	
	// Se verifica si el tipoReporte especificado es EXCEL
	if (tipoReporte.equalsIgnoreCase(TipoReporteEnum.EXCEL.toString())) {
		JRXlsxExporter exporter = new JRXlsxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
		SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		configuration.setDetectCellType(true);
		configuration.setCollapseRowSpan(true);
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		
	} else { // Si es PDF
		JasperExportManager.exportReportToPdfFile(jasperPrint, xPathPDF + idDcto  + ".pdf" );
		//JasperExportManager.exportReportToPdfFile(jasperPrint, xPathXML + idDcto  + ".xml" );
	}

	// Devolvemos el flujo de Bytes que contiene el informe exportado
	return stream;
}


}
