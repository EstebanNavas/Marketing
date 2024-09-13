package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblTipoOrdenSubcuenta;
import com.marketing.Projection.TblOpcionesDTO;

@Repository
public interface TblTipoOrdenSubcuentaRepo extends JpaRepository<TblTipoOrdenSubcuenta, String > {
	
	  @Query( value = "SELECT TOP 1 idSubcuenta   "
              + "      ,nombreSubcuenta     "
              + "      ,estado              "
              + "      ,idSeq               "
              + "FROM tblTipoOrdenSubcuenta "
              + "WHERE idTipoOrden =     ?1   "
              + "ORDER BY idAsiento         ", 
              nativeQuery = true)
  List<TblTipoOrdenSubcuenta> listaTipoOrdenSubcuenta(int idTipoOrden);

}
