package ofer.stempler.aggregator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ofer.stempler.aggregator.dao.AggregatedDataDao;
import ofer.stempler.aggregator.model.AggregatedData;

@Service
public class FilteringService {

    @Autowired
    private AggregatedDataDao aggregatedDataDao;

    /**
     * Creates a dynamic query based on the input parameters and return the result.
     * If not parameters where found, returns all records.
     *
     * @param provider
     * @param createdErrorCode
     * @param status
     * @return a list with the required query parameters
     */
    public List<AggregatedData> filterData(Integer provider, Integer createdErrorCode, String status) {

        List<AggregatedData> aggregatedDataList = aggregatedDataDao.findAll(Specification.where(
                //If no arguments are present, all records with id will return, meaning all.
                AggregatorDataSpecifications.withId()
                        .and(AggregatorDataSpecifications.withStatus(status))
                        .and(AggregatorDataSpecifications.withErrorCode(createdErrorCode))
                        .and(AggregatorDataSpecifications.withProvider(provider)

                        )));
        return aggregatedDataList;
    }
}
