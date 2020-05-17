package com.mustahsen.broadcaster.resolver;

import com.mustahsen.broadcaster.annotation.BroadcastField;
import org.springframework.stereotype.Component;

@Component
public class ConstantResolver implements IResolver {

    @Override
    public Object resolve(Object... arguments) {
        return ((BroadcastField) arguments[0]).sourceValue();
    }

}
