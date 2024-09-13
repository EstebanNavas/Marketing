package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblTipoOrdenSubcuenta")
public class TblTipoOrdenSubcuenta {
	
	@Column(name= "idTipoOrden")
	private Integer idTipoOrden;
	
	
	@Id
	@Column(name= "idSubcuenta")
	private String idSubcuenta;
	
	
	@Column(name= "estado")
	private Integer estado;
	
	@Column(name= "idSeq")
	private Integer idSeq;
	
	@Column(name= "idAsiento")
	private Integer idAsiento;
	
	@Column(name= "porcentajeIva")
	private float porcentajeIva;
	
	@Column(name= "idComprobante")
	private Integer idComprobante;
	
	@Column(name= "nombreSubcuenta")
	private String nombreSubcuenta;

	public Integer getIdTipoOrden() {
		return idTipoOrden;
	}

	public void setIdTipoOrden(Integer idTipoOrden) {
		this.idTipoOrden = idTipoOrden;
	}

	public String getIdSubcuenta() {
		return idSubcuenta;
	}

	public void setIdSubcuenta(String idSubcuenta) {
		this.idSubcuenta = idSubcuenta;
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

	public Integer getIdAsiento() {
		return idAsiento;
	}

	public void setIdAsiento(Integer idAsiento) {
		this.idAsiento = idAsiento;
	}

	public float getPorcentajeIva() {
		return porcentajeIva;
	}

	public void setPorcentajeIva(float porcentajeIva) {
		this.porcentajeIva = porcentajeIva;
	}

	public Integer getIdComprobante() {
		return idComprobante;
	}

	public void setIdComprobante(Integer idComprobante) {
		this.idComprobante = idComprobante;
	}

	public String getNombreSubcuenta() {
		return nombreSubcuenta;
	}

	public void setNombreSubcuenta(String nombreSubcuenta) {
		this.nombreSubcuenta = nombreSubcuenta;
	}
	
	
	

}
