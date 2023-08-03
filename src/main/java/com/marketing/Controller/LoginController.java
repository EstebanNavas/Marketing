package com.marketing.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Service.dbaquamovil.CtrlusuariosService;
import com.marketing.Service.dbaquamovil.TblLocalesService;

@Controller
public class LoginController {
	
	@Autowired
	CtrlusuariosService ctrlusuariosService;
	
	@Autowired
	TblLocalesService tblLocalesService;
	
	@PostMapping("/login-post")
	
	//Se obtienen los valores ingresados en el form del index
	 public String login(HttpServletRequest request,
                        @RequestParam(value = "usuario", required = false) String usuario,
                        @RequestParam(value = "password", required = false) String password,
                        @RequestParam(value = "sistema", required = false) String sistema,
                        Model model) {
		
		// Se parsea usuario a un tipo de dato Integer
        Integer idUsuario = (int) Long.parseLong(usuario);

        System.out.println("Entró a /login-post");
        // Se obtiene el usuario autenticado
        boolean isAuthenticated = ctrlusuariosService.authenticate(idUsuario, password);
        Ctrlusuarios usuarioAutenticado = ctrlusuariosService.obtenerUsuario(idUsuario);

        if (isAuthenticated) {
            
        	// Se setean los valores a las variables 
            request.getSession().setAttribute("local", tblLocalesService.consultarLocal(idUsuario));
            request.getSession().setAttribute("usuarioAuth", usuarioAutenticado);
            request.getSession().setAttribute("sistema", sistema);

            return "redirect:/";  // Redirigir a la página principal
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
