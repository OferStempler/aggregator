package ofer.stempler.aggregator.service.schedule;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import ofer.stempler.aggregator.service.RegistrationService;

@Component
public class AppScheduler {

    @Value("${cron.scheduler}")
    private String cronScheduler;

    @Autowired
    private TaskScheduler threadPoolTaskScheduler;

    @Autowired
    private RegistrationService registerService;

    @PostConstruct
    public void scheduleAggregator(){
        threadPoolTaskScheduler.schedule(this::run, new CronTrigger(cronScheduler));
    }

    public void run(){
        registerService.register();
    }
}
