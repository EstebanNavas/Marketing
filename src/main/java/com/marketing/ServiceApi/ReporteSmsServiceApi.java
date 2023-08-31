package com.marketing.ServiceApi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.marketing.Model.Reportes.ReporteSmsDTO;

import net.sf.jasperreports.engine.JRException;

public interface ReporteSmsServiceApi {

	ReporteSmsDTO obtenerReporteSms(Map<String, Object> params)throws JRException, IOException, SQLException;
}
