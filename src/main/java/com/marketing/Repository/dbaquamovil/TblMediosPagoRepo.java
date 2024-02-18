package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMediosPago;

@Repository
public interface TblMediosPagoRepo extends JpaRepository<TblMediosPago, Integer>{

	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblMediosPago " +
			"WHERE tblMediosPago.idLocal = ?1 " +
			"ORDER BY tblMediosPago.nombreMedio ",
			nativeQuery = true)
	List<TblMediosPago> ListaMediosDePago(int idLocal);
}
