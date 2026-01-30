package com.marketing.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Repository.dbaquamovil.TblPlusRepo;
import com.marketing.Service.DBMailMarketing.TblPucAuxService;
import com.marketing.Service.dbaquamovil.TblCategoriasService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTerceroEstractoService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class ReporteReferenciasController {
	
	 @Autowired
	 TblCategoriasService tblCategoriasService;
	 
	 @Autowired
	 TblPlusService tblPlusService;
	 
	 @Autowired
	 TblTerceroEstractoService  tblTerceroEstractoService;
	 
	 @Autowired
	 TblPucAuxService tblPucAuxService;
	 
	 @Autowired
	 TblLocalesService tblLocalesService;
	 
	 @Autowired
	 TblPlusRepo tblPlusRepo;
	 
		@Autowired
		ControlDeInactividad controlDeInactividad;

	@GetMapping("/ReporteReferencias")
	public String ReporteReferencias(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		 System.out.println("INGRESÓ A Referencia");
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

		    
	           List<TblCategoriasDTO> TodasLasReferencias = tblCategoriasService.ObtenerTodasLasReferencias(usuario.getIdLocal());

			    model.addAttribute("TodasLasReferencias", TodasLasReferencias);
		    

			
			return "Reporte/ReporteReferencias";


	}

}
