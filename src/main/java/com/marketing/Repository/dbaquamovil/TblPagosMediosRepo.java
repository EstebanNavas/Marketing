package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.TblPagosMedios;

@Repository
public interface TblPagosMediosRepo extends JpaRepository<TblPagosMedios, Integer> {

	
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM tblpagosmedios " +
	                 "FROM tblpagos " +
	                 "JOIN tblpagosmedios " +
	                 "ON tblpagos.idLocal = tblpagosmedios.idLocal " +
	                 "AND tblpagos.idTipoOrden =  tblpagosmedios.idTipoOrden " +
	                 "AND tblpagos.idRecibo  =  tblpagosmedios.idRecibo " +
	                 "AND tblpagos.indicador  =  tblpagosmedios.indicador " +
	                 "WHERE tblpagos.idLocal   =   ?1 " +
	                 "AND  tblpagos.idTipoOrden IN (7,9,29, 59) " +
	                 "AND  tblpagos.idPeriodo  =   ?2 ", nativeQuery = true)
	  public void retiraMedio(int idLocal, int idPeriodo);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = " DELETE FROM  tblpagosmedios      "
              + " WHERE tblpagosmedios.idLocal   = "
              + "?1                   "
              + " AND tblpagosmedios.idTipoOrden = ?2 ",
              nativeQuery = true)
	  public void retiraTemporal(int idLocal, int idTipoOrden);
	  
	  
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = " INSERT INTO tblpagosmedios		       "
              + "            (idLocal			       "
              + "            ,idTipoOrden		       "
              + "            ,idRecibo		       "
              + "            ,indicador	               "
              + "            ,idMedio			       "
              + "            ,vrMedio			       "
              + "            ,fechaCobro		       "
              + "            ,idBanco			       "
              + "            ,idDctoMedio		       "
              + "            ,estado			       "
              + "            ,idLog			       "
              + "            ,idPlu)			       "
              + " SELECT tmpPAG.IDLOCAL,                     "
              + "        tmpPAG.idTipoOrden,                 "
              + "        tmpPAG.idRecibo,                    "                  
              + "        tmpPAG.indicador,                   "  
              + "?1 AS idMedio,                 "
              + " 	   SUM(tmpPAG.vrMedio)                 "
              + " 	            AS vrMedio,               "
              + "?2 AS fechaCobro, "
              + "?3 AS idBanco,                "
              + "?4 AS idDctoMedio,        "
              + "?5 AS estado,                   "
              + "?6 AS idLog,                     "
              + "        tmpPAG.idPlu                        "
              + " FROM  (                                    "
              + " SELECT tbldctos.idLocal,                   "
              + "?7 AS idTipoOrden,         "                
              + "?8 AS idRecibo,               "
              + "?9 AS indicador,             "                  
              + "  tbldctos.idOrden,                         "
              + " SUM( tbldctosordenesdetalle.CANTIDAD *     "
              + "  tbldctosordenesdetalle.VRVENTAUNITARIO )  "
              + "                                AS vrMedio, "
              + "  tbldctosordenesdetalle.idPlu              "
              + " FROM   tbldctos                            "
              + " INNER JOIN tbldctosordenesdetalle          "
              + " ON tbldctosordenesdetalle.idLocal      =   "
              + "          tbldctos.idLocal                  "
              + " AND tbldctosordenesdetalle.idTipoOrden =   "
              + "                    tbldctos.idTipoOrden    "
              + "  AND  tbldctosordenesdetalle.idOrden   =   "
              + "                        tbldctos.idOrden    "
              + " WHERE  tbldctos.idLocal           =        "
              + "?10                             "
              + " AND   tbldctos.idDcto             =        "
              + "?11                                  "
              + " AND    tbldctos.idTipoOrden       = 9      "
              + " AND    tbldctos.idDctoCruce       = 0      "
              + " AND    tbldctos.idPeriodo         =        "
              + "?12                               "
              + " GROUP BY tbldctos.idLocal,                 "
              + "  tbldctos.idTipoOrden,                     "
              + "  tbldctos.idOrden ,                        "
              + "  tbldctosordenesdetalle.idPlu              "
              + " UNION                                      "
              + " SELECT  tblpagos.idLocal,                  "
              + "?7 AS idTipoOrden,         "                
              + "?8 AS idRecibo,               "
              + "?9 AS indicador,             "                   
              + " 		tbldctos.idOrden,              "
              + "   SUM(tblpagosmedios.vrMedio               "
              + " 		 * (-1)) AS vrMedio,           "
              + " 		tblpagosmedios.idPlu           "
              + " FROM   tbldctos                            "
              + " INNER JOIN tblpagos                        "
              + " ON tbldctos.IDLOCAL      =                 "
              + "           tblpagos.idLocal                 "
              + " AND tbldctos.IDTIPOORDEN =                 "
              + "       tblpagos.idTipoOrden                 "
              + " AND tbldctos.indicador   =                 "
              + "         tblpagos.indicador                 "
              + " AND tbldctos.idCliente   =                 "
              + "                tblpagos.nitCC              "
              + " AND tbldctos.idDcto      =                 "
              + "               tblpagos.idDcto              "
              + " INNER JOIN tblpagosmedios                  "
              + " ON tblpagos.IDLOCAL      =                 "
              + "           tblpagosmedios.idLocal           "
              + " AND tblpagos.IDTIPOORDEN =                 "
              + "       tblpagosmedios.idTipoOrden           "
              + " AND tblpagos.indicador   =                 "
              + "         tblpagosmedios.indicador           "
              + " AND tblpagos.idRecibo    =                 "
              + "          tblpagosmedios.idRecibo           "
              + " WHERE tblpagos.idLocal   =                 "
              + "?10                             "
              + " AND tblpagos.idTipoOrden = 9               "
              + " AND tbldctos.idDcto      =                 "
              + "?11                                  "
              + " AND  tbldctos.idPeriodo  =                 "
              + "?12                               "
              + " AND tblpagos.idLog =                       "
              + "?6                              "
              + " GROUP BY tblpagos.idLocal,                 "
              + "         tblpagos.idTipoOrden,              "
              + " 		tbldctos.idOrden,              "
              + " 		tblpagosmedios.idPlu           "
              + " UNION                                      "
              + " SELECT tbldctos.idLocal,                   "
              + "?7 AS idTipoOrden,         "                
              + "?8 AS idRecibo,               "
              + "?9 AS indicador,             "                   
              + "  tbldctos.idOrden,                         "
              + "  ( tbldctosordenesdetalle.CANTIDAD *       "
              + " tbldctosordenesdetalle.VRVENTAUNITARIO)    "
              + "                               AS vrMedio,  "
              + "        tbldctosordenesdetalle.idPlu        "
              + " FROM   tbldctos                            "
              + "  INNER JOIN tbldctosordenesdetalle         "
              + "  ON tbldctosordenesdetalle.idLocal =       "
              + "                          tbldctos.idLocal  "
              + "  AND tbldctosordenesdetalle.idTipoOrden =  "
              + "                      tbldctos.idTipoOrden  "
              + " AND  tbldctosordenesdetalle.idOrden   =    "
              + "                         tbldctos.idOrden   "
              + " WHERE  tbldctos.idLocal           =        "
              + "?10                             "
              + " AND   tbldctos.idDctoCruce        =        "
              + "?11                                  "
              + " AND    tbldctos.idTipoOrden       = 29     "
              + " AND    tbldctos.idDctoCruce      != 0      "
              + " AND    tbldctos.idPeriodo         =        "
              + "?12          ) AS tmpPAG          "
              + " GROUP BY  tmpPAG.IDLOCAL,                  "
              + "        tmpPAG.idTipoOrden,                 "
              + "        tmpPAG.idRecibo,                    "                  
              + "        tmpPAG.indicador,                   " 
              + " 	   tmpPAG.idPlu	",
              nativeQuery = true)
	  public void ingresaTotal(int IdMedio, String FechaCobroSqlServer, int IdBanco, int IdDctoMedio, int Estado, int IdLog, int IdTipoOrden, int IdRecibo, int Indicador,
			  					int IdLocal, int xIdDcto, int xIdPeriodo);
	  
	  
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = " DELETE FROM  tblpagosmedios      "
              + " WHERE tblpagosmedios.idLocal   = "
              + "?1                   "
              + " AND tblpagosmedios.idTipoOrden = ?2 "
              + " AND tblpagosmedios.idLog = ?3 ",
              nativeQuery = true)
	  public void retiraPagosTemporales(int idLocal, int idTipoOrden, int idLog);
	  
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "INSERT INTO tblpagosmedios "
              + "           (idLocal        "
              + "           ,idTipoOrden    "
              + "           ,idRecibo       "
              + "           ,indicador      "
              + "           ,idMedio        "
              + "           ,vrMedio        "
              + "           ,fechaCobro     "
              + "           ,idBanco        "
              + "           ,idDctoMedio    "
              + "           ,estado         "
              + "           ,idLog          "
              + "           ,idPlu)         "
              + "SELECT tblpagosmedios.idLocal,         "        
              + "?1 AS idTipoOrden,     "
              + "?2 AS idRecibo,           "
              + "?3 AS indicador          "   
              + ",MAX(tblpagosmedios.idMedio) AS idMedio"         
              + "  ,SUM(tblpagosmedios.vrMedio)         "   
              + "                      AS vrMedio       "
              + "    ,MAX(tblpagosmedios.fechaCobro)    " 
              + "    AS fechaCobro                      "
              + "    ,MAX(tblpagosmedios.idBanco)       "    
              + "                         AS idBanco    " 
              + "    ,MAX(tblpagosmedios.idDctoMedio)   "
              + "                        AS idDctoMedio " 
              + "   ,MAX(tblpagosmedios.estado)         "      
              + "                             AS estado "
              + "   ,MAX(tblpagosmedios.idLog) AS idLog "            
              + "   ,tblpagosmedios.idPlu  AS idPlu     "
              + "   FROM tblpagosmedios                 "
              + "   INNER JOIN tblpagos                 "                    
              + "   ON tblpagos.idLocal     =           "        
              + "              tblpagosmedios.idLocal   "
              + "   AND   tblpagos.idTipoOrden =        "           
              + "         tblpagosmedios.idTipoOrden    "  
              + "   AND   tblpagos.idRecibo    =        "          
              + "              tblpagosmedios.idRecibo  "   
              + "   AND   tblpagos.indicador   =        "       
              + "         tblpagosmedios.indicador      "  
              + "   AND   tblpagos.idLog       =        "           
              + "              tblpagosmedios.idLog     "                       
              + "   WHERE tblpagos.idLocal     =        "
              + "?4                        "                
              + "   AND tblpagos.idTipoOrden =          "
              + "?5                    "                
              + "   AND tblpagos.idRecibo    =          "
              + "?6                       "
              + "   AND tblpagos.idDcto      =          "
              + "?7                           "                
              + "   AND tblpagos.idLog       =          "
              + "?8                         "             
              + "   GROUP BY tblpagosmedios.idLocal,    "
              + "     tblpagosmedios.idTipoOrden,       "
              + "     tblpagosmedios.idRecibo,          "
              + "     tblpagosmedios.indicador,         "    
              + "     tblpagosmedios.idPlu              "      
              + " HAVING SUM(tblpagosmedios.vrMedio)!=0 ",
              nativeQuery = true)
	  public void ingresaPago(int xIdTipoOrdenNew, int xIdReciboNew, int xIndicadorNew, int idLocal, int IdTipoOrden, int IdRecibo, int xIdDcto, int idLog);
	  
	  
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM tblpagosmedios       "
              + "WHERE tblpagosmedios.idLocal   = "
              + "?1                  "
              + "AND tblpagosmedios.idTipoOrden = "
              + "?2              "
              + "AND tblpagosmedios.indicador   = "
              + "?3                "
              + "AND tblpagosmedios.idRecibo    = ?4 ",
              nativeQuery = true)
	  public void retiraReciboTemporal(int idLocal, int IdTipoOrden, int Indicador, int IdRecibo);
	  
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM tblpagosmedios      "
              + "FROM   tblpagos                 "
              + "INNER JOIN tblpagosmedios       "
              + "ON tblpagos.idLocal         =   "
              + "     tblpagosmedios.idLocal     "
              + "AND tblpagos.idTipoOrden    =   "
              + " tblpagosmedios.idTipoOrden     "
              + "AND tblpagos.idRecibo       =   "
              + "    tblpagosmedios.idRecibo     "
              + "AND tblpagos.indicador      =   "
              + "   tblpagosmedios.indicador     "
              + "AND tblpagos.idLog          =   "
              + "   tblpagosmedios.idLog         "
              + "WHERE   tblpagos.idLocal    =   "
              + "?1                 "
              + "AND tblpagos.idTipoOrden    =   "
              + "?2             "
              + "AND tblpagos.indicador      =   "
              + "?3               "
              + "AND tblpagos.idDcto         = ?4  ",
              nativeQuery = true)
	  public void retiraPagoDctoTemporal(int idLocal, int IdTipoOrden, int Indicador, int xIdDcto);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = " INSERT INTO tblpagosmedios		       "
              + "            (idLocal			       "
              + "            ,idTipoOrden		       "
              + "            ,idRecibo		       "
              + "            ,indicador	               "
              + "            ,idMedio			       "
              + "            ,vrMedio			       "
              + "            ,fechaCobro		       "
              + "            ,idBanco			       "
              + "            ,idDctoMedio		       "
              + "            ,estado			       "
              + "            ,idLog			       "
              + "            ,idPlu)			       "
              + " SELECT tmpPAG.IDLOCAL,                     "
              + "?1 AS idTipoOrden,         "
              + "?2 AS idRecibo,               "
              + "?3 AS indicador,             "
              + "?4 AS idMedio,                 "
              + " 	   SUM(tmpPAG.vrMedio)                 "
              + " 	            AS vrMedio,                "
              + "?5 AS fechaCobro, "
              + "?6 AS idBanco,                "
              + "?7 AS idDctoMedio,        "
              + "?8 AS estado,                   "
              + "?9 AS idLog,                     "
              + "        tmpPAG.idPlu                        "
              + " FROM  (                                    "
              + " SELECT tbldctos.idLocal,                   "
              + "  tbldctos.idTipoOrden,                     "
              + "  tbldctos.idOrden,                         "
              + " SUM( tbldctosordenesdetalle.CANTIDAD *     "
              + "  tbldctosordenesdetalle.VRVENTAUNITARIO )  "
              + "                                AS vrMedio, "
              + "  tbldctosordenesdetalle.idPlu              "
              + " FROM   tbldctos                            "
              + " INNER JOIN tbldctosordenesdetalle          "
              + " ON tbldctosordenesdetalle.idLocal      =   "
              + "          tbldctos.idLocal                  "
              + " AND tbldctosordenesdetalle.idTipoOrden =   "
              + "                    tbldctos.idTipoOrden    "
              + "  AND  tbldctosordenesdetalle.idOrden   =   "
              + "                        tbldctos.idOrden    "
              + " WHERE  tbldctos.idLocal           =        "
              + "?10                             "
              + " AND   tbldctos.idDcto             =        "
              + "?11                                  "
              + " AND    tbldctos.idTipoOrden       = 9      "
              + " AND    tbldctos.idDctoCruce       = 0      "
              + " AND    tbldctos.idPeriodo         =        "
              + "?12                              "
              + " GROUP BY tbldctos.idLocal,                 "
              + "  tbldctos.idTipoOrden,                     "
              + "  tbldctos.idOrden ,                        "
              + "  tbldctosordenesdetalle.idPlu              "
              + " UNION                                      "
              + " SELECT  tblpagos.idLocal,                  "
              + "         tblpagos.idTipoOrden,              "
              + " 		tbldctos.idOrden,              "
              + "   SUM(tblpagosmedios.vrMedio               "
              + " 		 * (-1)) AS vrMedio,           "
              + " 		tblpagosmedios.idPlu           "
              + " FROM   tbldctos                            "
              + " INNER JOIN tblpagos                        "
              + " ON tbldctos.IDLOCAL      =                 "
              + "           tblpagos.idLocal                 "
              + " AND tbldctos.IDTIPOORDEN =                 "
              + "       tblpagos.idTipoOrden                 "
              + " AND tbldctos.indicador   =                 "
              + "         tblpagos.indicador                 "
              + " AND tbldctos.idCliente   =                 "
              + "                tblpagos.nitCC              "
              + " AND tbldctos.idDcto      =                 "
              + "               tblpagos.idDcto              "
              + " INNER JOIN tblpagosmedios                  "
              + " ON tblpagos.IDLOCAL      =                 "
              + "           tblpagosmedios.idLocal           "
              + " AND tblpagos.IDTIPOORDEN =                 "
              + "       tblpagosmedios.idTipoOrden           "
              + " AND tblpagos.indicador   =                 "
              + "         tblpagosmedios.indicador           "
              + " AND tblpagos.idRecibo    =                 "
              + "          tblpagosmedios.idRecibo           "
              + " WHERE tblpagos.idLocal   =                 "
              + "?10                             "
              + " AND tblpagos.idTipoOrden = 9               "
              + " AND tbldctos.idDcto      =                 "
              + "?11                                  "
              + " AND  tbldctos.idPeriodo  =                 "
              + "?12                               "
              + " GROUP BY tblpagos.idLocal,                 "
              + "         tblpagos.idTipoOrden,              "
              + " 		tbldctos.idOrden,              "
              + " 		tblpagosmedios.idPlu           "
              + " UNION                                      "
              + " SELECT tbldctos.idLocal,                   "
              + "  tbldctos.idTipoOrdenCruce                 "
              + " 	                   AS idTipoOrden,     "
              + "  tbldctos.idOrden,                         "
              + "  ( tbldctosordenesdetalle.CANTIDAD *       "
              + " tbldctosordenesdetalle.VRVENTAUNITARIO)    "
              + "                               AS vrMedio,  "
              + "        tbldctosordenesdetalle.idPlu        "
              + " FROM   tbldctos                            "
              + "  INNER JOIN tbldctosordenesdetalle         "
              + "  ON tbldctosordenesdetalle.idLocal =       "
              + "                          tbldctos.idLocal  "
              + "  AND tbldctosordenesdetalle.idTipoOrden =  "
              + "                      tbldctos.idTipoOrden  "
              + " AND  tbldctosordenesdetalle.idOrden   =    "
              + "                         tbldctos.idOrden   "
              + " WHERE  tbldctos.idLocal           =        "
              + "?10                             "
              + " AND   tbldctos.idDctoCruce        =        "
              + "?11                                  "
              + " AND    tbldctos.idTipoOrden       = 29     "
              + " AND    tbldctos.idDctoCruce      != 0      "
              + " AND    tbldctos.idPeriodo         =        "
              + "?12          ) AS tmpPAG          "
              + " GROUP BY tmpPAG.IDLOCAL,                   "
              + "        tmpPAG.IDTIPOORDEN,                 "
              + " 	   tmpPAG.idPlu	",
              nativeQuery = true)
	  public void ingresaTotalPlanilla(int IdTipoOrden, int IdRecibo, int Indicador, int IdMedio, String FechaCobro, int IdBanco, int IdDctoMedio, int Estado, int IdLog, 
			                           int IdLocal, int xIdDcto, int xIdPeriodo);
	  
	  
	  
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "INSERT INTO tblpagosmedios     "
              + "           (idLocal          "
              + "           ,idTipoOrden      "
              + "           ,idRecibo         "
              + "           ,indicador        "
              + "           ,idMedio          "
              + "           ,vrMedio          "
              + "           ,fechaCobro       "
              + "           ,idBanco          "
              + "           ,idDctoMedio      "
              + "           ,estado           "
              + "           ,idLog            "
              + "           ,idPlu)           "
              + "VALUES (                     "
              + "?1,             "
              + "?2,         "
              + "?3,            "
              + "?4,           "
              + "?5,             "
              + "?6,            "
              + "?7,"
              + "?8,            "
              + "?9,        "
              + "?10,              "
              + "?11,               "
              + "?12)",
              nativeQuery = true)
	  public void ingresar(int IdLocal, int IdTipoOrden, int IdRecibo, int Indicador, int IdMedio, Double VrMedio, String FechaCobro, int IdBanco, int IdDctoMedio, 
			                           int Estado, int IdLog, int IdPlu);
	  
	  
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "INSERT INTO tblpagosmedios         "
              + "           (idLocal                "
              + "           ,idTipoOrden            "
              + "           ,idRecibo               "
              + "           ,indicador              "
              + "           ,idDctoMedio            "
              + "           ,idMedio                "
              + "           ,vrMedio                "
              + "           ,fechaCobro             "
              + "           ,idBanco                "
              + "           ,estado                 "
              + "           ,idLog                  "
              + "           ,idPlu)                 "
              + "SELECT tblpagosmedios.idLocal      "
              + "      ,tblpagosmedios.idTipoOrden, "
              + "?1                    "
              + "      ,tblpagosmedios.indicador    "
              + "      ,tblpagosmedios.idDctoMedio  "
              + "      ,tblpagosmedios.idMedio      "
              + "      ,tblpagosmedios.vrMedio *    "
              + "                  (-1) AS vrMedio  "
              + "      ,tblpagosmedios.fechaCobro   "
              + "      ,tblpagosmedios.idBanco      "
              + "      ,tblpagosmedios.estado,      "
              + "?2                      "
              + "      ,tblpagosmedios.idPlu        "
              + "FROM tblpagosmedios                "
              + "WHERE tblpagosmedios.idLocal     = "
              + "?3                    "
              + "AND   tblpagosmedios.idTipoOrden = "
              + "?4                "
              + "AND   tblpagosmedios.idRecibo    = "
              + "?5                   "
              + "AND   tblpagosmedios.indicador   = ?6 ", nativeQuery = true)
	  public void ingresaRetiro(int xIdReciboNew, int IdLog, int IdLocal, int IdTipoOrden, int IdRecibo, int Indicador);
	  
	  
}
