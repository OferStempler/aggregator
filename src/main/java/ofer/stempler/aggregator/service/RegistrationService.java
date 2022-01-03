package ofer.stempler.aggregator.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ofer.stempler.aggregator.dao.AggregatedDataDao;
import ofer.stempler.aggregator.dao.CrmDetailsDao;
import ofer.stempler.aggregator.model.CrmDetails;

@Service
public class RegistrationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    private static long LAST_ATTEMPT;

    @Value("${refresh.allowed.minutes}")
    private int refreshAllowedMinutes;

    @Autowired
    private CrmDetailsDao crmDetailsDao;

    @Autowired
    private AggregatedDataDao aggregatedDataDao;

    @Autowired
    private ObjectFactory<CrmRequestProcessor> factory;

    /**
     * Registers all CRMs details from the db and starts process them in parallel.
     */
    public void register() {

        //For demo and simplicity, a truncate takes place before each run.
        // However, depending on requirements, we can just save all runs.
        // In this case, we will specify a runId, and later query on top of a view for the latest run.
        aggregatedDataDao.truncateAggregatedData();

        List<CrmDetails> crmDetailsList = crmDetailsDao.findAll();

        //We can have the thread pool size configurable according to requirements and machine.
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<Integer>> callableList = new ArrayList<>();
        for (CrmDetails crmDetails : crmDetailsList) {
            CrmRequestProcessor crmRequestProcessor = factory.getObject();
            crmRequestProcessor.setCrmDetails(crmDetails);
            callableList.add(crmRequestProcessor);
        }

        try {
            List<Future<Integer>> futures = executorService.invokeAll(callableList);

            int successfulRuns = futures.stream().map(future -> {
                try {
                    return future.get();
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }).mapToInt(Integer::intValue).sum();

            LOGGER.info("Successfully aggregated data from {}/{} CRMs.", successfulRuns, crmDetailsList.size());
        } catch (InterruptedException e) {
            LOGGER.error("Error running callable tasks", e);
        } finally {
            executorService.shutdown();
            LAST_ATTEMPT = System.currentTimeMillis();
        }
    }


    /**
     * Checks last refresh attempt.
     * If refresh was done last than configuration refreshAllowedMinutes ago, no new aggregation will take place.
     */
    public void attemptRefresh() {

        long now = System.currentTimeMillis();
        if (now - LAST_ATTEMPT > TimeUnit.MINUTES.toMillis(refreshAllowedMinutes)) {
            register();
            LOGGER.info("Successfully refreshed");

        } else {
            long nextRun = (TimeUnit.MINUTES.toMillis(refreshAllowedMinutes) - (now - LAST_ATTEMPT)) / 1000;
            LOGGER.info("Refresh not allowed yet. Next refresh is allowed in: {} seconds", nextRun);
        }
    }
}
