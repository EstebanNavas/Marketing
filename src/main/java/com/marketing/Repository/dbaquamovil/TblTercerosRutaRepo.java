package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblTercerosRutaDTO;

@Repository
public interface TblTercerosRutaRepo extends JpaRepository<TblTercerosRuta, Integer> {
	
	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblTercerosRuta " +
			"WHERE tblTercerosRuta.idLocal = ?1 " +
			"ORDER BY tblTercerosRuta.nombreRuta ",
			nativeQuery = true)
	List<TblTercerosRuta> ListaRutas(int idLocal);
	
	@Query(value = "SELECT tblTercerosRuta.nombreRuta, tblTercerosRuta.nombreCiclo, tblTercerosRuta.idRuta, ctrlUsuarios.nombreUsuario " + 
			"FROM bdaquamovil.dbo.tblTercerosRuta " +
			"JOIN bdaquamovil.dbo.ctrlUsuarios " +
			"ON tblTercerosRuta.idLocal = ctrlUsuarios.idLocal " +
			"AND tblTercerosRuta.idUsuario = ctrlUsuarios.idUsuario " +
			"WHERE tblTercerosRuta.idLocal = ?1 " +
			"ORDER BY tblTercerosRuta.nombreRuta ",
			nativeQuery = true)
	List<TblTercerosRutaDTO> RutasOperario(int idLocal);
	
	@Query(value = "SELECT MAX(t.idRuta) FROM tblTercerosRuta t " + 
			"WHERE t.idLocal = ?1 ",
			nativeQuery = true)
	Integer maximoIdRuta(int idLocal);
	
	@Query(value = "SELECT  tblTercerosRuta.nombreRuta, tblTercerosRuta.nombreCiclo, tblTercerosRuta.idRuta, tblTercerosRuta.ordenRuta, tblTercerosRuta.idUsuario " + 
			"FROM bdaquamovil.dbo.tblTercerosRuta " +
			"WHERE tblTercerosRuta.idLocal = ?1 " +
			"AND idRuta = ?2 ",
			nativeQuery = true)
	List<TblTercerosRutaDTO> ObtenerRuta(int idLocal, int idRuta);
	
	// Actualizamos La Ruta
		  @Modifying
		  @Transactional
		  @Query(value = "UPDATE tblTercerosRuta SET nombreRuta = ?1, nombreCiclo = ?2, ordenRuta = ?3, idUsuario = ?4  " +

		                 "WHERE tblTercerosRuta.idLocal = ?5 " +
		                 "AND tblTercerosRuta.idRuta = ?6 " , nativeQuery = true)
		  public void actualizarRuta(String nombreRuta,  String nombreCiclo, int ordenRuta, int idUsuario, int idLocal, int idRuta ) ;
		  
		  
		  
		  @Query(value = "SELECT tbltercerosruta.idRuta      "
	                + "      ,tbltercerosruta.nombreRuta  "
	                + "      ,tbltercerosruta.nombreCiclo "
	                + "      ,tbltercerosruta.estado      "
	                + "      ,tbltercerosruta.ordenRuta   "
	                + "FROM tbltercerosruta               "
	                + "WHERE tbltercerosruta.idRuta  =    "
	                + "?2                     "
	                + "AND  tbltercerosruta.idLocal  =  ?1  ",
					nativeQuery = true)
			List<TblTercerosRutaDTO> listaFCH(int idLocal, int idRuta);
		

}
