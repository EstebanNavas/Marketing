package com.marketing.Service.DBMailMarketing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.DBMailMarketing.TblSiteStyleRepo;

@Service
public class TblSiteStyleService {

	@Autowired
	TblSiteStyleRepo tblSiteStyleRepo;
	
	
	public String Navbar_color(int IDLOCAL) {

		String Navbar_color = tblSiteStyleRepo.Navbar_color(IDLOCAL);
		return Navbar_color;
	}

	public String LetraNavbar_color(int IDLOCAL) {

		String LetraNavbar_color = tblSiteStyleRepo.LetraNavbar_color(IDLOCAL);
		return LetraNavbar_color;
	}

	public String LetraNavbar_fuente(int IDLOCAL) {

		String LetraNavbar_fuente = tblSiteStyleRepo.LetraNavbar_fuente(IDLOCAL);
		return LetraNavbar_fuente;
	}
	
	public String botonCarrusel_color(int IDLOCAL) {

		String botonCarrusel_color = tblSiteStyleRepo.botonCarrusel_color(IDLOCAL);
		return botonCarrusel_color;
	}
	
	public String imgCarrusel_1_imagen(int IDLOCAL) {

		String imgCarrusel_1_imagen = tblSiteStyleRepo.imgCarrusel_1_imagen(IDLOCAL);
		return imgCarrusel_1_imagen;
	}
	
	public String imgCarrusel_2_imagen(int IDLOCAL) {

		String imgCarrusel_2_imagen = tblSiteStyleRepo.imgCarrusel_2_imagen(IDLOCAL);
		return imgCarrusel_2_imagen;
	}
	
	public String textCarrusel_1_color(int IDLOCAL) {

		String textCarrusel_1_color = tblSiteStyleRepo.textCarrusel_1_color(IDLOCAL);
		return textCarrusel_1_color;
	}
	
	public String textCarrusel_1_fuente(int IDLOCAL) {

		String textCarrusel_1_fuente = tblSiteStyleRepo.textCarrusel_1_fuente(IDLOCAL);
		return textCarrusel_1_fuente;
	}
	
	public String textCarrusel_2_color(int IDLOCAL) {

		String textCarrusel_2_color = tblSiteStyleRepo.textCarrusel_2_color(IDLOCAL);
		return textCarrusel_2_color;
	}
	
	public String textCarrusel_2_fuente(int IDLOCAL) {

		String textCarrusel_2_fuente = tblSiteStyleRepo.textCarrusel_2_fuente(IDLOCAL);
		return textCarrusel_2_fuente;
	}
	
	
	public String textCarrusel_1_contenido(int IDLOCAL) {

		String textCarrusel_1_contenido = tblSiteStyleRepo.textCarrusel_1_contenido(IDLOCAL);
		return textCarrusel_1_contenido;
	}
	
	public String textCarrusel_2_contenido(int IDLOCAL) {

		String textCarrusel_2_contenido = tblSiteStyleRepo.textCarrusel_2_contenido(IDLOCAL);
		return textCarrusel_2_contenido;
	}
	
	public String textCarrusel_3_contenido(int IDLOCAL) {

		String textCarrusel_3_contenido = tblSiteStyleRepo.textCarrusel_3_contenido(IDLOCAL);
		return textCarrusel_3_contenido;
	}

	
	public String titulo_color(int IDLOCAL) {

		String titulo_color = tblSiteStyleRepo.titulo_color(IDLOCAL);
		return titulo_color;
	}
	
	
	public String titulo_fuente(int IDLOCAL) {

		String titulo_fuente = tblSiteStyleRepo.titulo_fuente(IDLOCAL);
		return titulo_fuente;
	}
	
	public String nosotrosResumen_color(int IDLOCAL) {

		String nosotrosResumen_color = tblSiteStyleRepo.nosotrosResumen_color(IDLOCAL);
		return nosotrosResumen_color;
	}
	
