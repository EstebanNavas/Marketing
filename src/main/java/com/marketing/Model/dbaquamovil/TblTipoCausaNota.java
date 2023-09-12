package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tblTipoCausaNota")
@IdClass(TblTipoCausaNotaPK.class)
public class TblTipoCausaNota {

	@Id
	@Column(name = "idTipoTabla")
	private Integer idTipoTabla;
	
	@Id
	@Column(name = "idCausa")
	private Integer idCausa;
	
	@Column(name = "nombreCausa")
	private String nombreCausa;
	
	@Column(name = "estado")
	private Integer estado;
	
	@Column(name = "idSeq")
	private Integer idSeq;

	public Integer getIdTipoTabla() {
		return idTipoTabla;
	}

	public void setIdTipoTabla(Integer idTipoTabla) {
		this.idTipoTabla = idTipoTabla;
	}

	public Integer getIdCausa() {
		return idCausa;
	}

	public void setIdCausa(Integer idCausa) {
		this.idCausa = idCausa;
	}

	public String getNombreCausa() {
		return nombreCausa;
	}

	public void setNombreCausa(String nombreCausa) {
		this.nombreCausa = nombreCausa;
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
	
	
	
}
