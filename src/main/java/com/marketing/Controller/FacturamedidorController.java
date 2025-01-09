package com.marketing.Controller;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

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
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.Bak_DctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.Bak_DctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosPeriodoRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosMediosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosRepo;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
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
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.UtilidadesIP;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoConsumoM3;

@Controller
public class FacturamedidorController {
	
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
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	Bak_DctosOrdenesDetalleRepo bak_DctosOrdenesDetalleRepo;
	
	@Autowired
	Bak_DctosOrdenesRepo bak_DctosOrdenesRepo;
	
	@Autowired
	TblPagosMediosRepo tblPagosMediosRepo;
	
	@Autowired
	TblPagosRepo tblPagosRepo;
	
	@Autowired
	TblDctosPeriodoRepo tblDctosPeriodoRepo;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	ProcesoCreaLecturaMovil procesoCreaLecturaMovil;
	
	@Autowired
	ProcesoGuardaLecturaMovil procesoGuardaLecturaMovil;
	
	@Autowired
	ProcesoConsumoM3 ProcesoConsumoM3;
	
	 @Autowired
	 ControlDeInactividad controlDeInactividad;
	
	@GetMapping("/Facturamedidor")
	public String facturamedidor (HttpServletRequest request,Model model) {
		
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
				
				
				
				
				List<TblTercerosRuta> Rutas = tblTercerosRutaService.ListaRutas(idLocal);
				model.addAttribute("xRutas", Rutas);
				
				List<TblMedidoresMacro> ListaMedidoresMacro = tblMedidoresMacroService.ListaMedidoresMacro(usuario.getIdLocal());
				model.addAttribute("ListaMedidoresMacro", ListaMedidoresMacro);
				
		
		return "Cliente/Facturamedidor";
	}
	
	
	@PostMapping("/Facturamedidor-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> FacturamedidorPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws ClassNotFoundException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    int idLocal = usuario.getIdLocal();
	    Integer xIdUsuario = usuario.getIdUsuario();
	    
	    Map<String, Object> response = new HashMap<>();

	    System.out.println("SI ENTRÓ A  /Facturamedidor-Post");

	        // Obtenemos los datos del JSON recibido
	        String idPeriodo = (String) requestBody.get("idPeriodo");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	        
	        String Contaseña = (String) requestBody.get("Contaseña");
	        System.out.println("Contaseña en  /Facturamedidor-Post " + Contaseña);
	        
	        
	        int xCero = 0;
	        Double xCeroDouble = 0.0;
	        int xUno = 1;
	        String xNada = "";
	        int xIndicador = 1;
	        int xIdTipoTerceroCliente = 1;
	        int xEstadoAtendido = 1;
	        
	        
	        int xIdTipoOrdenVenta = 9;
	        Integer xIdTipoOrdenVentaTemporal = xIdTipoOrdenVenta + 50;
	        
	        int xIdNivelAdministrador = 5;
            int xEstadoActivo = 1;

	        Integer idUsuarioAutorizado = ctrlusuariosService.listaAutorizador(idLocal, xIdNivelAdministrador, xEstadoActivo, Contaseña, xIdUsuario);
	        System.out.println("QUERY 1");
	        
	        //Validamos si la contraseña es correcta
	        if(idUsuarioAutorizado == 0) {

            	response.put("error", "0");
            	return ResponseEntity.ok(response);
	        	
	        }

	        
	        //----- valida estado no facturado != 1 ------------------------
            int xEstadoFacturadoSI = 1;
            
            
            List <TblDctosPeriodo> Periodo = tblDctosPeriodoService.listaUnFCH(idPeriodoInt, idLocal);
            System.out.println("QUERY 2");
            Integer estadoFacturado = 0;  
            for(TblDctosPeriodo P : Periodo) { // Obtenemos el estado Facturado
            	
            	estadoFacturado = P.getEstadoFacturado();
            	System.out.println("estadoFacturado es : " + estadoFacturado);
            }
            
            if(estadoFacturado == xEstadoFacturadoSI) {
            	
            	System.out.println("PERIODO YA FACTURADO");
            	response.put("Facturado", "1");
            	return ResponseEntity.ok(response);
            }
            
            
            //--- proceso bak local protege bd --------------------------- 
            bak_DctosOrdenesDetalleRepo.retiraOrdenesDetalle(idLocal);
            System.out.println("QUERY 3");        
            bak_DctosOrdenesRepo.retiraOrdenes(idLocal);
            System.out.println("QUERY 4");
            bak_DctosOrdenesRepo.ingresaOrdenes(idLocal, idPeriodoInt);
            System.out.println("QUERY 5");
            bak_DctosOrdenesDetalleRepo.ingresaOrdenesDetalle(idLocal, idPeriodoInt);
            System.out.println("QUERY 6");
            
            tblPagosMediosRepo.retiraTemporal(idLocal, xIdTipoOrdenVentaTemporal);
            System.out.println("QUERY 7");
            tblPagosRepo.retiraTemporal(idLocal, xIdTipoOrdenVentaTemporal);
            System.out.println("QUERY 8");
            
            // listaConsumoFCH = tipo=4
            List<TblDctosOrdenesDTO> Consumo = tblDctosOrdenesService.listaConsumoFCH(idPeriodoInt, xIdTipoOrdenVentaTemporal, idLocal);
            System.out.println("QUERY 9");
            int xIdOrden = 0;
            
            for(TblDctosOrdenesDTO C : Consumo ) {
            	
            	xIdOrden = C.getIdOrden();
            } 
           
            
            System.out.println("xIdOrden es " + xIdOrden);
            
            //--- Novedad ingresa dctoOrdenDetalleBean NUID inexistente 
            int xIdTipoConsumo = 4;
            
            // retiraConsumo si existe doble en consumo basico
            tblDctosOrdenesDetalleRepo.retiraConsumo(idLocal, xIdTipoOrdenVentaTemporal, xIdTipoConsumo, idPeriodoInt);
            System.out.println("QUERY 10");
            tblDctosOrdenesDetalleRepo.ingresaCreaLectura(xIdTipoOrdenVentaTemporal, xIdOrden, xIdTipoConsumo, idLocal);
            System.out.println("QUERY 11");
            
            //--------- Inicia Liquidacion ---------------------------------
            int xIdPeriodoActual = idPeriodoInt;
            
            Integer xIdPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(idPeriodoInt, idLocal);
            System.out.println("QUERY 12");
            tblDctosPeriodoRepo.modificaEstadoFacturado(idLocal, idPeriodoInt, xEstadoFacturadoSI);
            System.out.println("QUERY 13");
            tblDctosOrdenesDetalleRepo.retiraCambioEstrato(idLocal, xIdTipoOrdenVentaTemporal, idPeriodoInt);
            System.out.println("QUERY 14");
            tblDctosOrdenesDetalleRepo.actualizaOrden(xIdPeriodoActual, xIdTipoOrdenVentaTemporal, idLocal);
            System.out.println("QUERY 15");
            tblDctosOrdenesDetalleRepo.cantidadM3(idLocal, xIdPeriodoAnterior, xIdPeriodoActual);
            System.out.println("QUERY 16");
            tblDctosOrdenesDetalleRepo.cantidadAforoM3(idLocal, xIdPeriodoActual);
            System.out.println("QUERY 17");
            //listaUnLocal
            List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
            
            String xIdCausaConsumoPromedio = "";
            String xIdCausaConsumoPromedioEstrato = "90";                  
            String xIdCausaConsumoAforo = "91"; 
            
            // 1-SI factura sitio , 2-NO factura sitio
            int xEstadoSTR_Sitio_SI = 1;
            int xEstadoSTR = 0;
            boolean xOkFacturaSitio = false;
            
            for(TblLocales L : Local) {
    	    	
            	xIdCausaConsumoPromedio = L.getConsumoPromedio();
            	xEstadoSTR = L.getEstadoSTR();
    	    }
            
            
            // Local SI factura sitio
            if (xEstadoSTR_Sitio_SI == xEstadoSTR) {

                //
                xOkFacturaSitio = true;

                //-- Todos se cambian a promedio para lectura en sitio
                tblDctosOrdenesDetalleRepo.actualizaCausalPromedio(idLocal, xIdTipoOrdenVentaTemporal, xIdPeriodoActual);
                System.out.println("QUERY 18");
            }
                       
            //---
            String[] arrIdCausaConsumoPromedio = xIdCausaConsumoPromedio.split(",");
            
            //--
            for (int i = 0; i <  arrIdCausaConsumoPromedio.length; i++) {

                tblDctosOrdenesDetalleRepo.cantidadM3Promedio(idLocal, xIdTipoOrdenVentaTemporal, arrIdCausaConsumoPromedio[i], xIdPeriodoActual);               
                System.out.println("QUERY 19 + xIdCausaConsumoPromedio " + arrIdCausaConsumoPromedio[i]);                

            }
            

            //--- xIdCausaConsumoPromedioEstrato
            tblDctosOrdenesDetalleRepo.cantidadM3PromedioEstrato(idLocal, xIdTipoOrdenVentaTemporal, xIdCausaConsumoPromedioEstrato, xIdPeriodoActual);
            System.out.println("QUERY 20");
            //--- xIdCausaConsumoAforo
            tblDctosOrdenesDetalleRepo.cantidadM3Aforo(idLocal, xIdTipoOrdenVentaTemporal, xIdCausaConsumoAforo, xIdPeriodoActual);
            System.out.println("QUERY 20");
            // causalNovedadPromedio ( causalNovedadPromedio)
            tblDctosOrdenesDetalleRepo.cambiaConsumoNegativo(idLocal, xIdTipoOrdenVentaTemporal, xIdPeriodoActual);
            System.out.println("QUERY 22");
            // actualizaItem ( el mismo plu varias veces )
            tblDctosOrdenesDetalleRepo.actualizaItem(idLocal, xIdTipoOrdenVentaTemporal, xIdPeriodoActual);
            System.out.println("QUERY 23");
            
 
            //-- ConsumoQryM3
            try {
            	
            	ProcesoConsumoM3.consumoQryM3(idLocal);
            	
            }catch (ClassNotFoundException ex) {}
            
            
            // retiraCargoFijo
            tblDctosOrdenesDetalleRepo.retiraCargoFijo(idLocal, xIdTipoOrdenVentaTemporal);
            System.out.println("QUERY 28");
            // retiraPeriodoDiferente
            tblDctosOrdenesDetalleRepo.retiraPeriodoDiferente(idLocal, xIdTipoOrdenVentaTemporal, xIdPeriodoActual);
            System.out.println("QUERY 29");
            //-- retira estado suspendido definitivo = 2 ( pierde derecho )
            tblDctosOrdenesDetalleRepo.retiraSuspendidoDefinitivo(idLocal, xIdPeriodoActual, xIdTipoOrdenVentaTemporal);
            System.out.println("QUERY 30");
            // ingresaCargoFijo 
            int xIdTipo_CargoFijo = 6;
            int xIdRazonBasico = 5;
            
            
            tblDctosOrdenesDetalleRepo.ingresaCargoFijo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdTipo_CargoFijo);
            System.out.println("QUERY 31");
            //------Otros cargos, definidos por plu-------------------------
            int xIdTipo_Otros = 18;
            
            
            tblDctosOrdenesDetalleRepo.ingresaCargoOtros(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdTipo_Otros);
            System.out.println("QUERY 32");
            int xIdTipo_AlcantarillaCargoFijo = 50;

