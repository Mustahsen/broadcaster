package com.mustahsen.broadcaster.importer;

import com.mustahsen.broadcaster.aspect.BroadcastAspect;
import com.mustahsen.broadcaster.factory.ResolverFactory;
import com.mustahsen.broadcaster.resolver.ArgumentResolver;
import com.mustahsen.broadcaster.resolver.BaseResolver;
import com.mustahsen.broadcaster.resolver.ConstantResolver;
import com.mustahsen.broadcaster.utils.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        BroadcastAspect.class,
        BeanUtils.class,
        ResolverFactory.class,
        ArgumentResolver.class,
        ConstantResolver.class,
        BaseResolver.class
})
@ConfigurationPropertiesScan("com.mustahsen.broadcaster.*")
public class BroadcastImporter {
}
