package com.marketing.Service.dbaquamovil;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblTerceroEstracto;
import com.marketing.Model.dbaquamovil.TblTercerosSui;
import com.marketing.Repository.dbaquamovil.TblTercerosSuiRepo;

@Service
public class TblTercerosSuiService {
	
	@Autowired
	TblTercerosSuiRepo tblTercerosSuiRepo;
	
	
	public boolean ingresarTerceroSui(int idLocal, String idCliente, int idTipoTercero) {
		
		Integer ESTADO = 1;
		Integer cero = 0;
		Integer uno = 1;
		Integer NumeroFicha = 999999999;
		
		// Creamos una instancia de  TblTercerosSui
		TblTercerosSui orden = new TblTercerosSui();
		
    	orden.setIdLocal(idLocal);
    	orden.setIdCliente(idCliente);
    	orden.setIdTipoTercero(idTipoTercero);
        orden.setEstado(ESTADO);
        orden.setIdIgacZona(cero);
        orden.setIdIgacSector(cero);
        orden.setIdIgacVereda(cero);
        orden.setIdIgacPredio(cero);
        orden.setIdIgacCondPredio(cero);
        orden.setIdUMultiusuario(cero);
        orden.setIdUNoMultiusuario(cero);
        orden.setIdHogarComunitario(cero);
        orden.setEstadoMedidor(uno);
        orden.setIdTipoLectura(cero);
        orden.setVrCostoMediaTasaUso(cero);
        orden.setIdCausaRefactura(cero);
        orden.setDctoRefactura(cero);
        orden.setNumeroFicha(NumeroFicha);
        orden.setCorregimiento(cero);
        orden.setBarrio(cero);
        orden.setEdificio(cero);
        orden.setUnidadPredial(cero);
        orden.setUsuFactAforo(cero);
        orden.setUsuCuentaCaracterizacion(uno);
        
		
		// Guardamos el objeto orden en la tabla TblTercerosSui
    	tblTercerosSuiRepo.save(orden);
    	
    	System.out.println("TERCEROSUI INGRESADO CORRECTAMENTE");
		
		return true;
	}

}
