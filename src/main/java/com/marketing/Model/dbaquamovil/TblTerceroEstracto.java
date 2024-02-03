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
	
	@Column(name="estado")
	private Integer estado;
	
	@Column(name="promedioEstrato")
	private Float promedioEstrato;
	
	
	@Column(name="codigoClaseUso")
	private Integer codigoClaseUso;
	
	@Column(name="idServicio")
	private Integer idServicio;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "terceroEstracto", cascade = CascadeType.ALL)// Se establece relacion uno a muchos con la tabla TblTerceros
	private List<TblTerceros> terceros;
	
    public TblTerceroEstracto() {
        // Constructor sin argumentos
    }

	public Integer getIdEstracto() {
		return idEstracto;
	}

	public void setIdEstracto(Integer idEstracto) {
		this.idEstracto = idEstracto;
	}

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public String getNombreEstracto() {
		return nombreEstracto;
	}

	public void setNombreEstracto(String nombreEstracto) {
		this.nombreEstracto = nombreEstracto;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Float getPromedioEstrato() {
		return promedioEstrato;
	}

	public void setPromedioEstrato(Float promedioEstrato) {
		this.promedioEstrato = promedioEstrato;
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

	public List<TblTerceros> getTerceros() {
		return terceros;
	}

	public void setTerceros(List<TblTerceros> terceros) {
		this.terceros = terceros;
	}

	
	public TblTerceroEstracto(Integer idEstracto) {
	    this.idEstracto = idEstracto;
	}
	
}
