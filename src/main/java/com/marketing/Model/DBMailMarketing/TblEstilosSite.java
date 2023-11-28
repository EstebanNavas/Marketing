package com.marketing.Model.DBMailMarketing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblEstilosSite")
public class TblEstilosSite {

	@Id
	@Column(name="idStyle" )
	private Integer idStyle;
	
	
	@Column(name="IDLOCAL" )
	private Integer IDLOCAL;
	
	@Column(name="sistema" )
	private String sistema;
	
	@Column(name="campo" )
	private String campo;
	
	
	@Column(name="valor" )
	private String valor;
	
	@Column(name="tipo" )
	private String tipo;

	public Integer getIdStyle() {
		return idStyle;
	}

	public void setIdStyle(Integer idStyle) {
		this.idStyle = idStyle;
	}

	public Integer getIDLOCAL() {
		return IDLOCAL;
	}

	public void setIDLOCAL(Integer iDLOCAL) {
		IDLOCAL = iDLOCAL;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
