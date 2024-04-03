package com.marketing.Service.dbaquamovil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.dbaquamovil.Bak_DctosOrdenesDetalleRepo;

@Service
public class Bak_DctosOrdenesDetalleService {
	
	@Autowired
	Bak_DctosOrdenesDetalleRepo bak_DctosOrdenesDetalleRepo;

}
