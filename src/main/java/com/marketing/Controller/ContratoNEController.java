package com.marketing.Controller;

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
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblCiudadesDTO;
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
import com.marketing.Utilidades.ProcesoAjusteConsumoCliente;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaCredito;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaNE;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;

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
	
	
	
	
	@GetMapping("/ContratoNE")
	public String contratoNE(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer IdUsuario = usuario.getIdUsuario();

				Integer idTipoOrden = 8;
				
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
		
		HttpSession session = request.getSession();
		
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerEmpleadoNE con idTercero: " + idTercero);
		
		Integer idTipoTercero = 3;
		
		Integer idTipoOrden = 8;
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			
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
          
          int idOrden = 55902;
          
           List<TercerosDTO2> ListaTipoTrabajador = tblTercerosService.listaDetalleContratoFCHPorCategoria(usuario.getIdLocal(), idTipoOrden, idTercero, tipoTrabajador);
          
           for(TercerosDTO2 contrato : ListaTipoTrabajador) {

        	   List<TblPlusDTO>  ListaPlus = tblPlusService.listaPluCategoriaTipoNE(idLinea, contrato.getIdCategoria(), usuario.getIdLocal(), contrato.getIDPLU());
        	   model.addAttribute("xListaPlusTipoTrabajador", ListaPlus);
        	   model.addAttribute("xIdPlu", contrato.getIDPLU());
        	   
        	   idOrden = contrato.getIdOrden();
           }
           
           
           System.out.println("IDORDEN ES " + idOrden);
           
           
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

	}
	
	
	
	
	@PostMapping("/ModificarContrato")
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
	
	
	
	
	
}
