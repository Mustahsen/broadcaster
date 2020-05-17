package com.mustahsen.broadcaster.resolver;

import com.mustahsen.broadcaster.annotation.BroadcastField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ArgumentResolver implements IResolver {

    @Override
    public Object resolve(Object... arguments) {
        BroadcastField broadcastField = (BroadcastField) arguments[0];
        Map<String, Object> argumentMap = (Map<String, Object>) arguments[1];

        if (StringUtils.isBlank(broadcastField.sourceValue()) || MapUtils.isEmpty(argumentMap)) {
            return null;
        }

        String sourceValue = broadcastField.sourceValue();
        List<String> sourceValues = fields(sourceValue);

        if (CollectionUtils.isEmpty(sourceValues)) {
            return null;
        }

        if (sourceValues.size() == 1 && argumentMap.containsKey(sourceValue)) {
            return argumentMap.get(sourceValue);
        } else if (sourceValues.size() > 1 && argumentMap.containsKey(sourceValues.get(0))) {
            List<String> remainingFields = sourceValues.subList(1, sourceValues.size());
            try {
                Optional<Object> optional = Optional.of(resolveField(argumentMap.get(sourceValues.get(0)), remainingFields));

                if (optional.isPresent()) {
                    return optional.get();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
