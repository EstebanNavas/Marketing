package com.marketing.Service.DBMailMarketing;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.DBMailMarketing.IrcaLocalesTanque;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Projection.IRCAlocalesTanquesDTO;
import com.marketing.Repository.DBMailMarketing.IrcaLocalesTanqueRepository;



@Service
public class IrcaLocalesTanqueService {

    @Autowired
    private IrcaLocalesTanqueRepository repository;

    
    public List<IrcaLocalesTanque> obtenerTanquesPorLocal(int idLocal){
		
		List<IrcaLocalesTanque> Tanques = repository.obtenerTanquesPorLocal(idLocal);
		
		return Tanques;
	}
    
    public List<IrcaLocalesTanque> obtenerTanquesPorLocalTanque(int idLocal, int idTanque){
		
		List<IrcaLocalesTanque> Tanques = repository.obtenerTanquesPorLocalTanque(idLocal, idTanque);
		
		return Tanques;
	}
    
    public Integer maximoIdTanque(int idLocal) {
		
		Integer idTanque = repository.maximoIdTanque(idLocal);
		
		return idTanque;
		
	}
    
    
    public boolean ingresarTanque(int idLocal, int MaximoIdEstrato,  String descripcion, Double largo, Double ancho, Double profundo) {
		
		

		
		

		// Creamos una instancia de  TblAgendaLogVisitas
		IrcaLocalesTanque orden = new IrcaLocalesTanque();
		
    	orden.setIDLOCAL(idLocal);
    	orden.setIdTanque(MaximoIdEstrato);
    	orden.setNombreTanque(descripcion);
    	orden.setLargo(largo);
    	orden.setAncho(ancho);
    	orden.setProfundo(profundo);
    	//orden.setIdServicio(CeroInt);
    	

		// Guardamos el objeto orden en la tabla 
    	repository.save(orden);
    	
    	System.out.println("TANQUE INGRESADO CORRECTAMENTE");
		
		return true;
	}
    
    
}