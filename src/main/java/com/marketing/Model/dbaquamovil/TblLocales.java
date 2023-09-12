package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tblLocales")
public class TblLocales {

	@Id
	@Column(name = "IDLOCAL")
	private Integer idLocal;
	
	@Column(name = "RAZONSOCIAL", columnDefinition = "nvarchar")
	private String razonSocial;
	
	@Column(name = "nombreLocal")
	private String nombreLocal;
	
	@Column(name = "pathReport")
	private String pathReport;
	

	public Integer getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreLocal() {
		return nombreLocal;
	}

	public void setNombreLocal(String nombreLocal) {
		this.nombreLocal = nombreLocal;
	}

	
	public String getPathReport() {
		return pathReport;
	}

	public void setPathReport(String pathReport) {
		this.pathReport = pathReport;
	}


}
