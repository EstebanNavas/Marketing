package com.marketing.Repository.DBMailMarketing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketing.Model.DBMailMarketing.TblMailCampaignCliente;

@Repository
public interface TblMailCampaignClienteRepo extends JpaRepository<TblMailCampaignCliente, Integer> {

}
