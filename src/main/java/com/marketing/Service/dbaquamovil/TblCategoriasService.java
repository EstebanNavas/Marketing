package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Repository.dbaquamovil.TblCategoriasRepo;

@Service
public class TblCategoriasService {
	
	@Autowired
	TblCategoriasRepo tblCategoriasRepo;
	
	public List<TblCategorias> ListaCategorias(int idLocal){
		List<TblCategorias> categorias = tblCategoriasRepo.ListaCategorias(idLocal);
		
		return categorias;
		
	}
	
	
	public List<TblCategoriasDTO> ObtenerReferenciasPorCategoria(int idLocal, int idCategoria){
		
		List<TblCategoriasDTO> Referencias = tblCategoriasRepo.ObtenerReferenciasPorCategoria(idLocal, idCategoria);
		
		return Referencias;
	}
	
	public List<TblCategoriasDTO> ObtenerReferenciasPorIdPlu(int idLocal, int IDPLU){
		
		List<TblCategoriasDTO> Referencia = tblCategoriasRepo.ObtenerReferenciasPorIdPlu(idLocal, IDPLU);
		
		return Referencia;
	}
	
	
	public List<TblCategoriasDTO> ObtenerTodasLasReferencias(int idLocal){
		
		List<TblCategoriasDTO> Referencias = tblCategoriasRepo.ObtenerTodasLasReferencias(idLocal);
		
		return Referencias;
	}
	
	public Integer ObtenerIdLinea(int idLocal, int idCategoria){
		
		Integer idLinea = tblCategoriasRepo.ObtenerIdLinea(idLocal, idCategoria);
		
		return idLinea;
	}
	
	public List<String> ObtenerListaNombresCategorias(int idLocal){
		
		List<String> NombresCategorias = tblCategoriasRepo.ObtenerListaNombresCategorias(idLocal);
		
		return NombresCategorias;
	}

	
	
}




















