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
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Repository.dbaquamovil.TblMedidoresRepo;
import com.marketing.Service.dbaquamovil.TblMedidoresService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class MedidoresController {
	
	@Autowired
	TblMedidoresService tblMedidoresService;
	
	@Autowired
	TblMedidoresRepo tblMedidoresRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@GetMapping("/Medidores")
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

		    List<TblMedidores> Medidores =  tblMedidoresService.ListaMedidores(usuario.getIdLocal());		    
		    model.addAttribute("Medidores", Medidores);
		    

			
			return "Medidores/Medidores";


	}
	
	@GetMapping("/CrearMedidor")
	public String CrearEstrato(HttpServletRequest request,Model model) {
		
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

		    

			return "Medidores/CrearMedidor";

		
	}
	
	
	@PostMapping("/CrearMedidor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearMedidor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearEstrato");

	        // Obtenemos los datos del JSON recibido
	        String descripcion = (String) requestBody.get("descripcion");
	        
	        String diametro = (String) requestBody.get("diametro");
	        Integer diametroInt = Integer.parseInt(diametro);
	        
	     

	        //Obtenemos el maximo idEstrato
	        Integer MaximoIdMedidor = tblMedidoresService.maximoIdMedidor(usuario.getIdLocal()) + 1;
	        System.out.println("MaximoIdMedidor es : " + MaximoIdMedidor);
	        
	        // Ingresamos el nuevo Medidor
	        tblMedidoresService.ingresarMedidor(usuario.getIdLocal(), MaximoIdMedidor, descripcion, diametroInt);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/TraerMedidor-Post")
	public ModelAndView TraerMedidorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerRuta-Post");

	    // Obtenemos los datos del JSON recibido
	    String idMedidor = (String) requestBody.get("idMedidor");




	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerMedidor?idMedidor=" + idMedidor);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerMedidor")
	public String TraerMedidor(@RequestParam(name = "idMedidor", required = false) String idMedidor, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerEstrato con idPlu: " + idMedidor);
		
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
		    Integer idMedidorInt = Integer.parseInt(idMedidor);

		    
		    List<TblMedidores> Medidor =  tblMedidoresService.ObtenerMedidor(usuario.getIdLocal(), idMedidorInt);
		    
		    for(TblMedidores M : Medidor) {

		    	
		    	model.addAttribute("xIdMedidor", M.getIdMedidor());
		    	model.addAttribute("xMarcaMedidor", M.getMarcaMedidor());
		    	model.addAttribute("xDiametro", M.getDiametro());


		    }

			
			return "Medidores/ActualizarMedidor";


	}
	
	
	@PostMapping("/ActualizarMedidor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarMedidor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    //System.out.println("SI ENTRÓ A  /ActualizarMedidor-Post");

	        // Obtenemos los datos del JSON recibido
	    String descripcion = (String) requestBody.get("descripcion");

	    
        String idMedidor = (String) requestBody.get("idMedidor");
        Integer idMedidorInt = Integer.parseInt(idMedidor);
        
        String diametro = (String) requestBody.get("diametro");
        Integer diametroInt = Integer.parseInt(diametro);

        
	        
	        // Actualizamos la Medidor
        	tblMedidoresRepo.actualizarMedidor(descripcion, diametroInt, usuario.getIdLocal(), idMedidorInt);
        	
		    
	        System.out.println("MEDIDOR ACTUALIZADA CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	

}
