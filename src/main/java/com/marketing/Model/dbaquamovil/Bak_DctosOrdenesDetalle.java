package com.marketing.Model.dbaquamovil;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="bak_DctosOrdenesDetalle")
@IdClass(Bak_DctosOrdenesDetallePK.class)
public class Bak_DctosOrdenesDetalle {

	@Id
	@Column(name = "IDLOCAL")
	private Integer IDLOCAL;
	
	@Id
	@Column(name = "IDTIPOORDEN")
	private Integer IDTIPOORDEN;
	
	
	@Id
	@Column(name = "IDORDEN")
	private Integer IDORDEN;
	
	@Id
	@Column(name = "IDPLU")
	private Integer IDPLU;
	
	
	@Column(name = "CANTIDAD")
	private Double CANTIDAD;
	
	@Column(name = "IDTIPO")
	private Integer IDTIPO;
	
	@Column(name = "PORCENTAJEIVA")
	private Double PORCENTAJEIVA;
	
	@Column(name = "VRVENTAUNITARIO")
	private Double VRVENTAUNITARIO;
	
	@Column(name = "ESTADO")
	private Integer ESTADO;
	
	@Column(name = "NOMBREPLU")
	private String NOMBREPLU;
	
	@Column(name = "VRVENTAORIGINAL")
	private Double VRVENTAORIGINAL;
	
	@Column(name = "VRCOSTO")
	private Double VRCOSTO;
	
	@Column(name = "VRDSCTOPIE")
	private Double VRDSCTOPIE;
	
	@Column(name = "PORCENTAJEDSCTO")
	private Double PORCENTAJEDSCTO;
	
	@Column(name = "CANTIDADPEDIDA")
	private Double CANTIDADPEDIDA;
	
	@Column(name = "vrCostoNegociado")
	private Double vrCostoNegociado;
	
	@Column(name = "strIdBodega")
	private String strIdBodega;
	
	@Column(name = "vrCostoResurtido")
	private Double vrCostoResurtido;
	
	@Column(name = "STRIDLISTA")
	private String STRIDLISTA;
	
	@Column(name = "STRIDREFERENCIA")
	private String STRIDREFERENCIA;
	
	@Column(name = "PESOTEORICO")
	private Double PESOTEORICO;
	
	@Column(name = "NOMBREUNIDADMEDIDA")
	private String NOMBREUNIDADMEDIDA;
	
	@Column(name = "IDLOCALSUGERIDO")
	private Double IDLOCALSUGERIDO;
	
	@Column(name = "IDBODEGASUGERIDO")
	private String IDBODEGASUGERIDO;
	
	@Column(name = "comentario")
	private String comentario;
	
	@Column(name = "item")
	private Integer item;
	
	@Column(name = "itemPadre")
	private Integer itemPadre;
	
	@Column(name = "idEstadoTx")
	private Integer idEstadoTx;
	
	@Column(name = "idTipoTx")
	private Integer idTipoTx;
	
	@Column(name = "idReferenciaOriginal")
	private String idReferenciaOriginal;
	
	@Column(name = "idEstadoRefOriginal")
	private Integer idEstadoRefOriginal;
	
	@Column(name = "fechaEntrega")
	private Timestamp  fechaEntrega;
	
	@Column(name = "idBodega")
	private Integer idBodega;
	
	@Column(name = "vrCostoIND")
	private Double vrCostoIND;
	
	@Column(name = "idSubcuenta")
	private String idSubcuenta;
	
	@Column(name = "lecturaMedidor")
	private Double lecturaMedidor;
	
	@Column(name = "idCliente")
	private String idCliente;
	
	@Column(name = "idEstracto")
	private Integer idEstracto;
	
	@Column(name = "idRuta")
	private Integer idRuta;
	
	@Column(name = "idNovedadLectura")
	private Integer idNovedadLectura;
	
	@Column(name = "lecturaAnterior")
	private Double lecturaAnterior;

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

	public Double getCANTIDAD() {
		return CANTIDAD;
	}

	public void setCANTIDAD(Double cANTIDAD) {
		CANTIDAD = cANTIDAD;
	}

	public Integer getIDTIPO() {
		return IDTIPO;
	}

	public void setIDTIPO(Integer iDTIPO) {
		IDTIPO = iDTIPO;
	}

	public Double getPORCENTAJEIVA() {
		return PORCENTAJEIVA;
	}

	public void setPORCENTAJEIVA(Double pORCENTAJEIVA) {
		PORCENTAJEIVA = pORCENTAJEIVA;
	}

	public Double getVRVENTAUNITARIO() {
		return VRVENTAUNITARIO;
	}

