package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name= "tblPlus")
@IdClass(TblPlusPK.class)
public class TblPlus {

	@Id
	@Column(name= "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name= "idPlu")
	private Integer idPlu;
	
	@Column(name= "nombrePlu")
	private String nombrePlu;
	
	@Column(name= "vrGeneral")
	private Integer vrGeneral;
	
	@Column(name= "vrMayorista")
	private Integer vrMayorista;
	
	@Column(name= "porcentajeIva")
	private Integer porcentajeIva;
	
	@Column(name= "idTipo")
	private Integer idTipo;
	
	@Column(name= "idLinea")
	private Integer idLinea;
	
	@Column(name= "idUCompra")
	private Integer idUCompra;
	
	@Column(name= "idUVenta")
	private Integer idUVenta;
	
	@Column(name= "vrCosto")
	private Integer vrCosto;
	
	@Column(name= "idCategoria")
	private Integer idCategoria;
	
	@Column(name= "idMarca")
	private Integer idMarca;
	
	@Column(name= "vrSucursal")
	private Integer vrSucursal;
	
	@Column(name= "factorVenta")
	private Integer factorVenta;
	
	@Column(name= "factorDespacho")
	private Integer factorDespacho;
	
	@Column(name= "estado")
	private Integer estado;
	
	@Column(name= "idSeq")
	private Integer idSeq;
	
	@Column(name= "referencia")
	private String referencia;
	
	@Column(name= "vrImpoconsumo")
	private Integer vrImpoconsumo;
	
	@Column(name= "vrCostoIND")
	private Integer vrCostoIND;
	
	@Column(name= "idEstracto")
	private Integer idEstracto;
	
	@Column(name= "topeMaximo")
	private Integer topeMaximo;
	
	@Column(name= "aviso")
	private String aviso;
	
	@Column(name= "rangoMaximo")
	private Integer rangoMaximo;
	
	@Column(name= "codigoContable")
	private String codigoContable;
	
	@Column(name= "nombreContable")
	private String nombreContable;
	
	@Column(name= "cuentaContableDebito")
	private String cuentaContableDebito;
	
	@Column(name= "cuentaContableCredito")
	private String cuentaContableCredito;
	
	@Column(name= "cuentaRecaudoDebito")
	private String cuentaRecaudoDebito;
	
	@Column(name= "cuentaRecaudoCredito")
	private String cuentaRecaudoCredito;
	
	@Column(name= "idPluDeuda")
	private Integer idPluDeuda;
	
	@Column(name= "idApiNE")
	private Integer idApiNE;
	
	@Column(name= "idPluNE")
	private Integer idPluNE;
	

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdPlu() {
		return idPlu;
	}

	public void setIdPlu(Integer idPlu) {
		this.idPlu = idPlu;
	}

	public String getNombrePlu() {
		return nombrePlu;
	}

	public void setNombrePlu(String nombrePlu) {
		this.nombrePlu = nombrePlu;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public Integer getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Integer idLinea) {
		this.idLinea = idLinea;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Integer getVrGeneral() {
		return vrGeneral;
	}

	public void setVrGeneral(Integer vrGeneral) {
		this.vrGeneral = vrGeneral;
	}

	public Integer getVrMayorista() {
		return vrMayorista;
	}

	public void setVrMayorista(Integer vrMayorista) {
		this.vrMayorista = vrMayorista;
	}

	public Integer getPorcentajeIva() {
		return porcentajeIva;
	}

	public void setPorcentajeIva(Integer porcentajeIva) {
		this.porcentajeIva = porcentajeIva;
	}

	public Integer getIdUCompra() {
		return idUCompra;
	}

	public void setIdUCompra(Integer idUCompra) {
		this.idUCompra = idUCompra;
	}

	public Integer getIdUVenta() {
		return idUVenta;
	}

	public void setIdUVenta(Integer idUVenta) {
		this.idUVenta = idUVenta;
	}

	public Integer getVrCosto() {
		return vrCosto;
	}

	public void setVrCosto(Integer vrCosto) {
		this.vrCosto = vrCosto;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public Integer getVrSucursal() {
		return vrSucursal;
	}

	public void setVrSucursal(Integer vrSucursal) {
		this.vrSucursal = vrSucursal;
	}

	public Integer getFactorVenta() {
		return factorVenta;
	}

	public void setFactorVenta(Integer factorVenta) {
		this.factorVenta = factorVenta;
	}

	public Integer getFactorDespacho() {
		return factorDespacho;
	}

	public void setFactorDespacho(Integer factorDespacho) {
		this.factorDespacho = factorDespacho;
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

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getVrImpoconsumo() {
		return vrImpoconsumo;
	}

	public void setVrImpoconsumo(Integer vrImpoconsumo) {
		this.vrImpoconsumo = vrImpoconsumo;
	}

	public Integer getVrCostoIND() {
		return vrCostoIND;
	}

	public void setVrCostoIND(Integer vrCostoIND) {
		this.vrCostoIND = vrCostoIND;
	}

	public Integer getIdEstracto() {
		return idEstracto;
	}

	public void setIdEstracto(Integer idEstracto) {
		this.idEstracto = idEstracto;
	}

	public Integer getTopeMaximo() {
		return topeMaximo;
	}

	public void setTopeMaximo(Integer topeMaximo) {
		this.topeMaximo = topeMaximo;
	}

	public String getAviso() {
		return aviso;
	}

	public void setAviso(String aviso) {
		this.aviso = aviso;
	}

	public Integer getRangoMaximo() {
		return rangoMaximo;
	}

	public void setRangoMaximo(Integer rangoMaximo) {
		this.rangoMaximo = rangoMaximo;
	}

	public String getCodigoContable() {
		return codigoContable;
	}

	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}

	public String getNombreContable() {
		return nombreContable;
	}

	public void setNombreContable(String nombreContable) {
		this.nombreContable = nombreContable;
	}

	public String getCuentaContableDebito() {
		return cuentaContableDebito;
	}

	public void setCuentaContableDebito(String cuentaContableDebito) {
		this.cuentaContableDebito = cuentaContableDebito;
	}

	public String getCuentaContableCredito() {
		return cuentaContableCredito;
	}

	public void setCuentaContableCredito(String cuentaContableCredito) {
		this.cuentaContableCredito = cuentaContableCredito;
	}

	public String getCuentaRecaudoDebito() {
		return cuentaRecaudoDebito;
	}

	public void setCuentaRecaudoDebito(String cuentaRecaudoDebito) {
		this.cuentaRecaudoDebito = cuentaRecaudoDebito;
	}

	public String getCuentaRecaudoCredito() {
		return cuentaRecaudoCredito;
	}

	public void setCuentaRecaudoCredito(String cuentaRecaudoCredito) {
		this.cuentaRecaudoCredito = cuentaRecaudoCredito;
	}

	public Integer getIdPluDeuda() {
		return idPluDeuda;
	}

	public void setIdPluDeuda(Integer idPluDeuda) {
		this.idPluDeuda = idPluDeuda;
	}

	public Integer getIdApiNE() {
		return idApiNE;
	}

	public void setIdApiNE(Integer idApiNE) {
		this.idApiNE = idApiNE;
	}

	public Integer getIdPluNE() {
		return idPluNE;
	}

	public void setIdPluNE(Integer idPluNE) {
		this.idPluNE = idPluNE;
	}
	
	
	
}
