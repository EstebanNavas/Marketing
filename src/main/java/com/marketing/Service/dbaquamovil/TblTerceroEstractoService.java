package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Repository.dbaquamovil.TblTerceroEstractoRepo;

@Service
public class TblTerceroEstractoService {

	@Autowired
	TblTerceroEstractoRepo tblTerceroEstractoRepo;
	
	public List <TblTerceroEstracto> obtenerEstracto (int idLocal){
		
		List <TblTerceroEstracto> estracto = tblTerceroEstractoRepo.findByIdLocal(idLocal);
		
		return estracto;
	}
	
	
	public List<TblTerceroEstracto> ListaEstratos(int idLocal){
		
		List<TblTerceroEstracto> ListaEstratos = tblTerceroEstractoRepo.ListaEstratos(idLocal);
		
		return ListaEstratos;
	}
	
	public List<TblTerceroEstracto> ListaEstratosOrdenadorPorId(int idLocal){
		
		List<TblTerceroEstracto> ListaEstratos = tblTerceroEstractoRepo.ListaEstratosOrdenadorPorId(idLocal);
		
		return ListaEstratos;
	}
	
	public Integer maximoIdEstrato(int idLocal) {
		
		Integer idEstrato = tblTerceroEstractoRepo.maximoIdEstrato(idLocal);
		
		return idEstrato;
		
	}
	
	
	  public boolean ingresarEstrato(int idLocal, int MaximoIdEstrato,  String descripcion) {
			
			Integer ESTADO = 0;
			Integer CeroInt = 0;
			Integer UnoInt = 1;
			String punto = ".";
			float ceroFloat = 0;

			
			

			// Creamos una instancia de  TblAgendaLogVisitas
			TblTerceroEstracto orden = new TblTerceroEstracto();
			
	    	orden.setIdLocal(idLocal);
	    	orden.setIdEstracto(MaximoIdEstrato);
	    	orden.setNombreEstracto(descripcion);
	    	orden.setEstado(UnoInt);
	    	orden.setPromedioEstrato(ceroFloat);
	    	orden.setCodigoClaseUso(CeroInt);
	    	orden.setIdServicio(CeroInt);
	    	

			// Guardamos el objeto orden en la tabla 
	    	tblTerceroEstractoRepo.save(orden);
	    	
	    	System.out.println("ESTRATO INGRESADA CORRECTAMENTE");
			
			return true;
		}
	  
	  
	  public List<TblTerceroEstracto> ObtenerEstrato(int idLocal, int idEstracto){
		  
		  List<TblTerceroEstracto> Estrato = tblTerceroEstractoRepo.ObtenerEstrato(idLocal, idEstracto);
		  
		  return Estrato;
	  }
	
	
	
}




















