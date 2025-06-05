package org.example.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.time.LocalTime;

public class ModeCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        LocalTime currentTime = LocalTime.now();
        // Проверяем, находится ли текущее время между 7 утра и 23 вечера, время работы
        return currentTime.isAfter(LocalTime.of(7, 0)) && currentTime.isBefore(LocalTime.of(23, 0));
    }
}
