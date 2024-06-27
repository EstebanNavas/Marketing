package com.marketing.Service.DBMailMarketing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.marketing.Model.DBMailMarketing.TblEstilosSite;
import com.marketing.Repository.DBMailMarketing.TblEstilosSiteRepo;

@Service
public class TblEstilosSiteService {
	
	@Autowired
	TblEstilosSiteRepo tblEstilosSiteRepo;
	
	public String Navbar_color(int IDLOCAL) {

		String Navbar_color = tblEstilosSiteRepo.Navbar_color(IDLOCAL);
		return Navbar_color;
	}

	public String LetraNavbar_color(int IDLOCAL) {

		String LetraNavbar_color = tblEstilosSiteRepo.LetraNavbar_color(IDLOCAL);
		return LetraNavbar_color;
	}

	public String LetraNavbar_fuente(int IDLOCAL) {

		String LetraNavbar_fuente = tblEstilosSiteRepo.LetraNavbar_fuente(IDLOCAL);
		return LetraNavbar_fuente;
	}
	
	public String botonCarrusel_color(int IDLOCAL) {

		String botonCarrusel_color = tblEstilosSiteRepo.botonCarrusel_color(IDLOCAL);
		return botonCarrusel_color;
	}
	
	public String imgCarrusel_1_imagen(int IDLOCAL) {

		String imgCarrusel_1_imagen = tblEstilosSiteRepo.imgCarrusel_1_imagen(IDLOCAL);
		return imgCarrusel_1_imagen;
	}
	
	public String imgCarrusel_2_imagen(int IDLOCAL) {

		String imgCarrusel_2_imagen = tblEstilosSiteRepo.imgCarrusel_2_imagen(IDLOCAL);
		return imgCarrusel_2_imagen;
	}
	
	public String imgCarrusel_3_imagen(int IDLOCAL) {

		String imgCarrusel_3_imagen = tblEstilosSiteRepo.imgCarrusel_3_imagen(IDLOCAL);
		return imgCarrusel_3_imagen;
	}
	
	public String textCarrusel_1_color(int IDLOCAL) {

		String textCarrusel_1_color = tblEstilosSiteRepo.textCarrusel_1_color(IDLOCAL);
		return textCarrusel_1_color;
	}
	
	public String textCarrusel_1_fuente(int IDLOCAL) {

		String textCarrusel_1_fuente = tblEstilosSiteRepo.textCarrusel_1_fuente(IDLOCAL);
		return textCarrusel_1_fuente;
	}
	
	public String textCarrusel_2_color(int IDLOCAL) {

		String textCarrusel_2_color = tblEstilosSiteRepo.textCarrusel_2_color(IDLOCAL);
		return textCarrusel_2_color;
	}
	
	public String textCarrusel_2_fuente(int IDLOCAL) {

		String textCarrusel_2_fuente = tblEstilosSiteRepo.textCarrusel_2_fuente(IDLOCAL);
		return textCarrusel_2_fuente;
	}
	
	
	public String textCarrusel_1_contenido(int IDLOCAL) {

		String textCarrusel_1_contenido = tblEstilosSiteRepo.textCarrusel_1_contenido(IDLOCAL);
		return textCarrusel_1_contenido;
	}
	
	public String textCarrusel_2_contenido(int IDLOCAL) {

		String textCarrusel_2_contenido = tblEstilosSiteRepo.textCarrusel_2_contenido(IDLOCAL);
		return textCarrusel_2_contenido;
	}
	
	public String textCarrusel_3_contenido(int IDLOCAL) {

		String textCarrusel_3_contenido = tblEstilosSiteRepo.textCarrusel_3_contenido(IDLOCAL);
		return textCarrusel_3_contenido;
	}

	
	public String titulo_color(int IDLOCAL) {

		String titulo_color = tblEstilosSiteRepo.titulo_color(IDLOCAL);
		return titulo_color;
	}
	
	
	public String titulo_fuente(int IDLOCAL) {

		String titulo_fuente = tblEstilosSiteRepo.titulo_fuente(IDLOCAL);
		return titulo_fuente;
	}
	
