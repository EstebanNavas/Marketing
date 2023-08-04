package com.marketing.Model.DBMailMarketing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblMailPlantilla")
public class MailPlantilla {
	
	@Id
	@Column(name="idPlantilla")
	private Integer idPlantilla;
	
	
	@Column(name="idRequerimiento")
	private Integer idRequerimiento;
	
	@Column(name="NombrePlantilla")
	private String nombrePlantilla;
	
	
	public Integer getIdPlantilla() {
		return idPlantilla;
	}
	public Integer getIdRequerimiento() {
		return idRequerimiento;
	}
	public String getNombrePlantilla() {
		return nombrePlantilla;
	}
	public void setIdPlantilla(Integer idplantilla) {
		this.idPlantilla = idplantilla;
	}
	public void setIdRequerimiento(Integer idRequerimiento) {
		this.idRequerimiento = idRequerimiento;
	}
	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

}
