package ofer.stempler.aggregator.utils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Resources;

public class MockUtils {
    public static String createStringFromJson(String fileName) throws IOException {
        URL url = Resources.getResource(fileName);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }
}
