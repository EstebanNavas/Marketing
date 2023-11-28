package com.marketing.Repository.DBMailMarketing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblNoticiasSite;
import com.marketing.Model.DBMailMarketing.TblSiteNoticias;

@Repository
public interface TblSiteNoticiasRepo extends JpaRepository<TblSiteNoticias, Integer> {
	
	@Query(value = "SELECT tblSiteNoticias.* " +
	  		 "FROM BDMailMarketing.dbo.tblSiteNoticias " +
			  "WHERE tblSiteNoticias.IDLOCAL = ?1 " 
	, nativeQuery = true)
	    List<TblSiteNoticias> Noticias(int IDLOCAL); 

}
