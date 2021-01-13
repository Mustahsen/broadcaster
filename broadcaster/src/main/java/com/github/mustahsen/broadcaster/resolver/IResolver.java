package com.github.mustahsen.broadcaster.resolver;

import com.github.mustahsen.broadcaster.annotation.BroadcastPair;
import com.github.mustahsen.broadcaster.exception.InvalidArgumentMapException;
import com.github.mustahsen.broadcaster.exception.InvalidBroadcastAnnotationException;
import com.github.mustahsen.broadcaster.exception.NullObjectException;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The interface Resolver.
 */
public interface IResolver {

    /**
     * Resolve object.
     *
     * @param arguments the arguments
     * @return the object
     * @throws InvalidBroadcastAnnotationException the invalid broadcast annotation exception
     * @throws InvalidArgumentMapException         the invalid argument map exception
     */
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

    /**
     * Resolve broadcast annotation broadcast pair.
     *
     * @param arguments the arguments
     * @return the broadcast pair
     */
    BroadcastPair resolveBroadcastAnnotation(Object... arguments);

    /**
     * Resolve arguments map.
     *
     * @param arguments the arguments
     * @return the map
     */
    Map<String, Object> resolveArguments(Object... arguments);

    /**
     * Resolve return value object.
     *
     * @param arguments the arguments
     * @return the object
     */
    Object resolveReturnValue(Object... arguments);

    /**
     * Resolve object object.
     *
     * @param arguments the arguments
     * @return the object
     */
    Object resolveObject(Object... arguments);

    /**
     * Validate resolver arguments.
     *
     * @param broadcastPair the broadcast pair
     * @param argumentMap   the argument map
     * @param returnValue   the return value
     * @param object        the object
     * @throws InvalidArgumentMapException         the invalid argument map exception
     * @throws InvalidBroadcastAnnotationException the invalid broadcast annotation exception
     * @throws NullPointerException                the null pointer exception
     */
    void validateResolverArguments(BroadcastPair broadcastPair, Map<String, Object> argumentMap, Object returnValue, Object object)
            throws InvalidArgumentMapException, InvalidBroadcastAnnotationException, NullPointerException;

    /**
     * Validate broadcast annotation.
     *
     * @param broadcastPair the broadcast pair
     * @throws InvalidBroadcastAnnotationException the invalid broadcast annotation exception
     */
    default void validateBroadcastAnnotation(BroadcastPair broadcastPair) throws InvalidBroadcastAnnotationException {
        if (StringUtils.isBlank(broadcastPair.value())) {
            throw new InvalidBroadcastAnnotationException("Value can't blank!");
        }
    }

    /**
     * Validate argument map.
     *
     * @param argumentMap the argument map
     * @throws InvalidArgumentMapException the invalid argument map exception
     */
    default void validateArgumentMap(Map<String, Object> argumentMap) throws InvalidArgumentMapException {
        if (MapUtils.isEmpty(argumentMap)) {
            throw new InvalidArgumentMapException("ArgumentMap can't be empty!");
        }
    }

    /**
     * Validate object.
     *
     * @param object       the object
     * @param argumentName the argument name
     * @throws NullObjectException the null object exception
     */
    default void validateObject(Object object, String argumentName) throws NullObjectException {
        if (Objects.isNull(object)) {
            throw new NullObjectException(argumentName);
        }
    }

    /**
     * Property accessors list.
     *
     * @param sourceValue the source value
     * @return the list
     */
    List<String> propertyAccessors(String sourceValue);

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
    Object getResolvable(List<String> propertyAccessors, BroadcastPair broadcastPair, Map<String, Object> argumentMap, Object returnValue, Object object);

    /**
     * Resolve property object.
     *
     * @param object            the object
     * @param propertyAccessors the property accessors
     * @return the object
     */
    Object resolveProperty(Object object, List<String> propertyAccessors);

    /**
     * Resolve field object.
     *
     * @param object            the object
     * @param propertyAccessors the property accessors
     * @return the object
     */
    Object resolveField(Object object, List<String> propertyAccessors);

    /**
     * Resolve field object.
     *
     * @param object           the object
     * @param propertyAccessor the property accessor
     * @return the object
     */
    Object resolveField(Object object, String propertyAccessor);

}
