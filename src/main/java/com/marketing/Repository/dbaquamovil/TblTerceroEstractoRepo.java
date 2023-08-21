package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Projection.TblTercerosProjectionDTO;

@Repository
public interface TblTerceroEstractoRepo extends JpaRepository<TblTerceroEstracto, Integer> {

	@Query("SELECT t FROM TblTerceroEstracto t WHERE t.idLocal = ?1")
    List<TblTerceroEstracto> findByIdLocal(int idLocal);
}
