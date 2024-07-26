package com.marketing.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
import com.marketing.Projection.TblDctosPeriodoDTO;
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
import com.marketing.Utilidades.ProcesoConfirmaDiferido;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaCredito;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.UtilidadesIP;

@Controller
public class CobroPermanenteController {
	
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
	ProcesoGuardaCredito procesoGuardaCredito;
	
	@Autowired
	ProcesoConfirmaDiferido procesoConfirmaDiferido;
	
	
	
	
	@GetMapping("/CobroPermanente")
	public String cobroPermanente(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer IdUsuario = usuario.getIdUsuario();

				
				
				// ---------------------------------------------------------------- VALIDACION SUSCIPTOR SELECCIONADO --------------------------------------------------------
				
				int xIdTipoTerceroCliente = 1;
		        int xIdTipoOrdenFinanciacion = 7;

		        
		        int idLinea = 1;

		        //
		        int estadoActivo = 9;
				
				 // Obtener la fecha actual
		        LocalDate fechaActual = LocalDate.now();

		        // Formatear la fecha como un String
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		        String strFechaVisita = fechaActual.format(formatter);
		        
		        System.out.println("strFechaVisita  es" + strFechaVisita);
		        System.out.println("IdUsuario  es" + IdUsuario);
		        

				
				String idCliente = tblAgendaLogVisitasService.seleccionaVisitaEstadoFecha(estadoActivo, strFechaVisita, IdUsuario);
				System.out.println("idCliente desde /Factura " + idCliente);
				
				if(idCliente == null) {
					
					return "Cliente/Selecciona";
					
				}else {
					
					System.out.println("idCliente en el if es" + idCliente);
					
					List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(usuario.getIdLocal(), idCliente, xIdTipoTerceroCliente);
					
					for(TblTerceros L : listaTercero) {
						
						model.addAttribute("xEstado", L.getEstado());
						model.addAttribute("xNuid", L.getIdCliente());
						model.addAttribute("xNombreTercero", L.getNombreTercero());
						model.addAttribute("xRuta", L.getIdRuta());
						model.addAttribute("xDireccion", L.getDireccionTercero());
						model.addAttribute("xCCNIT", L.getCC_Nit());
						
					}
					
				}
				
				
				// Obtenemos la lista de PLUS
				List<TblPlus> listaPlus = tblPlusService.ObtenerPlusPorIdLinea(idLocal, idLinea);
				model.addAttribute("xListaPlus", listaPlus);
				
				
				
				//Validamos si el idCliente tiene ordenes idTipo = 7
				 List<TblDctosOrdenesDTO> CobroPermanenteLista = tblDctosOrdenesService.listaCobroPermanente(idLocal, xIdTipoOrdenFinanciacion, idCliente);
	                
				
				int idOrden = 0;
				
				for(TblDctosOrdenesDTO lista : CobroPermanenteLista) {
					
					idOrden = lista.getIDORDEN();
					
				}
				
				//Validamos si existe idOrden
				if(idOrden != 0) {
					
					model.addAttribute("xCobroPermanenteLista", CobroPermanenteLista);
					
				}
				
				

				
		
		return "Financiacion/CobroPermanente";
	}
	
	
	
