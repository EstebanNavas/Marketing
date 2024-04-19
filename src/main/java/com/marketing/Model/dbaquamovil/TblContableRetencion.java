package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblContableRetencion")
public class TblContableRetencion {
	
	@Id
	@Column(name = "idConcepto")
	private Integer idConcepto;
	
	
	@Column(name = "nombreConcepto")
	private String nombreConcepto;
	
	
	@Column(name = "porcentajeRetencion")
	private Double porcentajeRetencion;
	
	@Column(name = "vrBaseRetencion")
	private Double vrBaseRetencion;
	
	@Column(name = "estado")
	private Integer estado;
	
	@Column(name = "idSeq")
	private Integer idSeq;

	public Integer getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(Integer idConcepto) {
		this.idConcepto = idConcepto;
	}

	public String getNombreConcepto() {
		return nombreConcepto;
	}

	public void setNombreConcepto(String nombreConcepto) {
		this.nombreConcepto = nombreConcepto;
	}

	public Double getPorcentajeRetencion() {
		return porcentajeRetencion;
	}

	public void setPorcentajeRetencion(Double porcentajeRetencion) {
		this.porcentajeRetencion = porcentajeRetencion;
	}

	public Double getVrBaseRetencion() {
		return vrBaseRetencion;
	}

	public void setVrBaseRetencion(Double vrBaseRetencion) {
		this.vrBaseRetencion = vrBaseRetencion;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getIdSeq() {
		return idSeq;
	}

	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}
	
	
	

}
