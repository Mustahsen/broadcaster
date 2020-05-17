package com.mustahsen.broadcaster;

import com.mustahsen.broadcaster.annotation.Broadcast;
import com.mustahsen.broadcaster.annotation.BroadcastField;
import com.mustahsen.broadcaster.configuration.BroadcasterConfiguration;
import com.mustahsen.broadcaster.enums.BroadcastType;
import com.mustahsen.broadcaster.enums.BroadcastValueSource;
import com.mustahsen.broadcaster.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public void broadcast(Broadcast broadcast, Map<String, Object> argumentMap, Object returnValue, Throwable throwable) {
        if (broadcast.broadcastType().equals(BroadcastType.KAFKA)) {
            Long key = getKey(broadcast, argumentMap, returnValue, throwable);
            Map<String, Object> valueMap = getValues(broadcast, argumentMap, returnValue, throwable);
            kafkaProducer.produce(broadcast.target(), key, valueMap);



        }


    }

    private Long getKey(Broadcast broadcast, Map<String, Object> argumentMap, Object returnValue, Throwable throwable) {
        if (Objects.isNull(broadcast) || Objects.isNull(broadcast.broadcastKey())) {
            return null;
        }

        if (broadcast.broadcastKey().source().equals(BroadcastValueSource.CONSTANT)) {
            Long key = null;
            try {
                key = Long.valueOf(broadcast.broadcastKey().value());
            } catch (NumberFormatException nfe) {
                logKeyNumberFormatException(broadcast.broadcastKey().value());
            }

            return key;
        }

        if (broadcast.broadcastKey().source().equals(BroadcastValueSource.ARGUMENT)) {
            Long key = null;
            try {
                key = Long.valueOf(argumentMap.get(broadcast.broadcastKey().value()).toString());
            } catch (NumberFormatException nfe) {
                logKeyNumberFormatException(argumentMap.get(broadcast.broadcastKey().value()).toString());
            }

            return key;
        }

        if (broadcast.broadcastKey().source().equals(BroadcastValueSource.RETURNING)) {
            Long key = null;
            try {
                key = Long.valueOf(returnValue.toString());
            } catch (NumberFormatException nfe) {
                logKeyNumberFormatException(returnValue.toString());
            }

            return key;
        }

        return null;
    }

    private void logKeyNumberFormatException(String value) {
        log.info("Key: {} can't be parsed to Long!", value);
    }

    private Map<String, Object> getValues(Broadcast broadcast, Map<String, Object> argumentMap, Object returnValue, Throwable throwable) {
        Map<String, Object> valueMap = new HashMap<>();

        if (Objects.isNull(broadcast)
                || Objects.isNull(broadcast.broadcastFields())
                || broadcast.broadcastFields().length < 1) {
            return null;
        }

        for (BroadcastField broadcastField : broadcast.broadcastFields()) {
            if (broadcastField.source().equals(BroadcastValueSource.CONSTANT)) {
                valueMap.put(broadcastField.key(), broadcastField.value());
            } else if (broadcastField.source().equals(BroadcastValueSource.ARGUMENT)) {
                valueMap.put(broadcastField.key(), argumentMap.get(broadcastField.key()));
            } else if (broadcastField.source().equals(BroadcastValueSource.RETURNING)) {
                valueMap.put(broadcastField.key(), returnValue);
            }
        }

        return valueMap;
    }

}
