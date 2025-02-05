package com.marketing.Repository.DBMailMarketing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblPucAux;

@Repository
public interface TblPucAuxRepo extends JpaRepository<TblPucAux, Integer> {
	
	@Query(value = "SELECT * " +
            "FROM BDMailMarketing.dbo.tblPucAux " +
            "WHERE tblPucAux. idLocal = ?1 ",
            nativeQuery = true)
	 List<TblPucAux> listaTodosAuxiliares(int idLocal);

}
