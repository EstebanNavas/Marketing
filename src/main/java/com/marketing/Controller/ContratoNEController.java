package com.marketing.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.servlet.ModelAndView;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblCiudadesDTO;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
import com.marketing.Projection.TblPlusDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoAjusteConsumoCliente;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaCredito;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaNE;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.UtilidadesIP;

@Controller
public class ContratoNEController {

	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblDctosService TblDctosService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	TblPagosService tblPagosService;
	
	@Autowired
	TblMedidoresMacroService tblMedidoresMacroService;
	
	@Autowired
	TblTipoCausaNotaService tblTipoCausaNotaService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblPlusService tblPlusService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	ProcesoCreaLecturaMovil procesoCreaLecturaMovil;
	
	@Autowired
	ProcesoGuardaLecturaMovil procesoGuardaLecturaMovil;
	
	@Autowired
	ProcesoGuardaPluOrden procesoGuardaPluOrden;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	ProcesoGuardaNE procesoGuardaNE;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	
	
	
	@GetMapping("/ContratoNE")
	public String contratoNE(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				
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

				
				// Se le pasa un String vacio para buscar todos 
				String nombreTercero = "";
				
				nombreTercero = "'%" + nombreTercero.trim().toUpperCase() + "%'";
				
				Integer idTipoTercero = 3;
				
				List<TercerosDTO2> ListaTercerosEmpleados = tblTercerosService.ListaTercerosEmpleados(idLocal);
				
				System.out.println("ListaTercerosEmpleados " + ListaTercerosEmpleados);
				
				for (TercerosDTO2 tercero : ListaTercerosEmpleados ) {
					
					System.out.println("Nombre " + tercero.getNombreTercero());
					
				}
				
				
				
				
				model.addAttribute("xListaTerceros", ListaTercerosEmpleados);
               

				
		
		return "NominaElectronica/ContratoNE";
	}
	
	
	
	@PostMapping("/TraerTodosEmpleadosNE")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> TraerTodosEmpleadosNE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");


	    System.out.println("SI ENTRÓ A  /TraerTodosEmpleadosNE");

	    
	    int idLocal = usuario.getIdLocal();
		Integer IdUsuario = usuario.getIdUsuario();

		Integer idTipoOrden = 8;


		List<TercerosDTO2> ListaTercerosEmpleados = tblTercerosService.ListaTercerosEmpleados(idLocal);

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("ListaBusqueda", ListaTercerosEmpleados);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	@PostMapping("/BuscarEmpleadosNE")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarEmpleadosNE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");


	    System.out.println("SI ENTRÓ A  /BuscarEmpleadosNE");
	    
	   // Obtenemos los datos del JSON recibido
        String palabraClave = (String) requestBody.get("palabraClave");

	    
	    int idLocal = usuario.getIdLocal();
		Integer IdUsuario = usuario.getIdUsuario();

		Integer idTipoOrden = 8;


	     List<TercerosDTO2> listaContratoNEBusqueda = tblTercerosService.listaContratoNEAllBusqueda(idLocal, idTipoOrden, palabraClave);
		
		System.out.println("listaContratoNEBusqueda " + listaContratoNEBusqueda);
		
		for (TercerosDTO2 Contrato : listaContratoNEBusqueda ) {
			
			System.out.println("Nombre " + Contrato.getNombreTercero());
			
		}

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("ListaBusqueda", listaContratoNEBusqueda);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	@PostMapping("/TraerEmpleadoNE-Post")
	public ModelAndView TraerEmpleadoNE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerEmpleado");

