package com.marketing.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.marketing.ApiFacturacionElectronica;
import com.marketing.Model.DBMailMarketing.MailPlantilla;
import com.marketing.Model.dbaquamovil.CertificadoResponse;
import com.marketing.Model.dbaquamovil.ResolucionResponse;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Projection.ReporteFeDTO;
import com.marketing.Projection.TblDctosDTO4;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Repository.dbaquamovil.TblDctosPeriodoRepo;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.ServiceApi.ApiCertificado;
import com.marketing.ServiceApi.ApiResolucion;
import com.marketing.Utilidades.ControlDeInactividad;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

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
	
	@Autowired
	ControlDeInactividad controlDeInactividad;

	@GetMapping("/Factura")
	public String Factura(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
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

			// Obtenemos el ultimo idPeriodo donde estadoFEDctos sea = 0
			List<TblDctosPeriodo> xListaPeriodos = tblDctosPeriodoService.ObtenerIdPeriodo(usuario.getIdLocal());
			System.out.println("xListaPeriodos desde /Factura " + xListaPeriodos);
			
			model.addAttribute("xListaPeriodos", xListaPeriodos);
			
             
             return "DIAN/Factura";
	
	}
	
	
	
	@PostMapping("/BuscarFacturas")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarFacturas(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    int idTipoOrden = 9;

	    System.out.println("SI ENTRÓ A  /BuscarFacturas");

	        // Obtenemos los datos del JSON recibido
	        String Periodo = (String) requestBody.get("Periodo");
	        System.out.println("Periodo desde /BuscarFacturas " + Periodo);
	        Integer xIdPeriodo = Integer.parseInt(Periodo);

			List<Integer> cantFacturas = tblDctosService.ObtenerCantidadFacturas(usuario.getIdLocal(), idTipoOrden, xIdPeriodo);
			System.out.println("cantFacturas desde /Factura " + cantFacturas.size());

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("cantFacturas", cantFacturas.size());
		    return ResponseEntity.ok(response);
	   
	    
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
	    
//	    String xPrefijo = tblLocalesService.ObtenerPrefijo(usuario.getIdLocal());
//	    System.out.println("xPrefijo en FacturaPost : " + xPrefijo);
	    
	    String xIdResolucion = tblLocalesService.ObtenerIdResolucion(usuario.getIdLocal());
	    System.out.println("xIdResolucion es : " + xIdResolucion);
	    
	    //Obtenemos el idApi correspondiente
	    Integer idApi = tblLocalesService.ObtenerIdApi(usuario.getIdLocal());
	    
	    String ApiFE = "";

	    
	    switch (idApi) {
	    case 100:        
	    	ApiFE = "apisoenaccam.sh";
	        break; 
	        
	    case 1100:        
	    	ApiFE = "apiComercial.sh";
	        break; 	        
	        
	    case 120:
	    	ApiFE = "sudo nohup ./apisoenaccam.sh";
	        break;
	        
	    case 200:
	    	ApiFE = "apiSoenacESP.sh";
	        break;
	        
	    case 1200:        
	    	ApiFE = "apiComercial.sh";
	        break; 
	        
	    case 220:
	    	ApiFE = "sudo nohup ./apiSoenacESP.sh";
	        break;
	        
	    case 300:
	    	ApiFE = "apisoenacspd.sh";
	        break;
	        
	    case 320:
	    	ApiFE = "sudo nohup ./apisoenacspd.sh";
	        break;
	        
	    case 1300:
	    	ApiFE = "apiSPD.sh";
	        break;
	    
	    default:
	    	ApiFE = "";
	        break; 
	       }
	    
	    System.out.println("ApiFE es " + ApiFE);
	    
	    // Invocamos el JAR para generar la factura electronica 
        apiFacturacionElectronica.ejecutarJar(usuario.getIdLocal(), idTipoOrden, xPeriodoInt, ApiFE);
        
        //Actualizamos el valor de estadoFEDctos de 0 a 2 
        tblDctosPeriodoRepo.actualizarIdPeriodo(usuario.getIdLocal(), xPeriodoInt);
        
        response.put("envioOK", "OK");
	    
	    
	   // Invocamos la API para validar el certificado y obtenemos el resultado de la validación
//	    CertificadoResponse certificadoResponse = apiCertificado.consumirApi(xToken);
//	    
//	    
//	    // Obtenemos el valor de IsValid
//	    boolean isValid = certificadoResponse.isIs_valid();
//	    System.out.println("isValid en FacturaPost : " + isValid);
//	    
//	    
//	    String expirationDate = certificadoResponse.getExpiration_date();
//	    System.out.println("expirationDate DESPUES en FacturaPost : " + expirationDate);
//	    
//	    System.out.println("isValid DESPUES en FacturaPost : " + isValid);
//	    // Validamos si isValid es true
//	    if (isValid) {
//	        System.out.println("isValid es true ");
//	        
//	        String xExpirationDate = certificadoResponse.getExpiration_date();
//	        System.out.println("xExpirationDate essss:  " + xExpirationDate);
//	        
//	        // Obtenemosla fecha actual
//	        LocalDate xfechaActual = LocalDate.now();
//
//	        // Convierte la fecha de cadenas (String) a objetos LocalDate
//	        DateTimeFormatter fechaFormateada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//	        LocalDate fechaExpiracion = LocalDate.parse(xExpirationDate, fechaFormateada);
//
//	        // Calcula la diferencia en días
//	        long diferenciaEnDias = ChronoUnit.DAYS.between(xfechaActual, fechaExpiracion);
//
//	        System.out.println("Diferencia en días: " + diferenciaEnDias);
//	        
//	        // Realizamos las validaciones
//	        if (diferenciaEnDias < 5) {
//	            System.out.println("Certificado expira en menos de " + diferenciaEnDias + " días");
//	            
//	        } else if (diferenciaEnDias < 30) {
//	            System.out.println("Certificado próximo a expirar");
//	            
//	        } else {
//	            System.out.println("Certificado válido");
//	            
//	        }
//	        
//	        
//	        // Invocamos la API para validar la fecha de resolución y obtenemos el resultado de la validación
//	        ResolucionResponse resolucionResponse = apiResolucion.consumirApi(xToken, xIdResolucion);
//
//	        // obtenemos el dateTo
//	        String dateTo = resolucionResponse.getDate_to();
//	        System.out.println("dateTo en FacturaPost : " + dateTo);
//
//
//	        // Validamos si dateTo es nulo
//	        if (dateTo != null) {
//	            try {
//	                LocalDate fechaApi = LocalDate.parse(dateTo);
//	                LocalDate fechaActual = LocalDate.now();
//
//	                System.out.println("fechaApi en FacturaPost : " + fechaApi);
//
//	                // Validamos si la fecha de la resolución está vigente o no
//	                if (fechaApi.isEqual(fechaActual) || fechaApi.isAfter(fechaActual)) {
//	                    System.out.println("La fecha de la API es mayor o igual a la fecha actual.");
//	                    
//	                    // Invocamos el JAR para generar la factura electronica 
//	                    apiFacturacionElectronica.ejecutarJar(usuario.getIdLocal(), idTipoOrden, xPeriodoInt, ApiFE);
//	                    
//	                    //Actualizamos el valor de estadoFEDctos de 0 a 2 
//	                    tblDctosPeriodoRepo.actualizarIdPeriodo(usuario.getIdLocal(), xPeriodoInt);
//	                    
//	                    response.put("envioOK", "OK");
//	                } else {
//	                    System.out.println("La fecha de la API es anterior a la fecha actual.");
//	                    response.put("errorFecha", "La fecha de la resolución expiró");
//	                }
//	            } catch (DateTimeParseException e) {
//	                // Mostramos un mensaje se error si hay problema al convertir la fecha
//	                System.out.println("Error al convertir la fecha: " + e.getMessage());
//	                response.put("errorFecha", "Error al convertir la fecha");
//	            }
//	        } else {
//	            // Si dateTo es nulo se muestra el mensaje
//	            System.out.println("dateTo es nulo.");
//	            response.put("errorFecha", "La fecha de la resolución es nula");
//	        }
//	    } else {
//	    	
//	    	// Si el Certificado es FALSE se muestra el mensaje
//	        System.out.println("isValid es false ");
//	        response.put("expirado", "Certificado expirado");
//	    }

	    return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/NotasDB_CR")
	public String NotasDB_CR(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
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

			// Obtenemos el ultimo idPeriodo donde estadoFENotas sea = 0
			List<TblDctosPeriodo> ListaPeriodosNotas = tblDctosPeriodoService.ObtenerIdPeriodoNotas(usuario.getIdLocal());
			System.out.println("ListaPeriodosNotas desde /NotasDB_CR " + ListaPeriodosNotas);
			
			model.addAttribute("ListaPeriodosNotas", ListaPeriodosNotas);
			
//			List<Integer> cantFacturas = tblDctosService.ObtenerCantidadFacturas(usuario.getIdLocal(), idTipoOrden, xIdPeriodo);
//			System.out.println("cantNotas desde /NotasDB_CR " + cantFacturas.size());
//			
//			model.addAttribute("xCantFacturas", cantFacturas.size());
             
             return "DIAN/Notas";

	}
	
	
	@PostMapping("/BuscarNotas")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarNotas(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    int idTipoOrden = 29;

	        // Obtenemos los datos del JSON recibido
	        String Periodo = (String) requestBody.get("Periodo");
	        Integer xIdPeriodo = Integer.parseInt(Periodo);

			List<Integer> cantFacturas = tblDctosService.ObtenerCantidadFacturas(usuario.getIdLocal(), idTipoOrden, xIdPeriodo);
			System.out.println("cantFacturas desde /Factura " + cantFacturas.size());
			
			// Lista Notas
			List<TblDctosDTO4>  listaNotas = tblDctosService.listaFechaMapeoApi(usuario.getIdLocal(), idTipoOrden, xIdPeriodo);

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("cantFacturas", cantFacturas.size());
		    response.put("listaNotas", listaNotas);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	@PostMapping("/NotasDB_CR-post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> NotasDB_CRpost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    
	    System.out.println("Si ingresó a /NotasDB_CR-post");
	    
	    // Obtenemos los datos del JSON recibido
        String periodo = (String) requestBody.get("periodo");
        System.out.println("periodo desde la nueva /NotasDB_CR-post " + periodo);
        
        int xPeriodoInt = Integer.parseInt(periodo);
        
	    
	    Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");

	    Map<String, Object> response = new HashMap<>();
	    
	    int idLocal = 111;
	    int idTipoOrden = 29;
	    // Obtenemos el Token del local 
	    String xToken = tblLocalesService.ObtenerToken(usuario.getIdLocal());
	    System.out.println("xToken en NotasDB_CR-post : " + xToken);
	    
//	    String xPrefijo = tblLocalesService.ObtenerPrefijo(usuario.getIdLocal());
//	    System.out.println("xPrefijo en NotasDB_CR-post : " + xPrefijo);
	    
	    String xIdResolucion = tblLocalesService.ObtenerIdResolucion(usuario.getIdLocal());
	    System.out.println("xIdResolucion es : " + xIdResolucion);
	    
	    //Obtenemos el idApi correspondiente
	    Integer idApi = tblLocalesService.ObtenerIdApi(usuario.getIdLocal());
	    
	    String ApiFE = "";

	    
	    switch (idApi) {
	    case 100:        
	    	ApiFE = "apisoenaccam.sh";
	        break; 
	        
	    case 1100:        
	    	ApiFE = "apiComercial.sh";
	        break; 	
	        
	    case 120:
	    	ApiFE = "sudo nohup ./apisoenaccam.sh";
	        break;
	        
	    case 200:
	    	ApiFE = "apiSoenacESP.sh";
	        break;
	        
	    case 1200:        
	    	ApiFE = "apiComercial.sh";
	        break; 
	        
	    case 220:
	    	ApiFE = "sudo nohup ./apiSoenacESP.sh";
	        break;
	        
	    case 300:
	    	ApiFE = "apisoenacspd.sh";
	        break;
	        
	    case 320:
	    	ApiFE = "sudo nohup ./apisoenacspd.sh";
	        break;
	        
	    case 1300:
	    	ApiFE = "apiSPD.sh";
	        break;
	    
	    default:
	    	ApiFE = "";
	        break; 
	       }
	    
	    System.out.println("ApiFE es " + ApiFE);
	    
	    
	 // Invocamos el JAR para generar la factura electronica 
        apiFacturacionElectronica.ejecutarJar(usuario.getIdLocal(), idTipoOrden, xPeriodoInt, ApiFE);
        
        //Actualizamos el valor de estadoFEDctos de 0 a 2 
        tblDctosPeriodoRepo.actualizarIdPeriodo(usuario.getIdLocal(), xPeriodoInt);
        
        

	    // Invocamos la API para validar el certificado y obtenemos el resultado de la validación
//	    CertificadoResponse certificadoResponse = apiCertificado.consumirApi(xToken);
//	    
//	    
//	    // Obtenemos el valor de IsValid
//	    boolean isValid = certificadoResponse.isIs_valid();
//	    System.out.println("isValid en NotasDB_CR-post : " + isValid);
//	    
//	    System.out.println("isValid DESPUES en NotasDB_CR-post : " + isValid);
//	    // Validamos si isValid es true
//	    if (isValid) {
//	        System.out.println("isValid es true ");
//
//	     // Invocamos la API para validar la fecha de resolución y obtenemos el resultado de la validación
//	        ResolucionResponse resolucionResponse = apiResolucion.consumirApi(xToken, xIdResolucion);
//
//	        // obtenemos el dateTo
//	        String dateTo = resolucionResponse.getDate_to();
//	        System.out.println("dateTo en NotasDB_CR-post : " + dateTo);
//
//	        // Validamos si dateTo es nulo
//	        if (dateTo != null) {
//	            try {
//	                LocalDate fechaApi = LocalDate.parse(dateTo);
//	                LocalDate fechaActual = LocalDate.now();
//
//	                System.out.println("fechaApi en NotasDB_CR-post : " + fechaApi);
//
//	                // Validamos si la fecha de la resolución está vigente o no
//	                if (fechaApi.isEqual(fechaActual) || fechaApi.isAfter(fechaActual)) {
//	                    System.out.println("La fecha de la API es mayor o igual a la fecha actual.");
//	                    
//	                    // Invocamos el JAR para generar la factura electronica 
//	                    apiFacturacionElectronica.ejecutarJar(usuario.getIdLocal(), idTipoOrden, xPeriodoInt, ApiFE);
//	                    
//	                    //Actualizamos el valor de estadoFEDctos de 0 a 2 
//	                    tblDctosPeriodoRepo.actualizarIdPeriodo(usuario.getIdLocal(), xPeriodoInt);
//	                } else {
//	                    System.out.println("La fecha de la API es anterior a la fecha actual.");
//	                    response.put("errorFecha", "La fecha de la resolución expiró");
//	                }
//	            } catch (DateTimeParseException e) {
//	                // Mostramos un mensaje se error si hay problema al convertir la fecha
//	                System.out.println("Error al convertir la fecha: " + e.getMessage());
//	                response.put("errorFecha", "Error al convertir la fecha");
//	            }
//	        } else {
//	            // Si dateTo es nulo se muestra el mensaje
//	            System.out.println("dateTo es nulo.");
//	            response.put("errorFecha", "La fecha de la resolución es nula");
//	        }
//	    } else {
//	    	
//	    	// Si el Certificado es FALSE se muestra el mensaje
//	        System.out.println("isValid es false ");
//	        response.put("expirado", "Certificado expirado");
//	    }

	    return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/Certificado")
	public String Certificado(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
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
	
	
	@GetMapping("/Resolucion")
	public String Resolucion(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
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

			
			// Obtenemos el Token del local 
		    String xToken = tblLocalesService.ObtenerToken(usuario.getIdLocal());
		    System.out.println("xToken en /Resolucion : " + xToken);
		    
//		    String xPrefijo = tblLocalesService.ObtenerPrefijo(usuario.getIdLocal());
//		    System.out.println("xPrefijo en /Resolucion : " + xPrefijo);
		    
		    String xIdResolucion = tblLocalesService.ObtenerIdResolucion(usuario.getIdLocal());
		    System.out.println("xIdResolucion es : " + xIdResolucion);
			
		    // Invocamos la API para validar la fecha de resolución y obtenemos el resultado de la validación
	        ResolucionResponse resolucionResponse = apiResolucion.consumirApi(xToken, xIdResolucion);

	        // obtenemos el dateTo
	        String dateTo = resolucionResponse.getDate_to();
	        System.out.println("dateTo en FacturaPost : " + dateTo);
	        
	        Integer id = resolucionResponse.getId();
	        String prefix = resolucionResponse.getPrefix();
	        String resolution = resolucionResponse.getResolution();
	        Integer from = resolucionResponse.getFrom();
	        Integer to = resolucionResponse.getTo();
	        
	        System.out.println("from en FacturaPost : " + from);
	        System.out.println("to en FacturaPost : " + to);

		    
		    
		 
	        
	        model.addAttribute("xId", id);
			model.addAttribute("xPrefix", prefix);
			model.addAttribute("xResolution", resolution);
			model.addAttribute("xDateTo", dateTo);
			model.addAttribute("xFrom", from);
			model.addAttribute("xTo", to);
             
             return "DIAN/Resolucion";
		
	}
	
	@GetMapping("/ReporteFE")
	public String ReporteFE(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
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


			List <Integer> xListaPeriodos = tblDctosPeriodoService.ListaIdPeriodos(usuario.getIdLocal());
			System.out.println("xListaPeriodos es : " + xListaPeriodos);
			
			
			model.addAttribute("xListaPeriodos", xListaPeriodos);
             
             return "DIAN/ReporteFE";

	}
	
	@PostMapping("/ObtenerReportePeriodoFE")
    @ResponseBody
    public ResponseEntity <Map<String, Object>> ObtenerReportePeriodoFE(@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		Integer IdUsuario = usuario.getIdUsuario();
		//Integer idLocal = 131;
		
		  Map<String, Object> response = new HashMap<>();
			
		  Integer idTipoOrden = 9; //Facturación electronica
			
            // Obtenemos los datos del JSON recibido
            String idPeriodo = (String) requestBody.get("idPeriodo");// idPeriodo
            System.out.println("idPeriodo desde /ObtenerReportePeriodo " + idPeriodo);
            
            Integer idPeriodoInt = Integer.parseInt(idPeriodo);
            
            List<ReporteFeDTO> reporteFE = tblDctosService.ObtenerReporteFE(usuario.getIdLocal(), idTipoOrden, idPeriodoInt);
            System.out.println("reporteFE desde /ObtenerReportePeriodo " + reporteFE);
          

           response.put("xReporteFE", reporteFE);
            		    
            System.out.println("response en /ObtenerInfoPQR " + response);



            return ResponseEntity.ok(response);
		
    }
	
	@GetMapping("/ReporteNotas")
	public String ReporteNotas(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");
		
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

			
			List <Integer> xListaPeriodos = tblDctosPeriodoService.ListaIdPeriodos(usuario.getIdLocal());
			System.out.println("xListaPeriodos es : " + xListaPeriodos);
			
			
			model.addAttribute("xListaPeriodos", xListaPeriodos);
             
             return "DIAN/ReporteNotas";

	}
	
	
	@PostMapping("/ObtenerReportePeriodoNotas")
    @ResponseBody
    public ResponseEntity <Map<String, Object>> ObtenerReportePeriodoNotas(@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		Integer IdUsuario = usuario.getIdUsuario();
		//Integer idLocal = 131;
		
		  Map<String, Object> response = new HashMap<>();
			
		  Integer idTipoOrden = 29;
			
            // Obtenemos los datos del JSON recibido
            String idPeriodo = (String) requestBody.get("idPeriodo");// idPeriodo
            System.out.println("idPeriodo desde /ObtenerReportePeriodo " + idPeriodo);
            
            Integer idPeriodoInt = Integer.parseInt(idPeriodo);
            
            List<ReporteFeDTO> reporteFE = tblDctosService.ObtenerReporteFE(usuario.getIdLocal(), idTipoOrden, idPeriodoInt);
            System.out.println("reporteFE desde /ObtenerReportePeriodo " + reporteFE);
          

           response.put("xReporteFE", reporteFE);
            		    
            System.out.println("response en /ObtenerInfoPQR " + response);



            return ResponseEntity.ok(response);
		
    }
}
