package ofer.stempler.aggregator.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ofer.stempler.aggregator.model.AggregatedData;

@Repository
public interface AggregatedDataDao extends JpaRepository<AggregatedData, Long>, JpaSpecificationExecutor<AggregatedData> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "TRUNCATE TABLE aggregated_data")
    void truncateAggregatedData();


}
