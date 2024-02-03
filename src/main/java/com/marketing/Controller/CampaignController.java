package com.marketing.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Service.DBMailMarketing.MailCampaignService;
import com.marketing.Service.DBMailMarketing.MailPlantillaService;
import com.marketing.Service.DBMailMarketing.TblMailCampaignClienteService;
import com.marketing.Service.dbaquamovil.TblTerceroEstractoService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Model.dbaquamovil.TblLocales;

@Controller
public class CampaignController {
	
	@Autowired
	MailPlantillaService mailPlantillaService;
	
	@Autowired
	MailCampaignService mailCampaignService;
	
	@Autowired
	CampaignTask campaignTask;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblTerceroEstractoService tblTerceroEstractoService;
	
	@Autowired
	TblMailCampaignClienteService tblMailCampaignClienteService;
	
	@PostMapping("/CrearCampaign-post")
	public String crearCampaignPost(HttpServletRequest request,
			@RequestParam(value = "nombreCampaign", required = false) String nombreCampaign,
			@RequestParam(value = "periodicidad", required = false) String periodicidad,
			@RequestParam(value = "idPlantilla", required = false) Integer idPlantilla,
			@RequestParam(value = "fecha", required = false) String fecha,
			@RequestParam(value = "textoMensaje", required = false) String textoMensaje,
			@RequestParam(value = "textoSMS", required = false) String textoSMS,
			@RequestParam(value = "tercerosSeleccionados", required = false) List<String> tercerosSeleccionados, // Obtenemos la lista de los idTercero
			@RequestParam(value = "subject", required = false) String subject,
			Model model) {
			
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			
			if (periodicidad.equals("BATCH")) {
				 //Integer xIdCampaignMAX = mailCampaignService.maximaCampaign(usuario.getIdLocal(), sistema) + 1 ;
				 
				//Obtenemos el idCampign máximo y si es null le asignamos el valor de 0
				 Integer xIdCampaignMAX = (mailCampaignService.maximaCampaign(usuario.getIdLocal(), sistema) != null) ?
							mailCampaignService.maximaCampaign(usuario.getIdLocal(), sistema) + 1 : 0;
				 boolean xOKImgresoBatch= mailCampaignService.ingresarCampaignBatch(usuario.getIdLocal(), sistema, xIdCampaignMAX, nombreCampaign, periodicidad, idPlantilla, fecha, textoMensaje, textoSMS, subject);
				
				//Validamos si la lista tercerosSeleccionados no es null y validamos si el metodo ingresarCampaignBatch fue exitoso
				if ((tercerosSeleccionados != null) && (xOKImgresoBatch)) {
		 
				    for (String idCliente : tercerosSeleccionados) { // Obtenemos cada idTercero seleccionado
				        System.out.println("ID Tercero seleccionado: " + idCliente);

				     // Llamamos al método para ingresar a TblMailCampaignCliente usando el maxIdCampaign obtenido
	                    tblMailCampaignClienteService.ingresarCampaignCliente(xIdCampaignMAX, usuario.getIdLocal(), idCliente, sistema);
	                    
	                    System.out.println("Se guardó idTercero " + idCliente + ":");
	                    System.out.println(" Se guardó idCampaign: " + xIdCampaignMAX);
	                    System.out.println(" Se guardó  idLocal: " + usuario.getIdLocal());
	                    System.out.println(" Se guardó idCliente: " + idCliente);
	                    System.out.println("Registro guardado en la base de datos");
				    }
				    
				}
				
				// Programar la ejecución de la campaña BATCH
				Date fechaEjecucion = parsearFecha(fecha);
				mailCampaignService.iniciarEjecucionProgramada(usuario.getIdLocal(), sistema, xIdCampaignMAX, fechaEjecucion);
			}else {
				
				//Obtenemos el idCampign máximo y si es null le asignamos el valor de 0
				Integer xIdCampaignMAX = (mailCampaignService.maximaCampaign(usuario.getIdLocal(), sistema) != null) ?
										mailCampaignService.maximaCampaign(usuario.getIdLocal(), sistema) + 1 : 0;
				 
				 System.out.println("xIdCampaignMAX es : " + xIdCampaignMAX);
				 boolean xOKImgresoOnline = mailCampaignService.ingresarCampaignOnline(usuario.getIdLocal(), sistema, xIdCampaignMAX, nombreCampaign, periodicidad, idPlantilla, textoMensaje, textoSMS, subject);
				 

				//Validamos si la lista tercerosSeleccionados no es null y validamos si el metodo ingresarCampaignOnline fue exitoso
				if ((tercerosSeleccionados != null) && (xOKImgresoOnline)) {
		 
				    for (String idCliente : tercerosSeleccionados) { // Obtenemos cada idTercero seleccionado
				        System.out.println("ID Tercero seleccionado: " + idCliente);

				     // Llamamos al método para ingresar a TblMailCampaignCliente usando el maxIdCampaign obtenido
	                    tblMailCampaignClienteService.ingresarCampaignCliente(xIdCampaignMAX, usuario.getIdLocal(), idCliente, sistema);
	                    
	                    System.out.println("Se guardó idTercero " + idCliente + ":");
	                    System.out.println(" Se guardó idCampaign: " + xIdCampaignMAX);
	                    System.out.println(" Se guardó  idLocal: " + usuario.getIdLocal());
	                    System.out.println(" Se guardó idCliente: " + idCliente);
	                    System.out.println("Registro guardado en la base de datos");
				    }
				    
				}
				 
				  System.out.println(" nombreCampaign " + nombreCampaign);        	    
	                System.out.println(" textoMensaje " + textoSMS);	
	                System.out.println(" usuario.getIdLocal() " + usuario.getIdLocal());
	                System.out.println(" sistema " + sistema);
	                System.out.println(" xIdCampaign " + xIdCampaignMAX); 

			}
			
			model.addAttribute("success", "Campaña Ingresada Correctamente");
			model.addAttribute("url", "menuPrincipal");
			
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
			
			//Obtenemos todas las plantillas
			ArrayList<MailPlantilla> xDatosPlantillas = mailPlantillaService.consultarTodasLasPlantillas();
			model.addAttribute("xDatosPlantillas", xDatosPlantillas);
			
			System.out.println("xDatosPlantillas en el controller es : " + xDatosPlantillas);
					
			
			List<TblTercerosProjectionDTO> registrosTerceros = tblTercerosService.registrosTercerosTelefonicos(usuario.getIdLocal());
			model.addAttribute("registrosTerceros", registrosTerceros);
			
			
			System.out.println("Número de registros obtenidos en el controller es : " + registrosTerceros.size());
			
			
			registrosTerceros.forEach(x ->System.out.println(x.toString()));
			
			
	
			//Obtenemos el nombre del local de la session 
			TblLocales local = (TblLocales) request.getSession().getAttribute("local");
			String nombreLocal = local.getRazonSocial();
			String saludo = "Saludos " + "" + nombreLocal;
	        model.addAttribute("saludo", saludo);
			System.out.println("El nombre del local desde cntroller campaign es : " + nombreLocal);
			
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
		
		System.out.println("El usuario es : " + usuario.getIdLocal());
		
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
			model.addAttribute("url", "menuPrincipal");
		return "defaultSuccess";
		}
		

	}
}
