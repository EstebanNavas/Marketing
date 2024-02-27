package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblLocales")
public class TblLocales {

	@Id
	@Column(name = "IDLOCAL")
	private Integer idLocal;
	
	@Column(name = "RAZONSOCIAL", columnDefinition = "nvarchar")
	private String razonSocial;
	
	@Column(name = "nombreLocal")
	private String nombreLocal;
	
	@Column(name = "estado")
	private Float estado;
	
	@Column(name = "nit")
	private String nit;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "fax")
	private String fax;
	
	@Column(name = "regimen")
	private String regimen;
	
	@Column(name = "resolucion")
	private String resolucion;
	
	@Column(name = "fechaResolucion")
	private Timestamp fechaResolucion;
	
	
	@Column(name = "ciudad")
	private String ciudad;
	
	@Column(name = "txtFactura")
	private String txtFactura;
	
	@Column(name = "rango")
	private String rango;
	
	@Column(name = "prefijo")
	private String prefijo;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "margenDisponible")
	private Float margenDisponible;
	
	@Column(name = "valorDisponible")
	private Integer valorDisponible;
	
	@Column(name = "pathReport")
	private String pathReport;
	
	@Column(name = "periodoFactura")
	private Integer periodoFactura;
	
	

	public Float getEstado() {
		return estado;
	}

	public void setEstado(Float estado) {
		this.estado = estado;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getRegimen() {
		return regimen;
	}

	public void setRegimen(String regimen) {
		this.regimen = regimen;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public Timestamp getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(Timestamp fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getTxtFactura() {
		return txtFactura;
	}

	public void setTxtFactura(String txtFactura) {
		this.txtFactura = txtFactura;
	}

	public String getRango() {
		return rango;
	}

	public void setRango(String rango) {
		this.rango = rango;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Float getMargenDisponible() {
		return margenDisponible;
	}

	public void setMargenDisponible(Float margenDisponible) {
		this.margenDisponible = margenDisponible;
	}

	public Integer getValorDisponible() {
		return valorDisponible;
	}

	public void setValorDisponible(Integer valorDisponible) {
		this.valorDisponible = valorDisponible;
	}

	public Integer getPeriodoFactura() {
		return periodoFactura;
	}

	public void setPeriodoFactura(Integer periodoFactura) {
		this.periodoFactura = periodoFactura;
	}

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreLocal() {
		return nombreLocal;
	}

	public void setNombreLocal(String nombreLocal) {
		this.nombreLocal = nombreLocal;
	}

	
	public String getPathReport() {
		return pathReport;
	}

	public void setPathReport(String pathReport) {
		this.pathReport = pathReport;
	}


}
