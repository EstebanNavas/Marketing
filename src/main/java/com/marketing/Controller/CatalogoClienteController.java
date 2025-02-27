package com.marketing.Controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblCiudadesDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblCiudadesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblMedidoresService;
import com.marketing.Service.dbaquamovil.TblTerceroEstractoService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class CatalogoClienteController {
	
	@Autowired 
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblMedidoresMacroService tblMedidoresMacroService;
	
	@Autowired
	TblMedidoresService  tblMedidoresService;
	
	@Autowired
	TblTerceroEstractoService  tblTerceroEstractoService;
	
	@Autowired
	TblTercerosRutaService  tblTercerosRutaService;
	
	@Autowired
	TblTipoCausaNotaService tblTipoCausaNotaService;
	
	@Autowired
	TblCiudadesService  tblCiudadesService;
	
	@Autowired 
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	
	@GetMapping("/CatalogoCliente")
	public String catalogoCliente(HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /CatalogoCliente");
		
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
		
		    

			
			return "Catalogo/Cliente";
			


	}
	
	
	
	
	@GetMapping("/CrearCliente")
	public String CrearCliente(HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /CrearCliente");
		
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
		

		    Integer idTipoTercero = 2;

		    
		    
		    ArrayList<TblTipoCausaNota> TipoDocumento = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(16);
		    
		    ArrayList<TblTipoCausaNota> TipoPersona = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(17);
		    
		    ArrayList<TblTipoCausaNota> TipoRegimen = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(18);
		    
		    ArrayList<TblTipoCausaNota> RetencionFuente = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(19);
		    
		    List<TblCiudadesDTO> DepartamentosCiudades = tblCiudadesService.ListaCiudadesDepartamentos();
		    
		    Long MaximoIdTercero = tblTercerosService.MaximoIdTercero(usuario.getIdLocal(), idTipoTercero) + 1;
		    
		    // Obtenemos la fecha y hora actual
		    Date fechaRadicacion = new Date(); 

		    // Formatea la fecha en el formato deseado
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    String fechaInstalacion = dateFormat.format(fechaRadicacion);

		    model.addAttribute("fechaInstalacion", fechaInstalacion);
		    
		    for(TblCiudadesDTO ciudad : DepartamentosCiudades) {
		    	
		    	 System.out.println("ciudad  es: " + ciudad.getDepartamentoCiudad());
		    	
		    	
		    }
		    

	        model.addAttribute("TipoDocumento", TipoDocumento);
	        model.addAttribute("TipoPersona", TipoPersona);
	        model.addAttribute("TipoRegimen", TipoRegimen);
	        model.addAttribute("RetencionFuente", RetencionFuente);
	        model.addAttribute("DepartamentosCiudades", DepartamentosCiudades);
	        model.addAttribute("MaximoIdTercero", MaximoIdTercero);
	    
			
			return "Catalogo/CrearCliente";

		
	}
	
	
	
	@PostMapping("/CrearCliente-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearSuscriptorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 4;
	    Integer cero = 0;
	    String ceroStirng = "0";


	    System.out.println("SI ENTRÓ A  /CrearCliente-Post");

	        // Obtenemos los datos del JSON recibido
	        String nombreTercero = (String) requestBody.get("nombreTercero");
//	        Integer MaximoIdTercero = tblTercerosService.MaximoIdTercero(usuario.getIdLocal(), idTipoTercero) + 1;
//	        String idCliente = MaximoIdTercero.toString();
	        Integer digitoVerificacion = 0;
	        String ccNit = (String) requestBody.get("ccNit");   
	        String tipoDocumento = (String) requestBody.get("tipoDocumento");    
	        String tipoPersona = (String) requestBody.get("tipoPersona");  
	        Integer tipoPersonaint = Integer.parseInt(tipoPersona);
	        String reteFuente = (String) requestBody.get("reteFuente");  
	        Integer reteFuenteInt = Integer.parseInt(reteFuente);
	        String regimen = (String) requestBody.get("regimen"); 
	        String direccion = (String) requestBody.get("direccion");  
	        String DptoCiudad = (String) requestBody.get("DptoCiudad"); 
	        Integer DptoCiudadInt = Integer.parseInt(DptoCiudad);
	        String telefonoFijo = (String) requestBody.get("telefonoFijo");  
	        String telefonoCelular = (String) requestBody.get("telefonoCelular"); 
	        String telefonoFax = (String) requestBody.get("telefonoFax"); 
	        String email = (String) requestBody.get("email");    
	        String contacto = (String) requestBody.get("contacto");  
	     
		    // Obtenemos la fecha y hora actual
	        Date fechaActual = new Date();

	        // Formatear la fecha en el formato deseado
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String fechaActualFormateada = dateFormat.format(fechaActual);

	        // Convertir la fecha formateada a Timestamp
	        Timestamp fechaIngreso = Timestamp.valueOf(fechaActualFormateada + ":00");

	        
	        
	        

	        
	        // Ingresamos el nuevo tercero
	        tblTercerosService.ingresarTerceroCliente(usuario.getIdLocal(), ccNit, idTipoTercero, nombreTercero, direccion, direccion, DptoCiudadInt, telefonoFijo,
	        		telefonoCelular, email, cero, cero, ccNit, ceroStirng, cero, cero, ceroStirng, fechaIngreso, fechaIngreso, ceroStirng, tipoPersonaint, ceroStirng,
	        		digitoVerificacion, regimen, contacto, telefonoFax, tipoDocumento, reteFuenteInt );
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTercero", nombreTercero);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	@GetMapping("/TaertodosClientes")
	public String TaertodosClientes(HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");

		System.out.println("Entró a /TaertodosClientes");
		
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

		    
		    List<TercerosDTO> ListaTercerosClientes = tblTercerosService.ListaTercerosClientes(usuario.getIdLocal());
		    
		    for(TercerosDTO tercero : ListaTercerosClientes) {
		    	
		    	 System.out.println("tercero id : " + tercero.getIdCliente());
		    	 System.out.println("nombreTercero : " + tercero.getNombreTercero());
		    	 System.out.println("Direccion tercero: " + tercero.getDireccionTercero());
		    	
		    }
		    
		    
		    System.out.println("La lista de ListaTercerosClientes es: " + ListaTercerosClientes);
	        
	        model.addAttribute("ListaTercerosClientes", ListaTercerosClientes);
		    
			
			return "Catalogo/TodosLosClientes";

	}
	
	
	@PostMapping("/BuscarCliente")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarCliente(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarProveedor");

	        // Obtenemos los datos del JSON recibido
	        String palabraClave = (String) requestBody.get("palabraClave");
	        System.out.println("palabraClave desde /BuscarProveedor " + palabraClave);


	        
	        
	        
           List<TercerosDTO> ListaBusqueda = null;
	        
	        try {
	            // Intentamos convertir palabraClave a un número
	            Integer idCliente = Integer.parseInt(palabraClave);
	            
	            System.out.println("La palabra clave es un numero");
	            // Si no se lanza NumberFormatException, entonces palabraClave es un número
	            ListaBusqueda = tblTercerosService.BuscarTercerosClienteNUID(usuario.getIdLocal(), palabraClave);
	        } catch (NumberFormatException e) {
	        	
	        	System.out.println("La palabra clave NO es un numero");
	            // Si se lanza NumberFormatException, entonces palabraClave es una palabra normal
	        	ListaBusqueda = tblTercerosService.BuscarTercerosCliente(usuario.getIdLocal(), palabraClave);
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
	
	
	
	
	@PostMapping("/TraerCliente-Post")
	public ModelAndView TraerProveedorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ActualizarSuscriptor");

	    // Obtenemos los datos del JSON recibido
	    String idTercero = (String) requestBody.get("idTercero");
	    //Integer idTercero = Integer.parseInt(idTerceroString);


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerCliente?idTercero=" + idTercero);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerCliente")
	public String TraerCliente(@RequestParam(name = "idTercero", required = false) String idTercero, HttpServletRequest request, Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerCliente con idTercero: " + idTercero);
		
		
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
	
		Integer idTipoTercero = 4;

 
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

	
		    	model.addAttribute("xReteFuente", tercero.getIdAutoRetenedor());
		    	model.addAttribute("xTipoPersona", tercero.getIdPersona());
		    	
		    	model.addAttribute("xDptoCiudad", tercero.getIdDptoCiudad());
		    	model.addAttribute("xIdEstrato", tercero.getIdEstracto());
		    	//model.addAttribute("xIdEstrato", tercero.getTerceroEstracto().getIdEstracto());
		    	model.addAttribute("xIMedidor", tercero.getIdMedidor());
		    	model.addAttribute("xIMacro", tercero.getIdMacro());
		    	//model.addAttribute("xIRuta", tercero.getTerceroRuta().getIdRuta());
		    	
		    	model.addAttribute("xIRuta", tercero.getIdRuta());
		    	
		    	String tipoDocumentoStr = tercero.getTipoIdTercero();
		    	Integer tipoDocumentoInt = Integer.parseInt(tipoDocumentoStr);
		    	model.addAttribute("xtipoDocumento", tipoDocumentoInt);
		    	
		    	
		    	String idRegimenStr = tercero.getIdRegimen();
		    	Integer idRegimenInt = Integer.parseInt(idRegimenStr);
		    	model.addAttribute("xIdRegimen", idRegimenInt);
		    	
		    	
		    	
		    	model.addAttribute("xtipoSuscriptor", tercero.getTipoSuscriptor());
		    	model.addAttribute("xdireccionPredio", tercero.getDireccionTercero());
		    	model.addAttribute("xtelefonoFijo", tercero.getTelefonoFijo());
		    	model.addAttribute("xtelefonoCelular", tercero.getTelefonoCelular());
		    	model.addAttribute("xtelefonoFax", tercero.getTelefonoFax());
		    	model.addAttribute("xemail", tercero.getEmail());
		    	model.addAttribute("xcontacto", tercero.getContactoTercero());

		    	
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

	
	    
		    

			
			return "Catalogo/ActualizarCliente";


	}
	
	
	
	@PostMapping("/ActualizarCliente-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarCliente(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 4;
	    Integer cero = 0;
	    String ceroStirng = "0";

	    System.out.println("SI ENTRÓ A  /ActualizarCliente-Post");

	 // Obtenemos los datos del JSON recibido
        String nombreTercero = (String) requestBody.get("nombreTercero");
        String nuid = (String) requestBody.get("nuid");   
        String digitoVerificacion = (String) requestBody.get("digitoVerificacion");  
        Integer digitoVerificacionInt = Integer.parseInt(digitoVerificacion);
        String ccNit = (String) requestBody.get("ccNit");   
        String tipoDocumento = (String) requestBody.get("tipoDocumento");    
        String tipoPersona = (String) requestBody.get("tipoPersona");  
        Integer tipoPersonaint = Integer.parseInt(tipoPersona);
        String reteFuente = (String) requestBody.get("reteFuente");  
        String regimen = (String) requestBody.get("regimen"); 
        String direccion = (String) requestBody.get("direccion");  
        String DptoCiudad = (String) requestBody.get("DptoCiudad"); 
        Integer DptoCiudadInt = Integer.parseInt(DptoCiudad);
        String telefonoFijo = (String) requestBody.get("telefonoFijo");  
        String telefonoCelular = (String) requestBody.get("telefonoCelular"); 
        String telefonoFax = (String) requestBody.get("telefonoFax"); 
        String email = (String) requestBody.get("email");    
        String contacto = (String) requestBody.get("contacto");  
	     
		    // Obtenemos la fecha y hora actual
	        Date fechaActual = new Date();

	        // Formatear la fecha en el formato deseado
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String fechaActualFormateada = dateFormat.format(fechaActual);

	        // Convertir la fecha formateada a Timestamp
	        Timestamp fechaIngreso = Timestamp.valueOf(fechaActualFormateada + ":00");

	        
	        

	        
	        // Ingresamos el nuevo tercero
	        tblTercerosRepo.actualizarTerceroCliente(nombreTercero, direccion, direccion, DptoCiudadInt, telefonoFijo, telefonoCelular, email, cero, cero, ccNit, ceroStirng, cero, cero, ceroStirng, fechaIngreso, fechaIngreso, ceroStirng, tipoPersonaint, ceroStirng, digitoVerificacionInt, regimen, telefonoFax, contacto,  usuario.getIdLocal(), nuid, idTipoTercero);
		    
	        System.out.println("CLIENTE ACTUALIZADO CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTercero", nombreTercero);
		    response.put("idTercero", nuid);
		    return ResponseEntity.ok(response);
	   
	    
	}

}
