package com.marketing.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.sql.Timestamp;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblDctosOrdenes;
import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblPlusService;
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
	
	@Autowired
	TblPlusService tblPlusService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblDctosService tblDctosService;
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;

	@GetMapping("/GenerarPqr")
	public String generarPQR (HttpServletRequest request,Model model) {
		
        System.out.println("Si entro al controllador");
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		
		
		if(usuario==null) {
			model.addAttribute("usuario",new Ctrlusuarios());
			
			return "index";
		}else {
			
			Integer IdUsuario = usuario.getIdUsuario();

			// Obtenemos el IdLogMaximo del IdUsuario logueado
						Integer IdLogActivo =  tblAgendaLogVisitasService.ObtenerIdLogActivo(IdUsuario);
						System.out.println("El IdLogMaximo en /GenerarPqr es: " + IdLogActivo);
			
			
			
			
			// Validamos si el estado es 9
			if(IdLogActivo != 0) {
				System.out.println("EstadoLog =  " + IdLogActivo);
				
				// Obtenemos el IDORDEN 
				Integer idOrdenObtenido = tblDctosOrdenesService.ObtenerIdOrdenDelIdLog(usuario.getIdLocal(), IdLogActivo);
				System.out.println("idOrdenObtenido =  " + idOrdenObtenido);
				
				
				// Validamos si el IDORDEN es Diferente a Null, si es así es por que hay un IDORDEN ya registrado en tblDtosOrdenes
				if(idOrdenObtenido != null) {
					System.out.println("idLogObtenido NO es NULL " + idOrdenObtenido);
					
					Integer TipoOrden = tblDctosOrdenesService.ObtenerTipoOrden(usuario.getIdLocal(), idOrdenObtenido, IdLogActivo);
					
					// Validamos si idTipoOrden es 67 (Estado Temporal)
				    if(TipoOrden == 67) {
				    	
				    	System.out.println("idTipoOrden en el if es: " + TipoOrden);
				    	
				    	//Obtenemos el IdCliente relacionado con el IDTIPOORDEN
				    	Integer xidCliente = tblDctosOrdenesService.ObtenerIdCliente(usuario.getIdLocal(), IdUsuario);
				    	System.out.println("El xidUsuario en el if es: " + xidCliente);
				    	
				    	List<TblTercerosProjectionDTO> datosTerceros = tblTercerosService.obtenerDatosTercerosClientes(usuario.getIdLocal(), xidCliente);
				    	System.out.println("Los datosTerceros en el if es: " + datosTerceros);
				    	
				    	TblTercerosProjectionDTO registroObtenido = datosTerceros.get(0);
				    	
				    	String xNombreTercero = registroObtenido.getNombreTercero();
				    	String xIdCliente = registroObtenido.getIdCliente();
				    	String xTelefono = registroObtenido.getTelefonoCelular();
				    	String xEmail = registroObtenido.getEmail();
				    	String xDireccion = registroObtenido.getDireccionTercero();
				    	
				    	model.addAttribute("xNombreTercero", xNombreTercero);
				    	model.addAttribute("xIdCliente", xIdCliente);
				    	model.addAttribute("xTelefono", xTelefono);
				    	model.addAttribute("xEmail", xEmail);
				    	model.addAttribute("xDireccion", xDireccion);
				    
				    	
				    	// Obtenemos la lista de los terceros Empleados
						List<TblTercerosProjectionDTO> xnombreTecerosEmpleados  = tblTercerosService.obtenerNombreTercerosEmpleados(usuario.getIdLocal());
						
						//Obtenemos la lista de los clientes 
						List<TblTercerosProjectionDTO> xnombreTecerosClientes  = tblTercerosService.obtenerNombreTercerosClientes(usuario.getIdLocal());
						
						// Obtenemos el idusuario logueado
						Integer xIdUsuario = usuario.getIdUsuario();
						
						//Obtenemos la lista de los NombresUsuarios
						List <CtrlusuariosDTO> xListaNombresUsuarios = ctrlusuariosService.obtenerNombresUsuarios(usuario.getIdLocal(), xIdUsuario);
						System.out.println("xListaNombresUsuarios es : " + xListaNombresUsuarios);
						
						// Convierte xnombreTecerosClientes en una cadena JSON
					    ObjectMapper objectMapper = new ObjectMapper();
					    
					    // Creamos una variable donde vamos a almacenar el Json
					    String xnombreTecerosClientesJson;
					    try {
					        xnombreTecerosClientesJson = objectMapper.writeValueAsString(xnombreTecerosClientes);
					    } catch (JsonProcessingException e) {
					        xnombreTecerosClientesJson = "[]"; // En caso de error se crea un [] vacio
					    }
				    	
				    	  // Obtenemos la fecha y hora actual
					    Date fechaRadicacion = new Date(); 

					    // Formatea la fecha en el formato deseado
					    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					    String fechaFormateada = dateFormat.format(fechaRadicacion);

					    model.addAttribute("fechaRadicacion", fechaFormateada);
				    
				    	String xComentario  = tblDctosOrdenesDetalleService.ObtenerComentario(usuario.getIdLocal(), idOrdenObtenido, xidCliente);
				    	String xOrdenCompra = tblDctosOrdenesService.ObtenerOrdenCompra(usuario.getIdLocal(), idOrdenObtenido);
				    	
				    	ArrayList<TblPlus> xcategoria10 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 10);
						ArrayList<TblPlus> xcategoria11 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 11);
						ArrayList<TblPlus> xcategoria12 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 12);
						ArrayList<TblPlus> xcategoria13 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 13);
						ArrayList<TblPlus> xcategoria14 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 14);
				    	
						model.addAttribute("xcategoria10", xcategoria10);
						model.addAttribute("xcategoria11", xcategoria11);
						model.addAttribute("xcategoria12", xcategoria12);
						model.addAttribute("xcategoria13", xcategoria13);
						model.addAttribute("xcategoria14", xcategoria14);
						model.addAttribute("descripcionSolicitud", xComentario);
						model.addAttribute("xOrdenCompra", xOrdenCompra);
						model.addAttribute("xnombreTecerosEmpleados", xnombreTecerosEmpleados);
						model.addAttribute("xListaNombresUsuarios", xListaNombresUsuarios);
						model.addAttribute("xnombreTecerosClientesJson", xnombreTecerosClientesJson);
						
				    }
					
					
				}else { // SI EL idOrdenObtenido ES NULL
					
					System.out.println("idLogObtenido SI es NULL " + idOrdenObtenido);
					
					ArrayList<TblPlus> xcategoria10 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 10);
					ArrayList<TblPlus> xcategoria11 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 11);
					ArrayList<TblPlus> xcategoria12 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 12);
					ArrayList<TblPlus> xcategoria13 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 13);
					ArrayList<TblPlus> xcategoria14 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 14);
					
					
					// Obtenemos la lista de los terceros Empleados
					List<TblTercerosProjectionDTO> xnombreTecerosEmpleados  = tblTercerosService.obtenerNombreTercerosEmpleados(usuario.getIdLocal());
					
					//Obtenemos la lista de los clientes 
					List<TblTercerosProjectionDTO> xnombreTecerosClientes  = tblTercerosService.obtenerNombreTercerosClientes(usuario.getIdLocal());
					
					// Obtenemos el idusuario logueado
					Integer xIdUsuario = usuario.getIdUsuario();
					
					//Obtenemos la lista de los NombresUsuarios
					List <CtrlusuariosDTO> xListaNombresUsuarios = ctrlusuariosService.obtenerNombresUsuarios(usuario.getIdLocal(), xIdUsuario);
					System.out.println("xListaNombresUsuarios es : " + xListaNombresUsuarios);
					
					
					
					// Convierte xnombreTecerosClientes en una cadena JSON
				    ObjectMapper objectMapper = new ObjectMapper();
				    
				    // Creamos una variable donde vamos a almacenar el Json
				    String xnombreTecerosClientesJson;
				    try {
				        xnombreTecerosClientesJson = objectMapper.writeValueAsString(xnombreTecerosClientes);
				    } catch (JsonProcessingException e) {
				        xnombreTecerosClientesJson = "[]"; // En caso de error se crea un [] vacio
				    }
				    
				    // Obtenemos la fecha y hora actual
				    Date fechaRadicacion = new Date(); 

				    // Formatea la fecha en el formato deseado
				    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				    String fechaFormateada = dateFormat.format(fechaRadicacion);

				    model.addAttribute("fechaRadicacion", fechaFormateada);
					
					
					model.addAttribute("xcategoria10", xcategoria10);
					model.addAttribute("xcategoria11", xcategoria11);
					model.addAttribute("xcategoria12", xcategoria12);
					model.addAttribute("xcategoria13", xcategoria13);
					model.addAttribute("xcategoria14", xcategoria14);
					model.addAttribute("xnombreTecerosEmpleados", xnombreTecerosEmpleados);
					model.addAttribute("xListaNombresUsuarios", xListaNombresUsuarios);
					model.addAttribute("xnombreTecerosClientesJson", xnombreTecerosClientesJson);
					
				}
				
			}else { // SI IdLogActivo ES NULL OSEA QUE NO HAY NADA EN ESTADO 9 
				
				ArrayList<TblPlus> xcategoria10 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 10);
				ArrayList<TblPlus> xcategoria11 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 11);
				ArrayList<TblPlus> xcategoria12 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 12);
				ArrayList<TblPlus> xcategoria13 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 13);
				ArrayList<TblPlus> xcategoria14 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 14);
				
				
				// Obtenemos la lista de los terceros Empleados
				List<TblTercerosProjectionDTO> xnombreTecerosEmpleados  = tblTercerosService.obtenerNombreTercerosEmpleados(usuario.getIdLocal());
				
				//Obtenemos la lista de los clientes 
				List<TblTercerosProjectionDTO> xnombreTecerosClientes  = tblTercerosService.obtenerNombreTercerosClientes(usuario.getIdLocal());
				
				// Obtenemos el idusuario logueado
				Integer xIdUsuario = usuario.getIdUsuario();
				
				//Obtenemos la lista de los NombresUsuarios
				List <CtrlusuariosDTO> xListaNombresUsuarios = ctrlusuariosService.obtenerNombresUsuarios(usuario.getIdLocal(), xIdUsuario);
				System.out.println("xListaNombresUsuarios es : " + xListaNombresUsuarios);
				
				
				
				// Convierte xnombreTecerosClientes en una cadena JSON
			    ObjectMapper objectMapper = new ObjectMapper();
			    
			    // Creamos una variable donde vamos a almacenar el Json
			    String xnombreTecerosClientesJson;
			    try {
			        xnombreTecerosClientesJson = objectMapper.writeValueAsString(xnombreTecerosClientes);
			    } catch (JsonProcessingException e) {
			        xnombreTecerosClientesJson = "[]"; // En caso de error se crea un [] vacio
			    }
			    
			    // Obtenemos la fecha y hora actual
			    Date fechaRadicacion = new Date(); 

			    // Formatea la fecha en el formato deseado
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			    String fechaFormateada = dateFormat.format(fechaRadicacion);

			    model.addAttribute("fechaRadicacion", fechaFormateada);
				
				
				model.addAttribute("xcategoria10", xcategoria10);
				model.addAttribute("xcategoria11", xcategoria11);
				model.addAttribute("xcategoria12", xcategoria12);
				model.addAttribute("xcategoria13", xcategoria13);
				model.addAttribute("xcategoria14", xcategoria14);
				model.addAttribute("xnombreTecerosEmpleados", xnombreTecerosEmpleados);
				model.addAttribute("xListaNombresUsuarios", xListaNombresUsuarios);
				model.addAttribute("xnombreTecerosClientesJson", xnombreTecerosClientesJson);
				
				
				
				
				
			}
				
			    
			    
			return "pqr/pqr";
			
		}
	}
	
	
	@PostMapping("/GuardarLog")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> guardarLog(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  GuardarLog");

	    try {
	        // Obtenemos el IDLOG Máximo y le sumamos uno
	        Integer maximoIDLOGSum1 = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
	        System.out.println("maximoIDLOG en /GuardarLog: " + maximoIDLOGSum1);

	        // Obtenemos los datos del JSON recibido
	        String codigoUsuario = (String) requestBody.get("codigoUsuario");
	        System.out.println("codigoUsuario desde la nueva función " + codigoUsuario);

	        // Actualizamos los ESTADO Que sean = 9 a 1
	        tblAgendaLogVisitasRepo.actualizarEstadoA1(usuario.getIdLocal(), IdUsuario);

	        // Ingresamos el nuevo Log con ESTADO = 9
	        tblAgendaLogVisitasService.ingresarLog(usuario.getIdLocal(), maximoIDLOGSum1, codigoUsuario, IdUsuario);

	        Map<String, Object> response = new HashMap<>();
	        response.put("message", "LOG ingresado Correctamente");

	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("error", "Hubo un error al procesar la solicitud");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }
	}
	
	
	@PostMapping("/GuardarTemporalPqr")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> guardarTemporalPqr(@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		Map<String, Object> response = new HashMap<>();
		
		Integer IdUsuario = usuario.getIdUsuario();
		
		// Obtenemos los datos del JSON recibido
        String codigoUsuario = (String) requestBody.get("codigoUsuario"); // IdCliente
        String descripcionSolicitud = (String) requestBody.get("descripcionSolicitud");
        String NroFactura = (String) requestBody.get("NroFactura");
        String fechaRadicacion = (String) requestBody.get("fechaRadicacion");
        
        String orderRevision = (String) requestBody.get("orderRevision");
        String reesponsable = (String) requestBody.get("reesponsable");
      
        // Validamos si el campo "Reesponsable" es = a "Seleccione" si es asi se pasa el valor de "0", sino el valor obtenido 
        String valorReesponsable;
        
        if ("Seleccione".equals(reesponsable)) {
        	valorReesponsable = "0";
        } else {
        	valorReesponsable = reesponsable;
        }
        
        
        
        String fechaRadicacionFormateada = "";
        try {
            // Convierte la cadena de fecha en formato "yyyy-MM-dd'T'HH:mm" a un objeto Date
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date fechaRadicacionDate = inputDateFormat.parse(fechaRadicacion);

            // Formatea la fecha
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fechaRadicacionFormateada = outputDateFormat.format(fechaRadicacionDate);

            System.out.println("fechaRespuesta en /GuardarTemporalPqr " + fechaRadicacionFormateada);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        Timestamp xfechaRadicacion = null;
        
        try {
        // Convertimos la cadena fechaRadicacionFormateada en un objeto Timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = sdf.parse(fechaRadicacionFormateada);
        xfechaRadicacion = new Timestamp(parsedDate.getTime());
        
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        System.out.println("xfechaRadicacion" + xfechaRadicacion);
        
        
        
			
			// Obtenemos el IDLOG Máximo
        	Integer maximoIDLOG = tblAgendaLogVisitasService.ObtenerIdLogActivo(IdUsuario);
			System.out.println("maximoIDLOG: " + maximoIDLOG);
			
			
			    
			    
			    Integer idTipoOrden = tblDctosOrdenesService.ObtenerTipoOrdenCliente(usuario.getIdLocal(), codigoUsuario, IdUsuario);
			    System.out.println("idTipoOrden ESSSSSS : " + idTipoOrden);
			    
			    // Validamos si previamente se guardó la información de la pqr, si es así se borra el ultimo IdOrden y se insertan los nuevos valores
			    if(idTipoOrden != null) {
			    	
			    	//Obtenemos el maximoIDORDEN
			    	Integer maximoIDORDEN = tblDctosOrdenesService.obtenerMaximoIDORDEN(usuario.getIdLocal());
			    	
			    	// Obtenemos el máximo NumeroOrden
			    	Integer maximoNumeroOrden = tblDctosOrdenesService.findMaxNumeroOrden(usuario.getIdLocal()) + 1;
			    	
			    	// ELIMINAR REGISTROS EN TblDctosOrdenes
			    	tblDctosOrdenesRepo.eliminarRegistrosOrdenes(usuario.getIdLocal(), maximoIDORDEN);
	
			    	// ELIMINAR REGISTROS EN TblDctosOrdenesDetalle
			    	tblDctosOrdenesDetalleRepo.eliminarRegistros(usuario.getIdLocal(), maximoIDORDEN);
			    	
			    	// Ingresamos la orden en TblDctosOrdenes
		            tblDctosOrdenesService.ingresarOrden(usuario.getIdLocal(), maximoIDORDEN, codigoUsuario, IdUsuario, maximoIDLOG, maximoNumeroOrden, NroFactura, xfechaRadicacion);	
			    	
			    	
					   //Obtenemos los NombresPlus
		            Map<String, String> nombrePluIdPluMap = tblPlusService.ObtenerNombrePluAndIdPlu(usuario.getIdLocal());
		            
		            // Creamos una lista de String para almacenar los valores
		            List<String> listaIdPlus = new ArrayList<>();
		            
		            String idPlu4000 = "4000";
				    String idPlu4100 = "4100";

		            // Agregar los valores a la lista en el orden deseado
		            listaIdPlus.add((String) requestBody.get("servicio"));
		            listaIdPlus.add((String) requestBody.get("tipoTramite"));
		            listaIdPlus.add((String) requestBody.get("Causal"));
		            listaIdPlus.add((String) requestBody.get("detalleCausal"));
		            listaIdPlus.add((String) requestBody.get("medioRecepcion"));
		            listaIdPlus.add(idPlu4000);
		            listaIdPlus.add(idPlu4100);
		            
		            
		            response.put("xRadicado", maximoNumeroOrden);
				    
				    System.out.println("response en el controller es " + response);
				    

				    // Iteramos sobre la listaIdPlus
				    for (String idPlu : listaIdPlus) {
				        // Obtenemos el nombrePlu correspondiente al idPlu actual
				        String nombrePlu = nombrePluIdPluMap.get(idPlu);

				        // Convierte el String idPlu a Integer
				        Integer idPluInt = Integer.parseInt(idPlu);

				        // Obtenemos el valor correspondiente para los IDPLU 4000 y 4100
				        if (idPlu.equals("4000")) {
				            // Asigna el valor de orderRevision a VRVENTAORIGINAL
				  
				            String valor4000 = orderRevision;
				            Integer valor4000Int = Integer.parseInt(valor4000);
				   
				            tblDctosOrdenesDetalleService.ingresarDetalleOrden(usuario.getIdLocal(), maximoIDORDEN, codigoUsuario, idPluInt, nombrePlu, descripcionSolicitud, valor4000Int);
				        } else if (idPlu.equals("4100")) {
				            // Asigna el valor de reesponsable a VRVENTAORIGINAL
				   
				            String valor4100 = valorReesponsable;
				            Integer valor4100Int = Integer.parseInt(valor4100);
		
				            tblDctosOrdenesDetalleService.ingresarDetalleOrden(usuario.getIdLocal(), maximoIDORDEN, codigoUsuario, idPluInt, nombrePlu, descripcionSolicitud, valor4100Int);
				        } else {
				          
				        	Integer valor0 = 0;
				            tblDctosOrdenesDetalleService.ingresarDetalleOrden(usuario.getIdLocal(), maximoIDORDEN, codigoUsuario, idPluInt, nombrePlu, descripcionSolicitud, valor0);
				        }
				    }
			    	
			    }else {
			    		
			
			// Obtenemos el IDORDEN Máximo y le sumamos 1
			Integer maximoIdOrdenSum1 = tblDctosOrdenesService.obtenerMaximoIDORDEN(usuario.getIdLocal()) + 1;
			System.out.println("maximoIDORDEN: " + maximoIdOrdenSum1);
			
			
			// Obtenemos el máximo NumeroOrden y le sumamos 1
	    	Integer maximoNumeroOrdenSum1 = tblDctosOrdenesService.findMaxNumeroOrden(usuario.getIdLocal()) + 1;
			
            
            
            // Ingresamos la orden en TblDctosOrdenes
            tblDctosOrdenesService.ingresarOrden(usuario.getIdLocal(), maximoIdOrdenSum1, codigoUsuario, IdUsuario, maximoIDLOG, maximoNumeroOrdenSum1, NroFactura, xfechaRadicacion);
            
            //Obtenemos los NombresPlus
            Map<String, String> nombrePluIdPluMap = tblPlusService.ObtenerNombrePluAndIdPlu(usuario.getIdLocal());
            
            // Creamos una lista de String para almacenar los valores
            List<String> listaIdPlus = new ArrayList<>();
            
            String idPlu4000 = "4000";
		    String idPlu4100 = "4100";

            // Agregar los valores a la lista en el orden deseado
            listaIdPlus.add((String) requestBody.get("servicio"));
            listaIdPlus.add((String) requestBody.get("tipoTramite"));
            listaIdPlus.add((String) requestBody.get("Causal"));
            listaIdPlus.add((String) requestBody.get("detalleCausal"));
            listaIdPlus.add((String) requestBody.get("medioRecepcion"));
            listaIdPlus.add(idPlu4000);
            listaIdPlus.add(idPlu4100);
            
            
            response.put("xRadicado", maximoNumeroOrdenSum1);
		    
		    System.out.println("response en el controller es " + response);
                 
            
		 // Iteramos sobre la listaIdPlus
		    for (String idPlu : listaIdPlus) {
		        // Obtenemos el nombrePlu correspondiente al idPlu actual
		        String nombrePlu = nombrePluIdPluMap.get(idPlu);

		        // Convierte el String idPlu a Integer
		        Integer idPluInt = Integer.parseInt(idPlu);

		        // Obtenemos el valor correspondiente para los IDPLU 4000 y 4100
		        if (idPlu.equals("4000")) {
		            // Asigna el valor de orderRevision a VRVENTAORIGINAL
		            String valor4000 = orderRevision;
		            Integer valor4000Int = Integer.parseInt(valor4000);
		           
		            tblDctosOrdenesDetalleService.ingresarDetalleOrden(usuario.getIdLocal(), maximoIdOrdenSum1, codigoUsuario, idPluInt, nombrePlu, descripcionSolicitud, valor4000Int);
		        } else if (idPlu.equals("4100")) {
		            // Asigna el valor de reesponsable a VRVENTAORIGINAL
		        
		            String valor4100 = valorReesponsable;
		            Integer valor4100Int = Integer.parseInt(valor4100);
		         
		            tblDctosOrdenesDetalleService.ingresarDetalleOrden(usuario.getIdLocal(), maximoIdOrdenSum1, codigoUsuario, idPluInt, nombrePlu, descripcionSolicitud, valor4100Int);
		        } else {
		        	
		        	Integer valor0 = 0;
		            tblDctosOrdenesDetalleService.ingresarDetalleOrden(usuario.getIdLocal(), maximoIdOrdenSum1, codigoUsuario, idPluInt, nombrePlu, descripcionSolicitud, valor0);
		        }
		    }


            
	
		}  
			    

			    return ResponseEntity.ok(response);
		
    }
	
	
	@PostMapping("/GenerarPqr-post")
	public String generarPqrPost (HttpServletRequest request,
			@RequestParam(value = "codigoUsuario", required = false) String codigoUsuario,
			@RequestParam(value = "telefono", required = false) String telefono,
			@RequestParam(value = "correoElectronico", required = false) String correoElectronico,
			@RequestParam(value = "direccion", required = false) String direccion,
			@RequestParam("fechaRadicacion") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date fechaRadicacion,
			@RequestParam(value = "servicio", required = false) String servicio,
			@RequestParam(value = "tipoTramite", required = false) String tipoTramite,
			@RequestParam(value = "Causal", required = false) String Causal,
			@RequestParam(value = "detalleCausal", required = false) String detalleCausal,
			@RequestParam(value = "medioRecepcion", required = false) String medioRecepcion,
			@RequestParam(value = "reesponsable", required = false) String reesponsable,
			@RequestParam(value = "descripcionSolicitud", required = false) String descripcionSolicitud,
			@RequestParam(value = "numeroRadicado", required = false) String numeroOrden,
			@RequestParam(value = "subject", required = false) String subject,
			Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		if(usuario==null) {
			model.addAttribute("usuario",new Ctrlusuarios());
			
			return "index";
		}else { 
			Integer IdUsuario = usuario.getIdUsuario(); //Usuario logueado
			
			// Obtenemos el IDORDEN Máximo 
			Integer maximoIDORDEN = tblDctosOrdenesService.obtenerMaximoIDORDEN(usuario.getIdLocal());
			
			//Convertimos el IdOrden obtenido a Integer
			Integer NumeroOrdenInteger = Integer.parseInt(numeroOrden);
			
			
			Integer idLogObtenido = tblDctosOrdenesService.ObtenerIdLog(usuario.getIdLocal(), IdUsuario, NumeroOrdenInteger);
			System.out.println("idLogObtenido en /GenerarPqr-post: " + idLogObtenido);
			
			
			//Actualizamos el ESTADO del IDLOG a 1 
			tblAgendaLogVisitasRepo.actualizarEstadoAlGuardarPQR(usuario.getIdLocal(), IdUsuario, idLogObtenido);
			
			
			// ACTUALIZAR TblDctosOrdenes
			tblDctosOrdenesRepo.actualizarIdTipoOrden(usuario.getIdLocal(), IdUsuario, maximoIDORDEN);
			
			// ACTUALIZAR TblDctosOrdenesDetalle
			tblDctosOrdenesDetalleRepo.actualizarIdTipoOrdenDetalle(usuario.getIdLocal(), maximoIDORDEN);
			
			
			
			//Obtenemos el MAXIMO IdDto y le sumamos 1
			Integer maxIdDto = tblDctosService.findMaxIdDcto(usuario.getIdLocal()) + 1;
			
			

			 
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
			
			//Obtenemos la lista de ESTADO para validar si hay alguno en ESTADO = 9
			List<String> ListaEstados = tblDctosOrdenesDetalleService.ObtenerEstado9(usuario.getIdLocal());
			System.out.println("ListaEstados en /RespuestaPqr es " + ListaEstados);
			
			
			boolean hayEstado9 = false;

			for (String estado : ListaEstados) {
			    if ("9".equals(estado)) {
			        hayEstado9 = true;
			        break; // Terminamos el bucle si encontramos un estado 9
			    }
			}

			if (hayEstado9) {
			    System.out.println("Hay un estado en 9.");
			    
			  //Obtenemos de tblDctosOrdenesDetalle si hay un IDORDEN en estado 9 
				Integer IdOrdenEstado9 = tblDctosOrdenesDetalleService.ObtenerIdOrdenEstado9(usuario.getIdLocal());
				System.out.println("IdOrdenEstado9 en /RespuestaPqr es " + IdOrdenEstado9);
				
				
				// Obtenemos el NumeroOrden del IDORDEN
				Integer NumeroOrden  =  tblDctosOrdenesService.ObtenerNumeroOrden(usuario.getIdLocal(), IdOrdenEstado9);
				
				model.addAttribute("xidOrden", NumeroOrden);
			    
				Integer idClienteObtenido = tblDctosOrdenesService.ObtenerIdClientePorIdORden(usuario.getIdLocal(), IdOrdenEstado9);
				System.out.println("idClienteObtenido en /RespuestaPqr es " + idClienteObtenido);
				
				String idClienteObtenidoString = idClienteObtenido.toString();
				
				List<TblTercerosProjectionDTO> datosTerceros = tblTercerosService.obtenerDatosTercerosClientes(usuario.getIdLocal(), idClienteObtenido);
		    	System.out.println("Los datosTerceros en el if es: " + datosTerceros);
		    	
		    	TblTercerosProjectionDTO registroObtenido = datosTerceros.get(0);
		    	
		    	String xNombreTercero = registroObtenido.getNombreTercero();
		    	String xIdCliente = registroObtenido.getIdCliente();
		    	String xTelefono = registroObtenido.getTelefonoCelular();
		    	String xEmail = registroObtenido.getEmail();
		    	String xDireccion = registroObtenido.getDireccionTercero();

		    	
		    	//Le enviamos los valores a los campos en la vista RespuestaPQR
		    	model.addAttribute("xNombreTercero", xNombreTercero);
		    	model.addAttribute("xIdCliente", xIdCliente);
		    	model.addAttribute("xTelefono", xTelefono);
		    	model.addAttribute("xEmail", xEmail);
		    	model.addAttribute("xDireccion", xDireccion);
		    	
		    	
		    	String xComentario  = tblDctosOrdenesDetalleService.ObtenerComentarioRespuesta(usuario.getIdLocal(), IdOrdenEstado9, idClienteObtenido);
		    	System.out.println("EL xComentario es  " + xComentario);
		    	model.addAttribute("xComentario", xComentario);
		    	
		    	
		    	//Obtenemos la lista de los idclientes que tienen PQR ya creadas y NO respondidas ESTADO = 0
				List <String> ListaIdCLientes = tblDctosOrdenesService.ObtenerIdClienteIdTipoOrden17(usuario.getIdLocal());
				System.out.println("La lista de clientes es  " + ListaIdCLientes);
				
				//Obtenemos los datos de la lista de los idClientes ontenidos previamente
				List <TblTercerosProjectionDTO> datosListaTercerosClientes = tblTercerosService.obtenerDatosTercerosListaClientes(usuario.getIdLocal(), ListaIdCLientes);
				System.out.println("datosListaTercerosClientes " + datosListaTercerosClientes);
				
				ObjectMapper objectMappe = new ObjectMapper();
				
			    // Creamos una variable donde vamos a almacenar el Json
			    String xIdClientesJson;
			    try {
			    	xIdClientesJson = objectMappe.writeValueAsString(datosListaTercerosClientes);
			    } catch (JsonProcessingException e) {
			    	xIdClientesJson = "[]"; // En caso de error se crea un [] vacio
			    }
				
			    // Obtenemos la fecha y hora actual
			    Date fechaRadicacion = new Date(); 

			    // Formatea la fecha en el formato deseado
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			    String fechaFormateada = dateFormat.format(fechaRadicacion);

			    model.addAttribute("fechaRadicacion", fechaFormateada);
			    
			    ArrayList<TblPlus> xcategoria15 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 15);
				ArrayList<TblPlus> xcategoria16 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 16);
				System.out.println("xcategoria15 es : " + xcategoria15);
				System.out.println("xcategoria16 es : " + xcategoria16);
				
				model.addAttribute("xcategoria15", xcategoria15);
				model.addAttribute("xcategoria16", xcategoria16);
			    model.addAttribute("xnombreTecerosClientesJson", xIdClientesJson);
			    
			       //Obtenemos la lista de los IDORDEN (PQR) de cada IdCliente
	            List <String> ListaIdOrdenes = tblDctosOrdenesService.ObtenerListaIdOrden(usuario.getIdLocal(), idClienteObtenidoString);
	            System.out.println("ListaIdOrdenes desde //RespuestaPqr\" " + ListaIdOrdenes);
	            
	          

	            model.addAttribute("xListaIdOrdenes", ListaIdOrdenes);
		    	
			} else {
			    System.out.println("No hay estado en 9.");
			    
			  //Obtenemos la lista de los idclientes que tienen PQR ya creadas
				List <String> ListaIdCLientes = tblDctosOrdenesService.ObtenerIdClienteIdTipoOrden17(usuario.getIdLocal());
				System.out.println("La lista de clientes es  " + ListaIdCLientes);
				
				//Obtenemos los datos de la lista de los idClientes ontenidos previamente
				List <TblTercerosProjectionDTO> datosListaTercerosClientes = tblTercerosService.obtenerDatosTercerosListaClientes(usuario.getIdLocal(), ListaIdCLientes);
				System.out.println("datosListaTercerosClientes " + datosListaTercerosClientes);
				
				ObjectMapper objectMappe = new ObjectMapper();
				
			    // Creamos una variable donde vamos a almacenar el Json
			    String xIdClientesJson;
			    try {
			    	xIdClientesJson = objectMappe.writeValueAsString(datosListaTercerosClientes);
			    } catch (JsonProcessingException e) {
			    	xIdClientesJson = "[]"; // En caso de error se crea un [] vacio
			    }
				
			    // Obtenemos la fecha y hora actual
			    Date fechaRadicacion = new Date(); 

			    // Formatea la fecha en el formato deseado
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			    String fechaFormateada = dateFormat.format(fechaRadicacion);

			    model.addAttribute("fechaRadicacion", fechaFormateada);
			    
			    
			    ArrayList<TblPlus> xcategoria15 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 15);
				ArrayList<TblPlus> xcategoria16 = tblPlusService.ObtenerCategorias(usuario.getIdLocal(), 16);
				System.out.println("xcategoria15 es : " + xcategoria15);
				System.out.println("xcategoria16 es : " + xcategoria16);
				
				model.addAttribute("xcategoria15", xcategoria15);
				model.addAttribute("xcategoria16", xcategoria16);
			    model.addAttribute("xnombreTecerosClientesJson", xIdClientesJson);
				
			}
			
			

			
			
			
			return "pqr/RespuestaPqr";
		}
	}
	
	
	
	@PostMapping("/ObtenerInfoPQR")
    @ResponseBody
    public ResponseEntity <Map<String, Object>> ObtenerInfoPQR(@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		Integer IdUsuario = usuario.getIdUsuario();
		
		  Map<String, Object> response = new HashMap<>();
			
			
            // Obtenemos los datos del JSON recibido
            String codigoUsuario = (String) requestBody.get("codigoUsuario");//IdCliente
            System.out.println("codigoUsuario desde /ObtenerInfoPQR " + codigoUsuario);
            
            //Obtenemos la lista de los IDORDEN (PQR) de cada IdCliente
            List <String> ListaIdOrdenes = tblDctosOrdenesService.ObtenerListaIdOrden(usuario.getIdLocal(), codigoUsuario);
            System.out.println("ListaIdOrdenes desde /ObtenerInfoPQR " + ListaIdOrdenes);
            
          

            response.put("xListaIdOrdenes", ListaIdOrdenes);
            		    
            System.out.println("response en /ObtenerInfoPQR " + response);



            return ResponseEntity.ok(response);
		
    }
	
	@PostMapping("/MostarDatosPQR")
    @ResponseBody
    public ResponseEntity <Map<String, Object>> MostarDatosPQR(@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		Integer IdUsuario = usuario.getIdUsuario();
		
		  Map<String, Object> response = new HashMap<>();
			
	
            // Obtenemos los datos del JSON recibido
            String PqrSeleccionada = (String) requestBody.get("PqrSeleccionada");
		  	String codigoUsuario = (String) requestBody.get("codigoUsuario");// IdCliente
            System.out.println("PqrSeleccionada desde /ObtenerInfoPQR " + PqrSeleccionada);
            
            Integer PqrSeleccionadaInteger = Integer.parseInt(PqrSeleccionada);
     
            
            // Obtenemos el IDORDEN del numeroOrden seleccionado (PqrSeleccionadaInteger)
            Integer IdOrden  =  tblDctosOrdenesService.ObtenerIdOrden(usuario.getIdLocal(), PqrSeleccionadaInteger, codigoUsuario);
            
            List<TblDctosOrdenesDetalleDTO> InfoPQR = tblDctosOrdenesDetalleService.ObtenerInfoPQR(usuario.getIdLocal(), IdOrden);
            System.out.println("InfoPQR desde /ObtenerInfoPQR " + InfoPQR);
            
            //Obtenemos la fecha de radicación 
            String FechaRadicacion = tblDctosOrdenesService.ObtenerFechaRadicacion(usuario.getIdLocal(), IdOrden);
      
            

            response.put("xInfoPQR", InfoPQR);
            response.put("xFechaRadicacion", FechaRadicacion);
           		    
            System.out.println("response en /ObtenerInfoPQR " + response);



            return ResponseEntity.ok(response);
		
    }
	
	
	@PostMapping("/GuardarRespuestaTemporalPqr")
    @ResponseBody
    public ResponseEntity <Map<String, Object>> GuardarRespuestaTemporalPqr(@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		Integer IdUsuario = usuario.getIdUsuario();
		
		  Map<String, Object> response = new HashMap<>();
			
			
            // Obtenemos los datos del JSON recibido

		  String codigoUsuario = (String) requestBody.get("codigoUsuario"); // IdCliente
		  String descripcionRespuesta = (String) requestBody.get("descripcionRespuesta");
		  String idOrden = (String) requestBody.get("idOrden");
		 
		  
		  
		  Integer IDORDENInteger = Integer.parseInt(idOrden);
		  
		// Obtenemos el IDORDEN del numeroOrden seleccionado (PqrSeleccionadaInteger)
          Integer IdOrden  =  tblDctosOrdenesService.ObtenerIdOrden(usuario.getIdLocal(), IDORDENInteger, codigoUsuario);
		  
			//Obtenemos la lista de ESTADO para validar si hay alguno en ESTADO = 9
			List<String> ListaEstados = tblDctosOrdenesDetalleService.ObtenerEstado9(usuario.getIdLocal());
			System.out.println("ListaEstados en /RespuestaPqr es " + ListaEstados);
			
			
			boolean hayEstado9 = false;

			for (String estado : ListaEstados) {
			    if ("9".equals(estado)) {
			        hayEstado9 = true;
			        break; // Terminamos el bucle si encontramos un estado 9
			    }
			}

			if (hayEstado9) {
			    System.out.println("Hay un estado en 9.");
			    
			  //Obtenemos de tblDctosOrdenesDetalle si hay un IDORDEN en estado 9 
				Integer IdOrdenEstado9 = tblDctosOrdenesDetalleService.ObtenerIdOrdenEstado9(usuario.getIdLocal());
				System.out.println("IdOrdenEstado9 en /RespuestaPqr es " + IdOrdenEstado9);
				
				Integer idLogObtenido = tblDctosOrdenesService.ObtenerIdLog17(usuario.getIdLocal(), codigoUsuario, IdOrden);
				System.out.println("idLogObtenido en /GuardarRespuestaTemporalPqr: " + idLogObtenido);
				
				// Actualizamos el ESTADO a 9 y el Idusuario en tblAgendaLogVisitas
				tblAgendaLogVisitasRepo.actualizarEstadoAlGuardarRespuestaPQR(usuario.getIdLocal(), codigoUsuario, idLogObtenido, IdUsuario);
				
				//Eliminamos los registros del IDORDEN donde el item = 2
				tblDctosOrdenesDetalleRepo.eliminarRegistrosRespuesta(usuario.getIdLocal(), IdOrden);
				
				
			  	//Obtenemos los NombresPlus
			  	Map<String, String> nombrePluIdPluMap = tblPlusService.ObtenerNombrePluAndIdPlu(usuario.getIdLocal());
	            
	            // Creamos una lista de String para almacenar los valores
	            List<String> listaIdPlus = new ArrayList<>();

	            // Agregar los valores a la lista en el orden deseado
	            listaIdPlus.add((String) requestBody.get("tipoNotificacion"));
	            listaIdPlus.add((String) requestBody.get("tipoTramite"));


	            //Iteramos sobre la listaIdPlus
	            for(String idPlu : listaIdPlus) {
	            	
	            	// Obtenemos el nombrePlu correspondiente al idPlu actual
	                String nombrePlu = nombrePluIdPluMap.get(idPlu);
	            	
	            	// Convierte el String idPlu a Integer
	                Integer idPluInt = Integer.parseInt(idPlu);
	            	
	            	// Ingresamos la orden en TblDctosOrdenesDetalle
	                tblDctosOrdenesDetalleService.ingresarDetalleOrdenRespuesta(usuario.getIdLocal(), IdOrden, codigoUsuario, idPluInt, nombrePlu, descripcionRespuesta);
	            }
	            
	          //Obtenemos el MAXIMO IdDto y le sumamos 1
				Integer maxIdDto = tblDctosService.findMaxIdDcto(usuario.getIdLocal()) + 1;
				
				// Actualizamos el ESTADO a 9 
				tblDctosOrdenesDetalleRepo.actualizarEstadoDetalle(usuario.getIdLocal(), IdOrden);
	                        
				
	            response.put("xRadicadoRespuesta", maxIdDto);
	           		    
	            System.out.println("response en /ObtenerInfoPQR " + response);
				
				
			}else {
				
				Integer idLogObtenido = tblDctosOrdenesService.ObtenerIdLog17(usuario.getIdLocal(), codigoUsuario, IdOrden);
				System.out.println("idLogObtenido en /GuardarRespuestaTemporalPqr: " + idLogObtenido);
				
				// Actualizamos el ESTADO a 9 y el Idusuario en tblAgendaLogVisitas
				tblAgendaLogVisitasRepo.actualizarEstadoAlGuardarRespuestaPQR(usuario.getIdLocal(), codigoUsuario, idLogObtenido, IdUsuario);
			  
			  	//Obtenemos los NombresPlus
			  	Map<String, String> nombrePluIdPluMap = tblPlusService.ObtenerNombrePluAndIdPlu(usuario.getIdLocal());
			
	            // Creamos una lista de String para almacenar los valores
	            List<String> listaIdPlus = new ArrayList<>();

	            // Agregar los valores a la lista en el orden deseado
	            listaIdPlus.add((String) requestBody.get("tipoNotificacion"));
	            listaIdPlus.add((String) requestBody.get("tipoTramite"));

	            System.out.println("listaIdPlus en /GuardarRespuestaTemporalPqr: " + listaIdPlus);

	            //Iteramos sobre la listaIdPlus
	            for(String idPlu : listaIdPlus) {
	            	
	            	// Obtenemos el nombrePlu correspondiente al idPlu actual
	                String nombrePlu = nombrePluIdPluMap.get(idPlu);
	            	
	            	// Convierte el String idPlu a Integer
	                Integer idPluInt = Integer.parseInt(idPlu);

	            	
	            	// Ingresamos la orden en TblDctosOrdenesDetalle
	                tblDctosOrdenesDetalleService.ingresarDetalleOrdenRespuesta(usuario.getIdLocal(), IdOrden, codigoUsuario, idPluInt, nombrePlu, descripcionRespuesta);
	            }
	            
	          //Obtenemos el MAXIMO IdDto y le sumamos 1
				Integer maxIdDto = tblDctosService.findMaxIdDcto(usuario.getIdLocal()) + 1;
				
				// Actualizamos el ESTADO a 9 
				tblDctosOrdenesDetalleRepo.actualizarEstadoDetalle(usuario.getIdLocal(), IdOrden);
	                        
				
	            response.put("xRadicadoRespuesta", maxIdDto);
	           		    
	            System.out.println("response en /ObtenerInfoPQR " + response);

				
				
			}
		  

			


            return ResponseEntity.ok(response);
		
    }
	
	
	@PostMapping("/RespuestaPqr-post")
	public String RespuestaPqrPost (HttpServletRequest request,
			@RequestParam(value = "codigoUsuario", required = false) String codigoUsuario,
			@RequestParam(value = "PQRS", required = false) String PQRS,
			//@RequestParam("fechaRespuesta") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date fechaRespuesta,
			@RequestParam("fechaRespuesta") String fechaRespuestaStr, // Recibe como String
			@RequestParam("fechaNotificacion") String fechaNotificacionStr, // Recibe como String
			@RequestParam("FechatrasladoSSPD") String FechatrasladoSSPDStr, // Recibe como String
			@RequestParam(value = "subject", required = false) String subject,
			Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		if(usuario==null) {
			model.addAttribute("usuario",new Ctrlusuarios());
			
			return "index";
		}else { 
			Integer IdUsuario = usuario.getIdUsuario();
			
			System.out.println("FechatrasladoSSPDStr en /RespuestaPqr-post: " + FechatrasladoSSPDStr);
			
		     String fechaRespuestaFormateada = "";
		        String fechaNotificacionFormateada = "";
		        String FechatrasladoSSPDFormateada = "";

		        try {
		            // Convierte la cadena de fecha en formato "yyyy-MM-dd'T'HH:mm" a un objeto Date
		            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		            Date fechaRespuesta = inputDateFormat.parse(fechaRespuestaStr);
		            Date fechaNotificacion = inputDateFormat.parse(fechaNotificacionStr);

		            // Verifica si FechatrasladoSSPDStr no está vacío antes de intentar convertirlo
		            if (!FechatrasladoSSPDStr.isEmpty()) {
		                Date FechatrasladoSSPD = inputDateFormat.parse(FechatrasladoSSPDStr);
		                // Formatea la fecha en el formato "yyyy-MM-dd HH:mm:ss"
		                
		                
		                SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		                FechatrasladoSSPDFormateada = outputDateFormat.format(FechatrasladoSSPD);
		            }

		            // Formatea las fechas de respuesta y notificación
		            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		            fechaRespuestaFormateada = outputDateFormat.format(fechaRespuesta);
		            fechaNotificacionFormateada = outputDateFormat.format(fechaNotificacion);

		            System.out.println("fechaRespuesta en /RespuestaPqr-post: " + fechaRespuestaFormateada);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
						
			
	        System.out.println("fechaRespuestaFormatted en /RespuestaPqr-post: " + fechaRespuestaFormateada);
	        System.out.println("fechaNotificacionFormateada en /RespuestaPqr-post: " + fechaNotificacionFormateada);
	        System.out.println("FechatrasladoSSPDFormateada en /RespuestaPqr-post: " + FechatrasladoSSPDFormateada);
	        
	        
	        Timestamp xFechatrasladoSSPD = null;
	        
	        try {
	        // Convertimos la cadena FechatrasladoSSPDFormateada en un objeto Timestamp
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date parsedDate = sdf.parse(FechatrasladoSSPDFormateada);
	         xFechatrasladoSSPD = new Timestamp(parsedDate.getTime());
	        
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
	
	        System.out.println("xFechatrasladoSSPD en /RespuestaPqr-post: " + xFechatrasladoSSPD);
			
			Integer PQRSIntger = Integer.parseInt(PQRS);
			
			// Obtenemos el IDORDEN del numeroOrden seleccionado (PQRSIntger)
            Integer IdOrden  =  tblDctosOrdenesService.ObtenerIdOrden(usuario.getIdLocal(), PQRSIntger, codigoUsuario);
			
			//Actualizamos en tblDctosOrdenesDetalle el ESTADO del IDORDEN a = 1
			tblDctosOrdenesDetalleRepo.actualizarEstadoDetalleFinal(usuario.getIdLocal(), IdOrden, codigoUsuario);
			
			Integer IdLog =  tblDctosOrdenesRepo.ObtenerIdLog17(usuario.getIdLocal(), codigoUsuario, IdOrden);
			System.out.println("IdLog en /RespuestaPqr-post: " + IdLog);
			
			//Actualizamos el Estado a 1 en tblAgendaLogVisitas
			tblAgendaLogVisitasRepo.actualizarEstadoAlFinalizarRespuesta(usuario.getIdLocal(), codigoUsuario, IdLog);
			
			//Actualizamos el ESTADO a 1 en TblDctosOrdenes
			tblDctosOrdenesRepo.actualizarEstadoA1(usuario.getIdLocal(), IdOrden);
			

			// Actualizamos la FECHAENTREGA en TblDctosOrdenes
			tblDctosOrdenesRepo.actualizarFECHAENTREGA(usuario.getIdLocal(), IdOrden, xFechatrasladoSSPD);
	
			
			//Obtenemos el MAXIMO IdDto y le sumamos 1
			Integer maxIdDto = tblDctosService.findMaxIdDcto(usuario.getIdLocal()) + 1;
			
			//Ingresamos el nuevo Dcto
			tblDctosService.ingresarDto(usuario.getIdLocal(), IdOrden, maxIdDto, codigoUsuario, IdUsuario, fechaRespuestaFormateada, fechaNotificacionFormateada);
			

			model.addAttribute("success", "Respuesta de la PQR " + PQRSIntger +  " ingresada correctamente");
		}
		
		
		model.addAttribute("url", "/");
		
		return "defaultSuccess";
	}
	
	
}
