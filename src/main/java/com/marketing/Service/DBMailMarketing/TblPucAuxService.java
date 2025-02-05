package com.marketing.Service.DBMailMarketing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.DBMailMarketing.TblPucAux;
import com.marketing.Repository.DBMailMarketing.TblPucAuxRepo;

@Service
public class TblPucAuxService {
	
	@Autowired
	TblPucAuxRepo tblPucAuxRepo;
	
      public List<TblPucAux> listaTodosAuxiliares(int idLocal){
		
		List<TblPucAux> todosAuxiliares = tblPucAuxRepo.listaTodosAuxiliares(idLocal);
		
		return todosAuxiliares;
	}

}
