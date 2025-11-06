package com.marketing.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Model.dbaquamovil.TblTerceros;
import com.marketing.Repository.dbaquamovil.TblAgendaLogVisitasRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesDetalleRepo;
import com.marketing.Repository.dbaquamovil.TblDctosOrdenesRepo;
import com.marketing.Repository.dbaquamovil.TblDctosRepo;
import com.marketing.Repository.dbaquamovil.TblPlusInventarioRepo;
import com.marketing.Service.dbaquamovil.TblAgendaLogVisitasService;
import com.marketing.Service.dbaquamovil.TblCategoriasService;
import com.marketing.Service.dbaquamovil.TblCiudadesService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesDetalleService;
import com.marketing.Service.dbaquamovil.TblDctosOrdenesService;
import com.marketing.Service.dbaquamovil.TblDctosPeriodoService;
import com.marketing.Service.dbaquamovil.TblDctosService;
import com.marketing.Service.dbaquamovil.TblLocalesReporteService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblMedidoresMacroService;
import com.marketing.Service.dbaquamovil.TblPagosService;
import com.marketing.Service.dbaquamovil.TblPlusInventarioService;
import com.marketing.Service.dbaquamovil.TblPlusService;
import com.marketing.Service.dbaquamovil.TblTercerosRutaService;
import com.marketing.Service.dbaquamovil.TblTercerosService;
import com.marketing.Service.dbaquamovil.TblTipoCausaNotaService;
import com.marketing.Service.dbaquamovil.TblTipoOrdenSubcuentaService;
import com.marketing.ServiceApi.ReporteSmsServiceApi;
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaPluInventario;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;
import com.marketing.Utilidades.ProcesoGuardaPorcentaje;
import com.marketing.Utilidades.ProcesoIngresoComprobante;


import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;



@Controller
public class ClimaController {
	
	
	@Autowired
	 TblCategoriasService tblCategoriasService;
	
	@Autowired
	TblDctosPeriodoService tblDctosPeriodoService;
	
	@Autowired
	TblTercerosRutaService tblTercerosRutaService;
	
	@Autowired
	TblLocalesReporteService tblLocalesReporteService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblDctosService tblDctosService;
	
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
	TblCiudadesService  tblCiudadesService;
	
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
	TblTipoOrdenSubcuentaService tblTipoOrdenSubcuentaService;
	
	@Autowired
	TblDctosOrdenesRepo tblDctosOrdenesRepo;
	
	@Autowired
	TblDctosRepo tblDctosRepo;
	
	@Autowired
	TblAgendaLogVisitasRepo tblAgendaLogVisitasRepo;
	
	@Autowired
	ProcesoGuardaPorcentaje procesoGuardaPorcentaje;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	@Autowired
	ProcesoIngresoComprobante procesoIngresoComprobante;
	
	
	@Autowired
	ProcesoGuardaPluInventario procesoGuardaPluInventario;
	
	@Autowired
	TblPlusInventarioService tblPlusInventarioService;
	
	@Autowired
	TblPlusInventarioRepo tblPlusInventarioRepo;
	
