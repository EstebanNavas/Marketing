package com.marketing.Service.dbaquamovil;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Model.dbaquamovil.Ctrlusuarios;
import com.marketing.Projection.CtrlusuariosDTO;
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
	            Integer estado = usuario.getEstado(); // Obtenemos el estado del idUsuario
	            System.out.println("Estado del usuario logueado es : " + estado);
	            
	            // Comparamos la contraseña ingresada con la contraseña de la base de datos y si el estado es = 1
	            if (usuario.getClave().equals(clave) & estado == 1) {
	            	System.out.println("El usuario logueado SI cumple con el estado : " + estado);
	                return true;  // Autenticación exitosa
	            }
	        }
	        System.out.println("El usuario logueado NO cumple con el estado, o datos incorrectos ");
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
	  
	  
	  public List <CtrlusuariosDTO> obtenerNombresUsuarios(int idLocal, int idUsuario ){
		  
		  List <CtrlusuariosDTO> Usuarios = ctrlusuariosRepo.obtenerNombresUsuarios(idLocal, idUsuario);
		  
		  return Usuarios;
		  
	  }
	  
	  public String obtenerClaveUsuario(int idLocal, int idUsuario) {
		  
		  String Clave = ctrlusuariosRepo.obtenerClaveUsuario(idLocal, idUsuario);
		  
		  return Clave;
		  
	  }
	  
	  
	  public List <CtrlusuariosDTO> obtenerOperarios(int idLocal){
		  
		  List <CtrlusuariosDTO> operarios = ctrlusuariosRepo.obtenerOperarios(idLocal);
		  
		  return operarios;
	  }
	  
	  public String listaAutorizador(int idLocal, int idNivel, int estado) {
		  
		  String Clave = ctrlusuariosRepo.listaAutorizador(idLocal, idNivel, estado);
		  
		  return Clave;
	  }
	  
	  
	  public Integer listaAutorizador(int idLocal, int idNivel, int estado, String clave) {
		  
		  Integer idusuario = ctrlusuariosRepo.listaAutorizador(idLocal, idNivel, estado, clave);
		  
		  if(idusuario == null) {
			  
			  idusuario = 0;
			  return idusuario;
		  }
		  
		  return idusuario;
	  }
	  
	  
	  public List <CtrlusuariosDTO> obtenerUsuariosActivosNivel5(int idLocal){
		  
		  List <CtrlusuariosDTO> usuarios = ctrlusuariosRepo.obtenerUsuariosActivosNivel5(idLocal);
		  
		  return usuarios;
		  
	  }
	  
	  
	  public String obtenerNombreUsuario(int idLocal, int idusuario){
		  
		  String nombreUsuario = ctrlusuariosRepo.obtenerNombreUsuario(idLocal, idusuario);
		  
		  return nombreUsuario;
		  
	  }
	  
	  
}
