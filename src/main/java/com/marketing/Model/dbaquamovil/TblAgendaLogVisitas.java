package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tblAgendaLogVisitas")
@IdClass(TblAgendaLogVisitasPK.class)
public class TblAgendaLogVisitas {

	@Id
	@Column(name= "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name= "IDLOG")
	private Integer IDLOG;
	
	@Column(name= "idCliente")
	private String idCliente;
	
	@Column(name= "IDUSUARIO")
	private Integer IDUSUARIO;
	
	@Column(name= "IDPERIODO")
	private Integer IDPERIODO;
	
	@Column(name= "idTipoOrden")
	private Integer idTipoOrden;
	
	@Column(name= "ESTADO")
	private Integer ESTADO;
	
	@Column(name= "idLocalTercero")
	private Integer idLocalTercero;
	
	@Column(name = "FECHAVISITA")
	private Timestamp  FECHAVISITA;
	
	@Column(name= "IDESTADOVISITA")
	private Integer IDESTADOVISITA;
	
	@Column(name= "sessionId")
	private String sessionId;
	
	@Column(name = "ipTx")
	private String ipTx;
	
	@Column(name= "idEstadoTx")
	private Integer idEstadoTx;
	
	@Column(name = "fechaTxInicio")
	private Timestamp  fechaTxInicio;
	
	
	
	

	
	public Timestamp getFechaTxInicio() {
		return fechaTxInicio;
	}

	public void setFechaTxInicio(Timestamp fechaTxInicio) {
		this.fechaTxInicio = fechaTxInicio;
	}

	public Integer getIDESTADOVISITA() {
		return IDESTADOVISITA;
	}

	public void setIDESTADOVISITA(Integer iDESTADOVISITA) {
		IDESTADOVISITA = iDESTADOVISITA;
	}

	public Timestamp getFECHAVISITA() {
		return FECHAVISITA;
	}

	public void setFECHAVISITA(Timestamp fECHAVISITA) {
		FECHAVISITA = fECHAVISITA;
	}

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIDLOG() {
		return IDLOG;
	}

	public void setIDLOG(Integer iDLOG) {
		IDLOG = iDLOG;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIDUSUARIO() {
		return IDUSUARIO;
	}

	public void setIDUSUARIO(Integer iDUSUARIO) {
		IDUSUARIO = iDUSUARIO;
	}

	public Integer getIDPERIODO() {
		return IDPERIODO;
	}

	public void setIDPERIODO(Integer iDPERIODO) {
		IDPERIODO = iDPERIODO;
	}

	public Integer getIdTipoOrden() {
		return idTipoOrden;
	}

	public void setIdTipoOrden(Integer idTipoOrden) {
		this.idTipoOrden = idTipoOrden;
	}

	public Integer getESTADO() {
		return ESTADO;
	}

	public void setESTADO(Integer eSTADO) {
		ESTADO = eSTADO;
	}

	public Integer getIdLocalTercero() {
		return idLocalTercero;
	}

	public void setIdLocalTercero(Integer idLocalTercero) {
		this.idLocalTercero = idLocalTercero;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIpTx() {
		return ipTx;
	}

	public void setIpTx(String ipTx) {
		this.ipTx = ipTx;
	}

	public Integer getIdEstadoTx() {
		return idEstadoTx;
	}

	public void setIdEstadoTx(Integer idEstadoTx) {
		this.idEstadoTx = idEstadoTx;
	}
	
	
	
	
}
