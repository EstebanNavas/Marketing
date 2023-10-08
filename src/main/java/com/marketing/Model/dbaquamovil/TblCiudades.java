package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblCiudades")
public class TblCiudades {

	@Id
	@Column(name = "idCiudad")
	private Integer idCiudad;
	
	@Column(name = "nombreCiudad")
	private String nombreCiudad;
	
	@Column(name = "idDpto")
	private Integer idDpto;
	
	@Column(name = "nombreDpto")
	private String nombreDpto;
	
	@Column(name = "estado")
	private Integer estado;
	
	@Column(name = "idSeq")
	private Integer idSeq;
	
	@Column(name = "id")
	private Integer id;

	public Integer getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Integer idCiudad) {
		this.idCiudad = idCiudad;
	}

	public String getNombreCiudad() {
		return nombreCiudad;
	}

	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}

	public Integer getIdDpto() {
		return idDpto;
	}

	public void setIdDpto(Integer idDpto) {
		this.idDpto = idDpto;
	}

	public String getNombreDpto() {
		return nombreDpto;
	}

	public void setNombreDpto(String nombreDpto) {
		this.nombreDpto = nombreDpto;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getIdSeq() {
		return idSeq;
	}

	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
