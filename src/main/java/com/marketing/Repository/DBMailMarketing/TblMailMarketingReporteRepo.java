package com.marketing.Repository.DBMailMarketing;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import com.marketing.Projection.PruebaDTO;
import com.marketing.Projection.TblMailMarketingReporteDTO;

@Repository
public interface TblMailMarketingReporteRepo extends JpaRepository<TblMailMarketingReporte, Integer> {

	@Query("SELECT r FROM TblMailMarketingReporte r WHERE r.idLocal = ?1 ORDER BY r.idReporte DESC")
	List<TblMailMarketingReporteDTO> findByIdLocal(int idLocal);
	
	@Query("SELECT r FROM TblMailMarketingReporte r WHERE r.idLocal = ?1 ORDER BY r.idReporte DESC")
	List<Map<String, Object>> findByIdLocal2(int idLocal);
	
	 @Query("SELECT r FROM TblMailMarketingReporte r WHERE r.idLocal = ?1 ORDER BY r.idReporte DESC")
	    Page<TblMailMarketingReporte> findByidLocal(int idLocal, Pageable pageable);
	
//	 Page<TblMailMarketingReporte> findByIdLocalOrderByidReporteDesc(int idLocal, Pageable pageable);
	

}
