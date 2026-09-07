package com.marketing.Model.DBMailMarketing;

import java.util.Date;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@IdClass(IrcaLocalesTanqueId.class)
@Table(name = "tblIrcaLocalesTanque")
public class IrcaLocalesTanque {

    @Id
    @Column(name = "IDLOCAL")
    private Integer IDLOCAL;

    @Id
    @Column(name = "idTanque")
    private Integer idTanque;


    @Column(name = "nombreTanque")
    private String nombreTanque;

    
    @Column(name = "largo")
    private Double largo;

    
    @Column(name = "ancho")
    private Double ancho;
    
    @Column(name = "profundo")
    private Double profundo;

	public Integer getIDLOCAL() {
		return IDLOCAL;
	}

	public void setIDLOCAL(Integer iDLOCAL) {
		IDLOCAL = iDLOCAL;
	}

	public Integer getIdTanque() {
		return idTanque;
	}

	public void setIdTanque(Integer idTanque) {
		this.idTanque = idTanque;
	}

	public String getNombreTanque() {
		return nombreTanque;
	}

	public void setNombreTanque(String nombreTanque) {
		this.nombreTanque = nombreTanque;
	}

	public Double getLargo() {
		return largo;
	}

	public void setLargo(Double largo) {
		this.largo = largo;
	}

	public Double getAncho() {
		return ancho;
	}

	public void setAncho(Double ancho) {
		this.ancho = ancho;
	}

	public Double getProfundo() {
		return profundo;
	}

	public void setProfundo(Double profundo) {
		this.profundo = profundo;
	}
    


    
}