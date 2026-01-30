package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Projection.TblCategoriasDTO;

@Repository
public interface TblCategoriasRepo extends JpaRepository<TblCategorias, Integer> {

	@Query(value = "       SELECT tblCategorias.idLocal,                       "
			+ "		       tblCategorias.idLinea,                          "
			+ "			   tblCategorias.IdCategoria,                      "
			+ "			   tblCategorias.nombreCategoria,                  "
			+ "			   tblCategorias.idProducto,                       "
			+ "			   tblLineas.nombreLinea                           "
			+ "		FROM bdaquamovil.dbo.tblCategorias                     "
			+ "		INNER JOIN bdaquamovil.dbo.tblLineas                   "
			+ "		ON tblCategorias.idLocal = tblLineas.idLocal           "
			+ "		AND tblCategorias.idLinea = tblLineas.idLinea          "
			+ "		WHERE tblCategorias.idLocal = ?1                       "
			+ "		AND tblCategorias.idLinea IN (1, 100, 300)             "
			+ "		order by 2 , 4                                         ",
			nativeQuery = true)
	List<TblCategoriasDTO> ListaCategorias(int idLocal);
	

	
	
	@Query(value = "       SELECT tblCategorias.idLocal,                       "
			+ "		       tblCategorias.idLinea,                          "
			+ "			   tblCategorias.IdCategoria,                      "
			+ "			   tblCategorias.nombreCategoria,                  "
			+ "			   tblCategorias.idProducto,                       "
			+ "			   tblLineas.nombreLinea                           "
			+ "		FROM bdaquamovil.dbo.tblCategorias                     "
			+ "		INNER JOIN bdaquamovil.dbo.tblLineas                   "
			+ "		ON tblCategorias.idLocal = tblLineas.idLocal           "
			+ "		AND tblCategorias.idLinea = tblLineas.idLinea          "
			+ "		WHERE tblCategorias.idLocal = ?1                       "
			+ "		AND tblCategorias.idLinea IN (2)                       "
			+ "		order by 2 , 4                                         ",
			nativeQuery = true)
	List<TblCategoriasDTO> ListaCategoriasInventario(int idLocal);
	
	@Query(value = "       SELECT tblCategorias.idLocal,                       "
			+ "		       tblCategorias.idLinea,                          "
			+ "			   tblCategorias.IdCategoria,                      "
			+ "			   tblCategorias.nombreCategoria,                  "
			+ "			   tblCategorias.idProducto,                       "
			+ "			   tblLineas.nombreLinea                           "
			+ "		FROM bdaquamovil.dbo.tblCategorias                     "
			+ "		INNER JOIN bdaquamovil.dbo.tblLineas                   "
			+ "		ON tblCategorias.idLocal = tblLineas.idLocal           "
			+ "		AND tblCategorias.idLinea = tblLineas.idLinea          "
			+ "		WHERE tblCategorias.idLocal = ?1                       "
			+ "		AND tblCategorias.idLinea = 300                        "
			+ "		order by 2 , 4                                         ",
			nativeQuery = true)
	List<TblCategoriasDTO> ListaCategoriasLinea300(int idLocal);
	
