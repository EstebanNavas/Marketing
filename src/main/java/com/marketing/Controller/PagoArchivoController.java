package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblMediosPago;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO2;
import com.marketing.Projection.TblDctosPeriodoDTO;
import com.marketing.Projection.TblPagosDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosMediosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblMediosPagoService;
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
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.UtilidadesIP;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;




import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


@Controller
public class PagoArchivoController {
	
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
	TblMediosPagoService tblMediosPagoService;
	
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
	TblPagosRepo tblPagosRepo;
	
	@Autowired
	TblPagosMediosRepo tblPagosMediosRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	
	
	@GetMapping("/PagoArchivo")
	public String pagoArchivo(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
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

				
				// Obtener la fecha actual
		        LocalDate fechaActual = LocalDate.now();
				
				DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String FechActual = fechaActual.format(formatterAct);

		        model.addAttribute("xFechaActual", FechActual);
				
				
				
				
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
				
								
				if(Cuenta == 0) {
					
					model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodo + " NO HA SIDO FACTURADO");
	            	model.addAttribute("url", "./menuPrincipal");
	        		return "defaultErrorSistema";
				}
				
				
				//Medios de pago
				List<TblMediosPago> MediosDePago  = tblMediosPagoService.ListaMediosDePago(idLocal);
				model.addAttribute("xMediosDePago", MediosDePago);
				
				
			
				
				
				
		
