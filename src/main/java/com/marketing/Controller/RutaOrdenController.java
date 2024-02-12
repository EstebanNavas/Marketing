package com.marketing.Controller;

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
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;

@Controller
public class RutaOrdenController {
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	
	@GetMapping("/RutaOrden")
	public String RutaOrden(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /Referencia");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    List<TblTercerosRuta> Rutas =  tblTercerosRutaService.ListaRutas(usuario.getIdLocal());
	    
		    model.addAttribute("Rutas", Rutas);
		    

			
			return "RutaOrden/RutaOrden";
			
		}

	}
	
	@PostMapping("/BuscarRuta")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarRuta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarRuta");

	        // Obtenemos los datos del JSON recibido
	        String ruta = (String) requestBody.get("ruta");
	        System.out.println("palabraClave desde /BuscarCategoria " + ruta);
	        Integer idRuta = Integer.parseInt(ruta);

	        List<TercerosDTO>  Rutas = tblTercerosService.ListaTercerosRutas(usuario.getIdLocal(), idRuta);
	        System.out.println("La Rutas generada es:  " + Rutas );
	        
	        for(TercerosDTO rutaTercero : Rutas ) {
	        	
	        	System.out.println("getCC_Nit() es " + rutaTercero.getCC_Nit());
	        }

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("Rutas", Rutas);
		    return ResponseEntity.ok(response);
	   
	    
	}

}
