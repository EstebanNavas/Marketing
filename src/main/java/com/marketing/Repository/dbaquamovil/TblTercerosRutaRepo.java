package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

}