	public String nosotrosResumen_color(int IDLOCAL) {

		String nosotrosResumen_color = tblEstilosSiteRepo.nosotrosResumen_color(IDLOCAL);
		return nosotrosResumen_color;
	}
	
	public String nosotrosResumen_fuente(int IDLOCAL) {

		String nosotrosResumen_fuente = tblEstilosSiteRepo.nosotrosResumen_fuente(IDLOCAL);
		return nosotrosResumen_fuente;
	}

	public String nosotrosResumen_contenido(int IDLOCAL) {

		String nosotrosResumen_contenido = tblEstilosSiteRepo.nosotrosResumen_contenido(IDLOCAL);
		return nosotrosResumen_contenido;
	}
	
	
	public String nosotrosTexto_color(int IDLOCAL) {

		String nosotrosTexto_color = tblEstilosSiteRepo.nosotrosTexto_color(IDLOCAL);
		return nosotrosTexto_color;
	}
	
	public String nosotrosTexto_fuente(int IDLOCAL) {

		String nosotrosTexto_fuente = tblEstilosSiteRepo.nosotrosTexto_fuente(IDLOCAL);
		return nosotrosTexto_fuente;
	}
	
	public String nosotrosTexto_contenido(int IDLOCAL) {

		String nosotrosTexto_contenido = tblEstilosSiteRepo.nosotrosTexto_contenido(IDLOCAL);
		return nosotrosTexto_contenido;
	}
	
	public String tituloNombreEmpresa_color(int IDLOCAL) {

		String tituloNombreEmpresa_color = tblEstilosSiteRepo.tituloNombreEmpresa_color(IDLOCAL);
		return tituloNombreEmpresa_color;
	}
	
	public String tituloNombreEmpresa_fuente(int IDLOCAL) {

		String tituloNombreEmpresa_fuente = tblEstilosSiteRepo.tituloNombreEmpresa_fuente(IDLOCAL);
		return tituloNombreEmpresa_fuente;
	}
	
	public String tituloNombreEmpresa_contenido(int IDLOCAL) {

		String tituloNombreEmpresa_contenido = tblEstilosSiteRepo.tituloNombreEmpresa_contenido(IDLOCAL);
		return tituloNombreEmpresa_contenido;
	}
	
	public String checkList_color(int IDLOCAL) {

		String checkList_color = tblEstilosSiteRepo.checkList_color(IDLOCAL);
		return checkList_color;
	}
	
	public String checkList_texto_color(int IDLOCAL) {

		String checkList_texto_color = tblEstilosSiteRepo.checkList_texto_color(IDLOCAL);
		return checkList_texto_color;
	}
	
	public String checkList_texto_fuente(int IDLOCAL) {

		String checkList_texto_fuente = tblEstilosSiteRepo.checkList_texto_fuente(IDLOCAL);
		return checkList_texto_fuente;
	}
	
	public String checkList_texto_1_contenido(int IDLOCAL) {

		String checkList_texto_1_contenido = tblEstilosSiteRepo.checkList_texto_1_contenido(IDLOCAL);
		return checkList_texto_1_contenido;
	}
	
	public String checkList_texto_2_contenido(int IDLOCAL) {

		String checkList_texto_2_contenido = tblEstilosSiteRepo.checkList_texto_2_contenido(IDLOCAL);
		return checkList_texto_2_contenido;
	}
	
	public String checkList_texto_3_contenido(int IDLOCAL) {

		String checkList_texto_3_contenido = tblEstilosSiteRepo.checkList_texto_3_contenido(IDLOCAL);
		return checkList_texto_3_contenido;
	}
	
	public String subtitulo_color(int IDLOCAL) {

		String subtitulo_color = tblEstilosSiteRepo.subtitulo_color(IDLOCAL);
		return subtitulo_color;
	}
	
	public String subtitulo_fuente(int IDLOCAL) {

		String subtitulo_fuente = tblEstilosSiteRepo.subtitulo_fuente(IDLOCAL);
		return subtitulo_fuente;
	}
	
	public String linkTexto_color(int IDLOCAL) {

		String linkTexto_color = tblEstilosSiteRepo.linkTexto_color(IDLOCAL);
		return linkTexto_color;
	}
	
