package com.marketing.Service.dbaquamovil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.dbaquamovil.TblLineasRepo;

@Service
public class TblLineasService {
	
	@Autowired
	TblLineasRepo tblLineasRepo;

}
