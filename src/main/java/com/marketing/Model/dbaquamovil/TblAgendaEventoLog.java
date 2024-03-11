package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;


@Entity
@Table(name="tblAgendaEventoLog")
@IdClass(TblAgendaEventoLogPK.class)
public class TblAgendaEventoLog {
	
	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name = "idCliente")
	private String idCliente;
	
	
	@Id
	@Column(name = "idPeriodo")
	private Integer idPeriodo;
	
	
	@Id
	@Column(name = "idEvento")
	private Integer idEvento;
	
	
	@Column(name = "nombreEvento")
	private String nombreEvento;
	
	
	@Column(name = "dataEnvio")
	private String dataEnvio;
	
	@Column(name = "dataRecibo")
	private String dataRecibo;
	
	
	@Column(name = "fechaEvento")
	private Timestamp fechaEvento;
	
	@Column(name = "estado")
	private Integer estado;

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

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(String dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getDataRecibo() {
		return dataRecibo;
	}

	public void setDataRecibo(String dataRecibo) {
		this.dataRecibo = dataRecibo;
	}

	public Timestamp getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Timestamp fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

}
