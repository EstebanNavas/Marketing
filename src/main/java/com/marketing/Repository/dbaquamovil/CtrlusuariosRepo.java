package com.marketing.Repository.dbaquamovil;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Projection.CtrlusuariosDTO;
import com.marketing.Projection.TblTercerosProjectionDTO;

@Repository
public interface CtrlusuariosRepo extends JpaRepository<Ctrlusuarios, Integer> {
	public Optional<Ctrlusuarios> findByIdUsuario(Integer idusuario);

	 @Query(
				value = "SELECT ctrlUsuarios.nombreUsuario, ctrlUsuarios.idUsuario, 1 AS ordenSalida " +
						"FROM bdaquamovil.dbo.ctrlUsuarios " +
						"WHERE ctrlUsuarios.idlocal = ?1 " +
						"AND ctrlUsuarios.estado = 1 " +
						"AND ctrlUsuarios.idnivel = 5 " +
						"AND ctrlUsuarios.idUsuario = ?2 " +
						"UNION " +
						"SELECT ctrlUsuarios.nombreUsuario, ctrlUsuarios.idUsuario, 2 AS ordenSalida " +
						"FROM bdaquamovil.dbo.ctrlUsuarios " +
						"WHERE ctrlUsuarios.idlocal = ?1 " +
						"AND ctrlUsuarios.estado = 1 " +
						"AND ctrlUsuarios.idnivel = 5 " +
						"AND ctrlUsuarios.idUsuario != ?2 " +
						"ORDER BY 3 "
				,nativeQuery = true
				)

		List <CtrlusuariosDTO> obtenerNombresUsuarios(int idLocal, int idUsuario);
	 
	 @Query(
				value = "SELECT clave " +
						"FROM bdaquamovil.dbo.ctrlUsuarios " +
						"WHERE ctrlUsuarios.idlocal = ?1 " +
						"AND ctrlUsuarios.idUsuario = ?2 "
				,nativeQuery = true
				)

		String obtenerClaveUsuario(int idLocal, int idUsuario);
	 
	 
	 @Modifying
	  @Transactional
	  @Query(value = "UPDATE bdaquamovil.dbo.ctrlUsuarios " +
			  		"SET ctrlUsuarios.clave = ?1 " +
	                 "WHERE ctrlUsuarios.idLocal = ?2 " +
	                 "AND ctrlUsuarios.idUsuario = ?3 ", nativeQuery = true)
	  public void actualizarClave(String clave, int idLocal, int idUsuario);
	 
	 
	 @Query(value = "SELECT ctrlUsuarios.idUsuario, ctrlUsuarios.nombreUsuario " +
						"FROM bdaquamovil.dbo.ctrlUsuarios " +
						"WHERE ctrlUsuarios.idlocal = ?1 " +
						"AND ctrlUsuarios.estado= 1 " +
						"AND ctrlUsuarios.idNivel IN (20) "
				,nativeQuery = true)

	 List <CtrlusuariosDTO> obtenerOperarios(int idLocal);
	 
	 
	 @Query(
				value = "SELECT clave " +
						"FROM bdaquamovil.dbo.ctrlUsuarios " +
						"WHERE ctrlUsuarios.idlocal = ?1 " +
						"AND ctrlUsuarios.idNivel = ?2 " +
						"AND ctrlUsuarios.estado = ?3 "
				,nativeQuery = true
				)

		String listaAutorizador(int idLocal, int idNivel, int estado);
	 
	 
	 
	 
	 @Query(
				value = "SELECT ctrlusuarios.idUsuario    "

		                + "FROM   ctrlusuarios               "
		                + "WHERE  ctrlusuarios.idLocal =     "
		                + "?1            "
		                + "AND  ctrlusuarios.estado    =     "
		                + "?3                    "
		                + "AND  ctrlusuarios.idNivel IN (?2) "

		                + "AND ctrlusuarios.clave         =  "
		                + "?4 "
				,nativeQuery = true
				)

		Integer listaAutorizador(int idLocal, int idNivel, int estado, String clave);
	 
	 
	 
	 @Query(value = "SELECT *" +
				"FROM bdaquamovil.dbo.ctrlUsuarios " +
				"WHERE ctrlUsuarios.idlocal = ?1 " +
				"AND ctrlUsuarios.estado= 1 " +
				"AND ctrlUsuarios.idNivel= 5 "
		,nativeQuery = true)

	 List <CtrlusuariosDTO> obtenerUsuariosActivosNivel5(int idLocal);
	 
	 
	 
	 @Query(value = "SELECT ctrlUsuarios.nombreUsuario " +
				"FROM bdaquamovil.dbo.ctrlUsuarios " +
				"WHERE ctrlUsuarios.idlocal = ?1 " +
				"AND ctrlUsuarios.idUsuario= ?2 "
		,nativeQuery = true)

	 	String obtenerNombreUsuario(int idLocal, int idusuario);
	 
	 
}








