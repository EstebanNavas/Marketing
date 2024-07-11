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
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.MailjetTask;
import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaEventoLog;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblAgendaEventoLogDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblAgendaEventoLogService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.enums.TipoReporteEnum;
import com.marketing.Utilidades.GeneradorZip;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReporteFacturaMailXFiltro {

	@Autowired 
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblDctosService TblDctosService;
	
	@Autowired
	TblPagosService tblPagosService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	TblAgendaEventoLogService tblAgendaEventoLogService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	MailjetTask mailjetTask;
	
	
	@GetMapping("/ReporteFacturaMailXFiltro")
	public String reporteFacturaMailXFiltro (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();

				// Obtenemos la lista de periodos 
				List <TblDctosPeriodo> Periodos = tblDctosPeriodoService.ListaTotalPeriodos(idLocal);
				model.addAttribute("xPeriodos", Periodos);
				
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
				
				}
				
				
				List<TblTercerosRuta> Rutas = tblTercerosRutaService.ListaRutas(idLocal);
				model.addAttribute("xRutas", Rutas);
				

	
		
		
		return "Reporte/ReporteFacturaMailXFiltro";
	}
	
	
	@PostMapping("/DescargarReporteFacturaMailXFiltro")
	public ResponseEntity<Map<String, Object>> DescargarReporteFacturaMailXFiltro(HttpServletRequest request,
			@RequestParam("PeriodoCobro") String idPeriodo, 
			@RequestParam("Ruta") Integer idRuta, 
			Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		Integer IdUsuario = usuario.getIdUsuario();
	    
	    Map<String, Object> response = new HashMap<>();
	    // Crea la lista de strings
	   // List<String> listaIdClientes = new ArrayList<>();
	    
	    System.out.println("SI ENTRÓ A  DescargarReporteFacturaMailXFiltroIDCLIENTE");

	  //Obtenemos la lista de los idClientes por idRuta
	    List<String> listaIdClientes = tblTercerosService.ObtenerListaTercerosPorRuta(usuario.getIdLocal(), idRuta);

	    
	       // String idPeriodo = (String) requestBody.get("PeriodoCobro");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	        Double idPeriodoDouble = Double.parseDouble(idPeriodo);

	        String formato = "PDF";
	        

	        System.out.println("listaIdClientes es : " + listaIdClientes);
	        Integer idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 1120;
		    Integer xIdTipoOrden = 7;
		    
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
		   String xFromAddress = ""; // SE OBTIENE EL EMAIL DEL LOCAL
		   String xToAddress = "";                            // EN ESTA VARIABLE SE VA A GUARDAR EL CORREO DE LOS USUARIOS
		   String xTextoEmail = "";
		   String xTxtFactura = "";
		   String xPrefijo = "";
		   String xPathFileGralZip = "";
		   String NitNE = "";
		   String NombreLocal = "";
		   String Prefijo = "";
		   
		   String xPathReport = "";

		   String xPathImagen = "";
		   
		   String xTextoComentario = " ==> Su factura presenta cuentas vencidas."
	                + " Lo esperamos en la administración del acueducto para normalizar su situación. ";
		   
		   String xTextoSubsidioContribucion = "==> Incluye subsidio y contribución del mes";
		   
		   
		   String xCharSeparator = File.separator;
	        String xPathFileGral = ""; 
	        String StringPathLinux = "/home/sw"; 
	        String StringPathWindows = "c:"; 
	        
	        // Linux
	        if (xCharSeparator.compareTo("/") == 0) {

	            // Linux  /home/sw             
	            xPathFileGral = StringPathLinux;

	        } else {

	            // Windows  C:\\proyectoWeb  
	            xPathFileGral = StringPathWindows;
	        }  
	        
	        Integer xEstadoGeneraIAC = null;
	        
	        int xEstadoGeneraIAC_SI = 1;
	        
	        String xPathPDF = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "BDMailFactura" + xCharSeparator + idLocal + xCharSeparator;
	        String xPathFileChar = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "img" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator;
	        String xPathBarCode = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator;
	        String xPathQr = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "qr" + xCharSeparator + idLocal + xCharSeparator;
	        String xPathZippdfxml = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "zippdfxml" + xCharSeparator + idLocal + xCharSeparator;
	        
		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 
			    params.put("p_idPeriodo", idPeriodo);
			    params.put("p_nombreLocal", L.getNombreLocal());
			    NombreLocal = L.getNombreLocal();
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
			    Prefijo = L.getPrefijo();
			    params.put("p_fechaResolucion", L.getFechaResolucion());
			    params.put("p_ciudad", L.getCiudad());
			    params.put("p_rango", L.getRango());
			    
			    xPathImagen = L.getPathImagen();
			    String xFirmaDigital = xPathImagen + "codigoBarra_" + idLocal.toString() + ".jpg";
				   
		        System.out.println("xFirmaDigital es : " + xFirmaDigital);
			    
			    System.out.println("xPathImagen es : " + xPathImagen);
			    String xLogoName = xPathImagen + idLocal.toString() + ".jpg";
			   // params.put("p_logo", xLogoName);
			    params.put("p_cuentaBanco", L.getCuentaBanco());
			    params.put("p_idTipoOrden", IdTipoOrdenINI);
			    params.put("p_txtFactura", L.getTxtFactura());
			    params.put("p_textoLegal", L.getTextoLegal());
			    params.put("p_textoComentario", xTextoComentario);
			    params.put("p_textoSubsidioContribucion", xTextoSubsidioContribucion);
			    params.put("p_historiaConsumo", "Histórico M3 : ");
			    params.put("p_pathFileChar", xPathFileChar);
			    params.put("p_firmaDigital", xFirmaDigital);
			    params.put("p_Qr", xPathQr);
			    params.put("p_representanteLegal", L.getRepresentanteLegal());
			    params.put("p_txtSuspension", L.getTxtSuspension());
			    
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
			    xEstadoGeneraIAC = L.getEstadoGeneraIAC();
			    
			    xFromAddress = L.getEmail();
			    xTextoEmail = L.getTextoEmail();
			    xTxtFactura = L.getTxtFactura();
			    xPrefijo = L.getPrefijo();
			    xPathFileGralZip = L.getPathFileGral();
			    NitNE = L.getNitNE();
			    
		    }
		    
		    // Genera imagen IAC CODE128
	        if (xEstadoGeneraIAC_SI == xEstadoGeneraIAC) {
	            String xBarraName = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            params.put("p_barraName", xBarraName);
	        }
	        
	        
	        String xAsunto = "";
            String xMensaje = "";
            
            //--- Crea directorio 
            File zipDirectory = new File(xPathZippdfxml);
            if (!zipDirectory.isDirectory()) {
                zipDirectory.mkdirs();
            }
		   
            

            List <TblDctosPeriodo> periodo =  tblDctosPeriodoService.listaUnFCH(idPeriodoInt, idLocal);
            
            String xNombrePeriodo = "";
  
            
            String xFechaConRecargoStr = "";
            
            for(TblDctosPeriodo P : periodo) {
            	
            	xNombrePeriodo = P.getNombrePeriodo();
            	Timestamp xFechaConRecargo = P.getFechaConRecargo();
            	
            	xFechaConRecargoStr = xFechaConRecargo.toString();
            	
            }
            
            
            
            
		    
		   

	            // Obtenemos la lista de los terceros que NO se le hayan enviado Mail 
	            List<TercerosDTO> alista =  tblTercerosService.listaUnCliente(idLocal, idPeriodoInt, listaIdClientes);
	            
	            
	            Integer idDcto = 0;
	            
	            // Obtener el idCto
	            for(TercerosDTO aList : alista) {
	            	
	            	idDcto = aList.getIdDcto();
	            	
	            	GeneradorZip generadorZip = new GeneradorZip();
	                String xSistema = "aquamovil";
	                
	                List<TblDctosOrdenesDTO> lista = null;
	    		    
	                System.out.println("alista ess : " + aList.getIdCliente());
	                // QUERY PARA ALIMENTAR EL DATASOURCE
	                lista = tblDctosOrdenesService.listaUnClienteProducto(idLocal, listaIdClientes, idPeriodoDouble);
	    		    	
	    	            System.out.println("lista " + lista);
	    		    
	    	    
	    			    // Se crea una instancia de JRBeanCollectionDataSource con la lista 
	    			    JRDataSource dataSource = new JRBeanCollectionDataSource(lista);
	    			    
	    			    ReportesDTO dto = reporteSmsServiceApi.ReporteEnCarpeta(params, dataSource, formato, xFileNameReporte, xPathReport, xPathPDF, xPathPDF, idDcto); // Incluir (params, dataSource, formato, xFileNameReporte)
	    			    
	                
	                
	                generadorZip.AgregarPdfAZip(idLocal, xSistema, idDcto, xPathFileGralZip);
	                
	                String xCodigoTipoDcto = "01";
	                
	                xAsunto = NitNE + ";" + NombreLocal + ";" + Prefijo + idDcto + ";" + xCodigoTipoDcto + ";" + NombreLocal;
	                
	                xMensaje = "PERIODO COBRO: " + xNombrePeriodo + "\n"
                            + "FECHA PAGO CON RECARGO: " + xFechaConRecargoStr + "\n"
                            + xTextoEmail;
	                
	                xToAddress = aList.getEmail();
	                String xTextPart = "";
	                String PathFile = xPathPDF + idDcto + ".pdf";
	                String FileName = idDcto + ".pdf";
	                
	                //Enviamos la factura por Email
	                mailjetTask.ejecutarJar(idLocal, xAsunto, xTextPart, PathFile, idDcto, FileName, xToAddress, xTextoEmail, xPathZippdfxml);
	                
	            }

		    
		    
			    return ResponseEntity.ok(response);
		}
	
	

	
	
	@PostMapping("/DescargarReporteFacturaMailXFiltroIDCLIENTE")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> DescargarReporteFacturaMailXFiltroIDCLIENTE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Map<String, Object> response = new HashMap<>();
	    // Crea la lista de strings
	    List<String> listaIdClientes = new ArrayList<>();
	    
	    System.out.println("SI ENTRÓ A  DescargarReporteFacturaMailXFiltroIDCLIENTE");

	        // Obtenemos los datos del JSON recibido
	    	String idCliente = (String) requestBody.get("idTercero");
	    	// Agrega el idCliente a la lista
	    	listaIdClientes.add(idCliente);
	    
	        String idPeriodo = (String) requestBody.get("PeriodoCobro");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	        Double idPeriodoDouble = Double.parseDouble(idPeriodo);

	        String formato = "PDF";
	        

	        System.out.println("idCliente es : " + idCliente);
	        Integer idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 1140;
		    Integer xIdTipoOrden = 7;
		    
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
		   String xFromAddress = ""; // SE OBTIENE EL EMAIL DEL LOCAL
		   String xToAddress = "";                            // EN ESTA VARIABLE SE VA A GUARDAR EL CORREO DE LOS USUARIOS
		   String xTextoEmail = "";
		   String xTxtFactura = "";
		   String xPrefijo = "";
		   String xPathFileGralZip = "";
		   String NitNE = "";
		   String NombreLocal = "";
		   String Prefijo = "";
		   
		   String xPathReport = "";

		   String xPathImagen = "";
		   
		   String xTextoComentario = " ==> Su factura presenta cuentas vencidas."
	                + " Lo esperamos en la administración del acueducto para normalizar su situación. ";
		   
		   String xTextoSubsidioContribucion = "==> Incluye subsidio y contribución del mes";
		   
		   
		   String xCharSeparator = File.separator;
	        String xPathFileGralDB = ""; 
	        String StringPathLinux = "/home/sw"; 
	        String StringPathWindows = "c:"; 
	        
	        // Linux