	public void setVRVENTAUNITARIO(Double vRVENTAUNITARIO) {
		VRVENTAUNITARIO = vRVENTAUNITARIO;
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

	public Double getVRVENTAORIGINAL() {
		return VRVENTAORIGINAL;
	}

	public void setVRVENTAORIGINAL(Double vRVENTAORIGINAL) {
		VRVENTAORIGINAL = vRVENTAORIGINAL;
	}

	public Double getVRCOSTO() {
		return VRCOSTO;
	}

	public void setVRCOSTO(Double vRCOSTO) {
		VRCOSTO = vRCOSTO;
	}

	public Double getVRDSCTOPIE() {
		return VRDSCTOPIE;
	}

	public void setVRDSCTOPIE(Double vRDSCTOPIE) {
		VRDSCTOPIE = vRDSCTOPIE;
	}

	public Double getPORCENTAJEDSCTO() {
		return PORCENTAJEDSCTO;
	}

	public void setPORCENTAJEDSCTO(Double pORCENTAJEDSCTO) {
		PORCENTAJEDSCTO = pORCENTAJEDSCTO;
	}

	public Double getCANTIDADPEDIDA() {
		return CANTIDADPEDIDA;
	}

	public void setCANTIDADPEDIDA(Double cANTIDADPEDIDA) {
		CANTIDADPEDIDA = cANTIDADPEDIDA;
	}

	public Double getVrCostoNegociado() {
		return vrCostoNegociado;
	}

	public void setVrCostoNegociado(Double vrCostoNegociado) {
		this.vrCostoNegociado = vrCostoNegociado;
	}

	public String getStrIdBodega() {
		return strIdBodega;
	}

	public void setStrIdBodega(String strIdBodega) {
		this.strIdBodega = strIdBodega;
	}

	public Double getVrCostoResurtido() {
		return vrCostoResurtido;
	}

	public void setVrCostoResurtido(Double vrCostoResurtido) {
		this.vrCostoResurtido = vrCostoResurtido;
	}

	public String getSTRIDLISTA() {
		return STRIDLISTA;
	}

	public void setSTRIDLISTA(String sTRIDLISTA) {
		STRIDLISTA = sTRIDLISTA;
	}

	public String getSTRIDREFERENCIA() {
		return STRIDREFERENCIA;
	}

	public void setSTRIDREFERENCIA(String sTRIDREFERENCIA) {
		STRIDREFERENCIA = sTRIDREFERENCIA;
	}

	public Double getPESOTEORICO() {
		return PESOTEORICO;
	}

	public void setPESOTEORICO(Double pESOTEORICO) {
		PESOTEORICO = pESOTEORICO;
	}

	public String getNOMBREUNIDADMEDIDA() {
		return NOMBREUNIDADMEDIDA;
	}

	public void setNOMBREUNIDADMEDIDA(String nOMBREUNIDADMEDIDA) {
		NOMBREUNIDADMEDIDA = nOMBREUNIDADMEDIDA;
	}

	public Double getIDLOCALSUGERIDO() {
		return IDLOCALSUGERIDO;
	}

	public void setIDLOCALSUGERIDO(Double iDLOCALSUGERIDO) {
		IDLOCALSUGERIDO = iDLOCALSUGERIDO;
	}

	public String getIDBODEGASUGERIDO() {
		return IDBODEGASUGERIDO;
	}

	public void setIDBODEGASUGERIDO(String iDBODEGASUGERIDO) {
		IDBODEGASUGERIDO = iDBODEGASUGERIDO;
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

	public Integer getItemPadre() {
		return itemPadre;
	}

	public void setItemPadre(Integer itemPadre) {
		this.itemPadre = itemPadre;
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

	public String getIdReferenciaOriginal() {
		return idReferenciaOriginal;
	}

	public void setIdReferenciaOriginal(String idReferenciaOriginal) {
		this.idReferenciaOriginal = idReferenciaOriginal;
	}

	public Integer getIdEstadoRefOriginal() {
		return idEstadoRefOriginal;
	}

	public void setIdEstadoRefOriginal(Integer idEstadoRefOriginal) {
		this.idEstadoRefOriginal = idEstadoRefOriginal;
	}

	public Timestamp getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Integer getIdBodega() {
		return idBodega;
	}

	public void setIdBodega(Integer idBodega) {
		this.idBodega = idBodega;
	}

	public Double getVrCostoIND() {
		return vrCostoIND;
	}

	public void setVrCostoIND(Double vrCostoIND) {
		this.vrCostoIND = vrCostoIND;
	}

	public String getIdSubcuenta() {
		return idSubcuenta;
	}

	public void setIdSubcuenta(String idSubcuenta) {
		this.idSubcuenta = idSubcuenta;
	}

	public Double getLecturaMedidor() {
		return lecturaMedidor;
	}

	public void setLecturaMedidor(Double lecturaMedidor) {
		this.lecturaMedidor = lecturaMedidor;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdEstracto() {
		return idEstracto;
	}

	public void setIdEstracto(Integer idEstracto) {
		this.idEstracto = idEstracto;
	}

	public Integer getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}

	public Integer getIdNovedadLectura() {
		return idNovedadLectura;
	}

	public void setIdNovedadLectura(Integer idNovedadLectura) {
		this.idNovedadLectura = idNovedadLectura;
	}

	public Double getLecturaAnterior() {
		return lecturaAnterior;
	}

	public void setLecturaAnterior(Double lecturaAnterior) {
		this.lecturaAnterior = lecturaAnterior;
	}
	
	
}
























