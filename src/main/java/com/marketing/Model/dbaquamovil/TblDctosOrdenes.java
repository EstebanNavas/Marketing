package com.marketing.Model.dbaquamovil;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblDctosOrdenes")
@IdClass(TblDctosOrdenesPK.class)
public class TblDctosOrdenes {

	@Id
	@Column(name= "IDLOCAL")
	private Integer IDLOCAL;
	
	@Id
	@Column(name= "IDTIPOORDEN")
	private Integer IDTIPOORDEN;
	
	@Id
	@Column(name= "IDORDEN")
	private Integer IDORDEN;
	
	@Column(name= "FECHAORDEN")
	private Timestamp FECHAORDEN;
	
	@Column(name= "ESTADO")
	private Integer ESTADO;
	
	@Column(name= "idCliente")
	private String idCliente;
	
	@Column(name= "IDUSUARIO")
	private Integer IDUSUARIO;
	
	@Column(name= "IDORIGEN")
	private Integer IDORIGEN;
	
	@Column(name= "IDLOG")
	private Integer IDLOG;
	
	@Column(name= "numeroOrden")
	private Integer numeroOrden;
	
	@Column(name= "FECHAENTREGA")
	private Timestamp FECHAENTREGA;
	
	@Column(name= "ordenCompra")
	private String ordenCompra;
	
	@Column(name= "TIPODCTO")
	private String TIPODCTO;
	
	@Column(name= "EMAIL")
	private String EMAIL;
	
	
	@Column(name= "FORMAPAGO")
	private String FORMAPAGO;
	
	
	@Column(name= "idRazon")
	private Integer idRazon;
	
	@Column(name= "idPeriodo")
	private Integer idPeriodo;
	
	

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public Integer getIdRazon() {
		return idRazon;
	}

	public void setIdRazon(Integer idRazon) {
		this.idRazon = idRazon;
	}

	public String getFORMAPAGO() {
		return FORMAPAGO;
	}

	public void setFORMAPAGO(String fORMAPAGO) {
		FORMAPAGO = fORMAPAGO;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getTIPODCTO() {
		return TIPODCTO;
	}

	public void setTIPODCTO(String tIPODCTO) {
		TIPODCTO = tIPODCTO;
	}

	public String getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public Timestamp getFECHAENTREGA() {
		return FECHAENTREGA;
	}

	public void setFECHAENTREGA(Timestamp fECHAENTREGA) {
		FECHAENTREGA = fECHAENTREGA;
	}

	public Integer getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(Integer numeroOrden) {
		this.numeroOrden = numeroOrden;
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

	public Timestamp getFECHAORDEN() {
		return FECHAORDEN;
	}

	public void setFECHAORDEN(Timestamp fECHAORDEN) {
		FECHAORDEN = fECHAORDEN;
	}

	public Integer getESTADO() {
		return ESTADO;
	}

	public void setESTADO(Integer eSTADO) {
		ESTADO = eSTADO;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIDUSUARIO() {
		return IDUSUARIO;
	}

	public void setIDUSUARIO(Integer iDUSUARIO) {
		IDUSUARIO = iDUSUARIO;
	}

	public Integer getIDORIGEN() {
		return IDORIGEN;
	}

	public void setIDORIGEN(Integer iDORIGEN) {
		IDORIGEN = iDORIGEN;
	}

	public Integer getIDLOG() {
		return IDLOG;
	}

	public void setIDLOG(Integer iDLOG) {
		IDLOG = iDLOG;
	}
	
	

}
