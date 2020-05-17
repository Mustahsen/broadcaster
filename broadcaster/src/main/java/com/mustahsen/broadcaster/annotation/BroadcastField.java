package com.mustahsen.broadcaster.annotation;

import com.mustahsen.broadcaster.enums.BroadcastValueSource;

public @interface BroadcastField {
    BroadcastValueSource broadcastValueSource() default BroadcastValueSource.CONSTANT;
    String key() default "";
    String value() default "";
}
