package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.Bak_DctosOrdenesDetalle;


@Repository
public interface Bak_DctosOrdenesDetalleRepo extends JpaRepository<Bak_DctosOrdenesDetalle, Integer> {
	
	@Modifying
    @Transactional
	@Query(value = " DELETE FROM bak_DctosOrdenesDetalle    "
            + " FROM bak_DctosOrdenes                "
            + " INNER JOIN bak_DctosOrdenesDetalle   "
            + " ON bak_DctosOrdenes.IDLOCAL      =   "
            + "     bak_DctosOrdenesDetalle.IDLOCAL  "
            + " AND bak_DctosOrdenes.IDTIPOORDEN =   "
            + "  bak_DctosOrdenesDetalle.IDTIPOORDEN "
            + " AND bak_DctosOrdenes.IDORDEN     =   "
            + "      bak_DctosOrdenesDetalle.IDORDEN "
            + " WHERE bak_DctosOrdenes.IDLOCAL   =   "
            + "?1                           "
            + " AND bak_DctosOrdenes.IDTIPOORDEN     "
            + "                  IN (7,9,29,59)      ",
			nativeQuery = true)
	public void retiraOrdenesDetalle(int idLocal);
	
	
	@Modifying
    @Transactional
	@Query(value = " INSERT INTO bak_DctosOrdenesDetalle "
            + "            (IDLOCAL                 "
            + "            ,IDTIPOORDEN             "
            + "            ,IDORDEN                 "
            + "            ,IDPLU                   "
            + "            ,CANTIDAD                "
            + "            ,IDTIPO                  "
            + "            ,PORCENTAJEIVA           "
            + "            ,VRVENTAUNITARIO         "
            + "            ,ESTADO                  "
            + "            ,NOMBREPLU               "
            + "            ,VRVENTAORIGINAL         "
            + "            ,VRCOSTO                 "
            + "            ,VRDSCTOPIE              "
            + "            ,PORCENTAJEDSCTO         "
            + "            ,CANTIDADPEDIDA          "
            + "            ,strIdBodega             "
            + "            ,STRIDLISTA              "
            + "            ,NOMBREUNIDADMEDIDA      "
            + "            ,comentario              "
            + "            ,item                    "
            + "            ,itemPadre               "
            + "            ,idEstadoTx              "
            + "            ,idTipoTx                "
            + "            ,fechaEntrega            "
            + "            ,idBodega                "
            + "            ,idSubcuenta             "
            + "            ,lecturaMedidor          "
            + "            ,idCliente               "
            + "            ,idEstracto              "
            + "            ,idRuta                  "
            + "            ,idNovedadLectura)       "
            + " SELECT tbldctosordenesdetalle.IDLOCAL    "
            + "      ,tbldctosordenesdetalle.IDTIPOORDEN "
            + "      ,tbldctosordenesdetalle.IDORDEN     "
            + "      ,IDPLU                               "
            + "      ,CANTIDAD                            "
            + "      ,IDTIPO                              "
            + "      ,PORCENTAJEIVA                       "
            + "      ,VRVENTAUNITARIO                     "
            + "      ,tbldctosordenesdetalle.ESTADO      "
            + "      ,NOMBREPLU                           "
            + "      ,VRVENTAORIGINAL                     "
            + "      ,VRCOSTO                             "
            + "      ,VRDSCTOPIE                          "
            + "      ,PORCENTAJEDSCTO                     "
            + "      ,CANTIDADPEDIDA                      "
            + "      ,strIdBodega                         "
            + "      ,STRIDLISTA                          "
            + "      ,NOMBREUNIDADMEDIDA                  "
            + "      ,tbldctosordenesdetalle.comentario  "
            + "      ,item                                "
            + "      ,itemPadre                           "
            + "      ,tbldctosordenesdetalle.idEstadoTx  "
            + "      ,tbldctosordenesdetalle.idTipoTx    "
            + "     ,tbldctosordenesdetalle.fechaEntrega "
            + "      ,idBodega                            "
            + "      ,idSubcuenta                         "
            + "      ,lecturaMedidor                      "
            + "      ,tbldctosordenesdetalle.idCliente   "
            + "      ,idEstracto                          "
            + "      ,idRuta                              "
            + "      ,idNovedadLectura                    "
            + " FROM            tbldctosordenes     "
            + " INNER JOIN  tbldctosordenesdetalle  "
            + " ON tbldctosordenes.IDLOCAL       =  "
            + "     tbldctosordenesdetalle.IDLOCAL  "
            + " AND  tbldctosordenes.IDTIPOORDEN =  "
            + "  tbldctosordenesdetalle.IDTIPOORDEN "
            + " AND tbldctosordenes.IDORDEN      =  "
            + "    tbldctosordenesdetalle.IDORDEN   "
            + " WHERE tbldctosordenes.idLocal     = "
            + "?1                          "
            + " AND  tbldctosordenes.idTipoOrden IN "
            + " (7,59)                              "
            + " AND   tbldctosordenes.idPeriodo   = ?2 ",
			nativeQuery = true)
	public void ingresaOrdenesDetalle(int idLocal, int idPeriodo);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
