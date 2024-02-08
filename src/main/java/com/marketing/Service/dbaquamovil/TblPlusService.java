package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.marketing.Model.dbaquamovil.TblPlus;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Repository.dbaquamovil.TblPlusRepo;

@Service
public class TblPlusService {

	@Autowired
	TblPlusRepo tblPlusRepo;
	
	public ArrayList<TblPlus> ObtenerCategorias(int idLocal, int idCategoria){
		
		 ArrayList<TblPlus> categorias = tblPlusRepo.ObtenerCategorias(idLocal, idCategoria);
		 
		 
		 return categorias;
	}
	
	
    public Map<String, String> ObtenerNombrePluAndIdPlu(int idLocal){
		
			// Obtenemos la lista de los nombres de los idPlu
			List <TblPlus> nombresPlu = tblPlusRepo.ObtenerNombrePluAndIdPlu(idLocal);
			
			Map<String, String> nombrePluIdPluMap = new HashMap<>();
			
			for(TblPlus registro : nombresPlu) {// Recorremos la lista
				String idPlu = registro.getIdPlu().toString();
		        String nombrePlu = registro.getNombrePlu();
		        
		     // Llenamos el map con los valores de idPlu y nombrePlu
		        nombrePluIdPluMap.put(idPlu, nombrePlu);
	
		     
				
			}
			
			return nombrePluIdPluMap;
	}
    
    
    public Integer maximoIdPlu(int idLocal) {
    	
    	Integer maximoIdPlu = tblPlusRepo.maximoIdPlu(idLocal);
    	
    	return maximoIdPlu;
    }
    
    
    public boolean ingresarReferencia(int idLocal, int MaximoIdPlu,  String descripcion, int lista1, int ivaInt, int tipoInt, int estratoInt, int TmaximoInt, int categoriaInt, int idLinea) {
		
		Integer ESTADO = 0;
		Integer IDTIPOORDEN = 67;
		
		
		String tipoIdTercero = "C";
		Integer CeroInt = 0;
		String CeroString = "0";
		Integer UnoInt = 1;
		String UnoString = "1";
		Float ceroFloat = (float) 0;

		// Creamos una instancia de  TblAgendaLogVisitas
		TblPlus orden = new TblPlus();
		
    	orden.setIdLocal(idLocal);
    	orden.setIdPlu(MaximoIdPlu);
    	orden.setNombrePlu(descripcion);
    	orden.setVrGeneral(lista1);
    	orden.setVrMayorista(CeroInt);
    	orden.setPorcentajeIva(ivaInt);
    	orden.setIdTipo(tipoInt);
    	orden.setIdLinea(idLinea);
    	orden.setVrCosto(CeroInt);
    	orden.setIdCategoria(categoriaInt);
    	orden.setIdMarca(CeroInt);
    	orden.setVrSucursal(CeroInt);
    	orden.setFactorDespacho(CeroInt);
    	orden.setEstado(CeroInt);
    	orden.setIdSeq(CeroInt);
    	orden.setReferencia(CeroString);
    	orden.setVrImpoconsumo(CeroInt);
    	orden.setVrCostoIND(CeroInt);
    	orden.setIdEstracto(estratoInt);
    	orden.setTopeMaximo(TmaximoInt);
    	orden.setRangoMaximo(CeroInt);
    	orden.setIdPluDeuda(CeroInt);

		// Guardamos el objeto orden en la tabla 
    	tblPlusRepo.save(orden);
    	
    	System.out.println("REFERENCIA INGRESADA CORRECTAMENTE");
		
		return true;
	}
    
    
    
}
