package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblOpcionesPerfil;

@Repository
public interface TblOpcionesPerfilRepo extends JpaRepository<TblOpcionesPerfil, Integer> {

}
