package com.github.mustahsen.broadcastersample.service;

import com.github.mustahsen.broadcaster.annotation.Broadcast;
import com.github.mustahsen.broadcaster.annotation.BroadcastPair;
import com.github.mustahsen.broadcastersample.dto.InnerObjectDto;
import com.github.mustahsen.broadcastersample.dto.SampleObjectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.mustahsen.broadcaster.enums.BroadcastValueSource.ARGUMENT;
import static com.github.mustahsen.broadcaster.enums.BroadcastValueSource.OBJECT;
import static com.github.mustahsen.broadcaster.enums.BroadcastValueSource.RETURN_VALUE;

/**
 * The type Sample service.
 */
@Slf4j
@Service
public class SampleService {


    /**
     * Test broadcaster annotation sample object dto.
     *
     * @param arg1 the arg 1
     * @param arg2 the arg 2
     * @param arg3 the arg 3
     * @return the sample object dto
     */
    @Broadcast(
            target = "sample-topic",
            collection = @BroadcastPair(value = "arg3", valueSource = ARGUMENT),
            partitionKey = @BroadcastPair(value = "arg1", valueSource = ARGUMENT),
            body = {
                    @BroadcastPair(value = "arg2", key = "key1", valueSource = ARGUMENT),
                    @BroadcastPair(value = "value", key = "keyInteger", valueSource = OBJECT),
                    @BroadcastPair(value = "innerObjectDto.innerObjectString", key = "key2", valueSource = RETURN_VALUE)
            }
    )
    public SampleObjectDto testBroadcasterAnnotation(Long arg1, String arg2, List<Integer> arg3) {
        //some process
        SampleObjectDto sampleObjectDto = SampleObjectDto.builder()
                .stringMember(arg2)
                .integers(arg3)
                .longMember(arg1)
                .innerObjectDto(InnerObjectDto
                        .builder()
                        .innerObjectString(String.valueOf(Math.round(Math.random() * 100)))
                        .build())
                .build();
        log.info(sampleObjectDto.toString());
        return sampleObjectDto;
    }

}