	 @GetMapping("/Clima")
 	public String clima(HttpServletRequest request,Model model) {
		Class tipoObjeto = this.getClass();					
    String nombreClase = tipoObjeto.getName();		
    System.out.println("CONTROLLER " + nombreClase); 
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				Integer IdUsuario = usuario.getIdUsuario();
				
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
						
						 // Obtener la fecha actual
				        LocalDate fechaActual = LocalDate.now();

				        // Formatear la fecha como un String
				        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				        String strFechaVisita = fechaActual.format(formatter);
				        
				        System.out.println("strFechaVisita  es" + strFechaVisita);
				        
						
						
						String xFechaActual = fechaActual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				        model.addAttribute("xFechaActual", xFechaActual);
				        
				        
				        
				        Double latitud = tblLocalesService.ObtenerLatitud(idLocal);
				        Double longitud = tblLocalesService.ObtenerLongitud(idLocal);
				        
				        System.out.println("latitud  es " + latitud);
				        System.out.println("longitud  es " + longitud);
				        
				        
				        // --------------------- CONSULTA API OPEN-METEO ---------------------
				        try {
				            String apiUrl = "https://api.open-meteo.com/v1/forecast"
				                    + "?latitude=" + latitud
				                    + "&longitude=" + longitud
				                    + "&current=temperature_2m,weathercode"
				                    + "&daily=temperature_2m_max,temperature_2m_min,weathercode"
				                    + "&timezone=auto";

				            RestTemplate restTemplate = new RestTemplate();
				            String response = restTemplate.getForObject(apiUrl, String.class);
				            System.out.println("Respuesta es " + response);

				            ObjectMapper mapper = new ObjectMapper();
				            JsonNode root = mapper.readTree(response);

				            // Clima actual
				            JsonNode current = root.get("current");
				            double currentTemp = current.get("temperature_2m").asDouble();
				            int currentCode = current.get("weathercode").asInt();

				            // 🔹 Mapa con info del clima actual
				            Map<String, String> infoActual = obtenerDescripcionYIcono(currentCode);
				            Map<String, Object> hoy = new HashMap<>();
				            hoy.put("temperatura", currentTemp);
				            hoy.put("codigo", currentCode);
				            hoy.put("descripcion", infoActual.get("desc"));
				            hoy.put("icono", infoActual.get("icon"));

				            // 🔹 Pronóstico diario
				            JsonNode daily = root.get("daily");
				            List<Map<String, Object>> forecast = new ArrayList<>();

				            for (int i = 0; i < daily.get("time").size(); i++) {
				                Map<String, Object> day = new HashMap<>();
				                int weatherCode = daily.get("weathercode").get(i).asInt();

				                day.put("date", daily.get("time").get(i).asText());
				                day.put("max", daily.get("temperature_2m_max").get(i).asDouble());
				                day.put("min", daily.get("temperature_2m_min").get(i).asDouble());
				                day.put("code", weatherCode);

				                // descripción e ícono del día
				                Map<String, String> infoClima = obtenerDescripcionYIcono(weatherCode);
				                day.put("descripcion", infoClima.get("desc"));
				                day.put("icono", infoClima.get("icon"));

				                forecast.add(day);
				            }

				            // 🔹 Enviar todo al modelo
				            model.addAttribute("hoy", hoy);
				            model.addAttribute("forecast", forecast);

				        } catch (Exception e) {
				            e.printStackTrace();
				            model.addAttribute("error", "No se pudo obtener el clima actualmente");
				        }    
				        

				        
		
		return "Clima";
	}
	 
	 
	 
	// --------------------- MÉTODO AUXILIAR PARA ICONOS ---------------------
	 private String getWeatherIcon(int code) {
	     if (code == 0) return "☀️";          // Despejado
	     else if (code <= 3) return "⛅";     // Parcialmente nublado
	     else if (code <= 45) return "🌫️";   // Neblina
	     else if (code <= 67) return "🌧️";   // Lluvia
	     else if (code <= 77) return "❄️";   // Nieve
	     else if (code <= 99) return "⛈️";   // Tormenta
	     else return "❓";                    // Desconocido
	 }
	 
	 
	 private Map<String, String> obtenerDescripcionYIcono(int codigo) {
		    Map<String, String> data = new HashMap<>();

		    switch (codigo) {
		        case 0:
		            data.put("desc", "Despejado");
		            data.put("icon", "wi-day-sunny");
		            break;
		        case 1:
		            data.put("desc", "Mayormente despejado");
		            data.put("icon", "wi-day-sunny-overcast");
		            break;
		        case 2:
		            data.put("desc", "Parcialmente nublado");
		            data.put("icon", "wi-day-cloudy");
		            break;
		        case 3:
		            data.put("desc", "Nublado");
		            data.put("icon", "wi-cloudy");
		            break;
		        case 45: case 48:
		            data.put("desc", "Neblina");
		            data.put("icon", "wi-fog");
		            break;
		        case 51: case 53: case 55:
		            data.put("desc", "Llovizna");
		            data.put("icon", "wi-sprinkle");
		            break;
		        case 61: case 63: case 65:
		            data.put("desc", "Lluvia");
		            data.put("icon", "wi-rain");
		            break;
		        case 66: case 67:
		            data.put("desc", "Lluvia helada");
		            data.put("icon", "wi-sleet");
		            break;
		        case 71: case 73: case 75:
		            data.put("desc", "Nieve");
		            data.put("icon", "wi-snow");
		            break;
		        case 80: case 81: case 82:
		            data.put("desc", "Lluvias fuertes");
		            data.put("icon", "wi-showers");
		            break;
		        case 95:
		            data.put("desc", "Tormenta");
		            data.put("icon", "wi-thunderstorm");
		            break;
		        case 96: case 99:
		            data.put("desc", "Tormenta con granizo");
		            data.put("icon", "wi-storm-showers");
		            break;
		        default:
		            data.put("desc", "Desconocido");
		            data.put("icon", "wi-na");
		    }
		    return data;
		}


}
