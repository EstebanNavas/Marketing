package com.marketing.Service.dbaquamovil;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Repository.dbaquamovil.CtrlusuariosRepo;

@Service
public class CtrlusuariosService {
	
	@Autowired 
	CtrlusuariosRepo ctrlusuariosRepo;
	
	
	// EXTRAEMOS LOS DATOS DE INICIO DE SESION DEL LOCAL
	
	  public boolean authenticate(Integer idUsuario, String clave) {
	        Optional<Ctrlusuarios> usuarioOptional = ctrlusuariosRepo.findByIdUsuario(idUsuario);

	        if (usuarioOptional.isPresent()) {
	            Ctrlusuarios usuario = usuarioOptional.get();
	            // Comparar la contraseña ingresada con la contraseña almacenada en la base de datos
	            if (usuario.getClave().equals(clave)) {
	                return true;  // Autenticación exitosa
	            }
	        }
	        return false;  // Autenticación fallida
	    }
	  
	  public Ctrlusuarios obtenerUsuario(Integer idUsuario) {
		    Optional<Ctrlusuarios> usuarioOptional = ctrlusuariosRepo.findByIdUsuario(idUsuario);
		    return usuarioOptional.orElse(null); // Maneja el caso si no se encuentra el usuario
		}
	  
	  public Integer consultarIdLocalPorIdUsuario(Integer idUsuario) {
		    Optional<Ctrlusuarios> usuarioOptional = ctrlusuariosRepo.findByIdUsuario(idUsuario);
		    
		    if (usuarioOptional.isPresent()) {
		    	Ctrlusuarios usuario = usuarioOptional.get();
		    	System.out.println("consultarIdLocalPorIdUsuario : " + usuario.getIdLocal());
		        return usuario.getIdLocal();
		        
		    } else {
		        System.out.println("No se encontró ningún usuario con el idUsuario: " + idUsuario);
		        return null;
		    }
		}
}
