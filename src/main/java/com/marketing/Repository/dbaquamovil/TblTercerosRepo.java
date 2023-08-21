package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblTercerosProjectionDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TblTercerosRepo extends  JpaRepository<TblTerceros, Integer> {


	
//	@Query(value = "SELECT * FROM tblTerceros WHERE idLocal = ?1", nativeQuery = true)
//	List<TblTerceros> findByIdLocal(int idLocal);
	
	
//		@Query("SELECT t FROM TblTerceros t WHERE t.idLocal = ?1 AND t.idTipoTercero = 1 AND ISNUMERIC(t.telefonoCelular) = 1 AND LEN(t.telefonoCelular) = 10 ")
//	    List<TblTercerosProjectionDTO> findByIdLocal(int idLocal);
		
		
	  @Query("SELECT DISTINCT t FROM TblTerceros t " +
	            "JOIN FETCH t.terceroEstracto te " +
	            "JOIN FETCH t.terceroRuta tr " +
	            "WHERE t.idLocal = ?1 " +
	            "AND t.idLocal = te.idLocal "+
	            "AND  t.terceroEstracto.idEstracto = te.idEstracto " +
	            
	            "AND t.idLocal = tr.idLocal "+
	            "AND  t.terceroRuta.idRuta = tr.idRuta " +
	            
	            "AND t.idTipoTercero = 1 " +
	            "AND ISNUMERIC(t.telefonoCelular) = 1 " +
	            "AND LEN(t.telefonoCelular) = 10 " +
	            "ORDER BY t.nombreTercero")
	     List<TblTercerosProjectionDTO> findByIdLocal(int idLocal);
    
    
		
	
}
