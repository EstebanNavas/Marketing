package com.marketing.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Projection.TblOpcionesDTO;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;
import com.marketing.Service.dbaquamovil.TblOpcionesService;

@Controller
public class LoginController {
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@Autowired
	TblOpcionesService tblOpcionesService;
	
	@PostMapping("/login-post")
		//Se obtienen los valores ingresados en el form del index
	 public String login(HttpServletRequest request,  @RequestParam(value = "usuario", required = false) String usuario, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "sistema", required = false) String sistema,
                        Model model) {

        Integer idUsuario = (int) Long.parseLong(usuario);
        
        
        System.out.println("Entró a /login-post");
        
        if("aquamovil".equals(sistema)) {
        	System.out.println(" El sistema si es : " + sistema);
        }else {
        	System.out.println(" El sistema no es aquamovil, el sitema es  " + sistema);
        	model.addAttribute("error", "Sistema no válido");
        	model.addAttribute("url", "/");
        	return "defaultErrorSistema";  // Mostrar página de error
        }
        // Se obtiene el usuario autenticado
        boolean isAuthenticated = ctrlusuariosService.authenticate(idUsuario, password);
        
        Ctrlusuarios usuarioAutenticado = ctrlusuariosService.obtenerUsuario(idUsuario);
        Integer xidNivel = usuarioAutenticado.getIdNivel(); // El idNivel del usuario Logueado
        
         // Se obtiene el Idlocal de ctrlusuarios pasanddole como argumento el idUsuario
        Integer idLocalAutenticado = ctrlusuariosService.consultarIdLocalPorIdUsuario(idUsuario);
        
        // Obtenemos la Lista de Opciones Tipo 1
        List<Integer> ObtenerListaIdTipoOpcion1 = tblOpcionesService.ObtenerListaIdTipoOpcion1(idLocalAutenticado);
        System.out.println(" ObtenerListaIdTipoOpcion1 es : " + ObtenerListaIdTipoOpcion1);
        
        //Optenemos del idPerfil Logueado las opciones que coincidan con la lista ObtenerListaIdTipoOpcion1
        List<Integer> ListaIdTipoOpcion1OpcionesPerfil  = tblOpcionesService.ListaIdTipoOpcion1OpcionesPerfil(idLocalAutenticado, ObtenerListaIdTipoOpcion1, xidNivel);
        System.out.println("ListaIdTipoOpcion1OpcionesPerfil : " + ListaIdTipoOpcion1OpcionesPerfil);  
        
        
        // Se obtiene la lista de las opciones Tipo 1 dependiendo de la lista ObtenerListaIdTipoOpcion1
        List<TblOpcionesDTO>  ListaOpcionesTipo1 = tblOpcionesService.ObtenerTipoOpciones1(idLocalAutenticado, ListaIdTipoOpcion1OpcionesPerfil);
        System.out.println("La ListaOpcionesTipo1 es : " + ListaOpcionesTipo1);

        if (isAuthenticated) {
            
        	System.out.println("isAuthenticated es : " + isAuthenticated);
        	// Se setean los valores a las variables 
            request.getSession().setAttribute("local", tblLocalesService.consultarLocal(idLocalAutenticado));
            request.getSession().setAttribute("usuarioAuth", usuarioAutenticado);
            request.getSession().setAttribute("sistema", sistema);
            request.getSession().setAttribute("ListaOpcionesTipo1", ListaOpcionesTipo1); // Guardamos la lista en una variable de Session para usarla posteriormente en Thymeleaf
            
            System.out.println(" request.getSession() es  " + request.getSession());
            
            model.addAttribute("ListaOpcionesTipo1", ListaOpcionesTipo1);

            return "menuPrincipal";  // Redirigir a la página principal
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrecto");
            model.addAttribute("url", "/");
            return "defaultError";  // Mostrar página de error
        }
        
    }
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,Model model) {
		request.getSession().invalidate();
		
		return "redirect:/";
		
	}

}
