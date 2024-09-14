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
import org.springframework.http.HttpStatus;
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
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblCiudadesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblMedidoresService;
import com.marketing.Service.dbaquamovil.TblTerceroEstractoService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Service.dbaquamovil.TblTercerosSuiService;

@Controller
public class CatalogoSuscriptorControler {

	
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
	TblTercerosSuiService TblTercerosSuiService;
	
	@Autowired 
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;

	
	
	@GetMapping("/CatalogoSuscriptor")
	public String CatalogoSuscriptor(HttpServletRequest request,Model model) {
		
	//	Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
			System.out.println("Entró a /CatalogoSuscriptor");
			
			
		    // ----------------------------------------------------------- VALIDA INACTIVIDAD ------------------------------------------------------------
		    HttpSession session = request.getSession();
		    //Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    @SuppressWarnings("unchecked")
			List<TblAgendaLogVisitas> UsuarioLogueado = (List<TblAgendaLogVisitas>) session.getAttribute("UsuarioLogueado");
		    
		    Integer estadoUsuario = 0;
		    

		        for (TblAgendaLogVisitas usuario : UsuarioLogueado) {
		            Integer idLocal = usuario.getIdLocal();
		            Integer idLog = usuario.getIDLOG();
		            String sessionId = usuario.getSessionId();
		            
		            
		            System.out.println("idLocal: " + idLocal);
		            System.out.println("idLog: " + idLog);
		            System.out.println("sessionId: " + sessionId);
		            
		            
		           estadoUsuario = controlDeInactividad.ingresa(idLocal, idLog, sessionId);          
		        }
        
		           if(estadoUsuario.equals(2)) {
		        	   System.out.println("USUARIO INACTIVO");
		        	   return "redirect:/";
		           }
		    

		    

			
			return "Catalogo/Suscriptor";
			


	}
	
	
	
	
	@GetMapping("/TaertodosSuscriptores")
	public String TaertodosSuscriptores(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
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
		


		    
		    List<TercerosDTO> ListaTercerosSuscriptores = tblTercerosService.ListaTercerosSuscriptor(usuario.getIdLocal());
		    
		    for(TercerosDTO tercero : ListaTercerosSuscriptores) {
		    	
		    	 System.out.println("tercero id : " + tercero.getIdTercero());
		    	
		    }
		    
		    
		    System.out.println("La lista de Susctiptores es: " + ListaTercerosSuscriptores);
	        
	        model.addAttribute("ListaTercerosSuscriptores", ListaTercerosSuscriptores);
		    
			
			return "Catalogo/TodosLosSuscriptores";
			

		
	}
	
	
	@PostMapping("/BuscarSuscriptor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarSuscriptor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarSuscriptor");

