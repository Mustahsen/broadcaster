package com.mustahsen.broadcastersample.service;

import com.mustahsen.broadcaster.annotation.Broadcast;
import com.mustahsen.broadcaster.annotation.BroadcastPair;
import com.mustahsen.broadcastersample.dto.InnerObjectDto;
import com.mustahsen.broadcastersample.dto.SampleObjectDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mustahsen.broadcaster.enums.BroadcastValueSource.ARGUMENT;
import static com.mustahsen.broadcaster.enums.BroadcastValueSource.OBJECT;
import static com.mustahsen.broadcaster.enums.BroadcastValueSource.RETURN_VALUE;

@Slf4j
@Service
public class SampleService {



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
