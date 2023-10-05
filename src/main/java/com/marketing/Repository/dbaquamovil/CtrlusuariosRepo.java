package com.marketing.Repository.dbaquamovil;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


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
}
