package com.github.mustahsen.broadcaster.annotation;

import com.github.mustahsen.broadcaster.enums.BroadcastType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.github.mustahsen.broadcaster.enums.BroadcastType.KAFKA;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The interface Broadcast.
 */
@Target(value = METHOD)
@Retention(value = RUNTIME)
public @interface Broadcast {

    /**
     * Type broadcast type.
     *
     * @return the broadcast type
     */
    BroadcastType type() default KAFKA;

    /**
     * Target string.
     *
     * @return the string
     */
    String target() default "";

    /**
     * Collection broadcast pair.
     *
     * @return the broadcast pair
     */
    BroadcastPair collection() default @BroadcastPair;

    /**
     * Partition key broadcast pair.
     *
     * @return the broadcast pair
     */
    BroadcastPair partitionKey() default @BroadcastPair;

    /**
     * Body broadcast pair [ ].
     *
     * @return the broadcast pair [ ]
     */
    BroadcastPair[] body() default {};
}
