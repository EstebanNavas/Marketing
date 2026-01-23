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

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class RutaOrdenController {
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	
	@GetMapping("/RutaOrden")
	public String RutaOrden(HttpServletRequest request,Model model) {
		
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

		    
		    List<TblTercerosRuta> Rutas =  tblTercerosRutaService.ListaRutas(usuario.getIdLocal());
	    
		    model.addAttribute("Rutas", Rutas);
		    

			
			return "RutaOrden/RutaOrden";


	}
	
	@PostMapping("/BuscarRuta")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarRuta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarRuta");

	        // Obtenemos los datos del JSON recibido
	        String ruta = (String) requestBody.get("ruta");
	        System.out.println("palabraClave desde /BuscarCategoria " + ruta);
	        Integer idRuta = Integer.parseInt(ruta);

	        List<TercerosDTO>  Rutas = tblTercerosService.ListaTercerosRutas(usuario.getIdLocal(), idRuta);
	        System.out.println("La Rutas generada es:  " + Rutas );
	        
	        for(TercerosDTO rutaTercero : Rutas ) {
	        	
	        	System.out.println("getCC_Nit() es " + rutaTercero.getCC_Nit());
	        }

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("Rutas", Rutas);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/ActualizarRuta")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarRuta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /ActualizarRuta");

	        // Obtenemos los datos del JSON recibido
	        String ordenRuta = (String) requestBody.get("ordenRuta");
	        Integer ordenRutaInt = Integer.parseInt(ordenRuta);
	        System.out.println("ordenRuta desde /ActualizarRuta " + ordenRuta);

	        String idClienteString = (String) requestBody.get("idClienteString");
	        Integer idCliente = Integer.parseInt(idClienteString);
	        System.out.println("idClienteString desde /ActualizarRuta " + idClienteString);
	        
	        String idRutaString = (String) requestBody.get("idRutaString");
	        Integer idRuta = Integer.parseInt(idRutaString);
	        System.out.println("idRuta desde /ActualizarRuta " + idRuta);

	        //Actualizamos la ruta del tercero
	        tblTercerosRepo.actualizarOrdenRuta(ordenRutaInt, idCliente, usuario.getIdLocal());
	        
	        // Reordenamos la ruta
	        tblTercerosRepo.ReordenarRuta(usuario.getIdLocal(), idRuta);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");

		    return ResponseEntity.ok(response);
	   
	    
	}

	
	
	@GetMapping("/RutaOrdenPostCreacionSuscriptor")
	public String RutaOrdenPostCreacionSuscriptor(
	        @RequestParam(name = "idRuta", required = false) Integer idRuta,
	        HttpServletRequest request,
	        Model model) {
		
		 System.out.println("Ingreso a RutaOrdenPostCreacionSuscriptor "  );

	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");

	    // ---------------- VALIDACIÓN DE INACTIVIDAD ----------------
	    HttpSession session = request.getSession();

	    @SuppressWarnings("unchecked")
	    List<TblAgendaLogVisitas> UsuarioLogueado =
	            (List<TblAgendaLogVisitas>) session.getAttribute("UsuarioLogueado");

	    Integer estadoUsuario = 0;

	    for (TblAgendaLogVisitas usuarioLog : UsuarioLogueado) {
	        Integer idLocalUsuario = usuarioLog.getIdLocal();
	        Integer idLogUsuario = usuarioLog.getIDLOG();
	        String sessionIdUsuario = usuarioLog.getSessionId();

	        estadoUsuario = controlDeInactividad.ingresa(
	                idLocalUsuario, idLogUsuario, sessionIdUsuario);
	    }

	    if (estadoUsuario.equals(2)) {
	        return "redirect:/";
	    }
	    // ------------------------------------------------------------

	    List<TercerosDTO>  Rutas = tblTercerosService.ListaTercerosRutas(usuario.getIdLocal(), idRuta);
        System.out.println("La Rutas generada es:  " + Rutas );
        
        for(TercerosDTO rutaTercero : Rutas ) {
        	
        	System.out.println("getCC_Nit() es " + rutaTercero.getCC_Nit());
        }

	    model.addAttribute("xRutas", Rutas);
	    model.addAttribute("idRutaSeleccionada", idRuta);

	    return "RutaOrden/RutaOrdenPostCreacionSuscriptor";
	}
	
}