		return "Cliente/PagoArchivo";
	}
	
	
	
	
	
	@PostMapping("/SubirArchivo")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> SubirArchivo(@RequestParam("fechaPago") String fechaPago, 
		                                                 	@RequestParam("medioDePago") String medioDePago, 
															@RequestParam("archivo") MultipartFile archivo, HttpServletRequest request) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdLogPago");
	    
	    Integer xIdUsuario = usuario.getIdUsuario();
	    Integer xIdLocalUsuario = usuario.getIdLocal();
	    
	    Map<String, Object> response = new HashMap<>();
	    
	    Integer idMedio = Integer.parseInt(medioDePago);
	    
	    
	    int xIdTipoOrdenFactura = 9;
        int xIdTipoOrdenPagoTemporal = 59;
        double xCero = 0.0;
        int estadoActivo = 9;

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);


	    System.out.println("SI ENTRÓ A  /SubirArchivo con fechaPago:  " + fechaPago);
	    System.out.println("SI ENTRÓ A  /SubirArchivo con archivo:  " + archivo);
	    
	    
	    
	    //
        int estadoAtendido = 1; // visitaActiva
        int estadoProgramada = 9; // visitaProgramada
        int idEstadoVisita = 1; // Programada

        
        // Obtenemos el periodo activo
		List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(xIdLocalUsuario);
		
		Integer xIdPeriodoActivo = 0;

		
		for(TblDctosPeriodo P : PeriodoActivo) {
			
			xIdPeriodoActivo = P.getIdPeriodo();
		
		}
	    
		//-----------------------------------------------------------------------
		
		
		// Obtener tamaño del archivo en bytes
		long fileSizeInBytes = archivo.getSize();

		// Convertir el tamaño a MB
		double fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0);
		System.out.println("Tamaño del archivo " + fileSizeInMB);

		// límite de 2 MB
		if (fileSizeInMB > 2.0) {
		    
			response.put("message", "PESADO");
		    return ResponseEntity.ok(response);
		}
		
		
        
        
     // Procesa el archivo Excel
        try (InputStream inputStream = archivo.getInputStream()) {
        	
            Workbook workbook = Workbook.getWorkbook(inputStream);
            
            Sheet sheet = workbook.getSheet(0);
            
            int xNumeroFilas = sheet.getRows();
            
            
            //
            boolean xOkFormato = true;
            
            //           
            Cell xFechaPago;
            Cell xVrPago;
            Cell xIdDcto;

            
            
            
            
            int idLog = tblAgendaLogVisitasService.findMaxIDLOG() + 1;
            
            tblAgendaLogVisitasRepo.actualizaLogVisitaUsuario(estadoAtendido, xIdUsuario, estadoProgramada);
            
            
            tblAgendaLogVisitasRepo.ingresaLogVisita(idLog, xIdUsuario.toString(), xIdUsuario, xIdLocalUsuario, xIdLocalUsuario, xIdPeriodoActivo, strFechaVisita, idEstadoVisita, estadoAtendido, xIdTipoOrdenPagoTemporal, strFechaVisita);
             

			// Procesa cada fila del archivo Excel
			for (int indiceFila = 0; indiceFila < xNumeroFilas; indiceFila++) {
				
				xOkFormato = true;

				//
				xFechaPago = sheet.getCell(0, indiceFila);
				xVrPago = sheet.getCell(1, indiceFila);
				xIdDcto = sheet.getCell(2, indiceFila);


				//
				try {

				} catch (NumberFormatException nfe) {

					//
					xOkFormato = false;
				}

				try {

				} catch (NumberFormatException nfe) {

					//
					xOkFormato = false;

				}

				//
				if (xOkFormato) {

					String xFechaPagoArchivo = xFechaPago.getContents();
					//String xDcto = xIdDcto.getContents().substring(16, 23).trim(); // Quitar el subString
					String xDcto = xIdDcto.getContents().trim(); 
					String xVrPagoArchivo = xVrPago.getContents();

					System.out.println("xVrPagoArchivo es " + xVrPagoArchivo);
					
					Double xVrPagoArchivoDou = Double.parseDouble(xVrPagoArchivo);
					

					Integer xIdDctoInt = Integer.parseInt(xDcto);
					
					Double xIdDctoDouble = Double.parseDouble(xDcto);

					String xMensajeValidacion = "";

					List<TblDctosDTO> PeriodoLista = TblDctosService.listaPeriodoDcto(xIdLocalUsuario,
							xIdTipoOrdenFactura, xIdDctoInt, xIdPeriodoActivo);

					String idCliente = "";
					for (TblDctosDTO lista : PeriodoLista) {

						idCliente = lista.getIdCliente();
					}

					// existeDcto
					Integer existeDcto = TblDctosService.existeDctoPeriodo(xIdLocalUsuario, xIdTipoOrdenFactura, xIdDctoInt, xIdPeriodoActivo);

					if (existeDcto == null) {
						
						//
						xMensajeValidacion += "DCTO " + xIdDctoInt + " La Factura no existe " + idCliente;

					}
					

					// existeDctoPago
					Integer existeDctoVrPago = TblDctosService.existeVrPagoDctoPeriodo(xIdLocalUsuario, xIdTipoOrdenFactura, xIdDctoInt, xIdPeriodoActivo);
	

					if (existeDctoVrPago == null) {

						//
						xMensajeValidacion += "DCTO " + xIdDctoInt + " La Factura tiene pago " + idCliente;

					}

					// existeDcto
					Integer existeDctoPago = tblPagosService.validaDctoPago(xIdLocalUsuario, xIdTipoOrdenFactura, xIdDctoInt, xIdPeriodoActivo);
	

					if (existeDctoPago != null) {

						response.put("message", "SI");
						response.put("Dcto", existeDctoPago);
						return ResponseEntity.ok(response);

					}

					int xIdMaximaPlanilla = tblPagosService.maximaPlanilla(xIdLocalUsuario, xIdTipoOrdenPagoTemporal) + 1;
					
					System.out.println("xIdMaximaPlanilla");

					// INICIA PAGOS
					String xAnio = xFechaPagoArchivo.substring(4, 8);
					String xMes = xFechaPagoArchivo.substring(2, 4);
					String xDia = xFechaPagoArchivo.substring(0, 2);

					// -----------
					String xFecha = xAnio + "/" + xMes + "/" + xDia;
					
					System.out.println("xIdDctoInt es: " + xIdDctoInt);
					
					System.out.println("xIdLocalUsuario es: " + xIdLocalUsuario);

					List<TblDctosDTO> ListaPeriodo = TblDctosService.listaPeriodoDcto(xIdLocalUsuario, xIdTipoOrdenFactura, xIdDctoInt, xIdPeriodoActivo);
					
					//Obtenemos los valores actualizados de la factura 
					List<TblDctosOrdenesDTO> FacturaActual = tblDctosOrdenesService.ObtenerFacturaActualizada(xIdLocalUsuario, xIdDctoInt, xIdPeriodoActivo);

					
					//Obtenemos el valor base total 
					double VrBase = FacturaActual.stream()
						    .mapToDouble(TblDctosOrdenesDTO::getVrVentaUnitarioSinIva)
						    .sum();
					

					// ---------------------- Ingreso encanezado + detalle pago

					int xIdReciboMAX = tblPagosService.maximoReciboIdLocalxIndicador(xIdLocalUsuario, xIdTipoOrdenPagoTemporal, 1) + 1;
					
					
					System.out.println("VrBase es: " + VrBase);
					System.out.println("xVrPagoArchivoDou es: " + xVrPagoArchivoDou);
					System.out.println("existeDctoPago es: " + existeDctoPago);

					//Comparamos si existe una diferencia absoluta entre los dos valores para ser mas precisos debido a que son Doubles 
					if (Math.abs(VrBase - xVrPagoArchivoDou) < 0.0001) {
						
						System.out.println("Ingresó al if comparativo pagos");

						if (existeDctoPago == null) {
							
							System.out.println("Ingresó al if existeDctoPago");


							Integer idClienteInt = Integer.parseInt(idCliente);

							// ingresaPago
							tblPagosRepo.ingresaPago(xIdLocalUsuario, xIdTipoOrdenPagoTemporal, xIdReciboMAX, 1, xFecha, xVrPagoArchivoDou, idClienteInt, estadoActivo, xIdUsuario, 
									xCero, xCero, xCero, xCero, xIdPeriodoActivo, xIdDctoInt, idClienteInt, xIdMaximaPlanilla, xCero, idLog, xIdUsuario, xIdReciboMAX, xCero, xMensajeValidacion);
							
							System.out.println("INGRESA PAGO OK");

							//
							int xEstadoOk = 1;
							int xIdBancoOk = 0;
							int xIdDctoMedioOk = 1;
							//int xIdMedioArchivo = 3;// COTRAFA

							

							tblPagosMediosRepo.ingresaTotal(idMedio, xFecha, xIdBancoOk, xIdDctoMedioOk,xEstadoOk, idLog, xIdTipoOrdenPagoTemporal, xIdReciboMAX, 1,
									                        xIdLocalUsuario, xIdDctoInt,xIdPeriodoActivo);
							
							
							session.setAttribute("xIdLogPago", idLog);
							
							System.out.println("INGRESA TOTAL OK");

						}

					}else {
						
						System.out.println("LOS VALORES NO COINCIDEN PARA EL CLIENTE " + idCliente);
						response.put("message", "NO");
						return ResponseEntity.ok(response);
					}

				}

			}
            workbook.close();
        } catch (IOException | BiffException ex) {
            
            ex.printStackTrace();
        }
        

        System.out.println("SALIÓ DEL PROCESO");
		    
		    
		    response.put("message", "OK");
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	@GetMapping("/DetallePago")
	public String detallePago(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
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

				
				// Obtener la variable de la sesión
				Integer xIdLog =  (Integer) session.getAttribute("xIdLogPago");
			   // Integer idLog = Integer.parseInt(xIdLog);

				
				Integer idTipoOrden = 9;
				
				
				// ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				Integer idPeriodo = 0;
				
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					idPeriodo = P.getIdPeriodo();
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
				
				}
				
				System.out.println("idTipoOrden es : " + idTipoOrden);
				System.out.println("xIdLog es : " + xIdLog);
				
				List<TblDctosOrdenesDetalleDTO2> PlanillaLista = tblDctosOrdenesDetalleService.listaCuentaPlanilla(idLocal, idTipoOrden, xIdLog);
				model.addAttribute("xPlanillaLista", PlanillaLista);
				
				double totalVrSaldo = PlanillaLista.stream()
					    .mapToDouble(TblDctosOrdenesDetalleDTO2::getVrSaldo)
					    .sum();
				
				System.out.println("totalVrSaldo es : " + totalVrSaldo);
				model.addAttribute("totalVrSaldo", totalVrSaldo);
				
				
				model.addAttribute("xIdLog", xIdLog);
			
				

		
		return "Cliente/DetallePago";
	}
	
	
	
	@GetMapping("/RegresarPago")
	public String regresarPago(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
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

				
				// Obtener la variable de la sesión
				Integer xIdLog =  (Integer) session.getAttribute("xIdLogPago");

				
				System.out.println("Ingresó a RegresarPago con el xIdLog :  " + xIdLog);	
				
				int idTipoOrdenTemporal = 59;
				
				
				//Retirar registros de tblPagosMedios
				tblPagosMediosRepo.retiraPagosTemporales(idLocal, idTipoOrdenTemporal, xIdLog);
				
				
				//Retirar registros de tblPagos
				tblPagosRepo.retiraPagosTemporales(idLocal, idTipoOrdenTemporal, xIdLog);
				
				
				
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
				
								
				if(Cuenta == 0) {
					
					model.addAttribute("error", "PERIODO ACTUAL# " + idPeriodo + " NO HA SIDO FACTURADO");
	            	model.addAttribute("url", "./menuPrincipal");
	        		return "defaultErrorSistema";
				}
				
				// Obtener la fecha actual
		        LocalDate fechaActual = LocalDate.now();
				
				DateTimeFormatter formatterAct = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		        String FechActual = fechaActual.format(formatterAct);

		        model.addAttribute("xFechaActual", FechActual);
				
				//Medios de pago
				List<TblMediosPago> MediosDePago  = tblMediosPagoService.ListaMediosDePago(idLocal);
				model.addAttribute("xMediosDePago", MediosDePago);
				
				
			
				session.removeAttribute("xIdLogPago");
				
				
		
		return "Cliente/PagoArchivo";
	}
	
	
	
	@PostMapping("/ConfirmarPago")
	@ResponseBody
	public ResponseEntity<Resource>  ConfirmarPago(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException 
	{
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdLogPago");
	    
	    
	    // Obtenemos los datos del JSON recibido
	    String IdLog = (String) requestBody.get("xIdLog");
	    Integer xIdLog = Integer.parseInt(IdLog);
	    
	    Integer xIdUsuario = usuario.getIdUsuario();
	    Integer xIdLocalUsuario = usuario.getIdLocal();
	    
	    Map<String, Object> response = new HashMap<>();
	    

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
        
        
     // ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
		// Obtenemos el periodo activo
		List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(xIdLocalUsuario);
		
		Integer xPeriodo = 0;
		Integer idTipoOrden = 9;
		
		for(TblDctosPeriodo P : PeriodoActivo) {
			
			xPeriodo = P.getIdPeriodo();
			model.addAttribute("xIdPeriodo", P.getIdPeriodo());
			model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
		
		}

		int xIdTipoOrdenVenta = 9;
		int xIdTipoOrdenPagoProceso = 59;
		
		int xIdMaximaPlanilla = tblPagosService.maximaPlanilla(xIdLocalUsuario, xIdTipoOrdenVenta) + 1;
		
		
		List<TblPagosDTO> PagoProceso = tblPagosService.listaPagoProceso(xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIdLog);
		
		for(TblPagosDTO pago : PagoProceso) {
			
			
			int xIdReciboOld = pago.getIdRecibo();
            int xIndicador = pago.getIndicador();
            Double xIdDcto = pago.getIdDcto();
            
            Integer xIdDctoInt = xIdDcto.intValue();
            
            Double VrPago = pago.getVrPago();
			Double VrRteFuente = pago.getVrRteFuente();
			Double VrDsctoFcro = pago.getVrDescuento();
			Double VrRteIva = pago.getVrRteIva();
			Double VrRteIca = pago.getVrRteIca();
			
			String idCliente = pago.getNitCC();
            
            List<TblDctosDTO> unDcto = TblDctosService.listaUnDcto(xIdLocalUsuario, xIdTipoOrdenVenta, xIdDctoInt, xIndicador);
            
            
            //Obtenemos los valores actualizados de la factura 
			List<TblDctosOrdenesDTO> FacturaActual = tblDctosOrdenesService.ObtenerFacturaActualizada(xIdLocalUsuario, xIdDctoInt, xPeriodo);

			
			//Obtenemos el valor base total 
			double VrBase = FacturaActual.stream()
				    .mapToDouble(TblDctosOrdenesDTO::getVrVentaUnitarioSinIva)
				    .sum();
            
//            Double VrBase = 0.0;
//            for(TblDctosDTO  Dcto : unDcto) {
//            	
//            	VrBase = Dcto.getVrBase();
//            	
//            }
            
            
            if(Math.abs(VrPago - VrBase) < 0.0001) {
            	
//            	(Double  VrPago ,Double VrRteFuente, Double VrDsctoFcro, Double VrRteIva, Double VrRteIca, int idLocal, int IdTipoOrden, int IdDcto, int Indicador )
            	tblDctosRepo.actualizaPago(VrPago, VrRteFuente, VrDsctoFcro, VrRteIva, VrRteIca, xIdLocalUsuario, xIdTipoOrdenVenta, xIdDctoInt, xIndicador);
            	
            	int xIdReciboMAX = tblPagosService.maximoReciboIdLocalxIndicador(xIdLocalUsuario, xIdTipoOrdenVenta, xIndicador) + 1;
            	
            	
            	List<TblDctosDTO> CuentaDetallado = TblDctosService.listaCuentaDetalladoClienteFCH(xIdLocalUsuario, idCliente, xIdTipoOrdenVenta, xIdDctoInt);
            	
            	Double VrSaldo = 0.0;
            	
            	for(TblDctosDTO cuenta : CuentaDetallado ) {
            		
            		VrSaldo = cuenta.getVrSaldo();
            	}
            	
            	Double xVSaldoDctoActualizado = VrSaldo;

            	
            	tblPagosRepo.ingresaPagos(xIdTipoOrdenVenta, xIdReciboMAX, xIndicador, VrPago, xPeriodo, xIdDctoInt, xIdDctoInt.toString(), xIdMaximaPlanilla, 
            			xVSaldoDctoActualizado, xIdUsuario, xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIdReciboOld, xIdLog);
            	
            	
            	
            	tblPagosMediosRepo.ingresaPago(xIdTipoOrdenVenta, xIdReciboMAX, xIndicador, xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIdReciboOld, xIdDctoInt, xIdLog);
            	
            	
            	tblPagosMediosRepo.retiraReciboTemporal(xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIndicador, xIdReciboOld);
            	
            	tblPagosRepo.retiraReciboTemporal(xIdLocalUsuario, xIdTipoOrdenPagoProceso, xIndicador, xIdReciboOld);
            	
            }
           
			
		}
		
		
		// Obtenemos la IP del servidor
        UtilidadesIP utilidadesIP = new UtilidadesIP();
        String xIpTx = utilidadesIP.getIpServidor();
        
        int estadoProgramado = 1;
        int tareaVisita = 6;   // Cotizacion
        int xIdEstadoTxSinTx = 1;

      
        tblAgendaLogVisitasRepo.finalizaVisita(estadoProgramado, tareaVisita, xIdTipoOrdenVenta, xIdEstadoTxSinTx, xIpTx, strFechaVisita, xIdLocalUsuario, xIdLog);
        
        
        
        // -------------------------------------------------------------------------------------- Reporte    -----------------------------------------------------
		
        String formato = "PDF";
		
        int xIdReporte = 1800;
	    
	    //Obtenemos el FileName del reporte y el titulo 
	    List<TblLocalesReporte> reporte = tblLocalesReporteService.listaUnFCH(xIdLocalUsuario, xIdReporte);
	    
	    String xFileNameReporte = "";
	    String xTituloReporte = "";
	    
	    for(TblLocalesReporte R : reporte) {
	    	
	    	xFileNameReporte = R.getFileName();
	    	xTituloReporte = R.getReporteNombre();
	    }
		
		//Obtenemos la información del local que usaremos para los PARAMS del encabezado
	    List<TblLocales> Local = tblLocalesService.ObtenerLocal(xIdLocalUsuario);
		
	    Map<String, Object> params = new HashMap<>();
	    params.put("tipo", formato);
	    params.put("idLocal", xIdLocalUsuario);

	   Integer IdTipoOrdenINI = 9;
	   Integer IdTipoOrdenFIN = 29;
	   Integer IndicadorINICIAL = 1;
	   Integer IndicadorFINNAL = 2;
	   
	   String xPathReport = "";
	   
	   String xCharSeparator = File.separator;
	    for(TblLocales L : Local) {
	    	
		    // Parametros del encabezado 

		    params.put("p_nombreLocal", L.getNombreLocal());
		    params.put("p_nit", L.getNit());
		    params.put("p_titulo", xTituloReporte);
		    params.put("p_idLocal", xIdLocalUsuario);
		    params.put("p_idPlanilla", xIdMaximaPlanilla);
		    params.put("p_idTipoOrden", xIdTipoOrdenVenta);
		    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
	    	
	    }
	    
	   	    
	    
	    
	    
	    List<TblPagosDTO> lista = null;
	    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblPagosService.listaPlanilla(xIdLocalUsuario, xIdTipoOrdenVenta, xIdMaximaPlanilla);
	    	
            System.out.println("lista " + lista);
	    
    
		    // Se crea una instancia de JRBeanCollectionDataSource con la lista 
		    JRDataSource dataSource = new JRBeanCollectionDataSource(lista);
		    
		    ReportesDTO dto = reporteSmsServiceApi.Reportes(params, dataSource, formato, xFileNameReporte, xPathReport); // Incluir (params, dataSource, formato, xFileNameReporte)
		    
		    // Verifica si el stream tiene datos y, si no, realiza una lectura en un búfer
		    InputStream inputStream = dto.getStream();
		    if (inputStream == null) {
		        // Realiza una lectura en un búfer alternativo si dto.getStream() es nulo
		        byte[] emptyContent = new byte[0];
		        inputStream = new ByteArrayInputStream(emptyContent);
		    }
		    
		    
		    // Envuelve el flujo en un InputStreamResource
		    InputStreamResource streamResource = new InputStreamResource(inputStream);
		    
		    // Configura el tipo de contenido (media type)
		    MediaType mediaType;
		    if (params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name())) {
		        mediaType = MediaType.APPLICATION_OCTET_STREAM;
		    } else {
		        mediaType = MediaType.APPLICATION_PDF;
		    }
        


	    
	    
     // Configura la respuesta HTTP
	    return ResponseEntity.ok()
	            .header("Content-Disposition", "inline; filename=\"" + dto.getFileName() + "\"")
	            .contentLength(dto.getLength())
	            .contentType(mediaType)
	            .body(streamResource);
	   
	    
	}
	
	

}
