package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblMedidoresMacro;

@Repository
public interface TblMedidoresMacroRepo extends JpaRepository<TblMedidoresMacro, Integer> {

	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblMedidoresMacro " +
			"WHERE tblMedidoresMacro.idLocal = ?1 " +
			"ORDER BY tblMedidoresMacro.nombreMacro DESC ",
			nativeQuery = true)
	List<TblMedidoresMacro> ListaMedidoresMacro(int idLocal);
	
	@Query(value = "SELECT MAX(t.idMacro) FROM tblMedidoresMacro t " + 
			"WHERE t.idLocal = ?1 ",
			nativeQuery = true)
	Integer maximoIdMacroMedidor(int idLocal);
	
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblMedidoresMacro " +
			"WHERE tblMedidoresMacro.idLocal = ?1 " +
			"AND tblMedidoresMacro. idMacro = ?2 ",
			nativeQuery = true)
	List<TblMedidoresMacro> ObtenerMacroMedidor(int idLocal, int idMacro);
	
	// Actualizamos el Macro
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblMedidoresMacro SET nombreMacro = ?1, diametro = ?2 " +

	                 "WHERE tblMedidoresMacro.idLocal = ?3 " +
	                 "AND tblMedidoresMacro.idMacro = ?4 " , nativeQuery = true)
	  public void actualizarMacroMedidor(String nombreMacro,  int diametro, int idLocal, int idMedidor ) ;
}
