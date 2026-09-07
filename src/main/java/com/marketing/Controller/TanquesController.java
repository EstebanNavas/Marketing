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

import com.marketing.Model.DBMailMarketing.IrcaLocalesTanque;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.IRCAlocalesTanquesDTO;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Projection.TblTercerosRutaDTO;
import com.marketing.Repository.DBMailMarketing.IrcaLocalesTanqueRepository;
import com.marketing.Repository.dbaquamovil.TblTercerosRutaRepo;
import com.marketing.Service.DBMailMarketing.IrcaLocalesTanqueService;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class TanquesController {
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	IrcaLocalesTanqueRepository ircaLocalesTanqueRepository;
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblTercerosRutaRepo tblTercerosRutaRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@Autowired
	IrcaLocalesTanqueService ircaLocalesTanqueService;
	
	
	@GetMapping("/Tanques")
	public String Ruta(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /Tanques");
		
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
 
		    
		    List<IrcaLocalesTanque> TanquesLocal = ircaLocalesTanqueService.obtenerTanquesPorLocal(usuario.getIdLocal());
    
		    System.out.println("RutasOperario es " + TanquesLocal);
		    model.addAttribute("TanquesLocal", TanquesLocal);
		    
		    

			
			return "tanques/Tanques";


	}
	
	
	@GetMapping("/CrearTanque")
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

		    

			return "tanques/CrearTanques";

		
	}
	
	
	@PostMapping("/CrearTanque-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearTanque(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearTanque");

	        // Obtenemos los datos del JSON recibido
	        String descripcion = (String) requestBody.get("nombreTanque");
	        
	        String largo = (String) requestBody.get("largo");
	        Double largoDou = Double.parseDouble(largo);
	        
	        String ancho = (String) requestBody.get("ancho");
	        Double anchoDou = Double.parseDouble(ancho);
	        
	        String profundidad = (String) requestBody.get("profundidad");
	        Double profundidadDou = Double.parseDouble(profundidad);
	     

	        //Obtenemos el maximo idEstrato
	        Integer MaximoIdTanque = ircaLocalesTanqueService.maximoIdTanque(usuario.getIdLocal()) + 1;
	        System.out.println("MaximoIdEstrato es : " + MaximoIdTanque);
	        
	        // Ingresamos el nuevo Estrato
	        ircaLocalesTanqueService.ingresarTanque(usuario.getIdLocal(), MaximoIdTanque, descripcion, largoDou, anchoDou, profundidadDou);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTanque", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/TraerTanques-Post")
	public ModelAndView TraerRutaPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerTanques-Post");

	    // Obtenemos los datos del JSON recibido
	    String idTanque = (String) requestBody.get("idTanque");




	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerTanques?idTanque=" + idTanque);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerTanques")
	public String TraerTanque(@RequestParam(name = "idTanque", required = false) String idTanque, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerReferencia con idPlu: " + idTanque);
		
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

		    Integer idTanqueInt = Integer.parseInt(idTanque);

		    
		    List<IrcaLocalesTanque> Tanques = ircaLocalesTanqueService.obtenerTanquesPorLocalTanque(usuario.getIdLocal(), idTanqueInt);
		    
		    for(IrcaLocalesTanque R : Tanques) {

		    	
		    	model.addAttribute("xnombreTanque", R.getNombreTanque());
		    	model.addAttribute("xLargo", R.getLargo());
		    	model.addAttribute("xAncho", R.getAncho());
		    	model.addAttribute("xProfundidad", R.getProfundo());
		    	model.addAttribute("xidTanque", R.getIdTanque());
		    	
		    	System.out.println("IdTanque es : " + R.getIdTanque());

		    }
		    
		    //List <CtrlusuariosDTO> operarios = ctrlusuariosService.obtenerOperarios(usuario.getIdLocal());
		    
		    
		    //model.addAttribute("operarios", operarios);


			
			return "tanques/ActualizarTanques";


	}
	
	
	@PostMapping("/ActualizarTanques-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarEstrato(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    //System.out.println("SI ENTRÓ A  /ActualizarEstrato-Post");

	        // Obtenemos los datos del JSON recibido
	    String descripcion = (String) requestBody.get("nombreTanque");

	    String largo = (String) requestBody.get("largo");
	    Double largoDou = Double.parseDouble(largo);
	    
	    String ancho = (String) requestBody.get("ancho");
	    Double anchoDou = Double.parseDouble(ancho);
	    
	    String profundidad = (String) requestBody.get("profundidad");
	    Double profundidadDou = Double.parseDouble(profundidad);
	    
        String idTanque = (String) requestBody.get("idTanque");
        Integer idTanqueInt = Integer.parseInt(idTanque);
        
        

        
	        
	        // Actualizamos la Estrato
        
        
        ircaLocalesTanqueRepository.actualizarTanque(descripcion, largoDou, anchoDou, profundidadDou, usuario.getIdLocal(), idTanqueInt);
        	
		    
	        System.out.println("ESTRATO ACTUALIZADA CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("nombreTanque", descripcion);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
}
