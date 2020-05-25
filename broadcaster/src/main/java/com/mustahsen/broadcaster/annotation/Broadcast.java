package com.mustahsen.broadcaster.annotation;

import com.mustahsen.broadcaster.enums.BroadcastType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.mustahsen.broadcaster.enums.BroadcastType.KAFKA;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = METHOD)
@Retention(value = RUNTIME)
public @interface Broadcast {

    BroadcastType type() default KAFKA;
    String target() default "";

    BroadcastPair collection() default @BroadcastPair;
    BroadcastPair partitionKey() default @BroadcastPair;
    BroadcastPair[] body() default {};
}
