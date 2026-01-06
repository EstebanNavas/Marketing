package com.marketing.Controller;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.marketing.ActivaServicioTask;
import com.marketing.MailjetTask;
import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Service.DBMailMarketing.TblEstilosSiteService;
import com.marketing.Service.DBMailMarketing.TblNoticiasSiteService;
import com.marketing.Service.DBMailMarketing.TblSiteNoticiasService;
import com.marketing.Service.DBMailMarketing.TblSiteStyleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import net.sf.jasperreports.engine.JRException;


@RestController
public class DescargarFacturaDeManeraExternaController {
	

	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblDctosService tblDctosService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;

	
	@GetMapping("/public/descargarFactura")
	public ResponseEntity<Resource> descargarFacturaPorUrl(
	        @RequestParam Integer idLocal,
	        @RequestParam String idCliente
	) throws JRException, IOException, SQLException {

	    System.out.println("Descarga externa factura");
	    System.out.println("idLocal: " + idLocal);
	    System.out.println("idCliente: " + idCliente);
	    
	    
	 // Crea la lista de strings
	    List<String> listaIdClientes = new ArrayList<>();
	    
	   // Agrega el idCliente a la lista
    	listaIdClientes.add(idCliente);
	    
	    System.out.println("SI ENTRÓ A  descargarFacturaPorUrl");


	        System.out.println("idLocal es " + idLocal );
	        
	        Integer idPeriodoInt = 0;
	        	        
	        // Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
			
			for(TblDctosPeriodo P : PeriodoActivo) {
				
				idPeriodoInt =  P.getIdPeriodo();
			
			}
			
			String idPeriodo = idPeriodoInt.toString();
			Double idPeriodoDouble = Double.parseDouble(idPeriodo);

	        String formato = "PDF";
	        

	        System.out.println("idCliente es : " + idCliente);
	        //Integer idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 1140;
		    
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
			
		    Map<String, Object> params = new HashMap<>();
		    params.put("tipo", formato);
		    params.put("idLocal", idLocal);

		   Integer IdTipoOrdenINI = 9;
		   Integer IdTipoOrdenFIN = 29;
		   Integer IndicadorINICIAL = 1;
		   Integer IndicadorFINNAL = 2;
		   
		   String xPathReport = "";

		   String xPathImagen = "";
		   
		   String xTextoComentario = " ==> Su factura presenta cuentas vencidas."
	                + " Lo esperamos en la administración del acueducto para normalizar su situación. ";
		   
		   String xTextoSubsidioContribucion = "==> Incluye subsidio y contribución del mes";
		   
		   
		   String xCharSeparator = File.separator;
	      //  String xPathFileGral = ""; 
		   	String xPathFileGralDB = ""; 
	        String StringPathLinux = "/home/sw"; 
	        String StringPathWindows = "c:"; 
	        

	        Integer xEstadoGeneraIAC = null;
	        
	        int xEstadoGeneraIAC_SI = 1;
	        
	        
	      
	        
		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 
			    params.put("p_idPeriodo", idPeriodo);
			    params.put("p_nombreLocal", L.getNombreLocal());
			    params.put("p_razonSocial", L.getRazonSocial());
			    params.put("p_nit", L.getNit());
			    params.put("p_titulo", xTituloReporte);
			    params.put("p_direccion", L.getDireccion());
			    params.put("p_idLocal", idLocal);
			    params.put("p_indicadorINI", IndicadorINICIAL);
			    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
			    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
			    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
			    params.put("p_telefono", L.getTelefono());
			    params.put("p_fax", L.getFax());
			    params.put("p_email", L.getEmail());
			    params.put("p_resolucion", L.getResolucion());
			    params.put("p_prefijo", L.getPrefijo());
			    params.put("p_fechaResolucion", L.getFechaResolucion());
			    params.put("p_ciudad", L.getCiudad());
			    params.put("p_rango", L.getRango());
			    
			    xPathImagen = L.getPathImagen();
			    String xFirmaDigital = xPathImagen + "codigoBarra_" + idLocal.toString() + ".jpg";
				   
		        System.out.println("xFirmaDigital es : " + xFirmaDigital);
			    
			    System.out.println("xPathImagen es : " + xPathImagen);
			    String xLogoName = xPathImagen + idLocal.toString() + ".jpg";
			    params.put("p_logo", xLogoName);
			    params.put("p_cuentaBanco", L.getCuentaBanco());
			    params.put("p_idTipoOrden", IdTipoOrdenINI);
			    params.put("p_txtFactura", L.getTxtFactura());
			    params.put("p_textoLegal", L.getTextoLegal());
			    params.put("p_textoComentario", xTextoComentario);
			    params.put("p_textoSubsidioContribucion", xTextoSubsidioContribucion);
			    params.put("p_historiaConsumo", "Histórico M3 : ");
			    params.put("p_firmaDigital", xFirmaDigital);
			    params.put("p_logoSuperServicios", xPathImagen + "superServicios.jpg");
			    params.put("p_representanteLegal", L.getRepresentanteLegal());
			    params.put("p_firmaRepresentante", xPathImagen + "firma_" + idLocal.toString() + ".jpg");
			    params.put("p_textoLegal", L.getTextoLegal());
			    
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
			    xEstadoGeneraIAC = L.getEstadoGeneraIAC();
			    xPathFileGralDB = L.getPathFileGral(); //--------------------------------------------------------------------------------
		    }
		    
