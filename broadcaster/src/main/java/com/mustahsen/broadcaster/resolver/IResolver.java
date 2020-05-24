package com.mustahsen.broadcaster.resolver;

import com.mustahsen.broadcaster.annotation.BroadcastPair;
import com.mustahsen.broadcaster.exception.InvalidArgumentMapException;
import com.mustahsen.broadcaster.exception.InvalidBroadcastAnnotationException;
import com.mustahsen.broadcaster.exception.NullObjectException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface IResolver {

    default Object resolve(Object... arguments) throws InvalidBroadcastAnnotationException, InvalidArgumentMapException {
        BroadcastPair broadcastPair = resolveBroadcastAnnotation(arguments);
        Map<String, Object> argumentMap = resolveArguments(arguments);
        Object returnValue = resolveReturnValue(arguments);
        Object object = resolveObject(arguments);

        validateResolverArguments(broadcastPair, argumentMap, returnValue, object);

        String sourceValue = broadcastPair.value();
        List<String> propertyAccessors = propertyAccessors(sourceValue);
        Object resolvable = getResolvable(propertyAccessors, broadcastPair, argumentMap, returnValue, object);
        return resolveProperty(resolvable, propertyAccessors);
    }

    BroadcastPair resolveBroadcastAnnotation(Object... arguments);

    Map<String, Object> resolveArguments(Object... arguments);

    Object resolveReturnValue(Object... arguments);

    Object resolveObject(Object... arguments);

    void validateResolverArguments(BroadcastPair broadcastPair, Map<String, Object> argumentMap, Object returnValue, Object object)
            throws InvalidArgumentMapException, InvalidBroadcastAnnotationException, NullPointerException;

    default void validateBroadcastAnnotation(BroadcastPair broadcastPair) throws InvalidBroadcastAnnotationException {
        if (StringUtils.isBlank(broadcastPair.value())) {
            throw new InvalidBroadcastAnnotationException("Value can't blank!");
        }
    }

    default void validateArgumentMap(Map<String, Object> argumentMap) throws InvalidArgumentMapException {
        if (MapUtils.isEmpty(argumentMap)) {
            throw new InvalidArgumentMapException("ArgumentMap can't be empty!");
        }
    }

    default void validateObject(Object object, String argumentName) throws NullObjectException {
        if (Objects.isNull(object)) {
            throw new NullObjectException(argumentName);
        }
    }

    List<String> propertyAccessors(String sourceValue);

    Object getResolvable(List<String> propertyAccessors, BroadcastPair broadcastPair, Map<String, Object> argumentMap, Object returnValue, Object object);

    Object resolveProperty(Object object, List<String> propertyAccessors);

    Object resolveField(Object object, List<String> propertyAccessors);

    Object resolveField(Object object, String propertyAccessor);

}
