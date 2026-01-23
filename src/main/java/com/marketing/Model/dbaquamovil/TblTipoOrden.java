package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblTipoOrden")
public class TblTipoOrden {
	
	@Id
	@Column(name= "idTipoOrden")
	private Integer idTipoOrden;
		
	
	@Column(name= "nombreTipoOrden")
	private String nombreTipoOrden;
		
	@Column(name= "estado")
	private Integer estado;
	
	@Column(name= "signo")
	private Integer signo;
	
	@Column(name= "idSeq")
	private Integer idSeq;

	
	@Column(name= "idAlcance")
	private Integer idAlcance;


	public Integer getIdTipoOrden() {
		return idTipoOrden;
	}


	public void setIdTipoOrden(Integer idTipoOrden) {
		this.idTipoOrden = idTipoOrden;
	}


	public String getNombreTipoOrden() {
		return nombreTipoOrden;
	}


	public void setNombreTipoOrden(String nombreTipoOrden) {
		this.nombreTipoOrden = nombreTipoOrden;
	}


	public Integer getEstado() {
		return estado;
	}


	public void setEstado(Integer estado) {
		this.estado = estado;
	}


	public Integer getSigno() {
		return signo;
	}


	public void setSigno(Integer signo) {
		this.signo = signo;
	}


	public Integer getIdSeq() {
		return idSeq;
	}


	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}


	public Integer getIdAlcance() {
		return idAlcance;
	}


	public void setIdAlcance(Integer idAlcance) {
		this.idAlcance = idAlcance;
	}
	
   
}