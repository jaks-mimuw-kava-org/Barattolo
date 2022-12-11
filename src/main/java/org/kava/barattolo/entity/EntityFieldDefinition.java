package org.kava.barattolo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.reflect.Field;

public record EntityFieldDefinition(
        Field field,
        String classFieldName,
        String tableFieldName,
        boolean isPrimaryKey
) {
    public static EntityFieldDefinition of(Field field) {
        field.setAccessible(true);

        String classFieldName = field.getName();
        String tableFieldName = classFieldName;
        if (field.isAnnotationPresent(Column.class)) {
            tableFieldName = field.getAnnotation(Column.class).name();
        }
        return new EntityFieldDefinition(
                field,
                classFieldName,
                tableFieldName,
                field.isAnnotationPresent(Id.class)
        );
    }

    public EntityField withValue(Object value) {
        return new EntityField(this, value);
    }
}
