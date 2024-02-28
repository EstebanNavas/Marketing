package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.Reportes.ReporteDTO;
import com.marketing.Model.dbaquamovil.TblDctosOrdenesDetalle;
import com.marketing.Projection.TblDctosOrdenesDetalleDTO;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Model.Reportes.ReporteDTO;

@Service
public class TblDctosOrdenesDetalleService {

	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
public boolean ingresarDetalleOrden(int IDLOCAL, int IDORDEN, String idCliente, Integer IDPLU, String NOMBREPLU, String comentario, Integer VRVENTAORIGINAL ) {
		
		Integer ESTADO = 0;
		Integer IDTIPOORDEN = 67;
		Integer item = 0;
		Integer CANTIDAD = 1;
		Integer IDTIPO = 0;
		Integer VRVENTAUNITARIO =  0;
		//Integer VRVENTAORIGINAL = 0; 
		Integer VRCOSTO =0;
		Integer VRDSCTOPIE = 0;
		Integer PORCENTAJEIVA = 0;
		
		
		// Obtenemos la fecha y hora actuales
    	Timestamp fechaOrden = new Timestamp(System.currentTimeMillis()); 

            TblDctosOrdenesDetalle ordenDetalle = new TblDctosOrdenesDetalle();
            
            ordenDetalle.setIDLOCAL(IDLOCAL);
            ordenDetalle.setIDTIPOORDEN(IDTIPOORDEN);
            ordenDetalle.setIDORDEN(IDORDEN);
            ordenDetalle.setIdCliente(idCliente);
            ordenDetalle.setIDPLU(IDPLU); 
            ordenDetalle.setItem(item);
            ordenDetalle.setNOMBREPLU(NOMBREPLU);
            ordenDetalle.setComentario(comentario);
            ordenDetalle.setCANTIDAD(CANTIDAD);
            ordenDetalle.setIDTIPO(IDTIPO);
            ordenDetalle.setVRVENTAUNITARIO(VRVENTAUNITARIO);
            ordenDetalle.setVRVENTAORIGINAL(VRVENTAORIGINAL);
            ordenDetalle.setVRCOSTO(VRCOSTO);
            ordenDetalle.setVRDSCTOPIE(VRDSCTOPIE);
            ordenDetalle.setESTADO(ESTADO);
            ordenDetalle.setPORCENTAJEIVA(PORCENTAJEIVA);

            // Guardar el objeto TblDctosOrdenesDetalle en la base de datos
            tblDctosOrdenesDetalleRepo.save(ordenDetalle);
        
		
		return true;
	}


	public List<String> ObtenerNombresPlus(int idLocal, int IDORDEN, int idCliente){
		
		List<String> registrosPlus = tblDctosOrdenesDetalleRepo.ObtenerNombresPlus(idLocal, IDORDEN, idCliente);
		
		return registrosPlus;
	}
	
	
	public String ObtenerComentario(int idLocal, int IDORDEN, int idCliente){
		
		String registrosPlus = tblDctosOrdenesDetalleRepo.ObtenerComentario(idLocal, IDORDEN, idCliente);
		
		return registrosPlus;
	}
	
	
	public List<TblDctosOrdenesDetalleDTO> ObtenerInfoPQR(int idLocal, int IDORDEN){
		
		List<TblDctosOrdenesDetalleDTO> informacionPQR = tblDctosOrdenesDetalleRepo.ObtenerInfoPQR(idLocal, IDORDEN);
		
		return informacionPQR;
	}
	
public boolean ingresarDetalleOrdenRespuesta(int IDLOCAL, int IDORDEN, String idCliente, Integer IDPLU, String NOMBREPLU, String comentario ) {
		
		Integer ESTADO = 0;
		Integer IDTIPOORDEN = 17;
		Integer item = 2;
		Integer CANTIDAD = 1;
		Integer IDTIPO = 0;
		Integer VRVENTAUNITARIO =  0;
		Integer VRVENTAORIGINAL = 0; 
		Integer VRCOSTO =0;
		Integer VRDSCTOPIE = 0;
		Integer PORCENTAJEIVA = 0;
		
		
		// Obtenemos la fecha y hora actuales
    	Timestamp fechaOrden = new Timestamp(System.currentTimeMillis()); 

            TblDctosOrdenesDetalle ordenDetalle = new TblDctosOrdenesDetalle();
            
            ordenDetalle.setIDLOCAL(IDLOCAL);
            ordenDetalle.setIDTIPOORDEN(IDTIPOORDEN);
            ordenDetalle.setIDORDEN(IDORDEN);
            ordenDetalle.setIdCliente(idCliente);
            ordenDetalle.setIDPLU(IDPLU); 
            ordenDetalle.setItem(item);
            ordenDetalle.setNOMBREPLU(NOMBREPLU);
            ordenDetalle.setComentario(comentario);
            ordenDetalle.setCANTIDAD(CANTIDAD);
            ordenDetalle.setIDTIPO(IDTIPO);
            ordenDetalle.setVRVENTAUNITARIO(VRVENTAUNITARIO);
            ordenDetalle.setVRVENTAORIGINAL(VRVENTAORIGINAL);
            ordenDetalle.setVRCOSTO(VRCOSTO);
            ordenDetalle.setVRDSCTOPIE(VRDSCTOPIE);
            ordenDetalle.setESTADO(ESTADO);
            ordenDetalle.setPORCENTAJEIVA(PORCENTAJEIVA);

            // Guardar el objeto TblDctosOrdenesDetalle en la base de datos
            tblDctosOrdenesDetalleRepo.save(ordenDetalle);
        
		
		return true;
	}


	public List<String> ObtenerItems(int idLocal, int IDORDEN){
		
		List<String> Items = tblDctosOrdenesDetalleRepo.ObtenerItems(idLocal, IDORDEN);
		
		return Items;
	}

	
	public Integer ObtenerIdOrdenEstado9(int idLocal){
		
		Integer RegistroIdOrdenEstado9 =  tblDctosOrdenesDetalleRepo.ObtenerIdOrdenEstado9(idLocal);
		
		return RegistroIdOrdenEstado9;
	}
	
	
	public List<String> ObtenerEstado9(int idLocal){
		
		List<String> listaEstados = tblDctosOrdenesDetalleRepo.ObtenerEstado9(idLocal);
		
		return listaEstados;
	}
	
	public String ObtenerComentarioRespuesta(int idLocal, int IDORDEN, int idCliente) {
		
		String Comentario = tblDctosOrdenesDetalleRepo.ObtenerComentarioRespuesta(idLocal, IDORDEN, idCliente);
		
		return Comentario;
	}
	
	public String ObtenerComentarioPQR(int idLocal, int IDORDEN, int idCliente) {
		
		String Comentario = tblDctosOrdenesDetalleRepo.ObtenerComentarioPQR(idLocal, IDORDEN, idCliente);
		
		return Comentario;
	}
	
	public String ObtenerNombrePlu(int idLocal, int IDORDEN, String idCliente, int idCategoria) {
		
		String NombrePlu = tblDctosOrdenesDetalleRepo.ObtenerNombrePlu(idLocal, IDORDEN, idCliente, idCategoria);
		
		return NombrePlu;
	}
	
	
	public List<TblDctosOrdenesDetalleDTO> listaRubroAgrupado(int idLocal, int IdTipoOrdenINI, int IdTipoOrdenFIN, int idPeriodo){
		
		List<TblDctosOrdenesDetalleDTO> lista = tblDctosOrdenesDetalleRepo.listaRubroAgrupado(idLocal, IdTipoOrdenINI, IdTipoOrdenFIN, idPeriodo);
		
		return lista;
	}
}




