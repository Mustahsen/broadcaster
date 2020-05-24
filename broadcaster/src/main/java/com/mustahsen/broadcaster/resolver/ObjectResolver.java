package com.mustahsen.broadcaster.resolver;

import com.mustahsen.broadcaster.annotation.BroadcastPair;

import java.util.List;
import java.util.Map;

public class ObjectResolver extends BaseResolver {

    @Override
    public Object getResolvable(List<String> propertyAccessors,
                                BroadcastPair broadcastPair,
                                Map<String, Object> argumentMap,
                                Object returnValue,
                                Object object) {
        return object;
    }

    @Override
    public void validateResolverArguments(BroadcastPair broadcastPair, Map<String, Object> argumentMap, Object returnValue, Object object)
            throws RuntimeException {
        validateBroadcastAnnotation(broadcastPair);
        validateObject(object, "resolvable");
    }
}
