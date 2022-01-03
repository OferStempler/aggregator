package ofer.stempler.aggregator.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.io.Resources;

public class TestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static <T> T createInstanceFromJson(String fileName, Class<T> targetClass) throws IOException {
        URL url = Resources.getResource(fileName);
        String text = Resources.toString(url, StandardCharsets.UTF_8);
        return objectMapper.readValue(text, targetClass);
    }


}
