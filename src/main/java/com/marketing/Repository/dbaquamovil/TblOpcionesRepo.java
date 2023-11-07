package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblOpciones;
import com.marketing.Projection.TblOpcionesDTO;

@Repository
public interface TblOpcionesRepo extends JpaRepository<TblOpciones, Integer>{

	
	  @Query( value = "SELECT tblopciones.idOpcion, " +
			  "tblopciones.nombreOpcion, " +
			  "tblopciones.rutaOpcion, " +
			  "tblopciones.idTipoOpcion " +
	  		  "FROM bdaquamovil.dbo.tblOpciones " +
	  		  "WHERE tblopciones.idTipoOpcion = 1 " +
	  		  "AND tblopciones.idLocal = ?1 " +
	  		  "AND tblopciones.idOpcion IN ?2 " +
	  		  "ORDER BY tblopciones.idOpcion ", nativeQuery = true)
  List<TblOpcionesDTO> ObtenerTipoOpciones1(int idLocal, List<Integer> idOpciones);
	  
	  
	  
	  @Query( value = "SELECT tblopciones.idOpcion " +
	  		  "FROM bdaquamovil.dbo.tblOpciones " +
	  		  "WHERE tblopciones.idTipoOpcion = 1 " +
	  		  "AND tblopciones.idLocal = ?1 " +
	  		  "ORDER BY tblopciones.idOpcion ", nativeQuery = true)
  List<Integer> ObtenerListaIdTipoOpcion1(int idLocal);
	  
	  
	  @Query( value = "SELECT tblOpcionesPerfil.idOpcion " +
	  		  "FROM bdaquamovil.dbo.tblOpcionesPerfil " +
	  		  "WHERE tblOpcionesPerfil.idLocal = ?1 " +
	  		  "AND tblOpcionesPerfil.idOpcion IN ?2 " +
	  		  "AND tblOpcionesPerfil.idPerfil = ?3 " +
	  		  "ORDER BY tblOpcionesPerfil.idOpcion ", nativeQuery = true)
  List<Integer> ListaIdTipoOpcion1OpcionesPerfil(int idLocal, List<Integer> idOpciones, int xIdPerfil );
	  
	  
	  
