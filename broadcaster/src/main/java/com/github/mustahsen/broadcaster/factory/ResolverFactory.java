package com.github.mustahsen.broadcaster.factory;

import com.github.mustahsen.broadcaster.annotation.BroadcastPair;
import com.github.mustahsen.broadcaster.resolver.ArgumentResolver;
import com.github.mustahsen.broadcaster.resolver.ConstantResolver;
import com.github.mustahsen.broadcaster.resolver.FallbackResolver;
import com.github.mustahsen.broadcaster.resolver.IResolver;
import com.github.mustahsen.broadcaster.resolver.ObjectResolver;
import com.github.mustahsen.broadcaster.resolver.ReturnValueResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Resolver factory.
 */
@Component
public class ResolverFactory {

    private ArgumentResolver argumentResolver;
    private ConstantResolver constantResolver;
    private ReturnValueResolver returnValueResolver;
    private ObjectResolver objectResolver;
    private FallbackResolver fallbackResolver;

    /**
     * Instantiates a new Resolver factory.
     *
     * @param argumentResolver    the argument resolver
     * @param constantResolver    the constant resolver
     * @param returnValueResolver the return value resolver
     * @param objectResolver      the object resolver
     * @param fallbackResolver    the fallback resolver
     */
    @Autowired
    public ResolverFactory(ArgumentResolver argumentResolver,
                           ConstantResolver constantResolver,
                           ReturnValueResolver returnValueResolver,
                           ObjectResolver objectResolver,
                           FallbackResolver fallbackResolver) {
        this.argumentResolver = argumentResolver;
        this.constantResolver = constantResolver;
        this.returnValueResolver = returnValueResolver;
        this.objectResolver = objectResolver;
        this.fallbackResolver = fallbackResolver;
    }

    /**
     * Gets resolver.
     *
     * @param broadcastPair the broadcast pair
     * @return the resolver
     */
    public IResolver getResolver(BroadcastPair broadcastPair) {
        switch (broadcastPair.valueSource()) {
            case ARGUMENT:
                return argumentResolver;
            case CONSTANT:
                return constantResolver;
            case RETURN_VALUE:
                return returnValueResolver;
            case OBJECT:
                return objectResolver;
            default:
                return fallbackResolver;
        }
    }
}
