package com.marketing.Controller;

import java.sql.Timestamp;
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
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Projection.TblTercerosRutaDTO;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblLocalesService;

@Controller
public class PeriodoController {

	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@GetMapping("/Periodo")
	public String Referencia(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /Referencia");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    
		    List <TblDctosPeriodo> listaPeriodos = tblDctosPeriodoService.ListaTotalPeriodos(usuario.getIdLocal());
		    model.addAttribute("listaPeriodos", listaPeriodos);
		    
		    if (!listaPeriodos.isEmpty()) {
		        // Obtenemos el primer elemento de la lista
		        TblDctosPeriodo primerPeriodo = listaPeriodos.get(0);
		        
		        int xIdPeriodo = primerPeriodo.getIdPeriodo(); //Obtenemos el idPeriodo
		        Timestamp xfechaInicioConsumo = primerPeriodo.getFechaInicial(); 
		        Timestamp xfechaFinConsumo = primerPeriodo.getFechaFinal(); 
		        Timestamp xfechaSinRecargo = primerPeriodo.getFechaSinRecargo(); 
		        Timestamp xfechaConRecargo = primerPeriodo.getFechaConRecargo(); 
	
		        model.addAttribute("xIdPeriodo", xIdPeriodo);
		        model.addAttribute("xfechaInicioConsumo", xfechaInicioConsumo);
		        model.addAttribute("xfechaFinConsumo", xfechaFinConsumo);
		        model.addAttribute("xfechaSinRecargo", xfechaSinRecargo);
		        model.addAttribute("xfechaConRecargo", xfechaConRecargo);
		    } else {
		        System.out.println("La lista de periodos está vacía.");
		    }
	    

			
			return "Periodo/Periodo";
			
		}

	}
	
	
	
	@PostMapping("/CrearPeriodo-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearRuta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearPeriodo");

	        // Obtenemos los datos del JSON recibido
//	        String nuevoPeriodo = (String) requestBody.get("nuevoPeriodo");
//	        Integer idPeriodo = Integer.parseInt(nuevoPeriodo);
//
//	        String nombre = (String) requestBody.get("nombre");
//	        String FechaInicioConsumo = (String) requestBody.get("FechaInicioConsumo");
//	        String fechaFinConsumo = (String) requestBody.get("fechaFinConsumo");
//	        String fechaSinRecargo = (String) requestBody.get("fechaSinRecargo");
//	        String fechaConrecargo = (String) requestBody.get("fechaConrecargo");
	        
	        
	        Integer idPeriodo = 202306;
	        
	     
	        //Obtenemos de tbllocales el periodoFactura
	        Integer xPeriodoFactura = tblLocalesService.ObtenerPeriodoFactura(usuario.getIdLocal());
	        
	        //Obtenemos la lista de los idPeriodos
	        List <String> xIdPeriodoLista = tblDctosPeriodoService.listaPeriodo(usuario.getIdLocal(), xPeriodoFactura, idPeriodo);
	        System.out.println("xIdPeriodoLista " + xIdPeriodoLista);
	        
	        //promd
	        tblTercerosRepo.actualizaPromedio(usuario.getIdLocal(), xIdPeriodoLista, xPeriodoFactura);
	        System.out.println("PromedioActualziado");
	        
	        //actualizaPromedioEstrato
	        tblTercerosRepo.actualizaPromedioEstrato(usuario.getIdLocal(), xIdPeriodoLista, xPeriodoFactura);
	        System.out.println("PromedioEstratoActualziado");
	        
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");


		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
