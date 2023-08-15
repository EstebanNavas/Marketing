package com.marketing.Repository.DBMailMarketing;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblMailItemPlantilla;

@Repository
public interface TblMailItemPlantillaRepo extends JpaRepository<TblMailItemPlantilla, Integer> {

	@Query("SELECT MAX(t.idRequerimiento) FROM TblMailItemPlantilla t")
    Integer findMaxIdRequerimiento();
	
	 ArrayList <TblMailItemPlantilla> findAll();
}
