package com.marketing.Controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

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

import com.marketing.CharConsumoTask;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblTercerosRutaDTO;
import com.marketing.Repository.dbaquamovil.Bak_DctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosPeriodoRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosMediosRepo;
import com.marketing.Repository.dbaquamovil.TblPagosRepo;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.Utilidades.ControlDeInactividad;

@Controller
public class PeriodoController {

	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblTipoCausaNotaService tblTipoCausaNotaService;
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	TblDctosPeriodoRepo tblDctosPeriodoRepo;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblPagosMediosRepo tblPagosMediosRepo;
	
	@Autowired
	TblPagosRepo tblPagosRepo;
	
	@Autowired
	Bak_DctosOrdenesRepo bak_DctosOrdenesRepo;
	
	@Autowired
	CharConsumoTask charConsumoTask;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@Autowired
	TblDctosService tblDctosService;
	
	@GetMapping("/Periodo")
	public String Referencia(HttpServletRequest request,Model model) {
		
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
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

		    
		    
		    List <TblDctosPeriodo> listaPeriodos = tblDctosPeriodoService.ListaTotalPeriodos(usuario.getIdLocal());
		    model.addAttribute("listaPeriodos", listaPeriodos);
		    
		    if (!listaPeriodos.isEmpty()) {
		        // Obtenemos el primer elemento de la lista
		        TblDctosPeriodo primerPeriodo = listaPeriodos.get(0);
		        
		        int xIdPeriodo = primerPeriodo.getIdPeriodo(); //Obtenemos el idPeriodo
		        Timestamp xfechaInicioConsumo = primerPeriodo.getFechaInicial(); 
		        Timestamp xfechaFinConsumo = primerPeriodo.getFechaFinal(); 
		        Timestamp xfechaSinRecargo = primerPeriodo.getFechaSinRecargo(); 
		        Timestamp xfechaConRecargo = primerPeriodo.getFechaConRecargo(); 
	
		        model.addAttribute("xIdPeriodo", xIdPeriodo);
		        model.addAttribute("xfechaInicioConsumo", xfechaInicioConsumo);
		        model.addAttribute("xfechaFinConsumo", xfechaFinConsumo);
		        model.addAttribute("xfechaSinRecargo", xfechaSinRecargo);
		        model.addAttribute("xfechaConRecargo", xfechaConRecargo);
		    } else {
		        System.out.println("La lista de periodos está vacía.");
		    }
	    

			
			return "Periodo/Periodo";


	}
	
	
	
