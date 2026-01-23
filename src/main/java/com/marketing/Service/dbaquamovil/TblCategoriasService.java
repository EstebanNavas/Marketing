package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblCategorias;
import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Projection.TblCategoriasDTO;
import com.marketing.Repository.dbaquamovil.TblCategoriasRepo;

@Service
public class TblCategoriasService {
	
	@Autowired
	TblCategoriasRepo tblCategoriasRepo;
	
	public List<TblCategoriasDTO> ListaCategorias(int idLocal){
		List<TblCategoriasDTO> categorias = tblCategoriasRepo.ListaCategorias(idLocal);
		
		return categorias;
		
	}
	
	public List<TblCategoriasDTO> ListaCategoriasInventario(int idLocal){
		
		
		List<TblCategoriasDTO> categoriasInventario = tblCategoriasRepo.ListaCategoriasInventario(idLocal);
		
		return categoriasInventario;
		
	}
	
	public List<TblCategoriasDTO> ListaCategoriasLinea300(int idLocal){
		
		List<TblCategoriasDTO> categorias = tblCategoriasRepo.ListaCategoriasLinea300(idLocal);
		
		return categorias;
		
	}
	
	
	public List<TblCategoriasDTO> ObtenerReferenciasPorCategoria(int idLocal, int idCategoria, int idLinea){
		
		List<TblCategoriasDTO> Referencias = tblCategoriasRepo.ObtenerReferenciasPorCategoria(idLocal, idCategoria, idLinea);
		
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
	
	
	public List<TblCategoriasDTO> ObtenerTodasLasReferenciasInventario(int idLocal){
		
		List<TblCategoriasDTO> lista = tblCategoriasRepo.ObtenerTodasLasReferenciasInventario(idLocal);
		
		return lista;
	}
	
	
	public Integer ObtenerIdLinea(int idLocal, int idCategoria){
		
		Integer idLinea = tblCategoriasRepo.ObtenerIdLinea(idLocal, idCategoria);
		
		return idLinea;
	}
	
	public List<String> ObtenerListaNombresCategorias(int idLocal){
		
		List<String> NombresCategorias = tblCategoriasRepo.ObtenerListaNombresCategorias(idLocal);
		
		return NombresCategorias;
	}
	
	
	public List<TblCategoriasDTO> ObtenerNombresLineas(int idLocal){
		
		List<TblCategoriasDTO> Lineas = tblCategoriasRepo.ObtenerNombresLineas(idLocal);
		
		return Lineas;
	}
	
	
	public List<TblCategoriasDTO> ObtenerCategoriasPorLinea(int idLocal, int idLinea ){
		
		List<TblCategoriasDTO> Categorias = tblCategoriasRepo.ObtenerCategoriasPorLinea(idLocal, idLinea);
		
		return Categorias;
	}
	
	
	public Integer maximoIdCategoria(int idLocal, int idLinea) {
		
		Integer idCategoria = tblCategoriasRepo.maximoIdCategoria(idLocal, idLinea);
		
		if(idCategoria  == null) {
			
			idCategoria = 0;
			return idCategoria;
		}
		
		return idCategoria;
	}

	
	public boolean ingresarCategoria(int idLocal, int maximoIdCategoria,  String descripcion, int lineaInt ) {
		

		Integer CeroInt = 0;
		Integer UnoInt = 1;


		// Creamos una instancia de  TblAgendaLogVisitas
		TblCategorias orden = new TblCategorias();
		
    	orden.setIdLocal(idLocal);
    	orden.setIdLinea(lineaInt);
    	orden.setIdCategoria(maximoIdCategoria);
    	orden.setNombreCategoria(descripcion);
    	orden.setEstado(UnoInt);
    	orden.setIdProducto(CeroInt);


		// Guardamos el objeto orden en la tabla 
    	tblCategoriasRepo.save(orden);
    	
    	System.out.println("CATEGORIA INGRESADA CORRECTAMENTE");
		
		return true;
	}
	
	
	public List<TblCategoriasDTO> ObtenerTodasLasCategorias(int idLocal ){
		
		List<TblCategoriasDTO> Categorias = tblCategoriasRepo.ObtenerTodasLasCategorias(idLocal);
		
		return Categorias;
	}
	
	public List<TblCategoriasDTO> ObtenerCategoria(int idLocal, int idLinea, int IdCategoria ){
		
		List<TblCategoriasDTO> Categoria = tblCategoriasRepo.ObtenerCategoria(idLocal, idLinea, IdCategoria);
		
		return Categoria;
	}
	
	
	public List<TblCategoriasDTO> ObtenerTipos(int idLocal ){
		
		List<TblCategoriasDTO> listaTipos = tblCategoriasRepo.ObtenerTipos(idLocal);
		
		return listaTipos;
		
	}
	
	public List<TblCategoriasDTO> ObtenerTiposInventario(int idLocal ){
		
		List<TblCategoriasDTO> listaTipos = tblCategoriasRepo.ObtenerTiposInventario(idLocal);
		
		return listaTipos;
	}
	
}




















