package com.mustahsen.broadcastersample;

import com.mustahsen.broadcaster.annotation.EnableBroadcaster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBroadcaster
@SpringBootApplication
public class BroadcasterSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BroadcasterSampleApplication.class, args);
    }

}
