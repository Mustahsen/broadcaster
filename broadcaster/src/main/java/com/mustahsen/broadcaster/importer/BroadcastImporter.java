package com.mustahsen.broadcaster.importer;

import com.mustahsen.broadcaster.aspect.BroadcastAspect;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BroadcastAspect.class})
@ConfigurationPropertiesScan("com.mustahsen.broadcaster.*")
public class BroadcastImporter {
}
