package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblAgendaLogSoportes;
import com.marketing.Projection.ReporteFeDTO;

@Repository
public interface TblAgendaLogSoportesRepo extends JpaRepository<TblAgendaLogSoportes, Integer> {
	
	@Modifying
	  @Transactional
	  @Query(value = "INSERT INTO tblAgendaLogSoportes (idLocal,           "
            + "                                 idUsuario,       "
            + "                                 nombreReporte,       "
            + "                                 fechaSolitud,  "
            + "                                 ruta,         "             
            + "                                 estado)   "
            + "VALUES (?1,"
            + "?2,"
            + "?3,"
            + "?4,"
            + "?5,"               
            + "?6) ",
            nativeQuery = true)
	  public void ingresaLogReporte(int idLocal, Double idUsuario, String nombreReporte, String fechaSolitud, String ruta, int estado);
	
	
	
	@Query(value = " SELECT  idLocal                                   "
			+ "      ,idUsuario                                   "
			+ "      ,nombreReporte                               "
			+ "      ,fechaSolitud                                "
			+ "      ,ruta                                        "
			+ "      ,estado                                      "
			+ "  FROM bdaquamovil.dbo.tblAgendaLogSoportes        "
			+ "  where idlocal = ?1                               "
			+ "  and idUsuario = ?2                               "
			+ "  order by fechaSolitud desc                     ",
            nativeQuery = true)
	  List<TblAgendaLogSoportes> ObtenerReportesXUsuario(int idLocal, Double idUsuario);
	
	


}
