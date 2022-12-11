package org.kava.barattolo.entity;

public record EntityField(
        EntityFieldDefinition fieldDefinition,
        Object value
) {
}
