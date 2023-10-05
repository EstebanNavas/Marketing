package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblDctosOrdenes;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;

@Service
public class TblDctosOrdenesService {

	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	//OBTENEMOS EL IDORDEN MÁXIMO 
	public Integer obtenerMaximoIDORDEN(int idLocal, int IDUSUARIO) {
		
		Integer maxIDORDEN0 = 0;
		Integer maxIDORDEN = 0;
		
		//Buscamos el id de reporte maximo y se guarda en maxIdReporte
         maxIDORDEN = tblDctosOrdenesRepo.findMaxIDORDEN(idLocal, IDUSUARIO);
        if (maxIDORDEN == null) { // Validamos si maxIDORDEN es null
        	
            System.out.println("El maxIDORDEN es : " + maxIDORDEN);
            return maxIDORDEN0;
        } else {
            return maxIDORDEN;
        }
    }
	
	
	public boolean ingresarOrden(int IDLOCAL, int IDORDEN, String idCliente, int IDUSUARIO, int IDLOG, int NumeroOrden, String NroFactura) {
		
		Integer ESTADO = 0;
		Integer IDTIPOORDEN = 67;
		
		
		// Obtenemos la fecha y hora actuales
    	Timestamp fechaOrden = new Timestamp(System.currentTimeMillis()); 

		
		// Creamos una instancia de  TblAgendaLogVisitas
    	TblDctosOrdenes orden = new TblDctosOrdenes();
		
    	orden.setIDLOCAL(IDLOCAL);
    	orden.setIDTIPOORDEN(IDTIPOORDEN);
    	orden.setIDORDEN(IDORDEN);
    	orden.setFECHAORDEN(fechaOrden);
    	orden.setESTADO(ESTADO);
    	orden.setIdCliente(idCliente);
    	orden.setIDUSUARIO(IDUSUARIO);
    	orden.setIDLOG(IDLOG);
    	orden.setNumeroOrden(NumeroOrden);
    	orden.setOrdenCompra(NroFactura);
		
		
		
		// Guardamos el objeto orden en la tabla TblDctosOrdenes
    	tblDctosOrdenesRepo.save(orden);
		
		return true;
	}
	
	
	
	public List<TblDctosOrdenesDTO>  ObtenerIdTipoOrdenAndIdUsuarioAndIdOrden(int IDLOCAL, int IDUSUARIO) {
		
		List<TblDctosOrdenesDTO>  registros = tblDctosOrdenesRepo.ObtenerIdTipoOrdenAndIdUsuarioAndIdOrden(IDLOCAL, IDUSUARIO);
		System.out.println("EL tamaño de registros es : " + registros.size() );
		
		return registros;
	}
	
	
	public Integer ObtenerIdCliente(int IDLOCAL, int IDUSUARIO) {
		
		Integer xIdCliente = tblDctosOrdenesRepo.ObtenerIdCliente(IDLOCAL, IDUSUARIO);
		
		return xIdCliente;
	}
	
	
	public List <String> ObtenerIdClienteIdTipoOrden17(int IDLOCAL){
		 
		 List <String> RegistrosClientes = tblDctosOrdenesRepo.ObtenerIdClienteIdTipoOrden17(IDLOCAL);
		 
		 return RegistrosClientes;
	 }
	
	public List <String> ObtenerListaIdOrden(int IDLOCAL, String idCliente){
		
		List <String> listaIdOrdens = tblDctosOrdenesRepo.ObtenerListaIdOrden(IDLOCAL, idCliente);
		
		return listaIdOrdens;
		
	}
	
	// Obtenemos el IDLOG donde el IDTIPOORDEN es 67
	public Integer ObtenerIdLog(int IDLOCAL, int idCliente, int numeroOrden) {
		
		Integer idLog = tblDctosOrdenesRepo.ObtenerIdLog(IDLOCAL, idCliente, numeroOrden);
		
		return idLog;
	}
	
	// Obtenemos el IDLOG donde el IDTIPOORDEN es 17
	public Integer ObtenerIdLog17(int IDLOCAL, String idCliente, int IDORDEN) {
		
		Integer idLog = tblDctosOrdenesRepo.ObtenerIdLog17(IDLOCAL, idCliente, IDORDEN);
		
		return idLog;
	}
	
	
	public Integer ObtenerIdClientePorIdORden(int IDLOCAL, int IDORDEN) {
		
		Integer idCliente = tblDctosOrdenesRepo.ObtenerIdClientePorIdORden(IDLOCAL, IDORDEN);
		
		return idCliente;
	}
	
	public String ObtenerFechaRadicacion(int IDLOCAL, int IDORDEN) {
		
		String FechaRadicacion = tblDctosOrdenesRepo.ObtenerFechaRadicacion(IDLOCAL, IDORDEN);
		
		return FechaRadicacion;
	}
	
	
	public Integer findMaxNumeroOrden(int idLocal) {
		
		Integer numeroOrden0 = 0;
		Integer numeroOrden = 0;
		
		numeroOrden = tblDctosOrdenesRepo.findMaxNumeroOrden(idLocal);
		
		if(numeroOrden == null) {
			System.out.println("El numeroOrden en findMaxNumeroOrden es: " + numeroOrden);
			return numeroOrden0;
		}else {
			return numeroOrden;
		}
				
	}
	
	
	public Integer ObtenerIdOrden(int IDLOCAL,  int numeroOrden, String idCliente) {
		
		Integer IdOrden = tblDctosOrdenesRepo.ObtenerIdOrden(IDLOCAL, numeroOrden, idCliente);
		
		return IdOrden;
	}
	
	public Integer ObtenerNumeroOrden(int IDLOCAL, int IDORDEN) {
		
		Integer NumeroOrden = tblDctosOrdenesRepo.ObtenerNumeroOrden(IDLOCAL, IDORDEN);
		
		return NumeroOrden;
	}
	
	public String ObtenerOrdenCompra(int IDLOCAL, int IDORDEN) {
		
		String OrdenCompra = tblDctosOrdenesRepo.ObtenerOrdenCompra(IDLOCAL, IDORDEN);
		
		return OrdenCompra;
	}
	
	public List<Integer> ObtenerListaNumeroOrden(int IDLOCAL, List<Integer> IDORDEN){
		
		List<Integer> ListaNumeroOrden = tblDctosOrdenesRepo.ObtenerListaNumeroOrden(IDLOCAL, IDORDEN);
		
		  // Filtra los valores null de la lista
	    List<Integer> ListaFiltrada = ListaNumeroOrden.stream()
	            .filter(numeroOrden -> numeroOrden != null)
	            .collect(Collectors.toList());
		
		return ListaFiltrada;
	}
}