	        // Obtenemos los datos del JSON recibido
	        String palabraClave = (String) requestBody.get("palabraClave");
	        System.out.println("palabraClave desde /BuscarSuscriptor " + palabraClave);

//	        List<TercerosDTO> ListaBusqueda = tblTercerosService.BuscarTercerosSuscriptor(usuario.getIdLocal(), palabraClave);
//	        System.out.println("La ListaBusqueda generada es:  " + ListaBusqueda );
	        
	        
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
	
	
	
	
	@GetMapping("/CrearSuscriptor")
	public String CrearSuscriptor(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		System.out.println("Entró a /CrearSuscriptor");
		
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
		    
		 

		    List<TblMedidoresMacro> ListaMedidoresMacro = tblMedidoresMacroService.ListaMedidoresMacro(usuario.getIdLocal());
		    System.out.println("ListaMedidoresMacro  es: " + ListaMedidoresMacro);
		    
		    List<TblMedidores> listaMedidores = tblMedidoresService.ListaMedidores(usuario.getIdLocal());
		    
		    List<TblTerceroEstracto> listaEstratos = tblTerceroEstractoService.obtenerEstracto(usuario.getIdLocal());
		    
		    List<TblTercerosRuta> listaRutas = tblTercerosRutaService.ListaRutas(usuario.getIdLocal());
		    
		    ArrayList<TblTipoCausaNota> TipoSuscriptor = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(15);
		    
		    List<TblCiudadesDTO> DepartamentosCiudades = tblCiudadesService.ListaCiudadesDepartamentos();
		    
		    Long MaximoIdTercero = tblTercerosService.MaximoIdTercero(usuario.getIdLocal(), idTipoTercero) + 1;
		    
		    
		    // Obtenemos la fecha y hora actual
		    Date fechaRadicacion = new Date(); 

		    // Formatea la fecha en el formato deseado
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    String fechaInstalacion = dateFormat.format(fechaRadicacion);

		    model.addAttribute("fechaInstalacion", fechaInstalacion);
		    
		    
		    
		    model.addAttribute("listaMedidores", listaMedidores);
	        model.addAttribute("ListaMedidoresMacro", ListaMedidoresMacro);
	        model.addAttribute("listaEstratos", listaEstratos);
	        model.addAttribute("listaRutas", listaRutas);
	        model.addAttribute("TipoSuscriptor", TipoSuscriptor);
	        model.addAttribute("DepartamentosCiudades", DepartamentosCiudades);
	        model.addAttribute("MaximoIdTercero", MaximoIdTercero);
	    
			
			return "Catalogo/CrearSuscriptor";

		
	}
	
	
	@PostMapping("/CrearSuscriptor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearSuscriptorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /BuscarSuscriptor");

	        // Obtenemos los datos del JSON recibido
	        String nombreTercero = (String) requestBody.get("nombreTercero");
	        String nuid = (String) requestBody.get("nuid");   
	        String codigoAlterno = (String) requestBody.get("codigoAlterno");  
	        String ccNit = (String) requestBody.get("ccNit");   
	        String tipoSuscriptor = (String) requestBody.get("tipoSuscriptor");    
	        Integer tipoSucriptorInt = Integer.parseInt(tipoSuscriptor);
	        String DptoCiudad = (String) requestBody.get("DptoCiudad");  
	        Integer DptoCiudadInt = Integer.parseInt(DptoCiudad);
	        String direccionPredio = (String) requestBody.get("direccionPredio");  
	        String direccionCobro = (String) requestBody.get("direccionCobro"); 
	        String telefonoFijo = (String) requestBody.get("telefonoFijo");  
	        String telefonoCelular = (String) requestBody.get("telefonoCelular");  
	        String email = (String) requestBody.get("email");  
	        String ruta = (String) requestBody.get("ruta"); 
	        Integer idRuta = Integer.parseInt(ruta);
	        String estrato = (String) requestBody.get("estrato"); 
	        Integer idEstracto = Integer.parseInt(estrato);
	        String numeroMedidor = (String) requestBody.get("numeroMedidor");    
	        String codigoCatastral = (String) requestBody.get("codigoCatastral");  
	        String matricula = (String) requestBody.get("matricula");  
	        String marcaDiametroMedidor = (String) requestBody.get("marcaDiametroMedidor"); 
	        Integer idMedidor = Integer.parseInt(marcaDiametroMedidor);
	        String macroMedidor = (String) requestBody.get("macroMedidor");
	        Integer idMacro = Integer.parseInt(macroMedidor);
	        String fechaInstalacion = (String) requestBody.get("fechaInstalacion");
	        String promedioSuscriptor = (String) requestBody.get("promedioSuscriptor");
	        Double promedioSuscriptorDouble = Double.parseDouble(promedioSuscriptor);
	     
		    // Obtenemos la fecha y hora actual
	        Date fechaActual = new Date();

	        // Formatear la fecha en el formato deseado
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String fechaActualFormateada = dateFormat.format(fechaActual);

	        // Convertir la fecha formateada a Timestamp
	        Timestamp fechaIngreso = Timestamp.valueOf(fechaActualFormateada + ":00");

	        
	        
