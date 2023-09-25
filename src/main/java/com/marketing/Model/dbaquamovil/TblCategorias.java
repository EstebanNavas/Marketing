package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblCategorias")
public class TblCategorias {

	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Column(name = "idLinea")
	private Integer idLinea;
	
	@Column(name = "IdCategoria")
	private Integer IdCategoria;
	
	@Column(name = "nombreCategoria")
	private String nombreCategoria;
	
	@Column(name = "estado")
	private Integer estado;
	
	@Column(name = "idSeq")
	private Integer idSeq;
	
	@Column(name = "idProducto")
	private Integer idProducto;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdLinea() {
		return idLinea;
	}

	public void setIdLinea(Integer idLinea) {
		this.idLinea = idLinea;
	}

	public Integer getIdCategoria() {
		return IdCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		IdCategoria = idCategoria;
	}

	public String getNombreCategoria() {
		return nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
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

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	
	
}