	@PostMapping("/CrearPeriodo-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> CrearRuta(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
		
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  /CrearPeriodo");

	        // Obtenemos los datos del JSON recibido
	        String nuevoPeriodo = (String) requestBody.get("nuevoPeriodo");
	        Integer xIdPeriodo = Integer.parseInt(nuevoPeriodo);

	        String nombre = (String) requestBody.get("nombre");
	        String FechaInicioConsumoStr = (String) requestBody.get("FechaInicioConsumo");
	        String fechaFinConsumoStr = (String) requestBody.get("fechaFinConsumo");
	        String fechaSinRecargoStr = (String) requestBody.get("fechaSinRecargo");
	        String fechaConrecargoStr = (String) requestBody.get("fechaConrecargo");
	        
	        
	        Timestamp FechaInicioConsumo = null;
	        Timestamp fechaFinConsumo = null;
	        Timestamp fechaSinRecargo = null;
	        Timestamp fechaConrecargo = null;
	        
	        
	        // Formato de la fecha
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        
	        try {

	            // Parsear la primera fecha
	            Date parsedDate1 = dateFormat.parse(FechaInicioConsumoStr + " 00:00:00.000");
	            FechaInicioConsumo = new Timestamp(parsedDate1.getTime());
	            System.out.println("Fecha con recargo 1: " + FechaInicioConsumoStr + ", Timestamp: " + FechaInicioConsumo);
	            
	            Date parsedDate2 = dateFormat.parse(fechaFinConsumoStr);
	            fechaFinConsumo = new Timestamp(parsedDate2.getTime());
	
	            Date parsedDate3 = dateFormat.parse(fechaSinRecargoStr);
	            fechaSinRecargo = new Timestamp(parsedDate3.getTime());

	            Date parsedDate4 = dateFormat.parse(fechaConrecargoStr);
	            fechaConrecargo = new Timestamp(parsedDate4.getTime());
	            
	            
	        } catch (ParseException e) {
	            
	            e.printStackTrace();
	        }
	        

	        
	        
	        //Integer xIdPeriodo = 202306;
	        
	     
	        //Obtenemos de tbllocales el periodoFactura
	        Integer xPeriodoFactura = tblLocalesService.ObtenerPeriodoFactura(usuario.getIdLocal());
	        
	        //Obtenemos la lista de los idPeriodos
	        List <String> xIdPeriodoLista = tblDctosPeriodoService.listaPeriodo(usuario.getIdLocal(), xPeriodoFactura, xIdPeriodo);
	        System.out.println("xIdPeriodoLista " + xIdPeriodoLista);
	        
	        //promd
	        tblTercerosRepo.actualizaPromedio(usuario.getIdLocal(), xIdPeriodoLista, xPeriodoFactura);
	        System.out.println("PromedioActualziado");
	        
	        //actualizaPromedioEstrato
	        tblTercerosRepo.actualizaPromedioEstrato(usuario.getIdLocal(), xIdPeriodoLista, xPeriodoFactura);
	        System.out.println("PromedioEstratoActualziado");
	        
	        // Eliminamos la tabla tmp_historicoConsumo
	        tblDctosPeriodoRepo.eliminaTablaHistoricoConsumo();
	        System.out.println("HistoricoConsumo eliminado");
	        
	        //Creamos de nuevo la tabla  tmp_historicoConsumo
	        tblDctosOrdenesDetalleRepo.creaTablaHistoricoConsumo(usuario.getIdLocal(), xIdPeriodo, xIdPeriodoLista);
	        System.out.println("HistoricoConsumo CREADO");
	        
	        //Actualizamos el historico de consumo
	        tblTercerosRepo.actualizaHistoricoConsumo(usuario.getIdLocal(), xIdPeriodo);
	        System.out.println("HistoricoConsumo ACTUALIZADO");
	        
	        //INGRESAR NUEVO PERIODO
	        tblDctosPeriodoService.ingresarDctoPeriodo(usuario.getIdLocal(), xIdPeriodo, nombre, FechaInicioConsumo, fechaFinConsumo, fechaSinRecargo, fechaConrecargo);
	        
	        // Ejecutamos el JAR charConsumo
	        charConsumoTask.ejecutarJar(usuario.getIdLocal(), xIdPeriodo);
	        
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");


		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	@PostMapping("/TraerPeriodo-Post")
	public ModelAndView TraerRutaPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    System.out.println("Entró a /TraerPeriodo-Post");

	    // Obtenemos los datos del JSON recibido
	    String idPeriodo = (String) requestBody.get("idPeriodo");




	    // Redirige a la vista y le pasamos el parametro de idTercero
	    ModelAndView modelAndView = new ModelAndView("redirect:/TraerPeriodo?idPeriodo=" + idPeriodo);
	    return modelAndView;
	}
	
	
	@GetMapping("/TraerPeriodo")
	public String TraerRuta(@RequestParam(name = "idPeriodo", required = false) String idPeriodo, HttpServletRequest request, Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		System.out.println("Entró a /TraerReferencia con idPlu: " + idPeriodo);
		
		
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

		    
		    Integer idPeriodoInt = Integer.parseInt(idPeriodo);

		    
		    List <TblDctosPeriodo> Periodo = tblDctosPeriodoService.ObtenerPeriodo(usuario.getIdLocal(), idPeriodoInt);
		    
		    for(TblDctosPeriodo P : Periodo) {

		    	
		    	model.addAttribute("xIdPeriodo", P.getIdPeriodo());
		    	model.addAttribute("xNombre", P.getNombrePeriodo());
		    	model.addAttribute("xFechaInicial", P.getFechaInicial());
		    	model.addAttribute("xFechaFinal", P.getFechaFinal());
		    	model.addAttribute("xFechaSinRecargo", P.getFechaSinRecargo());
		    	model.addAttribute("xFechaConRecargo", P.getFechaConRecargo());
		    	model.addAttribute("xEstadoEmail", P.getEstadoEmail());
		    	model.addAttribute("xEstadoLecturaApp", P.getEstadoLecturaApp());
		    	model.addAttribute("xTextoPerdiodo", P.getTextoPeriodo());
		   

		    }
		    
		    ArrayList<TblTipoCausaNota> EstadoEmail = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(6);
		    ArrayList<TblTipoCausaNota> EstadoLecturasApp = tblTipoCausaNotaService.ObtenerTblTipoCausaNota(7);
		    
		    model.addAttribute("EstadoEmail", EstadoEmail);
	    	model.addAttribute("EstadoLecturasApp", EstadoLecturasApp);
	    	
		    //---  xIdLocalUsuario
	        int xIdNivelAdministrador = 5;
	        int xEstadoActivo = 1;
	        int xIdLocalUsuarioSU = 102;
	        
	        //Obtenemos la Clave de la DB y la enviamos como atributo para ser validada si se oprime el botón "RECUPERAR"
	        String xClave = ctrlusuariosService.listaAutorizador(xIdLocalUsuarioSU, xIdNivelAdministrador, xEstadoActivo);
	        model.addAttribute("xClave", xClave);


			
			return "Periodo/ActualizarActivarPeriodo";


	}
	
	
	
	
	@PostMapping("/ActivarPeriodo-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActivarPeriodoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
		
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    //System.out.println("SI ENTRÓ A  /ActualizarRuta-Post");

	        // Obtenemos los datos del JSON recibido
	    String idPeriodo = (String) requestBody.get("idPeriodo");
	    Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	    
	    //Actualizamos TODOS los idPeriodo en estado = 2
	    tblDctosPeriodoRepo.desactivaAll(usuario.getIdLocal());
	    System.out.println("IDPERIODOS DESACTIVADOS");
	    
	    //Actualizamos el IdPeriodo a Estado = 1
	    tblDctosPeriodoRepo.activaUn(usuario.getIdLocal(), idPeriodoInt);
	    System.out.println("IDPERIODO ACTIVADO");
        	
		    
	        System.out.println("IDPERIODO ACTIVADO CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("idPeriodo", idPeriodo);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	@PostMapping("/ActualizarPeriodo-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarPeriodoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();


	    //System.out.println("SI ENTRÓ A  /ActualizarRuta-Post");

	        // Obtenemos los datos del JSON recibido
	    String idPeriodo = (String) requestBody.get("idPeriodo");
	    Integer idPeriodoInt = Integer.parseInt(idPeriodo);
	    String nombre = (String) requestBody.get("nombre");
	    String TextoPeriodo = (String) requestBody.get("TextoPeriodo");
	    
	    
	    String FechaInicioConsumoStr = (String) requestBody.get("fechaInicio");
	    String fechaFinConsumoStr = (String) requestBody.get("fechaFin");
	    String fechaSinRecargoStr = (String) requestBody.get("fechaSinCargo");
	    String fechaConrecargoStr = (String) requestBody.get("fechaConCargo");
	    
	    Timestamp FechaInicioConsumo = null;
        Timestamp fechaFinConsumo = null;
        Timestamp fechaSinRecargo = null;
        Timestamp fechaConrecargo = null;
        
        
        // Formato de la fecha
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {

            // Parsear la primera fecha
            Date parsedDate1 = dateFormat.parse(FechaInicioConsumoStr + " 00:00:00.000");
            FechaInicioConsumo = new Timestamp(parsedDate1.getTime());
            System.out.println("Fecha con recargo 1: " + FechaInicioConsumoStr + ", Timestamp: " + FechaInicioConsumo);
            
            Date parsedDate2 = dateFormat.parse(fechaFinConsumoStr);
            fechaFinConsumo = new Timestamp(parsedDate2.getTime());

            Date parsedDate3 = dateFormat.parse(fechaSinRecargoStr);
            fechaSinRecargo = new Timestamp(parsedDate3.getTime());

            Date parsedDate4 = dateFormat.parse(fechaConrecargoStr);
            fechaConrecargo = new Timestamp(parsedDate4.getTime());
            
            
        } catch (ParseException e) {
            
            e.printStackTrace();
        }
	    
	    
	    String appLecturas = (String) requestBody.get("appLecturas");
	    Integer appLecturasInt = Integer.parseInt(appLecturas);
	    String envioMails = (String) requestBody.get("envioMails");
	    Integer envioMailsInt = Integer.parseInt(envioMails);
	    
	    
        //Actualizamos el Periodo
	    tblDctosPeriodoRepo.actualizarPeriodo(nombre, FechaInicioConsumo, fechaFinConsumo, fechaSinRecargo, fechaConrecargo,
	    											envioMailsInt, appLecturasInt, TextoPeriodo, usuario.getIdLocal(), idPeriodoInt);
		    
	        System.out.println("IDPERIODO ACTUALIDADO CORRECTAMENTE");
		    Map<String, Object> response = new HashMap<>();
		    response.put("message", "LOGGGGGGGGG");
		    response.put("idPeriodo", idPeriodo);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	@PostMapping("/RecuperarPeriodo-Post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> RecuperarPeriodoPost(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
		
		
		Class tipoObjeto = this.getClass();					
        String nombreClase = tipoObjeto.getName();		
        System.out.println("CONTROLLER " + nombreClase);
		
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	    Integer IdUsuario = usuario.getIdUsuario();

	    Map<String, Object> response = new HashMap<>();
	    System.out.println("SI ENTRÓ A  /RecuperarPeriodo-Post");

	        // Obtenemos los datos del JSON recibido
	    String idPeriodo = (String) requestBody.get("idPeriodo");
	    Integer xIdPeriodo = Integer.parseInt(idPeriodo);
	    
	    
	    
        
        // ------------------- 1
	    List <TblDctosPeriodo> Periodo = tblDctosPeriodoService.ObtenerPeriodo(usuario.getIdLocal(), xIdPeriodo);
	    Integer xEstadoFacturadoSI = 1;
	    Integer EstadoFacturado = 0;
	    
	    for(TblDctosPeriodo p : Periodo) {
	    	
	    	EstadoFacturado = p.getEstadoFacturado();
	    }
	    
	    
	    // diferente a facturado : Periodo no facturado
        if (!EstadoFacturado.equals(xEstadoFacturadoSI)) {

            String mensaje = ("Periodo activo no facturado");

            // Mostramos el mensaje de error 
            response.put("mensaje", mensaje);
            return ResponseEntity.ok(response);
        }
        
        
        //Valida si ya se enviaron a la DIAN las facturas
        
        List<TblDctosDTO> listaEnvio = tblDctosService.listaPeriodoEnvioDIAN(usuario.getIdLocal(), xIdPeriodo);
        System.out.println("listaEnvio es " + listaEnvio);
        
        if(listaEnvio != null && !listaEnvio.isEmpty()) {
        	
        	String mensaje = ("Periodo con facturas ya enviadas a la DIAN");

            // Mostramos el mensaje de error 
            response.put("mensaje", mensaje);
            return ResponseEntity.ok(response);
        	
        }
        
        
        
	    
	    // --------- 3 actualizaRecuperaEstadoCorte
	    tblTercerosRepo.actualizaRecuperaEstadoCorte(usuario.getIdLocal(), xIdPeriodo);
	    System.out.println("SI actualizaRecuperaEstadoCorte");
	    
	    // ------------------- 4 retiraOrdenesDetalle
	    tblDctosOrdenesDetalleRepo.retiraOrdenesDetalle(usuario.getIdLocal(), xIdPeriodo);
	    System.out.println("SI retiraOrdenesDetalle");
        
	    // ------------------- 5  recuperaOrdenesFinaciacion
	    tblDctosOrdenesDetalleRepo.recuperaOrdenesFinaciacion(usuario.getIdLocal(), xIdPeriodo);
	    System.out.println("SI recuperaOrdenesFinaciacion");
	    
	    // ------------------- 6  retiraOrdenes
	    tblDctosOrdenesRepo.retiraOrdenes(usuario.getIdLocal(), xIdPeriodo);
	    System.out.println("SI retiraOrdenes");
	    
	    // ------------------- 7  retiraDctos
	    tblDctosRepo.retiraDctos(usuario.getIdLocal(), xIdPeriodo);
	    System.out.println("SI retiraDctos");
	    
	    // ------------------- 8 retiraMedio
	    tblPagosMediosRepo.retiraMedio(usuario.getIdLocal(), xIdPeriodo);
	    System.out.println("SI retiraMedio");
	     
	    // ------------------- 9  retiraPago
	    tblPagosRepo.retiraPago(usuario.getIdLocal(), xIdPeriodo);
	    System.out.println("SI retiraPago");
	     
	    // ------------------- 10 ingresaOrdenes
	    tblDctosOrdenesRepo.ingresaOrdenes(usuario.getIdLocal(), xIdPeriodo);
	    System.out.println("SI ingresaOrdenes");
	    
	    tblDctosOrdenesDetalleRepo.ingresaOrdenesDetalle(usuario.getIdLocal(), xIdPeriodo);
	    System.out.println("SI ingresaOrdenesDetalle");
	    
	    // ------------------- 11  retiraOrdenesDetalle
	    bak_DctosOrdenesRepo.retiraOrdenesDetalle(usuario.getIdLocal());
	    System.out.println("SI retiraOrdenesDetalle");
	    
	    // ------------------- 12  retiraOrdenes
	    bak_DctosOrdenesRepo.retiraOrdenes(usuario.getIdLocal());
	    System.out.println("SI retiraOrdenes");
	    
	    // ------------------- 13 
	    Integer xEstadoFacturadoNO = 2;
	    tblDctosPeriodoRepo.modificaEstadoFacturado(usuario.getIdLocal(), xIdPeriodo, xEstadoFacturadoNO);
	    System.out.println("SI modificaEstadoFacturado");
	    
		    
	        System.out.println("IDPERIODO RECUPERADO CORRECTAMENTE");
		    
		    response.put("message", "LOGGGGGGGGG");
		    response.put("idPeriodo", idPeriodo);
		    
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	

	
	
	
	
	
}
