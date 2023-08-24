package com.marketing.Model.dbaquamovil;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="tblTerceroEstracto")
public class TblTerceroEstracto {
	
	@Id
	@Column(name="idEstracto")
	private Integer idEstracto;
	
	
	
	@Column(name="idLocal")
	private Integer idLocal;
	
	
	
	@Column(name="nombreEstracto")
	private String nombreEstracto;
	
	@Column(name="promedioEstrato")
	private Float estado;
	
	@Column(name="codigoClaseUso")
	private Integer codigoClaseUso;
	
	@Column(name="idServicio")
	private Integer idServicio;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "terceroEstracto", cascade = CascadeType.ALL)// Se establece relacion uno a muchos con la tabla TblTerceros
	private List<TblTerceros> terceros;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdEstracto() {
		return idEstracto;
	}

	public void setIdEstracto(Integer idEstracto) {
		this.idEstracto = idEstracto;
	}

	public String getNombreEstracto() {
		return nombreEstracto;
	}

	public void setNombreEstracto(String nombreEstracto) {
		this.nombreEstracto = nombreEstracto;
	}

	public Float getEstado() {
		return estado;
	}

	public void setEstado(Float estado) {
		this.estado = estado;
	}

	public Integer getCodigoClaseUso() {
		return codigoClaseUso;
	}

	public void setCodigoClaseUso(Integer codigoClaseUso) {
		this.codigoClaseUso = codigoClaseUso;
	}

	public Integer getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(Integer idServicio) {
		this.idServicio = idServicio;
	}
	
	
}