		    List <TblDctosPeriodo> infoPeriodo = tblDctosPeriodoService.ObtenerPeriodo(idLocal, idPeriodoInt);
     	    for(TblDctosPeriodo periodo : infoPeriodo) {
		    	
		    	params.put("p_textoPeriodo", periodo.getTextoPeriodo());
		    }
		    
		    
		    String xPathPDF = xPathFileGralDB + "aquamovil" + xCharSeparator + "BDMailFactura" + xCharSeparator + idLocal + xCharSeparator;
		    String xPathXML = xPathFileGralDB + "aquamovil" + xCharSeparator + "zip" + xCharSeparator + idLocal + xCharSeparator;
	        String xPathFileChar = xPathFileGralDB + "aquamovil" + xCharSeparator + "img" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator;
	        String xPathBarCode = xPathFileGralDB+ "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator;
	        String xPathQr = xPathFileGralDB + "aquamovil" + xCharSeparator + "qr" + xCharSeparator + idLocal + xCharSeparator;
	        String xPathZippdfxml = xPathFileGralDB + "aquamovil" + xCharSeparator + "zippdfxml" + xCharSeparator + idLocal + xCharSeparator;
		    
	        
	        params.put("p_pathFileChar", xPathFileChar);
	        params.put("p_Qr", xPathQr);
		    
		    
		    // Genera imagen IAC CODE128
	        if (xEstadoGeneraIAC_SI == xEstadoGeneraIAC) {
	            
	        	String xBarraName = xPathFileGralDB + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            params.put("p_barraName", xBarraName);
	        }
	        
	        System.out.println("idLocal es : " + idLocal);
		   
		    
		    List<TblDctosOrdenesDTO> lista = null;
		    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblDctosOrdenesService.listaUnClienteProducto(idLocal, listaIdClientes, idPeriodoDouble);
		    	
	            System.out.println("lista " + lista);
		    
	    
			    // Se crea una instancia de JRBeanCollectionDataSource con la lista 
			    JRDataSource dataSource = new JRBeanCollectionDataSource(lista);
			    
			    ReportesDTO dto = reporteSmsServiceApi.Reportes(params, dataSource, formato, xFileNameReporte, xPathReport); // Incluir (params, dataSource, formato, xFileNameReporte)
			    
			    // Verifica si el stream tiene datos y, si no, realiza una lectura en un búfer
			    InputStream inputStream = dto.getStream();
			    if (inputStream == null) {
			        // Realiza una lectura en un búfer alternativo si dto.getStream() es nulo
			        byte[] emptyContent = new byte[0];
			        inputStream = new ByteArrayInputStream(emptyContent);
			    }

	    InputStreamResource resource = new InputStreamResource(inputStream);

	    return ResponseEntity.ok()
	            .header("Content-Disposition",
	                    "attachment; filename=\"" + dto.getFileName() + "\"")
	            .contentType(MediaType.APPLICATION_PDF)
	            .contentLength(dto.getLength())
	            .body(resource);
	}
}
