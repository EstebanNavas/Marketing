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
	
	
	public List<TblOpcionesDTO> ObtenerTipoOpciones1(int idLocal, List<Integer> idOpciones){
		
		List<TblOpcionesDTO>  ListaOpcionesTipo1 = tblOpcionesRepo.ObtenerTipoOpciones1(idLocal, idOpciones);
		
		return ListaOpcionesTipo1;
	}
	
	
	public List<Integer> ObtenerListaIdTipoOpcion1(int idLocal){
		
		List<Integer> ObtenerListaIdTipoOpcion1 = tblOpcionesRepo.ObtenerListaIdTipoOpcion1(idLocal);
		
		return ObtenerListaIdTipoOpcion1;
	}
	
	
	public List<Integer> ListaIdTipoOpcion1OpcionesPerfil(int idLocal, List<Integer> idOpciones, int xIdPerfil ){
		
		List<Integer> ListaIdTipoOpcion1OpcionesPerfil = tblOpcionesRepo.ListaIdTipoOpcion1OpcionesPerfil(idLocal, idOpciones, xIdPerfil);
		
		return ListaIdTipoOpcion1OpcionesPerfil;
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
