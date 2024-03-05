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
}



