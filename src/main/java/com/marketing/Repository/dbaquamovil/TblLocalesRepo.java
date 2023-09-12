package com.marketing.Repository.dbaquamovil;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.marketing.Model.dbaquamovil.TblLocales;

public interface TblLocalesRepo extends JpaRepository<TblLocales, Integer> {
	Optional<TblLocales> findByIdLocal(Integer idLocal); // Buscamos el idLocal
	
	@Query("SELECT t.pathReport FROM TblLocales t " +
	"WHERE t.idLocal = :idLocal ")
	String rutaReporte(@Param("idLocal") int idLocal);
}
