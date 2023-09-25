package com.marketing.Repository.dbaquamovil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Model.dbaquamovil.TblTipoCausaNota;

@Repository
public interface TblPlusRepo extends JpaRepository<TblPlus, Integer>{

	
	@Query(value = "SELECT tblPlus.* " + 
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblPlus " +
			"ON tblCategorias.idLocal = tblPlus.idLocal " +
			"AND tblCategorias.idCategoria = tblPlus.idCategoria " +
			"AND tblCategorias.idLinea = tblPlus.idLinea " +
			"WHERE tblCategorias.idLocal = ?1 " +
			"AND   tblCategorias.idLinea = 200 " +
			"AND   tblCategorias.idCategoria = ?2", 
			nativeQuery = true)
	ArrayList<TblPlus> ObtenerCategorias(int idLocal, int idCategoria);
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblPlus " +
			"WHERE tblPlus.idLocal = ?1 " +
			"AND   tblPlus.idLinea = 200 ",
			nativeQuery = true)
	List<TblPlus> ObtenerNombrePluAndIdPlu(int idLocal);
}
