package com.marketing.Repository.DBMailMarketing;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblMailCredito;

@Repository
public interface TblMailCreditoRepo extends JpaRepository<TblMailCredito, Integer> {
	Optional<TblMailCredito> findByIdLocal(Integer idLocal);
}
