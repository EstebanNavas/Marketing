package com.marketing.Controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;




@Controller
public class InicioController {

	@GetMapping("/")
	public String inicio(HttpServletRequest request,Model model) {
		System.out.println("Si entro al controllador");
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		if(usuario==null) {
			model.addAttribute("usuario",new Ctrlusuarios());
			
			return "index";
		}else {
			LocalDate xFechaInicial = LocalDate.now();
			System.out.println(xFechaInicial.toString()+" 00:00:00.000");
			return "menuPrincipal";
		}
	
		

	}
	

}
