package com.marketing.Controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Service.DBMailMarketing.TblSiteStyleService;




@Controller
public class InicioController {
	
	@Autowired
	TblSiteStyleService tblSiteStyleService;

	@GetMapping("/")
	public String inicio(HttpServletRequest request,Model model) {
		System.out.println("Si entro al controllador");
		
		
		//NAVBAR
		String Navbar_color = tblSiteStyleService.Navbar_color(142);
		System.out.println("Navbar_color " + Navbar_color);
		model.addAttribute("Navbar_color", Navbar_color);

		String LetraNavbar_color = tblSiteStyleService.LetraNavbar_color(142);
		System.out.println("LetraNavbar_color " + LetraNavbar_color);
		model.addAttribute("LetraNavbar_color", LetraNavbar_color);

		String LetraNavbar_fuente = tblSiteStyleService.LetraNavbar_fuente(142);
		System.out.println("LetraNavbar_fuente " + LetraNavbar_fuente);
		model.addAttribute("LetraNavbar_fuente", LetraNavbar_fuente);
		
		//CARRUSEL
		String botonCarrusel_color = tblSiteStyleService.botonCarrusel_color(142);
		System.out.println("botonCarrusel_color " + botonCarrusel_color);
		model.addAttribute("botonCarrusel_color", botonCarrusel_color);
		
		String imgCarrusel_1_imagen = tblSiteStyleService.imgCarrusel_1_imagen(142);
		System.out.println("imgCarrusel_1_imagen " + imgCarrusel_1_imagen);
		model.addAttribute("imgCarrusel_1_imagen", imgCarrusel_1_imagen);
		
		String imgCarrusel_2_imagen = tblSiteStyleService.imgCarrusel_2_imagen(142);
		System.out.println("imgCarrusel_2_imagen " + imgCarrusel_2_imagen);
		model.addAttribute("imgCarrusel_2_imagen", imgCarrusel_2_imagen);

		String textCarrusel_1_color = tblSiteStyleService.textCarrusel_1_color(142);
		System.out.println("textCarrusel_1_color " + textCarrusel_1_color);
		model.addAttribute("textCarrusel_1_color", textCarrusel_1_color);

		String textCarrusel_1_fuente = tblSiteStyleService.textCarrusel_1_fuente(142);
		System.out.println("textCarrusel_1_fuente " + textCarrusel_1_fuente);
		model.addAttribute("textCarrusel_1_fuente", textCarrusel_1_fuente);
		

		String textCarrusel_2_color = tblSiteStyleService.textCarrusel_2_color(142);
		System.out.println("textCarrusel_2_color " + textCarrusel_2_color);
		model.addAttribute("textCarrusel_2_color", textCarrusel_2_color);

		String textCarrusel_2_fuente = tblSiteStyleService.textCarrusel_2_fuente(142);
		System.out.println("textCarrusel_2_fuente " + textCarrusel_2_fuente);
		model.addAttribute("textCarrusel_2_fuente", textCarrusel_2_fuente);
		
		String textCarrusel_1_contenido = tblSiteStyleService.textCarrusel_1_contenido(142);
		System.out.println("textCarrusel_1_contenido " + textCarrusel_1_contenido);
		model.addAttribute("textCarrusel_1_contenido", textCarrusel_1_contenido);

		String textCarrusel_2_contenido = tblSiteStyleService.textCarrusel_2_contenido(142);
		System.out.println("textCarrusel_2_contenido " + textCarrusel_2_contenido);
		model.addAttribute("textCarrusel_2_contenido", textCarrusel_2_contenido);
		
		String textCarrusel_3_contenido = tblSiteStyleService.textCarrusel_3_contenido(142);
		System.out.println("textCarrusel_3_contenido " + textCarrusel_3_contenido);
		model.addAttribute("textCarrusel_3_contenido", textCarrusel_3_contenido);
		
		
		//NOSOTROS
		String titulo_color = tblSiteStyleService.titulo_color(142);
		System.out.println("titulo_color " + titulo_color);
		model.addAttribute("titulo_color", titulo_color);
		
		String titulo_fuente = tblSiteStyleService.titulo_fuente(142);
		System.out.println("titulo_fuente " + titulo_fuente);
		model.addAttribute("titulo_fuente", titulo_fuente);
		
		String nosotrosResumen_color = tblSiteStyleService.nosotrosResumen_color(142);
		System.out.println("nosotrosResumen_color " + nosotrosResumen_color);
		model.addAttribute("nosotrosResumen_color", nosotrosResumen_color);
		
		String nosotrosResumen_fuente = tblSiteStyleService.nosotrosResumen_fuente(142);
		System.out.println("nosotrosResumen_fuente " + nosotrosResumen_fuente);
		model.addAttribute("nosotrosResumen_fuente", nosotrosResumen_fuente);
		
		String nosotrosResumen_contenido = tblSiteStyleService.nosotrosResumen_contenido(142);
		System.out.println("nosotrosResumen_contenido " + nosotrosResumen_contenido);
		model.addAttribute("nosotrosResumen_contenido", nosotrosResumen_contenido);
		
		String nosotrosTexto_color = tblSiteStyleService.nosotrosTexto_color(142);
		System.out.println("nosotrosTexto_color " + nosotrosTexto_color);
		model.addAttribute("nosotrosTexto_color", nosotrosTexto_color);
		
		String nosotrosTexto_fuente = tblSiteStyleService.nosotrosTexto_fuente(142);
		System.out.println("nosotrosTexto_fuente " + nosotrosTexto_fuente);
		model.addAttribute("nosotrosTexto_fuente", nosotrosTexto_fuente);
		
		String nosotrosTexto_contenido = tblSiteStyleService.nosotrosTexto_contenido(142);
		System.out.println("nosotrosTexto_contenido " + nosotrosTexto_contenido);
		model.addAttribute("nosotrosTexto_contenido", nosotrosTexto_contenido);
		
		String tituloNombreEmpresa_color = tblSiteStyleService.tituloNombreEmpresa_color(142);
		System.out.println("tituloNombreEmpresa_color " + tituloNombreEmpresa_color);
		model.addAttribute("tituloNombreEmpresa_color", tituloNombreEmpresa_color);
		
		String tituloNombreEmpresa_fuente = tblSiteStyleService.tituloNombreEmpresa_fuente(142);
		System.out.println("tituloNombreEmpresa_fuente " + tituloNombreEmpresa_fuente);
		model.addAttribute("tituloNombreEmpresa_fuente", tituloNombreEmpresa_fuente);
		
		String tituloNombreEmpresa_contenido = tblSiteStyleService.tituloNombreEmpresa_contenido(142);
		System.out.println("tituloNombreEmpresa_contenido " + tituloNombreEmpresa_contenido);
		model.addAttribute("tituloNombreEmpresa_contenido", tituloNombreEmpresa_contenido);
		
		
		String checkList_color = tblSiteStyleService.checkList_color(142);
		System.out.println("checkList_color " + checkList_color);
		model.addAttribute("checkList_color", checkList_color);
		
		String checkList_texto_color = tblSiteStyleService.checkList_texto_color(142);
		System.out.println("checkList_texto_color " + checkList_texto_color);
		model.addAttribute("checkList_texto_color", checkList_texto_color);
		
		String checkList_texto_fuente = tblSiteStyleService.checkList_texto_fuente(142);
		System.out.println("checkList_texto_fuente " + checkList_texto_fuente);
		model.addAttribute("checkList_texto_fuente", checkList_texto_fuente);
		
		String checkList_texto_1_contenido = tblSiteStyleService.checkList_texto_1_contenido(142);
		System.out.println("checkList_texto_1_contenido " + checkList_texto_1_contenido);
		model.addAttribute("checkList_texto_1_contenido", checkList_texto_1_contenido);
		
		String checkList_texto_2_contenido = tblSiteStyleService.checkList_texto_2_contenido(142);
		System.out.println("checkList_texto_2_contenido " + checkList_texto_2_contenido);
		model.addAttribute("checkList_texto_2_contenido", checkList_texto_2_contenido);
		
		String checkList_texto_3_contenido = tblSiteStyleService.checkList_texto_3_contenido(142);
		System.out.println("checkList_texto_3_contenido " + checkList_texto_3_contenido);
		model.addAttribute("checkList_texto_3_contenido", checkList_texto_3_contenido);
		
		String subtitulo_color = tblSiteStyleService.subtitulo_color(142);
		System.out.println("subtitulo_color " + subtitulo_color);
		model.addAttribute("subtitulo_color", subtitulo_color);
		
		String subtitulo_fuente = tblSiteStyleService.subtitulo_fuente(142);
		System.out.println("subtitulo_fuente " + subtitulo_fuente);
		model.addAttribute("subtitulo_fuente", subtitulo_fuente);
		
		String linkTexto_color = tblSiteStyleService.linkTexto_color(142);
		System.out.println("linkTexto_color " + linkTexto_color);
		model.addAttribute("linkTexto_color", linkTexto_color);
		
		String linkTexto_fuente = tblSiteStyleService.linkTexto_fuente(142);
		System.out.println("linkTexto_fuente " + linkTexto_fuente);
		model.addAttribute("linkTexto_fuente", linkTexto_fuente);
		
		String boton_color = tblSiteStyleService.boton_color(142);
		System.out.println("boton_color " + boton_color);
		model.addAttribute("boton_color", boton_color);
		
		String footer_color = tblSiteStyleService.footer_color(142);
		System.out.println("footer_color " + footer_color);
		model.addAttribute("footer_color", footer_color);
		
		String LetraFooter_color = tblSiteStyleService.LetraFooter_color(142);
		System.out.println("LetraFooter_color " + LetraFooter_color);
		model.addAttribute("LetraFooter_color", LetraFooter_color);
		
		String LetraFooter_fuente = tblSiteStyleService.LetraFooter_fuente(142);
		System.out.println("LetraFooter_fuente " + LetraFooter_fuente);
		model.addAttribute("LetraFooter_fuente", LetraFooter_fuente);
		
		String subtituloFooter_fuente = tblSiteStyleService.subtituloFooter_fuente(142);
		System.out.println("subtituloFooter_fuente " + subtituloFooter_fuente);
		model.addAttribute("subtituloFooter_fuente", subtituloFooter_fuente);
		
		String subtituloFooter_color = tblSiteStyleService.subtituloFooter_color(142);
		System.out.println("subtituloFooter_color " + subtituloFooter_color);
		model.addAttribute("subtituloFooter_color", subtituloFooter_color);
		
		String imgNotrosResumen_1_imagen = tblSiteStyleService.imgNotrosResumen_1_imagen(142);
		System.out.println("imgNotrosResumen_1_imagen " + imgNotrosResumen_1_imagen);
		model.addAttribute("imgNotrosResumen_1_imagen", imgNotrosResumen_1_imagen);
		
		String imgNotrosResumen_2_imagen = tblSiteStyleService.imgNotrosResumen_2_imagen(142);
		System.out.println("imgNotrosResumen_2_imagen " + imgNotrosResumen_2_imagen);
		model.addAttribute("imgNotrosResumen_2_imagen", imgNotrosResumen_2_imagen);
		
		String imgNotrosResumen_3_imagen = tblSiteStyleService.imgNotrosResumen_3_imagen(142);
		System.out.println("imgNotrosResumen_3_imagen " + imgNotrosResumen_3_imagen);
		model.addAttribute("imgNotrosResumen_3_imagen", imgNotrosResumen_3_imagen);
		
		String imgNoticia_1_imagen = tblSiteStyleService.imgNoticia_1_imagen(142);
		System.out.println("imgNoticia_1_imagen " + imgNoticia_1_imagen);
		model.addAttribute("imgNoticia_1_imagen", imgNoticia_1_imagen);
		
		String imgNoticia_2_imagen = tblSiteStyleService.imgNoticia_2_imagen(142);
		System.out.println("imgNoticia_2_imagen " + imgNoticia_2_imagen);
		model.addAttribute("imgNoticia_2_imagen", imgNoticia_2_imagen);
		
		String imgNoticia_3_imagen = tblSiteStyleService.imgNoticia_3_imagen(142);
		System.out.println("imgNoticia_3_imagen " + imgNoticia_3_imagen);
		model.addAttribute("imgNoticia_3_imagen", imgNoticia_3_imagen);
		
		String direccion_contenido = tblSiteStyleService.direccion_contenido(142);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblSiteStyleService.telefono_contenido(142);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblSiteStyleService.correo_contenido(142);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
	
		return "index";

	}
	

}
