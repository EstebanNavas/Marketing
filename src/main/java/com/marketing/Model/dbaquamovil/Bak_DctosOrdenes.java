package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="bak_DctosOrdenes")
@IdClass(Bak_DctosOrdenesPK.class)
public class Bak_DctosOrdenes {
	
	@Id
	@Column(name = "IDLOCAL")
	private Integer IDLOCAL;
	
	@Id
	@Column(name = "IDTIPOORDEN")
	private Integer IDTIPOORDEN;
	
	
	@Id
	@Column(name = "IDORDEN")
	private Integer IDORDEN;
	

	@Column(name = "FECHAORDEN")
	private Timestamp  FECHAORDEN;
	
	
	@Column(name = "ESTADO")
	private Double ESTADO;
	
	@Column(name = "idCliente")
	private String idCliente;
	
	@Column(name = "IDUSUARIO")
	private Double IDUSUARIO;
	
	@Column(name = "IDORIGEN")
	private Double IDORIGEN;
	
	@Column(name = "IDLOG")
	private Double IDLOG;
	
	@Column(name = "FECHAENTREGA")
	private Timestamp  FECHAENTREGA;
	
	@Column(name = "TIPODCTO")
	private String TIPODCTO;
	
	@Column(name = "DIRECCIONDESPACHO")
	private String DIRECCIONDESPACHO;
	
	@Column(name = "EMAIL")
	private String EMAIL;
	
	@Column(name = "FAX")
	private String FAX;
	
	@Column(name = "CONTACTO")
	private String CONTACTO;
	
	@Column(name = "OBSERVACION")
	private String OBSERVACION;
	
	@Column(name = "CIUDADDESPACHO")
	private String CIUDADDESPACHO;
	
	@Column(name = "FORMAPAGO")
	private String FORMAPAGO;
	
	@Column(name = "ordenCompra")
	private String ordenCompra;
	
	@Column(name = "descuentoComercial")
	private Double descuentoComercial;
	
	@Column(name = "impuestoVenta")
	private Integer impuestoVenta;
	
	@Column(name = "idRazon")
	private Integer idRazon;
	
	@Column(name = "idEstadoTx")
	private Integer idEstadoTx;
	
	@Column(name = "idTipoTx")
	private Integer idTipoTx;
	
	@Column(name = "numeroOrden")
	private Double numeroOrden;
	
	@Column(name = "idResponsable")
	private Integer idResponsable;
	
	@Column(name = "diasHistoria")
	private Integer diasHistoria;
	
	@Column(name = "diasInventario")
	private Integer diasInventario;
	
	@Column(name = "idBodegaOrigen")
	private Integer idBodegaOrigen;
	
	@Column(name = "idBodegaDestino")
	private Integer idBodegaDestino;
	
	@Column(name = "idPeriodo")
	private Integer idPeriodo;
	
	@Column(name = "vrTotalDiferir")
	private Double vrTotalDiferir;
	
	@Column(name = "cuotaDiferir")
	private Integer cuotaDiferir;
	
	@Column(name = "porcentajeInteresADiferir")
	private Double porcentajeInteresADiferir;
	
	@Column(name = "vrInteresADiferir")
	private Double vrInteresADiferir;
	
	@Column(name = "comentario")
	private String comentario;
	
	@Column(name = "historiaConsumo")
	private String historiaConsumo;
	
	@Column(name = "promedio")
	private Integer promedio;
	
	@Column(name = "cuotaVencida")
	private Integer cuotaVencida;
	
	@Column(name = "estadoCorte")
	private Integer estadoCorte;
	
	@Column(name = "fechaPagoUltimo")
	private Timestamp  fechaPagoUltimo;
	
	@Column(name = "vrPagoUltimo")
	private Double vrPagoUltimo;
	
	@Column(name = "promedioEstrato")
	private Integer promedioEstrato;
	
	@Column(name = "fechaInicioContrato")
	private Timestamp  fechaInicioContrato;
	
	
	@Column(name = "vrSalarioBasico")
	private Double vrSalarioBasico;
	
	@Column(name = "vrSubsidioTransporte")
	private Double vrSubsidioTransporte;
	
