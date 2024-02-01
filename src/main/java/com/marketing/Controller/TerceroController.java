package com.marketing.Controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblCiudadesDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Service.dbaquamovil.TblCiudadesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblMedidoresService;
import com.marketing.Service.dbaquamovil.TblTerceroEstractoService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;

@Controller
public class TerceroController {
	
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

	
	
	@GetMapping("/CatalogoSuscriptor")
	public String CatalogoSuscriptor(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /CatalogoSuscriptor");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    System.out.println("El usuario en session es: " + idUsuario);
		    

		    

			
			return "Catalogo/Suscriptor";
			
		}

	
		
	}
	
	
	
	
	@GetMapping("/TaertodosSuscriptores")
	public String TaertodosSuscriptores(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /CatalogoSuscriptor");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    System.out.println("El usuario en session es: " + idUsuario);
		    
		    List<TercerosDTO> ListaTercerosSuscriptores = tblTercerosService.ListaTercerosSuscriptor(usuario.getIdLocal());
		    
		    for(TercerosDTO tercero : ListaTercerosSuscriptores) {
		    	
		    	 System.out.println("tercero id : " + tercero.getIdTercero());
		    	
		    }
		    
		    
		    System.out.println("La lista de Susctiptores es: " + ListaTercerosSuscriptores);
	        
	        model.addAttribute("ListaTercerosSuscriptores", ListaTercerosSuscriptores);
		    
			
			return "Catalogo/TodosLosSuscriptores";
			
		}
		
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

	        List<TercerosDTO> ListaBusqueda = tblTercerosService.BuscarTercerosSuscriptor(usuario.getIdLocal(), palabraClave);
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
	
	
	
	
	@GetMapping("/CrearSuscriptor")
	public String CrearSuscriptor(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /CrearSuscriptor");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    System.out.println("El usuario en session es: " + idUsuario);
		    System.out.println("usuario.getIdLocal() es: " + usuario.getIdLocal());
		    

		    List<TblMedidoresMacro> ListaMedidoresMacro = tblMedidoresMacroService.ListaMedidoresMacro(usuario.getIdLocal());
		    System.out.println("ListaMedidoresMacro  es: " + ListaMedidoresMacro);
		    
		    List<TblMedidores> listaMedidores = tblMedidoresService.ListaMedidores(usuario.getIdLocal());
		    
		    List<TblTerceroEstracto> listaEstratos = tblTerceroEstractoService.obtenerEstracto(usuario.getIdLocal());
		    
		    List<TblTercerosRuta> listaRutas = tblTercerosRutaService.ListaRutas(usuario.getIdLocal());
		    
		    ArrayList<TblTipoCausaNota> TipoSuscriptor = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(15);
		    
		    List<TblCiudadesDTO> DepartamentosCiudades = tblCiudadesService.ListaCiudadesDepartamentos();
		    
		    for(TblCiudadesDTO ciudad : DepartamentosCiudades) {
		    	
		    	 System.out.println("ciudad  es: " + ciudad.getDepartamentoCiudad());
		    	
		    	
		    }
		    
		    
		    model.addAttribute("listaMedidores", listaMedidores);
	        model.addAttribute("ListaMedidoresMacro", ListaMedidoresMacro);
	        model.addAttribute("listaEstratos", listaEstratos);
	        model.addAttribute("listaRutas", listaRutas);
	        model.addAttribute("TipoSuscriptor", TipoSuscriptor);
	        model.addAttribute("DepartamentosCiudades", DepartamentosCiudades);
	    
			
			return "Catalogo/CrearSuscriptor";
			
		}
		
	}
	
	
	@PostMapping("/CrearSuscriptor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearSuscriptorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarSuscriptor");

	        // Obtenemos los datos del JSON recibido
	        String nombreTercero = (String) requestBody.get("nombreTercero");
	        System.out.println("nombreTercero desde /CrearSuscriptor-Post " + nombreTercero);
	        
	        String nuid = (String) requestBody.get("nuid");
	        System.out.println("nuid desde /CrearSuscriptor-Post " + nuid);
	        
	        String codigoAlterno = (String) requestBody.get("codigoAlterno");
	        System.out.println("codigoAlterno desde /CrearSuscriptor-Post " + codigoAlterno);
	        
	        String ccNit = (String) requestBody.get("ccNit");
	        System.out.println("ccNit desde /CrearSuscriptor-Post " + ccNit);
	        
	        String tipoSuscriptor = (String) requestBody.get("tipoSuscriptor");
	        System.out.println("tipoSuscriptor desde /CrearSuscriptor-Post " + tipoSuscriptor);
	        
	        String DptoCiudad = (String) requestBody.get("DptoCiudad");
	        System.out.println("DptoCiudad desde /CrearSuscriptor-Post " + DptoCiudad);
	        
	        String direccionPredio = (String) requestBody.get("direccionPredio");
	        System.out.println("direccionPredio desde /CrearSuscriptor-Post " + direccionPredio);
	        
	        String direccionCobro = (String) requestBody.get("direccionCobro");
	        System.out.println("direccionCobro desde /CrearSuscriptor-Post " + direccionCobro);
	        
	        String telefonoFijo = (String) requestBody.get("telefonoFijo");
	        System.out.println("telefonoFijo desde /CrearSuscriptor-Post " + telefonoFijo);
	        
	        String telefonoCelular = (String) requestBody.get("telefonoCelular");
	        System.out.println("telefonoCelular desde /CrearSuscriptor-Post " + telefonoCelular);
	        
	        String email = (String) requestBody.get("email");
	        System.out.println("email desde /CrearSuscriptor-Post " + email);
	        
	        String ruta = (String) requestBody.get("ruta");
	        System.out.println("ruta desde /CrearSuscriptor-Post " + ruta);
	        
	        String estrato = (String) requestBody.get("estrato");
	        System.out.println("estrato desde /CrearSuscriptor-Post " + estrato);
	        
	        String numeroMedidor = (String) requestBody.get("numeroMedidor");
	        System.out.println("numeroMedidor desde /CrearSuscriptor-Post " + numeroMedidor);
	        
	        String codigoCatastral = (String) requestBody.get("codigoCatastral");
	        System.out.println("codigoCatastral desde /CrearSuscriptor-Post " + codigoCatastral);
	        
	        String matricula = (String) requestBody.get("matricula");
	        System.out.println("matricula desde /CrearSuscriptor-Post " + matricula);
	        
	        String marcaDiametroMedidor = (String) requestBody.get("marcaDiametroMedidor");
	        System.out.println("marcaDiametroMedidor desde /CrearSuscriptor-Post " + marcaDiametroMedidor);
	        
	        String macroMedidor = (String) requestBody.get("macroMedidor");
	        System.out.println("macroMedidor desde /CrearSuscriptor-Post " + macroMedidor);
	        
	        // Ingresamos el nuevo tercero
	        //tblTercerosService.ingresarTercero(0, 0, marcaDiametroMedidor, 0, 0, 0, macroMedidor, null);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
}
