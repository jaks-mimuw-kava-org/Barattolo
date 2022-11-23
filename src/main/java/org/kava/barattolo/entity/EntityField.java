package org.kava.barattolo.entity;

public record EntityField(
        String name,
        Class<?> type,
        Object value,
        boolean isPrimaryKey
) {
}
