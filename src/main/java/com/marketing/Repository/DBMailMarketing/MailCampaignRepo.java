package com.marketing.Repository.DBMailMarketing;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.MailCampaign;

@Repository
public interface MailCampaignRepo extends JpaRepository<MailCampaign, Integer> {

//	Integer maximaCampaign(Integer idLocal, String sistema);
	
	 @Query("SELECT MAX(c.idCampaign) FROM MailCampaign c WHERE c.idLocal = :idLocal AND c.sistema = :sistema")
	    Integer maximaCampaign(@Param("idLocal") Integer idLocal, @Param("sistema") String sistema);
	 
	 ArrayList<MailCampaign> findByidLocalAndSistemaAndPeriodicidad(Integer idLocal, String sistema, String periodicidad);
	 
	 MailCampaign findByIdLocalAndSistemaAndIdCampaign(Integer idLocal, String sistema, Integer idCampaign);

}
