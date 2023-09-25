package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblDctos;

@Repository
public interface TblDctosRepo extends JpaRepository<TblDctos, Integer> {

	@Query("SELECT MAX(r.idDcto) FROM TblDctos r " +
			  "WHERE r.IDLOCAL = ?1 " +
			  "AND r.IDTIPOORDEN = 17 ")
	    Integer findMaxIdDcto(int IDLOCAL);
}