	public String nosotrosResumen_fuente(int IDLOCAL) {

		String nosotrosResumen_fuente = tblSiteStyleRepo.nosotrosResumen_fuente(IDLOCAL);
		return nosotrosResumen_fuente;
	}

	public String nosotrosResumen_contenido(int IDLOCAL) {

		String nosotrosResumen_contenido = tblSiteStyleRepo.nosotrosResumen_contenido(IDLOCAL);
		return nosotrosResumen_contenido;
	}
	
	
	public String nosotrosTexto_color(int IDLOCAL) {

		String nosotrosTexto_color = tblSiteStyleRepo.nosotrosTexto_color(IDLOCAL);
		return nosotrosTexto_color;
	}
	
	public String nosotrosTexto_fuente(int IDLOCAL) {

		String nosotrosTexto_fuente = tblSiteStyleRepo.nosotrosTexto_fuente(IDLOCAL);
		return nosotrosTexto_fuente;
	}
	
	public String nosotrosTexto_contenido(int IDLOCAL) {

		String nosotrosTexto_contenido = tblSiteStyleRepo.nosotrosTexto_contenido(IDLOCAL);
		return nosotrosTexto_contenido;
	}
	
	public String tituloNombreEmpresa_color(int IDLOCAL) {

		String tituloNombreEmpresa_color = tblSiteStyleRepo.tituloNombreEmpresa_color(IDLOCAL);
		return tituloNombreEmpresa_color;
	}
	
	public String tituloNombreEmpresa_fuente(int IDLOCAL) {

		String tituloNombreEmpresa_fuente = tblSiteStyleRepo.tituloNombreEmpresa_fuente(IDLOCAL);
		return tituloNombreEmpresa_fuente;
	}
	
	public String tituloNombreEmpresa_contenido(int IDLOCAL) {

		String tituloNombreEmpresa_contenido = tblSiteStyleRepo.tituloNombreEmpresa_contenido(IDLOCAL);
		return tituloNombreEmpresa_contenido;
	}
	
	public String checkList_color(int IDLOCAL) {

		String checkList_color = tblSiteStyleRepo.checkList_color(IDLOCAL);
		return checkList_color;
	}
	
	public String checkList_texto_color(int IDLOCAL) {

		String checkList_texto_color = tblSiteStyleRepo.checkList_texto_color(IDLOCAL);
		return checkList_texto_color;
	}
	
	public String checkList_texto_fuente(int IDLOCAL) {

		String checkList_texto_fuente = tblSiteStyleRepo.checkList_texto_fuente(IDLOCAL);
		return checkList_texto_fuente;
	}
	
	public String checkList_texto_1_contenido(int IDLOCAL) {

		String checkList_texto_1_contenido = tblSiteStyleRepo.checkList_texto_1_contenido(IDLOCAL);
		return checkList_texto_1_contenido;
	}
	
	public String checkList_texto_2_contenido(int IDLOCAL) {

		String checkList_texto_2_contenido = tblSiteStyleRepo.checkList_texto_2_contenido(IDLOCAL);
		return checkList_texto_2_contenido;
	}
	
	public String checkList_texto_3_contenido(int IDLOCAL) {

		String checkList_texto_3_contenido = tblSiteStyleRepo.checkList_texto_3_contenido(IDLOCAL);
		return checkList_texto_3_contenido;
	}
	
	public String subtitulo_color(int IDLOCAL) {

		String subtitulo_color = tblSiteStyleRepo.subtitulo_color(IDLOCAL);
		return subtitulo_color;
	}
	
	public String subtitulo_fuente(int IDLOCAL) {

		String subtitulo_fuente = tblSiteStyleRepo.subtitulo_fuente(IDLOCAL);
		return subtitulo_fuente;
	}
	
	public String linkTexto_color(int IDLOCAL) {

		String linkTexto_color = tblSiteStyleRepo.linkTexto_color(IDLOCAL);
		return linkTexto_color;
	}
	
