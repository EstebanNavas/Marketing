package com.marketing.Model.DBMailMarketing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblNoticiasSite")
public class TblNoticiasSite {
	
	@Id
	@Column(name="IdNoticia")
	private Integer IdNoticia;
	
	@Column(name="IDLOCAL")
	private Integer IDLOCAL;
	
	@Column(name="sistema")
	private String sistema;
	
	@Column(name="encabezado")
	private String encabezado;
	
	@Column(name="contenido")
	private String contenido;
	
	@Column(name="imagen_1")
	private String imagen_1;
	
	@Column(name="imagen_2")
	private String imagen_2;
	
	@Column(name="imagen_3")
	private String imagen_3;
	
	@Column(name="video")
	private String video;

	public Integer getIdNoticia() {
		return IdNoticia;
	}

	public void setIdNoticia(Integer idNoticia) {
		IdNoticia = idNoticia;
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

	public String getImagen_1() {
		return imagen_1;
	}

	public void setImagen_1(String imagen_1) {
		this.imagen_1 = imagen_1;
	}

	public String getImagen_2() {
		return imagen_2;
	}

	public void setImagen_2(String imagen_2) {
		this.imagen_2 = imagen_2;
	}

	public String getImagen_3() {
		return imagen_3;
	}

	public void setImagen_3(String imagen_3) {
		this.imagen_3 = imagen_3;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	
	

}
