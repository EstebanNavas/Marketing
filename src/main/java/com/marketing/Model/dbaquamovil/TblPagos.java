package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="tblPagos")
@IdClass(TblPagosPK.class)
public class TblPagos {

	
	@Id
	@Column(name = "idLocal")
	private Integer idLocal;
	
	@Id
	@Column(name = "idTipoOrden")
	private Integer idTipoOrden;
	
	
	@Id
	@Column(name = "idRecibo")
	private Integer idRecibo;
	
	
	@Id
	@Column(name = "indicador")
	private Integer indicador;
	
	
	@Column(name = "fechaPago")
	private Timestamp  fechaPago;
	
	@Column(name = "vrPago")
	private Float vrPago;
	
	@Column(name = "nitCC")
	private String nitCC;
	
	@Column(name = "estado")
	private Float estado;
	
	@Column(name = "idUsuario")
	private Float idUsuario;
	
	@Column(name = "vrRteFuente")
	private Float vrRteFuente;
	
	@Column(name = "vrDescuento")
	private Float vrDescuento;
	
	@Column(name = "vrRteIva")
	private Float vrRteIva;
	
	@Column(name = "vrRteIca")
	private Float vrRteIca;
	
	@Column(name = "idPeriodo")
	private Integer idPeriodo;
	
	@Column(name = "idDcto")
	private Float idDcto;
	
	@Column(name = "idDctoNitCC")
	private String idDctoNitCC;
	
	@Column(name = "idPlanilla")
	private Float idPlanilla;
	
	@Column(name = "vrSaldo")
	private Float vrSaldo;
	
	@Column(name = "idLog")
	private Double idLog;
	
	@Column(name = "idVendedor")
	private Float idVendedor;
	
	@Column(name = "idReciboCruce")
	private Float idReciboCruce;
	
	@Column(name = "vrPagoCambio")
	private Float vrPagoCambio;
	
	@Column(name = "comentario")
	private String comentario;

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public Integer getIdTipoOrden() {
		return idTipoOrden;
	}

	public void setIdTipoOrden(Integer idTipoOrden) {
		this.idTipoOrden = idTipoOrden;
	}

	public Integer getIdRecibo() {
		return idRecibo;
	}

	public void setIdRecibo(Integer idRecibo) {
		this.idRecibo = idRecibo;
	}

	public Integer getIndicador() {
		return indicador;
	}

	public void setIndicador(Integer indicador) {
		this.indicador = indicador;
	}

	public Timestamp getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Timestamp fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Float getVrPago() {
		return vrPago;
	}

	public void setVrPago(Float vrPago) {
		this.vrPago = vrPago;
	}

	public String getNitCC() {
		return nitCC;
	}

	public void setNitCC(String nitCC) {
		this.nitCC = nitCC;
	}

	public Float getEstado() {
		return estado;
	}

	public void setEstado(Float estado) {
		this.estado = estado;
	}

	public Float getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Float idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Float getVrRteFuente() {
		return vrRteFuente;
	}

	public void setVrRteFuente(Float vrRteFuente) {
		this.vrRteFuente = vrRteFuente;
	}

	public Float getVrDescuento() {
		return vrDescuento;
	}

	public void setVrDescuento(Float vrDescuento) {
		this.vrDescuento = vrDescuento;
	}

	public Float getVrRteIva() {
		return vrRteIva;
	}

	public void setVrRteIva(Float vrRteIva) {
		this.vrRteIva = vrRteIva;
	}

	public Float getVrRteIca() {
		return vrRteIca;
	}

	public void setVrRteIca(Float vrRteIca) {
		this.vrRteIca = vrRteIca;
	}

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public Float getIdDcto() {
		return idDcto;
	}

	public void setIdDcto(Float idDcto) {
		this.idDcto = idDcto;
	}

	public String getIdDctoNitCC() {
		return idDctoNitCC;
	}

	public void setIdDctoNitCC(String idDctoNitCC) {
		this.idDctoNitCC = idDctoNitCC;
	}

	public Float getIdPlanilla() {
		return idPlanilla;
	}

	public void setIdPlanilla(Float idPlanilla) {
		this.idPlanilla = idPlanilla;
	}

	public Float getVrSaldo() {
		return vrSaldo;
	}

	public void setVrSaldo(Float vrSaldo) {
		this.vrSaldo = vrSaldo;
	}

	public Double getIdLog() {
		return idLog;
	}

	public void setIdLog(Double idLog) {
		this.idLog = idLog;
	}

	public Float getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Float idVendedor) {
		this.idVendedor = idVendedor;
	}

	public Float getIdReciboCruce() {
		return idReciboCruce;
	}

	public void setIdReciboCruce(Float idReciboCruce) {
		this.idReciboCruce = idReciboCruce;
	}

	public Float getVrPagoCambio() {
		return vrPagoCambio;
	}

	public void setVrPagoCambio(Float vrPagoCambio) {
		this.vrPagoCambio = vrPagoCambio;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	
	
	
}