	public String linkTexto_fuente(int IDLOCAL) {

		String linkTexto_fuente = tblEstilosSiteRepo.linkTexto_fuente(IDLOCAL);
		return linkTexto_fuente;
	}
	
	public String boton_color(int IDLOCAL) {

		String boton_color = tblEstilosSiteRepo.boton_color(IDLOCAL);
		return boton_color;
	}
	
	public String footer_color(int IDLOCAL) {

		String footer_color = tblEstilosSiteRepo.footer_color(IDLOCAL);
		return footer_color;
	}
	
	public String LetraFooter_color(int IDLOCAL) {

		String LetraFooter_color = tblEstilosSiteRepo.LetraFooter_color(IDLOCAL);
		return LetraFooter_color;
	}
	
	public String LetraFooter_fuente(int IDLOCAL) {

		String LetraFooter_fuente = tblEstilosSiteRepo.LetraFooter_fuente(IDLOCAL);
		return LetraFooter_fuente;
	}
	
	public String subtituloFooter_fuente(int IDLOCAL) {

		String subtituloFooter_fuente = tblEstilosSiteRepo.subtituloFooter_fuente(IDLOCAL);
		return subtituloFooter_fuente;
	}
	
	public String subtituloFooter_color(int IDLOCAL) {

		String subtituloFooter_color = tblEstilosSiteRepo.subtituloFooter_color(IDLOCAL);
		return subtituloFooter_color;
	}
	
	public String imgNotrosResumen_1_imagen(int IDLOCAL) {

		String imgNotrosResumen_1_imagen = tblEstilosSiteRepo.imgNotrosResumen_1_imagen(IDLOCAL);
		return imgNotrosResumen_1_imagen;
	}
	
	public String imgNotrosResumen_2_imagen(int IDLOCAL) {

		String imgNotrosResumen_2_imagen = tblEstilosSiteRepo.imgNotrosResumen_2_imagen(IDLOCAL);
		return imgNotrosResumen_2_imagen;
	}
	
	public String imgNotrosResumen_3_imagen(int IDLOCAL) {

		String imgNotrosResumen_3_imagen = tblEstilosSiteRepo.imgNotrosResumen_3_imagen(IDLOCAL);
		return imgNotrosResumen_3_imagen;
	}
	
	public String imgNoticia_1_imagen(int IDLOCAL) {

		String imgNoticia_1_imagen = tblEstilosSiteRepo.imgNoticia_1_imagen(IDLOCAL);
		return imgNoticia_1_imagen;
	}
	
	public String imgNoticia_2_imagen(int IDLOCAL) {

		String imgNoticia_2_imagen = tblEstilosSiteRepo.imgNoticia_2_imagen(IDLOCAL);
		return imgNoticia_2_imagen;
	}
	
	public String imgNoticia_3_imagen(int IDLOCAL) {

		String imgNoticia_3_imagen = tblEstilosSiteRepo.imgNoticia_3_imagen(IDLOCAL);
		return imgNoticia_3_imagen;
	}
	
	public String quienesSomosTexto_color(int IDLOCAL) {

		String quienesSomosTexto_color = tblEstilosSiteRepo.quienesSomosTexto_color(IDLOCAL);
		return quienesSomosTexto_color;
	}
	
	public String quienesSomosTexto_fuente(int IDLOCAL) {

		String quienesSomosTexto_fuente = tblEstilosSiteRepo.quienesSomosTexto_fuente(IDLOCAL);
		return quienesSomosTexto_fuente;
	}
	
	public String quienesSomosTexto_contenido(int IDLOCAL) {

		String quienesSomosTexto_contenido = tblEstilosSiteRepo.quienesSomosTexto_contenido(IDLOCAL);
		return quienesSomosTexto_contenido;
	}
	
	public String misionTexto_color(int IDLOCAL) {

		String misionTexto_color = tblEstilosSiteRepo.misionTexto_color(IDLOCAL);
		return misionTexto_color;
	}
	
	public String misionTexto_fuente(int IDLOCAL) {

		String misionTexto_fuente = tblEstilosSiteRepo.misionTexto_fuente(IDLOCAL);
		return misionTexto_fuente;
	}
	
