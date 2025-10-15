package com.marketing.Controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Model.dbaquamovil.TblTipoOrdenSubcuenta;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Projection.TblCiudadesDTO;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosDTO3;
import com.marketing.Projection.TblDctosDTO4;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
import com.marketing.Projection.TblPlusDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblPlusInventarioRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblCategoriasService;
import com.marketing.Service.dbaquamovil.TblCiudadesService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblPlusInventarioService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.Service.dbaquamovil.TblTipoOrdenSubcuentaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaCredito;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaNE;
import com.marketing.Utilidades.ProcesoGuardaPluInventario;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.ProcesoGuardaPorcentaje;
import com.marketing.Utilidades.ProcesoIngresoComprobante;
import com.marketing.Utilidades.UtilidadesIP;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;




@Controller
public class VentaInventarioController {
	
	@Autowired
	 TblCategoriasService tblCategoriasService;
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblDctosService tblDctosService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	TblPagosService tblPagosService;
	
	@Autowired
	TblMedidoresMacroService tblMedidoresMacroService;
	
	@Autowired
	TblTipoCausaNotaService tblTipoCausaNotaService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblPlusService tblPlusService;
	
	@Autowired
	TblCiudadesService  tblCiudadesService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	ProcesoCreaLecturaMovil procesoCreaLecturaMovil;
	
	@Autowired
	ProcesoGuardaLecturaMovil procesoGuardaLecturaMovil;
	
	@Autowired
	ProcesoGuardaPluOrden procesoGuardaPluOrden;
	
	@Autowired
	TblTipoOrdenSubcuentaService tblTipoOrdenSubcuentaService;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	ProcesoGuardaPorcentaje procesoGuardaPorcentaje;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@Autowired
	ProcesoIngresoComprobante procesoIngresoComprobante;
	
	
	@Autowired
	ProcesoGuardaPluInventario procesoGuardaPluInventario;
	
	@Autowired
	TblPlusInventarioService tblPlusInventarioService;
	
	@Autowired
	TblPlusInventarioRepo tblPlusInventarioRepo;
	

	
	
	
	
	@GetMapping("/VentaInventario")
	public String ventaInventario(HttpServletRequest request,Model model) {
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase); 
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
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
               
			           
			     // ---------------------------------------------------------------- VALIDACION SUSCIPTOR SELECCIONADO --------------------------------------------------------
						
						int xIdTipoTerceroCliente = 1;
				        int xIdTipoOrden = 7;
				        int xIdTipoOrdenProceso = xIdTipoOrden + 50 ;

				        //
				        int estadoActivo = 9;
						
						 // Obtener la fecha actual
				        LocalDate fechaActual = LocalDate.now();

				        // Formatear la fecha como un String
				        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				        String strFechaVisita = fechaActual.format(formatter);
				        
				        System.out.println("strFechaVisita  es" + strFechaVisita);
				        

						
						String idCliente = tblAgendaLogVisitasService.seleccionaVisitaEstadoFecha(estadoActivo, strFechaVisita, IdUsuario);
						System.out.println("idCliente desde /Factura " + idCliente);
						
