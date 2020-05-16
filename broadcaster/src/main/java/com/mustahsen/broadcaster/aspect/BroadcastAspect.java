package com.mustahsen.broadcaster.aspect;

import com.mustahsen.broadcaster.Broadcaster;
import com.mustahsen.broadcaster.annotation.Broadcast;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BroadcastAspect {

    private Broadcaster broadcaster;

    @Autowired
    public BroadcastAspect(Broadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @Around(value = "@annotation(broadcast)")
    public void broadcastAfter(JoinPoint joinPoint, Broadcast broadcast) {
        log.info(joinPoint.toString());
        log.info("BroadcastAspect");
        broadcaster.broadcast();
    }
}
