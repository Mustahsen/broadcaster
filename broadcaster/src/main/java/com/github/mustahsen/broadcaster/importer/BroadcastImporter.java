package com.github.mustahsen.broadcaster.importer;

import com.github.mustahsen.broadcaster.aspect.BroadcastAspect;
import com.github.mustahsen.broadcaster.factory.ResolverFactory;
import com.github.mustahsen.broadcaster.resolver.ArgumentResolver;
import com.github.mustahsen.broadcaster.resolver.ConstantResolver;
import com.github.mustahsen.broadcaster.resolver.FallbackResolver;
import com.github.mustahsen.broadcaster.resolver.ObjectResolver;
import com.github.mustahsen.broadcaster.resolver.ReturnValueResolver;
import com.github.mustahsen.broadcaster.utils.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The type Broadcast importer.
 */
@Configuration
@Import({
        BroadcastAspect.class,
        BeanUtils.class,
        ResolverFactory.class,
        ArgumentResolver.class,
        ConstantResolver.class,
        ReturnValueResolver.class,
        ObjectResolver.class,
        FallbackResolver.class
})
@ConfigurationPropertiesScan("com.github.mustahsen.broadcaster.*")
public class BroadcastImporter {
}
