package com.mustahsen.broadcaster.resolver;

import com.mustahsen.broadcaster.annotation.BroadcastPair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class FallbackResolver extends BaseResolver {

    @Override
    public Object getResolvable(List<String> propertyAccessors,
                                BroadcastPair broadcastPair,
                                Map<String, Object> argumentMap,
                                Object returnValue,
                                Object object) {

        return null;
    }

    @Override
    public void validateResolverArguments(BroadcastPair broadcastPair, Map<String, Object> argumentMap, Object returnValue, Object object)
            throws RuntimeException {
        validateObject(broadcastPair.value(), "broadcastPair.value");
    }
}
