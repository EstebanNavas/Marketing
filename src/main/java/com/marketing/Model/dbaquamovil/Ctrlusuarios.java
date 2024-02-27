package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="ctrlUsuarios")
@IdClass(CtrlusuariosPK.class)
public class Ctrlusuarios {

	 @Id
	 @Column(name = "idusuario")
	 private Integer idUsuario;
	 
	 @Id
	 @Column(name = "idlocal")
	 private Integer idLocal;
	
	@Column(name="nombreusuario")
	private String nombreUsuario;

	private String clave;
	
	@Column(name="idnivel")
	private Integer idNivel;

	private String direccion;
	
	private String telefono;
	
	@Column(name="fechacambioclave")
	private String fechaCambioClave;
	
	private Integer estado;

	private String email;
	
	
	@Column(name="aliasUsuario")
	private String aliasUsuario;
	
	@Column(name="idseq")
	private Integer idSeq;
	
	public Integer getIdLocal() {
		return idLocal;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public String getClave() {
		return clave;
	}
	public Integer getIdNivel() {
		return idNivel;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getFechaCambioClave() {
		return fechaCambioClave;
	}
	public Integer getEstado() {
		return estado;
	}
	public String getEmail() {
		return email;
	}
	public String getAliasUsuario() {
		return aliasUsuario;
	}
	public Integer getIdSeq() {
		return idSeq;
	}
	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setFechaCambioClave(String fechaCambioClave) {
		this.fechaCambioClave = fechaCambioClave;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAliasUsuario(String aliasUsuario) {
		this.aliasUsuario = aliasUsuario;
	}
	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}
	
}


