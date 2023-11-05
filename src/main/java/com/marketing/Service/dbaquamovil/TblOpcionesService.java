package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Projection.TblOpcionesDTO;
import com.marketing.Repository.dbaquamovil.TblOpcionesRepo;

@Service
public class TblOpcionesService {

	@Autowired
	TblOpcionesRepo tblOpcionesRepo;
	
	
	public List<TblOpcionesDTO> ObtenerTipoOpciones1(int idLocal){
		
		List<TblOpcionesDTO>  ListaOpcionesTipo1 = tblOpcionesRepo.ObtenerTipoOpciones1(idLocal);
		
		return ListaOpcionesTipo1;
	}
	
	
	public List<TblOpcionesDTO> ObtenerListaUnNivel(int IdOpcion, int IdLocal, int xIdPerfil){
		
		List<TblOpcionesDTO> ListaUnnivel  = tblOpcionesRepo.ObtenerListaUnNivel(IdOpcion, IdLocal, xIdPerfil);
		
		return ListaUnnivel;
	}
	
	
	public List<TblOpcionesDTO> ObtenerListaSubOpcionMenu(int IdOpcion, int IdLocal, int xIdPerfil){
		
		List<TblOpcionesDTO> ListaSubOpcionMenu = tblOpcionesRepo.ObtenerListaSubOpcionMenu(IdOpcion, IdLocal, xIdPerfil);
		
		return ListaSubOpcionMenu;
		
	}
}
