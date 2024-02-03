package com.marketing.Model.dbaquamovil;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tblTercerosRuta")
//@IdClass(TblTercerosRutaPK.class)
public class TblTercerosRuta {
	
	
	@Id
	@Column(name="idRuta")
	private Integer idRuta;
	
	//@Id
	@Column(name="idLocal")
	private Integer idLocal;
	
	@Column(name="nombreCiclo")
	private String nombreCiclo;
	
	@Column(name="nombreRuta")
	private String nombreRuta;
	
	@Column(name="estado")
	private Integer estado;
	
	@Column(name="ordenRuta")
	private Integer ordenRuta;
	
	@Column(name="idUsuario")
	private Integer idUsuario;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "terceroRuta", cascade = CascadeType.ALL)// Se establece relacion uno a muchos con la tabla TblTerceros
	private List<TblTerceros> terceros;
	
	  public TblTercerosRuta() {
	        // Constructor sin argumentos
	    }

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}

	public String getNombreCiclo() {
		return nombreCiclo;
	}

	public void setNombreCiclo(String nombreCiclo) {
		this.nombreCiclo = nombreCiclo;
	}

	public String getNombreRuta() {
		return nombreRuta;
	}

	public void setNombreRuta(String nombreRuta) {
		this.nombreRuta = nombreRuta;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getOrdenRuta() {
		return ordenRuta;
	}

	public void setOrdenRuta(Integer ordenRuta) {
		this.ordenRuta = ordenRuta;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	public TblTercerosRuta(Integer idRuta) {
	    this.idRuta = idRuta;
	}
	

}
