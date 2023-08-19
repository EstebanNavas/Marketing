package com.marketing.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.marketing.Service.DBMailMarketing.TblMailItemPlantillaService;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;


@Controller
public class ItemController {
	
	@Autowired
	TblMailItemPlantillaService tblMailItemPlantillaService;
	

	
	@PostMapping("/CrearItem-post")
	public String CrearPlantilla(HttpServletRequest request,@RequestParam(value = "xFormato", required = false) String xFormato,
			@RequestParam(value = "xComentario", required = false) String xComentario
			,Model model) {
		
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}
		else {
			
			
			Integer xMaxIdItem= tblMailItemPlantillaService.obtenerMaximoIdRequerimiento();
			//realiza el insert del item
			tblMailItemPlantillaService.ingresarItemPlantilla(xMaxIdItem, xFormato, xComentario);
			
			
			model.addAttribute("success", "Item Ingresado Correctamente");
			model.addAttribute("url", "/");
			return "defaultSuccess";
		}
		
	}

	
	@GetMapping("/CrearItem")
	public String Mostrarplantilla(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}
		else {
			return "Plantilla/CrearItem";
		}
	}
}
