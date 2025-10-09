package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblPlusInventario;

@Repository
public interface TblPlusInventarioRepo extends JpaRepository<TblPlusInventario, Integer> {
	
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE tblplusinventario                               "
              + "SET    tblplusinventario.existencia       =      	    "
              + "       tmpINV.cantidad *                      	    	"
              + "           ( SELECT tbltipoorden.signo               	"
              + "             FROM tbltipoorden                       	"
              + "             WHERE tbltipoorden.idTipoOrden   =        "
              + "?1 )                                  "
              + "                    + tblplusinventario.existencia     "
              + "             FROM tblplusinventario,	   		        "
              + "                (					                    "
              + "SELECT tmpTOT.idLocal,				                    "
              + "       tmpTOT.idPlu,			                    	"
              + "       tmpTOT.idBodega,    	                    	"
              + "       SUM(tmpTOT.cantidad) 			            	"
              + "                  AS cantidad		                 	"
              + "FROM (SELECT tmpPLU.idLocal,			             	"
              + "             tblpluscombo.idPlu,			            "
              + "             tmpPLU.idBodega,	                        "
              + "            (tblpluscombo.cantidad *		            "
              + "                      tmpPLU.cantidad ) AS cantidad	"
              + "      FROM tblpluscombo,				                "
              + " ( SELECT tblplus.idTipo,			                    "
              + "          tmpDOR.*				                        "
              + "   FROM tblplus,(					                    "
              + "       SELECT tbldctosordenesdetalle.idLocal,      	"
              + "              tbldctosordenesdetalle.idPlu,        	"
              + "              tbldctosordenesdetalle.idBodega,     	"
              + "              SUM(tbldctosordenesdetalle.cantidad) 	"
              + "                                       AS cantidad 	"
              + "       FROM tbldctosordenesdetalle                 	"
              + "       WHERE tbldctosordenesdetalle.idLocal     =      "
              + "?2                                        "
              + "       AND   tbldctosordenesdetalle.idTipoOrden =      "
              + "?1                                    "
              + "       AND   tbldctosordenesdetalle.idOrden     =      "
              + "?3                                        "
              + "       AND   tbldctosordenesdetalle.estado      != 4	"
              + "       GROUP BY tbldctosordenesdetalle.idLocal,      	"
              + "                tbldctosordenesdetalle.idPlu,      	"
              + "                tbldctosordenesdetalle.idBodega)    	"
              + "                                            AS tmpDOR	"
              + "       WHERE tblplus.idPlu  = 			                "
              + "                             tmpDOR.idPlu ) AS tmpPLU	"
              + "   WHERE tmpPLU.idPlu       =      			        "
              + "                              tblpluscombo.idPluCombo	"
              + "UNION						                            "
              + "SELECT tbldctosordenesdetalle.idLocal,      		    "
              + "       tbldctosordenesdetalle.idPlu,        	        "
              + "       tbldctosordenesdetalle.idBodega,             	"
              + "       SUM(tbldctosordenesdetalle.cantidad) 	    	"
              + "                        AS cantidad 			        "
              + "FROM tbldctosordenesdetalle                 	     	"
              + "WHERE tbldctosordenesdetalle.idLocal            =      "
              + "?2                                        "
              + "       AND   tbldctosordenesdetalle.idTipoOrden =      "
              + "?1                                    "
              + "       AND   tbldctosordenesdetalle.idOrden     =      "
              + "?3                                        "
              + "AND   tbldctosordenesdetalle.estado            != 4	"
              + "AND   NOT EXISTS 				                        "
              + "      ( SELECT tblplus.*			                    "
              + "        FROM tblplus				                    "
              + "        WHERE tblplus.idPlu = 			                "
              + "                   tbldctosordenesdetalle.idPlu     	"
              + "        AND tblplus.idLocal = 			                "
              + "                   tbldctosordenesdetalle.idLocal     	"
              + "        AND tblplus.idTipo  = 2 )			            "
              + "GROUP BY tbldctosordenesdetalle.idLocal,      	        "
              + "         tbldctosordenesdetalle.idPlu,      	        "
              + "         tbldctosordenesdetalle.idBodega)          	"
              + "AS tmpTOT					                         	"
              + "GROUP BY tmpTOT.idLocal,			                	"
              + "         tmpTOT.idPlu,                                 "
              + "         tmpTOT.idBodega ) AS tmpINV		           	"
              + "WHERE tmpINV.idPlu       = tblplusinventario.idPlu	  	"
              + "AND   tmpINV.idLocal     = tblplusinventario.idLocal	"
              + "AND   tmpINV.idBodega    = tblplusinventario.idBodega	",
              nativeQuery = true)
	  public void actualizaInventarioEstado(int IdTipoOrden, int idLocal, int IdOrden);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "  INSERT INTO tblPlusInventario          "          
	             + "             (idLocal                    "          
	             + "             ,idBodega                   "          
	             + "             ,idPlu                      "          
	             + "             ,existencia                 "          
	             + "             ,idTipoOrden                "          
	             + "             ,idOrden                    "          
	             + "             ,cantidadOrden              "          
	             + "             ,estado)                    "          
	             + "  SELECT tblLocalesBodega.idLocal,       "          
	             + "        tblLocalesBodega.idBodega,       "          
	             + "         tblPlus.idPlu,                  "          
	             + "         0.0 AS existencia,              "          
	             + "         0  AS idTipoOrden,              "          
	             + "         0  AS idOrden,                  "          
	             + "         0.0 AS cantidadOrden,           "          
	             + "         01 AS estado                    "          
	             + "  FROM tblPlus,                          "          
	             + "       tblLocalesBodega                  "          
	             + "  WHERE NOT EXISTS                       "          
	             + "    (SELECT tblPlusInventario.*          "          
	             + "     FROM tblPlusInventario              "          
	             + "     WHERE tblPlus.idPlu =               "          
	             + "        tblPlusInventario.idPlu          "          
	             + "     AND  tblPlus.idLocal =              "          
	             + "        tblPlusInventario.idLocal )      "          
	             + "   AND tblLocalesBodega.idLocal = ?1     "          
	             + "   AND tblPlus.idLocal = ?1              ",
              nativeQuery = true)
	  public void ingresaPluInventario(int idLocal);

}
