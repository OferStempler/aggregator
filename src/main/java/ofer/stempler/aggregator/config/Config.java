package ofer.stempler.aggregator.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import ofer.stempler.aggregator.service.transport.RestErrorHandler;

@Configuration
public class Config {

    // Set to one to avoid more than a single scheduler thread running.
    private static final int SCHEDULER_POOL_SIZE = 1;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        RestTemplate restTemplate =  restTemplateBuilder.errorHandler(new RestErrorHandler()).build();
        return restTemplate;
    }

    @Bean
    public TaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(SCHEDULER_POOL_SIZE);
        return threadPoolTaskScheduler;
    }

}
