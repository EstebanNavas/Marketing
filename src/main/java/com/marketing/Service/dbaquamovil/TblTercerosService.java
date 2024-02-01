package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.Reportes.ReporteDTO;
import com.marketing.Model.dbaquamovil.TblDctosOrdenes;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblTercerosProjectionDTO;
import com.marketing.Projection.TercerosDTO;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;

@Service
public class TblTercerosService {

	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	
	
	public List<TblTercerosProjectionDTO> registrosTercerosTelefonicos(int idLocal) {
        List<TblTercerosProjectionDTO> resultados = tblTercerosRepo.findByIdLocal(idLocal);

        System.out.println("Número de registros obtenidos en el service: " + resultados.size());

        for (TblTercerosProjectionDTO resultado : resultados) {
            System.out.println("idLocal: " + resultado.getIdLocal());
            System.out.println("idCliente: " + resultado.getIdCliente());
            System.out.println("Nombre Estracto: " + resultado.getTerceroEstracto().getNombreEstracto());
            System.out.println("Nombre ruta: " + resultado.getTerceroRuta().getNombreRuta());
            System.out.println("--------------------------------------");
        }

        return resultados;
    }
	
	
	public List<TercerosDTO> ListaTercerosSuscriptor(int idLocal){
		
		List<TercerosDTO> ListaTerceros = tblTercerosRepo.ListaTercerosSuscriptor(idLocal);
		
		
		for(TercerosDTO tercero : ListaTerceros) {
			
			 System.out.println("idLocal: " + tercero.getIdLocal());
	            System.out.println("idCliente: " + tercero.getIdTercero());
	            System.out.println("Nombre Estracto: " + tercero.getIdEstracto());
	            System.out.println("Nombre ruta: " + tercero.getNombreRuta());
	            System.out.println("Estado: " + tercero.getNombreCausa());
	            System.out.println("--------------------------------------");
			
		}
		
		return ListaTerceros;
	}
	
	
	
	public List<TercerosDTO> BuscarTercerosSuscriptor(int idLocal, String palabraClave){
		
		List<TercerosDTO> ListaBusqueda = tblTercerosRepo.BuscarTercerosSuscriptor(idLocal, palabraClave);
		
		
		return ListaBusqueda;
	}
	
	
	
	
	
	
	
	
	public List<String> obtenerTelefonosCelularesPorIds(@Param("ids") List<String> ids, int idLocal) {
        List<String> telefonosCelulares = tblTercerosRepo.findTelefonoCelularByIdsAndIdLocal(ids, idLocal);
        return telefonosCelulares;
    }
	
	
	
	public List<ReporteDTO> obtenerNombreTerceros(int idLocal){
		
		List<TblTercerosProjectionDTO> nombresTerceros = tblTercerosRepo.obtenerNombreTerceros(idLocal);
		
		List<ReporteDTO> reporteDTOs = new ArrayList<>();
		
		for(TblTercerosProjectionDTO nombreTercero : nombresTerceros) {
			ReporteDTO reporteDTO = new ReporteDTO();
			
			reporteDTO.setNombreTercero(nombreTercero.getNombreTercero());
			System.out.println("getNombreTercero() en obtenerNombreTerceros es: " + nombreTercero.getNombreTercero() );
			
			reporteDTO.setIdCliente(nombreTercero.getIdCliente());
			System.out.println("getIdCliente() en obtenerNombreTerceros es: " + nombreTercero.getIdCliente() );
			
			reporteDTOs.add(reporteDTO);
		}
		
		return reporteDTOs;
	}
	
	
	public List<TblTercerosProjectionDTO> obtenerNombreTercerosEmpleados(int idLocal) {

		List<TblTercerosProjectionDTO> nombresTercerosEmpleados = tblTercerosRepo.obtenerNombreTercerosEmpleados(idLocal);
		


		return nombresTercerosEmpleados;
	}
	
	
	public List<TblTercerosProjectionDTO> obtenerNombreTercerosClientes(int idLocal) {

		List<TblTercerosProjectionDTO> nombresTercerosClientes = tblTercerosRepo.obtenerNombreTercerosClientes(idLocal);


		return nombresTercerosClientes;
	}

	public List<TblTercerosProjectionDTO> obtenerDatosTercerosClientes(int idLocal, int idCliente) {

		List<TblTercerosProjectionDTO> nombresTercerosClientes = tblTercerosRepo.obtenerDatosTercerosClientes(idLocal, idCliente);


		return nombresTercerosClientes;
	}
	
