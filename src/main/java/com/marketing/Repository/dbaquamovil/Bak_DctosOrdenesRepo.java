package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.Bak_DctosOrdenes;

@Repository
public interface Bak_DctosOrdenesRepo extends JpaRepository<Bak_DctosOrdenes, Integer> {
	
	
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM bak_DctosOrdenesDetalle " +
	                 "FROM bak_DctosOrdenes " +
	                 "JOIN bak_DctosOrdenesDetalle " +
	                 "ON bak_DctosOrdenes.IDLOCAL  =  bak_DctosOrdenesDetalle.IDLOCAL " +
	                 "AND bak_DctosOrdenes.IDTIPOORDEN =  bak_DctosOrdenesDetalle.IDTIPOORDEN " +
	                 "AND bak_DctosOrdenes.IDORDEN  =  bak_DctosOrdenesDetalle.IDORDEN " +
	                 "WHERE bak_DctosOrdenes.IDLOCAL   =  ?1 " +
	                 "AND bak_DctosOrdenes.IDTIPOORDEN IN (7,9,29,59) ", nativeQuery = true)
	  public void retiraOrdenesDetalle(int idLocal);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM  bak_DctosOrdenes " +
	                 "WHERE  IDLOCAL  = ?1 " +
	                 "AND IDTIPOORDEN  IN (7,9,29, 59) ", nativeQuery = true)
	  public void retiraOrdenes(int idLocal);

}
