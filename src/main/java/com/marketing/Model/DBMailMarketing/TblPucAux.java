package com.marketing.Model.DBMailMarketing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name = "TblPucAux")
@IdClass(TblPucAuxPK.class)
public class TblPucAux {
	
	@Id
	@Column(name= "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name= "idCuentaAux")
	private Integer idCuentaAux;
	
	@Column(name= "nombreCuenta")
	private String nombreCuenta;
	
	@Column(name= "ObligaTercero")
	private Integer ObligaTercero;
	
	@Column(name= "Iva")
	private Double Iva;
	
	@Column(name= "RteFuente")
	private Integer RteFuente;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdCuentaAux() {
		return idCuentaAux;
	}

	public void setIdCuentaAux(Integer idCuentaAux) {
		this.idCuentaAux = idCuentaAux;
	}

	public String getNombreCuenta() {
		return nombreCuenta;
	}

	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

	public Integer getObligaTercero() {
		return ObligaTercero;
	}

	public void setObligaTercero(Integer obligaTercero) {
		ObligaTercero = obligaTercero;
	}

	public Double getIva() {
		return Iva;
	}

	public void setIva(Double iva) {
		Iva = iva;
	}

	public Integer getRteFuente() {
		return RteFuente;
	}

	public void setRteFuente(Integer rteFuente) {
		RteFuente = rteFuente;
	}

}
