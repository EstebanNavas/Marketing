package com.marketing.ServiceApi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.marketing.Model.Reportes.ReporteSmsDTO;
import com.marketing.Model.Reportes.ReportesDTO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRDataSource;

public interface ReporteSmsServiceApi {

	ReporteSmsDTO obtenerReporteSms(Map<String, Object> params, JRDataSource dataSource, String formato, String xFileNameReporte, String xPathReport)throws JRException, IOException, SQLException;
	
	ReporteSmsDTO obtenerReportePQR(Map<String, Object> params, JRDataSource dataSource, String formato, String xFileNameReporte, String xPathReport)throws JRException, IOException, SQLException;
	
	ReporteSmsDTO obtenerReporteSUI(Map<String, Object> params, JRDataSource dataSource, String formato, String xFileNameReporte, String xPathReport)throws JRException, IOException, SQLException;
	
	ReportesDTO Reportes(Map<String, Object> params, JRDataSource dataSource, String formato, String xFileNameReporte, String xPathReport)throws JRException, IOException, SQLException;
	
	
	ReportesDTO ReporteEnCarpeta(Map<String, Object> params, JRDataSource dataSource, String formato, String xFileNameReporte, String xPathReport, String xPathPDF, String xPathXML,  int idDcto) throws JRException, IOException, SQLException;
	ReportesDTO ReporteEnCarpetaNotas(Map<String, Object> params, JRDataSource dataSource, String formato, String xFileNameReporte, String xPathReport, String xPathPDF,  String idDcto) throws JRException, IOException, SQLException;
}
