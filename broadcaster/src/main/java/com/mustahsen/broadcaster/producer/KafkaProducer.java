package com.mustahsen.broadcaster.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustahsen.broadcaster.configuration.BroadcasterConfiguration;
import com.mustahsen.broadcaster.listener.KafkaProducerCallbackListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_BATCH_SIZE;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_BUFFER_MEMORY;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_LINGER_MS;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_MAX_BLOCK_MS;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_RETRIES;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_SERVER_ADDRESSES;

@Slf4j
public class KafkaProducer {

    private Map<String, Object> kafkaProperties;
    private ProducerFactory<Long, Object> producerFactory;
    private KafkaTemplate<Long, Object> kafkaTemplate;

    public KafkaProducer(BroadcasterConfiguration broadcasterConfiguration) {
        this.kafkaProperties = new HashMap<>();
        this.kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broadcasterConfiguration.get(KAFKA_SERVER_ADDRESSES));
        this.kafkaProperties.put(ProducerConfig.RETRIES_CONFIG, broadcasterConfiguration.get(KAFKA_RETRIES));
        this.kafkaProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, broadcasterConfiguration.get(KAFKA_BATCH_SIZE));
        this.kafkaProperties.put(ProducerConfig.LINGER_MS_CONFIG, broadcasterConfiguration.get(KAFKA_LINGER_MS));
        this.kafkaProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, broadcasterConfiguration.get(KAFKA_BUFFER_MEMORY));
        this.kafkaProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, broadcasterConfiguration.get(KAFKA_MAX_BLOCK_MS));
        //TODO use byte serializer for key to make key more generic
        this.kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        this.kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        this.producerFactory = new DefaultKafkaProducerFactory<>(this.kafkaProperties, new LongSerializer(), new JsonSerializer(new ObjectMapper()));
        this.kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }

    public void produce(String topic, Long key, Map<String, Object> valueMap) {
        if (StringUtils.isBlank(topic)) {
            log.info("Topic: {} can't be blank!", topic);
            return;
        }

        ListenableFuture<SendResult<Long, Object>> future;

        if (Objects.isNull(key)) {
            future = kafkaTemplate.send(topic, valueMap);
        } else {
            future = kafkaTemplate.send(topic, key, valueMap);
        }

        future.addCallback(new KafkaProducerCallbackListener());
    }
}
