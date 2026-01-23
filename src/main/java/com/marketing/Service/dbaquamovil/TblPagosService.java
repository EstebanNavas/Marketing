package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Projection.TblPagosDTO;
import com.marketing.Repository.dbaquamovil.TblPagosRepo;

@Service
public class TblPagosService {

	@Autowired
	TblPagosRepo tblPagosRepo;
	
	public List<TblPagosDTO> listaRepRecaudoPeriodo(int idLocal, int idPeriodo){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaRepRecaudoPeriodo(idLocal, idPeriodo);
		
		return lista;
	}
	
	
	public  List<TblPagosDTO> listaRepRecaudoPeriodoRuta(int idLocal, int idPeriodo, int idRuta){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaRepRecaudoPeriodoRuta(idLocal, idPeriodo, idRuta);
		
		return lista;
	}
	
	
	public List<TblPagosDTO> listaRepRecaudoPeriodoFecha(int idLocal, int idPeriodo, String fecha){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaRepRecaudoPeriodoFecha(idLocal, idPeriodo, fecha);
		
		return lista;
	}
	
	public List<TblPagosDTO> listaRecuadoRubro(int idLocal, int idPeriodo){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaRecuadoRubro(idLocal, idPeriodo);
		
		return lista;
		
	}
	
	public List<TblPagosDTO> listaRecuadoRubroxRuta(int idLocal, int idPeriodo, int idRuta){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaRecuadoRubroxRuta(idLocal, idPeriodo, idRuta);
		
		return lista;
		
	}
	
	public List<TblPagosDTO> listaRecuadoRubroFecha(int idLocal, int idPeriodo, String fecha){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaRecuadoRubroFecha(idLocal, idPeriodo, fecha);
		
		return lista;
	}
	
	public List<TblPagosDTO> listaDetalleRecaudo(int idLocal, int idPeriodo){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaDetalleRecaudo(idLocal, idPeriodo);
		
		return lista;
	}
	
	
	
	public List<TblPagosDTO> listaAllRecaudo(int idLocal, int xIdTipoOrden, int idPeriodo){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaAllRecaudo(idLocal, xIdTipoOrden, idPeriodo);
		
		return lista;
	}
	
	
	
	public Integer validaDctoPago(int idLocal, int IdTipoOrden, int IdDcto, int idPeriodo) {
		
		Integer DctoPago = tblPagosRepo.validaDctoPago(idLocal, IdTipoOrden, IdDcto, idPeriodo);
		
		return DctoPago;
		
	}
	
	
	public Integer maximaPlanilla(int idLocal, int IdTipoOrden) {
		
		Integer planillaMax = tblPagosRepo.maximaPlanilla(idLocal, IdTipoOrden);
		
		if(planillaMax == null) {
			
			planillaMax = 0;
		}
		
		return planillaMax;
	}
	
	
	public Integer maximoReciboIdLocalxIndicador(int idLocal, int IdTipoOrden, int indicador) {
		
		Integer maximoRecibo = tblPagosRepo.maximoReciboIdLocalxIndicador(idLocal, IdTipoOrden, indicador);
		
			if(maximoRecibo == null) {
			
				maximoRecibo = 0;
			}
		
		return maximoRecibo;
		
	}
	
	
	public List<TblPagosDTO> listaPagoProceso(int idLocal, int IdTipoOrden, int IdLog){
		
		List<TblPagosDTO> PagoProceso = tblPagosRepo.listaPagoProceso(idLocal, IdTipoOrden, IdLog);
		
		return PagoProceso;
		
	}
	
	
	public List<TblPagosDTO> listaPlanilla(int idLocal, int IdTipoOrden, int IdPlanilla){
		
		List<TblPagosDTO> planillaLista = tblPagosRepo.listaPlanilla(idLocal, IdTipoOrden, IdPlanilla);
		
		return planillaLista;
		
	}
	
	
	public List<TblPagosDTO> listaPagoTemporalFCH(int idLocal, int IdTipoOrden, int xIndicador, int idLog){
		
		List<TblPagosDTO> pagoTemporalLista = tblPagosRepo.listaPagoTemporalFCH(idLocal, IdTipoOrden, xIndicador, idLog);
		
		return pagoTemporalLista;
	}
	
	
	public List<TblPagosDTO> listaPagoTemporalTotal(int idLocal, int IdTipoOrden, int idLog){
		
		List<TblPagosDTO> PagoTemporalTotal = tblPagosRepo.listaPagoTemporalTotal(idLocal, IdTipoOrden, idLog);
		
		return  PagoTemporalTotal;
		
	}
	
	
	public List<TblPagosDTO> listaPagoTercero(int xIdLocal, int IdTipoOrden, String idCliente, String fechaInicial, String fechaFinal){
		
		
		List<TblPagosDTO> listaPagos = tblPagosRepo.listaPagoTercero(xIdLocal, IdTipoOrden, idCliente, fechaInicial, fechaFinal);
		
		return listaPagos;
		
		
	}
	
	
	public Integer validaReciboRetirado(int xIdLocal, int IdTipoOrden, int IdReciboCruce, int Indicador) {
		
		Integer validaRecibo = tblPagosRepo.validaReciboRetirado(xIdLocal, IdTipoOrden, IdReciboCruce, Indicador);
		
		if(validaRecibo == null) {
			
			validaRecibo = 0;
			return validaRecibo;
		}
		
		return validaRecibo;
		
	}
	
	
	public List<TblPagosDTO> listaUnFCH(int xIdLocal, int IdTipoOrden, int IdRecibo, int Indicador){
		
		List<TblPagosDTO> lista = tblPagosRepo.listaUnFCH(xIdLocal, IdTipoOrden, IdRecibo, Indicador);
		
		return lista;
		
	}
	
	
	public List<TblPagosDTO> totalReciboFCH(int xIdLocal, int IdTipoOrden, int IdRecibo, int Indicador){
		
		List<TblPagosDTO> totalRecibo = tblPagosRepo.totalReciboFCH(xIdLocal, IdTipoOrden, IdRecibo, Indicador);
		
		return totalRecibo;
	}
	
	
	public List<TblPagosDTO> listaReciboMedidor(int xIdLocal, int IdTipoOrden, int IdRecibo, int Indicador){
		
		List<TblPagosDTO> listaRecibo = tblPagosRepo.listaReciboMedidor(xIdLocal, IdTipoOrden, IdRecibo, Indicador);
		
		return listaRecibo;
		
	}
	
	
	public List<TblPagosDTO> totalPlanillaFCH(int xIdLocal, int IdTipoOrden, int IdPlanilla){
		
		
		List<TblPagosDTO> totalPlanilla = tblPagosRepo.totalPlanillaFCH(xIdLocal, IdTipoOrden, IdPlanilla);
		
		return totalPlanilla;
		
	}
	
	
}
