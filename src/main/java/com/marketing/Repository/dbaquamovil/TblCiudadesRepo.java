package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblCiudades;

@Repository
public interface TblCiudadesRepo extends JpaRepository<TblCiudades, Integer> {

}
