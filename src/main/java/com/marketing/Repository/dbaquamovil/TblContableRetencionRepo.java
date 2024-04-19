package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblContableRetencion;
import com.marketing.Model.dbaquamovil.TblLocalesReporte;

@Repository
public interface TblContableRetencionRepo extends JpaRepository<TblContableRetencion, Integer> {
	
	
	@Query(value = "SELECT tblcontableretencion.idConcepto,          " +
            "       tblcontableretencion.nombreConcepto,      " +
            "       tblcontableretencion.porcentajeRetencion, " +
            "       tblcontableretencion.vrBaseRetencion,     " +
            "       tblcontableretencion.estado               " +
            "FROM tblcontableretencion                        " +
            "WHERE idConcepto                             =   " +
            "?1                                  " +
            "AND tblcontableretencion.vrBaseRetencion    <=  ?2 ",
			nativeQuery = true)
	List<TblContableRetencion> calculaRetencion(int IdConcepto, Double VrBase);

}
