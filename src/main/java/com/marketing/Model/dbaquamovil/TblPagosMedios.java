package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tblPagosMedios")
@IdClass(TblPagosMediosPK.class)
public class TblPagosMedios {

	
	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name = "idTipoOrden")
	private Integer idTipoOrden;
	
	
	@Id
	@Column(name = "idRecibo")
	private Integer idRecibo;
	
	
	@Id
	@Column(name = "indicador")
	private Integer indicador;
	
	
	@Id
	@Column(name = "idMedio")
	private Integer idMedio;
	
	
	@Column(name = "vrMedio")
	private Float vrMedio;
	
	
	@Column(name = "fechaCobro")
	private Timestamp  fechaCobro;
	
	
	@Column(name = "nombrePeriodo")
	private String nombrePeriodo;
	
	@Column(name = "idBanco")
	private Integer idBanco;
	
	
	@Id
	@Column(name = "idDctoMedio")
	private Integer idDctoMedio;
	
	
	@Column(name = "estado")
	private Integer estado;
	
	
	@Column(name = "idLog")
	private Double idLog;
	
	
	@Id
	@Column(name = "IDPLU")
	private Integer IDPLU;


	public Integer getIdLocal() {
		return idLocal;
	}


	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}


	public Integer getIdTipoOrden() {
		return idTipoOrden;
	}


	public void setIdTipoOrden(Integer idTipoOrden) {
		this.idTipoOrden = idTipoOrden;
	}


	public Integer getIdRecibo() {
		return idRecibo;
	}


	public void setIdRecibo(Integer idRecibo) {
		this.idRecibo = idRecibo;
	}


	public Integer getIndicador() {
		return indicador;
	}


	public void setIndicador(Integer indicador) {
		this.indicador = indicador;
	}


	public Integer getIdMedio() {
		return idMedio;
	}


	public void setIdMedio(Integer idMedio) {
		this.idMedio = idMedio;
	}


	public Float getVrMedio() {
		return vrMedio;
	}


	public void setVrMedio(Float vrMedio) {
		this.vrMedio = vrMedio;
	}


	public Timestamp getFechaCobro() {
		return fechaCobro;
	}


	public void setFechaCobro(Timestamp fechaCobro) {
		this.fechaCobro = fechaCobro;
	}


	public String getNombrePeriodo() {
		return nombrePeriodo;
	}


	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}


	public Integer getIdBanco() {
		return idBanco;
	}


	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}


	public Integer getIdDctoMedio() {
		return idDctoMedio;
	}


	public void setIdDctoMedio(Integer idDctoMedio) {
		this.idDctoMedio = idDctoMedio;
	}


	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	public Double getIdLog() {
		return idLog;
	}


	public void setIdLog(Double idLog) {
		this.idLog = idLog;
	}


	public Integer getIDPLU() {
		return IDPLU;
	}


	public void setIDPLU(Integer iDPLU) {
		IDPLU = iDPLU;
	}
	
	
	
	
	
	
	
	
	
	
}
