package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblTipoOrdenSubcuenta;
import com.marketing.Repository.dbaquamovil.TblTipoOrdenSubcuentaRepo;

@Service
public class TblTipoOrdenSubcuentaService {

	@Autowired
	TblTipoOrdenSubcuentaRepo tblTipoOrdenSubcuentaRepo;
	
	public List<TblTipoOrdenSubcuenta> listaTipoOrdenSubcuenta(int idTipoOrden){
		
		List<TblTipoOrdenSubcuenta> listaSuccuenta = tblTipoOrdenSubcuentaRepo.listaTipoOrdenSubcuenta(idTipoOrden);
		
		return listaSuccuenta;
		
	}
}
