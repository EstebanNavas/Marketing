package com.marketing.Repository.DBMailMarketing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.MailCampaign;
import com.marketing.Model.DBMailMarketing.TblMailMarketingReporte;
import com.marketing.Model.Reportes.ReporteDTO;
import com.marketing.Projection.TblMailMarketingReporteDTO;
import com.marketing.Model.dbaquamovil.TblTerceros;

@Repository
public interface MailCampaignRepo extends JpaRepository<MailCampaign, Integer> {

//	Integer maximaCampaign(Integer idLocal, String sistema);
	
	 @Query("SELECT MAX(c.idCampaign) FROM MailCampaign c WHERE c.idLocal = :idLocal AND c.sistema = :sistema")
	    Integer maximaCampaign(@Param("idLocal") Integer idLocal, @Param("sistema") String sistema);
	 
	 ArrayList<MailCampaign> findByidLocalAndSistemaAndPeriodicidad(Integer idLocal, String sistema, String periodicidad);
	 
	 MailCampaign findByIdLocalAndSistemaAndIdCampaign(Integer idLocal, String sistema, Integer idCampaign);
	 
	 
		@Query(
				value = "SELECT tblMailCampaign.sistema, " +
						"tblMailMarketingReporte.idCliente, " +
						"tblMailCampaign.idCampaign, " +
						"tblMailCampaign.textoSMS as descripcion, " +
						"tblMailMarketingReporte.fechaHoraEvento, " +
						"tblMailMarketingReporte.celular, " +
						"tblTerceros.nombreTercero " +
						"FROM BDMailMarketing.dbo.tblMailCampaign " +
						"JOIN BDMailMarketing.dbo.tblMailMarketingReporte " +
						"ON tblMailCampaign.IDLOCAL = tblMailMarketingReporte.idLocal " +
						"AND tblMailCampaign.sistema = tblMailMarketingReporte.sistema " +
						"AND tblMailCampaign.idCampaign = tblMailMarketingReporte.idCampaign " +
						"JOIN bdaquamovil.dbo.tblTerceros " +
						"ON tblMailMarketingReporte.idLocal = tblTerceros.idLocal " +
						"AND tblMailMarketingReporte.idCliente = tblTerceros.idCliente " +
						"WHERE tblMailCampaign.IDLOCAL = ?1 "+
						"AND tblMailCampaign.sistema = 'aquamovil' " +
						"AND tblTerceros.idTipoTercero = 1 " +
						"ORDER BY tblMailCampaign.idCampaign, tblMailMarketingReporte.fechaHoraEvento " ,
				nativeQuery = true
				)

		List <TblMailMarketingReporteDTO> obtenerReporteSmsAgrupadoIdCampaign(int idLocal);
}


















