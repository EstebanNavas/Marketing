package com.marketing.Model.DBMailMarketing;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.marketing.Model.dbaquamovil.TblTerceros;

@Entity
@Table(name = "tblMailMarketingReporte")
public class TblMailMarketingReporte implements Serializable{
	
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

	@Column(name = "idCliente", columnDefinition = "nvarchar")
	private String idCliente;

	
//	@ManyToOne(fetch = FetchType.LAZY) // Establece la relación muchos a uno
//    @JoinColumn(name = "idCliente") // Columna que actúa como clave externa
//    private TblTerceros tercero; // Referencia a la entidad TblTerceros
//
//	
//
//	
//	public TblTerceros getTercero() {
//		return tercero;
//	}
//
//	public void setTercero(TblTerceros tercero) {
//		this.tercero = tercero;
//	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
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

//	@Override
//	public String toString() {
//		return "TblMailMarketingReporte [idReporte=" + idReporte + ", idLocal=" + idLocal + ", sistema=" + sistema
//				+ ", idCampaign=" + idCampaign + ", idPlantilla=" + idPlantilla + ", idDcto=" + idDcto
//				+ ", idRequerimiento=" + idRequerimiento + ", documentoTercero=" + documentoTercero + ", estado="
//				+ estado + ", descripcion=" + descripcion + ", fechaHoraEvento=" + fechaHoraEvento + ", exception="
//				+ exception + ", email=" + email + ", celular=" + celular 
//				+ ", tblMailCampaignCliente=" + tblMailCampaignCliente + "]";
//	}

	



	
	
	

}
