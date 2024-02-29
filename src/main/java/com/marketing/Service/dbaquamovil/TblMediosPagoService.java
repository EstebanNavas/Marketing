package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblMedidores;
import com.marketing.Model.dbaquamovil.TblMediosPago;
import com.marketing.Repository.dbaquamovil.TblMediosPagoRepo;

@Service
public class TblMediosPagoService {
	
	@Autowired
	TblMediosPagoRepo tblMediosPagoRepo;
	
	public List<TblMediosPago> ListaMediosDePago(int idLocal){
		
		List<TblMediosPago> MediosDePago = tblMediosPagoRepo.ListaMediosDePago(idLocal);
		
		return MediosDePago;
	}
	
	
	public Integer maximoIdMediodDePago(int idLocal) {
		
		Integer idMedio = tblMediosPagoRepo.maximoIdMediodDePago(idLocal);
		
		return idMedio;
	}
	
	
	 public boolean ingresarMedioDePago(int idLocal, int MaximoIdMedioDePago,  String descripcion, String CuentaContable, String CuentaCxC, String Convenio) {
			
			Integer ESTADO = 0;
			Integer CeroInt = 0;
			Integer UnoInt = 1;
			String punto = ".";
			float ceroFloat = 0;

			
			

			// Creamos una instancia de  TblAgendaLogVisitas
			TblMediosPago orden = new TblMediosPago();
			
	    	orden.setIdLocal(idLocal);
	    	orden.setIdMedio(MaximoIdMedioDePago);
	    	orden.setNombreMedio(descripcion);
	    	orden.setEstado(UnoInt);
	    	orden.setIdSeq(UnoInt);
	    	orden.setTextoMedio(punto);
	    	orden.setCuentaContable(CuentaContable);
	    	orden.setCuentaCxC(CuentaCxC);
	    	orden.setIdConvenio(Convenio);
	    		
	    	

			// Guardamos el objeto orden en la tabla 
	    	tblMediosPagoRepo.save(orden);
	    	
	    	System.out.println("MEDIDO DE PAGO INGRESADO CORRECTAMENTE");
			
			return true;
		}
	 
	 
	 public List<TblMediosPago> ObtenerMedioDePago(int idLocal, int idMedio){
		 
		 List<TblMediosPago> medioDePago = tblMediosPagoRepo.ObtenerMedioDePago(idLocal, idMedio);
		 
		 return medioDePago;
	 }

}
