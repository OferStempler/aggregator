package ofer.stempler.aggregator.service;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ofer.stempler.aggregator.dao.AggregatedDataDao;
import ofer.stempler.aggregator.fail.AggregatorException;
import ofer.stempler.aggregator.model.Aggregator;
import ofer.stempler.aggregator.model.CrmDetails;
import ofer.stempler.aggregator.service.transport.HttpService;

@Component
@Scope(value = "prototype")
public class CrmRequestProcessor implements Callable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrmRequestProcessor.class);

    private final HttpService httpService;
    private final ObjectMapper objectMapper;
    private final AggregatedDataDao aggregatorDao;

    public CrmRequestProcessor(
            final HttpService httpService,
            final ObjectMapper objectMapper,
            final AggregatedDataDao aggregatorDao
    ) {
        this.httpService = httpService;
        this.objectMapper = objectMapper;
        this.aggregatorDao = aggregatorDao;
    }

    private CrmDetails crmDetails;

    public void setCrmDetails(CrmDetails crmDetails) {
        this.crmDetails = crmDetails;
    }

    @Override
    public Integer call() {
         return process();
    }

    /**
     * Processes each crm request:
     * 1. Sends GET request.
     * 2. Parse response
     * 3. Saves into db.
     * @return 1 for success or 0 for failure.
     */
    public int process(){
        try {
            LOGGER.info("Start processing crm: {}", crmDetails.getCrmName());
            String response = httpService.sendRequest(crmDetails.getUrl());
            Aggregator aggregator = objectMapper.readValue(response, Aggregator.class);

            //Additional data can be added and saved like run id, created date, etc.
            aggregatorDao.saveAll(aggregator.getAggregatedDataList());
        } catch (JsonProcessingException | AggregatorException e) {
            LOGGER.error("Error processing CRM: {} ", crmDetails.getCrmName(), e);

            //TODO we can save the error into a error db for investigations.
            return 0;
        }
        LOGGER.info("Successfully processed crm: {}", crmDetails.getCrmName());
        return 1;
    }
}
