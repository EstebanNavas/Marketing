package com.marketing.ServiceImpl;

import java.util.Map;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;


import com.marketing.Model.Reportes.ReporteSmsDTO;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.commons.JasperReportManager;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRException;

@Service
public class ReporteSmsServiceImpl implements ReporteSmsServiceApi{

	@Autowired
	JasperReportManager reportManager;
	
//	@Autowired
//	private DataSource dataSource;
	
	@Override
	public ReporteSmsDTO obtenerReporteSms (Map<String, Object> params) throws JRException, IOException, SQLException {
		
		// Obtenemos el nombre base del archivo sin la extensión.
		String fileName = "reporte_mensajes_sms";
		
		//Se determina la extensión del archivo en función del parámetro "tipo" seleccionado PDF o EXCEL
		String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx": ".pdf";
		
		// Se crea una instancia de ReporteSmsDTO para almacenar la información del reporte
		ReporteSmsDTO dto = new ReporteSmsDTO();
		
		// Se le setea el nombre del archivo a la variable dto
		dto.setFileName(fileName + extension);
		
		// Se llama al metdo export de la clase JasperReportManager para generar el archivo
		ByteArrayOutputStream stream = reportManager.export("reporte_mensajes_sms", params.get("tipo").toString(), params);
		
		//Se convierte el contenido de stream en un arrya de bytes
		byte[] bs = stream.toByteArray();
		
		// Se crea un nuevo ByteArrayInputStream utilizando el arreglo de bytes bs
		dto.setStream(new ByteArrayInputStream(bs));
		
		// Se establece la longitud del archivo en bytes
		dto.setLength(bs.length);
		
		return dto;
	}
}










