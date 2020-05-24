package com.mustahsen.broadcaster.annotation;

import com.mustahsen.broadcaster.enums.BroadcastValueSource;

import static com.mustahsen.broadcaster.enums.BroadcastValueSource.CONSTANT;

public @interface BroadcastPair {
    BroadcastValueSource valueSource() default CONSTANT;
    String value() default "";
    String key() default "";
}
