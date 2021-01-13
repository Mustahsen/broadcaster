package com.github.mustahsen.broadcaster.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Bytes;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;


/**
 * The type Kafka producer callback listener.
 */
@Slf4j
public class KafkaProducerCallbackListener implements ListenableFutureCallback<SendResult<Bytes, Object>> {

    /**
     * On failure.
     *
     * @param throwable the throwable
     */
    @Override
    public void onFailure(Throwable throwable) {
        log.error("Error sending message: {}", throwable.getMessage(), throwable);
    }

    /**
     * On success.
     *
     * @param sendResult the send result
     */
    @Override
    public void onSuccess(SendResult<Bytes, Object> sendResult) {
        if (sendResult != null
                && sendResult.getProducerRecord() != null
                && sendResult.getProducerRecord().value() != null) {

            String topic = sendResult.getProducerRecord().topic();
            log.info("Successful produce! Topic: {} Key: {} Data: {}",
                    topic, sendResult.getProducerRecord().key(), sendResult.getProducerRecord().value()
            );

        } else {
            log.info("Successful produce but not enough information!)");
        }
    }
}
