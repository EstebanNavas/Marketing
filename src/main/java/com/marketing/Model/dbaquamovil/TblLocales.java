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
	private String fechaResolucion;
	
	
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
	
	@Column(name = "pathImagen")
	private String pathImagen;
	
	@Column(name = "cuentaBanco")
	private String cuentaBanco;
	
	@Column(name = "periodoFactura")
	private Integer periodoFactura;
	
	@Column(name = "textoLegal")
	private String textoLegal;
	
	@Column(name = "estadoGeneraIAC")
	private Integer estadoGeneraIAC;
	
	
	@Column(name = "representanteLegal")
	private String representanteLegal;
	
	@Column(name = "textoEmail")
	private String textoEmail;
	
	
	@Column(name = "pathFileGral")
	private String pathFileGral;
	
	
	@Column(name = "txtSuspension")
	private String txtSuspension;
	
	
	@Column(name = "nitNE")
	private String nitNE;
	
	
	@Column(name = "cuentaRegistroTx")
	private Integer cuentaRegistroTx;
	
	
	

	public Integer getCuentaRegistroTx() {
		return cuentaRegistroTx;
	}

	public void setCuentaRegistroTx(Integer cuentaRegistroTx) {
		this.cuentaRegistroTx = cuentaRegistroTx;
	}

	public String getNitNE() {
		return nitNE;
	}

	public void setNitNE(String nitNE) {
		this.nitNE = nitNE;
	}

	public String getTxtSuspension() {
		return txtSuspension;
	}

	public void setTxtSuspension(String txtSuspension) {
		this.txtSuspension = txtSuspension;
	}

	public String getPathFileGral() {
		return pathFileGral;
	}

	public void setPathFileGral(String pathFileGral) {
		this.pathFileGral = pathFileGral;
	}

	public String getTextoEmail() {
		return textoEmail;
	}

	public void setTextoEmail(String textoEmail) {
		this.textoEmail = textoEmail;
	}

	public String getRepresentanteLegal() {
		return representanteLegal;
	}

	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	public Integer getEstadoGeneraIAC() {
		return estadoGeneraIAC;
	}

	public void setEstadoGeneraIAC(Integer estadoGeneraIAC) {
		this.estadoGeneraIAC = estadoGeneraIAC;
	}

	public String getTextoLegal() {
		return textoLegal;
	}

	public void setTextoLegal(String textoLegal) {
		this.textoLegal = textoLegal;
	}

	public String getCuentaBanco() {
		return cuentaBanco;
	}

	public void setCuentaBanco(String cuentaBanco) {
		this.cuentaBanco = cuentaBanco;
	}

	public String getPathImagen() {
		return pathImagen;
	}

	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}

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

	public String getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(String fechaResolucion) {
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
