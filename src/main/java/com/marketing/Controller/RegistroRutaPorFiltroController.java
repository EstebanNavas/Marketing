package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMedidoresMacro;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.TblCiudadesDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TblPagosDTO;
import com.marketing.Projection.TercerosDTO;
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
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Controller
public class RegistroRutaPorFiltroController {
	
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
	
	@GetMapping("/RegistroRutaPorFiltro")
	public String registroRutaPorFiltro (HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer xIdUsuario = usuario.getIdUsuario();

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
				
		
		return "Cliente/RegistroRutaPorFiltro";
	}
	
	
	// Declaración e inicialización de la variable fuera del método
		private static int xInicioRegistroTx = 0;
	
	
	
	@PostMapping("/Registrar")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> Registrar(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
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
	        
	        
	        List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
	        
	        Integer xCuentaRegistroTx = 0;
	        
	        for(TblLocales L : Local) {
		    	
	        	xCuentaRegistroTx = L.getCuentaRegistroTx();
		    }
	        
	        
	        int xIdTipoOrdenPago = 9;
	        int xIdTipoOrdenPagoProceso = xIdTipoOrdenPago + 50;
	        
	        int xIdTipo = 4;
	        
	        // RETIRA CAMBIO ESTRATO
	        tblDctosOrdenesDetalleRepo.retiraCambioEstrato(idLocal, xIdTipoOrdenPagoProceso, idPeriodoInt);
	        
	        // CREA PROCESO DE LECTURA
	        boolean resultado =   procesoCreaLecturaMovil.crea(idLocal, xIdTipoOrdenPagoProceso, idPeriodoInt, xIdUsuario);
	        
	         xInicioRegistroTx = 0;
	        
	        Integer xIdPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(idPeriodoInt, idLocal);
	        //(int idLocal, int xIdPeriodoAnterior, int xIdTipo, int idPeriodo, int xIdRuta, int xInicioRegistroTx, int xCuentaRegistroTx )
	        List<TercerosDTO2> lista = tblTercerosService.listaLecturaRutaTx(idLocal, xIdPeriodoAnterior, xIdTipo, idPeriodoInt, idRutaInt, xInicioRegistroTx, xCuentaRegistroTx);
	        
	        
		    for(TercerosDTO2 L : lista ) {
		    	
		    	System.out.println("lista CantidadPedida es  : " + L.getCantidadPedida());
		    }
	        
	        ArrayList<TblTipoCausaNota> EstadoLectura = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(2);
	        
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("lista", lista);
		    response.put("EstadoLectura", EstadoLectura);
		    response.put("xInicioRegistroTx", xInicioRegistroTx);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	@PostMapping("/GuardarRegistro")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> GuardarRegistro(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    int idLocal = usuario.getIdLocal();
	    Integer xIdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /GuardarRegistro");

	        // Obtenemos los datos del JSON recibido
	    	// Obtener los valores de los arrays
	    @SuppressWarnings("unchecked")
		List<String> xIdClienteArr = (List<String>) requestBody.get("xnuidArr");
	    @SuppressWarnings("unchecked")
	    List<String> xLecturaMedidorArr = (List<String>) requestBody.get("xlecturaActualArr");
	    @SuppressWarnings("unchecked")
	    List<String> xLecturaMedidorAnteriorArr = (List<String>) requestBody.get("xlecturaAnteriorArr");
	    @SuppressWarnings("unchecked")
	    List<String> xIdCausaArr = (List<String>) requestBody.get("xEstadoLecturaArr");
	    @SuppressWarnings("unchecked")
	    List<String> xCantidadPedida = (List<String>) requestBody.get("xCantidadPedidaArr");
	    
	    
	    String[] xIdClienteArrArray = xIdClienteArr.toArray(new String[0]);
	    String[] xLecturaMedidorArrArray = xLecturaMedidorArr.toArray(new String[0]);
	    String[] xLecturaMedidorAnteriorArrArray = xLecturaMedidorAnteriorArr.toArray(new String[0]);
	    String[] xIdCausaArrArray = xIdCausaArr.toArray(new String[0]);
	    String[] xCantidadPedidaArray = xCantidadPedida.toArray(new String[0]);
	    
	    
	    String idPeriodo = (String) requestBody.get("idPeriodo");
        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
        
        String idRuta = (String) requestBody.get("idRuta");
        Integer idRutaInt = Integer.parseInt(idRuta);

	        
	    System.out.println(" /GuardarRegistro xIdClienteArr es: " + xIdClienteArr);
	    System.out.println(" /GuardarRegistro xLecturaMedidorArr es: " + xLecturaMedidorArr);
	    System.out.println(" /GuardarRegistro xLecturaMedidorAnteriorArr es: " + xLecturaMedidorAnteriorArr);
	    System.out.println(" /GuardarRegistro xIdCausaArr es: " + xIdCausaArr);
	    System.out.println(" /GuardarRegistro xCantidadPedida es: " + xCantidadPedida);
	    
	    
	    
	    // REPLICAR CODIGO DE ITERACION
	    // DENTRO DE LA ITERACION SE EJECUTA EL METODO guardaTx
	    
	    int xIdTipoOrdenPago = 9;
        int xIdTipoOrdenPagoProceso = xIdTipoOrdenPago + 50;
        
        int xIdTipo = 4;
	    
	    String xIdCliente = "-1";
	    
	    
	    
	    for (int i = 0; i < xIdClienteArrArray.length; i++) { 
	    	
	    	System.out.println("INGRESÓ AL FOR");
	    	
	        xIdCliente = xIdClienteArrArray[i];
	        double xLecturaMedidor = Double.parseDouble(xLecturaMedidorArrArray[i]);
	        double xCantidad = Double.parseDouble(xLecturaMedidorArrArray[i]) - Double.parseDouble(xLecturaMedidorAnteriorArrArray[i]);
	        double xCantidadPedidaDoub = 0.0;
	        
	        int xIdCausaConsumoAforo = 91; 
	        int xIdCausa = Integer.parseInt(xIdCausaArrArray[i]);
	        
	        if (xIdCausa == xIdCausaConsumoAforo) {
	            xCantidad = Double.parseDouble(xCantidadPedidaArray[i]);
	            xCantidadPedidaDoub = Double.parseDouble(xCantidadPedidaArray[i]);
	        }
	        
	        
	        Integer maximoItem = procesoGuardaLecturaMovil.guardaTx(idLocal, xIdTipoOrdenPagoProceso, idPeriodoInt, xIdCliente, xLecturaMedidor, xIdUsuario, xCantidad, xIdCausa, xCantidadPedidaDoub);
	        System.out.println("maximoItem es: " + maximoItem);
	    }
	    
	    
        List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
        
        Integer xCuentaRegistroTx = 0;
        
        for(TblLocales L : Local) {
	    	
        	xCuentaRegistroTx = L.getCuentaRegistroTx();
	    }
	    
        xInicioRegistroTx += 15;
        System.out.println("xInicioRegistroTx es : " + xInicioRegistroTx);
	    
	    Integer xIdPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(idPeriodoInt, idLocal);
	    List<TercerosDTO2> lista = tblTercerosService.listaLecturaRutaTx(idLocal, xIdPeriodoAnterior, xIdTipo, idPeriodoInt, idRutaInt, xInicioRegistroTx, xCuentaRegistroTx);
	    
	    
	    System.out.println("lista es : " + lista);
	    
	    for(TercerosDTO2 L : lista ) {
	    	
	    	System.out.println("lista CantidadPedida es  : " + L.getCantidadPedida());
	    	System.out.println("lista IDCAUSA es  : " + L.getIdCausa());
	    }
	
	        
	        
	     
	        
	        ArrayList<TblTipoCausaNota> EstadoLectura = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(2);
		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("lista", lista);
		    response.put("EstadoLectura", EstadoLectura);
		    response.put("xInicioRegistroTx", xInicioRegistroTx);
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	@PostMapping("/DescargarReporteExcelRutaPorFiltro")
	public ResponseEntity<Resource> DescargarReporteExcelRutaPorFiltro(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	   
	    // Validar si el local está logueado	
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		String sistema=(String) request.getSession().getAttribute("sistema");

		
		 // Obtenemos los datos del JSON recibido
        String idPeriodo = (String) requestBody.get("idPeriodo");
        System.out.println("idPeriodo en DescargarReporteExcelRutaPorFiltro es  : " + idPeriodo);
        Integer idPeriodoInt = Integer.parseInt(idPeriodo);

        
        String idRuta = (String) requestBody.get("idRuta");
        System.out.println("idRuta en DescargarReporteExcelRutaPorFiltro es  : " + idRuta);
        Integer idRutaInt = Integer.parseInt(idRuta);
		
		
        String formato = (String) requestBody.get("formato");

		
		int idLocal = usuario.getIdLocal();
		
	    int xIdReporte = 2770;
	    
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

	   Integer IdTipoOrdenINI = 9;
	   Integer IdTipoOrdenFIN = 29;
	   Integer IndicadorINICIAL = 1;
	   Integer IndicadorFINNAL = 2;
	   
	   String xPathReport = "";
	   
	   
	    for(TblLocales L : Local) {
	    	
		    // Parametros del encabezado 
		    params.put("p_idPeriodo", idPeriodo);
		    params.put("p_nombreLocal", L.getNombreLocal());
		    params.put("p_nit", L.getNit());
		    params.put("p_titulo", xTituloReporte + " DEL PERIODO " + idPeriodo);
		    params.put("p_direccion", L.getDireccion());
		    params.put("p_idLocal", idLocal);
		    params.put("p_indicadorINI", IndicadorINICIAL);
		    params.put("p_idTipoOrdenINI", IdTipoOrdenINI);
		    params.put("p_indicadorFIN", IndicadorFINNAL);    // TERMINAR DE DEFINIR DE DONDE SE OBTIENEN ESTAS VARIALES 
		    params.put("p_idTipoOrdenFIN", IdTipoOrdenFIN);
		    params.put("p_idRuta", idRutaInt);
		    xPathReport = L.getPathReport();
	    	
	    }
	    
	    
	    Integer xIdPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(idPeriodoInt, idLocal);
	    
	    int xIdTipoConsumo = 4;
	    
	    List<TercerosDTO2> lista = null;
	    

            // QUERY PARA ALIMENTAR EL DATASOURCE
            lista = tblTercerosService.listaLecturaRutaAll(idLocal, xIdPeriodoAnterior, idPeriodoInt, xIdTipoConsumo);

    
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
	
	
	
	@PostMapping("/Renumerar-Post")
	public ModelAndView RenumerarPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /ActualizarSuscriptor");

	    // Obtenemos los datos del JSON recibido
	    String idTercero = (String) requestBody.get("idTercero");
	    //Integer idTercero = Integer.parseInt(idTerceroString);
	    
	    String idPeriodo = (String) requestBody.get("idPeriodo");
	    String xInicioRegistroTx = (String) requestBody.get("xInicioRegistroTx");
	    
	    System.out.println("xInicioRegistroTx en  Renumerar-Post es : " + xInicioRegistroTx);


	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/Renumerar?idTercero=" + idTercero + "&idPeriodo=" + idPeriodo);
	    return modelAndView;
	}
	
	
	@GetMapping("/Renumerar")
	public String Renumerar(@RequestParam(name = "idTercero", required = false) String idTercero,
            				@RequestParam(name = "idPeriodo", required = false) String idPeriodo,
            				HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerSuscriptor con idTercero: " + idTercero);
		
		
		List<String> listaIdClientes = new ArrayList<>(); // Crear una lista de String
		
		listaIdClientes.add(idTercero); // Agregar el valor de idCliente a la lista
		
		Integer idTipoTercero = 1;
		
		if(usuario == null) {
			model.addAttribute("usuario", new Ctrlusuarios());
			return "redirect:/";
		}else { 
			
			System.out.println("Entró a /Renumerar");
		    
		    HttpSession session = request.getSession();
		    Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
		    
		    Integer idPeriodoInt = Integer.parseInt(idPeriodo);
		    
		    int xIdTipo = 4;
		    
		    Integer xIdPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(idPeriodoInt, usuario.getIdLocal());
		    
		    List<TercerosDTO2> lista = tblTercerosService.listaLecturaRutaTxPorCliente(usuario.getIdLocal(), xIdPeriodoAnterior, xIdTipo, idPeriodoInt, listaIdClientes);
		    
		    
		    
		    for(TercerosDTO2 L : lista) {
		    	
		    	model.addAttribute("xnombreTercero", L.getNombreTercero());
		    	model.addAttribute("xnuid", L.getIdCliente());
		    	
		    	model.addAttribute("xLecturaAnterior", L.getLecturaAnterior());
		    	
		    	model.addAttribute("xRuta", L.getNombreRuta());
		    	model.addAttribute("xEstracto", L.getNombreEstracto());
		    }
		    
		    
		    model.addAttribute("xIdPeriodo", idPeriodoInt);

			
			return "Cliente/Renumerar";
			
		}

	}
	
	
	@PostMapping("/ActualizarLectura-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarLectura(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /ActualizarSuscriptor-Post");

	        // Obtenemos los datos del JSON recibido
	        String idPeriodo = (String) requestBody.get("idPeriodo");
	        String LecturaCorregida = (String) requestBody.get("LecturaCorregida");  
	        Double LecturaCorregidaDouble = Double.parseDouble(LecturaCorregida);
	        
	        String idCliente = (String) requestBody.get("nuid"); 
	        
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	        
	        //Obtenemos el Periodo anterior al actual 
	        Integer xIdPeriodoAnterior = tblDctosPeriodoService.ObtenerPeriodoAnteriorFacturado(idPeriodoInt, usuario.getIdLocal());
	        System.out.println("xIdPeriodoAnterior es  ActualizarLectura-Post es: " + xIdPeriodoAnterior);
	        
	        //Obtenemos el idOrden que el idTipoOrden sea = 9 (Ya facturado)
	        Integer idOrden = tblDctosOrdenesService.ObtenerIdOrdenConFactura(usuario.getIdLocal(), xIdPeriodoAnterior, idCliente);
	        System.out.println("idOrden es  ActualizarLectura-Post es: " + idOrden);
	        
	        
	        
	        // Actualiamos la lectura del periodo anterior
	        tblDctosOrdenesDetalleRepo.actualizarLecturaAnterior(LecturaCorregidaDouble, usuario.getIdLocal(), idOrden, idCliente);
		    
	        System.out.println("LECTURA ACTUALIZADA CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	@PostMapping("/RegistrarSuscriptor")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> BuscarSuscriptor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    System.out.println("SI ENTRÓ A  /RegistrarSuscriptor");

	        // Obtenemos los datos del JSON recibido
	        String palabraClave = (String) requestBody.get("palabraClave");
	        System.out.println("palabraClave desde /BuscarSuscriptor " + palabraClave);
	        
	        
	        String idPeriodo = (String) requestBody.get("idPeriodo");
	        Integer idPeriodoInt = Integer.parseInt(idPeriodo);

	        List<TercerosDTO> ListaBusqueda = tblTercerosService.BuscarTercerosSuscriptor(usuario.getIdLocal(), palabraClave);
	        System.out.println("La ListaBusqueda generada es:  " + ListaBusqueda );
	        

	        List<String> listaIdClientes = new ArrayList<>(); // Crear una lista de String
	        
		    for(TercerosDTO busqueda : ListaBusqueda) {
		    	
		    	System.out.println("busqueda " + busqueda.getIdTercero());
		    	System.out.println("busqueda nombre  " + busqueda.getNombreTercero());
		    	System.out.println("busqueda " + busqueda.getDireccionTercero());
		    	System.out.println("busqueda " + busqueda.getNombreCausa());
		    	
		    	String idCliente = busqueda.getIdTercero().toString(); // Declarar idCliente dentro del bucle
		        listaIdClientes.add(idCliente); // Agregar el valor de idCliente a la lista
		    	
		    	
		    }
		    
		    
		    
		    
		    
		    int xIdTipo = 4;
		    
		    Integer xIdPeriodoAnterior = tblDctosPeriodoService.listaAnteriorFCH(idPeriodoInt, usuario.getIdLocal());
		    
		    List<TercerosDTO2> lista = tblTercerosService.listaLecturaRutaTxPorCliente(usuario.getIdLocal(), xIdPeriodoAnterior, xIdTipo, idPeriodoInt, listaIdClientes);
		    
		    ArrayList<TblTipoCausaNota> EstadoLectura = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(2);

		    
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("lista", lista);
		    response.put("EstadoLectura", EstadoLectura);
		    return ResponseEntity.ok(response);
	   
	    
	}

}
