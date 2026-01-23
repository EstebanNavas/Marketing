package com.marketing.Model.dbaquamovil;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblDctosOrdenesDetalle")
@IdClass(TblDctosOrdenesDetallePK.class)
public class TblDctosOrdenesDetalle {

	@Id
	@Column(name= "IDLOCAL")
	private Integer IDLOCAL;
	
	@Id
	@Column(name= "IDTIPOORDEN")
	private Integer IDTIPOORDEN;
	
	@Id
	@Column(name= "IDORDEN")
	private Integer IDORDEN;
	
	@Id
	@Column(name= "IDPLU")
	private Integer IDPLU;
	
	@Column(name= "idCliente")
	private String idCliente;
	
	@Column(name= "item")
	private Integer item;
	
	@Column(name= "CANTIDAD")
	private Integer CANTIDAD;
	
	
	@Column(name= "IDTIPO")
	private Integer IDTIPO;
	
	@Column(name= "ESTADO")
	private Integer ESTADO;
	
	@Column(name= "NOMBREPLU")
	private String NOMBREPLU;
	
	@Column(name= "comentario")
	private String comentario;
	
	@Column(name= "VRVENTAUNITARIO")
	private Integer VRVENTAUNITARIO;
	
	@Column(name= "VRVENTAORIGINAL")
	private Integer VRVENTAORIGINAL;
	
	@Column(name= "VRCOSTO")
	private Integer VRCOSTO;
	
	@Column(name= "VRDSCTOPIE")
	private Integer VRDSCTOPIE;
	
	@Column(name= "PORCENTAJEIVA")
	private Integer PORCENTAJEIVA;
	
	@Column(name= "fechaRegistroTx")
	private Date fechaRegistroTx;
	
	
	// Nuevas Columnas
	@Column(name= "latitud")
	private Double latitud;
	
	@Column(name= "longitud")
	private Double longitud;
	
	
	

	
	public Integer getPORCENTAJEIVA() {
		return PORCENTAJEIVA;
	}

	public void setPORCENTAJEIVA(Integer pORCENTAJEIVA) {
		PORCENTAJEIVA = pORCENTAJEIVA;
	}

	public Integer getVRVENTAUNITARIO() {
		return VRVENTAUNITARIO;
	}

	public void setVRVENTAUNITARIO(Integer vRVENTAUNITARIO) {
		VRVENTAUNITARIO = vRVENTAUNITARIO;
	}

	public Integer getVRVENTAORIGINAL() {
		return VRVENTAORIGINAL;
	}

	public void setVRVENTAORIGINAL(Integer vRVENTAORIGINAL) {
		VRVENTAORIGINAL = vRVENTAORIGINAL;
	}

	public Integer getVRCOSTO() {
		return VRCOSTO;
	}

	public void setVRCOSTO(Integer vRCOSTO) {
		VRCOSTO = vRCOSTO;
	}

	public Integer getVRDSCTOPIE() {
		return VRDSCTOPIE;
	}

	public void setVRDSCTOPIE(Integer vRDSCTOPIE) {
		VRDSCTOPIE = vRDSCTOPIE;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIDLOCAL() {
		return IDLOCAL;
	}

	public void setIDLOCAL(Integer iDLOCAL) {
		IDLOCAL = iDLOCAL;
	}

	public Integer getIDTIPOORDEN() {
		return IDTIPOORDEN;
	}

	public void setIDTIPOORDEN(Integer iDTIPOORDEN) {
		IDTIPOORDEN = iDTIPOORDEN;
	}

	public Integer getIDORDEN() {
		return IDORDEN;
	}

	public void setIDORDEN(Integer iDORDEN) {
		IDORDEN = iDORDEN;
	}

	public Integer getIDPLU() {
		return IDPLU;
	}

	public void setIDPLU(Integer iDPLU) {
		IDPLU = iDPLU;
	}

	public Integer getCANTIDAD() {
		return CANTIDAD;
	}

	public void setCANTIDAD(Integer cANTIDAD) {
		CANTIDAD = cANTIDAD;
	}

	public Integer getIDTIPO() {
		return IDTIPO;
	}

	public void setIDTIPO(Integer iDTIPO) {
		IDTIPO = iDTIPO;
	}

	public Integer getESTADO() {
		return ESTADO;
	}

	public void setESTADO(Integer eSTADO) {
		ESTADO = eSTADO;
	}

	public String getNOMBREPLU() {
		return NOMBREPLU;
	}

	public void setNOMBREPLU(String nOMBREPLU) {
		NOMBREPLU = nOMBREPLU;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	
	public Date getFechaRegistroTx() {
		return fechaRegistroTx;
	}

	public void setFechaRegistroTx(Date fechaRegistroTx) {
		this.fechaRegistroTx = fechaRegistroTx;
	}

	
}














