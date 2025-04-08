package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.ResponseBody;


import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Projection.TblTercerosRutaDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.enums.TipoReporteEnum;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class OrdenDeTrabajoController {
	
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblDctosService TblDctosService;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@GetMapping("/OrdenDeTrabajo")
	public String OrdenDeTrabajo (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
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
				
				int idLocal = usuario.getIdLocal();

				// Obtenemos la lista de periodos 
				List <TblDctosPeriodo> Periodos = tblDctosPeriodoService.ListaTotalPeriodos(idLocal);
				model.addAttribute("xPeriodos", Periodos);
				
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
				
				}
				
				
				List <CtrlusuariosDTO> operarios = ctrlusuariosService.obtenerOperarios(usuario.getIdLocal());
				model.addAttribute("operarios", operarios);
				
				
				List<TblTercerosRuta> Rutas = tblTercerosRutaService.ListaRutas(idLocal);
				model.addAttribute("xRutas", Rutas);
				

	
		
		
		return "/OrdenDeTrabajo";
	}
	
	
	
	
	@PostMapping("/DescargarReporteOrdenDeTrabajo")
	public ResponseEntity<Resource> DescargarReporteOrdenDeTrabajo(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		Integer idLocal = usuario.getIdLocal();

		
		 // Obtenemos los datos del JSON recibido
        String idPeriodo = (String) requestBody.get("idPeriodo");
        System.out.println("idPeriodo en DescargarReporteCortes es  : " + idPeriodo);
        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
        
        
        String idUsuario = (String) requestBody.get("operario");  
        Integer idUsuarioInt = Integer.parseInt(idUsuario);
        String Observacion = (String) requestBody.get("Observacion");
        
        String idCliente = (String) requestBody.get("idCliente");
        
        String emailTercero = "";
    	String telefonoCelularTercero = "";
    	String nombreTercero = "";
    	Integer idRutaTercero = 0;
        
        //Validamos si idCliente no es null
        if(idCliente != null) {
        	
        	int idTipoTercero = 1;        	
        	List<TblTerceros> infoTercero = tblTercerosService.ObtenerInformacionTercero(idLocal, idCliente, idTipoTercero);
        	      	
        	for(TblTerceros tercero : infoTercero) {        		
        		emailTercero = tercero.getEmail();
        		telefonoCelularTercero = tercero.getTelefonoCelular();
        		idRutaTercero = tercero.getIdRuta();  
        		nombreTercero = tercero.getNombreTercero();
        	}
        	
        }else {
        	
        	idCliente = "";
        }
       
        
        //Obtener nombre ruta
        String nombreRuta = "";
        
        if(idRutaTercero != 0) {
        	
        	List<TblTercerosRutaDTO> infoRuta = tblTercerosRutaService.ObtenerRuta(idLocal, idRutaTercero);
        	
        	for(TblTercerosRutaDTO ruta : infoRuta) {        		
        		nombreRuta = ruta.getNombreRuta();
        	}
        }
        
        String idRutaStr = idRutaTercero.toString();
        String rutaCompleta = idRutaStr + nombreRuta;
        
      
        
        
        
        String formato = "PDF";
        System.out.println("xIdPeriodo : " + idPeriodo);
		
		
		int idTipoOrden = 30;
		int indicador = 1;
		
		//Obtenemos el maximo idDcto		
		Integer idDctoMax = TblDctosService.maximoDctoLocalIndicador(idLocal, idTipoOrden, indicador) + 1;
		
		//Obtenemos el maximo idOrden
		Integer idOrdenMax = tblDctosOrdenesRepo.maximaIdOrdenIdLocal(idLocal);
		
		
		 // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);
        
        System.out.println("strFechaVisita  es" + strFechaVisita);
        
        //Obtener nombre operario
        String nombreOperario = ctrlusuariosService.obtenerNombreUsuario(idLocal, idUsuarioInt);
		
//		(int idLocal, int IdTipoOrden, int IdOrden, int IdDcto, int Indicador, String FechaDctoSqlServer, Double VrBaseSinRedondeo, int VrPago, int Estado, Double VrIva, 
//				 int IdTipoNegocio, int VrRteFuente, Double VrDescuento, int VrRteIva, int VrRteIca, String NombreTercero, int IdUsuario, String IdCliente, int DiasPlazo, int PorcentajeDscto, 
//				 int IdCausa, String IdDctoNitCC, String FechaDctoNitCCSqlServer, int VrPagarDctoNitCC, int VrDsctoFcro, Double VrCostoMV, int IdLocalCruce, int IdTipoOrdenCruce, int IdDctoCruce, 
//				 int IdPeriodo, int IdVendedor, Double VrImpoconsumo, Double VrCostoIND, int IdOrdenCruce, int EtapaSTR, int EnvioFE)
		
		//Ingresamos el nuevo idDcto soporte
		tblDctosRepo.ingresaDcto(idLocal, idTipoOrden, idOrdenMax, idDctoMax, indicador, strFechaVisita, null, 0, indicador, null, 
				0, 0, null, 0, 0, nombreTercero, idUsuarioInt, idCliente, 0, 0, 
				0, "", strFechaVisita, 0, 0, null,idLocal, idTipoOrden, idDctoMax, 
				idPeriodoInt, 0, null, null, 0, 0, 0);
		
		
							
		
	    int xIdReporte = 4200;
	    
	    //Obtenemos el FileName del reporte y el titulo 
	    List<TblLocalesReporte> reporte = tblLocalesReporteService.listaUnFCH(idLocal, xIdReporte);
	    
	    String xFileNameReporte = "";
	    String xTituloReporte = "";
	    
	    for(TblLocalesReporte R : reporte) {
	    	
	    	xFileNameReporte = R.getFileName();
	    	xTituloReporte = R.getReporteNombre();
	    }
		
		//Obtenemos la información del local que usaremos para los PARAMS del encabezado
	    List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
		
	    Map<String, Object> params = new HashMap<>();
	    params.put("tipo", formato);
	    params.put("idLocal", idLocal);
	    params.put("p_nombreOperario", nombreOperario);
	    params.put("p_emailTercero", emailTercero);
	    params.put("p_telefonoCelularTercero", telefonoCelularTercero);
	    params.put("p_rutaTercero", rutaCompleta);

	   Integer IdTipoOrdenINI = 9;
	   Integer IdTipoOrdenFIN = 29;
	   Integer IndicadorINICIAL = 1;
	   Integer IndicadorFINNAL = 2;
	   
	   String xPathReport = "";
	   String xPathImagen = "";
	   
	   Integer xIdTipoOrden = 9;
	   
	   
	   
	   
	   String xCharSeparator = File.separator;
	    for(TblLocales L : Local) {
	    	
		    // Parametros del encabezado 
		    params.put("p_idPeriodo", idPeriodoInt);
		    params.put("p_nombreLocal", L.getNombreLocal());
		    params.put("p_nit", L.getNit());
		    params.put("p_titulo", xTituloReporte);
		    params.put("p_direccion", L.getDireccion());
		    params.put("p_idLocal", idLocal);
		    params.put("p_indicadorINI", IndicadorINICIAL);
		    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
		    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
		    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
		  
		    
		    xPathImagen = L.getPathImagen();
		    String xLogoName = xPathImagen + idLocal.toString() + ".jpg";
		    params.put("p_logo", xLogoName);
		    params.put("p_observacion", Observacion);
		    params.put("p_cuentaBanco", L.getCuentaBanco());
		    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
		    
	    	
	    }
	    
	    
	    List<TblDctosDTO> lista = null;
	    			

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = TblDctosService.listaOrdenDeTrabajo(idLocal, idTipoOrden, idDctoMax);

    
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
	
	
	
	
	
	@PostMapping("/ObtenerTerceros")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ObtenerTerceros(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,  Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		Integer idLocal = usuario.getIdLocal();
		System.out.println("idLocal en ObtenerTerceros es  " + idLocal);
		
		Map<String, Object> response = new HashMap<>();
		

		List<TercerosDTO> listaTerceros = tblTercerosService.ListaTercerosSuscriptor(idLocal);
      	System.out.println("listaTerceros  es  " + listaTerceros);
      	
      	for(TercerosDTO tercero : listaTerceros) {
      		
      		System.out.println("nombre tercero es   " + tercero.getNombreTercero());
      		System.out.println("CCNIT tercero es   " + tercero.getCC_Nit());
      	}
      	

      	response.put("xListaTerceros", listaTerceros);     	
		
      	return ResponseEntity.ok(response);
		
	}

}
