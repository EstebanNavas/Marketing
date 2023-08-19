package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.marketing.Model.dbaquamovil.TblTercerosPK;




@Entity
@Table(name="tblTerceros")
@IdClass(TblTercerosPK.class)
public class TblTerceros {

	@Id
	@Column(name="idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name="idCliente")
	private String idCliente;
	
	@Id
	@Column(name ="idTipoTercero")
	private Integer idTipoTercero;
	
	@Column(name ="idTercero")
	private Integer idTercero;
	

	@Column(name="nombreTercero")
	private String nombreTercero;
	
	@Column(name="direccionTercero")
	private String direccionTercero;
	
	@Column(name ="idDptoCiudad")
	private Integer idDptoCiudad;
	
	@Column(name="telefonoCelular")
	private String telefonoCelular;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdTercero() {
		return idTercero;
	}

	public void setIdTercero(Integer idTercero) {
		this.idTercero = idTercero;
	}

	public Integer getIdTipoTercero() {
		return idTipoTercero;
	}

	public void setIdTipoTercero(Integer idTipoTercero) {
		this.idTipoTercero = idTipoTercero;
	}

	public String getNombreTercero() {
		return nombreTercero;
	}

	public void setNombreTercero(String nombreTercero) {
		this.nombreTercero = nombreTercero;
	}

	public String getDireccionTercero() {
		return direccionTercero;
	}

	public void setDireccionTercero(String direccionTercero) {
		this.direccionTercero = direccionTercero;
	}

	public Integer getIdDptoCiudad() {
		return idDptoCiudad;
	}

	public void setIdDptoCiudad(Integer idDptoCiudad) {
		this.idDptoCiudad = idDptoCiudad;
	}

	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	@Override
	public String toString() {
		return "TblTerceros [idLocal=" + idLocal + ", idCliente=" + idCliente + ", idTercero=" + idTercero
				+ ", idTipoTercero=" + idTipoTercero + ", nombreTercero=" + nombreTercero + ", direccionTercero="
				+ direccionTercero + ", idDptoCiudad=" + idDptoCiudad + ", telefonoCelular=" + telefonoCelular
				+ ", getIdLocal()=" + getIdLocal() + ", getIdCliente()=" + getIdCliente() + ", getIdTercero()="
				+ getIdTercero() + ", getIdTipoTercero()=" + getIdTipoTercero() + ", getNombreTercero()="
				+ getNombreTercero() + ", getDireccionTercero()=" + getDireccionTercero() + ", getIdDptoCiudad()="
				+ getIdDptoCiudad() + ", getTelefonoCelular()=" + getTelefonoCelular() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}





