	@Query(value = "           SELECT  tblPlus.idLocal,                                                 "          
	          + "             tblPlus.IDPLU,                                                         "          
			  + "          	 tblCategorias.nombreCategoria + ' ' + tblPlus.nombrePlu AS nombrePlu,   "          
			  + "          	 tblPlus.idEstracto,                                                     "          
			  + "          	 tblPlus.idTIPO,                                                         "          
			  + "          	 tblPlus.vrGeneral,                                                      "          
			  + "          	 tblPlus.porcentajeIva,                                                  "          
			  + "          	 tblPlus.topeMaximo,                                                     "          
			  + "          	 tblPlus.rangoMaximo,                                                    "          
			  + "          	 tblPlus.vrCostoIND AS porcentajeSubCon, tblPlus.idLinea,                "          
			  + "          	 CASE                                                                    "          
	          + "              WHEN tblPlus.cuentaContableDebito IS NULL                             "          
	          + "              OR LTRIM(RTRIM(tblPlus.cuentaContableDebito)) = ''                    "          
	          + "              OR LTRIM(RTRIM(tblPlus.cuentaContableDebito)) = 'null'                "          
	          + "              OR tblPlus.cuentaContableDebito = '0'                                 "          
	          + "              THEN 'Sin asignar'                                                    "          
	          + "              ELSE tblPlus.cuentaContableDebito                                     "          
	          + "              END AS cuentaContableDebito,                                          "          
			  + "          	 CASE                                                                    "          
	          + "              WHEN tblPlus.cuentaContableCredito IS NULL                            "          
	          + "              OR LTRIM(RTRIM(tblPlus.cuentaContableCredito)) = ''                   "          
	          + "              OR LTRIM(RTRIM(tblPlus.cuentaContableCredito)) = 'null'               "          
	          + "              OR tblPlus.cuentaContableCredito = '0'                                "          
	          + "              THEN 'Sin asignar'                                                    "          
	          + "              ELSE tblPlus.cuentaContableCredito                                    "          
	          + "              END AS cuentaContableCredito,                                         "          
			  + "          	 CASE                                                                    "          
	          + "              WHEN tblPlus.cuentaRecaudoDebito IS NULL                              "          
	          + "              OR LTRIM(RTRIM(tblPlus.cuentaRecaudoDebito)) = ''                     "          
	          + "              OR LTRIM(RTRIM(tblPlus.cuentaRecaudoDebito)) = 'null'                 "          
	          + "              OR tblPlus.cuentaRecaudoDebito = '0'                                  "          
	          + "              THEN 'Sin asignar'                                                    "          
	          + "              ELSE tblPlus.cuentaRecaudoDebito                                      "          
	          + "              END AS cuentaRecaudoDebito,                                           "          
			  + "          	 CASE                                                                    "          
	          + "              WHEN tblPlus.cuentaRecaudoCredito IS NULL                             "          
	          + "              OR LTRIM(RTRIM(tblPlus.cuentaRecaudoCredito)) = ''                    "          
	          + "              OR LTRIM(RTRIM(tblPlus.cuentaRecaudoCredito)) = 'null'                "          
	          + "              OR tblPlus.cuentaRecaudoCredito = '0'                                 "          
	          + "              THEN 'Sin asignar'                                                    "          
	          + "              ELSE tblPlus.cuentaRecaudoCredito                                     "          
	          + "              END AS cuentaRecaudoCredito                                           "          
	 		  + "          	FROM bdaquamovil.dbo.tblCategorias                                       "          
			  + "          	JOIN bdaquamovil.dbo.tblPlus 	                                         "          
	 		  + "          	ON tblCategorias.idLocal = tblPlus.idLocal                               "          
			  + "          	AND tblCategorias.IDLINEA = tblPlus.IDLINEA                              "          
	 		  + "          	AND tblCategorias.IdCategoria = tblPlus.IdCategoria                      "          
			  + "          	WHERE tblCategorias.idLocal = ?1                                        "          
	 		  + "          	AND tblPlus.IdCategoria= ?2                                               "          
	 		  + "          	AND tblPlus.idLinea= ?3                                                   "          
			  + "          	ORDER BY tblCategorias.nombreCategoria                                   ",
			nativeQuery = true)
	List<TblCategoriasDTO> ObtenerReferenciasPorCategoria(int idLocal, int idCategoria, int idLinea);
	
	@Query(value = "SELECT  tblPlus.idLocal, tblPlus.IDPLU, tblCategorias.nombreCategoria + ' ' + tblPlus.nombrePlu AS nombrePlu, tblPlus.idEstracto, " + 
				"tblPlus.idTIPO, tblPlus.vrGeneral, tblPlus.porcentajeIva, tblPlus.topeMaximo, tblPlus.rangoMaximo, tblPlus.vrCostoIND AS porcentajeSubCon, tblPlus.idCategoria, tblPlus.vrCostoIND, " +
				"tblPlus.cuentaContableDebito, tblPlus.cuentaContableCredito, tblPlus.cuentaRecaudoDebito, tblPlus.cuentaRecaudoCredito, tblPlus.aviso " +
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
			"tblPlus.idTIPO, tblPlus.vrGeneral, tblPlus.porcentajeIva, tblPlus.topeMaximo, tblPlus.rangoMaximo, tblPlus.vrCostoIND AS porcentajeSubCon, tblPlus.vrCostoIND, " +
			"NULLIF(TRIM(tblPlus.cuentaContableDebito), '') AS cuentaContableDebito,  " + 
			"NULLIF(TRIM(tblPlus.cuentaContableCredito), '') AS cuentaContableCredito, "+
			"NULLIF(TRIM(tblPlus.cuentaRecaudoDebito), '') AS cuentaRecaudoDebito, "+
			"NULLIF(TRIM(tblPlus.cuentaRecaudoCredito), '') AS cuentaRecaudoCredito "+
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblPlus " +	
			"ON tblCategorias.idLocal = tblPlus.idLocal " +
			"AND tblCategorias.IDLINEA = tblPlus.IDLINEA " +
			"AND tblCategorias.IdCategoria = tblPlus.IdCategoria " +
			"WHERE tblCategorias.idLocal = ?1 " +
			"ORDER BY tblCategorias.nombreCategoria ",
			nativeQuery = true)
    List<TblCategoriasDTO> ObtenerTodasLasReferencias(int idLocal);
	
	
	@Query(value = "SELECT  tblPlus.idLocal, tblPlus.IDPLU, tblCategorias.nombreCategoria + ' ' + tblPlus.nombrePlu AS nombrePlu, tblPlus.idEstracto, " + 
			"tblPlus.idTIPO, tblPlus.vrGeneral, tblPlus.porcentajeIva, tblPlus.topeMaximo, tblPlus.rangoMaximo, tblPlus.vrCostoIND AS porcentajeSubCon, tblPlus.vrCostoIND " +
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblPlus " +	
			"ON tblCategorias.idLocal = tblPlus.idLocal " +
			"AND tblCategorias.IDLINEA = tblPlus.IDLINEA " +
			"AND tblCategorias.IdCategoria = tblPlus.IdCategoria " +
			"WHERE tblCategorias.idLocal = ?1 " +
			"AND tblCategorias.IDLINEA = 2 " +
			"ORDER BY tblCategorias.nombreCategoria ",
			nativeQuery = true)
    List<TblCategoriasDTO> ObtenerTodasLasReferenciasInventario(int idLocal);
	
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
	
