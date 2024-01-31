package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;

@Repository
public interface TblTercerosRutaRepo extends JpaRepository<TblTercerosRuta, Integer> {
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblTercerosRuta " +
			"WHERE tblTercerosRuta.idLocal = ?1 " +
			"ORDER BY tblTercerosRuta.nombreRuta ",
			nativeQuery = true)
	List<TblTercerosRuta> ListaRutas(int idLocal);

}
