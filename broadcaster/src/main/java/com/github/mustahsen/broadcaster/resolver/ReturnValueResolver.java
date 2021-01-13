package com.github.mustahsen.broadcaster.resolver;

import com.github.mustahsen.broadcaster.annotation.BroadcastPair;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * The type Return value resolver.
 */
@Component
public class ReturnValueResolver extends BaseResolver {

    private static final String RETURN_VALUE_REFERENCE_STRING = "this";

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
            return returnValue;
        } else if (propertyAccessors.size() == 1 && propertyAccessors.get(0).equals(RETURN_VALUE_REFERENCE_STRING)) {
            propertyAccessors.clear();
            return returnValue;
        } else if (propertyAccessors.size() > 1 && propertyAccessors.get(0).equals(RETURN_VALUE_REFERENCE_STRING)) {
            propertyAccessors.remove(0);
            return returnValue;
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
        validateObject(returnValue, "returnValue");
    }
}