	public String misionTexto_contenido(int IDLOCAL) {

		String misionTexto_contenido = tblEstilosSiteRepo.misionTexto_contenido(IDLOCAL);
		return misionTexto_contenido;
	}
	
	public String visionTexto_color(int IDLOCAL) {

		String visionTexto_color = tblEstilosSiteRepo.visionTexto_color(IDLOCAL);
		return visionTexto_color;
	}
	
	public String visionTexto_fuente(int IDLOCAL) {

		String visionTexto_fuente = tblEstilosSiteRepo.visionTexto_fuente(IDLOCAL);
		return visionTexto_fuente;
	}
	
	public String visionTexto_contenido(int IDLOCAL) {

		String visionTexto_contenido = tblEstilosSiteRepo.visionTexto_contenido(IDLOCAL);
		return visionTexto_contenido;
	}
	
	public String historiaTexto_color(int IDLOCAL) {

		String historiaTexto_color = tblEstilosSiteRepo.historiaTexto_color(IDLOCAL);
		return historiaTexto_color;
	}
	
	public String historiaTexto_fuente(int IDLOCAL) {

		String historiaTexto_fuente = tblEstilosSiteRepo.historiaTexto_fuente(IDLOCAL);
		return historiaTexto_fuente;
	}
	
	public String historiaTexto_contenido(int IDLOCAL) {

		String historiaTexto_contenido = tblEstilosSiteRepo.historiaTexto_contenido(IDLOCAL);
		return historiaTexto_contenido;
	}
	
	public String objetivoTexto_color(int IDLOCAL) {

		String objetivoTexto_color = tblEstilosSiteRepo.objetivoTexto_color(IDLOCAL);
		return objetivoTexto_color;
	}
	
	public String objetivoTexto_fuente(int IDLOCAL) {

		String objetivoTexto_fuente = tblEstilosSiteRepo.objetivoTexto_fuente(IDLOCAL);
		return objetivoTexto_fuente;
	}
	
	public String objetivoTexto_contenido(int IDLOCAL) {

		String objetivoTexto_contenido = tblEstilosSiteRepo.objetivoTexto_contenido(IDLOCAL);
		return objetivoTexto_contenido;
	}
	
	public String imgQuienesSomos_1_imagen(int IDLOCAL) {

		String imgQuienesSomos_1_imagen = tblEstilosSiteRepo.imgQuienesSomos_1_imagen(IDLOCAL);
		return imgQuienesSomos_1_imagen;
	}
	
	public String imgQuienesSomos_2_imagen(int IDLOCAL) {

		String imgQuienesSomos_2_imagen = tblEstilosSiteRepo.imgQuienesSomos_2_imagen(IDLOCAL);
		return imgQuienesSomos_2_imagen;
	}
	
	public String imgQuienesSomos_3_imagen(int IDLOCAL) {

		String imgQuienesSomos_3_imagen = tblEstilosSiteRepo.imgQuienesSomos_3_imagen(IDLOCAL);
		return imgQuienesSomos_3_imagen;
	}
	
	public String imgMision_1_imagen(int IDLOCAL) {

		String imgMision_1_imagen = tblEstilosSiteRepo.imgMision_1_imagen(IDLOCAL);
		return imgMision_1_imagen;
	}
	
	public String imgMision_2_imagen(int IDLOCAL) {

		String imgMision_2_imagen = tblEstilosSiteRepo.imgMision_2_imagen(IDLOCAL);
		return imgMision_2_imagen;
	}
	
	public String imgMision_3_imagen(int IDLOCAL) {

		String imgMision_3_imagen = tblEstilosSiteRepo.imgMision_3_imagen(IDLOCAL);
		return imgMision_3_imagen;
	}
	
	public String imgVision_1_imagen(int IDLOCAL) {

		String imgVision_1_imagen = tblEstilosSiteRepo.imgVision_1_imagen(IDLOCAL);
		return imgVision_1_imagen;
	}
	
	public String imgVision_2_imagen(int IDLOCAL) {

		String imgVision_2_imagen = tblEstilosSiteRepo.imgVision_2_imagen(IDLOCAL);
		return imgVision_2_imagen;
	}
	
