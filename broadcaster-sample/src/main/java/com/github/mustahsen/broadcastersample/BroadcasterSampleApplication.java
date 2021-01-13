package com.github.mustahsen.broadcastersample;

import com.github.mustahsen.broadcaster.annotation.EnableBroadcaster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Broadcaster sample application.
 */
@EnableBroadcaster
@SpringBootApplication
public class BroadcasterSampleApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BroadcasterSampleApplication.class, args);
    }

}
