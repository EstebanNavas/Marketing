package com.marketing.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.marketing.MailjetTask;
import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblDctosPeriodo;
import com.marketing.Repository.DBMailMarketing.TblSiteStyleRepo;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ProcesoAjusteConsumoCliente;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaCredito;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;




import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

@Controller
public class SubirNoticiaController {
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblDctosService TblDctosService;
	
	@Autowired
	TblTercerosService tblTercerosService;
	
	@Autowired
	TblDctosOrdenesService tblDctosOrdenesService;
	
	@Autowired
	TblPagosService tblPagosService;
	
	@Autowired
	TblMedidoresMacroService tblMedidoresMacroService;
	
	@Autowired
	TblTipoCausaNotaService tblTipoCausaNotaService;
	
	@Autowired
	TblAgendaLogVisitasService tblAgendaLogVisitasService;
	
	@Autowired
	TblDctosOrdenesDetalleService tblDctosOrdenesDetalleService;
	
	@Autowired
	TblPlusService tblPlusService;
	
	@Autowired
	ReporteSmsServiceApi reporteSmsServiceApi;
	
	@Autowired
	TblDctosOrdenesDetalleRepo tblDctosOrdenesDetalleRepo;
	
	@Autowired
	ProcesoCreaLecturaMovil procesoCreaLecturaMovil;
	
	@Autowired
	ProcesoGuardaLecturaMovil procesoGuardaLecturaMovil;
	
	@Autowired
	ProcesoGuardaPluOrden procesoGuardaPluOrden;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	ProcesoGuardaCredito procesoGuardaCredito;
	
	@Autowired
	ProcesoAjusteConsumoCliente procesoAjusteConsumoCliente;
	
	@Autowired
	MailjetTask mailjetTask;
	
	@Autowired
	TblSiteStyleRepo tblSiteStyleRepo;

	
	
	@GetMapping("/SubirNoticia")
	public String pruebaUrl(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer IdUsuario = usuario.getIdUsuario();

				
				
				// ------------------------------------------------- CONTROL DE PERIODO ACTIVO ----------------------------------------------------------------
				// Obtenemos el periodo activo
				List <TblDctosPeriodo> PeriodoActivo = tblDctosPeriodoService.ObtenerPeriodoActivo(idLocal);
				
				Integer idPeriodo = 0;
				
				
				for(TblDctosPeriodo P : PeriodoActivo) {
					
					idPeriodo = P.getIdPeriodo();
					model.addAttribute("xIdPeriodo", P.getIdPeriodo());
					model.addAttribute("xINombrePeriodo", P.getNombrePeriodo());
				
				}

				
		
		return "Administrador/SubirNoticia";
	}
	
	
	@PostMapping("/SubirNoticia")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> SubirArchivo(@RequestParam("nombreArchivo") String nombreArchivo,
															@RequestParam("archivo") MultipartFile archivo, HttpServletRequest request){
		
		
		Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
		
		
		HttpSession session = request.getSession();
	    session.removeAttribute("xIdLogPago");
	    
	    Integer xIdUsuario = usuario.getIdUsuario();
	    Integer IdLocal = usuario.getIdLocal();
	    
	    Map<String, Object> response = new HashMap<>();
	    

	    
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha como un String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String strFechaVisita = fechaActual.format(formatter);


	    System.out.println("SI ENTRÓ A  /SubirArchivo con nombre:  " + nombreArchivo);
	    System.out.println("SI ENTRÓ A  /SubirArchivo con archivo:  " + archivo);
	    
	    
		
     // Obtener tamaño del archivo en bytes
     	long fileSizeInBytes = archivo.getSize();

 		// Convertir el tamaño a MB
 		double fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0);
 		System.out.println("Tamaño del archivo " + fileSizeInMB);

 		// límite de 8 MB
 		if (fileSizeInMB > 8.0) {
 		    
 			response.put("message", "PESADO");
 		    return ResponseEntity.ok(response);
 		}
 		
 		// Ruta donde se va a guardar el PDF
 		String rutaUploadWindows = "C:\\proyectoWeb\\FileGral\\aquamovil\\sitioweb\\151";
 		
 		String rutaUploadLinux = "/home/sw/FileGral/aquamovil/sitioweb/" + IdLocal;
 		
 		Path uploadPath = Paths.get(rutaUploadLinux);
			
		Path filePath = uploadPath.resolve(nombreArchivo + ".pdf");
 		
 		try {
 			
 			
 	        Files.copy(archivo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
 		
 		} catch(IOException e) {
 			e.printStackTrace();
 			response.put("message", "ERROR AL GUARDAR ARCHIVO");
 		    return ResponseEntity.ok(response);
 		}
 		
 		
 		//String xCampo = nombreArchivo;
	    String xSistema = "aquamovil";
	    String xTIpo = "documento";
	   // String xValor = rutaUpload;
	    
	    
	    String rutaBDWindows = "/images/"+ IdLocal + "/"+ nombreArchivo + ".pdf ";
	    String rutaBDLinux = "/marketing/images/"+ IdLocal + "/"+ nombreArchivo + ".pdf ";
	    
 		System.out.println("rutaBD es " + rutaBDWindows);
	    
	    tblSiteStyleRepo.ingresaNoticia(IdLocal, nombreArchivo, xSistema, xTIpo, rutaBDWindows);
	    
	    
 		System.out.println("Nombre del archivo: " + nombreArchivo);  // Aquí debe imprimir el nombre que ingresaste en la vista
 	    
		System.out.println("Ruta de guardado: " + rutaUploadLinux);
 		System.out.println("Tamaño del archivo: " + fileSizeInMB + " MB");
 		
 		System.out.println("SALIÓ DEL PROCESO DE SUBIR PDF");
 		
 
	    
	    
	    response.put("message", "OK");
	    return ResponseEntity.ok(response);
	    
	}

	
	 
}
