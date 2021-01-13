package com.github.mustahsen.broadcaster.autoconfigure;

import com.github.mustahsen.broadcaster.Broadcaster;
import com.github.mustahsen.broadcaster.configuration.BroadcasterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_BATCH_SIZE;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_BUFFER_MEMORY;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_ENABLED;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_LINGER_MS;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_MAX_BLOCK_MS;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_RETRIES;
import static com.github.mustahsen.broadcaster.configuration.BroadcasterConfigurationParameters.KAFKA_SERVER_ADDRESSES;

/**
 * The type Broadcaster auto configuration.
 */
@Configuration
@ConditionalOnClass(Broadcaster.class)
@EnableConfigurationProperties(BroadcasterProperties.class)
public class BroadcasterAutoConfiguration {

    @Autowired
    private BroadcasterProperties broadcasterProperties;

    /**
     * Broadcaster configuration broadcaster configuration.
     *
     * @return the broadcaster configuration
     */
    @Bean
    @ConditionalOnMissingBean
    public BroadcasterConfiguration broadcasterConfiguration() {

        BroadcasterConfiguration broadcasterConfiguration = new BroadcasterConfiguration();
        broadcasterConfiguration.put(KAFKA_ENABLED, false);
        broadcasterConfiguration.put(KAFKA_SERVER_ADDRESSES, "localhost:9092");
        broadcasterConfiguration.put(KAFKA_RETRIES, 0);
        broadcasterConfiguration.put(KAFKA_BATCH_SIZE, 16384);
        broadcasterConfiguration.put(KAFKA_LINGER_MS, 1);
        broadcasterConfiguration.put(KAFKA_BUFFER_MEMORY, 33554432);
        broadcasterConfiguration.put(KAFKA_MAX_BLOCK_MS, 1000);

        if (broadcasterProperties.getKafkaEnabled() != null) {
            broadcasterConfiguration.put(KAFKA_ENABLED, broadcasterProperties.getKafkaEnabled());
        }

        if (broadcasterProperties.getKafkaProducer() != null) {
            if (broadcasterProperties.getKafkaProducer().getServer() != null
                    && broadcasterProperties.getKafkaProducer().getServer().getAddresses() != null) {

                broadcasterConfiguration.remove(KAFKA_SERVER_ADDRESSES);
                broadcasterConfiguration.put(KAFKA_SERVER_ADDRESSES, broadcasterProperties.getKafkaProducer().getServer().getAddresses());
            }
            if (broadcasterProperties.getKafkaProducer().getRetries() != null) {
                broadcasterConfiguration.remove(KAFKA_RETRIES);
                broadcasterConfiguration.put(KAFKA_RETRIES, broadcasterProperties.getKafkaProducer().getRetries());
            }
            if (broadcasterProperties.getKafkaProducer().getBatchSize() != null) {
                broadcasterConfiguration.remove(KAFKA_BATCH_SIZE);
                broadcasterConfiguration.put(KAFKA_BATCH_SIZE, broadcasterProperties.getKafkaProducer().getBatchSize());
            }
            if (broadcasterProperties.getKafkaProducer().getLingerMs() != null) {
                broadcasterConfiguration.remove(KAFKA_LINGER_MS);
                broadcasterConfiguration.put(KAFKA_LINGER_MS, broadcasterProperties.getKafkaProducer().getLingerMs());
            }
            if (broadcasterProperties.getKafkaProducer().getBufferMemory() != null) {
                broadcasterConfiguration.remove(KAFKA_BUFFER_MEMORY);
                broadcasterConfiguration.put(KAFKA_BUFFER_MEMORY, broadcasterProperties.getKafkaProducer().getBufferMemory());
            }
            if (broadcasterProperties.getKafkaProducer().getMaxBlockMs() != null) {
                broadcasterConfiguration.remove(KAFKA_MAX_BLOCK_MS);
                broadcasterConfiguration.put(KAFKA_MAX_BLOCK_MS, broadcasterProperties.getKafkaProducer().getMaxBlockMs());
            }
        }

        return broadcasterConfiguration;
    }

    /**
     * Broadcaster broadcaster.
     *
     * @param broadcasterConfiguration the broadcaster configuration
     * @return the broadcaster
     */
    @Bean
    @ConditionalOnMissingBean
    public Broadcaster broadcaster(BroadcasterConfiguration broadcasterConfiguration) {
        return new Broadcaster(broadcasterConfiguration);
    }
}
