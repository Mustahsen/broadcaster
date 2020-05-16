package com.mustahsen.broadcaster.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "broadcaster")
public class BroadcasterProperties {

    private Boolean kafkaEnabled = Boolean.FALSE;

    private KafkaProducer kafkaProducer = new KafkaProducer();

    public Boolean getKafkaEnabled() {
        return kafkaEnabled;
    }

    public void setKafkaEnabled(Boolean kafkaEnabled) {
        this.kafkaEnabled = kafkaEnabled;
    }

    public KafkaProducer getKafkaProducer() {
        return kafkaProducer;
    }

    public void setKafkaProducer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public class KafkaProducer {

        private Server server = new Server();

        private Integer retries = 0;
        private Integer batchSize = 16384;
        private Integer lingerMs = 1;
        private Integer bufferMemory = 33554432;
        private Integer maxBlockMs = 1000;

        public Server getServer() {
            return server;
        }

        public void setServer(Server server) {
            this.server = server;
        }

        public Integer getRetries() {
            return retries;
        }

        public void setRetries(Integer retries) {
            this.retries = retries;
        }

        public Integer getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(Integer batchSize) {
            this.batchSize = batchSize;
        }

        public Integer getLingerMs() {
            return lingerMs;
        }

        public void setLingerMs(Integer lingerMs) {
            this.lingerMs = lingerMs;
        }

        public Integer getBufferMemory() {
            return bufferMemory;
        }

        public void setBufferMemory(Integer bufferMemory) {
            this.bufferMemory = bufferMemory;
        }

        public Integer getMaxBlockMs() {
            return maxBlockMs;
        }

        public void setMaxBlockMs(Integer maxBlockMs) {
            this.maxBlockMs = maxBlockMs;
        }


        public class Server {

            private String addresses = "localhost:9092";

            public String getAddresses() {
                return addresses;
            }

            public void setAddresses(String addresses) {
                this.addresses = addresses;
            }
        }
    }
}
