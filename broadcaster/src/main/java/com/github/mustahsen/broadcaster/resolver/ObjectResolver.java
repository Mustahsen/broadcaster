package com.github.mustahsen.broadcaster.resolver;

import com.github.mustahsen.broadcaster.annotation.BroadcastPair;

import java.util.List;
import java.util.Map;

/**
 * The type Object resolver.
 */
public class ObjectResolver extends BaseResolver {

    /**
     * Gets resolvable.
     *
     * @param propertyAccessors the property accessors
     * @param broadcastPair     the broadcast pair
     * @param argumentMap       the argument map
     * @param returnValue       the return value
     * @param object            the object
     * @return the resolvable
     */
    @Override
    public Object getResolvable(List<String> propertyAccessors,
                                BroadcastPair broadcastPair,
                                Map<String, Object> argumentMap,
                                Object returnValue,
                                Object object) {
        return object;
    }

    /**
     * Validate resolver arguments.
     *
     * @param broadcastPair the broadcast pair
     * @param argumentMap   the argument map
     * @param returnValue   the return value
     * @param object        the object
     * @throws RuntimeException the runtime exception
     */
    @Override
    public void validateResolverArguments(BroadcastPair broadcastPair, Map<String, Object> argumentMap, Object returnValue, Object object)
            throws RuntimeException {
        validateBroadcastAnnotation(broadcastPair);
        validateObject(object, "resolvable");
    }
}
