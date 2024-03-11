package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblAgendaEventoLogPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private String idCliente;
	private Integer idPeriodo;
	private Integer idEvento;
	
	public TblAgendaEventoLogPK() {
		super();
	}
	public TblAgendaEventoLogPK(Integer idlocal,  String idCliente, Integer idPeriodo, Integer idEvento) {
		super();
		this.idLocal = idlocal;
		this.idCliente = idCliente;
		this.idPeriodo = idPeriodo;
		this.idEvento = idEvento;
	}
	public Integer getIdLocal() {
		return idLocal;
	}
	public String getIdCliente() {
		return idCliente;
	}
	public Integer getIdPeriodo() {
		return idPeriodo;
	}
	
	public Integer getIdEvento() {
		return idEvento;
	}
	
	
	
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}
	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}
	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}
}
