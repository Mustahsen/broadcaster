package com.github.mustahsen.broadcaster.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The type Broadcaster properties.
 */
@ConfigurationProperties(prefix = "broadcaster")
public class BroadcasterProperties {

    private Boolean kafkaEnabled = Boolean.FALSE;

    private KafkaProducer kafkaProducer = new KafkaProducer();

    /**
     * Gets kafka enabled.
     *
     * @return the kafka enabled
     */
    public Boolean getKafkaEnabled() {
        return kafkaEnabled;
    }

    /**
     * Sets kafka enabled.
     *
     * @param kafkaEnabled the kafka enabled
     */
    public void setKafkaEnabled(Boolean kafkaEnabled) {
        this.kafkaEnabled = kafkaEnabled;
    }

    /**
     * Gets kafka producer.
     *
     * @return the kafka producer
     */
    public KafkaProducer getKafkaProducer() {
        return kafkaProducer;
    }

    /**
     * Sets kafka producer.
     *
     * @param kafkaProducer the kafka producer
     */
    public void setKafkaProducer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * The type Kafka producer.
     */
    public class KafkaProducer {

        private Server server = new Server();

        private Integer retries = 0;
        private Integer batchSize = 16384;
        private Integer lingerMs = 1;
        private Integer bufferMemory = 33554432;
        private Integer maxBlockMs = 1000;

        /**
         * Gets server.
         *
         * @return the server
         */
        public Server getServer() {
            return server;
        }

        /**
         * Sets server.
         *
         * @param server the server
         */
        public void setServer(Server server) {
            this.server = server;
        }

        /**
         * Gets retries.
         *
         * @return the retries
         */
        public Integer getRetries() {
            return retries;
        }

        /**
         * Sets retries.
         *
         * @param retries the retries
         */
        public void setRetries(Integer retries) {
            this.retries = retries;
        }

        /**
         * Gets batch size.
         *
         * @return the batch size
         */
        public Integer getBatchSize() {
            return batchSize;
        }

        /**
         * Sets batch size.
         *
         * @param batchSize the batch size
         */
        public void setBatchSize(Integer batchSize) {
            this.batchSize = batchSize;
        }

        /**
         * Gets linger ms.
         *
         * @return the linger ms
         */
        public Integer getLingerMs() {
            return lingerMs;
        }

        /**
         * Sets linger ms.
         *
         * @param lingerMs the linger ms
         */
        public void setLingerMs(Integer lingerMs) {
            this.lingerMs = lingerMs;
        }

        /**
         * Gets buffer memory.
         *
         * @return the buffer memory
         */
        public Integer getBufferMemory() {
            return bufferMemory;
        }

        /**
         * Sets buffer memory.
         *
         * @param bufferMemory the buffer memory
         */
        public void setBufferMemory(Integer bufferMemory) {
            this.bufferMemory = bufferMemory;
        }

        /**
         * Gets max block ms.
         *
         * @return the max block ms
         */
        public Integer getMaxBlockMs() {
            return maxBlockMs;
        }

        /**
         * Sets max block ms.
         *
         * @param maxBlockMs the max block ms
         */
        public void setMaxBlockMs(Integer maxBlockMs) {
            this.maxBlockMs = maxBlockMs;
        }


        /**
         * The type Server.
         */
        public class Server {

            private String addresses = "localhost:9092";

            /**
             * Gets addresses.
             *
             * @return the addresses
             */
            public String getAddresses() {
                return addresses;
            }

            /**
             * Sets addresses.
             *
             * @param addresses the addresses
             */
            public void setAddresses(String addresses) {
                this.addresses = addresses;
            }
        }
    }
}
