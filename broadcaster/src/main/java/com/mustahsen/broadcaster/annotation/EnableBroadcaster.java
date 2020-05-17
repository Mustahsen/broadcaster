package com.mustahsen.broadcaster.annotation;

import com.mustahsen.broadcaster.importer.BroadcastImporter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(BroadcastImporter.class)
@Documented
public @interface EnableBroadcaster {
}
