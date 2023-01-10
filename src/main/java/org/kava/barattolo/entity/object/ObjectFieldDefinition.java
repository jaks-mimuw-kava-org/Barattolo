package org.kava.barattolo.entity.object;

import jakarta.persistence.*;

import java.lang.reflect.Field;

public record ObjectFieldDefinition(
        Field field,
        String name,
        ObjectFieldType fieldType
) {
    public static ObjectFieldDefinition of(Field field) {
        field.setAccessible(true);

        return new ObjectFieldDefinition(field, retrieveFieldName(field), retrieveFieldType(field));
    }

    public ObjectField withValue(Object value) {
        return new ObjectField(this, value);
    }

    private static String retrieveFieldName(Field field) {
        return field.getName();
    }

    private static ObjectFieldType retrieveFieldType(Field field) {
        ObjectFieldType fieldType;

        if (field.isAnnotationPresent(Id.class)) {
            fieldType = ObjectFieldType.PRIMARY_KEY;
        } else if (field.isAnnotationPresent(OneToMany.class)) {
            fieldType = ObjectFieldType.ONE_TO_MANY;
        } else if (field.isAnnotationPresent(ManyToOne.class)) {
            fieldType = ObjectFieldType.MANY_TO_ONE;
        } else if (field.isAnnotationPresent(ManyToMany.class)) {
            fieldType = ObjectFieldType.MANY_TO_MANY;
        } else if (field.isAnnotationPresent(OneToOne.class)) {
            fieldType = ObjectFieldType.ONE_TO_ONE;
        } else {
            fieldType = ObjectFieldType.FIELD;
        }

        return fieldType;
    }
}
