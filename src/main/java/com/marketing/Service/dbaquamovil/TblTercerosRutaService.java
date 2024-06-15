package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Projection.TblTercerosRutaDTO;
import com.marketing.Repository.dbaquamovil.TblTercerosRutaRepo;

@Service
public class TblTercerosRutaService {
	
	@Autowired
	TblTercerosRutaRepo tblTercerosRutaRepo;
	
	public List<TblTercerosRuta> ListaRutas(int idLocal){
		List<TblTercerosRuta> ListaRutas = tblTercerosRutaRepo.ListaRutas(idLocal);
		
		return ListaRutas;
		
	}
	
	
	public List<TblTercerosRutaDTO> RutasOperario(int idLocal){
		
		List<TblTercerosRutaDTO> Rutas = tblTercerosRutaRepo.RutasOperario(idLocal);
		
		return Rutas;
	}
	
	public Integer maximoIdRuta(int idLocal) {
		
		Integer idRuta = tblTercerosRutaRepo.maximoIdRuta(idLocal);
		
		return idRuta;
	}
	
	
	
    public boolean ingresarRuta(int idLocal, int MaximoIdRuta,  String descripcion, int idUsuario) {
		
		Integer ESTADO = 0;
		Integer CeroInt = 0;
		Integer UnoInt = 1;
		String punto = ".";

		
		

		// Creamos una instancia de  TblAgendaLogVisitas
		TblTercerosRuta orden = new TblTercerosRuta();
		
    	orden.setIdLocal(idLocal);
    	orden.setIdRuta(MaximoIdRuta);
    	orden.setNombreCiclo(punto);
    	orden.setNombreRuta(descripcion);
    	orden.setEstado(UnoInt);
    	orden.setOrdenRuta(CeroInt);
    	orden.setIdUsuario(idUsuario);


		// Guardamos el objeto orden en la tabla 
    	tblTercerosRutaRepo.save(orden);
    	
    	System.out.println("RUTA INGRESADA CORRECTAMENTE");
		
		return true;
	}
    
    
    public List<TblTercerosRutaDTO> ObtenerRuta(int idLocal, int idRuta){
    	
    	List<TblTercerosRutaDTO> Ruta = tblTercerosRutaRepo.ObtenerRuta(idLocal, idRuta);
    	
    	return Ruta;
    }
    
    
    public List<TblTercerosRutaDTO> listaFCH(int idLocal, int idRuta){
    	
    	
    	List<TblTercerosRutaDTO>  lista = tblTercerosRutaRepo.listaFCH(idLocal, idRuta);
    	
    	return lista;
    	
    }

}
