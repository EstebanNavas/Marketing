package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblMedidoresMacro;

@Repository
public interface TblMedidoresMacroRepo extends JpaRepository<TblMedidoresMacro, Integer> {

	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblMedidoresMacro " +
			"WHERE tblMedidoresMacro.idLocal = ?1 " +
			"ORDER BY tblMedidoresMacro.nombreMacro DESC ",
			nativeQuery = true)
	List<TblMedidoresMacro> ListaMedidoresMacro(int idLocal);
}
