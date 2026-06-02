package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblLineas;
import com.marketing.Projection.TblCategoriasDTO;

@Repository
public interface TblLineasRepo extends JpaRepository<TblLineas, Integer> {

	@Query(value = "SELECT  idLocal                       "
			+ "      ,idLinea                        "
			+ "      ,nombreLinea                    "
			+ "      ,estado                         "
			+ "      ,idSeq                          "
			+ "  FROM bdaquamovil.dbo.tblLineas      "
			+ "  where idlocal = ?1                 ",
			nativeQuery = true)
	List<TblLineas> listaLineasLocal(int idLocal);

}
