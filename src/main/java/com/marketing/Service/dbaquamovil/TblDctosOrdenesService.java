package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblDctosOrdenes;
import com.marketing.Projection.ReporteSuiDTO;
import com.marketing.Projection.TblDctosOrdenesDTO;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;

@Service
public class TblDctosOrdenesService {

	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	//OBTENEMOS EL IDORDEN MÁXIMO 
	public Integer obtenerMaximoIDORDEN(int idLocal) {
		
		Integer maxIDORDEN0 = 0;
		Integer maxIDORDEN = 0;
		
		//Buscamos el id de reporte maximo y se guarda en maxIdReporte
         maxIDORDEN = tblDctosOrdenesRepo.findMaxIDORDEN(idLocal);
        if (maxIDORDEN == null) { // Validamos si maxIDORDEN es null
        	
            System.out.println("El maxIDORDEN es : " + maxIDORDEN);
            return maxIDORDEN0;
        } else {
            return maxIDORDEN;
        }
    }
	
	
	public boolean ingresarOrden(int IDLOCAL, int IDORDEN, String idCliente, int IDUSUARIO, int IDLOG, int NumeroOrden, String NroFactura, Timestamp xfechaRadicacion) {
		
		Integer ESTADO = 0;
		Integer IDTIPOORDEN = 67;
		
		
		// Obtenemos la fecha y hora actuales
    	//Timestamp fechaOrden = new Timestamp(System.currentTimeMillis()); 

		
		// Creamos una instancia de  TblAgendaLogVisitas
    	TblDctosOrdenes orden = new TblDctosOrdenes();
		
    	orden.setIDLOCAL(IDLOCAL);
    	orden.setIDTIPOORDEN(IDTIPOORDEN);
    	orden.setIDORDEN(IDORDEN);
    	orden.setFECHAORDEN(xfechaRadicacion);
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
	
	
	public Integer ObtenerTipoOrdenCliente(int IDLOCAL, String idCliente, int IDUSUARIO) {
		
		Integer idTipoOrden = tblDctosOrdenesRepo.ObtenerTipoOrdenCliente(IDLOCAL, idCliente, IDUSUARIO);
		
		return idTipoOrden;
		
	}
	
	
	public Integer ObtenerIdCliente(int IDLOCAL, int IDUSUARIO, int idOrden) {
		
		Integer xIdCliente = tblDctosOrdenesRepo.ObtenerIdCliente(IDLOCAL, IDUSUARIO, idOrden);
		
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
	
	public Integer ObtenerIdOrdenDelIdLog(int IDLOCAL,  int idLog) {
		
		Integer idOrdenObtenido = tblDctosOrdenesRepo.ObtenerIdOrdenDelIdLog(IDLOCAL, idLog);
		
		return idOrdenObtenido;
	}
	
	public Integer ObtenerNumeroOrden(int IDLOCAL, int IDORDEN) {
		
		Integer NumeroOrden = tblDctosOrdenesRepo.ObtenerNumeroOrden(IDLOCAL, IDORDEN);
		
		return NumeroOrden;
	}
	
	public String ObtenerOrdenCompra(int IDLOCAL, int IDORDEN) {
		
		String OrdenCompra = tblDctosOrdenesRepo.ObtenerOrdenCompra(IDLOCAL, IDORDEN);
		
		return OrdenCompra;
	}
	
	
	public Integer ObtenerTipoOrden(int IDLOCAL, int IDORDEN, int IDLOG) {
		
		Integer TipoOrden = tblDctosOrdenesRepo.ObtenerTipoOrden(IDLOCAL, IDORDEN, IDLOG);
		
		return TipoOrden;
	}
	
	public List<String> ObtenerListaClientesFecha(int IDLOCAL,  String fechaInicial, String fechaFinal ){
		
		List<String> ListaClientesFecha = tblDctosOrdenesRepo.ObtenerListaClientesFecha(IDLOCAL, fechaInicial, fechaFinal);
		
		return ListaClientesFecha;
	}
	
	public List<Integer> ObtenerListaNumeroOrden(int IDLOCAL, List<Integer> IDORDEN, String fechaInicial, String fechaFinal){
		
		List<Integer> ListaNumeroOrden = tblDctosOrdenesRepo.ObtenerListaNumeroOrden(IDLOCAL, IDORDEN, fechaInicial, fechaFinal);
		
		  // Filtra los valores null de la lista
	    List<Integer> ListaFiltrada = ListaNumeroOrden.stream()
	            .filter(numeroOrden -> numeroOrden != null)
	            .collect(Collectors.toList());
		
		return ListaFiltrada;
	}
	
	public List<ReporteSuiDTO> ObtenerReporteSUI(int idLocal, String fechaInicial, String fechaFinal){
		
		List<ReporteSuiDTO> ReporteSUI = tblDctosOrdenesRepo.ObtenerReporteSUI(idLocal, fechaInicial, fechaFinal);
		
		for(ReporteSuiDTO Reporte : ReporteSUI) {
			
				System.out.println("idDpto: " + Reporte.getIdDpto());
				System.out.println("IdCiudad: " + Reporte.getIdCiudad());
				System.out.println("TipoAcentamiento: " + Reporte.getTipoAcentamiento());
				System.out.println("NumeroOrden: " + Reporte.getNumeroOrden());
				System.out.println("FechaRadicacion: " + Reporte.getfechaRadicacion());
				System.out.println("TipoTramite: " + Reporte.getTipoTramite());	
				System.out.println("NombreCausa: " + Reporte.getNombreCausa());
				System.out.println("CodigoCausaResolucion: " + Reporte.getCodigoCausaResolucion());
			    System.out.println("IdCliente: " + Reporte.getIdCliente());
			    System.out.println("OrdenCompra: " + Reporte.getOrdenCompra());
			    System.out.println("TipoRespuesta: " + Reporte.getTipoRespuesta());
			    System.out.println("FechaDcto: " + Reporte.getFechaDcto());
			    System.out.println("IdDcto: " + Reporte.getIdDcto());
			    System.out.println("FechaEjecucion: " + Reporte.getFechaEjecucion());    
			    System.out.println("TipoNotificacion: " + Reporte.getTipoNotificacion());
			    System.out.println("FECHAENTREGA: " + Reporte.getFECHAENTREGA());
			    
			   
			 
			    System.out.println("-------");
			    
			    
		}
		
		return ReporteSUI;
	}
	
	
	public List<TblDctosOrdenesDTO> listaDetalleFinanciacion(int idLocal, int xIdTipoOrden){
		
		List<TblDctosOrdenesDTO> lista = tblDctosOrdenesRepo.listaDetalleFinanciacion(idLocal, xIdTipoOrden);
		
		return lista;
	}
	
	public List<TblDctosOrdenesDTO> listaDetalleFinanciacionCuotaDiferir(int idLocal, int xIdTipoOrden, int idPeriodo, Double xCuotaDiferir){
		
		List<TblDctosOrdenesDTO> lista = tblDctosOrdenesRepo.listaDetalleFinanciacionCuotaDiferir(idLocal, xIdTipoOrden, idPeriodo, xCuotaDiferir);
		
		return lista;
	}
	
	
	public List<TblDctosOrdenesDTO> listaDetalleCobroPermanente(int idLocal, int xIdTipoOrden){
		
		List<TblDctosOrdenesDTO> lista = tblDctosOrdenesRepo.listaDetalleCobroPermanente(idLocal, xIdTipoOrden);
		
		return lista;
	}
	
	
	public List<TblDctosOrdenesDTO> listaUnClienteProducto(int idLocal, List<String> idClientes, Double idPeriodo){
		
		List<TblDctosOrdenesDTO> lista = tblDctosOrdenesRepo.listaUnClienteProducto(idLocal, idClientes, idPeriodo );
		
		return lista;
	}
	
	
	public List<TblDctosOrdenesDTO> listaLecturaAllSuscriptor(int xidPeriodoAnterior, int idLocal, int idTipoOrden, int idPeriodo ){
		
		List<TblDctosOrdenesDTO> lista = tblDctosOrdenesRepo.listaLecturaAllSuscriptor(xidPeriodoAnterior, idLocal, idTipoOrden, idPeriodo);
		
		return lista;
	}
	
	public  List<TblDctosOrdenesDTO> listaDetalleDeudaRuta(int idPeriodo, int idLocal, int idTipoOrden ){
		
		List<TblDctosOrdenesDTO> lista = tblDctosOrdenesRepo.listaDetalleDeudaRuta(idPeriodo, idLocal, idTipoOrden);
		
		return lista;
	}
	
	
	public List<TblDctosOrdenesDTO> PeriodoFacturado(int idLocal,  int idTipoOrden, int idPeriodo ){
		
		List<TblDctosOrdenesDTO> Cuenta = tblDctosOrdenesRepo.PeriodoFacturado(idLocal, idTipoOrden, idPeriodo);
		
		return Cuenta;
	}
	
	
	public Integer listaOrdenIdPeriodo(int idLocal,  int idPeriodo, int IdTipoOrden, int idRazon ){
		
		Integer  idOrden = tblDctosOrdenesRepo.listaOrdenIdPeriodo(idLocal, idPeriodo, IdTipoOrden, idRazon);
				
		
		if(idOrden == null) {
			
			return  0;
		}

		return idOrden;
	}
	
	
	public Integer maximaIdOrdenIdLocal(int idLocal) {
		
		Integer idOrden = tblDctosOrdenesRepo.maximaIdOrdenIdLocal(idLocal);
		
		if(idOrden == null) {
			
			idOrden = 0;
			return idOrden;
		}
		
		return idOrden;
	}
	
	
	public boolean ingresaDctosOrden(int IDLOCAL, int idTipoOrden, int xIdOrdenMax, Timestamp xfechaRadicacion, int xEstadoDctoOrden, String idCliente, int idUsuario, int xIdOrigenWeb,
			int xIdLogMax, String idTipoOrdenStr, String xEmail, String xIdFormaPago, int xIdRazonConsumo, int idPeriodo) {
		
		Integer ESTADO = 0;
		Integer IDTIPOORDEN = 67;
		
		
		// Obtenemos la fecha y hora actuales
    	//Timestamp fechaOrden = new Timestamp(System.currentTimeMillis()); 

		
		// Creamos una instancia de  TblAgendaLogVisitas
    	TblDctosOrdenes orden = new TblDctosOrdenes();
		
    	orden.setIDLOCAL(IDLOCAL);
    	orden.setIDTIPOORDEN(idTipoOrden);
    	orden.setIDORDEN(xIdOrdenMax);
    	orden.setFECHAORDEN(xfechaRadicacion);
    	orden.setESTADO(xEstadoDctoOrden);
    	orden.setIdCliente(idCliente);
    	orden.setIDUSUARIO(idUsuario);
    	orden.setIDORIGEN(xIdOrigenWeb);
    	orden.setIDLOG(xIdLogMax);
    	orden.setFECHAENTREGA(xfechaRadicacion);
    	orden.setTIPODCTO(idTipoOrdenStr);
    	orden.setEMAIL(xEmail);
    	orden.setFORMAPAGO(xIdFormaPago);
    	orden.setIdRazon(xIdRazonConsumo);
    	orden.setIdPeriodo(idPeriodo);

		
		
		
		// Guardamos el objeto orden en la tabla TblDctosOrdenes
    	tblDctosOrdenesRepo.save(orden);
		
		return true;
	}
	
	
	public Integer listaOrdenIdPeriodoIDLOG(int idLocal,  int idPeriodo, int IdTipoOrden, int idRazon) {
		
		Integer idLog = tblDctosOrdenesRepo.listaOrdenIdPeriodoIDLOG(idLocal, idPeriodo, IdTipoOrden, idRazon);
		
		return idLog;
	}
	
	
	public Integer ObtenerIdOrdenConFactura(int IDLOCAL, int idPeriodoAnterior, String idCliente) {
		
		Integer idOrden = tblDctosOrdenesRepo.ObtenerIdOrdenConFactura(IDLOCAL, idPeriodoAnterior, idCliente);
		
		return idOrden;
	}
	
	
	public List<TblDctosOrdenesDTO> listaConsumoFCH(int idPeriodo, int idTipoOrden, int idLocal){
		
		List<TblDctosOrdenesDTO> consumo = tblDctosOrdenesRepo.listaConsumoFCH(idPeriodo, idTipoOrden, idLocal);
		
		return consumo;
	}
	
	public List<TblDctosOrdenesDTO> listaDctoOrden( int idLocal, int idTipoOrden, int IdOrden){
		
		List<TblDctosOrdenesDTO> listaOrden = tblDctosOrdenesRepo.listaDctoOrden(idLocal, idTipoOrden, IdOrden);
		
		return listaOrden;
		
	}
	
	
	public List<TblDctosOrdenesDTO> listaDctoOrdenIdLog(int idLocal, int idLog){
		
		List<TblDctosOrdenesDTO> OrdenIdLog = tblDctosOrdenesRepo.listaDctoOrdenIdLog(idLocal, idLog);
		
		return OrdenIdLog;
		
	}
	
	
	public List<TblDctosOrdenesDTO> liquidaOrdenLocal( int idLocal, int idLog, int IdTipoOrden){
		
		List<TblDctosOrdenesDTO> liquidaOrden = tblDctosOrdenesRepo.liquidaOrdenLocal(idLocal, idLog, IdTipoOrden);
		
		return liquidaOrden;
		
	}
	
	
 public Integer existePedido( int idLog, int IdTipoOrden, int idLocal) {
	 
	 Integer idperiodo = tblDctosOrdenesRepo.existePedido(idLog, IdTipoOrden, idLocal);
	 
	 if(idperiodo == null) {
		 
		 idperiodo = 0;
		 
		 return idperiodo;
	 }
	 
	 return idperiodo;
 }
	
	
	public List<TblDctosOrdenesDTO> listaDctoOrdenIdLogIdTipoOrden( int idLocal, int IdTipoOrden, int idLog){
		
		List<TblDctosOrdenesDTO> listaDcto = tblDctosOrdenesRepo.listaDctoOrdenIdLogIdTipoOrden(idLocal, IdTipoOrden, idLog);
		
		return listaDcto;
		
	}
	
	
	public List<TblDctosOrdenesDTO> listaLiquidaDiferido(int idLocal, int IdTipoOrden, int idLog){
		
		
		List<TblDctosOrdenesDTO> liquidaReferidoLista = tblDctosOrdenesRepo.listaLiquidaDiferido(idLocal, IdTipoOrden, idLog);
		
		return liquidaReferidoLista;
	}
	
	
	public List<TblDctosOrdenesDTO> listaLiquidaDiferidoFCH(int idLocal, int IdTipoOrden, int idLog){
		
		List<TblDctosOrdenesDTO> listaLiquitaReferido = tblDctosOrdenesRepo.listaLiquidaDiferidoFCH(idLocal, IdTipoOrden, idLog);
		
		
		return listaLiquitaReferido;
		
	}
	
	
	public List<TblDctosOrdenesDTO> liquidaLog( int idLog, int idLocal, int IdTipoOrden ){
		
		List<TblDctosOrdenesDTO> logLiquida = tblDctosOrdenesRepo.liquidaLog(idLog, idLocal, IdTipoOrden);
		
		
		return logLiquida;
		
	}
	
	
	
	public List<TblDctosOrdenesDTO> obtenerOrdenTemporal( int idLocal, int idTipoOrden, String idCliente){
		
		List<TblDctosOrdenesDTO> orden = tblDctosOrdenesRepo.obtenerOrdenTemporal(idLocal, idTipoOrden, idCliente);
		
		return orden;
		
	}
	
	
	public List<TblDctosOrdenesDTO> listaCobroPermanente( int idLocal, int IdTipoOrden, String idCliente ){
		
		List<TblDctosOrdenesDTO> CobroPermanenteLista = tblDctosOrdenesRepo.listaCobroPermanente(idLocal, IdTipoOrden, idCliente);
		
		return CobroPermanenteLista;
		
	}
	
	
	public List<TblDctosOrdenesDTO> listaHistoricoFinanciacionCliente( int idLocal, int IdTipoOrden, String idCliente, String FechaInicial, String FechaFinal){
		
		List<TblDctosOrdenesDTO> listaHistorico = tblDctosOrdenesRepo.listaHistoricoFinanciacionCliente(idLocal, IdTipoOrden, idCliente, FechaInicial, FechaFinal);
		
		return listaHistorico;
		
	}
	
	
	public List<TblDctosOrdenesDTO> ObtenerFacturaActualizada(int idLocal, int idDcto, int xIdPeriodo){
		
		List<TblDctosOrdenesDTO> factura = tblDctosOrdenesRepo.ObtenerFacturaActualizada(idLocal, idDcto, xIdPeriodo);
		
		return factura;
		
	}
	
	
	public List<TblDctosOrdenesDTO> listaDetalleDeuda( int idLocal, int IdTipoOrden, int idPeriodo, int xIdPeriodoAnterior){
		
		List<TblDctosOrdenesDTO> deudaDetalle = tblDctosOrdenesRepo.listaDetalleDeuda(idLocal, IdTipoOrden, idPeriodo, xIdPeriodoAnterior);
		
		return deudaDetalle;
	}
	
	
	public Integer maximaIdContratoNE(int IDLOCAL, int idTipoOrden) {
		
		Integer maximoIdContrato = tblDctosOrdenesRepo.maximaIdContratoNE(IDLOCAL, idTipoOrden);
		
		return maximoIdContrato;
		
	}
}












