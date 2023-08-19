package com.marketing.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Slice;

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
	public String mostrarConsultarCredito(HttpServletRequest request,
			@RequestParam(value = "pagina", defaultValue = "1") int pagina,
            @RequestParam(value = "tamanoPagina", defaultValue = "50") int tamañoPagina,Model model) {
		
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
	        //List<TblMailMarketingReporte> registros = tblMailMarketingReporteService.obtenerRegistrosPorIdLocal(idLocal);
	        Page<TblMailMarketingReporte> paginaRegistros = tblMailMarketingReporteService.obtenerRegistrosPorIdLocalPaginados(idLocal, pagina, tamañoPagina);
	        
	
	        
	        // Se obtienen los creditos disponibles
	        Integer creditoDisponible = credito - debito;
	        System.out.println("El credito disponible es " + creditoDisponible);
	        
	        // Se agregan los valores a el modelo para que Thymeleaf pueda acceder a ellos
	        model.addAttribute("credito", credito);
	        model.addAttribute("debito", debito);
	        model.addAttribute("creditoDisponible", creditoDisponible);
	       // model.addAttribute("registros",  registros);
	        model.addAttribute("registros",  paginaRegistros.getContent());
	        model.addAttribute("paginaActual", pagina);
	        model.addAttribute("totalPaginas", paginaRegistros.getTotalPages());
	  ;
	        
	        return "Reporte/ConsultarCredito"; 
		}
	}
}
