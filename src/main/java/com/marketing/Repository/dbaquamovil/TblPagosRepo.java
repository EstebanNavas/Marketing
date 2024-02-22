package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblPagos;

@Repository
public interface TblPagosRepo extends JpaRepository<TblPagos, Integer> {

	
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM tblpagos " +
	                 "WHERE tblpagos.idLocal  =  ?1 " +
	                 "AND  tblpagos.idTipoOrden IN (7,9,29, 59) " +
	                 "AND  tblpagos.idPeriodo  = ?2 ", nativeQuery = true)
	  public void retiraPago(int idLocal, int idPeriodo);
}
