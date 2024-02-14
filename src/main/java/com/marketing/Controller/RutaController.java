package com.marketing.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