//	        if (xCharSeparator.compareTo("/") == 0) {
//
//	            // Linux  /home/sw             
//	        	xPathFileGralDB = StringPathLinux;
//
//	        } else {
//
//	            // Windows  C:\\proyectoWeb  
//	        	xPathFileGralDB = StringPathWindows;
//	        }  
	        
	        Integer xEstadoGeneraIAC = null;
	        
	        int xEstadoGeneraIAC_SI = 1;
	        
	        
	        
	       
	        
	        
		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 
			    params.put("p_idPeriodo", idPeriodo);
			    params.put("p_nombreLocal", L.getNombreLocal());
			    NombreLocal = L.getNombreLocal();
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
			    Prefijo = L.getPrefijo();
			    params.put("p_fechaResolucion", L.getFechaResolucion());
			    params.put("p_ciudad", L.getCiudad());
			    params.put("p_rango", L.getRango());
			    
			    xPathImagen = L.getPathImagen();
			    String xFirmaDigital = xPathImagen + "codigoBarra_" + idLocal.toString() + ".jpg";
				   
		        System.out.println("xFirmaDigital es : " + xFirmaDigital);
			    
			    System.out.println("xPathImagen es : " + xPathImagen);
			    String xLogoName = xPathImagen + idLocal.toString() + ".jpg";
			    params.put("p_logo", xLogoName);
			    System.out.println("xLogoName es : " + xLogoName);
			    params.put("p_cuentaBanco", L.getCuentaBanco());
			    params.put("p_idTipoOrden", IdTipoOrdenINI);
			    params.put("p_txtFactura", L.getTxtFactura());
			    params.put("p_textoLegal", L.getTextoLegal());
			    params.put("p_textoComentario", xTextoComentario);
			    params.put("p_textoSubsidioContribucion", xTextoSubsidioContribucion);
			    params.put("p_historiaConsumo", "Histórico M3 : ");

			    params.put("p_firmaDigital", xFirmaDigital);
			  
			    params.put("p_representanteLegal", L.getRepresentanteLegal());
			    params.put("p_txtSuspension", L.getTxtSuspension());
			    
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
			    xEstadoGeneraIAC = L.getEstadoGeneraIAC();
			    
			    xFromAddress = L.getEmail();
			    xTextoEmail = L.getTextoEmail();
			    xTxtFactura = L.getTxtFactura();
			    xPrefijo = L.getPrefijo();
			    xPathFileGralDB = L.getPathFileGral(); //--------------------------------------------------------------------------------
			    NitNE = L.getNitNE();
			    
		    }
		    
		    
		    System.out.println("xPathFileGralDB es : " + xPathFileGralDB);
		    
		    
		    
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
	          //  String xBarraName = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            
	            String xBarraName = xPathFileGralDB + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            params.put("p_barraName", xBarraName);
	        }
	        
	        
	        String xAsunto = "";
            String xMensaje = "";
            
            
            System.out.println("xPathZippdfxml en rea directorio es " + xPathZippdfxml);
            
            //--- Crea directorio zippdfxml
            File zipDirectoryXml = new File(xPathZippdfxml);
            if (!zipDirectoryXml.isDirectory()) {
            	zipDirectoryXml.mkdirs();
            }
            
            //--- Crea directorio zip
            File zipDirectory = new File(xPathXML);
            if (!zipDirectory.isDirectory()) {
                zipDirectory.mkdirs();
            }
		   
            

            List <TblDctosPeriodo> periodo =  tblDctosPeriodoService.listaUnFCH(idPeriodoInt, idLocal);
            
            String xNombrePeriodo = "";
  
            
            String xFechaConRecargoStr = "";
            
            for(TblDctosPeriodo P : periodo) {
            	
            	xNombrePeriodo = P.getNombrePeriodo();
            	Timestamp xFechaConRecargo = P.getFechaConRecargo();
            	
            	xFechaConRecargoStr = xFechaConRecargo.toString();
            	
            }
            
            
            
            
		    
		   

	            // Obtenemos la lista de los terceros que NO se le hayan enviado Mail y que el estado Email sea activo =  1
	            List<TercerosDTO> alista =  tblTercerosService.listaUnCliente(idLocal, idPeriodoInt, listaIdClientes);
	            
	            System.out.println("alista es " + alista);
	            
	            // Verificamos si la alista está vacia
	            if(alista.isEmpty()) {
	            	
	            	System.out.println("alista vacia" );
	            	
	            	response.put("message", "VACIO");            	
	            	return ResponseEntity.ok(response);
	            }
	            
	            Integer idDcto = 0;
	            
	            // Obtener el idCto
	            for(TercerosDTO aList : alista) {
	            	
	            	idDcto = aList.getIdDcto();
	            	
	            	System.out.println("Ingresó al for ");
	            	
	            	System.out.println("idDcto es " + idDcto);
	            	
	            	GeneradorZip generadorZip = new GeneradorZip();
	                String xSistema = "aquamovil";
	                
	                List<TblDctosOrdenesDTO> lista = null;
	    		    

	                // QUERY PARA ALIMENTAR EL DATASOURCE
	                lista = tblDctosOrdenesService.listaUnClienteProducto(idLocal, listaIdClientes, idPeriodoDouble);
	    		    	
	    	            System.out.println("lista " + lista);
	    		    
	    	    
	    			    // Se crea una instancia de JRBeanCollectionDataSource con la lista 
	    			    JRDataSource dataSource = new JRBeanCollectionDataSource(lista);
	    			    
	    			    System.out.println("xFileNameReporte al crear el reporte " + xFileNameReporte);
	    			    System.out.println("xPathReport al crear el reporte " + xPathReport);
	    			    System.out.println("xPathPDF al crear el reporte " + xPathPDF);
	    			    
	    			    // Donde se guarda el reporte en CARPETA es en xPathPDF
	    			    ReportesDTO dto = reporteSmsServiceApi.ReporteEnCarpeta(params, dataSource, formato, xFileNameReporte, xPathReport, xPathPDF, xPathXML, idDcto); // Incluir (params, dataSource, formato, xFileNameReporte)
	    			    
	    			    System.out.println("xPathFileGralDB que se pasa como argumento es " + xPathFileGralDB);
	                
	                generadorZip.AgregarPdfAZip(idLocal, xSistema, idDcto, xPathFileGralDB);
	                
	                String xCodigoTipoDcto = "01";
	                
	                xAsunto = NitNE + ";" + NombreLocal + ";" + Prefijo + idDcto + ";" + xCodigoTipoDcto + ";" + NombreLocal;
	                
	                xMensaje = "PERIODO COBRO: " + xNombrePeriodo + "\n"
                            + "FECHA PAGO CON RECARGO: " + xFechaConRecargoStr + "\n"
                            + xTextoEmail;
	                
	                xToAddress = aList.getEmail();
	                String xTextPart = "";
	                String PathFile = xPathPDF + idDcto + ".pdf";
	                String FileName = idDcto + ".pdf";
	                
	                //Enviamos la factura por Email
	                mailjetTask.ejecutarJar(idLocal, xAsunto, xTextPart, PathFile, idDcto, FileName, xToAddress, xTextoEmail, xPathZippdfxml);
	                
	                System.out.println("Se ejecuta el mailjetTask ");
	                
	            }

		    
	            response.put("message", "OK");   
			    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	@PostMapping("/EnviarTodasFacturasCliente")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> EnviarTodasFacturasCliente(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Map<String, Object> response = new HashMap<>();
	    // Crea la lista de strings
	    List<String> listaIdClientes = new ArrayList<>();
	    
	    System.out.println("SI ENTRÓ A  EnviarTodasFacturasCliente");

	        // Obtenemos los datos del JSON recibido
	    	//listaIdClientes.add(idCliente);
	    
	        String idPeriodo = (String) requestBody.get("PeriodoCobro");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	        Double idPeriodoDouble = Double.parseDouble(idPeriodo);

	        String formato = "PDF";
	        
	        Integer idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 1140;
		    Integer xIdTipoOrden = 7;
		    
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
		   String xFromAddress = ""; // SE OBTIENE EL EMAIL DEL LOCAL
		   String xToAddress = "";                            // EN ESTA VARIABLE SE VA A GUARDAR EL CORREO DE LOS USUARIOS
		   String xTextoEmail = "";
		   String xTxtFactura = "";
		   String xPrefijo = "";
		   String xPathFileGralZip = "";
		   String NitNE = "";
		   String NombreLocal = "";
		   String Prefijo = "";
		   
		   String xPathReport = "";

		   String xPathImagen = "";
		   
		   String xTextoComentario = " ==> Su factura presenta cuentas vencidas."
	                + " Lo esperamos en la administración del acueducto para normalizar su situación. ";
		   
		   String xTextoSubsidioContribucion = "==> Incluye subsidio y contribución del mes";
		   
		   
		   String xCharSeparator = File.separator;
	        String xPathFileGralDB = ""; 
	        String StringPathLinux = "/home/sw"; 
	        String StringPathWindows = "c:"; 
	        

	        
	        Integer xEstadoGeneraIAC = null;
	        
	        int xEstadoGeneraIAC_SI = 1;
	        

		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 
			    params.put("p_idPeriodo", idPeriodo);
			    params.put("p_nombreLocal", L.getNombreLocal());
			    NombreLocal = L.getNombreLocal();
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
			    Prefijo = L.getPrefijo();
			    params.put("p_fechaResolucion", L.getFechaResolucion());
			    params.put("p_ciudad", L.getCiudad());
			    params.put("p_rango", L.getRango());
			    
			    xPathImagen = L.getPathImagen();
			    String xFirmaDigital = xPathImagen + "codigoBarra_" + idLocal.toString() + ".jpg";
				   
		        System.out.println("xFirmaDigital es : " + xFirmaDigital);
			    
			    System.out.println("xPathImagen es : " + xPathImagen);
			    String xLogoName = xPathImagen + idLocal.toString() + ".jpg";
			    params.put("p_logo", xLogoName);
			    System.out.println("xLogoName es : " + xLogoName);
			    params.put("p_cuentaBanco", L.getCuentaBanco());
			    params.put("p_idTipoOrden", IdTipoOrdenINI);
			    params.put("p_txtFactura", L.getTxtFactura());
			    params.put("p_textoLegal", L.getTextoLegal());
			    params.put("p_textoComentario", xTextoComentario);
			    params.put("p_textoSubsidioContribucion", xTextoSubsidioContribucion);
			    params.put("p_historiaConsumo", "Histórico M3 : ");

			    params.put("p_firmaDigital", xFirmaDigital);
			  
			    params.put("p_representanteLegal", L.getRepresentanteLegal());
			    params.put("p_txtSuspension", L.getTxtSuspension());
			    
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
			    xEstadoGeneraIAC = L.getEstadoGeneraIAC();
			    
			    xFromAddress = L.getEmail();
			    xTextoEmail = L.getTextoEmail();
			    xTxtFactura = L.getTxtFactura();
			    xPrefijo = L.getPrefijo();
			    xPathFileGralDB = L.getPathFileGral(); //--------------------------------------------------------------------------------
			    NitNE = L.getNitNE();
			    
		    }
		    
		    
		    System.out.println("xPathFileGralDB es : " + xPathFileGralDB);
		    
		    
		    
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
	          //  String xBarraName = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            
	            String xBarraName = xPathFileGralDB + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            params.put("p_barraName", xBarraName);
	        }
	        
	        
	        String xAsunto = "";
            String xMensaje = "";
            
            
            System.out.println("xPathZippdfxml en rea directorio es " + xPathZippdfxml);
            
            //--- Crea directorio zippdfxml
            File zipDirectoryXml = new File(xPathZippdfxml);
            if (!zipDirectoryXml.isDirectory()) {
            	zipDirectoryXml.mkdirs();
            }
            
            //--- Crea directorio zip
            File zipDirectory = new File(xPathXML);
            if (!zipDirectory.isDirectory()) {
                zipDirectory.mkdirs();
            }
		   
            

            List <TblDctosPeriodo> periodo =  tblDctosPeriodoService.listaUnFCH(idPeriodoInt, idLocal);
            
            String xNombrePeriodo = "";
  
            
            String xFechaConRecargoStr = "";
            
            for(TblDctosPeriodo P : periodo) {
            	
            	xNombrePeriodo = P.getNombrePeriodo();
            	Timestamp xFechaConRecargo = P.getFechaConRecargo();
            	
            	xFechaConRecargoStr = xFechaConRecargo.toString();
            	
            }
            
            
            
