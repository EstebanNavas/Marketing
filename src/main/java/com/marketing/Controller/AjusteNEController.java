package com.marketing.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Model.dbaquamovil.TblAgendaLogVisitas;
import com.marketing.Projection.TblDctosDTO;
import com.marketing.Projection.TercerosDTO2;
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
import com.marketing.Utilidades.ControlDeInactividad;
import com.marketing.Utilidades.ProcesoCreaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaLecturaMovil;
import com.marketing.Utilidades.ProcesoGuardaNE;
import com.marketing.Utilidades.ProcesoGuardaPluOrden;

@Controller
public class AjusteNEController {
	
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
	ProcesoGuardaNE procesoGuardaNE;
	
	@Autowired
	ControlDeInactividad controlDeInactividad;
	
	
	
	
	@GetMapping("/AjusteNE")
	public String ajusteNE(HttpServletRequest request,Model model) {
		
		// Validar si el local está logueado	
				Ctrlusuarios usuario = (Ctrlusuarios)request.getSession().getAttribute("usuarioAuth");
				String sistema=(String) request.getSession().getAttribute("sistema");
				
				int idLocal = usuario.getIdLocal();
				
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

               

				
		
		return "NominaElectronica/AjusteNEConsulta";
	}
	

	
	
	
	
	@PostMapping("/EnviarAjusteDIAN-Post")
    public ResponseEntity<Map<String, String>> EnviarAjusteDIAN(@RequestBody Map<String, Object> requestBody, HttpServletRequest request, Model model) {

        Ctrlusuarios usuario = (Ctrlusuarios) request.getSession().getAttribute("usuarioAuth");
       
        Integer idLocal = usuario.getIdLocal();
        
        
        // Obtenemos los datos del JSON recibido
	    String xIdDcto = (String) requestBody.get("xIdDcto");
	    System.out.println("Entró a /EnviarAjusteDIAN con xIdDcto " + xIdDcto);
        
        Integer xIdDctoInt = Integer.parseInt(xIdDcto);
        
        int xIdTipoOrden = 8;
        int xIndicador = 1;
        
        
        List<TblDctosDTO> unDcto = TblDctosService.listaUnDcto(idLocal, xIdTipoOrden, xIdDctoInt, xIndicador);
        
        Double xIdPeriodo = 0.0;
        String xIdCliente = "";
        
        for(TblDctosDTO dcto : unDcto) {
        	
        	xIdPeriodo = dcto.getIdDcto();
        	xIdCliente = dcto.getIdCliente();
        }
        
        int xEnvioFE_proceso = 1;
        
        //
        String xCharSeparator = File.separator;
        String xRuta = "";

        // Linux 
        if (xCharSeparator.compareTo("/") == 0) {

            // Linux             
            xRuta = "" + xCharSeparator + "home" + xCharSeparator + "sw" + xCharSeparator + "jar" + xCharSeparator + "ApiSoenacNEAjuste" + xCharSeparator + "dist" + xCharSeparator + "ApiSoenacNEAjuste.jar ";

        } else {

            // Windows          
            xRuta = "C:" + xCharSeparator + "proyectoWeb" + xCharSeparator + "ApiSoenacNEAjuste" + xCharSeparator + "dist" + xCharSeparator + "ApiSoenacNEAjuste.jar ";
        }
        
        
        final String xRutaDisco = xRuta;

        //
        final int xIdLocalUsuarioFinal = idLocal;
        final int xIdDctoFinal = xIdDctoInt;
        
        //
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    //
                    Runtime rt = Runtime.getRuntime();

                    //
                    Process proc = rt.exec("java -jar " + xRutaDisco
                            + xIdLocalUsuarioFinal + " "
                            + xIdDctoFinal);

                //  System.out.println("  "+ proc);
                    //
                    BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

                    BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

                    // read the output from the command
                    String s = null;
                    while ((s = stdInput.readLine()) != null) {
                        System.out.println(s);
                    }

                    // read any errors from the attempted command
                    while ((s = stdError.readLine()) != null) {
                        System.out.println(s);
                    }
                    proc.waitFor();
                    System.out.println("success");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        t.start();
        
        
       


        Map<String, String> response = new HashMap<>();
        
        response.put("mensaje", "OK");
        return ResponseEntity.ok(response);
    }
	

}
