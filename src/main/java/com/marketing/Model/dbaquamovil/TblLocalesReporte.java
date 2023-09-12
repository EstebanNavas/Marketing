package com.marketing.Model.dbaquamovil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "tblLocalesReporte")
public class TblLocalesReporte {

	@Id
	@Column(name = "idReporte")
	private Integer idReporte;
	
	@Column(name = "idLocal")
	private Integer idLocal;
	
	
	@Column(name = "reporteNombre")
	private String reporteNombre;
	
	
	@Column(name = "fileName")
	private String fileName;


	public Integer getIdReporte() {
		return idReporte;
	}


	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}


	public Integer getIdLocal() {
		return idLocal;
	}


	public void setIdLocal(Integer idLocal) {
		this.idLocal = idLocal;
	}


	public String getReporteNombre() {
		return reporteNombre;
	}


	public void setReporteNombre(String reporteNombre) {
		this.reporteNombre = reporteNombre;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
