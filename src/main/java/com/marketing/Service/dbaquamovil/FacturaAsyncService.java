package com.marketing.Service.dbaquamovil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.marketing.FacturaMedidor;

@Service
public class FacturaAsyncService {
	
	@Autowired
	 FacturaMedidor facturaMedidor;

    @Async
    public void ejecutarFacturacion(Integer idPeriodo, Integer idLocal,
                                     Integer idUsuario, String contrasena) {

        // Llama al JAR externo
        facturaMedidor.ejecutarJar(
            idPeriodo, idLocal, idUsuario, contrasena
        );
    }
}