	public String linkTexto_fuente(int IDLOCAL) {

		String linkTexto_fuente = tblSiteStyleRepo.linkTexto_fuente(IDLOCAL);
		return linkTexto_fuente;
	}
	
	public String boton_color(int IDLOCAL) {

		String boton_color = tblSiteStyleRepo.boton_color(IDLOCAL);
		return boton_color;
	}
	
	public String footer_color(int IDLOCAL) {

		String footer_color = tblSiteStyleRepo.footer_color(IDLOCAL);
		return footer_color;
	}
	
	public String LetraFooter_color(int IDLOCAL) {

		String LetraFooter_color = tblSiteStyleRepo.LetraFooter_color(IDLOCAL);
		return LetraFooter_color;
	}
	
	public String LetraFooter_fuente(int IDLOCAL) {

		String LetraFooter_fuente = tblSiteStyleRepo.LetraFooter_fuente(IDLOCAL);
		return LetraFooter_fuente;
	}
	
	public String subtituloFooter_fuente(int IDLOCAL) {

		String subtituloFooter_fuente = tblSiteStyleRepo.subtituloFooter_fuente(IDLOCAL);
		return subtituloFooter_fuente;
	}
	
	public String subtituloFooter_color(int IDLOCAL) {

		String subtituloFooter_color = tblSiteStyleRepo.subtituloFooter_color(IDLOCAL);
		return subtituloFooter_color;
	}
	
	public String imgNotrosResumen_1_imagen(int IDLOCAL) {

		String imgNotrosResumen_1_imagen = tblSiteStyleRepo.imgNotrosResumen_1_imagen(IDLOCAL);
		return imgNotrosResumen_1_imagen;
	}
	
	public String imgNotrosResumen_2_imagen(int IDLOCAL) {

		String imgNotrosResumen_2_imagen = tblSiteStyleRepo.imgNotrosResumen_2_imagen(IDLOCAL);
		return imgNotrosResumen_2_imagen;
	}
	
	public String imgNotrosResumen_3_imagen(int IDLOCAL) {

		String imgNotrosResumen_3_imagen = tblSiteStyleRepo.imgNotrosResumen_3_imagen(IDLOCAL);
		return imgNotrosResumen_3_imagen;
	}
	
	public String imgNoticia_1_imagen(int IDLOCAL) {

		String imgNoticia_1_imagen = tblSiteStyleRepo.imgNoticia_1_imagen(IDLOCAL);
		return imgNoticia_1_imagen;
	}
	
	public String imgNoticia_2_imagen(int IDLOCAL) {

		String imgNoticia_2_imagen = tblSiteStyleRepo.imgNoticia_2_imagen(IDLOCAL);
		return imgNoticia_2_imagen;
	}
	
	public String imgNoticia_3_imagen(int IDLOCAL) {

		String imgNoticia_3_imagen = tblSiteStyleRepo.imgNoticia_3_imagen(IDLOCAL);
		return imgNoticia_3_imagen;
	}
	
	public String quienesSomosTexto_color(int IDLOCAL) {

		String quienesSomosTexto_color = tblSiteStyleRepo.quienesSomosTexto_color(IDLOCAL);
		return quienesSomosTexto_color;
	}
	
	public String quienesSomosTexto_fuente(int IDLOCAL) {

		String quienesSomosTexto_fuente = tblSiteStyleRepo.quienesSomosTexto_fuente(IDLOCAL);
		return quienesSomosTexto_fuente;
	}
	
	public String quienesSomosTexto_contenido(int IDLOCAL) {

		String quienesSomosTexto_contenido = tblSiteStyleRepo.quienesSomosTexto_contenido(IDLOCAL);
		return quienesSomosTexto_contenido;
	}
	
	public String misionTexto_color(int IDLOCAL) {

		String misionTexto_color = tblSiteStyleRepo.misionTexto_color(IDLOCAL);
		return misionTexto_color;
	}
	
