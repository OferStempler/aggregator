package ofer.stempler.aggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ofer.stempler.aggregator.model.AggregatedData;
import ofer.stempler.aggregator.service.FilteringService;
import ofer.stempler.aggregator.service.RegistrationService;

@RestController
public class AggregatorController {

    @Autowired
    private FilteringService filteringService;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping(value = "/getCrmData")
    public ResponseEntity<AggregatedData> getCrmData(
            @RequestParam(required = false, value = "provider") Integer provider,
            @RequestParam(required = false, value = "createdErrorCode") Integer createdErrorCode,
            @RequestParam(required = false, value = "status") String status

    ) {
        return new ResponseEntity(filteringService.filterData(provider, createdErrorCode, status), HttpStatus.OK);
    }

    /**
     * Attempts a refresh. The assumption here is the no matter if the refresh was done or not,
     * the end client will still get all results.
     * @return all up to date result set.
     */
    @GetMapping(value = "/refresh")
    public ResponseEntity<AggregatedData> refreshData() {
        registrationService.attemptRefresh();
        return new ResponseEntity(filteringService.filterData(null, null, null), HttpStatus.OK);
    }
}
