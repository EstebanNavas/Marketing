package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblTercerosSui;

@Repository
public interface TblTercerosSuiRepo extends JpaRepository<TblTercerosSui, Integer> {

}