	  @Query( value = "SELECT * FROM " +
			  "(SELECT *	FROM " +
			  "(SELECT tblopciones.idLocal " +
			  ",tblopciones.idOpcion " +
	  		  ",tblopciones.nombreOpcion " +
	  		  ",tblopciones.rutaOpcion " +
	  		  ",(SELECT ISNULL(COUNT(*),0) " +
	  		  "FROM tblopciones " +
	  		  "WHERE tblopciones.idOpcionPadre = ?1 " +
	  		  "AND tblopciones.idLocal  =  ?2  ) AS numeroHijo " +
	  		  ",tmpCHK.isChecked " +
	  		  ",tblopciones.idTipoOpcion " +
	  		  "FROM tblopciones " +
	  		  "INNER JOIN (SELECT tblopcionesperfil.idLocal " +
	  		  ",tblopcionesperfil.idOpcion " +
	  		  ",tblopcionesperfil.idPerfil " +
	  		  ",1 AS isChecked " +
	  		  "FROM tblopcionesperfil " +
	  		  "WHERE tblopcionesperfil.idPerfil = ?3 " +
	  		  "AND tblopcionesperfil.idLocal    =  ?2 " +
	  		  "UNION " +
	  		  "SELECT tblopciones.idLocal, " +
	  		  "tblopciones.idOpcion, " +
	  		  "?3 AS idPerfil, " +
	  		  "2 AS isChecked " +
	  		  "FROM tblopciones " +
	  		  "WHERE tblopciones.idOpcion = ?1 " +
	  		  "AND tblopciones.idLocal    = ?2 " +
	  		  "AND NOT EXISTS " +
	  		  "(SELECT tblopcionesperfil.* " +
	  		  "FROM tblopcionesperfil " +
	  		  "WHERE tblopcionesperfil.idPerfil = ?3 " +
	  		  "AND tblopcionesperfil.idLocal    = ?2 " +
	  		  "AND tblopcionesperfil.idOpcion   = tblopciones.idOpcion ))  AS tmpCHK " +
	  		  "ON tmpCHK.idOpcion = tblopciones.idOpcion " +
	  		  "AND tmpCHK.idLocal = tblopciones.idLocal " +
	  		  "WHERE tblopciones.idOpcion   = ?1 " +
	  		  "AND tblopciones.idLocal    = ?2  ) AS tmpPad " +
	  		  "WHERE tmpPad.numeroHijo=0 " +
	  		  "AND tmpPad.idTipoOpcion = 2 " +
	  		  "AND tmpPad.idLocal   = ?2 " +
	  		  "UNION " +
	  		  "SELECT tblopciones.idLocal " +
	  		  ",tblopciones.idOpcion " +
	  		  ",tblopciones.nombreOpcion " +
	  		  ",tblopciones.rutaOpcion " +
	  		  ",(SELECT ISNULL(COUNT(*),0) " +
	  		  "FROM tblopciones " +
	  		  "WHERE tblopciones.idOpcionPadre =  tmpOPC.idOpcion " +
	  		  "AND tblopciones.idLocal = tmpOPC.idLocal) AS numeroHijo " +
	  		  ",tmpOPC.isChecked " +
	  		  ",tblopciones.idTipoOpcion " +
	  		  "FROM tblopciones " +
	  		  "INNER JOIN (SELECT tblopcionesperfil.idLocal " +
	  		  ",tblopcionesperfil.idOpcion " +
	  		  ",tblopcionesperfil.idPerfil " +
	  		  ",1 AS isChecked " +
	  		  "FROM tblopcionesperfil " +
	  		  "WHERE tblopcionesperfil.idPerfil = ?3 " +
	  		  "AND    tblopcionesperfil.idLocal = ?2 " +
	  		  "UNION " +
	  		  "SELECT tblopciones.idLocal " +
	  		  ",tblopciones.idOpcion " +
	  		  ",?3 AS idPerfil " +
	  		  ",2 AS isChecked " +
	  		  "FROM tblopciones " +
	  		  "WHERE tblopciones.idOpcionPadre = ?1 " +
	  		  "AND    tblopciones.idLocal = ?2 " +
	  		  "AND NOT EXISTS " +
	  		  "(SELECT tblopcionesperfil.* " +
	  		  "FROM tblopcionesperfil " +
	  		  "WHERE tblopcionesperfil.idPerfil = ?3 " +
	  		  "AND tblopcionesperfil.idLocal = ?2 " +
	  		  "AND tblopcionesperfil.idOpcion = tblopciones.idOpcion " +
	  		  "AND tblopciones.idLocal = tblopciones.idLocal)) AS tmpOPC " +
	  		  "ON tmpOPC.idOpcion  =   tblopciones.idOpcion " +
	  		  "AND tmpOPC.idLocal  =  tblopciones.idLocal " +
	  		  "WHERE  tblopciones.idOpcionPadre = ?1 " +
	  		  "AND tblopciones.idLocal = ?2 ) AS tmpMenu " +
	  		  "WHERE tmpMenu.isChecked = 1 " +
	  		  "AND tmpMenu.idLocal = ?2 ", nativeQuery = true)
    List<TblOpcionesDTO> ObtenerListaUnNivel(int IdOpcion, int IdLocal, int xIdPerfil);
	  
	  
	  
	  
	  @Query( value = "SELECT * FROM " +
			  "(SELECT *	FROM " +
			  "(SELECT tblopciones.idLocal " +
			  ",tblopciones.idOpcion " +
	  		  ",tblopciones.nombreOpcion " +
	  		  ",tblopciones.rutaOpcion " +
	  		  ",(SELECT ISNULL(COUNT(*),0) " +
	  		  "FROM tblopciones " +
	  		  "WHERE tblopciones.idOpcionPadre = ?1 " +
	  		  "AND tblopciones.idLocal  =  ?2  ) AS numeroHijo " +
	  		  ",tmpCHK.isChecked " +
	  		  ",tblopciones.idTipoOpcion " +
	  		  "FROM tblopciones " +
	  		  "INNER JOIN (SELECT tblopcionesperfil.idLocal " +
	  		  ",tblopcionesperfil.idOpcion " +
	  		  ",tblopcionesperfil.idPerfil " +
	  		  ",1 AS isChecked " +
	  		  "FROM tblopcionesperfil " +
	  		  "WHERE tblopcionesperfil.idPerfil = ?3 " +
	  		  "AND tblopcionesperfil.idLocal    =  ?2 " +
	  		  "UNION " +
	  		  "SELECT tblopciones.idLocal, " +
	  		  "tblopciones.idOpcion, " +
	  		  "?3 AS idPerfil, " +
	  		  "2 AS isChecked " +
	  		  "FROM tblopciones " +
	  		  "WHERE tblopciones.idOpcion = ?1 " +
	  		  "AND tblopciones.idLocal    = ?2 " +
	  		  "AND NOT EXISTS " +
	  		  "(SELECT tblopcionesperfil.* " +
	  		  "FROM tblopcionesperfil " +
	  		  "WHERE tblopcionesperfil.idPerfil = ?3 " +
	  		  "AND tblopcionesperfil.idLocal    = ?2 " +
	  		  "AND tblopcionesperfil.idOpcion   = tblopciones.idOpcion ))  AS tmpCHK " +
	  		  "ON tmpCHK.idOpcion = tblopciones.idOpcion " +
	  		  "AND tmpCHK.idLocal = tblopciones.idLocal " +
	  		  "WHERE tblopciones.idOpcion   = ?1 " +
	  		  "AND tblopciones.idLocal    = ?2  ) AS tmpPad " +
	  		  "WHERE tmpPad.numeroHijo=0 " +
	  		  "AND tmpPad.idTipoOpcion = 2 " +
	  		  "AND tmpPad.idLocal   = ?2 " +
	  		  "UNION " +
	  		  "SELECT tblopciones.idLocal " +
	  		  ",tblopciones.idOpcion " +
	  		  ",tblopciones.nombreOpcion " +
	  		  ",tblopciones.rutaOpcion " +
	  		  ",(SELECT ISNULL(COUNT(*),0) " +
	  		  "FROM tblopciones " +
	  		  "WHERE tblopciones.idOpcionPadre =  tmpOPC.idOpcion " +
	  		  "AND tblopciones.idLocal = tmpOPC.idLocal) AS numeroHijo " +
	  		  ",tmpOPC.isChecked " +
	  		  ",tblopciones.idTipoOpcion " +
	  		  "FROM tblopciones " +
	  		  "INNER JOIN (SELECT tblopcionesperfil.idLocal " +
	  		  ",tblopcionesperfil.idOpcion " +
	  		  ",tblopcionesperfil.idPerfil " +
	  		  ",1 AS isChecked " +
	  		  "FROM tblopcionesperfil " +
	  		  "WHERE tblopcionesperfil.idPerfil = ?3 " +
	  		  "AND    tblopcionesperfil.idLocal = ?2 " +
	  		  "UNION " +
	  		  "SELECT tblopciones.idLocal " +
	  		  ",tblopciones.idOpcion " +
	  		  ",?3 AS idPerfil " +
	  		  ",2 AS isChecked " +
	  		  "FROM tblopciones " +
	  		  "WHERE tblopciones.idOpcionPadre = ?1 " +
	  		  "AND    tblopciones.idLocal = ?2 " +
	  		  "AND NOT EXISTS " +
	  		  "(SELECT tblopcionesperfil.* " +
	  		  "FROM tblopcionesperfil " +
	  		  "WHERE tblopcionesperfil.idPerfil = ?3 " +
	  		  "AND tblopcionesperfil.idLocal = ?2 " +
	  		  "AND tblopcionesperfil.idOpcion = tblopciones.idOpcion " +
	  		  "AND tblopciones.idLocal = tblopciones.idLocal)) AS tmpOPC " +
	  		  "ON tmpOPC.idOpcion  =   tblopciones.idOpcion " +
	  		  "AND tmpOPC.idLocal  =  tblopciones.idLocal " +
	  		  "WHERE  tblopciones.idOpcionPadre = ?1 " +
	  		  "AND tblopciones.idLocal = ?2 ) AS tmpMenu " +
	  		  "WHERE tmpMenu.isChecked = 1 " +
	  		  "AND tmpMenu.idLocal = ?2 ", nativeQuery = true)
    List<TblOpcionesDTO> ObtenerListaSubOpcionMenu(int IdOpcion, int IdLocal, int xIdPerfil);
	  
	  
	  
	  
	  
	  
	  
	  
}
