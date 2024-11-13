package com.marketing.Repository.dbaquamovil;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblTipoCausaNota;

@Repository
public interface TblTipoCausaNotaRepo extends JpaRepository<TblTipoCausaNota, Integer> {

	@Query(value = "SELECT * " + 
			"FROM bdaquamovil.dbo.tblTipoCausaNota "+
			"WHERE tblTipoCausaNota.idTipoTabla = ?1", nativeQuery = true)
	ArrayList<TblTipoCausaNota> ObtenerTblTipoCausaNota(int idTipoTabla);
	
	
	
	@Query(value = "  SELECT *                                  "
			+ "  FROM bdaquamovil.dbo.tblTipoCausaNota     "
			+ "  WHERE idTipoTabla = ?1                     "
			+ "  ORDER BY                                  "
			+ "  CASE                                      "
			+ "  WHEN idCausa = 99 THEN 0                  "
			+ "  WHEN idCausa = 0 THEN 1                   "
			+ "  ELSE 2                                    "
			+ "  END,                                      "
			+ "  idCausa;                                  ", nativeQuery = true)
	ArrayList<TblTipoCausaNota> ObtenerTblTipoCausaNotaLecturas(int idTipoTabla);
}
