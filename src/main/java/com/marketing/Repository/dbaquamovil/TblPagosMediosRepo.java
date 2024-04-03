package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblPagosMedios;

@Repository
public interface TblPagosMediosRepo extends JpaRepository<TblPagosMedios, Integer> {

	
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM tblpagosmedios " +
	                 "FROM tblpagos " +
	                 "JOIN tblpagosmedios " +
	                 "ON tblpagos.idLocal = tblpagosmedios.idLocal " +
	                 "AND tblpagos.idTipoOrden =  tblpagosmedios.idTipoOrden " +
	                 "AND tblpagos.idRecibo  =  tblpagosmedios.idRecibo " +
	                 "AND tblpagos.indicador  =  tblpagosmedios.indicador " +
	                 "WHERE tblpagos.idLocal   =   ?1 " +
	                 "AND  tblpagos.idTipoOrden IN (7,9,29, 59) " +
	                 "AND  tblpagos.idPeriodo  =   ?2 ", nativeQuery = true)
	  public void retiraMedio(int idLocal, int idPeriodo);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = " DELETE FROM  tblpagosmedios      "
              + " WHERE tblpagosmedios.idLocal   = "
              + "?1                   "
              + " AND tblpagosmedios.idTipoOrden = ?2 ",
              nativeQuery = true)
	  public void retiraTemporal(int idLocal, int idTipoOrden);
}
