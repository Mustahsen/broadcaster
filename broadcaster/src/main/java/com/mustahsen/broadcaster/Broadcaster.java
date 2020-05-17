package com.mustahsen.broadcaster;

import com.mustahsen.broadcaster.annotation.Broadcast;
import com.mustahsen.broadcaster.annotation.BroadcastField;
import com.mustahsen.broadcaster.configuration.BroadcasterConfiguration;
import com.mustahsen.broadcaster.enums.BroadcastType;
import com.mustahsen.broadcaster.factory.ResolverFactory;
import com.mustahsen.broadcaster.producer.KafkaProducer;
import com.mustahsen.broadcaster.resolver.IResolver;
import com.mustahsen.broadcaster.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Bytes;

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

    public void broadcast(Broadcast broadcast, Map<String, Object> argumentMap, Object returnValue) {
        if (kafkaEnabled && broadcast.broadcastType().equals(BroadcastType.KAFKA)) {
            Bytes key = getKey(broadcast, argumentMap);
            Map<String, Object> valueMap = getValues(broadcast, argumentMap);
            kafkaProducer.produce(broadcast.target(), key, valueMap);
        }
    }

    private Bytes getKey(Broadcast broadcast, Map<String, Object> argumentMap) {
        if (Objects.isNull(broadcast.key())) {
            return null;
        }
        Object key = BeanUtils.getBean(ResolverFactory.class).getResolver(broadcast.key().source()).resolve(broadcast.key(), argumentMap);
        if (Objects.nonNull(key)) {
            return Bytes.wrap(key.toString().getBytes());
        }
        return null;
    }

    private Map<String, Object> getValues(Broadcast broadcast, Map<String, Object> argumentMap) {
        if (Objects.isNull(broadcast.values())) {
            return null;
        }

        Map<String, Object> valueMap = new HashMap<>();

        for (BroadcastField broadcastField : broadcast.values()) {
            IResolver resolver = BeanUtils.getBean(ResolverFactory.class).getResolver(broadcastField.source());
            Object object = resolver.resolve(broadcastField, argumentMap);
            if (Objects.nonNull(object)) {
                valueMap.put(broadcastField.targetKey(), object);
            }
        }

        return valueMap;
    }

}
