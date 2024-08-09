package com.marketing.Controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;

@Controller
public class ReporteCriticaLecturas {

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
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	ProcesoCreaLecturaMovil procesoCreaLecturaMovil;
	
	@Autowired
	ProcesoGuardaLecturaMovil procesoGuardaLecturaMovil;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@GetMapping("/ReporteCriticaLecturas")
	public String reporteCriticaLecturas (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer xIdUsuario = usuario.getIdUsuario();
				
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

				// Obtenemos la lista de periodos 
				List <TblDctosPeriodo> Periodos = tblDctosPeriodoService.ListaTotalPeriodos(idLocal);
				model.addAttribute("xPeriodos", Periodos);
				
				// ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				Integer idPeriodo = 0;
				Integer idTipoOrden = 9;
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					idPeriodo = P.getIdPeriodo();
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
				
				}
				
				List<TblDctosOrdenesDTO> CuentaFacturado =  tblDctosOrdenesService.PeriodoFacturado(idLocal, idTipoOrden, idPeriodo);
				
				Integer Cuenta = 0;
				
				for(TblDctosOrdenesDTO C : CuentaFacturado) {
					
					Cuenta = C.getCuenta();
				}
				
								
				if(Cuenta != 0) {
					
					model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodo + " YA FACTURADO NO PERMITE REGISTRAR LECTURAS");
	            	model.addAttribute("url", "./menuPrincipal");
	        		return "defaultErrorSistema";
				}
				// -------------------------------------------------------------------------------------------------------------------------------------------------
				
				ArrayList<TblTipoCausaNota> CriticaLectura = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(20);
				model.addAttribute("CriticaLectura", CriticaLectura);
				
				List<TblTercerosRuta> Rutas = tblTercerosRutaService.ListaRutas(idLocal);
				model.addAttribute("xRutas", Rutas);
				
				List<TblMedidoresMacro> ListaMedidoresMacro = tblMedidoresMacroService.ListaMedidoresMacro(usuario.getIdLocal());
				model.addAttribute("ListaMedidoresMacro", ListaMedidoresMacro);
				
		
		return "Cliente/ReporteCriticaLecturas";
	}
	
	
	@PostMapping("/ConsultarCritica")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ConsultarCritica(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    int idLocal = usuario.getIdLocal();
	    Integer xIdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /BuscarSuscriptor");

	        // Obtenemos los datos del JSON recibido
	        String idPeriodo = (String) requestBody.get("idPeriodo");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	        
	        String idRuta = (String) requestBody.get("idRuta");
	        Integer idRutaInt = Integer.parseInt(idRuta);
	        
	        
	        String exceso = (String) requestBody.get("exceso");
	        Double xPorcentajeExceso = Double.parseDouble(exceso);
	        
	        String M3Consumo = (String) requestBody.get("M3Consumo");
	        Double xConsumoBase = Double.parseDouble(M3Consumo);
	        
	        String CriticaLectura = (String) requestBody.get("CriticaLectura");
	        Integer xIdTipoCritica = Integer.parseInt(CriticaLectura);
	        
	        
	        
	        List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
	        
	        Integer xCuentaRegistroTx = 0;
	        
	        for(TblLocales L : Local) {
		    	
	        	xCuentaRegistroTx = L.getCuentaRegistroTx();
		    }
	        
	        
	        int xIdTipoOrdenPago = 9;
	        int xIdTipoOrdenPagoProceso = xIdTipoOrdenPago + 50;
	        
	        int xIdTipoConsumo = 4;
	        
	        // RETIRA CAMBIO ESTRATO
	        tblDctosOrdenesDetalleRepo.retiraCambioEstrato(idLocal, xIdTipoOrdenPagoProceso, idPeriodoInt);
	        
	        // CREA PROCESO DE LECTURA
	        boolean resultado =   procesoCreaLecturaMovil.crea(idLocal, xIdTipoOrdenPagoProceso, idPeriodoInt, xIdUsuario);
	        

	        
	        Integer xIdPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(idPeriodoInt, idLocal);
	       
	        
	        
	        List<TercerosDTO2> lista = null;
	        
	        // CriticaPorcentajeExceso
	        if(xIdTipoCritica.equals(1)) {

	        	lista = tblTercerosService.listaCriticaPorcentajeExceso(xPorcentajeExceso, idLocal, xIdPeriodoAnterior, idPeriodoInt, xIdTipoConsumo, idRutaInt, xConsumoBase);
	        }
	        
	        // CriticaPorcExcesoDefecto
	        if(xIdTipoCritica.equals(2)) {

	        	lista = tblTercerosService.listaCriticaPorcExcesoDefecto(xPorcentajeExceso, idLocal, xIdPeriodoAnterior, idPeriodoInt, xIdTipoConsumo, idRutaInt, xConsumoBase);
	        }
	        
	        
	        // CriticaConsumoCero
	        if(xIdTipoCritica.equals(3)) {

	        	lista = tblTercerosService.listaCriticaConsumoCero(idLocal, xIdPeriodoAnterior, idPeriodoInt, xIdTipoConsumo, idRutaInt);
	        }
	        
	        
	        // CriticaConsumoNegativo
	        if(xIdTipoCritica.equals(4)) {

	        	lista = tblTercerosService.listaCriticaConsumoNegativo(idLocal, xIdPeriodoAnterior, idPeriodoInt, xIdTipoConsumo, idRutaInt);
	        }
	        
	        
	        // CriticaPromedioCero
	        if(xIdTipoCritica.equals(5)) {

	        	lista = tblTercerosService.listaCriticaPromedioCero(idLocal, xIdPeriodoAnterior, idPeriodoInt, xIdTipoConsumo, idRutaInt);
	        }
	        
	        
	        // CriticaInconsistencia
	        if(xIdTipoCritica.equals(6)) {

	        	lista = tblTercerosService.listaCriticaInconsistencia(idLocal, xIdPeriodoAnterior, idPeriodoInt, xIdTipoConsumo, idRutaInt);
	        }
	        
	        
	        
	        ArrayList<TblTipoCausaNota> EstadoLectura = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(2);
	        
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("lista", lista);
		    response.put("EstadoLectura", EstadoLectura);

		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
}
