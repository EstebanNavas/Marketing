package com.marketing.Service.dbaquamovil;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Projection.TblTercerosProjectionDTO;
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
	
	public List<String> obtenerTelefonosCelularesPorIds(@Param("ids") List<Integer> ids, int idLocal) {
        List<String> telefonosCelulares = tblTercerosRepo.findTelefonoCelularByIdsAndIdLocal(ids, idLocal);
        return telefonosCelulares;
    }

}
































