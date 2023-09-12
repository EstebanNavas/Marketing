package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblLocalesReporte;

@Repository
public interface TblLocalesReporteRepo extends JpaRepository<TblLocalesReporte, Integer>{

	@Query("SELECT t.fileName FROM TblLocalesReporte t " +
			"WHERE t.idLocal = :idLocal " + 
			"AND t.idReporte = :idReporte")
			String nombreReporte(@Param("idLocal") int idLocal, @Param("idReporte") int idReporte);
}
