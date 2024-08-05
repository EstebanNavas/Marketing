package com.marketing.Utilidades;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblTercerosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblContableRetencionService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatterBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class ControlDeInactividad {
	
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;

	@Autowired
	TblLocalesService tblLocalesService;

	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblPlusService tblPlusService;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblContableRetencionService tblContableRetencionService;
	
	@Autowired
	TblDctosService tblDctosService;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	TblTercerosRepo tblTercerosRepo;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
    private HttpServletRequest request;
	
	
	public Integer ingresa(int idLocal, int idLog, String sessionId) {
	    
		 // Obtener la última fecha y hora de la sesión con idEstadoTx = 9
	    String fechaYHoraSessionId = tblAgendaLogVisitasService.ObtenerFechaYHoraSessionId(idLocal, idLog, sessionId);
	    System.out.println("fechaYHoraSessionId es: " + fechaYHoraSessionId);

	    // Construir un formateador que acepte tanto 2 como 3 dígitos en los milisegundos
	    DateTimeFormatter formateador = new DateTimeFormatterBuilder()
	        .appendPattern("yyyy-MM-dd HH:mm:ss")
	        .optionalStart()
	        .appendFraction(java.time.temporal.ChronoField.NANO_OF_SECOND, 0, 3, true)
	        .optionalEnd()
	        .toFormatter();

	    // Convierte fechaYHoraSessionId a LocalDateTime
	    LocalDateTime fechaYHoraSessionIdFormateada = LocalDateTime.parse(fechaYHoraSessionId, formateador);
	    System.out.println("fechaYHoraSessionId formateada es: " + fechaYHoraSessionIdFormateada.format(formateador));

	    // Se obtiene la fecha y hora actual sin milisegundos
	    LocalDateTime fechaHoraActual = LocalDateTime.now().withNano(0);
	    System.out.println("fechaHoraActual es: " + fechaHoraActual.format(formateador));

	    // Compara las fechas y horas
	    long diferenciaMinutos = ChronoUnit.MINUTES.between(fechaYHoraSessionIdFormateada, fechaHoraActual);
	    System.out.println("diferenciaMinutos es : " + diferenciaMinutos);
	    
	    
	    if (diferenciaMinutos >= 5) {
	        System.out.println("Han pasado más de 2 minutos desde la última sesión.");
	        
	        //Obtenemos el idLocal de la sessionId
        	Integer xIdLocal = tblAgendaLogVisitasService.ObtenerIdLocalPorSession(sessionId);
        	
        	// Actualizamos los idEstadoTx Que sean = 9 a 1
            tblAgendaLogVisitasRepo.actualizarIdEstadoTxA1(xIdLocal, sessionId);
        	System.out.println("Sessión cerrada para el usuario con ID de sessión : " + sessionId);
        	
        	// Invalidar la sesión
        	HttpSession session = request.getSession();
        	String idSessionDelRequest = session.getId();
        	System.out.println("idSessionDelRequest es: " + idSessionDelRequest);
        	
        	request.getSession().invalidate();
        	
            
        	return 2;
        	
	        
	    } else {
	        System.out.println("No han pasado más de 5 minutos desde la última sesión.");
	        

	        // Formatear la fecha como un String
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String strFechaVisita = fechaHoraActual.format(formatter);
	        System.out.println("strFechaVisita es: " + strFechaVisita);
	        
	        //Actualizamos la fecha y hora de la session
	        tblAgendaLogVisitasRepo.actualizaActividad(strFechaVisita, idLocal, idLog, sessionId);
	        
	        
	        
	    }

	    return 0;
	}

}
