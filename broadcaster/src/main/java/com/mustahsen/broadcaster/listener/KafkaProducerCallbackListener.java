package com.mustahsen.broadcaster.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Slf4j
public class KafkaProducerCallbackListener implements ListenableFutureCallback<SendResult<Long, Object>> {

    @Override
    public void onFailure(Throwable throwable) {
        log.error("Error sending message: {}", throwable.getMessage(), throwable);
    }

    @Override
    public void onSuccess(SendResult<Long, Object> sendResult) {
        if (sendResult != null
                && sendResult.getProducerRecord() != null
                && sendResult.getProducerRecord().value() != null) {

            String topic = sendResult.getProducerRecord().topic();
            log.info("Successful produce! Topic: {} Data: {}",
                    topic, sendResult.getProducerRecord().value()
            );

        } else {
            log.info("Successful produce but not enough information!)");
        }
    }
}
