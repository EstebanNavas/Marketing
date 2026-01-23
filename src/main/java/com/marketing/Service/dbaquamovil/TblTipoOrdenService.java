package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblTipoOrden;
import com.marketing.Repository.dbaquamovil.TblTipoOrdenRepo;

@Service
public class TblTipoOrdenService {
	
	@Autowired
	TblTipoOrdenRepo tblTipoOrdenRepo;
	
	
	public List<TblTipoOrden> listaIdTipoOrden(){
		
		List<TblTipoOrden> lista = tblTipoOrdenRepo.listaIdTipoOrden();
		
		return lista;
	}
	
	public Integer ObtnerSignoTipoOrden(int idtipoOrden) {
		
		Integer signo = tblTipoOrdenRepo.ObtnerSignoTipoOrden(idtipoOrden);
		
		return signo;
	}
	
	
	public  String ObtnerNombreTipoOrden(int idtipoOrden) {
		
		String nombreTipoOrden = tblTipoOrdenRepo.ObtnerNombreTipoOrden(idtipoOrden);
		
		return nombreTipoOrden;
	}
	
	
	public List<TblTipoOrden> listaIdTipoOrdenAll(){
		
		List<TblTipoOrden> lista = tblTipoOrdenRepo.listaIdTipoOrdenAll();
		
		return lista;
	}
	

}