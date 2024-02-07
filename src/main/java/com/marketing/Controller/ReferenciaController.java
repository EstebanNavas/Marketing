package com.marketing.Controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Projection.TblCiudadesDTO;
import com.marketing.Service.dbaquamovil.TblCategoriasService;
import com.marketing.Service.dbaquamovil.TblPlusService;

@Controller
public class ReferenciaController {
	
	 @Autowired
	 TblCategoriasService tblCategoriasService;
	 
	 @Autowired
	 TblPlusService tblPlusService;

	@GetMapping("/Referencia")
	public String Referencia(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /Referencia");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    List<TblCategorias> ListaCategorias = tblCategoriasService.ListaCategorias(usuario.getIdLocal());
		    
		    
		    model.addAttribute("ListaCategorias", ListaCategorias);
		    

			
			return "Referencia/Referencia";
			
		}

	}
	
	
	@GetMapping("/TodasLasReferencias")
	public String TodasLasReferencias(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /TodasLasReferencias");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    List<TblCategoriasDTO> TodasLasReferencias = tblCategoriasService.ObtenerTodasLasReferencias(usuario.getIdLocal());

		    model.addAttribute("TodasLasReferencias", TodasLasReferencias);
		    

			
			return "Referencia/TodasLasReferencias";
			
		}

	}
	
	
	@GetMapping("/CrearReferencia")
	public String CrearReferencia(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /CrearSuscriptor");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    Integer idTipoTercero = 1;
		    
		    System.out.println("El usuario en session es: " + idUsuario);
		    System.out.println("usuario.getIdLocal() es: " + usuario.getIdLocal());
		    


		    
		    // Obtenemos la fecha y hora actual
		    Date fechaRadicacion = new Date(); 

		    // Formatea la fecha en el formato deseado
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		    String fechaInstalacion = dateFormat.format(fechaRadicacion);

		    model.addAttribute("fechaInstalacion", fechaInstalacion);
		    
		    List<TblCategorias> ListaCategorias = tblCategoriasService.ListaCategorias(usuario.getIdLocal());
		    
		    
		    model.addAttribute("ListaCategorias", ListaCategorias);
		    

	    
			
			return "Referencia/CrearReferencia";
			
		}
		
	}
	
	
	@PostMapping("/CrearReferencia-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearReferenciaPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearReferenciaPost");

	        // Obtenemos los datos del JSON recibido
	        String categoria = (String) requestBody.get("categoria");
	        Integer categoriaInt = Integer.parseInt(categoria);
	        
	        String descripcion = (String) requestBody.get("descripcion");
	        
	        String lista1 = (String) requestBody.get("lista1");
	        Integer lista1Int = Integer.parseInt(lista1);
	        
	        String iva = (String) requestBody.get("iva");
	        Integer ivaInt = Integer.parseInt(iva);
	        
	        String tipo = (String) requestBody.get("tipo");
	        Integer tipoInt = Integer.parseInt(tipo);
	        
	        String Tmaximo = (String) requestBody.get("Tmaximo");
	        Integer TmaximoInt = Integer.parseInt(Tmaximo);
	        
	        String estrato = (String) requestBody.get("estrato");
	        Integer estratoInt = Integer.parseInt(estrato);


	        
	       

	        
	        // Ingresamos La nueva referencia
	        tblPlusService.ingresarReferencia(usuario.getIdLocal(), descripcion, lista1Int, ivaInt, tipoInt, estratoInt, TmaximoInt);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");

		    return ResponseEntity.ok(response);
	   
	    
	}
}