            tblDctosOrdenesDetalleRepo.ingresaAlcantarillaCargoFijo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdTipo_AlcantarillaCargoFijo);
            System.out.println("QUERY 33");
           
            //--------- ingresaAlcantarillaConsumo-----------------------*/
            int xIdTipo_AlcantarillaConsumo = 51;
            
            tblDctosOrdenesDetalleRepo.ingresaAlcantarillaConsumo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdTipo_AlcantarillaConsumo);
            System.out.println("QUERY 34");
            
            //-- Anula estado suspendido Parcial = 5,6 (Pone en cero cargo Fijo)
            tblDctosOrdenesDetalleRepo.anulaCargoFijoSuspendidoParcial(idLocal, xIdPeriodoActual, xIdTipoOrdenVentaTemporal);
            System.out.println("QUERY 35");
            
            //-- consumoQryM3Alcantarillado
            try {
            	
            	ProcesoConsumoM3.consumoQryM3Alcantarillado(idLocal);
            	
            }catch (ClassNotFoundException ex) {}
            
            
            //--------- ingresaASEOCargoFijo-----------------------*/
            int xIdTipoASEOCargoFijo = 60;
       
            tblDctosOrdenesDetalleRepo.ingresaAseoCargoFijo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdTipoASEOCargoFijo);
            System.out.println("QUERY 40");
            
            //==========Actualiza consumo x cambio estrato========
            tblDctosOrdenesDetalleRepo.actualizaListaPrecioEstratoPlu(idLocal, xIdPeriodoActual);
            System.out.println("QUERY 41");
            tblDctosOrdenesDetalleRepo.actualizaListaPrecio(idLocal, xIdPeriodoActual);
            System.out.println("QUERY 42");
            
            //--------- Inicia Liquidacion Subsidio-----------------------*/
            int xIdSignoSubsidio = -1;
            int xIdTipoCategoriaSubsidio = 11;
            int xIdTipo_PluSubsidio = 19;
            int xIdTipo_ConsumoSub = 21;
            
            //Crago fijo Acueducto sub
            tblDctosOrdenesDetalleRepo.ingresaCategoriaCargoFijo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoSubsidio, xIdTipo_PluSubsidio);
            System.out.println("QUERY 43");
            //Consumo Acueducto sub
            tblDctosOrdenesDetalleRepo.ingresaCategoriaConsumo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoSubsidio, xIdTipo_ConsumoSub);
            System.out.println("QUERY 44");
            
            //--------- Inicia Liquidacion Contribucion-------------------*/
            int xIdSignoContribucion = 1;
            int xIdTipoCategoriaContribucion = 12;
            int xIdTipoPluContribucion = 20;
            int xIdTipo_ConsumoContribucion = 22;
            
            
            //cargo fijo acueducto contribucion
            tblDctosOrdenesDetalleRepo.ingresaCategoriaCargoFijo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoContribucion, xIdTipoPluContribucion);
            System.out.println("QUERY 45");
            //consumo acuedcuto cont
            tblDctosOrdenesDetalleRepo.ingresaCategoriaConsumo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoContribucion, xIdTipo_ConsumoContribucion);
            System.out.println("QUERY 46");
            
            //-ALCATARILALDO Subsidio-----------------------
            int xIdCategoriaSubsidioAlcantarillado = 11;
            int xIdTipo_SubsidioAlcantarillado = 29;
            int xIdTipo_ConsumoSubAlcantarillado = 25;
            
            
            //crago fijo alcantarillado sub
            tblDctosOrdenesDetalleRepo.ingresaCategoriaCargoFijo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoSubsidio, xIdTipo_SubsidioAlcantarillado);
            System.out.println("QUERY 47");
            //consumo alcantarillado sub
            tblDctosOrdenesDetalleRepo.ingresaCategoriaConsumo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoSubsidio, xIdTipo_ConsumoSubAlcantarillado);
            System.out.println("QUERY 48");
            
            //ALCANTARILLADO Contribucion-----------------------------------
            int xIdTipoCategoriaContribucionAlcantarillado = 12;
            int xIdTipoContribucionAlcantarillado = 24;
            int xIdTipoConsumoContAlcantarillado = 26;
            
            
            //crago fijo alcantarillado cont
            tblDctosOrdenesDetalleRepo.ingresaCategoriaCargoFijo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoContribucion, xIdTipoContribucionAlcantarillado);
            System.out.println("QUERY 49");
            //consumo alcantarillado consumo cont
            tblDctosOrdenesDetalleRepo.ingresaCategoriaConsumo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoContribucion, xIdTipoConsumoContAlcantarillado);
            System.out.println("QUERY 50");
            
            
            //-ASEO Subsidio-----------------------*/
            int xIdTipoCategoriaSubsidioAseo = 18;
            int xIdTipo_PluSubsidioAseo = 27;
            
            //cargo fijo aseo sub
            tblDctosOrdenesDetalleRepo.ingresaCategoriaCargoFijo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoSubsidio, xIdTipo_PluSubsidioAseo);
            System.out.println("QUERY 51");
            
            //-ASEO Contribucion-----------------------*/
            int xIdTipoCategoriaContribucionAseo = 12;
            int xIdTipoPluContribucionAseo = 28;
            
            //cargo fijo aseo cont
            tblDctosOrdenesDetalleRepo.ingresaCategoriaCargoFijo(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdSignoContribucion, xIdTipoPluContribucionAseo);
            System.out.println("QUERY 52");
            
            //-- Anula estado suspendido Parcial = 6 (Pone en cero SubsidioContibucionCargoFijo)
            tblDctosOrdenesDetalleRepo.anulaSubsidioContribucionCFSuspendidoParcial(idLocal, xIdPeriodoActual, xIdTipoOrdenVentaTemporal);
            System.out.println("QUERY 53");
            //-- anulaCargoFijoSuspendidoParcial tblterceros.estado IN (4) ------------------- ESTE NO ESTÁ
            
            
            //-Sancion Desincentivo---------------------------------------*/
            int xIdTipoDesincentivo = 40;  
            int xSignoSancion = 1;                
            String xStrIdListaSuntuario = "3";     
            
            //consumo sancion Desincentivo
            tblDctosOrdenesDetalleRepo.ingresaSancion(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xSignoSancion, xIdTipoDesincentivo, xIdPeriodoActual, xStrIdListaSuntuario);
            System.out.println("QUERY 54");
            
            //--------Ingresa Interes Pago Extemporaneo---------------------
            
            //listaUnLocal
            List<TblLocales> Locale = tblLocalesService.ObtenerLocal(idLocal);
            
            String xCodigoIAC = "";
            int xEstadoGeneraIAC = 0;
            int xEstadoAjuste = 0;
            int IdTipoInteres = 0;
            int EstadoPagoExtenporaneo = 0;
            int EstadoPagoAnticipado = 0;

            
            for(TblLocales L : Locale) {
    	    	
            	xCodigoIAC = L.getCodigoIAC();
            	xEstadoGeneraIAC = L.getEstadoGeneraIAC();
            	xEstadoAjuste = L.getEstadoAjusteCentena();
            	IdTipoInteres = L.getIdTipoInteres();
            	EstadoPagoExtenporaneo = L.getEstadoPagoExtenporaneo();
            	EstadoPagoAnticipado = L.getEstadoPagoAnticipado();
    	    }
            
            
            int xEstadoPagoExtemporaneo_SI = 1;
            int xEstadoGeneraIAC_SI = 1;
            int xEstadoAjusteDecena_SI = 1;
            int xEstadoAjusteCentena_SI = 3;
            
            //Interes 
            int xIdTipoInteresPagoExtemporaneo = 2;
            
            
            if (xIdTipoInteresPagoExtemporaneo == IdTipoInteres) {

                //
                if (EstadoPagoExtenporaneo == xEstadoPagoExtemporaneo_SI) {

                	tblDctosOrdenesDetalleRepo.ingresaInteresPagoExtemporaneo(xIdTipoOrdenVentaTemporal, xIdOrden, idLocal, xIdPeriodoAnterior);
                	System.out.println("QUERY 55");
                }
            }
            
            
            //PAgo anticipado-Descuento por pronto pago 
            int xEstadoPagoAnticipadoSI = 1;
            
            //
            if (xEstadoPagoAnticipadoSI == EstadoPagoAnticipado) {

            	tblDctosOrdenesDetalleRepo.ingresaVrPagoAnticipado(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdPeriodoAnterior);
            	System.out.println("QUERY 56");
            }
            
            
            //-- Anula consumo estado exento = 5 (Pone en cero consumo)
            tblDctosOrdenesDetalleRepo.anulaConsumoEstadoExento(idLocal, xIdPeriodoActual, xIdTipoOrdenVentaTemporal);
            System.out.println("QUERY 57");
            
            //====================== Inicia Liquidacion FACTURA ===========*/
            // Obtenemos la IP del servidor
            UtilidadesIP utilidadesIP = new UtilidadesIP();
            String xIpTx = utilidadesIP.getIpServidor();

            boolean xOkFacturado = false;
            
            
            List<TblDctosOrdenesDetalleDTO> listaPrevia = tblDctosOrdenesDetalleService.listaPrevia(xIdPeriodoActual, xIdTipoOrdenVentaTemporal, idLocal, xIdTipoTerceroCliente);
            System.out.println("QUERY 58");
            for(TblDctosOrdenesDetalleDTO lista : listaPrevia) {
            	
            	Integer xIdLog = tblAgendaLogVisitasService.findMaxIDLOG()+ 1;
            	System.out.println("QUERY 59");
            	String xIdCliente = lista.getIdCliente();
                double xPromedio = 0.0;
                
                if(lista.getPromedio() != null) {
                	
                	 xPromedio = lista.getPromedio(); 
                	
                }
   
                String xHistoriaConsumo = lista.getHistoriaConsumo();
                double xPromeioEstrato = 0.0;
                
                if(lista.getPromedioEstrato()!= null) {
                	
                	xPromeioEstrato = lista.getPromedioEstrato(); 
                  	
                  }
                
                boolean okIngreso = tblAgendaLogVisitasService.ingresaLogVisita(xIdLog, xIdCliente, xIdUsuario, idLocal, idLocal, idPeriodoInt, xEstadoAtendido, xEstadoAtendido, xIdTipoOrdenVentaTemporal);
                System.out.println("QUERY 60");
                
                List<TblTerceros> listaTercero =  tblTercerosService.listaUnTerceroFCH(idLocal, xIdCliente, xIdTipoTerceroCliente);
                System.out.println("QUERY 61");
                String xNombreTercero = "";
                String xDireccionTercero = "";
                String xTelefonoFijo = "";
                String xEmail = "";
                int xEstadoSuscriptor = 0;
                
                for(TblTerceros tercero : listaTercero) {
                	
                	xNombreTercero = tercero.getNombreTercero();
                	xDireccionTercero = tercero.getDireccionTercero();
                	xTelefonoFijo = tercero.getTelefonoFijo();
                	xEmail = tercero.getEmail();
                	xEstadoSuscriptor = tercero.getEstado();
                }
                
                
                int xIdOrdenNew = tblDctosOrdenesService.maximaIdOrdenIdLocal(idLocal) + 1;
                System.out.println("QUERY 62");
                
                // Obtenemos la fecha y hora actuales
                Timestamp fechaHoraActual = new Timestamp(System.currentTimeMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define el formato deseado
                String strFechaVisita = sdf.format(fechaHoraActual);
                
                
                // --- ingresaPedido
                tblDctosOrdenesRepo.ingresaPedido(idLocal, xIdTipoOrdenVenta, xIdOrdenNew, strFechaVisita, xEstadoSuscriptor, xIdCliente, xIdUsuario, xUno, xIdLog, strFechaVisita, 
                		xIdTipoOrdenVentaTemporal.toString(), xEmail, xTelefonoFijo, xNada, xNada, xDireccionTercero, xNada, xNada, xNada, xCero, xCero, xIdRazonBasico, xUno, xUno, xCero,
                		xCero, xCero, xCero, idPeriodoInt, xCeroDouble, xCero, xCeroDouble, xCeroDouble, xPromedio, xHistoriaConsumo, xPromeioEstrato); 
                System.out.println("QUERY 63");
                
                // --- ingresaDeudaMedidor
                tblDctosOrdenesDetalleRepo.ingresaDeudaMedidor(xIdTipoOrdenVentaTemporal, xIdOrden, xIdCliente, idLocal, xIdPeriodoAnterior);
                System.out.println("QUERY 64");
                
                //Interes normal x Deuda
                int xIdTipoInteresNormal = 1;
                
                if (xIdTipoInteresNormal == IdTipoInteres) {

                    // --- ingresaInteresMatricula
                	tblDctosOrdenesDetalleRepo.ingresaInteresMatricula(xIdTipoOrdenVentaTemporal, xIdOrden, idLocal, xIdCliente);
                	System.out.println("QUERY 65");
                }
                
                
            	 //InteressNormal+Interes extemporaneo
                int xIdTipoInteresExtemporaneo = 2;
                
                if (xIdTipoInteresExtemporaneo == IdTipoInteres) {

                    // --- ingresaInteresMatricula
                	tblDctosOrdenesDetalleRepo.ingresaInteresMatricula(xIdTipoOrdenVentaTemporal, xIdOrden, idLocal, xIdCliente);
                	System.out.println("QUERY 66");
                }
                
                
                // --- ingresaFinanciacion----------------------------------
                int xIdTipoOrdenFinanciacion = 7;
                int xEstadoFinanciacionActualizado = 4;
                
                tblDctosOrdenesDetalleRepo.ingresaFinanciacion(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdCliente);
                System.out.println("QUERY 67");
                
                tblDctosOrdenesDetalleRepo.actualizaCargoFinanciacion(xEstadoFinanciacionActualizado, xIdPeriodoActual, idLocal, xIdTipoOrdenFinanciacion, xIdCliente);
                System.out.println("QUERY 68");
                
                //--ingresa Cobro Permannete
                tblDctosOrdenesDetalleRepo.ingresaCobroPermanente(idLocal, xIdTipoOrdenVentaTemporal, xIdOrden, xIdCliente);
                System.out.println("QUERY 69");
                
                // --- actualizaOrdenCliente
                tblDctosOrdenesDetalleRepo.actualizaOrdenCliente(xIdTipoOrdenVenta, xIdOrdenNew, idLocal, xIdTipoOrdenVentaTemporal, xIdCliente, xIdPeriodoActual);
                System.out.println("QUERY 70");
                
                List<TblDctosOrdenesDetalleDTO> liquidaOrden = tblDctosOrdenesDetalleService.liquidaOrdenDetalleFCH(idLocal, xIdTipoOrdenVenta, xIdLog);
                System.out.println("QUERY 71");
                double xVrVentaSinIva = 0.0;
                double xVrVentaConIva = 0.0;
                double xVrVentaSinDscto = 0.0;
                double xVrIva = 0.0;
                double xVrCosto = 0.0;
                double ValorCero = 0.0;
                
                for(TblDctosOrdenesDetalleDTO L : liquidaOrden) {
                	
                	xVrVentaSinIva = L.getVrVentaSinIva();
                	xVrVentaConIva = L.getVrVentaConIva();
                	xVrVentaSinDscto = L.getVrVentaSinDscto();
                	xVrIva = L.getVrIvaVenta();
                	xVrCosto = L.getVrCostoSinIva();
                }
                
                
                
                // PASTICAUCA / NO POS
                Integer xIdDctoMax = TblDctosService.maximoDctoLocalIndicador(idLocal, xIdTipoOrdenVenta, xIndicador) + 1;
                System.out.println("QUERY 72");
                Double VrDescuento = xVrVentaSinIva - xVrVentaSinDscto;
                
                tblDctosRepo.ingresaDcto(idLocal, xIdTipoOrdenVenta, xIdOrdenNew, xIdDctoMax, xIndicador, strFechaVisita, xVrVentaSinIva, xCero, xUno, xVrIva, xUno, xCero, VrDescuento, 
                		xCero, xCero, xNombreTercero, xIdUsuario, xIdCliente, xCero, xCero, xCero, xIdDctoMax.toString(), strFechaVisita, xCero, xCero, xVrCosto, xCero, xCero, xCero, 
                		idPeriodoInt, xIdUsuario, ValorCero, ValorCero, xCero, xCero, xCero);
                System.out.println("QUERY 73");
                
                
                
                tblAgendaLogVisitasRepo.finaliza(xEstadoAtendido, xIdCliente, xEstadoAtendido, xIdTipoOrdenVenta, xUno, idLocal, xIpTx, strFechaVisita, xIdLog);
                System.out.println("QUERY 74");
                
                	xOkFacturado = true;
            	}
            
            
            //--------------------------------------------------------------
            int xIdTipoFinanciacion = 8;
            int xIdTipoOrdenFinanciacion = 7;
            
            tblDctosOrdenesDetalleRepo.actualizaDetalleFinanciacion(idLocal, xIdTipoOrdenFinanciacion, xIdPeriodoActual);
            System.out.println("QUERY 75");
            List<TblDctosOrdenesDetalleDTO> listafinaciacion = tblDctosOrdenesDetalleService.listaFinanciacion(idLocal, xIdTipoOrdenVenta, xIdTipoFinanciacion, idPeriodoInt);
            System.out.println("QUERY 76");
            
            for(TblDctosOrdenesDetalleDTO financiacion : listafinaciacion ) {
            	
            	int xIdOrdenFinanciacion = financiacion.getIDORDEN();
            	System.out.println("xIdOrdenFinanciacion es : " + xIdOrdenFinanciacion);
                String xComentarioFinanciacion = " -" + financiacion.getComentario();
                System.out.println("xComentarioFinanciacion es : " + xComentarioFinanciacion);
                
                tblDctosOrdenesRepo.actualizaComentario(xComentarioFinanciacion, idLocal, xIdTipoOrdenVenta, xIdOrdenFinanciacion);
                System.out.println("QUERY 77");
            }
            
            
            //Actualiza cuotas vencidas
            tblDctosOrdenesRepo.actualizaCuotaVencida(idLocal, xIdPeriodoActual);
            System.out.println("QUERY 78");
            // Proximo periodo lectura (tblTerceros)-cuota vencida ultimo mes facturado
            tblDctosOrdenesRepo.actualizaCuotaVencidaLectura(idLocal, xIdPeriodoActual);
            System.out.println("QUERY 79");
            
            //xActualizaCuotaVencidaxAnticipo--------------------------------
            tblDctosOrdenesRepo.actualizaCuotaVencidaxAnticipo(idLocal, xIdPeriodoActual, xIdTipoOrdenVenta);
            System.out.println("QUERY 80");
            //===============================================================
            int xEstadoCorteReconexion = 3;
            
            tblDctosOrdenesRepo.actualizaEstadoCorte(idLocal, xIdPeriodoActual);
            System.out.println("QUERY 81");
            
            tblTercerosRepo.actualizaEstadoCorte(idLocal, xIdPeriodoActual, xEstadoCorteReconexion);
            System.out.println("QUERY 82");
            //Borra registros de consumo diferentes al estrato actual de los suscriptores
            int xIdTipoLectura = 4;
            
            tblDctosOrdenesDetalleRepo.retiraRegistrosEstratoAnterior(idLocal, xIdTipoOrdenVenta, xIdPeriodoActual, xIdTipoLectura);
            System.out.println("QUERY 83");
            
            
            //--- Ajuste Decena ( posterior a facturado )
            if ((xOkFacturado) && (xEstadoAjusteDecena_SI == xEstadoAjuste)) {

                //
                int xIdTipoAjusteDecena = 23;

                // Liquida ingresa ajuste
                tblDctosOrdenesDetalleRepo.ingresaTipo(idLocal, xIdTipoAjusteDecena, xIdTipoOrdenVenta, xIdPeriodoActual);
                System.out.println("QUERY 84");
                // Actualiza Dctos       
                tblDctosRepo.actualizaDcto(idLocal, xIdTipoOrdenVenta, xIdPeriodoActual);
                System.out.println("QUERY 85");
            }
            
            
            //--- Ajuste Centena ( posterior a facturado )
            if ((xOkFacturado) && (xEstadoAjusteCentena_SI == xEstadoAjuste)) {

                //
                int xIdTipoAjusteDecena = 23;

                // Liquida ingresa ajuste
                tblDctosOrdenesDetalleRepo.ingresaTipoCentena(idLocal, xIdTipoAjusteDecena, xIdTipoOrdenVenta, xIdPeriodoActual);
                System.out.println("QUERY 86");
                // Actualiza Dctos       
                tblDctosRepo.actualizaDcto(idLocal, xIdTipoOrdenVenta, xIdPeriodoActual);
                System.out.println("QUERY 87");
            }
            
            
          //--- Valor contante NO cuotas vencidas
            int xIdTipoInteresVrMesVencido = 3;
            
            //
            if (xIdTipoInteresVrMesVencido == IdTipoInteres) {

                //--- Otros Intereses, definidos x vrPlu
                int xIdTipoInteres = 12;
                int xCuentaVencidaUNO = 1;
                int xCuentaVencidaDOS = 2;
                //--
                int xIdCategoriaUnaCuota = 2;
                int xIdCategoriaDosCuota = 7;

                //--
                tblDctosOrdenesDetalleRepo.ingresaOtroInteresUnaCuota(xIdTipoInteres, xIdCategoriaUnaCuota, idLocal, xIdTipoOrdenVenta, xIdPeriodoActual, xCuentaVencidaUNO);
                System.out.println("QUERY 88");
                //
                tblDctosOrdenesDetalleRepo.ingresaOtroInteresDosCuota(xIdTipoInteres, xIdCategoriaDosCuota, idLocal, xIdTipoOrdenVenta, xIdPeriodoActual, xCuentaVencidaDOS);
                System.out.println("QUERY 89");
                // Actualiza Dctos       
                tblDctosRepo.actualizaDcto(idLocal, xIdTipoOrdenVenta, xIdPeriodoActual);
                System.out.println("QUERY 90");
            }
            
            
            //--- ingresaInteresVrContanteXCuotaVencida
            int xIdTipoInteresVrMesxCuotasVencidas = 4;     
            
            if (xIdTipoInteresVrMesxCuotasVencidas == IdTipoInteres) {

                //------Otros Intereses, definidos por vrPlu-------------------------
                int xIdTipoInteres = 12;
                int xCuentaVencidaUNO = 1;
                int xIdCategoriaDos = 2;

                //--
                tblDctosOrdenesDetalleRepo.ingresaInteresVrContanteXCuotaVencida(xIdTipoInteres, xIdCategoriaDos, idLocal, xIdTipoOrdenVenta, xIdPeriodoActual, xCuentaVencidaUNO);
                System.out.println("QUERY 91");
                // Actualiza Dctos       
                tblDctosRepo.actualizaDcto(idLocal, xIdTipoOrdenVenta, xIdPeriodoActual);
                System.out.println("QUERY 92");
            } 
            
            // --- actualizaFechaPagoUltimo--
            tblDctosOrdenesRepo.actualizaFechaPagoUltimo(idLocal, xIdPeriodoActual);
            System.out.println("QUERY 93");
            
            
            // Genera imagen IAC CODE128 ( posterior a facturado )
            if ((xOkFacturado) && (xEstadoGeneraIAC_SI == xEstadoGeneraIAC) && (!xOkFacturaSitio)) {

                // TODO code application logic here
                String xCharSeparator = File.separator;
                String xRuta = "";

                // Linux 
                if (xCharSeparator.compareTo("/") == 0) {

                    // Linux               
                    xRuta = "" + xCharSeparator + "home" + xCharSeparator + "sw" + xCharSeparator + "jar" + xCharSeparator + "CodigoGS1" + xCharSeparator + "dist" + xCharSeparator + "CodigoGS1.jar ";

                } else {

                    // Windows         C:\proyectoWeb\CodigoGS1\dist  
                    xRuta = "C:" + xCharSeparator + "proyectoWeb" + xCharSeparator + "CodigoGS1" + xCharSeparator + "dist" + xCharSeparator + "CodigoGS1.jar";

                }
                
                System.out.println("QUERY 94");
                //
                final String xRutaDisco = xRuta;
                final String xIdCliente = "0"; //  0 ( son todos)                 

                //                       //
                Thread t = new Thread(new Runnable() {

                    @Override
                    @SuppressWarnings("empty-statement")
                    public void run() {
                        try {

                            //
                            Runtime rt = Runtime.getRuntime();

                            Process proc = rt.exec("java -jar " + xRutaDisco + " "
                                    + idLocal + " "
                                    + xIdPeriodoActual + " "
                                    + xIdCliente);

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
                System.out.println("QUERY 95");
                //====================== Fin Liquidacion FACTURA ===========*/                
            }
            
            
            
            
            
            
		    response.put("message", "OK");

		    return ResponseEntity.ok(response);
	   
	    
	}

}
