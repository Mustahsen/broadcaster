package com.mustahsen.broadcaster.factory;

import com.mustahsen.broadcaster.enums.BroadcastValueSource;
import com.mustahsen.broadcaster.resolver.ArgumentResolver;
import com.mustahsen.broadcaster.resolver.ConstantResolver;
import com.mustahsen.broadcaster.resolver.EmptyResolver;
import com.mustahsen.broadcaster.resolver.IResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResolverFactory {

    private ArgumentResolver argumentResolver;
    private ConstantResolver constantResolver;
    private EmptyResolver emptyResolver;

    @Autowired
    public ResolverFactory(ArgumentResolver argumentResolver,
                           ConstantResolver constantResolver,
                           EmptyResolver emptyResolver) {
        this.argumentResolver = argumentResolver;
        this.constantResolver = constantResolver;
        this.emptyResolver = emptyResolver;
    }



    public IResolver getResolver(BroadcastValueSource source) {
        switch (source) {
            case ARGUMENT:
                return argumentResolver;
            case CONSTANT:
                return constantResolver;
            default:
                return emptyResolver;
        }
    }
}
