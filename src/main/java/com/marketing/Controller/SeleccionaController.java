package com.marketing.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.ApiFacturacionElectronica;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosPeriodoRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.ServiceApi.ApiCertificado;
import com.marketing.ServiceApi.ApiResolucion;
import com.marketing.Utilidades.ControlDeInactividad;


@Controller
public class SeleccionaController {
	
	@Autowired
	ApiCertificado  apiCertificado;
	
	@Autowired
	ApiResolucion apiResolucion;
	
	@Autowired
	TblLocalesService  tblLocalesService;
	
	@Autowired
	ApiFacturacionElectronica  apiFacturacionElectronica;
	
	@Autowired
	TblDctosPeriodoService  tblDctosPeriodoService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblDctosService  tblDctosService; 
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblDctosPeriodoRepo tblDctosPeriodoRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;

	@GetMapping("/Selecciona")
	public String Selecciona(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		Integer IdUsuario = usuario.getIdUsuario();
		
		
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
		
		//------------------------------------------------ VALIDA SUSCRIPTOR SLECCIONADO  -----------------------------------------------------------------
	           

			int xIdTipoTerceroCliente = 1;
	        int xIdTipoOrden = 9;

	        //
	        int estadoActivo = 9;
			
			 // Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        String strFechaVisita = fechaActual.format(formatter);
	        
	        System.out.println("strFechaVisita  es" + strFechaVisita);
	        

			
			String idCliente = tblAgendaLogVisitasService.seleccionaVisitaEstadoFecha(estadoActivo, strFechaVisita, IdUsuario);
			System.out.println("idCliente desde /Factura " + idCliente);
			
			int estadoSeleccionado = 0;
			
			if(idCliente != null) {
				
				System.out.println("idCliente en el if es" + idCliente);
				
				List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), idCliente, xIdTipoTerceroCliente);
				
				for(TblTerceros L : listaTercero) {
					
					model.addAttribute("xEstado", L.getEstado());
					model.addAttribute("xNuid", L.getIdCliente());
					model.addAttribute("xNombreTercero", L.getNombreTercero());
					model.addAttribute("xRuta", L.getIdRuta());
					
				}
				
				estadoSeleccionado = 1;
				
			}
			
			
			// Obtenemos el ultimo idPeriodo donde estadoFEDctos sea = 0
			List<TblDctosPeriodo> xListaPeriodos = tblDctosPeriodoService.ObtenerIdPeriodo(usuario.getIdLocal());
			System.out.println("xListaPeriodos desde /Selecciona " + xListaPeriodos);
			
			
			model.addAttribute("xListaPeriodos", xListaPeriodos);
			model.addAttribute("xEstadoSeleccionado", estadoSeleccionado);
			
             
             return "Cliente/Selecciona";
		
	}
	
	
	
	@PostMapping("/Seleccionar-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> SeleccionarPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    HttpSession session = request.getSession();

	    System.out.println("SI ENTRÓ A  /Seleccionar-Post");

	        // Obtenemos los datos del JSON recibido
	        String idCliente = (String) requestBody.get("idCliente");
	        System.out.println("idCliente desde /Seleccionar-Post " + idCliente);
	        
	        
	        // Obtenemos el IDLOG Máximo y le sumamos uno
	        Integer maximoIDLOGSum1 = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
	        System.out.println("maximoIDLOG en /GuardarLog: " + maximoIDLOGSum1);

	        
	        // Actualizamos los ESTADO Que sean = 9 a 1
	        tblAgendaLogVisitasRepo.actualizarEstadoA1(usuario.getIdLocal(), IdUsuario);

	        // Ingresamos el nuevo Log con ESTADO = 9
	        tblAgendaLogVisitasService.ingresarLog(usuario.getIdLocal(), maximoIDLOGSum1, idCliente, IdUsuario);
	        
	        String pantalla = (String) session.getAttribute("pantalla");
	        System.out.println("pantalla desde /Seleccionar-Post es " + pantalla);
	        
	        if(pantalla == null) {
	        	
	        	pantalla = "menuPrincipal";
	        }
	        
	        session.removeAttribute("pantalla"); //Se remueve de la session el valor de pantalla	
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("pantalla", pantalla);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	@PostMapping("/SeleccionarProveedor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> SeleccionarProveedor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    HttpSession session = request.getSession();

	    System.out.println("SI ENTRÓ A  /Seleccionar-Post");

	        // Obtenemos los datos del JSON recibido
	        String idCliente = (String) requestBody.get("idCliente");
	        System.out.println("idCliente desde /Seleccionar-Post " + idCliente);
	        
	        
	        // Obtenemos el IDLOG Máximo y le sumamos uno
	        Integer maximoIDLOGSum1 = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
	        System.out.println("maximoIDLOG en /GuardarLog: " + maximoIDLOGSum1);

	        
	        // Actualizamos los ESTADO Que sean = 9 a 1
	        tblAgendaLogVisitasRepo.actualizarEstadoA1(usuario.getIdLocal(), IdUsuario);

	        // Ingresamos el nuevo Log con ESTADO = 9
	        tblAgendaLogVisitasService.ingresarLog(usuario.getIdLocal(), maximoIDLOGSum1, idCliente, IdUsuario);
	        
	        String pantalla = (String) session.getAttribute("pantalla");
	        System.out.println("pantalla desde /SeleccionarProveedor-Post es " + pantalla);
	        
	        if(pantalla == null) {
	        	
	        	pantalla = "menuPrincipal";
	        }
	        
	        session.removeAttribute("pantalla"); //Se remueve de la session el valor de pantalla	
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("pantalla", pantalla);
		    return ResponseEntity.ok(response);
	   
	    
	}
	

}
