package org.kava.entity;

public record EntityField(
        String name,
        Class<?> type,
        Object value,
        boolean isPrimaryKey
) {
}
