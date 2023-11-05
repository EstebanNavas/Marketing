package com.marketing.Service.dbaquamovil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.dbaquamovil.TblOpcionesPerfilRepo;

@Service
public class TblOpcionesPerfilService {

	@Autowired
	TblOpcionesPerfilRepo tblOpcionesPerfilRepo;
}
