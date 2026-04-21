package com.marketing.Model.dbaquamovil;

import java.io.Serializable;

public class TblAgendaLogSoportesPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer idLocal;
	
	
	private Double idUsuario;
	private String nombreReporte;
	private String fechaSolitud;
	
	public TblAgendaLogSoportesPK() {
		super();
	}
	public TblAgendaLogSoportesPK(Integer idlocal,  Double idUsuario, String nombreReporte, String fechaSolitud) {
		super();
		this.idLocal = idlocal;
		this.idUsuario = idUsuario;
		this.nombreReporte = nombreReporte;
		this.fechaSolitud = fechaSolitud;

	}
	public Integer getIdLocal() {
		return idLocal;
	}

	public Double getIdUsuario() {
		return idUsuario;
	}
	
	public String getNombreReporte() {
		return nombreReporte;
	}
	
	public String getFechaSolitud() {
		return fechaSolitud;
	}
	
	
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public void setIdUsuario(Double idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}
	
	public void setFechaSolitud(String fechaSolitud) {
		this.fechaSolitud = fechaSolitud;
	}

}
