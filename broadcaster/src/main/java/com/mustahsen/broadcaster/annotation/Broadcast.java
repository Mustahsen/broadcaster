package com.mustahsen.broadcaster.annotation;

import com.mustahsen.broadcaster.enums.BroadcastType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = METHOD)
@Retention(value = RUNTIME)
public @interface Broadcast {

    BroadcastType broadcastType() default BroadcastType.KAFKA;
    BroadcastKey broadcastKey() default @BroadcastKey(active = false, value = "");
    BroadcastField[] broadcastFields() default {};

}
