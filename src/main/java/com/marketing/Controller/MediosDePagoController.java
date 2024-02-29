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
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMediosPago;
import com.marketing.Repository.dbaquamovil.TblMediosPagoRepo;
import com.marketing.Service.dbaquamovil.TblMediosPagoService;

@Controller
public class MediosDePagoController {
	
	@Autowired
	TblMediosPagoService tblMediosPagoService;
	
	@Autowired
	TblMediosPagoRepo tblMediosPagoRepo;
	
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
	
	
	@GetMapping("/CrearMedioDePago")
	public String CrearEstrato(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /CrearRuta");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    

			return "MediosDePago/CrearMedioDePago";
			
		}
		
	}
	
	
	@PostMapping("/CrearMedioDePago-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearMedioDePago(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearEstrato");

	        // Obtenemos los datos del JSON recibido
	        String descripcion = (String) requestBody.get("descripcion");
	        
	        String CuentaContable = (String) requestBody.get("CuentaContable");
	        
	        String CuentaCxC = (String) requestBody.get("CuentaCxC");
	        
	        String Convenio = (String) requestBody.get("Convenio");
	        
	        
	     

	        //Obtenemos el maximo idEstrato
	        Integer MaximoIdMedioDePago = tblMediosPagoService.maximoIdMediodDePago(usuario.getIdLocal()) + 1 ;
	        System.out.println("MaximoIdMedioDePago es : " + MaximoIdMedioDePago);
	        
	        // Ingresamos el nuevo Medidor
	        tblMediosPagoService.ingresarMedioDePago(usuario.getIdLocal(), MaximoIdMedioDePago, descripcion, CuentaContable, CuentaCxC, Convenio);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	@PostMapping("/TraerMedioDePago-Post")
	public ModelAndView TraerMedioDePago(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerRuta-Post");

	    // Obtenemos los datos del JSON recibido
	    String idMedio = (String) requestBody.get("idMedio");




	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerMedioDePago?idMedio=" + idMedio);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerMedioDePago")
	public String TraerMedioDePago(@RequestParam(name = "idMedio", required = false) String idMedio, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerEstrato con idPlu: " + idMedio);
		
		Integer idTipoTercero = 1;
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			//System.out.println("Entró a /TraerRuta");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    Integer idMedioInt = Integer.parseInt(idMedio);

		    
		    List<TblMediosPago> MedioDePago =  tblMediosPagoService.ObtenerMedioDePago(usuario.getIdLocal(), idMedioInt);
		    
		    for(TblMediosPago M : MedioDePago) {

		    	
		    	model.addAttribute("xIdMedio", M.getIdMedio());
		    	model.addAttribute("xNombreMedio", M.getNombreMedio());
		    	model.addAttribute("xCuentaContable", M.getCuentaContable());
		    	model.addAttribute("xCuentaCxC", M.getCuentaCxC());
		    	model.addAttribute("xIdConvenio", M.getIdConvenio());
		    	model.addAttribute("xTextoMedio", M.getTextoMedio());


		    }

			
			return "MediosDePago/ActualizarMedioDePago";
			
		}

	}
	
	
	@PostMapping("/ActualizarMedioDePago-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarMedioDePago(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    //System.out.println("SI ENTRÓ A  /ActualizarMedidor-Post");

	        // Obtenemos los datos del JSON recibido
	    String descripcion = (String) requestBody.get("descripcion");

	    
        String idMedio = (String) requestBody.get("idMedio");
        Integer idMedioInt = Integer.parseInt(idMedio);
        
        String CuentaContable = (String) requestBody.get("CuentaContable");
        String CuentaCxC = (String) requestBody.get("CuentaCxC");
        String Convenio = (String) requestBody.get("Convenio");
        String TextoMedio = (String) requestBody.get("TextoMedio");
  

        
	        
	        // Actualizamos la Medidor
        	tblMediosPagoRepo.actualizarMedioDePago(descripcion, CuentaContable, CuentaCxC, Convenio, TextoMedio, usuario.getIdLocal(), idMedioInt);
        	
		    
	        System.out.println("MEDIO DE PAGO ACTUALIZADO CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}

}