	        String fechaRadicacionFormateada = "";
	        try {
	            // Convierte la cadena de fecha en formato "yyyy-MM-dd'T'HH:mm" a un objeto Date
	            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	            Date fechaRadicacionDate = inputDateFormat.parse(fechaInstalacion);

	            // Formatea la fecha
	            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            fechaRadicacionFormateada = outputDateFormat.format(fechaRadicacionDate);

	            System.out.println("fechaRespuesta en /GuardarTemporalPqr " + fechaRadicacionFormateada);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        
	        Timestamp fechaDeInstalacion = null;
	        
	        try {
	        // Convertimos la cadena fechaRadicacionFormateada en un objeto Timestamp
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date parsedDate = sdf.parse(fechaRadicacionFormateada);
	        fechaDeInstalacion = new Timestamp(parsedDate.getTime());
	        
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        
	        
	        int estadoEmail = 1;
	        
	        
	        // Validamos si el email está vacio y se pone el estadoEmail INACTIVO
	        if(email.equals("")) {
	        	System.out.println("email vacio");
	        	
	        	estadoEmail = 2;
	        	
	        }

	        
	        // Ingresamos el nuevo tercero
	        tblTercerosService.ingresarTercero(usuario.getIdLocal(), nuid, idTipoTercero, nombreTercero, direccionPredio, direccionCobro, DptoCiudadInt, telefonoFijo,
	        		telefonoCelular, email, idRuta, idEstracto, ccNit, numeroMedidor, idMedidor, idMacro, codigoCatastral, fechaIngreso, fechaDeInstalacion, codigoAlterno, tipoSucriptorInt, matricula, promedioSuscriptorDouble, estadoEmail );
		    
	       // Ingresamos el nuevo terceroSUI
	        TblTercerosSuiService.ingresarTerceroSui(usuario.getIdLocal(), nuid, idTipoTercero);
	        
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTercero", nombreTercero);
		    response.put("idTercero", nuid);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/TraerSuscriptor-Post")
	public ModelAndView TraerSuscriptorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ActualizarSuscriptor");

	    // Obtenemos los datos del JSON recibido
	    String idTercero = (String) requestBody.get("idTercero");
	    //Integer idTercero = Integer.parseInt(idTerceroString);


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerSuscriptor?idTercero=" + idTercero);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerSuscriptor")
	public String TraerSuscriptor(@RequestParam(name = "idTercero", required = false) String idTercero, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerSuscriptor con idTercero: " + idTercero);
		
		
		
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
		    	model.addAttribute("xcodigoAlterno", tercero.getCodigoAlterno());
		    	model.addAttribute("xccNit", tercero.getCC_Nit());
		    	
		    	model.addAttribute("xtipoSuscriptor", tercero.getTipoSuscriptor());
		    	model.addAttribute("xDptoCiudad", tercero.getIdDptoCiudad());
		    	model.addAttribute("xIdEstrato", tercero.getIdEstracto());
		    	//model.addAttribute("xIdEstrato", tercero.getTerceroEstracto().getIdEstracto());
		    	model.addAttribute("xIMedidor", tercero.getIdMedidor());
		    	model.addAttribute("xIMacro", tercero.getIdMacro());
		    	//model.addAttribute("xIRuta", tercero.getTerceroRuta().getIdRuta());
		    	model.addAttribute("xIRuta", tercero.getIdRuta());
		    	model.addAttribute("xEstado", tercero.getEstado());
		    	model.addAttribute("xEstadoCorte", tercero.getEstadoCorte());
		    	model.addAttribute("xEstadoEmail", tercero.getEstadoEmail());
		    	model.addAttribute("xEstadoWhatsApp", tercero.getEstadoWhatsApp() );
		    	
		    	model.addAttribute("xdireccionPredio", tercero.getDireccionTercero());
		    	model.addAttribute("xdireccionCobro", tercero.getDireccionCobro());
		    	model.addAttribute("xtelefonoFijo", tercero.getTelefonoFijo());
		    	model.addAttribute("xtelefonoCelular", tercero.getTelefonoCelular());
		    	model.addAttribute("xemail", tercero.getEmail());
		    	model.addAttribute("xnumeroMedidor", tercero.getNumeroMedidor());
		    	model.addAttribute("xmatricula", tercero.getMatricula());
		    	model.addAttribute("xfechaInstalacion", tercero.getFechaInstalacionMedidor());
		    	model.addAttribute("xcodigoCatastral", tercero.getCodigoCatastral());
		    	model.addAttribute("xpromedioEstrato", tercero.getPromedioEstrato());
		    	model.addAttribute("xpromedioSuscriptor", tercero.getPromedio());
		    	
		    	
		    }
		    
		    List<TblMedidoresMacro> ListaMedidoresMacro = tblMedidoresMacroService.ListaMedidoresMacro(usuario.getIdLocal());
		    System.out.println("ListaMedidoresMacro  es: " + ListaMedidoresMacro);
		    
		    List<TblMedidores> listaMedidores = tblMedidoresService.ListaMedidores(usuario.getIdLocal());
		    
		    List<TblTerceroEstracto> listaEstratos = tblTerceroEstractoService.obtenerEstracto(usuario.getIdLocal());
		    
		    List<TblTercerosRuta> listaRutas = tblTercerosRutaService.ListaRutas(usuario.getIdLocal());
		    
		    ArrayList<TblTipoCausaNota> TipoSuscriptor = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(15);
		    
		    ArrayList<TblTipoCausaNota> EstadoSuscriptor = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(3);
		    
		    ArrayList<TblTipoCausaNota> EstadoCorte = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(5);
		    
		    List<TblCiudadesDTO> DepartamentosCiudades = tblCiudadesService.ListaCiudadesDepartamentos();
		    
		    ArrayList<TblTipoCausaNota> EstadoEmail = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(6);
		    
		    ArrayList<TblTipoCausaNota> EstadoWhatsApp = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(21);
		    
		    model.addAttribute("listaMedidores", listaMedidores);
	        model.addAttribute("ListaMedidoresMacro", ListaMedidoresMacro);
	        model.addAttribute("listaEstratos", listaEstratos);
	        model.addAttribute("listaRutas", listaRutas);
	        model.addAttribute("TipoSuscriptor", TipoSuscriptor);
	        model.addAttribute("EstadoSuscriptor", EstadoSuscriptor);
	        model.addAttribute("EstadoCorte", EstadoCorte);
	        model.addAttribute("DepartamentosCiudades", DepartamentosCiudades);
	        model.addAttribute("EstadoEmail", EstadoEmail);
	        model.addAttribute("EstadoWhatsApp", EstadoWhatsApp);
	    
		    

			
			return "Catalogo/ActualizarSucriptor";


	}
	
	
	@PostMapping("/ActualizarSuscriptor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarSuscriptor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /ActualizarSuscriptor-Post");

