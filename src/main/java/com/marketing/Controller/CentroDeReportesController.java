package com.marketing.Controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogSoportes;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;

import com.marketing.Repository.dbaquamovil.TblPlusRepo;
import com.marketing.Service.DBMailMarketing.TblPucAuxService;
import com.marketing.Service.dbaquamovil.TblAgendaLogSoportesService;
import com.marketing.Service.dbaquamovil.TblCategoriasService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTerceroEstractoService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;

import org.springframework.http.MediaTypeFactory;






import java.io.FileInputStream;

@Controller
public class CentroDeReportesController {
	
	 @Autowired
	 TblCategoriasService tblCategoriasService;
	 
	 @Autowired
	 TblPlusService tblPlusService;
	 
	 @Autowired
	 TblTerceroEstractoService  tblTerceroEstractoService;
	 
	 @Autowired
	 TblPucAuxService tblPucAuxService;
	 
	 @Autowired
	 TblLocalesService tblLocalesService;
	 		
		@Autowired
		TblDctosService TblDctosService;
		
		@Autowired
		TblLocalesReporteService tblLocalesReporteService;
		
		@Autowired
		ReporteSmsServiceApi reporteSmsServiceApi;
	 
	 @Autowired
	 TblPlusRepo tblPlusRepo;
	 
		@Autowired
		ControlDeInactividad controlDeInactividad;
		
		@Autowired
		TblAgendaLogSoportesService tblAgendaLogSoportesService;

	@GetMapping("/CentroDeReportes")
	public String CentroDeReportes(HttpServletRequest request,Model model) {
		
		Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
		
		Integer idUsuario = usuario.getIdUsuario();
		Double idUsuarioDouble = idUsuario.doubleValue();
		
		Integer idLocal = usuario.getIdLocal();
		
		
		 System.out.println("INGRESÓ A Referencia");
		// ----------------------------------------------------------- VALIDA INACTIVIDAD ------------------------------------------------------------
	    HttpSession session = request.getSession();
	    //Integer idUsuario = (Integer) session.getAttribute("xidUsuario");
	    
	    @SuppressWarnings("unchecked")
		List<TblAgendaLogVisitas> UsuarioLogueado = (List<TblAgendaLogVisitas>) session.getAttribute("UsuarioLogueado");
	    
	    Integer estadoUsuario = 0;
	    

	        for (TblAgendaLogVisitas usuarioLog : UsuarioLogueado) {
	            Integer idLocalUsuario = usuarioLog.getIdLocal();
	            Integer idLogUsuario = usuarioLog.getIDLOG();
	            String sessionIdUsuario = usuarioLog.getSessionId();
	            
	            
	           estadoUsuario = controlDeInactividad.ingresa(idLocalUsuario, idLogUsuario, sessionIdUsuario);          
	        }
    
	           if(estadoUsuario.equals(2)) {
	        	   System.out.println("USUARIO INACTIVO");
	        	   return "redirect:/";
	           }
		
		//------------------------------------------------------------------------------------------------------------------------------------------

		    
	           List<TblAgendaLogSoportes> listaReportes = tblAgendaLogSoportesService.ObtenerReportesXUsuario(idLocal, idUsuarioDouble);

			    model.addAttribute("xlistaReportes", listaReportes);
		    

			
			return "Reporte/CentroDeReportes";


	}
	
	
	@GetMapping("/descargarReporte")
	public ResponseEntity<Resource> descargarReporte(
	        @RequestParam("ruta") String ruta,
	        @RequestParam("nombre") String nombre) {

	    try {

	        File file = new File(ruta);

	        if (!file.exists()) {
	            System.err.println("❌ Archivo no existe: " + ruta);
	            return ResponseEntity.notFound().build();
	        }

	        InputStreamResource resource =
	                new InputStreamResource(new FileInputStream(file));

	        // Obtener extensión 
	        String extension = ruta.substring(ruta.lastIndexOf("."));

	        
	        String nombreLimpio = nombre.replaceAll("[\\\\/:*?\"<>|]", "");

	        //Detectar tipo automáticamente
	        MediaType mediaType = MediaTypeFactory
	                .getMediaType(file.getName())
	                .orElse(MediaType.APPLICATION_OCTET_STREAM);

	        return ResponseEntity.ok()
	                .header("Content-Disposition",
	                        "attachment; filename=\"" + nombreLimpio + extension + "\"")
	                .contentType(mediaType)
	                .contentLength(file.length())
	                .body(resource);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.internalServerError().build();
	    }
	}


}
