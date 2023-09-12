package com.marketing.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PqrController {
	
	@Autowired
	TblTipoCausaNotaService tblTipoCausaNotaService;
	
	@Autowired
	TblTercerosService tblTercerosService;

	@GetMapping("/GenerarPqr")
	public String generarPQR (HttpServletRequest request,Model model) {
		
        System.out.println("Si entro al controllador");
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		if(usuario==null) {
			model.addAttribute("usuario",new Ctrlusuarios());
			
			return "index";
		}else {
			
			ArrayList<TblTipoCausaNota> xtipoTabla10 = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(10);
			ArrayList<TblTipoCausaNota> xtipoTabla11 = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(11);
			ArrayList<TblTipoCausaNota> xtipoTabla12 = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(12);
			ArrayList<TblTipoCausaNota> xtipoTabla13 = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(13);
			ArrayList<TblTipoCausaNota> xtipoTabla14 = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(14);
			
			// Este parametro no es del local que está logueado, se debe de modificar
			List<TblTercerosProjectionDTO> xnombreTecerosEmpleados  = tblTercerosService.obtenerNombreTercerosEmpleados(142);
			
			//Obtenemos la lista de los clientes 
			List<TblTercerosProjectionDTO> xnombreTecerosClientes  = tblTercerosService.obtenerNombreTercerosClientes(100);
			
			// Convierte xnombreTecerosClientes en una cadena JSON
		    ObjectMapper objectMapper = new ObjectMapper();
		    
		    // Creamos una variable donde vamos a almacenar el Json
		    String xnombreTecerosClientesJson;
		    try {
		        xnombreTecerosClientesJson = objectMapper.writeValueAsString(xnombreTecerosClientes);
		    } catch (JsonProcessingException e) {
		        xnombreTecerosClientesJson = "[]"; // En caso de error se crea un [] vacio
		    }
			
			
			
			
			model.addAttribute("xtipoTabla10", xtipoTabla10);
			model.addAttribute("xtipoTabla11", xtipoTabla11);
			model.addAttribute("xtipoTabla12", xtipoTabla12);
			model.addAttribute("xtipoTabla13", xtipoTabla13);
			model.addAttribute("xtipoTabla14", xtipoTabla14);
			model.addAttribute("xnombreTecerosEmpleados", xnombreTecerosEmpleados);
			model.addAttribute("xnombreTecerosClientes", xnombreTecerosClientes);
			model.addAttribute("xnombreTecerosClientesJson", xnombreTecerosClientesJson);
			
			return "pqr/pqr";
		}
	}
	
	
	@PostMapping("/GenerarPqr-post")
	public String generarPqrPost (HttpServletRequest request,
			@RequestParam(value = "codigoUsuario", required = false) String codigoUsuario,
			@RequestParam(value = "telefono", required = false) String telefono,
			@RequestParam(value = "correoElectronico", required = false) String correoElectronico,
			@RequestParam(value = "direccion", required = false) String direccion,
			@RequestParam("fechaRadicacion") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaRadicacion,
			@RequestParam(value = "servicio", required = false) String servicio,
			@RequestParam(value = "tipoTramite", required = false) String tipoTramite,
			@RequestParam(value = "Causal", required = false) String Causal,
			@RequestParam(value = "detalleCausal", required = false) String detalleCausal,
			@RequestParam(value = "medioRecepcion", required = false) String medioRecepcion,
			@RequestParam(value = "reesponsable", required = false) String reesponsable,
			@RequestParam(value = "descripcionSolicitud", required = false) String descripcionSolicitud,
			@RequestParam(value = "subject", required = false) String subject,
			Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		if(usuario==null) {
			model.addAttribute("usuario",new Ctrlusuarios());
			
			return "index";
		}else { 
			 System.out.println("codigoUsuario:" + codigoUsuario);
			 System.out.println("telefono" + telefono);
			 System.out.println("correoElectronico" + correoElectronico);
			 System.out.println("direccion" + direccion);
			 System.out.println("fechaRadicacion" + fechaRadicacion);
			 
			
		}
		
		model.addAttribute("success", "PQR ingresado Correctamente");
		model.addAttribute("url", "/");
		
		return "defaultSuccess";
	}
	
	
	
	
	
	
	
	@GetMapping("/RespuestaPqr")
	public String RespuestaPQR (HttpServletRequest request,Model model) {
		
        System.out.println("Si entro al controllador");
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		if(usuario==null) {
			model.addAttribute("usuario",new Ctrlusuarios());
			
			return "index";
		}else {
			
			return "pqr/RespuestaPqr";
		}
	}
}
