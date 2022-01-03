package ofer.stempler.aggregator.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ofer.stempler.aggregator.model.CrmDetails;

@Repository
public interface CrmDetailsDao extends JpaRepository<CrmDetails, Long> {
}
