package com.marketing.Controller;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.*;
import java.util.*;

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

import com.marketing.Model.dbaquamovil.CertificadoResponse;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblOpcionesDTO;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblOpcionesService;
import com.marketing.ServiceApi.ApiCertificado;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Repository.dbaquamovil.CtrlusuariosRepo;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Utilidades.*;

@Controller
public class LoginController {
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblOpcionesService tblOpcionesService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	CtrlusuariosRepo ctrlusuariosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	ApiCertificado  apiCertificado;
	
	
	Integer idLocalAutenticado = 0;
	Integer xidUsuario = 0;
	
	@GetMapping("/LoginSite")
	public String LoginSite(HttpServletRequest request,Model model) {
		
		return "LoginSite";
		
	}

	@PostMapping("/login-post")
		//Se obtienen los valores ingresados en el form del index
	 public String login(HttpServletRequest request,  @RequestParam(value = "usuario", required = false) String idUsuario, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "sistema", required = false) String sistema,
                        Model model) throws UnknownHostException {

         xidUsuario = (int) Long.parseLong(idUsuario);
        
        
        System.out.println("Entró a /login-post");
        
        if("aquamovil".equals(sistema)) {
        	System.out.println(" El sistema si es : " + sistema);
        }else {
        	System.out.println(" El sistema no es aquamovil, el sitema es  " + sistema);
        	model.addAttribute("error", "Sistema no válido");
        	model.addAttribute("url", "/");
        	return "defaultErrorSistema";  // Mostrar página de error
        }
        
        
        
        
        // Se obtiene el usuario autenticado
        boolean isAuthenticated = ctrlusuariosService.authenticate(xidUsuario, password);
        System.out.println(" isAuthenticated es : " + isAuthenticated);
        


        // Validamos si el usuario está autenticado o no (Si los datos de acceso son correctos)
        if (isAuthenticated) {
        	
        	System.out.println("isAuthenticated es : " + isAuthenticated);
        	
        	
        	
        	
        	Ctrlusuarios usuarioAutenticado = ctrlusuariosService.obtenerUsuario(xidUsuario);
            Integer xidNivel = usuarioAutenticado.getIdNivel(); // El idNivel del usuario Logueado
            
             // Se obtiene el Idlocal de ctrlusuarios pasanddole como argumento el idUsuario
             idLocalAutenticado = ctrlusuariosService.consultarIdLocalPorIdUsuario(xidUsuario);
            
            // Obtenemos la Lista de Opciones Tipo 1
            List<Integer> ObtenerListaIdTipoOpcion1 = tblOpcionesService.ObtenerListaIdTipoOpcion1(idLocalAutenticado);
            System.out.println(" ObtenerListaIdTipoOpcion1 es : " + ObtenerListaIdTipoOpcion1);
            
            //Optenemos del idPerfil Logueado las opciones que coincidan con la lista ObtenerListaIdTipoOpcion1
            List<Integer> ListaIdTipoOpcion1OpcionesPerfil  = tblOpcionesService.ListaIdTipoOpcion1OpcionesPerfil(idLocalAutenticado, ObtenerListaIdTipoOpcion1, xidNivel);
            System.out.println("ListaIdTipoOpcion1OpcionesPerfil : " + ListaIdTipoOpcion1OpcionesPerfil);  
            
            
            // Se obtiene la lista de las opciones Tipo 1 dependiendo de la lista ObtenerListaIdTipoOpcion1
            List<TblOpcionesDTO>  ListaOpcionesTipo1 = tblOpcionesService.ObtenerTipoOpciones1(idLocalAutenticado, ListaIdTipoOpcion1OpcionesPerfil);
            System.out.println("La ListaOpcionesTipo1 es : " + ListaOpcionesTipo1);
        	
        	// Obtenemos el idEstadoTx del usuario que se intenga loguear
        	Integer idEstadoTx = tblAgendaLogVisitasService.ObtenerEstadoLogIdEstadoTx(idLocalAutenticado, xidUsuario);
        	System.out.println("El idEstadoTx del usuario " + idUsuario + " es: " + idEstadoTx);
        	
        	if(idEstadoTx == null) {
        		
        		idEstadoTx = 1;
        	}
        	
        	// Validamos si el idEstadoTx es = 9, si es así es proque ya el usuario está logueado, se envia mensaje de error 
        	if(idEstadoTx == 9 ) {
        		
        		System.out.println("Usuario ya se encuentra logueado" );
        		model.addAttribute("error", "Este usuario ya se encuentra logueado");
            	model.addAttribute("url", "/");
        		return "defaultErrorSistema";
        	} 
        	
        	
        	HttpSession session = request.getSession();
        	session.setAttribute("xidUsuario", xidUsuario);
	
        	// Obtenemos el ID de session 
        	String sessionId = session.getId();	
        	System.out.println("sessionId es : " + sessionId);
        	

        	
        	// Obtenemos la fecha y hora actual
		    Date fechaActual = new Date(); 
		    
		    // Formatea la fecha en el formato deseado
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    String fechaFormateada = dateFormat.format(fechaActual);
		    System.out.println("fechaFormateada en el login es: "+ fechaFormateada);
		    
		    // Obtenemos la lista de las sessiones de la fecha actual y que esten activas con el idEstadoTX = 9
		    List<String> xListaSessionId = tblAgendaLogVisitasService.ObtenerListaSessionId(fechaFormateada);
        	System.out.println("xListaSessionId "+ xListaSessionId);
        	
        	// Verificamos si sessionId ya existe en la lista
        	if (xListaSessionId.contains(sessionId)) {
        		
        		System.out.println("sessionId SI existe en la lista");
        		
        		// Obtenemos el idUsuario de la sessionId
        		List<Integer> IdUsuarioSession = tblAgendaLogVisitasService.ObtenerIdUsuariosPorIdSession(sessionId);
        		
        		for(Integer xIdUsuarioSession : IdUsuarioSession) {
        			
        			System.out.println("xIdUsuarioSession ess " + xIdUsuarioSession);
        			//Comparamos si el Usuario que intenta loguearse es el mismo que tiene la sessionId activa
            		if(xIdUsuarioSession == xidUsuario) {
            			
            			model.addAttribute("error", "El Usuario " + xidUsuario + "ya se encuentra logueado en esta sessión, por favor ingresar desde otra página u otro navegador. ");
                    	model.addAttribute("url", "/");
                    	return "defaultErrorSistema";
            		}
        		}

        		
        		
        	} else {
        	    System.out.println("sessionId no existe en la lista");
        	    
        	}
        	
        	 // Obtenemos la IP del servidor
        	 UtilidadesIP utilidadesIP = new UtilidadesIP();
        	 String direccionIP = utilidadesIP.getIpServidor();
        	 System.out.println("direccionIP es : " + direccionIP);
        	 
        	// Obtenemos el IDLOG Máximo y le sumamos uno
 	        Integer maximoIDLOGSum1 = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
 	        System.out.println("maximoIDLOG en /login-post: " + maximoIDLOGSum1);
 	        
 	        // Actualizamos los ESTADO Que sean = 9 a 1
	        tblAgendaLogVisitasRepo.actualizarEstadoA1(idLocalAutenticado, xidUsuario);
        	 
        	// Ingresamos el nuevo Log con ESTADO = 9
 	        tblAgendaLogVisitasService.ingresarLogSessionID(idLocalAutenticado, maximoIDLOGSum1, idUsuario, xidUsuario, direccionIP, sessionId);
 	        
 	        
 	        //Obtenemos el registro del login guardado
 	        List<TblAgendaLogVisitas> UsuarioLogueado = tblAgendaLogVisitasService.ObtenerRegistroDelLogin(idLocalAutenticado, maximoIDLOGSum1);
 	       
 	       
 	        //------ SE GUARDA EN LA SESSION EL OBJETO registroLogin QUE CONTIENE LOS DATOS DEL USUARIO LOGUEADO
            session.setAttribute("UsuarioLogueado", UsuarioLogueado);
       	
           
 	        
 	        
 	        
        	
        	// Se setean los valores a las variables 
            request.getSession().setAttribute("local", tblLocalesService.consultarLocal(idLocalAutenticado));
            request.getSession().setAttribute("usuarioAuth", usuarioAutenticado);
            request.getSession().setAttribute("sistema", sistema);
            request.getSession().setAttribute("ListaOpcionesTipo1", ListaOpcionesTipo1); // Guardamos la lista en una variable de Session para usarla posteriormente en Thymeleaf
            
            System.out.println(" request.getSession() es  " + request.getSession());
            model.addAttribute("ListaOpcionesTipo1", ListaOpcionesTipo1);
            
            
            // ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
       	 	// Obtenemos el periodo activo
   			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocalAutenticado);
   			
   			
   			Integer idTipoOrden = 9;
   			Integer idPeriodo = 0;
   			
   			String lectura = "";
   			String factura = "";
   			
   			String NombrePeriodo = "";
   			
   			for(TblDctosPeriodo P : PeriodoActivo) {
   				
   				idPeriodo = P.getIdPeriodo();
   				NombrePeriodo = P.getNombrePeriodo();
   			
   			}
   			
   			List<TblDctosOrdenesDTO> CuentaFacturado =  tblDctosOrdenesService.PeriodoFacturado(idLocalAutenticado, idTipoOrden, idPeriodo);
   			
   			Integer Cuenta = 0;
   			
   			for(TblDctosOrdenesDTO C : CuentaFacturado) {
   				
   				Cuenta = C.getCuenta();
   			}
   			
   			 // Periodo facurado				
   			if(Cuenta != 0) {
   				
   				factura = "CON ==> Factura";
   				model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodo + " YA FACTURADO NO PERMITE REGISTRAR LECTURAS");

   			}else {
   				
   				factura = "SIN ==> Factura";
   			}
   			
   			
   			int xIdRazonConsumo = 4;
   			
   			Integer idOrden = tblDctosOrdenesService.listaOrdenIdPeriodo(idLocalAutenticado, idPeriodo, idTipoOrden,
   					xIdRazonConsumo);
   			
   			if (idOrden > 0) {

   				lectura = "SIN ==> Lectura";
   			} else {
   				
   				lectura = "CON ==> Lectura";
   				
   			}
   			
   			
   			
               
               
   			//----------------------------------------------------------------------------------------------------------------------------------------------------------
            
            
            
            // VALIDACION CERTIFICADO --------------------------------------------------------------------------------------------------------------------------------------
            
            String xToken = tblLocalesService.ObtenerToken(idLocalAutenticado);
		    System.out.println("xToken en /Certificado : " + xToken);
            
		    // Invocamos la API para validar el certificado y obtenemos el resultado de la validación
    	    CertificadoResponse certificadoResponse = apiCertificado.consumirApi(xToken);
    	    
    	    // Verificar si certificadoResponse es nulo
            if (certificadoResponse == null) {
                System.out.println("Error al validar certificado: respuesta nula.");
                
                model.addAttribute("xValido", "DIAN FUERA DE LÍNEA - Por favor intentar mas tarde");
	            model.addAttribute("xVence", "DIAN FUERA DE LÍNEA - Por favor intentar mas tarde" );
	            model.addAttribute("xNombrePeriodo", NombrePeriodo );
	            model.addAttribute("xIdPeriodo", idPeriodo );
	            model.addAttribute("xlectura", lectura );
	            model.addAttribute("xfactura", factura );
                
                return "menuPrincipal";
            }
    		
    		// Obtenemos el valor de IsValid para validar que el cetificado esté vigente, osea sea TRUE 
    	    boolean isValid = certificadoResponse.isIs_valid();
    	    
    		
    		// Validamos si isValid es true
    	    if (isValid) { 
    			
    			// Obtenemosla fecha actual
    	        LocalDate xfechaActual = LocalDate.now();

    	        // Convierte la fecha de String a  LocalDate
    	        DateTimeFormatter fechaFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	        
    	        String xExpirationDate = certificadoResponse.getExpiration_date();
    	        LocalDate fechaExpiracion = LocalDate.parse(xExpirationDate, fechaFormat);

    	        // Calculamos la diferencia en días
    	        long diferenciaEnDias = ChronoUnit.DAYS.between(xfechaActual, fechaExpiracion);

    	        System.out.println("Diferencia en días: " + diferenciaEnDias);
    	        
    	        String fechaSinHora = xExpirationDate.substring(0, 10);
    	       
    			
    			// Validamos las diferencias, si es menor a 5, menor a 30 o mayor a 30
    	        if (diferenciaEnDias < 5) {
    	            System.out.println("Certificado expira en menos de " + diferenciaEnDias + " días");
    	            model.addAttribute("xValido", "Certificado expira en menos de " + diferenciaEnDias + " días");
    	            model.addAttribute("xVence", "Vence " + fechaSinHora );
    	            model.addAttribute("xNombrePeriodo", NombrePeriodo );
    	            model.addAttribute("xIdPeriodo", idPeriodo );
    	            model.addAttribute("xlectura", lectura );
    	            model.addAttribute("xfactura", factura );
    	            return "menuPrincipal"; 
    	        } else if (diferenciaEnDias < 30) {
    	            System.out.println("Certificado próximo a expirar");
    	            model.addAttribute("xValido", "Certificado próximo a expirar en " + diferenciaEnDias + " días" );
    	            model.addAttribute("xVence", "Vence " + fechaSinHora );
    	            model.addAttribute("xNombrePeriodo", NombrePeriodo );
    	            model.addAttribute("xIdPeriodo", idPeriodo );
    	            model.addAttribute("xlectura", lectura );
    	            model.addAttribute("xfactura", factura );
    	            return "menuPrincipal"; 
    	        } else {
    	            System.out.println("Certificado válido");
    	            model.addAttribute("xValido", "Válido " + diferenciaEnDias + " días" );
    	            model.addAttribute("xVence", "Vence " + fechaSinHora );
    	            model.addAttribute("xNombrePeriodo", NombrePeriodo );
    	            model.addAttribute("xIdPeriodo", idPeriodo );
    	            model.addAttribute("xlectura", lectura );
    	            model.addAttribute("xfactura", factura );
    	            return "menuPrincipal"; 
    	        }
    		
    		}else {
    			
    			System.out.println("isValid es : " + isValid);
    		}
            
            
    	
			

            return "menuPrincipal";  // Redirigir a la página principal
        } else {
            model.addAttribute("error", "Datos incorrectos o usuario inactivo, ");
            model.addAttribute("url", "/");
            return "defaultError";  // Mostrar página de error
        }
        
    }
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,Model model) {
		
		// Actualizamos los ESTADO Que sean = 9 a 1
	    tblAgendaLogVisitasRepo.actualizarEstadoA1(idLocalAutenticado, xidUsuario);
	    
	    System.out.println("idUsuario es : " + xidUsuario);
	    
	    HttpSession session = request.getSession();
	    
	    // Obtenemos el ID de session del usuario que intenta cerrar sessión
    	String sessionId = session.getId();
    	System.out.println("sessionId en  /logout es: " + sessionId);
    	
    	
    	
    	
    	List<Integer> ListaIdLocales = tblAgendaLogVisitasService.ObtenerListaIdLocalPorSession(sessionId);
    	
    	for(Integer xIdLocal : ListaIdLocales ) {
    		
    		// Actualizamos los idEstadoTx Que sean = 9 a 1
    	    tblAgendaLogVisitasRepo.actualizarIdEstadoTxA1(xIdLocal, sessionId);
    		
    	}
    	
    	//Obtenemos el idLocal de la sessionId
    	//Integer xIdLocal = tblAgendaLogVisitasService.ObtenerIdLocalPorSession(sessionId);
    	
    	// Detenemos el contador asociado a la sesión (si existe)
        detenerContador(sessionId);
	    
	    // Actualizamos los idEstadoTx Que sean = 9 a 1
	    //tblAgendaLogVisitasRepo.actualizarIdEstadoTxA1(xIdLocal, sessionId);
	    
		request.getSession().invalidate();
		
		return "redirect:/";
		
	}
	
	
	@GetMapping("/menuPrincipal")
	public String menuPrincipal(HttpServletRequest request,Model model) {
		
		// Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocalAutenticado);
			
			
			
			Integer idTipoOrden = 9;
			Integer idPeriodo = 0;
			
			String lectura = "";
			String factura = "";
			
			String NombrePeriodo = "";
			
			for(TblDctosPeriodo P : PeriodoActivo) {
				
				idPeriodo = P.getIdPeriodo();
				NombrePeriodo = P.getNombrePeriodo();
			
			}
			
			List<TblDctosOrdenesDTO> CuentaFacturado =  tblDctosOrdenesService.PeriodoFacturado(idLocalAutenticado, idTipoOrden, idPeriodo);
			
			Integer Cuenta = 0;
			
			for(TblDctosOrdenesDTO C : CuentaFacturado) {
				
				Cuenta = C.getCuenta();
			}
			
			 // Periodo facurado				
			if(Cuenta != 0) {
				
				factura = "CON ==> Factura";
				model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodo + " YA FACTURADO NO PERMITE REGISTRAR LECTURAS");

			}else {
				
				factura = "SIN ==> Factura";
			}
			
			
			int xIdRazonConsumo = 4;
			
			Integer idOrden = tblDctosOrdenesService.listaOrdenIdPeriodo(idLocalAutenticado, idPeriodo, idTipoOrden,
					xIdRazonConsumo);
			
			if (idOrden > 0) {

				lectura = "SIN ==> Lectura";
			} else {
				
				lectura = "CON ==> Lectura";
				
			}
			
			
			
           
           
			//----------------------------------------------------------------------------------------------------------------------------------------------------------
        
        
        
        // VALIDACION CERTIFICADO --------------------------------------------------------------------------------------------------------------------------------------
        
        String xToken = tblLocalesService.ObtenerToken(idLocalAutenticado);
	    System.out.println("xToken en /Certificado : " + xToken);
        
	    // Invocamos la API para validar el certificado y obtenemos el resultado de la validación
	    CertificadoResponse certificadoResponse = apiCertificado.consumirApi(xToken);
	    
	    // Verificar si certificadoResponse es nulo
        if (certificadoResponse == null) {
            System.out.println("Error al validar certificado: respuesta nula.");
            
            model.addAttribute("xValido", "DIAN FUERA DE LÍNEA - Por favor intentar mas tarde");
            model.addAttribute("xVence", "DIAN FUERA DE LÍNEA - Por favor intentar mas tarde" );
            model.addAttribute("xNombrePeriodo", NombrePeriodo );
            model.addAttribute("xIdPeriodo", idPeriodo );
            model.addAttribute("xlectura", lectura );
            model.addAttribute("xfactura", factura );
            
            return "menuPrincipal";
        }
		
		// Obtenemos el valor de IsValid para validar que el cetificado esté vigente, osea sea TRUE 
	    boolean isValid = certificadoResponse.isIs_valid();
		
		// Validamos si isValid es true
	    if (isValid) { 
			
			// Obtenemosla fecha actual
	        LocalDate xfechaActual = LocalDate.now();

	        // Convierte la fecha de String a  LocalDate
	        DateTimeFormatter fechaFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        
	        String xExpirationDate = certificadoResponse.getExpiration_date();
	        LocalDate fechaExpiracion = LocalDate.parse(xExpirationDate, fechaFormat);

	        // Calculamos la diferencia en días
	        long diferenciaEnDias = ChronoUnit.DAYS.between(xfechaActual, fechaExpiracion);

	        System.out.println("Diferencia en días: " + diferenciaEnDias);
	        
	        String fechaSinHora = xExpirationDate.substring(0, 10);
	       
			
			// Validamos las diferencias, si es menor a 5, menor a 30 o mayor a 30
	        if (diferenciaEnDias < 5) {
	            System.out.println("Certificado expira en menos de " + diferenciaEnDias + " días");
	            model.addAttribute("xValido", "Certificado expira en menos de " + diferenciaEnDias + " días");
	            model.addAttribute("xVence", "Vence " + fechaSinHora );
	            model.addAttribute("xNombrePeriodo", NombrePeriodo );
	            model.addAttribute("xIdPeriodo", idPeriodo );
	            model.addAttribute("xlectura", lectura );
	            model.addAttribute("xfactura", factura );
	            return "menuPrincipal"; 
	        } else if (diferenciaEnDias < 30) {
	            System.out.println("Certificado próximo a expirar");
	            model.addAttribute("xValido", "Certificado próximo a expirar en " + diferenciaEnDias + " días" );
	            model.addAttribute("xVence", "Vence " + fechaSinHora );
	            model.addAttribute("xNombrePeriodo", NombrePeriodo );
	            model.addAttribute("xIdPeriodo", idPeriodo );
	            model.addAttribute("xlectura", lectura );
	            model.addAttribute("xfactura", factura );
	            return "menuPrincipal"; 
	        } else {
	            System.out.println("Certificado válido");
	            model.addAttribute("xValido", "Válido " + diferenciaEnDias + " días" );
	            model.addAttribute("xVence", "Vence " + fechaSinHora );
	            model.addAttribute("xNombrePeriodo", NombrePeriodo );
	            model.addAttribute("xIdPeriodo", idPeriodo );
	            model.addAttribute("xlectura", lectura );
	            model.addAttribute("xfactura", factura );
	            return "menuPrincipal"; 
	        }
		
		}else {
			
			System.out.println("isValid es : " + isValid);
		}
        

		
		return "menuPrincipal";
		
	}
	
	
	@GetMapping("/CambiarContrasena")
	public String CambiarContraseña(HttpServletRequest request,Model model) {

		System.out.println("Entró a /CambiarContraseña");
	    
	    HttpSession session = request.getSession();
	    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
	    
	    System.out.println("El usuario en session es: " + idUsuario);
	    
	    // Se obtiene el Idlocal de ctrlusuarios pasanddole como argumento el idUsuario
        Integer xIdLocal = ctrlusuariosService.consultarIdLocalPorIdUsuario(idUsuario);
        String xClave = ctrlusuariosService.obtenerClaveUsuario(xIdLocal, idUsuario);
        
        System.out.println("xClave es: " + xClave);
        
        model.addAttribute("xClave", xClave);
	    
		
		return "CambioContraseña";
		
	}
	
	
	@PostMapping("/CambiarContraseña-post")
	@ResponseBody
 public ResponseEntity<Map<String, Object>> CambiarContraseñaPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {

    System.out.println("Entró a /CambiarContraseña-post");
    
    HttpSession session = request.getSession();
    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
    
    // Se obtiene el Idlocal de ctrlusuarios pasanddole como argumento el idUsuario
    Integer xIdLocal = ctrlusuariosService.consultarIdLocalPorIdUsuario(idUsuario);
    
    System.out.println("El usuario en session es: " + idUsuario);
    
    // Obtenemos los datos del JSON recibido
    String passwordActual = (String) requestBody.get("passwordActual");
    System.out.println("codigoUsuario desde la nueva función " + passwordActual);
    
    String password = (String) requestBody.get("password");
    System.out.println("codigoUsuario desde la nueva función " + password);
    
    String Repetirpassword = (String) requestBody.get("Repetirpassword");
    System.out.println("codigoUsuario desde la nueva función " + Repetirpassword);
    
    // Actualizamos la nueva contraseña
    ctrlusuariosRepo.actualizarClave(password, xIdLocal, idUsuario);
    
    //Eliminamos de la sessión el idLocal y el idUsuario
    session.removeAttribute("xIdLocal");
    session.removeAttribute("idUsuario");

    
    Map<String, Object> response = new HashMap<>();
    response.put("message", "LOG ingresado Correctamente");
    return ResponseEntity.ok(response);
    
}
	
	
	@PostMapping("/LogoutPorInactividad")
	@ResponseBody
 public ResponseEntity<Map<String, Object>> LogoutPorInactividad(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {

    System.out.println("Ingresó a /LogoutPorInactividad");
    
    HttpSession session = request.getSession();
    
    // Obtenemos el ID de session del usuario que intenta cerrar sessión
	String sessionId = session.getId();
	System.out.println("sessionId en  /logout es: " + sessionId);
	
	//Obtenemos el idLocal de la sessionId
	//Integer xIdLocal = tblAgendaLogVisitasService.ObtenerIdLocalPorSession(sessionId);
	
	
	List<Integer> ListaIdLocales = tblAgendaLogVisitasService.ObtenerListaIdLocalPorSession(sessionId);
	
	for(Integer xIdLocal : ListaIdLocales ) {
		
		// Actualizamos los idEstadoTx Que sean = 9 a 1
	    tblAgendaLogVisitasRepo.actualizarIdEstadoTxA1(xIdLocal, sessionId);
		
	}
    
    
    

    request.getSession().invalidate();
	
    Map<String, Object> response = new HashMap<>();
    response.put("message", "LOG ingresado Correctamente");
    return ResponseEntity.ok(response);
    
}
	
	

	
	
    private static final long TIEMPO_INICIAL = 5 * 60 * 1000; //  minutos en milisegundos
    private Map<String, TimerTask> sesionTimers = new HashMap<>(); // Almacenamos las tareas del temporirazor
    private Timer timer = new Timer(); // Se crea una instancia de la clase Timer

    @PostMapping("/ReporteActividad")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> reporteActividad(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        System.out.println("Ingresó a /ReporteActividad");

        HttpSession session = request.getSession();
        String sessionId = session.getId();
        System.out.println("sessionId en /ReporteActividad es: " + sessionId);

        // Detener el temporizador existente, si hay alguno
        detenerContador(sessionId);

        // Iniciar un nuevo temporizador
        iniciarContador(sessionId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "LOG ingresado Correctamente");
        return ResponseEntity.ok(response);
    }

    public void iniciarContador(String sessionId) {
        TimerTask timerTask = new TimerTask() {  // Se crea una nueva tarea del temporizador
            long tiempoRestante = TIEMPO_INICIAL; // Inicializamos el tiempoRestante con el TIEMPO_INICIAL definido antes

            @Override
            public void run() {
                if (tiempoRestante > 0) { // Verificamos si el tiempoRestante es mayor a 0
                  // System.out.println("SessionId: " + sessionId + " - Tiempo restante: " + tiempoRestante / 1000 + " segundos");
                    tiempoRestante -= 1000; // Decrementamos el tiempoRestante un segundo 
                } else {
                    System.out.println("SessionId: " + sessionId + " - Tiempo agotado");
                    
                  //Obtenemos el idLocal de la sessionId
                	Integer xIdLocal = tblAgendaLogVisitasService.ObtenerIdLocalPorSession(sessionId);
                	
                	// Actualizamos los idEstadoTx Que sean = 9 a 1
                    tblAgendaLogVisitasRepo.actualizarIdEstadoTxA1(xIdLocal, sessionId);
                	System.out.println("Sessión cerrada para el usuario con ID de sessión : " + sessionId);
                    
                    detenerContador(sessionId);
                }
            }
        };

        // Cancelar el temporizador existente, si hay alguno
        detenerContador(sessionId);

        // Agregamos el nuevo temporizador al mapa
        sesionTimers.put(sessionId, timerTask);

        // Programamos el temporizador para ejecutarse cada segundo
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void detenerContador(String sessionId) {
        TimerTask timerTask = sesionTimers.get(sessionId);  // Obtenemos la tarea del temporizador asociado con la sessionId

        // Verificamos si existe un TimerTask para la sessionId y si el temporizador no ha sido cancelado previamente
        if (timerTask != null && !timerTask.cancel()) {
            // Si el temporizador no se canceló correctamente, lo eliminamos del mapa
            sesionTimers.remove(sessionId); // Eliminamos la tarea del temporizador del sesionTimers
            System.out.println("SessionId: " + sessionId + " - Contador detenido");
        } else {
            // Si el temporizador ya había sido cancelado previamente, mostramos un mensaje de advertencia
            System.out.println("El temporizador para sessionId: " + sessionId + " ya ha sido cancelado previamente.");
        }
    }

    




}









