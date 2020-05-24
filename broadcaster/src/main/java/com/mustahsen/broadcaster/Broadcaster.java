package com.mustahsen.broadcaster;

import com.mustahsen.broadcaster.annotation.Broadcast;
import com.mustahsen.broadcaster.annotation.BroadcastPair;
import com.mustahsen.broadcaster.configuration.BroadcasterConfiguration;
import com.mustahsen.broadcaster.enums.BroadcastType;
import com.mustahsen.broadcaster.factory.ResolverFactory;
import com.mustahsen.broadcaster.kafka.KafkaProducer;
import com.mustahsen.broadcaster.utils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_ENABLED;

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

    public void broadcast(Broadcast broadcast, Map<String, Object> argumentMap, Object returnValue) {
        if (kafkaEnabled && broadcast.type().equals(BroadcastType.KAFKA)) {

            Collection<Object> information = Collections.singletonList(new Object());

            if (StringUtils.isNotBlank(broadcast.collection().value())) {
                Object collection = BeanUtils.getBean(ResolverFactory.class)
                        .getResolver(broadcast.collection())
                        .resolve(broadcast.collection(), argumentMap, returnValue);

                if (Objects.nonNull(collection) && collection instanceof Collection ) {
                    information = (Collection<Object>) collection;
                }
            }

            information.parallelStream().forEach(element -> {
                Object key = getKey(broadcast, argumentMap, returnValue, element);
                Map<String, Object> bodyPairs = getValues(broadcast, argumentMap, returnValue, element);
                kafkaProducer.produce(broadcast.target(), key, bodyPairs);
            });
        }
    }

    private Object getKey(Broadcast broadcast, Map<String, Object> argumentMap, Object returnValue, Object object) {
        Object partitionKey = BeanUtils.getBean(ResolverFactory.class)
                .getResolver(broadcast.partitionKey())
                .resolve(broadcast.partitionKey(), argumentMap, returnValue, object);

        if (Objects.nonNull(partitionKey)) {
            return partitionKey;
        }
        return null;
    }

    private Map<String, Object> getValues(Broadcast broadcast, Map<String, Object> argumentMap, Object returnValue, Object object) {
        Map<String, Object> bodyPairs = new HashMap<>();

        for (BroadcastPair broadcastPair : broadcast.body()) {

            Object resolvable = BeanUtils.getBean(ResolverFactory.class)
                    .getResolver(broadcastPair)
                    .resolve(broadcastPair, argumentMap, returnValue, object);

            if (Objects.nonNull(resolvable)) {
                bodyPairs.put(broadcastPair.key(), resolvable);
            }
        }

        return bodyPairs;
    }

}
