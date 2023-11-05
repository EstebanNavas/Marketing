package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblOpcionesPerfil")
@IdClass(TblOpcionesPerfilPK.class)
public class TblOpcionesPerfil {

	@Id
	@Column(name = "idOpcion")
	private Integer idOpcion;
	
	@Id
	@Column(name = "idPerfil")
	private Integer idPerfil;
	
	@Id
	@Column(name = "idLocal")
	private Integer idLocal;

	public Integer getIdOpcion() {
		return idOpcion;
	}

	public void setIdOpcion(Integer idOpcion) {
		this.idOpcion = idOpcion;
	}

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	
	
}
