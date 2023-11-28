package com.marketing.Service.DBMailMarketing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.DBMailMarketing.TblNoticiasSite;
import com.marketing.Repository.DBMailMarketing.TblNoticiasSiteRepo;

@Service
public class TblNoticiasSiteService {
	
	@Autowired
	TblNoticiasSiteRepo tblNoticiasSiteRepo;
	
	public List<TblNoticiasSite> Noticias(int IDLOCAL){
		
		List<TblNoticiasSite> Noticias = tblNoticiasSiteRepo.Noticias(IDLOCAL);
		
		return Noticias;
	}

}
