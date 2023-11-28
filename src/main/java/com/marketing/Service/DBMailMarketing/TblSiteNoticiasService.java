package com.marketing.Service.DBMailMarketing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marketing.Model.DBMailMarketing.TblSiteNoticias;

import com.marketing.Model.DBMailMarketing.TblNoticiasSite;
import com.marketing.Repository.DBMailMarketing.TblSiteNoticiasRepo;

@Service
public class TblSiteNoticiasService {
	
	@Autowired
	TblSiteNoticiasRepo tblSiteNoticiasRepo;
	
	public List<TblSiteNoticias> Noticias(int IDLOCAL){
		
		List<TblSiteNoticias> Noticias = tblSiteNoticiasRepo.Noticias(IDLOCAL);
		
		return Noticias;
	}

}
