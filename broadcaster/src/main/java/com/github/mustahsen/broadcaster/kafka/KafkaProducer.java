package com.github.mustahsen.broadcaster.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mustahsen.broadcaster.configuration.BroadcasterConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.BytesSerializer;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_BATCH_SIZE;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_BUFFER_MEMORY;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_LINGER_MS;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_MAX_BLOCK_MS;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_RETRIES;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_SERVER_ADDRESSES;

/**
 * The type Kafka producer.
 */
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<Bytes, Object> kafkaTemplate;

    /**
     * Instantiates a new Kafka producer.
     *
     * @param broadcasterConfiguration the broadcaster configuration
     */
    public KafkaProducer(BroadcasterConfiguration broadcasterConfiguration) {
        Map<String, Object> kafkaProperties = new HashMap<>();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broadcasterConfiguration.get(KAFKA_SERVER_ADDRESSES));
        kafkaProperties.put(ProducerConfig.RETRIES_CONFIG, broadcasterConfiguration.get(KAFKA_RETRIES));
        kafkaProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, broadcasterConfiguration.get(KAFKA_BATCH_SIZE));
        kafkaProperties.put(ProducerConfig.LINGER_MS_CONFIG, broadcasterConfiguration.get(KAFKA_LINGER_MS));
        kafkaProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, broadcasterConfiguration.get(KAFKA_BUFFER_MEMORY));
        kafkaProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, broadcasterConfiguration.get(KAFKA_MAX_BLOCK_MS));
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, BytesSerializer.class);
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        ProducerFactory<Bytes, Object> producerFactory = new DefaultKafkaProducerFactory<>(kafkaProperties,
                new BytesSerializer(), new JsonSerializer(new ObjectMapper()));

        this.kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }

    /**
     * Produce.
     *
     * @param topic        the topic
     * @param partitionKey the partition key
     * @param bodyPairs    the body pairs
     */
    public void produce(String topic, Object partitionKey, Map<String, Object> bodyPairs) {
        if (StringUtils.isBlank(topic)) {
            log.info("Topic: {} can't be blank!", topic);
            return;
        }

        ListenableFuture<SendResult<Bytes, Object>> future;

        if (Objects.isNull(partitionKey)) {
            future = kafkaTemplate.send(topic, bodyPairs);
        } else {
            future = kafkaTemplate.send(topic, Bytes.wrap(partitionKey.toString().getBytes()), bodyPairs);
        }

        future.addCallback(new KafkaProducerCallbackListener());
    }
}
