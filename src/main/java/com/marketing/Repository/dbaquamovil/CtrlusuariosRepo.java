package com.marketing.Repository.dbaquamovil;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.marketing.Model.dbaquamovil.Ctrlusuarios;

@Repository
public interface CtrlusuariosRepo extends JpaRepository<Ctrlusuarios, Integer> {
	public Optional<Ctrlusuarios> findByIdUsuario(Integer idusuario);

}