	@PostMapping("/IngresarCobroPermanente")
	public ResponseEntity<Map<String, String>> IngresarCobroPermanente(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ConfirmarFinanciacion-Post");
	    
	    Integer IdUsuario = usuario.getIdUsuario();
	    int idLocal = usuario.getIdLocal();

	    // Obtenemos los datos del JSON recibido
	    String xIdCliente = (String) requestBody.get("idCliente");

	    String valorCobro = (String) requestBody.get("valorCobro");
	    Double xVrTarifaAseo = Double.parseDouble(valorCobro);
	    
	    String idPlu = (String) requestBody.get("idPlu");
	    Integer xIdPlu = Integer.parseInt(idPlu);
	    
	    int xEstadoPeriodoActivo = 1;
	    int xEstadoAtendido = 1;
	    int xEstadoActivo = 9;
	    int xIdTipoOrdenFinanciacion = 7;
	    int idTipoTerceroCliente = 1;
	    
	    
	    
	    
	    List <TblDctosPeriodoDTO> dctoPeriodo = tblDctosPeriodoService.listaEstadoFCH(idLocal, xEstadoPeriodoActivo);
	    
	    int xIdPeriodo = 0;
	    
	    for (TblDctosPeriodoDTO P : dctoPeriodo ) {
	    	
	    	xIdPeriodo = P.getIdPeriodo();
	    }
	    
	    
	    tblAgendaLogVisitasRepo.actualizaLogVisitaUsuario(xEstadoAtendido, IdUsuario, xEstadoActivo);
	    
	    int xIdLogMAX = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
	    
	    // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
	    

	    tblAgendaLogVisitasRepo.ingresaLogVisita(xIdLogMAX, xIdCliente, IdUsuario, idLocal, idLocal, xIdPeriodo, strFechaVisita,
	    										xEstadoAtendido, xEstadoActivo, xIdTipoOrdenFinanciacion, strFechaVisita);
	    
	    
	    
	    List<TblAgendaLogVisitas> visita = tblAgendaLogVisitasService.seleccionaVisitaEstadoxFecha(xEstadoActivo, strFechaVisita, IdUsuario, idLocal);
	 			
	 			int idLog = 0;
	
	 			for(TblAgendaLogVisitas V : visita) { 				
	 				idLog = V.getIDLOG();
	 			}
	 			
	 			
	 			String xNumeroCuotas = "1";
	 			Integer xNumeroCuotasInt = Integer.parseInt(xNumeroCuotas);
	 			Double xNumeroCuotasDouble = Double.parseDouble(xNumeroCuotas);
                //
                double xCantidad = 1.0;
                
                double xVrVentaUnitario = xVrTarifaAseo  / xNumeroCuotasInt;
                
                String strIdReferencia = idPlu;
                int xItem = 1;
                String xObservacion = "TARIFA COBRO PERMANENTE";
                String xPorcentajeInteres = "0";
                String xValorInteres = "0";
                
                
                
                
                procesoGuardaCredito.guarda(idLog, strIdReferencia, xCantidad, xVrVentaUnitario, xItem, xIdTipoOrdenFinanciacion, IdUsuario, idLocal, xIdCliente, 
                				xVrTarifaAseo.toString(), xNumeroCuotasDouble, xPorcentajeInteres, xValorInteres, xObservacion);
	    
                
            	List<TblTerceros> listaTercero = tblTercerosService.listaUnTerceroFCH(idLocal, xIdCliente, idTipoTerceroCliente);
            	
            	int xIndicador = 0;
                int xIdFormaPago = 0;
                String xNombreTercero = "";
                
                double xVrCero = 0.0;
    			
    			for(TblTerceros L : listaTercero) {
    				
    				xIndicador = L.getIndicador();
    				xIdFormaPago = L.getIdFormaPago();
    				xNombreTercero = L.getNombreTercero();
    				
    			}
                
                
                List<TblDctosOrdenesDetalleDTO> liquidaOrden = tblDctosOrdenesDetalleService.liquidaOrdenFCH(idLocal, xIdTipoOrdenFinanciacion, idLog);
                
                int IdOrden = 0;
                for(TblDctosOrdenesDetalleDTO orden : liquidaOrden) {
                	
                	IdOrden = orden.getIdOrden();
                }
                
               Integer xVrCeroInt = 0;
                
                int xIdDctoMax = TblDctosService.maximoDctoLocalIndicador(idLocal, xIdTipoOrdenFinanciacion, xIndicador) + 1;
                


                tblDctosRepo.ingresaDcto(idLocal, xIdTipoOrdenFinanciacion, IdOrden, xIdDctoMax, xIndicador, strFechaVisita, xVrTarifaAseo, xVrCeroInt, 1, xNumeroCuotasDouble,
                		2, xVrCeroInt, xVrCero, xVrCeroInt, xVrCeroInt, xNombreTercero, IdUsuario, xIdCliente, xIdFormaPago, xVrCeroInt, 
                		xVrCeroInt, xVrCeroInt.toString(), strFechaVisita, xVrCeroInt, xVrCeroInt, xVrCero, idLocal, xVrCeroInt, xVrCeroInt, 
                		xIdPeriodo, IdUsuario, xVrCero, xVrCero, xVrCeroInt, xVrCeroInt, xVrCeroInt);
                
                
                
                int estadoVisita = 9;
                int estadoSuspendido = 8;
                int xIdEstadoVisita = 1;
        
                
                tblAgendaLogVisitasRepo.actualizaVisita(estadoSuspendido, strFechaVisita, IdUsuario, estadoVisita);
                
                
                List<TblDctosOrdenesDTO> CobroPermanenteLista = tblDctosOrdenesService.listaCobroPermanente(idLocal, xIdTipoOrdenFinanciacion, xIdCliente);
                model.addAttribute("xCobroPermanenteLista", CobroPermanenteLista);
                

        Map<String, String> response = new HashMap<>();
        response.put("message", "OK");

        return ResponseEntity.ok(response);
	}
	
	
	
