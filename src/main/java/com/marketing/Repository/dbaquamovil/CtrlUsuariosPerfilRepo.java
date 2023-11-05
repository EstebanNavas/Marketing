package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.CtrlUsuariosPerfil;

@Repository
public interface CtrlUsuariosPerfilRepo extends JpaRepository<CtrlUsuariosPerfil, Integer> {

}
