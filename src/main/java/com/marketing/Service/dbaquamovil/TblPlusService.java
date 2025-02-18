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
import com.marketing.Projection.TblPlusDTO;
import com.marketing.Projection.TblPlusDTO2;
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
    
    
    public boolean ingresarReferencia(int idLocal, int MaximoIdPlu,  String descripcion, int lista1, int ivaInt, int tipoInt, int estratoInt, int TmaximoInt, int categoriaInt, int idLinea, int subsidioContribucionInt) {
		
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
    	orden.setVrCostoIND(subsidioContribucionInt);
    	orden.setIdEstracto(estratoInt);
    	orden.setTopeMaximo(TmaximoInt);
    	orden.setRangoMaximo(CeroInt);
    	orden.setIdPluDeuda(CeroInt);

		// Guardamos el objeto orden en la tabla 
    	tblPlusRepo.save(orden);
    	
    	System.out.println("REFERENCIA INGRESADA CORRECTAMENTE");
		
		return true;
	}
    
    
    public List<TblPlusDTO> listaEstractoTipoFCH(int idLocal, int idTipo, int idEstracto ){
        List<TblPlusDTO> lista  = tblPlusRepo.listaEstractoTipoFCH(idLocal, idTipo, idEstracto);
        

        
        return lista;
    }
    
    public List<TblPlusDTO> listaPluNota(int idLocal, int IdLinea, int idEstracto ){
    	
    	List<TblPlusDTO> listaPlu = tblPlusRepo.listaPluNota(idLocal, IdLinea, idEstracto);
    	
    	return listaPlu;
    }
    
    
    public List<TblPlusDTO> listaUnPluFCH(String idplu, int idLocal ){
    	
    	List<TblPlusDTO> listaPlu = tblPlusRepo.listaUnPluFCH(idplu, idLocal);
    	
    	return listaPlu;
    }
    
    
    public List<TblPlus> ObtenerFinanciacion(int idLocal, int idTipo ){
    	
    	List<TblPlus>  listafinanciacion = tblPlusRepo.ObtenerFinanciacion(idLocal, idTipo);
    	
    	return listafinanciacion;
    	
    	
    }
    
    
    public List<TblPlus> ObtenerPlusPorIdLinea(int idLocal, int idLinea ){
    	
    	List<TblPlus> listaPlus = tblPlusRepo.ObtenerPlusPorIdLinea(idLocal, idLinea);
    	
    	return listaPlus;
    }
    
    public List<TblPlusDTO> seleccionaPlu(int idLocal, String idCliente){
    	
    	List<TblPlusDTO> listaPlus = tblPlusRepo.seleccionaPlu(idLocal, idCliente);
    	
    	return listaPlus;
    }
    
    public List<TblPlusDTO> listaPluCategoriaTipoNE(int idLinea, int idCategoria, int idLocal, int idPlu){
    	
    	List<TblPlusDTO>  ListacategoriaNE = tblPlusRepo.listaPluCategoriaTipoNE(idLinea, idCategoria, idLocal, idPlu);
    	
    	return ListacategoriaNE;
    	
    }
    
    
    public List<TblPlusDTO2> listaPluCategoriaNE( int idLocal, int idLinea, String xIdCategoriaStr, int xIdOrden ){
    	
    	
    	List<TblPlusDTO2> listaCategoria = tblPlusRepo.listaPluCategoriaNE(idLocal, idLinea, xIdCategoriaStr, xIdOrden);
    	
    	return listaCategoria;
    	
    }
    
    
    public List<TblPlusDTO> ObtenerPlusxCategoria( int idLocal, int idCategoria){
    	
    	List<TblPlusDTO> listaPlus = tblPlusRepo.ObtenerPlusxCategoria(idLocal, idCategoria);
    	
    	return listaPlus;
    }
    
    
    public List<TblPlusDTO> listaPluNovedad( int idLocal){
    	
    	List<TblPlusDTO> pluNovedad = tblPlusRepo.listaPluNovedad(idLocal);
    	
    	return pluNovedad;
    }
    
    
    public List<TblPlusDTO> listaPluXLinea( int idLocal, int idLinea){
    	
    	
    	List<TblPlusDTO> listaPlus = tblPlusRepo.listaPluXLinea(idLocal, idLinea);
    	
    	return listaPlus;
    	
    }
    
    public String obtenerNombrePlu( int idLocal, int idPlu) {
    	
    	String nombrePlu = tblPlusRepo.obtenerNombrePlu(idLocal, idPlu);
    	
    	return nombrePlu;
    	
    }
    
}