	    // Obtenemos los datos del JSON recibido
	    String idTercero = (String) requestBody.get("idTercero");
	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerEmpleadoNE?idTercero=" + idTercero);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerEmpleadoNE")
	public String TraerEmpleadoNE(@RequestParam(name = "idTercero", required = false) String idTercero, HttpServletRequest request, Model model) {
	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerEmpleadoNE con idTercero: " + idTercero);
		
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
		
		Integer idTipoOrden = 8;

			
			
			if(idTercero == null) {
				
				// Obtener las variables de la sesión
				idTercero =  (String) session.getAttribute("xIdCliente");
				
			}
			
			
			// Obtener las variables de la sesión
		    String xIdPlu =  (String) session.getAttribute("xIdPlu");
			
			System.out.println("Entró a /TraerEmpleado");
		    
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");

		    
		    List<TercerosDTO2> detalleContrato = tblTercerosService.listaDetalleContratoFCH(usuario.getIdLocal(), idTipoOrden, idTercero);
		    
          for(TercerosDTO2 tercero : detalleContrato) {
		    	
		    	System.out.println("xInformacionTercero nombre = " + tercero.getNombreTercero());
		    	model.addAttribute("xnombreTercero", tercero.getNombreTercero());
		    	model.addAttribute("xnuid", tercero.getIdCliente());
		    	model.addAttribute("xcodigoAlterno", tercero.getCodigoAlterno());
		    	model.addAttribute("xccNit", tercero.getCC_Nit());
		    	model.addAttribute("xdireccionPredio", tercero.getDireccionTercero());
		    	model.addAttribute("xIdMedio", tercero.getIdMedio());
		    	
		    	model.addAttribute("xEntidadMedio", tercero.getEntidadMedio());
		    	model.addAttribute("xCuentaMedio", tercero.getCuentaMedio());
		    	
		    	
		    	String FechaInicial = tercero.getFechaInicioContrato();		    	
		    	DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");		    	
		    	LocalDateTime fechaInicio = LocalDateTime.parse(FechaInicial, formatterEntrada);		    	
		    	DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String FechInicialFormateada = fechaInicio.format(formatterAct);
		    	
		    	model.addAttribute("xFechaInicio", FechInicialFormateada);

		    	String FechaFinal = tercero.getFechaFinContrato();	
		    	LocalDateTime fechaFin = LocalDateTime.parse(FechaFinal, formatterEntrada);
		        String FechaFinalFormateada = fechaFin.format(formatterAct);
		    	
		    	model.addAttribute("xFechaFin", FechaFinalFormateada);
		    	
		    	
		    	model.addAttribute("xVrBasico", tercero.getVrSalarioBasico());
		    	model.addAttribute("xVrAuxilioTransporte", tercero.getVrSubsidioTransporte());

		    	
		    	
		    }
          
          int idLinea = 100;
          
          int tipoTrabajador = 1000;
          
          Integer idOrden = 0;
          
           List<TercerosDTO2> ListaTipoTrabajador = tblTercerosService.listaDetalleContratoFCHPorCategoria(usuario.getIdLocal(), idTipoOrden, idTercero, tipoTrabajador);
           System.out.println("ListaTipoTrabajador es " + ListaTipoTrabajador);
           
           for(TercerosDTO2 contrato : ListaTipoTrabajador) {

        	   List<TblPlusDTO>  ListaPlus = tblPlusService.listaPluCategoriaTipoNE(idLinea, contrato.getIdCategoria(), usuario.getIdLocal(), contrato.getIDPLU());
        	   model.addAttribute("xListaPlusTipoTrabajador", ListaPlus);
        	   model.addAttribute("xIdPlu", contrato.getIDPLU());
        	   
        	   idOrden = contrato.getIdOrden();
           }
           
           
           // Si el Empleado es nuevo y no tiene idOrden          
           if(idOrden.equals(0)) {
        	   
        	   int periodoNomina = 1001;
        	   int tipoContrato = 1002;
        	   int subIipoTrabajador = 1003;
        	   
        	   
        	   
        	   List<TercerosDTO> ListaBusqueda = tblTercerosService.BuscarTercerosEmpleadosNUID(usuario.getIdLocal(), idTercero);
        	   
        	   for(TercerosDTO tercero : ListaBusqueda) {
        		   
        		model.addAttribute("xnombreTercero", tercero.getNombreTercero());
   		    	model.addAttribute("xnuid", tercero.getIdCliente());
   		    	model.addAttribute("xcodigoAlterno", tercero.getCodigoAlterno());
   		    	model.addAttribute("xccNit", tercero.getCC_Nit());
   		    	System.out.println("xccNit es " + tercero.getCC_Nit());
   		    	model.addAttribute("xdireccionPredio", tercero.getDireccionTercero());
        		   
        	   }
        	   
        	   
        	   List<TblPlusDTO> xListaPlusTipoTrabajador = tblPlusService.ObtenerPlusxCategoria(usuario.getIdLocal(), tipoTrabajador);
        	   model.addAttribute("xListaPlusTipoTrabajador", xListaPlusTipoTrabajador);
        	   
        	   List<TblPlusDTO> xListaPlusPeriodoNomina = tblPlusService.ObtenerPlusxCategoria(usuario.getIdLocal(), periodoNomina);
        	   model.addAttribute("xListaPlusPeriodoNomina", xListaPlusPeriodoNomina);
        	   
        	   List<TblPlusDTO> xListaPlusTipoContrato = tblPlusService.ObtenerPlusxCategoria(usuario.getIdLocal(), tipoContrato);
        	   model.addAttribute("xListaPlusTipoContrato", xListaPlusTipoContrato);
        	   
        	   List<TblPlusDTO> xListaPluSubIipoTrabajador = tblPlusService.ObtenerPlusxCategoria(usuario.getIdLocal(), subIipoTrabajador);
        	   model.addAttribute("xListaPluSubIipoTrabajador", xListaPluSubIipoTrabajador);
        	   
           }
           
           
           //System.out.println("IDORDEN ES " + idOrden);
           
           
           int periodoNomina = 1001 ;
           
           List<TercerosDTO2> ListaPeriodoNomina = tblTercerosService.listaDetalleContratoFCHPorCategoria(usuario.getIdLocal(), idTipoOrden, idTercero, periodoNomina);
          
           for(TercerosDTO2 contrato : ListaPeriodoNomina) {

        	   List<TblPlusDTO>  ListaPlus = tblPlusService.listaPluCategoriaTipoNE(idLinea, contrato.getIdCategoria(), usuario.getIdLocal(), contrato.getIDPLU());
        	   model.addAttribute("xListaPlusPeriodoNomina", ListaPlus);
        	   model.addAttribute("xIdPlu", contrato.getIDPLU());
           }
           
           
           int tipoContrato = 1002;
           
           List<TercerosDTO2> ListaTipoContrato = tblTercerosService.listaDetalleContratoFCHPorCategoria(usuario.getIdLocal(), idTipoOrden, idTercero, tipoContrato);
          
           for(TercerosDTO2 contrato : ListaTipoContrato) {
        	   
        	   
        	   List<TblPlusDTO>  ListaPlus = tblPlusService.listaPluCategoriaTipoNE(idLinea, contrato.getIdCategoria(), usuario.getIdLocal(), contrato.getIDPLU());
        	   model.addAttribute("xListaPlusTipoContrato", ListaPlus);
        	   model.addAttribute("xIdPlu", contrato.getIDPLU());
           }
           
           
           int subIipoTrabajador = 1003;
           
           List<TercerosDTO2> ListaSubIipoTrabajador = tblTercerosService.listaDetalleContratoFCHPorCategoria(usuario.getIdLocal(), idTipoOrden, idTercero, subIipoTrabajador);
           
           //int idOrden = 0;
          
           for(TercerosDTO2 contrato : ListaSubIipoTrabajador) {

        	   //idOrden = contrato.getIdOrden();
        	   
        	   List<TblPlusDTO>  ListaPlus = tblPlusService.listaPluCategoriaTipoNE(idLinea, contrato.getIdCategoria(), usuario.getIdLocal(), contrato.getIDPLU());
        	   model.addAttribute("xListaPluSubIipoTrabajador", ListaPlus);
        	   model.addAttribute("xIdPlu", contrato.getIDPLU());
           }
          
           
          
          
           List<TblDctosOrdenesDetalleDTO2> listaContrato = tblDctosOrdenesDetalleService.listaContratoNE(usuario.getIdLocal(), idTipoOrden, idOrden);
           model.addAttribute("xListaContrato", listaContrato);
           
           
           model.addAttribute("xIdOrden", idOrden);
           
		    
         // Limpiar las variables anteriores de la sesión
         session.removeAttribute("xIdPlu");

			
			return "NominaElectronica/ModificarContrato";


	}
	
	
	
	
	@PostMapping("/ModificarContrato")
	@ResponseBody
	public ResponseEntity<Map<String, String>> ModificarContrato(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ModificarContrato");
	    
	    HttpSession session = request.getSession();
	    
	    Integer IdUsuario = usuario.getIdUsuario();
	    Integer idLocal = usuario.getIdLocal();

	    // Obtenemos los datos del JSON recibido
	    @SuppressWarnings("unchecked")
        List<String> xArrIdPlu = (List<String>) requestBody.get("arrIdPlu");
        System.out.println("xArrIdPlu es : " + xArrIdPlu);
        
   
	    String xIdCliente = (String) requestBody.get("idCliente");

	    
	    String xFechaInicioContrato = (String) requestBody.get("FechaInicio");
	    String xFechaFinContrato = (String) requestBody.get("FechaFin");
	    String xVrSalarioBasico = (String) requestBody.get("vrSalarioBasico");
	    Double xVrSalarioBasicoDouble =  Double.parseDouble(xVrSalarioBasico);
	    
	    
	    String xVrSubsidioTransporte = (String) requestBody.get("vrSubsidioTransporte");
	    Double xVrSubsidioTransporteDouble =  Double.parseDouble(xVrSubsidioTransporte);
	    
	    
	    String xIdMedio = (String) requestBody.get("idMedio");
	    Integer xIdMedioInt = Integer.parseInt(xIdMedio);
	    
	    
	    String xEntidadMedio = (String) requestBody.get("entidadMedio");
	    String xCuentadMedio = (String) requestBody.get("cuentaMedio");
	    
        

        String[] xIdPlu = xArrIdPlu.toArray(new String[0]);

  
        List<Integer> xIdPluInNE = new ArrayList<>();
        
       // Convertimos el Array de Strings a Arrays de Enteros
        for (String plu : xIdPlu) {
        	xIdPluInNE.add(Integer.parseInt(plu.trim()));
        	
        }
        
        xIdPluInNE.add(0);
        
        System.out.println("Lista de enteros " + xIdPluInNE);
        
        
        String xObservacion = "Identificador nomina electronica";

       
        
        Integer idTipoOrden = 8;
        
        List<TercerosDTO2> detalleContrato = tblTercerosService.listaDetalleContratoFCH(idLocal, idTipoOrden, xIdCliente);
        
        int xIdLogMAX = 0;
        
        for(TercerosDTO2 contrato : detalleContrato) {
        	
        	xIdLogMAX = contrato.getIdLog();
        }
        
        int xIdTipoOrdenNE = 8;
        
        
        
        procesoGuardaNE.modificaContrato(xIdLogMAX, xIdPluInNE, xIdTipoOrdenNE, IdUsuario, idLocal, xIdCliente, xFechaInicioContrato,
        		xFechaFinContrato, xVrSalarioBasicoDouble, xVrSubsidioTransporteDouble, xObservacion, xIdMedioInt, xEntidadMedio, xCuentadMedio);
        
        System.out.println("modificaContrato OK");
        
        
        // Guardar las variable en la sesión
	    session.setAttribute("xIdCliente", xIdCliente);


        Map<String, String> response = new HashMap<>();
        response.put("message", "OK");

        return ResponseEntity.ok(response);
	}
	
	
	
	@PostMapping("/GuardarContratoNE")
	@ResponseBody
	public ResponseEntity<Map<String, String>> GuardarContratoNE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /GuardarContrato");
	    
	    HttpSession session = request.getSession();
	    
	    Integer IdUsuario = usuario.getIdUsuario();
	    Integer idLocal = usuario.getIdLocal();

	    // Obtenemos los datos del JSON recibido
	    @SuppressWarnings("unchecked")
        List<String> xArrIdPlu = (List<String>) requestBody.get("arrIdPlu");
        System.out.println("xArrIdPlu es : " + xArrIdPlu);
        
   
	    String xIdCliente = (String) requestBody.get("idCliente");

	    
	    String xFechaInicioContrato = (String) requestBody.get("FechaInicio");
	    String xFechaFinContrato = (String) requestBody.get("FechaFin");
	    String xVrSalarioBasico = (String) requestBody.get("vrSalarioBasico");
	    Double xVrSalarioBasicoDouble =  Double.parseDouble(xVrSalarioBasico);
	    
	    
	    String xVrSubsidioTransporte = (String) requestBody.get("vrSubsidioTransporte");
	    Double xVrSubsidioTransporteDouble =  Double.parseDouble(xVrSubsidioTransporte);
	    
	    
	    String xIdMedio = (String) requestBody.get("idMedio");
	    Integer xIdMedioInt = Integer.parseInt(xIdMedio);
	    
	    
	    String xEntidadMedio = (String) requestBody.get("entidadMedio");
	    String xCuentadMedio = (String) requestBody.get("cuentaMedio");
	    
        

        String[] xIdPlu = xArrIdPlu.toArray(new String[0]);

  
        List<Integer> xIdPluInNE = new ArrayList<>();
        
       // Convertimos el Array de Strings a Arrays de Enteros
        for (String plu : xIdPlu) {
        	xIdPluInNE.add(Integer.parseInt(plu.trim()));
        	
        }
        
        xIdPluInNE.add(0);
        
        System.out.println("Lista de enteros " + xIdPluInNE);     
        String xObservacion = "Identificador nomina electronica";
        
        int xEstadoActivo = 9;
        int xEstadoSuspendido = 8;
        int xEstadoAtendido = 1;
        int xIdTipoTerceroNE = 3;
        int xEstadoPeriodoActivo = 1;
        int xIdTipoOrdenNE = 8;
        
     // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);

        
        tblAgendaLogVisitasRepo.actualizaLogNE(xEstadoAtendido, xIdCliente, xEstadoSuspendido);

        
        int xIdLogMAX = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
        
