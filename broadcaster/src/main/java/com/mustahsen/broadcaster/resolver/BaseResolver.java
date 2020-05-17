package com.mustahsen.broadcaster.resolver;

import org.springframework.stereotype.Component;

@Component
public class BaseResolver implements IResolver {

    @Override
    public Object resolve(Object... arguments) {
        return null;
    }

}