	public String imgVision_3_imagen(int IDLOCAL) {

		String imgVision_3_imagen = tblEstilosSiteRepo.imgVision_3_imagen(IDLOCAL);
		return imgVision_3_imagen;
	}
	
	public String direccion_contenido(int IDLOCAL) {

		String direccion_contenido = tblEstilosSiteRepo.direccion_contenido(IDLOCAL);
		return direccion_contenido;
	}
	
	public String telefono_contenido(int IDLOCAL) {

		String telefono_contenido = tblEstilosSiteRepo.telefono_contenido(IDLOCAL);
		return telefono_contenido;
	}
	
	public String correo_contenido(int IDLOCAL) {

		String correo_contenido = tblEstilosSiteRepo.correo_contenido(IDLOCAL);
		return correo_contenido;
	}
	
	public String icono_color(int IDLOCAL) {

		String icono_color = tblEstilosSiteRepo.icono_color(IDLOCAL);
		return icono_color;
	}
	
	public String videoQuienesSomos_video(int IDLOCAL) {

		String videoQuienesSomos_video = tblEstilosSiteRepo.videoQuienesSomos_video(IDLOCAL);
		System.out.println("videoQuienesSomos_video" + videoQuienesSomos_video);
		return videoQuienesSomos_video;
	}
	
	public String videoMision_video(int IDLOCAL) {

		String videoMision_video = tblEstilosSiteRepo.videoMision_video(IDLOCAL);
		return videoMision_video;
	}
	
	public String videoVision_video(int IDLOCAL) {

		String videoVision_video = tblEstilosSiteRepo.videoVision_video(IDLOCAL);
		return videoVision_video;
	}
	
	public String imgHistoria_1_imagen(int IDLOCAL) {

		String imgHistoria_1_imagen = tblEstilosSiteRepo.imgHistoria_1_imagen(IDLOCAL);
		return imgHistoria_1_imagen;
	}
	
	public String imgHistoria_2_imagen(int IDLOCAL) {

		String imgHistoria_2_imagen = tblEstilosSiteRepo.imgHistoria_2_imagen(IDLOCAL);
		return imgHistoria_2_imagen;
	}
	
	public String imgHistoria_3_imagen(int IDLOCAL) {

		String imgHistoria_3_imagen = tblEstilosSiteRepo.imgHistoria_3_imagen(IDLOCAL);
		return imgHistoria_3_imagen;
	}
	
	public String imgObjetivo_imagen(int IDLOCAL) {

		String imgObjetivo_imagen = tblEstilosSiteRepo.imgObjetivo_imagen(IDLOCAL);
		return imgObjetivo_imagen;
	}
	
	public String puntoDePago_1_contenido(int IDLOCAL) {

		String puntoDePago_1_contenido = tblEstilosSiteRepo.puntoDePago_1_contenido(IDLOCAL);
		return puntoDePago_1_contenido;
	}
	
	public String puntoDePago_2_contenido(int IDLOCAL) {

		String puntoDePago_2_contenido = tblEstilosSiteRepo.puntoDePago_2_contenido(IDLOCAL);
		return puntoDePago_2_contenido;
	}
	
	public String puntoDePago_3_contenido(int IDLOCAL) {

		String puntoDePago_3_contenido = tblEstilosSiteRepo.puntoDePago_3_contenido(IDLOCAL);
		return puntoDePago_3_contenido;
	}
	
	public String puntoDePago_4_contenido(int IDLOCAL) {

		String puntoDePago_4_contenido = tblEstilosSiteRepo.puntoDePago_4_contenido(IDLOCAL);
		return puntoDePago_4_contenido;
	}
	
	
	public List<TblEstilosSite> listaPuntosDePago (int IDLOCAL){
		
		List<TblEstilosSite> listaPago = tblEstilosSiteRepo.listaPuntosDePago(IDLOCAL);
		
		return listaPago;
		
	}
	
	public String  GoogleMaps(int IDLOCAL) {
		
		String maps = tblEstilosSiteRepo.GoogleMaps(IDLOCAL);
		
		return maps;
	}
	


}
