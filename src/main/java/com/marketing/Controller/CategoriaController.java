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
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Service.dbaquamovil.TblCategoriasService;

@Controller
public class CategoriaController {
	
	@Autowired
	TblCategoriasService tblCategoriasService;

	
	@GetMapping("/Categoria")
	public String Referencia(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /Referencia");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    

		    List<TblCategoriasDTO> NombresLineas = tblCategoriasService.ObtenerNombresLineas(usuario.getIdLocal());
		    
		    
		    model.addAttribute("NombresLineas", NombresLineas);
		    

			
			return "Categoria/Categoria";
			
		}

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
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /CrearCategoria");
		    
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
		    
		    List<TblCategoriasDTO> NombresLineas = tblCategoriasService.ObtenerNombresLineas(usuario.getIdLocal());
		    
		    
		    model.addAttribute("NombresLineas", NombresLineas);
		    

	    
			
			return "Categoria/CrearCategoria";
			
		}
		
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
	       
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
}
