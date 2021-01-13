package com.github.mustahsen.broadcaster.resolver;

import com.github.mustahsen.broadcaster.annotation.BroadcastPair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Base resolver.
 */
@Slf4j
public abstract class BaseResolver implements IResolver {

    /**
     * The constant DOT_REGEX.
     */
    public static final String DOT_REGEX = "\\.";

    /**
     * Property accessors list.
     *
     * @param sourceValue the source value
     * @return the list
     */
    @Override
    public List<String> propertyAccessors(String sourceValue) {
        return Arrays.stream(sourceValue.split(DOT_REGEX)).collect(Collectors.toList());
    }

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
    public abstract Object getResolvable(List<String> propertyAccessors,
                                         BroadcastPair broadcastPair,
                                         Map<String, Object> argumentMap,
                                         Object returnValue,
                                         Object object);

    /**
     * Resolve broadcast annotation broadcast pair.
     *
     * @param arguments the arguments
     * @return the broadcast pair
     */
    @Override
    public BroadcastPair resolveBroadcastAnnotation(Object... arguments) {
        return (BroadcastPair) arguments[0];
    }

    /**
     * Resolve arguments map.
     *
     * @param arguments the arguments
     * @return the map
     */
    @Override
    public Map<String, Object> resolveArguments(Object... arguments) {
        Object object = arguments[1];
        if (object instanceof Map) {
            return Map.class.cast(object);
        }
        return Collections.emptyMap();
    }

    /**
     * Resolve return value object.
     *
     * @param arguments the arguments
     * @return the object
     */
    @Override
    public Object resolveReturnValue(Object... arguments) {
        return arguments[2];
    }

    /**
     * Resolve object object.
     *
     * @param arguments the arguments
     * @return the object
     */
    @Override
    public Object resolveObject(Object... arguments) {
        if (arguments.length > 3) {
            return arguments[3];
        }
        return null;
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
    public abstract void validateResolverArguments(BroadcastPair broadcastPair,
                                                   Map<String, Object> argumentMap,
                                                   Object returnValue,
                                                   Object object) throws RuntimeException;

    /**
     * Resolve property object.
     *
     * @param object            the object
     * @param propertyAccessors the property accessors
     * @return the object
     */
    @Override
    public Object resolveProperty(Object object, List<String> propertyAccessors) {
        if (CollectionUtils.isEmpty(propertyAccessors) || Objects.isNull(object)) {
            return object;
        } else if (propertyAccessors.size() == 1) {
            return resolveField(object, propertyAccessors.get(0));
        } else {
            return resolveField(object, propertyAccessors);
        }
    }

    /**
     * Resolve field object.
     *
     * @param object            the object
     * @param propertyAccessors the property accessors
     * @return the object
     */
    @Override
    public Object resolveField(Object object, List<String> propertyAccessors) {
        Field field = ReflectionUtils.findField(object.getClass(), propertyAccessors.get(0));
        if (Objects.nonNull(field)) {
            field.setAccessible(true);
            try {
                return resolveProperty(field.get(object), propertyAccessors.subList(1, propertyAccessors.size()));
            } catch (IllegalAccessException e) {
                log.info("An error occurred during accessing the field: " + field, e);
            }
        }
        return object;
    }

    /**
     * Resolve field object.
     *
     * @param object           the object
     * @param propertyAccessor the property accessor
     * @return the object
     */
    @Override
    public Object resolveField(Object object, String propertyAccessor) {
        Field field = ReflectionUtils.findField(object.getClass(), propertyAccessor);
        if (Objects.nonNull(field)) {
            field.setAccessible(true);
            try {
                return field.get(object);
            } catch (IllegalAccessException e) {
                log.info("An error occurred during accessing the field: " + field, e);
            }
        }
        return object;
    }

}
