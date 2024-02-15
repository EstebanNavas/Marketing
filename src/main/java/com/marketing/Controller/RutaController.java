package com.marketing.Controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.TblTercerosRutaDTO;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;

@Controller
public class RutaController {
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	
	
	@GetMapping("/Ruta")
	public String Referencia(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /Referencia");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    
		    List<TblTercerosRutaDTO> RutasOperario = tblTercerosRutaService.RutasOperario(usuario.getIdLocal());
    
		    model.addAttribute("RutasOperario", RutasOperario);
		    

			
			return "Ruta/Ruta";
			
		}

	}
	
	
	@GetMapping("/CrearRuta")
	public String CrearRuta(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /CrearRuta");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    Integer idTipoTercero = 1;
		    
		    System.out.println("El usuario en session es: " + idUsuario);
		    System.out.println("usuario.getIdLocal() es: " + usuario.getIdLocal());
		    

		    List <CtrlusuariosDTO> operarios = ctrlusuariosService.obtenerOperarios(usuario.getIdLocal());
		    
	    
		    model.addAttribute("operarios", operarios);

			return "Ruta/CrearRuta";
			
		}
		
	}
	
	@PostMapping("/CrearRuta-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearRuta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearRuta");

	        // Obtenemos los datos del JSON recibido
	        String operario = (String) requestBody.get("operario");
	        Integer idUsuario = Integer.parseInt(operario);

	        
	        String descripcion = (String) requestBody.get("descripcion");
	        
	     

	        //Obtenemos el maximo idRuta
	        Integer MaximoIdRuta = tblTercerosRutaService.maximoIdRuta(usuario.getIdLocal()) + 1;
	        System.out.println("MaximoIdRuta es : " + MaximoIdRuta);
	        
	        // Ingresamos La nueva referencia
	        tblTercerosRutaService.ingresarRuta(usuario.getIdLocal(), MaximoIdRuta, descripcion, idUsuario);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	

}
