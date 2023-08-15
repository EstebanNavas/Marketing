package com.marketing.Model.DBMailMarketing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblMailItemPlantilla")
public class TblMailItemPlantilla {
	
	@Id
	@Column(name = "idrequerimiento")
	private Integer idRequerimiento;
	@Column(name = "comentario")
	private String comentario;

	@Column(name = "formato")
	private String formato;

	public Integer getIdRequerimiento() {
		return idRequerimiento;
	}

	public String getComentario() {
		return comentario;
	}

	public String getFormato() {
		return formato;
	}

	public void setIdRequerimiento(Integer idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	
}
