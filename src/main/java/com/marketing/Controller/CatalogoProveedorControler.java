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

@Controller
public class CatalogoProveedorControler {
	
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
	
	
	@GetMapping("/CatalogoProveedor")
	public String CatalogoProveedor(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /CatalogoSuscriptor");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    System.out.println("El usuario en session es: " + idUsuario);
		    

		    

			
			return "Catalogo/Proveedor";
			
		}

	}
	
	
	@GetMapping("/CrearProveedor")
	public String CrearProveedor(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /CrearProveedor");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    Integer idTipoTercero = 2;
		    
		    System.out.println("El usuario en session es: " + idUsuario);
		    System.out.println("usuario.getIdLocal() es: " + usuario.getIdLocal());
		    
		    
		    ArrayList<TblTipoCausaNota> TipoDocumento = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(16);
		    
		    ArrayList<TblTipoCausaNota> TipoPersona = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(17);
		    
		    ArrayList<TblTipoCausaNota> TipoRegimen = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(18);
		    
		    ArrayList<TblTipoCausaNota> RetencionFuente = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(19);
		    
		    List<TblCiudadesDTO> DepartamentosCiudades = tblCiudadesService.ListaCiudadesDepartamentos();
		    
		    Integer MaximoIdTercero = tblTercerosService.MaximoIdTercero(usuario.getIdLocal(), idTipoTercero) + 1;
		    
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
	    
			
			return "Catalogo/CrearProveedor";
			
		}
		
	}
	
	
	@PostMapping("/CrearProveedor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearSuscriptorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 2;
	    Integer cero = 0;
	    String ceroStirng = "0";


	    System.out.println("SI ENTRÓ A  /CrearProveedor-Post");

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
	        tblTercerosService.ingresarTerceroProveedor(usuario.getIdLocal(), ccNit, idTipoTercero, nombreTercero, direccion, direccion, DptoCiudadInt, telefonoFijo,
	        		telefonoCelular, email, cero, cero, ccNit, ceroStirng, cero, cero, ceroStirng, fechaIngreso, fechaIngreso, ceroStirng, tipoPersonaint, ceroStirng,
	        		digitoVerificacion, regimen, contacto, telefonoFax, tipoDocumento, reteFuenteInt );
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTercero", nombreTercero);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	@GetMapping("/TaertodosProveedores")
	public String TaertodosProveedores(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /TaertodosProveedores");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    System.out.println("El usuario en session es: " + idUsuario);
		    
		    List<TercerosDTO> ListaTercerosSuscriptores = tblTercerosService.ListaTercerosProveedor(usuario.getIdLocal());
		    
		    for(TercerosDTO tercero : ListaTercerosSuscriptores) {
		    	
		    	 System.out.println("tercero id : " + tercero.getIdTercero());
		    	
		    }
		    
		    
		    System.out.println("La lista de Susctiptores es: " + ListaTercerosSuscriptores);
	        
	        model.addAttribute("ListaTercerosSuscriptores", ListaTercerosSuscriptores);
		    
			
			return "Catalogo/TodosLosProveedores";
			
		}
		
	}
	

	
	
	@PostMapping("/BuscarProveedor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarProveedor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarProveedor");

	        // Obtenemos los datos del JSON recibido
	        String palabraClave = (String) requestBody.get("palabraClave");
	        System.out.println("palabraClave desde /BuscarProveedor " + palabraClave);

	        List<TercerosDTO> ListaBusqueda = tblTercerosService.BuscarTercerosProveedor(usuario.getIdLocal(), palabraClave);
	        System.out.println("La ListaBusqueda generada es:  " + ListaBusqueda );
	        
	        
	        
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
	
	@PostMapping("/TraerProveedor-Post")
	public ModelAndView TraerProveedorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ActualizarSuscriptor");

	    // Obtenemos los datos del JSON recibido
	    String idTercero = (String) requestBody.get("idTercero");
	    //Integer idTercero = Integer.parseInt(idTerceroString);


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerProveedor?idTercero=" + idTercero);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerProveedor")
	public String TraerProveedor(@RequestParam(name = "idTercero", required = false) String idTercero, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerProveedor con idTercero: " + idTercero);
		
		Integer idTipoTercero = 2;
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /TraerProveedor");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		
		    
		    
		    List<TblTerceros> InformacionTercero =  tblTercerosService.ObtenerInformacionTercero(usuario.getIdLocal(), idTercero, idTipoTercero);
		    
		    for(TblTerceros tercero : InformacionTercero) {
		    	
		    	System.out.println("xInformacionTercero nombre = " + tercero.getNombreTercero());
		    	model.addAttribute("xnombreTercero", tercero.getNombreTercero());
		    	model.addAttribute("xnuid", tercero.getIdCliente());
		    	model.addAttribute("xdigitoVerificacion", tercero.getDigitoVerificacion());
		    	model.addAttribute("xccNit", tercero.getCC_Nit());
		    	
		    	
		    	String tipoDocumento = tercero.getTipoIdTercero();
		    	

		    	
		    	
		    	if(tipoDocumento.equals("C")) {

		    		model.addAttribute("xtipoDocumento", 1);
		    	} else {

			    	Integer tipoDocumentoInt = Integer.parseInt(tipoDocumento);
			    	model.addAttribute("xtipoDocumento", tipoDocumentoInt);
		    		
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

	
	    
		    

			
			return "Catalogo/ActualizarProveedor";
			
		}

	}
	
	@PostMapping("/ActualizarProveedor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarProveedor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 2;
	    Integer cero = 0;
	    String ceroStirng = "0";

	    System.out.println("SI ENTRÓ A  /ActualizarSuscriptor-Post");

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
	        tblTercerosRepo.actualizarTerceroProveedor(nombreTercero, direccion, direccion, DptoCiudadInt, telefonoFijo, telefonoCelular, email, cero, cero, ccNit, ceroStirng, cero, cero, ceroStirng, fechaIngreso, fechaIngreso, ceroStirng, tipoPersonaint, ceroStirng, digitoVerificacionInt, regimen, telefonoFax, contacto,  usuario.getIdLocal(), nuid, idTipoTercero);
		    
	        System.out.println("PROVEEDOR ACTUALIZADO CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTercero", nombreTercero);
		    response.put("idTercero", nuid);
		    return ResponseEntity.ok(response);
	   
	    
	}

}
