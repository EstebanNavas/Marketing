package com.marketing.Repository.dbaquamovil;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.dbaquamovil.TblAgendaEventoLog;
import com.marketing.Model.dbaquamovil.TblLocales;
import com.marketing.Projection.TblAgendaEventoLogDTO;

@Repository
public interface TblAgendaEventoLogRepo extends JpaRepository<TblAgendaEventoLog, Integer> {

	
	@Query(value =  " SELECT tblagendaeventolog.idPeriodo,   "
            + "     tblagendaeventolog.idCliente,      "
            + "     tbldctos.nombreTercero,            "
            + "     tbldctos.idDcto,                   "
            + "     tblagendaeventolog.fechaEvento,    "
            + "     tbltercerosruta.idRuta,            "
            + "     tbltercerosruta.nombreRuta,        "
            + "     tblagendaeventolog.dataEnvio,      "
            + "     tblagendaeventolog.idEvento,      "
            + "     tblagendaeventolog.idLocal,      	"
            + "     tblagendaeventolog.estado,      	"
            + "     tblagendaeventolog.nombreEvento,      	"
            + "     tblagendaeventolog.dataRecibo      "
            + " FROM tblagendaeventolog                "
            + " INNER JOIN tbldctos                    "
            + " ON tblagendaeventolog.idLocal =        "
            + "                       tbldctos.IDLOCAL "
            + " AND tblagendaeventolog.idPeriodo =     "
            + "                     tbldctos.idPeriodo "
            + " AND tblagendaeventolog.idCliente =     "
            + "                     tbldctos.idCliente "
            + " INNER JOIN tblterceros                 "
            + " ON tblterceros.idLocal =               "
            + "                       tbldctos.idLocal "
            + " AND tblterceros.idCliente =            "
            + "                     tbldctos.idCliente "
            + "                                        "
            + " INNER JOIN tbltercerosruta             "
            + " ON tblterceros.idLocal =               "
            + "               tbltercerosruta.idLocal  "
            + " AND tblterceros.idRuta =               "
            + "               tbltercerosruta.idRuta   "
            + "                                        "
            + " WHERE tbldctos.IDLOCAL   =             "
            + "?1                          "
            + " AND tbldctos.idtipoorden = 9           "
            + " AND tbldctos.idPeriodo   =             "
            + "?2                        "
            + "ORDER BY tbldctos.nombreTercero ",
			nativeQuery = true)
	List<TblAgendaEventoLogDTO>  listaReporteEmail(int idLocal, int idPeriodo);
}
