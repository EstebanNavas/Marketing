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
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.TblTercerosRutaDTO;
import com.marketing.Repository.dbaquamovil.TblTerceroEstractoRepo;
import com.marketing.Service.dbaquamovil.TblTerceroEstractoService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class EstratoController {
	
	 @Autowired
	 TblTerceroEstractoService tblTerceroEstractoService;
	 
	 @Autowired
	 TblTerceroEstractoRepo tblTerceroEstractoRepo;
	 
	 @Autowired
	 ControlDeInactividad controlDeInactividad;
	
	@GetMapping("/Estrato")
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

		    
		    
		    
		    List<TblTerceroEstracto> ListaEstratos =  tblTerceroEstractoService.ListaEstratosOrdenadorPorId(usuario.getIdLocal());
		    
		    model.addAttribute("ListaEstratos", ListaEstratos);
		    

			
			return "Estrato/Estrato";

	}
	
	
	@GetMapping("/CrearEstrato")
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

		    

			return "Estrato/CrearEstrato";

		
	}
	
	
	@PostMapping("/CrearEstrato-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearEstrato(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearEstrato");

	        // Obtenemos los datos del JSON recibido
	        String descripcion = (String) requestBody.get("descripcion");
	        
	     

	        //Obtenemos el maximo idEstrato
	        Integer MaximoIdEstrato = tblTerceroEstractoService.maximoIdEstrato(usuario.getIdLocal()) + 1;
	        System.out.println("MaximoIdEstrato es : " + MaximoIdEstrato);
	        
	        // Ingresamos el nuevo Estrato
	        tblTerceroEstractoService.ingresarEstrato(usuario.getIdLocal(), MaximoIdEstrato, descripcion);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/TraerEstrato-Post")
	public ModelAndView TraerRutaPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerRuta-Post");

	    // Obtenemos los datos del JSON recibido
	    String idEstrato = (String) requestBody.get("idEstrato");




	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerEstrato?idEstrato=" + idEstrato);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerEstrato")
	public String TraerEstrato(@RequestParam(name = "idEstrato", required = false) String idEstrato, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerEstrato con idPlu: " + idEstrato);
		
		
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
  
		    Integer idEstratoInt = Integer.parseInt(idEstrato);

		    
		    List<TblTerceroEstracto> Estrato =  tblTerceroEstractoService.ObtenerEstrato(usuario.getIdLocal(), idEstratoInt);
		    
		    for(TblTerceroEstracto E : Estrato) {

		    	
		    	model.addAttribute("xIdEstrato", E.getIdEstracto());
		    	model.addAttribute("xNombreEstrato", E.getNombreEstracto());


		    }

			
			return "Estrato/ActualizarEstrato";


	}
	
	@PostMapping("/ActualizarEstrato-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarEstrato(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    //System.out.println("SI ENTRÓ A  /ActualizarEstrato-Post");

	        // Obtenemos los datos del JSON recibido
	    String descripcion = (String) requestBody.get("descripcion");

	    
        String idEstrato = (String) requestBody.get("idEstrato");
        Integer idEstratoInt = Integer.parseInt(idEstrato);

        
	        
	        // Actualizamos la Estrato
        	tblTerceroEstractoRepo.actualizarEstrato(descripcion, usuario.getIdLocal(), idEstratoInt);
        	
		    
	        System.out.println("ESTRATO ACTUALIZADA CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}

}
