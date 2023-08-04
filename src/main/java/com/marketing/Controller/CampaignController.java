package com.marketing.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketing.CampaignTask;
import com.marketing.Model.DBMailMarketing.MailCampaign;
import com.marketing.Model.DBMailMarketing.MailPlantilla;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Service.DBMailMarketing.MailCampaignService;
import com.marketing.Service.DBMailMarketing.MailPlantillaService;

@Controller
public class CampaignController {
	
	@Autowired
	MailPlantillaService mailPlantillaService;
	
	@Autowired
	MailCampaignService mailCampaignService;
	
	@Autowired
	CampaignTask campaignTask;
	
	@PostMapping("/CrearCampaign-post")
	public String crearCampaignPost(HttpServletRequest request,
			@RequestParam(value = "nombreCampaign", required = false) String nombreCampaign,
			@RequestParam(value = "periodicidad", required = false) String periodicidad,
			@RequestParam(value = "idPlantilla", required = false) Integer idPlantilla,
			@RequestParam(value = "fecha", required = false) String fecha,
			@RequestParam(value = "textoMensaje", required = false) String textoMensaje,
			@RequestParam(value = "textoSMS", required = false) String textoSMS,
			@RequestParam(value = "subject", required = false) String subject,
			Model model) {
			
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			
			if (periodicidad.equals("BATCH")) {
				Integer xIdCampaign = mailCampaignService.maximaCampaign(usuario.getIdLocal(), sistema);
				mailCampaignService.ingresarCampaignBatch(usuario.getIdLocal(), sistema, xIdCampaign, nombreCampaign, periodicidad, idPlantilla, fecha, textoMensaje, textoSMS, subject);
				
				// Programar la ejecución de la campaña BATCH
				Date fechaEjecucion = parsearFecha(fecha);
				mailCampaignService.iniciarEjecucionProgramada(usuario.getIdLocal(), sistema, xIdCampaign, fechaEjecucion);
			}else {
				
				  System.out.println(" nombreCampaign " + nombreCampaign);        	    
	                System.out.println(" textoMensaje " + textoSMS);	
	                System.out.println(" usuario.getIdLocal() " + usuario.getIdLocal());
	                System.out.println(" sistema " + sistema);
	                
	                Integer xIdCampaign = mailCampaignService.maximaCampaign(usuario.getIdLocal(), sistema);
	                
	                System.out.println(" xIdCampaign " + xIdCampaign);  
	                mailCampaignService.ingresarCampaignOnline(usuario.getIdLocal(), sistema, xIdCampaign, nombreCampaign, periodicidad, idPlantilla, textoMensaje, textoSMS, subject);
			}
			
			model.addAttribute("success", "Campaña Ingresada Correctamente");
			model.addAttribute("url", "/");
			
			return "defaultSuccess";
		}
		
	}
	
	// Se convierte la fecha en un Date
	private Date parsearFecha(String fecha) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            return format.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

	@GetMapping("/CrearCampaign")
	public String CrearCampaign(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			ArrayList<MailPlantilla> xDatosPlantillas = mailPlantillaService.consultarTodasLasPlantillas();
			model.addAttribute("xDatosPlantillas", xDatosPlantillas);
			
			LocalDateTime xFechaInicial = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
			LocalDateTime xFechaCorrecta = LocalDateTime.now().plusMinutes(5).withSecond(0).withNano(0);
		     System.out.println(xFechaInicial);
             System.out.println(xFechaCorrecta);
             model.addAttribute("xFechaInicial", xFechaInicial);
             model.addAttribute("xFechaCorrecta", xFechaCorrecta);
             return "Campaign/CrearCampaign";
		}
	}
	
	//Mostrar vista EjecutarCampaign
	@GetMapping("/EjecutarCampaign")
	public String ejecutarCampaign(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			ArrayList<MailCampaign> xDatosCampaign = mailCampaignService.datosCampaignByIdLocalAndSistemaAndPeriodicidad(usuario.getIdLocal(), sistema, "ONLINE");
			
			model.addAttribute("xDatosCampaign", xDatosCampaign);
			
			return "Campaign/EjecutarCampaign";
		}
		
		
	}
	
	//Ejecutar campaña ONLINE
	@PostMapping("/EjecutarCampaign-post")
	public String ejecutarCampaignPost(HttpServletRequest request,
			@RequestParam(value = "xIdCampaign", required = false) Integer xIdCampaign, // Se obtiene la campaña del formulario 
			@RequestParam(value = "xIndicador", required = false) Integer xIndicador,
			@RequestParam(value = "xIdTipoOrden", required = false) Integer xIdTipoOrden,
			Model model) {
		
		System.out.println("La campaña obtenida es : " + xIdCampaign);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth"); // Se obtiene el local que está logueado
		String sistema=(String) request.getSession().getAttribute("sistema");  // Se obtiene el sistema del local logueado
		
		System.out.println("El usuario es : " + usuario);
		System.out.println("El sistema es : " + sistema);
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			
			Integer campaign = mailCampaignService.datosCampaignByIdLocalAndSistemaAndIdCampaign(usuario.getIdLocal(), sistema, xIdCampaign);
			System.out.println("La campaña  es : " + campaign);
			
			campaignTask.ejecutarJar(usuario.getIdLocal(), xIdCampaign);
			model.addAttribute("success", "Tu Campaña comenzo su ejecucion correctamente, para ver estado por favor revisar apartado de REPORTES");
			model.addAttribute("url", "/");
		return "defaultSuccess";
		}
		

	}
}
