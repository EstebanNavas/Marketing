package com.marketing.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMediosPago;
import com.marketing.Service.dbaquamovil.TblMediosPagoService;

@Controller
public class MediosDePagoController {
	
	@Autowired
	TblMediosPagoService tblMediosPagoService;
	
	@GetMapping("/MediosDePago")
	public String Referencia(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    
		    List<TblMediosPago> MediosDePago = tblMediosPagoService.ListaMediosDePago(usuario.getIdLocal());	    
		    model.addAttribute("MediosDePago", MediosDePago);
		    

			
			return "MediosDePago/MediosDePago";
			
		}

	}

}
