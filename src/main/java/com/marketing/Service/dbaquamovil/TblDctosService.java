package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblDctos;
import com.marketing.Projection.ReporteFeDTO;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TblDctosDTO2;
import com.marketing.Projection.TblDctosDTO3;
import com.marketing.Projection.TblDctosDTO4;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;

@Service
public class TblDctosService {

	@Autowired
	TblDctosRepo tblDctosRepo;
	
	public Integer findMaxIdDcto(int IDLOCAL) {

		Integer maxIdCero = 0;
		Integer maxIdDto = 0;
		maxIdDto = tblDctosRepo.findMaxIdDcto(IDLOCAL);

		if (maxIdDto == null) {
			return maxIdCero;
		} else {

			return maxIdDto;
		}
	}
	
	public boolean ingresarDto(int IDLOCAL, int IDORDEN, int idDcto, String idCliente, int IDUSUARIO, String fechaDctoStr, String fechaDctoNitCC ) {
		
		Integer IDTIPOORDEN = 17;
		Integer indicador = 1;
		
		
		
		
		//Timestamp fechaDcto = new Timestamp(System.currentTimeMillis()); // Obtenemos la fecha y hora actuales
		
		try {
		// Convierte la cadena fechaDctoStr en un objeto Timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parsedDate = sdf.parse(fechaDctoStr);
        Timestamp fechaDcto = new Timestamp(parsedDate.getTime());
		
		TblDctos Dcto = new TblDctos(); // Creamos una instancia de  TblAgendaLogVisitas
		
		Dcto.setIDLOCAL(IDLOCAL);
		Dcto.setIDTIPOORDEN(IDTIPOORDEN);
		Dcto.setIDORDEN(IDORDEN);
		Dcto.setIdDcto(idDcto);
		Dcto.setIndicador(indicador);
		Dcto.setIdCliente(idCliente);
		Dcto.setFechaDcto(fechaDcto);
		Dcto.setIDUSUARIO(IDUSUARIO);
		Dcto.setFechaDctoNitCC(fechaDctoNitCC);
		
		
		// Guardamos el objeto reporte en la tabla TblAgendaLogVisitas
		tblDctosRepo.save(Dcto);
		
		return true;
	    } catch (ParseException e) {
	        // Maneja la excepción si la conversión de fecha falla
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public Integer ObtenerIdDcto(int idLocal, int IDORDEN, String idCliente) {
		
		Integer idDcto = tblDctosRepo.ObtenerIdDcto(idLocal, IDORDEN, idCliente);
		
		return idDcto;
	}
	
	public String ObtenerFechaDcto(int idLocal, int IDORDEN, String idCliente) {
		
		String FechaDcto =  tblDctosRepo.ObtenerFechaDcto(idLocal, IDORDEN, idCliente);
		
		return FechaDcto;
	}
	
	public List<String> ObtenerClientesPQR(int idLocal){
	    List<String> ClientesPQR = tblDctosRepo.ObtenerClientesPQR(idLocal);

	    // Filtrar los valores nulos de la lista
	    List<String> ClientesPQRSinNulos = ClientesPQR
	        .stream()
	        .filter(cliente -> cliente != null)
	        .collect(Collectors.toList());

	    return ClientesPQRSinNulos;
	}

	
	public List<Integer> ObtenerListaPQR(int idLocal, String idCliente){
		
		List<Integer> ListaPQR = tblDctosRepo.ObtenerListaPQR(idLocal, idCliente);
		
		return ListaPQR;
		
	}
	
	public Integer ObtenerIDORDEN(int idLocal, int idDcto) {
		
		Integer IDORDEN = tblDctosRepo.ObtenerIDORDEN(idLocal, idDcto);
		
		return IDORDEN;
	}
	
	public List<Integer> ObtenerListaIDORDEN(int idLocal, List<Integer> idDcto){
		
		List<Integer> ListaIDORDEN = tblDctosRepo.ObtenerListaIDORDEN(idLocal, idDcto);
		
		return ListaIDORDEN;
	}
	
	public List<Integer> ObtenerCantidadFacturas(int idLocal, int idTipoOrden, int idPeriodo ){
		
		List<Integer> cantFacturas = tblDctosRepo.ObtenerCantidadFacturas(idLocal, idTipoOrden, idPeriodo);
		
		return cantFacturas;
	}
	
	public List<ReporteFeDTO> ObtenerReporteFE(int idLocal, int idTipoOrden, int idPeriodo ){
		
		List<ReporteFeDTO> reporteFE = tblDctosRepo.ObtenerReporteFE(idLocal, idTipoOrden, idPeriodo);
		
		for(ReporteFeDTO dto : reporteFE) {
			
			// Obtener valores de cada elemento
	        Integer envioFE = dto.getEnvioFE();
	        Integer cuenta = dto.getCuenta();
	        
	        System.out.println("Envío FE: " + envioFE);
	        System.out.println("Cuenta: " + cuenta);
			
		}
		
		return reporteFE;
	}
	
	
	public List<TblDctosDTO> listaRepNotaRuta(int idLocal, int idPeriodo, int IdTipoOrdenINI, int IdTipoOrdenFIN, int IndicadorINICIAL, int IndicadorFINNAL, int idRuta ){
		
		List<TblDctosDTO>  Lista = tblDctosRepo.listaRepNotaRuta(idLocal, idPeriodo, IdTipoOrdenINI, IdTipoOrdenFIN, IndicadorINICIAL, IndicadorFINNAL, idRuta);
		
		return Lista;
		
	}
	
	
	public List<TblDctosDTO> listaRepNota(int idLocal, int idPeriodo, int IdTipoOrdenINI, int IdTipoOrdenFIN, int IndicadorINICIAL, int IndicadorFINNAL){
		
		List<TblDctosDTO> Lista = tblDctosRepo.listaRepNota(idLocal, idPeriodo, IdTipoOrdenINI, IdTipoOrdenFIN, IndicadorINICIAL, IndicadorFINNAL);
		
		return Lista;
	}
	
	
	public List<TblDctosDTO> listaDctoRepAbono(int ValorAbono, int idLocal, int xIdTipoOrden, String idCliente, int idPeriodo){
		
		List<TblDctosDTO> lista = tblDctosRepo.listaDctoRepAbono(ValorAbono, idLocal, xIdTipoOrden, idCliente, idPeriodo);
		
		return lista;
	}
	
	
	public List<TblDctosDTO> listaNovedad(int idLocal, int xIdTipoOrden, int idPeriodo){
		
		List<TblDctosDTO> lista = tblDctosRepo.listaNovedad(idLocal, xIdTipoOrden, idPeriodo);
		
		return lista;
	}
	
	public  List<TblDctosDTO2> listaRepNotas(int idLocal, int idPeriodo, int IdTipoOrdenINI, int IdTipoOrdenFIN, int IndicadorINICIAL, int IndicadorFINNAL){
		
		List<TblDctosDTO2> lista = tblDctosRepo.listaRepNotas(idLocal, idPeriodo, IdTipoOrdenINI, IdTipoOrdenFIN, IndicadorINICIAL, IndicadorFINNAL);
		
		return lista;
	}
	
	
	public List<TblDctosDTO> listaCxCPeriodoAll(int idLocal, int xIdTipoOrden, int idPeriodo){
		
		List<TblDctosDTO> lista = tblDctosRepo.listaCxCPeriodoAll(idLocal, xIdTipoOrden, idPeriodo);
		
		return lista;
	}
	
	
	public List<TblDctosDTO> listaCxCSuspendidoAll(int idLocal, int xIdTipoOrden){
		
		List<TblDctosDTO> lista = tblDctosRepo.listaCxCSuspendidoAll(idLocal, xIdTipoOrden);
		
		return lista;
	}
	
	
	public List<TblDctosDTO> listaRepMediosMagneticosDian(int idLocal, int IdTipoOrdenINI, int IdTipoOrdenFIN, int IdPeriodoInicial, int IdPeriodoFinal, int Indicador){
		
		List<TblDctosDTO> lista = tblDctosRepo.listaRepMediosMagneticosDian(idLocal, IdTipoOrdenINI, IdTipoOrdenFIN, IdPeriodoInicial, IdPeriodoFinal, Indicador );
		
		return lista;
	}
	
	public List<TblDctosDTO> listaSaldoFavor(String fecha, int idLocal, int IdTipoOrden, int IdPeriodo, int IndicadorInicial, int IndicadorFinal ){
		
		List<TblDctosDTO> lista = tblDctosRepo.listaSaldoFavor(fecha, idLocal, IdTipoOrden, IdPeriodo, IndicadorInicial, IndicadorFinal);
		
		
		return lista;
		
	}
	
	
	public String ObtenerIdCliente(int idLocal, int idDcto) {
		
		String idCliente = tblDctosRepo.ObtenerIdCliente(idLocal, idDcto);
		
		return idCliente;
	}
	
	
	public Integer maximoDctoLocalIndicador(int IDLOCAL, int IdTipoOrden, int Indicador) {
		
		Integer meximoIndicador = tblDctosRepo.maximoDctoLocalIndicador(IDLOCAL, IdTipoOrden, Indicador);
		
		if(meximoIndicador == null) {
			
			meximoIndicador = 0;
			return meximoIndicador;
		}
		
		return meximoIndicador;
	}
	
	public List<TblDctosDTO> ObtenerIdDctoxPeriodo(int idLocal, List<Integer> idtipoorden, String idCliente, int idPeriodo) {
		
		List<TblDctosDTO> idDcto = tblDctosRepo.ObtenerIdDctoxPeriodo(idLocal, idtipoorden, idCliente, idPeriodo);
		
		return idDcto;
	}
	
	
	public Integer ObtenerIdOrden(int idLocal, int idtipoorden, int idDcto) {
		
		Integer xIdDcto = tblDctosRepo.ObtenerIdOrden(idLocal, idtipoorden, idDcto);
		
		return xIdDcto;
		
	}
	
	
	public List<TblDctosDTO> listaSaldoDctoFCH(int idLocal, String idCliente, String IdTipoOrden, int IdDcto){
		
		List<TblDctosDTO> listaSaldo = tblDctosRepo.listaSaldoDctoFCH(idLocal, idCliente, IdTipoOrden, IdDcto);
		
		return listaSaldo;
		
	}
	
	
	public List<TblDctosDTO> listaUnDctoOrden(int idLocal, int IdTipoOrden, int IdOrden, int Indicador){
		
		
		List<TblDctosDTO> listaDcto =  tblDctosRepo.listaUnDctoOrden(idLocal, IdTipoOrden, IdOrden, Indicador);
		
		
		return listaDcto;
		
	}
	
	
	public List<TblDctosDTO> listaUnDctoFCH(int idLocal, int IdTipoOrden, int IdOrden){
		
		List<TblDctosDTO> listaUnDcto = tblDctosRepo.listaUnDctoFCH(idLocal, IdTipoOrden, IdOrden);
		
		return listaUnDcto;
		
	}
	
	
	
	public List<TblDctosDTO> listaUnDctoClienteFCH(int idLocal, int idperiodo, String idCliente){
		
		List<TblDctosDTO> listaDctoCliente = tblDctosRepo.listaUnDctoClienteFCH(idLocal, idperiodo, idCliente);
		
		return listaDctoCliente;
	}
	
	
	public Integer ObtenerIdOrdenPorCruce(int idLocal, int idOrdenCruce, String idCliente) {
		
		Integer idOrden = tblDctosRepo.ObtenerIdOrdenPorCruce(idLocal, idOrdenCruce, idCliente);
		
		return idOrden;
		
	}
	
	public List<TblDctosDTO> listaPeriodoDcto(int idLocal, int IdTipoOrden, int IdDcto, int idperiodo){
		
		List<TblDctosDTO> PeriodoLista = tblDctosRepo.listaPeriodoDcto(idLocal, IdTipoOrden, IdDcto, idperiodo);
		
		return PeriodoLista;
		
	}
	
	
	public Integer existeDctoPeriodo(int idLocal, int IdTipoOrden, int IdDcto, int idperiodo) {
		
		Integer DctoPeriodo = tblDctosRepo.existeDctoPeriodo(idLocal, IdTipoOrden, IdDcto, idperiodo);
		
		return DctoPeriodo;
		
	}
	
	
	public Integer existeVrPagoDctoPeriodo(int idLocal, int IdTipoOrden, int IdDcto, int idperiodo) {
		
		Integer existeVrPago = tblDctosRepo.existeVrPagoDctoPeriodo(idLocal, IdTipoOrden, IdDcto, idperiodo);
		
		return existeVrPago;
		
	}
	
	
	public List<TblDctosDTO> listaUnDcto(int idLocal, int IdTipoOrden, int IdDcto, int Indicador){
		
		List<TblDctosDTO> unDcto = tblDctosRepo.listaUnDcto(idLocal, IdTipoOrden, IdDcto, Indicador);
		
		return unDcto;
	}
	
	
	public  List<TblDctosDTO> listaCuentaDetalladoClienteFCH(int idLocal, String idCliente, int IdTipoOrden, int IdDcto){
		
		List<TblDctosDTO> CuentaDetallado = tblDctosRepo.listaCuentaDetalladoClienteFCH(idLocal, idCliente, IdTipoOrden, IdDcto);
		
		return CuentaDetallado;
		
	}
	
	
	public List<TblDctosDTO> listaCuentaPeriodoCliente(int idLocal, String idCliente, int IdTipoOrden, int idPeriodo){
		
		List<TblDctosDTO> CuentaPeriodoCLiente = tblDctosRepo.listaCuentaPeriodoCliente(idLocal, idCliente, IdTipoOrden, idPeriodo);
		
		return CuentaPeriodoCLiente;
		
	}
	
	
	public List<TblDctosDTO> listaCuentaDetalladoOrdenFCH(int idLocal, String idCliente, int IdTipoOrden, int idcto){
		
		List<TblDctosDTO> CuentaDetalle = tblDctosRepo.listaCuentaDetalladoOrdenFCH(idLocal, idCliente, IdTipoOrden, idcto);
		
		return CuentaDetalle;
		
	}
	
	
	public String ObtenerIdClientePorIdOrden(int idLocal, int idOrden) {
		
		String idCiente = tblDctosRepo.ObtenerIdClientePorIdOrden(idLocal, idOrden);
		
		return idCiente;
		
	}
	
	
	public List<TblDctosDTO> listaCuentaPlanilla(int idLocal, int IdTipoOrden, int idLog){
		
		List<TblDctosDTO> cuentaplanilla = tblDctosRepo.listaCuentaPlanilla(idLocal, IdTipoOrden, idLog);
		
		return cuentaplanilla;
	}
	
	
	
	public List<TblDctosDTO> listaUnDctoPeriodoCliente(int idLocal, int IdTipoOrden, String IdCliente, int idPeriodo){
		
		List<TblDctosDTO>  UnDctoPeriodo = tblDctosRepo.listaUnDctoPeriodoCliente(idLocal, IdTipoOrden, IdCliente, idPeriodo);
		
		return UnDctoPeriodo;
		
	}
	
	
	
	public List<TblDctosDTO3> listaComprobanteDetallado(int idLocal, int idPeriodo){
		
		List<TblDctosDTO3> listaDetallado = tblDctosRepo.listaComprobanteDetallado(idLocal, idPeriodo);
		
		
		return listaDetallado;
		
	}
	
	public List<TblDctosDTO3> listaComprobanteAgrupado(int idLocal, int idPeriodo){
		
		List<TblDctosDTO3> listaAgrupado = tblDctosRepo.listaComprobanteAgrupado(idLocal, idPeriodo);
		
		return listaAgrupado;
		
	}
	
	
	public List<TblDctosDTO3> listaComprobanteRecaudoDetallado(int idLocal, int idPeriodo){
		
		List<TblDctosDTO3> recaudoDetallado = tblDctosRepo.listaComprobanteRecaudoDetallado(idLocal, idPeriodo);
		
		
		return recaudoDetallado;
		
	}

	
	
	public List<TblDctosDTO3> listaComprobanteRecaudoAgrupado(int idLocal, int idPeriodo){
		
		List<TblDctosDTO3> recuadoAgrupago = tblDctosRepo.listaComprobanteRecaudoAgrupado(idLocal, idPeriodo);
		
		return recuadoAgrupago;
		
	}
	
	public Integer maximoDctoLocalAlcance(int idLocal, int xIdAlcance){
		
		
		Integer maximoDcto = tblDctosRepo.maximoDctoLocalAlcance(idLocal, xIdAlcance);
		
		return maximoDcto;
		
	}
	
	
	public List<TblDctosDTO4> listaFechaDocumentoSoporte(int idLocal, String fechaInicial, String fechaFinal){
		
		List<TblDctosDTO4> listaDctoSoporte = tblDctosRepo.listaFechaDocumentoSoporte(idLocal, fechaInicial, fechaFinal);
		
		return listaDctoSoporte;
		
	}
	
	
	public Integer maximoDcto(int idLocal) {
		
		Integer maximoDcto = tblDctosRepo.maximoDcto(idLocal);
		
		return maximoDcto;
		
	}
	
	
	
	public List<TblDctosDTO4> listaFechaMapeoApi(int idLocal, int IDTIPOORDEN, int idPeriodo){
		
		
		List<TblDctosDTO4>  listaNotas = tblDctosRepo.listaFechaMapeoApi(idLocal, IDTIPOORDEN, idPeriodo);
		
		return listaNotas;
	}
	
	
	public List<TblDctosDTO4> listaHistoriaDetalle(int idLocal, String idCliente, String FechaInicial, String FechaFinal, int IndicadorInicia, int IndicadorFinal ){
		
		List<TblDctosDTO4>  listaHistorico = tblDctosRepo.listaHistoriaDetalle(idLocal, idCliente, FechaInicial, FechaFinal, IndicadorInicia, IndicadorFinal);
		
		return listaHistorico;
	}
	
	
	public List<TblDctosDTO4> listaOrdenPeriodo(int idLocal,  String idCliente, String FechaInicial, String FechaFinal, int IndicadorInicia, int IndicadorFinal ){
		
		List<TblDctosDTO4> listaOrden = tblDctosRepo.listaOrdenPeriodo(idLocal, idCliente, FechaInicial, FechaFinal, IndicadorInicia, IndicadorFinal);
		
		return listaOrden;
		
	}
	
	
	public List<TblDctosDTO> listaOrdenDeTrabajo(int idLocal, int IdTipoOrden, int idDcto){
		
		List<TblDctosDTO> odenDeTrabajo = tblDctosRepo.listaOrdenDeTrabajo(idLocal, IdTipoOrden, idDcto);
		
		return odenDeTrabajo;
		
	}
}



