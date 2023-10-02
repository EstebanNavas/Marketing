package com.marketing.Repository.dbaquamovil;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Model.dbaquamovil.TblPlus;

public interface TblLocalesRepo extends JpaRepository<TblLocales, Integer> {
	Optional<TblLocales> findByIdLocal(Integer idLocal); // Buscamos el idLocal
	
	@Query("SELECT t.pathReport FROM TblLocales t " +
	"WHERE t.idLocal = :idLocal ")
	String rutaReporte(@Param("idLocal") int idLocal);
	
	@Query(value = "SELECT tblLocales.razonSocial " + 
			"FROM bdaquamovil.dbo.tblLocales " +
			"WHERE tblLocales.idLocal = ?1 ",
			nativeQuery = true)
	String ObtenerRazonSocial(int idLocal);
	
	@Query(value = "SELECT tblLocales.nit " + 
			"FROM bdaquamovil.dbo.tblLocales " +
			"WHERE tblLocales.idLocal = ?1 ",
			nativeQuery = true)
	String ObtenerNit(int idLocal);
	
	@Query(value = "SELECT tblLocales.direccion " + 
			"FROM bdaquamovil.dbo.tblLocales " +
			"WHERE tblLocales.idLocal = ?1 ",
			nativeQuery = true)
	String ObtenerDireccion(int idLocal);
	
	@Query(value = "SELECT tblLocales.telefono " + 
			"FROM bdaquamovil.dbo.tblLocales " +
			"WHERE tblLocales.idLocal = ?1 ",
			nativeQuery = true)
	String ObtenerTelefono(int idLocal);
	
	@Query(value = "SELECT tblLocales.ciudad " + 
			"FROM bdaquamovil.dbo.tblLocales " +
			"WHERE tblLocales.idLocal = ?1 ",
			nativeQuery = true)
	String ObtenerCiudad(int idLocal);
}