	@Query(value = "SELECT tblLineas.idLinea, tblLineas.nombreLinea " + 
			"FROM tblLineas " +
			"WHERE tblLineas.idLocal = ?1 " +
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
	
	@Query(value = "SELECT tblCategorias.idLocal, tblCategorias.idLinea, tblCategorias.nombreCategoria, tblCategorias.IdCategoria, tblCategorias.idProducto " + 
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblLineas " +
			"ON tblCategorias.idLocal = tblLineas.idLocal " + 
			"AND tblCategorias.idLinea = tblLineas.idLinea " + 
			"WHERE tblLineas.idLocal = ?1 " +
			"ORDER BY 2 ",
			nativeQuery = true)
	List<TblCategoriasDTO> ObtenerTodasLasCategorias(int idLocal );
	
	
	@Query(value = "SELECT tblCategorias.idLocal, tblCategorias.idLinea, tblCategorias.nombreCategoria, tblCategorias.IdCategoria, tblCategorias.idProducto, tblLineas.nombreLinea " + 
			"FROM bdaquamovil.dbo.tblCategorias " +
			"JOIN bdaquamovil.dbo.tblLineas " +
			"ON tblCategorias.idLocal = tblLineas.idLocal " + 
			"AND tblCategorias.idLinea = tblLineas.idLinea " + 
			"WHERE tblLineas.idLocal = ?1 " +
			"AND tblCategorias.idLinea= ?2 " +
			"AND tblCategorias.IdCategoria= ?3 " +
			"ORDER BY 2 ",
			nativeQuery = true)
	List<TblCategoriasDTO> ObtenerCategoria(int idLocal, int idLinea, int IdCategoria );
	
	
	// Actualizamos La Categoria
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblCategorias SET nombreCategoria = ?1 " +

	                 "WHERE tblCategorias.idLocal = ?2 " +
	                 "AND tblCategorias.idLinea = ?3 " +
	                 "AND tblCategorias.IdCategoria = ?4 " 
	                 , nativeQuery = true)
	  public void actualizarCategoria(String nombreCategoria, int idLocal, int idLinea, int IdCategoria ) ;
	
	  
	  
	  @Query(value = "    SELECT                                               "
		        + "    tblCategorias.nombreCategoria,                          "
		        + "    tblPlus.idTIPO                                          "
		 		+ "	FROM bdaquamovil.dbo.tblCategorias                         "
				+ "	JOIN bdaquamovil.dbo.tblPlus 	                           "
		 		+ "	ON tblCategorias.idLocal = tblPlus.idLocal                 "
				+ "	AND tblCategorias.IDLINEA = tblPlus.IDLINEA                "
		 		+ "	AND tblCategorias.IdCategoria = tblPlus.IdCategoria        "
				+ "	WHERE tblCategorias.idLocal = ?1                           "
		 		+ "	AND tblPlus.idTipo iN (4,5,6,18,41)                        "
		 		+ "	AND tblPlus.idLinea= 1                                     "
				+ "	GROUP BY tblPlus.idTipo,                                   "
				+ "	tblCategorias.nombreCategoria                              ",
				nativeQuery = true)
		List<TblCategoriasDTO> ObtenerTipos(int idLocal );
	  
	  @Query(value = "    SELECT                                               "
		        + "    tblCategorias.nombreCategoria,                          "
		        + "    tblPlus.idTIPO                                          "
		 		+ "	FROM bdaquamovil.dbo.tblCategorias                         "
				+ "	JOIN bdaquamovil.dbo.tblPlus 	                           "
		 		+ "	ON tblCategorias.idLocal = tblPlus.idLocal                 "
				+ "	AND tblCategorias.IDLINEA = tblPlus.IDLINEA                "
		 		+ "	AND tblCategorias.IdCategoria = tblPlus.IdCategoria        "
				+ "	WHERE tblCategorias.idLocal = ?1                           "
		 		+ "	AND tblPlus.idTipo iN (1,2)                                "
		 		+ "	AND tblPlus.idLinea= 1                                     "
				+ "	GROUP BY tblPlus.idTipo,                                   "
				+ "	tblCategorias.nombreCategoria                              ",
				nativeQuery = true)
		List<TblCategoriasDTO> ObtenerTiposInventario(int idLocal );
	  
}




















