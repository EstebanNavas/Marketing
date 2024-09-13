package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblCiudades;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Projection.TblCiudadesDTO;

@Repository
public interface TblCiudadesRepo extends JpaRepository<TblCiudades, Integer> {
	
	@Query(value = "SELECT tblCiudades.idCiudad, tblCiudades.nombreCiudad, tblCiudades.idDpto, tblCiudades.nombreDpto, CONCAT(nombreCiudad, ' - ', nombreDpto) AS departamentoCiudad " + 
			"FROM bdaquamovil.dbo.tblCiudades " +
			"ORDER BY tblCiudades.nombreCiudad ",
			nativeQuery = true)
	List<TblCiudadesDTO> ListaCiudadesDepartamentos();
	
	
	
	@Query(value = "SELECT tblCiudades.nombreCiudad " + 
			"FROM bdaquamovil.dbo.tblCiudades " +
			"WHERE tblCiudades.idCiudad = ?1 ",
			nativeQuery = true)
	String NombreCiudad(int idCiudad);

}
