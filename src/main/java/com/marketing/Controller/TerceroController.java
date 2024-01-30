package com.marketing.Controller;

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
import com.marketing.Projection.TercerosDTO;
import com.marketing.Service.dbaquamovil.TblTercerosService;

@Controller
public class TerceroController {
	
	@Autowired 
	TblTercerosService tblTercerosService;

	
	
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
		    

		    //model.addAttribute("ListaBusqueda", ListaBusqueda);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("ListaBusqueda", ListaBusqueda);
		    return ResponseEntity.ok(response);
	   

	    
	}
	
	
	
}
