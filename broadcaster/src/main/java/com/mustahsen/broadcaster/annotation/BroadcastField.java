package com.mustahsen.broadcaster.annotation;

import com.mustahsen.broadcaster.enums.BroadcastValueSource;
import com.mustahsen.broadcaster.resolver.BaseResolver;

public @interface BroadcastField {
    BroadcastValueSource source() default BroadcastValueSource.CONSTANT;
    Class<? extends BaseResolver> customResolver() default BaseResolver.class;
    String sourceValue() default "";
    String targetKey() default "";
}
