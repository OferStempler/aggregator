package ofer.stempler.aggregator.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ofer.stempler.aggregator.utils.MockUtils;

/**
 * Used only to enable live integration demo.
 */
@RestController
public class MockController {

    @GetMapping(value = "/mock/banana")
    public String mockBanana() throws IOException {

        return MockUtils.createStringFromJson("crm1.json");
    }


    @GetMapping(value = "/mock/strawberry")
    public String mockStrawberry() throws IOException {

        return MockUtils.createStringFromJson("crm2.json");
    }
}
