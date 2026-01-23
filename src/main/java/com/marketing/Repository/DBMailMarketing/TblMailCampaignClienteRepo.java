package com.marketing.Repository.DBMailMarketing;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblMailCampaignCliente;
import com.marketing.Projection.TblTercerosProjectionDTO;

@Repository
public interface TblMailCampaignClienteRepo extends JpaRepository<TblMailCampaignCliente, Integer> {

	@Query(value = "SELECT tblMailCampaignCliente.idCliente FROM tblMailCampaignCliente " +
			"WHERE tblMailCampaignCliente.idLocal = ?1 " + 
			"AND tblMailCampaignCliente.idCampaign = ?2 ", 
			nativeQuery = true)
	List<TblTercerosProjectionDTO> ListaClientesSeleccionados(int idLocal, int idCampaign);
}
