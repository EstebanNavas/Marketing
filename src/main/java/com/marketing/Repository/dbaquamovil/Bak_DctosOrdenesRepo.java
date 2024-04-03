package com.marketing.Repository.dbaquamovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.marketing.Model.dbaquamovil.Bak_DctosOrdenes;

@Repository
public interface Bak_DctosOrdenesRepo extends JpaRepository<Bak_DctosOrdenes, Integer> {
	
	
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM bak_DctosOrdenesDetalle " +
	                 "FROM bak_DctosOrdenes " +
	                 "JOIN bak_DctosOrdenesDetalle " +
	                 "ON bak_DctosOrdenes.IDLOCAL  =  bak_DctosOrdenesDetalle.IDLOCAL " +
	                 "AND bak_DctosOrdenes.IDTIPOORDEN =  bak_DctosOrdenesDetalle.IDTIPOORDEN " +
	                 "AND bak_DctosOrdenes.IDORDEN  =  bak_DctosOrdenesDetalle.IDORDEN " +
	                 "WHERE bak_DctosOrdenes.IDLOCAL   =  ?1 " +
	                 "AND bak_DctosOrdenes.IDTIPOORDEN IN (7,9,29,59) ", nativeQuery = true)
	  public void retiraOrdenesDetalle(int idLocal);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM  bak_DctosOrdenes " +
	                 "WHERE  IDLOCAL  = ?1 " +
	                 "AND IDTIPOORDEN  IN (7,9,29, 59) ", nativeQuery = true)
	  public void retiraOrdenes(int idLocal);
	  
	  
	  @Modifying
	  @Transactional
	  @Query(value = " INSERT INTO bak_DctosOrdenes        "
              + "            (IDLOCAL                 "
              + "            ,IDTIPOORDEN             "
              + "            ,IDORDEN                 "
              + "            ,FECHAORDEN              "
              + "            ,ESTADO                  "
              + "            ,idCliente               "
              + "            ,IDUSUARIO               "
              + "            ,IDORIGEN                "
              + "            ,IDLOG                   "
              + "            ,FECHAENTREGA            "
              + "            ,TIPODCTO                "
              + "            ,DIRECCIONDESPACHO       "
              + "            ,EMAIL                   "
              + "            ,FAX                     "
              + "            ,CONTACTO                "
              + "            ,OBSERVACION             "
              + "            ,CIUDADDESPACHO          "
              + "            ,FORMAPAGO               "
              + "            ,ordenCompra             "
              + "            ,descuentoComercial      "
              + "            ,impuestoVenta           "
              + "            ,idRazon                 "
              + "            ,idEstadoTx              "
              + "            ,idTipoTx                "
              + "            ,numeroOrden             "
              + "            ,idResponsable           "
              + "            ,diasHistoria            "
              + "            ,diasInventario          "
              + "            ,idBodegaOrigen          "
              + "            ,idBodegaDestino         "
              + "            ,idPeriodo               "
              + "            ,vrTotalDiferir          "
              + "            ,cuotaDiferir            "
              + "         ,porcentajeInteresADiferir  "
              + "            ,vrInteresADiferir       "
              + "            ,comentario              "
              + "            ,historiaConsumo         "
              + "            ,promedio                "
              + "            ,cuotaVencida            "
              + "            ,estadoCorte             "
              + "            ,fechaPagoUltimo         "
              + "            ,vrPagoUltimo            "
              + "            ,promedioEstrato         "
              + "            ,fechaInicioContrato     "
              + "            ,vrSalarioBasico         "
              + "            ,vrSubsidioTransporte    "
              + "            ,fechaFinContrato        "
              + "            ,idContrato              "
              + "            ,idMedio                 "
              + "            ,entidadMedio            "
              + "            ,cuentaMedio)            "
              + " SELECT        tbldctosordenes.*     "
              + " FROM            tbldctosordenes     "
              + " WHERE tbldctosordenes.idLocal     = "
              + "?1                          "
              + " AND tbldctosordenes.idTipoOrden IN  "
              + " (7,59)                              "
              + " AND   tbldctosordenes.idPeriodo   = "
              + "?2                        "
              + " AND EXISTS                          "
              + "  ( SELECT *                         "
              + "    FROM  tbldctosordenesdetalle     "
              + "    WHERE                            "
              + "    tbldctosordenes.IDLOCAL       =  "
              + "     tbldctosordenesdetalle.IDLOCAL  "
              + " AND  tbldctosordenes.IDTIPOORDEN =  "
              + " tbldctosordenesdetalle.IDTIPOORDEN  "
              + " AND tbldctosordenes.IDORDEN      =  "
              + "   tbldctosordenesdetalle.IDORDEN )  ", nativeQuery = true)
	  public void ingresaOrdenes(int idLocal, int idPeriodo);

}
