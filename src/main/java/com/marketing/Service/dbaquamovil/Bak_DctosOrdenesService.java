package com.marketing.Service.dbaquamovil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Repository.dbaquamovil.Bak_DctosOrdenesRepo;

@Service
public class Bak_DctosOrdenesService {

	@Autowired
	Bak_DctosOrdenesRepo bak_DctosOrdenesRepo;
}
