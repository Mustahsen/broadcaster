package com.mustahsen.broadcaster;

import com.mustahsen.broadcaster.configuration.BroadcasterConfiguration;
import com.mustahsen.broadcaster.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_ENABLED;

@Slf4j
public class Broadcaster {

    private BroadcasterConfiguration broadcasterConfiguration;
    private Boolean kafkaEnabled;
    private KafkaProducer kafkaProducer;

    public Broadcaster(BroadcasterConfiguration broadcasterConfiguration) {
        this.broadcasterConfiguration = broadcasterConfiguration;
    }

    @PostConstruct
    public void init() {
        if ((Boolean) broadcasterConfiguration.get(KAFKA_ENABLED)) {
            kafkaEnabled = Boolean.TRUE;
            kafkaProducer = new KafkaProducer(broadcasterConfiguration);
        } else {
            kafkaEnabled = Boolean.FALSE;
        }
    }

    public void broadcast() {
        log.info("Broadcaster");
    }

}
