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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Projection.TblCiudadesDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Repository.dbaquamovil.TblPlusRepo;
import com.marketing.Service.dbaquamovil.TblCategoriasService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTerceroEstractoService;

@Controller
public class ReferenciaController {
	
	 @Autowired
	 TblCategoriasService tblCategoriasService;
	 
	 @Autowired
	 TblPlusService tblPlusService;
	 
	 @Autowired
	 TblTerceroEstractoService  tblTerceroEstractoService;
	 
	 @Autowired
	 TblPlusRepo tblPlusRepo;

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
		    List<TblTerceroEstracto> listaEstratos = tblTerceroEstractoService.obtenerEstracto(usuario.getIdLocal());
		    
		    
		    model.addAttribute("ListaCategorias", ListaCategorias);
		    model.addAttribute("listaEstratos", listaEstratos);
		    

	    
			
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
	        
	        String subsidioContribucion = (String) requestBody.get("subsidioContribucion");
	        Integer subsidioContribucionInt = Integer.parseInt(subsidioContribucion);

	        // Se ingresa el idLinea 1 que es de SERVICIO
	        Integer idLinea = 1;

	        //Obtenemos el maximo idPlu
	        Integer MaximoIdPlu = tblPlusService.maximoIdPlu(usuario.getIdLocal()) + 1;
	        
