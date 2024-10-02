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
import com.marketing.Projection.TercerosDTO2;
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
	
	
	public List<TercerosDTO2> ListaTercerosEmpleados(int idLocal){
		
		List<TercerosDTO2> ListaTerceros = tblTercerosRepo.ListaTercerosEmpleados(idLocal);
		
		
		for (TercerosDTO2 tercero : ListaTerceros) {
            //long idTercero = tercero.getIdTercero(); // Usa long en lugar de int

            // Procesa el idTercero aquí
            System.out.println("idLocal: " + tercero.getIdLocal());
            System.out.println("idCliente: " + tercero.getIdCliente()); // Utiliza el idTercero como long
            System.out.println("Nombre Estracto: " + tercero.getIdEstracto());
            System.out.println("Nombre ruta: " + tercero.getNombreRuta());
            System.out.println("Estado: " + tercero.getNombreCausa());
            System.out.println("--------------------------------------");
        }
		
		return ListaTerceros;
	}
	
	
	// Método para ajustar el valor del idTercero si excede el rango del tipo int
    private long ajustarIdTercero(long idTercero) {
        // Convertir el idTercero a String para realizar la manipulación
        String idTerceroStr = String.valueOf(idTercero);

        // Si el idTercero es positivo, truncar los dígitos adicionales desde la derecha
        if (idTercero > 0) {
            idTerceroStr = idTerceroStr.substring(0, 9); // Mantener solo los primeros 9 dígitos
        }
        // Si el idTercero es negativo, truncar los dígitos adicionales desde la derecha, incluyendo el signo "-"
        else {
            idTerceroStr = idTerceroStr.substring(0, 10); // Mantener solo los primeros 10 dígitos
        }

        // Convertir el String ajustado de nuevo a long y devolverlo
        return Long.parseLong(idTerceroStr);
    }
	
	
	
	
	public List<TercerosDTO> ListaTercerosProveedor(int idLocal){
		
		List<TercerosDTO> ListaTerceros = tblTercerosRepo.ListaTercerosProveedor(idLocal);
		
		
		for(TercerosDTO tercero : ListaTerceros) {
			
			 System.out.println("idLocal: " + tercero.getIdLocal());
	          //  System.out.println("idCliente: " + tercero.getIdTercero());
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
	
	
	public List<TercerosDTO> BuscarTercerosSuscriptorNUID(int idLocal, String idCliente){
		
		List<TercerosDTO> ListaBusqueda = tblTercerosRepo.BuscarTercerosSuscriptorNUID(idLocal, idCliente);
		
		return ListaBusqueda;
		
	}
	
	
	public List<TercerosDTO> BuscarTercerosEmpleados(int idLocal, String palabraClave){
		
		List<TercerosDTO> ListaBusqueda = tblTercerosRepo.BuscarTercerosEmpleados(idLocal, palabraClave);
		
		
		return ListaBusqueda;
	}
	
	
	public List<TercerosDTO> BuscarTercerosEmpleadosNUID(int idLocal, String idCliente){
		
		List<TercerosDTO> ListaBusqueda = tblTercerosRepo.BuscarTercerosEmpleadosNUID(idLocal, idCliente);
		
		return ListaBusqueda;
	}
	
	public List<TercerosDTO> BuscarTercerosProveedor(int idLocal, String palabraClave){
		
		List<TercerosDTO> ListaBusqueda = tblTercerosRepo.BuscarTercerosProveedor(idLocal, palabraClave);
		
		
		return ListaBusqueda;
	}
	
	
	
	public List<TercerosDTO> BuscarTercerosProveedorNUID(int idLocal, String palabraClave){
		
		
		List<TercerosDTO> ListaBusqueda = tblTercerosRepo.BuscarTercerosProveedorNUID(idLocal, palabraClave);
		
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
	
	public Long MaximoIdTercero(int idLocal, int idTipoTercero) {
		
		Long idTercero = tblTercerosRepo.MaximoIdTercero(idLocal, idTipoTercero);

		if (idTercero == null) {

			idTercero = (long) 0;
		}
		
		return idTercero;
	}
	
	
	public boolean ingresarTercero(int idLocal, String idCliente, int idTipoTercero, String nombreTercero, String direccionTercero, String direccionCobro, int idDptoCiudad, String telefonoFijo,
			String telefonoCelular, String email, int idRuta, int idEstracto, String CC_Nit, String numeroMedidor, int idMedidor, int idMacro,  String codigoCatastral, Timestamp fechaIngreso, Timestamp fechaInstalacionMedidor, String codigoAlterno, int tipoSuscriptor, String matricula, Double promedio, int estadoEmail) {
		
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
    	orden.setIdRegimen("NI");
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
    	orden.setEstadoEmail(estadoEmail);
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
	
	public List<TercerosDTO> ObtenerSuscriptor(int idLocal, String idCliente){
		
		List<TercerosDTO> tercero = tblTercerosRepo.ObtenerSuscriptor(idLocal, idCliente);
		
	    // Verificar si tercero es un arreglo vacío
	    if (tercero != null && tercero.isEmpty()) {
	        tercero = null;
	    }
	    
		return tercero;
	}
	
	
	public List<String> ObtenerListaTercerosPorRuta(int idLocal, int idRuta){
		
		List<String> listaIdClientes = tblTercerosRepo.ObtenerListaTercerosPorRuta(idLocal, idRuta);
		
		return listaIdClientes;
	}
	
	
	public List<TercerosDTO> listaUnCliente(int idLocal, int idPeriodo, List<String> idCliente, int idEvento){
		
		List<TercerosDTO> alista = tblTercerosRepo.listaUnCliente(idLocal, idPeriodo, idCliente, idEvento);
		
		return alista;
		
	}
	
	
	public List<TercerosDTO> listaTodosLosClientesEstadoFacturaAct(int idLocal, int idPeriodo){
		
		List<TercerosDTO>  todosClientes = tblTercerosRepo.listaTodosLosClientesEstadoFacturaAct(idLocal, idPeriodo);
		
		return todosClientes;
		
	}
	
	
	public List<String> ObtenerListaTercerosEstadoEmail(int idLocal, int idRuta, int estadoEmail){
		
		List<String> CLientes = tblTercerosRepo.ObtenerListaTercerosEstadoEmail(idLocal, idRuta, estadoEmail);
		
		return CLientes;
	}
	
	public List<String> ObtenerListaTercerosEstadoWhatsApp(int idLocal, int idRuta, int estadoWhatsApp){
		
		List<String> clientesWpp = tblTercerosRepo.ObtenerListaTercerosEstadoWhatsApp(idLocal, idRuta, estadoWhatsApp);
		
		return clientesWpp;
		
	}
	
	
	public List<String> ObtenerListaTercerosEstadoEmailSinRuta(int idLocal, int estadoEmail){
		
		List<String> listaSinRuta = tblTercerosRepo.ObtenerListaTercerosEstadoEmailSinRuta(idLocal, estadoEmail);
		
		return listaSinRuta;
		
	}
	
	public List<String> ObtenerListaTercerosEstadoWhatsAppSinRuta(int idLocal, int estadoWhatsApp){
		
		List<String> listaSinRutaWpp = tblTercerosRepo.ObtenerListaTercerosEstadoWhatsAppSinRuta(idLocal, estadoWhatsApp);
		
		return listaSinRutaWpp;
		
	}
	
	
	public List<String> ObtenerListaTercerosEstadoWhatsAppEstadoEmailSinRuta(int idLocal, int estadoWhatsApp,  int estadoEmail){
		
		List<String> listaSinRutaWppEmail = tblTercerosRepo.ObtenerListaTercerosEstadoWhatsAppEstadoEmailSinRuta(idLocal, estadoWhatsApp, estadoEmail);
		
		return listaSinRutaWppEmail;
		
	}
	
	
	public List<String> ObtenerListaTercerosEstadoWhatsAppEstadoEmail(int idLocal, int idRuta, int estadoWhatsApp, int estadoEmail){
		
		List<String> listaSinWppEmail = tblTercerosRepo.ObtenerListaTercerosEstadoWhatsAppEstadoEmail(idLocal, idRuta, estadoWhatsApp, estadoEmail);
		
		return listaSinWppEmail;
		
	}
	
	
	
	public List<TercerosDTO2> listaAllTercero(int idLocal){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaAllTercero(idLocal);
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaConsumoMacro( int idPeriodo, int idLocal, int idMacro){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaConsumoMacro(idPeriodo, idLocal, idMacro);
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaLecturaRutaTx(int idLocal, int xIdPeriodoAnterior, int xIdTipo, int idPeriodo, int xIdRuta, int xInicioRegistroTx, int xCuentaRegistroTx, int idOrden ){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaLecturaRutaTx(idLocal, xIdPeriodoAnterior, xIdTipo, idPeriodo, xIdRuta, xInicioRegistroTx, xCuentaRegistroTx, idOrden);
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaUnTerceroFachada(int idLocal, String idCliente){
		
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaUnTerceroFachada(idLocal, idCliente);
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaLecturaRutaAll(int idLocal, int idPeriodoAnterior, int idPeriodoactual, int idTipo){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaLecturaRutaAll(idLocal, idPeriodoAnterior, idPeriodoactual, idTipo);
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaLecturaRutaTxPorCliente(int idLocal, int xIdPeriodoAnterior, int xIdTipo, int idPeriodo, List<String> idCliente){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaLecturaRutaTxPorCliente(idLocal, xIdPeriodoAnterior, xIdTipo, idPeriodo, idCliente);
		
		for(TercerosDTO2 L : lista) {
			
			System.out.println("listaaaaa en el service" + L.getIdCliente());
			System.out.println("listaaaaa en el service" + L.getNombreTercero());

			
		}
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaCriticaPorcentajeExceso(Double xPorcentajeExceso, int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta, Double xConsumoBase){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaCriticaPorcentajeExceso(xPorcentajeExceso, idLocal, xIdPeriodoAnterior, xIdPeriodoActual, xIdTipo, xIdRuta, xConsumoBase);
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaCriticaPorcExcesoDefecto(Double xPorcentajeExceso, int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta, Double xConsumoBase){
		
		 List<TercerosDTO2> lista = tblTercerosRepo.listaCriticaPorcExcesoDefecto(xPorcentajeExceso, idLocal, xIdPeriodoAnterior, xIdPeriodoActual, xIdTipo, xIdRuta, xConsumoBase);
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaCriticaConsumoCero(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaCriticaConsumoCero(idLocal, xIdPeriodoAnterior, xIdPeriodoActual, xIdTipo, xIdRuta);
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaCriticaConsumoNegativo(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaCriticaConsumoNegativo(idLocal, xIdPeriodoAnterior, xIdPeriodoActual, xIdTipo, xIdRuta);
		
		return lista;
	}
	
	
	public List<TercerosDTO2> listaCriticaPromedioCero(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaCriticaPromedioCero(idLocal, xIdPeriodoAnterior, xIdPeriodoActual, xIdTipo, xIdRuta);
		
		return lista;
	}
	
	public List<TercerosDTO2> listaCriticaInconsistencia(int idLocal, int xIdPeriodoAnterior, int xIdPeriodoActual, int xIdTipo, int xIdRuta){
		
		List<TercerosDTO2> lista = tblTercerosRepo.listaCriticaInconsistencia(idLocal, xIdPeriodoAnterior, xIdPeriodoActual, xIdTipo, xIdRuta);
		
		return lista;
	}
	
	
	public List<TblTerceros> listaUnTerceroFCH(int idLocal, String idCliente, int idTipoTercero){
		
		List<TblTerceros> listaTercero = tblTercerosRepo.listaUnTerceroFCH(idLocal, idCliente, idTipoTercero);
		
		return listaTercero;
	}
	
	
	public Integer ObteneridEstrato(int idLocal, String idCliente) {
		
		Integer idEstracto = tblTercerosRepo.ObteneridEstrato(idLocal, idCliente);
		
		return idEstracto;
	}
	
	
	public List<TercerosDTO2> listaTerceroFCH(int idLocal, int IdTipoTercero, String IdCliente){
		
		List<TercerosDTO2>  listaTercero = tblTercerosRepo.listaTerceroFCH(idLocal, IdTipoTercero, IdCliente);
		
		return listaTercero;
	}
	
	
	public List<TercerosDTO2> listaUnTerceroOrden(int idLocal, int IdTipoOrden, int IdOrden){
		
		List<TercerosDTO2> listaOrden = tblTercerosRepo.listaUnTerceroOrden(idLocal, IdTipoOrden, IdOrden);
		
		return listaOrden;
		
	}
	
	
	public List<TercerosDTO2> listaLecturaClienteFCH(int idLocal, int IdPeriodoActual, int IdTipo, int IdPeriodoAnterior, String IdCliente){
		
		List<TercerosDTO2> LecturaListaCliente = tblTercerosRepo.listaLecturaClienteFCH(idLocal, IdPeriodoActual, IdTipo, IdPeriodoAnterior, IdCliente);
		
		return LecturaListaCliente;
		
	}
	
	
	public List<TercerosDTO2> listaDetalleCortes(int idLocal, int IdTipoOrden, int idPeriodo, int CuotaVencida){
		
		List<TercerosDTO2> DetalleCortes = tblTercerosRepo.listaDetalleCortes(idLocal, IdTipoOrden, idPeriodo, CuotaVencida);
		
		return DetalleCortes;
		
	}
	
	
	public List<TercerosDTO2> listaReconexion(int idLocal, int idPeriodo){
		
		List<TercerosDTO2> ReconexionLista = tblTercerosRepo.listaReconexion(idLocal, idPeriodo);
		
		return ReconexionLista;
	}
	
	
	public List<TercerosDTO2> listaContratoNEAll(int idLocal, int IdTipoOrden){
		
		
		List<TercerosDTO2> listaContratoNE = tblTercerosRepo.listaContratoNEAll(idLocal, IdTipoOrden);
		
		return listaContratoNE;
		
	}
	
	public List<TercerosDTO2> listaContratoNEAllBusqueda(int idLocal, int IdTipoOrden, String palabraClave){
		
		
		List<TercerosDTO2> listaContratoNEBusqueda = tblTercerosRepo.listaContratoNEAllBusqueda(idLocal, IdTipoOrden, palabraClave);
		
		return listaContratoNEBusqueda;
	}
	
	
	public List<TercerosDTO2> listaDetalleContratoFCH(int idLocal, int IdTipoOrden, String idCliente){
		
		List<TercerosDTO2> detalleContrato = tblTercerosRepo.listaDetalleContratoFCH(idLocal, IdTipoOrden, idCliente);
		
		return detalleContrato;
		
	}
	
	public List<TercerosDTO2> listaDetalleContratoFCHPorCategoria(int idLocal, int IdTipoOrden, String idCliente, int idCategoria){
		
		List<TercerosDTO2> detalleContratoCategoria = tblTercerosRepo.listaDetalleContratoFCHPorCategoria(idLocal, IdTipoOrden, idCliente, idCategoria);
		
		return detalleContratoCategoria;
	}
	
	
	public List<String> ObtenerListaTerceros(int idLocal){
		
		 List<String> listaAllTercero = tblTercerosRepo.ObtenerListaTerceros(idLocal);
		 
		 return listaAllTercero;
		
	}
	
	
	public List<TercerosDTO2> seleccionaTerceroProveedorxNombreIdTipoTercero(String NombreTercero, int idTipoTercero,  int idLocal){
		
		List<TercerosDTO2> listaTerceros = tblTercerosRepo.seleccionaTerceroProveedorxNombreIdTipoTercero(NombreTercero, idTipoTercero, idLocal);
		
		
		return listaTerceros;
		
	}
	
	
	public List<TercerosDTO2> listaTercerosILimitada(int idLocal, int idtipoTercero, int idPeriodo){
		
		List<TercerosDTO2>  listaTercerosLimitada = tblTercerosRepo.listaTercerosILimitada(idLocal, idtipoTercero, idPeriodo);
		
		return listaTercerosLimitada;
	}
	
	
	public List<TercerosDTO2> listaTercerosSiigo(int idLocal, int idtipoTercero, int idPeriodo){
		
		
		List<TercerosDTO2> listaTercerosSiigo = tblTercerosRepo.listaTercerosSiigo(idLocal, idtipoTercero, idPeriodo);
		
		return listaTercerosSiigo;
		
	}
	
	
	public Integer obtenerEstadoWppSuscriptor(int idLocal,  String idCliente) {
		
		Integer estadoWpp = tblTercerosRepo.obtenerEstadoWppSuscriptor(idLocal, idCliente);
		
		if(estadoWpp == null) {
			
			estadoWpp = 2;
			return estadoWpp;
		}
		
		return estadoWpp;
	}
	
	
	public List<TercerosDTO2> obtenerSusciptor(int idLocal,  String idCliente, String CC_Nit, String telefonoCelular){
		
		List<TercerosDTO2> suscriptor = tblTercerosRepo.obtenerSusciptor(idLocal, idCliente, CC_Nit, telefonoCelular);
		
		return suscriptor;
	}
	
	
	public List<TercerosDTO> listaUnClienteWhatsApp(int idLocal, int idPeriodo, List<String> idCliente, int idEvento){
		
		List<TercerosDTO> listaCliente = tblTercerosRepo.listaUnClienteWhatsApp(idLocal, idPeriodo, idCliente, idEvento);
		
		return listaCliente;
		
	}
	
	
	public List<TercerosDTO> listaTodosLosClientesEstadoFacturaActWhasApp(int idLocal, int idPeriodo){
		
		
		List<TercerosDTO> listaClientes = tblTercerosRepo.listaTodosLosClientesEstadoFacturaActWhasApp(idLocal, idPeriodo);
		
		return listaClientes;		
		
	}
	
	
	public List<TercerosDTO2> listaUnTerceroUnionFCH(int idLocal,  String idCliente){
		
		List<TercerosDTO2> listaTerceroUnion = tblTercerosRepo.listaUnTerceroUnionFCH(idLocal, idCliente);
		
		return listaTerceroUnion;
		
	}
	
	
	public List<TercerosDTO2> listaLecturaRutaTxPorCliente(int idLocal, int xIdPeriodoAnterior, int xIdTipo, int idPeriodo, String idCliente, int xInicioRegistroTx, int xCuentaRegistroTx, int idOrden ){
		
		
		List<TercerosDTO2> lecturaPorCliente = tblTercerosRepo.listaLecturaRutaTxPorCliente(idLocal, xIdPeriodoAnterior, xIdTipo, idPeriodo, idCliente, xInicioRegistroTx, xCuentaRegistroTx, idOrden);
		
		return lecturaPorCliente;
		
	}
	
	
}
































