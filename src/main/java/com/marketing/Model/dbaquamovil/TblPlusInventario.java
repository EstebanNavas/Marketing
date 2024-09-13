package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tblPlusInventario")
@IdClass(TblPlusInventarioPK.class)
public class TblPlusInventario {

	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name = "idPlu")
	private Integer idPlu;
	
	
	@Id
	@Column(name = "idBodega")
	private Integer idBodega;
	
	
	@Column(name = "existencia")
	private Integer existencia;
	
	@Column(name = "idTipoOrden")
	private Integer idTipoOrden;
	
	@Column(name = "idOrden")
	private Integer idOrden;
	
	
	@Column(name = "cantidadOrden")
	private Integer cantidadOrden;
	
	@Column(name = "estado")
	private Integer estado;

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

	public Integer getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(Integer idBodega) {
		this.idBodega = idBodega;
	}

	public Integer getExistencia() {
		return existencia;
	}

	public void setExistencia(Integer existencia) {
		this.existencia = existencia;
	}

	public Integer getIdTipoOrden() {
		return idTipoOrden;
	}

	public void setIdTipoOrden(Integer idTipoOrden) {
		this.idTipoOrden = idTipoOrden;
	}

	public Integer getIdOrden() {
		return idOrden;
	}

	public void setIdOrden(Integer idOrden) {
		this.idOrden = idOrden;
	}

	public Integer getCantidadOrden() {
		return cantidadOrden;
	}

	public void setCantidadOrden(Integer cantidadOrden) {
		this.cantidadOrden = cantidadOrden;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	
	
	
	
	
	
}
