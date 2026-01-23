package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;


import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Projection.TblTercerosRutaDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusInventarioService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoOrdenService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoGuardaPluInventario;
import com.marketing.enums.TipoReporteEnum;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblPlusInventarioRepo;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class OrdenDeTrabajoController {
	
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblDctosService TblDctosService;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblDctosService tblDctosService;
	
	@Autowired
	TblPlusInventarioRepo tblPlusInventarioRepo;
	
	@Autowired
	TblTipoOrdenService tblTipoOrdenService;
	
	@Autowired
	ProcesoGuardaPluInventario procesoGuardaPluInventario;
	
	@Autowired
	TblPlusInventarioService tblPlusInventarioService;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@GetMapping("/OrdenDeTrabajo")
	public String OrdenDeTrabajo (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				Integer IdUsuario = usuario.getIdUsuario();
				
				
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
				
				
				List <CtrlusuariosDTO> operarios = ctrlusuariosService.obtenerOperarios(usuario.getIdLocal());
				model.addAttribute("operarios", operarios);
				
				
				List<TblTercerosRuta> Rutas = tblTercerosRutaService.ListaRutas(idLocal);
				model.addAttribute("xRutas", Rutas);
				

				// Ingresar LOG
				
				String idCliente = tblLocalesService.ObtenerNitNE(idLocal);
				
				// Obtenemos el IDLOG Máximo y le sumamos uno
		        Integer maximoIDLOGSum1 = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
		        System.out.println("maximoIDLOG en /GuardarLog: " + maximoIDLOGSum1);

		        
		        // Actualizamos los ESTADO Que sean = 9 a 1
		        tblAgendaLogVisitasRepo.actualizarEstadoA1(usuario.getIdLocal(), IdUsuario);

		        // Ingresamos el nuevo Log con ESTADO = 9
		        tblAgendaLogVisitasService.ingresarLog(usuario.getIdLocal(), maximoIDLOGSum1, idCliente, IdUsuario);
	
		        
		        
		
		
		return "/OrdenDeTrabajo";
	}
	
	
	@PostMapping("/FinalizarOrdenDeTrabajo-Post")
	@ResponseBody
	public ResponseEntity<Resource>  FinalizarOrdenDeTrabajo(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
        
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idLocal = usuario.getIdLocal();
	    
	   // Obtenemos el periodo activo
	 	List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
	 			
	 	Integer idPeriodo = 0;		
	 	for(TblDctosPeriodo P : PeriodoActivo) {
	 				
	 	   idPeriodo = P.getIdPeriodo();
	 			
	 	} 


	    System.out.println("SI ENTRÓ A  /FinalizarVenta");


	    @SuppressWarnings("unchecked")
	    List<Object> xIdPluList = (List<Object>) requestBody.get("xIdPluArr");
	    @SuppressWarnings("unchecked")
	    List<Object> xNombrePluList = (List<Object>) requestBody.get("xNombrePluArr");
	    @SuppressWarnings("unchecked")
	    List<Object> xVrUnitarioList = (List<Object>) requestBody.get("xVrUnitarioArr");
	    @SuppressWarnings("unchecked")
	    List<Object> xIvaList = (List<Object>) requestBody.get("xIvaArr");
	    @SuppressWarnings("unchecked")
	    List<Object> xCantidadList = (List<Object>) requestBody.get("xCantidadArr");
	    @SuppressWarnings("unchecked")
	    List<Object> xSubtotalList = (List<Object>) requestBody.get("xSubtotalArr");
	    @SuppressWarnings("unchecked")
	    List<Object> xExistenciaList = (List<Object>) requestBody.get("xExistenciaArr");

	    // Convertir todos a String de forma segura
	    String[] xIdPluArray = xIdPluList.stream().map(String::valueOf).toArray(String[]::new);
	    String[] xNombrePluArray = xNombrePluList.stream().map(String::valueOf).toArray(String[]::new);
	    String[] xVrUnitarioArray = xVrUnitarioList.stream().map(String::valueOf).toArray(String[]::new);
	    String[] xIvaArray = xIvaList.stream().map(String::valueOf).toArray(String[]::new);
	    String[] xCantidadArray = xCantidadList.stream().map(String::valueOf).toArray(String[]::new);
	    String[] xSubtotalArray = xSubtotalList.stream().map(String::valueOf).toArray(String[]::new);
	    String[] xExistenciaArray = xExistenciaList.stream().map(String::valueOf).toArray(String[]::new);
	    
	    
	   // Manejo del totalVenta (puede llegar como Double, Integer o String)
	    Object totalVentaObj = requestBody.get("totalVenta");
	    Double totalVenta = totalVentaObj != null ? Double.valueOf(totalVentaObj.toString()) : 0.0;
	    
	    String operario = (String) requestBody.get("operario");
	    Integer operarioInt = Integer.parseInt(operario);
	    
	    String idCliente = (String) requestBody.get("idCliente");
	    String Observacion = (String) requestBody.get("Observacion");
	    
	    Integer idTipoOrden = 30;
	    
	    

	    System.out.println("xIdPluArray es: " + xIdPluArray);
	    System.out.println("xNombrePluArray es: " + xNombrePluArray);
	    System.out.println("xVrUnitarioArray es: " + xVrUnitarioArray);
	    System.out.println("xIvaArray es: " + xIvaArray);
	    System.out.println("xCantidadArray es: " + xCantidadArray);
	    System.out.println("xSubtotalArray es: " + xSubtotalArray);
	    System.out.println("xExistenciaArray es: " + xExistenciaArray);
	    System.out.println("totalVenta es: " + totalVenta);
	    System.out.println("operario es: " + operario);
	    System.out.println("idCliente es: " + idCliente);
	    System.out.println("Observacion es: " + Observacion);
	    



	    //
        int estadoActivo = 9;
		
		 // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
	    
	    List<TblAgendaLogVisitas> visita = tblAgendaLogVisitasService.seleccionaVisitaEstadoxFecha(estadoActivo, strFechaVisita, IdUsuario, usuario.getIdLocal());
		
		
		int idLog = 0;
		int xIdUsuario = operarioInt;
		String xIdTercero = idCliente;
		
		//Obtenemos el signo del idTipoOrden
		Integer signoTipoOrden = tblTipoOrdenService.ObtnerSignoTipoOrden(idTipoOrden);
		
		

		
		for(TblAgendaLogVisitas V : visita) {			
			idLog = V.getIDLOG();
			
		}
		
	     // Calcula el valor total de la venta			
		double valorVentaTotal = 0.0;
		for (int i = 0; i < xSubtotalArray.length; i++) {
		    double xSubtotal = Double.parseDouble(xSubtotalArray[i]);
		    valorVentaTotal += xSubtotal;
		}

		
		int idOrden = 0;
	    
	    // INGRESA LOS PLUS EN DETALLE, ORDENESDETALLE Y DCTOS
		for (int indice = 0; indice < xIdPluArray.length; indice++) {

		    // Obtener valores de cada array
		    String xIdPluStr = xIdPluArray[indice];
		    String xCantidadStr = xCantidadArray[indice];
		    String xSubtotalStr = xSubtotalArray[indice];

		    // Convertir a tipos numéricos
		    double xCantidad = Double.parseDouble(xCantidadStr);
		    double xSubtotal = Double.parseDouble(xSubtotalStr);
		    
		    
		    double xVrVentaUnitario = (xCantidad != 0) ? (xSubtotal / xCantidad) : 0.0;

		    // Variables auxiliares
		    double xCeroDouble = 0.0;
		    String xCeroStr = "0.0";
		    int xCeroInt = 0;

		    int xItem = indice + 1; // normalmente los items arrancan en 1, no en 0

		    System.out.println("Procesando item #" + xItem);
		    System.out.println(" - xIdPlu: " + xIdPluStr);
		    System.out.println(" - xCantidad: " + xCantidad);
		    System.out.println(" - xSubtotal: " + xVrVentaUnitario);
		    

		    // Llamada a tu método de guardado
		     idOrden = procesoGuardaPluInventario.guarda(
		            idLog,
		            xIdPluStr,
		            xCantidad,
		            xVrVentaUnitario,
		            xItem,
		            idTipoOrden,
		            xIdUsuario,
		            usuario.getIdLocal(),
		            xIdTercero,
		            Observacion,
		            xCeroStr,
		            xCeroInt,
		            idPeriodo,
		            valorVentaTotal
		    );

		    System.out.println("idOrden es -> " + idOrden);
		    
		    
		    Integer idPluInt = Integer.parseInt(xIdPluStr);
		    
		    Double existenciaPlu = tblPlusInventarioService.ObtenerExistenciaPlu(usuario.getIdLocal(), idPluInt);
		    System.out.println("existenciaPlu es " + existenciaPlu);
		    
		    Double newExistencia;
		    
		    newExistencia = existenciaPlu - xCantidad;

		    
		    //Actualiza inventario 
		    tblPlusInventarioRepo.actualizaExistenciaPlu(newExistencia, usuario.getIdLocal(), idPluInt);
		    
		    
		}
		
		

	    
	    
	    
	   // -------------------------------------------------------------------------------------- Reporte    -----------------------------------------------------
	    

	        
		int xIdReporte = 4340;
		
		String formato = "PDF";
	    
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
	   
	   String xCharSeparator = File.separator;
	   
	    for(TblLocales L : Local) {
	    	
		    // Parametros del encabezado 

		    params.put("p_nombreLocal", L.getNombreLocal());
		    params.put("p_nit", L.getNit());
		    params.put("p_titulo", xTituloReporte);
		    params.put("p_idLocal", idLocal);
		    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
		    params.put("p_email", L.getEmail());
		    params.put("p_direccion", L.getDireccion().trim() + " " + L.getCiudad().trim());
		    
		    String xPathImagen = L.getPathImagen();
		    String xLogoName = xPathImagen + idLocal.toString() + ".jpg";
		    params.put("p_logo", xLogoName);
	    	
	    }
	    
	  
	    
	    // Tercero
	    int idTipoTercero = 1;
	    
	    List<TercerosDTO2> tercero = tblTercerosService.listaUnTerceroOrden(idLocal, idTipoOrden, idOrden);
	    
	    
	    
	    for(TercerosDTO2 T : tercero) {
	    	
	    	params.put("p_idTercero", T.getIdCliente().trim() + " " + T.getNombreTercero().trim());
	    	params.put("p_idCliente", T.getIdCliente());
	    	params.put("p_nombreTercero", T.getNombreTercero());
		    params.put("p_direccionTercero", T.getDireccionTercero());
		    params.put("p_formaPago", "0");
		    params.put("p_telefonoFijo", "TEL: " + T.getTelefonoFijo());
		    System.out.println("TELEFONO FIJO ES: " + T.getTelefonoFijo());
		    params.put("p_ciudadTercero", T.getCiudad());
	    	
	    }
	    
	    params.put("p_fechaOrden", "FECHA "   + strFechaVisita);	    
	    
	    Integer idDcto = tblDctosService.ObtenerIdDcto(idLocal, idOrden, xIdTercero);
	    params.put("p_iDcto", idDcto);
	    
	    String nombreTipoOrden = tblTipoOrdenService.ObtnerNombreTipoOrden(idTipoOrden);
	    params.put("p_nombreTipoOrden", nombreTipoOrden);
	    
	    
	    List<TblDctosOrdenesDetalleDTO2> lista = null;
	    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblDctosOrdenesDetalleService.detalleInventarioOrdenDeTrabajo(idLocal, idTipoOrden, idOrden);
	    	
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
        

        	 
        	 int xEstadoTerminado = 1; 
             int estadoVisita = 9;
		    
        	 tblAgendaLogVisitasRepo.actualizaVisita(xEstadoTerminado, strFechaVisita, IdUsuario, estadoVisita);

	    
	    
     // Configura la respuesta HTTP
	    return ResponseEntity.ok()
	            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
	            .contentLength(dto.getLength())
	            .contentType(mediaType)
	            .body(streamResource);
	   
	    
	}
	
	
	
	
	@PostMapping("/DescargarReporteOrdenDeTrabajo")
	public ResponseEntity<Resource> DescargarReporteOrdenDeTrabajo(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		Integer idLocal = usuario.getIdLocal();

		
		 // Obtenemos los datos del JSON recibido
        String idPeriodo = (String) requestBody.get("idPeriodo");
        System.out.println("idPeriodo en DescargarReporteCortes es  : " + idPeriodo);
        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
        
        
        String idUsuario = (String) requestBody.get("operario");  
        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        String Observacion = (String) requestBody.get("Observacion");
        
        String idCliente = (String) requestBody.get("idCliente");
        
        String emailTercero = "";
    	String telefonoCelularTercero = "";
    	String nombreTercero = "";
    	Integer idRutaTercero = 0;
        
        //Validamos si idCliente no es null
        if(idCliente != null) {
        	
        	int idTipoTercero = 1;        	
        	List<TblTerceros> infoTercero = tblTercerosService.ObtenerInformacionTercero(idLocal, idCliente, idTipoTercero);
        	      	
        	for(TblTerceros tercero : infoTercero) {        		
        		emailTercero = tercero.getEmail();
        		telefonoCelularTercero = tercero.getTelefonoCelular();
        		idRutaTercero = tercero.getIdRuta();  
        		nombreTercero = tercero.getNombreTercero();
        	}
        	
        }else {
        	
        	idCliente = "";
        }
       
        
        //Obtener nombre ruta
        String nombreRuta = "";
        
        if(idRutaTercero != 0) {
        	
        	List<TblTercerosRutaDTO> infoRuta = tblTercerosRutaService.ObtenerRuta(idLocal, idRutaTercero);
        	
        	for(TblTercerosRutaDTO ruta : infoRuta) {        		
        		nombreRuta = ruta.getNombreRuta();
        	}
        }
        
        String idRutaStr = idRutaTercero.toString();
        String rutaCompleta = idRutaStr + nombreRuta;
        
      
        
        
        
        String formato = "PDF";
        System.out.println("xIdPeriodo : " + idPeriodo);
		
		
		int idTipoOrden = 30;
		int indicador = 1;
		
		//Obtenemos el maximo idDcto		
		Integer idDctoMax = TblDctosService.maximoDctoLocalIndicador(idLocal, idTipoOrden, indicador) + 1;
		
		//Obtenemos el maximo idOrden
		Integer idOrdenMax = tblDctosOrdenesRepo.maximaIdOrdenIdLocal(idLocal);
		
		
		 // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
        System.out.println("strFechaVisita  es" + strFechaVisita);
        
        //Obtener nombre operario
        String nombreOperario = ctrlusuariosService.obtenerNombreUsuario(idLocal, idUsuarioInt);
		
//		(int idLocal, int IdTipoOrden, int IdOrden, int IdDcto, int Indicador, String FechaDctoSqlServer, Double VrBaseSinRedondeo, int VrPago, int Estado, Double VrIva, 
//				 int IdTipoNegocio, int VrRteFuente, Double VrDescuento, int VrRteIva, int VrRteIca, String NombreTercero, int IdUsuario, String IdCliente, int DiasPlazo, int PorcentajeDscto, 
//				 int IdCausa, String IdDctoNitCC, String FechaDctoNitCCSqlServer, int VrPagarDctoNitCC, int VrDsctoFcro, Double VrCostoMV, int IdLocalCruce, int IdTipoOrdenCruce, int IdDctoCruce, 
//				 int IdPeriodo, int IdVendedor, Double VrImpoconsumo, Double VrCostoIND, int IdOrdenCruce, int EtapaSTR, int EnvioFE)
		
		//Ingresamos el nuevo idDcto soporte
		tblDctosRepo.ingresaDcto(idLocal, idTipoOrden, idOrdenMax, idDctoMax, indicador, strFechaVisita, null, 0, indicador, null, 
				0, 0, null, 0, 0, nombreTercero, idUsuarioInt, idCliente, 0, 0, 
				0, "", strFechaVisita, 0, 0, null,idLocal, idTipoOrden, idDctoMax, 
				idPeriodoInt, 0, null, null, 0, 0, 0);
		
		
							
		
	    int xIdReporte = 4200;
	    
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
	    params.put("p_nombreOperario", nombreOperario);
	    params.put("p_emailTercero", emailTercero);
	    params.put("p_telefonoCelularTercero", telefonoCelularTercero);
	    params.put("p_rutaTercero", rutaCompleta);

	   Integer IdTipoOrdenINI = 9;
	   Integer IdTipoOrdenFIN = 29;
	   Integer IndicadorINICIAL = 1;
	   Integer IndicadorFINNAL = 2;
	   
	   String xPathReport = "";
	   String xPathImagen = "";
	   
	   Integer xIdTipoOrden = 9;
	   
	   
	   
	   
	   String xCharSeparator = File.separator;
	    for(TblLocales L : Local) {
	    	
		    // Parametros del encabezado 
		    params.put("p_idPeriodo", idPeriodoInt);
		    params.put("p_nombreLocal", L.getNombreLocal());
		    params.put("p_nit", L.getNit());
		    params.put("p_titulo", xTituloReporte);
		    params.put("p_direccion", L.getDireccion());
		    params.put("p_telefono", L.getTelefono());
		    params.put("p_idLocal", idLocal);
		    params.put("p_indicadorINI", IndicadorINICIAL);
		    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
		    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
		    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
		  
		    
		    xPathImagen = L.getPathImagen();
		    String xLogoName = xPathImagen + idLocal.toString() + ".jpg";
		    params.put("p_logo", xLogoName);
		    params.put("p_observacion", Observacion);
		    params.put("p_cuentaBanco", L.getCuentaBanco());
		    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
		    
	    	
	    }
	    
	    
	    List<TblDctosDTO> lista = null;
	    			

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = TblDctosService.listaOrdenDeTrabajo(idLocal, idTipoOrden, idDctoMax);

    
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
	
	
	
	
	
	@PostMapping("/ObtenerTerceros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ObtenerTerceros(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,  Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		Integer idLocal = usuario.getIdLocal();
		System.out.println("idLocal en ObtenerTerceros es  " + idLocal);
		
		Map<String, Object> response = new HashMap<>();
		

		List<TercerosDTO> listaTerceros = tblTercerosService.ListaTercerosSuscriptor(idLocal);
      	System.out.println("listaTerceros  es  " + listaTerceros);
      	
      	for(TercerosDTO tercero : listaTerceros) {
      		
      		System.out.println("nombre tercero es   " + tercero.getNombreTercero());
      		System.out.println("CCNIT tercero es   " + tercero.getCC_Nit());
      	}
      	

      	response.put("xListaTerceros", listaTerceros);     	
		
      	return ResponseEntity.ok(response);
		
	}
	
	
	
	
	@PostMapping("/DescargarReporteDetalleOrdenDeTrabajo")
	public ResponseEntity<Resource> DescargarReporteDetalleVentas(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
		
		
		 	Class tipoObjeto = this.getClass();					
	        String nombreClase = tipoObjeto.getName();		
	        System.out.println("CONTROLLER " + nombreClase); 
	   
	           	// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				
				// Obtenemos los datos del JSON recibido
		        String idPeriodo = (String) requestBody.get("idPeriodo");
		        System.out.println("idPeriodo en DescargarReporteCortes es  : " + idPeriodo);
		        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
		        		        		        
		        String formato = (String) requestBody.get("formato");
		        				
				System.out.println("PeriodoCobro : " + idPeriodo);

				
				int idLocal = usuario.getIdLocal();
				int idTipoOrden = 30;
				
			    int xIdReporte = 4220;
			    
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
			   
			   String xCharSeparator = File.separator;
			    for(TblLocales L : Local) {
			    	
				    // Parametros del encabezado 
				    params.put("p_idPeriodo", idPeriodoInt);
				    params.put("p_nombreLocal", L.getNombreLocal());
				    params.put("p_nit", L.getNit());
				    params.put("p_titulo", xTituloReporte);
				    params.put("p_direccion", L.getDireccion());
				    params.put("p_idLocal", idLocal);
				    params.put("p_indicadorINI", IndicadorINICIAL);
				    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
				    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
				    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
				    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
			    	
			    }
			    
			    
			    List<TblDctosDTO> lista = null;
			    		        	
		        	lista = TblDctosService.listaReporteOrdenDeTrabajo(idLocal, idTipoOrden, idPeriodoInt);
	
			    
		        	System.out.println("formato es : " + formato);
		        	System.out.println("xFileNameReporte es : " + xFileNameReporte);
		        	System.out.println("xPathReport es : " + xPathReport);
		    
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
				    
				    System.out.println("dto.getFileName() es : " + dto.getFileName());
				    
				     // Configura la respuesta HTTP
				    return ResponseEntity.ok()
				            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
				            .contentLength(dto.getLength())
				            .contentType(mediaType)
				            .body(streamResource);
		}
	
	
	

}
