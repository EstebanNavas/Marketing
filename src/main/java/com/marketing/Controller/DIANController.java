package com.marketing.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.ApiFacturacionElectronica;
import com.marketing.Model.DBMailMarketing.MailPlantilla;
import com.marketing.Model.dbaquamovil.CertificadoResponse;
import com.marketing.Model.dbaquamovil.ResolucionResponse;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Repository.dbaquamovil.TblDctosPeriodoRepo;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.ServiceApi.ApiCertificado;
import com.marketing.ServiceApi.ApiResolucion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
public class DIANController {
	
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
	TblDctosService  tblDctosService;
	
	@Autowired
	TblDctosPeriodoRepo tblDctosPeriodoRepo;

	@GetMapping("/Factura")
	public String Factura(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			
			int idLocal = 111; 
			int idTipoOrden = 9;
			
			// Obtenemos el ultimo idPeriodo donde estadoFEDctos sea = 0
			int xIdPeriodo = tblDctosPeriodoService.ObtenerIdPeriodo(usuario.getIdLocal());
			System.out.println("idPeriodo desde /Factura " + xIdPeriodo);
			
			model.addAttribute("xIdPeriodo", xIdPeriodo);
			
			List<Integer> cantFacturas = tblDctosService.ObtenerCantidadFacturas(usuario.getIdLocal(), idTipoOrden, xIdPeriodo);
			System.out.println("cantFacturas desde /Factura " + cantFacturas.size());
			
			model.addAttribute("xCantFacturas", cantFacturas.size());
             
             return "DIAN/Factura";
		}
	}
	
	
	@PostMapping("/Factura-post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> FacturaPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    
	    System.out.println("Si ingresó a /Factura-post");
	    
	    // Obtenemos los datos del JSON recibido
        String periodo = (String) requestBody.get("periodo");
        System.out.println("periodo desde la nueva /Factura-post " + periodo);
        
        int xPeriodoInt = Integer.parseInt(periodo);
        
        String facturas = (String) requestBody.get("facturas");
        System.out.println("facturas desde la nueva /Factura-post " + facturas);
	    
	    Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");

	    Map<String, Object> response = new HashMap<>();
	    
	    int idLocal = 111;
	    int idTipoOrden = 9;
	    // Obtenemos el Token del local 
	    String xToken = tblLocalesService.ObtenerToken(usuario.getIdLocal());
	    System.out.println("xToken en FacturaPost : " + xToken);
	    
	    String xPrefijo = tblLocalesService.ObtenerPrefijo(usuario.getIdLocal());
	    System.out.println("xPrefijo en FacturaPost : " + xPrefijo);
	    
	    
	   // Invocamos la API para validar el certificado y obtenemos el resultado de la validación
	    CertificadoResponse certificadoResponse = apiCertificado.consumirApi(xToken);
	    
	    
	    // Obtenemos el valor de IsValid
	    boolean isValid = certificadoResponse.isIs_valid();
	    System.out.println("isValid en FacturaPost : " + isValid);
	    
	    String expirationDate = certificadoResponse.getExpiration_date();
	    System.out.println("expirationDate DESPUES en FacturaPost : " + expirationDate);
	    
	    System.out.println("isValid DESPUES en FacturaPost : " + isValid);
	    // Validamos si isValid es true
	    if (isValid) {
	        System.out.println("isValid es true ");
	        
	        // Invocamos la API para validar la fecha de resolución y obtenemos el resultado de la validación
	        ResolucionResponse resolucionResponse = apiResolucion.consumirApi(xToken, xPrefijo);

	        // obtenemos el dateTo
	        String dateTo = resolucionResponse.getDate_to();
	        System.out.println("dateTo en FacturaPost : " + dateTo);


	        // Validamos si dateTo es nulo
	        if (dateTo != null) {
	            try {
	                LocalDate fechaApi = LocalDate.parse(dateTo);
	                LocalDate fechaActual = LocalDate.now();

	                System.out.println("fechaApi en FacturaPost : " + fechaApi);

	                // Validamos si la fecha de la resolución está vigente o no
	                if (fechaApi.isEqual(fechaActual) || fechaApi.isAfter(fechaActual)) {
	                    System.out.println("La fecha de la API es mayor o igual a la fecha actual.");
	                    
	                    // Invocamos el JAR para generar la factura electronica 
	                    apiFacturacionElectronica.ejecutarJar(usuario.getIdLocal(), idTipoOrden, xPeriodoInt);
	                    
	                    //Actualizamos el valor de estadoFEDctos de 0 a 2 
	                    tblDctosPeriodoRepo.actualizarIdPeriodo(idLocal, xPeriodoInt);
	                } else {
	                    System.out.println("La fecha de la API es anterior a la fecha actual.");
	                    response.put("errorFecha", "La fecha de la resolución expiró");
	                }
	            } catch (DateTimeParseException e) {
	                // Mostramos un mensaje se error si hay problema al convertir la fecha
	                System.out.println("Error al convertir la fecha: " + e.getMessage());
	                response.put("errorFecha", "Error al convertir la fecha");
	            }
	        } else {
	            // Si dateTo es nulo se muestra el mensaje
	            System.out.println("dateTo es nulo.");
	            response.put("errorFecha", "La fecha de la resolución es nula");
	        }
	    } else {
	    	
	    	// Si el Certificado es FALSE se muestra el mensaje
	        System.out.println("isValid es false ");
	        response.put("expirado", "Certificado expirado");
	    }

	    return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/NotasDB_CR")
	public String NotasDB_CR(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			
			int idLocal = 111; 
			int idTipoOrden = 29;
			
			// Obtenemos el ultimo idPeriodo donde estadoFENotas sea = 0
			int xIdPeriodo = tblDctosPeriodoService.ObtenerIdPeriodoNotas(usuario.getIdLocal());
			System.out.println("idPeriodo desde /NotasDB_CR " + xIdPeriodo);
			
			model.addAttribute("xIdPeriodo", xIdPeriodo);
			
			List<Integer> cantFacturas = tblDctosService.ObtenerCantidadFacturas(usuario.getIdLocal(), idTipoOrden, xIdPeriodo);
			System.out.println("cantNotas desde /NotasDB_CR " + cantFacturas.size());
			
			model.addAttribute("xCantFacturas", cantFacturas.size());
             
             return "DIAN/Notas";
		}
	}
	
	
	@PostMapping("/NotasDB_CR-post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> NotasDB_CRpost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    
	    System.out.println("Si ingresó a /NotasDB_CR-post");
	    
	    // Obtenemos los datos del JSON recibido
        String periodo = (String) requestBody.get("periodo");
        System.out.println("periodo desde la nueva /NotasDB_CR-post " + periodo);
        
        int xPeriodoInt = Integer.parseInt(periodo);
        
        String facturas = (String) requestBody.get("facturas");
        System.out.println("facturas desde la nueva /NotasDB_CR-post " + facturas);
	    
	    Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");

	    Map<String, Object> response = new HashMap<>();
	    
	    int idLocal = 111;
	    int idTipoOrden = 29;
	    // Obtenemos el Token del local 
	    String xToken = tblLocalesService.ObtenerToken(usuario.getIdLocal());
	    System.out.println("xToken en NotasDB_CR-post : " + xToken);
	    
	    String xPrefijo = tblLocalesService.ObtenerPrefijo(usuario.getIdLocal());
	    System.out.println("xPrefijo en NotasDB_CR-post : " + xPrefijo);

	    // Invocamos la API para validar el certificado y obtenemos el resultado de la validación
	    CertificadoResponse certificadoResponse = apiCertificado.consumirApi(xToken);
	    
	    
	    // Obtenemos el valor de IsValid
	    boolean isValid = certificadoResponse.isIs_valid();
	    System.out.println("isValid en NotasDB_CR-post : " + isValid);
	    
	    System.out.println("isValid DESPUES en NotasDB_CR-post : " + isValid);
	    // Validamos si isValid es true
	    if (isValid) {
	        System.out.println("isValid es true ");

	     // Invocamos la API para validar la fecha de resolución y obtenemos el resultado de la validación
	        ResolucionResponse resolucionResponse = apiResolucion.consumirApi(xToken, xPrefijo);

	        // obtenemos el dateTo
	        String dateTo = resolucionResponse.getDate_to();
	        System.out.println("dateTo en NotasDB_CR-post : " + dateTo);

	        // Validamos si dateTo es nulo
	        if (dateTo != null) {
	            try {
	                LocalDate fechaApi = LocalDate.parse(dateTo);
	                LocalDate fechaActual = LocalDate.now();

	                System.out.println("fechaApi en NotasDB_CR-post : " + fechaApi);

	                // Validamos si la fecha de la resolución está vigente o no
	                if (fechaApi.isEqual(fechaActual) || fechaApi.isAfter(fechaActual)) {
	                    System.out.println("La fecha de la API es mayor o igual a la fecha actual.");
	                    
	                    // Invocamos el JAR para generar la factura electronica 
	                    apiFacturacionElectronica.ejecutarJar(usuario.getIdLocal(), idTipoOrden, xPeriodoInt);
	                    
	                    //Actualizamos el valor de estadoFEDctos de 0 a 2 
	                    tblDctosPeriodoRepo.actualizarIdPeriodo(idLocal, xPeriodoInt);
	                } else {
	                    System.out.println("La fecha de la API es anterior a la fecha actual.");
	                    response.put("errorFecha", "La fecha de la resolución expiró");
	                }
	            } catch (DateTimeParseException e) {
	                // Mostramos un mensaje se error si hay problema al convertir la fecha
	                System.out.println("Error al convertir la fecha: " + e.getMessage());
	                response.put("errorFecha", "Error al convertir la fecha");
	            }
	        } else {
	            // Si dateTo es nulo se muestra el mensaje
	            System.out.println("dateTo es nulo.");
	            response.put("errorFecha", "La fecha de la resolución es nula");
	        }
	    } else {
	    	
	    	// Si el Certificado es FALSE se muestra el mensaje
	        System.out.println("isValid es false ");
	        response.put("expirado", "Certificado expirado");
	    }

	    return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/Certificado")
	public String Certificado(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			
			int idLocal = 111; 
			int idTipoOrden = 9;
			
			// Obtenemos el Token del local 
		    String xToken = tblLocalesService.ObtenerToken(usuario.getIdLocal());
		    System.out.println("xToken en /Certificado : " + xToken);
			
			  // Invocamos la API para validar el certificado y obtenemos el resultado de la validación
		    CertificadoResponse certificadoResponse = apiCertificado.consumirApi(xToken);
		    
		    
		    // Obtenemos el valor de IsValid
		    boolean isValid = certificadoResponse.isIs_valid();
		    System.out.println("isValid en /Certificado : " + isValid);
		    
		    if(isValid == true) {
		    	
		    	model.addAttribute("xIsValid", "SI");
		    }else {
		    	model.addAttribute("xIsValid", "NO");
		    	
		    }
		    
		    String expirationDate = certificadoResponse.getExpiration_date();
		    System.out.println("expirationDate en /Certificado : " + expirationDate);
			
			//model.addAttribute("xIsValid", isValid);
			model.addAttribute("xExpirationDate", expirationDate);
             
             return "DIAN/Certificado";
		}
	}
	
	
	@GetMapping("/Resolucion")
	public String Resolucion(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else {
			
			int idLocal = 111; 
			int idTipoOrden = 9;
			
			// Obtenemos el Token del local 
		    String xToken = tblLocalesService.ObtenerToken(usuario.getIdLocal());
		    System.out.println("xToken en /Resolucion : " + xToken);
		    
		    String xPrefijo = tblLocalesService.ObtenerPrefijo(usuario.getIdLocal());
		    System.out.println("xPrefijo en /Resolucion : " + xPrefijo);
			
		    // Invocamos la API para validar la fecha de resolución y obtenemos el resultado de la validación
	        ResolucionResponse resolucionResponse = apiResolucion.consumirApi(xToken, xPrefijo);

	        // obtenemos el dateTo
	        String dateTo = resolucionResponse.getDate_to();
	        System.out.println("dateTo en FacturaPost : " + dateTo);
	        
	        Integer from = resolucionResponse.getFrom();
	        Integer to = resolucionResponse.getTo();
	        System.out.println("from en FacturaPost : " + from);
	        System.out.println("to en FacturaPost : " + to);

		    
		    
		 
			
			model.addAttribute("xDateTo", dateTo);
			model.addAttribute("xFrom", from);
			model.addAttribute("xTo", to);
             
             return "DIAN/Resolucion";
		}
	}
}
