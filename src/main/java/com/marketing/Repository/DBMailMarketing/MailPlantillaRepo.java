package com.marketing.Repository.DBMailMarketing;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.MailPlantilla;

@Repository
public interface MailPlantillaRepo extends JpaRepository<MailPlantilla, Integer> {
	
}
