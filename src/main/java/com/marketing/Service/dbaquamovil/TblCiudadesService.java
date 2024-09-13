package com.marketing.Service.dbaquamovil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketing.Projection.TblCiudadesDTO;
import com.marketing.Repository.dbaquamovil.TblCiudadesRepo;

@Service
public class TblCiudadesService {

	@Autowired
	TblCiudadesRepo tblCiudadesRepo;
	
	public List<TblCiudadesDTO> ListaCiudadesDepartamentos(){
		
		List<TblCiudadesDTO> Lista = tblCiudadesRepo.ListaCiudadesDepartamentos();
		
		return Lista;
	}
	
	
	public String NombreCiudad(int idCiudad) {
		
		String nombreCiudad = tblCiudadesRepo.NombreCiudad(idCiudad);
		
		return nombreCiudad;
	}
}
