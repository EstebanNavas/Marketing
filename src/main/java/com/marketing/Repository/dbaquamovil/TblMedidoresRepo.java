package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblMedidores;


@Repository
public interface TblMedidoresRepo extends JpaRepository<TblMedidores, Integer> {
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblMedidores " +
			"WHERE tblMedidores.idLocal = ?1 " +
			"ORDER BY tblMedidores.marcaMedidor ",
			nativeQuery = true)
	List<TblMedidores> ListaMedidores(int idLocal);
	
	@Query(value = "SELECT MAX(t.idMedidor) FROM tblMedidores t " + 
			"WHERE t.idLocal = ?1 ",
			nativeQuery = true)
	Integer maximoIdMedidor(int idLocal);
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblMedidores " +
			"WHERE tblMedidores.idLocal = ?1 " +
			"AND tblMedidores.idMedidor = ?2 ",
			nativeQuery = true)
	List<TblMedidores> ObtenerMedidor(int idLocal, int idMedidor);
	
	// Actualizamos el Medidor
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblMedidores SET marcaMedidor = ?1, diametro = ?2 " +

		                 "WHERE tblMedidores.idLocal = ?3 " +
		                 "AND tblMedidores.idMedidor = ?4 " , nativeQuery = true)
		  public void actualizarMedidor(String marcaMedidor,  int diametro, int idLocal, int idMedidor ) ;
		  
		  
		  
		  @Query(value = " SELECT TOP 1            "
	                + "?1        "
	                + "            AS idLocal,"
	                + "        0 AS idMedidor "                
	                + " ,'NN' AS marcaMedidor "
	                + "       ,0 AS diametro  "
	                + "       ,estado         "
	                + "   ,1 AS ordenSalida   "
	                + " FROM tblmedidores     "
	                + " UNION                 "                
	                + " SELECT idLocal        "
	                + "    ,idMedidor         "
	                + "    ,marcaMedidor      "                
	                + "       ,diametro       "
	                + "       ,estado         "
	                + "  ,2 AS ordenSalida    "
	                + " FROM tblmedidores     "
	                + " WHERE idLocal =       "
	                + "?1        "
	                + " ORDER BY 5,2 ",
					nativeQuery = true)
			List<TblMedidores> listaAll(int idLocal);
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  

}
