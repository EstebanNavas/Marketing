package com.marketing.Service.dbaquamovil;

import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblTipoCausaNota;
import com.marketing.Repository.dbaquamovil.TblTipoCausaNotaRepo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TblTipoCausaNotaService {

	@Autowired
	TblTipoCausaNotaRepo tblTipoCausaNotaRepo;
	
	public ArrayList<TblTipoCausaNota>  ObtenerTblTipoCausaNota(int idTipoTabla) {
		
		ArrayList<TblTipoCausaNota> tipoCausas = tblTipoCausaNotaRepo.ObtenerTblTipoCausaNota(idTipoTabla);

		
		return tipoCausas;
	}
}
