package com.marketing.Controller;

import java.sql.Timestamp;
import java.text.ParseException;
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
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblCiudadesDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Projection.TercerosDTO2;
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
public class CatalogoEmpleadoController {

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
	
	
	@GetMapping("/CatalogoEmpleado")
	public String CatalogoSuscriptor(HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /CatalogoSuscriptor");
		
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

			
			
			
			return "Catalogo/Empleado";
	
	}
	
	
	@GetMapping("/TaertodosEmpleados")
	public String TaertodosSuscriptores(HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");

		System.out.println("Entró a /CatalogoEmpleados");
		
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

		    List<TercerosDTO2> ListaTercerosEmpleados = tblTercerosService.ListaTercerosEmpleados(usuario.getIdLocal());
		    
		    
		    System.out.println("La lista de Susctiptores es: " + ListaTercerosEmpleados);
	        
	        model.addAttribute("ListaTercerosEmpleados", ListaTercerosEmpleados);
		    
			
			return "Catalogo/TodosLosEmpleados";

		
	}
	
	
	@PostMapping("/BuscarEmpleado")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarSuscriptor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarEmpleado");

	        // Obtenemos los datos del JSON recibido
	        String palabraClave = (String) requestBody.get("palabraClave");
	        System.out.println("palabraClave desde /BuscarEmpleado " + palabraClave);

	        
	        
              List<TercerosDTO> ListaBusqueda = null;
	        
