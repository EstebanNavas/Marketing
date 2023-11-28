package com.marketing.Repository.DBMailMarketing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblNoticiasSite;

@Repository
public interface TblNoticiasSiteRepo extends JpaRepository<TblNoticiasSite, Integer> {

	
	@Query(value = "SELECT tblNoticiasSite.* " +
	  		 "FROM BDMailMarketing.dbo.tblNoticiasSite " +
			  "WHERE tblNoticiasSite.IDLOCAL = ?1 " 
	, nativeQuery = true)
	    List<TblNoticiasSite> Noticias(int IDLOCAL); 
}
