package ofer.stempler.aggregator.service.transport;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import ofer.stempler.aggregator.fail.AggregatorException;

@Component
public class RestErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode() != HttpStatus.OK) {
            return true;
        }
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

        throw new AggregatorException("Got error response: " + clientHttpResponse.getStatusCode());
    }
}
