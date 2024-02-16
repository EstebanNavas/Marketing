package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.Reportes.ReporteDTO;
import com.marketing.Model.dbaquamovil.TblDctosOrdenes;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Model.dbaquamovil.TblTercerosRuta;
import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
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
            System.out.println("Nombre Estracto: " + resultado.getNombreEstracto());
            System.out.println("Nombre ruta: " + resultado.getNombreRuta());
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
	
	
	public List<TercerosDTO> ListaTercerosEmpleados(int idLocal){
		
		List<TercerosDTO> ListaTerceros = tblTercerosRepo.ListaTercerosEmpleados(idLocal);
		
		
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
	
	public List<TercerosDTO> ListaTercerosProveedor(int idLocal){
		
		List<TercerosDTO> ListaTerceros = tblTercerosRepo.ListaTercerosProveedor(idLocal);
		
		
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
	
	public List<TercerosDTO> BuscarTercerosEmpleados(int idLocal, String palabraClave){
		
		List<TercerosDTO> ListaBusqueda = tblTercerosRepo.BuscarTercerosEmpleados(idLocal, palabraClave);
		
		
		return ListaBusqueda;
	}
	
	public List<TercerosDTO> BuscarTercerosProveedor(int idLocal, String palabraClave){
		
		List<TercerosDTO> ListaBusqueda = tblTercerosRepo.BuscarTercerosProveedor(idLocal, palabraClave);
		
		
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
	
	public Integer MaximoIdTercero(int idLocal, int idTipoTercero) {
		
		Integer idTercero = tblTercerosRepo.MaximoIdTercero(idLocal, idTipoTercero);

		if (idTercero == null) {

			idTercero = 0;
		}
		
		return idTercero;
	}
	
	
	public boolean ingresarTercero(int idLocal, String idCliente, int idTipoTercero, String nombreTercero, String direccionTercero, String direccionCobro, int idDptoCiudad, String telefonoFijo,
			String telefonoCelular, String email, int idRuta, int idEstracto, String CC_Nit, String numeroMedidor, int idMedidor, int idMacro,  String codigoCatastral, Timestamp fechaIngreso, Timestamp fechaInstalacionMedidor, String codigoAlterno, int tipoSuscriptor, String matricula, Double promedio) {
		
		Integer ESTADO = 0;
		Integer IDTIPOORDEN = 67;
		
		Integer idTercero = Integer.parseInt(idCliente);
		String tipoIdTercero = "C";
		Integer CeroInt = 0;
		String CeroString = "0";
		Integer UnoInt = 1;
		String UnoString = "1";
		Float ceroFloat = (float) 0;
		Double unoDouble = 1.0;
		
		//TblTercerosRuta terceroRuta = new TblTercerosRuta(idRuta);
		TblTerceroEstracto terceroEstracto = new TblTerceroEstracto(idEstracto);
		

		
		// Creamos una instancia de  TblAgendaLogVisitas
		TblTerceros orden = new TblTerceros();
		
    	orden.setIdLocal(idLocal);
    	orden.setIdCliente(idCliente);
    	orden.setIdTercero(idTercero);
    	orden.setTipoIdTercero(tipoIdTercero);
    	orden.setDigitoVerificacion(CeroInt);
    	orden.setIdTipoTercero(idTipoTercero);
    	orden.setIdPersona(CeroInt);
    	orden.setIdAutoRetenedor(UnoInt);
    	orden.setIdRegimen("NN");
    	orden.setNombreTercero(nombreTercero);
    	orden.setDireccionTercero(direccionTercero);
    	orden.setIdDptoCiudad(idDptoCiudad);
    	orden.setTelefonoFijo(telefonoFijo);
    	orden.setTelefonoCelular(telefonoCelular);
    	orden.setTelefonoFax(telefonoCelular);
    	orden.setEmail(email);
    	orden.setIdFormaPago(CeroInt);
    	orden.setEstado(UnoInt);
    	orden.setIdRuta(idRuta);
    	//orden.setTerceroRuta(terceroRuta);
    	orden.setNombreEmpresa("NN");
    	orden.setCupoCredito(CeroInt);
    	orden.setIndicador(UnoInt);
    	orden.setCiudadTercero("NN");
    	orden.setContactoTercero("NN");
    	orden.setIdListaPrecio(UnoInt);
    	orden.setIdVendedor(ceroFloat);
    	orden.setIdSeq(CeroInt);
    	orden.setIdEstracto(idEstracto);
    	//orden.setTerceroEstracto(terceroEstracto);
    	orden.setCuotaVencida(ceroFloat);
    	orden.setPromedio(promedio);
    	orden.setOrdenRuta(CeroInt);
    	orden.setDireccionCobro(direccionCobro);
    	orden.setCC_Nit(CC_Nit);
    	orden.setCuentaDerecho(UnoInt);
    	orden.setCodigoAlterno(codigoAlterno);
    	orden.setNumeroMedidor(numeroMedidor);
    	orden.setHistoriaConsumo(CeroString);
    	orden.setIdMedidor(idMedidor);
    	orden.setIdMacro(idMacro);
    	orden.setEstadoMedidor(UnoInt);
    	orden.setEstadoCorte(UnoInt);
    	orden.setEstadoEmail(UnoInt);
    	orden.setCodigoCatastral(codigoCatastral);
    	orden.setMatricula(matricula);
    	orden.setEstadoCarta(UnoInt);
    	orden.setResponsableEconomico("NN");
    	orden.setFechaIngreso(fechaIngreso);
    	orden.setPromedioEstrato(unoDouble);
    	orden.setFechaInstalacionMedidor(fechaInstalacionMedidor);
    	orden.setTipoSuscriptor(tipoSuscriptor);
		
		
		// Guardamos el objeto orden en la tabla TblTerceros
    	tblTercerosRepo.save(orden);
    	
    	System.out.println("TERCERO INGRESADO CORRECTAMENTE");
		
		return true;
	}
	
	
	public boolean ingresarTerceroProveedor(int idLocal, String idCliente, int idTipoTercero, String nombreTercero, String direccionTercero, String direccionCobro, int idDptoCiudad, String telefonoFijo,
			String telefonoCelular, String email, int idRuta, int idEstracto, String CC_Nit, String numeroMedidor, int idMedidor, int idMacro,  String codigoCatastral, Timestamp fechaIngreso, Timestamp fechaInstalacionMedidor, String codigoAlterno, int tipoSuscriptor, String matricula, 
			int digitoVerificacionInt, String regimen, String contacto, String telefonoFax, String tipoDocumento, int reteFuenteInt) {
		
		Integer ESTADO = 0;
		Integer IDTIPOORDEN = 67;
		
		Integer idTercero = Integer.parseInt(idCliente);
		String tipoIdTercero = "C";
		Integer CeroInt = 0;
		String CeroString = "0";
		Integer UnoInt = 1;
		String UnoString = "1";
		Float ceroFloat = (float) 0;
		Double unoDouble = 1.0;
		
		//TblTercerosRuta terceroRuta = new TblTercerosRuta(idRuta);
		TblTerceroEstracto terceroEstracto = new TblTerceroEstracto(idEstracto);
		

		
		// Creamos una instancia de  TblAgendaLogVisitas
		TblTerceros orden = new TblTerceros();
		
    	orden.setIdLocal(idLocal);
    	orden.setIdCliente(idCliente);
    	orden.setIdTercero(idTercero);
    	orden.setTipoIdTercero(tipoDocumento);
    	orden.setDigitoVerificacion(digitoVerificacionInt);
    	orden.setIdTipoTercero(idTipoTercero);
    	orden.setIdPersona(tipoSuscriptor);
    	orden.setIdAutoRetenedor(reteFuenteInt);
    	orden.setIdRegimen(regimen);
    	orden.setNombreTercero(nombreTercero);
    	orden.setDireccionTercero(direccionTercero);
    	orden.setIdDptoCiudad(idDptoCiudad);
    	orden.setTelefonoFijo(telefonoFijo);
    	orden.setTelefonoCelular(telefonoCelular);
    	orden.setTelefonoFax(telefonoFax);
    	orden.setEmail(email);
    	orden.setIdFormaPago(CeroInt);
    	orden.setEstado(UnoInt);
    	orden.setIdRuta(idRuta);
    	//orden.setTerceroRuta(terceroRuta);
    	orden.setNombreEmpresa("NN");
    	orden.setCupoCredito(CeroInt);
    	orden.setIndicador(UnoInt);
    	orden.setCiudadTercero("NN");
    	orden.setContactoTercero(contacto);
    	orden.setIdListaPrecio(UnoInt);
    	orden.setIdVendedor(ceroFloat);
    	orden.setIdSeq(CeroInt);
    	orden.setIdEstracto(idEstracto);
    	orden.setCuotaVencida(ceroFloat);
    	orden.setPromedio(unoDouble);
    	orden.setOrdenRuta(CeroInt);
    	orden.setDireccionCobro(direccionCobro);
    	orden.setCC_Nit(CC_Nit);
    	orden.setCuentaDerecho(UnoInt);
    	orden.setCodigoAlterno(codigoAlterno);
    	orden.setNumeroMedidor(numeroMedidor);
    	orden.setHistoriaConsumo(CeroString);
    	orden.setIdMedidor(idMedidor);
    	orden.setIdMacro(idMacro);
    	orden.setEstadoMedidor(UnoInt);
    	orden.setEstadoCorte(UnoInt);
    	orden.setEstadoEmail(UnoInt);
    	orden.setCodigoCatastral(codigoCatastral);
    	orden.setMatricula(matricula);
    	orden.setEstadoCarta(UnoInt);
    	orden.setResponsableEconomico("NN");
    	orden.setFechaIngreso(fechaIngreso);
    	orden.setPromedioEstrato(unoDouble);
    	orden.setFechaInstalacionMedidor(fechaInstalacionMedidor);
    	orden.setTipoSuscriptor(tipoSuscriptor);
		
		
		// Guardamos el objeto orden en la tabla TblTerceros
    	tblTercerosRepo.save(orden);
    	
    	System.out.println("TERCERO INGRESADO CORRECTAMENTE");
		
		return true;
	}
	
	
	public List<TblTerceros> ObtenerInformacionTercero(int idLocal, String idCliente, int idTipoTercero){
		
		List<TblTerceros> InfoTercero = tblTercerosRepo.ObtenerInformacionTercero(idLocal, idCliente, idTipoTercero);
		
		return InfoTercero;
	}
	
	
	public List<TercerosDTO> ListaTercerosRutas(int idLocal, int idRuta){
		
		List<TercerosDTO> Rutas = tblTercerosRepo.ListaTercerosRutas(idLocal, idRuta);
		
		return Rutas;
	}
}
































