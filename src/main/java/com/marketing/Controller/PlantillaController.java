package com.marketing.Controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Service.DBMailMarketing.MailPlantillaService;
import com.marketing.Service.DBMailMarketing.TblMailItemPlantillaService;
import com.marketing.Model.DBMailMarketing.TblMailItemPlantilla;

@Controller
public class PlantillaController {

	@Autowired
	TblMailItemPlantillaService tblMailItemPlantillaService;
	
	@Autowired
	MailPlantillaService mailPlantillaService;
	
	@PostMapping("/CrearPlantilla-post")
	public String crearPlantilla (HttpServletRequest request,@RequestParam(value = "xNombrePlantilla", required = false) String xNombrePlantilla,
			@RequestParam(value = "xItemPlantilla", required = false) ArrayList<Integer> xItemPlantilla,Model model){
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			if(xItemPlantilla ==null) {
				model.addAttribute("error", "No Selecciono ningun item, seleccione al menos uno.");
				model.addAttribute("url", "/CrearPlantilla");
				return "defaultError";
			}
			
			Integer xIdPlantilla= mailPlantillaService.maximaPlantilla();
			for(Integer xIdRequerimiento: xItemPlantilla) {
				
				mailPlantillaService.ingresarPlantilla(xIdRequerimiento, xIdPlantilla, xNombrePlantilla);
			}
			
			model.addAttribute("success", "Plantilla Ingresada Correctamente");
			model.addAttribute("url", "/");
			return "defaultSuccess";
		}
	}
	
	@GetMapping("/CrearPlantilla")
	public String mostrarPlantilla(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			
			ArrayList<TblMailItemPlantilla> xDatosPlantillas = tblMailItemPlantillaService.obtenerTodasLasPlantillas();
			model.addAttribute("xDatosPlantillas", xDatosPlantillas);
			return "Plantilla/CrearPlantilla";
		}
		
	}
}
