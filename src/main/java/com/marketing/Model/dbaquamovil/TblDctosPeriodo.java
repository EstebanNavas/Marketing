package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tblDctosPeriodo")
@IdClass(TblDctosPeriodoPK.class)
public class TblDctosPeriodo {

	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name = "idPeriodo")
	private Integer idPeriodo;
	
	@Column(name = "nombrePeriodo")
	private String nombrePeriodo;
	
	@Column(name = "fechaInicial")
	private Timestamp  fechaInicial;
	
	@Column(name = "fechaFinal")
	private Timestamp  fechaFinal;
	
	@Column(name = "fechaSinRecargo")
	private Timestamp  fechaSinRecargo;
	
	@Column(name = "fechaConRecargo")
	private Timestamp  fechaConRecargo;
	
	@Column(name = "estadoPeriodo")
	private Integer estadoPeriodo;
	
	@Column(name = "estado")
	private Integer estado;
	
	@Column(name = "estadoEmail")
	private Integer estadoEmail;
	
	@Column(name = "estadoLecturaApp")
	private Integer estadoLecturaApp;
	
	@Column(name = "estadoFacturado")
	private Integer estadoFacturado;
	
	@Column(name = "textoPeriodo")
	private String textoPeriodo;
	
	@Column(name = "estadoSTR")
	private Integer estadoSTR;
	
	@Column(name = "estadoFEDctos")
	private Integer estadoFEDctos;
	
	@Column(name = "estadoFENotas")
	private Integer estadoFENotas;
	
	@Column(name = "estadoEnvioEmail")
	private Integer estadoEnvioEmail;
	
	

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getNombrePeriodo() {
		return nombrePeriodo;
	}

	public void setNombrePeriodo(String nombrePeriodo) {
		this.nombrePeriodo = nombrePeriodo;
	}

	public Integer getEstadoFEDctos() {
		return estadoFEDctos;
	}

	public void setEstadoFEDctos(Integer estadoFEDctos) {
		this.estadoFEDctos = estadoFEDctos;
	}

	public Timestamp getFechaInicial() {
		return fechaInicial;
	}
	
	public String getFechaInicialAAMMDD() {
		return getFechaInicial().toString().substring(0,10);
	}
	

	public void setFechaInicial(Timestamp fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Timestamp getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Timestamp fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	public String getFechaFinalAAMMDD() {
		return getFechaFinal().toString().substring(0,10);
	}	

	public Timestamp getFechaSinRecargo() {
		return fechaSinRecargo;
	}

	public void setFechaSinRecargo(Timestamp fechaSinRecargo) {
		this.fechaSinRecargo = fechaSinRecargo;
	}

	public Timestamp getFechaConRecargo() {
		return fechaConRecargo;
	}
	
	public String getFechaSinRecargoAAMMDD() {
		return getFechaSinRecargo().toString().substring(0,10);
	}	

	public void setFechaConRecargo(Timestamp fechaConRecargo) {
		this.fechaConRecargo = fechaConRecargo;
	}
	
	
	public String getFechaConRecargoAAMMDD() {
		return getFechaConRecargo().toString().substring(0,10);
	}	

	public Integer getEstadoPeriodo() {
		return estadoPeriodo;
	}

	public void setEstadoPeriodo(Integer estadoPeriodo) {
		this.estadoPeriodo = estadoPeriodo;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getEstadoEmail() {
		return estadoEmail;
	}

	public void setEstadoEmail(Integer estadoEmail) {
		this.estadoEmail = estadoEmail;
	}

	public Integer getEstadoLecturaApp() {
		return estadoLecturaApp;
	}

	public void setEstadoLecturaApp(Integer estadoLecturaApp) {
		this.estadoLecturaApp = estadoLecturaApp;
	}

	public Integer getEstadoFacturado() {
		return estadoFacturado;
	}

	public void setEstadoFacturado(Integer estadoFacturado) {
		this.estadoFacturado = estadoFacturado;
	}

	public String getTextoPeriodo() {
		return textoPeriodo;
	}

	public void setTextoPeriodo(String textoPeriodo) {
		this.textoPeriodo = textoPeriodo;
	}

	public Integer getEstadoSTR() {
		return estadoSTR;
	}

	public void setEstadoSTR(Integer estadoSTR) {
		this.estadoSTR = estadoSTR;
	}

	public Integer getEstadoFENotas() {
		return estadoFENotas;
	}

	public void setEstadoFENotas(Integer estadoFENotas) {
		this.estadoFENotas = estadoFENotas;
	}

	public Integer getEstadoEnvioEmail() {
		return estadoEnvioEmail;
	}

	public void setEstadoEnvioEmail(Integer estadoEnvioEmail) {
		this.estadoEnvioEmail = estadoEnvioEmail;
	}
	
	
}
