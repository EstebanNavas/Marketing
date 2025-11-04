package com.marketing.Service.dbaquamovil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblPlusInventario;
import com.marketing.Repository.dbaquamovil.TblPlusInventarioRepo;

@Service
public class TblPlusInventarioService {
	
	@Autowired
	TblPlusInventarioRepo tblPlusInventarioRepo;
	
	
	  public boolean ingresarReferenciaInventario(int idLocal, int MaximoIdPlu,   int idBodega, Double existencia) {


  Integer CeroInt = 0;
  Double ceroDouble = 0.0;
  Integer UnoInt = 1;


  // Creamos una instancia de  TblAgendaLogVisitas
  TblPlusInventario orden = new TblPlusInventario();

  orden.setIdLocal(idLocal);
  orden.setIdPlu(MaximoIdPlu);
  orden.setIdBodega(idBodega);
  orden.setExistencia(existencia);
  orden.setIdTipoOrden(CeroInt);
  orden.setIdOrden(CeroInt);
  orden.setCantidadOrden(ceroDouble);
  orden.setEstado(UnoInt);


   // Guardamos el objeto orden en la tabla 
   tblPlusInventarioRepo.save(orden);

   System.out.println("REFERENCIA INGRESADA CORRECTAMENTE");

   return true;
  }	
	  
	  
	  
	  public Double ObtenerExistenciaPlu(int idLocal, int idPlu) {
		  
		  Double existencia =  tblPlusInventarioRepo.ObtenerExistenciaPlu(idLocal, idPlu);
		  
		  return existencia;
		  
	  }
	  
	  
	  public Integer ObtenerIdOrden(int idLocal, int idPlu) {
		  
		  Integer idOrden = tblPlusInventarioRepo.ObtenerIdOrden(idLocal, idPlu);
		  
		  if(idOrden == null) {
			  
			  idOrden = 0;
		  }
		  
		  return idOrden;
	  }

}
