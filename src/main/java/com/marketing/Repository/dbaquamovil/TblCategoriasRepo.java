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
			"AND tblCategorias.idLinea = 1 " +
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
 			"AND tblPlus.idLinea= 1 " +
			"ORDER BY tblCategorias.nombreCategoria ",
			nativeQuery = true)
	List<TblCategoriasDTO> ObtenerReferenciasPorCategoria(int idLocal, int idCategoria);
	
	@Query(value = "SELECT  tblPlus.idLocal, tblPlus.IDPLU, tblCategorias.nombreCategoria + ' ' + tblPlus.nombrePlu AS nombrePlu, tblPlus.idEstracto, " + 
				"tblPlus.idTIPO, tblPlus.vrGeneral, tblPlus.porcentajeIva, tblPlus.topeMaximo, tblPlus.rangoMaximo, tblPlus.vrCostoIND AS porcentajeSubCon, tblPlus.idCategoria, tblPlus.vrCostoIND " +
				"FROM bdaquamovil.dbo.tblCategorias " +
				"JOIN bdaquamovil.dbo.tblPlus " +	
				"ON tblCategorias.idLocal = tblPlus.idLocal " +
				"AND tblCategorias.IDLINEA = tblPlus.IDLINEA " +
				"AND tblCategorias.IdCategoria = tblPlus.IdCategoria " +
				"WHERE tblCategorias.idLocal = ?1 " +
				"AND tblPlus.IDPLU= ?2 " +
				"ORDER BY tblCategorias.nombreCategoria ",
	nativeQuery = true)
	List<TblCategoriasDTO> ObtenerReferenciasPorIdPlu(int idLocal, int IDPLU);
	
	@Query(value = "SELECT  tblPlus.idLocal, tblPlus.IDPLU, tblCategorias.nombreCategoria + ' ' + tblPlus.nombrePlu AS nombrePlu, tblPlus.idEstracto, " + 
			"tblPlus.idTIPO, tblPlus.vrGeneral, tblPlus.porcentajeIva, tblPlus.topeMaximo, tblPlus.rangoMaximo, tblPlus.vrCostoIND AS porcentajeSubCon, tblPlus.vrCostoIND " +
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblPlus " +	
			"ON tblCategorias.idLocal = tblPlus.idLocal " +
			"AND tblCategorias.IDLINEA = tblPlus.IDLINEA " +
			"AND tblCategorias.IdCategoria = tblPlus.IdCategoria " +
			"WHERE tblCategorias.idLocal = ?1 " +
			"ORDER BY tblCategorias.nombreCategoria ",
			nativeQuery = true)
    List<TblCategoriasDTO> ObtenerTodasLasReferencias(int idLocal);
	
	@Query(value = "SELECT tblCategorias.idLinea " + 
			"FROM bdaquamovil.dbo.tblCategorias " +
			"WHERE tblCategorias.idLocal = ?1 " +
			"AND idcategoria = ?2 ",
			nativeQuery = true)
	Integer ObtenerIdLinea(int idLocal, int idCategoria);
	
	@Query(value = "SELECT tblCategorias.nombreCategoria " + 
			"FROM bdaquamovil.dbo.tblCategorias " +
			"WHERE tblCategorias.idLocal = ?1 " +
			"AND idlinea = 1 ",
			nativeQuery = true)
	List<String> ObtenerListaNombresCategorias(int idLocal);
	
	@Query(value = "SELECT tblLineas.idLinea, MAX(tblLineas.nombreLinea) AS nombreLinea " + 
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblLineas " +
			"ON tblCategorias.idLocal = tblLineas.idLocal " + 
			"AND tblCategorias.idLinea = tblLineas.idLinea " + 
			"WHERE tblCategorias.idLocal = ?1 " +
			"GROUP BY tblLineas.idLinea " +
			"ORDER BY 2 ",
			nativeQuery = true)
	List<TblCategoriasDTO> ObtenerNombresLineas(int idLocal);
	
	@Query(value = "SELECT tblCategorias.idLocal, tblCategorias.idLinea, tblCategorias.nombreCategoria, tblCategorias.IdCategoria, tblCategorias.idProducto " + 
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblLineas " +
			"ON tblCategorias.idLocal = tblLineas.idLocal " + 
			"AND tblCategorias.idLinea = tblLineas.idLinea " + 
			"WHERE tblLineas.idLocal = ?1 " +
			"AND tblCategorias.idLinea= ?2 " +
			"ORDER BY 3 ",
			nativeQuery = true)
	List<TblCategoriasDTO> ObtenerCategoriasPorLinea(int idLocal, int idLinea );
	
	
	@Query(value = "SELECT MAX(c.IdCategoria) FROM tblCategorias c " + 
			"WHERE c.idLocal = ?1 " +
			"AND c.idLinea = ?2 ",
			nativeQuery = true)
	Integer maximoIdCategoria(int idLocal, int idLinea);
	
}




