	@Column(name = "fechaFinContrato")
	private Timestamp  fechaFinContrato;
	
	@Column(name = "idContrato")
	private Integer idContrato;
	
	@Column(name = "idMedio")
	private Integer idMedio;
	
	@Column(name = "entidadMedio")
	private String entidadMedio;
	
	@Column(name = "cuentaMedio")
	private String cuentaMedio;

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

	public Timestamp getFECHAORDEN() {
		return FECHAORDEN;
	}

	public void setFECHAORDEN(Timestamp fECHAORDEN) {
		FECHAORDEN = fECHAORDEN;
	}

	public Double getESTADO() {
		return ESTADO;
	}

	public void setESTADO(Double eSTADO) {
		ESTADO = eSTADO;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public Double getIDUSUARIO() {
		return IDUSUARIO;
	}

	public void setIDUSUARIO(Double iDUSUARIO) {
		IDUSUARIO = iDUSUARIO;
	}

	public Double getIDORIGEN() {
		return IDORIGEN;
	}

	public void setIDORIGEN(Double iDORIGEN) {
		IDORIGEN = iDORIGEN;
	}

	public Double getIDLOG() {
		return IDLOG;
	}

	public void setIDLOG(Double iDLOG) {
		IDLOG = iDLOG;
	}

	public Timestamp getFECHAENTREGA() {
		return FECHAENTREGA;
	}

	public void setFECHAENTREGA(Timestamp fECHAENTREGA) {
		FECHAENTREGA = fECHAENTREGA;
	}

	public String getTIPODCTO() {
		return TIPODCTO;
	}

	public void setTIPODCTO(String tIPODCTO) {
		TIPODCTO = tIPODCTO;
	}

	public String getDIRECCIONDESPACHO() {
		return DIRECCIONDESPACHO;
	}

	public void setDIRECCIONDESPACHO(String dIRECCIONDESPACHO) {
		DIRECCIONDESPACHO = dIRECCIONDESPACHO;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getFAX() {
		return FAX;
	}

	public void setFAX(String fAX) {
		FAX = fAX;
	}

	public String getCONTACTO() {
		return CONTACTO;
	}

	public void setCONTACTO(String cONTACTO) {
		CONTACTO = cONTACTO;
	}

	public String getOBSERVACION() {
		return OBSERVACION;
	}

	public void setOBSERVACION(String oBSERVACION) {
		OBSERVACION = oBSERVACION;
	}

	public String getCIUDADDESPACHO() {
		return CIUDADDESPACHO;
	}

	public void setCIUDADDESPACHO(String cIUDADDESPACHO) {
		CIUDADDESPACHO = cIUDADDESPACHO;
	}

	public String getFORMAPAGO() {
		return FORMAPAGO;
	}

	public void setFORMAPAGO(String fORMAPAGO) {
		FORMAPAGO = fORMAPAGO;
	}

	public String getOrdenCompra() {
		return ordenCompra;
	}

	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public Double getDescuentoComercial() {
		return descuentoComercial;
	}

	public void setDescuentoComercial(Double descuentoComercial) {
		this.descuentoComercial = descuentoComercial;
	}

	public Integer getImpuestoVenta() {
		return impuestoVenta;
	}

	public void setImpuestoVenta(Integer impuestoVenta) {
		this.impuestoVenta = impuestoVenta;
	}

	public Integer getIdRazon() {
		return idRazon;
	}

	public void setIdRazon(Integer idRazon) {
		this.idRazon = idRazon;
	}

	public Integer getIdEstadoTx() {
		return idEstadoTx;
	}

	public void setIdEstadoTx(Integer idEstadoTx) {
		this.idEstadoTx = idEstadoTx;
	}

	public Integer getIdTipoTx() {
		return idTipoTx;
	}

	public void setIdTipoTx(Integer idTipoTx) {
		this.idTipoTx = idTipoTx;
	}

	public Double getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(Double numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public Integer getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}

	public Integer getDiasHistoria() {
		return diasHistoria;
	}

	public void setDiasHistoria(Integer diasHistoria) {
		this.diasHistoria = diasHistoria;
	}

	public Integer getDiasInventario() {
		return diasInventario;
	}

	public void setDiasInventario(Integer diasInventario) {
		this.diasInventario = diasInventario;
	}

	public Integer getIdBodegaOrigen() {
		return idBodegaOrigen;
	}

	public void setIdBodegaOrigen(Integer idBodegaOrigen) {
		this.idBodegaOrigen = idBodegaOrigen;
	}

	public Integer getIdBodegaDestino() {
		return idBodegaDestino;
	}

	public void setIdBodegaDestino(Integer idBodegaDestino) {
		this.idBodegaDestino = idBodegaDestino;
	}

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public Double getVrTotalDiferir() {
		return vrTotalDiferir;
	}

	public void setVrTotalDiferir(Double vrTotalDiferir) {
		this.vrTotalDiferir = vrTotalDiferir;
	}

	public Integer getCuotaDiferir() {
		return cuotaDiferir;
	}

	public void setCuotaDiferir(Integer cuotaDiferir) {
		this.cuotaDiferir = cuotaDiferir;
	}

	public Double getPorcentajeInteresADiferir() {
		return porcentajeInteresADiferir;
	}

	public void setPorcentajeInteresADiferir(Double porcentajeInteresADiferir) {
		this.porcentajeInteresADiferir = porcentajeInteresADiferir;
	}

	public Double getVrInteresADiferir() {
		return vrInteresADiferir;
	}

	public void setVrInteresADiferir(Double vrInteresADiferir) {
		this.vrInteresADiferir = vrInteresADiferir;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getHistoriaConsumo() {
		return historiaConsumo;
	}

	public void setHistoriaConsumo(String historiaConsumo) {
		this.historiaConsumo = historiaConsumo;
	}

	public Integer getPromedio() {
		return promedio;
	}

	public void setPromedio(Integer promedio) {
		this.promedio = promedio;
	}

	public Integer getCuotaVencida() {
		return cuotaVencida;
	}

	public void setCuotaVencida(Integer cuotaVencida) {
		this.cuotaVencida = cuotaVencida;
	}

	public Integer getEstadoCorte() {
		return estadoCorte;
	}

	public void setEstadoCorte(Integer estadoCorte) {
		this.estadoCorte = estadoCorte;
	}

	public Timestamp getFechaPagoUltimo() {
		return fechaPagoUltimo;
	}

	public void setFechaPagoUltimo(Timestamp fechaPagoUltimo) {
		this.fechaPagoUltimo = fechaPagoUltimo;
	}

	public Double getVrPagoUltimo() {
		return vrPagoUltimo;
	}

	public void setVrPagoUltimo(Double vrPagoUltimo) {
		this.vrPagoUltimo = vrPagoUltimo;
	}

	public Integer getPromedioEstrato() {
		return promedioEstrato;
	}

	public void setPromedioEstrato(Integer promedioEstrato) {
		this.promedioEstrato = promedioEstrato;
	}

	public Timestamp getFechaInicioContrato() {
		return fechaInicioContrato;
	}

	public void setFechaInicioContrato(Timestamp fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}

	public Double getVrSalarioBasico() {
		return vrSalarioBasico;
	}

	public void setVrSalarioBasico(Double vrSalarioBasico) {
		this.vrSalarioBasico = vrSalarioBasico;
	}

	public Double getVrSubsidioTransporte() {
		return vrSubsidioTransporte;
	}

	public void setVrSubsidioTransporte(Double vrSubsidioTransporte) {
		this.vrSubsidioTransporte = vrSubsidioTransporte;
	}

	public Timestamp getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFinContrato(Timestamp fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	public Integer getIdMedio() {
		return idMedio;
	}

	public void setIdMedio(Integer idMedio) {
		this.idMedio = idMedio;
	}

	public String getEntidadMedio() {
		return entidadMedio;
	}

	public void setEntidadMedio(String entidadMedio) {
		this.entidadMedio = entidadMedio;
	}

	public String getCuentaMedio() {
		return cuentaMedio;
	}

	public void setCuentaMedio(String cuentaMedio) {
		this.cuentaMedio = cuentaMedio;
	}
	
	
	

}