	        // Obtenemos los datos del JSON recibido
	        String nombreTercero = (String) requestBody.get("nombreTercero");
	        String nuid = (String) requestBody.get("nuid");   
	        String codigoAlterno = (String) requestBody.get("codigoAlterno");  
	        String ccNit = (String) requestBody.get("ccNit");   
	        String tipoSuscriptor = (String) requestBody.get("tipoSuscriptor");
	        Integer tipoSuscriptorInt = Integer.parseInt(tipoSuscriptor);
	        String DptoCiudad = (String) requestBody.get("DptoCiudad");  
	        Integer DptoCiudadInt = Integer.parseInt(DptoCiudad);
	        String direccionPredio = (String) requestBody.get("direccionPredio");  
	        String direccionCobro = (String) requestBody.get("direccionCobro"); 
	        String telefonoFijo = (String) requestBody.get("telefonoFijo");  
	        String telefonoCelular = (String) requestBody.get("telefonoCelular");  
	        String email = (String) requestBody.get("email");  
	        String ruta = (String) requestBody.get("ruta"); 
	        Integer idRuta = Integer.parseInt(ruta);
	        String estrato = (String) requestBody.get("estrato"); 
	        Integer idEstracto = Integer.parseInt(estrato);
	        String numeroMedidor = (String) requestBody.get("numeroMedidor");    
	        String codigoCatastral = (String) requestBody.get("codigoCatastral");  
	        String matricula = (String) requestBody.get("matricula");  
	        String marcaDiametroMedidor = (String) requestBody.get("marcaDiametroMedidor"); 
	        Integer idMedidor = Integer.parseInt(marcaDiametroMedidor);
	        String macroMedidor = (String) requestBody.get("macroMedidor");
	        Integer idMacro = Integer.parseInt(macroMedidor);
	        String fechaInstalacion = (String) requestBody.get("fechaInstalacion");
	        String estadoSuscriptor = (String) requestBody.get("estadoSuscriptor");
	        Integer estadoSuscriptorInt = Integer.parseInt(estadoSuscriptor);
	        String estadoCorte = (String) requestBody.get("estadoCorte");
	        Integer estadoCorteInt = Integer.parseInt(estadoCorte);
	        String promedioSuscriptor = (String) requestBody.get("promedioSuscriptor");
	        Double promedioSuscriptorDoule = Double.parseDouble(promedioSuscriptor);
	        String estadoEmail = (String) requestBody.get("estadoEmail");
	        Integer estadoEmailInt = Integer.parseInt(estadoEmail);
	        String estadoWhatsApp = (String) requestBody.get("estadoWhatsApp");
	        Integer estadoWhatsAppInt = Integer.parseInt(estadoWhatsApp);
	     