	        try {
	            // Intentamos convertir palabraClave a un número
	            Integer idCliente = Integer.parseInt(palabraClave);
	            
	            System.out.println("La palabra clave es un numero");
	            // Si no se lanza NumberFormatException, entonces palabraClave es un número
	            ListaBusqueda = tblTercerosService.BuscarTercerosEmpleadosNUID(usuario.getIdLocal(), palabraClave);
	        } catch (NumberFormatException e) {
	        	
	        	System.out.println("La palabra clave NO es un numero");
	            // Si se lanza NumberFormatException, entonces palabraClave es una palabra normal
	            ListaBusqueda = tblTercerosService.BuscarTercerosEmpleados(usuario.getIdLocal(), palabraClave);
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
	
	
	@GetMapping("/CrearEmpleado")
	public String CrearSuscriptor(HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /CrearEmpleado");
		
		
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

		    Integer idTipoTercero = 3;

		    

		    List<TblMedidoresMacro> ListaMedidoresMacro = tblMedidoresMacroService.ListaMedidoresMacro(usuario.getIdLocal());
		    System.out.println("ListaMedidoresMacro  es: " + ListaMedidoresMacro);
		    
		    List<TblMedidores> listaMedidores = tblMedidoresService.ListaMedidores(usuario.getIdLocal());
		    System.out.println("listaMedidores  es: " + listaMedidores);
		    
		    List<TblTerceroEstracto> listaEstratos = tblTerceroEstractoService.obtenerEstracto(usuario.getIdLocal());
		    System.out.println("listaEstratos  es: " + listaEstratos);
		    
		    List<TblTercerosRuta> listaRutas = tblTercerosRutaService.ListaRutas(usuario.getIdLocal());
		    System.out.println("listaRutas  es: " + listaRutas);
		    
		    ArrayList<TblTipoCausaNota> TipoSuscriptor = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(15);
		    System.out.println("TipoSuscriptor  es: " + TipoSuscriptor);
		    
		    List<TblCiudadesDTO> DepartamentosCiudades = tblCiudadesService.ListaCiudadesDepartamentos();
		    System.out.println("DepartamentosCiudades  es: " + DepartamentosCiudades);
		    
		    Long MaximoIdTercero = tblTercerosService.MaximoIdTercero(usuario.getIdLocal(), idTipoTercero) + 1;
		    System.out.println("MaximoIdTercero  es: " + MaximoIdTercero);
		    
		    // Obtenemos la fecha y hora actual
		    Date fechaRadicacion = new Date(); 

		    // Formatea la fecha en el formato deseado
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    String fechaInstalacion = dateFormat.format(fechaRadicacion);

		    model.addAttribute("fechaInstalacion", fechaInstalacion);
		    
		    for(TblCiudadesDTO ciudad : DepartamentosCiudades) {
		    	
		    	 System.out.println("ciudad  es: " + ciudad.getDepartamentoCiudad());
		    	
		    	
		    }
		    
		    
		    model.addAttribute("listaMedidores", listaMedidores);
	        model.addAttribute("ListaMedidoresMacro", ListaMedidoresMacro);
	        model.addAttribute("listaEstratos", listaEstratos);
	        model.addAttribute("listaRutas", listaRutas);
	        model.addAttribute("TipoSuscriptor", TipoSuscriptor);
	        model.addAttribute("DepartamentosCiudades", DepartamentosCiudades);
	        model.addAttribute("MaximoIdTercero", MaximoIdTercero);
	    
			
			return "Catalogo/CrearEmpleado";

	}
	
	
	@PostMapping("/CrearEmpleado-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearEmpleadoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 3;
	    Integer cero = 0;
	    String ceroString = "0";

	    System.out.println("SI ENTRÓ A  /CrearEmpleado-Post");

	        // Obtenemos los datos del JSON recibido
	        String nombreTercero = (String) requestBody.get("nombreTercero");
	        String ccNit = (String) requestBody.get("ccNit");   

	        String DptoCiudad = (String) requestBody.get("DptoCiudad");  
	        Integer DptoCiudadInt = Integer.parseInt(DptoCiudad);
	        String direccionPredio = (String) requestBody.get("direccionPredio");  	
	        String telefonoFijo = (String) requestBody.get("telefonoFijo");  
	        String telefonoCelular = (String) requestBody.get("telefonoCelular");  
	        String email = (String) requestBody.get("email");  

	        Double ceroDouble = 0.0;
	        String codigoAlterno = "0";
	      	     
		    // Obtenemos la fecha y hora actual
	        Date fechaActual = new Date();

	        // Formatear la fecha en el formato deseado
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String fechaActualFormateada = dateFormat.format(fechaActual);

	        // Convertir la fecha formateada a Timestamp
	        Timestamp fechaIngreso = Timestamp.valueOf(fechaActualFormateada + ":00");

	        
	        
	        int estadoEmail = 1;
	        
	      // Validamos si el email está vacio y se pone el estadoEmail INACTIVO
	        if(email.equals("")) {
	        	System.out.println("email vacio");
	        	
	        	estadoEmail = 2;
	        	
	        }

	        
	        Integer idEstracto = 0;
	        Integer tipoSucriptorInt = 1;
	        Integer estadoTercero = 1;
	        Integer estadoCorte = 0;
	        Integer estadoWhatsApp = 0;
	        
	        // Ingresamos el nuevo tercero
	        tblTercerosService.ingresarTercero(usuario.getIdLocal(), ccNit, idTipoTercero, nombreTercero, direccionPredio, direccionPredio, DptoCiudadInt, telefonoFijo,
	        		telefonoCelular, email, cero, idEstracto, ccNit, ceroString, cero, cero, ceroString, fechaIngreso, fechaIngreso, codigoAlterno, tipoSucriptorInt, ceroString, ceroDouble, estadoEmail, estadoTercero, estadoCorte, estadoWhatsApp );
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTercero", nombreTercero);
		    response.put("idTercero", ccNit);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/TraerEmpleado-Post")
	public ModelAndView TraerSuscriptorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerEmpleado");

	    // Obtenemos los datos del JSON recibido
	    String idTercero = (String) requestBody.get("idTercero");
	    //Integer idTercero = Integer.parseInt(idTerceroString);


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerEmpleado?idTercero=" + idTercero);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerEmpleado")
	public String TraerSuscriptor(@RequestParam(name = "idTercero", required = false) String idTercero, HttpServletRequest request, Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerEmpleado con idTercero: " + idTercero);
		
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
		
		Integer idTipoTercero = 3;

		    List<TblTerceros> InformacionTercero =  tblTercerosService.ObtenerInformacionTercero(usuario.getIdLocal(), idTercero, idTipoTercero);
		    
		    for(TblTerceros tercero : InformacionTercero) {
		    	
		    	System.out.println("xInformacionTercero nombre = " + tercero.getNombreTercero());
		    	model.addAttribute("xnombreTercero", tercero.getNombreTercero());
		    	//model.addAttribute("xnuid", tercero.getIdCliente());
		    	model.addAttribute("xnuid", tercero.getIdCliente() );
		    	model.addAttribute("xcodigoAlterno", tercero.getCodigoAlterno());
		    	model.addAttribute("xccNit", tercero.getCC_Nit());
		    	
		       	model.addAttribute("xtipoSuscriptor", tercero.getTipoSuscriptor());
		    	model.addAttribute("xDptoCiudad", tercero.getIdDptoCiudad());
		    	model.addAttribute("xIdEstrato", tercero.getIdEstracto());
		    	//model.addAttribute("xIdEstrato", tercero.getTerceroEstracto().getIdEstracto());
		    	
		    	
		    	model.addAttribute("xtipoSuscriptor", tercero.getTipoSuscriptor());
		    	model.addAttribute("xdireccionPredio", tercero.getDireccionTercero());
		    	model.addAttribute("xdireccionCobro", tercero.getDireccionCobro());
		    	model.addAttribute("xtelefonoFijo", tercero.getTelefonoFijo());
		    	model.addAttribute("xtelefonoCelular", tercero.getTelefonoCelular());
		    	model.addAttribute("xemail", tercero.getEmail());
		    	model.addAttribute("xnumeroMedidor", tercero.getNumeroMedidor());
		    	model.addAttribute("xmatricula", tercero.getMatricula());
		    	model.addAttribute("xfechaInstalacion", tercero.getFechaInstalacionMedidor());
		    	model.addAttribute("xcodigoCatastral", tercero.getCodigoCatastral());
		    	
		    	
		    	
		    }
		    
		    List<TblMedidoresMacro> ListaMedidoresMacro = tblMedidoresMacroService.ListaMedidoresMacro(usuario.getIdLocal());
		    System.out.println("ListaMedidoresMacro  es: " + ListaMedidoresMacro);
		    
		    List<TblMedidores> listaMedidores = tblMedidoresService.ListaMedidores(usuario.getIdLocal());
		    
		    List<TblTerceroEstracto> listaEstratos = tblTerceroEstractoService.obtenerEstracto(usuario.getIdLocal());
		    
		    List<TblTercerosRuta> listaRutas = tblTercerosRutaService.ListaRutas(usuario.getIdLocal());
		    
		    ArrayList<TblTipoCausaNota> TipoSuscriptor = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(15);
		    
		    List<TblCiudadesDTO> DepartamentosCiudades = tblCiudadesService.ListaCiudadesDepartamentos();
		    
		    model.addAttribute("listaMedidores", listaMedidores);
	        model.addAttribute("ListaMedidoresMacro", ListaMedidoresMacro);
	        model.addAttribute("listaEstratos", listaEstratos);
	        model.addAttribute("listaRutas", listaRutas);
	        model.addAttribute("TipoSuscriptor", TipoSuscriptor);
	        model.addAttribute("DepartamentosCiudades", DepartamentosCiudades);
	
	    
		    

			
			return "Catalogo/ActualizarEmpleado";


	}
	
	
	@PostMapping("/ActualizarEmpleado-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarEmpleado(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 3;
	    Integer cero = 0;
	    String ceroString = "0";

	    System.out.println("SI ENTRÓ A  /ActualizarEmpleado-Post");

        // Obtenemos los datos del JSON recibido
        String nombreTercero = (String) requestBody.get("nombreTercero");
        String nuid = (String) requestBody.get("nuid");   
//        String codigoAlterno = (String) requestBody.get("codigoAlterno");  
        String ccNit = (String) requestBody.get("ccNit");   
//        String tipoSuscriptor = (String) requestBody.get("tipoSuscriptor");    
//        Integer tipoSucriptorInt = Integer.parseInt(tipoSuscriptor);
        String DptoCiudad = (String) requestBody.get("DptoCiudad");  
        Integer DptoCiudadInt = Integer.parseInt(DptoCiudad);
        String direccionPredio = (String) requestBody.get("direccionPredio");  	
        String telefonoFijo = (String) requestBody.get("telefonoFijo");  
        String telefonoCelular = (String) requestBody.get("telefonoCelular");  
        String email = (String) requestBody.get("email");  
//        String estrato = (String) requestBody.get("estrato"); 
//        Integer idEstracto = Integer.parseInt(estrato);
        Double promedio = 1.0;
	     
		    // Obtenemos la fecha y hora actual
	        Date fechaActual = new Date();

	        // Formatear la fecha en el formato deseado
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String fechaActualFormateada = dateFormat.format(fechaActual);

	        // Convertir la fecha formateada a Timestamp
	        Timestamp fechaIngreso = Timestamp.valueOf(fechaActualFormateada + ":00");


	        Integer idEstracto = 0;
	
	        Integer tipoSucriptorInt = 1;
	        
	        System.out.println("nuid es " + nuid);
	        
	        // Ingresamos el nuevo tercero
	        tblTercerosRepo.actualizarTercero(nombreTercero, direccionPredio, direccionPredio, DptoCiudadInt, telefonoFijo, telefonoCelular, email, cero, idEstracto, ccNit, ceroString, 
	        		cero, cero, ceroString, fechaIngreso, fechaIngreso, nuid, tipoSucriptorInt, ceroString, 1, cero, promedio, cero, cero,  usuario.getIdLocal(), nuid, idTipoTercero);
		    
	        System.out.println("EMPLEADO ACTUALIZADO CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTercero", nombreTercero);
		    response.put("idTercero", nuid);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
}


