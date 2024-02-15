package com.marketing.Model.dbaquamovil;

import java.util.List;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
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
	
//	@ManyToOne(fetch = FetchType.LAZY) // Se establece la relacion de muchos a uno con la tabla TblTerceroEstracto
//	@JoinColumn(name = "idEstracto")
//	private TblTerceroEstracto terceroEstracto;
	

	
	@Column(name ="idTercero")
	private Integer idTercero;
	
	@Column(name ="idRuta")
	private Integer idRuta;
	

	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumns({
//	    @JoinColumn(name = "idRuta", referencedColumnName = "idRuta"),
//	    @JoinColumn(name = "idLocal", referencedColumnName = "idLocal")
//	})
//	private TblTercerosRuta terceroRuta;
	
	
//	@ManyToOne(fetch = FetchType.LAZY) // Se establece la relacion de muchos a uno con la tabla TblTercerosRuta
//	@JoinColumn(name = "idRuta")
//	private TblTercerosRuta terceroRuta;

	
	
	@Column(name="tipoIdTercero")
	private String tipoIdTercero;
	
	@Column(name ="digitoVerificacion")
	private Integer digitoVerificacion;
	
	@Column(name ="idPersona")
	private Integer idPersona;
	
	@Column(name ="idAutoRetenedor")
	private Integer idAutoRetenedor;
	
	@Column(name="idRegimen")
	private String idRegimen;

	@Column(name="nombreTercero")
	private String nombreTercero;
		
	@Column(name="direccionTercero")
	private String direccionTercero;
	
	@Column(name ="idDptoCiudad")
	private Integer idDptoCiudad;
	
	@Column(name="telefonoFijo")
	private String telefonoFijo;
	
	@Column(name="telefonoCelular")
	private String telefonoCelular;

	@Column(name="telefonoFax")
	private String telefonoFax;
	
	@Column(name="email")
	private String email;
	
	@Column(name ="idFormaPago")
	private Integer idFormaPago;
	
	@Column(name ="estado")
	private Integer estado;
	
	@Column(name ="nombreEmpresa")
	private String nombreEmpresa;
	
	@Column(name ="cupoCredito")
	private Integer cupoCredito;
	
	@Column(name ="indicador")
	private Integer indicador;
	
	@Column(name ="ciudadTercero")
	private String ciudadTercero;
	
	@Column(name ="contactoTercero")
	private String contactoTercero;
	
	@Column(name ="idListaPrecio")
	private Integer idListaPrecio;
	
	@Column(name ="idVendedor")
	private Float idVendedor;
	
	@Column(name ="idSeq")
	private Integer idSeq;
	
	@Column(name ="idEstracto")
	private Integer idEstracto;
	
	@Column(name ="cuotaVencida")
	private Float cuotaVencida;
	
	@Column(name ="promedio")
	private Integer promedio;
	
	@Column(name ="ordenRuta")
	private Integer ordenRuta;
	
	@Column(name ="direccionCobro")
	private String direccionCobro;
	
	@Column(name ="CC_Nit")
	private String CC_Nit;
	
	@Column(name ="cuentaDerecho")
	private Integer cuentaDerecho;
	
	@Column(name ="codigoAlterno")
	private String codigoAlterno;
	
	@Column(name ="numeroMedidor")
	private String numeroMedidor;
	
	@Column(name ="historiaConsumo")
	private String historiaConsumo;
	
	@Column(name ="idMedidor")
	private Integer idMedidor;
	
	
	@Column(name ="idMacro")
	private Integer idMacro;
	
	
	@Column(name ="estadoMedidor")
	private Integer estadoMedidor;
	
	@Column(name ="estadoCorte")
	private Integer estadoCorte;
	
	@Column(name ="estadoEmail")
	private Integer estadoEmail;
	
	@Column(name ="codigoCatastral")
	private String codigoCatastral;
	
	@Column(name ="matricula")
	private String matricula;
	
	@Column(name ="estadoCarta")
	private Integer estadoCarta;
	
	@Column(name ="responsableEconomico")
	private String responsableEconomico;
	
	@Column(name ="fechaIngreso")
	private Timestamp fechaIngreso;
	
	@Column(name ="promedioEstrato")
	private Integer promedioEstrato;
	
	@Column(name ="fechaInstalacionMedidor")
	private Timestamp fechaInstalacionMedidor;
	
	@Column(name ="tipoSuscriptor")
	private Integer tipoSuscriptor;
	
	
	
	

	



	public Integer getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
	}

	public String getTipoIdTercero() {
		return tipoIdTercero;
	}

	public void setTipoIdTercero(String tipoIdTercero) {
		this.tipoIdTercero = tipoIdTercero;
	}

	public Integer getDigitoVerificacion() {
		return digitoVerificacion;
	}

	public void setDigitoVerificacion(Integer digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public Integer getIdAutoRetenedor() {
		return idAutoRetenedor;
	}

	public void setIdAutoRetenedor(Integer idAutoRetenedor) {
		this.idAutoRetenedor = idAutoRetenedor;
	}

	public String getIdRegimen() {
		return idRegimen;
	}

	public void setIdRegimen(String idRegimen) {
		this.idRegimen = idRegimen;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoFax() {
		return telefonoFax;
	}

	public void setTelefonoFax(String telefonoFax) {
		this.telefonoFax = telefonoFax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdFormaPago() {
		return idFormaPago;
	}

	public void setIdFormaPago(Integer idFormaPago) {
		this.idFormaPago = idFormaPago;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public Integer getCupoCredito() {
		return cupoCredito;
	}

	public void setCupoCredito(Integer cupoCredito) {
		this.cupoCredito = cupoCredito;
	}

	public Integer getIndicador() {
		return indicador;
	}

	public void setIndicador(Integer indicador) {
		this.indicador = indicador;
	}

	public String getCiudadTercero() {
		return ciudadTercero;
	}

	public void setCiudadTercero(String ciudadTercero) {
		this.ciudadTercero = ciudadTercero;
	}

	public String getContactoTercero() {
		return contactoTercero;
	}

	public void setContactoTercero(String contactoTercero) {
		this.contactoTercero = contactoTercero;
	}

	public Integer getIdListaPrecio() {
		return idListaPrecio;
	}

	public void setIdListaPrecio(Integer idListaPrecio) {
		this.idListaPrecio = idListaPrecio;
	}

	public Float getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Float idVendedor) {
		this.idVendedor = idVendedor;
	}

	public Integer getIdSeq() {
		return idSeq;
	}

	public Integer getIdEstracto() {
		return idEstracto;
	}

	public void setIdEstracto(Integer idEstracto) {
		this.idEstracto = idEstracto;
	}

	public void setIdSeq(Integer idSeq) {
		this.idSeq = idSeq;
	}

//	public Integer getIdEstracto() {
//		return idEstracto;
//	}
//
//	public void setIdEstracto(Integer idEstracto) {
//		this.idEstracto = idEstracto;
//	}

	public Float getCuotaVencida() {
		return cuotaVencida;
	}

	public void setCuotaVencida(Float cuotaVencida) {
		this.cuotaVencida = cuotaVencida;
	}

	public Integer getPromedio() {
		return promedio;
	}

	public void setPromedio(Integer promedio) {
		this.promedio = promedio;
	}

	public Integer getOrdenRuta() {
		return ordenRuta;
	}

	public void setOrdenRuta(Integer ordenRuta) {
		this.ordenRuta = ordenRuta;
	}

	public String getDireccionCobro() {
		return direccionCobro;
	}

	public void setDireccionCobro(String direccionCobro) {
		this.direccionCobro = direccionCobro;
	}

	public String getCC_Nit() {
		return CC_Nit;
	}

	public void setCC_Nit(String cC_Nit) {
		CC_Nit = cC_Nit;
	}

	public Integer getCuentaDerecho() {
		return cuentaDerecho;
	}

	public void setCuentaDerecho(Integer cuentaDerecho) {
		this.cuentaDerecho = cuentaDerecho;
	}

	public String getCodigoAlterno() {
		return codigoAlterno;
	}

	public void setCodigoAlterno(String codigoAlterno) {
		this.codigoAlterno = codigoAlterno;
	}

	public String getNumeroMedidor() {
		return numeroMedidor;
	}

	public void setNumeroMedidor(String numeroMedidor) {
		this.numeroMedidor = numeroMedidor;
	}

	public String getHistoriaConsumo() {
		return historiaConsumo;
	}

	public void setHistoriaConsumo(String historiaConsumo) {
		this.historiaConsumo = historiaConsumo;
	}

	public Integer getIdMedidor() {
		return idMedidor;
	}

	public void setIdMedidor(Integer idMedidor) {
		this.idMedidor = idMedidor;
	}

	public Integer getIdMacro() {
		return idMacro;
	}

	public void setIdMacro(Integer idMacro) {
		this.idMacro = idMacro;
	}

	public Integer getEstadoMedidor() {
		return estadoMedidor;
	}

	public void setEstadoMedidor(Integer estadoMedidor) {
		this.estadoMedidor = estadoMedidor;
	}

	public Integer getEstadoCorte() {
		return estadoCorte;
	}

	public void setEstadoCorte(Integer estadoCorte) {
		this.estadoCorte = estadoCorte;
	}

	public Integer getEstadoEmail() {
		return estadoEmail;
	}

	public void setEstadoEmail(Integer estadoEmail) {
		this.estadoEmail = estadoEmail;
	}

	public String getCodigoCatastral() {
		return codigoCatastral;
	}

	public void setCodigoCatastral(String codigoCatastral) {
		this.codigoCatastral = codigoCatastral;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Integer getEstadoCarta() {
		return estadoCarta;
	}

	public void setEstadoCarta(Integer estadoCarta) {
		this.estadoCarta = estadoCarta;
	}

	public String getResponsableEconomico() {
		return responsableEconomico;
	}

	public void setResponsableEconomico(String responsableEconomico) {
		this.responsableEconomico = responsableEconomico;
	}

	public Timestamp getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Timestamp fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Integer getPromedioEstrato() {
		return promedioEstrato;
	}

	public void setPromedioEstrato(Integer promedioEstrato) {
		this.promedioEstrato = promedioEstrato;
	}

	public Timestamp getFechaInstalacionMedidor() {
		return fechaInstalacionMedidor;
	}

	public void setFechaInstalacionMedidor(Timestamp fechaInstalacionMedidor) {
		this.fechaInstalacionMedidor = fechaInstalacionMedidor;
	}

	public Integer getTipoSuscriptor() {
		return tipoSuscriptor;
	}

	public void setTipoSuscriptor(Integer tipoSuscriptor) {
		this.tipoSuscriptor = tipoSuscriptor;
	}

//	public void setTerceroRuta(TblTercerosRuta terceroRuta) {
//		this.terceroRuta = terceroRuta;
//	}

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

	public Integer getIdTipoTercero() {
		return idTipoTercero;
	}

	public void setIdTipoTercero(Integer idTipoTercero) {
		this.idTipoTercero = idTipoTercero;
	}

	public Integer getIdTercero() {
		return idTercero;
	}

	public void setIdTercero(Integer idTercero) {
		this.idTercero = idTercero;
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

//	public TblTerceroEstracto getTerceroEstracto() {
//		return terceroEstracto;
//	}
	
//	public TblTercerosRuta getTerceroRuta() {
//		return terceroRuta;
//	}


//	public void setterceroestracto(tblterceroestracto terceroestracto) {
//		this.terceroestracto = terceroestracto;
//	}



	public String getTelefonoCelular() {
		return telefonoCelular;
	}

	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}

	

	
	
	
}





