	@PostMapping("/RetirarCobroPermanente")
	public ResponseEntity<Map<String, String>> RetirarCobroPermanente(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ConfirmarFinanciacion-Post");
	    
	    Integer IdUsuario = usuario.getIdUsuario();

	    // Obtenemos los datos del JSON recibido
	    String idOrden = (String) requestBody.get("idOrden");
	    Integer xIdOrden = Integer.parseInt(idOrden);
	    
	    int xIdTipoOrdenFinanciacion = 7;
	    
        
        int xItemPadre = 1;
        
        tblDctosOrdenesDetalleRepo.retiraItemNoFacturado(usuario.getIdLocal(), xIdTipoOrdenFinanciacion, xItemPadre, xIdOrden);
        
        
        


        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "./CobroPermanente");

        return ResponseEntity.ok(response);
	}
	
	
	
	
	@PostMapping("/ModificarCobro")
	public ResponseEntity<Map<String, String>> ModificarCobro(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ConfirmarFinanciacion-Post");
	    
	    
	    Integer IdUsuario = usuario.getIdUsuario();
	    Integer idLocal = usuario.getIdLocal();

	    // Obtenemos los datos del JSON recibido
	    @SuppressWarnings("unchecked")
	    List<String> xArrVrCobro = (List<String>) requestBody.get("arrVrCobro");
        System.out.println("xArrVrCobro es : " + xArrVrCobro);
        
        List<String> xArrIdOrden = (List<String>) requestBody.get("arrIdOrden");
        System.out.println("xArrVrCobro es : " + xArrVrCobro);
        
        List<String> xArrIdPlu = (List<String>) requestBody.get("arrIdPlu");
        System.out.println("xArrIdPlu es : " + xArrIdPlu);
        
        String[] arrVrCobro = xArrVrCobro.toArray(new String[0]);
        String[] arrIdOrden = xArrIdOrden.toArray(new String[0]);
        String[] arrIdPlu = xArrIdPlu.toArray(new String[0]);
        
        
        int xIdTipoOrdenFinanciacion = 7;
        
        
        for (int indice = 0; indice < arrVrCobro.length; indice++) {
        	
        	
        	 if (arrVrCobro[indice].length() == 0) {
                 continue;
             }
        	 
        	 //
             String xVrCobroStr  =  arrVrCobro[indice];
             Double xVrCobro = Double.parseDouble(xVrCobroStr);
             
             String xIdOrdenStr  = arrIdOrden[indice];
             Integer xIdOrden = Integer.parseInt(xIdOrdenStr);
             
             String xIdPlu = arrIdPlu[indice];
             
             
             tblDctosOrdenesRepo.modificaCobroPermanente(xVrCobro, idLocal, xIdTipoOrdenFinanciacion, xIdOrden);
             
             int xItemPadre = 1;
             
             
             tblDctosOrdenesDetalleRepo.modificaItemNoFacturado(xVrCobro, idLocal, xIdTipoOrdenFinanciacion, xIdOrden, xItemPadre);
             
             tblDctosRepo.modificaCobroPermanente(xVrCobro, idLocal, xIdTipoOrdenFinanciacion, xIdOrden);
        	
        }
        
	    
	  
        
        


        Map<String, String> response = new HashMap<>();
        response.put("message", "OK");

        return ResponseEntity.ok(response);
	}

}
