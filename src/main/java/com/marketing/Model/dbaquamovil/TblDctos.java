package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblDctos")
@IdClass(TblDctosPK.class)
public class TblDctos {

	@Id
	@Column(name= "IDLOCAL")
	private Integer IDLOCAL;
	
	@Id
	@Column(name= "IDTIPOORDEN")
	private Integer IDTIPOORDEN;
	
	@Id
	@Column(name= "IDORDEN")
	private Integer IDORDEN;
	
	@Id
	@Column(name= "idDcto")
	private Integer idDcto;
	
	@Id
	@Column(name= "indicador")
	private Integer indicador;
	
	
	@Column(name= "idCliente")
	private String idCliente;
	
	@Column(name= "fechaDcto")
	private Timestamp fechaDcto;
	
	@Column(name= "idEstado")
	private Integer idEstado;
	
	@Column(name= "IDUSUARIO")
	private Integer IDUSUARIO;
	
	@Column(name= "fechaDctoNitCC")
	private String fechaDctoNitCC;
	
	

	public String getFechaDctoNitCC() {
		return fechaDctoNitCC;
	}

	public void setFechaDctoNitCC(String fechaDctoNitCC) {
		this.fechaDctoNitCC = fechaDctoNitCC;
	}

	public Integer getIDUSUARIO() {
		return IDUSUARIO;
	}

	public void setIDUSUARIO(Integer iDUSUARIO) {
		IDUSUARIO = iDUSUARIO;
	}

	public Timestamp getFechaDcto() {
		return fechaDcto;
	}

	public void setFechaDcto(Timestamp fechaDcto) {
		this.fechaDcto = fechaDcto;
	}

	public Integer getIDLOCAL() {
		return IDLOCAL;
	}

	public void setIDLOCAL(Integer iDLOCAL) {
		IDLOCAL = iDLOCAL;
	}

	public Integer getIDTIPOORDEN() {
		return IDTIPOORDEN;
	}

	public void setIDTIPOORDEN(Integer iDTIPOORDEN) {
		IDTIPOORDEN = iDTIPOORDEN;
	}

	public Integer getIDORDEN() {
		return IDORDEN;
	}

	public void setIDORDEN(Integer iDORDEN) {
		IDORDEN = iDORDEN;
	}

	public Integer getIdDcto() {
		return idDcto;
	}

	public void setIdDcto(Integer idDcto) {
		this.idDcto = idDcto;
	}

	public Integer getIndicador() {
		return indicador;
	}

	public void setIndicador(Integer indicador) {
		this.indicador = indicador;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	
	
	
}











