package com.marketing.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Service.DBMailMarketing.MailPlantillaService;
import com.marketing.Service.DBMailMarketing.TblMailItemPlantillaService;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Model.DBMailMarketing.TblMailItemPlantilla;

@Controller
public class PlantillaController {

	@Autowired
	TblMailItemPlantillaService tblMailItemPlantillaService;
	
	@Autowired
	MailPlantillaService mailPlantillaService;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@PostMapping("/CrearPlantilla-post")
	public String crearPlantilla (HttpServletRequest request,@RequestParam(value = "xNombrePlantilla", required = false) String xNombrePlantilla,
			@RequestParam(value = "xItemPlantilla", required = false) ArrayList<Integer> xItemPlantilla,Model model){
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		
		// ----------------------------------------------------------- VALIDA INACTIVIDAD ------------------------------------------------------------
	    HttpSession session = request.getSession();
	    //Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
	    
	    @SuppressWarnings("unchecked")
		List<TblAgendaLogVisitas> UsuarioLogueado = (List<TblAgendaLogVisitas>) session.getAttribute("UsuarioLogueado");
	    
	    Integer estadoUsuario = 0;
	    

	        for (TblAgendaLogVisitas usuarioLog : UsuarioLogueado) {
	            Integer idLocalUsuario = usuarioLog.getIdLocal();
	            Integer idLogUsuario = usuarioLog.getIDLOG();
	            String sessionIdUsuario = usuarioLog.getSessionId();
	            
	            
	           estadoUsuario = controlDeInactividad.ingresa(idLocalUsuario, idLogUsuario, sessionIdUsuario);          
	        }
    
	           if(estadoUsuario.equals(2)) {
	        	   System.out.println("USUARIO INACTIVO");
	        	   return "redirect:/";
	           }
		
		//------------------------------------------------------------------------------------------------------------------------------------------

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
	
	@GetMapping("/CrearPlantilla")
	public String mostrarPlantilla(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		// ----------------------------------------------------------- VALIDA INACTIVIDAD ------------------------------------------------------------
	    HttpSession session = request.getSession();
	    //Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
	    
	    @SuppressWarnings("unchecked")
		List<TblAgendaLogVisitas> UsuarioLogueado = (List<TblAgendaLogVisitas>) session.getAttribute("UsuarioLogueado");
	    
	    Integer estadoUsuario = 0;
	    

	        for (TblAgendaLogVisitas usuarioLog : UsuarioLogueado) {
	            Integer idLocalUsuario = usuarioLog.getIdLocal();
	            Integer idLogUsuario = usuarioLog.getIDLOG();
	            String sessionIdUsuario = usuarioLog.getSessionId();
	            
	            
	           estadoUsuario = controlDeInactividad.ingresa(idLocalUsuario, idLogUsuario, sessionIdUsuario);          
	        }
    
	           if(estadoUsuario.equals(2)) {
	        	   System.out.println("USUARIO INACTIVO");
	        	   return "redirect:/";
	           }
		
		//------------------------------------------------------------------------------------------------------------------------------------------

			
			ArrayList<TblMailItemPlantilla> xDatosPlantillas = tblMailItemPlantillaService.obtenerTodasLasPlantillas();
			model.addAttribute("xDatosPlantillas", xDatosPlantillas);
			return "Plantilla/CrearPlantilla";

	}
	
	
	
	
	
	
}
