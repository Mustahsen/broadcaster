package com.mustahsen.broadcaster.resolver;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface IResolver {

    String EMPTY_STRING = "";
    String DOT_REGEX = "\\.";

    Object resolve(Object... arguments);

    default List<String> fields(String field) {
        return Arrays.asList(field.split(DOT_REGEX));
    }

    default Object resolveField(Object object, List<String> fields) throws IllegalAccessException {
        if (CollectionUtils.isEmpty(fields)) {
            return object;
        } else if (fields.size() == 1) {
            Optional<Field> optional = Optional.of(ReflectionUtils.findField(object.getClass(), fields.get(0)));
            if (optional.isPresent()) {
                Field field = optional.get();
                field.setAccessible(true);
                return field.get(object);
            }
            return null;
        } else {
            Optional<Field> optional = Optional.of(ReflectionUtils.findField(object.getClass(), fields.get(0)));
            if (optional.isPresent()) {
                Field field = optional.get();
                field.setAccessible(true);
                Object value = field.get(object);
                return resolveField(value, fields.subList(1, fields.size()));
            }
            return null;
        }
    }

}
