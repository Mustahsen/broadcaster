package com.github.mustahsen.broadcaster.resolver;

import com.github.mustahsen.broadcaster.annotation.BroadcastPair;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * The type Argument resolver.
 */
@Component
public class ArgumentResolver extends BaseResolver {

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

        if (CollectionUtils.isEmpty(propertyAccessors)) {
            return null;
        } else if (propertyAccessors.size() == 1) {
            Object resolvable = argumentMap.getOrDefault(propertyAccessors.get(0), null);
            propertyAccessors.clear();
            return resolvable;
        } else if (propertyAccessors.size() > 1) {
            Object resolvable = argumentMap.getOrDefault(propertyAccessors.get(0), null);
            propertyAccessors.remove(0);
            return resolvable;
        } else {
            return returnValue;
        }
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
        validateArgumentMap(argumentMap);
    }
}
