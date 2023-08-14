package com.marketing.Model.DBMailMarketing;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblMailMarketingReporte")
public class TblMailMarketingReporte {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDREPORTE")
	private Integer idReporte;
	
	
	@Column(name = "IDLOCAL")
	private Integer idLocal;
	
	@Column(name = "SISTEMA", columnDefinition = "nvarchar")
	private String sistema;
	
	@Column(name = "IDCAMPAIGN")
	private Integer idCampaign;
	
	@Column(name = "IDPLANTILLA")
	private Integer idPlantilla;
	
	@Column(name = "IDDCTO")
	private Integer idDcto;
	
	@Column(name = "IDREQUERIMIENTO")
	private Integer idRequerimiento;
	
	@Column(name = "DOCUMENTOTERCERO", columnDefinition = "nvarchar")
	private String documentoTercero;
	
	@Column(name = "ESTADO")
	private Integer estado;
	
	@Column(name = "DESCRIPCION", columnDefinition = "nvarchar")
	private String descripcion;
	
	@Column(name = "FECHAHORAEVENTO")
	private Timestamp  fechaHoraEvento;
	
	@Column(name = "EXCEPTION", columnDefinition = "nvarchar")
	private String exception;
	
	@Column(name = "EMAIL", columnDefinition = "nvarchar")
	private String email;
	
	@Column(name = "CELULAR", columnDefinition = "nvarchar")
	private String celular;

	public Integer getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}

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

	public Integer getIdPlantilla() {
		return idPlantilla;
	}

	public void setIdPlantilla(Integer idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public Integer getIdDcto() {
		return idDcto;
	}

	public void setIdDcto(Integer idDcto) {
		this.idDcto = idDcto;
	}

	public Integer getIdRequerimiento() {
		return idRequerimiento;
	}

	public void setIdRequerimiento(Integer idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
	}

	public String getDocumentoTercero() {
		return documentoTercero;
	}

	public void setDocumentoTercero(String documentoTercero) {
		this.documentoTercero = documentoTercero;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFechaHoraEvento() {
		return fechaHoraEvento;
	}

	public void setFechaHoraEvento(Timestamp fechaHoraEvento) {
		this.fechaHoraEvento = fechaHoraEvento;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	
	
	

}
