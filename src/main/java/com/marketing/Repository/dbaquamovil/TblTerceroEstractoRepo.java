package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Projection.TblTercerosProjectionDTO;

@Repository
public interface TblTerceroEstractoRepo extends JpaRepository<TblTerceroEstracto, Integer> {

	@Query("SELECT t FROM TblTerceroEstracto t WHERE t.idLocal = ?1")
    List<TblTerceroEstracto> findByIdLocal(int idLocal);
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblTerceroEstracto " +
			"WHERE tblTerceroEstracto.idLocal = ?1 " +
			"ORDER BY tblTerceroEstracto.nombreEstracto ",
			nativeQuery = true)
	List<TblTerceroEstracto> ListaEstratos(int idLocal);
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblTerceroEstracto " +
			"WHERE tblTerceroEstracto.idLocal = ?1 " +
			"ORDER BY tblTerceroEstracto.idEstracto ",
			nativeQuery = true)
	List<TblTerceroEstracto> ListaEstratosOrdenadorPorId(int idLocal);
	
	@Query(value = "SELECT MAX(t.idEstracto) FROM tblTerceroEstracto t " + 
			"WHERE t.idLocal = ?1 ",
			nativeQuery = true)
	Integer maximoIdEstrato(int idLocal);
	
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblTerceroEstracto " +
			"WHERE tblTerceroEstracto.idLocal = ?1 " +
			"AND tblTerceroEstracto.idEstracto = ?2 ",
			nativeQuery = true)
	List<TblTerceroEstracto> ObtenerEstrato(int idLocal, int idEstracto);
	
	// Actualizamos La Estrato
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblTerceroEstracto SET nombreEstracto = ?1 " +

	                 "WHERE tblTerceroEstracto.idLocal = ?2 " +
	                 "AND tblTerceroEstracto.idEstracto = ?3 " , nativeQuery = true)
	  public void actualizarEstrato(String nombreEstracto,  int idLocal, int idEstracto ) ;
	  
	  
	  
	  
}
