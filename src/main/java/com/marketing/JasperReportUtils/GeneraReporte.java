package com.marketing.JasperReportUtils;

import org.springframework.beans.factory.annotation.Autowired;

import com.marketing.Service.dbaquamovil.TblLocalesService;

import javax.servlet.ServletOutputStream;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource; 

public class GeneraReporte {

	@Autowired
	TblLocalesService tblLocalesService;
	
	private int idLocal;
    private String tituloReporte; 
    private String nombreReporte;
    private String formatoReporte;
    
    
    
    JasperReport jasperReport;

    //
    JasperPrint jasperPrint;
    
    Map<String, Object> pars = new HashMap();
    
    String xPathReport;
    
    String reportName; // Acá se le concatenda a la ruta donde está el reporte el nombre del reporte 
    
    // Acá se le pasan los valores a la variable pars y posteriormente se pasa pars como argumento a JapserReport para hacer la consulta del reporte 
    
    
    //
    JRBeanCollectionDataSource dataSource;
    
    public void generarReporte() {
        try {
        	 xPathReport = tblLocalesService.obtenerRutaReportePorIdLocal(idLocal);
            
             reportName = xPathReport + getNombreReporte(); // Acá se le concatenda a la ruta donde está el reporte el nombre del reporte 
        	
            pars.put("p_idLocal", getIdLocal());
            pars.put("p_pathReport", xPathReport);
            
            
        	//Compilamos el archivo XML y lo cargamos en memoria
            jasperReport = JasperCompileManager.compileReport(reportName + ".jrxml");
            
            //Llenamos el reporte con la informaciïón (de la DB)
            jasperPrint = JasperFillManager.fillReport(jasperReport, pars, dataSource);
            
            if (getFormatoReporte().compareTo("Pantalla") == 0) { // Si es PDF

                //-- PDF
         

                //
                System.out.println("Done!");

            }

            //------------------------------------------------------------------
            if (getFormatoReporte().compareTo("Archivo") == 0) {

                //--------------------------------------------------------------
   

            }
            
        } catch (Exception e) {
        	System.out.println(e);
            e.printStackTrace();
            
        }
    }
    
	public int getIdLocal() {
		return idLocal;
	}
	public void setIdLocal(int idLocal) {
		this.idLocal = idLocal;
	}
	public String getTituloReporte() {
		return tituloReporte;
	}
	public void setTituloReporte(String tituloReporte) {
		this.tituloReporte = tituloReporte;
	}
	public String getNombreReporte() {
		return nombreReporte;
	}
	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}
	public String getFormatoReporte() {
		return formatoReporte;
	}
	public void setFormatoReporte(String formatoReporte) {
		this.formatoReporte = formatoReporte;
	}
    
    
}
