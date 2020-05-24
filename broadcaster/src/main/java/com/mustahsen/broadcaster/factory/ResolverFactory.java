package com.mustahsen.broadcaster.factory;

import com.mustahsen.broadcaster.annotation.BroadcastPair;
import com.mustahsen.broadcaster.resolver.ArgumentResolver;
import com.mustahsen.broadcaster.resolver.ConstantResolver;
import com.mustahsen.broadcaster.resolver.FallbackResolver;
import com.mustahsen.broadcaster.resolver.IResolver;
import com.mustahsen.broadcaster.resolver.ObjectResolver;
import com.mustahsen.broadcaster.resolver.ReturnValueResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResolverFactory {

    private ArgumentResolver argumentResolver;
    private ConstantResolver constantResolver;
    private ReturnValueResolver returnValueResolver;
    private ObjectResolver objectResolver;
    private FallbackResolver fallbackResolver;

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
