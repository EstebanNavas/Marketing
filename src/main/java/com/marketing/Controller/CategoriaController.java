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
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Repository.dbaquamovil.TblCategoriasRepo;
import com.marketing.Service.dbaquamovil.TblCategoriasService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class CategoriaController {
	
	@Autowired
	TblCategoriasService tblCategoriasService;
	
	@Autowired
	TblCategoriasRepo tblCategoriasRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;

	
	@GetMapping("/Categoria")
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

		    List<TblCategoriasDTO> NombresLineas = tblCategoriasService.ObtenerNombresLineas(usuario.getIdLocal());
		    
		    
		    model.addAttribute("NombresLineas", NombresLineas);
		    

			
			return "Categoria/Categoria";


	}
	
	@PostMapping("/BuscarLinea")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarLinea(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarCategoria");

	        // Obtenemos los datos del JSON recibido
	        String linea = (String) requestBody.get("linea");
	        System.out.println("linea desde /BuscarLinea " + linea);
	        Integer idLinea = Integer.parseInt(linea);

	        
	        List<TblCategoriasDTO> CategoriasPorLinea = tblCategoriasService.ObtenerCategoriasPorLinea(usuario.getIdLocal(), idLinea);
	        
	        for(TblCategoriasDTO cate : CategoriasPorLinea ) {
	        	
	        	System.out.println("Nombre categoria es " + cate.getNombreCategoria());
	        	System.out.println("ID categoria es " + cate.getIdCategoria());
	        }

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("CategoriasPorLinea", CategoriasPorLinea);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@GetMapping("/CrearCategoria")
	public String CrearCategoria(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /CrearCategoria");
		
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

		    Integer idTipoTercero = 1;

		    // Obtenemos la fecha y hora actual
		    Date fechaRadicacion = new Date(); 

		    // Formatea la fecha en el formato deseado
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    String fechaInstalacion = dateFormat.format(fechaRadicacion);

		    model.addAttribute("fechaInstalacion", fechaInstalacion);
		    
		    List<TblCategoriasDTO> NombresLineas = tblCategoriasService.ObtenerNombresLineas(usuario.getIdLocal());
		    
		    
		    model.addAttribute("NombresLineas", NombresLineas);
		    

	    
			
			return "Categoria/CrearCategoria";

	}
	
	
	@PostMapping("/CrearCategoria-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearCategoriaPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearCategoriaPost");

	        // Obtenemos los datos del JSON recibido
	        String linea = (String) requestBody.get("linea");
	        Integer lineaInt = Integer.parseInt(linea);

	        
	        String descripcion = (String) requestBody.get("descripcion");
	        
	       

	        //Obtenemos el maximo idCategoria
	        Integer maximoIdCategoria = tblCategoriasService.maximoIdCategoria(usuario.getIdLocal(), lineaInt);
	        System.out.println("maximoIdCategoria es : " + maximoIdCategoria);
	        
	        // Ingresamos La nueva Categoria
	        tblCategoriasService.ingresarCategoria(usuario.getIdLocal(), maximoIdCategoria, descripcion, lineaInt);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@GetMapping("/TodasLasCategorias")
	public String TodasLasCategorias(HttpServletRequest request,Model model) {
		
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
		    List<TblCategoriasDTO> TodasLasCategorias = tblCategoriasService.ObtenerTodasLasCategorias(usuario.getIdLocal());

		    model.addAttribute("TodasLasCategorias", TodasLasCategorias);
		    

			
			return "Categoria/TodasLasCategorias";


	}
	
	
	@PostMapping("/TraerCategoria-Post")
	public ModelAndView TraerSuscriptorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerReferencia-Post");

	    // Obtenemos los datos del JSON recibido
	    String IdCategoria = (String) requestBody.get("IdCategoria");
	    String idLinea = (String) requestBody.get("idLinea");



	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerCategoria?IdCategoria=" + IdCategoria + "&idLinea=" + idLinea);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerCategoria")
	public String TraerCategoria(@RequestParam(name = "IdCategoria", required = false) String IdCategoria,
            @RequestParam(name = "idLinea", required = false) String idLinea,
            HttpServletRequest request, 
            Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerReferencia con IdCategoria: " + IdCategoria);
		
		
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

		    Integer IdCategoriaInt = Integer.parseInt(IdCategoria);
		    Integer idLineaInt = Integer.parseInt(idLinea);

		    
		    List<TblCategoriasDTO> Categoria = tblCategoriasService.ObtenerCategoria(usuario.getIdLocal(), idLineaInt, IdCategoriaInt);
		    
		    for(TblCategoriasDTO cate : Categoria) {
		    	
		    	System.out.println("cate xIdCategoria = " + cate.getIdCategoria());
		    	model.addAttribute("xNombre",  cate.getNombreCategoria());
		    	model.addAttribute("xIdCategoria",  cate.getIdCategoria());
		    	model.addAttribute("xIdLinea", cate.getIdLinea());
		    	model.addAttribute("xNombreLinea", cate.getNombreLinea());

		    	
		    	
		    }
		    
		    List<TblCategoriasDTO> NombresLineas = tblCategoriasService.ObtenerNombresLineas(usuario.getIdLocal());
		    
		    
		    model.addAttribute("NombresLineas", NombresLineas);

			
			return "Categoria/ActualizarCategoria";

	}
	
	
	@PostMapping("/ActualizarCategoria-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarCategoria(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    System.out.println("SI ENTRÓ A  /ActualizarReferencia-Post");

	        // Obtenemos los datos del JSON recibido
	    String idlinea = (String) requestBody.get("idlinea");
	    Integer idlineaInt = Integer.parseInt(idlinea);
	    
        String idCategoria = (String) requestBody.get("idCategoria");
        Integer idCategoriaInt = Integer.parseInt(idCategoria);

        String descripcionText = (String) requestBody.get("descripcionText");

	       

	        
	        // Actualizamos la Categoria
        	tblCategoriasRepo.actualizarCategoria(descripcionText, usuario.getIdLocal(), idlineaInt, idCategoriaInt);
		    
	        System.out.println("CATEGORIA ACTUALIZADA CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcionText", descripcionText);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}
}
