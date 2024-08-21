package com.marketing.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

import com.marketing.MailjetTask;
import com.marketing.WhatsAppTask;
import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaEventoLogService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.GeneradorZip;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReporteFacturaWhatsAppController {
	
	@Autowired 
	TblTercerosService tblTercerosService;
	
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
	TblPagosService tblPagosService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	TblAgendaEventoLogService tblAgendaEventoLogService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	MailjetTask mailjetTask;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@Autowired
	WhatsAppTask whatsAppTask;
	
	
	@GetMapping("/ReporteFacturaWhatsApp")
	public String reporteFacturaWhatsApp (HttpServletRequest request,Model model) {
		
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

				// Obtenemos la lista de periodos 
				List <TblDctosPeriodo> Periodos = tblDctosPeriodoService.ListaTotalPeriodos(idLocal);
				model.addAttribute("xPeriodos", Periodos);
				
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
				
				}
				
				
				List<TblTercerosRuta> Rutas = tblTercerosRutaService.ListaRutas(idLocal);
				model.addAttribute("xRutas", Rutas);
				

	
		
		
		return "Reporte/ReporteFacturaWhatsApp";
	}
	
	
	
	
	@PostMapping("/EnviarFacturaWhatsAppXCliente")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> EnviarFacturaWhatsAppXCliente(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Map<String, Object> response = new HashMap<>();
	    // Crea la lista de strings
	    List<String> listaIdClientes = new ArrayList<>();
	    
	    System.out.println("SI ENTRÓ A  EnviarFacturaWhatsAppXCliente");

	        // Obtenemos los datos del JSON recibido
	    	String idCliente = (String) requestBody.get("idTercero");
	    	// Agrega el idCliente a la lista
	    	listaIdClientes.add(idCliente);
	    
	        String idPeriodo = (String) requestBody.get("PeriodoCobro");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	        Double idPeriodoDouble = Double.parseDouble(idPeriodo);

	        String formato = "PDF";
	        

	        System.out.println("idCliente es : " + idCliente);
	        Integer idLocal = usuario.getIdLocal();
			
		    int xIdReporte = 1140;
		    
		    
		    //Obtenemos el FileName del reporte y el titulo 
		    List<TblLocalesReporte> reporte = tblLocalesReporteService.listaUnFCH(idLocal, xIdReporte);
		    
		   final String[] xFileNameReporte = new String[1];
		   final String[] xTituloReporte = new String[1];
		    
		    for(TblLocalesReporte R : reporte) {
		    	
		    	xFileNameReporte[0] = R.getFileName();
		    	xTituloReporte[0] = R.getReporteNombre();
		    }
			
			//Obtenemos la información del local que usaremos para los PARAMS del encabezado
		    List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
			
		    Map<String, Object> params = new HashMap<>();
		    params.put("tipo", formato);
		    params.put("idLocal", idLocal);

		   Integer IdTipoOrdenINI = 9;
		   Integer IdTipoOrdenFIN = 29;
		   Integer IndicadorINICIAL = 1;
		   Integer IndicadorFINNAL = 2;
		   String xFromAddress = ""; // SE OBTIENE EL EMAIL DEL LOCAL
		   String xToAddress = "";                            // EN ESTA VARIABLE SE VA A GUARDAR EL CORREO DE LOS USUARIOS
		   String xTextoEmail = "";
		   String xTxtFactura = "";
		   String xPrefijo = "";
		   String xPathFileGralZip = "";
		   String NitNE = "";
		   String NombreLocal = "";
		   String Prefijo = "";
		   
		   final String[] xPathReport = new String[1];

		   String xPathImagen = "";
		   
		   String xTextoComentario = " ==> Su factura presenta cuentas vencidas."
	                + " Lo esperamos en la administración del acueducto para normalizar su situación. ";
		   
		   String xTextoSubsidioContribucion = "==> Incluye subsidio y contribución del mes";
		   
		   
		   String xCharSeparator = File.separator;
		   final String[] xPathFileGralDB = new String[1];
	        String StringPathLinux = "/home/sw"; 
	        String StringPathWindows = "c:"; 
	        
	        
	        Integer xEstadoGeneraIAC = null;
	        
	        int xEstadoGeneraIAC_SI = 1;
	        
	        
	        
	       
	        
	        
		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 
			    params.put("p_idPeriodo", idPeriodo);
			    params.put("p_nombreLocal", L.getNombreLocal());
			    NombreLocal = L.getNombreLocal();
			    params.put("p_nit", L.getNit());
			    params.put("p_titulo", xTituloReporte[0]);
			    params.put("p_direccion", L.getDireccion());
			    params.put("p_idLocal", idLocal);
			    params.put("p_indicadorINI", IndicadorINICIAL);
			    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
			    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
			    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
			    params.put("p_telefono", L.getTelefono());
			    params.put("p_fax", L.getFax());
			    params.put("p_email", L.getEmail());
			    params.put("p_resolucion", L.getResolucion());
			    params.put("p_prefijo", L.getPrefijo());
			    Prefijo = L.getPrefijo();
			    params.put("p_fechaResolucion", L.getFechaResolucion());
			    params.put("p_ciudad", L.getCiudad());
			    params.put("p_rango", L.getRango());
			    
			    xPathImagen = L.getPathImagen();
			    String xFirmaDigital = xPathImagen + "codigoBarra_" + idLocal.toString() + ".jpg";
				   
		        System.out.println("xFirmaDigital es : " + xFirmaDigital);
			    
			    System.out.println("xPathImagen es : " + xPathImagen);
			    String xLogoName = xPathImagen + idLocal.toString() + ".jpg";
			    params.put("p_logo", xLogoName);
			    System.out.println("xLogoName es : " + xLogoName);
			    params.put("p_cuentaBanco", L.getCuentaBanco());
			    params.put("p_idTipoOrden", IdTipoOrdenINI);
			    params.put("p_txtFactura", L.getTxtFactura());
			    params.put("p_textoLegal", L.getTextoLegal());
			    params.put("p_textoComentario", xTextoComentario);
			    params.put("p_textoSubsidioContribucion", xTextoSubsidioContribucion);
			    params.put("p_historiaConsumo", "Histórico M3 : ");

			    params.put("p_firmaDigital", xFirmaDigital);
			  
			    params.put("p_representanteLegal", L.getRepresentanteLegal());
			    params.put("p_txtSuspension", L.getTxtSuspension());
			    
			    xPathReport[0] = L.getPathReport()  + "marketing" + xCharSeparator;
			    xEstadoGeneraIAC = L.getEstadoGeneraIAC();
			    
			    xFromAddress = L.getEmail();
			    xTextoEmail = L.getTextoEmail();
			    xTxtFactura = L.getTxtFactura();
			    xPrefijo = L.getPrefijo();
			    xPathFileGralDB[0] = L.getPathFileGral(); //--------------------------------------------------------------------------------
			    NitNE = L.getNitNE();
			    
		    }
		    
		    
		    System.out.println("xPathFileGralDB es : " + xPathFileGralDB);
		    
		    
		    
		    String xPathPDF = xPathFileGralDB[0] + "aquamovil" + xCharSeparator + "BDWhatsAppFactura" + xCharSeparator + idLocal + xCharSeparator;
		    String xPathXML = xPathFileGralDB[0] + "aquamovil" + xCharSeparator + "zip" + xCharSeparator + idLocal + xCharSeparator;
	        String xPathFileChar = xPathFileGralDB[0] + "aquamovil" + xCharSeparator + "img" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator;
	        String xPathBarCode = xPathFileGralDB[0] + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator;
	        String xPathQr = xPathFileGralDB[0] + "aquamovil" + xCharSeparator + "qr" + xCharSeparator + idLocal + xCharSeparator;
	        String xPathZippdfxml = xPathFileGralDB[0] + "aquamovil" + xCharSeparator + "zippdfxml" + xCharSeparator + idLocal + xCharSeparator;
		    
	        params.put("p_pathFileChar", xPathFileChar);
	        params.put("p_Qr", xPathQr);
		    
		    
		    // Genera imagen IAC CODE128
	        if (xEstadoGeneraIAC_SI == xEstadoGeneraIAC) {
	          //  String xBarraName = xPathFileGral + xCharSeparator + "FileGral" + xCharSeparator + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            
	            String xBarraName = xPathFileGralDB[0] + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            params.put("p_barraName", xBarraName);
	        }
	        
            
            System.out.println("xPathZippdfxml en rea directorio es " + xPathZippdfxml);
            
            //--- Crea directorio zippdfxml
            File zipDirectoryXml = new File(xPathZippdfxml);
            if (!zipDirectoryXml.isDirectory()) {
            	zipDirectoryXml.mkdirs();
            }
            
            //--- Crea directorio zip
            File zipDirectory = new File(xPathXML);
            if (!zipDirectory.isDirectory()) {
                zipDirectory.mkdirs();
            }
		   
            

            List <TblDctosPeriodo> periodo =  tblDctosPeriodoService.listaUnFCH(idPeriodoInt, idLocal);
            
            String xNombrePeriodo = "";
  
            
            String xFechaConRecargoStr = "";
            
            for(TblDctosPeriodo P : periodo) {
            	
            	xNombrePeriodo = P.getNombrePeriodo();
            	Timestamp xFechaConRecargo = P.getFechaConRecargo();
            	
            	xFechaConRecargoStr = xFechaConRecargo.toString();
            	
            }
            
            
            
           //Actualiza todos los TelefonoCelular estado 2
            tblTercerosRepo.actualizaEstadoWhatsAppInactivo(usuario.getIdLocal());
            
            
            //Actualiza estadoWhastApp = 1 de los TelefonoCelular que esten correctos
            tblTercerosRepo.actualizaEstadoWhatsAppOK(usuario.getIdLocal());
            
		    
            int idEvento = 200;

	            // Obtenemos la lista de los terceros que NO se le hayan enviado WhatsApp y que el estado WhatsApp sea activo =  1
	            List<TercerosDTO> alista =  tblTercerosService.listaUnCliente(idLocal, idPeriodoInt, listaIdClientes, idEvento);
	            
	            System.out.println("alista es " + alista);
	            
	            // Verificamos si la alista está vacia
	            if(alista.isEmpty()) {
	            	
	            	System.out.println("alista vacia" );
	            	
	            	response.put("message", "VACIO");            	
	            	return ResponseEntity.ok(response);
	            }

	            
	            // Iteramos sobre la lista de Clientes 
	            for (TercerosDTO aList : alista) {
	               final Integer idDcto = aList.getIdDcto();
	                System.out.println("idDcto es " + idDcto);
	                
	                final Long NUID = aList.getIdCliente();
	                final String  telefonoCelular = aList.getTelefonoCelular();
	                final String nombreTercero = aList.getNombreTercero();
	                
	                List<TblLocales> LocalObtenido = tblLocalesService.ObtenerLocal(idLocal);
	                
	                String nombreLocal = "";
	                
	                for(TblLocales local : LocalObtenido) {
	                	
	                	nombreLocal = local.getRazonSocial();
	                }

	                GeneradorZip generadorZip = new GeneradorZip();
	                String xSistema = "aquamovil";

	                //Query que alimenta el datasource
	                List<TblDctosOrdenesDTO> lista = tblDctosOrdenesService.listaUnSoloClienteProducto(idLocal, NUID.toString(), idPeriodoDouble);
	                System.out.println("lista " + lista);

	                JRDataSource dataSource = new JRBeanCollectionDataSource(lista);

	                final Integer finalIdDcto = idDcto;
	                final String finalXMensaje = "PERIODO COBRO: " + xNombrePeriodo + "\n"
	                        + "FECHA PAGO CON RECARGO: " + xFechaConRecargoStr + "\n"
	                        + xTextoEmail;
	                final String finalXToAddress = aList.getEmail();
	                final String finalXAsunto = NitNE + ";" + NombreLocal + ";" + Prefijo + finalIdDcto + ";" + "01" + ";" + NombreLocal;
	                final String finalXTextPart = "";
	                //final String finalPathFile = xPathPDF + finalIdDcto + ".pdf";
	                final String finalPathFile = xPathPDF;
	                final String finalFileName = finalIdDcto + ".pdf";
	                final String finalNombreLocal = nombreLocal;
	                
	                
	                //Tarea Asincronica que genera el PDF en un hilo separado
	                CompletableFuture<Void> reporteTask = CompletableFuture.runAsync(() -> {
	                    try {
	                        System.out.println("Creando reporte para idDcto " + finalIdDcto);
	                        ReportesDTO dto = reporteSmsServiceApi.ReporteEnCarpeta(params, dataSource, formato, xFileNameReporte[0], xPathReport[0], xPathPDF, xPathXML, finalIdDcto);
	                        System.out.println("Reporte creado para idDcto " + finalIdDcto);
	                    } catch (SQLException e) {
	                        e.printStackTrace(); 
	                    } catch (JRException e) {
							
							e.printStackTrace();
						} catch (IOException e) {
							
							e.printStackTrace();
						}
	                });

	                
	                //Se ejecuta esta tarea despues que reporteTask se haya completado
//	                CompletableFuture<Void> zipTask = reporteTask.thenRunAsync(() -> {
//	                    try {
//	                        System.out.println("Agregando PDF a zip para idDcto " + finalIdDcto);
//	                        generadorZip.AgregarPdfAZip(idLocal, xSistema, finalIdDcto, xPathFileGralDB[0]);
//	                        System.out.println("PDF agregado a zip para idDcto " + finalIdDcto);
//	                    }catch (FileNotFoundException e) {
//							
//							e.printStackTrace();
//						} catch (IOException e) {
//							
//							e.printStackTrace();
//						}
//	                });
	                
	                // Codigo de Wpp

	              //Por ultimo se ejecuta la tarea de enviar el Email despues que se haya generado el PDF
	                CompletableFuture<Void> wppTask = reporteTask.thenRunAsync(() -> {
	                    try {
	                        System.out.println("Enviando email para idDcto " + finalIdDcto);
	                        whatsAppTask.ejecutarJar(idLocal, finalIdDcto, finalPathFile, NUID.toString(), telefonoCelular, nombreTercero, finalNombreLocal, idPeriodoInt);
	                        System.out.println("Email enviado para idDcto " + finalIdDcto);
	                    } catch (Exception e) {
	                        e.printStackTrace(); // Manejo de cualquier otra excepción
	                    }
	                });

	                // Esperamos a que todas las tareas se completen
	                try {
	                	wppTask.get();
	                } catch (InterruptedException | ExecutionException e) {
	                    e.printStackTrace();
	                }
	            }

		    
	            response.put("message", "OK");   
			    return ResponseEntity.ok(response);
	   
	    
	}

}
