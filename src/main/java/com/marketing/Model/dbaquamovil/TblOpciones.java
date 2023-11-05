package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblOpciones")
@IdClass(TblOpcionesPK.class)
public class TblOpciones {
	
	@Id
	@Column(name = "idOpcion")
	private Integer idOpcion;
	
	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Column(name = "nombreOpcion")
	private String nombreOpcion;
	
	@Column(name = "rutaOpcion")
	private String rutaOpcion;
	
	@Column(name = "idTipoOpcion")
	private Integer idTipoOpcion;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "idOpcionPadre")
	private Integer idOpcionPadre;
	
	@Column(name = "estadoSTR")
	private Integer estadoSTR;

	public Integer getIdOpcion() {
		return idOpcion;
	}

	public void setIdOpcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public String getNombreOpcion() {
		return nombreOpcion;
	}

	public void setNombreOpcion(String nombreOpcion) {
		this.nombreOpcion = nombreOpcion;
	}

	public String getRutaOpcion() {
		return rutaOpcion;
	}

	public void setRutaOpcion(String rutaOpcion) {
		this.rutaOpcion = rutaOpcion;
	}

	public Integer getIdTipoOpcion() {
		return idTipoOpcion;
	}

	public void setIdTipoOpcion(Integer idTipoOpcion) {
		this.idTipoOpcion = idTipoOpcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getIdOpcionPadre() {
		return idOpcionPadre;
	}

	public void setIdOpcionPadre(Integer idOpcionPadre) {
		this.idOpcionPadre = idOpcionPadre;
	}

	public Integer getEstadoSTR() {
		return estadoSTR;
	}

	public void setEstadoSTR(Integer estadoSTR) {
		this.estadoSTR = estadoSTR;
	}
	
	
	

}