//             listaIdClientes.add("250");
//             listaIdClientes.add("127");
//             listaIdClientes.add("128");
//             
//             
//             for(String  aList : listaIdClientes) {
//	            	
//	            	System.out.println("ID Cliente - " + aList);
//
//	            	
//	            }
		    
		     

	            // Obtenemos la lista de los terceros que NO se le hayan enviado Mail y que el estado Email sea activo =  1
	            //List<TercerosDTO> alista =  tblTercerosService.listaUnCliente(idLocal, idPeriodoInt, listaIdClientes);
	            
	            //Obtenemos todos los clientes que NO se le hayan enviado Mail y que el estado Email sea activo =  1
	            List<TercerosDTO>  todosClientes = tblTercerosService.listaTodosLosClientesEstadoFacturaAct(idLocal, idPeriodoInt);
	            
	            System.out.println("todosClientes es " + todosClientes);
	            
	            // Verificamos si la alista está vacia
	            if(todosClientes.isEmpty()) {
	            	
	            	System.out.println("alista vacia" );
	            	
	            	response.put("message", "VACIO");            	
    	
	            }
	            
	            // Recorremos la lista de los clientes y guardamos cada idCliente en listaIdClientes
	            for(TercerosDTO cliente : todosClientes) {
            		
            		listaIdClientes.add(cliente.getIdCliente().toString());
            		
            	}


	            
	           
	            
	            Integer idDcto = 0;
	            

