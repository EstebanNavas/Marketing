package com.marketing.Repository.DBMailMarketing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.DBMailMarketing.IrcaLocalesTanque;
import com.marketing.Projection.IRCAlocalesTanquesDTO;



public interface IrcaLocalesTanqueRepository
		extends JpaRepository<IrcaLocalesTanque, Integer> {
	
	@Query(value =
	        "SELECT idLocal " +
	        " ,idTanque " +
	        " ,nombreTanque " +
	        " ,largo " +
	        " ,ancho " +
	        " ,profundo " +
	        "FROM tblIrcaLocalesTanque " +
	        "WHERE idLocal = ?1",
	        nativeQuery = true)
	List<IrcaLocalesTanque> obtenerTanquesPorLocal(Integer idLocal);
	
	@Query(value =
	        "SELECT idLocal " +
	        " ,idTanque " +
	        " ,nombreTanque " +
	        " ,largo " +
	        " ,ancho " +
	        " ,profundo " +
	        " FROM tblIrcaLocalesTanque " +
	        " WHERE idLocal = ?1 " + 
	        " AND idTanque = ?2 ",
	        nativeQuery = true)
	List<IrcaLocalesTanque> obtenerTanquesPorLocalTanque(int idLocal, int idTanque);

	
  @Modifying
  @Transactional
  @Query(value = "UPDATE tblIrcaLocalesTanque SET nombreTanque = ?1, largo = ?2, ancho = ?3, profundo = ?4  " +

                 "WHERE tblIrcaLocalesTanque.idLocal = ?5 " +
                 "AND tblIrcaLocalesTanque.idTanque = ?6 " , nativeQuery = true)
  public void actualizarTanque(String nombreTanque,  Double largo, Double ancho, Double profundo, int idLocal, int idTanque ) ;
  
  
  @Query(value = "SELECT MAX(t.idTanque) FROM tblIrcaLocalesTanque t " + 
			"WHERE t.idLocal = ?1 ",
			nativeQuery = true)
	Integer maximoIdTanque(int idLocal);
	
}