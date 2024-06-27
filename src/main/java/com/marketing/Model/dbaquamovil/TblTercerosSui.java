package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.marketing.Model.dbaquamovil.TblTercerosSuiPK;

@Entity
@Table(name="tblTercerosSui")
@IdClass(TblTercerosSuiPK.class)
public class TblTercerosSui {
	
	@Id
	@Column(name="idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name="idCliente")
	private String idCliente;
	
	
	@Column(name ="idTipoTercero")
	private Integer idTipoTercero;
	
	@Column(name ="estado")
	private Integer estado;
	
	@Column(name ="idIgacZona")
	private Integer idIgacZona;
	
	@Column(name ="idIgacSector")
	private Integer idIgacSector;
	
	@Column(name ="idIgacVereda")
	private Integer idIgacVereda;
	
	@Column(name ="idIgacPredio")
	private Integer idIgacPredio;
	
	@Column(name ="idIgacCondPredio")
	private Integer idIgacCondPredio;
	
	@Column(name ="idUMultiusuario")
	private Integer idUMultiusuario;
	
	@Column(name ="idUNoMultiusuario")
	private Integer idUNoMultiusuario;
	
	@Column(name ="idHogarComunitario")
	private Integer idHogarComunitario;
	
	@Column(name ="estadoMedidor")
	private Integer estadoMedidor;
	
	@Column(name ="idTipoLectura")
	private Integer idTipoLectura;
	
	@Column(name ="vrCostoMediaTasaUso")
	private float vrCostoMediaTasaUso;
	
	@Column(name ="idCausaRefactura")
	private Integer idCausaRefactura;
	
	@Column(name ="dctoRefactura")
	private Integer dctoRefactura;
	
	@Column(name ="numeroFicha")
	private Integer numeroFicha;
	
	@Column(name ="corregimiento")
	private Integer corregimiento;
	
	@Column(name ="barrio")
	private Integer barrio;
	
	@Column(name ="edificio")
	private Integer edificio;
	
	@Column(name ="unidadPredial")
	private Integer unidadPredial;
	
	@Column(name ="usuFactAforo")
	private Integer usuFactAforo;
	
	@Column(name ="usuCuentaCaracterizacion")
	private Integer usuCuentaCaracterizacion;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdTipoTercero() {
		return idTipoTercero;
	}

	public void setIdTipoTercero(Integer idTipoTercero) {
		this.idTipoTercero = idTipoTercero;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getIdIgacZona() {
		return idIgacZona;
	}

	public void setIdIgacZona(Integer idIgacZona) {
		this.idIgacZona = idIgacZona;
	}

	public Integer getIdIgacSector() {
		return idIgacSector;
	}

	public void setIdIgacSector(Integer idIgacSector) {
		this.idIgacSector = idIgacSector;
	}

	public Integer getIdIgacVereda() {
		return idIgacVereda;
	}

	public void setIdIgacVereda(Integer idIgacVereda) {
		this.idIgacVereda = idIgacVereda;
	}

	public Integer getIdIgacPredio() {
		return idIgacPredio;
	}

	public void setIdIgacPredio(Integer idIgacPredio) {
		this.idIgacPredio = idIgacPredio;
	}

	public Integer getIdIgacCondPredio() {
		return idIgacCondPredio;
	}

	public void setIdIgacCondPredio(Integer idIgacCondPredio) {
		this.idIgacCondPredio = idIgacCondPredio;
	}

	public Integer getIdUMultiusuario() {
		return idUMultiusuario;
	}

	public void setIdUMultiusuario(Integer idUMultiusuario) {
		this.idUMultiusuario = idUMultiusuario;
	}

	public Integer getIdUNoMultiusuario() {
		return idUNoMultiusuario;
	}

	public void setIdUNoMultiusuario(Integer idUNoMultiusuario) {
		this.idUNoMultiusuario = idUNoMultiusuario;
	}

	public Integer getIdHogarComunitario() {
		return idHogarComunitario;
	}

	public void setIdHogarComunitario(Integer idHogarComunitario) {
		this.idHogarComunitario = idHogarComunitario;
	}

	public Integer getEstadoMedidor() {
		return estadoMedidor;
	}

	public void setEstadoMedidor(Integer estadoMedidor) {
		this.estadoMedidor = estadoMedidor;
	}

	public Integer getIdTipoLectura() {
		return idTipoLectura;
	}

	public void setIdTipoLectura(Integer idTipoLectura) {
		this.idTipoLectura = idTipoLectura;
	}

	public float getVrCostoMediaTasaUso() {
		return vrCostoMediaTasaUso;
	}

	public void setVrCostoMediaTasaUso(float vrCostoMediaTasaUso) {
		this.vrCostoMediaTasaUso = vrCostoMediaTasaUso;
	}

	public Integer getIdCausaRefactura() {
		return idCausaRefactura;
	}

	public void setIdCausaRefactura(Integer idCausaRefactura) {
		this.idCausaRefactura = idCausaRefactura;
	}

	public Integer getDctoRefactura() {
		return dctoRefactura;
	}

	public void setDctoRefactura(Integer dctoRefactura) {
		this.dctoRefactura = dctoRefactura;
	}

	public Integer getNumeroFicha() {
		return numeroFicha;
	}

	public void setNumeroFicha(Integer numeroFicha) {
		this.numeroFicha = numeroFicha;
	}

	public Integer getCorregimiento() {
		return corregimiento;
	}

	public void setCorregimiento(Integer corregimiento) {
		this.corregimiento = corregimiento;
	}

	public Integer getBarrio() {
		return barrio;
	}

	public void setBarrio(Integer barrio) {
		this.barrio = barrio;
	}

	public Integer getEdificio() {
		return edificio;
	}

	public void setEdificio(Integer edificio) {
		this.edificio = edificio;
	}

	public Integer getUnidadPredial() {
		return unidadPredial;
	}

	public void setUnidadPredial(Integer unidadPredial) {
		this.unidadPredial = unidadPredial;
	}

	public Integer getUsuFactAforo() {
		return usuFactAforo;
	}

	public void setUsuFactAforo(Integer usuFactAforo) {
		this.usuFactAforo = usuFactAforo;
	}

	public Integer getUsuCuentaCaracterizacion() {
		return usuCuentaCaracterizacion;
	}

	public void setUsuCuentaCaracterizacion(Integer usuCuentaCaracterizacion) {
		this.usuCuentaCaracterizacion = usuCuentaCaracterizacion;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
