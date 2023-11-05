package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ctrlUsuariosPerfil")
public class CtrlUsuariosPerfil {

	@Id
	@Column(name = "idPerfil")
	private Integer idPerfil;
	
	@Column(name = "nombrePerfil")
	private String nombrePerfil;
	
	@Column(name = "idAplicacion")
	private Integer idAplicacion;

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNombrePerfil() {
		return nombrePerfil;
	}

	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}

	public Integer getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Integer idAplicacion) {
		this.idAplicacion = idAplicacion;
	}
	
	
	
}
