package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table (name = "tblMedidoresMacro")
@IdClass(TblMedidoresMacroPK.class)
public class TblMedidoresMacro {

	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name = "idMacro")
	private Integer idMacro;
	
	@Column(name = "nombreMacro")
	private String nombreMacro;
	
	@Column(name = "diametro")
	private Integer diametro;
	
	@Column(name = "estado")
	private Integer estado;

	public Integer getIdCiudad() {
		return idLocal;
	}

	public void setIdCiudad(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdMacro() {
		return idMacro;
	}

	public void setIdMacro(Integer idMacro) {
		this.idMacro = idMacro;
	}

	public String getNombreMacro() {
		return nombreMacro;
	}

	public void setNombreMacro(String nombreMacro) {
		this.nombreMacro = nombreMacro;
	}

	public Integer getDiametro() {
		return diametro;
	}

	public void setDiametro(Integer diametro) {
		this.diametro = diametro;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	
	

}