	public String misionTexto_fuente(int IDLOCAL) {

		String misionTexto_fuente = tblSiteStyleRepo.misionTexto_fuente(IDLOCAL);
		return misionTexto_fuente;
	}
	
	public String misionTexto_contenido(int IDLOCAL) {

		String misionTexto_contenido = tblSiteStyleRepo.misionTexto_contenido(IDLOCAL);
		return misionTexto_contenido;
	}
	
	public String visionTexto_color(int IDLOCAL) {

		String visionTexto_color = tblSiteStyleRepo.visionTexto_color(IDLOCAL);
		return visionTexto_color;
	}
	
	public String visionTexto_fuente(int IDLOCAL) {

		String visionTexto_fuente = tblSiteStyleRepo.visionTexto_fuente(IDLOCAL);
		return visionTexto_fuente;
	}
	
	public String visionTexto_contenido(int IDLOCAL) {

		String visionTexto_contenido = tblSiteStyleRepo.visionTexto_contenido(IDLOCAL);
		return visionTexto_contenido;
	}
	
	public String imgQuienesSomos_1_imagen(int IDLOCAL) {

		String imgQuienesSomos_1_imagen = tblSiteStyleRepo.imgQuienesSomos_1_imagen(IDLOCAL);
		return imgQuienesSomos_1_imagen;
	}
	
	public String imgQuienesSomos_2_imagen(int IDLOCAL) {

		String imgQuienesSomos_2_imagen = tblSiteStyleRepo.imgQuienesSomos_2_imagen(IDLOCAL);
		return imgQuienesSomos_2_imagen;
	}
	
	public String imgQuienesSomos_3_imagen(int IDLOCAL) {

		String imgQuienesSomos_3_imagen = tblSiteStyleRepo.imgQuienesSomos_3_imagen(IDLOCAL);
		return imgQuienesSomos_3_imagen;
	}
	
	public String imgMision_1_imagen(int IDLOCAL) {

		String imgMision_1_imagen = tblSiteStyleRepo.imgMision_1_imagen(IDLOCAL);
		return imgMision_1_imagen;
	}
	
	public String imgMision_2_imagen(int IDLOCAL) {

		String imgMision_2_imagen = tblSiteStyleRepo.imgMision_2_imagen(IDLOCAL);
		return imgMision_2_imagen;
	}
	
	public String imgMision_3_imagen(int IDLOCAL) {

		String imgMision_3_imagen = tblSiteStyleRepo.imgMision_3_imagen(IDLOCAL);
		return imgMision_3_imagen;
	}
	
	public String imgVision_1_imagen(int IDLOCAL) {

		String imgVision_1_imagen = tblSiteStyleRepo.imgVision_1_imagen(IDLOCAL);
		return imgVision_1_imagen;
	}
	
	public String imgVision_2_imagen(int IDLOCAL) {

		String imgVision_2_imagen = tblSiteStyleRepo.imgVision_2_imagen(IDLOCAL);
		return imgVision_2_imagen;
	}
	
	public String imgVision_3_imagen(int IDLOCAL) {

		String imgVision_3_imagen = tblSiteStyleRepo.imgVision_3_imagen(IDLOCAL);
		return imgVision_3_imagen;
	}
	
	public String direccion_contenido(int IDLOCAL) {

		String direccion_contenido = tblSiteStyleRepo.direccion_contenido(IDLOCAL);
		return direccion_contenido;
	}
	
	public String telefono_contenido(int IDLOCAL) {

		String telefono_contenido = tblSiteStyleRepo.telefono_contenido(IDLOCAL);
		return telefono_contenido;
	}
	
	public String correo_contenido(int IDLOCAL) {

		String correo_contenido = tblSiteStyleRepo.correo_contenido(IDLOCAL);
		return correo_contenido;
	}


}
