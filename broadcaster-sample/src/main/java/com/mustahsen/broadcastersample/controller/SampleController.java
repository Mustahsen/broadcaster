package com.mustahsen.broadcastersample.controller;

import com.mustahsen.broadcastersample.service.SampleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = SampleController.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController {

    public static final String ENDPOINT = "sample";

    private SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping
    public void get() {
        Long arg1 = Math.round(Math.random() * 100);
        String arg2 = String.valueOf(Math.random() * 100);

        int listSize = (int) Math.round(Math.random() * 5);
        List<Integer> arg3 = new ArrayList<>();
        for (int i = 0; i < listSize; ++i) {
            arg3.add((int) Math.round(Math.random() * 100));
        }

        log.info("Arguments : {}, {}, {}", arg1, arg2, arg3);
        sampleService.testBroadcasterAnnotation(arg1, arg2, arg3);
    }
}