						if(idCliente == null) {
							
							
							session.removeAttribute("pantalla"); //Se remueve de la session el valor de pantalla					
							String pantalla = "VentaInventario";
							
							session.setAttribute("pantalla", pantalla); //Se le asigna a la session el valor de pantalla 
							
							return "Cliente/Selecciona";
							
						}else {
							
							System.out.println("idCliente en el if es" + idCliente);
							
							List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), idCliente, xIdTipoTerceroCliente);
							
							for(TblTerceros L : listaTercero) {
								
								model.addAttribute("xEstado", L.getEstado());
								model.addAttribute("xNuid", L.getIdCliente());
								model.addAttribute("xNombreTercero", L.getNombreTercero());
								model.addAttribute("xRuta", L.getIdRuta());
								
							}
							
						}
		


				        model.addAttribute("xFechaActual", strFechaVisita);
		
		return "Inventario/DetalleInventarioVenta";
	}
	
	
	
	
	
	
	
	@PostMapping("/ObtenerCuentas")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ObtenerCuentas(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,  Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		Integer idLocal = usuario.getIdLocal();
		
		Map<String, Object> response = new HashMap<>();
		
		List<TblPlusDTO> listaPlus = tblPlusService.ObtenerPlusInventario(idLocal);
	
      	System.out.println("listaPlus  es  " + listaPlus);
      	

      	response.put("xlistaCuentas", listaPlus);     	
		
      	return ResponseEntity.ok(response);
		
	}
	
	
	
	
	
	
	@PostMapping("/FinalizarVenta-Post")
	@ResponseBody
	public ResponseEntity<Resource>  FinalizarVenta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
        
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idLocal = usuario.getIdLocal();


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
	    

	    System.out.println("xIdPluArray es: " + xIdPluArray);
	    System.out.println("xNombrePluArray es: " + xNombrePluArray);
	    System.out.println("xVrUnitarioArray es: " + xVrUnitarioArray);
	    System.out.println("xIvaArray es: " + xIvaArray);
	    System.out.println("xCantidadArray es: " + xCantidadArray);
	    System.out.println("xSubtotalArray es: " + xSubtotalArray);
	    System.out.println("xExistenciaArray es: " + xExistenciaArray);
	    System.out.println("totalVenta es: " + totalVenta);
	    
	    
	    Integer xIdTipoOrdenFactura = 9;
	    Integer xIndicador = 1;
	    
	    
	    int xIdDctoMax = tblDctosService.maximoDctoLocalIndicador(idLocal, xIdTipoOrdenFactura, xIndicador) + 1;
	    
	    
	    //
        int estadoActivo = 9;
		
		 // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
	    
	    List<TblAgendaLogVisitas> visita = tblAgendaLogVisitasService.seleccionaVisitaEstadoxFecha(estadoActivo, strFechaVisita, IdUsuario, usuario.getIdLocal());
		
		
		int idLog = 0;
		int xIdUsuario = 0;
		String xIdTercero = "";

		
		for(TblAgendaLogVisitas V : visita) {
			
			idLog = V.getIDLOG();
			xIdUsuario = V.getIDUSUARIO();
			xIdTercero = V.getIdCliente();
			
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
		            xIdTipoOrdenFactura,
		            xIdUsuario,
		            usuario.getIdLocal(),
		            xIdTercero,
		            xCeroStr,
		            xCeroStr,
		            xCeroInt,
		            xCeroInt,
		            valorVentaTotal
		    );

		    System.out.println("idOrden es -> " + idOrden);
		    
		    
		    Integer idPluInt = Integer.parseInt(xIdPluStr);
		    
		    Double existenciaPlu = tblPlusInventarioService.ObtenerExistenciaPlu(usuario.getIdLocal(), idPluInt);
		    System.out.println("existenciaPlu es " + existenciaPlu);
		    
		    Double newExistencia = existenciaPlu - xCantidad;
		    
		    //Actualiza inventario 
		    tblPlusInventarioRepo.actualizaExistenciaPlu(newExistencia, usuario.getIdLocal(), idPluInt);
		    
		    
		}
		
		

	    
	    
	    
	   // -------------------------------------------------------------------------------------- Reporte    -----------------------------------------------------
	    

	        
		int xIdReporte = 4300;
		
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
	    
	    List<TercerosDTO2> tercero = tblTercerosService.listaUnTerceroOrden(idLocal, xIdTipoOrdenFactura, idOrden);
	    
	    
	    
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
	    
	    
	    List<TblDctosOrdenesDetalleDTO2> lista = null;
	    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblDctosOrdenesDetalleService.detalleInventarioVenta(idLocal, xIdTipoOrdenFactura, idOrden);
	    	
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
	
	
	


}