	        // Ingresamos La nueva referencia
	        tblPlusService.ingresarReferencia(usuario.getIdLocal(), MaximoIdPlu,  descripcion, lista1Int, ivaInt, tipoInt, estratoInt, TmaximoInt, categoriaInt, idLinea, subsidioContribucionInt);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("descripcion", descripcion);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	@PostMapping("/BuscarCategoria")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarCategoria(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarCategoria");

	        // Obtenemos los datos del JSON recibido
	        String categoria = (String) requestBody.get("categoria");
	        System.out.println("palabraClave desde /BuscarCategoria " + categoria);
	        Integer idCategoria = Integer.parseInt(categoria);

	        List<TblCategoriasDTO> ReferenciasPorcategoria = tblCategoriasService.ObtenerReferenciasPorCategoria(usuario.getIdLocal(), idCategoria);
	        System.out.println("La ReferenciasPorcategoria generada es:  " + ReferenciasPorcategoria );
	        
	        for(TblCategoriasDTO cate : ReferenciasPorcategoria ) {
	        	
	        	System.out.println("idPlud es " + cate.getIDPLU());
	        }

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("ReferenciasPorcategoria", ReferenciasPorcategoria);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	@PostMapping("/TraerReferencia-Post")
	public ModelAndView TraerSuscriptorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerReferencia-Post");

	    // Obtenemos los datos del JSON recibido
	    String idPlu = (String) requestBody.get("idPlu");



	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerReferencia?idPlu=" + idPlu);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerReferencia")
	public String TraerReferencia(@RequestParam(name = "idPlu", required = false) String idPlu, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerReferencia con idPlu: " + idPlu);
		
		Integer idTipoTercero = 1;
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /TraerReferencia");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    Integer idPluInt = Integer.parseInt(idPlu);

		    
		    List<TblCategoriasDTO> ReferenciaPorIdPlu = tblCategoriasService.ObtenerReferenciasPorIdPlu(usuario.getIdLocal(), idPluInt);
		    
		    for(TblCategoriasDTO referencia : ReferenciaPorIdPlu) {
		    	
		    	System.out.println("referencia nombre = " + referencia.getNombrePlu());
		    	model.addAttribute("xId", referencia.getIDPLU());
		    	model.addAttribute("xDescripcion", referencia.getNombrePlu());
		    	
		    	
		    	model.addAttribute("xLista1", referencia.getVrGeneral());
		    	model.addAttribute("xIva", referencia.getPorcentajeIva());
		    	model.addAttribute("xTipo", referencia.getIdTIPO());
		    	model.addAttribute("xTmaximo", referencia.getTopeMaximo());
		    	Integer idEstrato = referencia.getIdEstracto();
		    	model.addAttribute("idEstrato", idEstrato);
		    	model.addAttribute("xIdCategoria", referencia.getIdCategoria());
		    	model.addAttribute("xSubsidioContribucion", referencia.getVrCostoIND());
		    	model.addAttribute("xRangoMaximo", referencia.getRangoMaximo());
		    	model.addAttribute("xcuentaContableDebito", referencia.getCuentaContableDebito());
		    	model.addAttribute("xcuentaContableCredito", referencia.getCuentaContableCredito());
		    	model.addAttribute("xcuentaRecaudoDebito", referencia.getCuentaRecaudoDebito());
		    	model.addAttribute("xcuentaRecaudoCredito", referencia.getCuentaRecaudoCredito());
		    	
		    	System.out.println("xcuentaContableDebito= " + referencia.getCuentaContableDebito());
		    	System.out.println("xcuentaContableCredito = " + referencia.getCuentaContableCredito());
		    	System.out.println("xcuentaRecaudoDebito = " + referencia.getCuentaRecaudoDebito());
		    	System.out.println("xcuentaRecaudoCredito = " + referencia.getCuentaRecaudoCredito());
		    	model.addAttribute("xAviso", referencia.getAviso());
		    	
		    }
		    
		    List<TblTerceroEstracto> listaEstratos = tblTerceroEstractoService.obtenerEstracto(usuario.getIdLocal());
		    
		    List<TblCategorias> ListaCategorias = tblCategoriasService.ListaCategorias(usuario.getIdLocal());
		    
		    
		    model.addAttribute("ListaCategorias", ListaCategorias);
		    model.addAttribute("listaEstratos", listaEstratos);

			
			return "Referencia/ActualizarReferencia";
			
		}

	}
	
	@PostMapping("/ActualizarReferencia-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarReferencia(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    System.out.println("SI ENTRÓ A  /ActualizarReferencia-Post");

	        // Obtenemos los datos del JSON recibido
	    String idPlu = (String) requestBody.get("idPlu");
	    Integer idPluInt = Integer.parseInt(idPlu);
	    
        String categoria = (String) requestBody.get("categoria");
        Integer categoriaInt = Integer.parseInt(categoria);

        
        String descripcion = (String) requestBody.get("descripcion");
        System.out.println("descripcion es: " + descripcion);
        
        // Obtenemos la lista de los nombres de las categorias del idLinea = 1
        List<String> NombresCategorias =  tblCategoriasService.ObtenerListaNombresCategorias(usuario.getIdLocal());
        System.out.println("NombresCategorias es: " + NombresCategorias);
        
        // Iteramos sobre la lista de nombres de categorías
        for (String nombreCategoria : NombresCategorias) {
            // Verificamos si la descripción contiene el nombre de la categoría
            if (descripcion.contains(nombreCategoria)) {
                // Eliminamos el nombre de la categoría de la descripción
                descripcion = descripcion.replace(nombreCategoria, "");
            }
        }

        System.out.println("La nueva descripción es: " + descripcion);
        
        String lista1 = (String) requestBody.get("lista1");
        Double lista1Int = Double.parseDouble(lista1);
        
        String iva = (String) requestBody.get("iva");
        Double ivaInt = Double.parseDouble(iva);
        
        String tipo = (String) requestBody.get("tipo");
        Integer tipoInt = Integer.parseInt(tipo);
        
        String Tmaximo = (String) requestBody.get("Tmaximo");
        Integer TmaximoInt = Integer.parseInt(Tmaximo);
        
        String estrato = (String) requestBody.get("estrato");
        Integer estratoInt = Integer.parseInt(estrato);

        
        String subsidioContribucion = (String) requestBody.get("subsidioContribucion");
        Double subsidioContribucionInt = Double.parseDouble(subsidioContribucion);
        
        String Rangomaximo = (String) requestBody.get("Rangomaximo");
        Integer RangomaximoInt = Integer.parseInt(Rangomaximo);
        
        String RecaudoDebito = (String) requestBody.get("RecaudoDebito");
        String FacturaDebito = (String) requestBody.get("FacturaDebito");
        String RecuadoCredito = (String) requestBody.get("RecuadoCredito");
        String FacturaCredito = (String) requestBody.get("FacturaCredito");
        String Aviso = (String) requestBody.get("Aviso");

        // Se ingresa el idLinea 1 que es de SERVICIO
        Integer idLinea = 1;
	       

	        
	        // Actualizamos la referencia
        	tblPlusRepo.actualizarReferencia(descripcion, lista1Int, ivaInt, tipoInt, categoriaInt, estratoInt, TmaximoInt, subsidioContribucionInt,
        			RangomaximoInt, Aviso, FacturaDebito, FacturaCredito, RecaudoDebito, RecuadoCredito, usuario.getIdLocal(), idPluInt );
		    
	        System.out.println("REFERENCIA ACTUALIZADA CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("idPlu", idPlu);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}

	
}













