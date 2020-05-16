package com.mustahsen.broadcaster.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mustahsen.broadcaster.configuration.BroadcasterConfiguration;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_BATCH_SIZE;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_BUFFER_MEMORY;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_LINGER_MS;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_MAX_BLOCK_MS;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_RETRIES;
import static com.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_SERVER_ADDRESSES;

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
        this.kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        this.kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        this.producerFactory = new DefaultKafkaProducerFactory<>(this.kafkaProperties, new LongSerializer(), new JsonSerializer(new ObjectMapper()));
        this.kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }
}