//        (int idLog, String idCliente, int idUsuario, int idLocalTercero, int idLocal, int idPeriodo, String fechaVisita,
//  			  int idEstadoVisita, int estado, int idTipoOrden, String fechaTxInicio)
        
        tblAgendaLogVisitasRepo.ingresaLogVisita(xIdLogMAX, xIdCliente, IdUsuario, idLocal, idLocal, 0,strFechaVisita, 
        		xEstadoAtendido, xEstadoActivo, xIdTipoOrdenNE, strFechaVisita);
         
        
       
        
        System.out.println("xIdLogMAX " + xIdLogMAX);  
        
        
//        (int xIdLog,
//    			List<Integer> xIdPluInNE,          
//                Integer xIdTipoOrden,
//                Integer xIdUsuario,
//                int xIdLocalUsuario,
//                String xIdCliente,
//                String xFechaInicioContrato,
//                String xFechaFinContrato,                        
//                double xVrSalarioBasico,
//                double xVrSubsidioTransporte,
//                String xObservacion,
//                int xIdMedio,
//                String xEntidadMedio,
//                String xCuentadMedio)
        
        
        
        procesoGuardaNE.guardaContrato(xIdLogMAX, xIdPluInNE, xIdTipoOrdenNE, IdUsuario, idLocal, xIdCliente, xFechaInicioContrato,
        	                    	xFechaFinContrato, xVrSalarioBasicoDouble, xVrSubsidioTransporteDouble, xObservacion, xIdMedioInt, xEntidadMedio, xCuentadMedio);
        
        System.out.println("Contrato guardado OK");
        
        int estadoVisita = 9;
        int estadoSuspendido = 8;
        int xIdEstadoVisita = 1;
        
        
        
        tblAgendaLogVisitasRepo.actualizaVisita(estadoSuspendido, strFechaVisita, IdUsuario, estadoVisita);
        
        
        // Guardar las variable en la sesión
	    session.setAttribute("xIdCliente", xIdCliente);


        Map<String, String> response = new HashMap<>();
        response.put("message", "OK");

        return ResponseEntity.ok(response);
	}
	
	
	
	
	
	@GetMapping("/PagoNE")
	public String pagoNE(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				
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

			
	
		
		return "NominaElectronica/PagoNEConsulta";
	}
	
	
	
	
	@PostMapping("/TraerListaPagosNE")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> TraerListaPagosNE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");


	    System.out.println("SI ENTRÓ A  /TraerListaPagosNE");
	    
	   // Obtenemos los datos del JSON recibido
        String xFechaDcto = (String) requestBody.get("xFechaDcto");

	    
	    int idLocal = usuario.getIdLocal();
		Integer IdUsuario = usuario.getIdUsuario();

		Integer idTipoOrden = 8;


		List<TblDctosOrdenesDTO> PagoNEAll = tblDctosOrdenesService.listaPagoNEAll(idLocal, idTipoOrden, xFechaDcto);

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("ListaBusqueda", PagoNEAll);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	
	
	
	
	@GetMapping("/MostrarEmpleadosPagoNE")
	public String mostrarEmpleadosPagoNE(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				
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

				
				// Se le pasa un String vacio para buscar todos 
				String nombreTercero = "";
				
				nombreTercero = "'%" + nombreTercero.trim().toUpperCase() + "%'";
				
				Integer idTipoTercero = 3;
				
				List<TercerosDTO2> ListaTercerosEmpleados = tblTercerosService.ListaTercerosEmpleados(idLocal);
				
				System.out.println("ListaTercerosEmpleados " + ListaTercerosEmpleados);
				
				for (TercerosDTO2 tercero : ListaTercerosEmpleados ) {
					
					System.out.println("Nombre " + tercero.getNombreTercero());
					
				}
				
				
				
				
				model.addAttribute("xListaTerceros", ListaTercerosEmpleados);
	
		
		return "NominaElectronica/PagoNE";
	}
	
	
	
	
	@PostMapping("/TraerPagoEmpleadoNE-Post")
	public ModelAndView TraerPagoEmpleadoNE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerPagoEmpleadoNE");

	    // Obtenemos los datos del JSON recibido
	    String idTercero = (String) requestBody.get("idTercero");
	    


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerPagoEmpleadoNE?idTercero=" + idTercero);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerPagoEmpleadoNE")
	public String TraerPagoEmpleadoNE(@RequestParam(name = "idTercero", required = false) String idTercero, HttpServletRequest request, Model model) {
	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerPagoEmpleadoNE con idTercero: " + idTercero);
		
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
		
		Integer idTipoOrden = 8;
			
			// Obtener las variables de la sesión
		    String xIdPlu =  (String) session.getAttribute("xIdPlu");

		    
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");

		    
		    List<TercerosDTO2> detalleContrato = tblTercerosService.listaDetalleContratoFCH(usuario.getIdLocal(), idTipoOrden, idTercero);
		    
		    int idOrden = 0;
		    String idCliente = "";
		    
          for(TercerosDTO2 tercero : detalleContrato) {
        	  
        	  idOrden = tercero.getIdOrden();
        	  idCliente = tercero.getIdCliente();
        	  
		    	System.out.println("xInformacionTercero nombre = " + tercero.getNombreTercero());
		    	model.addAttribute("xnombreTercero", tercero.getNombreTercero());
		    	model.addAttribute("xnuid", tercero.getIdCliente());
		    	model.addAttribute("xcodigoAlterno", tercero.getCodigoAlterno());
		    	model.addAttribute("xccNit", tercero.getCC_Nit());
		    	model.addAttribute("xdireccionPredio", tercero.getDireccionTercero());
		    	model.addAttribute("xIdMedio", tercero.getIdMedio());
		    	
		    	model.addAttribute("xEntidadMedio", tercero.getEntidadMedio());
		    	model.addAttribute("xCuentaMedio", tercero.getCuentaMedio());
		    	
		    	
		    	String FechaInicial = tercero.getFechaInicioContrato();		    	
		    	DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");		    	
		    	LocalDateTime fechaInicio = LocalDateTime.parse(FechaInicial, formatterEntrada);		    	
		    	DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String FechInicialFormateada = fechaInicio.format(formatterAct);
		    	
		    	model.addAttribute("xFechaInicio", FechInicialFormateada);

		    	String FechaFinal = tercero.getFechaFinContrato();	
		    	LocalDateTime fechaFin = LocalDateTime.parse(FechaFinal, formatterEntrada);
		        String FechaFinalFormateada = fechaFin.format(formatterAct);
		    	
		    	model.addAttribute("xFechaFin", FechaFinalFormateada);
		    	
		    	
		    	model.addAttribute("xVrBasico", tercero.getVrSalarioBasico());
		    	model.addAttribute("xVrAuxilioTransporte", tercero.getVrSubsidioTransporte());

		    	
		    	
		    }
          
          

          
          
          System.out.println("IDORDEN ES " + idOrden);

          
          int idLinea = 100;

          
          String xIdCategoriaDevenga = "1400";
          String xIdCategoriaDeduce = "1800";
          
          
          
          List<TblPlusDTO> listaCategoriaDevenga = tblPlusService.listaPluCategoriaNE(usuario.getIdLocal(), idLinea, xIdCategoriaDevenga, idOrden);
          model.addAttribute("xlistaCategoriaDevenga", listaCategoriaDevenga);
          
          for(TblPlusDTO listaDevenga : listaCategoriaDevenga) {
        	  
        	  System.out.println("vrGeneral ES " + listaDevenga.getVrGeneral());
          }
          
          
          List<TblPlusDTO> listaCategoriaDeducible = tblPlusService.listaPluCategoriaNE(usuario.getIdLocal(), idLinea, xIdCategoriaDeduce, idOrden);
          model.addAttribute("xlistaCategoriaDeducible", listaCategoriaDeducible);
          
          
          
           
           
           model.addAttribute("xIdOrden", idOrden);
           model.addAttribute("xIdCliente", idCliente);
           
		    
         // Limpiar las variables anteriores de la sesión
         session.removeAttribute("xIdPlu");

			
			return "NominaElectronica/ModificaPago";


	}
	
	
	
	@PostMapping("/NuevoPagoNE-Post")
	@ResponseBody
	public ResponseEntity<Map<String, String>> NuevoPagoNE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /NuevoPagoNE-Post");
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();

	    
	    
	    session.removeAttribute("idCliente");
	    session.removeAttribute("idOrden");
	    session.removeAttribute("idDcto");
	    session.removeAttribute("idPluArr");
	    session.removeAttribute("CantidadArr");
	    session.removeAttribute("VrUnitarioArr");

	    // Obtenemos los datos del JSON recibido
	    String idCliente = (String) requestBody.get("idCliente");
	    String idOrden = (String) requestBody.get("idOrden");
	    String idDcto = (String) requestBody.get("idDcto");
	    
	    
	    @SuppressWarnings("unchecked")
	    List<String> xidPluArr = (List<String>) requestBody.get("xidPluArr");

    	// Obtener los valores de los arrays
	    @SuppressWarnings("unchecked")
	    List<String> xCantidadArr = (List<String>) requestBody.get("xCantidadArr");

	    
	    @SuppressWarnings("unchecked")
	    List<String> xVrUnitarioArr = (List<String>) requestBody.get("xVrUnitarioArr");
	    
	    
	    
	    String[] idPluArr = xidPluArr.toArray(new String[0]);
	    String[] CantidadArr = xCantidadArr.toArray(new String[0]);
	    String[] VrUnitarioArr = xVrUnitarioArr.toArray(new String[0]);
	    
	    
	    // Guardar las listas en la sesión
	    session.setAttribute("idCliente", idCliente);
	    session.setAttribute("idOrden", idOrden);
	    session.setAttribute("idDcto", idDcto);
	    session.setAttribute("idPluArr", idPluArr);
	    session.setAttribute("CantidadArr", CantidadArr);
	    session.setAttribute("VrUnitarioArr", VrUnitarioArr);


	    // Redirige a la vista y le pasamos el parametro de idDcto
	    //ModelAndView modelAndView = new ModelAndView("redirect:/Confirmar?idDcto=" + idDcto );
	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./NuevoPagoNE");

        return ResponseEntity.ok(response);
	}
	
	
	
	
	@GetMapping("/NuevoPagoNE")
	public String NuevoPagoNE( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		Integer IdUsuario = usuario.getIdUsuario();
		
		 
		 
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
    
		Integer idUsuario = (Integer) session.getAttribute("xidUsuario");        
		    
		 
		 // Obtener las listas de la sesión
		    String idCliente = (String) session.getAttribute("idCliente");
		    String idOrden = (String) session.getAttribute("idOrden");
		 	String[] xIdPlu = (String[]) session.getAttribute("idPluArr");
		 	String[] xCantidadArr =  (String[]) session.getAttribute("CantidadArr");
		 	String[] xVrUnitarioArr =  (String[]) session.getAttribute("VrUnitarioArr");
		    
	
		System.out.println("Entró a NuevoPagoNE con idOrden: " + idOrden);
		System.out.println("Entró a NuevoPagoNE con idCliente: " + idCliente);

		
		

	    
	    // Imprimir los arreglos
	    System.out.println("Dividido xIdPlu:");
	    for (String elemento : xIdPlu) {
	        System.out.println(elemento);
	    }

	    System.out.println("Dividido xCantidadArr:");
	    for (String elemento : xCantidadArr) {
	        System.out.println(elemento);
	    }
	    
	    System.out.println("Dividido xVrUnitarioArr:");
	    for (String elemento : xVrUnitarioArr) {
	        System.out.println(elemento);
	    }
		


		    
		    int idTipoOrden = 8;
		    int xIdTipoOrdenNETemporal = idTipoOrden + 50;
		    
		    
         List<TercerosDTO2> detalleContrato = tblTercerosService.listaDetalleContratoFCH(usuario.getIdLocal(), idTipoOrden, idCliente);

         String xFechaInicioContrato = "";
         int xIdOrdenNE = 0;
         int xIdContrato = 0;
		    
          for(TercerosDTO2 tercero : detalleContrato) {
        	  
        	  xIdOrdenNE = tercero.getIdOrden();
        	  xIdContrato = tercero.getIdContrato();
        	  xFechaInicioContrato = tercero.getFechaInicioContrato();
		    	
		    }
          
          
          
          int xEstadoActivo = 9;
          int xEstadoAtendido = 1;
          int xIdTipoOrdenPedido = 9;
          
          
          // Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        String strFechaVisita = fechaActual.format(formatter);
        
          
          
          //Obtenemos el idlog Actual 
          List<TblAgendaLogVisitas> visita = tblAgendaLogVisitasService.seleccionaVisitaEstadoxFecha(xEstadoActivo, strFechaVisita, idUsuario, usuario.getIdLocal());
	
			Integer xIdLogActivo = 0;

	
			for(TblAgendaLogVisitas V : visita) {				
				xIdLogActivo = V.getIDLOG();
			}
			
			System.out.println("xIdLogActivo es " + xIdLogActivo);
			
			if(xIdLogActivo == null || xIdLogActivo == 0 ) {
				
				xIdLogActivo = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
		      	   
	            // Obtenemos el periodo activo
	       			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(usuario.getIdLocal());
	       			
	       			Integer idPeriodo = 0;
	       			
	       			int xIdTipoTerceroCliente = 1;
	       			
	       			for(TblDctosPeriodo P : PeriodoActivo) {
	       				
	       				idPeriodo = P.getIdPeriodo();
	       			
	       			}
	       			
	       		 

	  	    
	  	      tblAgendaLogVisitasRepo.ingresaLogVisita(xIdLogActivo, idCliente, IdUsuario, usuario.getIdLocal(), usuario.getIdLocal(), idPeriodo, strFechaVisita,
	  				xEstadoAtendido, xEstadoActivo, xIdTipoOrdenPedido, strFechaVisita);
				
			}
		    
		    
			System.out.println("xIdLogActivo despues de ingresaLogVisita es " + xIdLogActivo);
			
			
			int xIdOrdenMax = 0;
		    
			// No existe orden --> NUEVO
		    if(xIdLogActivo != null || xIdLogActivo != 0) {
		    	
		    	System.out.println("Entró al if porque xIdLogActivo es valido ");
		    	
		    	
		    	String xObservacion = "Identificador nomina electronica";
		    	
		    	
		    	//Aca ProcesoGuardaNE-----------------------------
		    	 xIdOrdenMax = procesoGuardaNE.guardaPago(xIdLogActivo, xIdOrdenNE, idTipoOrden, IdUsuario, 
		    			usuario.getIdLocal(), idCliente, xFechaInicioContrato, xObservacion, xIdContrato);
		    	
		    	
		    	 for (int i = 0; i < xIdPlu.length; i++) {

                     //
		    		 
		    		 String vrUnitarioStr = xVrUnitarioArr[i];
		    		 String cantidadStr = xCantidadArr[i];
		    		 String idPluStr = xIdPlu[i];

		    		 Double vrUnitario = Double.parseDouble(vrUnitarioStr);
		    		 Double cantidad = Double.parseDouble(cantidadStr);
		    		 Double idPlu = Double.parseDouble(idPluStr);
		    		 
		    		 System.out.println("vrUnitario es  " + vrUnitario);
		    		 System.out.println("xIdOrdenMax es  " + xIdOrdenMax);
		    		 
		    		 tblDctosOrdenesDetalleRepo.actualizaNE(vrUnitario, cantidad.intValue(), usuario.getIdLocal(), xIdTipoOrdenNETemporal, xIdOrdenMax, idPlu.intValue());  
		    		 System.out.println("actualizaNE del if OK ");
                 }
		    	
		    }
		    	
//		    	// Ya existe boton NUEVO
//	    	   List<TblDctosOrdenesDTO> DctoOrdenLog = tblDctosOrdenesService.listaDctoOrdenLog(xIdLogActivo, xIdTipoOrdenNETemporal,  usuario.getIdLocal());
//		    	
//		    	int xIdOrdenMax = 0;
//		    	for(TblDctosOrdenesDTO orden : DctoOrdenLog) {
//		    		
//		    		xIdOrdenMax = orden.getIdOrden();
//		    	}
//		    	
//		    	
//		    	for (int i = 0; i < xIdPlu.length; i++) {
//
//                    //
//		    		String vrUnitarioStr = xVrUnitarioArr[i];
//		    		 String cantidadStr = xCantidadArr[i];
//		    		 String idPluStr = xIdPlu[i];
//
//		    		 Double vrUnitario = Double.parseDouble(vrUnitarioStr);
//		    		 Double cantidad = Double.parseDouble(cantidadStr);
//		    		 Double idPlu = Double.parseDouble(idPluStr);
//		    		 
//
//		    		 
//		    		 tblDctosOrdenesDetalleRepo.actualizaNE(vrUnitario, cantidad.intValue(), usuario.getIdLocal(), xIdTipoOrdenNETemporal, xIdOrdenMax, idPlu.intValue());
//		    		 System.out.println("actualizaNE del else OK ");
//                }
		    	
	
		    
		    
		    
		    
		    List<TblDctosOrdenesDTO> PagoFCH = tblDctosOrdenesService.listaPagoFCH(usuario.getIdLocal(), idCliente, xIdLogActivo);
		    
		    System.out.println("PagoFCH " + PagoFCH);
		    
		    for(TblDctosOrdenesDTO pago : PagoFCH) {
		    	
		    	model.addAttribute("xVrDevengado", pago.getVrDevengado());
		    	model.addAttribute("xVrDeducido", pago.getVrDeducido());
		    	model.addAttribute("xVrTotal", pago.getVrDevengado() - pago.getVrDeducido());
		    	
		    	System.out.println("xVrDeducido " + pago.getVrDevengado());
		    	System.out.println("xVrDeducido " + pago.getVrDeducido());

		    }
		    
		    
		    
		    //--------------------------------------------------------------------------------------------------------
		    

		    
		    int idOrdenNEw = 0;

		    
          for(TercerosDTO2 tercero : detalleContrato) {
        	  
        	  idOrdenNEw = tercero.getIdOrden();
        	  idCliente = tercero.getIdCliente();
        	  
		    	System.out.println("xInformacionTercero nombre = " + tercero.getNombreTercero());
		    	model.addAttribute("xnombreTercero", tercero.getNombreTercero());
		    	model.addAttribute("xnuid", tercero.getIdCliente());
		    	model.addAttribute("xcodigoAlterno", tercero.getCodigoAlterno());
		    	model.addAttribute("xccNit", tercero.getCC_Nit());
		    	model.addAttribute("xdireccionPredio", tercero.getDireccionTercero());
		    	model.addAttribute("xIdMedio", tercero.getIdMedio());
		    	
		    	model.addAttribute("xEntidadMedio", tercero.getEntidadMedio());
		    	model.addAttribute("xCuentaMedio", tercero.getCuentaMedio());
		    	
		    	
		    	String FechaInicial = tercero.getFechaInicioContrato();		    	
		    	DateTimeFormatter formatterEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");		    	
		    	LocalDateTime fechaInicio = LocalDateTime.parse(FechaInicial, formatterEntrada);		    	
		    	DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String FechInicialFormateada = fechaInicio.format(formatterAct);
		    	
		    	model.addAttribute("xFechaInicio", FechInicialFormateada);

		    	String FechaFinal = tercero.getFechaFinContrato();	
		    	LocalDateTime fechaFin = LocalDateTime.parse(FechaFinal, formatterEntrada);
		        String FechaFinalFormateada = fechaFin.format(formatterAct);
		    	
		    	model.addAttribute("xFechaFin", FechaFinalFormateada);
		    	
		    	
		    	model.addAttribute("xVrBasico", tercero.getVrSalarioBasico());
		    	model.addAttribute("xVrAuxilioTransporte", tercero.getVrSubsidioTransporte());

		    	
		    	
		    }
          
          

          
          
          System.out.println("idOrdenNEw ES " + idOrdenNEw);

          
          int idLinea = 100;

          
          String xIdCategoriaDevenga = "1400";
          String xIdCategoriaDeduce = "1800";
          
          
          
          List<TblPlusDTO> listaCategoriaDevenga = tblPlusService.listaPluCategoriaNE(usuario.getIdLocal(), idLinea, xIdCategoriaDevenga, xIdOrdenMax);
          model.addAttribute("xlistaCategoriaDevenga", listaCategoriaDevenga);
          
          for(TblPlusDTO listaDevenga : listaCategoriaDevenga) {
        	  
        	  System.out.println("vrGeneral ES " + listaDevenga.getVrGeneral());
          }
          
          
          List<TblPlusDTO> listaCategoriaDeducible = tblPlusService.listaPluCategoriaNE(usuario.getIdLocal(), idLinea, xIdCategoriaDeduce, xIdOrdenMax);
          model.addAttribute("xlistaCategoriaDeducible", listaCategoriaDeducible);
          
          
          
           
           
           model.addAttribute("xIdOrden", xIdOrdenMax);
           model.addAttribute("xIdCliente", idCliente);
		    

			
			
            // Removemos de la session las variables
			session.removeAttribute("arrVrVentaUnitario");
		    session.removeAttribute("arrIdReferencia");

			
			return "NominaElectronica/ModificaPago";
			
		

	}
	
	
	
	
	
	@PostMapping("/FinalizarPagoNE-Post")
	public ResponseEntity<Map<String, String>> FinalizarPagoNE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /FinalizarPagoNE-Post");
	    
	    
	    // Limpiar las listas anteriores de la sesión
	    HttpSession session = request.getSession();

	    
	    
	    session.removeAttribute("idCliente");
	    session.removeAttribute("idOrden");
	    session.removeAttribute("idDcto");
	    session.removeAttribute("idPluArr");
	    session.removeAttribute("CantidadArr");
	    session.removeAttribute("VrUnitarioArr");

	    // Obtenemos los datos del JSON recibido
	    String xIdOrden = (String) requestBody.get("xIdOrden");
	    String xIdDcto = (String) requestBody.get("xIdDcto");
	    String xFechaDcto = (String) requestBody.get("xFechaDcto");
	    String xFechaPagoInicio = (String) requestBody.get("xFechaPagoInicio");
	    String xFechaPagoFinal = (String) requestBody.get("xFechaPagoFinal");
	    String xVrBaseContador = (String) requestBody.get("xVrBaseContador");
	    String idCliente = (String) requestBody.get("idCliente");

	    
	    
	    // Guardar las listas en la sesión
	    session.setAttribute("xIdOrden", xIdOrden);
	    session.setAttribute("xIdDcto", xIdDcto);
	    session.setAttribute("xFechaDcto", xFechaDcto);
	    session.setAttribute("xFechaPagoInicio", xFechaPagoInicio);
	    session.setAttribute("xFechaPagoFinal", xFechaPagoFinal);
	    session.setAttribute("xVrBaseContador", xVrBaseContador);
	    session.setAttribute("idCliente", idCliente);

	    
	    Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./FinalizarPagoNE");

        return ResponseEntity.ok(response);
	}
	
	
	
	
	@GetMapping("/FinalizarPagoNE")
	public String FinalizarPagoNE( HttpServletRequest request, Model model)  {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		Integer IdUsuario = usuario.getIdUsuario();
		
		 
		 
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
    
		Integer idUsuario = (Integer) session.getAttribute("xidUsuario");        
		    
		 
		 // Obtener las listas de la sesión
		    String xIdOrdenOld = (String) session.getAttribute("xIdOrden");
		    System.out.println("Entró a FinalizarPagoNE con xIdOrdenOld: " + xIdOrdenOld);
		    Integer xIdOrdenOldInt = Integer.parseInt(xIdOrdenOld);
		    
		    String xIdDcto = (String) session.getAttribute("xIdDcto");
		 	String xFechaDcto = (String) session.getAttribute("xFechaDcto");
		 	String xFechaPagoInicio = (String) session.getAttribute("xFechaPagoInicio");
		    String xFechaPagoFinal = (String) session.getAttribute("xFechaPagoFinal");
		 	String xVrBaseContador = (String) session.getAttribute("xVrBaseContador");
		 	String idCliente = (String) session.getAttribute("idCliente");
		 	
			
			System.out.println("Entró a FinalizarPagoNE con xIdDcto: " + xIdDcto);
			System.out.println("Entró a FinalizarPagoNE con xFechaDcto: " + xFechaDcto);
			System.out.println("Entró a FinalizarPagoNE con xVrBaseContador: " + xVrBaseContador);
		 	
		 	
		 	Integer xIdDctoInt = 0;
		 	
		 	if (xIdDcto != null && !xIdDcto.isEmpty() && !xIdDcto.equals("0")) {
		 	    // Convertir a entero aquí
		 	    xIdDctoInt = Integer.parseInt(xIdDcto);
		 	}
		 	


		    

		
		
		
		 int xIdTipoOrdenNE = 8;
		 int xIdTipoOrdenNETemporal = xIdTipoOrdenNE + 50;
		 int xEstadoActivo = 9;
         int xEstadoAtendido = 1;
         int xIdTipoOrdenPedido = 9;
         
         
         // Obtener la fecha actual
	        LocalDate fechaActual = LocalDate.now();

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        String strFechaVisita = fechaActual.format(formatter);
		
		//Obtenemos el idlog Actual 
        List<TblAgendaLogVisitas> visita = tblAgendaLogVisitasService.seleccionaVisitaEstadoxFecha(xEstadoActivo, strFechaVisita, idUsuario, usuario.getIdLocal());
	
			Integer xIdLogActivo = 0;

	
			for(TblAgendaLogVisitas V : visita) {				
				xIdLogActivo = V.getIDLOG();
			}

		    
		    
		    List<TblDctosOrdenesDTO> PagoFCH = tblDctosOrdenesService.listaPagoFCH(usuario.getIdLocal(), idCliente, xIdLogActivo);
		    
		     
		    Double xVrCalculado = 0.0;
		    
		    for(TblDctosOrdenesDTO pago : PagoFCH) {
		    	
		    	Double VrDevengado =  pago.getVrDevengado();
		    	Double VrDeducido = pago.getVrDeducido();

		    	xVrCalculado = VrDevengado - VrDeducido;
		    }
		    
		    
		    Double xVrBaseContadorDouble =  Double.parseDouble(xVrBaseContador);

		   // Validacion sumas iguales, valida si NO son valores iguales
            if (!xVrCalculado.equals(xVrBaseContadorDouble) ) {

            	model.addAttribute("error", "LIQUIDACION INCORRECTA DE NOMINA Vr.Calculado= " + xVrCalculado + " Vr.Contador= " + xVrBaseContadorDouble );
            	model.addAttribute("url", "./menuPrincipal"); 
        		return "defaultErrorSistema";
                 

            }
            
            
            Double xCeroDouble = 0.0;
            
            
            tblDctosOrdenesRepo.ingresaNE(xIdTipoOrdenNE, usuario.getIdLocal(), xIdTipoOrdenNETemporal, xIdOrdenOldInt);
            System.out.println("ingresaNE OK ");
            
            int xIdOrdenNew = xIdOrdenOldInt;
            
            
            tblDctosOrdenesDetalleRepo.actualiza(xIdTipoOrdenNE, xIdOrdenNew, usuario.getIdLocal(), xIdTipoOrdenNETemporal, xIdOrdenOldInt);
            System.out.println("actualiza OK ");
            
            int xIdTipoTerceroNE = 3;
            
            List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), idCliente, xIdTipoTerceroNE);
            
            
            int xIndicador = 0;
            int xIdFormaPago = 0;
            String nombreTercero = "";
            
            for(TblTerceros tercero : listaTercero) {
            	
            	xIndicador = tercero.getIndicador();
            	xIdFormaPago = tercero.getIdFormaPago();
            	nombreTercero = tercero.getNombreTercero();
            }
            
            
            
            if (xIdDctoInt.equals(0)) {
            	
            	Integer xIdDctoMax = TblDctosService.maximoDctoLocalIndicador(usuario.getIdLocal(), xIdTipoOrdenNE, xIndicador) + 1;
            	
            	
//            	(int idLocal, int IdTipoOrden, int IdOrden, int IdDcto, int Indicador, String FechaDctoSqlServer, Double VrBaseSinRedondeo, int VrPago, int Estado, Double VrIva, 
//           			 int IdTipoNegocio, int VrRteFuente, Double VrDescuento, int VrRteIva, int VrRteIca, String NombreTercero, int IdUsuario, String IdCliente, int DiasPlazo, int PorcentajeDscto, 
//           			 int IdCausa, String IdDctoNitCC, String FechaDctoNitCCSqlServer, int VrPagarDctoNitCC, int VrDsctoFcro, Double VrCostoMV, int IdLocalCruce, int IdTipoOrdenCruce, int IdDctoCruce, 
//           			 int IdPeriodo, int IdVendedor, Double VrImpoconsumo, Double VrCostoIND, int IdOrdenCruce, int EtapaSTR, int EnvioFE,  String fechaPagoInicio, String fechaPagoFin)
            	
                
            	tblDctosRepo.ingresaDctoNE(usuario.getIdLocal(), xIdTipoOrdenNE, xIdOrdenNew, xIdDctoMax, xIndicador, xFechaDcto, xCeroDouble, xVrCalculado.intValue(), 1, xCeroDouble,
            			2, xCeroDouble.intValue(), xCeroDouble, xCeroDouble.intValue(), xCeroDouble.intValue(), nombreTercero, IdUsuario, idCliente, xIdFormaPago, xCeroDouble.intValue(),
            			xCeroDouble.intValue(), xIdDctoMax.toString(), strFechaVisita, xCeroDouble.intValue(), xCeroDouble.intValue(), xCeroDouble, usuario.getIdLocal(), xCeroDouble.intValue(), xCeroDouble.intValue(),
            			xCeroDouble.intValue(), IdUsuario, xCeroDouble, xCeroDouble, xCeroDouble.intValue(), xCeroDouble.intValue(), 0, xFechaPagoInicio, xFechaPagoFinal);
            	
            	System.out.println("ingresaDctoNE OK ");
            }else {
            	
            	// actualizaDctoNE
            	tblDctosRepo.actualizaDctoNE(xIdOrdenNew, xVrCalculado, usuario.getIdLocal(), xIdTipoOrdenNE, xIdDctoInt);
            	System.out.println("actualizaDctoNE OK ");
            }
            
            
            
           // Obtenemos la IP del servidor
            UtilidadesIP utilidadesIP = new UtilidadesIP();
            String xIpTx = utilidadesIP.getIpServidor();
            
            int tareaVisita = 6;   // Cotizacion
            int estadoProgramado = 1;
            int xIdEstadoTxSinTx = 1;
            
           // (int estado, int idEstadoVisita, int IdTipoOrden, int IdEstadoTx, String IpTx, String FechaTx, int IdLocal, int idLog)
	        tblAgendaLogVisitasRepo.finalizaVisita(xEstadoAtendido, xEstadoAtendido, xIdTipoOrdenNE, xEstadoAtendido, xIpTx, strFechaVisita, usuario.getIdLocal(), xIdLogActivo);
	        System.out.println("finalizaVisita OK ");
            
			
            // Removemos de la session las variables
			session.removeAttribute("xIdOrden");
		    session.removeAttribute("xIdDcto");
		    session.removeAttribute("xFechaPagoInicio");
		    session.removeAttribute("xFechaPagoFinal");
		    session.removeAttribute("xVrBaseContador");
		    session.removeAttribute("idCliente");


			
		    return "redirect:./PagoNEconsultaFinaliza";
			
		

	}
	
	
	
	
	
	@GetMapping("/PagoNEconsultaFinaliza")
	public String pagoNEconsultaFinaliza(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				
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

				
		int idTipoOrden = 8;	   
		
		String xFechaDcto = (String) session.getAttribute("xFechaDcto");
		model.addAttribute("xFechaDcto", xFechaDcto);
			           
	    List<TblDctosOrdenesDTO> PagoNEAll = tblDctosOrdenesService.listaPagoNEAll(idLocal, idTipoOrden, xFechaDcto);
	    model.addAttribute("xPagoNEAll", PagoNEAll); 	      
			           
			           
			           
			           
		
		return "NominaElectronica/PagoNEconsultaFinaliza";
	}
	
	
	
	
	
	@PostMapping("/EnviarPagoDIAN-Post")
	@ResponseBody
    public ResponseEntity<Map<String, String>> EnviarAjusteDIAN(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {

        Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
       
        Integer idLocal = usuario.getIdLocal();
        
        
        // Obtenemos los datos del JSON recibido
	    String xIdDcto = (String) requestBody.get("xIdDcto");
	    System.out.println("Entró a /EnviarPagoDIAN con xIdDcto " + xIdDcto);
        
        Integer xIdDctoInt = Integer.parseInt(xIdDcto);
        
        int xIdTipoOrden = 8;
        int xIndicador = 1;
        
        
        List<TblDctosDTO> unDcto = TblDctosService.listaUnDcto(idLocal, xIdTipoOrden, xIdDctoInt, xIndicador);
        
        Double xIdPeriodo = 0.0;
        String xIdCliente = "";
        
        for(TblDctosDTO dcto : unDcto) {
        	
        	xIdPeriodo = dcto.getIdDcto();
        	xIdCliente = dcto.getIdCliente();
        }
        
        int xEnvioFE_proceso = 1;
        
        //
        String xCharSeparator = File.separator;
        String xRuta = "";

        // Linux 
        if (xCharSeparator.compareTo("/") == 0) {

            // Linux             
        	xRuta = "" + xCharSeparator + "home" + xCharSeparator + "sw" + xCharSeparator + "jar" + xCharSeparator + "ApiSoenacNE" + xCharSeparator + "dist" + xCharSeparator + "ApiSoenacNE.jar ";

        } else {

            // Windows          
        	xRuta = "C:" + xCharSeparator + "proyectoWeb" + xCharSeparator + "ApiSoenacNE" + xCharSeparator + "dist" + xCharSeparator + "ApiSoenacNE.jar ";
        }
        
        
        final String xRutaDisco = xRuta;

        //
        final int xIdLocalUsuarioFinal = idLocal;
        final int xIdDctoFinal = xIdDctoInt;
        
        //
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    //
                    Runtime rt = Runtime.getRuntime();

                    //
                    Process proc = rt.exec("java -jar " + xRutaDisco
                            + xIdLocalUsuarioFinal + " "
                            + xIdDctoFinal);

                //  System.out.println("  "+ proc);
                    //
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

                    BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

                    // read the output from the command
                    String s = null;
                    while ((s = stdInput.readLine()) != null) {
                        System.out.println(s);
                    }

                    // read any errors from the attempted command
                    while ((s = stdError.readLine()) != null) {
                        System.out.println(s);
                    }
                    proc.waitFor();
                    System.out.println("success");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();
        
        
       


        Map<String, String> response = new HashMap<>();
        
        response.put("mensaje", "OK");
        return ResponseEntity.ok(response);
    }
	
	
	
	
	
	
	
	
	
	
	
	
}
