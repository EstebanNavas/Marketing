package com.marketing.Model.DBMailMarketing;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblMailCampaign")
public class MailCampaign {

	@Id
	@Column(name = "IDCAMPAIGN")
	private Integer idCampaign;
	
	
	@Column(name = "IDLOCAL")
	private Integer idLocal;
	
	@Column(name = "SISTEMA", columnDefinition = "nvarchar")
	private String sistema;
	
	
	@Column(name = "NOMBRECAMPAIGN", columnDefinition = "nvarchar")
	private String NombreCampaign;
	
	@Column(name = "PERIODICIDAD", columnDefinition = "nvarchar")
	private String periodicidad;
	
	@Column(name = "IDPLANTILLA")
	private Integer idPlantilla;
	
	@Column(name = "fechaHora")
	private Timestamp fechaYhora;
	
	@Column(name = "TEXTOMENSAJE", columnDefinition = "nvarchar")
	private String textoMensaje;
	
	@Column(name = "textoSMS", columnDefinition = "nvarchar")
	private String textoSMS;
	
	@Column(name = "SUBJECT", columnDefinition = "nvarchar")
	private String subject;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public Integer getIdCampaign() {
		return idCampaign;
	}

	public void setIdCampaign(Integer idCampaign) {
		this.idCampaign = idCampaign;
	}

	public String getNombreCampaign() {
		return NombreCampaign;
	}

	public void setNombreCampaign(String nombreCampaign) {
		NombreCampaign = nombreCampaign;
	}

	public String getPeriodicidad() {
		return periodicidad;
	}

	public void setPeriodicidad(String periodicidad) {
		this.periodicidad = periodicidad;
	}

	public Integer getIdPlantilla() {
		return idPlantilla;
	}

	public void setIdPlantilla(Integer idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public Timestamp getFechaYhora() {
		return fechaYhora;
	}

	public void setFechaYhora(Timestamp timestamp) {
		this.fechaYhora = timestamp;
	}

	public String getTextoMensaje() {
		return textoMensaje;
	}

	public void setTextoMensaje(String textoMensaje) {
		this.textoMensaje = textoMensaje;
	}

	public String getTextoSMS() {
		return textoSMS;
	}

	public void setTextoSMS(String textoSMS) {
		this.textoSMS = textoSMS;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
}
