package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Query(value = "SELECT MAX(t.idMedio) FROM tblMediosPago t " + 
			"WHERE t.idLocal = ?1 ",
			nativeQuery = true)
	Integer maximoIdMediodDePago(int idLocal);
	
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblMediosPago " +
			"WHERE tblMediosPago.idLocal = ?1 " +
			"AND tblMediosPago.idMedio = ?2 ",
			nativeQuery = true)
	List<TblMediosPago> ObtenerMedioDePago(int idLocal, int idMedio);
	
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblMediosPago SET nombreMedio = ?1, cuentaContable = ?2, cuentaCxC = ?3, idConvenio = ?4, textoMedio = ?5 " +

	                 "WHERE tblMediosPago.idLocal = ?6 " +
	                 "AND tblMediosPago.idMedio = ?7 " , nativeQuery = true)
	  public void actualizarMedioDePago(String nombreMedio,  String  cuentaContable, String cuentaCxC, String  idConvenio, String textoMedio, int idLocal, int idMedio ) ;
	  
	  

		

}