	public List<TblTercerosProjectionDTO> obtenerDatosTercerosListaClientes(int idLocal, List<String> idClientes) {

		List<TblTercerosProjectionDTO> nombresTercerosClientes = tblTercerosRepo.obtenerDatosTercerosListaClientes(idLocal, idClientes);


		return nombresTercerosClientes;
	}
	
	public String ObtenerIdCliente(int idLocal, String idCliente) {
		
		String IdCliente = tblTercerosRepo.ObtenerIdCliente(idLocal, idCliente);
		
		return IdCliente;
	}
	
	public String ObtenerNombreTercero(int idLocal, String idCliente) {
		
		String NombreTercero = tblTercerosRepo.ObtenerNombreTercero(idLocal, idCliente);
		
		return NombreTercero;
	}
	
	
	public String ObtenerTelefonoCelular(int idLocal, String idCliente) {
		
		String TelefonoCelular = tblTercerosRepo.ObtenerTelefonoCelular(idLocal, idCliente);
		
		return TelefonoCelular;
	}
	
	public String ObtenerDireccionTercero(int idLocal, String idCliente) {
		
		String DireccionTercero = tblTercerosRepo.ObtenerDireccionTercero(idLocal, idCliente);
		
		return DireccionTercero;
	}
	
	
	public boolean ingresarTercero(int idLocal, int IDORDEN, String idCliente, int IDUSUARIO, int IDLOG, int NumeroOrden, String NroFactura, Timestamp xfechaRadicacion) {
		
		Integer ESTADO = 0;
		Integer IDTIPOORDEN = 67;
		
		
		// Obtenemos la fecha y hora actuales
    	//Timestamp fechaOrden = new Timestamp(System.currentTimeMillis()); 

		
		// Creamos una instancia de  TblAgendaLogVisitas
		TblTerceros orden = new TblTerceros();
		
    	orden.setIdLocal(idLocal);
    	orden.setIdCliente(idCliente);
    	orden.setIdTercero(IDTIPOORDEN);
    	orden.setTipoIdTercero(NroFactura);
    	orden.setDigitoVerificacion(IDTIPOORDEN);
    	orden.setIdTipoTercero(IDTIPOORDEN);
    	orden.setIdPersona(IDTIPOORDEN);
    	orden.setIdAutoRetenedor(IDTIPOORDEN);
    	orden.setIdRegimen(NroFactura);
    	orden.setNombreTercero(NroFactura);
    	orden.setDireccionTercero(NroFactura);
    	orden.setIdDptoCiudad(IDTIPOORDEN);
    	orden.setTelefonoFijo(NroFactura);
    	orden.setTelefonoCelular(NroFactura);
    	orden.setTelefonoFax(NroFactura);
    	orden.setEmail(NroFactura);
    	orden.setIdFormaPago(IDTIPOORDEN);
    	orden.setEstado(ESTADO);
    	orden.setTerceroRuta(null);
    	orden.setNombreEmpresa(NroFactura);
    	orden.setCupoCredito(IDTIPOORDEN);
    	orden.setIndicador(IDTIPOORDEN);
    	orden.setCiudadTercero(NroFactura);
    	orden.setContactoTercero(NroFactura);
    	orden.setIdListaPrecio(IDTIPOORDEN);
    	orden.setIdVendedor(null);
    	orden.setIdSeq(IDTIPOORDEN);
    	orden.setTerceroEstracto(null);
    	orden.setCuotaVencida(null);
    	orden.setPromedio(IDTIPOORDEN);
    	orden.setOrdenRuta(IDTIPOORDEN);
    	orden.setDireccionCobro(NroFactura);
    	orden.setCC_Nit(NroFactura);
    	orden.setCuentaDerecho(IDTIPOORDEN);
    	orden.setCodigoAlterno(NroFactura);
    	orden.setNumeroMedidor(NroFactura);
    	orden.setHistoriaConsumo(NroFactura);
    	orden.setIdMedidor(IDTIPOORDEN);
    	orden.setIdMacro(IDTIPOORDEN);
    	orden.setEstadoMedidor(ESTADO);
    	orden.setEstadoCorte(ESTADO);
    	orden.setEstadoEmail(ESTADO);
    	orden.setCodigoCatastral(NroFactura);
    	orden.setMatricula(NroFactura);
    	orden.setEstadoCarta(ESTADO);
    	orden.setResponsableEconomico(NroFactura);
    	orden.setFechaIngreso(xfechaRadicacion);
    	orden.setPromedioEstrato(IDTIPOORDEN);
    	orden.setFechaInstalacionMedidor(xfechaRadicacion);
    	orden.setTipoSuscriptor(IDTIPOORDEN);
		
		
		// Guardamos el objeto orden en la tabla TblTerceros
    	tblTercerosRepo.save(orden);
		
		return true;
	}
}
































