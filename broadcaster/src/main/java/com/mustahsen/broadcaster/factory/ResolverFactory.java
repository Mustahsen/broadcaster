package com.mustahsen.broadcaster.factory;

import com.mustahsen.broadcaster.annotation.BroadcastField;
import com.mustahsen.broadcaster.resolver.ArgumentResolver;
import com.mustahsen.broadcaster.resolver.BaseResolver;
import com.mustahsen.broadcaster.resolver.ConstantResolver;
import com.mustahsen.broadcaster.resolver.IResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResolverFactory {

    private ArgumentResolver argumentResolver;
    private ConstantResolver constantResolver;
    private BaseResolver baseResolver;

    @Autowired
    public ResolverFactory(ArgumentResolver argumentResolver,
                           ConstantResolver constantResolver,
                           BaseResolver baseResolver) {
        this.argumentResolver = argumentResolver;
        this.constantResolver = constantResolver;
        this.baseResolver = baseResolver;
    }



    public IResolver getResolver(BroadcastField broadcastField) {
        switch (broadcastField.source()) {
            case ARGUMENT:
                return argumentResolver;
            case CONSTANT:
                return constantResolver;
            case CUSTOM:
                return broadcastField.customResolver().cast(BaseResolver.class);
            default:
                return baseResolver;
        }
    }
}
