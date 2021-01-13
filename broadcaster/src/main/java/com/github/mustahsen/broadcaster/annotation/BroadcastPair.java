package com.github.mustahsen.broadcaster.annotation;

import com.github.mustahsen.broadcaster.enums.BroadcastValueSource;

import static com.github.mustahsen.broadcaster.enums.BroadcastValueSource.CONSTANT;

/**
 * The interface Broadcast pair.
 */
public @interface BroadcastPair {
    /**
     * Value source broadcast value source.
     *
     * @return the broadcast value source
     */
    BroadcastValueSource valueSource() default CONSTANT;

    /**
     * Value string.
     *
     * @return the string
     */
    String value() default "";

    /**
     * Key string.
     *
     * @return the string
     */
    String key() default "";
}