		    // Obtenemos la fecha y hora actual
	        Date fechaActual = new Date();

	        // Formatear la fecha en el formato deseado
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String fechaActualFormateada = dateFormat.format(fechaActual);

	        // Convertir la fecha formateada a Timestamp
	        Timestamp fechaIngreso = Timestamp.valueOf(fechaActualFormateada + ":00");

	        
	        
	        System.out.println("email en /ActualizarSuscriptores  " + email);
	        System.out.println("estadoEmail en /ActualizarSuscriptores  " + estadoEmail);
	        
	        String fechaRadicacionFormateada = "";
	        try {
	            // Convierte la cadena de fecha en formato "yyyy-MM-dd'T'HH:mm" a un objeto Date
	            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	            Date fechaRadicacionDate = inputDateFormat.parse(fechaInstalacion);

	            // Formatea la fecha
	            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            fechaRadicacionFormateada = outputDateFormat.format(fechaRadicacionDate);


	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        
	        Timestamp fechaDeInstalacion = null;
	        
	        try {
	        // Convertimos la cadena fechaRadicacionFormateada en un objeto Timestamp
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date parsedDate = sdf.parse(fechaRadicacionFormateada);
	        fechaDeInstalacion = new Timestamp(parsedDate.getTime());
	        
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	        
	        
	        
	        // Validamos si el email está vacio y se pone el estadoEmail INACTIVO
	        if(email.equals("")) {
	        	System.out.println("email vacio");
	        	
	        	estadoEmailInt = 2;
	        	
	        }
	        

	        
	        // Ingresamos el nuevo tercero
	        tblTercerosRepo.actualizarTercero(nombreTercero, direccionPredio, direccionCobro, DptoCiudadInt, telefonoFijo, telefonoCelular, email, idRuta, idEstracto, ccNit,
	        		numeroMedidor, idMedidor, idMacro, codigoCatastral, fechaIngreso, fechaDeInstalacion, codigoAlterno, tipoSuscriptorInt, matricula, estadoSuscriptorInt, estadoCorteInt, promedioSuscriptorDoule, estadoEmailInt, estadoWhatsAppInt, usuario.getIdLocal(), nuid, idTipoTercero);
		    
	        System.out.println("SUSCRIPTOR ACTUALIZADO CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTercero", nombreTercero);
		    response.put("idTercero", nuid);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
}
