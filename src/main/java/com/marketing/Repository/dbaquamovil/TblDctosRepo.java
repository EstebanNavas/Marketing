package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblDctos;

@Repository
public interface TblDctosRepo extends JpaRepository<TblDctos, Integer> {

	@Query("SELECT MAX(r.idDcto) FROM TblDctos r " +
			  "WHERE r.IDLOCAL = ?1 " +
			  "AND r.IDTIPOORDEN = 17 ")
	    Integer findMaxIdDcto(int IDLOCAL);
	
	  @Query(value = "SELECT idDcto " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.IDORDEN = ?2 " +
              "AND tblDctos.idCliente = ?3 ",
              nativeQuery = true)
	  Integer ObtenerIdDcto(int idLocal, int IDORDEN, String idCliente);
	  
	  @Query(value = "SELECT fechaDcto " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.IDORDEN = ?2 " +
              "AND tblDctos.idCliente = ?3 ",
              nativeQuery = true)
	  String ObtenerFechaDcto(int idLocal, int IDORDEN, String idCliente);
	  
	  @Query(value = "SELECT DISTINCT idCliente " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.IDTIPOORDEN = 17 ",
              nativeQuery = true)
	  List<String> ObtenerClientesPQR(int idLocal);
	  
	  @Query(value = "SELECT  idDcto " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.idCliente = ?2 "+
              "AND tblDctos.IDTIPOORDEN = 17 ",
              nativeQuery = true)
	  List<Integer> ObtenerListaPQR(int idLocal, String idCliente);
	  
	  @Query(value = "SELECT  IDORDEN " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.idDcto = ?2 " +
              "AND tblDctos.IDTIPOORDEN = 17 ",
              nativeQuery = true)
	  Integer ObtenerIDORDEN(int idLocal, int idDcto);
	  
	  @Query(value = "SELECT  IDORDEN " +
              "FROM bdaquamovil.dbo.tblDctos " +
              "WHERE tblDctos.IDLOCAL = ?1 " +
              "AND tblDctos.idDcto IN ?2 "+
              "AND tblDctos.IDTIPOORDEN = 17 ",
              nativeQuery = true)
	  List<Integer> ObtenerListaIDORDEN(int idLocal, List<Integer> idDcto);
}
