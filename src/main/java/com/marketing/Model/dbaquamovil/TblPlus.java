package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name= "tblPlus")
@IdClass(TblPlusPK.class)
public class TblPlus {

	@Id
	@Column(name= "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name= "idPlu")
	private Integer idPlu;
	
	@Column(name= "nombrePlu")
	private String nombrePlu;
	
	@Column(name= "idTipo")
	private Integer idTipo;
	
	@Column(name= "idLinea")
	private Integer idLinea;
	
	@Column(name= "idCategoria")
	private Integer idCategoria;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdPlu() {
		return idPlu;
	}

	public void setIdPlu(Integer idPlu) {
		this.idPlu = idPlu;
	}

	public String getNombrePlu() {
		return nombrePlu;
	}

	public void setNombrePlu(String nombrePlu) {
		this.nombrePlu = nombrePlu;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public Integer getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Integer idLinea) {
		this.idLinea = idLinea;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	
}
