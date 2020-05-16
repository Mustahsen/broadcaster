package com.mustahsen.broadcaster;

import com.mustahsen.broadcaster.configuration.BroadcasterConfiguration;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_ENABLED;

@Slf4j
public class Broadcaster {

    private BroadcasterConfiguration broadcasterConfiguration;

    public Broadcaster(BroadcasterConfiguration broadcasterConfiguration) {
        this.broadcasterConfiguration = broadcasterConfiguration;
    }

    @PostConstruct
    public void init() {
        if ((Boolean) broadcasterConfiguration.get(KAFKA_ENABLED)) {
            log.info("WOW");
        } else {
            log.info("NO WOW");
        }
    }

}
