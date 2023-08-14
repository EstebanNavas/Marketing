package com.marketing.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Service.DBMailMarketing.TblMailCreditoService;
import com.marketing.Service.DBMailMarketing.TblMailMarketingReporteService;

@Controller
public class ConsultasController {
	
	@Autowired
	TblMailCreditoService tblMailCreditoService;
	
	@Autowired
    private TblMailMarketingReporteService tblMailMarketingReporteService;
	
	@GetMapping("/consultarCredito")
	public String mostrarConsultarCredito(HttpServletRequest request,Model model) {
		
		// Se obtiene el usuario logueado
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		if(usuario == null) {
			model.addAttribute("usuario",new Ctrlusuarios());
			return "redirect:/"; // Si no está autenticado se redirige a la pagina inicial
			
		}else {
			
			int idLocal = usuario.getIdLocal(); // Se obtiene el idLocal del objeto usuario
	        System.out.println("idLocal obtenido de usuario es " + idLocal);
	        
	     // Se obtiene el credito y debito del local
	        int credito = tblMailCreditoService.consultaCreditoLocal(idLocal);
	        int debito =  tblMailCreditoService.consultaDebitoLocal(idLocal);
	        
	        // Obtener información de Reportes
	        List<TblMailMarketingReporte> registros = tblMailMarketingReporteService.obtenerRegistrosPorIdLocal(idLocal);
	        
	        
	        // Se obtienen los creditos disponibles
	        Integer creditoDisponible = credito - debito;
	        System.out.println("El credito disponible es " + creditoDisponible);
	        
	        // Se agregan los valores a el modelo para que Thymeleaf pueda acceder a ellos
	        model.addAttribute("credito", credito);
	        model.addAttribute("debito", debito);
	        model.addAttribute("creditoDisponible", creditoDisponible);
	        model.addAttribute("registros", registros);
	        
	        return "Reporte/ConsultarCredito"; 
		}
	}
}
