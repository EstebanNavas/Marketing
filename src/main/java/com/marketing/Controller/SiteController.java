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
import org.springframework.web.bind.annotation.ResponseBody;

import com.marketing.ActivaServicioTask;
import com.marketing.MailjetTask;
import com.marketing.Model.DBMailMarketing.TblEstilosSite;
import com.marketing.Model.DBMailMarketing.TblNoticiasSite;
import com.marketing.Model.DBMailMarketing.TblSiteNoticias;
import com.marketing.Model.Reportes.ReportesDTO;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Projection.TercerosDTO2;
import com.marketing.Service.DBMailMarketing.TblEstilosSiteService;
import com.marketing.Service.DBMailMarketing.TblNoticiasSiteService;
import com.marketing.Service.DBMailMarketing.TblSiteNoticiasService;
import com.marketing.Service.DBMailMarketing.TblSiteStyleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.enums.TipoReporteEnum;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class SiteController {
	
	@Autowired
	TblSiteStyleService tblSiteStyleService;
	
	@Autowired
	TblNoticiasSiteService tblNoticiasSiteService;
	
	@Autowired
	TblEstilosSiteService tblEstilosSiteService;
	
	@Autowired
	TblSiteNoticiasService tblSiteNoticiasService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	MailjetTask mailjetTask;
	
	@Autowired
	ActivaServicioTask activaServicioTask;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblDctosService tblDctosService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	int idLocal = 0;
	
	
	String URLBaseWindows = "/images/";
	String URLBaseLinux = "/marketing/images/";
	
	
	
	@GetMapping("/Nosotros")
	public String Nosotros(HttpServletRequest request,Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
         idLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("EL xIdLocal DESDE LA CLASE SiteController Y EL METODO  Nosotros ES :  " + idLocal);
		
		
		//NAVBAR
		String Navbar_color = tblEstilosSiteService.Navbar_color(idLocal);
		System.out.println("Navbar_color " + Navbar_color);
		model.addAttribute("Navbar_color", Navbar_color);

		String LetraNavbar_color = tblEstilosSiteService.LetraNavbar_color(idLocal);
		System.out.println("LetraNavbar_color " + LetraNavbar_color);
		model.addAttribute("LetraNavbar_color", LetraNavbar_color);

		String LetraNavbar_fuente = tblEstilosSiteService.LetraNavbar_fuente(idLocal);
		System.out.println("LetraNavbar_fuente " + LetraNavbar_fuente);
		model.addAttribute("LetraNavbar_fuente", LetraNavbar_fuente);
		
		String titulo_color = tblEstilosSiteService.titulo_color(idLocal);
		System.out.println("titulo_color " + titulo_color);
		model.addAttribute("titulo_color", titulo_color);
		
		String titulo_fuente = tblEstilosSiteService.titulo_fuente(idLocal);
		System.out.println("titulo_fuente " + titulo_fuente);
		model.addAttribute("titulo_fuente", titulo_fuente);
		
		String nosotrosResumen_color = tblEstilosSiteService.nosotrosResumen_color(idLocal);
		System.out.println("nosotrosResumen_color " + nosotrosResumen_color);
		model.addAttribute("nosotrosResumen_color", nosotrosResumen_color);
		
		String nosotrosResumen_fuente = tblEstilosSiteService.nosotrosResumen_fuente(idLocal);
		System.out.println("nosotrosResumen_fuente " + nosotrosResumen_fuente);
		model.addAttribute("nosotrosResumen_fuente", nosotrosResumen_fuente);
		
		String nosotrosResumen_contenido = tblEstilosSiteService.nosotrosResumen_contenido(idLocal);
		System.out.println("nosotrosResumen_contenido " + nosotrosResumen_contenido);
		model.addAttribute("nosotrosResumen_contenido", nosotrosResumen_contenido);
		
		String nosotrosTexto_color = tblEstilosSiteService.nosotrosTexto_color(idLocal);
		System.out.println("nosotrosTexto_color " + nosotrosTexto_color);
		model.addAttribute("nosotrosTexto_color", nosotrosTexto_color);
		
		String nosotrosTexto_fuente = tblEstilosSiteService.nosotrosTexto_fuente(idLocal);
		System.out.println("nosotrosTexto_fuente " + nosotrosTexto_fuente);
		model.addAttribute("nosotrosTexto_fuente", nosotrosTexto_fuente);
		
		String nosotrosTexto_contenido = tblEstilosSiteService.nosotrosTexto_contenido(idLocal);
		System.out.println("nosotrosTexto_contenido " + nosotrosTexto_contenido);
		model.addAttribute("nosotrosTexto_contenido", nosotrosTexto_contenido);
		
		String tituloNombreEmpresa_color = tblEstilosSiteService.tituloNombreEmpresa_color(idLocal);
		System.out.println("tituloNombreEmpresa_color " + tituloNombreEmpresa_color);
		model.addAttribute("tituloNombreEmpresa_color", tituloNombreEmpresa_color);
		
		String tituloNombreEmpresa_fuente = tblEstilosSiteService.tituloNombreEmpresa_fuente(idLocal);
		System.out.println("tituloNombreEmpresa_fuente " + tituloNombreEmpresa_fuente);
		model.addAttribute("tituloNombreEmpresa_fuente", tituloNombreEmpresa_fuente);
		
		String tituloNombreEmpresa_contenido = tblEstilosSiteService.tituloNombreEmpresa_contenido(idLocal);
		System.out.println("tituloNombreEmpresa_contenido " + tituloNombreEmpresa_contenido);
		model.addAttribute("tituloNombreEmpresa_contenido", tituloNombreEmpresa_contenido);
		
		
		String checkList_color = tblEstilosSiteService.checkList_color(idLocal);
		System.out.println("checkList_color " + checkList_color);
		model.addAttribute("checkList_color", checkList_color);
		
		String checkList_texto_color = tblEstilosSiteService.checkList_texto_color(idLocal);
		System.out.println("checkList_texto_color " + checkList_texto_color);
		model.addAttribute("checkList_texto_color", checkList_texto_color);
		
		String checkList_texto_fuente = tblEstilosSiteService.checkList_texto_fuente(idLocal);
		System.out.println("checkList_texto_fuente " + checkList_texto_fuente);
		model.addAttribute("checkList_texto_fuente", checkList_texto_fuente);
		
		String checkList_texto_1_contenido = tblEstilosSiteService.checkList_texto_1_contenido(idLocal);
		System.out.println("checkList_texto_1_contenido " + checkList_texto_1_contenido);
		model.addAttribute("checkList_texto_1_contenido", checkList_texto_1_contenido);
		
		String checkList_texto_2_contenido = tblEstilosSiteService.checkList_texto_2_contenido(idLocal);
		System.out.println("checkList_texto_2_contenido " + checkList_texto_2_contenido);
		model.addAttribute("checkList_texto_2_contenido", checkList_texto_2_contenido);
		
		String checkList_texto_3_contenido = tblEstilosSiteService.checkList_texto_3_contenido(idLocal);
		System.out.println("checkList_texto_3_contenido " + checkList_texto_3_contenido);
		model.addAttribute("checkList_texto_3_contenido", checkList_texto_3_contenido);
		
		String subtitulo_color = tblEstilosSiteService.subtitulo_color(idLocal);
		System.out.println("subtitulo_color " + subtitulo_color);
		model.addAttribute("subtitulo_color", subtitulo_color);
		
		String subtitulo_fuente = tblEstilosSiteService.subtitulo_fuente(idLocal);
		System.out.println("subtitulo_fuente " + subtitulo_fuente);
		model.addAttribute("subtitulo_fuente", subtitulo_fuente);
		
		String linkTexto_color = tblEstilosSiteService.linkTexto_color(idLocal);
		System.out.println("linkTexto_color " + linkTexto_color);
		model.addAttribute("linkTexto_color", linkTexto_color);
		
		String linkTexto_fuente = tblEstilosSiteService.linkTexto_fuente(idLocal);
		System.out.println("linkTexto_fuente " + linkTexto_fuente);
		model.addAttribute("linkTexto_fuente", linkTexto_fuente);
		
		String boton_color = tblEstilosSiteService.boton_color(idLocal);
		System.out.println("boton_color " + boton_color);
		model.addAttribute("boton_color", boton_color);
		
		String footer_color = tblEstilosSiteService.footer_color(idLocal);
		System.out.println("footer_color " + footer_color);
		model.addAttribute("footer_color", footer_color);
		
		String LetraFooter_color = tblEstilosSiteService.LetraFooter_color(idLocal);
		System.out.println("LetraFooter_color " + LetraFooter_color);
		model.addAttribute("LetraFooter_color", LetraFooter_color);
		
		String LetraFooter_fuente = tblEstilosSiteService.LetraFooter_fuente(idLocal);
		System.out.println("LetraFooter_fuente " + LetraFooter_fuente);
		model.addAttribute("LetraFooter_fuente", LetraFooter_fuente);
		
		String subtituloFooter_fuente = tblEstilosSiteService.subtituloFooter_fuente(idLocal);
		System.out.println("subtituloFooter_fuente " + subtituloFooter_fuente);
		model.addAttribute("subtituloFooter_fuente", subtituloFooter_fuente);
		
		String subtituloFooter_color = tblEstilosSiteService.subtituloFooter_color(idLocal);
		System.out.println("subtituloFooter_color " + subtituloFooter_color);
		model.addAttribute("subtituloFooter_color", subtituloFooter_color);
		
		String imgNotrosResumen_1_imagen = tblEstilosSiteService.imgNotrosResumen_1_imagen(idLocal);
		System.out.println("imgNotrosResumen_1_imagen " + imgNotrosResumen_1_imagen);
		model.addAttribute("imgNotrosResumen_1_imagen",  URLBaseLinux + idLocal + "/"+ imgNotrosResumen_1_imagen);
		
		String imgNotrosResumen_2_imagen = tblEstilosSiteService.imgNotrosResumen_2_imagen(idLocal);
		System.out.println("imgNotrosResumen_2_imagen " + imgNotrosResumen_2_imagen);
		model.addAttribute("imgNotrosResumen_2_imagen",  URLBaseLinux + idLocal + "/"+ imgNotrosResumen_2_imagen);
		
		String imgNotrosResumen_3_imagen = tblEstilosSiteService.imgNotrosResumen_3_imagen(idLocal);
		System.out.println("imgNotrosResumen_3_imagen " + imgNotrosResumen_3_imagen);
		model.addAttribute("imgNotrosResumen_3_imagen",  URLBaseLinux + idLocal + "/"+ imgNotrosResumen_3_imagen);
		
		String imgNoticia_1_imagen = tblEstilosSiteService.imgNoticia_1_imagen(idLocal);
		System.out.println("imgNoticia_1_imagen " + imgNoticia_1_imagen);
		model.addAttribute("imgNoticia_1_imagen",  URLBaseLinux + idLocal + "/"+ imgNoticia_1_imagen);
		
		String imgNoticia_2_imagen = tblEstilosSiteService.imgNoticia_2_imagen(idLocal);
		System.out.println("imgNoticia_2_imagen " + imgNoticia_2_imagen);
		model.addAttribute("imgNoticia_2_imagen",  URLBaseLinux + idLocal + "/"+ imgNoticia_2_imagen);
		
		String imgNoticia_3_imagen = tblEstilosSiteService.imgNoticia_3_imagen(idLocal);
		System.out.println("imgNoticia_3_imagen " + imgNoticia_3_imagen);
		model.addAttribute("imgNoticia_3_imagen",  URLBaseLinux + idLocal + "/"+ imgNoticia_3_imagen);
		
		String quienesSomosTexto_color = tblEstilosSiteService.quienesSomosTexto_color(idLocal);
		System.out.println("quienesSomosTexto_color " + quienesSomosTexto_color);
		model.addAttribute("quienesSomosTexto_color", quienesSomosTexto_color);
		
		String quienesSomosTexto_fuente = tblEstilosSiteService.quienesSomosTexto_fuente(idLocal);
		System.out.println("quienesSomosTexto_fuente " + quienesSomosTexto_fuente);
		model.addAttribute("quienesSomosTexto_fuente", quienesSomosTexto_fuente);
		
		String quienesSomosTexto_contenido = tblEstilosSiteService.quienesSomosTexto_contenido(idLocal);
		System.out.println("quienesSomosTexto_contenido " + quienesSomosTexto_contenido);
		model.addAttribute("quienesSomosTexto_contenido", quienesSomosTexto_contenido);
		
		String misionTexto_color = tblEstilosSiteService.misionTexto_color(idLocal);
		System.out.println("misionTexto_color " + misionTexto_color);
		model.addAttribute("misionTexto_color", misionTexto_color);
		
		String misionTexto_fuente = tblEstilosSiteService.misionTexto_fuente(idLocal);
		System.out.println("misionTexto_fuente " + misionTexto_fuente);
		model.addAttribute("misionTexto_fuente", misionTexto_fuente);
		
		String misionTexto_contenido = tblEstilosSiteService.misionTexto_contenido(idLocal);
		System.out.println("misionTexto_contenido " + misionTexto_contenido);
		model.addAttribute("misionTexto_contenido", misionTexto_contenido);
		
		String visionTexto_color = tblEstilosSiteService.visionTexto_color(idLocal);
		System.out.println("visionTexto_color " + visionTexto_color);
		model.addAttribute("visionTexto_color", visionTexto_color);
		
		String visionTexto_fuente = tblEstilosSiteService.visionTexto_fuente(idLocal);
		System.out.println("visionTexto_fuente " + visionTexto_fuente);
		model.addAttribute("visionTexto_fuente", visionTexto_fuente);
		
		String visionTexto_contenido = tblEstilosSiteService.visionTexto_contenido(idLocal);
		System.out.println("visionTexto_contenido " + visionTexto_contenido);
		model.addAttribute("visionTexto_contenido", visionTexto_contenido);
		
		String historiaTexto_color = tblEstilosSiteService.historiaTexto_color(idLocal);
		System.out.println("historiaTexto_color " + historiaTexto_color);
		model.addAttribute("historiaTexto_color", historiaTexto_color);
		
		String historiaTexto_fuente = tblEstilosSiteService.historiaTexto_fuente(idLocal);
		System.out.println("historiaTexto_fuente " + historiaTexto_fuente);
		model.addAttribute("historiaTexto_fuente", historiaTexto_fuente);
		
		String historiaTexto_contenido = tblEstilosSiteService.historiaTexto_contenido(idLocal);
		System.out.println("historiaTexto_contenido " + historiaTexto_contenido);
		model.addAttribute("historiaTexto_contenido", historiaTexto_contenido);
		
		String objetivoTexto_color = tblEstilosSiteService.objetivoTexto_color(idLocal);
		System.out.println("objetivoTexto_color " + objetivoTexto_color);
		model.addAttribute("objetivoTexto_color", objetivoTexto_color);
		
		String objetivoTexto_fuente = tblEstilosSiteService.objetivoTexto_fuente(idLocal);
		System.out.println("objetivoTexto_fuente " + objetivoTexto_fuente);
		model.addAttribute("objetivoTexto_fuente", objetivoTexto_fuente);
		
		String objetivoTexto_contenido = tblEstilosSiteService.objetivoTexto_contenido(idLocal);
		System.out.println("objetivoTexto_contenido " + objetivoTexto_contenido);
		model.addAttribute("objetivoTexto_contenido", objetivoTexto_contenido);
		
		String imgQuienesSomos_1_imagen = tblEstilosSiteService.imgQuienesSomos_1_imagen(idLocal);
		System.out.println("imgQuienesSomos_1_imagen " + imgQuienesSomos_1_imagen);
		model.addAttribute("imgQuienesSomos_1_imagen",  URLBaseLinux+ idLocal + "/"+ imgQuienesSomos_1_imagen);
		
		String imgQuienesSomos_2_imagen = tblEstilosSiteService.imgQuienesSomos_2_imagen(idLocal);
		System.out.println("imgQuienesSomos_2_imagen " + imgQuienesSomos_2_imagen);
		model.addAttribute("imgQuienesSomos_2_imagen",  URLBaseLinux + idLocal + "/"+ imgQuienesSomos_2_imagen);
		
		String imgQuienesSomos_3_imagen = tblEstilosSiteService.imgQuienesSomos_3_imagen(idLocal);
		System.out.println("imgQuienesSomos_3_imagen " + imgQuienesSomos_3_imagen);
		model.addAttribute("imgQuienesSomos_3_imagen",  URLBaseLinux + idLocal + "/"+ imgQuienesSomos_3_imagen);
		
		String imgMision_1_imagen = tblEstilosSiteService.imgMision_1_imagen(idLocal);
		System.out.println("imgMision_1_imagen " + imgMision_1_imagen);
		model.addAttribute("imgMision_1_imagen",  URLBaseLinux + idLocal + "/"+ imgMision_1_imagen);
		
		String imgMision_2_imagen = tblEstilosSiteService.imgMision_2_imagen(idLocal);
		System.out.println("imgMision_2_imagen " + imgMision_2_imagen);
		model.addAttribute("imgMision_2_imagen",  URLBaseLinux + idLocal + "/"+ imgMision_2_imagen);
		
		String imgMision_3_imagen = tblEstilosSiteService.imgMision_3_imagen(idLocal);
		System.out.println("imgMision_3_imagen " + imgMision_3_imagen);
		model.addAttribute("imgMision_3_imagen",  URLBaseLinux + idLocal + "/"+ imgMision_3_imagen);
		
		String imgVision_1_imagen = tblEstilosSiteService.imgVision_1_imagen(idLocal);
		System.out.println("imgVision_1_imagen " + imgVision_1_imagen);
		model.addAttribute("imgVision_1_imagen",  URLBaseLinux + idLocal + "/"+ imgVision_1_imagen);
		
		String imgVision_2_imagen = tblEstilosSiteService.imgVision_2_imagen(idLocal);
		System.out.println("imgVision_2_imagen " + imgVision_2_imagen);
		model.addAttribute("imgVision_2_imagen",  URLBaseLinux + idLocal + "/"+ imgVision_2_imagen);
		
		
		String videoQuienesSomos_video = tblEstilosSiteService.videoQuienesSomos_video(idLocal);
		System.out.println("videoQuienesSomos_video " + videoQuienesSomos_video);
		model.addAttribute("videoQuienesSomos_video", videoQuienesSomos_video);
		
		String videoMision_video = tblEstilosSiteService.videoMision_video(idLocal);
		System.out.println("videoMision_video " + videoMision_video);
		model.addAttribute("videoMision_video", videoMision_video);
		
		String videoVision_video = tblEstilosSiteService.videoVision_video(idLocal);
		System.out.println("videoVision_video " + videoVision_video);
		model.addAttribute("videoVision_video", videoVision_video);
		
		String imgVision_3_imagen = tblEstilosSiteService.imgVision_3_imagen(idLocal);
		System.out.println("imgVision_3_imagen " + imgVision_3_imagen);
		model.addAttribute("imgVision_3_imagen", URLBaseLinux + idLocal + "/"+ imgVision_3_imagen);
		
		String direccion_contenido = tblEstilosSiteService.direccion_contenido(idLocal);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblEstilosSiteService.telefono_contenido(idLocal);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblEstilosSiteService.correo_contenido(idLocal);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
		String imgHistoria_1_imagen = tblEstilosSiteService.imgHistoria_1_imagen(idLocal);
		System.out.println("imgHistoria_1_imagen " + imgHistoria_1_imagen);
		model.addAttribute("imgHistoria_1_imagen",  URLBaseLinux + idLocal + "/"+ imgHistoria_1_imagen);
		
		String imgHistoria_2_imagen = tblEstilosSiteService.imgHistoria_2_imagen(idLocal);
		System.out.println("imgHistoria_2_imagen " + imgHistoria_2_imagen);
		model.addAttribute("imgHistoria_2_imagen",  URLBaseLinux + idLocal + "/"+ imgHistoria_2_imagen);
		
		String imgHistoria_3_imagen = tblEstilosSiteService.imgHistoria_3_imagen(idLocal);
		System.out.println("imgHistoria_3_imagen " + imgHistoria_3_imagen);
		model.addAttribute("imgHistoria_3_imagen",  URLBaseLinux + idLocal + "/"+ imgHistoria_3_imagen);
		
		String imgObjetivo_imagen = tblEstilosSiteService.imgObjetivo_imagen(idLocal);
		System.out.println("imgObjetivo_imagen " + imgObjetivo_imagen);
		model.addAttribute("imgObjetivo_imagen",  URLBaseLinux + idLocal + "/"+ imgObjetivo_imagen);
		
		String imgCarrusel_1_imagen = tblEstilosSiteService.imgCarrusel_1_imagen(idLocal);
		System.out.println("imgCarrusel_1_imagen " + imgCarrusel_1_imagen);
		model.addAttribute("imgCarrusel_1_imagen",  URLBaseLinux + idLocal + "/"+ imgCarrusel_1_imagen);
		
		return "nosotros";
		
	}
	
	
	@GetMapping("/Noticias")
	public String Noticias(HttpServletRequest request,Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
         idLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("EL xIdLocal DESDE LA CLASE SiteController Y EL METODO  Noticias ES :  " + idLocal);
		
		
		//NAVBAR
		String Navbar_color = tblEstilosSiteService.Navbar_color(idLocal);
		System.out.println("Navbar_color " + Navbar_color);
		model.addAttribute("Navbar_color", Navbar_color);

		String LetraNavbar_color = tblEstilosSiteService.LetraNavbar_color(idLocal);
		System.out.println("LetraNavbar_color " + LetraNavbar_color);
		model.addAttribute("LetraNavbar_color", LetraNavbar_color);

		String LetraNavbar_fuente = tblEstilosSiteService.LetraNavbar_fuente(idLocal);
		System.out.println("LetraNavbar_fuente " + LetraNavbar_fuente);
		model.addAttribute("LetraNavbar_fuente", LetraNavbar_fuente);
		
		String titulo_color = tblEstilosSiteService.titulo_color(idLocal);
		System.out.println("titulo_color " + titulo_color);
		model.addAttribute("titulo_color", titulo_color);
		
		String titulo_fuente = tblEstilosSiteService.titulo_fuente(idLocal);
		System.out.println("titulo_fuente " + titulo_fuente);
		model.addAttribute("titulo_fuente", titulo_fuente);
		
		String tituloNombreEmpresa_color = tblEstilosSiteService.tituloNombreEmpresa_color(idLocal);
		System.out.println("tituloNombreEmpresa_color " + tituloNombreEmpresa_color);
		model.addAttribute("tituloNombreEmpresa_color", tituloNombreEmpresa_color);
		
		String tituloNombreEmpresa_fuente = tblEstilosSiteService.tituloNombreEmpresa_fuente(idLocal);
		System.out.println("tituloNombreEmpresa_fuente " + tituloNombreEmpresa_fuente);
		model.addAttribute("tituloNombreEmpresa_fuente", tituloNombreEmpresa_fuente);
		
		String tituloNombreEmpresa_contenido = tblEstilosSiteService.tituloNombreEmpresa_contenido(idLocal);
		System.out.println("tituloNombreEmpresa_contenido " + tituloNombreEmpresa_contenido);
		model.addAttribute("tituloNombreEmpresa_contenido", tituloNombreEmpresa_contenido);
		
		
		String subtitulo_color = tblEstilosSiteService.subtitulo_color(idLocal);
		System.out.println("subtitulo_color " + subtitulo_color);
		model.addAttribute("subtitulo_color", subtitulo_color);
		
		String subtitulo_fuente = tblEstilosSiteService.subtitulo_fuente(idLocal);
		System.out.println("subtitulo_fuente " + subtitulo_fuente);
		model.addAttribute("subtitulo_fuente", subtitulo_fuente);
		
		String linkTexto_color = tblEstilosSiteService.linkTexto_color(idLocal);
		System.out.println("linkTexto_color " + linkTexto_color);
		model.addAttribute("linkTexto_color", linkTexto_color);
		
		String linkTexto_fuente = tblEstilosSiteService.linkTexto_fuente(idLocal);
		System.out.println("linkTexto_fuente " + linkTexto_fuente);
		model.addAttribute("linkTexto_fuente", linkTexto_fuente);
		
		String boton_color = tblEstilosSiteService.boton_color(idLocal);
		System.out.println("boton_color " + boton_color);
		model.addAttribute("boton_color", boton_color);
		
		String footer_color = tblEstilosSiteService.footer_color(idLocal);
		System.out.println("footer_color " + footer_color);
		model.addAttribute("footer_color", footer_color);
		
		String LetraFooter_color = tblEstilosSiteService.LetraFooter_color(idLocal);
		System.out.println("LetraFooter_color " + LetraFooter_color);
		model.addAttribute("LetraFooter_color", LetraFooter_color);
		
		String LetraFooter_fuente = tblEstilosSiteService.LetraFooter_fuente(idLocal);
		System.out.println("LetraFooter_fuente " + LetraFooter_fuente);
		model.addAttribute("LetraFooter_fuente", LetraFooter_fuente);
		
		String subtituloFooter_fuente = tblEstilosSiteService.subtituloFooter_fuente(idLocal);
		System.out.println("subtituloFooter_fuente " + subtituloFooter_fuente);
		model.addAttribute("subtituloFooter_fuente", subtituloFooter_fuente);
		
		String subtituloFooter_color = tblEstilosSiteService.subtituloFooter_color(idLocal);
		System.out.println("subtituloFooter_color " + subtituloFooter_color);
		model.addAttribute("subtituloFooter_color", subtituloFooter_color);
		
		String imgNotrosResumen_1_imagen = tblEstilosSiteService.imgNotrosResumen_1_imagen(idLocal);
		System.out.println("imgNotrosResumen_1_imagen " + imgNotrosResumen_1_imagen);
		model.addAttribute("imgNotrosResumen_1_imagen",  URLBaseLinux + idLocal + "/"+ imgNotrosResumen_1_imagen);
		
		String imgNotrosResumen_2_imagen = tblEstilosSiteService.imgNotrosResumen_2_imagen(idLocal);
		System.out.println("imgNotrosResumen_2_imagen " + imgNotrosResumen_2_imagen);
		model.addAttribute("imgNotrosResumen_2_imagen",  URLBaseLinux + idLocal + "/"+ imgNotrosResumen_2_imagen);
		
		String imgNotrosResumen_3_imagen = tblEstilosSiteService.imgNotrosResumen_3_imagen(idLocal);
		System.out.println("imgNotrosResumen_3_imagen " + imgNotrosResumen_3_imagen);
		model.addAttribute("imgNotrosResumen_3_imagen",  URLBaseLinux + idLocal + "/"+ imgNotrosResumen_3_imagen);
		
		String imgNoticia_1_imagen = tblEstilosSiteService.imgNoticia_1_imagen(idLocal);
		System.out.println("imgNoticia_1_imagen " + imgNoticia_1_imagen);
		model.addAttribute("imgNoticia_1_imagen",  URLBaseLinux + idLocal + "/"+ imgNoticia_1_imagen);
		
		String imgNoticia_2_imagen = tblEstilosSiteService.imgNoticia_2_imagen(idLocal);
		System.out.println("imgNoticia_2_imagen " + imgNoticia_2_imagen);
		model.addAttribute("imgNoticia_2_imagen", URLBaseLinux + idLocal + "/"+ imgNoticia_2_imagen);
		
		String imgNoticia_3_imagen = tblEstilosSiteService.imgNoticia_3_imagen(idLocal);
		System.out.println("imgNoticia_3_imagen " + imgNoticia_3_imagen);
		model.addAttribute("imgNoticia_3_imagen",  URLBaseLinux + idLocal + "/"+ imgNoticia_3_imagen);
		
		
		String direccion_contenido = tblEstilosSiteService.direccion_contenido(idLocal);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblEstilosSiteService.telefono_contenido(idLocal);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblEstilosSiteService.correo_contenido(idLocal);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
		List<TblSiteNoticias> Noticias = tblSiteNoticiasService.Noticias(idLocal);
		System.out.println("Noticias " + Noticias);
		model.addAttribute("Noticias", Noticias);
		
		return "noticias";
		
	}
	
	@GetMapping("/Contacto")
	public String Contacto(HttpServletRequest request,Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
         idLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("EL xIdLocal DESDE LA CLASE SiteController Y EL METODO  Contacto ES :  " + idLocal);
		
		//NAVBAR
		String Navbar_color = tblEstilosSiteService.Navbar_color(idLocal);
		System.out.println("Navbar_color " + Navbar_color);
		model.addAttribute("Navbar_color", Navbar_color);

		String LetraNavbar_color = tblEstilosSiteService.LetraNavbar_color(idLocal);
		System.out.println("LetraNavbar_color " + LetraNavbar_color);
		model.addAttribute("LetraNavbar_color", LetraNavbar_color);

		String LetraNavbar_fuente = tblEstilosSiteService.LetraNavbar_fuente(idLocal);
		System.out.println("LetraNavbar_fuente " + LetraNavbar_fuente);
		model.addAttribute("LetraNavbar_fuente", LetraNavbar_fuente);
		
		String titulo_color = tblEstilosSiteService.titulo_color(idLocal);
		System.out.println("titulo_color " + titulo_color);
		model.addAttribute("titulo_color", titulo_color);
		
		String titulo_fuente = tblEstilosSiteService.titulo_fuente(idLocal);
		System.out.println("titulo_fuente " + titulo_fuente);
		model.addAttribute("titulo_fuente", titulo_fuente);
		
		
		String tituloNombreEmpresa_color = tblEstilosSiteService.tituloNombreEmpresa_color(idLocal);
		System.out.println("tituloNombreEmpresa_color " + tituloNombreEmpresa_color);
		model.addAttribute("tituloNombreEmpresa_color", tituloNombreEmpresa_color);
		
		String tituloNombreEmpresa_fuente = tblEstilosSiteService.tituloNombreEmpresa_fuente(idLocal);
		System.out.println("tituloNombreEmpresa_fuente " + tituloNombreEmpresa_fuente);
		model.addAttribute("tituloNombreEmpresa_fuente", tituloNombreEmpresa_fuente);
		
		String tituloNombreEmpresa_contenido = tblEstilosSiteService.tituloNombreEmpresa_contenido(idLocal);
		System.out.println("tituloNombreEmpresa_contenido " + tituloNombreEmpresa_contenido);
		model.addAttribute("tituloNombreEmpresa_contenido", tituloNombreEmpresa_contenido);
		
		
		String subtitulo_color = tblEstilosSiteService.subtitulo_color(idLocal);
		System.out.println("subtitulo_color " + subtitulo_color);
		model.addAttribute("subtitulo_color", subtitulo_color);
		
		String subtitulo_fuente = tblEstilosSiteService.subtitulo_fuente(idLocal);
		System.out.println("subtitulo_fuente " + subtitulo_fuente);
		model.addAttribute("subtitulo_fuente", subtitulo_fuente);
		
		String linkTexto_color = tblEstilosSiteService.linkTexto_color(idLocal);
		System.out.println("linkTexto_color " + linkTexto_color);
		model.addAttribute("linkTexto_color", linkTexto_color);
		
		String linkTexto_fuente = tblEstilosSiteService.linkTexto_fuente(idLocal);
		System.out.println("linkTexto_fuente " + linkTexto_fuente);
		model.addAttribute("linkTexto_fuente", linkTexto_fuente);
		
		String boton_color = tblEstilosSiteService.boton_color(idLocal);
		System.out.println("boton_color " + boton_color);
		model.addAttribute("boton_color", boton_color);
		
		String footer_color = tblEstilosSiteService.footer_color(idLocal);
		System.out.println("footer_color " + footer_color);
		model.addAttribute("footer_color", footer_color);
		
		String LetraFooter_color = tblEstilosSiteService.LetraFooter_color(idLocal);
		System.out.println("LetraFooter_color " + LetraFooter_color);
		model.addAttribute("LetraFooter_color", LetraFooter_color);
		
		String LetraFooter_fuente = tblEstilosSiteService.LetraFooter_fuente(idLocal);
		System.out.println("LetraFooter_fuente " + LetraFooter_fuente);
		model.addAttribute("LetraFooter_fuente", LetraFooter_fuente);
		
		String subtituloFooter_fuente = tblEstilosSiteService.subtituloFooter_fuente(idLocal);
		System.out.println("subtituloFooter_fuente " + subtituloFooter_fuente);
		model.addAttribute("subtituloFooter_fuente", subtituloFooter_fuente);
		
		String subtituloFooter_color = tblEstilosSiteService.subtituloFooter_color(idLocal);
		System.out.println("subtituloFooter_color " + subtituloFooter_color);
		model.addAttribute("subtituloFooter_color", subtituloFooter_color);
	
		String direccion_contenido = tblEstilosSiteService.direccion_contenido(idLocal);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblEstilosSiteService.telefono_contenido(idLocal);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblEstilosSiteService.correo_contenido(idLocal);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
		
		
		
		String GoogleMaps = tblEstilosSiteService.GoogleMaps(idLocal);
		System.out.println("GoogleMaps " + GoogleMaps);
		model.addAttribute("xGoogleMaps", GoogleMaps);
		
		return "contacto";
		
	}
	
	
	
	
	
	@GetMapping("/ConsultarFactura")
	public String ConsultarFactura(HttpServletRequest request,Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
         idLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("EL xIdLocal DESDE LA CLASE SiteController Y EL METODO  ConsultarFactura ES :  " + idLocal);
        
        model.addAttribute("xILocal", idLocal);
		
		//NAVBAR
		String Navbar_color = tblEstilosSiteService.Navbar_color(idLocal);
		System.out.println("Navbar_color " + Navbar_color);
		model.addAttribute("Navbar_color", Navbar_color);

		String LetraNavbar_color = tblEstilosSiteService.LetraNavbar_color(idLocal);
		System.out.println("LetraNavbar_color " + LetraNavbar_color);
		model.addAttribute("LetraNavbar_color", LetraNavbar_color);

		String LetraNavbar_fuente = tblEstilosSiteService.LetraNavbar_fuente(idLocal);
		System.out.println("LetraNavbar_fuente " + LetraNavbar_fuente);
		model.addAttribute("LetraNavbar_fuente", LetraNavbar_fuente);
		
		String titulo_color = tblEstilosSiteService.titulo_color(idLocal);
		System.out.println("titulo_color " + titulo_color);
		model.addAttribute("titulo_color", titulo_color);
		
		String titulo_fuente = tblEstilosSiteService.titulo_fuente(idLocal);
		System.out.println("titulo_fuente " + titulo_fuente);
		model.addAttribute("titulo_fuente", titulo_fuente);
		
		String tituloNombreEmpresa_color = tblEstilosSiteService.tituloNombreEmpresa_color(idLocal);
		System.out.println("tituloNombreEmpresa_color " + tituloNombreEmpresa_color);
		model.addAttribute("tituloNombreEmpresa_color", tituloNombreEmpresa_color);
		
		String tituloNombreEmpresa_fuente = tblEstilosSiteService.tituloNombreEmpresa_fuente(idLocal);
		System.out.println("tituloNombreEmpresa_fuente " + tituloNombreEmpresa_fuente);
		model.addAttribute("tituloNombreEmpresa_fuente", tituloNombreEmpresa_fuente);
		
		String tituloNombreEmpresa_contenido = tblEstilosSiteService.tituloNombreEmpresa_contenido(idLocal);
		System.out.println("tituloNombreEmpresa_contenido " + tituloNombreEmpresa_contenido);
		model.addAttribute("tituloNombreEmpresa_contenido", tituloNombreEmpresa_contenido);
		
		
		String subtitulo_color = tblEstilosSiteService.subtitulo_color(idLocal);
		System.out.println("subtitulo_color " + subtitulo_color);
		model.addAttribute("subtitulo_color", subtitulo_color);
		
		String subtitulo_fuente = tblEstilosSiteService.subtitulo_fuente(idLocal);
		System.out.println("subtitulo_fuente " + subtitulo_fuente);
		model.addAttribute("subtitulo_fuente", subtitulo_fuente);
		
		String linkTexto_color = tblEstilosSiteService.linkTexto_color(idLocal);
		System.out.println("linkTexto_color " + linkTexto_color);
		model.addAttribute("linkTexto_color", linkTexto_color);
		
		String linkTexto_fuente = tblEstilosSiteService.linkTexto_fuente(idLocal);
		System.out.println("linkTexto_fuente " + linkTexto_fuente);
		model.addAttribute("linkTexto_fuente", linkTexto_fuente);
		
		String boton_color = tblEstilosSiteService.boton_color(idLocal);
		System.out.println("boton_color " + boton_color);
		model.addAttribute("boton_color", boton_color);
		
		String footer_color = tblEstilosSiteService.footer_color(idLocal);
		System.out.println("footer_color " + footer_color);
		model.addAttribute("footer_color", footer_color);
		
		String LetraFooter_color = tblEstilosSiteService.LetraFooter_color(idLocal);
		System.out.println("LetraFooter_color " + LetraFooter_color);
		model.addAttribute("LetraFooter_color", LetraFooter_color);
		
		String LetraFooter_fuente = tblEstilosSiteService.LetraFooter_fuente(idLocal);
		System.out.println("LetraFooter_fuente " + LetraFooter_fuente);
		model.addAttribute("LetraFooter_fuente", LetraFooter_fuente);
		
		String subtituloFooter_fuente = tblEstilosSiteService.subtituloFooter_fuente(idLocal);
		System.out.println("subtituloFooter_fuente " + subtituloFooter_fuente);
		model.addAttribute("subtituloFooter_fuente", subtituloFooter_fuente);
		
		String subtituloFooter_color = tblEstilosSiteService.subtituloFooter_color(idLocal);
		System.out.println("subtituloFooter_color " + subtituloFooter_color);
		model.addAttribute("subtituloFooter_color", subtituloFooter_color);
		
		
		String direccion_contenido = tblEstilosSiteService.direccion_contenido(idLocal);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblEstilosSiteService.telefono_contenido(idLocal);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblEstilosSiteService.correo_contenido(idLocal);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
		
		
		return "ConsultarFactura";
		
	}
	
	
	@GetMapping("/DondePagar")
	public String DondePagar(HttpServletRequest request,Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
         idLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("EL xIdLocal DESDE LA CLASE SiteController Y EL METODO  DondePagar ES :  " + idLocal);
		
		//NAVBAR
		String Navbar_color = tblEstilosSiteService.Navbar_color(idLocal);
		System.out.println("Navbar_color " + Navbar_color);
		model.addAttribute("Navbar_color", Navbar_color);

		String LetraNavbar_color = tblEstilosSiteService.LetraNavbar_color(idLocal);
		System.out.println("LetraNavbar_color " + LetraNavbar_color);
		model.addAttribute("LetraNavbar_color", LetraNavbar_color);

		String LetraNavbar_fuente = tblEstilosSiteService.LetraNavbar_fuente(idLocal);
		System.out.println("LetraNavbar_fuente " + LetraNavbar_fuente);
		model.addAttribute("LetraNavbar_fuente", LetraNavbar_fuente);
		
		String titulo_color = tblEstilosSiteService.titulo_color(idLocal);
		System.out.println("titulo_color " + titulo_color);
		model.addAttribute("titulo_color", titulo_color);
		
		String titulo_fuente = tblEstilosSiteService.titulo_fuente(idLocal);
		System.out.println("titulo_fuente " + titulo_fuente);
		model.addAttribute("titulo_fuente", titulo_fuente);
		
		String tituloNombreEmpresa_color = tblEstilosSiteService.tituloNombreEmpresa_color(idLocal);
		System.out.println("tituloNombreEmpresa_color " + tituloNombreEmpresa_color);
		model.addAttribute("tituloNombreEmpresa_color", tituloNombreEmpresa_color);
		
		String tituloNombreEmpresa_fuente = tblEstilosSiteService.tituloNombreEmpresa_fuente(idLocal);
		System.out.println("tituloNombreEmpresa_fuente " + tituloNombreEmpresa_fuente);
		model.addAttribute("tituloNombreEmpresa_fuente", tituloNombreEmpresa_fuente);
		
		String tituloNombreEmpresa_contenido = tblEstilosSiteService.tituloNombreEmpresa_contenido(idLocal);
		System.out.println("tituloNombreEmpresa_contenido " + tituloNombreEmpresa_contenido);
		model.addAttribute("tituloNombreEmpresa_contenido", tituloNombreEmpresa_contenido);
		
		
		String subtitulo_color = tblEstilosSiteService.subtitulo_color(idLocal);
		System.out.println("subtitulo_color " + subtitulo_color);
		model.addAttribute("subtitulo_color", subtitulo_color);
		
		String subtitulo_fuente = tblEstilosSiteService.subtitulo_fuente(idLocal);
		System.out.println("subtitulo_fuente " + subtitulo_fuente);
		model.addAttribute("subtitulo_fuente", subtitulo_fuente);
		
		String linkTexto_color = tblEstilosSiteService.linkTexto_color(idLocal);
		System.out.println("linkTexto_color " + linkTexto_color);
		model.addAttribute("linkTexto_color", linkTexto_color);
		
		String linkTexto_fuente = tblEstilosSiteService.linkTexto_fuente(idLocal);
		System.out.println("linkTexto_fuente " + linkTexto_fuente);
		model.addAttribute("linkTexto_fuente", linkTexto_fuente);
		
		String boton_color = tblEstilosSiteService.boton_color(idLocal);
		System.out.println("boton_color " + boton_color);
		model.addAttribute("boton_color", boton_color);
		
		String footer_color = tblEstilosSiteService.footer_color(idLocal);
		System.out.println("footer_color " + footer_color);
		model.addAttribute("footer_color", footer_color);
		
		String LetraFooter_color = tblEstilosSiteService.LetraFooter_color(idLocal);
		System.out.println("LetraFooter_color " + LetraFooter_color);
		model.addAttribute("LetraFooter_color", LetraFooter_color);
		
		String LetraFooter_fuente = tblEstilosSiteService.LetraFooter_fuente(idLocal);
		System.out.println("LetraFooter_fuente " + LetraFooter_fuente);
		model.addAttribute("LetraFooter_fuente", LetraFooter_fuente);
		
		String subtituloFooter_fuente = tblEstilosSiteService.subtituloFooter_fuente(idLocal);
		System.out.println("subtituloFooter_fuente " + subtituloFooter_fuente);
		model.addAttribute("subtituloFooter_fuente", subtituloFooter_fuente);
		
		String subtituloFooter_color = tblEstilosSiteService.subtituloFooter_color(idLocal);
		System.out.println("subtituloFooter_color " + subtituloFooter_color);
		model.addAttribute("subtituloFooter_color", subtituloFooter_color);
		
		
		String direccion_contenido = tblEstilosSiteService.direccion_contenido(idLocal);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblEstilosSiteService.telefono_contenido(idLocal);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblEstilosSiteService.correo_contenido(idLocal);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
		String puntoDePago_1_contenido = tblEstilosSiteService.puntoDePago_1_contenido(idLocal);
		System.out.println("puntoDePago_1_contenido " + puntoDePago_1_contenido);
		model.addAttribute("puntoDePago_1_contenido", puntoDePago_1_contenido);
		
		String puntoDePago_2_contenido = tblEstilosSiteService.puntoDePago_2_contenido(idLocal);
		System.out.println("puntoDePago_2_contenido " + puntoDePago_2_contenido);
		model.addAttribute("puntoDePago_2_contenido", puntoDePago_2_contenido);
		
		String puntoDePago_3_contenido = tblEstilosSiteService.puntoDePago_3_contenido(idLocal);
		System.out.println("puntoDePago_3_contenido " + puntoDePago_3_contenido);
		model.addAttribute("puntoDePago_3_contenido", puntoDePago_3_contenido);
		
		String puntoDePago_4_contenido = tblEstilosSiteService.puntoDePago_4_contenido(idLocal);
		System.out.println("puntoDePago_4_contenido " + puntoDePago_4_contenido);
		model.addAttribute("puntoDePago_4_contenido", puntoDePago_4_contenido);
		
		
		List<TblEstilosSite> listaPago = tblEstilosSiteService.listaPuntosDePago(idLocal);
		
		model.addAttribute("xListaPago", listaPago);
		
		for(TblEstilosSite pago : listaPago ) {
			
			System.out.println("Pago es " + pago.getValor());
			System.out.println("-----------------------------------------------------------------------");
			
		}
		
		
		return "DondePagar";
		
	}
	
	
	@GetMapping("/PqrSitioWeb")
	public String PqrSitioWeb(HttpServletRequest request,Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
         idLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("EL xIdLocal DESDE LA CLASE SiteController Y EL METODO  PQRsite ES :  " + idLocal);
		
		//NAVBAR
		String Navbar_color = tblEstilosSiteService.Navbar_color(idLocal);
		System.out.println("Navbar_color " + Navbar_color);
		model.addAttribute("Navbar_color", Navbar_color);

		String LetraNavbar_color = tblEstilosSiteService.LetraNavbar_color(idLocal);
		System.out.println("LetraNavbar_color " + LetraNavbar_color);
		model.addAttribute("LetraNavbar_color", LetraNavbar_color);

		String LetraNavbar_fuente = tblEstilosSiteService.LetraNavbar_fuente(idLocal);
		System.out.println("LetraNavbar_fuente " + LetraNavbar_fuente);
		model.addAttribute("LetraNavbar_fuente", LetraNavbar_fuente);
		
		String titulo_color = tblEstilosSiteService.titulo_color(idLocal);
		System.out.println("titulo_color " + titulo_color);
		model.addAttribute("titulo_color", titulo_color);
		
		String titulo_fuente = tblEstilosSiteService.titulo_fuente(idLocal);
		System.out.println("titulo_fuente " + titulo_fuente);
		model.addAttribute("titulo_fuente", titulo_fuente);
		
		
		String tituloNombreEmpresa_color = tblEstilosSiteService.tituloNombreEmpresa_color(idLocal);
		System.out.println("tituloNombreEmpresa_color " + tituloNombreEmpresa_color);
		model.addAttribute("tituloNombreEmpresa_color", tituloNombreEmpresa_color);
		
		String tituloNombreEmpresa_fuente = tblEstilosSiteService.tituloNombreEmpresa_fuente(idLocal);
		System.out.println("tituloNombreEmpresa_fuente " + tituloNombreEmpresa_fuente);
		model.addAttribute("tituloNombreEmpresa_fuente", tituloNombreEmpresa_fuente);
		
		String tituloNombreEmpresa_contenido = tblEstilosSiteService.tituloNombreEmpresa_contenido(idLocal);
		System.out.println("tituloNombreEmpresa_contenido " + tituloNombreEmpresa_contenido);
		model.addAttribute("tituloNombreEmpresa_contenido", tituloNombreEmpresa_contenido);
		
		
		String subtitulo_color = tblEstilosSiteService.subtitulo_color(idLocal);
		System.out.println("subtitulo_color " + subtitulo_color);
		model.addAttribute("subtitulo_color", subtitulo_color);
		
		String subtitulo_fuente = tblEstilosSiteService.subtitulo_fuente(idLocal);
		System.out.println("subtitulo_fuente " + subtitulo_fuente);
		model.addAttribute("subtitulo_fuente", subtitulo_fuente);
		
		String linkTexto_color = tblEstilosSiteService.linkTexto_color(idLocal);
		System.out.println("linkTexto_color " + linkTexto_color);
		model.addAttribute("linkTexto_color", linkTexto_color);
		
		String linkTexto_fuente = tblEstilosSiteService.linkTexto_fuente(idLocal);
		System.out.println("linkTexto_fuente " + linkTexto_fuente);
		model.addAttribute("linkTexto_fuente", linkTexto_fuente);
		
		String boton_color = tblEstilosSiteService.boton_color(idLocal);
		System.out.println("boton_color " + boton_color);
		model.addAttribute("boton_color", boton_color);
		
		String footer_color = tblEstilosSiteService.footer_color(idLocal);
		System.out.println("footer_color " + footer_color);
		model.addAttribute("footer_color", footer_color);
		
		String LetraFooter_color = tblEstilosSiteService.LetraFooter_color(idLocal);
		System.out.println("LetraFooter_color " + LetraFooter_color);
		model.addAttribute("LetraFooter_color", LetraFooter_color);
		
		String LetraFooter_fuente = tblEstilosSiteService.LetraFooter_fuente(idLocal);
		System.out.println("LetraFooter_fuente " + LetraFooter_fuente);
		model.addAttribute("LetraFooter_fuente", LetraFooter_fuente);
		
		String subtituloFooter_fuente = tblEstilosSiteService.subtituloFooter_fuente(idLocal);
		System.out.println("subtituloFooter_fuente " + subtituloFooter_fuente);
		model.addAttribute("subtituloFooter_fuente", subtituloFooter_fuente);
		
		String subtituloFooter_color = tblEstilosSiteService.subtituloFooter_color(idLocal);
		System.out.println("subtituloFooter_color " + subtituloFooter_color);
		model.addAttribute("subtituloFooter_color", subtituloFooter_color);

	
		String direccion_contenido = tblEstilosSiteService.direccion_contenido(idLocal);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblEstilosSiteService.telefono_contenido(idLocal);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblEstilosSiteService.correo_contenido(idLocal);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
		
		
		return "PQRSite";
		
	}
	
	
	@PostMapping("/GenerarPQRSite-post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> generarPQRSitePost (@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request, Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
        Integer xidLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("xidLocal en /Contacto-post  es:" + xidLocal);
		
		
		// Obtenemos los datos del JSON recibido
        String email = (String) requestBody.get("email"); 
        System.out.println("email es:" + email);
        
        String tipoDocumento = (String) requestBody.get("tipoDocumento"); 
        System.out.println("tipoDocumento es:" + tipoDocumento);
        
        String identificacion = (String) requestBody.get("identificacion"); 
        System.out.println("identificacion es:" + identificacion);
        
        String nombres = (String) requestBody.get("nombres"); 
        System.out.println("nombres es:" + nombres);
        
        String apellidos = (String) requestBody.get("apellidos"); 
        System.out.println("apellidos es:" + apellidos);
        
        String direccion = (String) requestBody.get("direccion"); 
        System.out.println("direccion es:" + direccion);
        
        String telefono = (String) requestBody.get("telefono"); 
        System.out.println("telefono es:" + telefono);
        
        String celular = (String) requestBody.get("celular"); 
        System.out.println("celular es:" + celular);
        
        String codigoInterno = (String) requestBody.get("codigoInterno"); 
        System.out.println("codigoInterno es:" + codigoInterno);
        
        String tipoSolicitud = (String) requestBody.get("tipoSolicitud"); 
        System.out.println("tipoSolicitud es:" + tipoSolicitud);
        
        String nombreSolicitud = (String) requestBody.get("nombreSolicitud"); 
        System.out.println("nombreSolicitud es:" + nombreSolicitud);
        
        String registradoPor = (String) requestBody.get("registradoPor"); 
        System.out.println("registradoPor es:" + registradoPor);
        
        String comentario = (String) requestBody.get("comentario"); 
        System.out.println("comentario es:" + comentario);
        
        String xAsunto = tipoSolicitud + " " + nombres ;
        
        String xContenidoCorreo = "Nombre: " + nombres + "\n" +
                "Apellidos: " + apellidos + "\n" +
                "Direccion: " + direccion + "\n" +
                "Documento: " + identificacion + "\n" +
                "Telefono: " + telefono + "\n" +
                "Celular: " + celular + "\n" +
                "Correo electronico: " + email + "\n" +
                "Código interno: " + codigoInterno + "\n" +
                "Tipo de Solicitud: " + tipoSolicitud + "\n" +
                "Nombre solicitud: " + nombreSolicitud + "\n" +
                "Registrado por: " + registradoPor + "\n" +
                 "\n" +
                "Comentario: " + comentario;
        
        String PathFile = "";
        Integer idDcto = 0;
        String FileName = "";
        String xTextoEmail = "";
        String xPathZippdfxml = "";
        
        
        //------------------------------------  INGRESA PQR EN DB -----------------------------------------------------------------
        
     // Obtenemos el IDORDEN Máximo y le sumamos 1
     Integer maximoIdOrdenSum1 = tblDctosOrdenesService.obtenerMaximoIDORDEN(xidLocal) + 1;
     System.out.println("maximoIDORDEN: " + maximoIdOrdenSum1);
     			
     			
     // Obtenemos el máximo NumeroOrden y le sumamos 1
     Integer maximoNumeroOrdenSum1 = tblDctosOrdenesService.findMaxNumeroOrden(xidLocal) + 1;
     			
     Integer maximoIDLOG = 0;        
     Integer IdUsuario = 9999;
     String NroFactura = "0";
     
     Integer idPluServicioAcdto = 4200;
     Integer idPluServicio = 4205;
     Integer idPluPeticion = 4210;
     
     String nombrePlu = "PQR";
     Integer valor0 = 0;
     
     String ComentarioFinal = "PQR WEB - " + comentario;
     
     
     // Obtener la fecha actual
     LocalDateTime fechaActual = LocalDateTime.now();

     // Formatear la fecha como un String
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     String strFechaVisita = fechaActual.format(formatter);
     
     
     Timestamp xfechaRadicacion = null;
     
     try {
     // Convertimos la cadena fechaRadicacionFormateada en un objeto Timestamp
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     Date parsedDate = sdf.parse(strFechaVisita);
     xfechaRadicacion = new Timestamp(parsedDate.getTime());
     
     } catch (ParseException e) {
         e.printStackTrace();
     }
                 
      // Ingresamos la orden en TblDctosOrdenes
      tblDctosOrdenesService.ingresarOrdenPQRSite(xidLocal, maximoIdOrdenSum1, codigoInterno, IdUsuario, maximoIDLOG, maximoNumeroOrdenSum1, NroFactura, xfechaRadicacion);
        
      //Ingresamos orden de TblDctosOrdenesDetalle
      tblDctosOrdenesDetalleService.ingresarDetalleOrdenPQRSite(xidLocal, maximoIdOrdenSum1, codigoInterno, idPluServicioAcdto, nombrePlu, ComentarioFinal, valor0);
      tblDctosOrdenesDetalleService.ingresarDetalleOrdenPQRSite(xidLocal, maximoIdOrdenSum1, codigoInterno, idPluServicio, nombrePlu, ComentarioFinal, valor0);
      tblDctosOrdenesDetalleService.ingresarDetalleOrdenPQRSite(xidLocal, maximoIdOrdenSum1, codigoInterno, idPluPeticion, nombrePlu, ComentarioFinal, valor0);
      
      //Obtenemos el MAXIMO IdDto y le sumamos 1
	  Integer maxIdDto = tblDctosService.findMaxIdDcto(xidLocal) + 1;
	  
	  System.out.println("maxIdDto es " + maxIdDto);
      
      //Ingresamos el nuevo Dcto
      //tblDctosService.ingresarDto(xidLocal, maximoIdOrdenSum1, maxIdDto, codigoInterno, IdUsuario, strFechaVisita, strFechaVisita);
        
        
        // Invocamos el Jar de Mailjet y le pasamos los parametros 
        mailjetTask.ejecutarJar(xidLocal, xAsunto, xContenidoCorreo, PathFile, idDcto, FileName, email, xTextoEmail, xPathZippdfxml);
        
        Map<String, Object> response = new HashMap<>();
		

		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping("/Contacto-post")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> contactoPost(@RequestBody Map<String, Object> requestBody,
            HttpServletRequest request, Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
        Integer xidLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("xidLocal en /Contacto-post  es:" + xidLocal);
		
		
		
		// Obtenemos los datos del JSON recibido
        String nombre = (String) requestBody.get("nombre"); 
        System.out.println("nombre es:" + nombre);
        
        String email = (String) requestBody.get("email"); 
        System.out.println("email es:" + email);
        
        String asunto = (String) requestBody.get("asunto"); 
        System.out.println("asunto es:" + asunto);
        
        String mensaje = (String) requestBody.get("mensaje"); 
        System.out.println("mensaje es:" + mensaje);
        
        String xContenidoCorreo = "Nombre: " + nombre + " , Correo: " + email + " , Mensaje: " + mensaje;
        
        System.out.println("idLocal en /Contacto-post  es:" + idLocal);
        
        String PathFile = "";
        Integer idDcto = 0;
        String FileName = "";
        String xTextoEmail = "";
        String xPathZippdfxml = "";
        // Invocamos el Jar de Mailjet y le pasamos los parametros 
        mailjetTask.ejecutarJar(xidLocal, asunto, xContenidoCorreo, PathFile, idDcto, FileName, email, xTextoEmail, xPathZippdfxml);
        
        Map<String, Object> response = new HashMap<>();
		

		
		return ResponseEntity.ok(response);
		
	}
	
	
	
	

	@GetMapping("/Institucional")
	public String Estatutos(HttpServletRequest request,Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
         idLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("EL xIdLocal DESDE LA CLASE SiteController Y EL METODO  Nosotros ES :  " + idLocal);
		
		
		//NAVBAR
		String Navbar_color = tblEstilosSiteService.Navbar_color(idLocal);
		System.out.println("Navbar_color " + Navbar_color);
		model.addAttribute("Navbar_color", Navbar_color);

		String LetraNavbar_color = tblEstilosSiteService.LetraNavbar_color(idLocal);
		System.out.println("LetraNavbar_color " + LetraNavbar_color);
		model.addAttribute("LetraNavbar_color", LetraNavbar_color);

		String LetraNavbar_fuente = tblEstilosSiteService.LetraNavbar_fuente(idLocal);
		System.out.println("LetraNavbar_fuente " + LetraNavbar_fuente);
		model.addAttribute("LetraNavbar_fuente", LetraNavbar_fuente);
		
		String titulo_color = tblEstilosSiteService.titulo_color(idLocal);
		System.out.println("titulo_color " + titulo_color);
		model.addAttribute("titulo_color", titulo_color);
		
		String titulo_fuente = tblEstilosSiteService.titulo_fuente(idLocal);
		System.out.println("titulo_fuente " + titulo_fuente);
		model.addAttribute("titulo_fuente", titulo_fuente);
		
		String tituloNombreEmpresa_color = tblEstilosSiteService.tituloNombreEmpresa_color(idLocal);
		System.out.println("tituloNombreEmpresa_color " + tituloNombreEmpresa_color);
		model.addAttribute("tituloNombreEmpresa_color", tituloNombreEmpresa_color);
		
		String tituloNombreEmpresa_fuente = tblEstilosSiteService.tituloNombreEmpresa_fuente(idLocal);
		System.out.println("tituloNombreEmpresa_fuente " + tituloNombreEmpresa_fuente);
		model.addAttribute("tituloNombreEmpresa_fuente", tituloNombreEmpresa_fuente);
		
		String tituloNombreEmpresa_contenido = tblEstilosSiteService.tituloNombreEmpresa_contenido(idLocal);
		System.out.println("tituloNombreEmpresa_contenido " + tituloNombreEmpresa_contenido);
		model.addAttribute("tituloNombreEmpresa_contenido", tituloNombreEmpresa_contenido);
		
		
		String checkList_color = tblEstilosSiteService.checkList_color(idLocal);
		System.out.println("checkList_color " + checkList_color);
		model.addAttribute("checkList_color", checkList_color);
		
		String checkList_texto_color = tblEstilosSiteService.checkList_texto_color(idLocal);
		System.out.println("checkList_texto_color " + checkList_texto_color);
		model.addAttribute("checkList_texto_color", checkList_texto_color);
		
		String checkList_texto_fuente = tblEstilosSiteService.checkList_texto_fuente(idLocal);
		System.out.println("checkList_texto_fuente " + checkList_texto_fuente);
		model.addAttribute("checkList_texto_fuente", checkList_texto_fuente);
		
		String checkList_texto_1_contenido = tblEstilosSiteService.checkList_texto_1_contenido(idLocal);
		System.out.println("checkList_texto_1_contenido " + checkList_texto_1_contenido);
		model.addAttribute("checkList_texto_1_contenido", checkList_texto_1_contenido);
		
		String checkList_texto_2_contenido = tblEstilosSiteService.checkList_texto_2_contenido(idLocal);
		System.out.println("checkList_texto_2_contenido " + checkList_texto_2_contenido);
		model.addAttribute("checkList_texto_2_contenido", checkList_texto_2_contenido);
		
		String checkList_texto_3_contenido = tblEstilosSiteService.checkList_texto_3_contenido(idLocal);
		System.out.println("checkList_texto_3_contenido " + checkList_texto_3_contenido);
		model.addAttribute("checkList_texto_3_contenido", checkList_texto_3_contenido);
		
		String subtitulo_color = tblEstilosSiteService.subtitulo_color(idLocal);
		System.out.println("subtitulo_color " + subtitulo_color);
		model.addAttribute("subtitulo_color", subtitulo_color);
		
		String subtitulo_fuente = tblEstilosSiteService.subtitulo_fuente(idLocal);
		System.out.println("subtitulo_fuente " + subtitulo_fuente);
		model.addAttribute("subtitulo_fuente", subtitulo_fuente);
		
		String linkTexto_color = tblEstilosSiteService.linkTexto_color(idLocal);
		System.out.println("linkTexto_color " + linkTexto_color);
		model.addAttribute("linkTexto_color", linkTexto_color);
		
		String linkTexto_fuente = tblEstilosSiteService.linkTexto_fuente(idLocal);
		System.out.println("linkTexto_fuente " + linkTexto_fuente);
		model.addAttribute("linkTexto_fuente", linkTexto_fuente);
		
		String boton_color = tblEstilosSiteService.boton_color(idLocal);
		System.out.println("boton_color " + boton_color);
		model.addAttribute("boton_color", boton_color);
		
		String footer_color = tblEstilosSiteService.footer_color(idLocal);
		System.out.println("footer_color " + footer_color);
		model.addAttribute("footer_color", footer_color);
		
		String LetraFooter_color = tblEstilosSiteService.LetraFooter_color(idLocal);
		System.out.println("LetraFooter_color " + LetraFooter_color);
		model.addAttribute("LetraFooter_color", LetraFooter_color);
		
		String LetraFooter_fuente = tblEstilosSiteService.LetraFooter_fuente(idLocal);
		System.out.println("LetraFooter_fuente " + LetraFooter_fuente);
		model.addAttribute("LetraFooter_fuente", LetraFooter_fuente);
		
		String subtituloFooter_fuente = tblEstilosSiteService.subtituloFooter_fuente(idLocal);
		System.out.println("subtituloFooter_fuente " + subtituloFooter_fuente);
		model.addAttribute("subtituloFooter_fuente", subtituloFooter_fuente);
		
		String subtituloFooter_color = tblEstilosSiteService.subtituloFooter_color(idLocal);
		System.out.println("subtituloFooter_color " + subtituloFooter_color);
		model.addAttribute("subtituloFooter_color", subtituloFooter_color);
		

		
		String direccion_contenido = tblEstilosSiteService.direccion_contenido(idLocal);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblEstilosSiteService.telefono_contenido(idLocal);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblEstilosSiteService.correo_contenido(idLocal);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
		

		
		List<TblEstilosSite> documentos = tblEstilosSiteService.Documentos(idLocal);
		model.addAttribute("documentos", documentos);
		
		for(TblEstilosSite dcto : documentos) {
			
			System.out.println("iNombre Dcto " + dcto.getCampo());
			System.out.println("Ruta Dcto " + dcto.getValor());
			System.out.println("------------------------------" );
			
		}
		
		return "Institucional";


		
	}
	
	
	
	
	
	@GetMapping("/WhatsApp")
	public String WhatsApp(HttpServletRequest request,Model model) {
		
		// Obtenmos la sesión desde la solicitud
        HttpSession session = request.getSession();

        // Obtemos el valor de idLocal de la sesión
         idLocal = (Integer) session.getAttribute("idLocal");
        System.out.println("EL xIdLocal DESDE LA CLASE SiteController Y EL METODO  ConsultarFactura ES :  " + idLocal);
        
        model.addAttribute("xILocal", idLocal);
		
		//NAVBAR
		String Navbar_color = tblEstilosSiteService.Navbar_color(idLocal);
		System.out.println("Navbar_color " + Navbar_color);
		model.addAttribute("Navbar_color", Navbar_color);

		String LetraNavbar_color = tblEstilosSiteService.LetraNavbar_color(idLocal);
		System.out.println("LetraNavbar_color " + LetraNavbar_color);
		model.addAttribute("LetraNavbar_color", LetraNavbar_color);

		String LetraNavbar_fuente = tblEstilosSiteService.LetraNavbar_fuente(idLocal);
		System.out.println("LetraNavbar_fuente " + LetraNavbar_fuente);
		model.addAttribute("LetraNavbar_fuente", LetraNavbar_fuente);
		
		String titulo_color = tblEstilosSiteService.titulo_color(idLocal);
		System.out.println("titulo_color " + titulo_color);
		model.addAttribute("titulo_color", titulo_color);
		
		String titulo_fuente = tblEstilosSiteService.titulo_fuente(idLocal);
		System.out.println("titulo_fuente " + titulo_fuente);
		model.addAttribute("titulo_fuente", titulo_fuente);
		
		String nosotrosResumen_color = tblEstilosSiteService.nosotrosResumen_color(idLocal);
		System.out.println("nosotrosResumen_color " + nosotrosResumen_color);
		model.addAttribute("nosotrosResumen_color", nosotrosResumen_color);
		
		String nosotrosResumen_fuente = tblEstilosSiteService.nosotrosResumen_fuente(idLocal);
		System.out.println("nosotrosResumen_fuente " + nosotrosResumen_fuente);
		model.addAttribute("nosotrosResumen_fuente", nosotrosResumen_fuente);
		
		String nosotrosResumen_contenido = tblEstilosSiteService.nosotrosResumen_contenido(idLocal);
		System.out.println("nosotrosResumen_contenido " + nosotrosResumen_contenido);
		model.addAttribute("nosotrosResumen_contenido", nosotrosResumen_contenido);
		
		String nosotrosTexto_color = tblEstilosSiteService.nosotrosTexto_color(idLocal);
		System.out.println("nosotrosTexto_color " + nosotrosTexto_color);
		model.addAttribute("nosotrosTexto_color", nosotrosTexto_color);
		
		String nosotrosTexto_fuente = tblEstilosSiteService.nosotrosTexto_fuente(idLocal);
		System.out.println("nosotrosTexto_fuente " + nosotrosTexto_fuente);
		model.addAttribute("nosotrosTexto_fuente", nosotrosTexto_fuente);
		
		String nosotrosTexto_contenido = tblEstilosSiteService.nosotrosTexto_contenido(idLocal);
		System.out.println("nosotrosTexto_contenido " + nosotrosTexto_contenido);
		model.addAttribute("nosotrosTexto_contenido", nosotrosTexto_contenido);
		
		String tituloNombreEmpresa_color = tblEstilosSiteService.tituloNombreEmpresa_color(idLocal);
		System.out.println("tituloNombreEmpresa_color " + tituloNombreEmpresa_color);
		model.addAttribute("tituloNombreEmpresa_color", tituloNombreEmpresa_color);
		
		String tituloNombreEmpresa_fuente = tblEstilosSiteService.tituloNombreEmpresa_fuente(idLocal);
		System.out.println("tituloNombreEmpresa_fuente " + tituloNombreEmpresa_fuente);
		model.addAttribute("tituloNombreEmpresa_fuente", tituloNombreEmpresa_fuente);
		
		String tituloNombreEmpresa_contenido = tblEstilosSiteService.tituloNombreEmpresa_contenido(idLocal);
		System.out.println("tituloNombreEmpresa_contenido " + tituloNombreEmpresa_contenido);
		model.addAttribute("tituloNombreEmpresa_contenido", tituloNombreEmpresa_contenido);
		
		
		String checkList_color = tblEstilosSiteService.checkList_color(idLocal);
		System.out.println("checkList_color " + checkList_color);
		model.addAttribute("checkList_color", checkList_color);
		
		String checkList_texto_color = tblEstilosSiteService.checkList_texto_color(idLocal);
		System.out.println("checkList_texto_color " + checkList_texto_color);
		model.addAttribute("checkList_texto_color", checkList_texto_color);
		
		String checkList_texto_fuente = tblEstilosSiteService.checkList_texto_fuente(idLocal);
		System.out.println("checkList_texto_fuente " + checkList_texto_fuente);
		model.addAttribute("checkList_texto_fuente", checkList_texto_fuente);
		
		String checkList_texto_1_contenido = tblEstilosSiteService.checkList_texto_1_contenido(idLocal);
		System.out.println("checkList_texto_1_contenido " + checkList_texto_1_contenido);
		model.addAttribute("checkList_texto_1_contenido", checkList_texto_1_contenido);
		
		String checkList_texto_2_contenido = tblEstilosSiteService.checkList_texto_2_contenido(idLocal);
		System.out.println("checkList_texto_2_contenido " + checkList_texto_2_contenido);
		model.addAttribute("checkList_texto_2_contenido", checkList_texto_2_contenido);
		
		String checkList_texto_3_contenido = tblEstilosSiteService.checkList_texto_3_contenido(idLocal);
		System.out.println("checkList_texto_3_contenido " + checkList_texto_3_contenido);
		model.addAttribute("checkList_texto_3_contenido", checkList_texto_3_contenido);
		
		String subtitulo_color = tblEstilosSiteService.subtitulo_color(idLocal);
		System.out.println("subtitulo_color " + subtitulo_color);
		model.addAttribute("subtitulo_color", subtitulo_color);
		
		String subtitulo_fuente = tblEstilosSiteService.subtitulo_fuente(idLocal);
		System.out.println("subtitulo_fuente " + subtitulo_fuente);
		model.addAttribute("subtitulo_fuente", subtitulo_fuente);
		
		String linkTexto_color = tblEstilosSiteService.linkTexto_color(idLocal);
		System.out.println("linkTexto_color " + linkTexto_color);
		model.addAttribute("linkTexto_color", linkTexto_color);
		
		String linkTexto_fuente = tblEstilosSiteService.linkTexto_fuente(idLocal);
		System.out.println("linkTexto_fuente " + linkTexto_fuente);
		model.addAttribute("linkTexto_fuente", linkTexto_fuente);
		
		String boton_color = tblEstilosSiteService.boton_color(idLocal);
		System.out.println("boton_color " + boton_color);
		model.addAttribute("boton_color", boton_color);
		
		String footer_color = tblEstilosSiteService.footer_color(idLocal);
		System.out.println("footer_color " + footer_color);
		model.addAttribute("footer_color", footer_color);
		
		String LetraFooter_color = tblEstilosSiteService.LetraFooter_color(idLocal);
		System.out.println("LetraFooter_color " + LetraFooter_color);
		model.addAttribute("LetraFooter_color", LetraFooter_color);
		
		String LetraFooter_fuente = tblEstilosSiteService.LetraFooter_fuente(idLocal);
		System.out.println("LetraFooter_fuente " + LetraFooter_fuente);
		model.addAttribute("LetraFooter_fuente", LetraFooter_fuente);
		
		String subtituloFooter_fuente = tblEstilosSiteService.subtituloFooter_fuente(idLocal);
		System.out.println("subtituloFooter_fuente " + subtituloFooter_fuente);
		model.addAttribute("subtituloFooter_fuente", subtituloFooter_fuente);
		
		String subtituloFooter_color = tblEstilosSiteService.subtituloFooter_color(idLocal);
		System.out.println("subtituloFooter_color " + subtituloFooter_color);
		model.addAttribute("subtituloFooter_color", subtituloFooter_color);
		
		String imgNotrosResumen_1_imagen = tblEstilosSiteService.imgNotrosResumen_1_imagen(idLocal);
		System.out.println("imgNotrosResumen_1_imagen " + imgNotrosResumen_1_imagen);
		model.addAttribute("imgNotrosResumen_1_imagen", imgNotrosResumen_1_imagen);
		
		String imgNotrosResumen_2_imagen = tblEstilosSiteService.imgNotrosResumen_2_imagen(idLocal);
		System.out.println("imgNotrosResumen_2_imagen " + imgNotrosResumen_2_imagen);
		model.addAttribute("imgNotrosResumen_2_imagen", imgNotrosResumen_2_imagen);
		
		String imgNotrosResumen_3_imagen = tblEstilosSiteService.imgNotrosResumen_3_imagen(idLocal);
		System.out.println("imgNotrosResumen_3_imagen " + imgNotrosResumen_3_imagen);
		model.addAttribute("imgNotrosResumen_3_imagen", imgNotrosResumen_3_imagen);
		
		String imgNoticia_1_imagen = tblEstilosSiteService.imgNoticia_1_imagen(idLocal);
		System.out.println("imgNoticia_1_imagen " + imgNoticia_1_imagen);
		model.addAttribute("imgNoticia_1_imagen", imgNoticia_1_imagen);
		
		String imgNoticia_2_imagen = tblEstilosSiteService.imgNoticia_2_imagen(idLocal);
		System.out.println("imgNoticia_2_imagen " + imgNoticia_2_imagen);
		model.addAttribute("imgNoticia_2_imagen", imgNoticia_2_imagen);
		
		String imgNoticia_3_imagen = tblEstilosSiteService.imgNoticia_3_imagen(idLocal);
		System.out.println("imgNoticia_3_imagen " + imgNoticia_3_imagen);
		model.addAttribute("imgNoticia_3_imagen", imgNoticia_3_imagen);
		
		String quienesSomosTexto_color = tblEstilosSiteService.quienesSomosTexto_color(idLocal);
		System.out.println("quienesSomosTexto_color " + quienesSomosTexto_color);
		model.addAttribute("quienesSomosTexto_color", quienesSomosTexto_color);
		
		String quienesSomosTexto_fuente = tblEstilosSiteService.quienesSomosTexto_fuente(idLocal);
		System.out.println("quienesSomosTexto_fuente " + quienesSomosTexto_fuente);
		model.addAttribute("quienesSomosTexto_fuente", quienesSomosTexto_fuente);
		
		String quienesSomosTexto_contenido = tblEstilosSiteService.quienesSomosTexto_contenido(idLocal);
		System.out.println("quienesSomosTexto_contenido " + quienesSomosTexto_contenido);
		model.addAttribute("quienesSomosTexto_contenido", quienesSomosTexto_contenido);
		
		String misionTexto_color = tblEstilosSiteService.misionTexto_color(idLocal);
		System.out.println("misionTexto_color " + misionTexto_color);
		model.addAttribute("misionTexto_color", misionTexto_color);
		
		String misionTexto_fuente = tblEstilosSiteService.misionTexto_fuente(idLocal);
		System.out.println("misionTexto_fuente " + misionTexto_fuente);
		model.addAttribute("misionTexto_fuente", misionTexto_fuente);
		
		String misionTexto_contenido = tblEstilosSiteService.misionTexto_contenido(idLocal);
		System.out.println("misionTexto_contenido " + misionTexto_contenido);
		model.addAttribute("misionTexto_contenido", misionTexto_contenido);
		
		String visionTexto_color = tblEstilosSiteService.visionTexto_color(idLocal);
		System.out.println("visionTexto_color " + visionTexto_color);
		model.addAttribute("visionTexto_color", visionTexto_color);
		
		String visionTexto_fuente = tblEstilosSiteService.visionTexto_fuente(idLocal);
		System.out.println("visionTexto_fuente " + visionTexto_fuente);
		model.addAttribute("visionTexto_fuente", visionTexto_fuente);
		
		String visionTexto_contenido = tblEstilosSiteService.visionTexto_contenido(idLocal);
		System.out.println("visionTexto_contenido " + visionTexto_contenido);
		model.addAttribute("visionTexto_contenido", visionTexto_contenido);
		
		String imgQuienesSomos_1_imagen = tblEstilosSiteService.imgQuienesSomos_1_imagen(idLocal);
		System.out.println("imgQuienesSomos_1_imagen " + imgQuienesSomos_1_imagen);
		model.addAttribute("imgQuienesSomos_1_imagen", imgQuienesSomos_1_imagen);
		
		String imgQuienesSomos_2_imagen = tblEstilosSiteService.imgQuienesSomos_2_imagen(idLocal);
		System.out.println("imgQuienesSomos_2_imagen " + imgQuienesSomos_2_imagen);
		model.addAttribute("imgQuienesSomos_2_imagen", imgQuienesSomos_2_imagen);
		
		String imgQuienesSomos_3_imagen = tblEstilosSiteService.imgQuienesSomos_3_imagen(idLocal);
		System.out.println("imgQuienesSomos_3_imagen " + imgQuienesSomos_3_imagen);
		model.addAttribute("imgQuienesSomos_3_imagen", imgQuienesSomos_3_imagen);
		
		String imgMision_1_imagen = tblEstilosSiteService.imgMision_1_imagen(idLocal);
		System.out.println("imgMision_1_imagen " + imgMision_1_imagen);
		model.addAttribute("imgMision_1_imagen", imgMision_1_imagen);
		
		String imgMision_2_imagen = tblEstilosSiteService.imgMision_2_imagen(idLocal);
		System.out.println("imgMision_2_imagen " + imgMision_2_imagen);
		model.addAttribute("imgMision_2_imagen", imgMision_2_imagen);
		
		String imgMision_3_imagen = tblEstilosSiteService.imgMision_3_imagen(idLocal);
		System.out.println("imgMision_3_imagen " + imgMision_3_imagen);
		model.addAttribute("imgMision_3_imagen", imgMision_3_imagen);
		
		String imgVision_1_imagen = tblEstilosSiteService.imgVision_1_imagen(idLocal);
		System.out.println("imgVision_1_imagen " + imgVision_1_imagen);
		model.addAttribute("imgVision_1_imagen", imgVision_1_imagen);
		
		String imgVision_2_imagen = tblEstilosSiteService.imgVision_2_imagen(idLocal);
		System.out.println("imgVision_2_imagen " + imgVision_2_imagen);
		model.addAttribute("imgVision_2_imagen", imgVision_2_imagen);
		
		String imgVision_3_imagen = tblEstilosSiteService.imgVision_3_imagen(idLocal);
		System.out.println("imgVision_3_imagen " + imgVision_3_imagen);
		model.addAttribute("imgVision_3_imagen", imgVision_3_imagen);
		
		String direccion_contenido = tblEstilosSiteService.direccion_contenido(idLocal);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblEstilosSiteService.telefono_contenido(idLocal);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblEstilosSiteService.correo_contenido(idLocal);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
		
		
		return "WhatsApp";
		
	}
	
	
	
	@PostMapping("/ActivarServicioWhatsApp")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> ActualizarSuscriptor(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");

	    Map<String, Object> response = new HashMap<>();
	    
	    Integer idTipoTercero = 1;

	    System.out.println("SI ENTRÓ A  ActivarServicioWhatsApp");

	        // Obtenemos los datos del JSON recibido
	        String xIdCliente = (String) requestBody.get("xIdCliente");
	        String xILocalStr = (String) requestBody.get("xILocal");   
	        Integer xILocal = Integer.parseInt(xILocalStr);
	        
	        String email = (String) requestBody.get("email");  
	        String ccNit = (String) requestBody.get("ccNit");   
	        String telefonoCelular = (String) requestBody.get("telefonoCelular");
	        String estadoServicioStr = (String) requestBody.get("estadoServicio");
	        Integer estadoServicio = Integer.parseInt(estadoServicioStr);
	     
		    // Obtenemos la fecha y hora actual
	        Date fechaActual = new Date();

	        // Formatear la fecha en el formato deseado
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        String fechaActualFormateada = dateFormat.format(fechaActual);

	        // Convertir la fecha formateada a Timestamp
	        Timestamp fechaIngreso = Timestamp.valueOf(fechaActualFormateada + ":00");
	        
	        
	        List<TercerosDTO2> suscriptor = tblTercerosService.obtenerSusciptor(xILocal, xIdCliente, ccNit, telefonoCelular);
	        System.out.println("suscriptor es : " + suscriptor);
	        
	        if(suscriptor == null || suscriptor.isEmpty() || (suscriptor.size() == 1 && suscriptor.get(0) == null)) {
	        	System.out.println("DATOS NO COINCIDEN");
	        	
	        	response.put("message", "FALSE");
	        	return ResponseEntity.ok(response);
	        	
	        }
	    
	        
	        //Obtenemos el estado actual de estadoWhatsApp
	        Integer estado = tblTercerosService.obtenerEstadoWppSuscriptor(xILocal, xIdCliente);
	        
	        
	        if(estado.equals(estadoServicio)) {     	        	
	        	if(estado.equals(1)) {
	        		response.put("message", "Activo");
		        	return ResponseEntity.ok(response);
	        	}
	        	
	        	
                  if(estado.equals(2)) {
                	  response.put("message", "Inactivo");
      	        	return ResponseEntity.ok(response);
	        	}

	        }
	        
//	        response.put("message", "OK");
//	        
//	        
//	        //Invocamos el JAR
//	        activaServicioTask.ejecutarJar(xILocal, ccNit, xIdCliente, telefonoCelular, email, estadoServicio);
	        
	        
	        response.put("message", "OK");
	        
	        // Ejecutar el JAR en un hilo separado para no bloquear la respuesta
	        new Thread(() -> {
	            activaServicioTask.ejecutarJar(xILocal, ccNit, xIdCliente, telefonoCelular, email, estadoServicio);
	        }).start();

		    
		    
		    return ResponseEntity.ok(response);
	   
	    
	}
	
	
	
	
	@PostMapping("/DescargarReporteFacturaProductoClienteSite")
	@ResponseBody
	public ResponseEntity<Resource>  DescargarReporteFacturaProductoIDCLIENTE(@RequestBody Map<String, Object> requestBody, HttpServletRequest request,Model model) throws JRException, IOException, SQLException {
	    Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
	   // Integer IdUsuario = usuario.getIdUsuario();
	    
	   
	    // Crea la lista de strings
	    List<String> listaIdClientes = new ArrayList<>();
	    
	    System.out.println("SI ENTRÓ A  DescargarReporteFacturaProductoClienteSite");

	    
	    String xILocal = (String) requestBody.get("xILocal");
        Integer idLocal = Integer.parseInt(xILocal);
	        // Obtenemos los datos del JSON recibido
        String Cc_Nit = (String) requestBody.get("idTercero");
    	
    	//Obtenemos la lista de idCliente por CC_Nit
    	List<TercerosDTO2> listaIdCliente = tblTercerosService.ListaIdClienteXCcNit(idLocal, Cc_Nit);
    	
    	for(TercerosDTO2 lista : listaIdCliente ) {	    		
    		// Agrega el idCliente a la lista
	    	listaIdClientes.add(lista.getIdCliente());
    		
    	}

	        System.out.println("idLocal es " + idLocal );
	        
	        System.out.println("idLocal es " + idLocal );	        
	        Integer idPeriodoInt = 0;
	        
	      
	        // Obtenemos el periodo activo
			List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
			
			for(TblDctosPeriodo P : PeriodoActivo) {
				
				idPeriodoInt =  P.getIdPeriodo();
			
			}

			List<Integer> listaPeriodos = tblDctosPeriodoService.ObtenerUltimos5Periodos(idLocal, idPeriodoInt);
			System.out.println("listaPeriodos es " + listaPeriodos );
			System.out.println("listaIdClientes es " + listaIdClientes );
			
			String idPeriodo = idPeriodoInt.toString();
			

	        
			String formato = "PDF";
		    int xIdReporte = 1140;
		   
		    
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

		   String xPathImagen = "";
		   
		   String xTextoComentario = " ==> Su factura presenta cuentas vencidas."
	                + " Lo esperamos en la administración del acueducto para normalizar su situación. ";
		   
		   String xTextoSubsidioContribucion = "==> Incluye subsidio y contribución del mes";
		   
		   
		   String xCharSeparator = File.separator;
	      //  String xPathFileGral = ""; 
		   	String xPathFileGralDB = ""; 
	        String StringPathLinux = "/home/sw"; 
	        String StringPathWindows = "c:"; 
	        

	        Integer xEstadoGeneraIAC = null;
	        
	        int xEstadoGeneraIAC_SI = 1;
	        
	        
	      
	        
		    for(TblLocales L : Local) {
		    	
			    // Parametros del encabezado 
			    params.put("p_idPeriodo", idPeriodo);
			    params.put("p_nombreLocal", L.getNombreLocal());
			    params.put("p_razonSocial", L.getRazonSocial());
			    params.put("p_nit", L.getNit());
			    params.put("p_titulo", xTituloReporte);
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
			    params.put("p_fechaResolucion", L.getFechaResolucion());
			    params.put("p_ciudad", L.getCiudad());
			    params.put("p_rango", L.getRango());
			    
			    xPathImagen = L.getPathImagen();
			    String xFirmaDigital = xPathImagen + "codigoBarra_" + idLocal.toString() + ".jpg";
				   
		        System.out.println("xFirmaDigital es : " + xFirmaDigital);
			    
			    System.out.println("xPathImagen es : " + xPathImagen);
			    String xLogoName = xPathImagen + idLocal.toString() + ".jpg";
			    params.put("p_logo", xLogoName);
			    params.put("p_cuentaBanco", L.getCuentaBanco());
			    params.put("p_idTipoOrden", IdTipoOrdenINI);
			    params.put("p_txtFactura", L.getTxtFactura());
			    params.put("p_textoLegal", L.getTextoLegal());
			    params.put("p_textoComentario", xTextoComentario);
			    params.put("p_textoSubsidioContribucion", xTextoSubsidioContribucion);
			    params.put("p_historiaConsumo", "Histórico M3 : ");
			    params.put("p_firmaDigital", xFirmaDigital);
			    params.put("p_logoSuperServicios", xPathImagen + "superServicios.jpg");
			    params.put("p_representanteLegal", L.getRepresentanteLegal());
			    params.put("p_firmaRepresentante", xPathImagen + "firma_" + idLocal.toString() + ".jpg");
			    params.put("p_textoLegal", L.getTextoLegal());
			    
			    xPathReport = L.getPathReport()  + "marketing" + xCharSeparator;
			    xEstadoGeneraIAC = L.getEstadoGeneraIAC();
			    xPathFileGralDB = L.getPathFileGral(); //--------------------------------------------------------------------------------
		    }
		    
		    List <TblDctosPeriodo> infoPeriodo = tblDctosPeriodoService.ObtenerPeriodo(idLocal, idPeriodoInt);
     	    for(TblDctosPeriodo periodo : infoPeriodo) {
		    	
		    	params.put("p_textoPeriodo", periodo.getTextoPeriodo());
		    }
		    
		    
		    String xPathPDF = xPathFileGralDB + "aquamovil" + xCharSeparator + "BDMailFactura" + xCharSeparator + idLocal + xCharSeparator;
		    String xPathXML = xPathFileGralDB + "aquamovil" + xCharSeparator + "zip" + xCharSeparator + idLocal + xCharSeparator;
	        String xPathFileChar = xPathFileGralDB + "aquamovil" + xCharSeparator + "img" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator;
	        String xPathBarCode = xPathFileGralDB+ "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator;
	        String xPathQr = xPathFileGralDB + "aquamovil" + xCharSeparator + "qr" + xCharSeparator + idLocal + xCharSeparator;
	        String xPathZippdfxml = xPathFileGralDB + "aquamovil" + xCharSeparator + "zippdfxml" + xCharSeparator + idLocal + xCharSeparator;
		    
	        
	        params.put("p_pathFileChar", xPathFileChar);
	        params.put("p_Qr", xPathQr);
		    
		    
		    // Genera imagen IAC CODE128
	        if (xEstadoGeneraIAC_SI == xEstadoGeneraIAC) {
	            
	        	String xBarraName = xPathFileGralDB + "aquamovil" + xCharSeparator + "barcode" + xCharSeparator + idLocal + xCharSeparator + idPeriodo + xCharSeparator ;
	            params.put("p_barraName", xBarraName);
	        }
	        
	        System.out.println("idLocal es : " + idLocal);
		   
		    
		    List<TblDctosOrdenesDTO> lista = null;
		    

            // QUERY PARA ALIMENTAR EL DATASOURCE
		    lista = tblDctosOrdenesService.listaUnClienteProductoUltimos5(idLocal, listaIdClientes, listaPeriodos);
		    	
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
