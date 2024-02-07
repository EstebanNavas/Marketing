package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Projection.TblCategoriasDTO;

@Repository
public interface TblCategoriasRepo extends JpaRepository<TblCategorias, Integer> {

	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblCategorias " +
			"WHERE tblCategorias.idLocal = ?1 " +
			"ORDER BY tblCategorias.nombreCategoria ",
			nativeQuery = true)
	List<TblCategorias> ListaCategorias(int idLocal);
	
	@Query(value = "SELECT  tblPlus.idLocal, tblPlus.IDPLU, tblCategorias.nombreCategoria + ' ' + tblPlus.nombrePlu AS nombrePlu, tblPlus.idEstracto, " + 
					"tblPlus.idTIPO, tblPlus.vrGeneral, tblPlus.porcentajeIva, tblPlus.topeMaximo, tblPlus.rangoMaximo, tblPlus.vrCostoIND AS porcentajeSubCon " +
 			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblPlus " +	
 			"ON tblCategorias.idLocal = tblPlus.idLocal " +
			"AND tblCategorias.IDLINEA = tblPlus.IDLINEA " +
 			"AND tblCategorias.IdCategoria = tblPlus.IdCategoria " +
			"WHERE tblCategorias.idLocal = ?1 " +
 			"AND tblPlus.IdCategoria= ?2 " +
			"ORDER BY tblCategorias.nombreCategoria ",
			nativeQuery = true)
	List<TblCategoriasDTO> ObtenerReferenciasPorCategoria(int idLocal, int idCategoria);
	
	@Query(value = "SELECT  tblPlus.idLocal, tblPlus.IDPLU, tblCategorias.nombreCategoria + ' ' + tblPlus.nombrePlu AS nombrePlu, tblPlus.idEstracto, " + 
			"tblPlus.idTIPO, tblPlus.vrGeneral, tblPlus.porcentajeIva, tblPlus.topeMaximo, tblPlus.rangoMaximo, tblPlus.vrCostoIND AS porcentajeSubCon " +
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblPlus " +	
			"ON tblCategorias.idLocal = tblPlus.idLocal " +
			"AND tblCategorias.IDLINEA = tblPlus.IDLINEA " +
			"AND tblCategorias.IdCategoria = tblPlus.IdCategoria " +
			"WHERE tblCategorias.idLocal = ?1 " +
			"ORDER BY tblCategorias.nombreCategoria ",
			nativeQuery = true)
    List<TblCategoriasDTO> ObtenerTodasLasReferencias(int idLocal);
	
}




















