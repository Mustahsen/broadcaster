package com.mustahsen.broadcaster.aspect;

import com.mustahsen.broadcaster.Broadcaster;
import com.mustahsen.broadcaster.annotation.Broadcast;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
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
    public void process(ProceedingJoinPoint proceedingJoinPoint, Broadcast broadcast) throws Throwable {
        log.info(proceedingJoinPoint.toString());
        proceedingJoinPoint.proceed();
        log.info("process");
        //((org.aspectj.lang.reflect.MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames()
        broadcaster.broadcast();
    }

}
