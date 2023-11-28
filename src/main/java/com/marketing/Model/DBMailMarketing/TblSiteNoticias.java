package com.marketing.Model.DBMailMarketing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblSiteNoticias")
public class TblSiteNoticias {

	@Id
	@Column(name="idNoticia")
	private Integer idNoticia;
	
	@Column(name="IDLOCAL")
	private Integer IDLOCAL;
	
	@Column(name="sistema")
	private String sistema;
	
	@Column(name="encabezado")
	private String encabezado;
	
	@Column(name="contenido")
	private String contenido;
	
	@Column(name="imagen")
	private String imagen;
	
	@Column(name="video")
	private String video;

	public Integer getIdNoticia() {
		return idNoticia;
	}

	public void setIdNoticia(Integer idNoticia) {
		this.idNoticia = idNoticia;
	}

	public Integer getIDLOCAL() {
		return IDLOCAL;
	}

	public void setIDLOCAL(Integer iDLOCAL) {
		IDLOCAL = iDLOCAL;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(String encabezado) {
		this.encabezado = encabezado;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	
	
}
