package com.mustahsen.broadcaster.aspect;

import com.mustahsen.broadcaster.Broadcaster;
import com.mustahsen.broadcaster.annotation.Broadcast;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
    public void process(ProceedingJoinPoint proceedingJoinPoint, Broadcast broadcast) {
        Map<String, Object> argumentMap = getArgumentMap(proceedingJoinPoint);

        try {
            broadcaster.broadcast(broadcast, argumentMap, proceedingJoinPoint.proceed());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Map<String, Object> getArgumentMap(ProceedingJoinPoint proceedingJoinPoint) {
        Map<String, Object> argumentMap = new HashMap<>();
        Object[] parameterValues = proceedingJoinPoint.getArgs();
        String[] parameterNames = ((org.aspectj.lang.reflect.MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        for (int i = 0; i < parameterNames.length; ++i) {
            argumentMap.put(parameterNames[i], parameterValues[i]);
        }
        return argumentMap;
    }


}
