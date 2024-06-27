package com.marketing.Controller;

import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.marketing.Model.DBMailMarketing.TblNoticiasSite;
import com.marketing.Model.DBMailMarketing.TblSiteNoticias;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Service.DBMailMarketing.TblEstilosSiteService;
import com.marketing.Service.DBMailMarketing.TblNoticiasSiteService;
import com.marketing.Service.DBMailMarketing.TblSiteNoticiasService;
import com.marketing.Service.DBMailMarketing.TblSiteStyleService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class InicioController {
	
	@Autowired
	TblSiteStyleService tblSiteStyleService;
	
	@Autowired
	TblNoticiasSiteService tblNoticiasSiteService;
	
	@Autowired
	TblEstilosSiteService tblEstilosSiteService;
	
	@Autowired
	TblSiteNoticiasService tblSiteNoticiasService;
	
	int idLocal = 0;
	//Integer xIdLocal = 0;

	@GetMapping("/")
	public String inicio(HttpServletRequest request, Model model) {
		System.out.println("Si entro al controllador");
		
	    // Obtenemos el valor del xIdLocal almacenado en la sesión
	    HttpSession session = request.getSession();
	    session.removeAttribute("xIdLocal"); // Eliminamos de la session cualquier valor de xIdLocal
	    
	    Integer xIdLocal = (Integer) session.getAttribute("xIdLocal");
	    System.out.println("El xIdLocal sin gaurdar es:  " + xIdLocal);

	    // Si xIdLocal no está en la sesión, obtenemos el valor del parámetro "xIdLocal" de la URL
	    if (xIdLocal == null) {
	        String xIdLocalParametro = request.getParameter("xIdLocal");
	        System.out.println("El xIdLocal es null " + xIdLocal);
	        System.out.println("El xIdLocalParametro  " + xIdLocalParametro);
	        try {
	        	idLocal = Integer.parseInt(xIdLocalParametro);
	            // Guardamos en la sesión el idLocal
	            session.setAttribute("idLocal", idLocal);
	            System.out.println("El xIdLocal GUARDADO EN LA SESIÓN ES: " + idLocal);
	        } catch (NumberFormatException e) {
	            System.out.println("El parámetro xIdLocal no es un número válido.");
	            
	            
	        }
	    }
		
		
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
		
		//CARRUSEL
		String botonCarrusel_color = tblEstilosSiteService.botonCarrusel_color(idLocal);
		System.out.println("botonCarrusel_color " + botonCarrusel_color);
		model.addAttribute("botonCarrusel_color", botonCarrusel_color);
		
		String imgCarrusel_1_imagen = tblEstilosSiteService.imgCarrusel_1_imagen(idLocal);
		System.out.println("imgCarrusel_1_imagen " + imgCarrusel_1_imagen);
		model.addAttribute("imgCarrusel_1_imagen", imgCarrusel_1_imagen);
		
		String imgCarrusel_2_imagen = tblEstilosSiteService.imgCarrusel_2_imagen(idLocal);
		System.out.println("imgCarrusel_2_imagen " + imgCarrusel_2_imagen);
		model.addAttribute("imgCarrusel_2_imagen", imgCarrusel_2_imagen);
		
		String imgCarrusel_3_imagen = tblEstilosSiteService.imgCarrusel_3_imagen(idLocal);
		System.out.println("imgCarrusel_3_imagen " + imgCarrusel_3_imagen);
		model.addAttribute("imgCarrusel_3_imagen", imgCarrusel_3_imagen);

		String textCarrusel_1_color = tblEstilosSiteService.textCarrusel_1_color(idLocal);
		System.out.println("textCarrusel_1_color " + textCarrusel_1_color);
		model.addAttribute("textCarrusel_1_color", textCarrusel_1_color);

		String textCarrusel_1_fuente = tblEstilosSiteService.textCarrusel_1_fuente(idLocal);
		System.out.println("textCarrusel_1_fuente " + textCarrusel_1_fuente);
		model.addAttribute("textCarrusel_1_fuente", textCarrusel_1_fuente);
		

		String textCarrusel_2_color = tblEstilosSiteService.textCarrusel_2_color(idLocal);
		System.out.println("textCarrusel_2_color " + textCarrusel_2_color);
		model.addAttribute("textCarrusel_2_color", textCarrusel_2_color);

		String textCarrusel_2_fuente = tblEstilosSiteService.textCarrusel_2_fuente(idLocal);
		System.out.println("textCarrusel_2_fuente " + textCarrusel_2_fuente);
		model.addAttribute("textCarrusel_2_fuente", textCarrusel_2_fuente);
		
		String textCarrusel_1_contenido = tblEstilosSiteService.textCarrusel_1_contenido(idLocal);
		System.out.println("textCarrusel_1_contenido " + textCarrusel_1_contenido);
		model.addAttribute("textCarrusel_1_contenido", textCarrusel_1_contenido);

		String textCarrusel_2_contenido = tblEstilosSiteService.textCarrusel_2_contenido(idLocal);
		System.out.println("textCarrusel_2_contenido " + textCarrusel_2_contenido);
		model.addAttribute("textCarrusel_2_contenido", textCarrusel_2_contenido);
		
		String textCarrusel_3_contenido = tblEstilosSiteService.textCarrusel_3_contenido(idLocal);
		System.out.println("textCarrusel_3_contenido " + textCarrusel_3_contenido);
		model.addAttribute("textCarrusel_3_contenido", textCarrusel_3_contenido);
		
		
		//NOSOTROS
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
		
		String direccion_contenido = tblEstilosSiteService.direccion_contenido(idLocal);
		System.out.println("direccion_contenido " + direccion_contenido);
		model.addAttribute("direccion_contenido", direccion_contenido);
		
		String telefono_contenido = tblEstilosSiteService.telefono_contenido(idLocal);
		System.out.println("telefono_contenido " + telefono_contenido);
		model.addAttribute("telefono_contenido", telefono_contenido);
		
		String correo_contenido = tblEstilosSiteService.correo_contenido(idLocal);
		System.out.println("correo_contenido " + correo_contenido);
		model.addAttribute("correo_contenido", correo_contenido);
		
		String icono_color = tblEstilosSiteService.icono_color(idLocal);
		System.out.println("icono_color " + icono_color);
		model.addAttribute("icono_color", icono_color);
		
		List<TblSiteNoticias> Noticias = tblSiteNoticiasService.Noticias(idLocal);
		System.out.println("Noticias " + Noticias);
		model.addAttribute("Noticias", Noticias);
		
	
		return "index";

	}
	

}
