package com.marketing.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Repository.dbaquamovil.TblMedidoresMacroRepo;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class MedidoresMacroController {
	
	@Autowired
	TblMedidoresMacroService tblMedidoresMacroService;
	
	@Autowired
	TblMedidoresMacroRepo tblMedidoresMacroRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@GetMapping("/MedidoresMacro")
	public String Referencia(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
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

		    
		    List<TblMedidoresMacro> MedidoresMacro = tblMedidoresMacroService.ListaMedidoresMacro(usuario.getIdLocal());    
		    model.addAttribute("MedidoresMacro", MedidoresMacro);
		    

			
			return "MedidoresMacro/MedidoresMacro";

	}
	
	
	@GetMapping("/CrearMacroMedidor")
	public String CrearMacroMedidor(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
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

		    

			return "MedidoresMacro/CrearMacroMedidor";

	}
	
	
	@PostMapping("/CrearMacroMedidor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearMacroMedidorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearMacroMedidor");

	        // Obtenemos los datos del JSON recibido
	        String descripcion = (String) requestBody.get("descripcion");
	        
	        String diametro = (String) requestBody.get("diametro");
	        Integer diametroInt = Integer.parseInt(diametro);
	        
	     

	        //Obtenemos el maximo idEstrato
	        Integer MaximoIdMacroMedidor = tblMedidoresMacroService.maximoIdMacroMedidor(usuario.getIdLocal()) + 1 ;
	        System.out.println("MaximoIdMacroMedidor es : " + MaximoIdMacroMedidor);
	        
	        // Ingresamos el nuevo Medidor
	        tblMedidoresMacroService.ingresarMacroMedidor(usuario.getIdLocal(), MaximoIdMacroMedidor, descripcion, diametroInt);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/TraerMacroMedidor-Post")
	public ModelAndView TraerMacroMedidorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerRuta-Post");

	    // Obtenemos los datos del JSON recibido
	    String idMacro = (String) requestBody.get("idMacro");




	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerMacroMedidor?idMacro=" + idMacro);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerMacroMedidor")
	public String TraerMacroMedidor(@RequestParam(name = "idMacro", required = false) String idMacro, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerMacroMedidor con idMacro: " + idMacro);
		
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

		    
		    Integer idMacroInt = Integer.parseInt(idMacro);

		    
		    List<TblMedidoresMacro> Macro =  tblMedidoresMacroService.ObtenerMacroMedidor(usuario.getIdLocal(), idMacroInt);
		    
		    for(TblMedidoresMacro M : Macro) {

		    	
		    	model.addAttribute("xIdMacro", M.getIdMacro());
		    	model.addAttribute("xNombreMacro", M.getNombreMacro());
		    	model.addAttribute("xDiametro", M.getDiametro());


		    }

			
			return "MedidoresMacro/ActualizarMacroMedidor";


	}
	
	
	@PostMapping("/ActualizarMacroMedidor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarMacroMedidor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    //System.out.println("SI ENTRÓ A  /ActualizarMedidor-Post");

	        // Obtenemos los datos del JSON recibido
	    String descripcion = (String) requestBody.get("descripcion");

	    
        String idMacro = (String) requestBody.get("idMacro");
        Integer idMacroInt = Integer.parseInt(idMacro);
        
        String diametro = (String) requestBody.get("diametro");
        Integer diametroInt = Integer.parseInt(diametro);

        
	        
	        // Actualizamos el Macro 
        tblMedidoresMacroRepo.actualizarMacroMedidor(descripcion, diametroInt, usuario.getIdLocal(), idMacroInt);
        	
		    
	        System.out.println("MACRO ACTUALIZADO CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}

}
