package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblMedidores;


@Repository
public interface TblMedidoresRepo extends JpaRepository<TblMedidores, Integer> {
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblMedidores " +
			"WHERE tblMedidores.idLocal = ?1 " +
			"ORDER BY tblMedidores.marcaMedidor ",
			nativeQuery = true)
	List<TblMedidores> ListaMedidores(int idLocal);

}
