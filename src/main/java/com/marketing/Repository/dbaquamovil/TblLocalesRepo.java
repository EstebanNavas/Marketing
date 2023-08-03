package com.marketing.Repository.dbaquamovil;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marketing.Model.dbaquamovil.TblLocales;

public interface TblLocalesRepo extends JpaRepository<TblLocales, Integer> {
	Optional<TblLocales> findByIdLocal(Integer idLocal); // Buscamos el idLocal
}
