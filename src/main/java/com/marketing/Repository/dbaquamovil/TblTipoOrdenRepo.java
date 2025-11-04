package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblTipoOrden;

@Repository
public interface TblTipoOrdenRepo extends JpaRepository<TblTipoOrden, Integer> {
	
	@Query(value = " SELECT  [idTipoOrden]                          "          
			+ "      ,[nombreTipoOrden]                             "          
			+ "      ,[estado]                                      "          
			+ "      ,[signo]                                       "          
			+ "      ,[idSeq]                                       "          
			+ "      ,[idAlcance]                                   "          
			+ "  FROM [ticmov_invercolok].[dbo].[tblTipoOrden]      "          
			+ "  where idtipoorden NOT IN (1,9,6,8, 29, 59)         "          
			+ "  and idTipoOrden<100                                ",
			nativeQuery = true)
	List<TblTipoOrden> listaIdTipoOrden();
	
	
	@Query(value = " SELECT  [signo]                               "          
			 + " FROM [ticmov_invercolok].[dbo].[tblTipoOrden]     "          
			 + " where idtipoorden = ?1                            ",
			nativeQuery = true)
	  Integer ObtnerSignoTipoOrden(int idtipoOrden);
	
	
	@Query(value = " SELECT  [nombreTipoOrden]                     "          
			 + " FROM [ticmov_invercolok].[dbo].[tblTipoOrden]     "          
			 + " where idtipoorden = ?1                            ",
			nativeQuery = true)
	  String ObtnerNombreTipoOrden(int idtipoOrden);
	
	
	@Query(value = " SELECT  [idTipoOrden]                          "          
			+ "      ,[nombreTipoOrden]                             "          
			+ "      ,[estado]                                      "          
			+ "      ,[signo]                                       "          
			+ "      ,[idSeq]                                       "          
			+ "      ,[idAlcance]                                   "          
			+ "  FROM [ticmov_invercolok].[dbo].[tblTipoOrden]      "          
			+ "  where idTipoOrden<100                              ",
			nativeQuery = true)
	List<TblTipoOrden> listaIdTipoOrdenAll();

}