//	            // Obtener el idCto


	            for(TercerosDTO aList : todosClientes) {
	            	
	            	idDcto = aList.getIdDcto();
	            	
	            	System.out.println("Ingresó al for ");
	            	
	            	System.out.println("idDcto es " + idDcto);
	            	
	            	GeneradorZip generadorZip = new GeneradorZip();
	                String xSistema = "aquamovil";
	                
	                List<TblDctosOrdenesDTO> lista = null;
	    		    

	                // QUERY PARA ALIMENTAR EL DATASOURCE
	                lista = tblDctosOrdenesService.listaUnClienteProducto(idLocal, listaIdClientes, idPeriodoDouble);
	    		    	
	    	            System.out.println("lista " + lista);
	    		    
	    	    
	    			    // Se crea una instancia de JRBeanCollectionDataSource con la lista 
	    			    JRDataSource dataSource = new JRBeanCollectionDataSource(lista);
	    			    
	    			    System.out.println("xFileNameReporte al crear el reporte " + xFileNameReporte);
	    			    System.out.println("xPathReport al crear el reporte " + xPathReport);
	    			    System.out.println("xPathPDF al crear el reporte " + xPathPDF);
	    			    
	    			    // Donde se guarda el reporte en CARPETA es en xPathPDF
	    			    ReportesDTO dto = reporteSmsServiceApi.ReporteEnCarpeta(params, dataSource, formato, xFileNameReporte, xPathReport, xPathPDF, xPathXML, idDcto); // Incluir (params, dataSource, formato, xFileNameReporte)
	    			    
	    			    System.out.println("xPathFileGralDB que se pasa como argumento es " + xPathFileGralDB);
	                
	                generadorZip.AgregarPdfAZip(idLocal, xSistema, idDcto, xPathFileGralDB);
	                
	                String xCodigoTipoDcto = "01";
	                
	                xAsunto = NitNE + ";" + NombreLocal + ";" + Prefijo + idDcto + ";" + xCodigoTipoDcto + ";" + NombreLocal;
	                
	                xMensaje = "PERIODO COBRO: " + xNombrePeriodo + "\n"
                            + "FECHA PAGO CON RECARGO: " + xFechaConRecargoStr + "\n"
                            + xTextoEmail;
	                
	                xToAddress = aList.getEmail();
	                String xTextPart = "";
	                String PathFile = xPathPDF + idDcto + ".pdf";
	                String FileName = idDcto + ".pdf";
	                
	                //Enviamos la factura por Email
	                mailjetTask.ejecutarJar(idLocal, xAsunto, xTextPart, PathFile, idDcto, FileName, xToAddress, xTextoEmail, xPathZippdfxml);
	                
	                System.out.println("Se ejecuta el mailjetTask ");
	                
	            }

		    
	            response.put("message", "OK");   
			    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/DescargarReporteConsultaEnvioMail")
	@ResponseBody
	public ResponseEntity<Resource>  DescargarReporteFacturaProductoIDCLIENTE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    // Crea la lista de strings
	    List<String> listaIdClientes = new ArrayList<>();
	    
	    System.out.println("SI ENTRÓ A  DescargarReporteEstadoProductoNUID");

	        // Obtenemos los datos del JSON recibido
	    	String idCliente = (String) requestBody.get("idTercero");
	    	// Agrega el idCliente a la lista
	    	listaIdClientes.add(idCliente);
	    
	        String idPeriodo = (String) requestBody.get("idPeriodo");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	        Double idPeriodoDouble = Double.parseDouble(idPeriodo);

	        String formato = (String) requestBody.get("formato");
	        

	        System.out.println("idCliente es : " + idCliente);
	        Integer idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 2730;
		    Integer xIdTipoOrden = 9;
		    
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
	        String xPathFileGral = ""; 
	        
	        String StringPathLinux = "/home/sw"; 
	        String StringPathWindows = "c:"; 
	        
	     // Linux
	        if (xCharSeparator.compareTo("/") == 0) {

	            // Linux  /home/sw             
	            xPathFileGral = StringPathLinux;

	        } else {

	            // Windows  C:\\proyectoWeb  
	            xPathFileGral = StringPathWindows;
	        }  
	        
	        Integer xEstadoGeneraIAC = null;
	        
	        int xEstadoGeneraIAC_SI = 1;
	        
	        
	        String xPathFileChar = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "img" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator;
	        System.out.println("xPathFileChar es : " + xPathFileChar);
	        
		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 
			    params.put("p_idPeriodo", idPeriodo);
			    params.put("p_nombreLocal", L.getNombreLocal());
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
			    System.out.println("xLogoName es : " + xLogoName);
			    params.put("p_logo", xLogoName);
			    params.put("p_cuentaBanco", L.getCuentaBanco());
			    params.put("p_idTipoOrden", IdTipoOrdenINI);
			    params.put("p_txtFactura", L.getTxtFactura());
			    params.put("p_textoLegal", L.getTextoLegal());
			    params.put("p_textoComentario", xTextoComentario);
			    params.put("p_textoSubsidioContribucion", xTextoSubsidioContribucion);
			    params.put("p_historiaConsumo", "Histórico M3 : ");
			    params.put("p_pathFileChar", xPathFileChar);
			    params.put("p_firmaDigital", xFirmaDigital);
			    params.put("p_representanteLegal", L.getRepresentanteLegal());
			    
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
			    xEstadoGeneraIAC = L.getEstadoGeneraIAC();
		    }
		    
		    // Genera imagen IAC CODE128
	        if (xEstadoGeneraIAC_SI == xEstadoGeneraIAC) {
	            String xBarraName = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            params.put("p_barraName", xBarraName);
	        }
	        
	        System.out.println("idLocal es : " + idLocal);
		   
		    
	        List<TblAgendaEventoLogDTO>  lista = null;
		    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblAgendaEventoLogService.listaReporteEmail(idLocal, idPeriodoInt);
		    	
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
	
}
