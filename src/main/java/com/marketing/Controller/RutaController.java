package com.marketing.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Projection.TblTercerosRutaDTO;
import com.marketing.Repository.dbaquamovil.TblTercerosRutaRepo;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class RutaController {
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblTercerosRutaRepo tblTercerosRutaRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	
	@GetMapping("/Ruta")
	public String Ruta(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /Ruta");
		
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
 
		    
		    List<TblTercerosRutaDTO> RutasOperario = tblTercerosRutaService.RutasOperario(usuario.getIdLocal());
    
		    System.out.println("RutasOperario es " + RutasOperario);
		    model.addAttribute("RutasOperario", RutasOperario);
		    

			
			return "Ruta/Ruta";


	}
	
	
	@GetMapping("/CrearRuta")
	public String CrearRuta(HttpServletRequest request,Model model) {
		
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

		    List <CtrlusuariosDTO> operarios = ctrlusuariosService.obtenerOperarios(usuario.getIdLocal());
		    
	    
		    model.addAttribute("operarios", operarios);

			return "Ruta/CrearRuta";

	}
	
	@PostMapping("/CrearRuta-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearRuta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearRuta");

	        // Obtenemos los datos del JSON recibido
	        String operario = (String) requestBody.get("operario");
	        Integer idUsuario = Integer.parseInt(operario);

	        
	        String descripcion = (String) requestBody.get("descripcion");
	        
	     

	        //Obtenemos el maximo idRuta
	        Integer MaximoIdRuta = tblTercerosRutaService.maximoIdRuta(usuario.getIdLocal()) + 1;
	        System.out.println("MaximoIdRuta es : " + MaximoIdRuta);
	        
	        // Ingresamos La nueva referencia
	        tblTercerosRutaService.ingresarRuta(usuario.getIdLocal(), MaximoIdRuta, descripcion, idUsuario);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/TraerRuta-Post")
	public ModelAndView TraerRutaPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerRuta-Post");

	    // Obtenemos los datos del JSON recibido
	    String idRuta = (String) requestBody.get("idRuta");




	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerRuta?idRuta=" + idRuta);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerRuta")
	public String TraerRuta(@RequestParam(name = "idRuta", required = false) String idRuta, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerReferencia con idPlu: " + idRuta);
		
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

		    Integer idRutaInt = Integer.parseInt(idRuta);

		    
		    List<TblTercerosRutaDTO> Ruta = tblTercerosRutaService.ObtenerRuta(usuario.getIdLocal(), idRutaInt);
		    
		    for(TblTercerosRutaDTO R : Ruta) {

		    	
		    	model.addAttribute("xNombreRuta", R.getNombreRuta());
		    	model.addAttribute("xNombreCiclo", R.getNombreCiclo());
		    	model.addAttribute("xOrdenRuta", R.getOrdenRuta());
		    	model.addAttribute("xIdusuario", R.getIdUsuario());
		    	model.addAttribute("xIdRuta", R.getIdRuta());
		    	
		    	System.out.println("idRuta es : " + R.getIdRuta());

		    }
		    
		    List <CtrlusuariosDTO> operarios = ctrlusuariosService.obtenerOperarios(usuario.getIdLocal());
		    
		    
		    model.addAttribute("operarios", operarios);


			
			return "Ruta/ActualizarRuta";


	}
	
	@PostMapping("/ActualizarRuta-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarRutaPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    //System.out.println("SI ENTRÓ A  /ActualizarRuta-Post");

	        // Obtenemos los datos del JSON recibido
	    String operario = (String) requestBody.get("operario");
	    Integer idusuario = Integer.parseInt(operario);
	    
        String ordenRuta = (String) requestBody.get("ordenRuta");
        Integer ordenRutaInt = Integer.parseInt(ordenRuta);

        
        String descripcion = (String) requestBody.get("descripcion");
       // System.out.println("descripcion es: " + descripcion);
        
        String nombreCiclo = (String) requestBody.get("nombreCiclo");
        
	    String idRuta = (String) requestBody.get("idRuta");
	    Integer idRutaInt = Integer.parseInt(idRuta);
        

	       

	        
	        // Actualizamos la Ruta
	    	tblTercerosRutaRepo.actualizarRuta(descripcion, nombreCiclo, ordenRutaInt, idusuario, usuario.getIdLocal(), idRutaInt);
        	
		    
	        System.out.println("RUTA ACTUALIZADA CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	

}
