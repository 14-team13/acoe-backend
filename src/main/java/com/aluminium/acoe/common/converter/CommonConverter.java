package com.aluminium.acoe.common.converter;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CommonConverter {
    public <T, U> U convertToGeneric(T source, Class<U> targetClass) {
        if (source == null) {
            return null;
        } else {
            U target = BeanUtils.instantiateClass(targetClass);
            BeanUtils.copyProperties(source, target);
            return target;
        }
    }
}
