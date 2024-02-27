package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblLocalesReporte;
import com.marketing.Projection.TblTercerosRutaDTO;

@Repository
public interface TblLocalesReporteRepo extends JpaRepository<TblLocalesReporte, Integer>{

	@Query("SELECT t.fileName FROM TblLocalesReporte t " +
			"WHERE t.idLocal = :idLocal " + 
			"AND t.idReporte = :idReporte")
			String nombreReporte(@Param("idLocal") int idLocal, @Param("idReporte") int idReporte);
	
	
	
	@Query(value = "SELECT  * " + 
			"FROM bdaquamovil.dbo.tblLocalesReporte " +
			"WHERE tblLocalesReporte.idLocal = ?1 " +
			"AND tblLocalesReporte.idReporte = ?2 ",
			nativeQuery = true)
	List<TblLocalesReporte> listaUnFCH(int idLocal, int idReporte);
}
