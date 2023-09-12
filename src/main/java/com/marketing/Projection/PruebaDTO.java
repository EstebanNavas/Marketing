package com.marketing.Projection;

import java.io.Serializable;
import java.sql.Timestamp;

import com.marketing.Model.DBMailMarketing.TblMailCampaignCliente;

public class PruebaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer idReporte;
	Integer idLocal;
	String sistema;
	Integer idCampaign;
	Integer idPlantilla;
	Integer idDcto;
	Integer idRequerimiento;
	String documentoTercero;
	Integer estado;
	String descripcion;
	Timestamp fechaHoraEvento;
	String exception;
	String email;
	String celular;
	
	//TblMailCampaignCliente
	
	String  idCliente;
	
	

	public PruebaDTO() {
		super();
	}
	
	

	public PruebaDTO(Integer idReporte, Integer idLocal, String sistema) {
		super();
		this.idReporte = idReporte;
		this.idLocal = idLocal;
		this.sistema = sistema;
		//this.idCliente = idCliente;
	}



	public PruebaDTO(Integer idReporte, Integer idLocal, String sistema, Integer idCampaign, Integer idPlantilla,
			Integer idDcto, Integer idRequerimiento, String documentoTercero, Integer estado, String descripcion,
			Timestamp fechaHoraEvento, String exception, String email, String celular, String idCliente) {
		super();
		this.idReporte = idReporte;
		this.idLocal = idLocal;
		this.sistema = sistema;
		this.idCampaign = idCampaign;
		this.idPlantilla = idPlantilla;
		this.idDcto = idDcto;
		this.idRequerimiento = idRequerimiento;
		this.documentoTercero = documentoTercero;
		this.estado = estado;
		this.descripcion = descripcion;
		this.fechaHoraEvento = fechaHoraEvento;
		this.exception = exception;
		this.email = email;
		this.celular = celular;
		this.idCliente = idCliente;
	}

	public String getIdCLiente() {
		return idCliente;
	}

	public void setIdCLiente(String idCLiente) {
		this.idCliente = idCLiente;
	}

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



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "PruebaDTO [reporte=" + idReporte + ", idLocal=" + idLocal + ", sistema=" + sistema + ", idCampaign="
				+ idCampaign + ", idPlantilla=" + idPlantilla + ", idDcto=" + idDcto + ", idRequerimiento="
				+ idRequerimiento + ", documentoTercero=" + documentoTercero + ", estado=" + estado + ", descripcion="
				+ descripcion + ", fechaHoraEvento=" + fechaHoraEvento + ", exception=" + exception + ", email=" + email
				+ ", celular=" + celular + ", tblMailCampaignCliente=" + idCliente + "]";
	}
	
	
	
	

}
