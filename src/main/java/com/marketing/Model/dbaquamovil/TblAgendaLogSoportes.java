package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblAgendaLogSoportes")
@IdClass(TblAgendaLogSoportesPK.class)
public class TblAgendaLogSoportes {
	
	@Id
	@Column(name= "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name= "idUsuario")
	private Double idUsuario;
	
	@Id
	@Column(name= "nombreReporte")
	private String nombreReporte;
	
	@Id
	@Column(name= "fechaSolitud")
	private String fechaSolitud;
	
	@Column(name= "ruta")
	private String ruta;
	
	@Column(name= "estado")
	private Integer estado;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Double getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Double idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreReporte() {
		return nombreReporte;
	}

	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}

	public String getFechaSolitud() {
		return fechaSolitud;
	}

	public void setFechaSolitud(String fechaSolitud) {
		this.fechaSolitud = fechaSolitud;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	
	

}
