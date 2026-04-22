package com.marketing.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosViewDTO;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblPlusInventarioRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblCategoriasService;
import com.marketing.Service.dbaquamovil.TblCiudadesService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblPlusInventarioService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.Service.dbaquamovil.TblTipoOrdenSubcuentaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaPluInventario;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.ProcesoGuardaPorcentaje;
import com.marketing.Utilidades.ProcesoIngresoComprobante;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ImagenesLecturaController {
	
	@Autowired
	 TblCategoriasService tblCategoriasService;
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblDctosService tblDctosService;
	
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
	TblCiudadesService  tblCiudadesService;
	
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
	TblTipoOrdenSubcuentaService tblTipoOrdenSubcuentaService;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	ProcesoGuardaPorcentaje procesoGuardaPorcentaje;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@Autowired
	ProcesoIngresoComprobante procesoIngresoComprobante;
	
	
	@Autowired
	ProcesoGuardaPluInventario procesoGuardaPluInventario;
	
	@Autowired
	TblPlusInventarioService tblPlusInventarioService;
	
	@Autowired
	TblPlusInventarioRepo tblPlusInventarioRepo;
	

	
	
	
	
	@GetMapping("/ImagenesLectura")
	public String imagenesLectura(HttpServletRequest request, Model model) {

	    System.out.println("CONTROLLER ImagenesLectura");

	    HttpSession session = request.getSession();

	    Ctrlusuarios usuario = (Ctrlusuarios) session.getAttribute("usuarioAuth");
	    String sistema = (String) session.getAttribute("sistema");

	    int idLocal = usuario.getIdLocal();
	    Integer IdUsuario = usuario.getIdUsuario();

	    // ================= VALIDACIÓN INACTIVIDAD =================
	    @SuppressWarnings("unchecked")
	    List<TblAgendaLogVisitas> UsuarioLogueado =
	            (List<TblAgendaLogVisitas>) session.getAttribute("UsuarioLogueado");

	    Integer estadoUsuario = 0;

	    for (TblAgendaLogVisitas usuarioLog : UsuarioLogueado) {
	        estadoUsuario = controlDeInactividad.ingresa(
	                usuarioLog.getIdLocal(),
	                usuarioLog.getIDLOG(),
	                usuarioLog.getSessionId()
	        );
	    }

	    if (estadoUsuario.equals(2)) {
	        System.out.println("USUARIO INACTIVO");
	        return "redirect:/";
	    }

	    // ================= VALIDACIÓN CLIENTE =================
	    int estadoActivo = 9;

	    LocalDate fechaActual = LocalDate.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	    String strFechaVisita = fechaActual.format(formatter);

	    String idCliente = tblAgendaLogVisitasService
	            .seleccionaVisitaEstadoFecha(estadoActivo, strFechaVisita, IdUsuario);

	    if (idCliente == null) {
	        session.setAttribute("pantalla", "ImagenesLectura");
	        return "Cliente/Selecciona";
	    }

	    // ================= INFO CLIENTE =================
	    List<TblTerceros> listaTercero =
	            tblTercerosService.listaUnTerceroFCH(idLocal, idCliente, 1);

	    for (TblTerceros L : listaTercero) {
	        model.addAttribute("xEstado", L.getEstado());
	        model.addAttribute("xNuid", L.getIdCliente());
	        model.addAttribute("xNombreTercero", L.getNombreTercero());
	        model.addAttribute("xRuta", L.getIdRuta());
	    }

	    // ================= PATH BASE =================
	    String xPathFileGralDB = "";

	    List<TblLocales> Local = tblLocalesService.ObtenerLocal(idLocal);
	    for (TblLocales L : Local) {
	        xPathFileGralDB = L.getPathFileGral();
	    }

	    String xCharSeparator = File.separator;

	    // ================= PERIODO ACTIVO =================
	    List<TblDctosPeriodo> PeriodoActivo =
	            tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);

	    Integer idPeriodoActual = 0;
	    for (TblDctosPeriodo P : PeriodoActivo) {
	        idPeriodoActual = P.getIdPeriodo();
	    }

	    System.out.println("idPeriodoActual: " + idPeriodoActual);

	    // ================= COORDENADAS =================
	    List<TblDctosDTO> coordenadasBase =
	            tblDctosService.ObtenerCordenadasPorPeriodo(idLocal, idPeriodoActual, idCliente);

	    List<TblDctosViewDTO> coordenadas = new ArrayList<>();

	    for (TblDctosDTO coord : coordenadasBase) {

	        // ===== Ruta física =====
	        String xRrutaImagen = xPathFileGralDB
	                + "aquamovil" + xCharSeparator
	                + "imgmedidor" + xCharSeparator
	                + idLocal + xCharSeparator
	                + coord.getIdPeriodo() + xCharSeparator
	                + idCliente + ".png";

	        System.out.println("Ruta física: " + xRrutaImagen);

	        File file = new File(xRrutaImagen);

	        // ===== Ruta web =====
	        String rutaWeb;

	        if (file.exists()) {
	            rutaWeb = "/FileGral/aquamovil/imgmedidor/"
	                    + idLocal + "/"
	                    + coord.getIdPeriodo() + "/"
	                    + idCliente + ".png";
	        } else {
	            rutaWeb = "/img/no-image.png";
	        }

	        // ===== DTO para la vista =====
	        TblDctosViewDTO dto = new TblDctosViewDTO(coord);
	        dto.setRutaImagen(rutaWeb); 
	        coordenadas.add(dto);
	    }

	    // ================= MODEL =================
	    model.addAttribute("coordenadas", coordenadas);
	    model.addAttribute("xFechaActual", strFechaVisita);

	    return "Administrador/ImagenesLectura";
	}
	

}