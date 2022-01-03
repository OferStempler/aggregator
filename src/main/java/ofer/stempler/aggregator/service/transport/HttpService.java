package ofer.stempler.aggregator.service.transport;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpService {

    private final RestTemplate restTemplate;

    public HttpService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendRequest(String url) {
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }
}
