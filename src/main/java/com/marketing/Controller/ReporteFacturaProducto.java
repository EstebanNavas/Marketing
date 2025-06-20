package com.marketing.Controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctos;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblPagosDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReporteFacturaProducto {

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
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	
	@GetMapping("/ReporteFacturaProducto")
	public String reporteFacturaProducto (HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				
				// ----------------------------------------------------------- VALIDA INACTIVIDAD ------------------------------------------------------------
			    HttpSession session = request.getSession();
			    //Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
			    
			    @SuppressWarnings("unchecked")
				List<TblAgendaLogVisitas> UsuarioLogueado = (List<TblAgendaLogVisitas>) session.getAttribute("UsuarioLogueado");
			    
			    Integer estadoUsuario = 0;
			    

			        for (TblAgendaLogVisitas usuarioLog : UsuarioLogueado) {
			            Integer idLocalUsuario = usuarioLog.getIdLocal();
			            Integer idLogUsuario = usuarioLog.getIDLOG();
			            String sessionIdUsuario = usuarioLog.getSessionId();
			            
			            
			           estadoUsuario = controlDeInactividad.ingresa(idLocalUsuario, idLogUsuario, sessionIdUsuario);          
			        }
		    
			           if(estadoUsuario.equals(2)) {
			        	   System.out.println("USUARIO INACTIVO");
			        	   return "redirect:/";
			           }
				
				//------------------------------------------------------------------------------------------------------------------------------------------

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
				

	
		
		
		return "Reporte/ReporteFacturaProducto";
	}
	
	

	
	
	
	
	
	
	@PostMapping("/DescargarReporteFacturaProducto")
	public ResponseEntity<Resource> DescargarReporteFacturaProducto(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
	
		
		 // Obtenemos los datos del JSON recibido
        String idPeriodo = (String) requestBody.get("idPeriodo");
        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
        
        String Ruta = (String) requestBody.get("Ruta");
        Integer idRuta = Integer.parseInt(Ruta);
        
        String filtro = (String) requestBody.get("Filtro");
        Integer Filtro = Integer.parseInt(filtro);

        
       List<String> listaIdClientes = null;
       
       
       
       //Actualiza todos los email estado 2
       tblTercerosRepo.actualizaEstadoEmailInactivo(usuario.getIdLocal());
       
       
       //Actualiza estado Email = 1 de los email que esten correctos
       tblTercerosRepo.actualizaEstadoEmailOK(usuario.getIdLocal());
       
       
       
		
       int EstadoEmail = 2;
       int EstadoWhatsApp = 2;
       
		if(Filtro == 0) {
			System.out.println("Filtro es 0");

				//Obtenemos la lista de los idClientes por idRuta
				listaIdClientes  = tblTercerosService.ObtenerListaTercerosPorRuta(usuario.getIdLocal(), idRuta);

		}
		
		
		
		//Correo
		if(Filtro == 2) {
			
			System.out.println("Filtro es 2");
              if(idRuta == 0) {
				
				System.out.println("idRuta es " + idRuta );
				listaIdClientes = tblTercerosService.ObtenerListaTercerosEstadoEmailSinRuta(usuario.getIdLocal());
				
			}else {
				
				System.out.println("idRuta es " + idRuta );
				listaIdClientes = tblTercerosService.ObtenerListaTercerosEstadoEmail(usuario.getIdLocal(), idRuta);
			}
			
		}
		
		
		//WhatsApp
		if(Filtro == 3) {
			System.out.println("Filtro es 3");
            if(idRuta == 0) {
				
				System.out.println("idRuta es " + idRuta );
				listaIdClientes = tblTercerosService.ObtenerListaTercerosEstadoWhatsAppSinRuta(usuario.getIdLocal());
				
			}else {
				
				System.out.println("idRuta es " + idRuta );
				listaIdClientes = tblTercerosService.ObtenerListaTercerosEstadoWhatsApp(usuario.getIdLocal(), idRuta);
			}
			
		}
		
		
		
		 //WhatsApp - Email
				if(Filtro == 4) {
					System.out.println("Filtro es 4");
		            if(idRuta == 0) {
						
						System.out.println("idRuta es " + idRuta );
						listaIdClientes = tblTercerosService.ObtenerListaTercerosEstadoWhatsAppEstadoEmailSinRuta(usuario.getIdLocal());
						
					}else {
						
						System.out.println("idRuta es " + idRuta );
						listaIdClientes = tblTercerosService.ObtenerListaTercerosEstadoWhatsAppEstadoEmail(usuario.getIdLocal(), idRuta);
					}
					
				}
		
		
		
		
	    
		System.out.println("listaIdClientes : " + listaIdClientes);

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
		   String xPathFileGralDB = ""; 
		   String xPathReport = "";

		   String xPathImagen = "";
		   
		   String xTextoComentario = " ==> Su factura presenta cuentas vencidas."
	                + " Lo esperamos en la administración del acueducto para normalizar su situación. ";
		   
		   String xTextoSubsidioContribucion = "==> Incluye subsidio y contribución del mes";
		   
		   
		   String xCharSeparator = File.separator;
	        String xPathFileGral = ""; 
	        
	        Integer xEstadoGeneraIAC = null;
	        
	        int xEstadoGeneraIAC_SI = 1;
	        
	        
	       
		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 
			    params.put("p_idPeriodo", idPeriodoInt);
			    params.put("p_nombreLocal", L.getNombreLocal());
			    params.put("p_razonSocial", L.getRazonSocial());
			    params.put("p_nit", L.getNit());
			    params.put("p_titulo", xTituloReporte);
			    params.put("p_direccion", L.getDireccion());
			    params.put("p_idLocal", idLocal);
			    params.put("p_indicadorINI", IndicadorINICIAL);
			    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
			    params.put("p_indicadorFIN", IndicadorFINNAL);    
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
			    params.put("p_representanteLegal", L.getRepresentanteLegal());
			    
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
	           // String xBarraName = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodoInt + xCharSeparator ;
	        	String xBarraName = xPathFileGralDB + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            params.put("p_barraName", xBarraName);
	        }
	        
	        System.out.println("idLocal es : " + idLocal);
		   
		    
		    List<TblDctosOrdenesDTO> lista = null;
		    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblDctosOrdenesService.listaUnClienteProducto(idLocal, listaIdClientes, idPeriodoDouble);
            
            int totalLista= lista.size();
            int totalListaSus= listaIdClientes.size();
		    	
	            System.out.println("lista " + lista);
	            System.out.println("totalLista " + totalLista);
	            System.out.println("totalListaSus " + totalListaSus);
		    
	    
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

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/BuscarSuscriptorReporte")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarSuscriptorReporte(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
        
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarSuscriptor");

	        // Obtenemos los datos del JSON recibido
	        String palabraClave = (String) requestBody.get("palabraClave");
	        System.out.println("palabraClave desde /BuscarSuscriptor " + palabraClave);

//	        List<TercerosDTO> ListaBusqueda = tblTercerosService.BuscarTercerosSuscriptor(usuario.getIdLocal(), palabraClave);
//	        System.out.println("La ListaBusqueda generada es:  " + ListaBusqueda );
//	        
	        
           List<TercerosDTO> ListaBusqueda = null;
	        
	        try {
	            // Intentamos convertir palabraClave a un número
	            Integer idCliente = Integer.parseInt(palabraClave);
	            
	            System.out.println("La palabra clave es un numero");
	            // Si no se lanza NumberFormatException, entonces palabraClave es un número
	            ListaBusqueda = tblTercerosService.BuscarTercerosSuscriptorNUID(usuario.getIdLocal(), palabraClave);
	        } catch (NumberFormatException e) {
	        	
	        	System.out.println("La palabra clave NO es un numero");
	            // Si se lanza NumberFormatException, entonces palabraClave es una palabra normal
	            ListaBusqueda = tblTercerosService.BuscarTercerosSuscriptor(usuario.getIdLocal(), palabraClave);
	        }
	        
	        
	        
		    for(TercerosDTO busqueda : ListaBusqueda) {
		    	
		    	System.out.println("busqueda " + busqueda.getIdTercero());
		    	System.out.println("busqueda nombre  " + busqueda.getNombreTercero());
		    	System.out.println("busqueda " + busqueda.getDireccionTercero());
		    	System.out.println("busqueda " + busqueda.getNombreCausa());
		    	
		    }

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("ListaBusqueda", ListaBusqueda);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/BuscarSuscriptorDCTOReporte")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarSuscriptorDCTOReporte(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Map<String, Object> response = new HashMap<>();
	    System.out.println("SI ENTRÓ A  /BuscarSuscriptor");

	        // Obtenemos los datos del JSON recibido
	        String palabraClave = (String) requestBody.get("palabraClave");
	        Integer idDcto = Integer.parseInt(palabraClave);
	        System.out.println("palabraClave desde /BuscarSuscriptor " + palabraClave);
	        
	        //Obtenemos el idCliente del idDcto
	        String idCliente = TblDctosService.ObtenerIdCliente(usuario.getIdLocal(), idDcto);
	        
	        if(idCliente == null) {
	        	System.out.println("idCliente es NULL" );
	        	return ResponseEntity.ok(response);
	        }

	        List<TercerosDTO> ListaBusqueda = tblTercerosService.BuscarTercerosSuscriptorNUID(usuario.getIdLocal(), idCliente);
	        System.out.println("La ListaBusqueda generada es:  " + ListaBusqueda );
	        
	        
	        
		    for(TercerosDTO busqueda : ListaBusqueda) {
		    	
		    	System.out.println("busqueda " + busqueda.getIdTercero());
		    	System.out.println("busqueda nombre  " + busqueda.getNombreTercero());
		    	System.out.println("busqueda " + busqueda.getDireccionTercero());
		    	System.out.println("busqueda " + busqueda.getNombreCausa());
		    	
		    }

		    
		    
		    response.put("message", "LOGGGGGGGGG");
		    response.put("ListaBusqueda", ListaBusqueda);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/DescargarReporteFacturaProductoIDCLIENTE")
	@ResponseBody
	public ResponseEntity<?>  DescargarReporteFacturaProductoIDCLIENTE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    // Crea la lista de strings
	    List<String> listaIdClientes = new ArrayList<>();
	    
	    System.out.println("SI ENTRÓ A  DescargarReporteEstadoProductoNUID");

	        // Obtenemos los datos del JSON recibido
	    	String idCliente = (String) requestBody.get("idTercero");
	    	// Agrega el idCliente a la lista
	    	listaIdClientes.add(idCliente);
	    
	        String idPeriodo = (String) requestBody.get("PeriodoCobro");
	        Double idPeriodoDouble = Double.parseDouble(idPeriodo);
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);

	        String formato = "PDF";
	        

	        System.out.println("idCliente es : " + idCliente);
	        Integer idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 1140;
		    Integer xIdTipoOrden = 7;
		    
		    int xEstadoGeneraIAC_SI = 1;
		    Integer xEstadoGeneraIAC = null;
		    
		    int xEstadoSTR_SI = 1;
		    Integer xEstadoSTR = null;
		    
		    List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
		    
		    for(TblLocales L : Local) {
		    	xEstadoGeneraIAC = L.getEstadoGeneraIAC();
		    	xEstadoSTR = L.getEstadoSTR();
		    }
		    
		    int xEtapaSTR_SI = 40;
		    //Integer xEtapaSTR = 0;
		    
		    Integer xEtapaSTR = TblDctosService.ObtenerEtapaSTR(idLocal, idPeriodoInt, idCliente);
		    
		
			String xCharSeparator = File.separator;
			
			Map<String, Object> response = new HashMap<>();
			
			if(xEstadoGeneraIAC_SI == xEstadoGeneraIAC) {
			
		//xEstadoGeneraIAC	
		 // Genera imagen IAC CODE128 ( posterior a facturado )
			if ( (xEstadoSTR_SI == xEstadoSTR) && (xEtapaSTR_SI == xEtapaSTR) || (xEstadoSTR_SI != xEstadoSTR) )
				{
					
				// TODO code application logic here
				//String xCharSeparator = File.separator;
				String xRuta = "";

				// Linux 
				if (xCharSeparator.compareTo("/") == 0) {

					// Linux               
					xRuta = "" + xCharSeparator + "home" + xCharSeparator + "sw" + xCharSeparator + "jar" + xCharSeparator + "CodigoGS1" + xCharSeparator + "dist" + xCharSeparator + "CodigoGS1.jar ";

				} else {

					// Windows          
					xRuta = "C:" + xCharSeparator + "proyectoWeb" + xCharSeparator + "CodigoGS1" + xCharSeparator + "dist" + xCharSeparator + "CodigoGS1.jar ";

				}

				//
				final int xIdLocalUsuarioFinal = idLocal;                    
				final int xIdPeriodoFinal = idPeriodoInt;                        
				final String xRutaDisco = xRuta;
				final String xIdClienteFinal = idCliente; //  0 ( son todos)                 

				//                       //
				Thread t = new Thread(new Runnable() {

					@Override
					@SuppressWarnings("empty-statement")
					public void run() {
						try {

							//
							Runtime rt = Runtime.getRuntime();

							Process proc = rt.exec("java -jar " + xRutaDisco
									+ xIdLocalUsuarioFinal + " "
									+ xIdPeriodoFinal + " " 
									+ xIdClienteFinal);

							//
							BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

							BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

							// read the output from the command
							String s = null;
							while ((s = stdInput.readLine()) != null) {
								System.out.println(s);
							}

							// read any errors from the attempted command
							while ((s = stdError.readLine()) != null) {
								System.out.println(s);
							}
							proc.waitFor();
							System.out.println("success");
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
				t.start();  
				
			
			    // Esperar a que un HILO (Thread) termine	
				try {
				    t.join(); 
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
				

				}else{
					
					
					response.put("mensaje", "La factura aún no ha sido exportada. Intenta más tarde.");

				    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
				}
				
			}
		    
		    
		    //Obtenemos el FileName del reporte y el titulo 
		    List<TblLocalesReporte> reporte = tblLocalesReporteService.listaUnFCH(idLocal, xIdReporte);
		    
		    String xFileNameReporte = "";
		    String xTituloReporte = "";
		    
		    
		    
		    for(TblLocalesReporte R : reporte) {
		    	
		    	xFileNameReporte = R.getFileName();
		    	xTituloReporte = R.getReporteNombre();
		    }
		    
		    //--
			
		    
			//
			//Obtenemos la información del local que usaremos para los PARAMS del encabezado
		   // List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
			
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
		   
		   
		  // String xCharSeparator = File.separator;
	      //  String xPathFileGral = ""; 
		   	String xPathFileGralDB = ""; 
	        String StringPathLinux = "/home/sw"; 
	        String StringPathWindows = "c:"; 
	        

	       // Integer xEstadoGeneraIAC = null;
	        
	        xEstadoGeneraIAC_SI = 1;
	        
	        
	      
	        
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
