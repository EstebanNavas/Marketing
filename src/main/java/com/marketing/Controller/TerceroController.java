package com.marketing.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TerceroController {

	
	@GetMapping("/CatalogoSuscriptor")
	public String CatalogoSuscriptor(HttpServletRequest request,Model model) {

		System.out.println("Entró a /CatalogoSuscriptor");
	    
	    HttpSession session = request.getSession();
	    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
	    
	    System.out.println("El usuario en session es: " + idUsuario);
        
        //model.addAttribute("xClave", xClave);
	    
		
		return "Catalogo/Suscriptor";
		
	}
}
