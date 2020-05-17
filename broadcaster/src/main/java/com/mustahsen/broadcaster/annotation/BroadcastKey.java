package com.mustahsen.broadcaster.annotation;

import com.mustahsen.broadcaster.enums.BroadcastValueSource;

public @interface BroadcastKey {
    BroadcastValueSource source() default BroadcastValueSource.CONSTANT;
    boolean active();
    String value();
}
