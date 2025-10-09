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
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.Service.dbaquamovil.TblTipoOrdenSubcuentaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaNE;
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
	TblDctosService TblDctosService;
	
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
	
	
	
	
	@GetMapping("/VentaInventario")
	public String ventaInventario(HttpServletRequest request,Model model) {
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
               
		
			           // Obtener la fecha actual
				        LocalDate fechaActual = LocalDate.now();

				        // Formatear la fecha como un String
				        DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				        String FechActual = fechaActual.format(formatterAct);

				        model.addAttribute("xFechaActual", FechActual);
		
		return "Inventario/VentaInventario";
	}
	
	
	
	
	@GetMapping("/TraerClienteInventarioVenta")
	public String traerClienteInventarioVenta(HttpServletRequest request,Model model) {
		
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
               
		
			           // Obtener la fecha actual
				        LocalDate fechaActual = LocalDate.now();

				        // Formatear la fecha como un String
				        DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				        String FechActual = fechaActual.format(formatterAct);

				        model.addAttribute("xFechaActual", FechActual);
		
		return "Inventario/TraerClienteInventarioVenta";
	}
	
	
	@PostMapping("/DetalleInventarioVenta-Post")
	public ModelAndView DetalleInventarioVenta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase); 
        
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /DetalleInventarioVenta");

	    // Obtenemos los datos del JSON recibido
	    String idTercero = (String) requestBody.get("idTercero");
	    //Integer idTercero = Integer.parseInt(idTerceroString);


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/DetalleInventarioVenta?idTercero=" + idTercero);
	    return modelAndView;
	}
	
	
	@GetMapping("/DetalleInventarioVenta")
	public String DetalleInventarioVenta(@RequestParam(name = "idTercero", required = false) String idTercero, HttpServletRequest request, Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase); 
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /DetalleInventarioVenta con idTercero: " + idTercero);
		
		
		 // ----------------------------------------------------------- VALIDA INACTIVIDAD ------------------------------------------------------------
	    HttpSession session = request.getSession();
	    //Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
	    
	    @SuppressWarnings("unchecked")
		List<TblAgendaLogVisitas> UsuarioLogueado = (List<TblAgendaLogVisitas>) session.getAttribute("UsuarioLogueado");
	    
	    Integer estadoUsuario = 0;
	    

	        for (TblAgendaLogVisitas usuarioLog : UsuarioLogueado) {
	            Integer idLocal = usuarioLog.getIdLocal();
	            Integer idLog = usuarioLog.getIDLOG();
	            String sessionId = usuarioLog.getSessionId();
	            
	            
	            System.out.println("idLocal: " + idLocal);
	            System.out.println("idLog: " + idLog);
	            System.out.println("sessionId: " + sessionId);
	            
	            
	           estadoUsuario = controlDeInactividad.ingresa(idLocal, idLog, sessionId);          
	        }
    
	           if(estadoUsuario.equals(2)) {
	        	   System.out.println("USUARIO INACTIVO");
	        	   return "redirect:/";
	           }
		
		//------------------------------------------------------------------------------------------------------------------------------------------
	
		Integer idTipoTercero = 1;

 
		    List<TblTerceros> InformacionTercero =  tblTercerosService.ObtenerInformacionTercero(usuario.getIdLocal(), idTercero, idTipoTercero);
		    
		    for(TblTerceros tercero : InformacionTercero) {
		    	
		    	System.out.println("xInformacionTercero nombre = " + tercero.getNombreTercero());
		    	model.addAttribute("xnombreTercero", tercero.getNombreTercero());
		    	model.addAttribute("xnuid", tercero.getIdCliente());
		    	model.addAttribute("xdigitoVerificacion", tercero.getDigitoVerificacion());
		    	model.addAttribute("xccNit", tercero.getCC_Nit());
		    	
		    	
		    	String tipoDocumento = tercero.getTipoIdTercero();
		    	

		    	
		    	
		    	
		    	  switch (tipoDocumento) {
		            case "C":
		                System.out.println("Opción 1 seleccionada");
		                model.addAttribute("xtipoDocumento", 1);
			    		

		                break;
		            case "A":
		            	model.addAttribute("xtipoDocumento", 2);
			    		

		                break;
		                default:
		                System.out.println("Opción no válida");
		        }
		    	

		       
	    		
		    	
		    	
		    	String xIdRegimen = tercero.getIdRegimen();
		    	
		        switch (xIdRegimen) {
	            case "NI":
	                System.out.println("Opción 1 seleccionada");
		    		model.addAttribute("xIdRegimen", 2);
		    		

	                break;
	            case "RS":
		    		model.addAttribute("xIdRegimen", 4);
		    		

	                break;
	            case "RC":
		    		model.addAttribute("xIdRegimen", 3);

	                break;
	            case "GC":
	            	model.addAttribute("xIdRegimen", 1);

	                break;
	            default:
	                System.out.println("Opción no válida");
	        }


		    	
		    	model.addAttribute("xtelefonoCelular", tercero.getTelefonoCelular());
		    	model.addAttribute("xNombreProveedor", tercero.getNombreTercero());
		    	model.addAttribute("xNuid", tercero.getIdCliente());
		    	
		    	Integer idDtoCiudad = tercero.getIdDptoCiudad();
		    	
                String NombreCiudad = tblCiudadesService.NombreCiudad(idDtoCiudad);
                model.addAttribute("xNombreCiudad", NombreCiudad);

		    	
		    }
		    
		    ArrayList<TblTipoCausaNota> TipoDocumento = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(16);
		    
		    ArrayList<TblTipoCausaNota> TipoPersona = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(17);
		    
		    ArrayList<TblTipoCausaNota> TipoRegimen = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(18);
		    
		    ArrayList<TblTipoCausaNota> RetencionFuente = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(19);
		    
		    List<TblCiudadesDTO> DepartamentosCiudades = tblCiudadesService.ListaCiudadesDepartamentos();
		    
	        model.addAttribute("TipoDocumento", TipoDocumento);
	        model.addAttribute("TipoPersona", TipoPersona);
	        model.addAttribute("TipoRegimen", TipoRegimen);
	        model.addAttribute("RetencionFuente", RetencionFuente);
	        model.addAttribute("DepartamentosCiudades", DepartamentosCiudades);

	        
	     // Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String FechActual = fechaActual.format(formatterAct);

	        model.addAttribute("xFechaActual", FechActual);
             
             
             List<TblPlusDTO> listaPlus = tblPlusService.listaPluXLinea(usuario.getIdLocal(), 300);
             model.addAttribute("listaPlus", listaPlus);

			
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
	
	
	
	
	
	
	@PostMapping("/GuardarVenta-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> GuardarVenta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
        
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idLocal = usuario.getIdLocal();


	    System.out.println("SI ENTRÓ A  /GuardarVenta");

	    @SuppressWarnings("unchecked")
	    List<Object> xDctoRefList = (List<Object>) requestBody.get("xDctoRefArr");
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
	    String[] xDctoReArray = xDctoRefList.stream().map(String::valueOf).toArray(String[]::new);
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
	    
	    
	    System.out.println("xDctoReArray es: " + xDctoReArray);
	    System.out.println("xIdPluArray es: " + xIdPluArray);
	    System.out.println("xNombrePluArray es: " + xNombrePluArray);
	    System.out.println("xVrUnitarioArray es: " + xVrUnitarioArray);
	    System.out.println("xIvaArray es: " + xIvaArray);
	    System.out.println("xCantidadArray es: " + xCantidadArray);
	    System.out.println("xSubtotalArray es: " + xSubtotalArray);
	    System.out.println("xExistenciaArray es: " + xExistenciaArray);
	    System.out.println("totalVenta es: " + totalVenta);
	    
	    

    	
	    
	    
	 /*   for(int i = 0; i < xCuentaArray.length; i ++  ) {
	    	
	    	
	    	 // Verifica si el Dcto ya existe
	        Boolean existe = tblDctoService.ExisteDcto(idLocal, idTipoOrden, maxIdCpte);
	        System.out.println("existe Dcto es " + existe);

	        if (!existe) {
	            System.out.println("El documento no existe ");
	            
	            //(int idLocal, int idTipoCpte, int idCpte, int idTipoOrden, int idDcto, String FechaDcto, String siglaMoneda, int idTasa, int idPeriodo)
	            tblDctoRepo.ingresaDcto(idLocal, idTipoCpte, maxIdCpte, idTipoOrden, maxIdCpte, fechaDcto, siglaMoneda, 0, idPeriodo);
	            System.out.println("ingresaDcto ");
	            
	            int item = 1;

	            // Ciclo para ingresar detalle
	            for (int l = 0; l < xCuentaArray.length; l ++) {
	            	
	    	    	Integer idCuenta = Integer.parseInt(xCuentaArray[l]);
	    	    	System.out.println("idCuenta es " + idCuenta);
	    	    	
	    	    	String cliente = xTerceroArray[l];
	    	    	Double vrDebito = Double.parseDouble(xDebitoArray[l]);
	    	    	Double vrCredito = Double.parseDouble(xCreditoArray[l]);
	    	        String descripcion = xDescripcionArray[l];
	    	        String observación = xDetalleContableArray[l];
	            		                	
	                	                	
                        //(int idLocal, int idTipoCpte, int idCpte, int idCuentaAux, String idCliente, int item, int sucursal, int codProducto, int codBodega, 
	      			    // int accion, int cantProducto, int prefijo, int consecutivo, int numeroCuota, String fechaVencimiento, int codImpuesto, int codGrupoActivoFijo, int codActivoFijo,
	    			   //String descripcion, int codCentroSubCentro, Double vrDebito, Double vrCredito, String observacion, Double baseGravable, int mesCierre )
	                 	tblDctoDetalleRepo.ingresaDctoDetalle(idLocal, idTipoCpte, maxIdCpte, idCuenta, cliente, item, 0, 0, 0, 
	                	 0, 0, 0, 0, 0, fechaDcto, 0, 0, 0, descripcion, 0, vrDebito, vrCredito, observación, 0.0, idPeriodo);
	                 	
	                	System.out.println("ingresaDctoDetalle ");
	                    
	                    item++;
	               
	            }

	        } else {
	        	

	        }
	    	
	    	
	    } */
	    

	        
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		  

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	/*		
	
	@PostMapping("/LegalizarDctoSoporte-Post")
	@ResponseBody
	public ResponseEntity<Resource>  LegalizarDctoSoporte(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase); 
		Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    System.out.println("SI ENTRÓ A  LegalizarDctoSoporte");

	        // Obtenemos los datos del JSON recibido	        
	        Integer xIdTipoOrden = 601;
            String xIdCliente = (String) requestBody.get("idCliente");
            System.out.println("xIdCliente en el ingreso es " + xIdCliente);
            String xFechaCorte = (String) requestBody.get("xFechaCorte");
            String xDescripcion = (String) requestBody.get("xDescripcion");
            String xIdDctoNitCC = (String) requestBody.get("xIdDctoNitCC");

            String xVrUnitario = (String) requestBody.get("xVrUnitario");
            String xVrIva = "0";
            String xVrRteIva = "0";
            String xPorcentajeRteFuente = (String) requestBody.get("xPorcentajeRteFuente");
            String xVrRteIca = "0";
            String xVrPagoDou = (String) requestBody.get("xVrPago");
            
            
            Double xPorcentajeRteFuenteDou = Double.parseDouble(xPorcentajeRteFuente);
            Double xVrUnitarioDou = Double.parseDouble(xVrUnitario);

            Double xVrPago = xVrUnitarioDou - (xVrUnitarioDou * (xPorcentajeRteFuenteDou / 100.00));
            Double xVrRteFuenteDou = xVrUnitarioDou * (xPorcentajeRteFuenteDou / 100.00);
            
            
          
            Integer idPlu = Integer.parseInt(xDescripcion);
            
            String formato = "PDF";

	        int idLocal = usuario.getIdLocal();
            
            
            Integer xIdLog =  tblAgendaLogVisitasService.findMaxIDLOG() + 1;
            
            int estadoAtendido = 1; // visitaActiva
            int estadoProgramada = 9; // visitaProgramada
            int idEstadoVisita = 1; // Programada 
            
            tblAgendaLogVisitasRepo.actualizaLogVisitaUsuario(estadoAtendido, IdUsuario, estadoProgramada);
            
         // Obtener la fecha actual
            LocalDate fechaActual = LocalDate.now();

            // Formatear la fecha como un String
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String strFechaVisita = fechaActual.format(formatter);
            

            tblAgendaLogVisitasRepo.ingresaLogVisita(xIdLog, xIdCliente, IdUsuario, idLocal, idLocal, 0,strFechaVisita, 
            		idEstadoVisita, estadoProgramada, xIdTipoOrden, strFechaVisita);
            
            System.out.println("ingresaLogVisita OK");
            //
           Integer xIdLogActual = xIdLog;
           
          

            
            
           procesoGuardaPorcentaje.guarda(xIdLogActual,
        		   xVrUnitarioDou, 
        		   xIdTipoOrden,
        		   IdUsuario, 
        		   idLocal, 
        		   xIdCliente, 
        		   xFechaCorte, 
        		   xPorcentajeRteFuenteDou,
        		   idPlu);
           
           
           System.out.println("procesoGuardaPorcentaje OK");
           
           int xIdAlcance = 6;
           int xIndicador = 1;
           int xIdTipoNegocio = 1;
           int xIdTipoOrdenIni = 0;
           
            
      int idOrden =  procesoIngresoComprobante.ingresaCompra(idLocal, 
        		   xIdTipoOrden, 
        		   xIdLog,
        		   xIdTipoOrden,
        		   xFechaCorte,
        		   xDescripcion,
        		   xIdDctoNitCC, 
        		   xIdAlcance,
        		   xIndicador, 
        		   xIdTipoNegocio, 
        		   xVrIva, 
        		   xVrRteFuenteDou.toString(),
        		   xVrRteIva, 
        		   xVrRteIca);
            
      System.out.println("procesoIngresoComprobante OK");
           
           int xIdEstadoTxSinTx = 1;
           int tareaVisita = 6;
           int estadoTerminado = 1;
           
           
        // Obtenemos la IP del servidor
           UtilidadesIP utilidadesIP = new UtilidadesIP();
           String xIpTx = utilidadesIP.getIpServidor();
           

           tblAgendaLogVisitasRepo.finaliza(estadoTerminado, xIdCliente, tareaVisita, xIdTipoOrden, xIdEstadoTxSinTx, idLocal, xIpTx, strFechaVisita, xIdLogActual);
           
           
            // -------------------------------------------------------------------------------------- Reporte    -----------------------------------------------------
	        

		    int xIdReporte = 1250;
		    
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

		   //Obtenemos el prefijo de localesCaja
		   String xPrefijoDS = tblLocalesService.ObtenerPrefijoDocumentoSoporte(idLocal);
		   
		   
		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 

			    params.put("p_nombreLocal", L.getNombreLocal());
			    params.put("p_nit", L.getNit());
			    params.put("p_tituloReporte", xTituloReporte);
			    params.put("p_idLocal", idLocal);
			    params.put("p_indicadorINI", IndicadorINICIAL);
			    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
			    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
			    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
		    }
		    
		    
		    
		    List<TercerosDTO2> listaTerceroUnion = tblTercerosService.listaUnTerceroUnionFCH(idLocal, xIdCliente);
	    	
	    	
	    	for(TercerosDTO2 tercero : listaTerceroUnion ) {
	    		
	    		params.put("p_nombreTercero", tercero.getNombreTercero());
	    		params.put("p_idTercero", "Proveedor " + tercero.getIdCliente());
	    		params.put("p_idOrden", tercero.getIdOrden());
	    		params.put("p_idLocal", tercero.getIdLocal());

	    	}

	    	
	    	List<TblDctosDTO> listaDcto =	TblDctosService.listaUnDctoFCH(idLocal, xIdTipoOrden, idOrden);
	    	
	    	for(TblDctosDTO dcto : listaDcto) {
	    		
	    		params.put("p_observacion", "CONCEPTO " + " " + dcto.getObservacion());
	    		params.put("p_fechaOrden", "Fecha Compra " + dcto.getFechaDcto());
	    		params.put("p_textoFactura", "Documento soporte "  + xPrefijoDS + dcto.getIdDcto());
	    		params.put("p_nombreUsuario", "Elaboro " + dcto.getNombreVendedor());
	    		params.put("p_fechaTx", "Fecha elaboracion "+ dcto.getFechaTx());
	    		params.put("p_idDctoNitCC", "Dcto.Ref. " + dcto.getIdDctoNitCC());
	    		
	    	}
	    	

		    
		    List<TblDctosOrdenesDetalleDTO2> lista = null;
		    

	            // QUERY PARA ALIMENTAR EL DATASOURCE
	            lista = tblDctosOrdenesDetalleService.detallaUnComprobanteCompraEgresoIngreso(xIdTipoOrden, idOrden, idLocal);
		    	
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
	
	
	
	
	@PostMapping("/TraerListaDctoSoporte")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> TraerListaDctoSoporte(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase); 
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");


	    System.out.println("SI ENTRÓ A  /TraerListaDctoSoporte");
	    
	   // Obtenemos los datos del JSON recibido
        String xFechaInicial = (String) requestBody.get("FechaInicial");
        String xFechaFinal = (String) requestBody.get("FechaFinal");

	    
	    int idLocal = usuario.getIdLocal();

		List<TblDctosDTO4> listaDctoSoporte = TblDctosService.listaFechaDocumentoSoporte(idLocal, xFechaInicial, xFechaFinal);

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("ListaBusqueda", listaDctoSoporte);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	@PostMapping("/DescargarReporteDctoSoporte")
	public ResponseEntity<Resource> DescargarReporteDctoSoporte(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	   
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase); 
		
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		

		
		 // Obtenemos los datos del JSON recibido
        String idDctoStr = (String) requestBody.get("idDctoStr");
        Integer xDcto = Integer.parseInt(idDctoStr);

        String formato = "PDF";

        int idTipoOrden = 601;
        
        Integer idLocal = usuario.getIdLocal();
        
        //Obtenemos el idOrden
        Integer idOrden =  TblDctosService.ObtenerIdOrden(idLocal, idTipoOrden, xDcto); 
        
       //Obtenemos el idCliente
        String idCliente = TblDctosService.ObtenerIdClientePorIdOrden(idLocal, idOrden);
        
        
		
        int xIdReporte = 1250;
	    
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
	   
	   String xPrefijoDS = tblLocalesService.ObtenerPrefijoDocumentoSoporte(idLocal);  
	   
	    for(TblLocales L : Local) {
	    	
		    // Parametros del encabezado 

		    params.put("p_nombreLocal", L.getNombreLocal());
		    params.put("p_nit", L.getNit());
		    params.put("p_tituloReporte", xTituloReporte);
		    params.put("p_idLocal", idLocal);
		    params.put("p_indicadorINI", IndicadorINICIAL);
		    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
		    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
		    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
		    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
	    }
	    
	    
	    
	    List<TercerosDTO2> listaTerceroUnion = tblTercerosService.listaUnTerceroUnionFCH(idLocal, idCliente);
    	
    	
    	for(TercerosDTO2 tercero : listaTerceroUnion ) {
    		
    		params.put("p_nombreTercero", tercero.getNombreTercero());
    		params.put("p_idTercero", "Proveedor " + tercero.getIdCliente());
    		params.put("p_idOrden", tercero.getIdOrden());
    		params.put("p_idLocal", tercero.getIdLocal());

    	}

    	
    	List<TblDctosDTO> listaDcto =	TblDctosService.listaUnDctoFCH(idLocal, idTipoOrden, idOrden);
    	
    	for(TblDctosDTO dcto : listaDcto) {
    		
    		params.put("p_observacion", "CONCEPTO " + " " + dcto.getObservacion());
    		params.put("p_fechaOrden", "Fecha Compra " + dcto.getFechaDcto());
    		params.put("p_textoFactura", "Documento soporte "  + xPrefijoDS + dcto.getIdDcto());
    		params.put("p_nombreUsuario", "Elaboro " + dcto.getNombreVendedor());
    		params.put("p_fechaTx", "Fecha elaboracion "+ dcto.getFechaTx());
    		params.put("p_idDctoNitCC", "Dcto.Ref. " + dcto.getIdDctoNitCC());
    		
    	}
    	

	    
	    List<TblDctosOrdenesDetalleDTO2> lista = null;
	    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblDctosOrdenesDetalleService.detallaUnComprobanteCompraEgresoIngreso(idTipoOrden, idOrden, idLocal);
	    	
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
	
	
	
	
	@PostMapping("/EnviarDctoSoporteDIAN-Post")
	@ResponseBody
    public ResponseEntity<Map<String, String>> EnviarDctoSoporteDIAN(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {

		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase); 
		
        Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
       
        Integer idLocal = usuario.getIdLocal();
  
        
        // Obtenemos los datos del JSON recibido
	    String xIdDcto = (String) requestBody.get("xIdDcto");
	    System.out.println("Entró a /EnviarDctoSoporteDIAN con xIdDcto " + xIdDcto);
        
        Integer xIdDctoInt = Integer.parseInt(xIdDcto);
        
        int xIdTipoOrden = 601;

        //
        String xCharSeparator = File.separator;
        String xRuta = "";

     // Linux 
        if (xCharSeparator.compareTo("/") == 0) {

            // Linux               
            xRuta = "" + xCharSeparator + "home" + xCharSeparator + "sw" + xCharSeparator + "jar" + xCharSeparator + "ApiDctoSoporte" + xCharSeparator + "target" + xCharSeparator + "ApiDctoSoporte.jar ";

        } else {

            // Windows          
            xRuta = "C:" + xCharSeparator + "proyectoWeb" + xCharSeparator + "ApiDctoSoporte" + xCharSeparator + "target" + xCharSeparator + "ApiDctoSoporte.jar ";

        }

        //
        final String xRutaDisco = xRuta;
        final String xSistema = "marketing";
        final String xTipo = "documento";

        //
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    //
                    Runtime rt = Runtime.getRuntime();

                    //
                    Process proc = rt.exec("java -jar " + xRutaDisco
                            + idLocal + " "
                            + xIdTipoOrden + " "
                            + xIdDctoInt + " "
                            + xSistema +  " "
                            + xTipo);



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
        
        
       


        Map<String, String> response = new HashMap<>();
        
        response.put("mensaje", "OK");
        return ResponseEntity.ok(response);
    }
	
	
	@PostMapping("/AjustarDctoSoporteDIAN-Post")
	@ResponseBody
    public ResponseEntity<Map<String, String>> AjustarDctoSoporteDIAN(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {

		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase); 
		
        Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
       
        Integer idLocal = usuario.getIdLocal();
  
        
        // Obtenemos los datos del JSON recibido
	    String xIdDcto = (String) requestBody.get("xIdDcto");
	    System.out.println("Entró a /AjustarDctoSoporteDIAN con xIdDcto " + xIdDcto);
        
        Integer xIdDctoInt = Integer.parseInt(xIdDcto);
        
        int xIdTipoOrden = 601;

        //
        String xCharSeparator = File.separator;
        String xRuta = "";

     // Linux 
        if (xCharSeparator.compareTo("/") == 0) {

            // Linux               
            xRuta = "" + xCharSeparator + "home" + xCharSeparator + "sw" + xCharSeparator + "jar" + xCharSeparator + "ApiDctoSoporte" + xCharSeparator + "target" + xCharSeparator + "ApiDctoSoporte.jar ";

        } else {

            // Windows          
            xRuta = "C:" + xCharSeparator + "proyectoWeb" + xCharSeparator + "ApiDctoSoporte" + xCharSeparator + "target" + xCharSeparator + "ApiDctoSoporte.jar ";

        }

        //
        final String xRutaDisco = xRuta;
        final String xSistema = "marketing";
        final String xTipo = "ajuste";

        //
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    //
                    Runtime rt = Runtime.getRuntime();

                    //
                    Process proc = rt.exec("java -jar " + xRutaDisco
                            + idLocal + " "
                            + xIdTipoOrden + " "
                            + xIdDctoInt + " "
                            + xSistema +  " "
                            + xTipo);



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
        
        
       


        Map<String, String> response = new HashMap<>();
        
        response.put("mensaje", "OK");
        return ResponseEntity.ok(response);
    }
	
	*/

}
